package cross.cross_scripts.change_paperwork_number;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.MySql;
import it.wego.cross.entity.Pratica;

public class PaperworkNumberHandler {


	private static final Logger logger = LogManager.getLogger(PaperworkNumberHandler.class);

	private MySql mysql;
	private boolean pre2024;

	public PaperworkNumberHandler(MySql mysql, boolean pre2024) { 
		this.mysql = mysql;
		this.pre2024 = pre2024;
	}

	public void run() throws Exception {
		logger.warn("Run " + this.getClass().getName());

		Statement stmt = mysql.getStatement();
		int min = min();
		int max = max();
		int increment = 500;
		for(int i = min; i<max; i+=increment) {
			String query ="SELECT p.id_pratica, p.protocollo, p.oggetto_pratica FROM \r\n"
					+ "pratica p inner join procedimenti_enti proe on p.id_proc_ente = proe.id_proc_ente \r\n"
					+ "where "+(pre2024?" p.anno < 2024 ":"")+" and proe.id_ente = 2001 and NOT EXISTS (SELECT 1 FROM civilia_pratica cp where cp.codice = p.identificativo_pratica) \r\n"
					+ "and length(p.protocollo) > 0 and p.protocollo != '0' and p.id_pratica between "+i+" and "+(i+increment)+";";
			ResultSet set = stmt.executeQuery(query);
			while(set.next()){
				Pratica p = new Pratica();
				p.setIdPratica(set.getInt(1));
				p.setProtocollo(set.getString(2));
				p.setOggettoPratica(set.getString(3));
				logger.info("Pratica: " + p);
				handlePapework(p);
			}
		}
	}

	private void setNumeroPrtica(Integer idPratica, Integer numero)throws SQLException, ClassNotFoundException{
		Statement stmt = mysql.getStatement();
		mysql.setAutocommit(true);
		stmt.executeUpdate("update pratica set numero_pratica = " + numero + " where id_pratica = "+ idPratica);
		stmt.close();
	}

	private void handlePapework(Pratica pratica) {
		String protocollo = pratica.getProtocollo();
		String oggetto = pratica.getOggettoPratica();

		Integer protocolNumber = null;
		boolean check = false;
		try {
			if(oggetto != null && oggetto.length() > 0) {
				Matcher m = Pattern.compile("PAS\\s+(\\d+)[^\\d]\\d+").matcher(oggetto);
				if(m.find()) {
					protocolNumber = Integer.parseInt(m.group(1));
					check = true;
				}
			}
			if(!check) {
				if(protocollo != null && protocollo.length() > 0) {
					Matcher m = Pattern.compile("6\\.11-F(.+)").matcher(protocollo);
					if(m.find()) {
						protocolNumber = Integer.parseInt(m.group(1));
						check = true;
					} 
				}
			}
			if(!check)
				protocolNumber = Integer.parseInt(protocollo);
			
			if(protocolNumber != null) {
				logger.info("protocol:" + pratica.getProtocollo() + "; new protocol:" + protocolNumber);
				setNumeroPrtica(pratica.getIdPratica(), protocolNumber);
			}
		}
		catch(Exception e) {
			logger.error(e);
		}
	}


	private int min() throws ClassNotFoundException, SQLException {
		int number = -1;
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery("SELECT min(id_pratica) from pratica");
		while(set.next()) {
			number = set.getInt(1);
		}
		stmt.close();
		return number;
	}

	private int max() throws ClassNotFoundException, SQLException {
		int number = -1;
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery("SELECT max(id_pratica) from pratica");
		while(set.next()) {
			number = set.getInt(1);
		}
		stmt.close();
		return number;
	}
}

