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
<%@ page import="it.people.layout.*, it.people.fsl.servizi.esempi.tutorial.demo.steps.LoopbackStep"%>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:xhtml/>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<h1>Pagina per effettuare il test del loopback</h1>
<br />
<br />
<ppl:errors/>

<ppl:fieldLabel key="label.loopback.prova" fieldName="data.prova" />
<ppl:textField name="pplProcess" property="data.prova"/>
<ppl:textField name="pplProcess" property="data.prova" disabled="true"/>

<ppl:commandLoopback styleClass="btn">
	<bean:message key="loopback.loopbackSenzaValidazione"/>
</ppl:commandLoopback>

<ppl:commandLoopback styleClass="btn" validate="true">
	<bean:message key="loopback.loopbackValidante"/>
</ppl:commandLoopback>

<p>
ultimo valore inserito:
<ppl:fieldWrite name="pplProcess" property="data.prova" default="..."  />
</p>

<ppl:commandLoopback styleClass="btn" commandProperty="<%=LoopbackStep.PULSANTE_A%>">
	<bean:message key="loopback.loopbackA"/>
</ppl:commandLoopback>
<ppl:commandLoopback styleClass="btn" validate="true" commandProperty="<%=LoopbackStep.PULSANTE_B%>">
	<bean:message key="loopback.loopbackB"/>
</ppl:commandLoopback>
<ppl:commandLoopback styleClass="btn" commandProperty="<%=LoopbackStep.PULSANTE_C%>" commandIndex="1">
	<bean:message key="loopback.loopbackC"/>
</ppl:commandLoopback>
<ppl:commandLoopback styleClass="btn" commandProperty="<%=LoopbackStep.PULSANTE_C%>" commandIndex="2">
	<bean:message key="loopback.loopbackC"/>
</ppl:commandLoopback>

<p/><bean:message key="loopback.pulsantePremuto"/>
<bean:write name="pplProcess" property="data.pulsantePremuto" />
<p/><bean:message key="loopback.pulsantePremutoIndice"/>
<bean:write name="pplProcess" property="data.pulsantePremutoIndice" />
