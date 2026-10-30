package it.wego.cross.mailservices.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.wego.cross.mailservices.component.MailReaderCross;
import it.wego.cross.mailservices.utilities.myIndexableMap;

public class DaoConfigCross {

	private static final Logger logger = LoggerFactory.getLogger(DaoConfigCross.class.getName());
	
	private MySQLDbcp2 dbpool;
	
	private int Ente;

	private int idComune;

	private String sUsernameServerImap;

	private String sPasswordServerImap;

	private String sMailHost;

	private String sServerImap;

	private String sServerImapPort;

	private String sServerImapWebMail;

	private String sServerImapWebMailUsername;

	private String sServerImapWebMailPassowrd;

	private Map<String, String> mapConfig;
		
	private static String tmpCheckmail = "SELECT "
			+ "DISTINCT c.`id_ente`, "
			+ "e.descrizione AS descrizione_ente, "
			+ "c.name,"
			+ "c.value,"
			+ "c2.value AS abilita"
			+ "FROM (((((`opencross`.`configuration` c )"
			+ "JOIN `opencross`.`enti` e)"
			+ "JOIN opencross.enti_comuni ec)"
			+ "JOIN opencross.lk_comuni lkc)"
			+ "JOIN `opencross`.`configuration` c2 ON (c2.id_ente = c.id_ente "
			+ "AND LOWER(c2.name) = 'abilita.elab.inbox' AND c2.value = 'S')"
			+ "-- AND LOWER(c2.name) = 'abilita.elab.inbox.massivo' AND c2.value = 'S')"
			+ ")"
			+ "WHERE ((e.id_ente = c.id_ente)"
			+ "AND (ec.id_ente = e.id_ente)"
			+ "AND (lkc.id_comune = ec.id_comune))"
			+ "AND c.name IN ( "
			+ "'mail.host', "
			+ "'mail.imap.host',"
			+ "'mail.username',"
			+ "'mail.password',"
			+ "'mail.imap.port',"
			+ "'abilita.lettura.email',"
			+ "'mail.config.cartellaCross',"
			+ "'mail.config.cartellaLette'"
			+ ")"
			+ "AND c.value IS NOT NULL"
			+ "AND c.value <> ''"
			+ "AND e.descrizione LIKE 'SUE%'";
	
	public static String queryConfiguration = "SELECT 	"
			+ "	c.`id_ente` AS id_ente,  "
			+ "	e.descrizione AS descrizione_ente,"
			+ "	ec.`id_comune` AS id_comune, "
			+ "	lkc.descrizione AS descrizione_comune,"
			+ "	c.`name` AS nome_parametro, "
			+ "	c.`value` AS valore_parametro"
			+ " FROM ((((`opencross`.`configuration` c )"
			+ " JOIN `opencross`.`enti` e)"
			+ " JOIN opencross.enti_comuni ec)"
			+ " JOIN opencross.lk_comuni lkc)"
			+ " WHERE ((e.id_ente = c.id_ente)"
			+ " AND (ec.id_ente = e.id_ente)"
			+ " AND (lkc.id_comune = ec.id_comune))"
			+ " AND c.name IN ('mail.host', "
			+ "'mail.imap.host',"
			+ "'mail.username',"
			+ "'mail.password',"
			+ "'mail.imap.port',"
			+ "'abilita.lettura.email',"
			+ "'mail.config.cartellaCross',"
			+ "'mail.config.cartellaLette',"
			+ "'webmail.host',"
			+ "'webmail.username',"
			+ "'webmail.password',"
			+ "'abilita.elab.inbox',"
			+ "'abilita.elab.inbox.massivo'"
			+ ") "
			+ " AND c.id_ente = ?"
			+ " AND c.value IS NOT NULL"
			+ " AND c.value <> ''"
			+ " AND e.descrizione LIKE 'SUE%'";
	
	public static String insertEmailConsegnate = 
			"INSERT INTO `opencross`.`email_consegnate`"
			+ "(`id_ente`,"
			+ "`id_messaggio`,"
			+ "`msgid`,"
			+ "`data_invio_mail`,"
			+ "`oggetto_risposta`,"
			+ "`corpo_risposta`)"
			+ " VALUES (?, ?, ?, ?, ?, ?)";
	
	
	public static String checkRicevuta = "SELECT COUNT(1) AS ricevuta_gia_esistente"
			+ " FROM `opencross`.`email_consegnate`"
			+ " WHERE id_ente = ?"
			+ " AND id_messaggio = ?"
			+ " AND msgid = ?"; 
	
