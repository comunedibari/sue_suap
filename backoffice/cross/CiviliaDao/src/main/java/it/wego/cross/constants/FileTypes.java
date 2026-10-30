/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author GabrieleM
 */
public class FileTypes {

    public static final Map<String, String> myMap;

    static {
        Map<String, String> aMap = new HashMap<String, String>();
        aMap.put("default", "filebianco ");
        aMap.put("application/octet-stream", "filebianco");
        aMap.put("application/pdf", "filepdf");
        aMap.put("application/msword", "fileword");
        aMap.put("text/plain", "fileword");
        aMap.put("image/png", "fileimmagine");
        aMap.put("image/jpg", "fileimmagine");
        aMap.put("application/vnd.oasis.opendocument.text", "fileword");
        aMap.put("application/pkcs7-mime", "filefirmato");
        myMap = Collections.unmodifiableMap(aMap);
    }
}
