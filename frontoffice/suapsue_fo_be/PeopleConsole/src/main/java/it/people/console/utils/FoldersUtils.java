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
package it.people.console.utils;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;

import org.springframework.web.context.WebApplicationContext;

import it.people.console.system.MessageSourceAwareClass;

/**
 * @author Riccardo Forafo'
 *
 * @since Oct 23, 2008
 *
 */
public class FoldersUtils extends MessageSourceAwareClass {

	private static FoldersUtils instance = null;
	
	private FoldersUtils() {
		
	}
	
	/**
	 * @return
	 */
	public static FoldersUtils instance() {
		if (instance == null) {
			instance = new FoldersUtils();
		}
		return instance;
	}
	
	/**
	 * @param baseTemporaryPath
	 * @param sessionEvent
	 * @return
	 */
	public String getSessionTemporaryRepositoryPath(HttpSessionEvent sessionEvent) {

		return sanitazeOSFlavourPath(sessionEvent.getSession().getServletContext().
		getRealPath(Constants.System.ROOT) +  
		this.getStaticProperty("temporary.path") + System.getProperty("file.separator") +
		this.getStaticProperty("session.temporary.path") + 
			System.getProperty("file.separator") + sessionEvent.getSession().getId());
		
	}

	/**
	 * @param baseTemporaryPath
	 * @param httpServletRequest
	 * @return
	 */
	public String getSessionTemporaryRepositoryPath(HttpServletRequest httpServletRequest) {

		return sanitazeOSFlavourPath(httpServletRequest.getSession().getServletContext().
		getRealPath(Constants.System.ROOT) +  
		this.getStaticProperty("temporary.path") + System.getProperty("file.separator") +
		this.getStaticProperty("session.temporary.path") + 
			System.getProperty("file.separator") + httpServletRequest.getSession().getId());
		
	}
	
	/**
	 * @param baseTemporaryPath
	 * @param webApplicationContext
	 * @return
	 */
	public File getTemporaryRepositoryPath(WebApplicationContext webApplicationContext) {

		return new File(sanitazeOSFlavourPath(webApplicationContext.getServletContext().
		getRealPath(Constants.System.ROOT) + this.getStaticProperty("temporary.path")));
		
	}

	/**
	 * @param baseTemporaryPath
	 * @param sessionEvent
	 * @return
	 */
	public File getTemporaryRepositoryPath(HttpSessionEvent sessionEvent) {

		return new File(sanitazeOSFlavourPath(sessionEvent.getSession().getServletContext().
		getRealPath(Constants.System.ROOT) + this.getStaticProperty("temporary.path")));
		
	}
	
    /**
     * @param dir
     * @param repositoryFolder
     * @return
     */
    public synchronized boolean cleanUp(File dir, File repositoryFolder) {
    	
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = cleanUp(new File(dir, children[i]), repositoryFolder);
                if (!success) {
                    return false;
                }
            }
        }

		if (repositoryFolder == null) {
            return dir.delete();
		}
		else {
	        if (!dir.getAbsolutePath().equalsIgnoreCase(repositoryFolder.getAbsolutePath())) {
	            return dir.delete();
	        }
	        else {
	        	return true;
	        }
		}
		
    } 	
	
    /**
     * @param path
     * @return
     */
    public String sanitazeOSFlavourPath(String path) {
    	
    	String osFileSeparator = System.getProperty("file.separator");
    	String replaceString = (osFileSeparator.equalsIgnoreCase("\\")) ? "/" : "\\";
    	return path.replace(replaceString, osFileSeparator);
    	
    }

    /**
     * @param path
     * @return
     */
    public String sanitazeHttpPath(String path) {
    	
    	return path.replace("\\", "/");
    	
    }
    
    /**
     * @return
     */
    public String getRelativeSessionTemporaryRepositoryPath() {
    	return this.getStaticProperty("temporary.path") + System.getProperty("file.separator") + 
    		this.getStaticProperty("session.temporary.path");
    }
    
}
