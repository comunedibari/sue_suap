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
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DocumentoFisicoBean"%>
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.controllers.PrecompilazioneAllegati"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.commons.*, java.io.*, java.sql.*"%>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<%-- Orrendo accrocchio per mostrare allegati e normative prelevandoli dal db --%>
<%-- PC - precompilazione allegati inizio --%>
<%
    String language = request.getParameter("language");
    String codDoc = request.getParameter("cod_doc");
    String codRif = request.getParameter("cod_rif");
    try {
        PrecompilazioneAllegati pa = new PrecompilazioneAllegati();
        DocumentoFisicoBean dfb = pa.PrecompilazioneAllegati(pplProcess, request, session, language, codDoc, codRif);
        if (dfb != null) {
            if (dfb.getDocumentoFisico() == null) {
                throw new Exception("Documento non trovato");
            }
            int length = (int) dfb.getDocumentoFisico().length();
            response.setHeader("Content-disposition", "attachment;filename=\"" + dfb.getNomeFile()+ "\"");
            response.setContentType(dfb.getContentType());
            response.setContentLength((int) dfb.getDocumentoFisico().length());
            byte[] buf = dfb.getDocumentoFisico().getBytes(1, length);
            response.getOutputStream().write(buf);
            if (length == -1) {
                throw new Exception("Documento non trovato");
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
            response.flushBuffer();
        }
    } catch (Exception se) {
%>
<%-- PC - precompilazione allegati fine --%>
<p align="center" class="SectionText">
    <% se.printStackTrace(new PrintWriter(out));%>
</p><br>
<%
    }

%>                
<script type="text/javascript">
    self.close()
</script>
