<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<tiles:insertAttribute name="body_error" />

<h2 style="text-align: center"><spring:message code="avvisi.creazione"/></h2> 

 <form:form id="avvisoForm" action="${path}/avvisi/creaAvviso.htm" class="uniForm inlineLabels" method="post" modelAttribute="avvisoDTO">
 	  
 	  <div class="inlineLabels">
 	  	
	 	  <input  name="action" type="hidden" value="${avvisoDTO.action}">
	 	  <input type="hidden" name="idAvviso" value="${avvisoDTO.idAvviso}">
	 	 
	 	  
	 	   <div class="ctrlHolder">
	            <label for="scadenza" class="required"><spring:message code="avvisi.scadenza"/></label>
	            <input name="stringScadenza"  id="stringScadenza"  type="text" class="textInput required dataPicker" value="${avvisoDTO.stringScadenza}"/> 
	        </div>
	         <div class="ctrlHolder">
	            <label for="testo" class="required"><spring:message code="avvisi.testo"/></label>
	             <textarea class="textarea_anagrafica_disable" name="testo" class="textInput required">${avvisoDTO.testo }</textarea>	          
	        </div>
	       <div class="buttonHolder" align="left">
	<%-- 	            <a href="<%=path%>/ente/index.htm" class="secondaryAction">&larr; Indietro</a> --%>
	            <button id="submit" type="submit" class="primaryAction"></button>
	        </div>
	  	
 	  
      </div>
</form:form>
 
 <script>
 var path="${path}";
 var action="${avvisoDTO.action}";
 
 if (action=="creaAvviso"){
	 $("#avvisoForm").attr('action', path+"/avvisi/creaAvviso.htm");
	 $("#submit").html("<spring:message code='avvisi.crea'/>"); 
 }else{
	 $("#avvisoForm").attr('action', path+"/avvisi/modificaAvviso.htm");
	 $("#submit").html("<spring:message code='avvisi.modifica'/>"); 
 }

 
 
 
 $(".dataPicker").datepicker({
     dateFormat: 'dd/mm/yy'
 });
 
 </script>