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

import java.lang.reflect.Method;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;

import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;

public class ManagerAttributiNuoviFramework {
	
	private static Logger logger = LoggerFactory.getLogger(ManagerAttributiNuoviFramework.class.getName());
	
	public void settaAttributoProcessData(ProcessData dataForm,String nomeAttributo, Class classeParametro, Object value){
		try {
			String head = nomeAttributo.substring(0, 1);
			String tail = nomeAttributo.substring(1);
			String firstCharUpper = head.toUpperCase();
			String nomeAtt = firstCharUpper+tail;
			
			Method mtd = dataForm.getClass().getMethod("set"+nomeAtt, new Class[] {classeParametro});
			if (mtd != null ){
				mtd.invoke(dataForm, new Object[] {value});
			}
		} catch (Exception e){
			logger.warn("Non e' stato possibile settare il parametro '"+nomeAttributo+"'");
		}
	}
	
}
