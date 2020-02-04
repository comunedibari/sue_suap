<%@ page language="java" %>
<%@ page import="it.people.layout.*, it.people.Activity, it.people.IStep"%>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:xhtml/>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<h1><bean:message key= "intro.titolo" /></h1>
<br />
<br />
<ppl:errors/>

<table>
    <tr>
        <td colspan="2"><bean:message key= "intro.descrizione" /></td>
	</tr>
</table>