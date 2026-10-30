<%
    String path = request.getContextPath();
    String urlTrigger = path + "";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${utenteConnesso.idUtente == 1 && isProd && paperworkWithoutProtocol > 0}">
	<button id="triggerJobButton" type="button"	class="primaryAction" style="background-color: #0067A9"><spring:message code="run.automatic.registering" /></button>
	<script type="text/javascript">
		$( "#triggerJobButton" ).one( "click", function() {
			$("#generic_loading").html('<spring:message code="avvisi.run.paperwork.registering"/>').show();

			$.ajax({
	            url: '${path}/extra/automatic_register.htm',
	            type: "POST",
	            error: function(XMLHttpRequest, textStatus, errorThrown) {
	            	$("#generic_loading").html('<spring:message code="avvisi.error.paperwork.registering"/>');
	            	setTimeout(function() {
					    $('#generic_loading').fadeOut('slow');
					}, 7000);
	            },
	            success:function (data) {
	            	var message = '<spring:message code="avvisi.end.paperwork.registering"/>';
	            	if(data != 'executed')
	            		message = '<spring:message code="avvisi.runned.paperwork.registering"/>';
	            	$("#generic_loading").html(message);
	            	setTimeout(function() {
					    $('#generic_loading').fadeOut('slow');
					}, 7000);
	              }
	        });
		});
		
	</script>
</c:if>