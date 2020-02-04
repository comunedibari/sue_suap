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
<%@ page import="it.people.fsl.servizi.esempi.tutorial.demo.model.*"%>
<html:xhtml/>
<div class="title1">
    <h1><bean:message key="label.TestWS.titolo" /></h1>
</div>
<ppl:errors/>
        	<%
        	ProcessData data = (ProcessData)pplProcess.getData();
			String rispostaWS = data.getRispostaWebService();
			if (rispostaWS != null && rispostaWS.length() > 50) {
			%>
			<div class="text_block">
			    <p>
			        <bean:message key="label.TestWS.risultatoChiamata" /><br />
			        <pre>
			<%
				String buffer = "";
				int position = 0;
				for(int index = 0; index < rispostaWS.length(); index++) {
					if (position > 50) {
						buffer += rispostaWS.charAt(index);
						data.setTmpString(buffer);
						%>
						<ppl:fieldWrite name="pplProcess" property='<%="data.tmpString" %>'/><br/>
						<%
						position = 0;
						buffer = "";
					} else {
						buffer += rispostaWS.charAt(index);
						position++;
					}
				}
        	%>
			        </pre>
			    </p>
			</div>
	        <%
	        }
			 %>



