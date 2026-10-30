<%
String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<span id="richiedentiBeneficiario">
	<div class="row">
		<div class="width20">
	       <label>
	           <spring:message code="ricerca.cognome"/>
	       </label>
	       <input id="search_ricercaAnagraficaCognome" name="search_ricercaAnagraficaCognome" size="35" maxlength="50" type="text" class="textInput" value="${identification.surname}"/>
	       <p class="formHint"></p>
	   </div>
	   <div class="width20">
	       <label>
	           <spring:message code="ricerca.nome"/>
	       </label>
	       <input id="search_ricercaAnagraficaNome" name="search_ricercaAnagraficaNome" size="35" maxlength="50" type="text" class="textInput" value="${identification.name}"/>
	       <p class="formHint"></p>
	   </div>
	   <div class="width20">
	       <label>
	           <spring:message code="ricerca.ragioneSociale"/>
	       </label>
	       <input id="search_ricercaAnagraficaRagioneSociale" name="search_ricercaAnagraficaRagioneSociale" size="35" maxlength="50" type="text" class="textInput"  value="${identification.companyName}"/>
	       <p class="formHint"></p>
	   </div>
		<div class="width20">
			<label for="search_ricercaAnagraficaCF"><spring:message code="ricerca.cfPartitaIva"/></label>
			<input id="search_ricercaAnagraficaCF" name="search_ricercaAnagraficaCF" size="35" maxlength="50" type="text"  class="textInput width100" value="${identification.code}" />
		</div>
	
		<div class="width5">
			<span id="delete_anagrafica_btn" idx="${anagraficaLoop.index+1}">
				<img src="/cross/themes/default/images/icons/reject.png" />
			</span>
			<input size="35" maxlength="35" type="hidden" class="textInput" >
			<span id="add_anagrafica_btn" class="${anagraficaLoop != null && !anagraficaLoop.last ? 'hidden': ''}" idx="${anagraficaLoop.index+1}" >
	            <img src="/cross/themes/default/images/icons/add.png" />
	        </span>
	    </div>  
    </div>
    <p class="formHint"></p>
</span>