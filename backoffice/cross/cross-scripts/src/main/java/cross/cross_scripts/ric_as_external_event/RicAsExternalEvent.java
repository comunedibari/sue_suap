package cross.cross_scripts.ric_as_external_event;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.MySql;



public class RicAsExternalEvent {

	private MySql mysql;
	private static final Logger logger = LogManager.getLogger(RicAsExternalEvent.class);

	public RicAsExternalEvent(MySql mysql) {
		this.mysql = mysql;
	} 

	public void run () throws SQLException, ClassNotFoundException, ParseException {
		logger.info("Start RicAsExternalEvent");

		int min = min();
		int max = max();
		//		List<Integer> pratiche = Arrays.asList(209657);

		for(int i = min; i < max; i+=1000) {
			List<Integer> pratiche = extract(i,i+1000);
			for(Integer idPratica: pratiche) {
				Pratica pratica = extractPratica(idPratica);
				if(pratica  != null) {
					System.out.println(pratica);
					setLastDays(pratica.idPratica, pratica.getData());
				} else
					System.out.println("NOT FOUND " + idPratica);
			}
		}
		mysql.close();

		logger.info("End RicAsExternalEvent");
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


	private List<Integer> extract(int a, int b) throws SQLException, ClassNotFoundException {
		Statement stmt = mysql.getStatement();
		List<Integer> pratiche = new ArrayList<>();
		ResultSet set = stmt.executeQuery("select p.id_pratica from pratica p "
				+ "WHERE p.data_ultimo_evento_esterno = '1900-01-01 00:00:00'"
				+ "and p.id_pratica between " + a + " and " + b);
		while(set.next()) {
			pratiche.add(set.getInt(1));
		}
		stmt.close();
		return pratiche;
	}
	private Pratica extractPratica(int idPratica) throws SQLException, ClassNotFoundException {
		Statement stmt = mysql.getStatement();
		Pratica pratica = null;
		ResultSet set = stmt.executeQuery("SELECT p.id_pratica, pe.data_evento, pre.cod_evento "
				+ "from pratica p inner join pratiche_eventi pe on p.id_pratica = pe.id_pratica "
				+ "inner join processi_eventi pre on pe.id_evento = pre.id_evento "
				+ "where pe.id_evento is not null and pre.cod_evento in ('PROCINT', 'OINOLTRO2', 'RIC') and "
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

		String query = "UPDATE PRATICA SET data_ultimo_evento_esterno = '"+date+"' where id_pratica= " + idPratica + ";";
		stmt.execute(query);
		stmt.execute("commit");
		stmt.close();
	}

}