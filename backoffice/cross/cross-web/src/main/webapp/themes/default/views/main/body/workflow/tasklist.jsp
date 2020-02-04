<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script language="javascript">
    var path = '${path}';
</script>

<link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/core.css"/>"  media="all"/>
<script  type="text/javascript" src="<c:url value="/javascript/cross/tasklist.js"/>"></script>

<h2 style="text-align: center">Task attivi</h2>
<tiles:insertAttribute name="operazioneRiuscitaAjax" />

<div id="impostazioni_div">
    <tiles:insertAttribute name="body_error" />
    <div class="tasklistTableContainer">
        <table id="tasklistTable"><tr><td/></tr></table> 
        <div id="tasklistPager"></div> 
    </div>
</div>

<div class="hidden task_protocol_manual_management ">
    <div class="ctrlHolder">
        <label for="protocollo_automatico">Riprovare con la protocollazione automatica?</label>
        <select id="protocollo_automatico" name="protocollo_automatico" class="textInput required chosen-select" >
            <option value="true">Yes</option>
            <option value="false">No</option>
        </select>
    </div>
    <div class="ctrlHolder hidden">
        <label for="protocollo_segnatura">Segnatura protocollo</label>
        <input type="text" id="protocollo_segnatura" name="protocollo_segnatura" class="textInput required" />
        <p class="formHint protocolloSegnatura"><spring:message code="common.field.mandatory"/></p>
    </div>
    <div class="ctrlHolder hidden">
        <label for="protocollo_data">Data protocollo</label>
        <input type="text" id="protocollo_data" name="protocollo_data" class="textInput required dataPicker" />
        <p class="formHint protocolloData"><spring:message code="common.field.mandatory"/></p>
    </div>
</div>

<div id="task_mail_manual_management" title="Rimandare l'email?">
    <p class="validateTips">Di seguito l&apos;email che non &egrave; stato possibile inviare.</p>

    <form id="task_mail_manual_form">
        <input type="hidden" name="taskId" />
        <input type="hidden" name="mailId" />
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

<!--    <div class="ctrlHolder">
        <label>Riprovare a inviare la mail?</label>
    </div>
</div>-->