	public static String getEntiElabInboxGiornaliero = "SELECT "
			+ " DISTINCT c.`id_ente`, e.descrizione AS descrizione_ente, c2.value AS abilita"
			+ " FROM (((((`opencross`.`configuration` c )"
			+ " JOIN `opencross`.`enti` e)"
			+ " JOIN opencross.enti_comuni ec)"
			+ " JOIN opencross.lk_comuni lkc)"
			+ " JOIN `opencross`.`configuration` c2 ON (c2.id_ente = c.id_ente "
			+ "	AND LOWER(c2.name) = 'abilita.elab.inbox' AND c2.value = 'S'))"
			+ " WHERE ((e.id_ente = c.id_ente)"
			+ " AND (ec.id_ente = e.id_ente)"
			+ " AND (lkc.id_comune = ec.id_comune))"
			+ " AND c.name = 'mail.imap.host'"
			+ " AND c.value IS NOT NULL"
			+ " AND c.value <> ''"
			+ " AND e.descrizione LIKE 'SUE%'";			
	
	public static String getEntiElabInboxMassivo = "SELECT "
			+ " DISTINCT c.`id_ente`, e.descrizione AS descrizione_ente, c2.value AS abilita"
			+ " FROM (((((`opencross`.`configuration` c )"
			+ " JOIN `opencross`.`enti` e)"
			+ " JOIN opencross.enti_comuni ec)"
			+ " JOIN opencross.lk_comuni lkc)"
			+ " JOIN `opencross`.`configuration` c2 ON (c2.id_ente = c.id_ente "
			+ "	AND LOWER(c2.name) = 'abilita.elab.inbox.massivo' AND c2.value = 'S'))"
			+ " WHERE ((e.id_ente = c.id_ente)"
			+ " AND (ec.id_ente = e.id_ente)"
			+ " AND (lkc.id_comune = ec.id_comune))"
			+ " AND c.name = 'mail.imap.host'"
			+ " AND c.value IS NOT NULL"
			+ " AND c.value <> ''"
			+ " AND e.descrizione LIKE 'SUE%'";	

	public static String loggerDB = "INSERT INTO `opencross`.`email_consegnate_stacktrace`"
			+ "(`stacktrace`)"
			+ "VALUES (?)";

	public DaoConfigCross() {
		dbpool = new MySQLDbcp2();
	}
	/**
	 * @return the mapConfig
	 */
	public Map<String, String> getMapConfig() {
		return mapConfig;
	}
	/**
	 * @param mapConfig the mapConfig to set
	 */
	public void setMapConfig(Map<String, String> mapConfig) {
		this.mapConfig = mapConfig;
	}

	/**
	 * @return the dbpool
	 */
	public MySQLDbcp2 getDbpool() {
		return dbpool;
	}
	/**
	 * @param dbpool the dbpool to set
	 */
	public void setDbpool(MySQLDbcp2 dbpool) {
		this.dbpool = dbpool;
	}

	/**
	 * @return the sMailHost
	 */
	public String getsMailHost() {
		return sMailHost;
	}
	/**
	 * @param sMailHost the sMailHost to set
	 */
	public void setsMailHost(String sMailHost) {
		this.sMailHost = sMailHost;
	}

	/**
	 * @return the sServerImapWebMail
	 */
	public String getsServerImapWebMail() {
		return sServerImapWebMail;
	}
	/**
	 * @param sServerImapWebMail the sServerImapWebMail to set
	 */
	public void setsServerImapWebMail(String sServerImapWebMail) {
		this.sServerImapWebMail = sServerImapWebMail;
	}
	/**
	 * @return the sServerImapWebMailUsername
	 */
	public String getsServerImapWebMailUsername() {
		return sServerImapWebMailUsername;
	}
	/**
	 * @param sServerImapWebMailUsername the sServerImapWebMailUsername to set
	 */
	public void setsServerImapWebMailUsername(String sServerImapWebMailUsername) {
		this.sServerImapWebMailUsername = sServerImapWebMailUsername;
	}
	/**
	 * @return the sServerImapWebMailPassowrd
	 */
	public String getsServerImapWebMailPassowrd() {
		return sServerImapWebMailPassowrd;
	}
	/**
	 * @param sServerImapWebMailPassowrd the sServerImapWebMailPassowrd to set
	 */
	public void setsServerImapWebMailPassowrd(String sServerImapWebMailPassowrd) {
		this.sServerImapWebMailPassowrd = sServerImapWebMailPassowrd;
	}
	/**
	 * @return the sUsernameServerImap
	 */
	public String getsUsernameServerImap() {
		return sUsernameServerImap;
	}

	/**
	 * @param sUsernameServerImap the sUsernameServerImap to set
	 */
	public void setsUsernameServerImap(String sUsernameServerImap) {
		this.sUsernameServerImap = sUsernameServerImap;
	}

	/**
	 * @return the sPasswordServerImap
	 */
	public String getsPasswordServerImap() {
		return sPasswordServerImap;
	}

