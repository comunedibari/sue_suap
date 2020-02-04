<%@ page language="java" %>
<%@ page import="it.people.layout.*, it.people.fsl.servizi.test.cedaf.demo.steps.LoopbackStep"%>
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