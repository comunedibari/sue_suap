/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.genova.actions;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import it.wego.cross.actions.PraticheAction;
import it.wego.cross.beans.AttoriComunicazione;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.client.xml.extractone.EstremiXidentificazioneVerDocType;
import it.wego.cross.dao.AllegatiDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.UsefulDao;
import it.wego.cross.dto.dozer.forms.SueInvioWsDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.events.comunicazionews.controller.ComunicazioneWsGenovaClient;
import it.wego.cross.exception.CrossException;
import it.wego.cross.genova.edilizia.client.xml.RispostaBO;
import static it.wego.cross.plugins.aec.AeCGestionePratica.log;
import it.wego.cross.serializer.GenovaSerializer;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.UsefulService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.xml.bind.JAXBElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author piergiorgio
 */
@Component
public class PluginGenovaAction {
    
    @Autowired
    private UsefulService usefulService;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private ComunicazioneWsGenovaClient genovaClient;
    @Autowired
    private UsefulDao usefulDao;
    @Autowired
    private GenovaSerializer genovaSerializer;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private PraticheAction praticheAction;
    @Autowired
    private AllegatiDao allegatiDao;
    @Autowired
    private PraticheService praticheService;
    
    @Transactional(rollbackFor = Exception.class)
    public void salvaEvento(Pratica pratica, PraticheEventi eventoPratica, List<it.wego.cross.entity.Allegati> allegati) throws Exception {
        usefulService.update(pratica);
        praticaDao.aggiornaPraticheEvento(eventoPratica, allegati);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public EventoBean salvaEvento(Pratica pratica, Utente user, ProcessiEventi processoEvento, SueInvioWsDTO sueInvioWsDTO) throws Exception {
        Enti ente = pratica.getIdProcEnte().getIdEnte();
        LkComuni comune = pratica.getIdComune();
        
        ComunicazioneBean comunicazioneEvento = new ComunicazioneBean();
        
        comunicazioneEvento.setIdPratica(pratica.getIdPratica());
        comunicazioneEvento.setIdEventoProcesso(processoEvento.getIdEvento());
        comunicazioneEvento.setIdUtente(user.getIdUtente());
        AttoriComunicazione attori = praticheAction.getAttoriComunicazione(pratica.getPraticaAnagraficaList());
        comunicazioneEvento.setDestinatari(attori);
        comunicazioneEvento.setInviaMail(Boolean.TRUE);
//        comunicazioneEvento.setDestinatariEmail(destinatariEmail);
        comunicazioneEvento.setVisibilitaCross(Boolean.TRUE);
        comunicazioneEvento.setVisibilitaUtente(Boolean.TRUE);
        comunicazioneEvento.setOggettoProtocollo(pratica.getOggettoPratica());
        
        if (sueInvioWsDTO.getInvioSueWs()) {
            it.wego.cross.xml.Pratica praticaCross = genovaSerializer.serialize(pratica, null, user);
            String xml = Utils.marshall(praticaCross);
            
            try {
                RispostaBO esito = genovaClient.inviaPratica(xml, ente, comune);
                if ("KO".equalsIgnoreCase(esito.getEsito())) {
                    throw new CrossException(esito.getErrorMessage());
                } else {
                    String fascicoloProtocollo = esito.getIdFascicoloProtocollo();
                    log.info("[ATTENZIONE!!!!!] Id fascicolo BO Edilizia: " + fascicoloProtocollo);
                    String message = "Il formato del fascicolo ritornato non rispetta lo standard idFascicolo/AnnoFascicolo/NumeroFascicolo/NumeroSottofascicolo. Valore ritornato: " + fascicoloProtocollo;
                    Preconditions.checkArgument(fascicoloProtocollo.contains("/"), message);
                    String[] fascicoloSplitted = fascicoloProtocollo.split("/");
                    Preconditions.checkArgument(fascicoloSplitted.length == 4, message);
                    String idFascicolo = fascicoloSplitted[0];
                    log.info("Id fascicolo: " + idFascicolo);
                    String annoFascicolo = fascicoloSplitted[1];
                    log.info("Anno fascicolo: " + annoFascicolo);
                    String numeroFascicolo = fascicoloSplitted[2];
                    log.info("Numero fascicolo: " + numeroFascicolo);
                    String numeroSottoFascicolo = fascicoloSplitted[3];
                    log.info("Numero sottofascicolo: " + numeroSottoFascicolo);
                    
                    pratica.setIdentificativoEsterno(esito.getFascicoloProtocollo());
                    log.info("Idendficativo esterno: " + pratica.getIdentificativoEsterno());
                    
                    pratica.setResponsabileProcedimento(esito.getResposabileProcedimento());
                    pratica.setAnnoRiferimento(Integer.valueOf(annoFascicolo));
                    pratica.setCodRegistro(esito.getTipoRegistroProtocolloBackOffice());
                    pratica.setProtocollo(numeroFascicolo);
                    pratica.setUfficioRiferimento(esito.getUfficioRiferimento());
                    if (!Utils.e(esito.getDataRegistroProtocolloBackOffice())) {
                        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                        pratica.setDataProtocollazione(fmt.parse(esito.getDataRegistroProtocolloBackOffice()));
                    }
                }
            } catch (Exception e) {
                throw new CrossException("Errore durante l'invio della pratica a Backoffice: " + e.getMessage(), e);
            }
        } else {
            try {
                pratica.setResponsabileProcedimento(sueInvioWsDTO.getResponsabileProcedimento());
                pratica.setAnnoRiferimento(sueInvioWsDTO.getAnnoRiferimento());
                pratica.setCodRegistro(sueInvioWsDTO.getCodiceRegistro());
                pratica.setProtocollo(sueInvioWsDTO.getCodiceFascicolo());
                pratica.setUfficioRiferimento(sueInvioWsDTO.getUfficioProcedimento());
                pratica.setDataProtocollazione(sueInvioWsDTO.getDataFascicolo());
            } catch (Exception e) {
                throw new CrossException("Errore durante il salvataggio delle informazioni legate alla pratica: " + e.getMessage(), e);
            }
        }
        workFlowService.gestisciProcessoEvento(comunicazioneEvento);
        usefulDao.update(pratica);
       
//        return praticheService.getPraticaEvento(comunicazioneEvento.getIdEventoPratica());
        return comunicazioneEvento;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void aggiornaAllegatiCaricatiManualmente(String idUD, List<Allegati> allegati) throws Exception {
        for (Allegati allegato : allegati) {
            String fileEsterno = getXmlRecuperoDaUnitaDocumentale(idUD, allegato.getNomeFile());
            allegato.setIdFileEsterno("D|" + fileEsterno);
            allegato.setFile(null);
            allegatiDao.mergeAllegato(allegato);
        }
    }
    
    public String getXmlPerExtractOne(Integer annoProtocollo, Integer numeroProtocollo, String fileName) throws Exception {
        it.wego.cross.client.xml.extractone.EstremiXidentificazioneVerDocType estremi = new it.wego.cross.client.xml.extractone.EstremiXidentificazioneVerDocType();
        it.wego.cross.client.xml.extractone.EstremiXIdentificazioneUDType estremiUnitaDocumentaria = new it.wego.cross.client.xml.extractone.EstremiXIdentificazioneUDType();
        it.wego.cross.client.xml.extractone.EstremiRegNumType estremiReg = new it.wego.cross.client.xml.extractone.EstremiRegNumType();
        estremiReg.setAnnoReg(annoProtocollo);
        estremiReg.setNumReg(numeroProtocollo);
        estremiUnitaDocumentaria.setEstremiRegNum(estremiReg);
        estremi.setEstremiXIdentificazioneUD(estremiUnitaDocumentaria);
        it.wego.cross.client.xml.extractone.EstremiXIdentificazioneFileUDType estremiPerFile = new it.wego.cross.client.xml.extractone.EstremiXIdentificazioneFileUDType();
        estremiPerFile.setNomeFile(fileName);
        estremi.setEstremixIdentificazioneFileUD(estremiPerFile);
        it.wego.cross.client.xml.extractone.ObjectFactory of = new it.wego.cross.client.xml.extractone.ObjectFactory();
        JAXBElement<it.wego.cross.client.xml.extractone.EstremiXidentificazioneVerDocType> root = of.createFileUDToExtract(estremi);
        String xml = Utils.marshall(root, it.wego.cross.client.xml.extractone.EstremiXidentificazioneVerDocType.class
        );
        return xml;
    }
    
    private String getXmlRecuperoDaUnitaDocumentale(String unitaDocumentale, String nomeFile) throws Exception {
//        String unitaDocumentale = idFileEsterno.split("\\|")[1];
//        String nomeFile = idFileEsterno.split("\\|")[2];
        it.wego.cross.client.xml.extractone.EstremiXidentificazioneVerDocType estremiIdentificazioneVerDoc = new it.wego.cross.client.xml.extractone.EstremiXidentificazioneVerDocType();
        //Estremi identificazione Unita documentale
        it.wego.cross.client.xml.extractone.EstremiXIdentificazioneUDType estremiIdentificazioneUD = new it.wego.cross.client.xml.extractone.EstremiXIdentificazioneUDType();
        estremiIdentificazioneUD.setIdUD(new BigInteger(unitaDocumentale));
        //Estremi identificazione file
        it.wego.cross.client.xml.extractone.EstremiXIdentificazioneFileUDType estremiIdentificazioneFile = new it.wego.cross.client.xml.extractone.EstremiXIdentificazioneFileUDType();
        estremiIdentificazioneFile.setNomeFile(nomeFile);
        estremiIdentificazioneVerDoc.setEstremiXIdentificazioneUD(estremiIdentificazioneUD);
        estremiIdentificazioneVerDoc.setEstremixIdentificazioneFileUD(estremiIdentificazioneFile);
        it.wego.cross.client.xml.extractone.ObjectFactory of = new it.wego.cross.client.xml.extractone.ObjectFactory();
        JAXBElement<it.wego.cross.client.xml.extractone.EstremiXidentificazioneVerDocType> root = of.createFileUDToExtract(estremiIdentificazioneVerDoc);
        String xml = Utils.marshall(root, EstremiXidentificazioneVerDocType.class
        );
        return xml;
    }
    
    private Map<String, String> getAurigaMetadataMap() throws IOException {
        InputStream is = getClass().getResourceAsStream("/auriga_mime_type.properties");
        Properties props = new Properties();
        props.load(is);
        ImmutableMap<String, String> map = Maps.fromProperties(props);
        return map;
    }
    
}
