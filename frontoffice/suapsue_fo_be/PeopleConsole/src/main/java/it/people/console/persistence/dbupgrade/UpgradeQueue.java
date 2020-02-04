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
package it.people.console.persistence.dbupgrade;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import it.people.console.persistence.dbupgrade.exceptions.UpgradeQueueException;
import it.people.console.utils.Constants;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 16/lug/2011 10.28.29
 *
 */
public class UpgradeQueue {

	/**
	 * @param actualMajorVersion
	 * @param actualMinorVersion
	 * @return
	 * @throws UpgradeQueueException
	 */
	public static LinkedList<String> getUpgradeQueue(final InputStream upgradesFile, 
			final int actualMajorVersion, 
			final int actualMinorVersion) throws UpgradeQueueException {
		
		LinkedList<String> result = new LinkedList<String>();
		
		boolean actualMajorVersionFound = actualMajorVersion == 0;
		boolean actualMinorVersionFound = actualMinorVersion == 0;
		boolean minorMajorMinorVersionFound = false;
		
		try {
			
			final SortedMap<Integer, Vector<Integer>> buffer = new TreeMap<Integer, Vector<Integer>>();
			
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = saxParserFactory.newSAXParser();
		
			DefaultHandler handler = new DefaultHandler() {
				
				boolean release = false;
				boolean minor = false;
				int actualRelease = 0;
				
				public void startElement(String uri, String localName,
						String qName, Attributes attributes) throws SAXException {
	
					if (qName.equalsIgnoreCase("release")) {
						release = true;
						String attribute = attributes.getValue("major");
						int releaseNumber = Integer.parseInt(attribute);
						actualRelease = releaseNumber;
						if (buffer.get(releaseNumber) == null) {
							buffer.put(releaseNumber, new Vector<Integer>());
						}
					}

					if (qName.equalsIgnoreCase("minor")) {
						minor = true;
					}
					
				}
	
				public void endElement(String uri, String localName,
						String qName) throws SAXException {
	
					if (qName.equalsIgnoreCase("release")) {
						release = false;
					}
	
				}			
	
				public void characters(char ch[], int start, int length) throws SAXException {
	
					if (minor && release) {
						if (buffer.get(actualRelease) != null) {
							buffer.get(actualRelease).add(Integer.parseInt(new String(ch, start, length)));
						}
						minor = false;
					}
					
				}
			     
			};
			
			saxParser.parse(upgradesFile, handler);
		
			upgradesFile.close();
			
			Set<Integer> bufferKeys = buffer.keySet();
			Iterator<Integer> bufferKeysIterator = bufferKeys.iterator();
			while(bufferKeysIterator.hasNext()) {
				Integer release = bufferKeysIterator.next();
				if (!actualMajorVersionFound && actualMajorVersion == release.intValue()) {
					actualMajorVersionFound = true;
				}
				Vector<Integer> values = buffer.get(release);
				Iterator<Integer> valuesIterator = values.iterator();
				while(valuesIterator.hasNext()) {
					Integer minorRelease = valuesIterator.next();
					if (!actualMinorVersionFound && actualMinorVersion == minorRelease.intValue()) {
						actualMinorVersionFound = true;
					}
					if (actualMajorVersionFound && actualMinorVersionFound) {
						if (getVersion(release, minorRelease) > 
						getVersion(actualMajorVersion, actualMinorVersion) && !minorMajorMinorVersionFound) {
							minorMajorMinorVersionFound = true;
						}
						if (minorMajorMinorVersionFound) {
							result.add(Constants.DbUpgrade.SCRITP_NAME_PREFIX + release + 
								Constants.DbUpgrade.SCRITP_VERSION_NUMBERS_SEPARATOR + minorRelease + 
								Constants.DbUpgrade.SCRITP_NAME_SUFFIX);
						}
					}
				}
			}
			
		} catch (ParserConfigurationException e) {
			throw new UpgradeQueueException(e);
		} catch (SAXException e) {
			throw new UpgradeQueueException(e);
		} catch (IOException e) {
			throw new UpgradeQueueException(e);
		}

		
		return result;
		
	}

	/**
	 * @param major
	 * @param minor
	 * @return
	 */
	private static double getVersion(int major, int minor) {
		
		return new Double(String.valueOf(major) + "." + String.valueOf(minor));
		
	}
	
	public static void main(String[] args) {
		
		System.out.println(getVersion(3, 1024));
		
	}
	
}
