<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script language="javascript">
    var path = '<%= request.getContextPath()%>';
</script>
<script  type="text/javascript" src="<c:url value="/javascript/cross/console.js"/>"></script>

<h2>Email non inviate</h2>
<tiles:insertAttribute name="operazioneRiuscitaAjax" />
<%--
<div id="invioMailMassive">
    <input id="invioMailMassiveButton" type="submit" value="Invio massivo" />
</div>
--%>
<div class="tableContainer">
    <table id="mail"></table>
    <div id="pagerMail"></div>
</div>

<div id="task_mail_manual_management" title="Rimandare l'email?">
    <p class="validateTips">Di seguito l&apos;email che non &egrave; stato possibile inviare.</p>

    <form id="task_mail_manual_form">
        <input type="hidden" name="mailId" />
        <input type="hidden" name="taskId" />
        <div class="ctrlHolder">
            <label class="required" for="destinatario">Destinatario</label>
            <input type="text" id="destinatario" name="destinatario" class="textInput required value" style="width: 100%;"/>
        </div>
        <div class="ctrlHolder">
            <label class="required" for="oggettoEmail">Oggetto email</label>
            <input type="text" id="oggettoEmail" name="oggettoEmail" class="textInput required value" style="width: 100%;" readonly/>
        </div>
        <div class="ctrlHolder">    
            <label class="required">Corpo email</label>
            <div class="value text-area" id="corpoEmail"></div>
        </div>
    </form>
</div>

<div id="windowInserisciIdMail" class="hidden modal-content">
    <div class="ctrlHolder">
        <label for="lista_id_mail">Inserire gli id delle mail da reinviare separati da virgola</label>
        <input type="text" id="lista_id_mail" name="lista_id_mail" class="textInput required" />
        <p class="formHint lista_id_mail"><spring:message code="common.field.mandatory"/></p>
    </div>
</div>
<div style="float:left;">
    <a class="secondaryAction" href="/cross/console/index.htm">&larr; Indietro</a>
</div>