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
		<title><spring:message code="auditStatistiche.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="auditStatisticheFilter">
			<c:choose>

			<c:when test="${empty statistichePerNodo}">
				<div class="panel">
					<div class="panelTitle panelSubTitle">
						<spring:message code="message.statistics.empty" />
					</div>	
				</div>	
			</c:when>
			<c:otherwise>
				 
				<fieldset>
				<div class="panel">
			       	
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
<!-- 
					<div class="panelRowUsers">
				        <label>
			            	<spring:message code="nodeAuditConversations.searchByDate" />
			            </label>

						<div id="dr"></div>
					</div>
 -->
					<form:errors path="error" cssClass="error"/>
		            <form:input type="hidden" path="initialLetter" name="initialLetter" id="initialLetter" />
		            
				</div>

				<c:if test="${!empty statistichePerNodo}">
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
					<input type="submit" id="view" name="view" value="Visualizza" class="button" alt="Visualizza" title="Visualizza"/>
					<input type="submit" id="cancel" name="cancel" value="< Indietro" class="button" alt="Torna alla pagina precedente" title="Torna alla pagina precedente" />
				</div>
				</fieldset>
			
				<div class="panel">
					
					<c:if test="${!empty statistichePerNodo}">
						<div class="statNavigation">
							<c:forEach var="letter" items="${alphabet}" >
								<c:choose>
									<c:when test="${auditStatisticheFilter.initialLetter == letter.key}">
										<input type="submit" id=letter_${letter.key} 
										name=selectedLetter  class="buttonStatSelected" value=${letter.key} />
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${letter.value}">
												<input type="submit" id=letter_${letter.key} 
												name=selectedLetter class="buttonStat" value=${letter.key} alt=${letter.key} title=${letter.key} />
											</c:when>
											<c:otherwise>
												<input type="submit" id=letter_${letter.key} 
												name=selectedLetter class="buttonStatDisabled" value=${letter.key} disabled="disabled"/>
											</c:otherwise>
										</c:choose>
										
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</div>
						
					    	<c:forEach var="nodo" items="${statistichePerNodo}" varStatus="rowCounter">
	
								<div class="statNode">
								
									<h1>
										&nbsp;${nodo.nomeNodo} 
									</h1>
 										<c:if test="${!empty nodo.statistiche}">
										
											
											<!-- Accessi Per Servizio -->
											<c:forEach var="statPerNodo" items="${nodo.statistiche}" varStatus="rowCounter">
											 <c:if test="${statPerNodo.key == 'accessiPerServizio' }" > 
											 
												<c:if test="${!empty statPerNodo.value.risultati}">
												<div class="statNodeService">
												<ul><li>
													<div class=" panelSubTitleStat"><spring:message code="${statPerNodo.value.nomeStatistica}" /></div>
													<div class="tablestat" >
												    <table class="log stat" summary="<spring:message code="${statPerNodo.value.nomeStatistica}"/>per il nodo ${nodo.nomeNodo}"  title="<spring:message code="${statPerNodo.value.nomeStatistica}"/>per il nodo ${nodo.nomeNodo}">
												    	    	
													    <tr class=logLabelRow>
											                <th scope="col"><spring:message code="auditStatistiche.label.servizio" /></th>
											                <th scope="col" class="right"><spring:message code="auditStatistiche.label.accessi" /></th>
											            </tr>    
														<c:forEach var="risultati" items="${statPerNodo.value.risultati}" varStatus="rowCounter">
															<c:choose>
															    <c:when test="${rowCounter.count % 2 == 0}">
															      <c:set var="rowStyle" scope="page" value="logOddRow"/>
															    </c:when>
															    <c:otherwise>
															      <c:set var="rowStyle" scope="page" value="logEvenRow"/>
															    </c:otherwise>
															</c:choose>
							  								<tr class="${rowStyle}">	
																<td>${risultati.nomeServizio}</td>
																<td align="right">${risultati.risultati}</td>
															</tr>
														</c:forEach>
													</table>
													</div>
												</li></ul>
												</div>
												</c:if>
											 </c:if>
											</c:forEach>
											<!--------->

											<!-- Servizi Top Ten -->
											<c:forEach var="statPerNodo" items="${nodo.statistiche}" varStatus="rowCounter">
											 <c:if test="${statPerNodo.key == 'serviziTopTen' }" > 
											
												<c:if test="${!empty statPerNodo.value.risultati}">
												<div class="statNodeService">												
												<ul><li>
													<div class=" panelSubTitleStat"><spring:message code="${statPerNodo.value.nomeStatistica}" /></div>
													<div class="tablestat" >
												    <table class="log stat" summary="<spring:message code="${statPerNodo.value.nomeStatistica}"/>per il nodo ${nodo.nomeNodo}"  title="<spring:message code="${statPerNodo.value.nomeStatistica}"/>per il nodo ${nodo.nomeNodo}">
												    	    	
													    <tr class=logLabelRow>
											                <th scope="col"><spring:message code="auditStatistiche.label.servizio" /></th>
											                <th scope="col" class="right"><spring:message code="auditStatistiche.label.accessi" /></th>
											            </tr>    
														<c:forEach var="risultati" items="${statPerNodo.value.risultati}" varStatus="rowCounter">
															<c:choose>
															    <c:when test="${rowCounter.count % 2 == 0}">
															      <c:set var="rowStyle" scope="page" value="logOddRow"/>
															    </c:when>
															    <c:otherwise>
															      <c:set var="rowStyle" scope="page" value="logEvenRow"/>
															    </c:otherwise>
															</c:choose>
							  								<tr class="${rowStyle}">	
																<td>${risultati.nomeServizio}</td>
																<td align="right">${risultati.risultati}</td>
															</tr>
														</c:forEach>
													</table>
													</div>
												</li></ul>
												</div>
												</c:if>
											 </c:if>
											</c:forEach>
											<!--------->
											
											<!-- Abbandono servizi -->
											<c:forEach var="statPerNodo" items="${nodo.statistiche}" varStatus="rowCounter">
											 <c:if test="${statPerNodo.key == 'abbandonoServizi' }" > 
											 
												<c:if test="${!empty statPerNodo.value.risultati}">
												<div class="statNodeService">												
												<ul><li>
													<div class=" panelSubTitleStat"><spring:message code="${statPerNodo.value.nomeStatistica}" /></div>
													<div class="tablestat" >
												    <table class="log stat" summary="<spring:message code="${statPerNodo.value.nomeStatistica}"/>per il nodo ${nodo.nomeNodo}"  title="<spring:message code="${statPerNodo.value.nomeStatistica}"/>per il nodo ${nodo.nomeNodo}">
												    	    	
													    <tr class=logLabelRow>
											                <th scope="col"><spring:message code="auditStatistiche.label.servizio" /></th>
											                <th scope="col" class="right"><spring:message code="auditStatistiche.label.abbandoni" /></th>
											            </tr>    
														<c:forEach var="risultati" items="${statPerNodo.value.risultati}" varStatus="rowCounter">
															<c:choose>
															    <c:when test="${rowCounter.count % 2 == 0}">
															      <c:set var="rowStyle" scope="page" value="logOddRow"/>
															    </c:when>
															    <c:otherwise>
															      <c:set var="rowStyle" scope="page" value="logEvenRow"/>
															    </c:otherwise>
															</c:choose>
							  								<tr class="${rowStyle}">	
																<td>${risultati.nomeServizio}</td> 
																<td align="right">${risultati.risultati} %</td>
															</tr>
														</c:forEach>
													</table>
													</div>
												</li></ul>
												</div>
												</c:if>
											 </c:if> 
											</c:forEach>
											<!--------->
											
											
										</c:if>
								</div>		
											
							</c:forEach>
					
					</c:if>
					
				</div>
			
			</c:otherwise>
			</c:choose>
			
		  	<div class="buttonsbar">
				<input type="submit" id="esci" name="cancel" value="< Indietro" class="button" alt="Torna alla pagina precedente" title="Torna alla pagina precedente"/>
			</div>
		</form:form>
	</body>
</html>
