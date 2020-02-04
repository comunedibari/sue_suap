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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.*"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<title>PDF</title>
</head>
<body>
<%
	byte[] pdf = null;
	try {
 
       	ManagerDocumentiDinamici mdd = new ManagerDocumentiDinamici();
       	String codSportello = (String)request.getParameter("cod");
       	String moduloInBianco = (String)request.getParameter("white");
// PC - Stampa bozza inizio  
        String stampaBozza = (String)request.getParameter("bozza");        
// PC - Stampa bozza fine         
     
// PC - Stampa bozza inizio          
//       	if (moduloInBianco!=null && moduloInBianco.equalsIgnoreCase("TRUE")) {
//	         pdf = mdd.invokeDocDynModuloPDF(pplProcess,codSportello,request.getSession(), request, true);
//       	} else {
//       		pdf = mdd.invokeDocDynModuloPDF(pplProcess,codSportello,request.getSession(), request, false);
//       	}
       	if (stampaBozza!=null && stampaBozza.equalsIgnoreCase("TRUE")) {
	         pdf = mdd.invokeDocDynModuloPDF(pplProcess,codSportello,request.getSession(), request, false, true);
       	} else {
       		pdf = mdd.invokeDocDynModuloPDF(pplProcess,codSportello,request.getSession(), request, false, false);
       	}        
// PC - Stampa bozza fine                 
        
        
  		response.resetBuffer();

        response.setHeader("Content-disposition","inline;filename=moduloPDF.pdf");
        response.setContentType("application/pdf");
        if(pdf.length > 0){
        	response.getOutputStream().write(pdf); 
            response.getOutputStream().flush();
            response.getOutputStream().close();
            response.flushBuffer();
        } else {
        	out.println(" Errore nell'invocazione dei Documenti Dinamici");
        	System.out.println(" Errore nell'invocazione dei Documenti Dinamici");
        }
        
        
		

	} catch (Exception e) {
		System.out.println("ERRORE - DOCUMENTI DINAMICI");
		e.printStackTrace();
		response.setHeader("Pragma","No-cache");
        response.setContentType("text/html");
        String error = "<h3>Si e' verificato un errore, ci scusiamo per il disagio.</h3>";
    	response.getOutputStream().write(error.getBytes()); 
        response.getOutputStream().flush();
        response.getOutputStream().close();  
        response.flushBuffer();
        
	}
%>
</body>
</html>
