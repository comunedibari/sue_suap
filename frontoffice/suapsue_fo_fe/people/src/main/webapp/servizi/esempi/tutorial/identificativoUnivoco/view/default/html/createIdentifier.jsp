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
<%@ page import="it.people.layout.*, it.people.Activity, it.people.IStep"%>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:xhtml/>
<h1><bean:message key= "createIdentifier.titolo" /></h1>
<br />
<br />
<ppl:errors/>

<table>
    <tr>
        <td><bean:message key= "createIdentifier.identificativoGenerale" /></td>
        <td><bean:write name="pplProcess" property="data.identificatoreUnivoco.codiceSistema.codiceIdentificativoOperazione"/>
    </tr>
    <tr>
        <td><bean:message key= "createIdentifier.identificativo" /></td>
        <td><bean:write name="pplProcess" property="data.identificativoOperazioneTemporaneo"/>
    </tr>
    <tr>
        <td><bean:message key= "createIdentifier.identificativoOldStyle" /></td>
        <td><bean:write name="pplProcess" property="data.identificativoOperazioneTemporaneoOldStyle"/>
    </tr>
    <tr>
	    <td>
			<ppl:commandLoopback styleClass="btn">
				<bean:message key="createIdentifier.pulsante.generaId"/>
			</ppl:commandLoopback>
    	</td>
    </tr>	
</table>