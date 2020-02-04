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
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<t:div styleClass="BoxFiltro">
	<t:panelGrid columns="5">
		<t:outputText value="Qualifica: " />
		<t:selectOneMenu
			valueChangeListener="#{accreditamentiFilterBean.typeQualificaChanged}">
			<f:selectItems value="#{accreditamentiFilterBean.filterTypeText}" />
		</t:selectOneMenu>
		<t:inputText value="#{accreditamentiFilterBean.fromQualifica}" />
		<t:outputText value=" a " />
		<t:inputText value="#{accreditamentiFilterBean.toQualifica}" />

		<t:outputText value="Codice Fiscale: " />
		<t:selectOneMenu
			valueChangeListener="#{accreditamentiFilterBean.typeCFChanged}">
			<f:selectItems value="#{accreditamentiFilterBean.filterTypeText}" />
		</t:selectOneMenu>
		<t:inputText value="#{accreditamentiFilterBean.fromCF}" />
		<t:outputText value=" a " />
		<t:inputText value="#{accreditamentiFilterBean.toCF}" />

		<t:outputText value="Comune: " />
		<t:selectOneMenu
			valueChangeListener="#{accreditamentiFilterBean.typeComuneChanged}">
			<f:selectItems value="#{accreditamentiFilterBean.filterTypeText}" />
		</t:selectOneMenu>
		<t:inputText value="#{accreditamentiFilterBean.fromComune}" />
		<t:outputText value=" a " />
		<t:inputText value="#{accreditamentiFilterBean.toComune}" />

		<t:outputText value="  " />
		<t:outputText value="  " />
		<t:panelGrid columns="2">
			<t:commandButton value="Annulla"
				action="#{accreditamentiFilterBean.cancel}" />
			<t:commandButton value="Filtra"
				action="#{accreditamentiFilterBean.filter}" />
		</t:panelGrid>
	</t:panelGrid>
</t:div>
