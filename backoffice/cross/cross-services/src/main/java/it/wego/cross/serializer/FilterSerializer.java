/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.dto.enums.LogicalOperator;
import it.wego.cross.dto.filters.CadastralAddress;
import it.wego.cross.dto.filters.CadastralAddresses;
import it.wego.cross.dto.filters.CadastralData;
import it.wego.cross.dto.filters.CadastralDataList;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.filters.Identification;
import it.wego.cross.dto.filters.Identifications;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkClassificazioneProcedimento;
import it.wego.cross.entity.LkStatiScadenze;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.LkTipoInterventoSuap;
import it.wego.cross.entity.LkTipoPratica;
import it.wego.cross.entity.LkTipoProcedimentoSuap;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.utils.Utils;

/**
 *
 * @author giuseppe
 */
@Component
public class FilterSerializer {

    @Autowired
    private PraticheService praticheService;
    @Autowired
    private EntiService entiService;
    @Autowired
    private UtentiService utentiService;

    public Filter getSearchFilter(HttpServletRequest request) throws ParseException {
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String protocollo = request.getParameter("search_id_pratica");
        if (!Utils.e(request.getParameter("search_protocollo_pratica"))) {
            protocollo = request.getParameter("search_protocollo_pratica");
        }
        String registerNumberByEvent = request.getParameter("search_protocollo_evento");
        String statoProcedimentoUtente = request.getParameter("search_stato_procedimento_utente");
        String indirizzo = request.getParameter("search_indirizzo");
        String civico = request.getParameter("search_civico");
        String idTipoUnita = request.getParameter("search_tipoUnita");
        String annoRiferimento = request.getParameter("search_annoRiferimento");
        String dataInizioString = request.getParameter("search_data_from");
        String dataFineString = request.getParameter("search_data_to");
        String enteSelezionatoString = request.getParameter("search_ente");
        String enteRiferimento = request.getParameter("search_ente_riferimento");
        String statoPraticaString = request.getParameter("search_stato");
        String ricercaTutti = request.getParameter("search_all");
        String tipologiaScadenza = request.getParameter("search_tipo_scadenza");
        String descrizioneComune = request.getParameter("search_descrizione_comune");
        String codCatastale = request.getParameter("search_codice_catastale");
        String fascicolo = request.getParameter("search_fascicolo");
        String oggetto = request.getParameter("search_oggetto");
        String foglio = request.getParameter("search_foglio");
        String mappale = request.getParameter("search_mappale");
        String subalterno = request.getParameter("search_subalterno");
        String identificativoDocumento = request.getParameter("search_identificativoPraticaParameter");
        String nome = request.getParameter("search_nome");
        String cognome = request.getParameter("search_cognome");
        String denominazione = request.getParameter("search_denominazione");
        String partitaIva = request.getParameter("search_partita_iva");
        String codiceFiscale = request.getParameter("search_codice_fiscale");
        String tipoAnagrafica = request.getParameter("search_tipo_anagrafica");
        String ricercaAnagraficaCF = request.getParameter("ricercaAnagraficaCF");       
        String ricercaAnagraficaNome = request.getParameter("ricercaAnagraficaNome");
        String ricercaProcedimento = request.getParameter("procedimento");
        String tipoProcedimento = request.getParameter("tipoProcedimento");
        String ricercaStatoProcedimento = request.getParameter("statoProcedimento");
        String idComune = request.getParameter("search_id_comune");
        String desComune = request.getParameter("search_des_comune");
        String idProcedimento = request.getParameter("search_idProcedimento");
        String estensioneParticella = request.getParameter("search_estensioneParticella");
        String idTipoSistemaCatastale = request.getParameter("search_tipoSistemaCatastale");
        String idTipoParticella = request.getParameter("search_tipoParticella");
        String sezione = request.getParameter("search_sezione");
        String comuneCensuario = request.getParameter("search_comuneCensuario");
        String identificativoPratica = request.getParameter("search_identificativo_pratica");
        String idPratica = request.getParameter("search_idpratica");
        String idOperatoreSelezionato = request.getParameter("search_operatore");
        String protocolloSuap = request.getParameter("search_protocollo_suap");
        String tipoProcedimentoSUAPString = request.getParameter("search_tipoProcedimentoSUAP");
        String tipoInterventoSUAPString = request.getParameter("search_tipoInterventoSUAP");
        String dataInizioProtSuapString = request.getParameter("search_data_inizio_prot_suap");
        String dataFineProtSuapString = request.getParameter("search_data_fine_prot_suap");
        String classificazioneProcedimentoString = request.getParameter("search_tipoClassificazioneProcedimento");
        String idTipoPratica = request.getParameter("search_tipo_pratica");
        String annoPratica = request.getParameter("search_anno_pratica");
        String numeroPratica = request.getParameter("search_numero_pratica");
        String interventionDescription = request.getParameter("search_descrizione_intervento");
        
        String tree = request.getParameter("search_tree");
        
        
        Filter filter = new Filter();
        filter.setConnectedUser(utenteConnesso);
        Date dataInizio = null;
        if (!Utils.e(dataInizioString)) {
            dataInizio = df.parse(dataInizioString);
        }
        filter.setDataInizio(dataInizio);
        LkStatiScadenze scadenza = null;
        if (!Utils.e(tipologiaScadenza)) {
            scadenza = praticheService.findStatoScadenzaById(tipologiaScadenza);

        }
        filter.setIdStatoScadenza(scadenza);
        Date dataFine = null;
        if (!Utils.e(dataFineString)) {
            dataFine = df.parse(dataFineString);
        }
        filter.setDataFine(dataFine);
        Enti enteSelezionato = null;
        if (!Utils.e(enteSelezionatoString)) {
            Integer idEnte = Integer.valueOf(enteSelezionatoString);
            enteSelezionato = entiService.findByIdEnte(idEnte);
        }
        filter.setEnteSelezionato(enteSelezionato);
        LkStatoPratica statoPratica = null;
        if (!Utils.e(statoPraticaString)) {
            if (Utils.isInteger(statoPraticaString)) {
                Integer idStatoPratica = Integer.valueOf(statoPraticaString);
                statoPratica = praticheService.findLookupStatoPratica(idStatoPratica);
            } else {
                filter.setDesStatoPratica(statoPraticaString);
            }
        }
        filter.setIdStatoPratica(statoPratica);
        if (!Utils.e(protocollo)) {
            filter.setNumeroProtocollo(protocollo);
        }
        if (!Utils.e(registerNumberByEvent)) {
        	filter.setRegisterNumberByEvent(registerNumberByEvent);
        }
        boolean filtraUtenteConnesso = true;
        if (ricercaTutti != null && "OK".equalsIgnoreCase(ricercaTutti)) {
            filtraUtenteConnesso = false;
        }
        filter.setSearchByUtenteConnesso(filtraUtenteConnesso);
        if (!Utils.e(descrizioneComune)) {
            filter.setDescrizioneComune(descrizioneComune);
        }
        if (!Utils.e(codCatastale)) {
            filter.setCodiceCatastale(codCatastale);
        }
        if (!Utils.e(fascicolo)) {
            filter.setNumeroFascicolo(fascicolo);
        }
        if (!Utils.e(oggetto)) {
            filter.setOggetto(oggetto);
        }
        if (!Utils.e(identificativoDocumento)) {
            filter.setIdentificativoPratica(identificativoDocumento);
        }
        if (!Utils.e(nome)) {
            filter.setNome(nome);
        }
        if (!Utils.e(cognome)) {
            filter.setCognome(cognome);
        }
        if (!Utils.e(denominazione)) {
            filter.setDenominazione(denominazione);
        }
        if (!Utils.e(codiceFiscale)) {
            filter.setCodiceFiscale(codiceFiscale);
        }
        if (!Utils.e(partitaIva)) {
            filter.setPartitaIva(partitaIva);
        }
        if (!Utils.e(statoProcedimentoUtente)) {
            filter.setStatoProcedimentoUtente(statoProcedimentoUtente);
        }
        if (!Utils.e(tipoAnagrafica)) {
            filter.setTipoAnagrafica(tipoAnagrafica);
        }
        if (!Utils.e(ricercaAnagraficaCF)) {
            filter.setRicercaAnagraficaCF(ricercaAnagraficaCF);
        }
        if (!Utils.e(ricercaAnagraficaNome)) {
            filter.setRicercaAnagraficaNome(ricercaAnagraficaNome);
        }
        if (!Utils.e(foglio)) {
            filter.setFoglio(foglio);
        }
        if (!Utils.e(mappale)) {
            filter.setMappale(mappale);
        }
        if (!Utils.e(subalterno)) {
            filter.setSubalterno(subalterno);
        }
        if (!Utils.e(ricercaProcedimento)) {
            filter.setProcedimento(ricercaProcedimento);
        }
        if (!Utils.e(tipoProcedimento)) {
            filter.setTipoProcedimento(tipoProcedimento);
        }
        if (!Utils.e(ricercaStatoProcedimento)) {
            filter.setStatoProcedimento(ricercaStatoProcedimento);
        }
        if (!Utils.e(idComune)) {
            filter.setIdComune(Integer.valueOf(idComune));
        }
        if (!Utils.e(desComune)) {
            filter.setDesComune(desComune);
        }
        if (!Utils.e(idProcedimento)) {
            filter.setIdProcedimento(Integer.parseInt(idProcedimento));
        }
        if (!Utils.e(indirizzo)) {
            filter.setIndirizzo(indirizzo);
        }
        if (!Utils.e(civico)) {
            filter.setCivico(civico);
        }
        if (!Utils.e(idTipoUnita)) {
            filter.setIdTipoUnita(Integer.parseInt(idTipoUnita));
        }
        if (!Utils.e(idTipoSistemaCatastale)) {
            filter.setIdTipoSistemaCatastale(Integer.parseInt(idTipoSistemaCatastale));
        }
        if (!Utils.e(idTipoParticella)) {
            filter.setIdTipoParticella(Integer.parseInt(idTipoParticella));
        }
        if (!Utils.e(estensioneParticella)) {
            filter.setEstensioneParticella(estensioneParticella);
        }
        if (!Utils.e(sezione)) {
            filter.setSezione(sezione);
        }
        if (!Utils.e(comuneCensuario)) {
            filter.setComuneCensuario(comuneCensuario);
        }
        if (!Utils.e(annoRiferimento)) {
            filter.setAnnoRiferimento(Integer.parseInt(annoRiferimento));
        }
        if (!Utils.e(enteRiferimento)) {
            filter.setAnnoRiferimento(Integer.parseInt(enteRiferimento));
        }
        if (!Utils.e(identificativoPratica)) {
            filter.setIdentificativoPratica(identificativoPratica);
        }
        if (!Utils.e(idPratica)) {
            filter.setIdPratica(Integer.parseInt(idPratica)); 
        }
        if (!Utils.e(idOperatoreSelezionato)) {
            filter.setIdOperatoreSelezionato(Integer.parseInt(idOperatoreSelezionato)); 
        }
        if (!Utils.e(protocolloSuap)) {
            filter.setProtocolloSuap(protocolloSuap); 
        }
        LkTipoProcedimentoSuap procedimentoSUAP = null;
        if (!Utils.e(tipoProcedimentoSUAPString)) {
            if (Utils.isInteger(tipoProcedimentoSUAPString)) {
                Integer idProcedimentoSUAP = Integer.valueOf(tipoProcedimentoSUAPString);
                procedimentoSUAP = praticheService.findLookupProcedimentoSUAP(idProcedimentoSUAP);
            } else {
                filter.setDesProcedimentoSuap(tipoProcedimentoSUAPString);
            }
        }
        filter.setIdTipoProcedimentoSuap(procedimentoSUAP);
        
        LkTipoInterventoSuap tipoInterventoSUAP = null;
        if (!Utils.e(tipoInterventoSUAPString)) {
            if (Utils.isInteger(tipoInterventoSUAPString)) {
                Integer idInterventoSUAP = Integer.valueOf(tipoInterventoSUAPString);
                tipoInterventoSUAP = praticheService.findLookupInterventoSUAP(idInterventoSUAP);
            } else {
                filter.setDesProcedimentoSuap(tipoProcedimentoSUAPString);
            }
        }
        filter.setIdTipoInterventoSuap(tipoInterventoSUAP);
        Date dataInizioprotsuap = null;
        if (!Utils.e(dataInizioProtSuapString)) {
        	dataInizioprotsuap = df.parse(dataInizioProtSuapString);
        }
        filter.setDataInizioProtSuap(dataInizioprotsuap);
        
        Date dataFineprotsuap = null;
        if (!Utils.e(dataFineProtSuapString)) {
        	dataFineprotsuap = df.parse(dataFineProtSuapString);
        }
        filter.setDataFineProtSuap(dataFineprotsuap);
        LkClassificazioneProcedimento classificazioneProcedimento = null;
        if (!Utils.e(classificazioneProcedimentoString)) {
            if (Utils.isInteger(classificazioneProcedimentoString)) {
                Integer idClassificazioneProcedimento = Integer.valueOf(classificazioneProcedimentoString);
                classificazioneProcedimento = praticheService.findLookupClassificazioneProcedimento(idClassificazioneProcedimento);
            } else {
                filter.setDesClassificazioneProcedimento(classificazioneProcedimentoString);
            }
        }
        filter.setIdClassificazioneProcedimento(classificazioneProcedimento);
        
        String[] arrayTree = null;
        if(tree!=null && !tree.isEmpty()) {
        	arrayTree = tree.split(",");
        }
        
        if(arrayTree!=null && arrayTree.length > 0) {
        	filter.setListTree(Arrays.asList(arrayTree));
        }
        
        Identifications identifications = extractAnagrafiche(request);
        if(identifications != null) {
        	filter.setIdentifications(identifications);
        }
        
        CadastralDataList cadastralDataList = extractCadastralDataList(request);
        if(cadastralDataList != null) {
        	filter.setCadastralDataList(cadastralDataList);
        }
        
        CadastralAddresses cadastralAddresses = extractCadastralAddresses(request);
        if(cadastralAddresses != null) {
        	filter.setCadastralAddresses(cadastralAddresses);
        }
        
        if (!Utils.e(idTipoPratica) && Utils.isInteger(idTipoPratica)) {
        	LkTipoPratica tipoPratica = praticheService.findLookupTipoPratica(Integer.valueOf(idTipoPratica));
        	filter.setIdTipoPratica(tipoPratica);
        }

        if (!Utils.e(annoPratica) && Utils.isInteger(annoPratica)) {
        	filter.setAnnoPratica(Integer.parseInt(annoPratica));
        }

        if (!Utils.e(numeroPratica) && Utils.isInteger(numeroPratica)) {
        	filter.setNumeroPratica(Integer.parseInt(numeroPratica));
        }

        if (!Utils.e(interventionDescription)) {
        	filter.setInterventionDescription(interventionDescription);
        }

        return filter;
    }

