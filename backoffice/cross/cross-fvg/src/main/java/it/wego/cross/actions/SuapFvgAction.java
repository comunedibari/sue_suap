/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import it.wego.cross.constants.Constants;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.StagingDao;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.Staging;
import it.wego.cross.entity.Utente;
import it.wego.cross.plugins.documenti.GestioneAllegatiFS;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.SuapFvgService;
import it.wego.cross.service.UsefulService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PdfAttachmentUtils;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Allegato;
import it.wego.cross.xml.Anagrafiche;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPathConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.xml.xpath.XPathExpression;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author GabrieleM
 */
@Component
public class SuapFvgAction {

    @Autowired
    private UsefulService usefulService;
    @Autowired
    private EntiService entiService;
    @Autowired
    private SuapFvgService suapFvgService;
    @Autowired
    private StagingDao stagingDao;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private GestioneAllegatiFS gestioneAllegatiFS;
    @Autowired
    private DocumentBuilder documentBuilder;
    //XPATH EXPRESSIONS
    @Autowired
    private XPathExpression allegatiFvgXpr;
    @Autowired
    private XPathExpression descrizioneAllegatiFvgXpr;
    @Autowired
    private XPathExpression identificativoAllegatiFvgXpr;
    @Autowired
    private XPathExpression tipoFileAllegatiFvgXpr;

    @Transactional
    public PraticheProtocollo salvaPratica(MultipartFile file, it.wego.cross.dto.dozer.EnteDTO entePerCuiOpero, Utente utente) throws Exception {

        Preconditions.checkArgument(file.getBytes().length != 0, "E' necessario caricare un file");
        Map<String, byte[]> attachmentMap = PdfAttachmentUtils.getPdfAttachmentsFromP7mFileData(file.getBytes());
        Preconditions.checkArgument(attachmentMap.containsKey("managedData.xml"), "Il file caricato non contiene l'allegato managedData.xml. Impossibile continuare.");

        byte[] suapFvgXmlData = attachmentMap.get("managedData.xml");

        String xmlPraticaCross = suapFvgService.getXmlFromTemplate(suapFvgXmlData);
        it.wego.cross.xml.Pratica praticaCrossXml = PraticaUtils.getPraticaFromXML(xmlPraticaCross);

        String identificativoPratica = file.getOriginalFilename().replaceAll(".pdf.p7m", "");
        identificativoPratica = identificativoPratica.replaceAll(".pdf", "");

        praticaCrossXml.setIdentificativoPratica(identificativoPratica);

        praticaCrossXml.setDataRicezione(Utils.dateToXmlGregorianCalendar(Calendar.getInstance().getTime()));

        String mittenti = "";
        for (Anagrafiche anagrafica : praticaCrossXml.getAnagrafiche()) {
            if ("G".equalsIgnoreCase(anagrafica.getAnagrafica().getTipoAnagrafica())) {
                mittenti = mittenti.concat(anagrafica.getAnagrafica().getDenominazione()).concat("<br/>");
            } else {
                mittenti = mittenti.concat(anagrafica.getAnagrafica().getCognome()).concat(" ").concat(anagrafica.getAnagrafica().getNome()).concat("<br/>");
            }
        }

        byte[] suapFvgDescrizioniFile = attachmentMap.get("mets.xml");
        HashMap<String, HashMap<String, String>> descrizioniAllegati = getDescrizioneAllegati(suapFvgDescrizioniFile);

        attachmentMap.put(file.getOriginalFilename(), file.getBytes());
        for (Map.Entry<String, byte[]> attachment : attachmentMap.entrySet()) {
            String createTempFile = gestioneAllegatiFS.createTempFile(attachment.getKey(), attachment.getValue(), entiService.getEnte(entePerCuiOpero.getIdEnte()), null);
            it.wego.cross.xml.Allegato allegatoXml = new Allegato();
            allegatoXml.setNomeFile(attachment.getKey());
            allegatoXml.setDescrizione(attachment.getKey());
            HashMap<String, String> datiAllegato = getNomeDescrizione(descrizioniAllegati, attachment.getKey());
            if (!datiAllegato.isEmpty()) {
                if (!Strings.isNullOrEmpty(datiAllegato.get("descrizione"))) {
                    allegatoXml.setDescrizione(datiAllegato.get("descrizione"));
                }
                allegatoXml.setTipoFile(datiAllegato.get("tipoFile"));
            }
            allegatoXml.setPathFile(createTempFile);
            allegatoXml.setRiepilogoPratica(attachment.getKey().equals(file.getOriginalFilename()) ? "S" : "N");
            praticaCrossXml.getAllegati().getAllegato().add(allegatoXml);
        }

        Staging staging = new Staging();
        staging.setIdentificativoProvenienza(identificativoPratica);
        staging.setOggetto(praticaCrossXml.getOggetto());
        Log.WS.info("Data ricezione: " + Utils.xmlGregorianCalendarToDate(praticaCrossXml.getDataRicezione()));
        staging.setDataRicezione(Utils.xmlGregorianCalendarToDate(praticaCrossXml.getDataRicezione()));
        Log.WS.info("Tipo messaggio: " + Constants.CARICAMENTO_MANUALE_PRATICA);
        staging.setTipoMessaggio(Constants.CARICAMENTO_MANUALE_PRATICA);
        staging.setXmlRicevuto(suapFvgXmlData);
        staging.setXmlPratica(PraticaUtils.getXmlFromPratica(praticaCrossXml).getBytes("UTF-8"));
        staging.setIdEnte(entiService.getEnte(entePerCuiOpero.getIdEnte()));
        stagingDao.insert(staging);

        PraticheProtocollo praticaProtocollo = new PraticheProtocollo();
        praticaProtocollo.setOggetto(praticaCrossXml.getOggetto());
        praticaProtocollo.setIdentificativoPratica(identificativoPratica);
        praticaProtocollo.setDestinatario(entePerCuiOpero.getUnitaOrganizzativa());
        praticaProtocollo.setIdUtentePresaInCarico(utente);
        praticaProtocollo.setStato("In compilazione");
        praticaProtocollo.setMittente(mittenti);
//        praticaProtocollo.setNProtocollo(pratica.getProtocollo());
//        praticaProtocollo.setAnnoRiferimento(pratica.getAnnoRiferimento());
//        praticaProtocollo.setCodRegistro(pratica.getRegistro());
        praticaProtocollo.setDataRicezione(Utils.xmlGregorianCalendarToDate(praticaCrossXml.getDataRicezione()));
//        praticaProtocollo.setDestinatario(ente.getUnitaOrganizzativa());
//        praticaProtocollo.setDataProtocollazione(pratica.getDataProtocollo());
        praticaProtocollo.setTipoDocumento(Constants.CARICAMENTO_MANUALE_PRATICA);
        praticaProtocollo.setModalita(Constants.CARICAMENTO_MANUALE_PRATICA);
        praticaProtocollo.setIdStaging(staging);
        praticaDao.insert(praticaProtocollo);
        usefulService.flush();

        return praticaProtocollo;
    }

