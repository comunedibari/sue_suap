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
		<title><spring:message code="feservice.details.title" /></title>
		<!--  script to submit params on combo actions -->
		<script language="JavaScript" src="/PeopleConsole/javascript/comboUtils.js" type="text/javascript"></script>
	</head>
	<body>
		<form:form modelAttribute="feService">

					<div class="buttonsbar">
						<input type="submit" id="cancel" name="cancel" value="Indietro" class="button" />
					</div>
			<div class="panel">
				<fieldset>
					<div class="panelRow2column">
						<label for="serviceName"> <spring:message code="feservice.details.serviceName" /> </label>
						<form:input id="serviceName" path="serviceName" />
						<form:errors path="serviceName" cssClass="errors" />
					</div>
					<div class="panelRow2column">
						<label for="nodeName"><spring:message code="feservice.details.municipalityName" /> </label>
						<div id="nodeName" class="textBlack">${feService.nodeName}</div>
					</div>
					<div class="panelRow2column">
						<label for="area"><spring:message code="feservice.details.activity" /> </label>
						<div id="area" class="textBlack">${feService.area}</div>
					</div>	
					<div class="panelRow2column">
						<label for="subArea"><spring:message code="feservice.details.subActivity" /> </label>
						<div id="subArea" class="textBlack">${feService.subArea}</div>
					</div>
					<div class="panelRow2column">
						<label for="_package"><spring:message code="feservice.details.package" /></label>
						<div id="_package" class="textBlack">${feService._package}</div>
					</div>	
					<div class="panelRow2column">
						<label for="process"><spring:message code="feservice.details.process" /> </label>
						<div id="process" class="textBlack">${feService.process}</div>
					</div>
					<div class="panelRow2column">
						<label for="logLevel"><spring:message code="feservice.details.logLevel" /></label>
						<form:select path="logLevel" id="logLevel">
							<form:options items="${logLevels}" itemLabel="label" itemValue="value" />
						</form:select>
					</div>
					<div class="panelRow2column">
						<label for="serviceStatus"><spring:message code="feservice.details.state" /></label>
						<form:select path="serviceStatus" id="serviceStatus">
							<form:options items="${statusTypes}" itemLabel="label" itemValue="value" />
						</form:select>
					</div>	
					<div class="panelRow2column">
						<label for="sendmailtoowner"><spring:message code="feservice.details.sendmailtoowner" /></label>
						<form:select path="sendmailtoowner" id="sendmailtoowner">
							<form:options items="${sendMailToOwnerTypes}" itemLabel="label" itemValue="value" />
						</form:select>
					</div>
					<div class="panelRow2column">
						<label for="attachmentsInCitizenReceipt"><spring:message code="feservice.details.includeAttachmentsInReceipt" /></label>
						<form:select path="attachmentsInCitizenReceipt" id="attachmentsInCitizenReceipt">
							<form:options items="${includeAttachmentsInReceiptTypes}" itemLabel="label" itemValue="value" />
						</form:select>
					</div>
					
					<!-- Gestione allegato in XML o remoto -->
					<div class="panelRow2column">
						<label for="embedAttachmentInXml"><spring:message code="feservice.details.embedAttachmentInXml" /></label>
						<form:select path="embedAttachmentInXml" id="embedAttachmentInXml">
							<form:options items="${embedAttachmentInXmlTypes}" itemLabel="label" itemValue="value" />
						</form:select>
					</div>
					
					<!-- Firma Procedimento ONLINE - OFFLINE -->
					<div class="text panelFieldset panelrowclearer">
						<fieldset>
							<legend><spring:message code="feservice.details.signProcess" /></legend>

							<div class="leftAlignInline">
								<form:select path="processSignEnabled" id="processSignEnabled">
									<form:options items="${signProcessTypes}" itemLabel="label" itemValue="value" />
								</form:select>
							</div>
							
							<div>
								<label for="feservice.details.onlineSign">
									<form:checkbox id="feservice.details.onlineSign" path="onlineSign" />
									<spring:message code="feservice.details.onlineSign" />
								</label>
								
								<label for="feservice.details.offlineSign">
									<form:checkbox id="feservice.details.offlineSign" path="offlineSign" />
									<spring:message code="feservice.details.offlineSign" />
								</label>
							</div>
							<br />
							<form:errors path="processSignEnabled" cssClass="error"/>
						</fieldset>
					</div>	

					
					<!-- Privacy checkbox filedset -->
					<div class="text panelFieldset panelrowclearer">
						<fieldset>
							<legend><spring:message code="feservice.details.privacy" /></legend>
							
							<label for="feservice.details.privacy.showPrivacyDisclaimer">
								<form:checkbox id="feservice.details.privacy.showPrivacyDisclaimer" path="showPrivacyDisclaimer" />
								<spring:message code="feservice.details.privacy.showPrivacyDisclaimer" />
							</label>
							<label for="feservice.details.privacy.privacyDisclaimerRequireAcceptance">
								<form:checkbox id="feservice.details.privacy.privacyDisclaimerRequireAcceptance" path="privacyDisclaimerRequireAcceptance" />
								<spring:message code="feservice.details.privacy.privacyDisclaimerRequireAcceptance" />
							</label>
						</fieldset>
					</div>
							
					
					<div class="text panelFieldset panelrowclearer">
						<fieldset>
							<legend><spring:message code="feservice.details.processActivationType" /></legend>
							<form:radiobutton path="activationType" value="1"/><span class="text"><spring:message code="feservice.details.processActivationType.webService" /></span>
							<br/>
							<form:radiobutton path="activationType" value="2"/><span class="text"><spring:message code="feservice.details.processActivationType.eMail" /></span>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="text"><spring:message code="feservice.details.processActivationType.eMail.address" /></span>&nbsp;<form:input path="processActivationType.eMailAddress"/>
							<form:errors path="processActivationType.eMailAddress" cssClass="errors" />
							<br/>
							<form:radiobutton path="activationType" value="3"/><span class="text"><spring:message code="feservice.details.processActivationType.none" /></span>
						</fieldset>
					</div>

					<div class="buttonsbar">
						<input type="submit" id="updateFeService" name="updateFeService" value="Aggiorna" class="button" />
					</div>
				</fieldset>
			</div>
			
			<div id="tabPaneHeader">
				<pcform:tabbedPaneHeader selectedTab="${tab}" 
					tabsParams="${tabbedPaneHeaders}" 
					queryStringParam="tab" 
					selectedTabCssId="selected" />
			</div>				

			<div id="tabPaneContent">
				
				<c:choose>
					
					<c:when test="${tab == 'parametri'}">
						<pcform:listHolderTable pagedListHolderId="parametersList" 
							pagedListHoldersCache="${feService.pagedListHolders}" 
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
					</c:when>
					
					<c:when test="${tab == 'messaggi'}">
						<div class="pagedListHolderPanel">
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
								pagedListHoldersCache="${feService.pagedListHolders}" 
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
					</c:when>
				
					<c:when test="${tab == 'tablevalues'}">
					
						<div class="pagedListHolderPanel">
							<div class="languages">
								Tablevalue:&nbsp;
								<form:select cssClass="textBlack" id="tableValuesTableId" path="selectedTableId" onChange="javascript:submitWithParam('usedCombo','tableValuesTableId');">
									<form:options items="${tableValuesTableIdCombo}" itemLabel="label" itemValue="value" />
								</form:select>
							</div>
						
							<pcform:listHolderTable pagedListHolderId="tableValueList" 
								pagedListHoldersCache="${feService.pagedListHolders}" 
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
								
					</c:when>
					
					
					<c:when test="${tab == 'audit'}">
					
						<div class="pagedListHolderPanel">		
							<pcform:listHolderTable pagedListHolderId="auditProcessorsList" 
								pagedListHoldersCache="${feService.pagedListHolders}" 
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
					</c:when>
										
					<c:when test="${tab == 'velocityTemplates'}">
					
						<div class="pagedListHolderPanel">		
							<pcform:listHolderTable pagedListHolderId="velocityTemplatesList" 
								pagedListHoldersCache="${feService.pagedListHolders}" 
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
								
					</c:when>
					
					
					
					<c:otherwise>
						<pcform:listHolderTable pagedListHolderId="beReferencesList" 
							pagedListHoldersCache="${feService.pagedListHolders}" 
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
					</c:otherwise>		
				</c:choose>
			</div>
			
			
			<div class="buttonsbar">
				<input type="submit" id="cancel" name="cancel" value="Indietro" class="button" />
			</div>

		</form:form>
	</body>
</html>
