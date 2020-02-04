/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins;

import com.google.common.base.Strings;
import it.wego.cross.actions.DatiEstesiAction;
import it.wego.cross.avbari.actions.RicercaDatiCatastaliAction;
import it.wego.cross.avbari.actions.RicercaIndirizziAction;
import it.wego.cross.avbari.constants.AvBariConstants;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.DefDatiEstesi;
import it.wego.cross.entity.LkTipoOggetto;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.plugins.aec.AeCGestionePratica;
import it.wego.cross.plugins.aec.ConcessioniAutorizzazioniNamespaceContext;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.ProcedimentiService;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 *
 * @author Gabriele
 */
public class GestionePraticaAvBari extends AeCGestionePratica {

    @Autowired
    private RicercaIndirizziAction ricercaIndirizziAction;

    @Autowired
    private RicercaDatiCatastaliAction ricercaDatiCatastaliAction;

    @Autowired
    private LookupDao lookupDao;

    @Autowired
    private DatiEstesiAction datiEstesiAction;

    @Autowired
    private ProcedimentiService procedimentiService;

    @Autowired
    private ConfigurationService configurationService;

    @Override
    public Procedimenti getProcedimentoRiferimento(List<Procedimenti> procedimenti) {
        if (procedimenti != null && procedimenti.size() > 0) {
            Procedimenti procedimentoRiferimento = null;
            int counter = 0;
            for (Procedimenti procedimento : procedimenti) {
                //Se è edilizia esco subito dando priorità all'ordinario
                counter++;
                if (procedimento.getTipoProc().equals("EO")) {
                    procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("EDILIZIA_ORDINARIO");
                    break;
                } else if (procedimento.getTipoProc().equals("EA")) {
                    procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("EDILIZIA_AUTOMATICO");
                } else {
                    //Verifico se è commercio.
                    //Do la priorità al commercio ordinario ma solo se non c'è una edilizia
                    if (procedimento.getTipoProc().equals("CO")) {
                        //Se non c'è edilizia automatizzato, allora metto commercio ordinario
                        //Altrimenti verifico se  non ho procedimenti di riferimento.
                        if (procedimentoRiferimento == null || (!procedimentoRiferimento.getTipoProc().equals("EA"))) {
                            procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("COMMERCIO_ORDINARIO");
                        }
                    } else if (procedimento.getTipoProc().equals("CA")) {
                        //Se è commercio automatico, verifico se era già stato individuato un procedimento di riferimento
                        //ed in tal caso lo aggiorno
                        if (procedimentoRiferimento != null) {
                            //Ho commercio automatizzato se non ho un commecio ordinario o non ho un edilizia automatizzata
                            if (!procedimentoRiferimento.getTipoProc().equals("CO") && !procedimentoRiferimento.getTipoProc().equals("EA")) {
                                procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("COMMERCIO_AUTOMATIZZATO");
                            }
                        } else {
                            //se non ho procedimenti di riferimento, allora setti automatizzato
                            procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("COMMERCIO_AUTOMATIZZATO");
                        }
                    } else {
                        //Ho un procedimento delle telecomunicazioni ordinario se non ho in lista nessun procedimento dell'edilizia o del commercio
                        //Ho un TLC ordinario se procedimento riferimento è null o non è edilizia o commercio
                        if (procedimento.getTipoProc().equals("SC")
                                && (procedimentoRiferimento != null
                                && !procedimentoRiferimento.getTipoProc().startsWith("E")
                                && !procedimentoRiferimento.getTipoProc().startsWith("C")) || procedimentoRiferimento == null) {
                            procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("SERVIZI_CITTADINI");
                        } else {
                            //Mantengo quello che ho già selezionato
                            if (counter == procedimenti.size()) {
                                //Se non ho proprio nessun procedimento, il default è COMMERCIO AUTOMATIZZATO
                                procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("COMMERCIO_AUTOMATIZZATO");
                            }
                        }
                    }
                }
            }
            return procedimentoRiferimento;
        } else {
            return null;
        }
    }

