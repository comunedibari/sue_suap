<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="messageType" value="${message.error?(message.warning?'warning':'danger'):'success'}"/>
<c:set var="messageColor" value="${message.error?(message.warning?'yellow':'red'):'green'}"/>

<c:if test="${message != null}">
    <div class="alert alert-block alert-${messageType}">
        <button type="button" class="close" data-dismiss="alert">
            <i class="ace-icon fa fa-times"></i>
        </button>

        <c:choose>
            <c:when test="${not empty message.messages}">
                <ol>
                    <c:forEach var="messaggio" begin="0" items="${message.messages}">
                        <li>${messaggio}</li>
                    </c:forEach>
                </ol>
            </c:when>
            <c:otherwise>
                <h3><spring:message code="error.noerror"/></h3>
            </c:otherwise>
        </c:choose>
    </div>
</c:if>
