package cross.cross_scripts.external_events;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.MySql;

public class LastExternalEventDays {

	private MySql mysql;
	private static final Logger logger = LogManager.getLogger(LastExternalEventDays.class);
	public LastExternalEventDays(MySql mysql) {
		this.mysql = mysql;
	} 
	
	public void run () throws SQLException, ClassNotFoundException, ParseException {
		logger.info("Start LastExternalEventDays");
		
		List<Integer> pratiche = extract(mysql);
		for(Integer i: pratiche) {
			Pratica pratica = extractPratica(mysql, i);
			if(pratica  != null) {
				System.out.println(pratica);
				setLastDays(pratica.idPratica, pratica.getData());
			} else
				System.out.println("not found for " + i);
		}
		mysql.close();
		
		logger.info("End LastExternalEventDays");
	}
	
	private int min(MySql mysql) throws ClassNotFoundException, SQLException {
		int number = -1;
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery("SELECT min(id_pratica) from pratica");
		while(set.next()) {
			number = set.getInt(1);
		}
		stmt.close();
		return number;
	}
	
	private int max(MySql mysql) throws ClassNotFoundException, SQLException {
		int number = -1;
		Statement stmt = mysql.getStatement();
		ResultSet set = stmt.executeQuery("SELECT max(id_pratica) from pratica");
		while(set.next()) {
			number = set.getInt(1);
		}
		stmt.close();
		return number;
	}
	
	
	private List<Integer> extract(MySql mysql) throws SQLException, ClassNotFoundException {
		Statement stmt = mysql.getStatement();
		List<Integer> pratiche = new ArrayList<>();
		ResultSet set = stmt.executeQuery("select pp.id_pratica from pratica pp where exists("
				+ "SELECT * "
				+ "from pratica p inner join pratiche_eventi pe on p.id_pratica = pe.id_pratica "
				+ "inner join processi_eventi pre on pe.id_evento = pre.id_evento "
				+ "where pe.id_evento is not null and pre.cod_evento in ('PROCINT', 'OINOLTRO2') "
				+ "and p.id_pratica = pp.id_pratica"
				+ ")");
		while(set.next()) {
			pratiche.add(set.getInt(1));
		}
		stmt.close();
		return pratiche;
	}
	private Pratica extractPratica(MySql mysql, int idPratica) throws SQLException, ClassNotFoundException {
		Statement stmt = mysql.getStatement();
		Pratica pratica = null;
		ResultSet set = stmt.executeQuery("SELECT p.id_pratica, pe.data_evento, pre.cod_evento "
				+ "from pratica p inner join pratiche_eventi pe on p.id_pratica = pe.id_pratica "
				+ "inner join processi_eventi pre on pe.id_evento = pre.id_evento "
				+ "where pe.id_evento is not null and pre.cod_evento in ('PROCINT', 'OINOLTRO2') and "
				+ "p.id_pratica = "+idPratica+" "
				+ "order by pe.data_evento desc "
				+ "limit 1;");
		while(set.next()) {
			Integer id = set.getInt(1);
			String data= set.getString(2);
			String code = set.getString(3);
			pratica = new Pratica(id,data, code);
		}
		stmt.close();
		return pratica;
	}
	
	private void setLastDays(int idPratica, String date) throws SQLException {
		Statement stmt = mysql.getStatement();
		Pratica pratica = null;
		String query = "UPDATE PRATICA SET data_ultimo_evento_esterno = '"+date+"' where id_pratica= " + idPratica + ";";
		stmt.execute(query);
		stmt.execute("commit");
		stmt.close();
	}
}
