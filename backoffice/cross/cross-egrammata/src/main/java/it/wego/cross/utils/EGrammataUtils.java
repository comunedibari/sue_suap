/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author giuseppe
 */
public class EGrammataUtils {

    private static final Logger log = LoggerFactory.getLogger("plugin");

    public static String hashXML(String xml, String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        String stringToHash = xml + password;
        log.debug("Stringa da cui ricavare l'HASH: "+ stringToHash);
        md.update(stringToHash.getBytes());
        byte digest[] = md.digest();
        return Base64.encodeBase64String(digest);
    }
    
    public static String marshall(Object obj) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter st = new StringWriter();
        marshaller.marshal(obj, st);
        return st.toString();
    }
}
