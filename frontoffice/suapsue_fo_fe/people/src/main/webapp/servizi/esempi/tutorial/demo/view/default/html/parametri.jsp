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

<%@ page import="it.people.util.*" %>

<%
    ServiceParameters spar = (ServiceParameters) session.getAttribute("serviceParameters");
%>

<html:xhtml/>
<div class="title1"> <h1> <bean:message key= "label.parametri.titolo"  /> </h1> </div>
               
<div class="text_block">
	<p></p>
</div>    

<p><strong>parametro1: </strong> <%= spar.get("parametro1")%></p>
<p><strong>parametro2: </strong> <%= spar.get("parametro2")%></p>
<ppl:fieldLabel key="label.validazione.valore1" fieldName="data.valore1" />
<ppl:textField name="pplProcess" property="data.valore1"/>
<ppl:fieldWrite name="pplProcess"  property="data.valore1"/>