	/**
	 * @param sPasswordServerImap the sPasswordServerImap to set
	 */
	public void setsPasswordServerImap(String sPasswordServerImap) {
		this.sPasswordServerImap = sPasswordServerImap;
	}


	/**
	 * @return the ente
	 */
	public int getEnte() {
		return Ente;
	}

	/**
	 * @param ente the ente to set
	 */
	public void setEnte(int ente) {
		Ente = ente;
	}

	/**
	 * @return the idComune
	 */
	public int getIdComune() {
		return idComune;
	}

	/**
	 * @param idComune the idComune to set
	 */
	public void setIdComune(int idComune) {
		this.idComune = idComune;
	}

	/**
	 * @return the sServerImap
	 */
	public String getsServerImap() {
		return sServerImap;
	}

	/**
	 * @param sServerImap the sServerImap to set
	 */
	public void setsServerImap(String sServerImap) {
		this.sServerImap = sServerImap;
	}

	
	/**
	 * @return the sServerImapPort
	 */
	public String getsServerImapPort() {
		return sServerImapPort;
	}
	/**
	 * @param sServerImapPort the sServerImapPort to set
	 */
	public void setsServerImapPort(String sServerImapPort) {
		this.sServerImapPort = sServerImapPort;
	}
	
	public myIndexableMap<Integer, String> getListEnti(int tipo_elab) throws SQLException {
		
		myIndexableMap<Integer, String> mapEnti = new myIndexableMap<Integer, String>();

		Connection connection = null;
		PreparedStatement statement = null;
		String query = "";

		if(tipo_elab == MailReaderCross.INBOX_GIORNALIERO) {query = getEntiElabInboxGiornaliero;}

		if(tipo_elab == MailReaderCross.INBOX_MASSIVO) {query = getEntiElabInboxMassivo;}
		
		try {
			connection = dbpool.getDataSource().getConnection();			
			statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();			
			while (resultSet.next()) {
				
				mapEnti.put(Integer.valueOf(resultSet.getInt("id_ente")), resultSet.getString("descrizione_ente"));
			}
			resultSet.close();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			connection.close();
		}
		return mapEnti;
		
	}
	public void loggerDB(String msgTrace) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			int index=1;
			connection = dbpool.getDataSource().getConnection();
			
