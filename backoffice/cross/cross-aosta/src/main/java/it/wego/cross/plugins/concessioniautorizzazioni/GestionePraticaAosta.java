package it.wego.cross.plugins.concessioniautorizzazioni;

import com.google.common.base.Strings;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.ProcedimentoType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.RichiestaDocument;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.RichiestadiConcessioniEAutorizzazioniType;
import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.plugins.aec.AeCGestionePratica;
import it.wego.cross.plugins.aec.ConcessioniAutorizzazioniNamespaceContext;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.utils.Utils;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.xml.sax.InputSource;

public class GestionePraticaAosta extends AeCGestionePratica {

    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private MessageSource messageSource;

    @Override
    public String getOggettoPratica(Object pratica) throws Exception {
        String xml = (String) pratica;
        RichiestaDocument richiesta = RichiestaDocument.Factory.parse(xml);
        RichiestadiConcessioniEAutorizzazioniType cea = richiesta.getRichiesta();
        ProcedimentoType[] procedimenti = cea.getRiepilogoDomanda().getProcedimenti().getProcedimentoArray();
        Integer[] ordine = new Integer[procedimenti.length];
        int i = 0;
        for (ProcedimentoType procedimento : procedimenti) {
            ordine[i] = Integer.parseInt(procedimento.getCodiceCud());
            i++;
        }
        Arrays.sort(ordine);
        List<ProcedimentoType> procedimentiDaConcatenare = new ArrayList<ProcedimentoType>();
        for (Integer s : ordine) {
            for (ProcedimentoType procedimento : procedimenti) {
                if (s.toString().equals(procedimento.getCodiceCud()) && !procedimentiDaConcatenare.contains(procedimento)) {
                    procedimentiDaConcatenare.add(procedimento);
                    break;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        if (!procedimentiDaConcatenare.isEmpty()) {
            //Metto il primo procedimento
            sb.append(procedimentiDaConcatenare.get(0).getNome());
            if (procedimentiDaConcatenare.size() > 1) {
                sb.append(" piu' altri endoprocedimenti collegati");
            }
        }
        return sb.toString();
    }

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
                        if (procedimentoRiferimento == null || !procedimentoRiferimento.getTipoProc().equals("EA")) {
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
                        if (procedimento.getTipoProc().equals("TO")
                                && (procedimentoRiferimento != null
                                && !procedimentoRiferimento.getTipoProc().startsWith("E")
                                && !procedimentoRiferimento.getTipoProc().startsWith("C")) || procedimentoRiferimento == null) {
                            procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("TELECOMUNICAZIONI_ORDINARIO");
                        } else if (procedimento.getTipoProc().equals("TA")
                                && (!procedimentoRiferimento.getTipoProc().startsWith("E")
                                && !procedimentoRiferimento.getTipoProc().startsWith("C")
                                && !procedimentoRiferimento.getTipoProc().startsWith("TO"))) {
                            //Ho un TLC automatizzato se procedimento riferimento è null o non è edilizia o commercio o telecomunicazioni
                            procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("TELECOMUNICAZIONI_AUTOMATIZZATO");
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
    public String getUrlCatasto(Object datiInput, Pratica pratica) throws Exception {
        String url = null;
        if (datiInput instanceof it.wego.cross.entity.DatiCatastali) {
            it.wego.cross.entity.DatiCatastali dati = (it.wego.cross.entity.DatiCatastali) datiInput;
            //public String getUrlCatasto(DatiCatastali dati, Pratica pratica) throws Exception {
            if (pratica.getIdComune() != null && !Utils.e(dati.getMappale()) && !Utils.e(dati.getFoglio())) {
                url = pratica.getIdComune().getCodCatastale() + "_" + dati.getFoglio() + "_" + dati.getMappale();
                url = configurationService.getCachedConfiguration("url.cercaMappale", pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune()) + url;
            }
        } else if (datiInput instanceof it.wego.cross.xml.Immobile) {
            it.wego.cross.xml.Immobile dati = (it.wego.cross.xml.Immobile) datiInput;
            if (pratica.getIdComune() != null && !Utils.e(dati.getMappale()) && !Utils.e(dati.getFoglio())) {
                url = pratica.getIdComune().getCodCatastale() + "_" + dati.getFoglio() + "_" + dati.getMappale();
                url = configurationService.getCachedConfiguration("url.cercaMappale", pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune()) + url;
            }
        }
        return url;
    }

    @Override
    public String getUrlCatastoIndirizzo(Object datiInput, Pratica pratica) throws Exception {
        String url = null;
        if (datiInput instanceof it.wego.cross.entity.IndirizziIntervento) {
            it.wego.cross.entity.IndirizziIntervento dati = (it.wego.cross.entity.IndirizziIntervento) datiInput;
            if (!Utils.e(dati.getLongitudine()) && !Utils.e(dati.getLatitudine())) {
                String tmp = configurationService.getCachedConfiguration("url.mappaCatastale", pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune());
                tmp = tmp.replace("[X]", dati.getLatitudine());
                tmp = tmp.replace("[Y]", dati.getLongitudine());
                url = tmp;
            }
            if (!Utils.e(dati.getDatoEsteso1()) && !Utils.e(dati.getDatoEsteso2())) {
                String tmp = configurationService.getCachedConfiguration("url.mappaCatastale", pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune());
                tmp = tmp.replace("[X]", dati.getDatoEsteso1());
                tmp = tmp.replace("[Y]", dati.getDatoEsteso2());
                url = tmp;
            }
        } else if (datiInput instanceof it.wego.cross.xml.IndirizzoIntervento) {
            it.wego.cross.xml.IndirizzoIntervento dati = (it.wego.cross.xml.IndirizzoIntervento) datiInput;
            if (!Utils.e(dati.getLongitudine()) && !Utils.e(dati.getLatitudine())) {
                String tmp = configurationService.getCachedConfiguration("url.mappaCatastale", pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune());
                tmp = tmp.replace("[X]", dati.getLatitudine());
                tmp = tmp.replace("[Y]", dati.getLongitudine());
                url = tmp;
            }
            if (!Utils.e(dati.getDatoEsteso1()) && !Utils.e(dati.getDatoEsteso2())) {
                String tmp = configurationService.getCachedConfiguration("url.mappaCatastale", pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune());
                tmp = tmp.replace("[X]", dati.getDatoEsteso1());
                tmp = tmp.replace("[Y]", dati.getDatoEsteso2());
                url = tmp;
            }
        }
        return url;
    }

    @Override
    public List<IndirizzoInterventoDTO> getDatiToponomastica(IndirizzoInterventoDTO indirizzoInterventoDTO, Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public List<DatiCatastaliDTO> getDatiCatastali(DatiCatastaliDTO datiCatastaliDTO, Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public List<String> controllaDatoCatastale(Object datoInput, Integer idEnte) throws Exception {
        return null;
    }

    @Override
    public List<String> controllaIndirizzoIntervento(Object datoInput, Integer idEnte) throws Exception {
        List<String> errori = new ArrayList<String>();
        if (datoInput instanceof it.wego.cross.xml.IndirizzoIntervento) {

            it.wego.cross.xml.IndirizzoIntervento indirizzo = (it.wego.cross.xml.IndirizzoIntervento) datoInput;
            if ((StringUtils.isEmpty(indirizzo.getLongitudine()) && !StringUtils.isEmpty(indirizzo.getLatitudine()))
                    || (!StringUtils.isEmpty(indirizzo.getLongitudine()) && StringUtils.isEmpty(indirizzo.getLatitudine()))) {
                errori.add("dati longitudine - latitudine non completi");
            }
            if (StringUtils.isEmpty(indirizzo.getLongitudine()) && StringUtils.isEmpty(indirizzo.getLatitudine()) && StringUtils.isEmpty(indirizzo.getIndirizzo())) {
                errori.add(messageSource.getMessage("error.datiCatastali.indirizzo", null, Locale.getDefault()));
            }
        }
        if (datoInput instanceof it.wego.cross.dto.dozer.IndirizzoInterventoDTO) {
            it.wego.cross.dto.dozer.IndirizzoInterventoDTO indirizzo = (it.wego.cross.dto.dozer.IndirizzoInterventoDTO) datoInput;
            if ((StringUtils.isEmpty(indirizzo.getLongitudine()) && !StringUtils.isEmpty(indirizzo.getLatitudine()))
                    || (!StringUtils.isEmpty(indirizzo.getLongitudine()) && StringUtils.isEmpty(indirizzo.getLatitudine()))) {
                errori.add("dati longitudine - latitudine non completi");
            }
            if (StringUtils.isEmpty(indirizzo.getLongitudine()) && StringUtils.isEmpty(indirizzo.getLatitudine()) && StringUtils.isEmpty(indirizzo.getIndirizzo())) {
                errori.add(messageSource.getMessage("error.datiCatastali.indirizzo", null, Locale.getDefault()));
            }
        }
        if (errori.isEmpty()) {
            return null;
        } else {
            return errori;
        }
    }

    @Override
    public String getEsistenzaStradario(Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public String getEsistenzaRicercaCatasto(Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public void postCreazionePratica(it.wego.cross.entity.Pratica pratica, String data) throws Exception {

    }
}
