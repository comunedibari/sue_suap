package utils;
//import com.jcraft.jsch.*;
//
//import java.io.*;
//
public class SFTPFileReader {
//
//	//	String remoteFile = "/usr/local/apache-tomcat-6.0.41/cross_files/attachments/DMBMLN02S59A662L-10062024-0820/DMBMLN02S59A662L-10062024-0820.SUAP.XML";  // Percorso remoto del file
//	private String xml;
//
//	public SFTPFileReader(String remoteFile) {
//		String remoteHost = "";
//		String username = ";
//		String password = "";
//
//		try {
//			JSch jsch = new JSch();
//			Session session = jsch.getSession(username, remoteHost, 22);
//			session.setPassword(password);
//			session.setConfig("StrictHostKeyChecking", "no");
//			session.connect();
//
////			Channel channel = session.openChannel("sftp");
//			channel.connect();
//			ChannelSftp sftpChannel = (ChannelSftp) channel;
//
//			InputStream stream = sftpChannel.get(remoteFile);
//			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//
//			StringBuilder sb = new StringBuilder();
//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				sb.append(line + " ");
//			}
//
//			reader.close();
//			sftpChannel.exit();
//			session.disconnect();
//			xml = sb.toString();
//		} catch (JSchException | SftpException | IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public String getXml() {
//		return xml;
//	}
//
//
//	//    public static void main(String[] args) {
//	//    	new SFTPFileReader();
//	//    }
}
