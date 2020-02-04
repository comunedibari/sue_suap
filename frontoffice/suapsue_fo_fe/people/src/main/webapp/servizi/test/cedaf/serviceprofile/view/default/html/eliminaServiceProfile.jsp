<%@ page language="java" %>
<%@ page import="it.people.layout.*"%>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:xhtml/>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<h1> <bean:message key= "eliminaServiceProfile.titolo" /></h1>
<br />
<br />
<ppl:errors/>

<table>
    <tr>
        <td><ppl:fieldLabel key="eliminaServiceProfile.label.serviceName" fieldName="data.serviceName" /></td>
        <td><ppl:textField property="data.serviceName" size="60" maxlength="80" /> </td>
    </tr>
    <tr>
        <td><ppl:fieldLabel key="eliminaServiceProfile.label.bookmarkId" fieldName="data.bookmarkId" /></td>
        <td><ppl:textField property="data.bookmarkId" size="60" maxlength="40" /> </td>
    </tr>
</table>

<ppl:commandLoopback styleClass="btn" validate="true">
	<bean:message key="eliminaServiceProfile.button.loopBack"/>
</ppl:commandLoopback>

