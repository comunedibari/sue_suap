package it.wego.cross.fvg;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.wego.cross.utils.PdfAttachmentUtils;
import it.wego.cross.utils.XmlUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuapFvgXmlTransformationTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static byte[] templateData;
    private static Map<String, byte[]> fileData;

    @BeforeClass
    public static void initClass() throws IOException {
        templateData = IOUtils.toByteArray(SuapFvgXmlTransformationTest.class.getResourceAsStream("/template.xslt"));
        Preconditions.checkNotNull(templateData);
        fileData = Collections.unmodifiableMap(Maps.newLinkedHashMap(Maps.asMap(Sets.newLinkedHashSet(Arrays.asList("GDSFRC74H26L483F-SUAP-299252176888100.pdf", "GDSFRC74H26L483F-SUAP-299413971869752.pdf", "GDSFRC74H26L483F-SUAP-299431747491810.pdf")), new Function<String, byte[]>() {

            @Override
            public byte[] apply(String fileName) {
                try {
                    return IOUtils.toByteArray(SuapFvgXmlTransformationTest.class.getResourceAsStream("/" + fileName));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        })));
    }

    @Test
    public void testXmlExtractAndTransform() throws Exception {
        logger.info("testXmlExtractAndTransform");
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        for (Map.Entry<String, byte[]> pdfFile : fileData.entrySet()) {
            File extractedFile = new File(tempDir, pdfFile.getKey() + "_extracted.xml"), transformedFile = new File(tempDir, pdfFile.getKey() + "_transformed.xml");
            logger.info("processing file {}, extracted to {}, transformed to {}", pdfFile.getKey(), extractedFile, transformedFile);
            byte[] xmlData = PdfAttachmentUtils.getPdfAttachmentsFromPdfFileData(pdfFile.getValue()).get("managedData.xml");
            FileUtils.writeByteArrayToFile(extractedFile, xmlData);
            String result = XmlUtils.doXmlTransformation(templateData, xmlData);
            Preconditions.checkNotNull(Strings.emptyToNull(result));
            FileUtils.writeStringToFile(transformedFile, result);
        }
        logger.info("testXmlExtractAndTransform : OK");
    }
}
