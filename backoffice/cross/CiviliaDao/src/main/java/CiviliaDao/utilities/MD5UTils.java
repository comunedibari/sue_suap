package CiviliaDao.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5UTils {


	private static final Charset UTF_8 = StandardCharsets.UTF_8;
	private static final String OUTPUT_FORMAT = "%-20s:%s";

	private static byte[] checksum(String filePath) {

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}

		try (InputStream is = new FileInputStream(filePath);
				DigestInputStream dis = new DigestInputStream(is, md)) {
			while (dis.read() != -1) ; //empty loop to clear the data
			md = dis.getMessageDigest();
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		return md.digest();

	}

	private static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
	
	public String getMD5Value(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return result;
    }
	
	private String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

	public static void main(String[] args) {

		String file = "c:\\test\\readme.txt";
		System.out.println(String.format(OUTPUT_FORMAT, "Input (file) ", file));
		System.out.println(String.format(OUTPUT_FORMAT, "MD5 (checksum hex) ", bytesToHex(checksum(file))));

	}

}