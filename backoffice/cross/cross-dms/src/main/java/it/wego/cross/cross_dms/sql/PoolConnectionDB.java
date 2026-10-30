package it.wego.cross.cross_dms.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import it.wego.cross.cross_dms.utilities.LogClass;
import it.wego.cross.cross_dms.utilities.myUtility;

public class PoolConnectionDB {
    private Connection connection;  
    
	private String DATABASE_DRIVER;
	private String DATABASE_URL;
	private String PORT;
	private String USERNAME;
	private String PASSWORD;

	public LogClass logger;
	
	public static final int MAX_POOL_CONNECTION = 2;

	public static final String DB_CIVILIA = "civilia_db";

	public static final String DB_CROSS = "opencross";

	private int CounterPoolConnection;
	
	ArrayList<java.sql.Connection> poolConnection;
	
	Map<Integer, java.sql.Connection> poolConnectionMap;


	public PoolConnectionDB(String database) {
		super();
		
		myUtility util = new myUtility();
		if(database.equals(DB_CIVILIA)) {
			DATABASE_DRIVER = util.getValueProperties("civilia.jdbc.driverClassName");
			DATABASE_URL = util.getValueProperties("civilia.jdbc.url");
			PORT = util.getValueProperties("civilia.jdbc.port");
			USERNAME = util.getValueProperties("civilia.jdbc.username");
			PASSWORD = util.getValueProperties("civilia.jdbc.password");
		} else if(database.equals(DB_CROSS)) {
			DATABASE_DRIVER = util.getValueProperties("cross.jdbc.driverClassName");
			DATABASE_URL = util.getValueProperties("cross.jdbc.url");
			PORT = util.getValueProperties("cross.jdbc.port");
			USERNAME = util.getValueProperties("cross.jdbc.username");
			PASSWORD = util.getValueProperties("cross.jdbc.password");			
		}
		try {
			Map<Integer, java.sql.Connection> pool = new ConcurrentHashMap<>();
			
			this.setPoolConnectionMap(pool);
			
			for (int i = 0; i < MAX_POOL_CONNECTION; i++) {
				LogClass.trace("Create connection pool:" + i);
				pool.put(i, getConnection());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws Exception {
		
		return connect();
	}

	public Connection connect() throws Exception {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                //System.out.println("Database connected!");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                throw new IllegalStateException("Cannot connect the database!", e);
            }
        }
        return connection;
    }

	public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

	/**
	 * @return the dATABASE_DRIVER
	 */
	public String getDATABASE_DRIVER() {
		return DATABASE_DRIVER;
	}

	/**
	 * @param dATABASE_DRIVER the dATABASE_DRIVER to set
	 */
	public void setDATABASE_DRIVER(String dATABASE_DRIVER) {
		DATABASE_DRIVER = dATABASE_DRIVER;
	}

	/**
	 * @return the dATABASE_URL
	 */
	public String getDATABASE_URL() {
		return DATABASE_URL;
	}

	/**
	 * @param dATABASE_URL the dATABASE_URL to set
	 */
	public void setDATABASE_URL(String dATABASE_URL) {
		DATABASE_URL = dATABASE_URL;
	}

	/**
	 * @return the pORT
	 */
	public String getPORT() {
		return PORT;
	}

	/**
	 * @param pORT the pORT to set
	 */
	public void setPORT(String pORT) {
		PORT = pORT;
	}

	/**
	 * @return the uSERNAME
	 */
	public String getUSERNAME() {
		return USERNAME;
	}

	/**
	 * @param uSERNAME the uSERNAME to set
	 */
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	/**
	 * @return the pASSWORD
	 */
	public String getPASSWORD() {
		return PASSWORD;
	}

	/**
	 * @param pASSWORD the pASSWORD to set
	 */
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	private int get_NONCE() {			
		Random rand = new Random();
		int upperbound = MAX_POOL_CONNECTION - 1;
		int int_random = rand.nextInt(upperbound); 

		return int_random;
	}
	
	public void close() {
		int i=0;
		for (Connection val : this.getPoolConnectionMap().values())
		{			
			try {
				LogClass.trace("Close connection pool:" + i++);
				val.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
		
	public java.sql.Connection getConnectionPool(){
		Connection conn = null;

		int searchKey = this.get_NONCE();
        if (this.getPoolConnectionMap().containsKey(searchKey))
        {
        	conn = this.getPoolConnectionMap().get(searchKey);
        	//LogClass.trace("Get connection pool:" + searchKey);
        	//System.out.println("Get connection pool:" + searchKey);
        	if(conn == null) {
            	//LogClass.trace("Get connection pool null:" + searchKey);
            	//System.out.println("Get connection pool null get_NONCE:" + searchKey);

        		conn = this.getPoolConnectionMap().get(this.get_NONCE());
        	}
        }      
        return conn;
	}
	/**
	 * @return the poolConnectionMap
	 */
	private Map<Integer, java.sql.Connection> getPoolConnectionMap() {
		return poolConnectionMap;
	}

	/**
	 * @param poolConnectionMap the poolConnectionMap to set
	 */
	private void setPoolConnectionMap(Map<Integer, java.sql.Connection> poolConnectionMap) {
		this.poolConnectionMap = poolConnectionMap;
	}
		
	public static void main(String[] args) {
		
		PoolConnectionDB dbpool = new PoolConnectionDB(DB_CIVILIA);
		Dao dao = new Dao();		
		dao.setDbPool(dbpool);
		LogClass.trace("Main PoolConnectionDB");
		int id_dom_aiuto = 95482;
		try {
			//int bando = dao.getIdBando(95482);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