    public static Filter getSearchFilter(JqgridPaginator paginator) throws ParseException {
        Filter filter = new Filter();
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        return filter;
    }
    
    private Identifications extractAnagrafiche(HttpServletRequest request) {
    	List<Identification> identifications = new ArrayList<>();

    	String logicalOperator = request.getParameter("search_flag_anagrafica");
    	String[] ricercaAnagraficaCFs = request.getParameterValues("search_ricercaAnagraficaCF");
        String[] ricercaAnagraficaNames = request.getParameterValues("search_ricercaAnagraficaNome");
        String[] ricercaAnagraficaSurnames = request.getParameterValues("search_ricercaAnagraficaCognome");
        String[] ricercaAnagraficaCompanyNames = request.getParameterValues("search_ricercaAnagraficaRagioneSociale");
        
        int length = getMaxLength(ricercaAnagraficaCFs, ricercaAnagraficaNames, ricercaAnagraficaSurnames, ricercaAnagraficaCompanyNames);
        
        for(int i=0; i < length; i++) {
        	String ricercaAnagraficaCF = getValue(ricercaAnagraficaCFs, i);
            String ricercaAnagraficaName = getValue(ricercaAnagraficaNames, i);
            String ricercaAnagraficaSurname = getValue(ricercaAnagraficaSurnames ,i);
            String ricercaAnagraficaCompanyName = getValue(ricercaAnagraficaCompanyNames ,i);
            if(checkAtLeastOneNotNull(ricercaAnagraficaCF, ricercaAnagraficaName, ricercaAnagraficaSurname, ricercaAnagraficaCompanyName))
            	identifications.add(new Identification(ricercaAnagraficaCF, ricercaAnagraficaSurname, ricercaAnagraficaName, ricercaAnagraficaCompanyName));
        }
        return identifications.isEmpty()? null : new Identifications(LogicalOperator.fromString(logicalOperator == null? LogicalOperator.AND.getValue(): logicalOperator), identifications);
    }
    
