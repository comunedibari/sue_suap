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
			valueChangeListener="#{qualificheFilterBean.typeQualificaChanged}">
			<f:selectItems value="#{qualificheFilterBean.filterTypeText}" />
		</t:selectOneMenu>
		<t:inputText value="#{qualificheFilterBean.fromQualifica}" />
		<t:outputText value=" a " />
		<t:inputText value="#{qualificheFilterBean.toQualifica}" />

		<t:outputText value="Descrizione: " />
		<t:selectOneMenu
			valueChangeListener="#{qualificheFilterBean.typeDescrChanged}">
			<f:selectItems value="#{qualificheFilterBean.filterTypeText}" />
		</t:selectOneMenu>
		<t:inputText value="#{qualificheFilterBean.fromDescr}" />
		<t:outputText value=" a " />
		<t:inputText value="#{qualificheFilterBean.toDescr}" />

		<t:outputText value="Tipologia: " />
		<t:selectOneMenu
			valueChangeListener="#{qualificheFilterBean.typeTipologiaChanged}">
			<f:selectItems value="#{qualificheFilterBean.filterTypeText}" />
		</t:selectOneMenu>
		<t:selectOneMenu value="#{qualificheFilterBean.fromTipologia}">
			<f:selectItem itemLabel="" itemValue="" />
			<t:selectItems value="#{tableManager.tableAction.tipoQualifiche}"
				var="Var1" itemLabel="#{Var1}" itemValue="#{Var1}" />
		</t:selectOneMenu>
		<t:outputText value=" a " />
		<t:selectOneMenu value="#{qualificheFilterBean.toTipologia}">
			<f:selectItem itemLabel="" itemValue="" />
			<t:selectItems value="#{tableManager.tableAction.tipoQualifiche}"
				var="Var2" itemLabel="#{Var2}" itemValue="#{Var2}" />
		</t:selectOneMenu>

		<t:outputText value="  " />
		<t:outputText value="  " />
		<t:panelGrid columns="2">
			<t:commandButton value="Annulla"
				action="#{qualificheFilterBean.cancel}" />
			<t:commandButton value="Filtra"
				action="#{qualificheFilterBean.filter}" />
		</t:panelGrid>
	</t:panelGrid>
</t:div>
