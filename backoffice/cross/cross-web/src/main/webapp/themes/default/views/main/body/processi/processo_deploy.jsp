<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style type="text/css">

    table {
        overflow:hidden;
        border:1px solid #d3d3d3;
        background:#fefefe;
        width:100%;
        margin:5% auto 0;
        border-radius:5px;
    }

    th, td {padding:18px 28px 18px; text-align:center; }

    th {padding-top:22px; text-shadow: 1px 1px 1px #fff; background:#e8eaeb;}

    td {border-top:1px solid #e0e0e0; border-right:1px solid #e0e0e0;}

    tr.odd-row td {background:#f6f6f6;}

    td.first, th.first {text-align:left}

    td.last {border-right:none;}

    td {
        background: -moz-linear-gradient(100% 25% 90deg, #fefefe, #f9f9f9);
        background: -webkit-gradient(linear, 0% 0%, 0% 25%, from(#f9f9f9), to(#fefefe));
    }

    tr.odd-row td {
        background: -moz-linear-gradient(100% 25% 90deg, #f6f6f6, #f1f1f1);
        background: -webkit-gradient(linear, 0% 0%, 0% 25%, from(#f1f1f1), to(#f6f6f6));
    }

    th {
        background: -moz-linear-gradient(100% 20% 90deg, #e8eaeb, #ededed);
        background: -webkit-gradient(linear, 0% 0%, 0% 20%, from(#ededed), to(#e8eaeb));
    }

</style>

<script language="javascript">
    var path = '${path}';
</script>

<link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/core.css"/>"  media="all"/>
<script  type="text/javascript" src="<c:url value="/javascript/cross/processo.deploy.js"/>"></script>

<tiles:insertAttribute name="operazioneRiuscitaAjax" />

<div>
    <tiles:insertAttribute name="body_error" />

    <h2 style="text-align: center"><spring:message code="processo.aggiungi.title"/></h2>
    <form action="${path}/processi/processo/deploySubmit.htm" class="uniForm inlineLabels" method="post" enctype="multipart/form-data">
        <div class="inlineLabels">
            <div class="ctrlHolder">
                <label for="processName" class="required">Nome processo</label>
                <input name="processName" type="text" maxlength="255" class="textInput required" >
                <p class="formHint"><spring:message code="eventi.processo.obbligatorio"/></p>
            </div>
            <div class="ctrlHolder">
                <label for="processFile" class="required">Processo BAR</label>
                <input type="file" name="processFile" class="textInput required"/>   
                <p class="formHint"><spring:message code="eventi.processo.obbligatorio"/></p>
            </div>
        </div>

        <div class="buttonHolder">
            <button type="submit" class="primaryAction">Deploy</button>
        </div>
    </form>

    <h2 style="text-align: center">Processi</h2>
    <table>
        <tr>
            <th>Id</th>
            <th>Key</th>
            <th>Name</th>
            <th>Resource name</th>
            <th>Version</th>
            <th></th>
        </tr>
        <c:forEach items="${process_list}" var="process">
            <tr>
                <td>${process.id}</td>
                <td>${process.key}</td>
                <td>${process.name}</td>
                <td>${process.resourceName}</td>
                <td>${process.version}</td>
                <td>
                    <a href="${path}/process/istance/start.htm?processId=${process.id}">Avvia</a>
                    <a href="${path}/process/undeploy.htm?processId=${process.id}">Elimina(Cascade)</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <h2 style="text-align: center">Istanze attive</h2>
    <table>
        <tr>
            <th>Id</th>
            <th>Activiti Id</th>
            <th>Process Instance Id</th>
            <th>Process Definition Id</th>
            <th>Altro</th>
        </tr>
        <c:forEach items="${instance_list}" var="instance">
            <tr>
                <td>${instance.id}</td>
                <td>${instance.activityId}</td>
                <td>${instance.processInstanceId}</td>
                <td>${instance.processDefinitionId}</td>
                <td></td>
            </tr>
        </c:forEach>
    </table>
    <h2 style="text-align: center">Istanze storiche</h2>
    <table>
        <tr>
            <th>Id</th>
            <th>ProcessDefinition Id</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th></th>
        </tr>
        <c:forEach items="${history_instance_list}" var="instance">
            <tr>
                <td>${instance.id}</td>
                <td>${instance.processDefinitionId}</td>
                <td>${instance.startTime}</td>
                <td>${instance.endTime}</td>
                <td><a href="${path}/process/istance/delete.htm?historicInstanceId=${instance.id}">Elimina</a></td>
            </tr>
        </c:forEach>
    </table>
    <h2 style="text-align: center">Task attivi</h2>
    <table>
        <tr>
            <th>Id</th>
            <th>Description</th>
            <th>Owner</th>
            <th>Assignee</th>
            <th>Process Instance Id</th>
            <th>Execution Id</th>
            <th>Process Definition Id</th>
            <th>Create Time</th>
            <th>Task Definition Key</th>
            <th>DueDate</th>
            <th></th>
        </tr>
        <c:forEach items="${task_list}" var="task">
            <tr>
                <td>${task.id}</td>
                <td>${task.description}</td>
                <td>${task.owner}</td>
                <td>${task.assignee}</td>
                <td>${task.processInstanceId}</td>
                <td>${task.executionId}</td>
                <td>${task.processDefinitionId}</td>
                <td>${task.createTime}</td>
                <td>${task.taskDefinitionKey}</td>
                <td>${task.dueDate}</td>
                <td><a class="complete_task_protocol_manual_management" task-id="${task.id}" task-key="${task.taskDefinitionKey}" href="#">Completa</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
            
            <!--<form id="creazioneEventoForm" action="/cross/pratiche/caricamento/manuale.htm" class="uniForm inlineLabels comunicazione" method="post" enctype="multipart/form-data">-->

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
<div class="hidden task_mail_manual_management ">
    <div class="ctrlHolder">
        <label>Riprovare a inviare la mail?</label>
    </div>
</div>

