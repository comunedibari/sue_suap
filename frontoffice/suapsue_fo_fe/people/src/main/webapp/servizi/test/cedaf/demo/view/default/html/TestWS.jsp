<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<%@ page import="it.people.fsl.servizi.test.cedaf.demo.model.*"%>
<html:xhtml/>
<div class="title1">
    <h1><bean:message key="label.TestWS.titolo" /></h1>
</div>
<ppl:errors/>
<div class="text_block">
    <p>
        <bean:message key="label.TestWS.risultatoChiamata" /><br />
        <ppl:errors />
        <pre>
        	<%=((ProcessData) pplProcess.getData()).getRispostaWebService()%>
        </pre>
    </p>
</div>



