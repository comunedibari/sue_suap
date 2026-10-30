package it.wego.cross.cross_dms;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import it.wego.cross.cross_dms.sql.CrossFilterPratiche;
import it.wego.cross.cross_dms.sql.Dao;
import it.wego.cross.cross_dms.sql.PoolConnectionDB;
import it.wego.cross.cross_dms.sql.Root;
import it.wego.cross.cross_dms.sql.dmsREFERENTI;
import it.wego.cross.cross_dms.utilities.LogClass;
import it.wego.cross.cross_dms.utilities.UtilityRestClientClass;
import it.wego.cross.cross_dms.utilities.myUtility;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.NotaDTO;
import it.wego.cross.dto.PraticaNuova;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaProcedimenti;

@Component
public class DMSClientDao {

	public static LogClass logger;

	public DMSClientDao() {}

	public DettaglioPratica getDettaglioPraticheById(String idPratica){
		DettaglioPratica dtpratica = new DettaglioPratica();
		/** LOR	
		Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CIVILIA); 
		
		DettaglioPratica dtpratica = null;

		try {
			dtpratica = dao.getDettaglioPraticheById(idPratica);
		} catch (SQLException e) {
			e.printStackTrace();
			LogClass.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			LogClass.error(e.getMessage());
		}
		
		dao.closeConnection();
		*/
		return dtpratica;		
	}

	public List<PraticaAnagrafica> getListAnagraficheById(String idPratica){

		List<PraticaAnagrafica> praticaAnagrafica = new ArrayList<>();
		Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CIVILIA); 

		try {
			praticaAnagrafica = dao.getListAnagraficheById(idPratica);
		} catch (SQLException e) {
			e.printStackTrace();
			LogClass.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			LogClass.error(e.getMessage());
		}
		
		dao.closeConnection();
		
