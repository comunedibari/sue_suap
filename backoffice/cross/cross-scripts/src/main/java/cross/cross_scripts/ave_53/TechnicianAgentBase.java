package cross.cross_scripts.ave_53;

import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;

import cross.cross_scripts.MySql;
import it.wego.cross.plugins.aec.ConcessioniAutorizzazioniNamespaceContext;

public class TechnicianAgentBase {

	public static final String SELECT_FROM_PRATICA = "SELECT p.id_pratica, p.identificativo_pratica, p.id_staging, p.data_ricezione "
			+ "from opencross.pratica p "
			+ "where p.id_pratica between :V_A and :V_B";
	
	public static final String SELECT_PRATICHE_EVENTI = "select pe.id_pratica_evento, pe.data_evento, pe.note "
			+ "from opencross.pratiche_eventi pe inner join opencross.processi_eventi poe on pe.id_evento = poe.id_evento \r\n"
			+ "where poe.cod_evento = 'MODPROC' and id_pratica = ";

	public static final String SELECT_PRATICHE_EVENTI_RAW = "select pe.id_pratica_evento, pe.data_evento, pe.note "
			+ "from opencross.pratiche_eventi pe "
			+ "where pe.id_evento in :ID_EVENTS and id_pratica = :PAPERWORK_ID";

	public static final String SELECT_FROM_STAGING = "SELECT CONVERT(s.xml_ricevuto using utf8) "
	+ "from  opencross.staging s "
	+ "where s.id_staging = ";

	public static final String SELECT_FROM_STAGING_RAW = "SELECT s.xml_ricevuto "
			+ "from  opencross.staging s "
			+ "where s.id_staging = ";

	public static final String SELECT_FROM_TMP_PRATICA_OWNER = "SELECT technician_tax_code from opencross.tmp_pratica_owner where id_pratica = :ID_PRATICA";

	public static final String SELECT_FROM_PRATICA_AND_TMP = "SELECT p.id_pratica, p.identificativo_pratica, p.id_staging, p.data_ricezione "
			+ "from opencross.pratica p "
			+ "WHERE not exists(select 1 from opencross.tmp_pratica_owner tpo where tpo.id_pratica = p.id_pratica) "
			+ "AND p.id_pratica between :V_A and :V_B";
	public static final String SELECT_FROM_PRATICA_AND_PRATICA_OWNER = "SELECT p.id_pratica, p.identificativo_pratica, p.id_staging, p.data_ricezione "
			+ "from opencross.pratica p "
			+ "WHERE not exists(select 1 from opencross.pratica_procuratore pp where pp.id_pratica = p.id_pratica) "
			+ "AND p.id_pratica between :V_A and :V_B";
	
	
	public static final String CHANGE_TECHNICIAN_AGENT_1 = "Cambio tecnico procuratore associato da ";
	public static final String CHANGE_TECHNICIAN_AGENT_2 = " a ";

	private static final Logger logger = LogManager.getLogger(TechnicianAgentBase.class);

	protected MySql openCrossMySql;

	protected String modProcIdsString;
	
	public TechnicianAgentBase(MySql openCrossMySql) {
		this.openCrossMySql = openCrossMySql;
	}

	/* ****************************
	 * FROM XML
	 * ****************************/
	
	protected String getOwnerTaxCodeFromTable(Integer idPratica) throws SQLException {
		String query = SELECT_FROM_TMP_PRATICA_OWNER.replace(":ID_PRATICA", idPratica.toString());
		List<String> rets = executeSelect(query, set ->  {
			String taxCode = null;
			try{
				taxCode = set.getString(1);
			} catch (SQLException e) {
				throw new RuntimeException("Error temporary owner");
			}
			return taxCode;
		});

		return rets.get(0);
	}

	protected String getOwnerTaxCodeFromXml(Integer idStaging) throws SQLException {
		Statement stmt = openCrossMySql.getStatement();
		ResultSet set = stmt.executeQuery(SELECT_FROM_STAGING_RAW + idStaging);
		String taxCode = null;
		while(set.next()) {
			byte[] bytes = set.getBytes(1);
			if(bytes != null) {
				String data = new String(bytes);
	
				if(data != null) {
					taxCode = getStringTxt(data, "Titolare","ogg:PersonaFisica","ogg:CodiceFiscale");
	//				taxCode = getString(data, "//:Titolare/ogg:PersonaFisica/ogg:CodiceFiscale");
				}
			}
		}
		stmt.close();
		return taxCode;
	}
	
	protected String getStringTxt(String data, String... xpattern)  {

		String ret = "";
		String dataToEvaluate = data;
		int indexOfStart0 = dataToEvaluate.indexOf("<"+xpattern[0]+">");
		int indexOfEnd0 = dataToEvaluate.indexOf("</"+xpattern[0]+">");

		if(indexOfStart0 > -1 && indexOfEnd0 > -1) {
			dataToEvaluate = dataToEvaluate.substring(indexOfStart0, indexOfEnd0);
			int indexOfStart1 = dataToEvaluate.indexOf("<"+xpattern[1]+">");
			int indexOfEnd1 = dataToEvaluate.indexOf("</"+xpattern[1]+">");
			
			if(indexOfStart0 > -1 && indexOfEnd0 > -1) {
				dataToEvaluate = dataToEvaluate.substring(indexOfStart1, indexOfEnd1);
				Matcher m = Pattern.compile("<"+xpattern[2]+">\\s*(\\w+)\\s*</"+xpattern[2]+">").matcher(dataToEvaluate);
				if(m.find()) {
					ret = m.group(1);
				}
			}
		}
		return ret;
	}
	
