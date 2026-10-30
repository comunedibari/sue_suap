package it.wego.cross.cross_tomcat_services.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.wego.cross.cross_tomcat_services.utils.JNDINodeReader;
import it.wego.cross.cross_tomcat_services.utils.PairKey;

public class DbReader {

	private CrossDbConnector crossConnector = null;
	
	public DbReader(JNDINodeReader jndi) throws ClassNotFoundException, SQLException{
		crossConnector = CrossDbConnector.getInstance(jndi.getUrl(), jndi.getUser(), jndi.getPwd());
	}
	
	public List<Integer> findAllPaperworkTypes() throws SQLException {
		List<Integer> ids = new LinkedList<>();
		Statement stmt = crossConnector.getStatement();
		ResultSet set = stmt.executeQuery("select id_tipo_pratica from lk_tipo_pratica");
		while(set.next()) {
			ids.add(set.getInt(1));
		}
		stmt.close();
		return ids;
		
	}

	public List<Integer> findAllInstitutionId() throws SQLException {
		List<Integer> ids = new LinkedList<>();
		Statement stmt = crossConnector.getStatement();
		ResultSet set = stmt.executeQuery("select id_ente from enti");
		while(set.next()) {
			ids.add(set.getInt(1));
		}
		stmt.close();
		return ids;
		
	}
	
	public Map<PairKey<Integer, Integer>, Integer> findDataForCurrentYear() throws SQLException {
		Map<PairKey<Integer, Integer>, Integer> map = new HashMap<>();
		Calendar c = Calendar.getInstance();
		Integer year = c.get(Calendar.YEAR);
		Statement stmt = crossConnector.getStatement();
		ResultSet set = stmt.executeQuery("SELECT pre.id_ente, p.id_tipo_pratica, count(p.numero_pratica) "
				+ "FROM Pratica p inner join procedimenti_enti pre on p.id_proc_ente=pre.id_proc_ente "
				+ "where p.id_tipo_pratica is not null and p.anno = "+year+" "
				+ "GROUP BY pre.id_ente, p.id_tipo_pratica");
		while(set.next()) {
			Integer max = set.getInt(3);
			map.put(new PairKey<>(set.getInt(1), set.getInt(2)), max != null? max:0);
		}
		stmt.close();
		return map;
	}
}
