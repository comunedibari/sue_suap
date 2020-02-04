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
package it.people.dbm.pages;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import it.people.dbm.dao.Documenti;
import it.people.dbm.dao.ImmaginiTemplate;
import it.people.dbm.dao.Normative;
import it.people.dbm.dao.OneriDocumenti;
import it.people.dbm.dao.Template;
import it.people.dbm.dao.TemplatesVari;
import it.people.dbm.model.DocumentoModel;
import it.people.dbm.model.FileUploadModel;
import it.people.dbm.model.ImmagineModel;
import it.people.dbm.model.NormativaModel;
import it.people.dbm.model.Tariffario;
import it.people.dbm.model.TemplateModel;
import it.people.dbm.model.TemplateModelFile;
import it.people.dbm.model.TemplateVariModel;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Piergiorgio
 */
public class ScaricaFile extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(ScaricaFile.class);
	
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        try {
            if (request.getParameter("tipo").equals("tariffari")) {
                OneriDocumenti classe = new OneriDocumenti();
                Tariffario tariffario = classe.leggi(request.getParameter("codDoc"));
                response.setContentType("\"" + tariffario.getTip_doc() + "\"");
                response.setContentLength((int) tariffario.getDoc_blob().length());
                response.setHeader("Content-Disposition", "attachment; filename=\"" + tariffario.getNome_file() + "\"");

                InputStream in = tariffario.getDoc_blob().getBinaryStream();
                int length = (int) tariffario.getDoc_blob().length();
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];

                while ((length = in.read(buffer)) != -1) {
                	if (log.isDebugEnabled()) {
                		log.debug("writing " + length + " bytes");
                	}
                    out.write(buffer, 0, length);
                }

                in.close();
            }
            if (request.getParameter("tipo").equals("template")) {
                Template classe = new Template();
                TemplateModel template = classe.leggi(request.getParameter("codSport"), request.getParameter("codCom"), request.getParameter("codProc"), request.getParameter("codServizio"), request.getParameter("codLang"));
                List<TemplateModelFile> ltmf = template.getListaFile();
                TemplateModelFile tmf = ltmf.get(0);
                response.setContentType("\"application/vnd.oasis.opendocument.text\"");
                response.setContentLength((int) tmf.getTemplate().length);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + tmf.getNomeFile() + "\"");
                out.write(tmf.getTemplate());
            }
            if (request.getParameter("tipo").equals("immagine")) {
                ImmaginiTemplate classe = new ImmaginiTemplate();
                ImmagineModel immagine = classe.leggi(request.getParameter("codSport"), request.getParameter("codCom"), request.getParameter("nomeImmagine"),request.getParameter("codLang") );
                response.setContentType("\"" + immagine.getTipoFile() + "\"");
                response.setContentLength((int) immagine.getImmagine().length);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + immagine.getNomeFile() + "\"");
                out.write(immagine.getImmagine());
            }
            if (request.getParameter("tipo").equals("templates_vari")) {
                TemplatesVari classe = new TemplatesVari();
                TemplateVariModel immagine = classe.leggi(request.getParameter("codSport"), request.getParameter("nomeTemplate"), request.getParameter("codLang"));
                response.setContentType("\"" + immagine.getTipoFile() + "\"");
                response.setContentLength((int) immagine.getTemplateVariRisorse().length);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + immagine.getNomeFile() + "\"");
                out.write(immagine.getTemplateVariRisorse());
            }

            if (request.getParameter("tipo").equals("normativa")) {
                Normative normativa = new Normative();
                NormativaModel norma = normativa.leggi(request.getParameter("codNorma"), request.getParameter("tipDoc"));
                FileUploadModel fum = norma.getFileUpload();
                response.setContentType("\"" + fum.getTipDoc() + "\"");
                response.setContentLength((int) fum.getLength().intValue());
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fum.getNomeFile() + "\"");

                InputStream in = fum.getDocBlob();
                int length = (int) fum.getLength().intValue();
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];

                while ((length = in.read(buffer)) != -1) {
                	if (log.isDebugEnabled()) {
                		log.debug("writing " + length + " bytes");
                	}
                    out.write(buffer, 0, length);
                }

                in.close();
            }
            if (request.getParameter("tipo").equals("documento")) {
                Documenti documento = new Documenti();
                DocumentoModel norma = documento.leggi(request.getParameter("codDoc"), request.getParameter("tipDoc"));
                FileUploadModel fum = norma.getFileUpload();
                response.setContentType("\"" + fum.getTipDoc() + "\"");
                response.setContentLength((int) fum.getLength().intValue());
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fum.getNomeFile() + "\"");

                InputStream in = fum.getDocBlob();
                int length = (int) fum.getLength().intValue();
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];

                while ((length = in.read(buffer)) != -1) {
                	if (log.isDebugEnabled()) {
                		log.debug("writing " + length + " bytes");
                	}
                    out.write(buffer, 0, length);
                }

                in.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
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
    @Override
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
