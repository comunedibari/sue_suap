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

http://joinup.ec.europa.eu/software/page/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.

This product includes software developed by Yale University

See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<%@ page language="java" %>
<%@ page import="it.people.layout.*"%>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:xhtml/>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<h1> <bean:message key= "caricaServiceProfile.titolo" /></h1>
<br />
<br />
<ppl:errors/>

<table>
    <tr>
        <td><ppl:fieldLabel key="caricaServiceProfile.label.serviceCategory" fieldName="data.serviceCategory" /></td>
        <td><ppl:textField property="data.serviceCategory" size="60" maxlength="80" /> </td>
    </tr>
    <tr>
        <td><ppl:fieldLabel key="caricaServiceProfile.label.serviceSubcategory" fieldName="data.serviceSubcategory" /></td>
        <td><ppl:textField property="data.serviceSubcategory" size="60" maxlength="80" /> </td>
    </tr>
    <tr>
        <td><ppl:fieldLabel key="caricaServiceProfile.label.serviceName" fieldName="data.serviceName" /></td>
        <td><ppl:textField property="data.serviceName" size="60" maxlength="80" /> </td>
    </tr>
    <tr>
        <td><ppl:fieldLabel key="caricaServiceProfile.label.bookmarkId" fieldName="data.bookmarkId" /></td>
        <td><ppl:textField property="data.bookmarkId" size="60" maxlength="40" /> </td>
    </tr>
    <tr>
        <td><bean:message key="caricaServiceProfile.label.strongAuthentication" /></td>
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
        <td><bean:message key="caricaServiceProfile.label.weakAuthentication" /></td>
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
        <td><bean:message key="caricaServiceProfile.label.abilitaIntermediari" /></td>
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
        <td><bean:message key="caricaServiceProfile.label.abilitaUtenteRegistrato" /></td>
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
	<bean:message key="caricaServiceProfile.button.loopBack"/>
</ppl:commandLoopback>

