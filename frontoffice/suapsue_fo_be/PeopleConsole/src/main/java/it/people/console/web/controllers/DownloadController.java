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
package it.people.console.web.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;

import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.console.web.utils.WebUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 15/lug/2011 09.50.27
 *
 */
@Controller
@RequestMapping("")
public class DownloadController extends MessageSourceAwareController {

	public DownloadController() {
		this.setCommandObjectName("commandObject");
	}
	
    @RequestMapping(value = "download.do", method = RequestMethod.GET)
    public String setupDownload(ModelMap model, HttpServletRequest request, 
    		HttpServletResponse response) {
    	
    	String fileName = request.getParameter("fileName");
//    	String fileType = request.getParameter("type");
    	String fileKey = null;
		try {
			fileKey = UriUtils.decode(request.getParameter("key"), "UTF-8");
			File tmpDir = WebUtils.getTempDir(request.getSession().getServletContext());
	    	File downloadFile = new File(tmpDir, fileKey);

			BufferedInputStream in = new BufferedInputStream(new FileInputStream(downloadFile));
			String mimetype = request.getSession().getServletContext().getMimeType(downloadFile.getAbsolutePath());

			int fileSize = (int)downloadFile.length();
			response.setBufferSize(fileSize);
			response.setContentType(mimetype);
			response.setHeader("Pragma", "private");
			response.setHeader("Cache-Control", "private, must-revalidate");
			response.setHeader("Content-Disposition", "attachment; filename=\"" 
					+ fileName + "\"");
			response.setContentLength(fileSize);
	
			FileCopyUtils.copy(in, response.getOutputStream());
			in.close();
			
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return "";
    	
    }

}
