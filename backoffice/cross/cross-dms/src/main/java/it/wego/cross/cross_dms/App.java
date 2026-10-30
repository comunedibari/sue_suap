package it.wego.cross.cross_dms;

import it.wego.cross.constants.StatoPratica;
import it.wego.cross.cross_beans.PraticaDMS;

import it.wego.cross.cross_dms.sql.Dao;
import it.wego.cross.cross_dms.sql.MySqlConnect;
import it.wego.cross.cross_dms.sql.PoolConnectionDB;
import it.wego.cross.cross_dms.sql.PraticaCross;
import it.wego.cross.cross_dms.sql.PraticaCrossWS;
import it.wego.cross.cross_dms.sql.PraticheDms;
import it.wego.cross.cross_dms.sql.RootPraticheDms;
import it.wego.cross.cross_dms.sql.TypeMetadata;
import it.wego.cross.cross_dms.sql.crossCOMUNE;
import it.wego.cross.cross_dms.sql.crossDATICATASTALI;
import it.wego.cross.cross_dms.sql.crossPROCEDIMENTOENTE;
import it.wego.cross.cross_dms.sql.crossPROCESSO;
import it.wego.cross.cross_dms.sql.crossRECAPITO;
import it.wego.cross.cross_dms.sql.crossSTATOPRATICA;
import it.wego.cross.cross_dms.sql.crossUTENTE;
import it.wego.cross.cross_dms.sql.dmsDATICATASTALI;
import it.wego.cross.cross_dms.sql.dmsOGGETTO;
import it.wego.cross.cross_dms.sql.dmsPROVVEDIMENTOFINALE;
import it.wego.cross.cross_dms.sql.dmsRECAPITO;
import it.wego.cross.cross_dms.sql.dmsREFERENTI;
import it.wego.cross.cross_dms.sql.dmsSTATOPRATICA;
import it.wego.cross.cross_dms.sql.dmsUBICAZIONE;
import it.wego.cross.cross_dms.utilities.LogClass;
import it.wego.cross.cross_dms.utilities.MyJSONObject;
import it.wego.cross.cross_dms.utilities.MyJSONValue;
import it.wego.cross.cross_dms.utilities.ParserJson;
import it.wego.cross.cross_dms.utilities.UtilityRestClientClass;
import it.wego.cross.cross_dms.utilities.myUtility;
import it.wego.cross.dto.dozer.AnagraficaDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1

import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1

/**
 * Hello world!
 *
 */
public class App 
{
	public static LogClass logger;
	private final static Charset UTF8_CHARSET = Charset.forName("UTF-8");

	private String urlDms;
	private String dms_pratiche;
	private String cross_putPratica;
	private String cross_putDatiCatastali;
	private String cross_login;
	private String cross_credentials;
	private String cross_putAnagrafica;
	private String annoInizioScansionePratiche;

	final static String GETALLPRATICHEDMS = "GETALLPRATICHEDMS";
	final static String GETLISTAPRATICHEDMS = "GETLISTAPRATICHEDMS";

	private myUtility m_util;

	public App(){
		m_util = new myUtility();
	}
	public String getUrlDms() {
		return urlDms;
	}
	public void setUrlDms(String urlDms) {
		this.urlDms = urlDms;
	}
	public String getDms_pratiche() {
		return dms_pratiche;
	}
	public void setDms_pratiche(String dms_pratiche) {
		this.dms_pratiche = dms_pratiche;
	}
	public String getCross_putPratica() {
		return cross_putPratica;
	}
	public void setCross_putPratica(String cross_putPratica) {
		this.cross_putPratica = cross_putPratica;
	}
	public String getCross_putDatiCatastali() {
		return cross_putDatiCatastali;
	}
	public void setCross_putDatiCatastali(String cross_putDatiCatastali) {
		this.cross_putDatiCatastali = cross_putDatiCatastali;
	}
	public String getCross_login() {
		return cross_login;
	}
	public void setCross_login(String cross_login) {
		this.cross_login = cross_login;
	}
	public String getCross_credentials() {
		return cross_credentials;
	}
	public void setCross_credentials(String cross_credentials) {
		this.cross_credentials = cross_credentials;
	}
	public String getCross_putAnagrafica() {
		return cross_putAnagrafica;
	}
	public void setCross_putAnagrafica(String cross_putAnagrafica) {
		this.cross_putAnagrafica = cross_putAnagrafica;
	}
	public myUtility getUtil() {
		return m_util;
	}

	public void setUtil(myUtility util) {
		this.m_util = util;
	}

