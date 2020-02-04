/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import com.google.common.base.Preconditions;
import it.wego.cross.exception.CrossException;
import it.wego.cross.utils.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriele
 */
@Service
public class SuapFvgServiceImpl implements SuapFvgService {

    private static final String SUAP_FG_XSLT = "plugin.suapfvg.xslt";
    @Autowired
    private ConfigurationService configurationService;

    @Override
    public String getXmlFromTemplate(byte[] xmlToTransform) throws Exception {
        InputStream bais = null;
        try {
            String templateBase64String = configurationService.getCachedPluginConfiguration(SUAP_FG_XSLT, null, null);
            Preconditions.checkArgument(templateBase64String != null && !templateBase64String.isEmpty(), "Impossibile trovare il template XSL per trasformare la pratica. Verificare la proprieta '%s' sulla tabella plugin_configuration.", SUAP_FG_XSLT);

            InputStream template = new ByteArrayInputStream(Base64.decodeBase64(templateBase64String.getBytes("UTF-8")));

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(template));
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            ByteArrayOutputStream writer = new ByteArrayOutputStream();
            bais = new ByteArrayInputStream(xmlToTransform);
            transformer.transform(new StreamSource(bais), new StreamResult(writer));
            String xmlOutput = new String(writer.toByteArray());
            return xmlOutput;
        } catch (Exception e) {
            Log.APP.error("",e);
            throw new CrossException("Impossibile importare la pratica. Contattare l'assistenza.");
        } finally {
            if (bais != null) {
                bais.close();
            }
        }
    }
}
