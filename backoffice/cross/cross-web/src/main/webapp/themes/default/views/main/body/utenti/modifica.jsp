<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript" src="<c:url value="/javascript/jquery.dynatree.js"/>"></script>
<script type="text/javascript">
    var urlGestisciRuolo = "${path}/utenti/modifica/gestisciruoliAjax.htm";
    var treeData = ${tree};
    $(function () {
        $("#alberoEntiRuoli").dynatree({
            checkbox: true,
            selectMode: 3,
            children: treeData,
            onSelect: function (select, node) {
                $.ajax({
                    url: urlGestisciRuolo,
                    dataType: "json",
                    data: {
                        select: select,
                        key: node.data.key,
                        parent: node.getParent().data.key,
                        idUtente: ${utente.idUtente}
                    },
                    success: function (data) {
                        if (data.success) {
                            var stringa = ".attivaBottone" + node.getParent().data.key + "\\\#" + node.data.key;
                            $(stringa+" > input").removeAttr('onclick');
                            $(stringa+" > input").unbind('click');
                            if (select) {
                                $(stringa).removeClass("hidden");
                                var chiave=data.attributes['idRuoloEnte'];
                                $(stringa+" > input").attr('onclick',"gestisciProcedimentiPopUp("+chiave+")");
                            } else {
                                $(stringa).addClass("hidden");
                            }
                            mostraMessaggioAjax(data.message, "success");
                        } else {
                            mostraMessaggioAjax(data.message, "error");
                        }
                    }
                });
            },
            onDblClick: function (node, event) {
                node.toggleSelect();
            },
            onKeydown: function (node, event) {
                if (event.which === 32) {
                    node.toggleSelect();
                    return false;
                }
            }
        });
    });
    function gestisciProcedimentiPopUp(id) {
        var wHeight = $(window).height() * 0.8;
        windows(wHeight, id);
    }
    function windows(wHeight, id) {
        wgf.utils.showFormDialog(
                'Collega procedimenti',
                '${path}/form/ajax/view.htm?idForm=it.wego.cross.forms.common.UtentiEntiProcedimentiGrid&idUtenteRuoloEnte=' + id);
    }
</script>
<link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/ui.dynatree/ui.dynatree.css" />" media="all"/>
<tiles:insertAttribute name="body_error" />

<div>
    <tiles:insertAttribute name="operazioneRiuscitaAjax" />
    <div class="sidebar_auto">
        <div class="page-control" data-role="page-control">
            <span class="menu-pull"></span> 
            <div class="menu-pull-bar"></div>
            <!-- Etichette cartelle -->
            <ul>
                <li class="active"><a href="#frame1"><spring:message code="utenti.title.modifica"/></a></li>
                <li><a href="#frame2">Enti / Ruoli / Procedimenti</a></li>
            </ul>
            <!-- Contenuto cartelle -->
            <div class="frames">
                <div class="frame active" id="frame1">

                    <form action="${path}/utenti/modifica.htm" class="uniForm inlineLabels" method="post">

                        <div class="inlineLabels">

                            <input type="hidden" name="idUtente" value="${utente.idUtente}" />

                            <div class="ctrlHolder">
                                <label for="nome" class="required"><spring:message code="utenti.nome"/></label>
                                <input name="nome" id="nome" maxlength="255" type="text" class="textInput required" value="${utente.nome}" >
                                <p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
                            </div>

                            <div class="ctrlHolder">
                                <label for="cognome" class="required"><spring:message code="utenti.cognome"/></label>
                                <input name="cognome" id="cognome" maxlength="255" type="text" class="textInput required" value="${utente.cognome}" >
                                <p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
                            </div>

                            <div class="ctrlHolder">
                                <label for="codiceFiscale" class="required"><spring:message code="utenti.codicefiscale"/></label>
                                <input name="codiceFiscale" id="codiceFiscale" maxlength="16" type="text" class="textInput required" value="${utente.codiceFiscale}" >
                                <p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
                            </div>

                            <div class="ctrlHolder">
                                <label for="username" class="required"><spring:message code="utenti.username"/></label>
                                <input name="username" id="username" maxlength="255" type="text" class="textInput required" value="${utente.username}">
                                <p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
                            </div>

                            <div class="ctrlHolder">
                                <label for="password" class="required"><spring:message code="utenti.password"/></label>
                                <input name="password" id="username" maxlength="255" type="password" class="textInput required">
                                <p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
                            </div>

                            <div class="ctrlHolder">
								<label for="email" class="required"><spring:message code="utenti.email"/></label>
								<input name="email" id="email" maxlength="255" type="text" class="textInput" value="${utente.email}" >
								<p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
							</div>

                            <div class="ctrlHolder">
                                <label for="telefono" class="required"><spring:message code="utenti.telefono"/></label>
                                <input name="telefono" id="telefono" maxlength="50" type="text" class="textInput" value="${utente.telefono}">
                            </div>

                            <div class="ctrlHolder">
                                <label for="superusercheck" class="required"><spring:message code="utenti.superuser"/></label>
                                <input type="checkbox" name="superusercheck" id="superusercheck"  class="textInput" onclick="aggiornacheckbox()">   
                                <input type="hidden"  id="superuser" name="superuser" value=${utente.superuser} >
                            </div>

                            <div class="ctrlHolder">
                                <label for="note" class="required"><spring:message code="utenti.note"/></label>
                                <textarea rows="4" cols="100" name="note" id="note" class="textArea" value="${utente.note}">${utente.note}</textarea>
                            </div>
                        </div>

                        <div class="buttonHolder">
                            <button type="submit" class="primaryAction" value="salvaAnagrafica" name="action"><spring:message code="utenti.button.modifica"/></button>
                        </div>

                    </form>

                </div>
                <div class="frame" id="frame2">
                    <div id="alberoEntiRuoli" class="inlineLabels"></div>
                </div>
            </div>
            <div class="buttonHolder">
                <a href="${path}/utenti/index.htm" class="secondaryAction">&larr; Indietro</a>
            </div>
        </div>
    </div>

</div>

<script>
    function aggiornacheckbox() {
        var form = $("#superusercheck");
        if (($("#superusercheck").prop('checked') !== null) && ($("#superusercheck").prop('checked') === true)) {
            if ($("#superuser").size() > 0) {
                $("#superuser").remove();
            }
            form.append('<input type=\"hidden\"  id=\"superuser\" name=\"superuser\" value=\"S\" >');
        } else {
            if ($("#superuser").size() > 0) {
                $("#superuser").remove();
            }
            form.append('<input type=\"hidden\"  id=\"superuser\" name=\"superuser\" value=\"N\" >');
        }
        return false;
    }
</script>
<script>
    function aggiornacheckboxInizio() {
        if ($("#superuser").attr('value') == 'N') {
            $("#superusercheck").prop('checked', false);
        }
        if ($("#superuser").attr('value') == 'S') {
            $("#superusercheck").prop('checked', true);
        }
    }
    window.onload = aggiornacheckboxInizio();
</script>