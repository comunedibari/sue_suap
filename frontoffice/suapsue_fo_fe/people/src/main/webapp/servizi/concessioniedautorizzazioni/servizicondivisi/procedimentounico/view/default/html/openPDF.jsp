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

http://www.osor.eu/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.
See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.people.util.ServiceParameters"%>
<%@ page import="it.gruppoinit.commons.Utilities"%>
<title>PDF</title>
</head>
<body>
<%
	try {
		
		  response.resetBuffer();

          response.setHeader("Content-disposition","inline;filename=riepilogoPDF.pdf");
          response.setContentType("application/pdf");
          byte[] buf = (byte[])session.getAttribute("pdfByteArray");
          if(buf.length > 0){
        	  System.out.println("byte pdf ricevuti");
              //if(length==-1){
 			  //	 throw new Exception("Documento non trovato");
 			  //}
 			  //response.setContentLength(buf.length);
              //data.close();
              response.getOutputStream().write(buf); 
              response.getOutputStream().flush();
              response.getOutputStream().close();
              
              response.flushBuffer();
	          session.removeAttribute("pdfByteArray");
	          
	          //response.setContentLength(baos.size());
			  //ServletOutputStream outputStream = response.getOutputStream();
			  //baos.writeTo(outputStream);
			  //outputStream.flush();
          }else {
              response.setContentType("text/html");
              java.io.PrintWriter writer = response.getWriter();
              writer.println("Si � verificato un errore durante la lettura del PDF");
              session.removeAttribute("pdfByteArray");
          }
        
		

	} catch (Exception e) {System.out.println("---");}
%>
</body>
</html>
