package ave_58;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.core.Is;

public class TestNewBasePath {
	
	@Test
	public void testAttachment() {
		DiskPathHandlerExt diskPath = new DiskPathHandlerExt(null);
		String documentBase = "/usr/local/apache-tomcat-6.0.41/cross_files/documents";
		String attachmentBase = "/usr/local/apache-tomcat-6.0.41/cross_files/attachments";
		
		String newDocumentBase = diskPath.modifyBase(documentBase, "try");
		assertThat(newDocumentBase, Is.is("/usr/local/apache-tomcat-6.0.41/cross_files\\try\\documents"));
		
		String newattachmentBase = diskPath.modifyBase(attachmentBase, "try");
		assertThat(newattachmentBase, Is.is("/usr/local/apache-tomcat-6.0.41/cross_files\\try\\attachments"));
	}
}
