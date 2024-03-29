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
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@page import="java.util.*"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatoBean"%>
<link rel="stylesheet" type="text/css" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/style.css" />
<html:xhtml/>

<logic:equal value="true" name="pplProcess" property="data.internalError">
    <jsp:include page="defaultError.jsp" flush="true" />
</logic:equal>

<logic:notEqual value="true" name="pplProcess" property="data.internalError">
    <logic:messagesPresent>
        <table style="border:2px dotted red; padding: 3px; width:96%;">
            <tr>
                <td><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
                    <b><ppl:errors /></b>
                </td>
            </tr>
        </table>
    </logic:messagesPresent>

    <jsp:include page="webclock.jsp" flush="true" />

    <br/><h3><bean:message key="allegatiFacoltativi.titolo"/></h3>
    <table style="border-collapse:collapse;" cellpadding="5" cellspacing="0">
        <%  ProcessData data = (ProcessData) pplProcess.getData();

            ArrayList listaAllFac = (ArrayList) request.getAttribute("allegatiFacoltativi");
            Iterator it = listaAllFac.iterator();
            int index = 0;
            while (it.hasNext()) {
                AllegatoBean allegato = (AllegatoBean) it.next();
                if (!allegato.isIndicatoreOptionAllegati()) {

                    if (allegato.isChecked()) {
        %>
        <tr> 
            <td width="6%">&nbsp;&nbsp;&nbsp;<input type='checkbox' id='<%=allegato.getCodiceCondizione()%><%=index%>' name='<%=allegato.getCodiceCondizione()%>'  checked="checked" />&nbsp;&nbsp;</td>
            <td width="94%" class="labelOperazioni"><label for='<%=allegato.getCodiceCondizione()%><%=index%>'><%=allegato.getTestoCondizione()%></label>
                <%if (allegato.getCodiceNormativaVisulizzata() != null && !allegato.getCodiceNormativaVisulizzata().equalsIgnoreCase("")) {
                        String link = "normativa.jsp&index=" + allegato.getCodiceNormativaVisulizzata();%> 
                <ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
                <%}%>	
            </td> 
        </tr>
        <%} else {%>	
        <tr>
            <td width="6%">&nbsp;&nbsp;&nbsp;<input type='checkbox' id='<%=allegato.getCodiceCondizione()%><%=index%>' name='<%=allegato.getCodiceCondizione()%>' />&nbsp;&nbsp;</td>
            <td width="94%" class="labelOperazioni"><label for='<%=allegato.getCodiceCondizione()%><%=index%>'><%=allegato.getTestoCondizione()%></label>
                <%if (allegato.getCodiceNormativaVisulizzata() != null && !allegato.getCodiceNormativaVisulizzata().equalsIgnoreCase("")) {
                        String link = "normativa.jsp&index=" + allegato.getCodiceNormativaVisulizzata();%> 
                <ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
                <%}%>											
            </td>
        </tr>
        <%}
        } else {
            String rowBgColor = "#FFFFFF";
            if ((index % 2) == 0) {
                rowBgColor = "#F7F7F7";
            }
            if (allegato.isInitialized()) {
                if (allegato.isChecked()) {
        %>                
        <tr>
            <td bgcolor="<%=rowBgColor%>"><input type="radio" id="<%=allegato.getCodiceCondizione()%><%=index%>_allega" value="S" checked="checked" name="<%=allegato.getCodiceCondizione()%>" /><label class="labelOperazioni" for="<%=allegato.getCodiceCondizione()%><%=index%>_allega"><bean:message key="allegatiFacoltativi.allega"/></label></td>
            <td bgcolor="<%=rowBgColor%>" rowspan="2" class="labelOperazioni"><%=allegato.getTestoCondizione()%>
                <%if (allegato.getCodiceNormativaVisulizzata() != null && !allegato.getCodiceNormativaVisulizzata().equalsIgnoreCase("")) {
                        String link = "normativa.jsp&index=" + allegato.getCodiceNormativaVisulizzata();%> 
                <ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
                <%}%>											
            </td>
        </tr> 
        <tr>
            <td bgcolor="<%=rowBgColor%>"><input type="radio" id="<%=allegato.getCodiceCondizione()%><%=index%>_nonallega" value="N" name="<%=allegato.getCodiceCondizione()%>" /><label class="labelOperazioni" for="<%=allegato.getCodiceCondizione()%><%=index%>_nonallega"><bean:message key="allegatiFacoltativi.nonAllega"/></label></td>
        </tr>
        <%
        } else {
        %>
        <tr>
            <td bgcolor="<%=rowBgColor%>"><input type="radio" id="<%=allegato.getCodiceCondizione()%><%=index%>_allega" value="S" name="<%=allegato.getCodiceCondizione()%>" /><label class="labelOperazioni" for="<%=allegato.getCodiceCondizione()%><%=index%>_allega"><bean:message key="allegatiFacoltativi.allega"/></label></td>
            <td bgcolor="<%=rowBgColor%>" rowspan="2" class="labelOperazioni"><%=allegato.getTestoCondizione()%>
                <%if (allegato.getCodiceNormativaVisulizzata() != null && !allegato.getCodiceNormativaVisulizzata().equalsIgnoreCase("")) {
                        String link = "normativa.jsp&index=" + allegato.getCodiceNormativaVisulizzata();%> 
                <ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
                <%}%>											
            </td>
        </tr> 
        <tr>
            <td bgcolor="<%=rowBgColor%>"><input type="radio" id="<%=allegato.getCodiceCondizione()%><%=index%>_nonallega" value="N" checked="checked" name="<%=allegato.getCodiceCondizione()%>" /><label class="labelOperazioni" for="<%=allegato.getCodiceCondizione()%><%=index%>_nonallega"><bean:message key="allegatiFacoltativi.nonAllega"/></label></td>
        </tr>
        <%
            }
        } else {

        %>

        <tr>
            <td bgcolor="<%=rowBgColor%>"><input type="radio" id="<%=allegato.getCodiceCondizione()%><%=index%>_allega" value="S" name="<%=allegato.getCodiceCondizione()%>" /><label class="labelOperazioni" for="<%=allegato.getCodiceCondizione()%><%=index%>_allega"><bean:message key="allegatiFacoltativi.allega"/></label></td>
            <td bgcolor="<%=rowBgColor%>" rowspan="2" class="labelOperazioni"><%=allegato.getTestoCondizione()%>
                <%if (allegato.getCodiceNormativaVisulizzata() != null && !allegato.getCodiceNormativaVisulizzata().equalsIgnoreCase("")) {
                        String link = "normativa.jsp&index=" + allegato.getCodiceNormativaVisulizzata();%> 
                <ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
                <%}%>											
            </td>
        </tr> 
        <tr>
            <td bgcolor="<%=rowBgColor%>"><input type="radio" id="<%=allegato.getCodiceCondizione()%><%=index%>_nonallega" value="N" name="<%=allegato.getCodiceCondizione()%>" /><label class="labelOperazioni" for="<%=allegato.getCodiceCondizione()%><%=index%>_nonallega"><bean:message key="allegatiFacoltativi.nonAllega"/></label></td>
        </tr>

        <%
            }
        %>
        <tr style="height: 10px;"><td colspan="2"></td></tr>
            <%
                    }
                    index++;
                }
            %>

    </table>
    <br/>
    <%@include file="bookmark.jsp"%>
</logic:notEqual>