    private CadastralDataList extractCadastralDataList(HttpServletRequest request){
    	List<CadastralData> cadastralDataList = new ArrayList<>();

    	String logicalOperator = request.getParameter("search_flag_catasto");
    	String[] unitTypeIds= request.getParameterValues("search_tipoUnita");
    	String[] maps = request.getParameterValues("search_mappale");
        String[] subordinates = request.getParameterValues("search_subalterno");
        String[] sections = request.getParameterValues("search_sezione");
        String[] papers = request.getParameterValues("search_foglio");
        
        int length = getMaxLength(unitTypeIds, maps, subordinates, sections, papers);
        
        for(int i=0; i < length; i++) {

        	String unitTypeId = getValue(unitTypeIds, i);
        	String map = getValue(maps, i);
            String subordinate = getValue(subordinates, i);
            String section = getValue(sections, i);
            String sheet = getValue(papers, i);
            
            if(checkAtLeastOneNotNull(unitTypeId, map, subordinate, section, sheet))	
            	cadastralDataList.add(new CadastralData()
            			.setUnitTypeId(unitTypeId)
            			.setMap(map)
            			.setSubordinate(subordinate)
            			.setSection(section)
            			.setSheet(sheet));
        }
        return cadastralDataList.isEmpty()? null : new CadastralDataList(LogicalOperator.fromString(logicalOperator == null? LogicalOperator.AND.getValue(): logicalOperator), cadastralDataList);
    }
    