    private HashMap<String, HashMap<String, String>> getDescrizioneAllegati(byte[] suapFvgDescrizioniFile) throws Exception {
        HashMap<String, HashMap<String, String>> documenti = new HashMap<String, HashMap<String, String>>();
        Document doc = documentBuilder.parse(new ByteArrayInputStream(suapFvgDescrizioniFile));
        List<Node> list = allegatiFvgXpr.evaluateAsNodeList(doc.getDocumentElement());
        for (int i = 0; i < list.size(); i++) {
            Node node = list.get(i);
            HashMap<String, String> datiSpecifici = new HashMap<String, String>();
            String description = descrizioneAllegatiFvgXpr.evaluateAsString(node);
            String id = identificativoAllegatiFvgXpr.evaluateAsString(node);
            String tipoFile = tipoFileAllegatiFvgXpr.evaluateAsString(node);
            if (id != null && !"".equals(id)) {
                datiSpecifici.put("descrizione", description);
                datiSpecifici.put("tipoFile", tipoFile);
                documenti.put(id, datiSpecifici);
            }
        }
        return documenti;
    }

    private HashMap<String, String> getNomeDescrizione(HashMap<String, HashMap<String, String>> mappa, String nomeFile) {
        HashMap<String, String> ret = new HashMap<String, String>();
        for (String id : mappa.keySet()) {
            if (id.contains(nomeFile)) {
                ret.put("descrizione", mappa.get(id).get("descrizione"));
                ret.put("tipoFile", mappa.get(id).get("tipoFile"));
                break;
            }
        }
        return ret;
    }

}
