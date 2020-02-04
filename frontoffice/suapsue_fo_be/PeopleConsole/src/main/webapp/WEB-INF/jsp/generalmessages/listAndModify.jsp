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
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="pcfn" uri="/WEB-INF/peopleconsole-fn.tld" %>
<%@ taglib prefix="pcform" uri="/WEB-INF/peopleconsole.tld" %>
<html>
	<head>
		<title><spring:message code="generalmessages.listAndModify.title" /></title>
		
		<script language="JavaScript" src="/PeopleConsole/javascript/comboUtils.js" type="text/javascript"></script>
	</head>
	<body>
		<form:form modelAttribute="frameworkGenericMessages">

		<div class="pagedListHolderPanel">
			<fieldset>
				<legend>Messaggi del framework</legend>
				<div class="languages">
					Lingue disponibili:&nbsp;
					<form:select cssClass="textBlack" path="selectedFrameworkRegisterableLanguage">
						<form:options items="${frameworkRegisterableLocales}" itemLabel="value" itemValue="label" />
					</form:select>
					<input type="submit" id="registerFrameworkLanguage" name="registerFrameworkLanguage" value="Registra" class="button"/>
				</div>
				<div class="languages">
					Lingue registrate:&nbsp;
					<form:select cssClass="textBlack" path="selectedFrameworkLanguage" onChange="javascript:submit();">
						<form:options items="${frameworkLocales}" itemLabel="value" itemValue="label" />
					</form:select>
				</div>
				<pcform:listHolderTable pagedListHolderId="frameworkLabelsList" 
					pagedListHoldersCache="${frameworkGenericMessages.pagedListHolders}" 
					componentClass="pagedListHolderTable" 
					pagerClass="pagedListHolderPager" 
					pagerPagesTitleClass="pagedListHolderPagerTitleClass" 
					useTableBodyEvenOddRows="true" 
					tableHeadRowClass="logLabelHeader" 
					tableHeadRowCellClass="logLabelHeader" 
					tableClass="log" 
					tableBodyEvenRowClass="logEvenRow" 
					tableBodyOddRowClass="logOddRow" 
					rowsForPageClass="textNormal" 
					rowsForPageList="${rowsForPageList}" 
					rowsForPageLabel="${rowsForPageLabel}" 
					rowsForPageRefreshLabel="${rowsForPageRefreshLabel}" 
					highlightTableRows="true" 
					tableRowsHighlightingColor="${tableRowsHighlightingColor}" />
			</fieldset>
		</div>

		<div class="pagedListHolderPanel">
			<fieldset>
				<legend>Messaggi dei servizi</legend>
				<div class="languages">
					Servizio:&nbsp;
					<form:select cssClass="textBlack" path="selectedServicePackage" onChange="javascript:submit();">
						<form:options items="${feRegisteredServicesPackages}" itemLabel="label" itemValue="value" />
					</form:select>
				</div>
				<div class="languages">
					Lingue disponibili:&nbsp;
					<form:select cssClass="textBlack" path="selectedServicesRegisterableLanguage">
						<form:options items="${serviceRegisterableLocales}" itemLabel="value" itemValue="label" />
					</form:select>
					<input type="submit" id="registerServiceLanguage" name="registerServiceLanguage" value="Registra" class="button"/>
				</div>
				<div class="languages">
					Lingue registrate:&nbsp;
					<form:select cssClass="textBlack" path="selectedServicesLanguage" onChange="javascript:submit();">
						<form:options items="${serviceLocales}" itemLabel="value" itemValue="label" />
					</form:select>
				</div>
				<pcform:listHolderTable pagedListHolderId="serviceLabelsList" 
					pagedListHoldersCache="${frameworkGenericMessages.pagedListHolders}" 
					componentClass="pagedListHolderTable" 
					pagerClass="pagedListHolderPager" 
					pagerPagesTitleClass="pagedListHolderPagerTitleClass" 
					useTableBodyEvenOddRows="true" 
					tableHeadRowClass="logLabelHeader" 
					tableHeadRowCellClass="logLabelHeader" 
					tableClass="log" 
					tableBodyEvenRowClass="logEvenRow" 
					tableBodyOddRowClass="logOddRow" 
					rowsForPageClass="textNormal" 
					rowsForPageList="${rowsForPageList}" 
					rowsForPageLabel="${rowsForPageLabel}" 
					rowsForPageRefreshLabel="${rowsForPageRefreshLabel}" 
					highlightTableRows="true" 
					tableRowsHighlightingColor="${tableRowsHighlightingColor}" />
			</fieldset>
		</div>
		
		<div class="pagedListHolderPanel">
			<fieldset>
				
				<legend>Tablevalues dei servizi</legend>
				
				<div class="pagedListHolderPanel">
					
					<div class="languages">
						Servizio:&nbsp;
						<form:select cssClass="textBlack" path="selectedServicePackageTablevalue" 
							onChange="javascript:submitWithParam('usedCombo','feRegisteredServicesPackagesTablevalues');">
							<form:options items="${feRegisteredServicesPackagesTablevalues}" itemLabel="label" itemValue="value" />
						</form:select>
					</div>
					
					<div class="languages">
						Tablevalue:&nbsp;
						<form:select cssClass="textBlack" path="selectedTableValuesTableId" 
							onChange="javascript:submitWithParam('usedCombo','tableValuesTableIdCombo');">
							<form:options items="${tableValuesTableIdCombo}" itemLabel="label" itemValue="value" />
						</form:select>
					</div>
					
					<pcform:listHolderTable pagedListHolderId="tableValueList" 
						pagedListHoldersCache="${frameworkGenericMessages.pagedListHolders}" 
						componentClass="pagedListHolderTable" 
						pagerClass="pagedListHolderPager" 
						pagerPagesTitleClass="pagedListHolderPagerTitleClass" 
						useTableBodyEvenOddRows="true" 
						tableHeadRowClass="logLabelHeader" 
						tableHeadRowCellClass="logLabelHeader" 
						tableClass="log" 
						tableBodyEvenRowClass="logEvenRow" 
						tableBodyOddRowClass="logOddRow" 
						rowsForPageClass="textNormal" 
						rowsForPageList="${rowsForPageList}" 
						rowsForPageLabel="${rowsForPageLabel}" 
						rowsForPageRefreshLabel="${rowsForPageRefreshLabel}" 
						highlightTableRows="true" 
						tableRowsHighlightingColor="${tableRowsHighlightingColor}" /> 
				</div>		
				
			</fieldset>
		</div>
		
		
		<div class="pagedListHolderPanel">
			<fieldset>
				<legend>Template Velocity generali</legend>

				<div class="pagedListHolderPanel">		
					<pcform:listHolderTable pagedListHolderId="velocityTemplatesList" 
						pagedListHoldersCache="${frameworkGenericMessages.pagedListHolders}" 
						componentClass="pagedListHolderTable" 
						pagerClass="pagedListHolderPager" 
						pagerPagesTitleClass="pagedListHolderPagerTitleClass" 
						useTableBodyEvenOddRows="true" 
						tableHeadRowClass="logLabelHeader" 
						tableHeadRowCellClass="logLabelHeader" 
						tableClass="log" 
						tableBodyEvenRowClass="logEvenRow" 
						tableBodyOddRowClass="logOddRow" 
						rowsForPageClass="textNormal" 
						rowsForPageList="${rowsForPageList}" 
						rowsForPageLabel="${rowsForPageLabel}" 
						rowsForPageRefreshLabel="${rowsForPageRefreshLabel}" 
						highlightTableRows="true" 
						tableRowsHighlightingColor="${tableRowsHighlightingColor}" />
				</div>		
			</fieldset>
		</div>
		
		
		
		
		
		
		
		</form:form>
	</body>
</html>
