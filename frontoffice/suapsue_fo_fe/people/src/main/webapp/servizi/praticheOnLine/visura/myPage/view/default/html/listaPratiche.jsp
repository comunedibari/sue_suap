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
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>  
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@page import="java.util.*"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.PraticaBean"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.DatiEstesiMyPage"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.IndirizzoMyPage"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.DeleganteMyPage"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.ActivityBean"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.model.ProcessData"%>
<%@page import="it.people.process.SubmittedProcessHistory"%>
<%@page import="it.people.util.DateFormatter"%>
<%@page import="it.people.process.SubmittedProcessState"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.TipologiaPraticheSelezionabili" %>
<%@page import="it.people.util.MessageBundleHelper" %>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<html:xhtml/>
<SCRIPT>
    function goto(where) {
        document.forms[0].action = where;
        document.forms[0].submit();
    }

    function errore(TextBox)
    {
        alert("La data non è valida");
        TextBox.value = "";
        TextBox.focus();
    }

    function isValidDate(TextBox, MostraERR)
    {
        dateStr = TextBox.value;
        var datePat = /^(\d{1,2})(\/|-)(\d{1,2})\2(\d{2,4})$/;

        if (dateStr == "")
        {
            return true;
        }

        if (dateStr.match("/") == null)
        {
            if ((dateStr.length = 8) || (dateStr.length = 6)) {
                var dateStrTmp = dateStr.substring(0, 2) + "/" + dateStr.substring(2, 4) + "/" + dateStr.substring(4, dateStr.length);
                dateStr = dateStrTmp;
                TextBox.value = dateStr;
            } else {
                errore(TextBox);
            }
        } else {
            aData = dateStr.split("/");
            day = aData[0];
            month = aData[1];
            year = aData[2];
            if (day.length == 1) {
                day = "0" + day;
            }
            if (month.length == 1) {
                month = "0" + month;
            }
            var dateStrTmp = day + "/" + month + "/" + year;
            dateStr = dateStrTmp;
            TextBox.value = dateStr;
        }

        var matchArray = dateStr.match(datePat);

        if (matchArray == null) {
            if (MostraERR)
            {
                errore(TextBox);
            }
            return false;
        }
        month = matchArray[3];
        day = matchArray[1];
        year = matchArray[4];
        // controllo lunghezza anno
        if (year.length == 3) {
            if (MostraERR) {
                errore(TextBox);
            }
            return false;
        }
        // Aggiusta l'anno
        switch (year.length)
        {
            case 2:
                if (year > "50")
                {
                    year = "19" + year.substring(0, 2);
                }
                else
                {
                    year = "20" + year.substring(0, 2);
                }
                break;
        }
        dateStr = dateStr.substring(0, 6) + year;
        //Riscrive la data nel text box
        TextBox.value = dateStr;

        if (month < 1 || month > 12) {
            if (MostraERR)
            {
                errore(TextBox);
            }
            return false;
        }

        if (day < 1 || day > 31) {
            if (MostraERR)
            {
                errore(TextBox);
            }
            return false;
        }

        if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
            if (MostraERR)
            {
                errore(TextBox);
            }
            return false
        }

        if (month == 2) {
            var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
            if (day > 29 || (day == 29 && !isleap)) {
                if (MostraERR)
                {
                    errore(TextBox);
                }
                return false;
            }
        }
        return true;
    }

</SCRIPT>
<% ProcessData dataForm = (ProcessData) pplProcess.getData();
    String erroriInvioValue = TipologiaPraticheSelezionabili.erroriInvio.getCodice();
    String inCompilazioneValue = TipologiaPraticheSelezionabili.inCompilazione.getCodice();
    String inviateValue = TipologiaPraticheSelezionabili.inviate.getCodice();
%>

