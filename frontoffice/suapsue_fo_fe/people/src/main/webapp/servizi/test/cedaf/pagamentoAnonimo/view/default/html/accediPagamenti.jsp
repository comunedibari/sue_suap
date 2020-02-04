<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>

<html:xhtml/>
<div class="title1">
	<h1><bean:message key ="label.accediPagamenti.titolo" /></h1>
</div>    
<br />
<div class="text_block">
	<bean:message key="label.accediPagamenti.info" />     
    <ppl:errors />
    <table>
	    <tr>
	        <td><ppl:fieldLabel key="label.accediPagamenti.email" fieldName="data.email" /></td>
	        <td><bean:write name="pplProcess" property="data.email" /></td>
	    </tr>
	    <tr>
	        <td><ppl:fieldLabel key="label.accediPagamenti.importo" fieldName="data.importo" /></td>
	        <td><bean:write name="pplProcess" property="data.importoString" /></td>
	    </tr>
    </table>
</div>