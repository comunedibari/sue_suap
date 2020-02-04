<%-- 
    Document   : indietro
    Created on : 3-apr-2014, 15.31.49
    Author     : GabrieleM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<script>
    function aggiungiParametroDaReinviare(name, value) {
        var href = $("#indietro").attr("href");
        $("#indietro").attr("href", href+"&"+name+"="+value);
    }
</script>
<div class="buttonHolder">
    <div class="buttonHolder">
                <a id="indietro" href="${path}${linkIndietro}.htm" class="secondaryAction">&larr; <spring:message code="common.button.indietro"/>
               </a>
    </div>
</div>
