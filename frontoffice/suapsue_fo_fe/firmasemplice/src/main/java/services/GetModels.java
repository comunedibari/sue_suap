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
package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.mortbay.servlet.MultiPartResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Restituice al client uno stream di bytes rappresentante un file di properties che contiene la lista dei modelli trovati sul server
 *
 * @author Giuseppe
 */
public class GetModels extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
	
	private static Logger logger = LoggerFactory.getLogger(GetModels.class);
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String serverConfFiles = getServletContext().getRealPath("") + "/ConfFiles";
    File tempDir = new File(serverConfFiles + "/temp/");
    File modelsDir = new File(serverConfFiles + "/modelli/");

        if (!tempDir.exists())
            tempDir.mkdir();

        if (!modelsDir.exists())
                modelsDir.mkdir();

        Properties modelsList = new Properties();
        FileOutputStream modelsListOutputStream = null;
        FileInputStream modelsListInputStream = null;
        File modelsListFile = new File(tempDir.getAbsolutePath() + "/" + request.getSession().getId() + "_models_list_temp_.properties");
        try {
            modelsListOutputStream = new FileOutputStream(modelsListFile.getAbsolutePath());
            modelsListInputStream = new FileInputStream(modelsListFile.getAbsolutePath());
            modelsList.load(modelsListInputStream);
        }
        catch (FileNotFoundException ex) {
            logger.error("", ex);
            modelsListOutputStream.close();
            modelsListFile.delete();
//            response.setStatus(HttpServletResponse.SC_OK);
//            return;
        }
        catch (IOException ex) {
            logger.error("", ex);
            modelsListOutputStream.close();
            modelsListFile.delete();
            return;
        }

        try {
            // leggo il nome dei modelli dai singoli file di properties e li mando al client come messaggio multipart
            FilenameFilter propertiesFileFilter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    try {
                        if (name.substring(name.lastIndexOf(".") + 1, name.length()).equalsIgnoreCase("properties"))
                            return true;
                        else
                            return false;
                        }
                    catch (Exception ex) {
                        logger.error("", ex);
                        return false;
                    }
                }
            };
            String propertiesFileList[] = modelsDir.list(propertiesFileFilter);
            int n_models = 0;
            for(int i = 0; i < propertiesFileList.length; i ++) {
                Properties model = new Properties();
                FileInputStream modelFileInputStream = new FileInputStream(modelsDir.getAbsolutePath() + "/" + propertiesFileList[i]);
                model.load(modelFileInputStream);
                String modelName = model.getProperty("ModelName");
                if (modelName != null) {
                    n_models++;
                    modelsList.setProperty(propertiesFileList[i], modelName);
//                    System.out.println(model.getProperty("ModelName") + ";" + propertiesFileList[i]);
                }
                modelFileInputStream.close();
            }
            if (logger.isInfoEnabled()) {
            	logger.info("Modelli trovati: " + n_models);
            }
            modelsList.store(modelsListOutputStream, " Start Template");
            modelsListOutputStream.close();
            modelsListInputStream.close();
        }
        catch (NullPointerException ex) {
            logger.error("", ex);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            modelsListOutputStream.close();
            modelsListFile.delete();
            return;
        }
        catch (FileNotFoundException ex) {
            logger.error("", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            modelsListOutputStream.close();
            modelsListFile.delete();
            return;
        }
        catch (IOException ex) {
            logger.error("", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            modelsListOutputStream.close();
            modelsListFile.delete();
            return;
        }

        try {

//            MultiPartResponse mresp = new MultiPartResponse(response);
//            mresp.startPart("text/plain");
            OutputStream outresp = response.getOutputStream();

            FileInputStream fis = new FileInputStream(modelsListFile.getAbsolutePath());
            byte[] readData = new byte[1024];
            int i = fis.read(readData);
            while (i != -1) {
                outresp.write(readData, 0, i);
                i = fis.read(readData);
            }
            fis.close();
            outresp.close();
        }
        catch (IOException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            modelsListFile.delete();
            return;
        }
        modelsListFile.delete();
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
