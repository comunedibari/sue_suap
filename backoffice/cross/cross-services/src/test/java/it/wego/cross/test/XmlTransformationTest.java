package it.wego.cross.test;

import com.google.common.base.Preconditions;
import it.wego.cross.utils.XmlUtils;
import java.io.IOException;
import javax.xml.transform.TransformerException;
import org.apache.commons.compress.utils.IOUtils;
import org.bouncycastle.cms.CMSException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTransformationTest {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private static byte[] sourceXml, targetXml, xmlTemplate;
    
    @BeforeClass
    public static void initClass() throws IOException {
        sourceXml = IOUtils.toByteArray(XmlTransformationTest.class.getResourceAsStream("/sourceXml.xml"));
        Preconditions.checkNotNull(sourceXml);
        targetXml = IOUtils.toByteArray(XmlTransformationTest.class.getResourceAsStream("/targetXml.xml"));
        Preconditions.checkNotNull(targetXml);
        xmlTemplate = IOUtils.toByteArray(XmlTransformationTest.class.getResourceAsStream("/xmlTemplate.xml"));
        Preconditions.checkNotNull(xmlTemplate);
    }
    
    @Test
    public void testXmlTransformation() throws CMSException, IOException, TransformerException {
        logger.info("testXmlTransformation");
        String result = XmlUtils.doXmlTransformation(xmlTemplate, sourceXml);
        Preconditions.checkNotNull(result);
        logger.info("testXmlTransformation result = {}", result);
        Preconditions.checkArgument(result.contains("<n_iscrizione_ri>01425360938</n_iscrizione_ri>"));
//        Preconditions.checkArgument(Objects.equals(result, new String(targetXml))); TODO
        logger.info("testXmlTransformation : OK");
    }
    
}
