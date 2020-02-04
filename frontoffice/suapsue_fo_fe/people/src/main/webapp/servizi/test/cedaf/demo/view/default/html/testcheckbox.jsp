<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>

<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<html:xhtml/>
<h1>Pagina per effettuare il test dei checkbox</h1>
<br />
<ppl:errors/>
<br/>
Verifica che i valori impostati nei check box siano correttamente
inseriti e successivamente valorizzati.

<table>
<%int i = 0;%>
<logic:iterate name="pplProcess" property="data.opsVec" id="rows">
	<tr>
		<td VALIGN="MIDDLE">
		<logic:equal name="rows" value="true">
			<input id="chk<%=i%>" 
					type="checkbox"
					name="data.opsVec"
					value="true"
					checked="true"
					>			
		</logic:equal>
		<logic:equal name="rows" value="false">
			<input id="chk<%=i %>" 
					type="checkbox"
					name="data.opsVec" 
					value="true">			
		</logic:equal>
		</td>
	</tr>
	<%i++;%>
</logic:iterate>
</table>