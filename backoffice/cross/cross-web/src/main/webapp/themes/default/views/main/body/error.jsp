<%-- 
    Document   : messaggio
    Created on : 25-gen-2012, 17.04.55
    Author     : CS
--%>
<%@page import="it.wego.cross.utils.Utils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<c:if test="${message != null and not empty message.messages}">
    <c:if test="${message.error == true}">
        <div class="uniForm failedSubmit" action="#">
            <div class="message ${message.warning?'warning':'error'}">
                <h3><spring:message code="error.default.message"/></h3>
                <ol>
                    <c:forEach var="messaggio" begin="0" items="${message.messages}">
                        <li>${messaggio}</li>
                    </c:forEach>
                </ol>
            </div>
        </div>
    </c:if>
    <c:if test="${message.error == false}">
        <div class="uniForm" action="#">
            <div class="message success">
                <c:choose>
                    <c:when test="${!empty message.messages}">
                        <ol>
                            <c:forEach var="messaggio" begin="0" items="${message.messages}">
                                <h3><li>${messaggio}</li></h3>
                            </c:forEach>
                        </ol>
                    </c:when>
                    <c:otherwise>
                        <h3><spring:message code="error.noerror"/></h3>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
    </c:if>
</c:if>