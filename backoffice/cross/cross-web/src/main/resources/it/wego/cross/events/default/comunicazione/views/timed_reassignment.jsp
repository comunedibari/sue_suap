<%
    String path = request.getContextPath();
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="it.wego.cross.constants.Constants" %>

<fieldset class="research-fieldset" > 
	<script type="text/javascript">

		$(document).ready(function(){
			if(codEvento != "RIASPRAT"){
				$("#flag_timed_active").change(function(){
					$("#notes,#days,#users").attr("disabled", !$(this).is(":checked"));
				})
				$("#notes,#days,#users").attr("disabled", !$(this).is(":checked"));
			} else{
				$("#search_flag_anagrafica_lbl").remove();
				$("#notes,#days,#users").attr("disabled", false);
			}
		})

	</script>
	
	<legend class="legend">
	   	<span>
		    <label id="search_flag_anagrafica_lbl">
	        	<input id="flag_timed_active" name="search_flag_anagrafica" type="checkbox" value="1" />
	        	<spring:message code="pratica.comunicazione.evento.attiva.riassegnazione.temporizzata" />
	        </label>
	        
	   </span> 
	</legend>
	<div id="timedReassignationData" class="row">
		<div class="width30">
			<label for="search_id_pratica">
                <spring:message code="evento.reassign.user"/>
            </label>
			<form:select path="utenteRiassegnazione.idUtente" id="users" disabled="true">
				
	           	<c:forEach items="${reassignmentData.users}" begin="0" var="userToReassign" >
					<c:set var="selected" value=""/> 
	                <c:if test="${utenteConnesso.idUtente == userToReassign.idUtente}">
	                    <c:set var="selected" value="true"/>     
	                </c:if>
	                <form:option value="${userToReassign.idUtente }" label="${userToReassign.cognome } ${userToReassign.nome }" selected="${selected}" />
	  			</c:forEach>
	 		</form:select>
		</div>
		<div class="width20">
			<label for="search_id_pratica">
                <spring:message code="evento.days"/>
            </label>
			<form:input path="giorniRiassegnazione" id="days" type="text" value="${reassignmentData.defaultDays }" disabled="true"/>
		</div>
	</div>
	<div class="row">
        <form:label path="contenuto" class="required"><spring:message code="pratica.comunicazione.evento.note"/></form:label>
        <form:textarea cols="25" rows="25" path="noteRiassegnazione" id="notes" style="width:99%;"/>
		
	</div>
</fieldset>