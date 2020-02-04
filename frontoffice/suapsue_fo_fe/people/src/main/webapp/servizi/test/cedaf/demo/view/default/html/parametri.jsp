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
	<p><bean:message key= "label.parametriTest.info" /></p>
</div>    

<p><strong>parametro1: </strong> <%= spar.get("parametro1")%></p>
<p><strong>parametro2: </strong> <%= spar.get("parametro2")%></p>
<ppl:fieldLabel key="label.validazione.valore1" fieldName="data.valore1" />
<ppl:textField name="pplProcess" property="data.valore1"/>
<ppl:fieldWrite name="pplProcess"  property="data.valore1"/>