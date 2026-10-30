package cross.cross_scripts.operations;

import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import cross.cross_scripts.MySql;
import it.wego.cross.plugins.aec.ConcessioniAutorizzazioniNamespaceContext;

public class PathOperazioniReader {
	
	private static final Logger logger = LogManager.getLogger(PathOperazioniReader.class);
	private MySql mysql;
	
	public PathOperazioniReader(MySql mysql) {
		this.mysql = mysql;
		this.mysql.setAutocommit(true);
	} 
	
	public void run() throws Exception {

		int increment = 100;
		logger.info("Start Operazioni");

		int min = min(mysql);
		int max = max(mysql);
		for(int i = min; i < max; i+=increment) {
			logger.info("Iteration: " + i);
			extract(mysql, i , i + increment-1);
		}
		mysql.close();
		logger.info("End Operazioni");
	}

	private int min(MySql mysql) throws ClassNotFoundException, SQLException {
		int number = -1;
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery("SELECT min(id_staging) from staging");
		while(set.next()) {
			number = set.getInt(1);
		}
		stmt.close();
		return number;
	}

	private int max(MySql mysql) throws ClassNotFoundException, SQLException {
		int number = -1;
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery("SELECT max(id_staging) from staging");
		while(set.next()) {
			number = set.getInt(1);
		}
		stmt.close();
		return number;
	}

	private void extract(MySql mysql, int a, int b) throws SQLException, ClassNotFoundException {
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery("SELECT p.id_pratica, CONVERT( s.xml_ricevuto using utf8) "
				+ "from staging s inner join pratica p on s.id_staging = p.id_staging where s.xml_ricevuto is not null and s.id_staging between " + a + " and " + b);
		while(set.next()) {
			String idPratica = set.getString(1);
			logger.info("idPratica: " + idPratica);
			String data = set.getString(2);
			List<OperationNode> operations = null;
			if(data != null) {
				operations = getOperations(data);
				if(operations.size() > 0) {
					for(int j = 0; j < operations.size();j++) {
						String opCode = operations.get(j).getOpCode();
						String code = operations.get(j).getCode();
						String descrizione = operations.get(j).getDes();
						Integer id = null; 

						Statement stmtSelect = mysql.getStatement();
						ResultSet rset = stmtSelect.executeQuery("select id from OPERAZIONE where codice_operazione = '"+opCode+"'");
						while(rset.next()){
							id = rset.getInt(1);
						}
						stmtSelect.close();
						
						if(id == null) {
							Statement stmtInsert = mysql.getStatement();
							String insert = "INSERT INTO OPERAZIONE(codice_operazione, codice, descrizione) values('"+opCode+"','"+code+"', '"+descrizione.replaceAll("'", "\\\\'")+"')";
							stmtInsert.execute(insert);
							stmtInsert.close();
							
							stmtSelect = mysql.getStatement();
							rset = stmtSelect.executeQuery("select id from OPERAZIONE where codice_operazione = '"+opCode+"'");
							while(rset.next()){
								id = rset.getInt(1);
							}
							stmtSelect.close();
						}
						try {
							Statement stmtInsert = mysql.getStatement();
							String insert = "INSERT INTO PRATICA_OPERAZIONE(ID_PRATICA, ID_OPERAZIONE) values("+idPratica+", "+id+")";
							stmtInsert.execute(insert);
							stmtInsert.close();
						}
						catch(Exception e) {
							logger.info(e.getMessage());
						}
						
					}

				}
			}
		}
		stmt.close();
	}

	private List<OperationNode> getOperations(String data)  {
		InputSource source = new InputSource(new StringReader(data));
		XPath xpath = XPathFactory.newInstance().newXPath();
		NamespaceContext context = new ConcessioniAutorizzazioniNamespaceContext(
				"cea", "http://gruppoinit.it/b110/ConcessioniEAutorizzazioni/procedimentoUnico",
				"ogg", "http://egov.diviana.it/b109/OggettiCondivisi",
				"", "http://gruppoinit.it/b110/ConcessioniEAutorizzazioni/procedimentoUnico");
		xpath.setNamespaceContext(context);

		List<OperationNode> ret = new LinkedList<>();
		try {
			XPathExpression expr = xpath.compile("//:OperazioniAttivita/:Operazione");
			NodeList list= (NodeList) expr.evaluate(source, XPathConstants.NODESET);
			for (int i = 0; i < list.getLength(); i++) {
				Node nodei = list.item(i);


				XPathExpression exprOpCode = xpath.compile("//:OperazioniAttivita/:Operazione["+(i+1)+"]/:CodOperazione");
				XPathExpression exprDes = xpath.compile("//:OperazioniAttivita/:Operazione["+(i+1)+"]/:DesOperazione");
				XPathExpression exprCode = xpath.compile("//:OperazioniAttivita/:Operazione["+(i+1)+"]/:Codice");
				String opCode = (String) exprOpCode.evaluate(nodei, XPathConstants.STRING);
				String des = (String) exprDes.evaluate(nodei, XPathConstants.STRING);
				String code = (String) exprCode.evaluate(nodei, XPathConstants.STRING);
//				logger.info(code);
				ret.add(new OperationNode(opCode,code, des));


			}
		} catch (XPathExpressionException e) {
			logger.error("errore in:" + data);
		}

		return ret;
	}
}
