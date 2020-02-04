<%@ page language="java" %>
<%@ page import="it.people.layout.*"%>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:xhtml/>
<div class="title1">
	<h1> <bean:message key= "label.intro.titolo" /></h1>
</div>
	
<div class="text_block">
	<bean:message key="label.intro.info" />
	<ppl:errors/>
	<table>
		    <tr>
		        <td><ppl:fieldLabel key="label.intro.nome" fieldName="data.nome" /></td>
		        <td><ppl:textField property="data.nome" size="40" maxlength="80" /> </td>
		    </tr>
		    <tr>
		        <td><ppl:fieldLabel key="label.intro.cognome" fieldName="data.cognome" /></td>
		        <td><ppl:textField property="data.cognome" size="40" maxlength="80" /></td>
		    </tr>
	</table>
</div>