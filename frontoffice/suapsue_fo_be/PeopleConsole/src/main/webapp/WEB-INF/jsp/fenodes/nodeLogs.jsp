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
		<title><spring:message code="fenodes.nodeLogs.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="feNodeLogFilter">
			<fieldset>
			<div class="panel">
				<div class="panelRowUsers">
					<label for="logLevel"><spring:message code="fenodes.nodeLogs.searchByLevel" /></label>
					<form:select path="logLevel" id="logLevel">
						<form:options items="${logLevels}" itemLabel="label" itemValue="value" />
					</form:select>
				</div>
				<div class="panelRowUsers">
		            <label for="selectedFeService">
		            	<spring:message code="fenodes.nodeLogs.searchByService" />
		            </label>
					<form:select path="serviceId" id="serviceId">
						<form:options items="${feServicesList}" itemLabel="feServiceName" itemValue="feServiceId" />
					</form:select>
				</div>
				<form:errors path="error" cssClass="error"/>
				
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
				

				

			</div>
			<div class="buttonsbar">
				<input type="submit" id="view" name="view" value="Visualizza" class="button" alt="Visualizza" title="Visualizza "/>
				<input type="submit" id="cancel" name="cancel" value="< Indietro" class="button" alt="Torna alla pagina precedente" title="Torna alla pagina precedente"/>
			</div>
			
			<form:hidden path="orderBy" name="orderBy" id="orderBy"/>
			<form:hidden path="orderType" name="orderType" id="orderType"/>
			
			</fieldset>
			<c:if test="${!empty logBeans}">
			<div class="tablelog">
			    <table class="log">
				    <tr class=logLabelRow>
		                <th align="left">
							<button type="submit" id="orderByDate" name="orderByDate" class="buttonTable">
								<spring:message code="fenodes.nodeLogs.label.date" />
								<c:if test="${feNodeLogFilter.orderBy=='date'}">
									<c:if test="${feNodeLogFilter.orderType=='desc'}">
										<img src="/PeopleConsole/images/sort-desc.png" border="0" 
											title='<spring:message code="fenodes.nodeLogs.label.date" />'  
											alt='<spring:message code="fenodes.nodeLogs.label.date" />' 
									  	>     
								   	</c:if>
								   	<c:if test="${feNodeLogFilter.orderType=='asc'}">
								   	  	<img src="/PeopleConsole/images/sort-asc.png" border="0" 
											title='<spring:message code="fenodes.nodeLogs.label.date" />'  
											alt='<spring:message code="fenodes.nodeLogs.label.date" />' 
									  	>
								   	</c:if>
								</c:if>
							</button>
						</th>

		                <th align="left" width="54px;">
		                	<button type="submit" id="orderByLog" name="orderByLog" class="buttonTable" >
								<spring:message code="fenodes.nodeLogs.label.logLevel" />
								<c:if test="${feNodeLogFilter.orderBy=='log'}">
									<c:if test="${feNodeLogFilter.orderType=='desc'}">
									
										<img src="/PeopleConsole/images/sort-desc.png" border="0" 
											title='<spring:message code="fenodes.nodeLogs.label.logLevel" />'  
											alt='<spring:message code="fenodes.nodeLogs.label.logLevel" />' 
									  	>     
								   	</c:if>
								   	<c:if test="${feNodeLogFilter.orderType=='asc'}">
								   	  	<img src="/PeopleConsole/images/sort-asc.png" border="0" 
											title='<spring:message code="fenodes.nodeLogs.label.logLevel" />'  
											alt='<spring:message code="fenodes.nodeLogs.label.logLevel" />' 
									  	>    
								   	</c:if>
								</c:if>
							</button> 
		                </th>
		                
		               	<th align="left">
							<button type="submit" id="orderByService" name="orderByService" class="buttonTable">
								<spring:message code="fenodes.nodeLogs.label.service" />
								<c:if test="${feNodeLogFilter.orderBy=='service'}">
									<c:if test="${feNodeLogFilter.orderType=='desc'}">
										<img src="/PeopleConsole/images/sort-desc.png" border="0" 
											title='<spring:message code="fenodes.nodeLogs.label.service" />'  
											alt='<spring:message code="fenodes.nodeLogs.label.service" />' 
									  	>     
								   	</c:if>
								   	<c:if test="${feNodeLogFilter.orderType=='asc'}">
								   	  	<img src="/PeopleConsole/images/sort-asc.png" border="0" 
											title='<spring:message code="fenodes.nodeLogs.label.service" />'  
											alt='<spring:message code="fenodes.nodeLogs.label.service" />' 
									  	>    
								   	</c:if>
								</c:if>
							</button> 
						</th> 
		                
		               	<th align="left">
							<button type="submit" id="orderByMessage" name="orderByMessage" class="buttonTable">
								<spring:message code="fenodes.nodeLogs.label.message" />
								<c:if test="${feNodeLogFilter.orderBy=='message'}">
									<c:if test="${feNodeLogFilter.orderType=='desc'}">
										<img src="/PeopleConsole/images/sort-desc.png" border="0" 
											title='<spring:message code="fenodes.nodeLogs.label.message" />'  
											alt='<spring:message code="fenodes.nodeLogs.label.message" />' 
									  	>     
								   	</c:if>
								   	<c:if test="${feNodeLogFilter.orderType=='asc'}">
								   	  	<img src="/PeopleConsole/images/sort-asc.png" border="0" 
											title='<spring:message code="fenodes.nodeLogs.label.message" />'  
											alt='<spring:message code="fenodes.nodeLogs.label.message" />' 
									  	>    
								   	</c:if>
								</c:if>
							</button> 
						</th> 

		            </tr>    
					<c:forEach var="log" items="${logBeans}" varStatus="rowCounter">
					  <c:choose>
					    <c:when test="${rowCounter.count % 2 == 0}">
					      <c:set var="rowStyle" scope="page" value="logOddRow"/>
					    </c:when>
					    <c:otherwise>
					      <c:set var="rowStyle" scope="page" value="logEvenRow"/>
					    </c:otherwise>
					  </c:choose>
					  <tr class="${rowStyle}">
					  	<td><fmt:formatDate value="${log.date.time}" pattern="dd/MM/yyyy"/></td>
						<td align="center">
							${log.logLevel}</td>
						<td>${log.servizio}</td>
						<td>${log.messaggio}</td>
					  </tr>
					</c:forEach>
			    </table>
		    </div>
		    </c:if>
		     

		</form:form>
	</body>
</html>
