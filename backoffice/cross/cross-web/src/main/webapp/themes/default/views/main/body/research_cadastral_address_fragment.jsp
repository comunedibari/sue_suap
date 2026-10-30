<%
String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<span id="CatastoIndirizzo" >
	<div class="row">
	    <div class="width30" id="divSearch_indirizzo">
	        <label for="search_indirizzo"><spring:message code="ricerca.indirizzo"/></label>
	        <input id="search_indirizzo" name="search_indirizzo" size="35" maxlength="30" type="text" class="textInput" value="${cadastralAddress.streetName}" />
	    </div>
	    <div class="width20" id="divSearch_civico">
	        <label for="search_civico"><spring:message code="ricerca.civico"/></label>
	        <input id="search_civico" name="search_civico" size="35" maxlength="30" type="text" class="textInput" value="${cadastralAddress.streetNumber}" />
	    </div>
	     <div class="width5">
			<span id="delete_cadastral_address_btn" idx="${addressLoop.index+1}">
				<img src="/cross/themes/default/images/icons/reject.png" />
			</span>
			<input size="35" maxlength="35" type="hidden" class="textInput" >
			<span id="add_cadastral_address_btn" class="${addressLoop != null && !addressLoop.last ? 'hidden': ''}" idx="${addressLoop.index+1}" >
	            <img src="/cross/themes/default/images/icons/add.png" />
	        </span>
	    </div>  
	</div>
	<p class="formHint"></p>
</span>  