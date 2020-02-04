package it.wego.people.simpledesk;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
import it.wego.xml.XmlUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 *
 * @author aleph
 */
public class PdfFormReader {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PdfReader reader;
    private Supplier<Document> metadataSupplier = Suppliers.memoize(new Supplier<Document>() {

        public Document get() {
            byte[] metadata = null;
            try {
                metadata = reader.getMetadata();
                if (metadata == null) {
                    logger.info("null metadata");
                    return null;
                }
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                return documentBuilder.parse(new ByteArrayInputStream(metadata));
            } catch (Exception ex) {
                logger.warn("error reading xml data {}", metadata == null ? null : new String(metadata));
                throw new RuntimeException(ex);
            }
        }
    });
    private final XmlUtils xmlUtils;

    public PdfFormReader(PdfReader reader) throws Exception {
        this.reader = reader;
        xmlUtils = XmlUtils.newInstance();
    }

    public PdfFormReader(InputStream in) throws Exception {
        this(new PdfReader(in));
    }

    public PdfFormReader(byte[] data) throws Exception {
        this(new PdfReader(data));
    }
    private Supplier<Map<String, String>> fieldsSupplier = Suppliers.memoize(new Supplier<Map<String, String>>() {

        public Map<String, String> get() {
            return Maps.transformValues(reader.getAcroFields().getFields(), FieldValueToStringFunc.INSTANCE);
        }
    });

    private Map<String, String> readAllPdfFields() {
        Map<String, String> res = fieldsSupplier.get();
        logger.debug("got {} total fields", res.size());
        return res;
    }

    public Map<String, String> readPdfFields() {
        Map<String, String> res = Maps.filterKeys(readAllPdfFields(), Predicates.not(SimpleDeskUtils.getHiddenFieldPredicate()));
        logger.debug("got {} pdf fields", res.size());
        return res;
    }

    @Deprecated
    public Map<String, String> readHiddenData() {
        Map<String, String> res = Maps.newHashMap();
        for (Entry<String, String> entry : Maps.filterKeys(readAllPdfFields(), SimpleDeskUtils.getHiddenFieldPredicate()).entrySet()) {
            res.put(entry.getKey().replaceFirst("^" + PdfFormConstants.HIDDEN_DATA_PREFIX, ""), entry.getValue());
        }
//        res.entrySet().addAll(Collections2.transform(Maps.filterKeys(readAllPdfFields(), SimpleDeskUtils.getHiddenFieldPredicate()).entrySet(),new Function<Entry<String,String>,Entry<String,String>>(){
//
//            public Entry<String, String> apply(final Entry<String, String> input) {
//                final String key=input.getKey().replaceFirst("^" + PdfFormConstants.HIDDEN_DATA_PREFIX, "");
//                return new Entry<String, String>(){
//
//                    public String getKey() {
//                        return key;
//                    }
//
//                    public String getValue() {
//                        return input.getValue();
//                    }
//
//                    public String setValue(String value) {
//                        return input.setValue(value);
//                    }
//                    
//                };
//            }
//        }));
        logger.debug("got {} hidden data", res.size());
        return res;
    }

    public String getHiddenData(String key) {
        return readAllPdfFields().get(PdfFormConstants.HIDDEN_DATA_PREFIX + key);
    }

    public PdfReader getReader() {
        return reader;
    }

    public Document getMetadata() {
        return metadataSupplier.get();
    }
//    public static final String METADATA_XPATH_ROOT = "/xmpmeta/RDF/Description/";

    public Iterable<String> getMetadata(String xpath) throws XPathExpressionException, ExecutionException {
        return xmlUtils.getStrings(xpath, getMetadata());
    }

    public void close() {
        reader.close();
    }

//    private static enum HiddenDataKeyFunction implements EntryTransformer<String, String, String> {
//
//        INSTANCE;
//
//        public String transformEntry(String key, String value) {
//            throw new UnsupportedOperationException("Not supported yet.");
//        }
//    }
//    private static enum HiddenDataKeyTransformer implements Function<String, String> {
//
//        INSTANCE;
//
//        public String apply(String input) {
//            return input.replaceFirst("^" + PdfFormConstants.HIDDEN_DATA_PREFIX, "");
//        }
//    }
    /**
     * note:  converte 'Off' -> null, per gestire correttamente le checkbox
     */
    private static enum FieldValueToStringFunc implements Function<Item, String> {

        INSTANCE;

        public String apply(Item input) {
            PdfDictionary value = input.getValue(0);
            PdfString asString = value.getAsString(PdfName.V);
            if (asString != null) {
                return asString.toUnicodeString();
            }
            PdfName asName = value.getAsName(PdfName.V);
            return (asName == null || Objects.equal(asName, PdfName.Off)) ? null : PdfName.decodeName(new String(asName.getBytes()));
        }
    }
}
