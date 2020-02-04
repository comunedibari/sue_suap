package it.wego.people.simpledesk;

import it.wego.people.simpledesk.processdata.HrefList;
import it.wego.xml.XmlUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 *
 * @author aleph
 */
public class SimpleDeskTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final File testDir = new File(System.getProperty("it.wego.testDir", "/tmp/simpleDeskTest"));
    private XmlUtils xmlUtils;

    @Before
    public void init() throws Exception {
        if (!testDir.isDirectory()) {
            testDir.mkdirs();
        }
        Assume.assumeTrue(testDir.canRead() && testDir.canWrite());
        xmlUtils = XmlUtils.newInstance();
        logger.info("-- begin test --");
    }

    @After
    public void dispose() throws Exception {
        logger.info("-- end test --");
    }

    @Test
    public void testXmlMetaPdfReading() {
        File in = new File(testDir, "simpledesk_meta.pdf");
        if (!in.canRead()) {
            logger.info("skip testing reading xml meta from pdf file {} ", in);
            Assume.assumeTrue(false);
        }
        logger.info("testing reading xml meta from pdf file {} ", in);
        try {
            PdfFormReader reader = new PdfFormReader(new FileInputStream(in));
            Document metadata = reader.getMetadata();
            logger.info("got xml metadata \n\n{}", xmlUtils.xmlToString(metadata));
        } catch (Throwable ex) {
            logger.error("error while reading xml meta from pdf file", ex);
            Assert.fail();
        }
    }

    @Test
    public void testXml2pdf() {
        File in = new File(testDir, "processData_in.xml"), out = new File(testDir, "processData_out.pdf");
        if (!in.canRead()) {
            logger.info("skip testing xml to pdf from file {} to file {}", in, out);
            Assume.assumeTrue(false);
        }
        if (out.exists()) {
            out.delete();
        }
        logger.info("testing xml to pdf from file {} to file {}", in, out);
        try {
            new ProcessDataPdfProcessor().xml2pdf(new FileInputStream(in), new FileOutputStream(out));
        } catch (Throwable ex) {
            logger.error("error while transforming xml to pdf", ex);
            Assert.fail();
        }
    }

//    @Test
//    public void testPdfWriter() throws Exception {
//        Assume.assumeTrue(false); //skip test
////        File file = File.createTempFile("simpledesk_test_", ".pdf");
//        File file = new File(testDir, "simpledesk_out.pdf");
//        if (file.exists()) {
//            file.delete();
//        }
//        logger.info("testing pdf creation to file {}", file);
//        try {
//            new PdfFormBuilder(new FileOutputStream(file)).beginTable(4).appendTextField("fieldLabel1", "fieldName1", "fieldValue1").appendTextField("fieldLabel2", "fieldName2", "fieldValue2").appendTextField("fieldLabel3", "fieldName3", "fieldValue3").endTableRow().endTableRow().appendHtmlCell("<b>HELO</b>").endTableRow().endTableRow().appendRadioField("fieldLabel4a", "fieldName4", "fieldValue4a", true).appendRadioField("fieldLabel4b", "fieldName4", "fieldValue4b", false).endTableRow().endTableRow().appendCheckboxField("fieldLabel5", "fieldName5", "fieldValue5", true).appendCheckboxField("fieldLabel6", "fieldName6", "fieldValue6", false).close(PdfFormBuilder.CLOSE_OUTPUTSTREAM);
//            logger.info("created pdf file {}", file);
//        } catch (Throwable ex) {
//            logger.error("error while creating pdf", ex);
//            Assert.fail();
//        }
//    }
    @Test
    public void testPdfReader() throws Exception {
        File file = new File(testDir, "simpledesk_in.pdf");
        if (!file.canRead()) {
            logger.info("skip testing pdf field read from file {}", file);
            Assume.assumeTrue(false);
        }
//        Assume.assumeTrue(false); //skip test
        logger.info("testing pdf field read from file {}", file);
        Map<String, String> fields = SimpleDeskUtils.readPdfFields(new FileInputStream(file));
        logger.info("got fields = {} from file = {}", fields, file);
    }

    @Test
    public void testPdfHiddenMetaReader() throws Exception {
        File file = new File(testDir, "simpledesk_in.pdf");
        if (!file.canRead()) {
            logger.info("skip testing pdf hidden data read from file {}", file);
            Assume.assumeTrue(false);
        }
//        Assume.assumeTrue(false); //skip test
        logger.info("testing pdf hidden data read from file {}", file);
        try {
            Map<String, String> fields = SimpleDeskUtils.readHiddenData(new FileInputStream(file));
            logger.info("got hidden data = {} from file = {}", fields, file);
        } catch (Throwable ex) {
            logger.error("error while reading hidden data", ex);
            Assert.fail();
        }
    }

    @Test
    public void testHrefJaxb() throws Exception {
        File in = new File(testDir, "listaHref_in.xml");
        if (!in.canRead()) {
            logger.info("skip testing xml href jaxb binding");
            Assume.assumeTrue(false);
        }
        logger.info("testing xml href jaxb binding");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(HrefList.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            logger.info("reading xml from {}", in);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(in);
            HrefList listaHref = jaxbUnmarshaller.unmarshal(document, HrefList.class).getValue();
            logger.info("gor object {}", listaHref);

            File out = new File(testDir, "listaHref_out.xml");
            logger.info("writing object to {}", out);
            jaxbMarshaller.marshal(listaHref, out);

            logger.info("test success, check {}", out);
        } catch (Throwable ex) {
            logger.error("error while testing xml href jaxb binding", ex);
            Assert.fail();
        }
    }

    @Test
    public void testXmlPdfHrefmerge() throws Exception {
        File xmlIn = new File(testDir, "processData_in.xml"),
                pdfIn = new File(testDir, "processData_in.pdf"),
                xmlOut = new File(testDir, "processData_out.xml");
        if (!(xmlIn.canRead() && pdfIn.canRead())) {
            logger.info("skip testing xml pdf href field merge {} {} -> {}", new Object[]{xmlIn, pdfIn, xmlOut});
            Assume.assumeTrue(false);
        }
        logger.info("testing xml pdf href field merge {} {} -> {}", new Object[]{xmlIn, pdfIn, xmlOut});
        try {
            new ProcessDataPdfProcessor().fillXmlFromPdf(new FileInputStream(xmlIn), new FileInputStream(pdfIn), new FileOutputStream(xmlOut), true);
            logger.info("test success, check {}", xmlOut);
        } catch (Throwable ex) {
            logger.error("error while testing xml pdf href field merge", ex);
            Assert.fail();
        }

    }
}
