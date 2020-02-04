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

package it.people.feservice.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 01/09/2012
 *
 */
public class ZipUtils {

	private static Logger logger = LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * Compress a single file into a zip file.
	 * 
	 * @param inFile
	 *            file to compress
	 * @param zipOutFile
	 *            destination ZIP file
	 * @return the path of compressed file
	 * @throws IOException
	 */
	public static String zipSingleFile(File inFile, File zipOutFile) throws IOException {

		byte[] buffer = new byte[1024];

		FileOutputStream fos = new FileOutputStream(zipOutFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
		ZipEntry ze = new ZipEntry(inFile.getName());
		zos.putNextEntry(ze);
		FileInputStream in = new FileInputStream(inFile);

		int len;
		while ((len = in.read(buffer)) > 0) {
			zos.write(buffer, 0, len);
		}

		in.close();
		zos.closeEntry();
		zos.close();
		fos.close();

		return zipOutFile.getCanonicalPath();

	}

}