<table style="border:1px solid #EAEAEA; padding: 5px; width:98%;">
    <tr><td>
            <logic:equal value="true" name="pplProcess" property="data.inCompilazione" >
                <h2><bean:message key="label.lista.compilazione"/></h2>
                <bean:message key="subtitle.lista.compilazione"/>
            </logic:equal>
            <logic:notEqual value="true" name="pplProcess" property="data.inCompilazione" >
                <logic:equal value="<%=erroriInvioValue%>" name="pplProcess" property="data.tipologiaSelezionata">
                    <h2><bean:message key="label.lista.nonInviate"/></h2>
                </logic:equal>
                <logic:equal value="<%=inviateValue%>" name="pplProcess" property="data.tipologiaSelezionata">
                    <h2><bean:message key="label.lista.completate"/></h2>
                    <bean:message key="subtitle.lista.inviate"/>
                </logic:equal>
            </logic:notEqual>
        </td></tr>

    <%-- PC - paginazione inizio --%>
    <%--<pg:pager url="lookupDispatchProcess.do"  export="currentPageNumber=pageNumber" maxPageItems="<%=Integer.parseInt(dataForm.getNumPratichePerPag()) %>"  maxIndexPages="<%=Integer.parseInt(dataForm.getNumPratichePerPag()) %>" >--%>
    <pg:pager url="lookupDispatchProcess.do"  items="<%=Integer.parseInt(dataForm.getNumPratiche())%>" export="offset,currentPageNumber=pageNumber" maxPageItems="<%=Integer.parseInt(dataForm.getNumPratichePerPag())%>"  maxIndexPages="<%=Integer.parseInt(dataForm.getNumPratichePerPag())%>" isOffset="<%= true%>" >
        <%-- PC - paginazione fine --%>
        <pg:param name="keywords"/>  
        <ex:searchresults>

            <%
                if (dataForm.getListaPratiche() != null) {
                    Iterator it = dataForm.getListaPratiche().iterator();
                    while (it.hasNext()) {
                        PraticaBean pratica = (PraticaBean) it.next();
            %>
            <c:out value='${pratica}'/>
            <pg:item>
                <tr><td>
                        <!--  <img src="/people/servizi/praticheOnLine/visura/myPage/img/pallino.png" alt="<bean:message key="alt.puntoDiElenco"/>" />&nbsp;--><b><%=pratica.getContentName()%></b>&nbsp;-&nbsp;
                        <% String initQuery = "/initProcess.do?"
                                    + "processName=" + pratica.getProcessName()
                                    + "&amp;processId=" + pratica.getProcessDataID() + "&amp;initFromMyPage=true";
                        %>
                        <% String resendQuery = "/initProcess.do?"
                                    + "processName=" + pratica.getProcessName()
                                    + "&amp;processId=" + pratica.getProcessDataID()
                                    + "&amp;sbmtProcessId=" + pratica.getOid()
                                    + "&amp;resend=true" + "&amp;initFromMyPage=true";
                        %>
                        <% if (pratica.getStatus() != null && pratica.getStatus().startsWith("S_SIGN")) {%>
                        <html:link page='<%=initQuery%>' >
                            <span class="txtNormal">&nbsp;<strong><%=pratica.getProcessDataID()%>&nbsp;&nbsp;<img src="/people/servizi/praticheOnLine/visura/myPage/img/document-sign.png" title="Completamento firma ed invio" alt="Completamento firma ed invio" /></strong></span>
                                </html:link>
                                <%} else if (pratica.getStatus() != null && pratica.getStatus().equals("S_EDIT")) {%>
                                <html:link page='<%=initQuery%>' >
                            <span class="txtNormal">&nbsp;<strong><%=pratica.getProcessDataID()%>&nbsp;&nbsp;<img src="/people/servizi/praticheOnLine/visura/myPage/img/modifica.gif" title="Ritorna in compilazione" alt="Ritorna in compilazione" /></strong></span>
                                </html:link>
                                <% } else {%> <span class="txtNormal">&nbsp;<strong><%=pratica.getProcessDataID()%></strong></span>
                        <%} %>
                        <% if (pratica.isSendError()) {%>
                        <html:link page='<%=resendQuery%>' >
                            <span class="txtNormal">&nbsp;<strong><%=pratica.getProcessDataID()%>&nbsp;&nbsp;<img src="/people/servizi/praticheOnLine/visura/myPage/img/resend.png" title="Re-invio pratica" alt="Re-invio pratica" /></strong></span>
                                </html:link>
                                <% } %>
                        &nbsp;&nbsp;
                        <%String link = "dettaglioPratiche.jsp&index=" + pratica.getOid();%> 
                        <ppl:linkLoopback property="<%=link%>" ><img src="/people/servizi/praticheOnLine/visura/myPage/img/draw_zoomG.jpg" title="Visualizza dettaglio pratica" alt="Visualizza dettaglio pratica" /></ppl:linkLoopback>
                            <br />
                        <%if (pratica.getOggetto() != null && !pratica.getOggetto().equalsIgnoreCase("")) {%>
                        <bean:message key="label.lista.oggetto"/> <%=pratica.getOggetto()%><br />
                        <%}%>
                        <bean:message key="label.lista.richiedente"/> [<%=pratica.getUserID()%>]<br />
                        <% if (pratica.getDatiEstesiMyPage() != null && pratica.getDatiEstesiMyPage().getDelegantiMyPage() != null && !pratica.getDatiEstesiMyPage().getDelegantiMyPage().isEmpty()) {
                                for (DeleganteMyPage anag : pratica.getDatiEstesiMyPage().getDelegantiMyPage()) {
                        %>
                        <strong><bean:message key="label.lista.procedimento.beneficiario"/></strong>
                        <% if (anag.getCognome() != null && !"".equals(anag.getCognome())) {%>
                        <%= anag.getCognome()%> &nbsp;                            
                        <% } %>
                        <% if (anag.getNome() != null && !"".equals(anag.getNome())) {%>
                        <%= anag.getNome()%> &nbsp;                            
                        <% } %>
                        <% if (anag.getDenominazione() != null && !"".equals(anag.getDenominazione())) {%>
                        <%= anag.getDenominazione()%> &nbsp;                            
                        <% } %>
                        <% if (anag.getCodiceFiscale() != null && !"".equals(anag.getCodiceFiscale())) {%>
                        <strong><bean:message key="label.lista.procedimento.beneficiario.codiceFiscale"/>&nbsp;<%= anag.getCodiceFiscale()%> &nbsp;</strong>                            
                        <% } %>
                        <% if (anag.getPartitaIva() != null && !"".equals(anag.getPartitaIva())) {%>
                        <strong><bean:message key="label.lista.procedimento.beneficiario.partitaIva"/>&nbsp;<%= anag.getPartitaIva()%> &nbsp;</strong>                            
                        <% } %>
                        <br />
                        <%
                                }
                            }
                        %>

                        <% if (pratica.getDatiEstesiMyPage() != null && pratica.getDatiEstesiMyPage().getIndirizziMyPage() != null && !pratica.getDatiEstesiMyPage().getIndirizziMyPage().isEmpty()) {
                                for (IndirizzoMyPage ind : pratica.getDatiEstesiMyPage().getIndirizziMyPage()) {
                        %>
                        <strong><bean:message key="label.lista.procedimento.indirizzo"/></strong>
                        <% if (ind.getVia() != null && !"".equals(ind.getVia())) {%>
                        <%= ind.getVia()%>                            
                        <% } %>
                        <% if (ind.getCivico() != null && !"".equals(ind.getCivico())) {%>
                        ,<%= ind.getCivico()%> &nbsp;                            
                        <% } %>
                        <% if (ind.getLettera() != null && !"".equals(ind.getLettera())) {%>
                        <strong><bean:message key="label.lista.procedimento.lettera"/></strong>
                        <%= ind.getLettera()%> &nbsp;                            
                        <% } %>                        
                        <% if (ind.getColore() != null && !"".equals(ind.getColore())) {%>
                        <strong><bean:message key="label.lista.procedimento.colore"/></strong>
                        <%= ind.getColore()%> &nbsp;                            
                        <% } %>
                        <% if (ind.getScala() != null && !"".equals(ind.getScala())) {%>
                        <strong><bean:message key="label.lista.procedimento.scala"/></strong>
                        <%= ind.getScala()%> &nbsp;                            
                        <% } %>
                        <% if (ind.getPiano() != null && !"".equals(ind.getPiano())) {%>
                        <strong><bean:message key="label.lista.procedimento.piano"/></strong>
                        <%= ind.getPiano()%> &nbsp;                            
                        <% } %>                        
                        <% if (ind.getInterno() != null && !"".equals(ind.getInterno())) {%>
                        <strong><bean:message key="label.lista.procedimento.interno"/></strong>
                        <%= ind.getInterno()%> &nbsp;                            
                        <% } %>
                        <% if (ind.getInternoLettera() != null && !"".equals(ind.getInternoLettera())) {%>
                        <strong><bean:message key="label.lista.procedimento.internolettera"/></strong>
                        <%= ind.getInternoLettera()%> &nbsp;                            
                        <% } %>
                        
                        <% if (ind.getComune() != null && !"".equals(ind.getComune())) {%>
                        &nbsp;( <%= ind.getComune()%> )                            
                        <% } %>
                        <br />
                        <%
                                }
                            }
                        %>

                        <logic:equal value="true" name="pplProcess" property="data.inCompilazione" >
                            <bean:message key="label.lista.procedimento"/>  <%=pratica.getCreation_time()%><br/>
                            <% if (pratica.getStatus().startsWith("S_SIGN")) {%>
                            <span class="txtNormal">
                                &nbsp; [ <bean:message key="label.modulionline.InFaseDiFirma" />
                                <html:link page='<%= "/abortSign.do?processName=" + pratica.getProcessName() + "&amp;processId=" + pratica.getOid()%>' >
                                    <span class="txtNormal"><bean:message key="label.modulionline.AnnullaFirma" /></span>
                                </html:link>]
                            </span>
                            <% } else if (pratica.getStatus().equals("S_EDIT")) {%>
                            <span class="txtNormal">
                                &nbsp; [ <bean:message key="label.modulionline.InFaseDiModifica" /> <strong><%=pratica.getPaymentStatus()%></strong>
                                <%String link2 = "deletePratica.jsp&index=" + pratica.getOid();%> 
                                <ppl:linkLoopback property="<%=link2%>" ><img src="/people/servizi/praticheOnLine/visura/myPage/img/delete.png" title="Elimina pratica" alt="Elimina pratica" /></ppl:linkLoopback>
                                    <!-- 
                                <html:link page='<%= "/framework/protected/deleteProcess.jsp?processId=" + pratica.getOid()%>' >
                                    <span class="txtNormal"><bean:message key="label.modulionline.ElimaProcedimento" /></span>
                                </html:link>
                                -->
                                ]
                            </span>			
                            <% } %>
                        </logic:equal>
                        <logic:notEqual value="true" name="pplProcess" property="data.inCompilazione" >
                            <%Iterator itt = pratica.getActivityHistory().iterator();
                                while (itt.hasNext()) {
                                    ActivityBean submittedHistory = (ActivityBean) itt.next();
                                    String date = DateFormatter.format(submittedHistory.getTimestamp());
                            %>
                            <span class="txtNormal">
                                <%=date%>&nbsp;<bean:message key="<%=SubmittedProcessState.get(new Long(submittedHistory.getCodice())).getMessageKey()%>" />
                            </span><br/>

                            <%
                                }
                            %>
                        </logic:notEqual>

                    </td></tr>
                <tr><td>&nbsp;</td></tr>
            </pg:item>
            <%
                    }
                }
            %>


        </ex:searchresults> 
        <% if (dataForm.getListaPratiche() == null || dataForm.getListaPratiche().size() == 0) {%>
        <tr><td>&nbsp;&nbsp;<bean:message key="label.lista.nessuna" /></td></tr>

        <% }%>
        <tr><td>&nbsp;</td></tr>


        <tr><td>
                <pg:index>
                    <P align="center" class="rows">
                        <pg:first unless="current">
                            <a href="<%= pageUrl%>"><img style="vertical-align:baseline;" src="/people/servizi/praticheOnLine/visura/myPage/img/resultset_firstG.jpg" title="Prima pagina" alt="Prima pagina" /></a>
                            </pg:first>

                        <pg:prev>
                            <a href="<%= pageUrl%>"><img style="vertical-align:baseline;" src="/people/servizi/praticheOnLine/visura/myPage/img/resultset_previousG.jpg" title="Pagina precedente" alt="Pagina precedente" /></a>
                            </pg:prev>

                        <pg:pages>
                            <%
                                if (pageNumber == currentPageNumber) {
                            %><b style="font-size:100%; vertical-align:baseline;color:#921115;" >&nbsp;&nbsp;<%= pageNumber%></b><%
                            } else {
                            %>&nbsp;&nbsp;<a style="text-decoration:none; font-size:100%; vertical-align:baseline; color:#888;" href="<%= pageUrl%>"><b><%= pageNumber%></b></a><%
                                }
                                    %>
                                </pg:pages>

                        <pg:next>
                            &nbsp;&nbsp;<a href="<%= pageUrl%>"><img style="vertical-align:baseline;" src="/people/servizi/praticheOnLine/visura/myPage/img/resultset_nextG.jpg" title="Pagina successiva" alt="Pagina successiva" /></a>
                            </pg:next>

                        <pg:last unless="current">
                            <a href="<%= pageUrl%>"><img style="vertical-align:baseline;" src="/people/servizi/praticheOnLine/visura/myPage/img/resultset_lastG.jpg" title="Pagina successiva" alt="Pagina successiva" /></a>
                            </pg:last>
                    </P>
                </pg:index>
            </td></tr>
        </pg:pager> 
    <tr  id="footer">
        <td>
            <input type="submit" name="navigation.button.previous" value="&lt;&lt; Indietro" class="btn" /> 

        </td></tr>
</table>
<!--  h5 align="right"><input type="submit" name="navigation.button.previous" value="&lt;&lt; Indietro" class="btn" /></h5  -->
<br /><br /><html><body></body></html>
