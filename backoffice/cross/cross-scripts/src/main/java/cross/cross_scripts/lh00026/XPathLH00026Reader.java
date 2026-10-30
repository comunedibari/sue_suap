package cross.cross_scripts.lh00026;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;

import cross.cross_scripts.Main;
import cross.cross_scripts.MySql;
import it.wego.cross.plugins.aec.ConcessioniAutorizzazioniNamespaceContext;

public class XPathLH00026Reader {
	
	private static final Logger logger = LogManager.getLogger(Main.class);
	private MySql mysql;
	
	public XPathLH00026Reader(MySql mysql) { 
		this.mysql = mysql;
	}
			
	public void run() throws Exception {

		logger.info("Start LH00026");
		
		int min = min(mysql);
		int max = max(mysql);
		for(int i = min; i < max; i+=1000) {
			logger.info("Iteration: " + i);
			extract(mysql, i , i + 1000);
		}
		mysql.close();
		
		logger.info("End LH00026");
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
		ResultSet set = stmt.executeQuery("SELECT s.id_staging, CONVERT( s.xml_ricevuto using utf8) "
				+ "from opencross.pratica p, opencross.staging s "
				+ "where p.id_staging = s.id_staging "
				+ "and id_pratica not in (select r.id_pratica from opencross.risultato_caricamento_pratiche r where r.id_pratica is not null) "
				+ "and s.id_staging between " + a + " and " + b);
		while(set.next()) {
			String data = set.getString(2);
			String descrizione = null;
			if(data != null) {
				descrizione = getString(data, "//:DichiarazioniDinamiche/:DichiarazioneDinamica/:href[text()='LH00026']/../:campiHref/:campoHref/:valoreUtente");
				if(descrizione != null && descrizione.length() > 0) {
					Statement stmt2 = mysql.getStatement();
					Integer stagingId = set.getInt(1);
					logger.info("stagingId: " + stagingId + " Descrizione: " + descrizione.replaceAll("'", "\\\\'"));
					String insert = "UPDATE PRATICA SET DESCRIZIONE_INTERVENTO = '"+descrizione.replaceAll("'", "\\\\'")+"' where id_staging = "+ stagingId;
					stmt2.execute(insert);
					stmt2.close();
				}
			}
		}
		stmt.close();
	}

	private String getString(String data, String xpattern)  {
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
			logger.error("errore in:" + data);
		}

		return ret;
	}

}