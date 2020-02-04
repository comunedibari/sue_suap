<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.steps.ModelloUnicoStep"%>
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.PrintStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.*"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" /><%
    try {
        final String wsUrl = "SIMPLEDESK_PD_WS";
        ProcessData processData = (ProcessData) pplProcess.getData();
        String encoding = request.getCharacterEncoding();

        if (request.getParameter("scarica") != null) {
            Iterator hrefIterator = processData.getListaHref().values().iterator();
            ModelloUnicoStep mus = new ModelloUnicoStep();
            while (hrefIterator.hasNext()) {
                SezioneCompilabileBean href = (SezioneCompilabileBean) hrefIterator.next();
                while (href.getNumSezioniMultiple() > 0 && href.getNumSezioniMultiple() < 5) {
                    mus.aggiungiSezioneMultipla(href);
                }
            }
            // end grow multiple fields
            String xml = Bean2XML.marshallObject(processData, encoding);
            String xmlBlob = new String(Base64.encodeBase64(xml.getBytes("UTF-8")));
            String pdfBlob = pplProcess.callService(wsUrl, "{'method':'xmlToPdf','processData':'" + xmlBlob + "'}");
            byte[] pdf = Base64.decodeBase64(pdfBlob.getBytes());
            if (pdf == null || pdf.length == 0) {
                throw new Exception("unable to obtain pdf file");
            }
            StringBuilder fileNameBuilder = new StringBuilder("modulo_compilabile_");
            List interventi = processData.getInterventi();
            for (int i = 0; i < interventi.size(); i++) {
                if (fileNameBuilder.length() > 50) {
                    fileNameBuilder.append("_etc");
                    break;
                }
                if (i > 0) {
                    fileNameBuilder.append("_e_");
                }
                fileNameBuilder.append(((InterventoBean) interventi.get(i)).getDescrizione());
            }
            String fileName = fileNameBuilder.toString().replaceAll("[^a-zA-Z0-9]+", "_").replaceAll("_$", "") + ".pdf";
            response.setHeader("Content-disposition", "inline;filename=" + fileName);
            response.setContentType("application/pdf");
            response.getOutputStream().write(pdf);
            response.getOutputStream().close();
        }
        String errorMessage = (String) request.getAttribute("moduloCompilabile_errorMessage");
        if (request.getParameter("ricarica") != null || errorMessage != null) {

%>

<h1><bean:message key="moduloCompilabile.ricarica.title"/></h1>
<div style="border:1px solid #EAEAEA; padding: 5px; width:96%;" >
    <% if (errorMessage != null) {%> <div style="border:2px dotted red; padding: 12px; width:96%; margin: 12px; font-size: 12px"><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione"><b><%=errorMessage%></b></div> <% }%>
    <form enctype="multipart/form-data" method="post" action="servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/moduloCompilabile.jsp"
          onSubmit="if(document.getElementById('caricaModuloCompilato_fileInput').value == '') return false;" >
        <div style="padding:5em 4em 5em 3em;">
            &nbsp;&nbsp;<label><bean:message key="moduloCompilabile.labelFile" /></label>&nbsp;&nbsp;
            <html:file styleId="nomeallegato" property="data.uploadFile"/>
            &nbsp;&nbsp;<bean:message key="moduloCompilabile.forzaCaricamento" />&nbsp;<input class="btn" type="checkbox" name="forzaCaricamento" value="forzaCaricamento">
            &nbsp;&nbsp;<input class="btn" type="submit" name="navigation.button.caricaModuloCompilato" value="<bean:message key="moduloCompilabile.button.carica" />" id="caricaModuloCompilato_fileInput">
        </div>
    </form>
</div>
<div align="center">

    <ppl:linkLoopback accesskey="B" property="modelloUnico.jsp" styleClass="btn"><bean:message key="moduloCompilabile.button.annulla" /></ppl:linkLoopback>
    </div>
    <script type="text/javascript" >
        window.onload=function(){
            document.getElementById('footer').style.display='none'; // hide footer buttons
        }
    </script>

<%    }
    } catch (Exception e) {
        System.out.println("ERRORE - MODULO COMPILABILE");
        e.printStackTrace();
        response.setHeader("Pragma", "No-cache");
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        if (e.getMessage().contains("compatibile")) {
            writer.write("<h3>Si e' verificato un errore: il modulo caricato non corrisponde con il processo selezionato.</h3>\n");
        } else {
            writer.write("<h3>Si e' verificato un errore, ci scusiamo per il disagio.</h3>\n");
            e.printStackTrace(writer);
        }
        writer.close();
    } finally {
        response.flushBuffer();
    }
%>