	public String getAnnoInizioScansionePratiche() {
		return annoInizioScansionePratiche;
	}
	public void setAnnoInizioScansionePratiche(String annoInizioScansionePratiche) {
		this.annoInizioScansionePratiche = annoInizioScansionePratiche;
	}

	void get_all_pratiche_dms(int processo)
	{
		Dao daoCivilia = null;
		Dao daoCross = null;
		int save_offset = 0;
		String save_pratica = "", jsonCrossDataCatastali="", jsonAnagrafica = "", last_rest_call_execution = "", last_rest_response_call="";

		try {
			daoCivilia = new Dao();daoCivilia.openConnection(PoolConnectionDB.DB_CIVILIA); 

			daoCross = new Dao();daoCross.openConnection(PoolConnectionDB.DB_CROSS); 

			UtilityRestClientClass restClientCross = new UtilityRestClientClass();		

			UtilityRestClientClass restClientDms = new UtilityRestClientClass();

			String urlDms = m_util.getValueProperties("dms.getListaPratiche");
			String cross_putPratica = m_util.getValueProperties("cross.putPratica");
			String cross_putDatiCatastali = m_util.getValueProperties("cross.putDatiCatastali");
			String cross_login = m_util.getValueProperties("cross.login");
			String cross_credentials = m_util.getValueProperties("cross.credentials");
			String cross_putAnagrafica = m_util.getValueProperties("cross.putAnagrafica");

			String annoInizioScansionePratiche = m_util.getValueProperties("dms.annoRiferimento");

			int inizio_anno_rif = Integer.valueOf(annoInizioScansionePratiche)+1;

			int fine_anno_rif = Integer.valueOf(daoCross.queryString("SELECT DATE_FORMAT(NOW(), '%Y') AS anno", "anno"));

			int totale_anni_da_elaborare = fine_anno_rif - inizio_anno_rif;

			System.out.println( "Import pratiche DMS inizio_anno_rif: " + inizio_anno_rif + " fine_anno_rif: " + fine_anno_rif);

			System.out.println( " totale_anni_da_elaborare: " + totale_anni_da_elaborare);

			int inizio_range_anni_x_processo = (inizio_anno_rif + ((processo-1) * 40) + 1);

			int fine_range_anni_x_processo = inizio_anno_rif + (processo * 40);

			int limit = Integer.valueOf(m_util.getValueProperties("dms.limit"));

			int offset = 0;

			String parameters="", jsonArrayPraticheDMS="";

			last_rest_call_execution = cross_login + " parameters:" + cross_credentials;

			String tokenAccess = restClientCross.getTokenAccessHttps(cross_login, cross_credentials);		

			last_rest_response_call = " response:" + tokenAccess;

			for (int i = inizio_range_anni_x_processo; i < fine_range_anni_x_processo; i++,offset++) {

				ObjectMapper omDms = new ObjectMapper();

				parameters = "offset=" + offset + "&limit=" + limit;

				last_rest_call_execution = urlDms + " parameters:" + parameters;

				// jsonArrayPraticheDMS = restClientDms.HttpRESTFulGET(urlDms, parameters);

				last_rest_response_call = " response:" + jsonArrayPraticheDMS;

				if(jsonArrayPraticheDMS == null || jsonArrayPraticheDMS.isEmpty()) {break;} //Fine pratiche

				RootPraticheDms rootPraticheDms = omDms.readValue(jsonArrayPraticheDMS, RootPraticheDms.class);

				ArrayList<PraticheDms> praticheDms = rootPraticheDms.getPratiche();

				for(PraticheDms praDMS : praticheDms) {

					/*
					String sIdentificativoPraticaDms = 							
							(praDMS.getTypeMetadata().gettIPO_PRATICA() != null ? praDMS.getTypeMetadata().gettIPO_PRATICA().toString() : "DMS") + 
							"-" +
							(praDMS.getTypeMetadata().getnUMERO() != null ? praDMS.getTypeMetadata().getnUMERO().toString() : "00001") +								
							"-" +
							(praDMS.getTypeMetadata().getaNNO() != 0 ? praDMS.getTypeMetadata().getaNNO() : "2023");
					 */

					String sIdentificativoPraticaDms = (praDMS.getName() != null ? praDMS.getName().toString() : ""); 

					save_pratica = sIdentificativoPraticaDms;

					save_offset = offset;

					//Verifica esistenza Pratica DMS in CROSS   
					int exitsPraticaCross = daoCross.queryInt(
							"SELECT COUNT(1) AS pratica_esistente" + 
									" FROM `opencross`.`pratica`" + 
									" WHERE identificativo_pratica = '" + sIdentificativoPraticaDms + "'","pratica_esistente");

					if(exitsPraticaCross == 0) { 
						//Pratica DMS non esistente in CROSS   

						ArrayList<dmsOGGETTO> lstOggetto = praDMS.getTypeMetadata().getoGGETTO();
						String oggettoPratica = "";
						for(dmsOGGETTO oggetto_pratica : lstOggetto) 
						{
							oggettoPratica = oggetto_pratica.getdESCRIZIONE_INTERVENTO() != null ? oggetto_pratica.getdESCRIZIONE_INTERVENTO().toString() : "";
						}

						String respProcedimento = "";
						ArrayList<dmsREFERENTI> rEFERENTI = praDMS.getTypeMetadata().getrEFERENTI();
						for(dmsREFERENTI respProc : rEFERENTI) {
							respProcedimento = respProc.getcOGNOME_DENOMINAZIONE() != null ? respProc.getcOGNOME_DENOMINAZIONE().toString() : "";
						}

						String dataProtocollazione = "";
						ArrayList<dmsPROVVEDIMENTOFINALE> lstProvvFinale = praDMS.getTypeMetadata().getpROVVEDIMENTO_FINALE(); 
						for(dmsPROVVEDIMENTOFINALE provvFinale : lstProvvFinale) {
							dataProtocollazione = provvFinale.getdATA() != null ? provvFinale.getdATA().toString() : "";
						}

						int anno_riferimento_pratica = praDMS.getTypeMetadata().getaNNO() != 0 ? praDMS.getTypeMetadata().getaNNO() : 2023;
						String protocollo = praDMS.getTypeMetadata().getpROTOCOLLO_DI_ARRIVO() != null ? praDMS.getTypeMetadata().getpROTOCOLLO_DI_ARRIVO().toString() : "";
						String data_presentazione = praDMS.getTypeMetadata().getdATA_DI_PRESENTAZIONE() != null ? praDMS.getTypeMetadata().getdATA_DI_PRESENTAZIONE().toString() : ""; 
						String data_ricezione = praDMS.getTypeMetadata().getdATA_DI_PRESENTAZIONE() != null ? praDMS.getTypeMetadata().getdATA_DI_PRESENTAZIONE().toString() : ""; 


						PraticaCrossWS praCross = new PraticaCrossWS(
								null,                               // "idPratica":,
								sIdentificativoPraticaDms,          // "identificativoPratica"
								oggettoPratica,                     // "oggettoPratica"
								protocollo,                         // "protocollo"
								"1",                                // "vecchioProtocollo"
								"102",                              // "codRegistro"
								anno_riferimento_pratica,           // "annoRiferimento"
								1,                                  // "idPraticaPadre"
								2,                                  // "statoPratica"
								data_presentazione,                 // "dataApertura"
								data_presentazione,                 // "dataChiusura"
								6728,                               // "recapito"
								22,                                 // "processo"
								1,                                  // "utente"
								893,                                // "comune"
								data_ricezione,                     // "dataRicezione
								1,                                  // "idStaging"
								1,                                  // "idModello"
								respProcedimento,                   // "responsabileProcedimento"
								dataProtocollazione,                // "dataProtocollazione"
								"",                                 // "dataVecchiaProtocollazione"
								1,                                  // "giorniSospensione"
								3,                                  // "statoEmail"
								1509,                               // "procedimentoEnte"
								"Ufficio Bari",                     // "ufficioRiferimento"
								"1",                                // "identificativoEsterno"
								"1",                                // "integrazione"
								"",                                 // "dataProtSuap"
								"1",                                // "protSuap"
								1,                                  // "idTipoProcedimentoSuap"
								1);                                 // "idTipoInterventoSuap"

						//Inserimento pratica DMS in CROSS
						ObjectMapper omCross = new ObjectMapper();
						String jsonCross = omCross.writeValueAsString(praCross);
						//String strCross = omCross.writeValueAsString(praCross);						
						//JSONParser parser = new JSONParser();
						//JSONObject jsonCross = (JSONObject) parser.parse(strCross);

						LogClass.trace(" cross_putPratica:" + cross_putPratica + " jsonCross:" + jsonCross);

						last_rest_call_execution = cross_putPratica + " parameters:" + jsonCross;

						String result = restClientCross.HttpPostRestClientBearerAuth(cross_putPratica, jsonCross, tokenAccess);	

						last_rest_response_call = " response:" + result;

						int idPratica = Integer.valueOf(result);

						ArrayList<dmsUBICAZIONE> lstUbicazione = praDMS.getTypeMetadata().getuBICAZIONE();
						ArrayList<dmsDATICATASTALI> lstDatiCatastali = praDMS.getTypeMetadata().getdATI_CATASTALI();

						for(dmsDATICATASTALI dc : lstDatiCatastali) {
							crossDATICATASTALI crossDatiCatastali = new crossDATICATASTALI();	
							int idTipoParticella = 2; //Edificiale 

							int idTipoSistemaCatastale = 1; //ORDINARIO

							int idTipoUnita = 1; //Fabbricati

							crossDatiCatastali.setDatiImmobile(idPratica, idTipoParticella, idTipoSistemaCatastale, idTipoUnita);

							crossDatiCatastali.setSezione(dc.getsEZIONE() != null ? dc.getsEZIONE().toString() : "");

							crossDatiCatastali.setFoglio( dc.getfOGLIO() != null ? dc.getfOGLIO().toString() : "");

							crossDatiCatastali.setMappale(dc.getpARTICELLA() != null ? dc.getpARTICELLA().toString() : "");

							crossDatiCatastali.setEstensioneParticella("");

							crossDatiCatastali.setSubalterno(dc.getsUBALTERNO() != null ? dc.getsUBALTERNO().toString() : "");

							crossDatiCatastali.setComuneCensuario("Bari");

							ArrayList<Object> lstEdificio = praDMS.getTypeMetadata().geteDIFICIO();

							for(Object edificio : lstEdificio) {
								crossDatiCatastali.setCodImmobile(edificio != null ? edificio.toString() : "");
							}

							String categoriaImmobile = "Edificiale";
							crossDatiCatastali.setCategoria(categoriaImmobile);

							ObjectMapper omCrossDataCatastali = new ObjectMapper();
							jsonCrossDataCatastali = omCrossDataCatastali.writeValueAsString(crossDatiCatastali);

							last_rest_call_execution = cross_putDatiCatastali + " parameters:" + jsonCrossDataCatastali;
							result = restClientCross.HttpPostRestClientBearerAuth(cross_putDatiCatastali, jsonCrossDataCatastali, tokenAccess);	
							last_rest_response_call = " response:" + result;

							int idDatiCatastali = Integer.valueOf(result);

						}

						StringBuilder sbAnagrafiche = new StringBuilder();  
						ObjectMapper mapperAnag = new ObjectMapper();
						ArrayList<dmsREFERENTI> lstReferentiAnag = praDMS.getTypeMetadata().getrEFERENTI();
						for(dmsREFERENTI ref : lstReferentiAnag) {
							AnagraficaDTO crossAnag = new AnagraficaDTO();

							String ruolo = (ref.getrUOLO() != null ? ref.getrUOLO().toString() : "");

							String partitaIva = (ref.getpARTITA_IVA() != null ? ref.getpARTITA_IVA().toString() : "");

							if(partitaIva.length()>0 && partitaIva.length()<16) {
								crossAnag.setTipoAnagrafica("G");
							}
							String codiceFiscale = (ref.getcODICE_FISCALE() != null ? ref.getcODICE_FISCALE().toString() : "");

							if(codiceFiscale.length()>0 && codiceFiscale.length()==16) {
								crossAnag.setTipoAnagrafica("F");
							}

							crossAnag.setCognome(ref.getcOGNOME_DENOMINAZIONE() != null ? ref.getcOGNOME_DENOMINAZIONE().toString() :"");	

							crossAnag.setNome(ref.getnOME() != null ? ref.getnOME().toString() : "");

							jsonAnagrafica = mapperAnag.writeValueAsString(crossAnag);

							if(sbAnagrafiche.length()>0) {sbAnagrafiche.append(",");}

							sbAnagrafiche.append(jsonAnagrafica);

							if(sbAnagrafiche.length()>0) {
								last_rest_call_execution = cross_putAnagrafica + " parameters:" + jsonAnagrafica;
								result = restClientCross.HttpPostRestClientBearerAuth(cross_putAnagrafica, jsonAnagrafica, tokenAccess);
								last_rest_response_call = " response:" + result;
								int idAnagrafica = Integer.valueOf(result);
							}
						}	
					}
				}

				//offset = offset + limit; ??????

				System.out.println(i);				
			}

			System.out.println( " DMS inizio processo(" + processo + "): " + inizio_range_anni_x_processo + " fine: " + fine_range_anni_x_processo);

		} catch (Exception e) {

			e.printStackTrace();
			LogClass.error(e.getMessage() + 
					" LAST REST CALL EXECUTION:" + last_rest_call_execution +
					" LAST REST RESPONSE:" + last_rest_response_call);
			String sqlErrorePratica = "INSERT INTO `opencross`.`allineamento_dms` " 
					+ "(`identificativoPratica`," 
					+ "`offset`, "
					+ "`flag_elaborato`, "
					+ "`flag_aggiornato`)"
					+ "VALUES"
					+ "('" + save_pratica + "',"   
					+ save_offset + "," 
					+ "'N'," 
					+ "'N')";
			try {
				daoCross.executeUpdate(sqlErrorePratica);
			} catch (Exception e1) {
				e1.printStackTrace();
				LogClass.error("Errore su:" + sqlErrorePratica + e1.getMessage());
			}

		}
		finally
		{
			daoCivilia.closeConnection();
			daoCross.closeConnection();
		}
	}

