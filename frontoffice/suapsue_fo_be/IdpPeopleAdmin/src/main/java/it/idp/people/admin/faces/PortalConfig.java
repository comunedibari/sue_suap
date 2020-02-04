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
/*

  Licenza:	    Licenza Progetto PEOPLE
  Fornitore:    CEFRIEL
  Autori:       M. Pianciamore, P. Selvini

  Questo codice sorgente � protetto dalla licenza valida nell'ambito del
  progetto PEOPLE. La propriet� intellettuale di questo codice � e rester�
  esclusiva di "CEFRIEL Societ� Consortile a Responsabilit� Limitata" con
  sede legale in via Renato Fucini 2, 20133 Milano (MI).

  Disclaimer:

  COVERED CODE IS PROVIDED UNDER THIS LICENSE ON AN "AS IS" BASIS, WITHOUT
  WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT 
  LIMITATION, WARRANTIES THAT THE COVERED CODE IS FREE OF DEFECTS, MERCHANTABLE,
  FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE
  QUALITY AND PERFORMANCE OF THE COVERED CODE IS WITH YOU. SHOULD ANY COVERED
  CODE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY
  OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR
  CORRECTION.
    
*/
package it.idp.people.admin.faces;

import it.idp.people.admin.common.PageProperties;
import it.idp.people.admin.sqlmap.common.IdpPeopleAdminConstants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class PortalConfig 
{
	private Properties properties = null;
	private HashMap pageMap = null;
	
	public void setPortalID(String idcomune) {
		properties.setProperty(IdpPeopleAdminConstants.ID_COMUNE, idcomune);
	}

	public void setSuffixCADomain(String suffixCADomain) {
		properties.setProperty(IdpPeopleAdminConstants.USERNAME_SUFFIX, suffixCADomain);
	}

	public PortalConfig()
	{
		properties = new Properties();
		
		try 
		{
			properties.load(this.getClass().getResource("/properties/app.properties").openStream());
			buildPageMap();						
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}		
	}
	
	public String getPortalID()
	{
		try 
		{
			return (String)properties.get(IdpPeopleAdminConstants.ID_COMUNE);
		} 
		catch (Exception e) 
		{
			return "";
		}
	}
	
	public String getSuffixCADomain()
	{
		try 
		{
			return (String)properties.get(IdpPeopleAdminConstants.USERNAME_SUFFIX);
		} 
		catch (Exception e) 
		{
			return "";
		}
	}
	
	public String save()
	{
		try 
		{
			properties.store(new FileOutputStream(this.getClass().getResource("/properties/app.properties").getFile()), null);
			return IdpPeopleAdminConstants.RETURN_STATUS_SUCCESS;
		} 
		catch (Exception e) 
		{
			return IdpPeopleAdminConstants.RETURN_STATUS_FAILED;
		}
	}
	
	private void buildPageMap()
	{
		pageMap = new HashMap(); 
		
		int pageCount = Integer.parseInt(properties.getProperty("viewCount"));
		
		for(int i=1; i<=pageCount; i++)
		{
			PageProperties page = new PageProperties();
			
			page.setName((String)properties.getProperty("name" + i));
			page.setModelClassName((String)properties.getProperty("modelClassName" + i));
			page.setDaoClassName((String)properties.getProperty("daoClassName" + i));
			page.setDetailsPage((String)properties.getProperty("details" + i));
			page.setFilterPage((String)properties.getProperty("filter" + i));
			page.setIndex(i);
			
			pageMap.put(new Integer(i), page);
		}
	}

	public HashMap getPageMap() {
		return pageMap;
	}
}
