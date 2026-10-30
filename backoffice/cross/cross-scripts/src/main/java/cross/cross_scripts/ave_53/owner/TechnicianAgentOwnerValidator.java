package cross.cross_scripts.ave_53.owner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

import cross.cross_scripts.MySql;
import cross.cross_scripts.ric_as_external_event.RicAsExternalEvent;
import it.wego.cross.plugins.aec.ConcessioniAutorizzazioniNamespaceContext;

/**
 * validate that the owner is the technician agent
 */
public class TechnicianAgentOwnerValidator {

	private MySql mysql;
	private static final Logger logger = LogManager.getLogger(RicAsExternalEvent.class);

	public TechnicianAgentOwnerValidator(MySql mysql) {
		this.mysql = mysql;
	} 

	public void run() throws Exception {
		logger.warn("run " + this.getClass().getName());

		int min = min(mysql);
		int max = max(mysql);
		int increment = 500;
		System.out.println("min: " + min);
		System.out.println("max: " + max);
		for(int i = max; i > min; i-=increment) {
			logger.info("Iteration: " + i);
			extracts(mysql, i-increment , i);
		}
		mysql.close();
		logger.warn("END " + this.getClass().getName());
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

	private void extracts(MySql mysql, int a, int b) throws SQLException, ClassNotFoundException, IOException {
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery("SELECT s.id_staging, CONVERT( s.xml_ricevuto using utf8) "
				+ "from opencross.pratica p, opencross.staging s "
				+ "where p.id_staging = s.id_staging "
				+ "and s.id_staging between " + a + " and " + b);
		try(PrintWriter ptNull = new PrintWriter(new BufferedWriter(new FileWriter(".\\nullFile.txt", true)));
				PrintWriter ptDifferrence = new PrintWriter(new BufferedWriter(new FileWriter(".\\differenceFile.txt", true)));
				PrintWriter ptError = new PrintWriter(new BufferedWriter(new FileWriter(".\\errorFile.txt", true)));){
			while(set.next()) {
				String data = set.getString(2);
				Integer id = set.getInt(1);
				logger.info("reading: " + id);
				if(data != null) {
					String codiceIdentificativoOperazione = getString(data, "//ogg:CodiceIdentificativoOperazione");
					String codiceFiscale = getString(data, "//:Titolare/ogg:PersonaFisica/ogg:CodiceFiscale");

					try {
						if(codiceIdentificativoOperazione == null || codiceFiscale == null) {
							logger.error("NULL VALUES, check: " + id); 
							ptNull.println(id); 
						}

						if(codiceIdentificativoOperazione != null && codiceFiscale != null && !codiceIdentificativoOperazione.startsWith(codiceFiscale)) {
							logger.error("differents values, check: " + id); 
							logger.error("\t" + codiceFiscale);
							logger.error("\t" + codiceIdentificativoOperazione);
							ptDifferrence.println(id);
						}
					}
					catch(Exception e) {
						logger.error("NULL VALUES, check: " + id);
						ptError.println(id);
					}
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
			//			logger.error("errore in:" + data);
		}

		return ret;
	}
}
