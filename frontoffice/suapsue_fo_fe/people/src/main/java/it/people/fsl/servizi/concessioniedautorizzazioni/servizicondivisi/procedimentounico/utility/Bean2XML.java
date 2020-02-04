/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import it.people.process.PplData;
import it.people.process.common.entity.AbstractEntity;

import java.beans.IntrospectionException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.logging.*;
import org.xml.sax.SAXException;

/**
 * @author federicog
 * 
 * Bean2XML.java
 * 
 * @date 11-ott-2005
 * 
 */
public class Bean2XML {
    
    private static Log logger = LogFactory.getLog(Bean2XML.class);
    /**
     * Trasforma in XML il bean contenente i dati inseriti dagli utenti
     * 
     * @return Restituisce i dati inseriti dagli utente in formato stringa
     */
    public static String marshallPplData(PplData obj, String encoding) {
        //StringWriter sw = new StringWriter();
    	ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            BeanWriter bw = new BeanWriter(os, encoding);
            //FileWriter filewriter = new FileWriter("out"); //
            //String encname = filewriter.getEncoding();//
            //System.out.println(">>>>>>>>>>>>>>>>>> "+encname);
            
            bw.getBindingConfiguration().setMapIDs(false);
            //bw.setWriteIDs(false);
            bw.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
            bw.enablePrettyPrint();
//            if ((System.getProperty("file.encoding")).equalsIgnoreCase("UTF-8")) {
            	bw.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>");
            	//bw.writeXmlDeclaration("<?xml version=\"1.0\"?>");
//            } else {
//            	bw.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
//            	// bw.writeXmlDeclaration("<?xml version=\"1.0\"?>");
//            }

            bw.write(obj);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        String ret ="";

        
        try {
//	        if ((System.getProperty("file.encoding")).equalsIgnoreCase("UTF-8")) {
	        	ret = os.toString(encoding);
//	        } else {
//	        	ret = os.toString("ISO-8859-1");
//	        }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Trasforma xml passato in ingresso in oggetto MyObject
     * @param xml
     *            Stringa contenente un xml
     * 
     * @return Oggetto rappresentante i dati inseriti dagli utenti
     */
    public static PplData unmarshallPplData(String xml, String encoding) {
        PplData newData = null;
        logger.debug(xml);
        try {
            BeanReader br = new BeanReader();
            //br.setMatchIDs(false);
            br.getBindingConfiguration().setMapIDs(false);
            br.registerBeanClass(PplData.class);
            ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes(encoding));
            newData = (PplData)br.parse(bais);
        } catch (IOException e) {
            logger.error(e);
        } catch (IntrospectionException e) {
            logger.error(e);
        } catch (SAXException e) {           
            logger.error(e);
        }
        return newData;
    }
    public static AbstractEntity unmarshall(Class clazz,String xml, String encoding) {
        AbstractEntity newData = null;
        try {
            //this is a debug comment inserted Federico
            BeanReader br = new BeanReader();
            br.getBindingConfiguration().setMapIDs(false);
            //br.setMatchIDs(false);
            br.registerBeanClass(clazz);
            ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes(encoding));
            newData = (AbstractEntity)br.parse(bais);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        return newData;
    }
    
    public static String marshallObject(Object obj, String encoding) {
//        StringWriter sw = new StringWriter();
    	ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            BeanWriter bw = new BeanWriter(os, encoding);
            bw.getBindingConfiguration().setMapIDs(false);
            //bw.setWriteIDs(false);
            bw.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
            bw.enablePrettyPrint();
//            if ((System.getProperty("file.encoding")).equalsIgnoreCase("UTF-8")) {
            	bw.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>");
//            } else {
//            	bw.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
//            }
            bw.write(obj);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }

        String ret ="";

        
        try {
//	        if ((System.getProperty("file.encoding")).equalsIgnoreCase("UTF-8")) {
	        	ret = os.toString(encoding);
//	        } else {
//	        	ret = os.toString("ISO-8859-1");
//	        }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return ret;
        
    }
    
    public static Object unmarshallObject(String xml, Class clazz, String encoding) {
        Object newData = null;
        logger.debug(xml);
        try {
            BeanReader br = new BeanReader();
            //br.setMatchIDs(false);
            br.getBindingConfiguration().setMapIDs(false);
            br.registerBeanClass(clazz);
            ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes(encoding));
            newData = br.parse(bais);
        } catch (IOException e) {
            logger.error(e);
        } catch (IntrospectionException e) {
            logger.error(e);
        } catch (SAXException e) {           
            logger.error(e);
        }
        return newData;
    }
        
}
