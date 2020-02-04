/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.reporter.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Piergiorgio
 */
public class ConfigUtil {

    private static FileInputStream file;
    private static Properties properties;
    private static Log log = LogFactory.getLog(ConfigUtil.class);

    private void init() {
        try {

            properties=new Properties();
            InputStream is1=getClass().getClassLoader().getResourceAsStream("resource.properties");
            properties.load(is1);

        } catch (Exception e){
            log.error("Errore",e);
        }
    }

    public Properties getInstance(){
        if (properties == null){
            init();
        }
        return properties;
    }


}
