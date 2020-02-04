package it.wego.people.simpledesk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aleph
 */
public class ProcessDataPdfService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Gson gson = new Gson();
    private final Type type = new TypeToken<Map<String, String>>() {
    }.getType();

    public String process(String data) throws Exception {
        try {
            Map<String, String> dataMap = gson.fromJson(data, type);
            String method = dataMap.get("method");
            Validate.notNull(method, "method param is missing");
            String processDataBlob = dataMap.get("processData");
            Validate.notNull(processDataBlob, "process data param is missing");
            byte[] processData = Base64.decodeBase64(processDataBlob);
            if (method.equals("xmlToPdf")) {
                return Base64.encodeBase64String(new ProcessDataPdfProcessor().xml2pdf(processData));
            } else if (method.equals("mergePdf")) {
                String pdfBlob = dataMap.get("pdfFile");
                Validate.notNull(pdfBlob, "pdf data param is missing");
                byte[] pdfFile = Base64.decodeBase64(pdfBlob);
                return Base64.encodeBase64String(new ProcessDataPdfProcessor().fillXmlFromPdf(processData, pdfFile, false));
            } else if (method.equals("mergePdfForced")) {
                String pdfBlob = dataMap.get("pdfFile");
                Validate.notNull(pdfBlob, "pdf data param is missing");
                byte[] pdfFile = Base64.decodeBase64(pdfBlob);
                return Base64.encodeBase64String(new ProcessDataPdfProcessor().fillXmlFromPdf(processData, pdfFile, true));
            } else {
                throw new Exception("unknown method : " + method);
            }
        } catch (Exception ex) {
//            logger.error("error processing request", ex);
//            throw ex;
            logger.error("error processing request", ex);
            String message = (new StringBuilder()).append("Error : ").append(ex.getMessage()).toString();
            logger.info("retuning error message  = {}", message);
            return message;
        }
    }
}
