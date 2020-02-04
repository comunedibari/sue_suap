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
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import it.people.console.dto.FTPTransferResult;
import it.people.console.system.MessageSourceAwareClass;

/**
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 03/set/2012 18:27:00
 *
 */
public class FTPUtils {

	private static Logger logger = LoggerFactory.getLogger(FTPUtils.class);
	
	private static String FTP_HOST = "127.0.0.1";
	private static int FTP_PORT = 21;
	private static String FTP_USER = "anonymous";
	private static String FTP_PASS = "password";
	
	//FTP Active or passive mode
	private static boolean localActive = false;
	private static boolean binaryTransfer = false;
	private static boolean useEpsvWithIPv4 = false;
	private static long keepAliveTimeout = -1;
    private static int controlKeepAliveReplyTimeout = -1;
	
	public static FTPTransferResult uploadFile(File localFile, String remoteFile, String host, int port, String user, String pass) {

		MessageSourceAwareClass messagesSource = new MessageSourceAwareClass();
		
		FTPTransferResult result = new FTPTransferResult();
		result.setTransferredFileName(remoteFile);
		
		//Create client
		final FTPClient ftp = new FTPClient();

		boolean error = false;
		
		try {
			//Connection
			if (logger.isDebugEnabled()) {
				logger.debug("Connecting to host '" + host + "' on port '" + port + "'...");
			}
			ftp.connect(host, port);
			int reply = ftp.getReplyCode();

			if (logger.isDebugEnabled()) {
				logger.debug("Reply = " + ftp.getReplyCode() + "[" + ftp.getReplyString() + "].");
			}
			
			//Test connection
			if (!FTPReply.isPositiveCompletion(reply)) {
				error = true;
				ftp.disconnect();
				logger.error("Monitoring FTP server refused connection.");
				result.setError(true);
				result.setErrorMessage(messagesSource.getProperty("message.ftpUpload.errorMessage.connectionRefused"));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Connection successfull.");
				logger.debug("Logging in...");
			}
			
			//Login
			if (ftp.login(user, pass)) {

				if (logger.isDebugEnabled()) {
					logger.debug("Logging successfull.");
				}
				
				if (binaryTransfer) {
					ftp.setFileType(FTP.BINARY_FILE_TYPE);
				}

				//FTP CONFIGURATION 

				if (localActive) {
					ftp.enterLocalActiveMode();
				} else {
					ftp.enterLocalPassiveMode();
				}
				ftp.setUseEPSVwithIPv4(useEpsvWithIPv4);
				if (keepAliveTimeout >= 0) {
			            ftp.setControlKeepAliveTimeout(keepAliveTimeout);
			    }
			    if (controlKeepAliveReplyTimeout >= 0) {
			            ftp.setControlKeepAliveReplyTimeout(controlKeepAliveReplyTimeout);
			    }
				
				//Store file on Server
				FileInputStream input = new FileInputStream(localFile);
	            
	            ftp.storeFile(remoteFile, input);
	            
	            if (ftp.getReplyCode() >= 400) {
					result.setError(true);
					result.setErrorMessage(ftp.getReplyString());
	            } else {
					result.setError(false);
					result.setErrorMessage(messagesSource.getProperty("message.ftpUpload.errorMessage.success"));
	            }
	            
	            input.close();
				
	            //check that control connection is still working OK
	            ftp.noop(); 
	            ftp.logout();
	            
			} else {
				logger.error("Could not connect to monitoring FTP server: login failed.");				
				result.setError(true);
				result.setErrorMessage(messagesSource.getProperty("message.ftpUpload.errorMessage.loginFailed"));
			}
            
        } catch (FTPConnectionClosedException e) {
            error = true;
            logger.error("Server closed connection.");
			result.setError(true);
			result.setErrorMessage(messagesSource.getProperty("message.ftpUpload.errorMessage.serverClosedConnection"));
        } catch (SocketException e) {
        	error = true;
			logger.error("Socket Exception while connecting FTP server.");
			result.setError(true);
			result.setErrorMessage(messagesSource.getProperty("message.ftpUpload.errorMessage.socketExcpetion"));
		} catch (IOException e) {
			error = true;
			logger.error("Could not connect to monitoring FTP server.");
			result.setError(true);
			result.setErrorMessage(messagesSource.getProperty("message.ftpUpload.errorMessage.noConnection"));
		} finally {
        	if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                }
                catch (IOException f) {
                    // do nothing
                }
            }
        }
		
		return result;
		
	}
}
