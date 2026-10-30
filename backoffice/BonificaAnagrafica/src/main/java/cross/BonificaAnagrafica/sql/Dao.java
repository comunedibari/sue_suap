package cross.BonificaAnagrafica.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cross.BonificaAnagrafica.BonificaAnagraficaApplication;
import cross.utilities.LogClass;
import cross.utilities.myIndexableMap;
import cross.utilities.myUtility;


public class Dao {
  
		
	private PoolConnectionDB dbPool;
	
	public Dao() {
		
	}
	
	public void openConnection(String url)
	{
		dbPool = new PoolConnectionDB(url);
	}

	public void closeConnection()
	{
		dbPool.close();
	}
	/**
	 * @return the logger
	 */

	/**
	 * @param connection the connection to set
	 */
	public Connection getConnection(int thread_id) {
		return this.dbPool.getConnectionPool(thread_id);
	}
	
	
	/**
	 * @return the dbPool
	 */
	public PoolConnectionDB getDbPool() {
		return dbPool;
	}


	/**
	 * @param dbPool the dbPool to set
	 */
	public void setDbPool(PoolConnectionDB dbPool) {
		this.dbPool = dbPool;
	}
	
	public String getUTCDataTime(int thread_id) throws Exception {
		String UTCDataTime = null;
		
		try {

			java.sql.Connection connection = getConnection(thread_id);
			PreparedStatement ps = connection.prepareStatement(Query.getUTCDataTime);

			// set the preparedstatement parameters
			java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime()); 
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UTCDataTime = rs.getString("datatimezone");
			}
			ps.close();
			
