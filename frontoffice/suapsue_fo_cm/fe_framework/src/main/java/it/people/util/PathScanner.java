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
package it.people.util;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.Vector;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica S.p.A.
 * 
 */
public class PathScanner {

    public Collection<File> listFiles(File basePath, FileFilter fileFilter) {

	// Vector result = new Vector();

	Collection<File> result = new java.util.LinkedList<File>();

	recursiveListFiles(basePath, result, fileFilter);

	return result;

    }

    private void recursiveListFiles(File directory, Collection<File> files,
	    FileFilter fileFilter) {

	File[] found = directory.listFiles();

	if (found != null) {
	    for (File file : found) {
		if (file.isDirectory()) {
		    recursiveListFiles(file, files, fileFilter);
		} else {
		    if (fileFilter.accept(file)) {
			files.add(file);
		    }

		}
	    }
	}

    }

    public Vector scanFiles(File basePath) {

	Vector result = new Vector();

	recursiveScanFiles(basePath, result);

	return result;

    }

    private void recursiveScanFiles(File fileObject, Vector buffer) {

	if (fileObject.isDirectory()) {
	    File[] pathFiles = fileObject.listFiles();
	    for (int index = 0; index < pathFiles.length; index++) {
		recursiveScanFiles(pathFiles[index], buffer);
	    }
	} else {
	    buffer.add(fileObject.getAbsolutePath());
	}

    }

    public Vector scan(File basePath) {

	Vector result = new Vector();

	recursiveScan(basePath, result);

	result.remove(0);

	return result;

    }

    private void recursiveScan(File fileObject, Vector buffer) {

	if (fileObject.isDirectory()) {
	    buffer.add(fileObject.getAbsolutePath());
	    File[] pathFiles = fileObject.listFiles();
	    for (int index = 0; index < pathFiles.length; index++) {
		recursiveScan(pathFiles[index], buffer);
	    }
	} else {
	    buffer.add(fileObject.getAbsolutePath());
	}

    }

    public Vector scanFiles(File basePath, FileFilter fileFilter) {

	Vector result = new Vector();

	recursiveScanFiles(basePath, result, fileFilter);

	return result;

    }

    private void recursiveScanFiles(File fileObject, Vector buffer,
	    FileFilter fileFilter) {

	if (fileObject.isDirectory()) {
	    File[] pathFiles = fileObject.listFiles();
	    for (int index = 0; index < pathFiles.length; index++) {
		recursiveScanFiles(pathFiles[index], buffer, fileFilter);
	    }
	} else {
	    if (fileFilter.accept(fileObject)) {
		buffer.add(fileObject.getAbsolutePath());
	    }
	}

    }

}
