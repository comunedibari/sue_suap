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
		<title><spring:message code="fenodes.nodeDetails.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="feNode">
		<fieldset>
			<!-- show error -->
			<form:errors path="error" cssClass="error"/>
			
			<div class="panel">
				<div class="panelRow">
		            <label class="label" for="name">
		            	<spring:message code="fenodes.listAndAdd.nodeName" />
		            </label>
					<form:input id="name" cssClass="input" path="name" />
					<form:errors path="name" cssClass="errors"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="feServiceURL">
		            	<spring:message code="fenodes.listAndAdd.nodeFEWSURL" />
		            </label>
					<form:input id="feServiceURL" cssClass="input" path="feServiceURL" />
					<form:errors path="feServiceURL" cssClass="errors"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="municipality">
		            	<spring:message code="fenodes.listAndAdd.communeName" />
		            </label>
					<form:input id="municipality" cssClass="input" path="municipality" />
					<form:errors path="municipality" cssClass="errors"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="municipalityCode">
		            	<spring:message code="fenodes.listAndAdd.communeCode" />
		            </label>
					<form:input id="municipalityCode" cssClass="input" path="municipalityCode" readonly="true"/>
					<form:errors path="municipalityCode" cssClass="errors"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="delegationControlEnabled">
		            	<spring:message code="fenodes.listAndAdd.delegationControlEnabled" />
		            </label>
		            <form:checkbox id="delegationControlEnabled" cssClass="input" path="delegationControlEnabled" />
					<form:errors path="delegationControlEnabled" cssClass="errors"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="delegationControlEnabled">
		            	<spring:message code="fenodes.listAndAdd.delegationControlWSURL" />
		            </label>
					<form:input id="delegationControlServiceURL" cssClass="input" path="delegationControlServiceURL" />
					<form:errors path="delegationControlServiceURL" cssClass="errors"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="aooPrefix">
		            	<spring:message code="fenodes.listAndAdd.aooPrefix" />
		            </label>
					<form:input id="aooPrefix" cssClass="input" path="aooPrefix" />
					<form:errors path="aooPrefix" cssClass="errors"/>
				</div>
				
				<!-- Announcement Message -->
				<div class="panelRow">
		            <label class="label" for="announcementMessage">
		            	<spring:message code="fenodes.listAndAdd.announcementMessage" />
		            </label>
					<form:textarea id="announcementMessage" path="announcementMessage" rows="5" cols="30"/>
					<form:errors path="announcementMessage" cssClass="error"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="showAnnouncement">
		            	<spring:message code="fenodes.listAndAdd.showAnnouncement" />
		            </label>
		            <form:checkbox id="showAnnouncement" path="showAnnouncement" />
					<form:errors path="showAnnouncement" cssClass="error"/>
				</div>
				
				<!-- Firma Procedimento ONLINE - OFFLINE -->
				<br />
				<div class="text panelFieldset panelrowclearer">
					<fieldset>
						<legend><spring:message code="feservice.details.signProcess" /></legend>

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
					</fieldset>
				</div>	
			
			</div>

			
			<div class="buttonsbar">
				<input type="submit" id="updateFeNode" name="updateFeNode" value="Aggiorna" class="button" />
				<input type="submit" id="cancel" name="cancel" value="Annulla" class="button" />
			</div>
		</fieldset>


		<div id="tabPaneHeader">
				<pcform:tabbedPaneHeader selectedTab="${tab}" 
					tabsParams="${tabbedPaneHeaders}" 
					queryStringParam="tab" 
					selectedTabCssId="selected" />
			</div>				

			<div id="tabPaneContent">
				
				<c:choose>
					<c:when test="${tab == 'velocityTemplates'}">
						<div class="pagedListHolderPanel">		
							<pcform:listHolderTable pagedListHolderId="velocityTemplatesList" 
								pagedListHoldersCache="${feNode.pagedListHolders}" 
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
						<div class="pagedListHolderPanel">
							<div class="languages">
								Lingue disponibili:&nbsp;
								<form:select cssClass="textBlack" path="selectedServicesRegisterableLanguage">
									<form:options items="${nodeRegisterableLocales}" itemLabel="value" itemValue="label" />
								</form:select>
								<input type="submit" id="registerNodeLanguage" name="registerNodeLanguage" value="Registra" class="button"/>
							</div>
							<div class="languages">
								Lingue registrate:&nbsp;
								<form:select cssClass="textBlack" path="selectedServicesLanguage" onChange="javascript:submit();">
									<form:options items="${nodeLocales}" itemLabel="value" itemValue="label" />
								</form:select>
							</div>		
									
							<pcform:listHolderTable pagedListHolderId="nodeLabelsList" 
								pagedListHoldersCache="${feNode.pagedListHolders}" 
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
					</c:otherwise>
					
			</c:choose>
		</div>
		
		</form:form>
		
	</body>
</html>
