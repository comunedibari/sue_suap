<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<tiles:insertAttribute name="body_error" />

<h2 style="text-align: center"><spring:message code="utenti.title.modificaPassword"/></h2> 


 <form:form action="${path}/utenti/modificaPassword.htm" class="uniForm inlineLabels" method="post" modelAttribute="utente">
 	  
 	  <div class="inlineLabels">
 	  
 	  
 	  	  <input type="hidden" name="action" value="modificaPassword">
 	  
	 	  <div class="ctrlHolder">
	            <label for="username" class="required"><spring:message code="utenti.username"/></label>
	            <input name="username" disabled="disabled" id="username" maxlength="255" type="text" class="textInput required" value="${username}">
	          
	        </div>
	        
	        <div class="ctrlHolder">
	            <label for="password" class="required"><spring:message code="utenti.password"/></label>
	            <input name="password" id="username" maxlength="255" type="password" class="textInput required">
	            <p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
	        </div>
	        <div class="ctrlHolder">
	            <label for="newPassword" class="required"><spring:message code="utenti.new.password"/></label>
	            <input name="newPassword" id="newPassword" maxlength="255" type="password" class="textInput required">
	            <p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
	        </div>
	        <div class="ctrlHolder">
	            <label for="confirmNewPassword" class="required"><spring:message code="utenti.confirm.new.password"/></label>
	            <input name="confirmNewPassword" id="confirmNewPassword" maxlength="255" type="password" class="textInput required">
	            <p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
	        </div>
	 		<div class="buttonHolder" align="left">
<%-- 	            <a href="<%=path%>/ente/index.htm" class="secondaryAction">&larr; Indietro</a> --%>
	            <button type="submit" class="primaryAction"><spring:message code="utenti.modifica.password"/></button>
	        </div>
       
       </div>
 
  
 
 </form:form>