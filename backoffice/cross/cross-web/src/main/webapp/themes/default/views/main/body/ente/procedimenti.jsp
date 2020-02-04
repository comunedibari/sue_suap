<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
%>
<tiles:insertAttribute name="body_error" />
<div id="enteSelezionato">
    <p> Ente selezionato : ${ente.descrizione} </p>
</div>
<script type="text/javascript">

    var url = getUrl();
    var urlModificaResponsabile = "${path}/ente/procedimenti/modificaResponsabileAjax.htm";
    var soloProcedimento='soloProcedimento';
    var ancheLePratiche='ancheLePratiche';
    function modificaResponsabile(id, responsabileProcedimento) {
        var wHeight = $(window).height() * 0.8;
        $("#windowModificaResponsabile .responsabileProcedimento").val($("#responsabileProcedimento_"+id).html());
        $("#windowModificaResponsabile .nuovoResponsabileProcedimento").val('');
        $("#windowModificaResponsabile .id_procedimento").val(id);
        windowModificaResponsabile(wHeight);
    }
    function windowModificaResponsabile(wHeight) {
        $('#windowModificaResponsabile').dialog({
            title: '<spring:message code="ente.procedimenti.responsabileprocedimento.modifica.title"/>',
            modal: true,
            height: wHeight,
            width: '50%',
            close: function () {
                $("#windowModificaResponsabile .id_procedimento").val("");
                $("#windowModificaResponsabile .nuovaResponsabileProcedimento").val("");
                $("#windowModificaResponsabile .nuovoResponsabileProcedimento").val("");
            },
            buttons: {
                '<spring:message code="ente.button.procedimenti.responsabileprocedimento.singolo"/>': function () {
                    aggiorna(soloProcedimento);
                },
                '<spring:message code="ente.button.procedimenti.responsabileprocedimento.anche.pratiche"/>': function () {
                    aggiorna(ancheLePratiche);
                },
                'Annulla': function () {
                    $(this).dialog("close");
                }
            }
        });
    }
    function aggiorna(azione) {
        var div = $("<div>");
        if (azione == soloProcedimento) {
            div.html('<spring:message code="ente.procedimenti.responsabileprocedimento.conferma.soloprocedimento"/>');
        }
        if (azione == ancheLePratiche) {
            div.html('<spring:message code="ente.procedimenti.responsabileprocedimento.conferma.anchepratiche"/>');
        }
        $(div).dialog({
            modal: true,
            title: '<spring:message code="ente.procedimenti.responsabileprocedimento.conferma.title"/>',
            buttons: {
                'Procedi': function () {
                    var idProcedimento = $("#windowModificaResponsabile .id_procedimento").val();
                    var responsabileProcedimento = $("#windowModificaResponsabile .nuovoResponsabileProcedimento").val();
                    $.ajax({
                        url: urlModificaResponsabile,
                        dataType: "json",
                        type: "POST",
                        data: {
                            action: azione,
                            idProc: idProcedimento,
                            responsabileProcedimento: responsabileProcedimento
                        },
                        success: function (data) {
                            var messaggio = data.message;
                            if (!data.success) {
                                mostraMessaggioAjax(messaggio, "error");
                            } else {
                                $("#windowModificaResponsabile").dialog("close");
                                $("#responsabileProcedimento_" + idProcedimento).html(responsabileProcedimento);
                                mostraMessaggioAjax(messaggio, "success");
                            }
                        }
                    });
                    $(div).dialog('close');
                },
                Annulla: function () {
                    $(div).dialog('close');
                }
            }
        });
    }
    $(document).ready(function ()
    {

        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colNames: [
                        'ID',
                        '<spring:message code="ente.procedimenti.nome"/>',
                        '<spring:message code="ente.procedimenti.termini"/>',
                        '<spring:message code="ente.procedimenti.abilitazione"/>',
                        '<spring:message code="ente.procedimenti.responsabileprocedimento"/>',
                        '<spring:message code="ente.procedimenti.processo"/>',
                        '<spring:message code="ente.procedimenti.azioni"/>'],
                    colModel:
                            [{
                                    name: 'idProc',
                                    index: 'idProc',
                                    hidden: true
                                },
                                {
                                    name: 'desProc',
                                    index: 'desProc',
                                },
                                {
                                    name: 'termini',
                                    index: 'termini',
                                    width: 50,
                                    sortable: true
                                },
                                {
                                    name: 'responsabileProcedimento',
                                    index: 'responsabileProcedimento',
                                    sortable: true,
                                    cellattr: function (rowId, value, rowObject, colModel, arrData) {
                                        return 'id="responsabileProcedimento_' + rowObject["idProc"] + '"';
                                    }
                                },
                                {
                                    name: 'abilitato',
                                    index: 'abilitato',
                                    sortable: true,
                                    width: 50,
                                    formatter: function (cellvalue) {
                                        if (cellvalue == true) {
                                            cellvalue = 'ABILITATO';
                                        } else {
                                            cellvalue = 'NON ABILITATO';
                                        }
                                        return cellvalue;
                                    }
                                },
                                {
                                    name: 'processo',
                                    index: 'processo',
                                    sortable: false
                                },
                                {
                                    name: 'azione',
                                    index: 'id',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function (cellvalue, options, rowObject) {
                                        var modificaResponsabile = "";
                                        if (rowObject["abilitato"]) {
                                            modificaResponsabile = '<div id="modificaResponsabile"><form onSubmit="modificaResponsabile(' + rowObject["idProc"] + ',\'' + rowObject["responsabileProcedimento"] + '\'); return false;" method="post" class="gridForm"><button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" ><spring:message code="ente.button.procedimenti.responsabileprocedimento"/></button></form></div>';
                                        }
                                        var link = ' <div id="button"><form action="${path}/ente/selezionaProcesso.htm" method="post" class="gridForm"><input type="hidden" name="idProc" value="' + rowObject["idProc"] + '"><button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"><spring:message code="ente.button.procedimenti.processi"/></button></form><form action="<%=path%>/ente/selezionaProcedimento.htm" method="post" class="gridForm"><input type="hidden" name="idProc" value="' + rowObject["idProc"] + '"><select name="submit"><option value="<spring:message code="ente.procedimenti.disabilita"/>"><spring:message code="ente.procedimenti.disabilita"/></option><option value="<spring:message code="ente.procedimenti.abilita"/>"><spring:message code="ente.procedimenti.abilita"/></option></select><button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"><spring:message code="ente.button.procedimenti.modifica"/></button></form>' + modificaResponsabile + '</div>';
                                        return link;
                                    }
                                }
                            ],
                    /*
                     <c:set var="page" value="1"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.page!=null }">
                     <c:set var="page" value="${filtroRicerca.page}"/>
                     </c:if>
                     */
                    page: '${page}',
                    /*
                     <c:set var="orderColumn" value="idProc"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.orderColumn!=null}">
                     <c:set var="orderColumn" value="${filtroRicerca.orderColumn}"/>
                     </c:if>
                     */
                    sortname: '${orderColumn}',
                    /*
                     <c:set var="orderDirection" value="asc"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.orderDirection!=null}">
                     <c:set var="orderDirection" value="${filtroRicerca.orderDirection}"/>
                     </c:if>
                     */
                    sortorder: "${orderDirection}",
                    /*
                     <c:set var="rowNum" value="10"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.limit!=null}">
                     <c:set var="rowNum" value="${filtroRicerca.limit}"/>
                     </c:if>
                     */
                    rowNum: "${rowNum}",
                    rowList: [10, 20, 30, 100],
                    pager: '#pager',
                    viewrecords: true,
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width()
                });
    });

</script>
<tiles:insertAttribute name="operazioneRiuscitaAjax" />
<div id="impostazioni_div">
    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
    <div class="buttonHolder">
        <a href="<%=path%>/ente/modifica.htm" class="secondaryAction">&larr; Indietro</a>
    </div>
</div>
<div id="windowModificaResponsabile" class="modal-content">
    <input class="input_anagrafica_disable id_procedimento hidden" type="text" value="">
    <div class="box_procedimento ctrlHolder">
        <label class="required">
            <spring:message code="ente.procedimenti.responsabileprocedimento.old"/>
        </label>
        <input class="input_anagrafica_disable responsabileProcedimento" type="text" disabled="" value="">
    </div>
    <div class="box_allegato ctrlHolder">
        <label class="required">
            <spring:message code="ente.procedimenti.responsabileprocedimento.new"/>
        </label>
        <input class="input_anagrafica_disable nuovoResponsabileProcedimento" type="text" value="">
    </div>        
</div> 


