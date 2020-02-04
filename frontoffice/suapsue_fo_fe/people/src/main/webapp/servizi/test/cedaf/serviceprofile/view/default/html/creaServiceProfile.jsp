<%@ page language="java" %>
<%@ page import="it.people.layout.*"%>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:xhtml/>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<h1> <bean:message key= "creaServiceProfile.titolo" /></h1>
<br />
<br />
<ppl:errors/>

<table>
    <tr>
        <td><ppl:fieldLabel key="creaServiceProfile.label.serviceCategory" fieldName="data.serviceCategory" /></td>
        <td><ppl:textField property="data.serviceCategory" size="60" maxlength="80" /> </td>
    </tr>
    <tr>
        <td><ppl:fieldLabel key="creaServiceProfile.label.serviceSubcategory" fieldName="data.serviceSubcategory" /></td>
        <td><ppl:textField property="data.serviceSubcategory" size="60" maxlength="80" /> </td>
    </tr>
    <tr>
        <td><ppl:fieldLabel key="creaServiceProfile.label.serviceName" fieldName="data.serviceName" /></td>
        <td><ppl:textField property="data.serviceName" size="60" maxlength="80" /> </td>
    </tr>
    <tr>
        <td><ppl:fieldLabel key="creaServiceProfile.label.bookmarkId" fieldName="data.bookmarkId" /></td>
        <td><ppl:textField property="data.bookmarkId" size="60" maxlength="40" /> </td>
    </tr>
    <tr>
        <td><bean:message key="creaServiceProfile.label.strongAuthentication" /></td>
		<td>
	        <html:radio property="data.strongAuthentication" value="true" >
	            <ppl:label key="label.Yes"/>
	        </html:radio>
	        <html:radio property="data.strongAuthentication" value="false" >
	            <ppl:label key="label.No"/>
	        </html:radio>
		</td>    
    </tr>
    <tr>
        <td><bean:message key="creaServiceProfile.label.weakAuthentication" /></td>
		<td>
	        <html:radio property="data.weakAuthentication" value="true" >
	            <ppl:label key="label.Yes"/>
	        </html:radio>
	        <html:radio property="data.weakAuthentication" value="false" >
	            <ppl:label key="label.No"/>
	        </html:radio>
		</td>    
    </tr>
    <tr>
        <td><bean:message key="creaServiceProfile.label.abilitaIntermediari" /></td>
		<td>
	        <html:radio property="data.abilitaIntermediari" value="true" >
	            <ppl:label key="label.Yes"/>
	        </html:radio>
	        <html:radio property="data.abilitaIntermediari" value="false" >
	            <ppl:label key="label.No"/>
	        </html:radio>
		</td>    
    </tr>
    <tr>
        <td><bean:message key="creaServiceProfile.label.abilitaUtenteRegistrato" /></td>
		<td>
	        <html:radio property="data.abilitaUtenteRegistrato" value="true" >
	            <ppl:label key="label.Yes"/>
	        </html:radio>
	        <html:radio property="data.abilitaUtenteRegistrato" value="false" >
	            <ppl:label key="label.No"/>
	        </html:radio>
		</td>    
    </tr>
</table>

<ppl:commandLoopback styleClass="btn" validate="true">
	<bean:message key="creaServiceProfile.button.loopBack"/>
</ppl:commandLoopback>

