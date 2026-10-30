/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.activation.DataHandler;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author giuseppe
 */
public class AurigaUtils {

    private static final Logger log = LoggerFactory.getLogger("plugin");
    private static final int INITIAL_SIZE = 1024 * 1024;
    private static final int BUFFER_SIZE = 1024;

    public static String hashXML(String xml, String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        String stringToHash = xml + password;
        log.debug("Stringa da cui ricavare l'HASH: " + stringToHash);
        md.update(stringToHash.getBytes());
        byte digest[] = md.digest();
        return Base64.encodeBase64String(digest);
    }

    public static String decodeXml(String r) {
        byte[] digest = Base64.decodeBase64(r);
        return new String(digest);
    }

    public static boolean isUploaded(String s) {
        if (s != null && s.contains("|")) {
            String[] splitted = s.split("\\|");
            return splitted[0].equals("D");
        } else {
            return false;
        }
    }

    public static byte[] toBytes(DataHandler dh) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(INITIAL_SIZE);
        InputStream in = dh.getInputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) >= 0) {
            bos.write(buffer, 0, bytesRead);
        }
        return bos.toByteArray();
    }

}
