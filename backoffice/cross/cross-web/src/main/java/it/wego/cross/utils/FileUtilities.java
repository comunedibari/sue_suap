package it.wego.cross.utils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;

public abstract class FileUtilities {

	private FileUtilities() {}

	public static synchronized File extractTxtFileFromP7M(File p7mFile) throws Exception {
		byte[] p7mFileByteArray = fromFileToByteArray(p7mFile);
		return extractTxtFileFromP7M(p7mFile.getParent(), p7mFileByteArray);
	}
	
	public static synchronized File extractTxtFileFromP7M(String path, byte[] p7mFileByteArray) throws Exception {
		byte[] extractedFileByteArray = getData(p7mFileByteArray);
		File extractedFile = new File(path+"\\tempp7m");
		FileUtils.writeByteArrayToFile(extractedFile, extractedFileByteArray);
		return extractedFile;
	}
	
	
	public static synchronized byte[] extractByteFromP7M(String path, byte[] p7mFileByteArray) throws Exception {
		byte[] extractedFileByteArray = getData(p7mFileByteArray);
				return extractedFileByteArray;
	}
	
	private static byte[] fromFileToByteArray(File file) {
		try {
			return FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			Log.APP.error("Error while reading .p7m file!", e);
		}
		return new byte[0];
	}

	private static byte[] getData(final byte[] p7bytes) {
		CMSSignedData cms = null;
		try {
			cms = new CMSSignedData(p7bytes);
		} catch (CMSException e) {
			Log.APP.error("Error while converting bytes to CMSSignedData : " + e.getMessage(), e);
		}
		if( cms == null || cms.getSignedContent() == null) {
			return new byte[0];
		}
		return (byte[]) cms.getSignedContent().getContent();
	} 
	
	public static String getParent(String path) {
		String ret = path;
		Matcher m =Pattern.compile("^(.+)\\\\").matcher(path);
		if(m.find())
			ret = m.group(1);
		return ret;
			
	}
}
