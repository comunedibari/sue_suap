<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    var url = getUrl();
</script>
<script>
    function hidShowStack() {
        var stat = $("#stack").attr("value");
        if ("H" === stat) {
            $("#stack").attr("value", "S");
            $("#stacktrace").show();
        }
        if ("S" === stat) {
            $("#stack").attr("value", "H");
            $("#stacktrace").hide();
        }
    }
    ;

</script>
<div id="formErrore">
    <fieldset class="fieldsetComunicazione">
        <legend><spring:message code="console.dettaglio"/></legend>
        <div>
            <div class="ctrlHolder">
                <label class="required">
                    <spring:message code="console.errori.pratica"/>
                </label>
                <span class="value">${errore.idPratica}</span>
            </div>
            <div class="ctrlHolder">
                <label class="required">
                    <spring:message code="console.errori.idutente"/>
                </label>
                <span class="value">${errore.idUtente}</span>
            </div>
            <div class="ctrlHolder">
                <label class="required">
                    <spring:message code="console.errori.data"/>
                </label>
                <span class="value"><fmt:formatDate pattern="dd/MM/yyyy" value="${errore.data}" /></span>
            </div>
            <div class="ctrlHolder">
                <label class="required">
                    <spring:message code="console.errori.coderrore"/>
                </label>
                <span class="value">${errore.codErrore}</span>
            </div>
            <div class="ctrlHolder">
                <label class="required">
                    <spring:message code="console.errori.descrizione"/>
                </label>
                <span class="value">${errore.descrizione}</span>
            </div>
            <div class="ctrlHolder">
                <label class="required">
                    <spring:message code="console.errori.trace"/>
                </label>
                <button onclick="hidShowStack();
        return false;" >Mostra trace</button>
                <div id="stacktrace">${errore.trace}"</div>
                <div class="ctrlHolder">
                    <label class="required">
                        <spring:message code="console.errori.exception"/>
                    </label>
                    <span class="value">${errore.exception}</span>
                </div>
            </div>
    </fieldset>

</div>
<div style="float:left;">
    <a class="secondaryAction" href="/cross/console/index.htm">&larr; Indietro</a>
</div>
<input type="hidden" id="stack" value="H">
