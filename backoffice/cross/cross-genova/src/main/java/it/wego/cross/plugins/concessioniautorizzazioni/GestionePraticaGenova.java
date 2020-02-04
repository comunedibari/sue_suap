package it.wego.cross.plugins.concessioniautorizzazioni;

import com.google.common.collect.Lists;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.ProcedimentoType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.RichiestaDocument;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.RichiestadiConcessioniEAutorizzazioniType;
import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.genova.anagrafe.client.stub.Toponomastica;
import it.wego.cross.genova.anagrafe.client.stub.ToponomasticaService;
import it.wego.cross.genova.anagrafe.client.stub.ToponomasticaService_Impl;
import it.wego.cross.genova.toponomastica.client.xml.CampoPrecompilazioneBean;
import it.wego.cross.genova.toponomastica.client.xml.PrecompilazioneBean;
import it.wego.cross.plugins.aec.AeCGestionePratica;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.cxf.common.util.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class GestionePraticaGenova extends AeCGestionePratica {

    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private MessageSource messageSource;

    private static final String ENDPOINT_TOPONOMASTICA = "genova.toponomastica.endpoint";

    @Override
    public Procedimenti getProcedimentoRiferimento(List<Procedimenti> procedimenti) {
        if (procedimenti != null && procedimenti.size() > 0) {
            Procedimenti procedimentoRiferimento = null;
            for (Procedimenti procedimento : procedimenti) {
                //Se è edilizia esco subito dando priorità all'ordinario
                if (procedimento.getTipoProc().equals("EO")) {
                    procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("EDILIZIA_ORDINARIO");
                    break;
                } else if (procedimento.getTipoProc().equals("EA")) {
                    procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("EDILIZIA_AUTOMATIZZATO");
                } else if (procedimento.getTipoProc().equals("CO")) {
                    procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("COMMERCIO_ORDINARIO");
                    break;
                } else if (procedimento.getTipoProc().equals("CA")) {
                    procedimentoRiferimento = procedimentiService.findProcedimentoByCodProc("COMMERCIO_AUTOMATIZZATO");
                }
            }
            return procedimentoRiferimento;
        } else {
            return null;
        }
    }

    @Override
    public String getUrlCatasto(Object dato, Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public String getUrlCatastoIndirizzo(Object dato, Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public List<IndirizzoInterventoDTO> getDatiToponomastica(IndirizzoInterventoDTO indirizzoInterventoDTO, Pratica pratica) throws Exception {

        Enti ente = pratica.getIdProcEnte().getIdEnte();
        LkComuni comune = pratica.getIdComune();

        PrecompilazioneBean input = getRichiestaInput(indirizzoInterventoDTO.getIndirizzo(), indirizzoInterventoDTO.getCivico());

        String endpointToponomastica = configurationService.getCachedConfiguration(ENDPOINT_TOPONOMASTICA, ente.getIdEnte(), comune.getIdComune());
        ToponomasticaService toponomasticaService = new ToponomasticaService_Impl();
        Toponomastica toponomastica = toponomasticaService.getToponomastica();
        ((javax.xml.rpc.Stub) toponomastica)._setProperty("javax.xml.rpc.service.endpoint.address", endpointToponomastica);
        String xmlRichiesta = Utils.marshall(input);
        log.debug("Richiesta: " + xmlRichiesta);
        String result = toponomastica.process(xmlRichiesta);
        log.debug("Risposta: " + result);
        PrecompilazioneBean output = getRichiestaOutput(result);
        List<IndirizzoInterventoDTO> datiToponomastici = getDatiToponomastici(output);
        return datiToponomastici;
    }

    @Override
    public List<DatiCatastaliDTO> getDatiCatastali(DatiCatastaliDTO datiCatastaliDTO, Pratica pratica) throws Exception {
        return null;
    }

    private PrecompilazioneBean getRichiestaInput(String indirizzo, String civico) {
        PrecompilazioneBean bean = new PrecompilazioneBean();
        CampoPrecompilazioneBean desVia = new CampoPrecompilazioneBean();
        desVia.setCodice("DES_VIA");
        desVia.getDescrizione().add(indirizzo);
        CampoPrecompilazioneBean codCivico = new CampoPrecompilazioneBean();
        codCivico.setCodice("COD_CIVICO");
        codCivico.getDescrizione().add(civico);
        PrecompilazioneBean.Input input = new PrecompilazioneBean.Input();
        input.getCampoPrecompilazioneBean().add(desVia);
        input.getCampoPrecompilazioneBean().add(codCivico);
        bean.setInput(input);
        bean.setCodEnte("010025");
        return bean;
    }

    private PrecompilazioneBean getRichiestaOutput(String sXml) throws Exception {
        Document xml = null;
        try {
            if (sXml != null) {
                xml = DocumentHelper.parseText(sXml);
            } else {
                throw new Exception("Il servizio toponomastica non ha ritornato alcun valore");
            }
        } catch (DocumentException e) {
            log.error("Errore cercando di interpretare l'xml ricevuto: " + sXml, e);
            throw new Exception("Errore cercando di interpretare l'xml ricevuto");
        }
        PrecompilazioneBean bean = new PrecompilazioneBean();
        List nodoMultiplo = xml.selectNodes("//Output/CampoPrecompilazioneBean");
        PrecompilazioneBean.Output output = new PrecompilazioneBean.Output();
        if (nodoMultiplo != null && nodoMultiplo.size() > 0) {
            Iterator itDati = nodoMultiplo.iterator();
            while (itDati.hasNext()) {
                Node nodoCampoPrecBean = (Node) itDati.next();
                CampoPrecompilazioneBean campoPrecompilazione = new CampoPrecompilazioneBean();
                String codice = nodoCampoPrecBean.valueOf("Codice");
                String totale = nodoCampoPrecBean.valueOf("count(Descrizione)");
                int count = Integer.parseInt(totale);
                for (int i = 1; i <= count; i++) {
                    String desc = nodoCampoPrecBean.valueOf("Descrizione[" + i + "]");
                    String descMod = desc.replace('\'', '`');
                    campoPrecompilazione.getDescrizione().add(descMod);
                }
                campoPrecompilazione.setCodice(codice.equalsIgnoreCase("") ? "" : codice);
                output.getCampoPrecompilazioneBean().add(campoPrecompilazione);
            }
        }
        bean.setOutput(output);
        return bean;
    }

    private List<IndirizzoInterventoDTO> getDatiToponomastici(PrecompilazioneBean output) {
        List<IndirizzoInterventoDTO> dati = new ArrayList<IndirizzoInterventoDTO>();
        PrecompilazioneBean.Output valori = output.getOutput();
        if (valori != null && valori.getCampoPrecompilazioneBean() != null && !valori.getCampoPrecompilazioneBean().isEmpty()) {
            List<CampoPrecompilazioneBean> campi = valori.getCampoPrecompilazioneBean();
            //Indica quanti sono i risultati che mi ha dato la query
            int total = campi.get(0).getDescrizione().size();
            for (int i = 0; i < total; i++) {
                IndirizzoInterventoDTO dto = new IndirizzoInterventoDTO();
                for (CampoPrecompilazioneBean campo : campi) {
                    if (campo.getCodice().equals("COD_VIA")) {
                        String codVia = campo.getDescrizione().get(i);
                        dto.setCodVia(codVia);
                    } else if (campo.getCodice().equals("DES_VIA")) {
                        String desVia = campo.getDescrizione().get(i);
                        dto.setIndirizzo(desVia);
                    } else if (campo.getCodice().equals("COD_CIVICO")) {
                        String codCivico = campo.getDescrizione().get(i);
                        dto.setCodCivico(codCivico);
                    } else if (campo.getCodice().equals("COD_LETTERA")) {
                        String lettera = campo.getDescrizione().get(i);
                        dto.setLettera(lettera);
                    } else if (campo.getCodice().equals("COD_COLORE")) {
                        String colore = campo.getDescrizione().get(i);
                        dto.setColore(colore);
                    } else if (campo.getCodice().equals("COD_CAP")) {
                        String cap = campo.getDescrizione().get(i);
                        dto.setCap(cap);
                    } else if (campo.getCodice().equals("NUMERO_INTERNO")) {
                        String numeroInterno = campo.getDescrizione().get(i);
                        dto.setInternoNumero(numeroInterno);
                    } else if (campo.getCodice().equals("LETTERA_INTERNO")) {
                        String letteraInterno = campo.getDescrizione().get(i);
                        dto.setInternoLettera(letteraInterno);
                    } else if (campo.getCodice().equals("SCALA_INTERNO")) {
                        String scala = campo.getDescrizione().get(i);
                        dto.setInternoScala(scala);
                    }
                }
                dati.add(dto);
            }
        }
        return dati;
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
            if (StringUtils.isEmpty(indirizzo.getCodVia()) || StringUtils.isEmpty(indirizzo.getCivico())) {
                errori.add("indirizzo non completo - mancano i riferimenti allo stradario");
            }
        }
        if (datoInput instanceof it.wego.cross.dto.dozer.IndirizzoInterventoDTO) {
            it.wego.cross.dto.dozer.IndirizzoInterventoDTO indirizzo = (it.wego.cross.dto.dozer.IndirizzoInterventoDTO) datoInput;
            if (StringUtils.isEmpty(indirizzo.getCodVia()) || StringUtils.isEmpty(indirizzo.getCivico())) {
                errori.add("indirizzo non completo - mancano i riferimenti allo stradario");
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
    public String getOggettoPratica(Object pratica) throws Exception {
        String xml = (String) pratica;
        RichiestaDocument richiesta = RichiestaDocument.Factory.parse(xml);
        RichiestadiConcessioniEAutorizzazioniType cea = richiesta.getRichiesta();
        ProcedimentoType[] procedimenti = cea.getRiepilogoDomanda().getProcedimenti().getProcedimentoArray();
        Map<String, String> procedimentiAeC = new HashMap<String, String>();
        if (procedimenti != null) {
            for (ProcedimentoType procedimento : procedimenti) {
                procedimentiAeC.put(procedimento.getCodiceProcedimento(), procedimento.getNome());
            }
        }
        List<Procedimenti> procedimentiCross = Lists.newArrayList();
        for (Map.Entry<String, String> entry : procedimentiAeC.entrySet()) {
            Procedimenti procedimento = procedimentiService.findProcedimentoByCodProc(entry.getKey());
            procedimentiCross.add(procedimento);
        }

        Collections.sort(procedimentiCross, new Comparator<Procedimenti>() {

            @Override
            public int compare(Procedimenti o1, Procedimenti o2) {
                return o2.getPeso().compareTo(o1.getPeso());
            }
        });
        StringBuilder sb = new StringBuilder();
        for (Procedimenti p : procedimentiCross){
            sb.append(p.getProcedimentiTestiList().get(0).getDesProc()).append(", ");
        }
        return sb.toString();
    }

    @Override
    public String getEsistenzaStradario(Pratica pratica) throws Exception {
        return "true";
    }

    @Override
    public String getEsistenzaRicercaCatasto(Pratica pratica) throws Exception {
        return null;
    }

    @Override
    public void postCreazionePratica(it.wego.cross.entity.Pratica pratica, String data) throws Exception {

    }

}