			String query = loggerDB;
			statement = connection.prepareStatement(query);
			String msg = StringUtils.truncate(msgTrace, 250);
			statement.setString(index++, msg);
			int result = statement.executeUpdate();							
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {statement.close();} catch (SQLException e) {}
			try { connection.close(); } catch(Exception e) {}
		}
	}
	public int insertRicevuta(
			int idEnte,
			String id_messaggio, 
			String msgid, 
			Date data_invio_mail, 
			String oggetto_risposta,
			String corpo_risposta) throws SQLException, ParseException 
	{
		String query = "";
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		int result = 0;
		
		try {
			int index=0;
			connection = dbpool.getDataSource().getConnection();
			
			query = checkRicevuta;
			statement = connection.prepareStatement(query);
			index=1;
			statement.setInt(index++, idEnte);
			statement.setString(index++, id_messaggio);
			statement.setString(index++, msgid);
			ResultSet resultSet = statement.executeQuery();			
			int ricevuta_esistente = 0;
			while (resultSet.next()) {
				ricevuta_esistente = resultSet.getInt("ricevuta_gia_esistente");
			}
			resultSet.close();
			statement.close();
			
			if(ricevuta_esistente == 0) {
				query = insertEmailConsegnate;
				statement = connection.prepareStatement(query);
				index=1;
				statement.setInt(index++, idEnte);
				statement.setString(index++, id_messaggio);
				statement.setString(index++, msgid);
				
				java.util.Date dataTemp = null;
				//dataTemp = new SimpleDateFormat("dd/MM/yyyy").parse(data_invio_mail);
				java.sql.Date sqlDate = new java.sql.Date(data_invio_mail.getTime());
				statement.setDate(index++, sqlDate);
				statement.setString(index++, oggetto_risposta);
				statement.setString(index++, corpo_risposta);

				result  = statement.executeUpdate();			
				statement.close();
				
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		finally {
			connection.close();
		}
		return result;
	}
		
	public void getConfiguration(int idEnte) throws SQLException {
		mapConfig = new myIndexableMap<String, String>();
		
		String query = queryConfiguration;

		Connection connection = dbpool.getDataSource().getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = connection.prepareStatement(query);

			int index=1;
			statement.setInt (index++, idEnte);
			resultSet = statement.executeQuery();			
			
			while (resultSet.next()) {
				mapConfig.put(resultSet.getString("nome_parametro"), resultSet.getString("valore_parametro"));
				System.out.println("nome_parametro:" +	resultSet.getString("nome_parametro") + 
						" valore:" + resultSet.getString("valore_parametro"));
			}
		
			setUsernameAndPassword(idEnte, 
					(String) mapConfig.get("mail.username"),
					(String) mapConfig.get("mail.password"),
					(String) mapConfig.get("mail.host"),
					(String) mapConfig.get("mail.imap.host"),
					(String) mapConfig.get("mail.imap.port"),  
					(String) mapConfig.get("webmail.host"),  
					(String) mapConfig.get("webmail.username"),  
					(String) mapConfig.get("webmail.password")  
					);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			resultSet.close();
			statement.close();
			connection.close();
		}
				
		//query = "SELECT * FROM table WHERE id = '?'";
        //query = myUtility.setString(query,  "?", String.valueOf(id));        
	}
	public void setUsernameAndPassword(int idEnte, 
			String userName, 
			String password, 
			String mailHost,
			String serverImap, 
			String serverPort,
			String webmailHost,
			String webmailUserName, 
			String webmailPassword 
			) {
		Ente = idEnte;
		sUsernameServerImap = userName;
		sPasswordServerImap = password;
		sMailHost = mailHost;
		sServerImap = serverImap;
		sServerImapPort = serverPort;
		sServerImapWebMail = webmailHost;
		sServerImapWebMailUsername = webmailUserName;
		sServerImapWebMailPassowrd = webmailPassword;
	}
	
	public String queryString(String query, String selectField) throws Exception {
		String output = null;

		try {
			logger.info("Dao::queryString() " + query + " selectField" + selectField);

			java.sql.Connection connection = dbpool.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				output = rs.getString(selectField);
			}
			ps.close();
			connection.close();

		} catch (Exception e) {
			logger.error("Dao: Errore in queryString: " + e.getMessage());
			throw e;
		}
		return output;		
	}

	public ResultSet queryString(String query) throws Exception {
		String output = null;
		ResultSet rs = null; 
		try {
			logger.info("Dao::queryString() " + query);

			java.sql.Connection connection = dbpool.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			ps.close();			
			connection.close();

		} catch (Exception e) {
			logger.error("Dao: Errore in queryString: " + e.getMessage());
			throw e;
		}
		return rs;		
	}

	public int queryInt(String query, String selectField) throws Exception {
		int output = 0;

		try {
			logger.info("Dao::queryInt() " + query + " selectField" + selectField);

			java.sql.Connection connection = dbpool.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				output = rs.getInt(selectField);
			}
			ps.close();
			connection.close();

		} catch (Exception e) {
			logger.error("Dao: Errore in queryInt: " + e.getMessage());
			throw e;
		}
		return output;		
	}

	public void executeQuery(String query) throws Exception {

		try {
			logger.info("Dao::executeQuery() " + query);

			java.sql.Connection connection = dbpool.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ps.executeQuery();
			ps.close();			
			connection.close();
		} catch (Exception e) {
			logger.error("Dao: Errore in executeQuery: " + e.getMessage());
			throw e;
		}
	}

	public void executeUpdate(String query) throws Exception {

		try {
			logger.info("Dao::executeUpdate() " + query);

			java.sql.Connection connection = dbpool.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ps.executeUpdate();
			ps.close();			
			connection.close();
		} catch (Exception e) {
			logger.error("Dao: Errore in executeUpdate: " + e.getMessage());
			throw e;
		}
	}

	public int executeInsert(String query) throws Exception {

		int last_insert_id = 0;
		try {
			logger.info("Dao::executeInsert() " + query);

			java.sql.Connection connection = dbpool.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();

			try (ResultSet rs_insert = ps.getGeneratedKeys()) {									
				if (rs_insert.next()) {
					last_insert_id = rs_insert.getInt(1);
				}
				rs_insert.close();								    

			} catch(Exception e) {
				e.printStackTrace();
				logger.trace("ERRORE IN RUN  msgerrore:" + e.getMessage());
			}
			ps.close();
		} catch (Exception e) {
			logger.error("Dao: Errore in executeInsert: " + e.getMessage());
			throw e;
		}
		return last_insert_id; 
	}		
	public void logInfo(String msg) throws Exception {
		try {
			logger.info("Dao::logInfo() " + msg);

			String query=loggerDB;
			java.sql.Connection connection = dbpool.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, msg);
			int rc = ps.executeUpdate();
			ps.close();

			connection.close();

		} catch (Exception e) {
			logger.info("Dao: Errore in logInfoGio: " + e.getMessage());
			throw e;
		}		
	}

	public static void main(String[] args) {
		DaoConfigCross q = new DaoConfigCross();
		
		int idEnte = 2001;
		try {
			q.getConfiguration(idEnte);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
