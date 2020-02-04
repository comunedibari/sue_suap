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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
	<head>
		<!-- link href="/SpringMVCStructure/css/base.css" rel="stylesheet" media="screen" type="text/css" /-->
		<script language="JavaScript" src="<%out.print(request.getContextPath());%>/javascript/audit.js" type="text/javascript"></script>
		<title><spring:message code="nodeAuditConversations.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="auditConversationFilter" name="auditConversationFilter">
		<form:hidden path="pageSize" />
		
			<fieldset>
			<div class="panel">
				<div class="panelRow">
					<label for="selectedUser"><spring:message code="nodeAuditConversations.searchByUser" /></label>&nbsp;
				</div>
				<div class="panelRowUsers">
					<form:radiobutton path="tipoUtente" name="tipoUtente" id="tipoUtente0" value="0" />&nbsp;
					<label class="label"><spring:message code="nodeAuditConversations.searchByUser_cittadini" /></label>
					<label class="labelDate"><spring:message code="nodeAuditConversations.searchByUser_firstName" /></label>
					<form:input path="firstName"  />
					<label class="labelDate"><spring:message code="nodeAuditConversations.searchByUser_lastName" /></label>
					<form:input path="lastName"  />
					<label class="labelDate"><spring:message code="nodeAuditConversations.searchByUser_taxCode" /></label>&nbsp;
					<form:input path="taxCode" maxlength="16" />
					<br>
					<form:radiobutton path="tipoUtente" name="tipoUtente" id="tipoUtente1" value="1"/>&nbsp;
					<label class="label"><spring:message code="nodeAuditConversations.searchByUser_intermediari" /></label>
					<label class="labelDate"><spring:message code="nodeAuditConversations.searchByUser_firstName" /></label>
					<form:input path="firstNameNp"  />
					<label class="labelDate"><spring:message code="nodeAuditConversations.searchByUser_lastName" /></label>
					<form:input path="lastNameNp"  />
					<label class="labelDate"><spring:message code="nodeAuditConversations.searchByUser_businessName" /></label>
					<form:input path="businessName" size="28" />
					<br>
				</div>
				
				<div class="panelRowUsers">
		            <label for="selectedFeService">
		            	<spring:message code="nodeAuditConversations.searchByService" />
		            </label>
		            <label class="labelDate"><spring:message code="nodeAuditConversations.searchByService_area" /></label>
					<form:select path="attivita" id="attivita" onchange="javascript:updateArea();submit();">
						<form:options items="${servicesAttivitaList}" itemLabel="label" itemValue="value" />
					</form:select>
					<noscript>
						<input type="submit" id="sceltaArea" name="sceltaArea" value="Conferma area" class="button" alt="Conferma area" title="Conferma area" />
					</noscript>
		            <label class="labelDate">&nbsp;<spring:message code="nodeAuditConversations.searchByService_service" /></label>
					<form:select path="serviceId" id="serviceId">
						<form:options items="${servicesList}" itemLabel="feServiceName" itemValue="feServiceId" />
					</form:select>
				</div>

				<div class="panelRowUsers">
			        <label>
		            	<spring:message code="nodeAuditConversations.searchByDate" />
		            </label>
		            <label class="labelDate">
		            	<spring:message code="nodeAuditConversations.searchByDate_from" />
		            </label>
			
					<!-- calendar attaches to existing form element -->
					<form:input path="from" maxlength="10" name="fromInput" id="fromInput" />
						<script language="JavaScript">
						var o_cal = 
						new tcal ({
							// input name
							'controlname': 'fromInput'
						});
						
						// individual template parameters can be modified via the calendar variable
						o_cal.a_tpl.yearscroll = false;
						o_cal.a_tpl.weekstart = 1;
						
						</script>
					
					&nbsp;
					<label class="labelDate">            	
		            	<spring:message code="nodeAuditConversations.searchByDate_to" />
		            </label>
					<form:input path="to" maxlength="10" name="toInput" id="toInput"/>
						<script language="JavaScript">
		
						var o_cal2 = 
						new tcal ({
							'controlname': 'toInput'
						}, o_cal2);
						</script>
				</div>
				<form:errors path="error" cssClass="error"/>
				
			</div>

			<c:if test="${!empty auditConversationsBeans}">
				<div class="panel">
				
					<fieldset>
						<legend class="title">Generazione report</legend>
						<div class="panelRow">
							<form:select items="${reportTypes}" itemLabel="label" 
								itemValue="value" path="reportSettings.reportType" />
							<input type="submit" id="generateReport" name="generateReport" value="Genera report" class="button" alt="Genera report" title="Genera report" />
							<c:if test="${reportGenerationErrorMessage != null}">
								<%@ include file="/WEB-INF/jsp/shared/reportError.jsp" %>
							</c:if>
						</div>
					</fieldset>
				</div>
			</c:if>
				
			<div class="buttonsbar">
				<input type="submit" id="viewPage" name="viewPage" value="Visualizza" class="button"  alt="Visualizza" title="Visualizza"/>
				<input type="submit" id="cancel" name="cancel" value="< Indietro" class="button"  alt="Torna alla pagina precedente" title="Torna alla pagina precedente"/>
			</div>
			
			</fieldset>
			<c:if test="${!empty auditConversationsBeans}">
			<fieldset>
				<div class="midbar">
				
					<div class="panelRowResultForPage">
						<label for="resultForPage" class="panelRowResultForPage"><spring:message code="pageNavigation.label.resultForPage" /></label>		
						<form:select items="${resultForPageList}" itemLabel="label" 
							itemValue="value" path="pageNavigationSettings.resultsForPage" cssClass="panelRowResultForPage"/>
					</div>
				
					<form:hidden path="res_count" />
					<input type="image" src="/PeopleConsole/images/arrow-first.gif" id="viewPage" name="viewPage" value="Prima pagina" alt="Prima pagina" title="Prima pagina"/>
					<input type="image" src="/PeopleConsole/images/arrow-previous.gif" id="prevPage" name="prevPage" value="Pagina precedente" alt="Pagina precedente" title="Pagina precedente" />
					<span class="textNormal" >
					<form:input path="actualPage" readonly="readonly" size="1"/>&nbsp;di&nbsp;
					<form:input path="totalPages" readonly="readonly" size="2"/>
					</span>
					<input type="image" src="/PeopleConsole/images/arrow-next.gif" id="nextPage" name="nextPage" value="Pagina successiva" alt="Pagina successiva" title="Pagina successiva" />
					<input type="image" src="/PeopleConsole/images/arrow-last.gif" id="lastPage" name="lastPage" value="Ultima pagina" alt="Ultima pagina" title="Ultima pagina" />
				</div>
			</fieldset>

			<div class="tablelog">
			    <table class="log" summary="Conversazioni" title="Conversazioni">
				    <tr class=logLabelRow>
		                <th scope="col"><spring:message code="nodeAuditConversations.label.date" /></th>
		                <th scope="col"></th>                
		                <th scope="col"><spring:message code="nodeAuditConversations.label.user" /></th>                
		                <th scope="col"><spring:message code="nodeAuditConversations.label.service" /></th>                
		                <th scope="col"><spring:message code="nodeAuditConversations.label.action" /></th>                
		                <th scope="col"></th>                
		                <th scope="col"><spring:message code="nodeAuditConversations.label.message" /></th>                
		            </tr>    
		            
					<c:forEach var="auditConv" items="${auditConversationsBeans}" varStatus="rowCounter">
					  <c:choose>
					    <c:when test="${rowCounter.count % 2 == 0}">
					      <c:set var="rowStyle" scope="page" value="logOddRow"/>
					    </c:when>
					    <c:otherwise>
					      <c:set var="rowStyle" scope="page" value="logEvenRow"/>
					    </c:otherwise>
					  </c:choose>
					  <tr class="${rowStyle}">					  
				   		
				   		<td><fmt:formatDate value="${auditConv.audit_timestamp_date}"  pattern="dd/MM/yyyy HH:mm:ss" /></td>
				   		 	
					  	<c:choose> 
					  		<c:when test="${!empty auditConv.audit_users_link}">
							  	<td><a href="${auditConv.audit_users_link}"  id="${auditConv.audit_users_link}">
									<img src="/PeopleConsole/images/lente.png" border="0" title="Dettaglio conversazione" alt="Dettaglio conversazione"></a>
								</td>
							</c:when>
							<c:otherwise> 
						    	<td></td> 
						  	</c:otherwise> 
						</c:choose> 
						<td>${auditConv.tax_code}</td>
						<td>${auditConv.process_name}</td>
						<td>${auditConv.action_name}</td>
					  	<c:choose> 
					  		<c:when test="${auditConv.include_audit_febe_xml!=0}">
							  	<td>
							  		<img src="/PeopleConsole/images/icon_xml.png" border="0" title="Ricevuto documento XML" alt="Ricevuto documento XML">
								</td>
							</c:when>
							<c:otherwise> 
						    	<td></td> 
						  	</c:otherwise> 
						</c:choose> 
						<td>${auditConv.message}</td>
					  </tr>
					</c:forEach>
					
			    </table>
		    </div>
		    </c:if>
		    

		</form:form>
	</body>
</html>
