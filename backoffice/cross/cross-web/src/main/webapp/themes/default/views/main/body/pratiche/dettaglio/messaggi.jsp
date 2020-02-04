<%
    String path = request.getContextPath();
    String url = path + "/pratica/dettaglio/messaggi.htm";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:choose>
    <c:when test="${!empty messaggi}">
        <div class="messageContainer">
            <div class="messageBox">
                <c:forEach items="${messaggi}" var="messaggio" begin="0">
                    <div class="dialog">
                        <div class="time">
                            <span class="timestamp"><fmt:formatDate pattern="dd/MM/yyyy - HH:mm:ss" value="${messaggio.timestamp}" /></span>
                        </div>
                        <div class="name">${messaggio.nomeMittente} ${messaggio.cognomeMittente}</div>
                        <div class="messageText">${messaggio.testo}</div>
                    </div>
                </c:forEach>
            </div>
            <div class="messageForm">
                <script>
                    $(document).ready(function() {
                    
                        var url = '${path}/search/utenti.htm';
                        $( "#destinatario" ).tokenInput(url, {
                            theme: 'facebook',
                            queryParam: 'query',
                            propertyToSearch: "description",
                            tokenLimit: 1,
                            tokenFormatter: function(item){ 
                                return "<li>" + 
                                    "<div style='display: inline-block; padding-left: 10px;'>" +
                                    "<div class='full_name'>" + item.description + "</div>" +
                                    "</div></li>" },
                            resultsFormatter :function(item){ 
                                return "<li>" + 
                                    "<div style='display: inline-block; padding-left: 10px;'>" +
                                    "<div class='full_name'>" + item.description + "</div>" +
                                    "</div></li>" }
                        });
                    });
                </script>
                <form:form action="<%=url%>" class="uniForm " method="post" commandName="messaggio">
                    <label for="destinatario" class="required"><spring:message code="pratica.comunicazione.messaggio.destinatario"/></label>
                    <%-- <input class="textInput" id="destinatario" path="messaggio.idDestinatario" class="destinatari"/> --%>
                    <form:input path="idDestinatario" id="destinatario"/>
                    <%-- <textarea name="messaggio.testo" class="messaggioTextArea"></textarea> --%>
                    <form:textarea path="testo"  class="messaggioTextArea"/>
                    <%-- <input type="submit" value="<spring:message code="pratica.comunicazione.messaggio.invia"/>" class="button ui-state-default ui-corner-all"/> --%>
                    <button type="submit" class="primaryAction" ><spring:message code="pratica.comunicazione.messaggio.invia"/></button>
                </form:form>
            </div>
        </div>
    </c:when> 
    <c:otherwise>
        <div class="nomessage">
            <h4><spring:message code="pratica.comunicazione.messaggio.nomessage"/></h4>
        </div>
    </c:otherwise>
</c:choose>
