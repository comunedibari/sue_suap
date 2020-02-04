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
<%@page import="java.util.*"%>
<%@page import="it.people.util.NavigatorHelper"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<script type="text/javascript">

function confirmBeforeExecuteSubmit(newAction) {

    if (confirm("Si conferma la cancellazione dell'evento della vita e dei servizi associati?\n")){ 
    	executeSubmit(newAction);
    }     
}

</script>
<%
ArrayList bottoniNascosti = (ArrayList)request.getAttribute("bottoniNascosti");
if (bottoniNascosti!=null){
	bottoniNascosti.add(NavigatorHelper.BOTTONE_SALVA);
}
%>
<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;">
	<tr>
		<td align="left" colspan="2">
			<b>Eventi della vita:</b>
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td valign="top" align="left" width="50%">
			<html:select style="width: 350px;" property="data.datiTemporanei.eventoVitaSel">
				<logic:notEmpty name="pplProcess" property="data.datiTemporanei.eventiVita">
					<html:optionsCollection name="pplProcess" property="data.datiTemporanei.eventiVita" label="descrizione" value="codiceEventoVita"/> 
				</logic:notEmpty> 
			</html:select> &nbsp;
		</td>
		<td valign="top" align="left" width="50%">
			<input type="button" class="btn"
			onclick="javascript:confirmBeforeExecuteSubmit('loopBack.do?propertyName=bookmark&amp;action=eventiVita.jsp&amp;operazione=delete');"
			value="ELIMINA" /><br/><br/>
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td valign="top" align="left" width="50%">
			<input type="text" name="eventoVita" style="width: 400px;"/>
		</td>
		<td valign="top" align="left" width="50%">	
			<input type="button" class="btn"
			onclick="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=eventiVita.jsp&amp;operazione=insert');"
			 value="AGGIUNGI" /><br/>
		</td>
	</tr>
</table>
<br/>
<table align="center">
<tr>		
  <td><input type="button" class="btn"
			onclick="javascript:executeSubmit('loopBack.do?propertyName=bookmark&amp;action=servizi.jsp');"
			value="TORNA ALLA GENERAZIONE SERVIZI" />
		</td>
	</tr>
</table>
<br/>	
