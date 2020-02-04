/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import com.google.common.base.Preconditions;
import it.wego.cross.utils.FileUtils;
import java.io.IOException;
import org.bouncycastle.cms.CMSException;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author aleph
 */
public class TestFileUtils {

    @Test
    public void isSigned() throws IOException, CMSException, Exception {

        MultipartFile file = new MockMultipartFile("file1.pdf.p7m", "file1.pdf.p7m", "", getClass().getResourceAsStream("/file1.pdf.p7m"));
        Preconditions.checkArgument(FileUtils.isSigned(file));

        file = new MockMultipartFile("0_Procura.pdf.p7m", "file1.pdf.p7m", "", getClass().getResourceAsStream("/0_Procura.pdf.p7m"));
        Preconditions.checkArgument(FileUtils.isSigned(file));
        
        file = new MockMultipartFile("0_Procura.pdf.p7m", "0_Procura.pdf.p7m", "", getClass().getResourceAsStream("/0_Procura.pdf.p7m"));
        Preconditions.checkArgument(FileUtils.isSigned(file));
        
        file = new MockMultipartFile("fakep7m.pdf.p7m", "fakep7m.pdf.p7m", "", getClass().getResourceAsStream("/fakep7m.pdf.p7m"));
        Preconditions.checkArgument(!FileUtils.isSigned(file));

    }
}