		return praticaAnagrafica;		
	}

	public List<PraticaProcedimenti> getListEndoprocedimentiById(String idPratica) {
		List<PraticaProcedimenti> listPraticaProcedimenti = new ArrayList<>();
		
		
		/*
		Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CIVILIA); 
		try {
			listPraticaProcedimenti = dao.getListEndoprocedimentiById(idPratica);
		} catch (SQLException e) {
			e.printStackTrace();
			LogClass.error(e.getMessage());
		}
		dao.closeConnection();
		*/
		return listPraticaProcedimenti;	
	}
	
	public List<DatiCatastali> getListDatiCatastaliById(String idPratica) {
		List<DatiCatastali> listDatiCatastali = new ArrayList<>();

/*		
		Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CIVILIA); 		

		try {
			listDatiCatastali = dao.getListDatiCatastaliById(idPratica);
		} catch (SQLException e) {
			e.printStackTrace();
			LogClass.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			LogClass.error(e.getMessage());
		}
		
		dao.closeConnection();
*/		
		return listDatiCatastali;
	
	}
	
	public List<IndirizzoInterventoDTO> getListIndirizziIntervento(String idPratica) {
		List<IndirizzoInterventoDTO> listIndirizziIntervento = new ArrayList<>();
		Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CIVILIA); 
		
		try {
			listIndirizziIntervento = dao.getListIndirizziIntervento(idPratica);
		} catch (SQLException e) {
			e.printStackTrace();
			LogClass.error(e.getMessage());
		}
		
		dao.closeConnection();
		return listIndirizziIntervento;	
	}
	
	public List<AllegatoDTO> getListAllegatiPratica(String idPratica) {
		Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CIVILIA); 

		List<AllegatoDTO> listAllegati = new ArrayList<AllegatoDTO>();

		try {
			listAllegati = dao.getListAllegatiPratica(idPratica);
		} catch (SQLException e) {
			e.printStackTrace();
			LogClass.error(e.getMessage());
		}
		
		dao.closeConnection();
		
		
        for (AllegatoDTO al : listAllegati) {
			LogClass.debug(" LOR ALLEGATI - descrizione:" + al.getDescrizione()
					+ " LOR nomeFile:" + al.getNomeFile()
					+ " LOR tipo file:" + al.getTipoFile()
					);								
		}
		return listAllegati;
	}
	
	public String getAllegatoPratica(String idFile) {
		String base64 = "";
		Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CIVILIA); 

		String b64 = null;

		try {
			base64 = dao.getAllegatiById(idFile);
		} catch (SQLException e) {
			e.printStackTrace();
			LogClass.error(e.getMessage());
		}
		
		dao.closeConnection();
		return base64;
	}
	
	public List<PraticaNuova> ricercaPratiche(Filter crossfiltroRicercaPratiche) {
		ArrayList<PraticaNuova> listaPraticheDms = new ArrayList<PraticaNuova>();
/* LOR
		Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CROSS); 

		LogClass.trace("DMS ricercaPratiche");
		StringBuilder richiedenti = new StringBuilder();
		String richiedente="", beneficiario="",tecnico="",ruolo="",nome="";		
		CrossFilterPratiche crossFilter = new CrossFilterPratiche();
		
		myUtility util = new myUtility();
		
		String jsonFilter = "";
		
		ObjectMapper om = JsonMapper.builder()
			    .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
			    .build();
		try {
			if(crossfiltroRicercaPratiche.getAnnoRiferimento() != null && crossfiltroRicercaPratiche.getAnnoRiferimento()>0) {
				crossFilter.setAnnoRiferimento(crossfiltroRicercaPratiche.getAnnoRiferimento().toString());	
			}
			if(crossfiltroRicercaPratiche.getDataInizio() != null) {
				crossFilter.setDataInizio(crossfiltroRicercaPratiche.getDataInizio().toString());	
			}
			if(crossfiltroRicercaPratiche.getDataFine() != null ) {
				crossFilter.setDataInizio(crossfiltroRicercaPratiche.getDataInizio().toString());	
			}
			if(crossfiltroRicercaPratiche.getFoglio() != null && !crossfiltroRicercaPratiche.getFoglio().isEmpty()) {
				crossFilter.setFoglio(crossfiltroRicercaPratiche.getFoglio().toString());	
			}
			if(crossfiltroRicercaPratiche.getMappale() != null && !crossfiltroRicercaPratiche.getMappale().isEmpty()) {
				crossFilter.setMappale(crossfiltroRicercaPratiche.getMappale().toString());	
			}
			if(crossfiltroRicercaPratiche.getSubalterno() != null && !crossfiltroRicercaPratiche.getSubalterno().isEmpty()) {
				crossFilter.setSubalterno(crossfiltroRicercaPratiche.getSubalterno().toString());	
			}
			if(crossfiltroRicercaPratiche.getNumeroFascicolo() != null && !crossfiltroRicercaPratiche.getNumeroFascicolo().isEmpty()) {
				crossFilter.setNumeroFascicolo(crossfiltroRicercaPratiche.getNumeroFascicolo().toString());	
			}
			if(crossfiltroRicercaPratiche.getOggetto() != null && !crossfiltroRicercaPratiche.getOggetto().isEmpty()) {
				crossFilter.setOggetto(crossfiltroRicercaPratiche.getOggetto().toString());	
			}
			if(crossfiltroRicercaPratiche.getStatoPratica() != null && !crossfiltroRicercaPratiche.getStatoPratica().isEmpty()) {
				crossFilter.setStatoPratica(crossfiltroRicercaPratiche.getStatoPratica().toString());	
			}
			if(crossfiltroRicercaPratiche.getIdentificativoPratica() != null && !crossfiltroRicercaPratiche.getIdentificativoPratica().isEmpty()) {
				crossFilter.setIdentificativoPratica(crossfiltroRicercaPratiche.getIdentificativoPratica().toString());	
			}
			if(crossfiltroRicercaPratiche.getNome() != null && !crossfiltroRicercaPratiche.getNome().isEmpty()) {
				crossFilter.setNome(crossfiltroRicercaPratiche.getNome().toString());	
			}
			if(crossfiltroRicercaPratiche.getCognome() != null && !crossfiltroRicercaPratiche.getCognome().isEmpty()) {
				crossFilter.setCognome(crossfiltroRicercaPratiche.getCognome().toString());	
			}
			if(crossfiltroRicercaPratiche.getDenominazione() != null && !crossfiltroRicercaPratiche.getDenominazione().isEmpty()) {
				crossFilter.setDenominazione(crossfiltroRicercaPratiche.getDenominazione().toString());	
			}
			if(crossfiltroRicercaPratiche.getPartitaIva() != null && !crossfiltroRicercaPratiche.getPartitaIva().isEmpty()) {
				crossFilter.setPartitaIva(crossfiltroRicercaPratiche.getPartitaIva().toString());	
			}
			if(crossfiltroRicercaPratiche.getCodiceFiscale() != null && !crossfiltroRicercaPratiche.getCodiceFiscale().isEmpty()) {
				crossFilter.setCodiceFiscale(crossfiltroRicercaPratiche.getCodiceFiscale().toString());	
			}
			if(crossfiltroRicercaPratiche.getIndirizzo() != null && !crossfiltroRicercaPratiche.getIndirizzo().isEmpty()) {
				crossFilter.setIndirizzo(crossfiltroRicercaPratiche.getIndirizzo().toString());	
			}
			if(crossfiltroRicercaPratiche.getCivico() != null && !crossfiltroRicercaPratiche.getCivico().isEmpty()) {
				crossFilter.setCivico(crossfiltroRicercaPratiche.getCivico().toString());	
			}

			jsonFilter = crossFilter.to_json();

			LogClass.trace("DMS ricercaPratiche filtro ricerca: " + jsonFilter);

			jsonFilter = util.MD5(jsonFilter);
			
			UtilityRestClientClass restGET = new UtilityRestClientClass();
			
			String urlparam = util.getValueProperties("dms.RicercaPratiche");
			
			String limit = util.getValueProperties("dms.limit");
			
			String jsonParam = "filter=" + jsonFilter + "&offset=1&limit=" + limit + "&sort=cognome"; 
					
			String jsonResult = restGET.HttpRESTFulGET(urlparam, jsonParam);
			
			//Root rootJsonDms = om.readValue(jsonResult, Root.class);
			//dmsDETTAGLIOPRATICHE rootJsonDms = om.readValue(jsonResult, dmsDETTAGLIOPRATICHE.class);
			Root dmsDettPratica = om.readValue(jsonResult, Root.class);
			
			//for(Root dmsDettPratica : rootJsonDms.getDettPratiche())
			//{
				//String idPratica = rootJsonDms.getId();
				
				//DettaglioPratica dt = dao.getDettaglioPraticheById(idPratica);
				
				PraticaNuova praNuova = new PraticaNuova();
				
				java.util.Date dataTemp = null;
				try {
					String tmpDataRicezione = dmsDettPratica.getTypeMetadata().getdATA_DI_PRESENTAZIONE() != null ? 
							dmsDettPratica.getTypeMetadata().getdATA_DI_PRESENTAZIONE().toString() : "31/05/2023";
					String formato_data = myUtility.get_format_date(tmpDataRicezione);
					dataTemp = new SimpleDateFormat(formato_data).parse(dmsDettPratica.getTypeMetadata().getdATA_DI_PRESENTAZIONE().toString());
					java.sql.Date sqlDate = new java.sql.Date(dataTemp.getTime());
					praNuova.setDataRicezione(sqlDate);
				} catch (ParseException e) {
					praNuova.setDataRicezione(null);
				}
								
				String query = "SELECT `descrizione` FROM `opencross`.`lk_stato_pratica` WHERE id_stato_pratica = 2 LIMIT 0, 1";
				String descrStatoPratica = dao.queryString(query, "descrizione");
				praNuova.setStatoPratica(descrStatoPratica);
				
				praNuova.setIdentificativoPratica(dmsDettPratica.getName() != null ? dmsDettPratica.getName().toString() : "");
				praNuova.setDescPratica(dmsDettPratica.getTypeMetadata().gettIPO_PRATICA() != null ? 
						dmsDettPratica.getTypeMetadata().gettIPO_PRATICA().toString() : "");
				praNuova.setFascicolo(dmsDettPratica.getTypeMetadata().getfASCICOLO() != null ? 
						dmsDettPratica.getTypeMetadata().getfASCICOLO().toString() : "");
				praNuova.setOggettoPratica(
						dmsDettPratica.getTypeMetadata().getoGGETTO().get(0).getdESCRIZIONE_INTERVENTO() != null ?
						dmsDettPratica.getTypeMetadata().getoGGETTO().get(0).getdESCRIZIONE_INTERVENTO().toString() : "");
				praNuova.setComune(dmsDettPratica.getTypeMetadata().getuBICAZIONE().get(0).getfRAZIONE_LOCALITA() != null ?
						dmsDettPratica.getTypeMetadata().getuBICAZIONE().get(0).getfRAZIONE_LOCALITA().toString() : "");
				
				query = "SELECT `descrizione` FROM `opencross`.`enti` WHERE id_ente = 1001 LIMIT 0, 1";
				String descrEnte = dao.queryString(query, "descrizione");								
				praNuova.setEnte(descrEnte);
				
				query = "SELECT 	CONCAT(`cognome`,' ',`nome`) AS utente FROM `opencross`.`utente` WHERE id_utente = 1";
				String utenteIncaricato = dao.queryString(query, "utente");
				praNuova.setCodiceUtente(1);
				praNuova.setInCarico(utenteIncaricato);
				
				// R: SANTANGELO MARIA ANGELA<br />B: SANTANGELO MARIA ANGELA<br />TP: VERDI MARIO<br />  
				for(dmsREFERENTI ref : dmsDettPratica.getTypeMetadata().getrEFERENTI()) {

					ruolo = (ref.getrUOLO() != null ? ref.getrUOLO().toString() : "");
					
					if(myUtility.findInString("richiedent", ruolo)) {
						richiedente = (ref.getcOGNOME_DENOMINAZIONE()!=null ? ref.getcOGNOME_DENOMINAZIONE().toString() : "");
						nome = (ref.getnOME()!=null ? ref.getnOME().toString() : "");
						if(richiedenti.length()>0){richiedenti.append("<br />");}
						richiedenti.append("R: " + richiedente + " " + nome);
					}
							
					if(myUtility.findInString("beneficiari", ruolo)) {
						beneficiario = (ref.getcOGNOME_DENOMINAZIONE()!=null ? ref.getcOGNOME_DENOMINAZIONE().toString() : "");
						nome = (ref.getnOME()!=null ? ref.getnOME().toString() : "");
						if(richiedenti.length()>0){richiedenti.append("<br />");}
						richiedenti.append("B: " + beneficiario + " " + nome);
					}
					if(myUtility.findInString("tecnico", ruolo)) {
						tecnico = (ref.getcOGNOME_DENOMINAZIONE()!=null ? ref.getcOGNOME_DENOMINAZIONE().toString() : "");
						nome = (ref.getnOME()!=null ? ref.getnOME().toString() : "");
						if(richiedenti.length()>0){richiedenti.append("<br />");}
						richiedenti.append("TP: " + tecnico + " " + nome);
					}
				}
				praNuova.setRichiedente(richiedenti.toString());
				
				listaPraticheDms.add(praNuova);
			//}
		} catch (Exception e) {
			e.printStackTrace();
			LogClass.error(e.getMessage());
		} 
		
		dao.closeConnection();
*/		
		return listaPraticheDms;
	}
		
	public String AddPratica(String identificativo_pratica) throws Exception {
		String result="OK";

		myUtility util = new myUtility();
		Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CIVILIA); 

		try {
			result = dao.addPraticaDMS(identificativo_pratica);
		} catch (SQLException e) {
			result="AddPratica ERRORE:" + e.getMessage();
			e.printStackTrace();
			LogClass.error(e.getMessage());
		}
		
		dao.closeConnection();

		return result;
	}
	public static void main(String[] args) {
		DMSClientDao dmsMapper = new DMSClientDao();
		String idPratica = "DIA-4001-2002";

		Filter filtroCross = new Filter();
		filtroCross.setAnnoRiferimento(2015);
		filtroCross.setCodiceFiscale("01234567891");
		filtroCross.setCognome("Geom. Latrofa Pietro");
		
		// List<PraticaNuova> pn = dmsMapper.ricercaPratiche(filtroCross);
		
		/*  DettaglioPratica dtp = dmsMapper.getDettaglioPraticheById(idPratica); 		
        for (PraticaAnagrafica pa : dtp.getPratica()) {
			
			System.out.println("DETT-> ANAGRAFICA NOME:" + pa.getAnagrafica().getNome() 
					+ " DETT-> COGNOME:" + pa.getAnagrafica().getCognome()
					+ " DETT-> CODICE FISCALE:" + pa.getAnagrafica().getCodiceFiscale()
					+ " DETT-> DENOMINAZIONE:" + pa.getAnagrafica().getDenominazione()
					+ " DETT-> P.IVA:" + pa.getAnagrafica().getPartitaIva()						
					+ " DETT-> TIPOANAGRAFICA:"  + pa.getAnagrafica().getTipoAnagrafica()
					+ " DETT-> VARIANTE ANAGRAFICA:"  + pa.getAnagrafica().getVarianteAnagrafica()
					+ " DETT-> ID ANAGRAFICA:"  + pa.getAnagrafica().getIdAnagrafica()
					+ " DETT-> PROVENIENZA:"  + pa.getAnagrafica().getProvenienza()
					+ " DETT-> FLG INDIVIDUALE:"  + pa.getFlgDittaIndividuale() 
					+ " DETT-> RUOLO:" + pa.getLkTipoRuolo().getDescrizione() );								
		}
		*/
        /*
        for (DatiCatastali dt : dtp.getDaticatastali()) {
        	System.out.println(" DATI CATASTALI - CATEGORIA:" + dt.getCategoria()
					+ " DETT-> tipoParticella:" + dt.getIdTipoParticella().getDescrizione()
					+ " DETT-> foglio:" + dt.getFoglio()
					+ " DETT-> mappale:" + dt.getMappale()
					+ " DETT-> sezione:" + dt.getSezione()						
					+ " DETT-> tipoUnita:"  + dt.getIdTipoUnita().getDescrizione()
					+ " DETT-> estensioneParticella:"  + dt.getEstensioneParticella()
					+ " DETT-> subalterno:"  + dt.getSubalterno()
					+ " DETT-> comuneCensuario:"  + dt.getComuneCensuario());								
		}
        for (NotaDTO note : dtp.getAnnotazioni()) {
        	System.out.println(" DETT-> ANNOTAZIONI:"  					
							+ " DETT-> idNota             :" + note.getIdNota() 
							+ " DETT-> idUtente           :" + note.getIdUtente()
							+ " DETT-> dataInserimento    :" + note.getDataInserimento()
							+ " DETT-> testo              :" + note.getTesto()
							+ " DETT-> desUtente          :" + note.getDesUtente()
							+ " DETT-> testoBreve         :" + note.getTestoBreve());								
		}*
        
        for (AllegatoDTO allegato : dtp.getAllegati()) {
        	System.out.println(" DETT-> ALLEGATI:"  					
							+ " DETT-> NomeFile    :" + allegato.getNomeFile() 
							+ " DETT-> NomeFileB64 :" + allegato.getNomeFileB64()
							+ " DETT-> Descrizione :" + allegato.getDescrizione()
							+ " DETT-> PathFile    :" + allegato.getPathFile()
							+ " DETT-> TipoFile    :" + allegato.getTipoFile()
							+ " DETT-> IdAllegato  :" + allegato.getIdAllegato());								
		}
		*/
        Pratica pratica = new Pratica();

/**/        
		
        List<PraticaAnagrafica> praticaAnagraficaList = new ArrayList<PraticaAnagrafica>();
        //List<PraticaAnagrafica> praticaAnagraficaCross = new ArrayList<PraticaAnagrafica>();
        List<PraticaAnagrafica> praticaAnagraficaDms = dmsMapper.getListAnagraficheById(idPratica);
        //praticaAnagraficaList.addAll(praticaAnagraficaCross);
        praticaAnagraficaList.addAll(praticaAnagraficaDms);

        for (PraticaAnagrafica pa : praticaAnagraficaList) {
						
			LogClass.debug("ANAGRAFICA NOME:" + pa.getAnagrafica().getNome() 
					+ " COGNOME:" + pa.getAnagrafica().getCognome()
					+ " CODICE FISCALE:" + pa.getAnagrafica().getCodiceFiscale()
					+ " DENOMINAZIONE:" + pa.getAnagrafica().getDenominazione()
					+ " P.IVA:" + pa.getAnagrafica().getPartitaIva()						
					+ " TIPOANAGRAFICA:"  + pa.getAnagrafica().getTipoAnagrafica()
					+ " VARIANTE ANAGRAFICA:"  + pa.getAnagrafica().getVarianteAnagrafica()
					+ " ID ANAGRAFICA:"  + pa.getAnagrafica().getIdAnagrafica()
//						+ " PROVENIENZA:"  + pa.getAnagrafica().getProvenienza()
					+ " FLG INDIVIDUALE:"  + pa.getFlgDittaIndividuale() 
					+ " RUOLO:" + pa.getLkTipoRuolo().getDescrizione() );								
		}
/**/		
/*					
        List<DatiCatastali> daticatastaliList = dmsMapper.getListDatiCatastaliById(idPratica);
        for (DatiCatastali dt : daticatastaliList) {
			LogClass.debug(" DATI CATASTALI - CATEGORIA:" + dt.getCategoria()
					+ " tipoParticella:" + dt.getIdTipoParticella().getDescrizione()
					+ " foglio:" + dt.getFoglio()
					+ " mappale:" + dt.getMappale()
					+ " sezione:" + dt.getSezione()						
					+ " tipoUnita:"  + dt.getIdTipoUnita().getDescrizione()
					+ " estensioneParticella:"  + dt.getEstensioneParticella()
					+ " subalterno:"  + dt.getSubalterno()
					+ " comuneCensuario:"  + dt.getComuneCensuario());								
		}
*/		
/*        
        List<PraticaProcedimenti> datiprocedimentiList = dmsMapper.getListEndoprocedimentiById(idPratica);
        for (PraticaProcedimenti dp : datiprocedimentiList) {
			LogClass.debug(" PROCEDIMENTI - descrizione:" + dp.getProcedimenti().getTipoProc()
					+ " Ente:" + dp.getEnti().getDescrizione()
					+ " termini:" + dp.getProcedimenti().getTermini()
					);								
		}
        
*			   

        List<AllegatoDTO> allegatiList = dmsMapper.getListAllegatiPratica(idPratica);
        for (AllegatoDTO al : allegatiList) {
			LogClass.debug(" ALLEGATI - descrizione:" + al.getDescrizione()
					+ " nomeFile:" + al.getNomeFile()
					+ " tipo file:" + al.getTipoFile()
					);								
		}
        
        List<IndirizzoInterventoDTO> listIndirizzoIntervento = dmsMapper.getListIndirizziIntervento(idPratica);
        for (IndirizzoInterventoDTO list : listIndirizzoIntervento) {
     			LogClass.debug(" INDIRIZZO INTERVENTO - descrizione:" + list.getIndirizzo()
     					+ " NUMERO CIVICO:" + list.getCivico()
     					+ " SCALA:" + list.getInternoScala()
     					);								
     		}
	}
*
        String fileid = "5801e5e2-332b-40e1-9edd-32047422918a";
        String uuidWithoutDashes = fileid.replaceAll( "-" , "" );
        //String x = "6CFAFD0DA976088FE05400144FFB4B37";
        
        BigInteger big = new BigInteger(uuidWithoutDashes, 16);
        System.out.println(big);
        
        String output = big.toString();
        System.out.println(output);

		try {
	        Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CROSS);
	        String query = "";
	        query = "SELECT idfile FROM allegati_interop_dms WHERE uuid = '?'";
	        query = myUtility.setString(query,  "?", String.valueOf(fileid));
			int id_file = dao.queryInt(query, "idfile");
			if(id_file == 0) {
				
		        query = "INSERT INTO `opencross`.`allegati_interop_dms`"
		        		+ "(`identificativoPratica`,`uuid`,`nome_file`)"
		        		+ " 	VALUES('?','?','?')";
		        query = myUtility.setString(query,  "?", String.valueOf("DIA-4001-2002"));
		        query = myUtility.setString(query,  "?", String.valueOf("5801e5e2-332b-40e1-9edd-32047422918a"));
		        query = myUtility.setString(query,  "?", String.valueOf("prova"));
		        id_file = dao.executeInsert(query);
			}
	        fileid = String.valueOf(id_file);
			query = "SELECT `uuid` FROM `opencross`.`allegati_interop_dms` where `idfile` = ?";
			query = myUtility.setString(query,  "?", String.valueOf(fileid));

			String uuidFile = dao.queryString(query, "uuid");
			dao.closeConnection();
	        dmsMapper.getAllegatoPratica(uuidFile);
		} catch (SQLException e) {
			e.printStackTrace();
			LogClass.error(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		
        Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CROSS);
        String addpratica_identificativo_pratica = args[1];
        String result = "";
		try {
			result = dmsMapper.AddPratica(addpratica_identificativo_pratica);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        LogClass.debug("ADDPRATICA DMS" 
				+ " RESULT:" + result );			
	}          
}
