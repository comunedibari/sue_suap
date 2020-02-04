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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglib.jsp"%>
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@page import="java.util.Iterator"%>
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean"%>
<%@page import="java.util.*"%>
<%@page import="it.people.util.NavigatorHelper"%>
<%
ArrayList bottoniNascosti = (ArrayList)request.getAttribute("bottoniNascosti");
if (bottoniNascosti!=null){
	bottoniNascosti.add(NavigatorHelper.BOTTONE_SALVA);
	bottoniNascosti.add(NavigatorHelper.BOTTONE_AVANTI);
	bottoniNascosti.add(NavigatorHelper.BOTTONE_INDIETRO);
}
String base = request.getContextPath();
%>

<jsp:include page="webclock.jsp" flush="true" />

<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;">
	<tr>
		<td>
			<b>Per aprire il file cliccare sul nome dell'allegato:</b>
		</td>
	</tr>
	<tr>
		<td align="center">
			<logic:iterate name="listaDocumenti" id="rows">
			<ul>
				<li>
				<!--  
				<a href="javascript: void 0" onclick="window.open('<%=base+htmlPath%>/openDoc.jsp?cod_doc=<bean:write name="rows" filter="yes" property="codice"/>&amp;language=it&amp;nome_file=<bean:write name="rows" filter="false" property="nomeFile"/>',1,'toolbar=no,scrollbars=no,resizable=yes,menubar=no,width=500,height=600')">
					<bean:write name="rows" filter="false" property="nomeFile"/>
				</a>
				-->
				<a href='<%=base+htmlPath+"/openDoc.jsp?cod_doc="%><bean:write name="rows" filter="yes" property="codice"/>&amp;language=it&amp;nome_file=<bean:write name="rows" filter="false" property="nomeFile"/>' target="_blank" title="<bean:write name="rows" filter="false" property="nomeFile"/>" ><bean:write name="rows" filter="false" property="nomeFile"/></a>
				
				</li>
			</ul>	
			</logic:iterate>
		</td>
	</tr>
</table><br/>
<table align="center">
	<tr>
		<td>
			<!--<input type="button" class="btn" onclick="javascript:executeSubmit('loopBack.do?propertyName=modelloUnico.jsp');" value="TORNA AL MODELLO UNICO" />-->
			<ppl:linkLoopback accesskey="B" property="modelloUnico.jsp" styleClass="btn">TORNA AL MODELLO UNICO</ppl:linkLoopback>
		</td>
	</tr>
</table>
<br/>
