
<%
String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="conto_anagrafiche" value="0" scope="request"/>
<c:if test="${anagraficheList.size() gt 0}">
	<div class=" imbox_frame margine_imbox_frame anagraficabox">
		<c:if test="${anagraficheList.size() gt 1}">
	        <div class="controllo_sinistra">
	            <div class="controller_box">
	                <div class="box_left"  onclick="item_animate${provenienza_idx}('<c:out value="${anagraficheList.size()}"></c:out>', '#anagrafica_box_${provenienza_idx}', '-=277px');"></div>
	            </div>
	        </div>
		</c:if>
		<div id="anagrafica_box_${provenienza_idx}">
			<div id="anagrafica_box">
				<table cellpadding="0" cellspacing="0">
					<tr>                
						<c:forEach items="${anagraficheList}" begin="0" var="anagrafica" varStatus="status">
							<c:set var="anagrafica" value="${anagrafica}" scope="request"/>
							<c:set var="status" value="${status}" scope="request"/>
							<c:set var="provenienza_idx" value="${provenienza_idx}" scope="request"/>
							<c:import url="/themes/default/views/main/body/pratiche/dettaglio/anagrafica_dettaglio.jsp"/>
							<c:set var="conto_anagrafiche" value="${conto_anagrafiche + 1}" scope="request"/>
						</c:forEach>
					</tr>
				</table>
			</div>
		</div>
		<c:if test="${anagraficheList.size() gt 1}">
	        <div class="controllo_destra">
	            <div class="controller_box">
	                <div class="box_right" onclick="item_animate${provenienza_idx}('<c:out value="${anagraficheList.size()}"></c:out>', '#anagrafica_box_${provenienza_idx}', '+=277px');"></div>
	                </div>
	            </div>
	    </c:if>
	</div>
<script type="text/javascript">

    function item_animate${provenienza_idx}(conto, id, px) {
        var massimo = (conto - 1) * 277;
        var ultimo = 0 - ((conto - 2) * 277);
        var minimo = 0 - ((conto - 1) * 277);
        var attuale = parseInt($(id).css("marginLeft"));
        if (attuale === minimo) {
            $(id).animate({'marginLeft': 0});
        }
        else {
            if (attuale === massimo) {
                $(id).animate({'marginLeft': ultimo});
            } else {
                $(id).animate({'marginLeft': px});
            }
        }
    }

</script> 
</c:if>