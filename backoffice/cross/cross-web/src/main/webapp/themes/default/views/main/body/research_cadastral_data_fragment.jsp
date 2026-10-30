<%
String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<span id="CatastoField">
	<div class="row">
		<div class="width15" id="divSearch_tipoUnita">
		   <label for="search_tipoUnita"><spring:message code="ricerca.tipoUnita"/></label>
		   <select id="search_tipoUnita" name="search_tipoUnita" class="select textInput">
		        <option  value="" ><spring:message code="ricerca.inizio.tipoCatasto"/></option>
		        <c:forEach items="${lkTipiUnita}" var="tipoUnita" begin="0">
			         <c:if test="${1 == tipoUnita.idTipoUnita || 2 == tipoUnita.idTipoUnita}"><c:set var="selected" value=""/> 
				         <c:if test="${tipoUnita.idTipoUnita != null &&  cadastralData.unitTypeId == tipoUnita.idTipoUnita}">
				             <c:set var="selected" value="selected=\"selected\""/>     
				         </c:if>
			         	<option value="${tipoUnita.idTipoUnita}" ${selected}>${tipoUnita.descrizione}</option>
		         	</c:if>
		        </c:forEach>
		    </select>
		    <p class="formHint"></p>
		</div> 
		
		<div class="width5" id="divSearch_sezione">
		    <label for="search_sezione"><spring:message code="ricerca.sezione"/></label>
		    <input id="search_sezione" name="search_sezione" size="35" maxlength="30" type="text" class="textInput" value="${cadastralData.section}">
		    <p class="formHint"></p>
		</div>
		                              
		<div class="width5" id="divSearch_foglio">
		    <label for="search_foglio"><spring:message code="ricerca.foglio"/></label>
		    <input id="search_foglio" name="search_foglio" size="35" maxlength="30" type="text" class="textInput" value="${cadastralData.sheet}">
		    <p class="formHint"></p>
		</div>
		
		<div class="width10" id="divSearch_mappale">
		    <label for="search_mappale"><spring:message code="ricerca.mappale"/></label>
		    <input id="search_mappale" name="search_mappale" size="35" maxlength="30" type="text" class="textInput" value="${cadastralData.map}">
		    <p class="formHint"></p>
		</div>
		                           
		<div class="width20" id="divSearch_subalterno">
		    <label for="search_subalterno"><spring:message code="ricerca.subalterno"/></label>
		    <input id="search_subalterno" name="search_subalterno" size="35" maxlength="30" type="text" class="textInput" value="${cadastralData.subordinate}">
			<input size="35" maxlength="35" type="hidden" class="textInput" />
		</div>
	
		<div class="width5">
			<span id="delete_cadastral_data_btn" idx="${dataLoop.index+1}">
				<img src="/cross/themes/default/images/icons/reject.png" />
			</span>
			<input size="35" maxlength="35" type="hidden" class="textInput" >
			<span id="add_cadastral_data_btn" class="${dataLoop != null && !dataLoop.last ? 'hidden': ''}" idx="${dataLoop.index+1}" >
	            <img src="/cross/themes/default/images/icons/add.png" />
	        </span>
	    </div>  
	    <p class="formHint"></p>
    </div>
</span>