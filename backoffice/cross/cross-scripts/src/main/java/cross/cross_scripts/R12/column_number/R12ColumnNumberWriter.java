package cross.cross_scripts.R12.column_number;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.MySql;

public class R12ColumnNumberWriter {

	private final String STARTER_YEAR = "2018";
	private static final Logger logger = LogManager.getLogger(R12ColumnNumberWriter.class);
	private MySql mysql;
	
	public R12ColumnNumberWriter(MySql mysql) {
		this.mysql = mysql;
	} 
	
	public void run () throws SQLException {
		Statement stmt = null;

		try {
			stmt = mysql.getStatement();

			List<String> typeids = getTypeIds(stmt);
			List<String> institutions = getInstitutions(stmt);
			
			for(String institution:institutions) {
				for(String typeId:typeids) {
					
					for(int year = Integer.parseInt(STARTER_YEAR); year <=2023; year++) {
						List<String> paperworkIds = getPaperworkId(stmt, typeId, institution, year);
						logger.info("year: "+year +", Ente: "+institution + ", Tipo: " + typeId + ", Size: " + paperworkIds.size());
						
						int counter = getWorkpaperExistingMaxNumeroPratica(stmt, typeId, institution, year);
						logger.info("year: "+year +", Ente: "+institution + ", Tipo: " + typeId + ", max number: " + counter);

						for(String paperworkId: paperworkIds) {
							counter++;
							logger.info(paperworkId + " -> year: "+ year +", ente: "+institution+" Tipo:" + typeId + ", numero " + counter);
							setNumeroPratica(stmt, paperworkId, counter);
						}
					}
				}
			}
			mysql.commit();
		} catch (ClassNotFoundException | SQLException e) {
			logger.error(e);
			mysql.rollback();
		} finally {
			stmt.close();
			mysql.close();
		}
		logger.info("END");
	}

	private List<String> getTypeIds(Statement stmt) throws SQLException, ClassNotFoundException{
		ResultSet set = stmt.executeQuery("select distinct id_tipo_pratica from lk_tipo_pratica");
		List<String> types = new LinkedList<>();
		while(set.next()) {
			types.add(Integer.toString(set.getInt(1)));
		}
		return types;
	}

	private List<String> getInstitutions(Statement stmt) throws SQLException, ClassNotFoundException{
		ResultSet set = stmt.executeQuery("select id_ente from enti");
		List<String> types = new LinkedList<>();
		while(set.next()) {
			types.add(Integer.toString(set.getInt(1)));
		}
		return types;
	}

	private List<String> getPaperworkId(Statement stmt, String type, String institutionId, int year) throws SQLException, ClassNotFoundException{
		String query = "select p.id_pratica "
						+ "from pratica p inner join procedimenti_enti pe on p.id_proc_ente = pe.id_proc_ente "
					    + "where p.id_tipo_pratica = "+ type + " and pe.id_ente = "+institutionId+" and p.anno=" + year + " and "
					    + "p.numero_pratica is null "
					    + "order by p.data_ricezione asc";

		ResultSet set = stmt.executeQuery(query);
		List<String> types = new ArrayList<>();
		while(set.next()) {
			types.add(set.getString(1));
		}
		return types;
	}

	private Integer getWorkpaperExistingMaxNumeroPratica(Statement stmt, String type, String institutionId, int year) throws SQLException, ClassNotFoundException{
		String query = "select max(p.numero_pratica) "
				+ "from pratica p inner join procedimenti_enti pe on p.id_proc_ente = pe.id_proc_ente "
				+ "where p.id_tipo_pratica = "+ type + " and pe.id_ente = "+institutionId+" and p.anno=" + year + " and "
					    + "p.numero_pratica is not null ";
		
		ResultSet set = stmt.executeQuery(query);
		Integer max = 0;
		while(set.next()) {
			Integer number = set.getInt(1);
			if(number != null && number != 0)
				max = number;
		}
		return max;
	}

	private void setNumeroPratica(Statement stmt, String idPratica, int numero)throws SQLException, ClassNotFoundException{
		stmt.execute("update pratica set numero_pratica = " + numero + " where id_pratica = "+ idPratica);
	}
}
