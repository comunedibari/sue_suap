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
package it.people.core;

import it.people.core.persistence.PersistenceManager;
import it.people.core.persistence.PersistenceManagerFactory;
import it.people.core.persistence.exception.peopleException;
import it.people.process.AbstractPplProcess;
import it.people.process.sign.SignedDataHelper;
import it.people.process.sign.SignedDataHolder;
import it.people.process.sign.entity.SignedData;
import it.people.util.PeopleProperties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Category;

/**
 * 
 * User: sergio Date: Nov 30, 2003 Time: 8:47:25 PM <br>
 * <br>
 * 
 */
public class SignedDataManager {

    private Category cat = Category.getInstance(SignedDataManager.class
	    .getName());

    private static SignedDataManager ourInstance;

    /**
     * 
     * @return Restituisce l'istanza SignedDataManager
     */
    public synchronized static SignedDataManager getInstance() {
	if (ourInstance == null) {
	    ourInstance = new SignedDataManager();
	}
	return ourInstance;
    }

    private SignedDataManager() {
    }

    /**
     * Salva i dati su file e su DB
     * 
     * @param sd
     *            SignedData da salvare
     * @param pplProcess
     *            Processo padre dei dati
     * @throws peopleException
     */
    public void set(SignedData sd, AbstractPplProcess pplProcess,
	    HttpServletRequest request) throws peopleException {
	String name = getUniqueName();
	PersistenceManager spm = null;
	try {
	    String path = PeopleProperties.UPLOAD_DIRECTORY
		    .getValueString(pplProcess.getCommune().getKey());
	    String fullname = "";
	    // String nuovaGestioneFile =
	    // request.getSession().getServletContext().getInitParameter("remoteAttachFile");
	    // if (nuovaGestioneFile!=null &&
	    // nuovaGestioneFile.equalsIgnoreCase("true")){
	    if (!pplProcess.isEmbedAttachmentInXml()) {
		try {
		    path = PeopleProperties.UPLOAD_DIRECTORY
			    .getValueString(pplProcess.getCommune().getKey());
		} catch (Exception e) {
		    throw e;
		}
		if (path != null) {
		    File rootDirNodo = new File(path + "\\"
			    + pplProcess.getCommune().getKey());
		    if (!rootDirNodo.exists()) {
			rootDirNodo.mkdir();
		    }
		    File rootDirPratica = new File(path + "\\"
			    + pplProcess.getCommune().getKey() + "\\"
			    + pplProcess.getIdentificatoreProcedimento());
		    if (!rootDirPratica.exists()) {
			rootDirPratica.mkdirs();
		    }
		    path = path + "\\" + pplProcess.getCommune().getKey()
			    + "\\" + pplProcess.getIdentificatoreProcedimento();
		    String idUnivoco = Long.toString((new Date()).getTime());
		    // File uploadFile = new File(basePathName,
		    // idUnivoco+"_"+"riepilogo.html");
		    try {
			fullname = path + "\\" + idUnivoco + "_"
				+ "riepilogo.html.p7m";
			File f = new File(fullname);
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(sd.getSignedContent().getPkcs7Data());
			fos.close();
		    } catch (IOException e) {
			cat.error(new String(
				"Errore nel salvataggio del riepilogo firmato"));
		    }
		    sd.setPath(fullname);
		}
	    } else {
		fullname = path + File.separator + name /* + ".html.p7m" */;
		sd.setPath(fullname);
		File f = new File(fullname);
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(sd.getSignedContent().getPkcs7Data());
	    }

	    SignedDataHolder sdh = SignedDataHelper.exportToHolder(sd,
		    pplProcess);
	    spm = PersistenceManagerFactory.getInstance().get(sdh.getClass(),
		    PersistenceManager.Mode.WRITE);

	    spm.set(sdh);
	} catch (Exception ex) {
	    cat.error(ex);
	    throw new peopleException(ex.getMessage());
	} finally {
	    if (spm != null) {
		spm.close();
	    }
	}

    }

    /**
     * 
     * @param pplProcess
     *            Processo di cui voglio recuperare le informazioni firmate
     * @return Restituisce una collezione di oggetti SignedData
     * @throws peopleException
     */
    public Collection get(AbstractPplProcess pplProcess) throws peopleException {
	try {
	    Collection elements = SingPersistencaMgrFactory.getInstance().get(
		    pplProcess.getOid());
	    ArrayList manageableObjects = new ArrayList();
	    Iterator iter = elements.iterator();
	    while (iter.hasNext()) {
		try {
		    SignedDataHolder sdh = (SignedDataHolder) iter.next();
		    manageableObjects.add(SignedDataHelper
			    .importFromHolder(sdh));
		} catch (Exception sdhEx) {
		    cat.error(sdhEx);
		}
	    }
	    return manageableObjects;
	} catch (Exception ex) {
	    cat.error(ex);
	    throw new peopleException(ex.getMessage());
	}
    }

    /**
     * 
     * @param oid
     *            OID del Processo di cui voglio recuperare le informazioni
     *            firmate
     * @return Restituisce una collezione di oggetti SignedData
     * @throws peopleException
     */
    public List get(Long oid) throws peopleException {
	try {
	    Collection elements = SingPersistencaMgrFactory.getInstance().get(
		    oid);
	    ArrayList manageableObjects = new ArrayList();
	    Iterator iter = elements.iterator();
	    while (iter.hasNext()) {
		try {
		    SignedDataHolder sdh = (SignedDataHolder) iter.next();
		    manageableObjects.add(SignedDataHelper
			    .importFromHolder(sdh));
		} catch (Exception sdhEx) {
		    cat.error(sdhEx);
		}
	    }
	    return manageableObjects;
	} catch (Exception ex) {
	    cat.error(ex);
	    throw new peopleException(ex.getMessage());
	}
    }

    private String getUniqueName() {
	return Long.toString((new Date()).getTime());
    }
}
