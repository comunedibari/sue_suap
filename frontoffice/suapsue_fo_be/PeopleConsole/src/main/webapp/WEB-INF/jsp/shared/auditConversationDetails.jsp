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
<%@ taglib prefix="pcfn" uri="/WEB-INF/peopleconsole-fn.tld" %>
<html>
	<head>
		<title><spring:message code="auditConversationDetails.title" /></title>
	</head>
	<body>
	
	<form:form modelAttribute="auditDetail">
	
	<fieldset>
		<div class="panel">
				
			<c:choose> 
				<c:when test="${auditDetail.anon_user == true}" > 
					<div class="panelTitle">
						<spring:message code="auditConversationDetails.anon_user" />
				  	</div>
				</c:when> 
			
				<c:otherwise> 
					<div class="panelTitle">
						 <spring:message code="auditConversationDetails.np_user" />
							<c:if test="${empty auditDetail.richiedente}">, &nbsp;
								<spring:message code="auditConversationDetails.richiedente.title" />
							</c:if>
							<c:if test="${empty auditDetail.titolare}">, &nbsp;
								<spring:message code="auditConversationDetails.titolare.title" />
							</c:if>
					</div>
					<div class="narrow">
			            <label class="label" for="np_first_name">
			            	<spring:message code="auditConversationDetails.np.firstName" />
			            </label>
						<div id="np_first_name" class="textBlack">${auditDetail.np_first_name}&nbsp;</div>
					</div>
					<div class="narrow">
			            <label class="label" for="np_last_name">
			            	<spring:message code="auditConversationDetails.np.lastName" />
			            </label>
						<div id="np_last_name" class="textBlack">${auditDetail.np_last_name}&nbsp;</div>
					</div>
					<div class="narrow">
			            <label class="label" for="np_tax_code">
			            	<spring:message code="auditConversationDetails.np.taxcode" />
			            </label>
						<div id="np_tax_code" class="textBlack">${auditDetail.np_tax_code}&nbsp;</div>
					</div>
					<div class="narrow">
			            <label class="label" for="np_address">
			            	<spring:message code="auditConversationDetails.np.address" />
			            </label>
						<div id="np_address" class="textBlack">${auditDetail.np_address}&nbsp;</div>
					</div>
					<div class="narrow">
			            <label class="label" for="np_e_address">
			            	<spring:message code="auditConversationDetails.np.e_address" />
			            </label>
						<div id="np_e_address" class="textBlack">${auditDetail.np_e_address}&nbsp;</div>
					</div>
				
				</c:otherwise> 
			</c:choose>
			
			<c:if test="${not empty auditDetail.tipo_qualifica}">
				<div class="narrow">
					<label class="label" for="tipo_qualifica">
				    	<spring:message code="auditConversationDetails.tipo_qualifica" />
		            </label>
					<div id="tipo_qualifica" class="textBlack">${auditDetail.tipo_qualifica}&nbsp;</div>
				</div>
				<div class="narrow">
		            <label class="label" for="descr_qualifica">
		            	<spring:message code="auditConversationDetails.descr_qualifica" />
		            </label>
					<div id="descr_qualifica" class="textBlack">${auditDetail.descr_qualifica}&nbsp;</div>
				</div>
			</c:if>
			
			<div></div>
			<div></div>
			
			<!-- se utente anonimo non mostro richiedente e titolare -->
			<c:if test="${auditDetail.anon_user == false}" > 
			
				<c:if test="${not empty auditDetail.richiedente}">
					<div class="panelrowclearer"></div>
					<div class="panelTitle">
						<spring:message code="auditConversationDetails.richiedente.title" />
					</div>
					<c:choose>
						<c:when test="${auditDetail.richiedente_utente == true}" > 
							<div class="narrow">
								<label class="label" for="richiedente">
						    		<spring:message code="auditConversationDetails.richiedente" />
				            	</label>
								<div id="richiedente">${auditDetail.richiedente}</div>
							</div>
						</c:when> 
						<c:otherwise>
							<div class="narrow">
								<label class="label" for="richiedente.firstName">
						    		<spring:message code="auditConversationDetails.np.firstName" />
				            	</label>
								<div id="richiedente.firstName" class="textBlack">${auditDetail.richiedente_first_name}&nbsp;</div>
							</div>
							<div class="narrow">
								<label class="label" for="richiedente.lastName">
						    		<spring:message code="auditConversationDetails.np.lastName" />
				            	</label>
								<div id="richiedente.lastName" class="textBlack">${auditDetail.richiedente_last_name}&nbsp;</div>
							</div>
							<div class="narrow">
								<label class="label" for="richiedente.taxcode">
						    		<spring:message code="auditConversationDetails.np.taxcode" />
				            	</label>
								<div id="richiedente.taxcode" class="textBlack">${auditDetail.richiedente_tax_code}&nbsp;</div>
							</div>
							<div class="narrow">
								<label class="label" for="richiedente.address">
						    		<spring:message code="auditConversationDetails.np.address" />
				            	</label>
								<div id="richiedente.address" class="textBlack">${auditDetail.richiedente_address}&nbsp;</div>
							</div>
							<div class="narrow">
								<label class="label" for="richiedente.e_address">
						    		<spring:message code="auditConversationDetails.np.e_address" />
				            	</label>
								<div id="richiedente.e_address" class="textBlack">${auditDetail.richiedente_e_address}&nbsp;</div>
							</div>
						
						</c:otherwise> 
					</c:choose>
				</c:if>
				
				<div></div>
				<div></div>				
				<div></div>
				<div></div>
				
				<c:if test="${not empty auditDetail.titolare}">
					<div class="panelrowclearer"></div>
					<div class="panelTitle">
						<spring:message code="auditConversationDetails.titolare.title" />
					</div>
					<!-- Titolare Persona Fisica -->
					<c:if test="${auditDetail.titolare_pf == true}">
						<c:choose>
							<c:when test="${auditDetail.titolare_utente == true}" > 
								<div class="narrow">
									<label class="label" for="titolare">
							    		<spring:message code="auditConversationDetails.titolare" />
					            	</label>
									<div id="titolare">${auditDetail.titolare}</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="narrow">
									<label class="label" for="titolare.firstName">
							    		<spring:message code="auditConversationDetails.np.firstName" />
					            	</label>
									<div id="titolare.firstName" class="textBlack">${auditDetail.titolare_first_name}&nbsp;</div>
								</div>
								<div class="narrow">
									<label class="label" for="titolare.lastName">
							    		<spring:message code="auditConversationDetails.np.lastName" />
					            	</label>
									<div id="titolare.lastName" class="textBlack">${auditDetail.titolare_last_name}&nbsp;</div>
								</div>
								<div class="narrow">
									<label class="label" for="titolare.taxcode">
							    		<spring:message code="auditConversationDetails.np.taxcode" />
					            	</label>
									<div id="titolare.taxcode" class="textBlack">${auditDetail.titolare_tax_code}&nbsp;</div>
								</div>
								<div class="narrow">
									<label class="label" for="titolare.address">
							    		<spring:message code="auditConversationDetails.np.address" />
					            	</label>
									<div id="titolare.address" class="textBlack">${auditDetail.titolare_address}&nbsp;</div>
								</div>
								<div class="narrow">
									<label class="label" for="titolare.e_address">
							    		<spring:message code="auditConversationDetails.np.e_address" />
					            	</label>
									<div id="titolare.e_address" class="textBlack">${auditDetail.titolare_e_address}&nbsp;</div>
								</div>
							
							</c:otherwise> 
						</c:choose> 
		
					</c:if>
					<!-- Titolare Persona Giuridica -->
					<c:if test="${auditDetail.titolare_pf == false}">
						<div class="narrow">
							<label class="label" for="titolare.pg.businessName">
					    		<spring:message code="auditConversationDetails.pg.business.name" />
			            	</label>
							<div id="titolare.pg.businessName" class="textBlack">${auditDetail.titolare_pg_business_name}&nbsp;</div>
						</div>
						<div class="narrow">
							<label class="label" for="titolare.pg.address">
					    		<spring:message code="auditConversationDetails.pg.address" />
			            	</label>
							<div id="titolare.pg.address" class="textBlack">${auditDetail.titolare_pg_address}&nbsp;</div>
						</div>
						<div class="narrow">
							<label class="label" for="titolare.pg.taxcode">
					    		<spring:message code="auditConversationDetails.pg.taxcode" />
			            	</label>
							<div id="titolare.pg.taxcode" class="textBlack">${auditDetail.titolare_pg_tax_code}&nbsp;</div>
						</div>
						<div class="narrow">
							<label class="label" for="titolare.pg.vatnumber">
					    		<spring:message code="auditConversationDetails.pg.vatnumber" />
			            	</label>
							<div id="titolare.pg.vatnumber" class="textBlack">${auditDetail.titolare_pg_vat_number}&nbsp;</div>
						</div>
						<!-- Rappr. Legale -->
						<c:choose>
							<c:when test="${auditDetail.rapprLegale_utente == true}" > 
								<div class="panelrowclearer"></div>
								<div class="narrow">
									<label class="label" for="rapprLegale">
							    		<spring:message code="auditConversationDetails.rapprLegale.title" />
					            	</label>
									<div id="rapprLegale" class="textBlack">${auditDetail.rapprLegale}&nbsp;</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="panelrowclearer"></div>
								<div class="panelTitle">
									<spring:message code="auditConversationDetails.rapprLegale.title" />
								</div>
								<div class="narrow">
									<label class="label" for="rapprLegale.firstName">
							    		<spring:message code="auditConversationDetails.np.firstName" />
					            	</label>
									<div id="rapprLegale.firstName" class="textBlack">${auditDetail.titolare_first_name}&nbsp;</div>
								</div>
								<div class="narrow">
									<label class="label" for="rapprLegale.lastName">
							    		<spring:message code="auditConversationDetails.np.lastName" />
					            	</label>
									<div id="rapprLegale.lastName" class="textBlack">${auditDetail.titolare_last_name}&nbsp;</div>
								</div>
								<div class="narrow">
									<label class="label" for="rapprLegale.taxcode">
							    		<spring:message code="auditConversationDetails.np.taxcode" />
					            	</label>
									<div id="rapprLegale.taxcode" class="textBlack">${auditDetail.titolare_tax_code}&nbsp;</div>
								</div>
								<div class="narrow">
									<label class="label" for="rapprLegale.address">
							    		<spring:message code="auditConversationDetails.np.address" />
					            	</label>
									<div id="rapprLegale.address" class="textBlack">${auditDetail.titolare_address}&nbsp;</div>
								</div>
								<div class="narrow">
									<label class="label" for="rapprLegale.e_address">
							    		<spring:message code="auditConversationDetails.np.e_address" />
					            	</label>
									<div id="rapprLegale.e_address" class="textBlack">${auditDetail.titolare_e_address}&nbsp;</div>
								</div>
							</c:otherwise> 
						</c:choose> 
						
					</c:if>
				</c:if>
			
			</c:if><!-- fine controllo utente anonimo -->
			
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			
			<div class="panelrowclearer"></div>
			
			<div class="panelTitle">
				<spring:message code="auditConversationDetails.c.title" />
			</div>
			
			<div class="narrow">
	            <label class="label" for="c_action">
	            	<spring:message code="auditConversationDetails.c.action" />
	            </label>
				<div id="c_action" class="textBlack">${auditDetail.c_action}&nbsp;</div>
			</div>
			<div class="narrow">
	            <label class="label" for="c_timestamp_date">
	            	<spring:message code="auditConversationDetails.c.datetime" />
	            </label>
				<div id="c_timestamp_date" class="textBlack"><fmt:formatDate value="${auditDetail.c_timestamp_date}"  pattern="dd/MM/yyyy HH:mm:ss" />&nbsp;</div>
			</div>
			<div class="narrow">
	            <label class="label" for="c_message">
	            	<spring:message code="auditConversationDetails.c.message" />
	            </label>
				<div id="c_message" class="textBlack">${auditDetail.c_message}&nbsp;</div>
			</div>
			

			
			<div class="buttonsbar">
				<input type="submit" id="esci" name="cancel" value="< Indietro" class="button"  alt="Torna alla pagina precedente"  title="Torna alla pagina precedente" />
			</div>

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
		
		<c:if test="${auditDetail.c_includexml != 0}" > 
			<div class="panel">

				<c:if test="${AuditKeyProvided}">
				
					<div class="clearer"></div>
					
					<!-- chiave già fornita -->
					<div class="narrow">
			            <label class="label" for="xmlIn">
			            	<spring:message code="auditConversationDetails.xmlIn" />
			            </label>
						<textarea class=narrow readonly="readonly" rows="8">${auditDetail.xmlIn}</textarea>
					</div>
					
					<div class="narrow">
			            <label class="label" for="xmlOut">
			            	<spring:message code="auditConversationDetails.xmlOut" />
			            </label>
						<textarea class=narrow readonly="readonly" rows="8">${auditDetail.xmlOut}</textarea>
					</div>
					
				</c:if>
			
			</div>
		</c:if>
	</fieldset>
	
	
	</form:form>
	
	
	<c:if test="${auditDetail.c_includexml != 0}" >

			<c:if test="${AuditKeyProvided==null}">
			
				<!-- chiave da fornire -->
				<div class="panel">
					
					<div class="clearer"></div>

					<label>Per visualizzare lo scambio xml è necessario fornire un certificato valido </label>
					<div>&nbsp;</div>
					
					<form:form modelAttribute="p12UploadItem" method="post" enctype="multipart/form-data">
								
						<div class="panelRow">
							<spring:hasBindErrors name="p12UploadItem">
								<c:forEach var="error" items="${errors.allErrors}">
									<li class="error">
										<spring:message code="${error.code}" text="${error.defaultMessage}"/>
									</li>
								</c:forEach>
							</spring:hasBindErrors>
						</div>
						
						<fieldset class="p12">
						
			 				<div class="panelRow">
			                    <form:label for="fileData" path="fileData">Certificato</form:label>
			                    <form:input path="fileData" type="file" alt="Sfoglia.." title="Sfoglia.."/>
			 				</div>
							<div class="panelRow">
			 					<form:label for="password" path="password">Password</form:label>
			                    <form:password path="password"/>
			 				</div>
			 				<div class=buttonsbar>
			                    <input type="submit" id="certificate" name="certificate" value="Invia certificato" class="button" 
			                    	alt="Invia certificato" title="Invia certificato"/>
			 				</div>
			            
						</fieldset>
						
					</form:form>

				</div>

			</c:if>
			
	</c:if>
		
	
	</body>
</html>