	void get_list_pratiche_dms(List<String> lstPraticheDms)
	{
		Dao daoCivilia = null;
		Dao daoCross = null;
		int save_offset = 0;
		String save_pratica = "", jsonCrossDataCatastali="", jsonAnagrafica = "", last_rest_call_execution = "", last_rest_response_call="";
		String pra_tipo_pratica = "", pra_numero_pratica = "", pra_anno_pratica = ""; 

		try {
			daoCivilia = new Dao();daoCivilia.openConnection(PoolConnectionDB.DB_CIVILIA); 

			daoCross = new Dao();daoCross.openConnection(PoolConnectionDB.DB_CROSS); 

			UtilityRestClientClass restClientCross = new UtilityRestClientClass();		

			UtilityRestClientClass restClientDms = new UtilityRestClientClass();

			this.setUrlDms(m_util.getValueProperties("dms.getListaPratiche"));
			this.setDms_pratiche(m_util.getValueProperties("dms.getPraticaById"));
			this.setCross_putPratica(m_util.getValueProperties("cross.putPratica"));
			this.setCross_putDatiCatastali(m_util.getValueProperties("cross.putDatiCatastali"));
			this.setCross_login(m_util.getValueProperties("cross.login"));
			this.setCross_credentials(m_util.getValueProperties("cross.credentials"));
			this.setCross_putAnagrafica(m_util.getValueProperties("cross.putAnagrafica"));			
			this.setAnnoInizioScansionePratiche(m_util.getValueProperties("dms.annoRiferimento"));

			int inizio_anno_rif = Integer.valueOf(annoInizioScansionePratiche)+1;

			int fine_anno_rif = Integer.valueOf(daoCross.queryString("SELECT DATE_FORMAT(NOW(), '%Y') AS anno", "anno"));

			int totale_anni_da_elaborare = fine_anno_rif - inizio_anno_rif;

			System.out.println( "Import pratiche DMS inizio_anno_rif: " + inizio_anno_rif + " fine_anno_rif: " + fine_anno_rif);

			System.out.println( " totale_anni_da_elaborare: " + totale_anni_da_elaborare);

			String parameters="", jsonArrayPraticheDMS="";

			//try (PrintWriter out = new PrintWriter("/home/lorenzo/json/12_Estrazione campione Almaviva.csv")) {

			for (String identificativo_pratica : lstPraticheDms) 
			{
				String sIdentificativoPraticaDms = identificativo_pratica;

				String tmp_pratica[] = sIdentificativoPraticaDms.split("-", 3);
				for(int i=0;i<3;i++) {
					if(i==0) {pra_tipo_pratica = tmp_pratica[0];}
					if(i==1) {pra_numero_pratica = tmp_pratica[1];}
					if(i==2) {pra_anno_pratica = tmp_pratica[2];}
				}
				int anno = Integer.valueOf(pra_anno_pratica).intValue();  
				int numero_pratica = Integer.valueOf(pra_numero_pratica).intValue();  

				JSONObject jsonFilterPraticaDms = new JSONObject(); 			    

				JSONArray arrayPayloadFilter = new JSONArray();

				JSONObject itemPayload_type_id = new JSONObject();
				JSONObject payload_typeid = new JSONObject();
				payload_typeid.put("eq", (int)1480);
				itemPayload_type_id.put("typeId", payload_typeid);
				//System.out.println("typeId:" + itemPayload_type_id.toString());

				JSONObject itemPayload_type_anno_pratica = new JSONObject();			    
				JSONObject payload_anno = new JSONObject();
				payload_anno.put("eq", (int)anno);			    
				itemPayload_type_anno_pratica.put("typeMetadata.ANNO", payload_anno);
				//System.out.println("anno:" + itemPayload_type_anno_pratica.toString());

				JSONObject itemPayload_type_tipo_pratica = new JSONObject();
				JSONObject payload_tipo_pratica = new JSONObject();
				payload_tipo_pratica.put("eq", (String)pra_tipo_pratica);
				itemPayload_type_tipo_pratica.put("typeMetadata.TIPO PRATICA", payload_tipo_pratica);

				JSONObject itemPayload_type_numero_numero = new JSONObject();
				JSONObject payload_numero_pratica = new JSONObject();
				payload_numero_pratica.put("eq", (String)pra_numero_pratica);
				itemPayload_type_numero_numero.put("typeMetadata.NUMERO", payload_numero_pratica);

				arrayPayloadFilter.add(itemPayload_type_id);
				arrayPayloadFilter.add(itemPayload_type_anno_pratica);
				arrayPayloadFilter.add(itemPayload_type_tipo_pratica);
				arrayPayloadFilter.add(itemPayload_type_numero_numero);			    			    

				jsonFilterPraticaDms.put("and", arrayPayloadFilter);

				int offset = 0;

				//String start_json_filter = "offset=0&filter=";
				String start_json_filter = "offset=0";

				parameters = start_json_filter + jsonFilterPraticaDms.toString();

				//System.out.println("Parameters:" + parameters);

				String sUrlDms = this.getDms_pratiche().trim(); 

				last_rest_call_execution = sUrlDms + "?" + parameters;

				System.out.println("Parameters:" + last_rest_call_execution);

				// jsonArrayPraticheDMS = restClientDms.HttpRESTFulGET(urlDms, parameters);
				// jsonArrayPraticheDMS = restClientDms.HttpRESTFulGETDms(sUrlDms, start_json_filter, jsonFilterPraticaDms.toString());
				String url = sUrlDms + "?";
				start_json_filter = start_json_filter + "&filter=";
				jsonArrayPraticheDMS = UtilityRestClientClass.RESTGET(url, "application/json", identificativo_pratica, start_json_filter, jsonFilterPraticaDms.toString());
				last_rest_response_call = jsonArrayPraticheDMS;
				System.out.println("last_rest_response_call:" + jsonArrayPraticheDMS);

				last_rest_response_call = " response:" + jsonArrayPraticheDMS;

				System.out.println("last_rest_response_call:" + last_rest_response_call);

				//LOR if(jsonArrayPraticheDMS == null || jsonArrayPraticheDMS.isEmpty()) {break;} //Fine pratiche

				try (PrintWriter out2 = new PrintWriter("/home/lorenzo/json_response/" + identificativo_pratica + ".json")) {
					out2.println(last_rest_response_call);
				}

				/* LOR
				if(jsonArrayPraticheDMS == null || jsonArrayPraticheDMS.isEmpty()) {break;} //Fine pratiche

				ObjectMapper om = new ObjectMapper();
				PraticaDMS[] rootPraticaDms = om.readValue(last_rest_response_call, PraticaDMS[].class);

				for(PraticaDMS p : rootPraticaDms)
				{
					it.wego.cross.cross_beans.TypeMetadata metaDati = p.getTypeMetadata(); 

				}
				 */
				/* LOR
				 * Verifica esistenza Pratica DMS in CROSS   
				int exitsPraticaCross = daoCross.queryInt(
						"SELECT COUNT(1) AS pratica_esistente" + 
								" FROM `opencross`.`pratica`" + 
								" WHERE identificativo_pratica = '" + sIdentificativoPraticaDms + "'","pratica_esistente");
				 */
			}

		} catch (Exception e) {

			e.printStackTrace();
			LogClass.error(e.getMessage() + 
					" LAST REST CALL EXECUTION:" + last_rest_call_execution +
					" LAST REST RESPONSE:" + last_rest_response_call);
			String sqlErrorePratica = "INSERT INTO `opencross`.`allineamento_dms` " 
					+ "(`identificativoPratica`," 
					+ "`offset`, "
					+ "`flag_elaborato`, "
					+ "`flag_aggiornato`)"
					+ "VALUES"
					+ "('" + save_pratica + "',"   
					+ save_offset + "," 
					+ "'N'," 
					+ "'N')";
			try {
				daoCross.executeUpdate(sqlErrorePratica);
			} catch (Exception e1) {
				e1.printStackTrace();
				LogClass.error("Errore su:" + sqlErrorePratica + e1.getMessage());
			}

		}
		finally
		{
			daoCivilia.closeConnection();
			daoCross.closeConnection();
		}
	}
	/*
	private static void printJsonPath(String path, Object object, Map<String, Object> map) {
	    Object value = "";

	    if (object instanceof JSONObject) {
	        JSONObject jsonObject = (JSONObject) object;
	        for (String key : jsonObject.keySet()) {
	            value = jsonObject.get(key);
	            String newPath = path.isEmpty() ? key : path + "." + key;
	            printJsonPath(newPath, value, map);
	        }
	    } else if (object instanceof JSONArray) {
	        JSONArray jsonArray = (JSONArray) object;
	        for (int i = 0; i < ((CharSequence) jsonArray).length(); i++) {
	            value = jsonArray.get(i);
	            String newPath = path + "[" + i + "]";
	            printJsonPath(newPath, value, map);
	        }
	    } else {
	        if (!path.isEmpty() && path.charAt(0) == '.') {
	            path = path.substring(1);
	        }

	        map.put(path, object);
	    }
	}
	 */	
	void elab_json_dms() {
		
		String dirnameSource = "C:/SUESUAP/GIT/area-vasta/backoffice/cross/AllineamentoCrossDms/json";
		String dirnameDestination = "C:/SUESUAP/GIT/area-vasta/backoffice/cross/AllineamentoCrossDms/estrazionii";

		File folder = new File(dirnameSource);

		File[] listOfFiles = folder.listFiles();

		// this.copyFilesTroncati(dirnameSource, dirnameDestination, listOfFiles);

		try {
			FileOutputStream fos = new FileOutputStream(dirnameDestination + "/estrazioni.csv");

			String headerFileCSV = "identificativo_pratica;codice_tipo_pratica;numero_pratica;anno_pratica;codice_fiscale;partita_iva;cognome_ragione_sociale;nome;tipo_ref";
			//byteArrayStr= new String(Base64.encodeBase64(byteArray));
			fos.write(Base64.decodeBase64(headerFileCSV.getBytes()));

			for (File file : listOfFiles) {
				if (file.isFile()) {
					System.out.println(file.getName());


					byte[] byteArray = new byte[(int) file.length()];

					FileInputStream fileInputStream = new FileInputStream(file);
					fileInputStream.read(byteArray);

					//String content = new String(Base64.encodeBase64(byteArray));
					String content = App.decodeUTF8(byteArray);

					/*
					ObjectMapper om = new ObjectMapper();

					PraticaDMS[] rootPraticaDms = om.readValue(content, PraticaDMS[].class);

					for(PraticaDMS p : rootPraticaDms)
					{
						it.wego.cross.cross_beans.TypeMetadata metaDati = p.getTypeMetadata(); 

					}
					 */


					// myj.readgsonSstring(content);
					/* LOR
					JSONArray array;
					JSONParser parser = new JSONParser();
					array = (JSONArray) parser.parse(content);
					// JSONObject obj = (JSONObject) array.get(0);
					 */

					//ParserJson prJ = new ParserJson(content);


					JSONParser parser = new JSONParser();
					MyJSONObject myj = new MyJSONObject();
					JSONArray array = (JSONArray) parser.parse(content);
					String value="", key="";
					//for (int i=0; i < array.length(); i++) {
					for (int i=0; i < array.size(); i++) {
						JSONObject jTypeMeta = myj.getChildJSONObjectByName((JSONObject) array.get(i), "typeMetadata");

						if(jTypeMeta.containsKey("REFERENTI")) {

							JSONArray jArrayReferenti = ParserJson.getChildJSONObjectArrayByName(jTypeMeta,  "REFERENTI");

							for(int n=0; n<jArrayReferenti.size(); n++ ) { 

								JSONObject referenteObj = (JSONObject)jArrayReferenti.get(i); 

								try{  value = "";  value =  (String)jTypeMeta.get("TIPO PRATICA").toString().replaceAll("[{}]","");} catch(Exception e) {}
								String tipo_pratica = value;

								try{  value = "";  value =  (String)jTypeMeta.get("NUMERO").toString().replaceAll("[{}]","");} catch(Exception e) {}
								String numero_pratica = value;

								try{  value = "";  value =  (String)jTypeMeta.get("ANNO").toString().replaceAll("[{}]","");} catch(Exception e) {}								
								String anno_pratica = value;

								String identificativo_pratica = tipo_pratica + "-" + numero_pratica + "-" + anno_pratica;

								try{  value = "";  value =  (String)referenteObj.get("CODICE FISCALE").toString().replaceAll("[{}]","");} catch(Exception e) {}								
								String codice_fiscale = value;  

								try{  value = "";  value =  (String)referenteObj.get("PARTITA IVA").toString().replaceAll("[{}]","");} catch(Exception e) {}								
								String partita_iva = value;  

								try{  value = "";  value =  (String)referenteObj.get("COGNOME_DENOMINAZIONE").toString().replaceAll("[{}]","");} catch(Exception e) {}								
								String cognome_denominazione = value;  

								try{  value = "";  value =  (String)referenteObj.get("NOME").toString().replaceAll("[{}]","");} catch(Exception e) {}								
								String nome = value;  

								try{  value = "";  value =  (String)referenteObj.get("RUOLO").toString().replaceAll("[{}]","");} catch(Exception e) {}								
								String ruolo = value;  

								String record_csv= new String(identificativo_pratica + ";"+tipo_pratica);
								fos.write(Base64.decodeBase64(record_csv.getBytes()));
							}

						}						
					}

				}
			}

			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void copyFilesTroncati(String dirnameSource, String dirnameDestination, File[] listOfFiles)
	{
		for (File file : listOfFiles) {
			if (file.isFile()) {
				System.out.println(file.getName());
				try {
					File sourceFile = new File(dirnameSource + "/" + file.getName());
					File destinationFile = new File(dirnameDestination + "/" + file.getName());

					byte[] byteArray = new byte[(int) sourceFile.length()];

					try {
						FileInputStream fileInputStream = new FileInputStream(sourceFile);
						fileInputStream.read(byteArray);
						FileOutputStream fos = new FileOutputStream(destinationFile);

						String byteArrayStr=null;
						String response = App.decodeUTF8(byteArray); 
						if(response.substring(1, 10).equals("response:")) {
							byte[] bufferTroncato = App.truncate(byteArray, 10);
							response = App.decodeUTF8(bufferTroncato);
							byteArrayStr= new String(Base64.encodeBase64(bufferTroncato));

						} else {
							byteArrayStr= new String(Base64.encodeBase64(byteArray));
						}

						fos.write(Base64.decodeBase64(byteArrayStr.getBytes()));
						fos.close();
					} 
					catch (FileNotFoundException e) {
						System.out.println("File Not Found.");
						e.printStackTrace();
					}
					catch (IOException e1) {
						System.out.println("Error Reading The File.");
						e1.printStackTrace();
					}					
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
	}
	public static String decodeUTF8(byte[] bytes) {
		return new String(bytes, UTF8_CHARSET);
	}

	public static byte[] encodeUTF8(String string) {
		return string.getBytes(UTF8_CHARSET);
	}
	public static byte[] truncate(byte[] array, int newLength) {
		if (array.length < newLength) {
			return array;
		} else {
			byte[] truncated = new byte[(array.length - newLength)];

			//public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
			System.arraycopy(array, newLength, truncated, 0, (array.length - newLength));

			return truncated;
		}
	}
	public static void main( String[] args )
	{
		System.out.println( "Import pratiche DMS" );

		Options options = new Options();

		Option tipoelaborazione = new Option("t", "tipoelaborazione", true, "tipo elaborazione");
		tipoelaborazione.setRequired(true);
		options.addOption(tipoelaborazione);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;  

		try {        	
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("utility-name", options);

			System.exit(1);
		}

		String tipoElab = cmd.getOptionValue("tipoelaborazione").trim();

		System.out.println(tipoElab);

		try {      
			App app = new App();

			if(tipoElab.equalsIgnoreCase("getAllPratiche")) {

				String[] args1 = {GETALLPRATICHEDMS};
				//SchedulerTask.main(args1);
				//ORI Main.main(args1);
				app.get_all_pratiche_dms(0);

			} else if(tipoElab.equalsIgnoreCase("get_list_pratiche_dms")) {

				String[] args1 = {GETLISTAPRATICHEDMS};

				List<String> lista_pratiche = new ArrayList<String>(); 

				MySqlConnect mysqlConn = new MySqlConnect();

				mysqlConn.connect();

				java.sql.Connection conn = mysqlConn.getConnection();

				PreparedStatement ps = conn.prepareStatement("SELECT `identificativo_pratica` FROM `civilia_db`.`estrazione_campione_almaviva`");

				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					lista_pratiche.add(rs.getString("identificativo_pratica").toString());
				}
				rs.close();
				ps.close();
				mysqlConn.disconnect();

				app.get_list_pratiche_dms(lista_pratiche);

			} else {
				//app.elab_json_dms();
				DMSClientDao dao = new DMSClientDao();
				//String[] args1 = {"ELABORAZIONE"};
				dao.main(args);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
