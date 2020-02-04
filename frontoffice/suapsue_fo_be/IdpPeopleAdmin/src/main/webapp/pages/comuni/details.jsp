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
<%@ taglib
	uri="http://sourceforge.net/projects/jsf-comp/clientvalidators"
	prefix="cv"%>
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
						<t:outputText value="Comune di #{tableManager.current.comune}" />
					</f:subview>
					<f:subview id="titleInsert" rendered="#{tableManager.insert}">
						<t:outputText value="Inserisci nuovo comune" />
					</f:subview>
				</h2>
				<t:outputText value="#{tableManager.tableAction.error}"
					style="color: red;" />
				<br />
				<table>
					<tr>
						<td align="right">
							<t:outputText value="Comune: "
								style=" width : 54px; height : 19px;" />
						</td>
						<td>
							<t:inputText id="Comune" value="#{tableManager.current.comune}"
								style="width:200px;" />
							<cv:requiredFieldValidator componentToValidate="Comune"
								highlight="true" display="dynamic"
								errorMessage="Il nome del Comune è obbligatorio" style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Provincia: " />
						</td>
						<td>
							<t:inputText id="Provincia"
								value="#{tableManager.current.provincia}" style="width:30px;" maxlength="2" />
							<cv:requiredFieldValidator componentToValidate="Provincia"
								highlight="true" display="dynamic"
								errorMessage="La Provincia è obbligatoria" style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Regione: " />
						</td>
						<td>
							<t:inputText id="Regione" value="#{tableManager.current.regione}"
								style="width:40px;" maxlength="3" />
							<cv:requiredFieldValidator componentToValidate="Regione"
								highlight="true" display="dynamic"
								errorMessage="La Regione è obbligatoria" style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="CAP: " />
						</td>
						<td>
							<t:inputText id="CAP" value="#{tableManager.current.cap}"
								style="width:60px;" maxlength="5" />
							<cv:requiredFieldValidator componentToValidate="CAP"
								highlight="true" display="dynamic"
								errorMessage="Il CAP è obbligatorio" style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Prefisso: " />
						</td>
						<td>
							<t:inputText id="Prefisso" required="true"
								value="#{tableManager.current.prefisso}" style="width:60px;" maxlength="5" />
							<cv:requiredFieldValidator componentToValidate="Prefisso"
								highlight="true" display="dynamic"
								errorMessage="Il Prefisso è obbligatorio" style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Codice comune: " />
						</td>
						<td>
							<t:inputText id="CodiceComune" required="true"
								value="#{tableManager.current.codiceComune}"
								style="width:55px;" maxlength="4" />
							<cv:requiredFieldValidator componentToValidate="CodiceComune"
								highlight="true" display="dynamic"
								errorMessage="Il Codice Comune è obbligatorio" style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:outputText value="Codice ISTAT: " />
						</td>
						<td>
							<t:inputText id="CodiceISTAT" required="true"
								value="#{tableManager.current.codiceIstat}" style="width:75px;" maxlength="6" />
							<cv:requiredFieldValidator componentToValidate="CodiceISTAT"
								highlight="true" display="dynamic"
								errorMessage="Il Codice ISTAT è obbligatorio" style="error" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<t:commandButton value="#{tableManager.buttonLabel}"
								action="#{tableManager.buttonAction}"
								onclick="return validate();" />
						</td>
						<td>
							<t:commandButton value="Annulla" immediate="true" action="cancel" />
						</td>
					</tr>
				</table>
				<cv:scriptGenerator form="form1" popup="false" />
			</h:form>
		</f:view>
	</body>
</html>
