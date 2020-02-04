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
package it.wego.dynamicodt.transformation.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author marcob
 */
public class Zip {

	private static Logger logger = LoggerFactory.getLogger(Zip.class);

	/** Creates a new instance of Zip */
	public Zip() {
	}

	public static void zipDir(File dirObj, File outFile) {
		if (!dirObj.isDirectory()) {
			System.err.println(dirObj.getAbsolutePath() + " is not a directory");
			System.exit(1);
		}

		try {

			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFile));

			addDir(dirObj, out, dirObj.getAbsolutePath());
			// Complete the ZIP file
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	/*
	 *
	 */
	private static void addDir(File dirObj, ZipOutputStream out, String pathToRemove) throws IOException {

		if (logger.isDebugEnabled()) {
			logger.debug(" PATH: " + dirObj.getAbsolutePath());
		}
		File[] files = dirObj.listFiles();
		byte[] tmpBuf = new byte[1024];

		CRC32 crc = new CRC32();
		int len;

		int rem = pathToRemove.length() + 1;

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {

				if (files[i].listFiles().length == 0) {
					String dirPath = files[i].getAbsolutePath().substring(rem);
					if (logger.isDebugEnabled()) {
						logger.debug("Aggiungo dir vuota " + dirPath);
					}
					ZipEntry dirEntry = new ZipEntry(dirPath + System.getProperty("file.separator") + ".");
					out.putNextEntry(dirEntry);
				} else {
					addDir(files[i], out, pathToRemove);
				}

				continue;
			}

			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(files[i].getAbsolutePath()));
			crc.reset();
			while ((len = bis.read(tmpBuf)) != -1) {
				crc.update(tmpBuf, 0, len);
			}
			bis.close();
			// Reset to beginning of input stream
			bis = new BufferedInputStream(new FileInputStream(files[i].getAbsolutePath()));

			String relativePath = files[i].getAbsolutePath().substring(rem);
			if (logger.isDebugEnabled()) {
				logger.debug(" Adding: " + relativePath);
				logger.debug(" CRC: " + crc.getValue());
			}

			relativePath = relativePath.replace('\\', '/');

			ZipEntry ze = new ZipEntry(relativePath);
			ze.setSize(files[i].length());
			ze.setCompressedSize(files[i].length());
			ze.setMethod(ZipEntry.STORED);
			ze.setCrc(crc.getValue());

			out.putNextEntry(ze);

			// Transfer from the file to the ZIP file

			while ((len = bis.read(tmpBuf)) != -1) {
				out.write(tmpBuf, 0, len);
			}

			// Complete the entry
			out.closeEntry();
			bis.close();
		}
	}

	public void unzip(InputStream inputStream, File destdir) throws IOException {
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		ZipEntry zipEntry = null;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			File file = new File(destdir.getAbsolutePath() + File.separator + zipEntry);
			if (logger.isDebugEnabled()) {
				logger.debug("File name: " + file.getAbsolutePath() + " " + zipEntry.isDirectory());
			}
			if (zipEntry.isDirectory()) {
				file.mkdirs();
			} else {
				if (!file.getParentFile().exists()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Creo la dir" + file.getParentFile().getAbsolutePath());
					}
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				copyInputStream(zipInputStream, fos);
				fos.close();
			}
		}
		inputStream.close();
	}

	private final void copyInputStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) >= 0) {
			out.write(buffer, 0, len);
		}
	}
}
