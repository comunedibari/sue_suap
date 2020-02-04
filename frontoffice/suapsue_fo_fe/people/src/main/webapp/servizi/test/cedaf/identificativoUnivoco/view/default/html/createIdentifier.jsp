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