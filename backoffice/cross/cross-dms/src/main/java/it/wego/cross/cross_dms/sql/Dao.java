package it.wego.cross.cross_dms.sql;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import it.wego.cross.beans.Referenti;
import it.wego.cross.beans.Ubicazione;
import it.wego.cross.beans.allegati.UploadInfo;
import it.wego.cross.beans.pratica.AddPraticaDms;
import it.wego.cross.civilia.Costants;
import it.wego.cross.cross_dms.DettaglioPratica;
import it.wego.cross.cross_dms.utilities.LogClass;
import it.wego.cross.cross_dms.utilities.UtilityRestClientClass;
import it.wego.cross.cross_dms.utilities.myUtility;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.NotaDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkTipoParticella;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.LkTipoUnita;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.Procedimenti;

public class Dao {

	public LogClass logger;

	private PoolConnectionDB dbPool;

	private myUtility myUtl;

	public Dao() {
		myUtl = new myUtility();
	}

	public myUtility getMyUtl() {
		return myUtl;
	}

	public void setMyUtl(myUtility myUtl) {
		this.myUtl = myUtl;
	}


	public void openConnection(String db)
	{
		dbPool = new PoolConnectionDB(db);
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
			LogClass.error("Dao: Errore in getUTCDataTime: " + e.getMessage());
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
			LogClass.error("Dao: Errore in queryString: " + e.getMessage());
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
			LogClass.error("Dao: Errore in queryString: " + e.getMessage());
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
			LogClass.error("Dao: Errore in queryInt: " + e.getMessage());
			throw e;
		}
		return output;		
	}

	public void executeQuery(String query) throws Exception {

		try {
			LogClass.info("Dao::executeQuery()");

			java.sql.Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ps.executeQuery();
			ps.close();			
			//connection.close();
		} catch (Exception e) {
			LogClass.error("Dao: Errore in executeQuery: " + e.getMessage());
			throw e;
		}
	}

	public void executeUpdate(String query) throws Exception {

		try {
			LogClass.info("Dao::executeUpdate()");

			java.sql.Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ps.executeUpdate();
			ps.close();			
			//connection.close();
		} catch (Exception e) {
			LogClass.error("Dao: Errore in executeUpdate: " + e.getMessage());
			throw e;
		}
	}

	public int executeInsert(String query) throws Exception {

		int last_insert_id = 0;
		try {
			LogClass.info("Dao::executeInsert()");

			java.sql.Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();

			try (ResultSet rs_insert = ps.getGeneratedKeys()) {									
				if (rs_insert.next()) {
					last_insert_id = rs_insert.getInt(1);
				}
				rs_insert.close();								    

			} catch(Exception e) {
				e.printStackTrace();
				LogClass.trace("ERRORE IN RUN  msgerrore:" + e.getMessage());
			}
			ps.close();
		} catch (Exception e) {
			LogClass.error("Dao: Errore in executeInsert: " + e.getMessage());
			throw e;
		}
		return last_insert_id; 
	}		

	public int getNumberRows(String query){
		try{
			java.sql.Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			if(resultset.last()){
				return resultset.getRow();
			} else {
				return 0; //just cus I like to always do some kinda else statement.
			}
		} catch (Exception e){
			System.out.println("getNumberRows - Error getting row count" + e.getMessage());
			LogClass.trace("getNumberRows - Error getting row count" + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}	
	public DettaglioPratica getDettaglioPraticheById(String idPratica) throws Exception {		

		DettaglioPratica dettPratica = new DettaglioPratica();

		List<PraticaAnagrafica> listPraticaAnagrafica = new ArrayList<PraticaAnagrafica>();
		List<DatiCatastali> listDatiCatastali = new ArrayList<DatiCatastali>();
		List<IndirizzoInterventoDTO> listIndirizziIntervento = new ArrayList<IndirizzoInterventoDTO>();
		List<AllegatoDTO> listAllegati = new ArrayList<AllegatoDTO>();
		List<NotaDTO> listAnnotazioni = new ArrayList<NotaDTO>();

		ObjectMapper om = JsonMapper.builder()
				.enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
				.build();

		try {
			UtilityRestClientClass restGET = new UtilityRestClientClass();
			String urlparam = myUtl.getValueProperties("dms.getPraticaById");
			String jsonResult = restGET.HttpRESTFulGET(urlparam, idPratica);
			LogClass.trace("getDettaglioPraticheById REQUEST:" + urlparam + idPratica + " RESPONSE:" + jsonResult);
			Root rootJsonDms = om.readValue(jsonResult, Root.class);

			PraticaAnagrafica praticaAnagrafica = new PraticaAnagrafica();
			Anagrafica anagrafica = new Anagrafica();
			anagrafica.setProvenienza(Costants.DMS);

			ArrayList<dmsREFERENTI> referenti = rootJsonDms.getTypeMetadata().getrEFERENTI();
			for (dmsREFERENTI ref : referenti) {
				anagrafica.setNome(ref.getnOME().toString());
				anagrafica.setCognome(ref.getcOGNOME_DENOMINAZIONE().toString());
				anagrafica.setDenominazione(ref.getcOGNOME_DENOMINAZIONE().toString());
				anagrafica.setCodiceFiscale(ref.getcODICE_FISCALE().toString());
				anagrafica.setPartitaIva(ref.getpARTITA_IVA().toString());	

				LkTipoRuolo lk = new LkTipoRuolo();				
				String cod_ruolo = "999";		
				lk.setIdTipoRuolo(Integer.valueOf(cod_ruolo));
				lk.setCodRuolo(cod_ruolo);				
				lk.setDescrizione(ref.getrUOLO().toString());				
				praticaAnagrafica.setLkTipoRuolo(lk);
			}

			praticaAnagrafica.setAnagrafica(anagrafica);						
			listPraticaAnagrafica.add(praticaAnagrafica);			

			dettPratica.setPratica(listPraticaAnagrafica);

			//Dati Catastali
			ArrayList<dmsDATICATASTALI> daticatastali = rootJsonDms.getTypeMetadata().getdATI_CATASTALI();
			for (dmsDATICATASTALI dc : daticatastali) {

				DatiCatastali datiCatastali = new DatiCatastali();
				datiCatastali.setProvenienza(Costants.DMS);

				LkTipoParticella idTipoParticella = new LkTipoParticella();
				idTipoParticella.setIdTipoParticella(0);
				idTipoParticella.setDescrizione((dc.getpARTICELLA() != null ? dc.getpARTICELLA().toString() : ""));			
				datiCatastali.setIdTipoParticella(idTipoParticella);

				datiCatastali.setFoglio((dc.getfOGLIO() != null ? dc.getfOGLIO().toString() : ""));			
				datiCatastali.setMappale((dc.getfOGLIO() != null ? dc.getfOGLIO().toString() : ""));			
				datiCatastali.setSezione((dc.getsEZIONE() != null ? dc.getsEZIONE().toString() : ""));			

				LkTipoUnita idTipoUnita = new LkTipoUnita();
				idTipoUnita.setIdTipoUnita(0);
				idTipoUnita.setDescrizione("");
				datiCatastali.setIdTipoUnita(idTipoUnita);

				//datiCatastali.setEstensioneParticella(rs.getString("estensioneParticella"));
				datiCatastali.setSubalterno((dc.getsUBALTERNO() != null ? dc.getsUBALTERNO().toString() : ""));			
				//datiCatastali.setComuneCensuario(rs.getString("comuneCensuario"));
				datiCatastali.setProvenienza(Costants.DMS);

				listDatiCatastali.add(datiCatastali);
			}

			dettPratica.setDaticatastali(listDatiCatastali);

			//IndirizzoIntervento
			ArrayList<dmsUBICAZIONE> datiIndirizzoIntervento = rootJsonDms.getTypeMetadata().getuBICAZIONE();
			for (dmsUBICAZIONE dc : datiIndirizzoIntervento) {

				IndirizzoInterventoDTO datiIndirizzoInterventoDTO = new IndirizzoInterventoDTO();
				datiIndirizzoInterventoDTO.setProvenienza(Costants.DMS);				
				datiIndirizzoInterventoDTO.setLatitudine((dc.getlATITUDINE() != null ? dc.getlATITUDINE().toString() : ""));				
				datiIndirizzoInterventoDTO.setLongitudine((dc.getlONGITUDINE() != null ? dc.getlONGITUDINE().toString() : ""));
				datiIndirizzoInterventoDTO.setCivico((dc.getnUMERO_CIVICO()!= null ? dc.getnUMERO_CIVICO().toString() : ""));
				datiIndirizzoInterventoDTO.setLocalita((dc.getfRAZIONE_LOCALITA() != null ? dc.getfRAZIONE_LOCALITA().toString() : ""));
				datiIndirizzoInterventoDTO.setIndirizzo((dc.getiNDIRIZZO_ATTUALE() != null ? dc.getiNDIRIZZO_ATTUALE().toString() : ""));
				datiIndirizzoInterventoDTO.setPiano((dc.getpIANO() != null ? dc.getpIANO().toString() : ""));
				datiIndirizzoInterventoDTO.setInternoNumero((dc.getiNTERNO() != null ? dc.getiNTERNO().toString() : ""));
				datiIndirizzoInterventoDTO.setInternoLettera((dc.getsCALA() != null ? dc.getsCALA().toString() : ""));

				listIndirizziIntervento.add(datiIndirizzoInterventoDTO);
			}

			dettPratica.setIndIntervento(listIndirizziIntervento);

			//Allegati
			ArrayList<Allegato> allegati  = rootJsonDms.getTypeMetadata().getaLLEGATI().getAllegato();
			for (Allegato al : allegati) {

				AllegatoDTO alDTO = new AllegatoDTO();
				alDTO.setProvenienza(Costants.DMS);							
				alDTO.setDescrizione((al.getDescrizione() != null ? al.getDescrizione().toString() : ""));
				alDTO.setNomeFile((al.getNome_file() != null ? al.getNome_file().toString() : ""));
				alDTO.setIdAllegato((al.getId() != 0 ? al.getId() : null));
				listAllegati.add(alDTO);							
			}

			dettPratica.setAllegati(listAllegati);

			//Annotazioni
			ArrayList<dmsANNOTAZIONI> listAnnotazioniDms = rootJsonDms.getTypeMetadata().getaNNOTAZIONI();
			for (dmsANNOTAZIONI notaDms : listAnnotazioniDms) {
				NotaDTO notaCross = new NotaDTO();
				notaCross.setTesto(notaDms.getnOTA() != null ? notaDms.getnOTA().toString() : "");

				String dataInserimento = (notaDms.getdATA() != null ? notaDms.getdATA().toString() : "");

				if(dataInserimento != null && !dataInserimento.trim().isEmpty()) {
					java.util.Date dataTemp = null;
					try {
						dataTemp = new SimpleDateFormat("yyyy-MM-dd").parse(dataInserimento);
						java.sql.Date sqlDate = new java.sql.Date(dataTemp.getTime());
						notaCross.setDataInserimento(sqlDate);
					} catch (ParseException e) {
						anagrafica.setDataNascita(null);
					}
				}
				notaCross.setDesUtente(notaDms.getuTENTE() != null ? notaDms.getuTENTE().toString() : "");
				listAnnotazioni.add(notaCross);
			}

			dettPratica.setAnnotazioni(listAnnotazioni);

		} catch (Exception e) {
			e.printStackTrace();
			LogClass.error("Dao: Errore in getDettaglioPraticheById: " + e.getMessage());
		}			

		return dettPratica;		
	}
	/*
	 * AnagraficaDTO
	 * AnagraficaRecatipoDTO
	 * ComuneDTO
	 * ProvinciaDTO
	 * RecapitoDTO
	 */

	public List<PraticaAnagrafica> getListAnagraficheById(String idPratica) throws Exception {
		List<PraticaAnagrafica> listPraticaAnagrafica = new ArrayList<PraticaAnagrafica>();

		try {
			UtilityRestClientClass rest = new UtilityRestClientClass();

			/*
			 *LOR START DEL 			
			 *
			//FileInputStream fstream = new FileInputStream("json/DIA-4001-2002.json");
			FileInputStream fstream = new FileInputStream("json/CIL-1277-2014.json");			
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			StringBuilder sbr = new StringBuilder();
			String jsonResult;
			while ((jsonResult = br.readLine()) != null)   {sbr.append(jsonResult);}
			fstream.close();
			jsonResult = sbr.toString();
			 *
			 *LOR END DEL 			
			 */			
			String jsonResult = rest.GETDmsDettaglioPratica(idPratica);

			LogClass.trace("getListAnagraficheById REQUEST:" + idPratica + " RESPONSE:" + jsonResult);

			it.wego.cross.beans.DettaglioPratica dtp = new it.wego.cross.beans.DettaglioPratica();
			dtp.parser_json(jsonResult);

			int sizeReferenti = dtp.getTypeMetadata().getReferenti().length;
			if(sizeReferenti>0) {
				Referenti[] ref = dtp.getTypeMetadata().getReferenti();
				for (int i = 0; i < sizeReferenti; i++) {
					PraticaAnagrafica praticaAnagrafica = new PraticaAnagrafica();
					Anagrafica anagrafica = new Anagrafica();
					anagrafica.setProvenienza(Costants.DMS);

					anagrafica.setNome(ref[i].getNome().toString());
					anagrafica.setCognome(ref[i].getCognomeDenominazione().toString());
					anagrafica.setDenominazione(ref[i].getCognomeDenominazione().toString());
					anagrafica.setCodiceFiscale(ref[i].getCodiceFiscale().toString());
					anagrafica.setPartitaIva(ref[i].getPartitaIva().toString());	

					LkTipoRuolo lk = new LkTipoRuolo();				
					String cod_ruolo = "999";		
					lk.setIdTipoRuolo(Integer.valueOf(cod_ruolo));
					lk.setCodRuolo(cod_ruolo);				
					lk.setDescrizione(ref[i].getRuolo().toString());				
					praticaAnagrafica.setLkTipoRuolo(lk);

					praticaAnagrafica.setAnagrafica(anagrafica);						
					listPraticaAnagrafica.add(praticaAnagrafica);
				}

			}			
		} catch (Exception e) {
			e.printStackTrace();
			LogClass.error("Dao: Errore in getListAnagraficheById: " + e.getMessage());
		}			
		return listPraticaAnagrafica;		
	}


	public List<DatiCatastali> getListDatiCatastaliById(String idPratica) throws Exception {
		List<DatiCatastali> listDatiCatastali = new ArrayList<DatiCatastali>();

		ObjectMapper om = JsonMapper.builder()
				.enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
				.build();
		try {
			UtilityRestClientClass restGET = new UtilityRestClientClass();
			String urlparam = myUtl.getValueProperties("dms.getPraticaById");
			String jsonResult = restGET.httpGetRestClient(urlparam, idPratica);
			LogClass.trace("getListDatiCatastaliById REQUEST:" + urlparam + idPratica + " RESPONSE:" + jsonResult);
			Root rootJsonDms = om.readValue(jsonResult, Root.class);

			ArrayList<dmsDATICATASTALI> daticatastali = rootJsonDms.getTypeMetadata().getdATI_CATASTALI();
			for (dmsDATICATASTALI dc : daticatastali) {

				DatiCatastali datiCatastali = new DatiCatastali();
				datiCatastali.setProvenienza(Costants.DMS);

				LkTipoParticella idTipoParticella = new LkTipoParticella();
				idTipoParticella.setIdTipoParticella(0);
				idTipoParticella.setDescrizione((dc.getpARTICELLA() != null ? dc.getpARTICELLA().toString() : ""));			
				datiCatastali.setIdTipoParticella(idTipoParticella);

				datiCatastali.setFoglio((dc.getfOGLIO() != null ? dc.getfOGLIO().toString() : ""));			
				datiCatastali.setMappale((dc.getfOGLIO() != null ? dc.getfOGLIO().toString() : ""));			
				datiCatastali.setSezione((dc.getsEZIONE() != null ? dc.getsEZIONE().toString() : ""));			

				LkTipoUnita idTipoUnita = new LkTipoUnita();
				idTipoUnita.setIdTipoUnita(0);
				idTipoUnita.setDescrizione("");
				datiCatastali.setIdTipoUnita(idTipoUnita);

				//datiCatastali.setEstensioneParticella(rs.getString("estensioneParticella"));
				datiCatastali.setSubalterno((dc.getsUBALTERNO() != null ? dc.getsUBALTERNO().toString() : ""));			
				//datiCatastali.setComuneCensuario(rs.getString("comuneCensuario"));
				datiCatastali.setProvenienza(Costants.DMS);

				listDatiCatastali.add(datiCatastali);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogClass.error("Dao: Errore in getListDatiCatastaliById: " + e.getMessage());
		}		
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

	public List<IndirizzoInterventoDTO> getListIndirizziIntervento(String idPratica) throws SQLException {
		List<IndirizzoInterventoDTO> listIndirizziIntervento = new ArrayList<IndirizzoInterventoDTO>();

		try {
			UtilityRestClientClass rest = new UtilityRestClientClass();

			/*
			 *LOR START DEL 			
			 *
				FileInputStream fstream = new FileInputStream("json/DIA-42-2002.json");
				//FileInputStream fstream = new FileInputStream("json/CIL-1277-2014.json");			
				BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				StringBuilder sbr = new StringBuilder();
				String jsonResult;
				while ((jsonResult = br.readLine()) != null)   {sbr.append(jsonResult);}
				fstream.close();
				jsonResult = sbr.toString();
			 *
			 *LOR END DEL 			
			 */			
			String jsonResult = rest.GETDmsDettaglioPratica(idPratica);

			LogClass.trace("getListIndirizziIntervento REQUEST:" + " RESPONSE:" + jsonResult);

			it.wego.cross.beans.DettaglioPratica dtp = new it.wego.cross.beans.DettaglioPratica();
			dtp.parser_json(jsonResult);

			int sizeUbicazione = dtp.getTypeMetadata().getUbicazione().length;
			
			if(sizeUbicazione>0) {
				Ubicazione[] refUbicazione = dtp.getTypeMetadata().getUbicazione();

				for (int i = 0; i < sizeUbicazione; i++) {
					IndirizzoInterventoDTO datiIndirizzoInterventoDTO = new IndirizzoInterventoDTO();
					datiIndirizzoInterventoDTO.setProvenienza(Costants.DMS);				
					datiIndirizzoInterventoDTO.setLatitudine((refUbicazione[i].getLatitudine() != null ? refUbicazione[i].getLatitudine().toString() : ""));				
					datiIndirizzoInterventoDTO.setLongitudine((refUbicazione[i].getLongitudine() != null ? refUbicazione[i].getLongitudine().toString() : ""));
					datiIndirizzoInterventoDTO.setCivico((refUbicazione[i].getNumeroCivico()!= null ? refUbicazione[i].getNumeroCivico().toString() : ""));
					datiIndirizzoInterventoDTO.setLocalita((refUbicazione[i].getFrazioneLocalita() != null ? refUbicazione[i].getFrazioneLocalita().toString() : ""));
					datiIndirizzoInterventoDTO.setIndirizzo((refUbicazione[i].getIndirizzoDellaPratica() != null ? refUbicazione[i].getIndirizzoDellaPratica().toString() : ""));
					datiIndirizzoInterventoDTO.setPiano((refUbicazione[i].getPiano() != null ? refUbicazione[i].getPiano().toString() : ""));
					datiIndirizzoInterventoDTO.setInternoNumero((refUbicazione[i].getInterno() != null ? refUbicazione[i].getInterno().toString() : ""));
					datiIndirizzoInterventoDTO.setInternoLettera((refUbicazione[i].getScala() != null ? refUbicazione[i].getScala(): ""));

					listIndirizziIntervento.add(datiIndirizzoInterventoDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogClass.error("Dao: Errore in getListIndirizziIntervento: " + e.getMessage());
		}
		return listIndirizziIntervento;		
	}

	public List<AllegatoDTO> getListAllegatiPratica(String idPratica) throws SQLException {
		List<AllegatoDTO> listAllegati = new ArrayList<AllegatoDTO>();
		String query = "";

		Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CROSS);

		try {
			UtilityRestClientClass rest = new UtilityRestClientClass();
			it.wego.cross.beans.allegati.Allegati allegatiDms = new it.wego.cross.beans.allegati.Allegati();

			String jsonResult="";
			LogClass.trace("getListAllegatiPratica REQUEST:" + idPratica + " RESPONSE:" + jsonResult);									
			jsonResult = rest.GETDmsAllegatiPratica(idPratica);
			allegatiDms.parser_json(jsonResult);

			int sizeUploadInfo = allegatiDms.getUploadInfo().length;
			if(sizeUploadInfo>0) {
				UploadInfo[] ref = allegatiDms.getUploadInfo();
				ArrayList<Allegato> allegati  = new ArrayList<Allegato>();
				for (int i = 0; i < sizeUploadInfo; i++) {
					AllegatoDTO alDTO = new AllegatoDTO();
					alDTO.setProvenienza(Costants.DMS);							
					alDTO.setDescrizione((ref[i].getName() != null ? ref[i].getName().toString() : ""));
					alDTO.setNomeFile((ref[i].getName() != null ? ref[i].getName().toString() : ""));

					String uuid = ref[i].getUUID().toString();
					query = "SELECT idfile FROM allegati_interop_dms WHERE uuid = '?'";
					query = myUtility.setString(query,  "?", String.valueOf(uuid));
					int id_file = dao.queryInt(query, "idfile");
					if(id_file == 0) {

						query = "INSERT INTO `opencross`.`allegati_interop_dms`"
								+ "(`identificativoPratica`,`uuid`,`nome_file`)"
								+ " 	VALUES('?','?','?')";
						query = myUtility.setString(query,  "?", String.valueOf(idPratica));
						query = myUtility.setString(query,  "?", String.valueOf(ref[i].getUUID().toString()));
						query = myUtility.setString(query,  "?", String.valueOf(ref[i].getName().toString()));
						id_file = dao.executeInsert(query);
					}

					// alDTO.setIdFileEsterno((ref[i].getUUID().toString() != null ? ref[i].getUUID().toString() : ""));
					alDTO.setIdAllegato(id_file);
					alDTO.setTipoFile((ref[i].getType().toString() != null ? ref[i].getType().toString() : ""));
					listAllegati.add(alDTO);							
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogClass.error("Dao: Errore in getListIndirizziIntervento: " + e.getMessage());
		}	
		dao.closeConnection();

		return listAllegati;		
	}

	public String getAllegatiById(String idFile) throws SQLException {
		Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CROSS);

		String base64 = "";

		try {
			String fileURL = myUtl.getValueProperties("dms.getFileAllegato");

			UtilityRestClientClass rest = new UtilityRestClientClass();

			String query = "SELECT `uuid` FROM `opencross`.`allegati_interop_dms` where `idfile` = ?";
			query = myUtility.setString(query,  "?", String.valueOf(idFile));
			String uuidFile = dao.queryString(query, "uuid");

			//http://<url>:8888/documentale-content/api/v1/content/5801e5e2-332b-40e1-9edd-32047422918a/content -o ./000210080991_Elaborato_Grafico.PDF
			fileURL = fileURL + "/" + uuidFile + "/content";

			base64 = rest.downloadFile(fileURL);

			LogClass.trace("getAllegatiById REQUEST:" + fileURL + idFile + " RESPONSE:" + base64);


		} catch (Exception e) {
			e.printStackTrace();
			LogClass.error("Dao: Errore in getListIndirizziIntervento: " + e.getMessage());
		}		
		dao.closeConnection();

		return base64;		
	}

	public String addPraticaDMS(String identificativo_pratica) throws Exception {
		String result="OK";
		String jsonResult = "";
		AddPraticaDms add_pratica = new AddPraticaDms();
		String sUrlDms = myUtl.getValueProperties("dms.AddPratica");

		String json_aggiungi_pratica_dms = "";
		try {
			add_pratica.estrae_dati_pratica(identificativo_pratica);
			json_aggiungi_pratica_dms = add_pratica.toStringJson();
			//String sUrl = sUrlDms + "?" + json_aggiungi_pratica_dms;

			jsonResult = UtilityRestClientClass.sendPostRequest(sUrlDms, identificativo_pratica, json_aggiungi_pratica_dms.toString());
			
			// jsonResult = UtilityRestClientClass.sendingPostRequest(sUrlDms,  json_aggiungi_pratica_dms.toString());
						
		} catch (Exception e) {
			jsonResult = UtilityRestClientClass.__HttpRESTFulPOSTDms(sUrlDms,  json_aggiungi_pratica_dms.toString());
			System.out.println("__HttpRESTFulPOSTDms Results." + jsonResult);
			e.printStackTrace();
		}
		return result;
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
		List<IndirizzoInterventoDTO> lst_indint = new ArrayList<IndirizzoInterventoDTO>();;

		Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CIVILIA); 

		try {
			//base64 = dao.getAllegatiById("001");
			lst_indint = dao.getListIndirizziIntervento("1111");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println("Results." + base64);

		dao.closeConnection();
	}

}