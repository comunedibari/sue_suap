/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.constants.Constants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dao.*;
import it.wego.cross.utils.Utils;
import it.wego.cross.webservices.client.Reporter.stubs.Reporter;
import it.wego.cross.webservices.client.Reporter.stubs.ReporterPortType;
import it.wego.cross.xsd.reporter.documentoutputtype.OutputTypeRoot;
import java.net.URL;
import javax.xml.ws.BindingProvider;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabri
 */
@Service
public class DocEngineServiceImpl implements DocEngineService {

    @Autowired
    ConfigurationService configurationService;

    @Override
    public byte[] createDocument(String xmlData, byte[] templateByteArray, String documentOutputType) throws Exception {
        Base64 decoder = new Base64();

        OutputTypeRoot documentXmlOutputType = new OutputTypeRoot();
        if (documentOutputType.equalsIgnoreCase("PDF")) {
            documentXmlOutputType.setOutputFormat("PDF");
        } else if (documentOutputType.equalsIgnoreCase("DOC")) {
            documentXmlOutputType.setOutputFormat("DOC");
        } else {
            documentXmlOutputType.setOutputFormat("RTF");
        }

        String documentStringOutput = Utils.marshall(documentXmlOutputType);

        String reporterClientUrl = configurationService.getCachedConfiguration(SessionConstants.REPORTER_CLIENT_URL,null,null);
        Reporter service = new Reporter(new URL(reporterClientUrl+"?wsdl"));
        ReporterPortType port = service.getReporterSOAP11PortHttp();
        
        if (StringUtils.isEmpty(reporterClientUrl)) {
            throw new Exception("Chiave '" + SessionConstants.REPORTER_CLIENT_URL + "' non trovata nella tabella Configurations.");
        }
        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, reporterClientUrl);
        
        return port.generateDocument(decoder.decode(templateByteArray), new byte[0], xmlData.getBytes(), new byte[0], documentStringOutput.getBytes());
    }
}
