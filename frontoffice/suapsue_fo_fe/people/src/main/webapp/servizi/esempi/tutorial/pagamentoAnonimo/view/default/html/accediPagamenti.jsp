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