	protected String getString(String data, String xpattern)  {
		InputSource source = new InputSource(new StringReader(data));
		XPath xpath = XPathFactory.newInstance().newXPath();
		NamespaceContext context = new ConcessioniAutorizzazioniNamespaceContext(
				"cea", "http://gruppoinit.it/b110/ConcessioniEAutorizzazioni/procedimentoUnico",
				"ogg", "http://egov.diviana.it/b109/OggettiCondivisi",
				"", "http://gruppoinit.it/b110/ConcessioniEAutorizzazioni/procedimentoUnico");
		xpath.setNamespaceContext(context);

		String ret = null;
		try {
			ret = xpath.evaluate(xpattern, source);
		} catch (XPathExpressionException e) {
			logger.error("errore in xpath reading");
		}

		return ret;
	}


	/* ****************************
	 * GENERIC QUERIES
	 * ****************************/
	
	protected void setModProcIds() throws SQLException {
		List<Integer> modProcId = this.modProcIds();
		
		this.modProcIdsString = modProcId.stream().map(a->a.toString()).reduce((a,b)->a+","+b).get();
	}
	
	protected List<Integer> modProcIds() throws SQLException {
		String query = "select id_evento FROM opencross.processi_eventi where cod_evento = 'MODPROC'";
		return executeSelect(query, set ->  {
			int number = -1;
			try{
				number = set.getInt(1);
			} catch (SQLException e) {
				throw new RuntimeException("Error in min");
			}
			return number;
		});
		
	}
	protected int min() throws SQLException {
		String query = "SELECT min(id_pratica) from opencross.pratica";
		List<Integer> rets = executeSelect(query, set ->  {
			int number = -1;
			try{
				number = set.getInt(1);
			} catch (SQLException e) {
				throw new RuntimeException("Error in min");
			}
			return number;
		});

		return rets.get(0);
	}

	protected int max() throws SQLException {
		String query = "SELECT max(id_pratica) from opencross.pratica";
		List<Integer> rets = executeSelect(query, set ->  {
			int number = -1;
			try{
				number = set.getInt(1);
			} catch (SQLException e) {
				throw new RuntimeException("Error in max");
			}
			return number;
		});

		return rets.get(0);
	}

	protected <T> List<T> executeSelect(String query, Function<ResultSet, T> c) throws SQLException {
		List<T> rets = new LinkedList<>();
		Statement stmt = openCrossMySql.getStatement();
		ResultSet set = stmt.executeQuery(query);

		while(set.next()) {
			T value = c.apply(set);
			rets.add(value);
		}
		stmt.close();
		return rets;
	}

	protected void consumeSelect(String query, Consumer<ResultSet> consumer) throws SQLException {
		Statement stmt = openCrossMySql.getStatement();
		ResultSet set = stmt.executeQuery(query);
		
		while(set.next()) {
			consumer.accept(set);
		}
		stmt.close();
	}
	
	/* ****************************
	 * CROSS BO FO
	 * ****************************/

	protected List<Object> extractTechnicalAgent(String identificativoPratica) throws SQLException{
		Set<String> set = new HashSet<>();

		String[] dbs = {"people_g", "people_m", "people_bari"};

		for(int i = 0; i < dbs.length; i++) {
			String technicalAgent = (extractTechnicalAgentFromSubmittedProcess(identificativoPratica, dbs[i]));
			if(technicalAgent != null && !set.contains(technicalAgent))
				set.add(technicalAgent);
			technicalAgent = (extractTechnicalAgentFromPendingProcess(identificativoPratica, dbs[i]));
			if(technicalAgent != null && !set.contains(technicalAgent))
				set.add(technicalAgent);
		}

		return Arrays.asList(set.toArray());

	}

	protected String extractTechnicalAgentFromSubmittedProcess(String identificativoPratica, String dbName) throws SQLException {
		String query  ="SELECT e.user_id from "+dbName+".submitted_process e where e.people_protocol_id = '"+identificativoPratica+"';";
		List<String> rets = executeSelect(query, set ->  {
			String number = "";
			try{
				number = set.getString(1);
			} catch (SQLException e) {
				throw new RuntimeException("Error extractTechnicalAgentFromPendingProcess min");
			}
			return number;
		});
		return rets.isEmpty()? null: rets.get(0);
	}

	protected String extractTechnicalAgentFromPendingProcess(String identificativoPratica, String dbName) throws SQLException {
		String query = "SELECT e.user_id from "+dbName+".pending_process e where e.process_data_id = '"+identificativoPratica+"';";
		List<String> rets = executeSelect(query, set ->  {
			String number = "";
			try{
				number = set.getString(1);
			} catch (SQLException e) {
				throw new RuntimeException("Error extractTechnicalAgentFromPendingProcess min");
			}
			return number;
		});
		return rets.isEmpty()? null: rets.get(0);
	}
}
