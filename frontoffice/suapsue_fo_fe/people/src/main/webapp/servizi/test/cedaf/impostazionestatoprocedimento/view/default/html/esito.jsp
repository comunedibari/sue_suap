<%@ page language="java" %>
<%@ page import="it.people.layout.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.people.util.NavigatorHelper" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
	// Naconde il pulsante avanti/invia/firma
	ArrayList bottoniNascosti = (ArrayList)request.getAttribute("bottoniNascosti");
	bottoniNascosti.add(NavigatorHelper.BOTTONE_INDIETRO);
	bottoniNascosti.add(NavigatorHelper.BOTTONE_SALVA);	
%>


<html:xhtml/>
<div class="title1">
	<h1> <bean:message key="label.esito.titolo" /></h1>
</div>
	
<div class="text_block">
	<bean:message key="label.esito.info" /><br />
	<ppl:errors/><br />
	<bean:message key="label.esito.risposta" /><br />
	<bean:write name="pplProcess" property="data.rispostaWebService" />
</div>