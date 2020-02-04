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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Giuseppe
 */
public class MakeIndex extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
	
	private static Logger logger = LoggerFactory.getLogger(MakeIndex.class);
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
//        PrintWriter configWriter = null;
        BufferedReader in = null;
        BufferedReader htmlReader = null;
//        BufferedReader configReader = null;
        try {
            in = new BufferedReader(new FileReader(getServletContext().getRealPath("") + "/ClientLastVersion.txt"));
            String line = in.readLine();
            float clientVersionNumber;
            try {
                clientVersionNumber = Float.parseFloat(line);
            }
            catch (NumberFormatException ex) {
                logger.error("", ex);
                clientVersionNumber = -1.0f;
            }
            line = in.readLine();
            String programFileName = line;

            StringBuilder html = new StringBuilder();
            htmlReader = new BufferedReader(new FileReader(getServletContext().getRealPath("") + "/staticHomePage.html"));
            line = htmlReader.readLine();
            while (line != null) {
                line += "\n";
                html.append(line);
                line = htmlReader.readLine();
            }

            String htmlString = html.toString();
            htmlString = htmlString.replace("program_version", String.valueOf(clientVersionNumber));
            htmlString = htmlString.replace("program_link", programFileName);
            htmlString = htmlString.replace("program_name", programFileName);
            htmlString = htmlString.replace("manual_link", "");
            htmlString = htmlString.replace("manual_name", "ancora non c'è");
            out.print(htmlString);
            
//            // Sostituisco l'hostname di "WebServiceURL" neel file config.properties che l'applet scaricherà con l'hostname del server
//            File configFile = new File(getServletContext().getRealPath("") + "/requiredAppletFiles/config.properties");
//            configReader = new BufferedReader(new FileReader(configFile));
//            line = configReader.readLine();
//            StringBuilder configOutBuilder = new StringBuilder();
//            while (line != null) {
//                if (line.contains("WebServiceURL")) {
//                    String old_hostname = line.split(":")[1].replace("//", "");
//                    line = line.replace(old_hostname, request.getServerName());
//                }
//                line += "\n";
//                configOutBuilder.append(line);
//                line = configReader.readLine();
//            }
//            configWriter = new PrintWriter(configFile);
//            configWriter.print(configOutBuilder);

        }
        catch (IOException ex) {
        }
        finally {
            in.close();
            htmlReader.close();
//            configReader.close();
//            configWriter.close();
            out.close();
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
