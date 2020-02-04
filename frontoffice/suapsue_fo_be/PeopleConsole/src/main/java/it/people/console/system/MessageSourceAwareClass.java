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
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.console.system;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import it.people.feservice.utils.StringUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 17/giu/2011 14.18.58
 *
 */
public class MessageSourceAwareClass extends AbstractLogger {

	private static ReloadableResourceBundleMessageSource propertiesRepository;
	private static MessageSource staticPropertiesRepository;

	public void setPropertiesRepository(ReloadableResourceBundleMessageSource _propertiesRepository) {
		propertiesRepository = _propertiesRepository;
	}

	public void setStaticPropertiesRepository(MessageSource _staticPropertiesRepository) {
		staticPropertiesRepository = _staticPropertiesRepository;
	}

	protected ReloadableResourceBundleMessageSource getPropertiesRepository() {
		return propertiesRepository;
	}

	protected MessageSource getStaticPropertiesRepository() {
		return staticPropertiesRepository;
	}

	public String getStaticProperty(String propertyKey) {
		return StringUtils.nullToEmptyString(
				this.getStaticPropertiesRepository().
					getMessage(propertyKey, null, Locale.getDefault())).trim();
	}
	
	public String getProperty(String propertyKey) {
		return StringUtils.nullToEmptyString(
				this.getPropertiesRepository().getMessage(propertyKey, null, 
						Locale.getDefault())).trim();
	}
	
}
