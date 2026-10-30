<%
    String path = request.getContextPath();
    String url = path + "/pratiche/nuove/assegna.htm";
    String download = path + "/download/pratica.htm";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<div id="accettazione_pratiche">
    <tiles:insertAttribute name="ricerca" />
    <c:set var="columnForSorting" value="idPratica" scope="request" />
    <tiles:insertAttribute name="research_table" />
    <tiles:insertAttribute name="research_footer" />
    <script type="text/javascript">
	    function gestionePraticheActionsFormatter(cellvalue, options, rowObject) {
            var link = '<div id="button">'+
						'<div class="centra_impostazzioni_bottoni">'+
            			'<form action="<%=url%>" method="post" style="float:left">'+
            			'<input type="hidden" name="id_pratica" value="' + rowObject["idPratica"] + '">'+
            			'<button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"  title="<spring:message code="pratica.button.assegna"/>">'+
            			'<div class="bottone_assegna"></div> '+
            			'</button></form>'+
            			'<form action="<%=download%>" method="post" style="float:left" target="_blank">'+
            			'<input type="hidden" name="id_pratica" value="' + rowObject["idPratica"] + '">'+
            			'<button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" title="<spring:message code="pratica.button.assegna.download"/>">'+
            			'<div class="scarica5">'+
            			'</div>'+
            			'</button>'+
            			'</form>'+
            			'</div>'+
            			'</div>';
            return link;
        }
	</script>
</div>