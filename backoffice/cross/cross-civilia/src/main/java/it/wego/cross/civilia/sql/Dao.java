package it.wego.cross.civilia.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.wego.cross.civilia.Costants;
import it.wego.cross.civilia.utilities.LogClass;
import it.wego.cross.civilia.utilities.myUtility;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkTipoParticella;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.LkTipoUnita;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.Procedimenti;


public class Dao {
  
	public LogClass LogClass;
		
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
	 * @return the LogClass
	 */
	public LogClass getLogClass() {
		return LogClass;
	}

	/**
	 * @param LogClass the LogClass to set
	 */
	public void setLogClass(LogClass LogClass) {
		this.LogClass = LogClass;
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
			LogClass.info("Dao::getUTCDataTime()");

			java.sql.Connection connection = getConnection();
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
	public String queryString(String query, String selectField) throws Exception {
		String output = null;

		try {
			LogClass.info("Dao::queryString()");

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
			LogClass.info("Dao: Errore in queryString: " + e.getMessage());
			throw e;
		}
		return output;		
	}

	public ResultSet queryString(String query) throws Exception {
		String output = null;
		ResultSet rs = null; 
		try {
			LogClass.info("Dao::queryString()");

			java.sql.Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			ps.close();			
			//connection.close();

		} catch (Exception e) {
			LogClass.info("Dao: Errore in queryString: " + e.getMessage());
			throw e;
		}
		return rs;		
	}

	public int queryInt(String query, String selectField) throws Exception {
		int output = 0;

		try {
			LogClass.info("Dao::queryInt()");

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
			LogClass.info("Dao: Errore in queryInt: " + e.getMessage());
			throw e;
		}
		return output;		
	}

	public void executeQuery(String query) throws Exception {

		try {
			LogClass.info("Dao::executeQuery()");

			java.sql.Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			ps.close();			
			//connection.close();
		} catch (Exception e) {
			LogClass.info("Dao: Errore in executeQuery: " + e.getMessage());
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
		int step = 0;
		LogClass.trace("CiviliaDao::getListAnagraficheById START");
		List<PraticaAnagrafica> listPraticaAnagrafica = new ArrayList<PraticaAnagrafica>();
	
		String sql = 
				" SELECT "					
				+ "a.`id_anagrafica`, "
				+ "a.`tipoAnagrafica`, "
				+ "a.`cognome`, "
				+ "a.`nome`, "
				+ "a.`codiceFiscale`, "
				+ "a.`denominazione`, "
				+ "a.`partitaIva`, "
				+ "a.`flgIndividuale`, "
				+ "a.`dataNascita`, "
				+ "a.`idCittadinanza`, "
				+ "a.`nazionalita`, "
				+ "a.`comuneNascita`," 
				+ "a.`localitaNascita`, "
				+ " a.`sesso`"
				+ " FROM `civilia_db`.`pratica` p"
				+ " JOIN `civilia_db`.`anagrafica_civilia` a ON (a.pratica_id = p.id) "
				+ " WHERE a.cognome is not null and a.nome is not null and a.codiceFiscale is not null and p.codice = ?"
				+ " GROUP BY a.`tipoAnagrafica`, a.`cognome`, a.`nome`, a.`codiceFiscale`, "
				+ " a.`denominazione`, a.`partitaIva`, a.`flgIndividuale`, a.`dataNascita`, "
				+ " a.`idCittadinanza`, a.`nazionalita`, a.`comuneNascita`,a.`localitaNascita`,  a.`sesso`";
		
		PreparedStatement ps = getConnection().prepareStatement(sql);
		step=1;
		index=1;
		ps.setString (index++, idPratica);		
		ResultSet rs = ps.executeQuery();
		step=2;
		
		while (rs.next()) {
			PraticaAnagrafica praticaAnagrafica = new PraticaAnagrafica();
			Anagrafica anagrafica = new Anagrafica();
			anagrafica.setIdAnagrafica(Integer.valueOf(rs.getString("id_anagrafica")));
			anagrafica.setProvenienza(Costants.CIVILIA);
			anagrafica.setTipoAnagrafica(rs.getString("tipoAnagrafica").charAt(0));
			anagrafica.setCognome(rs.getString("cognome"));
			anagrafica.setDenominazione(rs.getString("cognome"));
			anagrafica.setNome(rs.getString("nome"));
			anagrafica.setCodiceFiscale(rs.getString("codiceFiscale"));
			anagrafica.setRuolo_civilia(rs.getString("denominazione"));
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
						
			LkComuni comuneNascita = new LkComuni();
			comuneNascita.setIdComune(0);
			comuneNascita.setDescrizione(rs.getString("comuneNascita"));			
			anagrafica.setIdComuneNascita(comuneNascita);
			anagrafica.setLocalitaNascita(rs.getString("comuneNascita"));
			
			anagrafica.setLocalitaNascita(rs.getString("localitaNascita"));

			if(rs.getString("sesso") != null && !rs.getString("sesso").isEmpty()) {
				anagrafica.setSesso( rs.getString("sesso").charAt(0));
			} 
			LogClass.trace("CiviliaDao::getListAnagraficheById ANAGRAFICA: " + anagrafica.toString());
			
			praticaAnagrafica.setAnagrafica(anagrafica);
			
			LkTipoRuolo lk = new LkTipoRuolo();
			String cod_ruolo = "999";

			lk.setIdTipoRuolo(Integer.valueOf(cod_ruolo));
			lk.setCodRuolo(cod_ruolo);
			lk.setDescrizione(rs.getString("denominazione"));
			
			praticaAnagrafica.setLkTipoRuolo(lk);
						
			praticaAnagrafica.setFlgDittaIndividuale(rs.getString("tipoAnagrafica"));

			LogClass.trace("CiviliaDao::getListAnagraficheById PRATICA_ANAGRAFICA: " + praticaAnagrafica.toString());

			listPraticaAnagrafica.add(praticaAnagrafica);						
		}
		step=4;

		ps.close();
		
		LogClass.trace("CiviliaDao::getListAnagraficheById LAST STEP EXECUTE: " + step);
		return listPraticaAnagrafica;		
	}
	
	private Lk_tipo_ruolo get_ruolo_cross(String ruolo_civilia, Connection cross_conn) throws SQLException {
		int index = 1, w_id_tipo_ruolo = 0, w_hidden = 0;
		String w_cod_ruolo = "";
		String w_descrizione_ruolo_cross = ""; 
		Lk_tipo_ruolo lkr_cross = null;
		
		String sql = "SELECT DISTINCT" 
				+ " lkr_cross.id_tipo_ruolo," 
				+ " lkr_cross.cod_ruolo," 
				+ " lkr_cross.descrizione AS descrizione_ruolo_cross," 
				+ " lkr_cross.hidden" 
				+ " FROM opencross.ruoli_etichetta_civilia_cross lkr_civ" 
				+ " JOIN opencross.lk_tipo_ruolo lkr_cross ON (lkr_cross.id_tipo_ruolo = lkr_civ.id_ruolo_cross) "
				+ " WHERE lkr_civ.ruolo_etichetta_civilia_cross = ? limit 1";
				            
		PreparedStatement ps = cross_conn.prepareStatement(sql);
		index=1;
		ps.setString (index++, ruolo_civilia);
		ResultSet rs = ps.executeQuery();		
		while (rs.next()) {
			w_id_tipo_ruolo = rs.getInt("id_tipo_ruolo");
			w_cod_ruolo = rs.getString("cod_ruolo");
			w_descrizione_ruolo_cross = rs.getString("descrizione_ruolo_cross");
			w_hidden = rs.getInt("hidden");
			
			lkr_cross = new Lk_tipo_ruolo(w_id_tipo_ruolo, w_descrizione_ruolo_cross, w_cod_ruolo, w_hidden);
		}
		rs.close();
		ps.close();

		return lkr_cross;
	}
	
	public List<DatiCatastali> getListDatiCatastaliById(String idPratica) throws SQLException {
		int index = 1;

		List<DatiCatastali> listDatiCatastali = new ArrayList<DatiCatastali>();
		String sql = "SELECT p.codice AS identificativoPratica, "
				+ "	ot.categoria AS categoria, " 
				+ "	ot.`ot_classe_descrizione` AS tipoParticella, " 
				+ "	otc.`foglio` AS foglio, " 
				+ "	otc.`numero_particella` AS mappale, " 
				+ "	otc.`sezione_catastale` AS sezione, " 
				+ "	ot.`ot_classe_codice`, "
				+ "	otc.`comune_censuario_tipo` AS tipoUnita, " 
				+ "	ot.caratteristica AS estensioneParticella, "
				+ "	otc.`subalterno` AS subalterno, " 
				+ "	ot.`comune_censuario_descrizione` AS comuneCensuario "
				+ " FROM `civilia_db`.`pratica` p  "  	 
				+ " LEFT OUTER JOIN `civilia_db`.`oggetti_territoriali` ot ON (p.`id` = ot.`pratica_id`)"
				+ " LEFT OUTER JOIN `civilia_db`.`ot_catasto` otc ON (ot.`id` = otc.`oggetto_territoriale_id`)"
				+ " WHERE p.codice = ?";
				            
		
		PreparedStatement ps = getConnection().prepareStatement(sql);
		index=1;
		ps.setString (index++, idPratica);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			DatiCatastali datiCatastali = new DatiCatastali();
			datiCatastali.setProvenienza(Costants.CIVILIA);
			datiCatastali.setCategoria(rs.getString("categoria"));
			
			LkTipoParticella idTipoParticella = new LkTipoParticella();
			idTipoParticella.setIdTipoParticella(0);
			idTipoParticella.setDescrizione(rs.getString("tipoParticella"));			
			datiCatastali.setIdTipoParticella(idTipoParticella);
			
			datiCatastali.setFoglio(rs.getString("foglio"));
			datiCatastali.setMappale(rs.getString("mappale"));
			datiCatastali.setSezione(rs.getString("sezione"));
			
			LkTipoUnita idTipoUnita = new LkTipoUnita();
			idTipoUnita.setIdTipoUnita(0);
			idTipoUnita.setDescrizione(rs.getString("tipoUnita"));
			datiCatastali.setIdTipoUnita(idTipoUnita);
			
			datiCatastali.setEstensioneParticella(rs.getString("estensioneParticella"));
			datiCatastali.setSubalterno(rs.getString("subalterno"));
			datiCatastali.setComuneCensuario(rs.getString("comuneCensuario"));
			datiCatastali.setProvenienza("CIVILIA");
			listDatiCatastali.add(datiCatastali);
		}
		ps.close();
		
		return listDatiCatastali;		
	}

	
	public List<PraticaProcedimenti> getListEndoprocedimentiById(String idPratica) throws SQLException {
		int index = 1, idProcedimento = 1;

		List<PraticaProcedimenti> listPraticaProcedimenti = new ArrayList<PraticaProcedimenti>();
		
		String sql = " SELECT tab_union_procedimenti.identificativoPratica,"
				+" tab_union_procedimenti.descrizione,"
				+" tab_union_procedimenti.enteCompetente,"
				+" tab_union_procedimenti.terminiEvasione "
				+" FROM (SELECT "
				+" 	p.codice					AS identificativoPratica,"
				+" 	pp.`tipo_procedimento` 		AS descrizione,"
				+" 	rp.`cognome_ragsociale`		AS enteCompetente,"
				+" 	pp.`gg_espletamento`		AS terminiEvasione "
				+" 	FROM `civilia_db`.`pratica` p "
				+" 	INNER JOIN `civilia_db`.`procedimento_primario` pp ON (p.id = pp.pratica_id)"
				+"  INNER JOIN `civilia_db`.`responsabile_procedimento` rp ON (pp.id = rp.procedimento_primario_id)"
				+" 	WHERE p.codice = ?"
				+" UNION "
				+" 	SELECT 	p.codice						AS identificativoPratica,"
				+" 		en.`tipo_proc_settore_descrizione` 	AS descrizione,"
				+" 		en.`resp_proc_cognome_ragsociale` 		AS enteCompetente,"
				+" 		en.`tipo_proc_gg_espletamento` 		AS terminiEvasione"
				+" 	FROM `civilia_db`.`pratica` p "
				+" 	INNER JOIN `civilia_db`.`endoprocedimenti` en "
				+" 		ON (p.id = en.pratica_id AND en.tipo_proc_settore_descrizione <> '')	"
				+" 	WHERE p.codice = ?"
				+" ) tab_union_procedimenti";
		
		PreparedStatement ps = getConnection().prepareStatement(sql);
		index=1;
		ps.setString (index++, idPratica);
		ps.setString (index++, idPratica);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			PraticaProcedimenti pp = new PraticaProcedimenti();
			pp.setProvenienza(Costants.CIVILIA);
			Procedimenti p = new Procedimenti();
			p.setIdProc(idProcedimento++);
			
			p.setTipoProc(rs.getString("descrizione"));
			
			int termini = myUtility.convertDecimalToInteger(rs.getString("terminiEvasione"));
			//if(termini == 0) {termini=30;}  
			p.setTermini(termini);
			
			Enti e = new Enti();
			//e.setIdEnte(2001);
			if(rs.getString("enteCompetente")!= null && !rs.getString("enteCompetente").isEmpty()) {
				e.setDescrizione(rs.getString("enteCompetente"));
			}
			
			pp.setEnti(e);
			pp.setProcedimenti(p);
									
			listPraticaProcedimenti.add(pp);
		}
		ps.close();
		
		return listPraticaProcedimenti;		
	}


