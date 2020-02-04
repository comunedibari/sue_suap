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
<%@ page contentType="text/html"%>
<jsp:directive.page
	import="it.idp.people.admin.sqlmap.sirac.accreditamenti.AccreditamentiDAO" />
<jsp:directive.page
	import="it.idp.people.admin.sqlmap.sirac.accreditamenti.Accreditamenti" />
<jsp:directive.page import="it.idp.people.admin.faces.Manager" />
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib
	uri="http://sourceforge.net/projects/jsf-comp/clientvalidators"
	prefix="cv"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"%>
<html>
	<head>
		<script type="text/javascript" src="/IdpPeopleAdmin/scripts/validator.js"></script>
		<title>Dettagli</title>
		<%
			Manager manager = (Manager) session.getAttribute("tableManager");
			Accreditamenti current = ((Accreditamenti) manager.getCurrent());
			((AccreditamentiDAO) manager.getTableAction())
					.checkIntermediario(current);
			((AccreditamentiDAO) manager.getTableAction()).setError("");
		%>
	</head>
	<body>
		<f:view>
			<h:form id="form1" enctype="multipart/form-data">
				<br />
				<h2>
					<f:subview id="titleEdit" rendered="#{!tableManager.insert}">
						<t:outputText
							value="Accreditamento n° #{tableManager.current.idAccreditamento}" />
					</f:subview>
					<f:subview id="titleInsert" rendered="#{tableManager.insert}">
						<t:outputText value="Inserisci nuovo accreditamento" />
					</f:subview>
				</h2>
				<t:outputText value="#{tableManager.tableAction.error}"
					style="color: red;" />
				<br />
				<table>
					<tr>
						<td align="right">
							<t:outputText value="Codice fiscale: "
								style=" width : 54px; height : 19px;" />
						</td>
						<td>
							<t:inputText id="codiceFiscale"
								value="#{tableManager.current.codiceFiscale}"
								style="width:160px;" required="true" maxlength="16" />
							<cv:customValidator componentToValidate="codiceFiscale"
								function="ControllaCF" params="'form1:codiceFiscale'"
								highlight="true" display="dynamic"
								errorMessage="Il Codice Fiscale è errato o mancante" style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Codice ente: " />
						</td>
						<td>
							<f:subview id="idComuneLabel" rendered="#{!tableManager.insert}">
								<t:inputText readonly="true"
									value="#{tableManager.current.idComune}" style="width:300px;"
									maxlength="6" />
							</f:subview>
							<f:subview id="portalIDLabel" rendered="#{tableManager.insert}">
								<t:inputText readonly="true" value="#{tableManager.portalID}"
									style="width:200px;" />
							</f:subview>
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Qualifica: " />
						</td>
						<td>
							<t:selectOneMenu id="idQualifica"
								value="#{tableManager.current.idQualifica}"
								binding="#{tableManager.tableAction.selectQualifica}">
								<f:selectItems value="#{tableManager.tableAction.idQualifiche}" />
								<a4j:support event="onchange" immediate="true"
									reRender="scriptBox, cfInterLabel1, cfInterLabel2, pivaInterLabel1, pivaInterLabel2, denominazione1, denominazione2"
									actionListener="#{tableManager.tableAction.valueChange}" />
							</t:selectOneMenu>
							<cv:requiredFieldValidator componentToValidate="idQualifica"
								highlight="true" display="dynamic"
								errorMessage="La qualifica è obbligatoria" style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Domicilio elettronico: " />
						</td>
						<td>
							<t:inputText id="domicilioElettronico"
								value="#{tableManager.current.domicilioElettronico}"
								style="width:200px;" />
							<cv:requiredFieldValidator
								componentToValidate="domicilioElettronico" highlight="true"
								display="dynamic"
								errorMessage="Il domicilio elettronico è obbligatorio"
								style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<a4j:outputPanel id="cfInterLabel1">
								<t:outputText value="Codice fiscale intermediario: " rendered="#{tableManager.tableAction.intermediario}" />
								<t:outputText value="Codice fiscale: " rendered="#{!tableManager.tableAction.intermediario}" />
							</a4j:outputPanel>
						</td>
						<td>
							<a4j:outputPanel id="cfInterLabel2">
									<h:inputText id="codicefiscaleIntermediario"
										value="#{tableManager.current.codicefiscaleIntermediario}" 
										style="width:160px;" maxlength="11" disabled="#{!tableManager.tableAction.intermediario}"/>
									<cv:customValidator componentToValidate="codicefiscaleIntermediario"
										function="ControllaPIVA"
										params="'form1:codicefiscaleIntermediario'" highlight="true"
										display="dynamic" errorMessage="Il codice fiscale dell'intermediario è errato o mancante"
										style="error" />
							</a4j:outputPanel>
						</td>
					</tr>
					<tr>
						<td align="right">
							<a4j:outputPanel id="pivaInterLabel1">
								<t:outputText value="Partita iva intermediario: "
									rendered="#{tableManager.tableAction.intermediario}" />
							</a4j:outputPanel>
							<a4j:outputPanel id="pivaInterLabel2">
								<t:outputText value="Partita iva: "
									rendered="#{!tableManager.tableAction.intermediario}" />
							</a4j:outputPanel>
						</td>
						<td>
							<t:inputText id="partitaivaIntermediario"
								value="#{tableManager.current.partitaivaIntermediario}"
								style="width:160px;" maxlength="11" />
							<cv:customValidator componentToValidate="partitaivaIntermediario"
								function="ControllaPIVA"
								params="'form1:partitaivaIntermediario'" highlight="true"
								display="dynamic" errorMessage="La partita iva è errata o mancante"
								style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Descrizione: " />
						</td>
						<td>
							<t:inputText id="descrizione"
								value="#{tableManager.current.descrizione}" style="width:200px;" />
							<cv:requiredFieldValidator componentToValidate="descrizione"
								highlight="true" display="dynamic"
								errorMessage="La descrizione è obbligatoria" style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Sede legale: " />
						</td>
						<td>
							<t:inputText id="sedeLegale"
								value="#{tableManager.current.sedeLegale}" style="width:200px;" />
							<cv:requiredFieldValidator componentToValidate="sedeLegale"
								highlight="true" display="dynamic"
								errorMessage="La sede legale è obbligatoria" style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<a4j:outputPanel id="denominazione1">
								<t:outputText value="Denominazione: "
									rendered="#{tableManager.tableAction.intermediario}" />
							</a4j:outputPanel>
							<a4j:outputPanel id="denominazione2">
								<t:outputText value="Nome e Cognome: "
									rendered="#{!tableManager.tableAction.intermediario}" />
							</a4j:outputPanel>
						</td>
						<td>
							<t:inputText id="denominazione"
								value="#{tableManager.current.denominazione}"
								style="width:200px;" />
							<cv:requiredFieldValidator componentToValidate="denominazione"
								highlight="true" display="dynamic"
								errorMessage="La denominazione è obbligatoria" style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Attivo: " />
						</td>
						<td>
							<t:selectBooleanCheckbox value="#{tableManager.current.attivo}" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Eliminato: " />
						</td>
						<td>
							<t:selectBooleanCheckbox value="#{tableManager.current.deleted}" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Seleziona file auto-certificazione: " />
						</td>
						<td>
							<t:inputFileUpload id="file" immediate="true" storage="file"
								value="#{tableManager.tableAction.file}" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:commandButton value="#{tableManager.buttonLabel}"
								action="#{tableManager.buttonAction}"
								onclick="return validate();" />
						</td>
						<td>
							<t:commandButton value="Annulla" action="cancel" />
						</td>
					</tr>
				</table>
				<cv:scriptGenerator form="form1" popup="false" />
			</h:form>
		</f:view>
	</body>
</html>
