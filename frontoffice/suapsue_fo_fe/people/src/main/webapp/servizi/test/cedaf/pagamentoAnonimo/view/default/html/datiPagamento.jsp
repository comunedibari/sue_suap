<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>

<html:xhtml/>
<div class="title1">
	<h1><bean:message key ="label.datiPagamento.titolo" /></h1>
</div>    
<br />
<div class="text_block">
	<bean:message key="label.datiPagamento.info" />
	<br />
    <ppl:errors />
    <table>
	    <tr>
	        <td><ppl:fieldLabel key="label.datiPagamento.email" fieldName="data.email" /></td>
	        <td><ppl:textField property="data.email" size="40" maxlength="80" /> </td>
	    </tr>
	    <tr>
	        <td><ppl:fieldLabel key="label.datiPagamento.importo" fieldName="data.importoString" /></td>
	        <td><ppl:textField property="data.importoString" size="40" maxlength="80" /></td>
	    </tr>
    </table>
</div>