	public List<AllegatoDTO> getListAllegatiPratica(String idPratica) throws SQLException {
		int index = 1;

		List<AllegatoDTO> listAllegati = new ArrayList<AllegatoDTO>();
		
		String sql =  " SELECT "
				+ "	p.codice AS identificativo_pratica, "
				+ "	p.id_pratica AS id_pratica, "
				+ "	alp.data_arrivo AS data_ricezione, "
				+ "	alp.id AS idAllegato,"
				+ "	alp.descrizione AS descrizione,"
				+ "	alp.SHA AS nomeFile, "
				+ "	alp.categoria AS tipoFile,"
				+ " '' AS pathFisico"
				+ " FROM civilia_db.allegati_pratica alp "
				+ " LEFT OUTER JOIN `civilia_db`.`pratica` p ON (alp.pratica_id = p.id) 	"
				+ " WHERE p.codice = ?";
		
		PreparedStatement ps = getConnection().prepareStatement(sql);
		index=1;
		ps.setString (index++, idPratica);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			AllegatoDTO al = new AllegatoDTO();
			//al.setProvenienza(Costants.CIVILIA);
			al.setDescrizione(rs.getString("descrizione"));
			al.setNomeFile(rs.getString("nomeFile"));
			al.setPathFile(rs.getString("pathFisico"));
			al.setProvenienza(Costants.CIVILIA);
			listAllegati.add(al);
		}
		ps.close();
		
