package cross.cross_scripts.ave_44;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.MySql;

public class PECHandler extends BaseHandler{

	private static final Logger logger = LogManager.getLogger(PECHandler.class);

	public PECHandler(MySql mysql) {
		super(mysql, null);
	}
	
	@Override
	public void run() throws Exception {
		logger.warn("run " + this.getClass().getName());
		
		int min = min("id_pratica_evento", "pratiche_eventi");
		int max = max("id_pratica_evento", "pratiche_eventi");
		int increment = 1000;
		try(Statement stmt = getMysql().getStatement();){
			for(int i = max; i>min; i-=increment) {
				String query = "SELECT e.id_pratica_evento, count(*) \r\n"
				+ "FROM pratiche_eventi pe inner join email e on e.id_pratica_evento = pe.id_pratica_evento inner join processi_eventi poe on pe.id_evento = poe.id_evento\r\n"
				+ "WHERE poe.flg_mail = 'S' AND pe.stato_mail != 10 AND e.id_pratica_evento between " + (i-increment) + " and " +(i) + " "
				+ "group by e.id_pratica_evento\r\n"
				+ "order by e.id_pratica_evento desc;";
				ResultSet set = stmt.executeQuery(query);
				while(set.next()) {
					Integer idPraticaEvento = set.getInt(1);
					Integer counter = set.getInt(2);
					Node node = new Node(idPraticaEvento, counter);
					logger.info("Elaborazione: " + node);
					process(node);
					
				}
			}
		}
	}
	
	private void process(Node node) throws SQLException {
		try(Statement stmt = getMysql().getStatement();){
			String query = "select count(*)\r\n"
					+ "from email e inner join email_consegnate ec on e.id_messaggio = ec.msgid \r\n"
					+ "where e.id_pratica_evento = " + node.getIdPraticaEvento() + " and ec.oggetto_risposta like 'CONSEGNA: %'";
			ResultSet set = stmt.executeQuery(query);
			while(set.next()) {
				int counterOfSent = set.getInt(1);
				if(counterOfSent == node.getCounting()) {
					updateStatoMail(node.getIdPraticaEvento());
				}
				else
					logger.info("PECs not sent for event:" + node.getIdPraticaEvento());
			}
		}
	}
	
	private void updateStatoMail(int idPraticaEvento) throws SQLException {
		getMysql().setAutocommit(true);
		try(Statement stmt = getMysql().getStatement()){
			logger.info("UPDATE PRATICHE_EVENTI SET STATO_MAIL = 10 WHERE ID_PRATICA_EVENTO = " + idPraticaEvento + ";");
			stmt.executeUpdate("UPDATE PRATICHE_EVENTI SET STATO_MAIL = 10 WHERE ID_PRATICA_EVENTO = " + idPraticaEvento + ";");
		}
	}
}
