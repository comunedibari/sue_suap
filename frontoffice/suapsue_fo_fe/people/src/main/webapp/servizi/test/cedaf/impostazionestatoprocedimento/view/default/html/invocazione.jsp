<%@ page language="java" %>
<%@ page import="it.people.layout.*"%>
<%@ page import="it.people.util.NavigatorHelper" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
	// Naconde il pulsante avanti/invia/firma
	ArrayList bottoniNascosti = (ArrayList)request.getAttribute("bottoniNascosti");
	bottoniNascosti.add(NavigatorHelper.BOTTONE_SALVA);	
%>
<html:xhtml/>
<div class="title1">
	<h1> <bean:message key= "label.invocazione.titolo" /></h1>
</div>
	
<div class="text_block">
	<bean:message key="label.invocazione.info" />
	<ppl:errors/>
	<table>
		    <tr>
				<td><bean:message key="label.invocazione.nome" /><br /></td>
				<td><bean:write name="pplProcess" property="data.nome" /></td>
			</tr>
			<tr>
				<td><bean:message key="label.invocazione.cognome" /><br /></td>
				<td><bean:write name="pplProcess" property="data.cognome" /></td>
		    </tr>
	</table>
</div>