    private CadastralAddresses extractCadastralAddresses(HttpServletRequest request) {
    	List<CadastralAddress> cadastralAdresses = new ArrayList<>();

    	String logicalOperator = request.getParameter("search_flag_indirizzi");
    	String[] addresses = request.getParameterValues("search_indirizzo");
        String[] streetNumbers = request.getParameterValues("search_civico");
        String[] townIds = request.getParameterValues("search_id_comune");
        String[] townNames = request.getParameterValues("search_des_comune");
        
    	
        int length = getMaxLength(addresses, streetNumbers, townIds, townNames);
        
        for(int i=0; i < length; i++) {
        	String address = getValue(addresses, i);
            String streetNumber = getValue(streetNumbers, i);
            String townId = getValue(townIds ,i);
            String townName = getValue(townNames ,i);
            if(checkAtLeastOneNotNull(address, streetNumber, townId, townName))
            	cadastralAdresses.add(new CadastralAddress().setStreetNumber(streetNumber).setStreetName(address).setTownId(townId).setTownName(townName));
        }
        return cadastralAdresses.isEmpty()? null : new CadastralAddresses(LogicalOperator.fromString(logicalOperator == null? LogicalOperator.AND.getValue(): logicalOperator), cadastralAdresses);
    }
   
    private String getValue(String[] array, Integer i) {
    	return (array != null && i < array.length && !Utils.e(array[i])) ? array[i] : null;
    }
    
    private int getMaxLength(String[]...arrays) {
    	int length = 0;
    	if(arrays != null)
	    	for(int i = 0; i < arrays.length; i++) 
	    		length = (arrays[i] != null && arrays[i].length > length) ? arrays[i].length: length;
    	return length;
    }
    
    private boolean checkAtLeastOneNotNull(Object...values) {
    	boolean check = false;
    	int i = 0;
    	if(values != null)
	    	while(!check && i <values.length) 
	    		check |= values[i++] != null;  	
    	return check;
    }
}

