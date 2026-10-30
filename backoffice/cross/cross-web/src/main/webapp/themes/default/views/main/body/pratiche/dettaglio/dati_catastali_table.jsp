
<%
String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="dato_catastale_index" value="0" scope="request"/>
<c:if test="${datiCatastaliList.size() gt 0}">
	<div class=" imbox_frame margine_imbox_frame dato_catastalebox">
		<c:if test="${datiCatastaliList.size() gt 1}">
	        <div class="controllo_sinistra">
	            <div class="controller_box">
	                <div class="box_left"  onclick="item_animate('<c:out value="${datiCatastaliList.size()}"></c:out>', '#dato_catastale_box_${provenienza_idx}', '-=277px');"></div>
	            </div>
	        </div>
		</c:if>
		<div id="dato_catastale_box_${provenienza_idx}">
			<div id="dato_catastale_box">
				<table cellpadding="0" cellspacing="0">
					<tr class="DatiCatastali">                
						<c:forEach items="${datiCatastaliList}" begin="0" var="immobile" varStatus="loop">
							<c:set var="immobile" value="${immobile}" scope="request"/>
               			   <c:set var="loop" value="${loop}" scope="request"/>
							<c:set var="provenienza_idx" value="${provenienza_idx}" scope="request"/>
							<c:import url="/themes/default/views/main/body/pratiche/dettaglio/dati_catastali_dettaglio.jsp"/>
							<c:set var="dato_catastale_index" value="${dato_catastale_index + 1}" scope="request"/>
						</c:forEach>
					</tr>
				</table>
			</div>
		</div>
		<c:if test="${datiCatastaliList.size() gt 1}">
	        <div class="controllo_destra">
	            <div class="controller_box">
	                <div class="box_right" onclick="item_animate('<c:out value="${datiCatastaliList.size()}"></c:out>', '#dato_catastale_box_${provenienza_idx}', '+=277px');"></div>
	                </div>
	            </div>
	    </c:if>
	</div>
</c:if>