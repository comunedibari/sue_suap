package dms.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dms.utilities.LogClass;
import dms.utilities.myUtility;

@RestController
public class DmsController {

	private static final String templateRicerca = "%Pratica%";

/*	
	# getListaPratiche /api/v1/folders?offset={Offset}&filter={Filtro}&limit={Limite}	-- Recupero lista di tutte le pratiche paginate
	
	http://.../api/v1/documents?filter={"Nome":{"eq":"Mario"}}&offset=0&limit=20&sort=+Cognome,+Nome
	-	la ricerca per Cognome, che deve essere diverso da “Bianchi”, con paginazione (prima pagina con max 20 risultati) e ordinamento crescente 
	-   per Cognome e Nome, come segue: 
	
	GET http://.../api/v1/documents?filter= {"Cognome":{"ne": "Bianchi"}}&offset=0&limit=20	
	
	# getPraticaById /api/v1/folders/{idPratica} GET -- Recupero dettaglio di una pratica
	# getAllegatiPratica /api/v1/documents?offset={Offset}&filter={Filtro}&limit={Limite} GET -- Recupero lista di allegati di una pratica
	# getFileAllegato	/api/v1/content/{idDocumento}/content	GET -- carica allegato
	# AddPratica	/api/v1/addPratica	POST --	Inserimento pratica chiusa in CROSS su sistema DMS
	# RicercaPratiche	/api/v1/documents GET -- Ricerca documenti su sistema DMS
*/	
	
	@GetMapping("/getListaPratiche")
	public String getListaPratiche(
	@RequestParam(value = "filtro", defaultValue = "2023") String filtro, 
	@RequestParam(value = "offset", defaultValue = "1") String offset,
	@RequestParam(value = "limit", defaultValue = "20") String limit) 
	{
		LogClass.debug("REQUEST getListaPratiche:" + filtro + "/" + offset + "/" + limit);
		if(Integer.valueOf(offset)>9) {
			return "";
		} else {			
			GetListaPratiche wsGetAllPratiche = new GetListaPratiche(filtro, Integer.valueOf(offset), Integer.valueOf(limit));
			LogClass.debug("RESPONSE getListaPratiche:" +  wsGetAllPratiche.getResponse()); 
			return wsGetAllPratiche.getResponse();
		}
	}
	
	@GetMapping("/getPraticaById")
	public String getPraticaById(
	@RequestParam(value = "idPratica", defaultValue = "PDC-0001-2023") String idPratica) 
	{
		LogClass.debug("REQUEST getPraticaById:" + idPratica); 
		GetDettaglioPratica wsGetDettaglioPratica = new GetDettaglioPratica(idPratica);
		LogClass.debug("RESPONSE getPraticaById:" + wsGetDettaglioPratica.getResponse()); 
		return wsGetDettaglioPratica.getResponse();
	}
	
	@GetMapping("/getAllegatiPratica")
	public String getAllegatiPratica(
	@RequestParam(value = "idPratica", defaultValue = "000-000-0001") String idPratica) 
	{
		LogClass.debug("REQUEST getAllegatiPratica:" + idPratica);
		GetAllegatiPratica wsAllegatoPratica = new GetAllegatiPratica(idPratica);
		LogClass.debug("RESPONSE getAllegatiPratica:" + wsAllegatoPratica.getResponse());
		return wsAllegatoPratica.getResponse();
	}

	@GetMapping("/getFileAllegato")
	public String getFileAllegato (
	@RequestParam(value = "id", defaultValue = "000-000-0001") String id) 
	{
		LogClass.debug("REQUEST getFileAllegato:" + id);
		GetFileAllegato wsAllegatoPratica = new GetFileAllegato(id);
		LogClass.debug("RESPONSE getAllegatiPratica:" + wsAllegatoPratica.getResponse());
		return wsAllegatoPratica.getResponse();
	}

	//@GetMapping("/RicercaPratiche")
	@GetMapping(path = "/RicercaPratiche", produces=MediaType.APPLICATION_JSON_VALUE)
	public String RicercaPratiche(
	@RequestParam(value = "filter", defaultValue = "") String filter,
	@RequestParam(value = "offset", defaultValue = "1") String offset,
	@RequestParam(value = "limit", defaultValue = "20") String limit,
	@RequestParam(value = "sort", defaultValue = "") String sort) 
	{
		String jsonResponse = "";

		LogClass.debug("REQUEST RicercaPratiche:" + filter + "/" + offset + "/" + limit);
		RicercaPratiche wsRicercaPratica = new RicercaPratiche(filter, offset, limit, sort);

		String response = wsRicercaPratica.getResponse();  
		JSONParser jsonParser = new JSONParser();
		try {
			JSONObject jObj = (JSONObject) jsonParser.parse(response);
			jsonResponse = jObj.toJSONString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		LogClass.debug("RESPONSE RicercaPratiche:" + jsonResponse);

		jsonResponse = "";
		return jsonResponse;
	}

	@PostMapping("/AddPratica")
	public String AddPratica(
	@RequestParam(value = "jsonAdd", defaultValue = "") String jsonAdd) 
	{
		LogClass.debug("REQUEST AddPratica:" + jsonAdd);
		AddPratica addpratica = new AddPratica(jsonAdd);
		LogClass.debug("RESPONSE AddPratica:" + addpratica.getResponse());
		return addpratica.getResponse();
	}
}
