package CiviliaDao.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import CiviliaDao.utilities.LogClass;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.ProvinciaDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkFormeGiuridiche;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkTipoCollegio;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.PraticaAnagrafica;

public class Dao {
  
	public LogClass logger;
		
	private PoolConnectionDB dbPool;
	
	public Dao() {
		
	}
	
	public void openConnection()
	{
		dbPool = new PoolConnectionDB();
	}

	public void closeConnection()
	{
		dbPool.close();
	}
	/**
	 * @return the logger
	 */
	public LogClass getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(LogClass logger) {
		this.logger = logger;
	}

	/**
	 * @param connection the connection to set
	 */
	public Connection getConnection() {
		return this.dbPool.getConnectionPool();
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
	
	public String getUTCDataTime() throws Exception {
		String UTCDataTime = null;
		
		try {
			logger.info("Dao::getUTCDataTime()");

			java.sql.Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(Query.getUTCDataTime);

			// set the preparedstatement parameters
			java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime()); 
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UTCDataTime = rs.getString("datatimezone");
			}
			ps.close();
			
			logger.info("Dao::UTCDataTime:" + UTCDataTime);
			//connection.close();

		} catch (Exception e) {
			logger.info("Dao: Errore in getUTCDataTime: " + e.getMessage());
			throw e;
		}
		return UTCDataTime;
		
	}
	public String queryString(String query, String selectField) throws Exception {
		String output = null;

		try {
			logger.info("Dao::queryString()");

			java.sql.Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(query);

			// set the preparedstatement parameters

			java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime()); 

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				output = rs.getString(selectField);
			}
			ps.close();

			//connection.close();

		} catch (Exception e) {
			logger.info("Dao: Errore in queryString: " + e.getMessage());
			throw e;
		}
		return output;		
	}

	public ResultSet queryString(String query) throws Exception {
		String output = null;
		ResultSet rs = null; 
		try {
			logger.info("Dao::queryString()");

			java.sql.Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			ps.close();			
			//connection.close();

		} catch (Exception e) {
			logger.info("Dao: Errore in queryString: " + e.getMessage());
			throw e;
		}
		return rs;		
	}

	public int queryInt(String query, String selectField) throws Exception {
		int output = 0;

		try {
			logger.info("Dao::queryInt()");

			java.sql.Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(query);

			// set the preparedstatement parameters

			java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime()); 

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				output = rs.getInt(selectField);
			}
			ps.close();

			//connection.close();

		} catch (Exception e) {
			logger.info("Dao: Errore in queryInt: " + e.getMessage());
			throw e;
		}
		return output;		
	}

	public void executeQuery(String query) throws Exception {

		try {
			logger.info("Dao::executeQuery()");

			java.sql.Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			ps.close();			
			//connection.close();
		} catch (Exception e) {
			logger.info("Dao: Errore in executeQuery: " + e.getMessage());
			throw e;
		}
	}

	/*
	 * AnagraficaDTO
	 * AnagraficaRecatipoDTO
	 * ComuneDTO
	 * ProvinciaDTO
	 * RecapitoDTO
	 */
	
	public List<PraticaAnagrafica> getListAnagraficheById(String idPratica) throws SQLException {
		int index = 1;

		List<PraticaAnagrafica> listPraticaAnagrafica = new ArrayList<PraticaAnagrafica>();
		
		String sql =   " SELECT    "                                                                                                                                                                                                                                                    
				+ " union_tab_anag.tipoAnagrafica,   " 
				+ " union_tab_anag.cognome,   "
				+ " union_tab_anag.nome,   " 
				+ " union_tab_anag.codiceFiscale,   "
				+ " union_tab_anag.denominazione,   "
				+ " union_tab_anag.partitaIva,   "
				+ " union_tab_anag.flgIndividuale,   " 
				+ " union_tab_anag.dataNascita,   " 
				+ " union_tab_anag.idCittadinanza,   " 
				+ " union_tab_anag.nazionalita,   " 
				+ " union_tab_anag.comuneNascita,   " 
				+ " union_tab_anag.localitaNascita,   "         
				+ " union_tab_anag.sesso,   "                                                                                     
				+ " union_tab_anag.tipoCollegio,   "                                                                                      
				+ " union_tab_anag.numeroIscrizione,   "                                                                             
				+ " union_tab_anag.dataIscrizione,   "                                                                           
				+ " union_tab_anag.provinciaIscrizione,   "                                                                            
				+ " union_tab_anag.provinciaCciaa,   "                                                                                
				+ " union_tab_anag.flgAttesaIscrizioneRi,   "                                                                            
				+ " union_tab_anag.flgObbligoIscrizioneRi,   "                                                                              
				+ " union_tab_anag.dataIscrizioneRi,   " 
				+ " union_tab_anag.numeroIscrizioneRi,   " 
				+ " union_tab_anag.flgAttesaIscrizioneRea,   " 
				+ " union_tab_anag.dataIscrizioneRea,   " 
				+ " union_tab_anag.numeroIscrizioneRea,   " 
				+ " union_tab_anag.formaGiuridica,   " 
				+ " union_tab_anag.varianteAnagrafica FROM ("
				+ " SELECT "                                                                                                               
				+ " (CASE WHEN rp.is_fisica <> 0 THEN 'F' ELSE 'G' END) AS tipoAnagrafica,   " 
				+ " rp.cognome_ragsociale AS cognome,   " 
				+ " rp.nome AS nome,   " 
				+ " rp.cod_fiscale AS codiceFiscale,   " 
				+ " 'Responsabile procedimento' AS denominazione,   " 
				+ " rp.partita_iva AS partitaIva,   " 
				+ " '' AS flgIndividuale,   " 
				+ " rp.data_nascitaCost AS dataNascita,   " 
				+ " '' AS idCittadinanza,   " 
				+ " '' AS nazionalita,   " 
				+ " (rp.luogo_nascitaCost || ' ' || rp.luogo_nascitaCost_Estero) AS comuneNascita,   " 
				+ " (rp.luogo_nascitaCost || ' ' || rp.luogo_nascitaCost_Estero) AS localitaNascita,   " 
				+ " rp.sesso AS sesso,   "                                                                                           
				+ " '' AS tipoCollegio,   " 
				+ " '' AS numeroIscrizione,   " 
				+ " '' AS dataIscrizione,   " 
				+ " '' AS provinciaIscrizione,   " 
				+ " '' AS provinciaCciaa,   " 
				+ " '' AS flgAttesaIscrizioneRi,   " 
				+ " '' AS flgObbligoIscrizioneRi,   " 
				+ " '' AS dataIscrizioneRi,   " 
				+ " '' AS numeroIscrizioneRi,   " 
				+ " '' AS flgAttesaIscrizioneRea,   " 
				+ " '' AS dataIscrizioneRea,   " 
				+ " '' AS numeroIscrizioneRea,   " 
				+ " '' AS formaGiuridica,   " 
				+ " '' AS varianteAnagrafica " 
				+ " FROM `civilia_db`.`responsabile_procedimento` rp "
				+ " LEFT OUTER JOIN `civilia_db`.`procedimento_primario` pp "
				+ " ON (rp.procedimento_primario_id = pp.id) "
				+ " LEFT OUTER JOIN `civilia_db`.`pratica` p ON (pp.pratica_id = p.id) " 	
				+ " WHERE p.codice = ? "
				+ " UNION "
				+ " SELECT "                                                                                                              
				+ " (CASE WHEN a.is_fisica <> 0 THEN 'F' ELSE 'G' END) AS tipoAnagrafica,   "                                
				+ " a.cognome_ragsociale AS cognome,   "                                             
				+ " a.nome AS nome,   "                                                                      
				+ " a.cod_fiscale AS codiceFiscale,   "                                     
				+ " ref.tipo_referenza_descrizione AS denominazione,   "                                            
				+ " a.partita_iva AS partitaIva,   "                                                                
				+ " '' AS flgIndividuale,   "                                                                          
				+ " a.data_nascitaCost AS dataNascita,   "                                   
				+ " '' AS idCittadinanza,   "                                                                        
				+ " '' AS nazionalita,   "                                              
				+ " '' AS comuneNascita,   "              	                        
				+ " '' AS localitaNascita,   "                 	                        
				+ " a.sesso AS sesso,   "                                               
				+ " ref.iscrizione_alboprof_descrizione AS tipoCollegio,   "                                              
				+ " ref.iscrizione_alboprof_num_iscrizione AS numeroIscrizione,   " 
				+ " ref.data_inizio AS dataIscrizione,   " 
				+ " ref.iscrizione_alboprof_provincia AS provinciaIscrizione,   "
				+ " '' AS provinciaCciaa,   " 
				+ " '' AS flgAttesaIscrizioneRi,   " 
				+ " '' AS flgObbligoIscrizioneRi,   " 
				+ " ref.data_iscrizione_ri AS dataIscrizioneRi,   " 
				+ " ref.num_iscrizione_ri AS numeroIscrizioneRi,   " 
				+ " '' AS flgAttesaIscrizioneRea,   " 
				+ " ref.data_iscrizione_rea AS dataIscrizioneRea,   " 
				+ " ref.num_iscrizione_rea AS numeroIscrizioneRea,   " 
				+ " '' AS formaGiuridica,   " 
				+ " '' AS varianteAnagrafica " 
				+ " FROM `civilia_db`.`pratica` p "                                                                           
				+ " LEFT OUTER JOIN `civilia_db`.`referenti` ref ON (p.id = ref.pratica_id) "
				+ " LEFT OUTER JOIN `civilia_db`.`anagrafica` a ON (ref.`id` = a.`referente_id`) "	                         
				+ " WHERE p.codice = ? "
				+ " ) union_tab_anag ";
				            
		
		PreparedStatement ps = getConnection().prepareStatement(sql);
		index=1;
		ps.setString (index++, idPratica);
		ps.setString (index++, idPratica);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			PraticaAnagrafica praticaAnagrafica = new PraticaAnagrafica();
			Anagrafica anagrafica = new Anagrafica();

			anagrafica.setTipoAnagrafica(rs.getString("tipoAnagrafica").charAt(0));
			anagrafica.setCognome(rs.getString("cognome"));
			anagrafica.setNome(rs.getString("nome"));
			anagrafica.setCodiceFiscale(rs.getString("codiceFiscale"));
			anagrafica.setDenominazione(rs.getString("denominazione"));
			anagrafica.setPartitaIva(rs.getString("partitaIva"));
			
			//anagrafica.setFlgIndividuale(rs.getString("flgIndividuale") != null ? rs.getString("flgIndividuale").charAt(0) : '0');
			if(rs.getString("flgIndividuale") != null && !rs.getString("flgIndividuale").isEmpty()) {
				anagrafica.setFlgIndividuale( rs.getString("flgIndividuale").charAt(0));
			} 
			
			String datadinascita = rs.getString("dataNascita");			
			if(datadinascita != null && !datadinascita.trim().isEmpty()) {
				java.util.Date dataTemp = null;
				try {
					dataTemp = new SimpleDateFormat("dd/MM/yyyy").parse(datadinascita);
					java.sql.Date sqlDate = new java.sql.Date(dataTemp.getTime());
					anagrafica.setDataNascita(sqlDate);
				} catch (ParseException e) {
					anagrafica.setDataNascita(null);
				}
			} 
			
			//anagrafica.setIdCittadinanza(0);
			//anagrafica.setDesNazionalita(rs.getString("nazionalita"));
			
			
			LkComuni comuneNascita = new LkComuni();
			comuneNascita.setIdComune(0);
			comuneNascita.setDescrizione(rs.getString("comuneNascita"));			
			anagrafica.setIdComuneNascita(comuneNascita);
			anagrafica.setLocalitaNascita(rs.getString("comuneNascita"));
			
			anagrafica.setLocalitaNascita(rs.getString("localitaNascita"));

			if(rs.getString("sesso") != null && !rs.getString("sesso").isEmpty()) {
				anagrafica.setSesso( rs.getString("sesso").charAt(0));
			} 
			
			LkTipoCollegio idTipoCollegio = new LkTipoCollegio();
			idTipoCollegio.setIdTipoCollegio(0);
			idTipoCollegio.setDescrizione(rs.getString("tipoCollegio"));
			anagrafica.setIdTipoCollegio(idTipoCollegio);
			
			anagrafica.setNumeroIscrizione(rs.getString("numeroIscrizione"));

			String dataIscrizione = rs.getString("dataIscrizione");			
			if(dataIscrizione != null && !dataIscrizione.trim().isEmpty()) {
				java.util.Date dataTemp;
				try {
					dataTemp = new SimpleDateFormat("dd/MM/yyyy").parse(dataIscrizione);
					java.sql.Date sqlDate = new java.sql.Date(dataTemp.getTime());
					anagrafica.setDataIscrizione(sqlDate);
				} catch (ParseException e) {
					anagrafica.setDataIscrizione(null);
				}
			}
						
			LkProvincie idProvincia = new LkProvincie();
			idProvincia.setIdProvincie(0);
			idProvincia.setDescrizione(rs.getString("provinciaIscrizione"));
			anagrafica.setIdProvinciaIscrizione(idProvincia);
			
			ProvinciaDTO provinciaCciaa = new ProvinciaDTO();
			provinciaCciaa.setDescrizione(rs.getString("provinciaCciaa"));
			
			LkProvincie idProvinciaCciaa = new LkProvincie();
			idProvinciaCciaa.setIdProvincie(0);
			idProvinciaCciaa.setDescrizione(rs.getString("provinciaCciaa"));
			anagrafica.setIdProvinciaCciaa(idProvinciaCciaa);
			
			if(rs.getString("flgAttesaIscrizioneRi") != null && !rs.getString("flgAttesaIscrizioneRi").isEmpty()) {
				anagrafica.setFlgAttesaIscrizioneRi( rs.getString("flgAttesaIscrizioneRi").charAt(0));
			} 

			if(rs.getString("flgObbligoIscrizioneRi") != null && !rs.getString("flgObbligoIscrizioneRi").isEmpty()) {
				anagrafica.setFlgObbligoIscrizioneRi( rs.getString("flgObbligoIscrizioneRi").charAt(0));
			} 
			
			String dataIscrizioneRi = rs.getString("dataIscrizioneRi");	
			if(dataIscrizioneRi != null && !dataIscrizioneRi.trim().isEmpty()) {
				java.util.Date dataTemp;
				try {
					dataTemp = new SimpleDateFormat("dd/MM/yyyy").parse(dataIscrizioneRi);
					java.sql.Date sqlDate = new java.sql.Date(dataTemp.getTime());
					anagrafica.setDataIscrizioneRi(sqlDate);
				} catch (ParseException e) {
					anagrafica.setDataIscrizioneRi(null);
				}
			}
						
			anagrafica.setnIscrizioneRi(rs.getString("numeroIscrizioneRi"));

			if(rs.getString("flgAttesaIscrizioneRea") != null && !rs.getString("flgAttesaIscrizioneRea").isEmpty()) {
				anagrafica.setFlgAttesaIscrizioneRi( rs.getString("flgAttesaIscrizioneRea").charAt(0));
			} 
						
			String dataIscrizioneRea = rs.getString("dataIscrizioneRea");	
			if(dataIscrizioneRea != null && !dataIscrizioneRea.trim().isEmpty()) {
				java.util.Date dataTemp;
				try {
					dataTemp = new SimpleDateFormat("dd/MM/yyyy").parse(dataIscrizioneRea);
					java.sql.Date sqlDate = new java.sql.Date(dataTemp.getTime());
					anagrafica.setDataIscrizioneRea(sqlDate);
				} catch (ParseException e) {
					anagrafica.setDataIscrizioneRea(null);
				}
			}
			anagrafica.setnIscrizioneRea(rs.getString("numeroIscrizioneRea"));
			
			LkFormeGiuridiche idFormegiuridiche = new LkFormeGiuridiche();
			idFormegiuridiche.setIdFormeGiuridiche(0);
			idFormegiuridiche.setDescrizione(rs.getString("formaGiuridica"));
			anagrafica.setIdFormaGiuridica(idFormegiuridiche);
			anagrafica.setVarianteAnagrafica(rs.getString("varianteAnagrafica"));
			
			
			praticaAnagrafica.setAnagrafica(anagrafica);
			
			LkTipoRuolo lk = new LkTipoRuolo();
			lk.setIdTipoRuolo(0);
			lk.setDescrizione(rs.getString("denominazione"));
			praticaAnagrafica.setLkTipoRuolo(lk);
						
			praticaAnagrafica.setFlgDittaIndividuale(rs.getString("tipoAnagrafica"));
			
			listPraticaAnagrafica.add(praticaAnagrafica);
		}
		ps.close();
		
		return listPraticaAnagrafica;		
	}
	
	public void logInfo(String msg) throws Exception {
		try {
			logger.info("Dao::logInfo()");

			java.sql.Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(Query.insertMsgLog);
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

		String sql = "INSERT INTO `civilia_db`.`fascicolo` " + 
				"	(`codice`,`commissioni`,`codice_sportello_unico`,`csysdate`,`today`) VALUES (?,?,?,?,?)";
		
		Dao dao = new Dao();
		PreparedStatement preparedStmt;
		int id_fascicolo = 0;
		try {
			preparedStmt = dao.getConnection().prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
		
			int index=1;
			preparedStmt.setString (index++, "aaaaaaaaaaaa");
			preparedStmt.setString (index++, "bbbbbbbbbbbb");
			preparedStmt.setString (index++, "SUAP");
			java.util.Date dataTempTimestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("10/10/2020 07:41:55");
			java.sql.Timestamp sqlDateTime = new java.sql.Timestamp(dataTempTimestamp.getTime());
			
			preparedStmt.setTimestamp(index++, (Timestamp) sqlDateTime);
			
			java.util.Date dataTemp = new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2020");
			java.sql.Date sqlDate = new java.sql.Date(dataTemp.getTime());
			preparedStmt.setDate (index++, (Date) sqlDate);
			int affectedRows = preparedStmt.executeUpdate();

			if (affectedRows == 0) {
			    throw new SQLException("Creating civilia_db failed, no rows affected.");
			}

			try (ResultSet rs = preparedStmt.getGeneratedKeys()) {
			    if (rs.next()) {
			    	id_fascicolo = rs.getInt(1);
			    }
			    rs.close();
			}
								
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
