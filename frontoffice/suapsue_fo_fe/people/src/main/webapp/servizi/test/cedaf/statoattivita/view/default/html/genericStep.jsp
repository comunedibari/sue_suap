<%@ page language="java" %>
<%@ page import="it.people.layout.*, it.people.Activity, it.people.IStep"%>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:xhtml/>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<h1><bean:message key= "genericStep.titolo" /></h1>
<br />
<br />
<ppl:errors/>

<%
Activity activity = pplProcess.getProcessWorkflow().getProcessView().getCurrentActivity();
IStep step = activity.getCurrentStep();
%>
<table>
    <tr>
        <td colspan="2"><strong><bean:message key= "genericStep.currentActivity" /></strong></td>
	</tr>
    <tr>
        <td><bean:message key= "genericStep.currentActivity.id" /></td>
        <td><%=activity.getId()%></td>
    </tr>
    <tr>
        <td><bean:message key= "genericStep.currentActivity.state" /></td>
        <td><%=activity.getState().getLabel()%></td>
    </tr>
    <tr>
        <td colspan="2"><strong><bean:message key= "genericStep.currentStep" /></strong></td>
	</tr>
    <tr>
        <td><bean:message key= "genericStep.currentStep.id" /></td>
        <td><%=step.getId()%></td>
    </tr>
    <tr>
        <td><bean:message key= "genericStep.currentStep.state" /></td>
        <td><%=step.getState().getLabel()%></td>
    </tr>
</table>