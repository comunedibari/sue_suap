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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.ProxyManager;
import tools.SignatureVerifier;

/**
 *
 * @author Giuseppe
 */
public class ReceiveSignedFile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
	
	private static Logger logger = LoggerFactory.getLogger(ReceiveSignedFile.class);
	
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    String serverConfFiles = getServletContext().getRealPath("") + "/ConfFiles";
    File tempDir = new File(serverConfFiles + "/temp/");
    File settigsFile = new File(serverConfFiles + "/settings.properties");
    File recivedSignedFileDir = new File(getServletContext().getRealPath("") + "/RecivedSignedFiles/");

    File recivedFile = null;
    File tempFile = null;

    if (!tempDir.exists())
        tempDir.mkdir();

    if (!recivedSignedFileDir.exists())
        recivedSignedFileDir.mkdir();

        Properties settings = new Properties();
        FileInputStream settingsInputStream = null;

        String proxyHost = null;
        String proxyPort = null;
        String proxyUsername = null;
        String proxyPassword = null;

        String keystoreFileName = null;
        String keystorePassword = null;

        boolean checkCertificateRevocation = false;
        try {
            settingsInputStream = new FileInputStream(settigsFile.getAbsolutePath());
            settings.load(settingsInputStream);
            settingsInputStream.close();

            try {
                proxyHost = settings.getProperty("proxyHost");
                if (proxyHost.equals("")) {
                    proxyHost = null;
                }
                proxyPort = settings.getProperty("proxyPort");
                if (proxyPort.equals("")) {
                    proxyPort = null;
                }
                proxyUsername = settings.getProperty("proxyUsername");
                if (proxyUsername.equals("")) {
                    proxyUsername = null;
                }
                proxyPassword = settings.getProperty("proxyPassword");
                if (proxyPassword.equals("")) {
                    proxyPassword = null;
                }

                keystoreFileName = settings.getProperty("keystoreFileName");
                if (keystoreFileName.equals("")) {
                    keystoreFileName = null;
                }
                keystorePassword = settings.getProperty("keystorePassword");
                if (keystorePassword.equals("")) {
                    keystorePassword = null;
                }
                checkCertificateRevocation = Boolean.parseBoolean(settings.getProperty("checkCertificateRevocation"));
            }
            catch (NullPointerException ex) {
            }

            if (proxyHost != null && proxyPort != null) {
                char[] proxyPasswordCharArray = null;
                if (proxyPassword != null)
                    proxyPasswordCharArray = proxyPassword.toCharArray();

                ProxyManager proxy = new ProxyManager(proxyHost, proxyPort, proxyUsername, proxyPasswordCharArray);
                proxy.updateSystemProxy();
            }
        }
        catch (Exception ex) {
            logger.error("Errore nella lettura del file settings.properties", ex);
        }

        SignatureVerifier verifier = null;

        try {

            try {
                if (ServletFileUpload.isMultipartContent(request)) {

                    ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory(1024 * 1024, tempDir));
                    List fileItems = sfu.parseRequest(request);
                    for (int elementIndex = 0; elementIndex<fileItems.size(); elementIndex++) {

                        FileItem item = (FileItem)fileItems.get(elementIndex);
                        if (item.isFormField()) {
                            if (logger.isDebugEnabled()) {
                            	logger.debug(item.getString());
                            }
                            // vedere
                        }

                        else if (!item.isFormField()) { // Il file ricevuto. Lo salvo su disco

                            if (logger.isInfoEnabled()) {
                            	logger.info("Ricevo il file " + item.getName() + "...");
                            }
                            InputStream fis = null;
                            FileOutputStream fos = null;
                            try {

                                GregorianCalendar calendar = new GregorianCalendar();
                                int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
                                int month = calendar.get(GregorianCalendar.MONTH) + 1;
                                int year = calendar.get(GregorianCalendar.YEAR);
                                int hour = calendar.get(GregorianCalendar.HOUR_OF_DAY);
                                int minutes = calendar.get(GregorianCalendar.MINUTE);
                                int seconds = calendar.get(GregorianCalendar.SECOND);
                                String prefixSeparator = "_";

                                // il nome del file sarà: giorno_mese_anno_ore_minuti_secondi_nomedelfilericevuto
                                String prefixString =   day + prefixSeparator +
                                                        month + prefixSeparator +
                                                        year + prefixSeparator +
                                                        hour + prefixSeparator +
                                                        minutes + prefixSeparator +
                                                        seconds + prefixSeparator;

                                tempFile = new File(tempDir.getAbsolutePath() + "/__temp_recived_file_" + request.getSession().getId());

                                String recivedFilePathAndName = recivedSignedFileDir.getAbsolutePath() + "/" + prefixString + item.getName();
                                // rimunovo eventuali caratteri "?" che mi possono generare errori nella creazione del file
                                recivedFilePathAndName = recivedFilePathAndName.replace("?", "");
                                recivedFile = new File(recivedFilePathAndName);

                                fos = new FileOutputStream(tempFile);
                                fis = item.getInputStream();
                                byte[] readData = new byte[1024];
                                int i = fis.read(readData);
                                while (i != -1) {
                                    fos.write(readData, 0, i);
                                    i = fis.read(readData);
                                }
                                fis.close();
                                fos.close();

                                String CSPTrustedListsURL = null;
                                try {
                                    CSPTrustedListsURL = settings.getProperty("CSPTrustedLists");
                                }
                                catch (NullPointerException ex) {
                                    logger.error("Errore nella lettura del parametro: \"CSPTrustedLists\" nel file \"settings.properties\": Valore non trovato.", ex);
                                }
                                verifier = new SignatureVerifier(recivedFile, checkCertificateRevocation, CSPTrustedListsURL, tempDir.getAbsolutePath());

                                if (verifier.verify()) {
                                    tempFile.renameTo(recivedFile);
                                }
                                else
                                    tempFile.delete();
                            }
                            catch (IOException ex) {
                                logger.error("", ex);
                                if (tempFile != null)
                                    tempFile.delete();
                                if (fis != null)
                                    fis.close();
                                if (fos != null)
                                    fos.close();
                                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                                return;
                            }
                        }
                        item.delete(); // elimino l'eventuale file temporaneao creato nel parsing della request (N.B. non elimino il file appena creato)
                    }
                }
            }
            catch (FileUploadException fuex) {
                logger.error("", fuex);
                if (recivedFile != null)
                    recivedFile.delete();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }

        }
        catch (IOException ex) {
            logger.error("", ex);
            if (recivedFile != null)
                recivedFile.delete();

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        // risponde al client inviando l'url dove il file è stato memorizzato
        PrintWriter respOutWriter = null;
        try {
            respOutWriter = response.getWriter();
            String fileURL = null;
            if (verifier.isAllSignOK()) {
                fileURL = request.getRequestURL().toString().replace(request.getServletPath(), "/" + recivedSignedFileDir.getName()) + "/" + recivedFile.getName();
                if (logger.isInfoEnabled()) {
                	logger.info("Ricezione e verifica completate.\nURL del file: " + fileURL);
                }
            }
            else {
                if (logger.isInfoEnabled()) {
                	logger.info("Ricezione e verifica completate. Il file non ha superato la verifica delle firme e quindni non sarà memorizzato sul server.");
                }
            }

            String resp = verifier.getSignsResultString();
            if (logger.isInfoEnabled()) {
            	logger.info("stato delle firme che verrà inviato al client:\n" + resp);
            }

            if (fileURL != null && resp.equals(""))
                resp = fileURL;
            else if (!resp.equals("") && fileURL != null)
                resp = fileURL + "\n" + resp;

            	if (logger.isDebugEnabled()) {
            		logger.debug("Invio la risposta al client...");
            	}
                respOutWriter.print(resp);
        }
        catch (IOException ex) {
            logger.error("", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        finally {
            respOutWriter.close();
            if (logger.isInfoEnabled()) {
            	logger.info("Operazione completata.");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
