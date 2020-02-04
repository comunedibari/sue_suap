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
<title><spring:message code="accreditamentiQualifiche.title" />
</title>
</head>
<body>
	<form:form modelAttribute="qualifica">
		<div>
			<fieldset>
				<form:errors path="error" cssClass="error" />
				<div class="panel">
					<div class="panelRowTaxCode">
						<label for="id_qualifica"> <spring:message code="accreditamentiQualifiche.label.id_qualifica" /> </label>
						<form:label path="id_qualifica">${qualifica.id_qualifica}</form:label>
						<form:hidden path="id_qualifica"/>
					</div>
					<div class="panelrowclearer" />
					<div class="panelRow">
						<pcform:label required="*" for="descrizione"> <spring:message code="accreditamentiQualifiche.label.descrizione" /> </pcform:label>
						<form:input id="descrizione" path="descrizione" />
						<form:errors path="descrizione" cssClass="error" />
					</div>
					<div class="panelRow">
						<label class="label" for="tipo_qualifica"> <spring:message code="accreditamentiQualifiche.label.tipo_qualifica" /> </label>
						<form:select path="tipo_qualifica" id="tipo_qualifica" onchange="javascript:submit();">
							<form:options items="${tipiQualifica}" itemLabel="label" itemValue="value" />
						</form:select>
						<noscript>
							<input type="submit" id="aggiornaQualifica" name="aggiornaQualifica" value="Aggiorna" class="button" />
						</noscript>
					</div>
					<c:if test="${titolareVisible}"> 
						<div class="panelRow">
							<label class="label" for="titolare"> <spring:message code="accreditamentiQualifiche.label.titolare" /> </label>
							<form:select path="titolare" id="titolare">
								<form:options items="${tipiTitolare}" itemLabel="label" itemValue="value" />
							</form:select>
						</div>
					</c:if>
					<div class="panelRow">
						<label class="label" for="auto_certificabile"> <spring:message code="accreditamentiQualifiche.label.auto_certificabile" /> </label>
						<form:checkbox id="auto_certificabile" path="auto_certificabile" />
						<form:errors path="auto_certificabile" cssClass="error"/>
					</div>
				</div>
				
				
				<div class="buttonsbar">
					<input type="submit" id="updateQualifica" name="updateQualifica" value="Aggiorna" class="button" />
					<input type="submit" id="esci" name="cancel" value="< Indietro" class="button" alt="Torna alla pagina precedente" title="Torna alla pagina precedente" />
				</div>
				
			</fieldset>
		</div>
		
		
		
	</form:form>
</body>
</html>