    @Override
    public String getUrlCatasto(Object dato, it.wego.cross.entity.Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public String getUrlCatastoIndirizzo(Object dato, it.wego.cross.entity.Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public List<IndirizzoInterventoDTO> getDatiToponomastica(IndirizzoInterventoDTO indirizzoInterventoDTO, it.wego.cross.entity.Pratica pratica) throws Exception {
        return ricercaIndirizziAction.RicercaIndirizzi(indirizzoInterventoDTO, pratica);
    }

    @Override
    public List<DatiCatastaliDTO> getDatiCatastali(DatiCatastaliDTO datiCatastaliDTO, it.wego.cross.entity.Pratica pratica) throws Exception {
        return ricercaDatiCatastaliAction.RicercaDatiCatastali(datiCatastaliDTO, pratica);
    }

    @Override
    public List<String> controllaDatoCatastale(Object datoInput, Integer idEnte) throws Exception {
        String esisteCatasto = configurationService.getCachedPluginConfiguration(AvBariConstants.ESISTENZA_CATASTO, idEnte, null);
        List<String> errori = new ArrayList<String>();
        if (esisteCatasto != null && esisteCatasto.equalsIgnoreCase("TRUE")) {
            if (datoInput instanceof it.wego.cross.xml.Immobile) {
                it.wego.cross.xml.Immobile datoCatastale = (it.wego.cross.xml.Immobile) datoInput;
                if (StringUtils.isEmpty(datoCatastale.getCodImmobile())) {
                    errori.add("dato catastale non completo - mancano i riferimenti al sistema catastale");
                }
            }
            if (datoInput instanceof it.wego.cross.dto.dozer.DatiCatastaliDTO) {
                it.wego.cross.dto.dozer.DatiCatastaliDTO datoCatastale = (it.wego.cross.dto.dozer.DatiCatastaliDTO) datoInput;
                if (StringUtils.isEmpty(datoCatastale.getCodImmobile())) {
                    errori.add("dato catastale non completo - mancano i riferimenti al sistema catastale");
                }
            }
        }
        if (errori.isEmpty()) {
            return null;
        } else {
            return errori;
        }
    }

    @Override
    public List<String> controllaIndirizzoIntervento(Object datoInput, Integer idEnte) throws Exception {
        String esisteStradario = configurationService.getCachedPluginConfiguration(AvBariConstants.ESISTENZA_STRADARIO, idEnte, null);
        List<String> errori = new ArrayList<String>();
        if (esisteStradario != null && esisteStradario.equalsIgnoreCase("TRUE")) {
            if (datoInput instanceof it.wego.cross.xml.IndirizzoIntervento) {
                it.wego.cross.xml.IndirizzoIntervento indirizzo = (it.wego.cross.xml.IndirizzoIntervento) datoInput;
                if (StringUtils.isEmpty(indirizzo.getCodVia())) {
                    errori.add("indirizzo non completo - mancano i riferimenti allo stradario");
                }
            }
            if (datoInput instanceof it.wego.cross.dto.dozer.IndirizzoInterventoDTO) {
                it.wego.cross.dto.dozer.IndirizzoInterventoDTO indirizzo = (it.wego.cross.dto.dozer.IndirizzoInterventoDTO) datoInput;
                if (StringUtils.isEmpty(indirizzo.getCodVia())) {
                    errori.add("indirizzo non completo - mancano i riferimenti allo stradario");
                }
            }
        } else {
            if (datoInput instanceof it.wego.cross.xml.IndirizzoIntervento) {
                it.wego.cross.xml.IndirizzoIntervento indirizzo = (it.wego.cross.xml.IndirizzoIntervento) datoInput;
                if (StringUtils.isEmpty(indirizzo.getIndirizzo())) {
                    errori.add("compilare almeno il campo indirizzo");
                }
            }
            if (datoInput instanceof it.wego.cross.dto.dozer.IndirizzoInterventoDTO) {
                it.wego.cross.dto.dozer.IndirizzoInterventoDTO indirizzo = (it.wego.cross.dto.dozer.IndirizzoInterventoDTO) datoInput;
                if (StringUtils.isEmpty(indirizzo.getIndirizzo())) {
                    errori.add("compilare almeno il campo indirizzo");
                }
            }
        }
        if (errori.isEmpty()) {
            return null;
        } else {
            return errori;
        }
    }

    @Override
    public String getEsistenzaStradario(it.wego.cross.entity.Pratica pratica) throws Exception {
        String ret = null;
        Integer idEnte = pratica.getIdProcEnte().getIdEnte().getIdEnte();
        String esisteStradario = configurationService.getCachedPluginConfiguration(AvBariConstants.ESISTENZA_STRADARIO, idEnte, pratica.getIdComuneInteger());
        if (esisteStradario != null && esisteStradario.equalsIgnoreCase("TRUE")) {
            ret = "true";
        }
        return ret;
    }

    @Override
    public String getEsistenzaRicercaCatasto(it.wego.cross.entity.Pratica pratica) throws Exception {
        String ret = null;
        Integer idEnte = pratica.getIdProcEnte().getIdEnte().getIdEnte();
        String esisteCatasto = configurationService.getCachedPluginConfiguration(AvBariConstants.ESISTENZA_CATASTO, idEnte, pratica.getIdComuneInteger());
        if (esisteCatasto != null && esisteCatasto.equalsIgnoreCase("TRUE")) {
            ret = "true";
        }
        return ret;
    }

    @Override
    public void postCreazionePratica(it.wego.cross.entity.Pratica pratica, String data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document document = documentBuilderFactory.newDocumentBuilder().parse(new InputSource(new StringReader(data)));
        XPath xpath = XPathFactory.newInstance().newXPath();
        NamespaceContext context = new ConcessioniAutorizzazioniNamespaceContext(
                "cea", "http://gruppoinit.it/b110/ConcessioniEAutorizzazioni/procedimentoUnico",
                "ogg", "http://egov.diviana.it/b109/OggettiCondivisi");
        xpath.setNamespaceContext(context);
        String ricerca = "//cea:campoHref[cea:CampoXmlMod='" + AvBariConstants.INTERVENTO_IN_DEROGA + "']/cea:valoreUtente";
        String valore = xpath.evaluate(ricerca, document);
        LkTipoOggetto to = lookupDao.findTipoOggettoByCodice(AvBariConstants.TIPO_OGGETTO);

        if (to != null) {
            inserisciDatiEstesi(document, xpath, AvBariConstants.INTERVENTO_IN_DEROGA, pratica, to);
            inserisciDatiEstesi(document, xpath, AvBariConstants.INTERVENTO_IN_SANATORIA, pratica, to);
            inserisciDatiEstesi(document, xpath, AvBariConstants.DATA_INIZIO_LAVORI, pratica, to);
            inserisciDatiEstesi(document, xpath, AvBariConstants.DATA_FINE_LAVORI, pratica, to);
            inserisciDatiEstesi(document, xpath, AvBariConstants.DATA_FINE_LAVORI_PRESUNTA, pratica, to);
        }

    }

    private void inserisciDatiEstesi(Document document, XPath xpath, String tipoCampo, it.wego.cross.entity.Pratica pratica, LkTipoOggetto to) throws Exception {
        String ricerca = "//cea:campoHref[cea:CampoXmlMod='" + tipoCampo + "']/cea:valoreUtente";
        String valore = xpath.evaluate(ricerca, document);
        if (!Strings.isNullOrEmpty(valore)) {
            DefDatiEstesi de = new DefDatiEstesi();
            de.setIdTipoOggetto(to);
            de.setIdIstanza(pratica.getIdPratica().toString());
            de.setCodValue(tipoCampo);
            if (tipoCampo.equals(AvBariConstants.INTERVENTO_IN_DEROGA) || tipoCampo.equals(AvBariConstants.INTERVENTO_IN_SANATORIA)) {
                de.setValue("S");
            } else {
                de.setValue(valore);
            }
            datiEstesiAction.salvaDatoEstesoEntity(de);
        }
    }

}
