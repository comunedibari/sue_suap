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
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<link rel="stylesheet" type="text/css" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/style.css" />

<html:xhtml/>
<logic:equal value="true" name="pplProcess" property="data.internalError">
    <jsp:include page="defaultError.jsp" flush="true" />
</logic:equal>
<logic:notEqual value="true" name="pplProcess" property="data.internalError">
    <jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

    <%@page import="java.util.Set"%>
    <%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
    <%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.*"%>
    <%@ page import="it.people.core.*"%>
    <%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.*"%>

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

    <%
      // PC - Stampa bozza inizio    
      // String basebb = request.getContextPath() + "/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/moduloDinamico.jsp?white=TRUE&cod=";
        String basebb = request.getContextPath() + "/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/moduloDinamico.jsp?bozza=TRUE&cod=";
      // PC - Stampa bozza fine 
        String urlModComp = request.getContextPath() + "/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/moduloCompilabile.jsp";
        // "/loopBack.do?propertyName=moduloInBianco.jsp";
        ProcessData data = (ProcessData) pplProcess.getData();
    %>

    <table style="border:1px solid #EAEAEA; padding: 5px; width:96%;" summary="Modello di Domanda" >
        <tr>
            <td colspan="2" align="center"><b><bean:message key="mu.note.compila.modifica.titolo" /></b><br/>
            </td>
        </tr>
        <tr>
            <td colspan="2" >
                <bean:message key="mu.note.compila.modifica" /><br/><br/>
            </td>
        </tr>
        <tr>
            <td width="100%" align="right" colspan="2">
                <i><bean:message key="mu.label.procedimentoplurimo" /></i><br/><br/>
            </td>
        </tr>
        <tr>
            <td width="100%" colspan="2">
                <b><bean:message key="mu.label.sportelli" /></b><br/>
            </td>
        </tr>
        <logic:iterate id="rowsId" name="pplProcess" property="data.listaSportelli" >
            <tr>
                <td width="30%">
                    &nbsp;&nbsp;&nbsp;<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/bullet.png" alt="bullet sportello" />&nbsp;&nbsp;<bean:write name="rowsId" filter="false" property="value.descrizioneSportello"/><br/><br/>
                </td>
                <td width="70%">
                    <a class="btn" href="<%=basebb%><bean:write name="rowsId" filter="false" property="value.codiceSportello"/>" target="_blank" title="<bean:message key="mu.label.stampamoduloinbianco" />" ><bean:message key="mu.label.stampamoduloinbianco" /></a>
                    <span>
                        &nbsp;&nbsp;<!--<a class="btn" href="<%=urlModComp%>?scarica=scarica" target="_blank" title="<bean:message key="mu.label.stampamodulocompilabile" />" ><bean:message key="mu.label.stampamodulocompilabile" /></a>-->
                    </span>
                   <span>
                        &nbsp;&nbsp;<!--<a class="btn" href="/people/loopBack.do?propertyName=moduloCompilabile.jsp&amp;ricarica=ricarica" target="_self" title="<bean:message key="mu.label.ricaricamodulocompilabile" />" ><bean:message key="mu.label.ricaricamodulocompilabile" /></a>-->
                    </span>
                    <br/><br/>
                </td>
            </tr>
        </logic:iterate>
        <tr>
            <td width="100%" colspan="2">
                <br/><b><bean:message key="mu.label.interventiSelezionati" /></b><br/>
            </td>
        </tr>
        <logic:iterate id="rowsId" name="pplProcess" property="data.interventi" >
            <tr>
                <td width="100%" colspan="2">
                    &nbsp;&nbsp;&nbsp;<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/bullet.png" alt="bullet procedimento" />&nbsp;&nbsp;<bean:write name="rowsId" filter="false" property="descrizione"/>

                    <%
                        PplUser peopleUserAdmin = PeopleContext.create(request).getUser();
                        if ( peopleUserAdmin.getExtendedAttribute(Costant.PplUser.AMMINISTRATORE_BOOKMARK) != null ) {
                            if ((Boolean) peopleUserAdmin.getExtendedAttribute(Costant.PplUser.AMMINISTRATORE_BOOKMARK)){
                    %>
                    <i>&nbsp;(codice intervento <bean:write name="rowsId" filter="false" property="codice"/>)</i>
                    <% }
                     }%>


                    <br/><br/>
                </td>
            </tr>
        </logic:iterate>
        <logic:iterate id="rowsId" name="pplProcess" property="data.interventiFacoltativi" >
            <tr>
                <td width="100%" colspan="2">
                    &nbsp;&nbsp;&nbsp;<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/bullet.png" alt="bullet procedimento" />&nbsp;&nbsp;<bean:write name="rowsId" filter="false" property="descrizione"/>
                    <br/><br/>
                </td>
            </tr>
        </logic:iterate>

        <tr>
            <td colspan="2">
                <table border="1" width="100%" cellspacing="0" summary="Codice identificativo People">
                    <tr>
                        <td><bean:message key="mu.label.codice.domanda" /></td>
                        <td>
                            <b><logic:empty name="pplProcess" property="data.identificatorePeople.identificatoreProcedimento">
                                    <!--bean:message key="MU.nocodice" /-->&nbsp;
                                </logic:empty>
                                <logic:notEmpty name="pplProcess" property="data.identificatorePeople.identificatoreProcedimento">
                                    <bean:write name="pplProcess" filter="false" property="data.identificatorePeople.identificatoreProcedimento" />
                                </logic:notEmpty> </b></td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <!-- INFORMAZIONI RICHIEDENTE -->
                <jsp:include page="modelloUnico_richiedente.jsp" flush="true" />
                <!-- FINE INFORMAZIONI RICHIEDENTE -->
            </td>
        </tr>



        <tr>
            <td colspan="2">
                <!-- INFORMAZIONI RICHIEDENTE -->
                <jsp:include page="modelloUnico_altriRichiedente.jsp" flush="true" />
                <!-- FINE INFORMAZIONI RICHIEDENTE -->
            </td>
        </tr>

        <logic:notEmpty name="pplProcess" property="data.oggettoIstanza">
            <logic:notEmpty name="pplProcess" property="data.oggettoIstanza.campi">
                <tr>
                    <td colspan="2">
                        <br />
                        <A NAME='<bean:write name="pplProcess" filter="false" property="data.oggettoIstanza.href" />'></A>
                        <bean:write name="pplProcess" property="data.oggettoIstanza.html" filter="yes"/>
                    </td>
                </tr>
            </logic:notEmpty>
        </logic:notEmpty>



        <tr>
            <td colspan="2" align="center">
                <b><bean:message key="mu.label.comunica" /></b>
            </td>
        </tr>

        <tr>
            <td colspan="2"><br/><bean:message key="mu.label.avvio.procedimento" /><br/></td>
        </tr>

        <tr>
            <td colspan="2">
                <table border="1" width="100%" cellspacing="0" summary=".">
                    <tr>
                        <td align="center" width="23%"><br/><b><bean:message key="mu.label.procedimenti.int.proc" /></b><br/><br/></td>
                        <td align="center" width="23%"><br/><b><bean:message key="mu.label.procedimenti.int.intervento" /></b><br/><br/></td>
                        <td align="center" width="10%"><br/><b><bean:message key="mu.label.procedimenti.int.ente" /></b><br/><br/></td>
                        <td align="center" width="44%"><br/><b><bean:message key="mu.label.procedimenti.int.norme" /></b><br/><br/></td>
                    </tr>


                    <% java.util.ArrayList lista = (java.util.ArrayList) request.getAttribute("listaProcedimenti");
                        for (java.util.Iterator iterator = lista.iterator(); iterator.hasNext();) {
                            it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ModelloUnicoBean mub = (it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ModelloUnicoBean) iterator.next();
                    %>
                    <tr>
                        <td><%=mub.getDescrizione()%></td>
                        <td><ul>
                                <%for (java.util.Iterator iterator2 = mub.getListaIntervento().iterator(); iterator2.hasNext();) {
                                        java.lang.String interv = (java.lang.String) iterator2.next();

                                %>
                                <li><%=interv%></li>
                                <%}%>
                            </ul>
                        </td>
                        <td><%=mub.getEnte()%></td>
                        <td>
                            <ul>
                                <%
                                    int i = 0;
                                    for (java.util.Iterator iterator3 = mub.getListaTmp().iterator(); iterator3.hasNext();) {
                                        i++;
                                        it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.NormativaBean normativa = (it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.NormativaBean) iterator3.next();
                                %>
                                <li><b><%=normativa.getNomeRiferimento()%></b>&nbsp;<%=normativa.getTitoloRiferimento()%>&nbsp;
                                    <%if (normativa.getNomeFile() != null && !normativa.getNomeFile().equalsIgnoreCase("")) {%>
                                    <ppl:linkLoopback  property="normativa.jsp" propertyIndex="<%=normativa.getCodRif()%>" ><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/document.gif" width="18" height="18" align="middle" alt="link a documento" /></ppl:linkLoopback>
                                    <%}%>
                                </li>
                                <%}
                                %>
                            </ul>
                            <% if (i == 0) {%>&nbsp;<%}%>
                        </td>
                    </tr>
                    <%}%>





                </table>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <table width="100%" cellpadding="2" cellspacing="0" summary="." border="0">
                    <tr>
                        <td width="100%"><b><bean:message key="mu.label.dichiarazioni.int" /></b></td>
                    </tr>
                    <tr>
                        <td align="center"><b><bean:message key="mu.label.dichiara" /></b></td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <%-- PC - ordina allegati
                                <logic:iterate id="rowsIdHref" name="pplProcess" property="data.listaHref" >
                                        <A NAME='<bean:write name="rowsIdHref" property="value.href" />'></A>
                                        <bean:write name="rowsIdHref" property="value.html" filter="yes"/>
                                </logic:iterate>
                --%>
                <%
                    java.util.ArrayList ar = data.getListaHrefOrdered();
                    java.util.Map mp = data.getListaHref();
                    for (java.util.Iterator it = ar.iterator(); it.hasNext();) {
                        String key = (String) it.next();
                %>
                <A NAME="<%=key%>"></A>

                <%
                    PplUser peopleUserAdmin = PeopleContext.create(request).getUser();
                    if ( peopleUserAdmin.getExtendedAttribute(Costant.PplUser.AMMINISTRATORE_BOOKMARK) != null ) {
                    if ((Boolean) peopleUserAdmin.getExtendedAttribute(Costant.PplUser.AMMINISTRATORE_BOOKMARK)){
                %>
                (<%=key%>)
                <% }
                    }%>

                <%=((SezioneCompilabileBean) mp.get(key)).getHtml()%>
                <%
                    }
                %>

            </td>
        </tr>

        <logic:iterate id="rowsIdDicStat" name="pplProcess" property="data.listaDichiarazioniStatiche" >
            <tr>
                <td colspan="2">
                    <table border="1" width="100%" cellspacing="0" summary=".">

                        <tr>
                            <td>
                                <bean:write name="rowsIdDicStat" filter="false" property="value.descrizione"/>
                            </td>
                        </tr>

                    </table>
                </td>
            </tr>
        </logic:iterate>

        <tr><td><br/><br/></td></tr>

        <tr><td align="center" colspan="2"><b><bean:message key="mu.label.documenti.tit"/></b></td></tr>
        <tr><td><br/></td></tr>
        <tr>
            <td colspan="2">
                <table border="1" width="100%" cellpadding="2" cellspacing="0" summary=".">
                    <tr>
                        <th width="10%"><bean:message key="mu.label.documenti.ente"/></th>
                        <th width="45%"><bean:message key="mu.label.allegati.istcom"/></th>
                        <th width="45%"><bean:message key="mu.label.allegati.oggetto"/></th>
                    </tr>
                    <logic:iterate id="rowsIdDoc" name="listaProcedimenti" scope="request">
                        <tr>
                            <td>
                                <bean:write name="rowsIdDoc" filter="false" property="ente"/>
                            </td>
                            <td>
                                <bean:write name="rowsIdDoc" filter="false" property="descrizione"/>
                            </td>
                            <td>

                                <logic:iterate id="rowsIdDoc2" name="rowsIdDoc" property="listaIntervento">
                                    -&nbsp;<bean:write name="rowsIdDoc2" filter="false"/><br/>
                                </logic:iterate>

                            </td>
                        </tr>
                        <tr>
                            <td><b><bean:message key="mu.label.allegati.copie"/></b></td>
                            <td colspan="2"><b><bean:message key="mu.label.allegati.documento"/></b></td>
                        </tr>
                        <logic:iterate id="rowsIdAll2" name="rowsIdDoc" property="listaDocumenti" >
                            <tr>
                                <td>
                                    <bean:write name="rowsIdAll2" filter="false" property="copie"/>
                                </td>
                                <td colspan="2">
                                    <bean:write name="rowsIdAll2" filter="false" property="titolo"/>
                                </td>
                            </tr>
                        </logic:iterate>
                        <tr>
                            <td colspan="3">
                                &nbsp;
                            </td>
                        </tr>
                    </logic:iterate>
                </table>
            </td>
        </tr>

        <tr><td><br/><br/><br/></td></tr>

        <tr>
            <td colspan="2">
                <table border="1" width="100%" cellpadding="2" cellspacing="0" summary=".">
                    <tr>
                        <th colspan="2"><bean:message key="mu.label.allegati.g" /></th>
                    </tr>
                    <tr>
                        <th><bean:message key="mu.label.allegati.int.documento" /></th>
                        <th><bean:message key="mu.label.allegati.nomefile" /></th>
                    </tr>
                    <%-- Qui vengono mostrati i soli allegati che è possibile scaricare --%>

                    <%
                        Set s = data.getListaModulistica().keySet();
                        for (java.util.Iterator iterator = s.iterator(); iterator.hasNext();) {
                            String codMod = (String) iterator.next();
                            ModulisticaBean modulo = (ModulisticaBean) data.getListaModulistica().get(codMod);
                            if (modulo.getNomeFile() != null && (!modulo.getNomeFile().equalsIgnoreCase("")) && !modulo.getCodiceDoc().startsWith("PROC_SPEC_")) {
                                String nomeF = modulo.getNomeFile();
                                String titolo = modulo.getTitolo();
                                String codR = modulo.getCodiceDoc();
                    %>
                    <tr>
                        <td valign="top"><%=titolo%></td>
                        <td align="center" valign="top">
                            <ppl:linkLoopback property="documenti.jsp" propertyIndex="<%=codR%>"><%=nomeF%></ppl:linkLoopback>
                            </td>
                        </tr>
                    <%
                            }
                        }

                    %>





                </table>

            </td>
        </tr>

        <tr><td><br/><br/></td></tr>
    </table>
    <logic:notEmpty name="lastHref" scope="request" >
        <input type="hidden" value="<bean:write name="lastHref" scope="request" filter="false"/>" name="lastHref"  id="lastHref"  />
    </logic:notEmpty>
    <script type="text/javascript">
        var elemNodo = document.getElementById('lastHref');
        if (elemNodo!=null) {
            self.location.hash=elemNodo.value;
        }
    </script>    
    <%@include file="bookmark.jsp"%>
</logic:notEqual>
