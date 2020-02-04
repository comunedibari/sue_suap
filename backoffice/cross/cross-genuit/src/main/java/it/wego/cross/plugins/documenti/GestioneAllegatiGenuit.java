package it.wego.cross.plugins.documenti;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.axis.attachments.AttachmentPart;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.asitech.webservice.protocollo.Field;
import it.asitech.webservice.protocollo.PRIGetSidResponse;
import it.asitech.webservice.protocollo.PRIObjectOut;
import it.asitech.webservice.protocollo.ProtocolloServiceSoapBindingStub;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.plugins.GestioneAbstractGenuit;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.protocollo.GestioneProtocolloGenuit;
import it.wego.cross.utils.GenuitUtils;
import it.wego.cross.utils.Utils;

@Repository
public class GestioneAllegatiGenuit extends GestioneAbstractGenuit implements it.wego.cross.plugins.documenti.GestioneAllegati {

    private static final Logger log = LoggerFactory.getLogger(GestioneProtocolloGenuit.class);

    @Override
    public byte[] getFileContent(Allegati allegato, Enti ente, LkComuni comune) throws Exception {
        if (!Utils.e(allegato.getIdFileEsterno())) {
            Allegato a = getFile(allegato.getIdFileEsterno(), ente, comune);
            return a.getFile();
        } else if (!Utils.e(allegato.getFile())) {
            return allegato.getFile();
        } else if (!Utils.e(allegato.getPathFile())) {
            File f = new File(allegato.getPathFile());
            InputStream stream = new FileInputStream(f);
            return IOUtils.toByteArray(stream);
        } else {
            return null;
        }
    }

    @Override
    public void add(Allegati allegato, Enti ente, LkComuni comune) throws Exception {
        if (allegato.getIdFileEsterno() != null) {
            allegato.setPathFile(null);
            allegato.setFile(null);
        }
    }

    @Override
    public Allegato getFile(String idFileEsterno, Enti ente, LkComuni comune) throws Exception {
        ProtocolloServiceSoapBindingStub service = getService(ente, comune);
        PRIGetSidResponse priGetSid = null;
        try {
            priGetSid = service.PRIGetSid(user, password);
            if (priGetSid == null || StringUtils.isEmpty(priGetSid.getSid())) {
                throw new Exception("Impossibile stabilire la connessione con il protocollo. Errore: " + ((priGetSid == null) ? "SID NULL" : GenuitUtils.serializeResponseMessagge(priGetSid.getReturnCode())));
            }
            Integer idFile = Integer.valueOf(idFileEsterno);
            PRIObjectOut fileProtocollo = service.PRIGetFile(priGetSid.getSid(), this.user, idFile);
            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm").setPrettyPrinting().create();
            log.trace("REQUEST:\n\n" + gson.toJson(fileProtocollo));
            Field[] fieldsFileProtocollo = fileProtocollo.getPriObj().getFields();
            if (GenuitUtils.hasError(fileProtocollo.getReturnCode())) {
                throw new Exception("Impossibile recuperare il file dal protocollo. Errore : " + GenuitUtils.serializeResponseMessagge(fileProtocollo.getReturnCode()));
            }
            Object[] attachmentArray = service.getAttachments();
            if (attachmentArray.length == 0) {
                throw new Exception("Nessun file allegato alla risposta. Messaggio risposta : " + GenuitUtils.serializeResponseMessagge(fileProtocollo.getReturnCode()));
            }
            if (attachmentArray.length > 1) {
                throw new Exception("Ci sono più file allegati alla risposta. Messaggio di risposta : " + GenuitUtils.serializeResponseMessagge(fileProtocollo.getReturnCode()));
            }
            if (!(attachmentArray[0] instanceof AttachmentPart)) {
                throw new Exception("L'allegato restituito non è un allegato... Messaggio di risposta : " + GenuitUtils.serializeResponseMessagge(fileProtocollo.getReturnCode()));
            }
            AttachmentPart attachment = (AttachmentPart) attachmentArray[0];
            ByteArrayOutputStream fileByteArrayOutputStream = new ByteArrayOutputStream();
            attachment.getDataHandler().writeTo(fileByteArrayOutputStream);
            byte[] fileByteArray = fileByteArrayOutputStream.toByteArray();
            Allegato allegato = new Allegato();
            allegato.setIdEsterno(String.valueOf(idFile));
            allegato.setFile(fileByteArray);
            Object[] descrizioneFields = GenuitUtils.getFieldValue(fieldsFileProtocollo, "DESCRIZIONE");
            allegato.setDescrizione(descrizioneFields[0].toString());
            Object[] fileNameFields = GenuitUtils.getFieldValue(fieldsFileProtocollo, "FILE_NAME");
            allegato.setNomeFile(fileNameFields[0].toString());
            Object[] contentTypeFields = GenuitUtils.getFieldValue(fieldsFileProtocollo, "CONTENT_TYPE");
            allegato.setTipoFile(contentTypeFields[0].toString());
            return allegato;
        } finally {
            service.clearAttachments();
            if (priGetSid != null && !StringUtils.isEmpty(priGetSid.getSid())) {
                service.PRIEndSession(priGetSid.getSid());
            }
        }
    }


    @Override
    public String uploadFile(Pratica pratica, List<Allegati> allegato) throws Exception {
        //Non è abilitato l'evento di caricamento file. Skip ...
        return null;
    }

    @Override
    public String uploadAttachmentsOnMyPage(Pratica pratica, List<it.wego.cross.entity.Allegati> allegati) throws Exception {
        //NOT ALREADY SUPPORTED
        return "";
    }
}
