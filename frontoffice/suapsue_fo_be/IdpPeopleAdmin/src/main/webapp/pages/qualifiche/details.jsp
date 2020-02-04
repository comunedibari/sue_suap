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
<%@ taglib uri="http://sourceforge.net/projects/jsf-comp/clientvalidators" prefix="cv"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<html>
	<head>
		<title>Dettagli</title>
	</head>
	<body>
		<f:view>
			<h:form id="form1">
				<br />
				<h2>
					<f:subview id="titleEdit" rendered="#{!tableManager.insert}">
						<t:outputText
							value="Qualifica #{tableManager.current.descrizione}" />
					</f:subview>
					<f:subview id="titleInsert" rendered="#{tableManager.insert}">
						<t:outputText value="Inserisci nuova qualifica" />
					</f:subview>
				</h2>
				<t:outputText value="#{tableManager.tableAction.error}"
					style="color: red;" />
				<br />
				<table>
					<tr>
						<td align="right">
							<t:outputText value="Identificativo qualifica: "
								style=" width : 54px; height : 19px;" />
						</td>
						<td>
							<t:inputText id="idQualifica"
								value="#{tableManager.current.idQualifica}" style="width:200px;" />
							<cv:requiredFieldValidator componentToValidate="idQualifica"
								highlight="true" display="dynamic"
								errorMessage="L'identificativo della qualifica è obbligatorio"
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
							<t:outputText value="Tipo qualifica: " />
						</td>
						<td>
							<t:selectOneMenu id="tipoQualifica"
								value="#{tableManager.current.tipoQualifica}">
								<f:selectItem itemLabel="--- scegliere una qualifica ---" itemValue="" />
								<t:selectItems
									value="#{tableManager.tableAction.tipoQualifiche}" var="Var"
									itemLabel="#{Var}" itemValue="#{Var}" />
							</t:selectOneMenu>
							<cv:requiredFieldValidator componentToValidate="tipoQualifica"
								highlight="true" display="dynamic"
								errorMessage="Il tipo della qualifica è obbligatorio"
								style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Auto certificabile: " />
						</td>
						<td>
							<t:selectBooleanCheckbox
								value="#{tableManager.current.autoCertificabile}" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Rappresentante legale: " />
						</td>
						<td>
							<t:selectBooleanCheckbox
								value="#{tableManager.current.hasRappresentanteLegale}" />
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