			LogClass.info("Dao::UTCDataTime:" + UTCDataTime);
			//connection.close();

		} catch (Exception e) {
			LogClass.info("Dao: Errore in getUTCDataTime: " + e.getMessage());
			throw e;
		}
		return UTCDataTime;
		
	}
	/*
	 * java.sql.Date date = getCurrentDatetime();
	 * pstmt.setDate(2, date);
	 * 
	 */
	
	public java.sql.Date getCurrentDatetime() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}
	
	public String queryString(String query, String selectField, int thread_id) throws Exception {
		String output = null;

		try {
			LogClass.info("Dao::queryString()");

			java.sql.Connection connection = getConnection(thread_id);
			PreparedStatement ps = connection.prepareStatement(query);

			// set the preparedstatement parameters

			java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime()); 

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				output = rs.getString(selectField);
			}
			rs.close();
			ps.close();

			//connection.close();

		} catch (Exception e) {
			LogClass.info("Dao: Errore in queryString: " + e.getMessage());
			throw e;
		}
		return output;		
	}

	public myIndexableMap<String, String> queryStringMap(String query, myIndexableMap<String, String> selectFields, int thread_id) throws Exception {
		myIndexableMap<String, String> myMapOut = new myIndexableMap<String, String>();
    	    	
		try {
			java.sql.Connection connection = getConnection(thread_id);
			PreparedStatement ps = connection.prepareStatement(query);

			LogClass.trace("Dao:  queryStringMap: " + query);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				LogClass.trace("Dao: queryStringMap selectFields.getKeyListSize(): " + selectFields.getKeyListSize() );

				for(int index=0; index<selectFields.getKeyListSize();index++)
				{
					LogClass.trace("Dao: queryStringMap index: " + index );
					///output = rs.getString(selectField);
			    	String key = ((myIndexableMap<String, String>) selectFields).getKeyAt(index);
					LogClass.trace("Dao: queryStringMap key: " + key );

			    	String value =  ((myIndexableMap<String, String>) selectFields).getValueAt(index);
					LogClass.trace("Dao: queryStringMap value: " + value );
					
			    	myMapOut.put(value, rs.getString(value));
				}
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			LogClass.info("Dao: Errore in queryStringMap: " + e.getMessage());
			throw e;
		}
		return myMapOut;		
	}

	public int queryInt(String query, String selectField, int thread_id) throws Exception {
		int output = 0;
		
		try {
			java.sql.Connection connection = getConnection(thread_id);
			PreparedStatement ps = connection.prepareStatement(query);

			// set the preparedstatement parameters

			java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime()); 

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				output = rs.getInt(selectField);
			}
			rs.close();
			ps.close();

			//connection.close();

		} catch (Exception e) {
			LogClass.info("Dao: Errore in queryInt: " + e.getMessage());
			throw e;
		}
		return output;		
	}

	public Confronta_ruoli_cross queryCmpRuoli(int id_ruolo_as_is, int id_ruolo_to_be, myUtility utl, int thread_id) throws Exception {
		Confronta_ruoli_cross output = new Confronta_ruoli_cross();
		
		try {
			String query = Query.select_confronta_ruoli_cross.replaceAll("@", utl.getValueProperties("schema_opencross"));
			query = myUtility.setString(query, "?", Integer.toString(id_ruolo_as_is));
			query = myUtility.setString(query, "?", Integer.toString(id_ruolo_to_be));
			
			java.sql.Connection connection = getConnection(thread_id);
			PreparedStatement ps = connection.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				output.setId_tipo_ruolo_attuale( rs.getInt("id_tipo_ruolo_attuale") );
				output.setDescrizione_attuale( rs.getString("descrizione_attuale") );
				output.setId_tipo_ruolo_civilia( rs.getInt("id_tipo_ruolo_civilia") );
				output.setDescrizione_civilia( rs.getString("descrizione_civilia") );
			}
			rs.close();
			ps.close();

			//connection.close();

		} catch (Exception e) {
			LogClass.info("Dao: Errore in queryInt: " + e.getMessage());
			throw e;
		}
		return output;		
	}
	
	public void executeQuery(String query, int thread_id) throws Exception {

		try {
			java.sql.Connection connection = getConnection(thread_id);
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			ps.close();			
			//connection.close();
		} catch (Exception e) {
			LogClass.info("Dao: Errore in executeQuery: " + e.getMessage());
			throw e;
		}
	}

	public int executeUpdate(String query, int thread_id) throws Exception {
		int updateCount=0;
		try {
			java.sql.Connection connection = getConnection(thread_id);
			PreparedStatement ps = connection.prepareStatement(query);
			updateCount = ps.executeUpdate();
			ps.close();			
			//connection.close();
		} catch (Exception e) {
			LogClass.info("Dao: Errore in executeUpdate: " + e.getMessage());
			throw e;
		}
		return updateCount;
	}


	public void logInfo(String msg, int thread_id) throws Exception {
		try {
			java.sql.Connection connection = getConnection(thread_id);
			PreparedStatement ps = connection.prepareStatement(Query.insertMsgLog);
			ps.setString(1, msg);
			int rc = ps.executeUpdate();
			ps.close();
			
			connection.close();

		} catch (Exception e) {
			LogClass.info("Dao: Errore in logInfoGio: " + e.getMessage());
			throw e;
		}		
	}
	
	public static void main(String[] args) {

		int index = 0;
		myUtility utl = new myUtility();
		
		
		String query1 = "SELECT COUNT(1) AS ruolo_na FROM opencross_prod.`ruoli_etichetta_civilia_cross` r WHERE  1=1"
				+ "	AND `ruolo_etichetta_civilia_cross` = 'Titolare altro diritto sull'immobile'";
		
		String query = myUtility.replaceSingleQuote(query1);
		query = query1.replaceAll("^\"|\"$", "");
		query = query1.replaceAll("^\"|\"$", "");
		query = query1.replaceAll("^\"|\"$", "");
		query = query1.replace("'", "\'");
		
		Dao dao_opencross_prod = new Dao();
		dao_opencross_prod.openConnection(BonificaAnagraficaApplication.URL_DB_OPENCROSS_PROD);
		query = "";
			
    	query = Query.select_ruolo_etichetta.replace("@", utl.getValueProperties("schema_opencross"));
    	query = query.replace("?", "Richiedente/Denunciante");
    	
		myIndexableMap<String, String> myMapFileds = new myIndexableMap<String, String>();
		myMapFileds.put("1", "id");
		myMapFileds.put("2", "ruolo_cross");
									    	
		myIndexableMap<String, String> myMapFiledsOut;
		try {
			myMapFiledsOut = dao_opencross_prod.queryStringMap(query, myMapFileds, 1);
		
			query = Query.select_ruolo_cross.replace("@", utl.getValueProperties("schema_opencross"));
			
			int  idx_descr_ruolo_cross = myMapFiledsOut.getKeyIndex("ruolo_cross");
			String descr_ruolo_cross = ((myIndexableMap<String, String>) myMapFiledsOut).getValueAt(idx_descr_ruolo_cross);
			query = query.replace("?", descr_ruolo_cross);
			
			int idx_ruolo_etichetta = myMapFiledsOut.getKeyIndex("id");
			int id_ruolo_etichetta = Integer.valueOf(((myIndexableMap<String, String>) myMapFiledsOut).getValueAt(idx_ruolo_etichetta));
			
			System.out.println("descr_ruolo_cross:"+descr_ruolo_cross + " id_ruolo_etichetta=" + id_ruolo_etichetta);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
