package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.controllers;

import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Bean2XML;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;
import java.util.*;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.Validate;
import org.apache.struts.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author aleph
 */
public class CaricaModuloCompilato extends Action {

    private String encoding;
    private static Logger logger = LoggerFactory.getLogger(CaricaModuloCompilato.class);
    public static final String PARAMETER_UPLOADFILE = "uploadFile";
    public static final String PARAMETER_FORZATURA = "forzaCaricamento";
    private final static Map<String, Object> exceptions = new LinkedHashMap<String, Object>() {
        @Override
        protected boolean removeEldestEntry(Entry<String, Object> eldest) {
            return size() > 100;
        }
    };

    public static Object extractException(String id) {
        return exceptions.remove(id);
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        
        this.encoding = request.getCharacterEncoding();
        
        AbstractPplProcess process = (AbstractPplProcess) form;
        logger.debug("loading pdf module");
        try {
            FormFile formFile = (FormFile) PropertyUtils.getProperty(process.getData(), PARAMETER_UPLOADFILE);
            boolean forzaCaricamento = false;
            if (process.getMultipartRequestHandler().getTextElements().containsKey(PARAMETER_FORZATURA) 
                    && process.getMultipartRequestHandler().getTextElements().get(PARAMETER_FORZATURA) != null 
                    && ((String[]) process.getMultipartRequestHandler().getTextElements().get(PARAMETER_FORZATURA))[0].equals(PARAMETER_FORZATURA)) {
                forzaCaricamento = true;
                
            }
            byte[] pdf = formFile == null ? null : formFile.getFileData();
            PropertyUtils.setProperty(process.getData(), PARAMETER_UPLOADFILE, null);
            String newXmlBlob;
            ProcessData oldProcessData = (ProcessData) process.getData();
            if (pdf != null && pdf.length > 0) {
                final String wsUrl = "SIMPLEDESK_PD_WS";
                String pdfBlob = new String(Base64.encodeBase64(pdf)),
                        oldXml = Bean2XML.marshallObject(oldProcessData, this.encoding),
                        xmlBlob = new String(Base64.encodeBase64(oldXml.getBytes("UTF-8")));
                if (!forzaCaricamento){
                newXmlBlob = process.callService(wsUrl, "{'method':'mergePdf','processData':'" + xmlBlob + "','pdfFile':'" + pdfBlob + "'}");
                } else {
                    newXmlBlob = process.callService(wsUrl, "{'method':'mergePdfForced','processData':'" + xmlBlob + "','pdfFile':'" + pdfBlob + "'}");
                }
            } else {
                newXmlBlob = "Errore : e' necessario selezionare un file da caricare";
            }

            if (newXmlBlob.startsWith("Error")) {
                ActionForward forward = mapping.findForward("retry");
                ActionForward modifiedSuccessAction = new ActionForward(forward);
                String uuid = UUID.randomUUID().toString();
                exceptions.put(uuid, newXmlBlob);
                modifiedSuccessAction.setPath(forward.getPath() + "&errorMessageId=" + uuid);
                return modifiedSuccessAction;
            } else {
                String newXml = new String(Base64.decodeBase64(newXmlBlob.getBytes()), "UTF-8");
                ProcessData newProcessData = (ProcessData) Bean2XML.unmarshallObject(newXml, ProcessData.class, this.encoding);

                // copiamo solo i campi
                for (Iterator iterator = newProcessData.getListaHref().entrySet().iterator(); iterator.hasNext();) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String hrefName = entry.getKey().toString();
                    SezioneCompilabileBean newHref = (SezioneCompilabileBean) entry.getValue(),
                            oldHref = (SezioneCompilabileBean) oldProcessData.getListaHref().get(hrefName);
                    Map map = new HashMap();
                    for (Iterator campi = oldHref.getCampi().iterator(); campi.hasNext();) {
                        HrefCampiBean campo = (HrefCampiBean) campi.next();
                        map.put(campo.getNome() + "_" + campo.getContatore(), campo);
                    }
                    for (Iterator campi = newHref.getCampi().iterator(); campi.hasNext();) {
                        HrefCampiBean newCampo = (HrefCampiBean) campi.next(),
                                oldCampo = (HrefCampiBean) map.get(newCampo.getNome() + "_" + newCampo.getContatore());
                        oldCampo.setValoreUtente(newCampo.getValoreUtente());
                    }
                }

                return mapping.findForward("success");
            }
        } catch (Throwable ex) {
            logger.error("error loading pdf", ex);
            String uuid = UUID.randomUUID().toString();
            exceptions.put(uuid, ex);
            ActionForward forward = mapping.findForward("error");
            ActionForward modifiedSuccessAction = new ActionForward(forward);
            modifiedSuccessAction.setPath(forward.getPath() + "&errorMessageId=" + uuid);
            return modifiedSuccessAction;
        }
    }
}
