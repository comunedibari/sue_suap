/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import it.wego.cross.constants.StatiEmail;
import it.wego.cross.constants.Workflow;
import it.wego.cross.dao.StagingDao;
import it.wego.cross.entity.Email;
import it.wego.cross.entity.LkStatiMail;
import it.wego.cross.entity.Staging;
import it.wego.cross.service.MailService;
import it.wego.cross.upgradenewversion.UpgradeNewVersionService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Pratica;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.activiti.engine.RuntimeService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author piergiorgio
 */
@Component
public class UpdateNewVersionAction {

    @Autowired
    private UpgradeNewVersionService serviceDao;
    @Autowired
    private MailService mailService;
    @Autowired
    private WorkflowAction workflowAction;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private StagingDao stagingDao;
    @Autowired
    private Mapper mapper;

    @Transactional(rollbackFor = Exception.class)
    public void upgradeNewVersionRow(Integer key) throws Exception {
        Staging s = stagingDao.findByCodStaging(key);
        if (s.getXmlPratica() != null && s.getXmlPratica().length > 0) {
            byte[] updateObject = trasformaXml(s.getXmlPratica());
            s.setXmlPratica(updateObject);
            stagingDao.merge(s);

        }
    }

    private byte[] trasformaXml(byte[] xmlPraticaOld) throws Exception {
        it.wego.cross.xmlold.Pratica oldPratica = getPraticaFromXML(new String(xmlPraticaOld));
        Pratica newPratica = mapper.map(oldPratica, Pratica.class);
        if (oldPratica.getDatiCatastali() != null && oldPratica.getDatiCatastali().getImmobile() != null) {

            it.wego.cross.xml.IndirizziIntervento indirizziIntervento = new it.wego.cross.xml.IndirizziIntervento();
            for (it.wego.cross.xmlold.Immobile immobile : oldPratica.getDatiCatastali().getImmobile()) {
                it.wego.cross.xml.IndirizzoIntervento ii = new it.wego.cross.xml.IndirizzoIntervento();
                ii.setAltreInformazioniIndirizzo(immobile.getAltreInformazioniIndirizzo());
                ii.setCap(immobile.getCap());
                ii.setCivico(immobile.getCivico());
                ii.setCodCivico(immobile.getCodiceCivico());
                ii.setCodVia(immobile.getCodiceVia());
                ii.setColore(immobile.getColore());
                if (!Utils.e(immobile.getCounter())) {
                    ii.setCounter(immobile.getCounter().add(Utils.bi(1000)));
                }
                ii.setIdDug(immobile.getIdDug());
                ii.setIndirizzo(immobile.getIndirizzo());
                ii.setInternoLettera(immobile.getInternoLettera());
                ii.setInternoNumero(immobile.getInternoNumero());
                ii.setInternoScala(immobile.getInternoScala());
                ii.setLettera(immobile.getLettera());
                ii.setLocalita(immobile.getLocalita());
                ii.setLatitudine(immobile.getLatitudine());
                ii.setLongitudine(immobile.getLongitudine());
                if (!Utils.e(ii.getIndirizzo()) || !Utils.e(ii.getAltreInformazioniIndirizzo()) || !Utils.e(ii.getLatitudine())) {
                    indirizziIntervento.getIndirizzoIntervento().add(ii);
                }
                ii.setConfermato("true");
            }
            newPratica.setIndirizziIntervento(indirizziIntervento);
        }
        // raddrizza daticatastali
        if (newPratica.getDatiCatastali() != null && newPratica.getDatiCatastali().getImmobile() != null) {
            for (it.wego.cross.xml.Immobile immobile : newPratica.getDatiCatastali().getImmobile()) {
                immobile.setIdTipoUnita(immobile.getIdTipoSistemaCatastale());
                immobile.setIdTipoSistemaCatastale(BigInteger.ONE);
                immobile.setConfermato("true");
            }
        }
        return getXmlFromPraticaNew(newPratica);
    }

    public static it.wego.cross.xmlold.Pratica getPraticaFromXML(String xml) throws Exception {
        return getPraticaFromXML(xml.getBytes("UTF-8"));
    }

    public static it.wego.cross.xmlold.Pratica getPraticaFromXML(byte[] xmlBytes) throws Exception {
        it.wego.cross.xmlold.Pratica pratica = null;
        InputStream is = null;
        try {

            JAXBContext jc = JAXBContext.newInstance(it.wego.cross.xmlold.Pratica.class);
            Unmarshaller um = jc.createUnmarshaller();
            is = new ByteArrayInputStream(xmlBytes);
            pratica = (it.wego.cross.xmlold.Pratica) um.unmarshal(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return pratica;
    }

    public static byte[] getXmlFromPraticaNew(it.wego.cross.xml.Pratica pratica) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter sw = new StringWriter();
        marshaller.marshal(pratica, sw);
        return sw.toString().getBytes();
    }

    @Transactional(rollbackFor = Exception.class)
    public void startEmailProcess(Email email) throws Exception {
        Map<String, Object> variables = workflowAction.getVariabiliPratica(email.getIdPraticaEvento());
        Log.WORKFLOW.info("Avvio il flusso di invio email per la mail con ID " + email.getIdEmail());
        variables.put(Workflow.ID_EMAIL, email.getIdEmail());
        variables.put(Workflow.TIPO_TASK, Workflow.TIPO_TASK_TASK);
        Log.WORKFLOW.info("Avvio il processo per l'email con id " + email.getIdEmail());
        runtimeService.startProcessInstanceByKey("cross_send_mail_process", variables);
    }

}
