package it.wego.people.simpledesk;

import com.google.common.base.Predicate;
import com.itextpdf.text.pdf.PdfReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aleph
 */
public class SimpleDeskUtils {

    private static final Logger logger = LoggerFactory.getLogger(SimpleDeskUtils.class);
//
//    public static void buildPdf(OutputStream outputStream) throws Exception {
//        new PdfFormBuilder(outputStream).appendTextField("fieldLabel", "fieldName", "fieldValue").close();
//    }

    public static Map<String, String> readPdfFields(InputStream in) throws IOException, Exception {
        return readPdfFieldsAndClose(new PdfReader(in));
    }

    public static Map<String, String> readPdfFields(byte[] data) throws IOException, Exception {
        return readPdfFieldsAndClose(new PdfReader(data));
    }

    public static Map<String, String> readPdfFieldsAndClose(PdfReader reader) throws Exception {
        PdfFormReader pdfFormReader = new PdfFormReader(reader);
        Map<String, String> res = pdfFormReader.readPdfFields();
        pdfFormReader.close();
        return res;
    }

    public static Predicate<String> getHiddenFieldPredicate() {
        return HiddenFieldPredicate.INSTANCE;
    }

    public static Map<String, String> readHiddenData(InputStream in) throws IOException, Exception {
        PdfFormReader pdfFormReader = new PdfFormReader(in);
        Map<String, String> res = pdfFormReader.readHiddenData();
        pdfFormReader.close();
        return res;
    }

    private static enum HiddenFieldPredicate implements Predicate<String> {

        INSTANCE;

        public boolean apply(String input) {
            return input.startsWith(PdfFormConstants.HIDDEN_DATA_PREFIX);
        }
    }
}
