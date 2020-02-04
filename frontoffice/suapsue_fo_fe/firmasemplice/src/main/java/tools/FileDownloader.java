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
package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import utils.UtilityFunctions;

/**
 * Permette di scaricare un file da un URL con la possibilità di scrittura
 * sincronizzata
 * 
 * @author Giuseppe
 */
public class FileDownloader {

	private static Logger logger = LoggerFactory.getLogger(FileDownloader.class);

	private static final Boolean mutex = Boolean.FALSE;
	private boolean createInfoFile;

	/**
	 * Crea l'oggetto FileDownloader
	 * 
	 * @param createDateFile
	 *            se "true", crea un file testuale con la data di creazione del
	 *            file scaricato e usa quel file come verifica per decidere se
	 *            il file è da scaricare
	 */
	public FileDownloader(boolean createInfoFile) {
		this.createInfoFile = createInfoFile;
	}

	/**
	 * Scarica un file da un URL Se il file è già presente e non è stato
	 * modificato non lo riscarica
	 * 
	 * @param url
	 *            indirizzo del file da scaricare
	 * @param filePrefix
	 *            prefisso da aggiungere al file scaricato, può essere composto
	 *            dalla path locale nel quale salvare il file più un prefisso da
	 *            aggiungere in testa del nome al file (il nome del file
	 *            dovrebbe essere quello del file memorizzato sul server)
	 * @param syncronizedWrite
	 *            se "true" acquisisce la mutua esclusione per lo scaricamento
	 *            del file. Inoltre se il file esiste già ed è uguale al file da
	 *            scaricare non lo riscarica e ritorna il file locale. Il
	 *            controllo è effettuato sulla dimensione e sulla data del file.
	 * @return il file scaricato.
	 * @throws FileNotFoundException
	 */
	public File downloadFileFromUrl(URL url, String filePrefix,
			boolean syncronizedWrite) throws FileNotFoundException {
		InputStream is = null;
		FileOutputStream fos = null;
		if (filePrefix == null)
			filePrefix = "";

		try {
			UtilityFunctions.trustAllHostNameAndCertificate();
		} catch (NoSuchAlgorithmException ex) {
			logger.error("", ex);
			return null;
		} catch (KeyManagementException ex) {
			logger.error("", ex);
			return null;
		}

		try {
			url = new URL(url.toString().replace(" ", "%20"));
		} catch (MalformedURLException ex) {
			logger.error("", ex);
			return null;
		}

		try {
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();

			// urlConn.setRequestProperty("User-Agent",
			// "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 1.0.3705)");

			String fileName = filePrefix
					+ new File(urlConn.getURL().getFile()).getName();
			File fileToDownload = new File(fileName);

			long remoteFileLenght = 0;
			long remoteFileDate = 0;
			long localFileLenght = -1;
			long localFileDate = -1;
			try {
				remoteFileLenght = Long.parseLong(urlConn
						.getHeaderField("Content-Length"));
				SimpleDateFormat sdf = new SimpleDateFormat(
						"EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
				remoteFileDate = sdf.parse(
						urlConn.getHeaderField("Last-Modified")).getTime();

				if (new File(fileName + ".info").exists() && createInfoFile) {
					BufferedReader br = new BufferedReader(new FileReader(
							fileName + ".info"));
					String line = br.readLine();
					try {
						localFileLenght = Long.parseLong(line);
					} catch (NumberFormatException ex) {
					}
					line = br.readLine();
					try {
						localFileDate = Long.parseLong(line);
					} catch (NumberFormatException ex) {
					}
				}
			} catch (Exception ex) {
				logger.error("", ex);
			}

			boolean downloadFile = true;

			try {
				if (syncronizedWrite) {

					// se il file non esiste lo devo scaricare
					if (fileToDownload.exists()) {

						// controllo se il file presente è uguale al file remoto
						if (remoteFileLenght == localFileLenght) {
							if (remoteFileDate <= localFileDate) {
								downloadFile = false;
							}
						}
					}
				}
			}

			// se si verifica un eccezione in questo controllo scarico il file
			catch (Exception ex) {
				logger.error("", ex);
			}

			if (downloadFile) {

				if (syncronizedWrite) {

					synchronized (mutex) {
						// ricontrollo se il file è da scaricare, perché potrei
						// essere rimasto bloccato nel syncronized mentre un
						// altro thread stava scaricando il file.
						// In questo caso il file potrebbe non essere da
						// scaricare, quindi controllo.
						try {
							if (fileToDownload.exists()) {
								if (remoteFileLenght == localFileLenght) {
									if (remoteFileDate <= localFileDate) {
										downloadFile = false;
									}
								}
							}
						}
						// se si verifica un eccezione in questo controllo
						// scarico il file
						catch (Exception ex) {
							logger.error("", ex);
						}

						if (downloadFile) {

							if (logger.isDebugEnabled()) {
								logger.debug("Scarico il file: " + fileName
										+ "...");
							}
							urlConn.connect();
							is = urlConn.getInputStream();
							writeFile(is, fileName, createInfoFile,
									remoteFileLenght, remoteFileDate);

							try {
								is.close();
							} catch (Exception ex) {
							}
						} else {
							if (logger.isDebugEnabled()) {
								logger.debug("Non è necessario scaricare il file: "
										+ fileName + ".");
							}
						}
					}
				} else {

					if (logger.isDebugEnabled()) {
						logger.debug("Scarico il file: " + fileName + "...");
					}
					urlConn.connect();
					is = urlConn.getInputStream();
					writeFile(is, fileName, createInfoFile, remoteFileLenght,
							remoteFileDate);
					try {
						is.close();
					} catch (Exception ex) {
					}
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Non è necessario scaricare il file: "
							+ fileName + ".");
				}
			}

			return new File(fileName);
		} catch (IOException ex) {
			logger.error("", ex);
			return null;
		}
	}

	// scrive un InputStream su disco
	private void writeFile(InputStream is, String fileName,
			boolean writeInfoFile, long fileLength, long fileDate)
			throws FileNotFoundException, IOException {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(fileName);

			byte[] buffer = new byte[1024];
			int len;

			while ((len = is.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}

			if (writeInfoFile) {
				bw = new BufferedWriter(new FileWriter(fileName + ".info"));
				String stringFileLength = Long.toString(fileLength);
				String stringFileDate = Long.toString(fileDate);
				bw.write(stringFileLength, 0, stringFileLength.length());
				bw.newLine();
				bw.write(stringFileDate, 0, stringFileDate.length());
			}
		} finally {
			try {
				fos.close();
			} catch (Exception ex) {
			}
			try {
				bw.close();
			} catch (Exception ex) {
			}
		}
	}
}
