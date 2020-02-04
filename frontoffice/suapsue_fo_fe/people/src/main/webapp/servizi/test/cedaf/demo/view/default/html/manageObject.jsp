<%@ page language="java" %>
<%@ page import="it.people.layout.*"%>

<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html:xhtml/>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<h1><bean:message key="manageObject.titolo"/></h1>

<br />
<br />
<ppl:errors/>

<table summary="<bean:message key="manageObject.tableSummary"/>">
	<tr>
		<td id="nome"><bean:message key="manageObject.nome"/></td>
		<td id="valore"><bean:message key="manageObject.valore"/></td>
	</tr>

<logic:iterate id="oggetto" name="pplProcess" property="data.elencoOggetti">
	<tr>
		<td headers="nome">
			<bean:write name="oggetto" property="nome"/>
		</td>
		<td headers="valore">
			<bean:write name="oggetto" property="valore"/>
		</td>
		<td>
			<!-- esempio di utilizzo del tag di rimozione con l'indice implicito -->		
			<ppl:commandRemoveObject property="data.elencoOggetti" indexed="true" styleClass="btn">
				<bean:message key="manageObject.elimina"/>
			</ppl:commandRemoveObject>
		</td>
	</tr>
</logic:iterate>

	<tr>
		<td>
			<!-- elimina l'ultimo oggetto inserito -->
			<ppl:commandRemoveObject property="data.elencoOggetti" styleClass="btn" >
				<bean:message key="manageObject.eliminaUltimo"/>
			</ppl:commandRemoveObject>
		</td>
	</tr>

	<tr>
		<td><html:text name="pplProcess" property="data.elemento.nome" value="inserisci un nome"></html:text></td>
		<td><html:text name="pplProcess" property="data.elemento.valore" value="inserisci un valore"></html:text></td>
		<td>
			<ppl:commandAddObject styleClass="btn" property="data.elencoOggetti">
				<bean:message key="manageObject.addNewObject"/>
			</ppl:commandAddObject>		
		</td>
	</tr>
</table>
	