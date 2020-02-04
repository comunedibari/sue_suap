<%--
Copyright (c) 2011, Regione Emilia-Romagna, Italy
 
Licensed under the EUPL, Version 1.1 or - as soon they
will be approved by the European Commission - subsequent
versions of the EUPL (the "Licence");
You may not use this work except in compliance with the
Licence.

For convenience a plain text copy of the English version
of the Licence can be found in the file LICENCE.txt in
the top-level directory of this software distribution.

You may obtain a copy of the Licence in any of 22 European
Languages at:

http://joinup.ec.europa.eu/software/page/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.

This product includes software developed by Yale University

See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.model.ProcessData"%>
<%@page import="java.util.Iterator"%>
<%@page import="it.people.process.common.entity.Attachment"%>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />


<%  
try {
	ProcessData dataForm = (ProcessData) pplProcess.getData();
	Attachment attachSelected = null;
	String codiceFile = (String)request.getParameter("cod");
	boolean trovato = false;
	if (codiceFile!=null) {
		Iterator it = dataForm.getDettaglioPratica().getCertificato().iterator();
		while (it.hasNext() && !trovato){
			Attachment attach = (Attachment) it.next();
			if (attach.getDescrizione()!=null && attach.getDescrizione().equalsIgnoreCase(codiceFile)){
				trovato = true;
				attachSelected = attach;
			}
		}


		it = dataForm.getDettaglioPratica().getAltriAllegati().iterator();
		while (it.hasNext() && !trovato){
			Attachment attach = (Attachment) it.next();
			if (attach.getDescrizione()!=null && attach.getDescrizione().equalsIgnoreCase(codiceFile)){
				trovato = true;
				attachSelected = attach;
			}
		}

		
	}
	if (attachSelected!=null ){
		 
	    String type = null;
	    java.net.URL u = new java.net.URL("file://"+attachSelected.getPath());
	    java.net.URLConnection uc = null;
	    uc = u.openConnection();
	    type = uc.getContentType();

		
		
		File f = new File(attachSelected.getPath());
//		javax.activation.MimetypesFileTypeMap mimeTypesMap = new javax.activation.MimetypesFileTypeMap();
//		String tmp = attachSelected.getName();
//		File ff = new File(tmp);
//		String s = new javax.activation.MimetypesFileTypeMap().getContentType(ff);
//		String extension = attachSelected.getName().substring(attachSelected.getName().lastIndexOf(".")+1);
		//String mimeType = mimeTypesMap.getContentType(type);
		response.setHeader("Content-disposition","inline;filename="+attachSelected.getName());
	    response.setContentType(type);
	    FileInputStream fis = new FileInputStream (f);
	    String len = String.valueOf(f.length());
	    byte[] buf = new byte[Integer.parseInt(len)];
	    int length = fis.read(buf);
		response.getOutputStream().write(buf); 
	 	if(length==-1){
			throw new Exception("Documento non trovato");
	 	}
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	    response.flushBuffer();
	    fis.close();
	}
		

} catch (Exception e) {
	 %>
	 <p align="center" class="SectionText">
	 <% e.printStackTrace(new PrintWriter(out)); %>
	 </p><br>
<%	
}
%>
<script type="text/javascript">
	// self.close()
</script>
