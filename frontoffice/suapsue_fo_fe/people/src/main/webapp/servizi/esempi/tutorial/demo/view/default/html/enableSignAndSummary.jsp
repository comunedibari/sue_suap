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
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<html:xhtml/>
<div class="title1">
    <h1><bean:message key="label.enableSignAndSummary.titolo" /></h1>
</div>
<ppl:errors/>
<div class="text_block">
    <bean:message key="label.enableSignAndSummary.descrizione" /><br />
    <ppl:errors />
	<br />
	<table>
		<tr>
			<td><bean:message key="label.enableSignAndSummary.abilitaFirma"/></td>
			<td>
				<html:radio property="data.abilitaFirma" value="true" >
            		<ppl:label key="label.Yes"/>
        		</html:radio>
			</td>
			<td>
		        <html:radio property="data.abilitaFirma" value="false" >
		            <ppl:label key="label.No"/>
		        </html:radio>
			</td>
		</tr>
		<tr>
			<td><bean:message key="label.enableSignAndSummary.abilitaRiepilogo"/></td>
			<td>
				<html:radio property="data.abilitaRiepilogo" value="always" >
            		<ppl:label key="label.enableSignAndSummary.abilitaRiepilogo.always"/>
        		</html:radio>
			</td>
			<td>
		        <html:radio property="data.abilitaRiepilogo" value="finally" >
		            <ppl:label key="label.enableSignAndSummary.abilitaRiepilogo.finally"/>
		        </html:radio>
			</td>
			<td>
				<html:radio property="data.abilitaRiepilogo" value="none" >
            		<ppl:label key="label.enableSignAndSummary.abilitaRiepilogo.none"/>
        		</html:radio>
			</td>
		</tr>
	</table>
</div>
