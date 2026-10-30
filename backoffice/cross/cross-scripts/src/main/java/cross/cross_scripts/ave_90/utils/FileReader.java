package cross.cross_scripts.ave_90.utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {

	//	String remoteFile = "/usr/local/apache-tomcat-6.0.41/cross_files/attachments/DMBMLN02S59A662L-10062024-0820/DMBMLN02S59A662L-10062024-0820.SUAP.XML";  // Percorso remoto del file
	private String xml;

	public FileReader(String pathFile) {

		try {

		    byte[] bytes = Files.readAllBytes(Paths.get(pathFile));
		    xml = new String (bytes);
		} catch (IOException e) {
		    //handle exception
		}
	}

	public String getXml() {
		return xml;
	}
}
