package it.wego.cross.test;

import com.google.common.base.Preconditions;
import it.wego.cross.utils.PdfAttachmentUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import org.apache.commons.compress.utils.IOUtils;
import org.bouncycastle.cms.CMSException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfAttachmentTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static byte[] p7mFileData, pdfFileData, xmlFileData;

    @BeforeClass
    public static void initClass() throws IOException {
        p7mFileData = IOUtils.toByteArray(PdfAttachmentTest.class.getResourceAsStream("/file.pdf.p7m"));
        Preconditions.checkNotNull(p7mFileData);
        pdfFileData = IOUtils.toByteArray(PdfAttachmentTest.class.getResourceAsStream("/file.pdf"));
        Preconditions.checkNotNull(pdfFileData);
        xmlFileData = IOUtils.toByteArray(PdfAttachmentTest.class.getResourceAsStream("/file.xml"));
        Preconditions.checkNotNull(xmlFileData);
    }

    @Test
    public void testP7mToPdfRead() throws CMSException, IOException {
        logger.info("testP7mToPdfRead");
        byte[] newPdfFileData = PdfAttachmentUtils.getPdfFromP7m(p7mFileData);
        Preconditions.checkNotNull(newPdfFileData);
        Preconditions.checkArgument(Arrays.equals(newPdfFileData, pdfFileData));
        logger.info("testP7mToPdfRead : OK");
    }

    @Test
    public void testP7mAttachmentRead() throws Exception {
        logger.info("testP7mAttachmentRead");
        Map<String, byte[]> attachmentMap = PdfAttachmentUtils.getPdfAttachmentsFromP7mFileData(p7mFileData);
        logger.info("testP7mAttachmentRead attachments = {}", attachmentMap);
        Preconditions.checkNotNull(attachmentMap);
        Preconditions.checkArgument(!attachmentMap.isEmpty());
        Preconditions.checkArgument(Arrays.equals(attachmentMap.get("managedData.xml"), xmlFileData));
        logger.info("testP7mAttachmentRead : OK");
    }
}