		return listAllegati;		
	}
	
	public Anagrafica getAnagraficaById(Integer idAnagrafica) throws SQLException {
		int index = 1;

		Anagrafica anagrafica = new Anagrafica();
		
		String sql = " SELECT 	"
				+ "a.`id_anagrafica`, "
				+ "a.`tipoAnagrafica`, "
				+ "a.`cognome`, "
				+ "a.`nome`, "
				+ "a.`codiceFiscale`, "
				+ "a.`denominazione` AS ruolo_civilia, "
				+ "a.`partitaIva`, "
				+ "a.`flgIndividuale`, "
				+ "a.`dataNascita`, "
				+ "a.`idCittadinanza`, "
				+ "a.`nazionalita`, "
				+ "a.`comuneNascita`," 
				+ "a.`localitaNascita`, "
				+ "a.`sesso`"
				+ " FROM `civilia_db`.`anagrafica_civilia` a "
				+ " WHERE a.cognome is not null and a.nome is not null and a.codiceFiscale is not null and a.id_anagrafica = ?"
				+ " GROUP BY a.`tipoAnagrafica`, a.`cognome`, a.`nome`, a.`codiceFiscale`, "
				+ " a.`denominazione`, a.`partitaIva`, a.`flgIndividuale`, a.`dataNascita`, "
				+ " a.`idCittadinanza`, a.`nazionalita`, a.`comuneNascita`,a.`localitaNascita`,  a.`sesso`";
		
		MySqlConnect dao_cross = new MySqlConnect(); 
		Connection cross_connection = dao_cross.getConnection_cross(); 

		PreparedStatement ps = getConnection().prepareStatement(sql);
		index=1;
		ps.setString (index++, idAnagrafica.toString());
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			anagrafica.setIdAnagrafica(rs.getInt("id_anagrafica"));
			anagrafica.setNome(rs.getString("nome"));
			anagrafica.setCognome(rs.getString("cognome"));
			anagrafica.setDenominazione(rs.getString("cognome"));
			anagrafica.setCodiceFiscale(rs.getString("codiceFiscale"));
			String sesso = rs.getString("sesso");
			anagrafica.setSesso(sesso.length() > 0 ? sesso.charAt(0): null);
			anagrafica.setRuolo_civilia(rs.getString("ruolo_civilia"));
			
			Lk_tipo_ruolo lkt = get_ruolo_cross(rs.getString("ruolo_civilia"), cross_connection); 
			LkTipoRuolo lk = new LkTipoRuolo();
			String cod_ruolo = "999";		
			lk.setIdTipoRuolo(lkt.getid_tipo_ruolo());
			lk.setCodRuolo(lkt.getcod_ruolo());
			lk.setDescrizione(lkt.getdescrizione());
			
	        try {
				anagrafica.setDataNascita(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("dataNascita")));
			} catch (ParseException e) {
				LogClass.trace("Errore parsing data anagrafica");
			}
		}
		ps.close();
		dao_cross.close_cross(cross_connection);
		return anagrafica;		
	}

	public void logInfo(String msg) throws Exception {
		try {
			LogClass.info("Dao::logInfo()");

			java.sql.Connection connection = getConnection();
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
