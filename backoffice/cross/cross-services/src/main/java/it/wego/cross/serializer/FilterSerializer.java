/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.actions.UtentiAction;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.dto.filters.ComunicazioneFilter;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.filters.GestioneDatiEstesiFilter;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkStatiScadenze;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.utils.Utils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        String statoProcedimentoUtente = request.getParameter("search_stato_procedimento_utente");
        String indirizzo = request.getParameter("search_indirizzo");
        String civico = request.getParameter("search_civico");
        String idTipoUnita = request.getParameter("search_tipoCatasto");
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

}
