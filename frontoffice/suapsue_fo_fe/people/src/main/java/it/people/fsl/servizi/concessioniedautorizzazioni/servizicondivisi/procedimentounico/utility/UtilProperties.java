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

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtilProperties {
	
	private static Log logger = LogFactory.getLog(UtilProperties.class);
	static String localPath ="C:/People2_0_2_RiusoRER/eclipseworkspace/people/WebContent/WEB-INF/classes/it/people/fsl/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico";
	
	public static String getPropertyKey(Properties pComune, Properties pGeneric, Properties pLocal, String key) {
		logger.debug("invocato metodo -- getPropertiesKey('"+((pComune==null)?"null":"valorizzato")+"','"+((pComune==null)?"null":"valorizzato")+"','"+((pLocal==null)?"null":"valorizzato")+"','"+key+"')");
		String ret = null;
		if (pComune != null) {
			ret = pComune.getProperty(key);
		}
		if ((pComune == null) || (ret == null)) {
			if (pGeneric!=null) ret = pGeneric.getProperty(key);
		}
		if (ret==null && pLocal!=null){
			ret = pLocal.getProperty(key);
		}		
		logger.debug("valore restituito = "+ret);
		if (ret!=null) {
			ret = ret.trim();
		}
		return ret;
	}
	
	public static Properties[] getProperties(String path1, String prefix, String oidComune){
        Properties props[] = new Properties[3];
		Properties pComune = new Properties();
        Properties pGeneric = new Properties();
        Properties pLocal = new Properties();
        try {
        	pComune.load(new FileInputStream(path1+prefix+"_"+oidComune+".properties"));
        } catch (Exception e){
        	pComune = null;
        }
        try {
        	pGeneric.load(new FileInputStream(path1+prefix+".properties"));
        } catch (Exception e){
        	pGeneric = null;
        }  
        try {
        	pLocal.load(new FileInputStream(localPath+System.getProperty("file.separator")+"risorse"+System.getProperty("file.separator")+prefix+".properties"));
        } catch (Exception e){
        	pLocal = null;
        }    
        props[0] = pComune;
        props[1] = pGeneric;
        props[2] = pLocal;

		return props;
	}
}
