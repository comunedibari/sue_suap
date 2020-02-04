<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<script type="text/javascript">
    var url = getUrl();
    $(document).ready(function()
    {


        $("#listTable").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colNames: [
                        '<spring:message code="datiestesi.iddatoesteso"/>',
                        '<spring:message code="datiestesi.codtipooggetto"/>',
                        '<spring:message code="datiestesi.destipooggetto"/>',
                        '<spring:message code="datiestesi.idistanza"/>',
                        '<spring:message code="datiestesi.codvalore"/>',
                        '<spring:message code="datiestesi.value"/>',
                        '<spring:message code="datiestesi.azioni"/>'],
                    colModel:
                            [{
                                    name: 'idDatiEstesi',
                                    index: 'idDatiEstesi',
                                    hidden: true
                                },
                                {
                                    name: 'codTipoOggetto',
                                    index: 'codTipoOggetto'
                                },
                                {
                                    name: 'desTipoOggetto',
                                    index: 'desTipoOggetto',
                                    sortable: true
                                },
                                {
                                    name: 'idIstanza',
                                    index: 'idIstanza'
                                },
                                {
                                    name: 'codValue',
                                    index: 'codValue'
                                },
                                {
                                    name: 'value',
                                    index: 'value'
                                },
                                {
                                    name: 'azione',
                                    index: 'id',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function(cellvalue, options, rowObject) {
                                        var link = '<div class="gridActionContainer">\n\
                                                        <a onclick = "modificaValue(' + rowObject["idDatiEstesi"] + ')" href = "#"><img src="${path}/themes/default/css/images/pencil.png"></a>\n\
                                                        <a onClick = "cancellaRiga(' + rowObject["idDatiEstesi"] + ')" href = "#"><img src="${path}/themes/default/css/images/basket_orange.png"></a>\n\
                                                    </div>';
                                        return link;
                                    }
                                }

                            ],
                    rowList: [5, 10, 20, 30, 100],
                    pager: '#pager',
                    /*
    <c:set var="page" value="1"/>
    <c:if test="${filtroRicerca!=null && filtroRicerca.page!=null }">
        <c:set var="page" value="${filtroRicerca.page}"/>
    </c:if> 
                     */
                    page: '${page}',
                    /*
    <c:set var="orderColumn" value="codTipoOggetto"/>
    <c:if test="${filtroRicerca!=null && filtroRicerca.orderColumn!=null}">
        <c:set var="orderColumn" value="${filtroRicerca.orderColumn}"/>
    </c:if>
                     */
                    sortname: '${orderColumn}',
                    viewrecords: true,
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


                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width()
                });
    });
    function cancellaRiga(datoEstesoId) {
        var div = $("<div>");
        div.html('<spring:message code="datiestesi.conferma.cancellazione.richiesta"/>');
        $(div).dialog(
                {
                    modal: true,
                    title: '<spring:message code="datiestesi.conferma.cancellazione"/>',
                    buttons: {
                        Ok: function() {
                            $.ajax({
                                type: 'POST',
                                url: '${path}/datiestesi/azioni.htm',
                                data: {
                                    action: "cancella",
                                    idDatiEstesi: datoEstesoId
                                },
                                success: function(data) {
                                    var esito = data.success;
                                    var messaggio = data.msg;
                                    if (String(esito) === 'true') {
                                        mostraMessaggioAjax(messaggio, "success");
                                    } else {
                                        mostraMessaggioAjax(messaggio, "error");
                                    }
                                    $("#listTable").trigger("reloadGrid");
                                },
                                dataType: 'json'
                            });
                            $(div).dialog('close');
                        },
                        Annulla: function() {
                            $(div).dialog('close');
                        }
                    }
                });
    }
    function modificaValue(datoEstesoId) {
        var div = $('#detailDatoEstestoDialog');
        $(div).dialog({
            modal: true,
            width: 1000,
            height: 700,
            title: '<spring:message code="datiestesi.modifica.title"/>',
            closeOnEscape: true,
            open: $.ajax({
                url: "${path}/datiestesi/azioni.htm",
                dataType: "json",
                data: {
                    action: "cerca",
                    idDatiEstesi: datoEstesoId
                },
                success: function(data) {
                    $("#detailDatoEstestoDialog .value").val(data.value);
                    $("#detailDatoEstestoDialog .codValue").val(data.codValue);
                    $("#detailDatoEstestoDialog .idDatoEsteso").val(data.idDatoEsteso);
                    $("#detailDatoEstestoDialog .codTipoOggetto").val(data.codTipoOggetto);
                    $("#detailDatoEstestoDialog .desTipoOggetto").val(data.desTipoOggetto);
                }

            }),
            buttons: {
                Modifica: function(event) {
                    var value = $("#detailDatoEstestoDialog .value").val();
                    $.ajax({
                        url: "${path}/datiestesi/azioni.htm",
                        dataType: "json",
                        data: {
                            action: "modifica",
                            idDatiEstesi: datoEstesoId,
                            value: value
                        },
                        success: function(data) {
                            var esito = data.success;
                            var messaggio = data.msg;
                            if (String(esito) === 'true') {
                                mostraMessaggioAjax(messaggio, "success");
                                $('#detailDatoEstestoDialog').dialog("close");
                                $("#listTable").trigger("reloadGrid");
                            } else {
                                mostraMessaggioAjax(messaggio, "error");
                            }
                        }
                    });
                },
                Chiudi: function() {
                    $(this).dialog("close");
                }
            }
        });
    }
    function inserisciNuovo() {
        var div = $('#insertDatoEstestoDialog');
        $(div).dialog({
            modal: true,
            width: 1000,
            height: 700,
            title: '<spring:message code="datiestesi.modifica.title"/>',
            closeOnEscape: true,
            buttons: {
                Inserisci: function(event) {
                    var value = $("#insertDatoEstestoDialog .value").val();
                    var codValue = $("#insertDatoEstestoDialog .codValue").val();
                    var idIstanza = $("#insertDatoEstestoDialog .idIstanza").val();
                    var codTipoOggetto = $("#insertDatoEstestoDialog .codTipoOggetto").val();
                    $.ajax({
                        url: "${path}/datiestesi/azioni.htm",
                        dataType: "json",
                        data: {
                            action: "inserisci",
                            codValue: codValue,
                            value: value,
                            idIstanza: idIstanza,
                            codTipoOggetto: codTipoOggetto
                        },
                        success: function(data) {
                            var esito = data.success;
                            var messaggio = data.msg;
                            if (String(esito) === 'true') {
                                mostraMessaggioAjax(messaggio, "success");
                                $('#insertDatoEstestoDialog').dialog("close");
                                $("#listTable").trigger("reloadGrid");
                            } else {
                                mostraMessaggioAjax(messaggio, "error");
                            }
                        }
                    });
                },
                Chiudi: function() {
                    $(this).dialog("close");
                }
            }
        });
    }
</script>
<div>
    <tiles:insertAttribute name="operazioneRiuscita" /> 
</div>
<tiles:insertAttribute name="body_error" />
<spring:message code="datiestesi.select.default" var="selectDefault"/>
<div id="impostazioni_div">
    <div class="table-add-link">

        <a onclick = "inserisciNuovo()" href = "#"alt="<spring:message code="datiestesi.button.nuovo"/>" title="<spring:message code="datiestesi.button.nuovo"/>">
            <spring:message code="datiestesi.button.nuovo"/>
        </a>

    </div>
    <br />
    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="listTable"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>

</div>

<div id="detailDatoEstestoDialog" class="hidden modifica_dato_esteso ">
    <div class="ctrlHolder">
        <input type="text" name="idDatoEsteso" class="hidden textInput required idDatoEsteso" />
    </div>
    <div class="ctrlHolder">
        <label for="codTipoOggetto"><spring:message code="datiestesi.form.codtipooggetto"/></label>
        <input type="text" id="codTipoOggetto" name="codTipoOggetto" class="textInput required codTipoOggetto" disabled="disabled"/>
    </div>
    <div class="ctrlHolder">
        <label for="desTipoOggetto"><spring:message code="datiestesi.form.destipooggetto"/></label>
        <input type="text" id="desTipoOggetto" name="desTipoOggetto" class="textInput required desTipoOggetto" disabled="disabled"/>
    </div>
    <div class="ctrlHolder">
        <label for="idIstanza"><spring:message code="datiestesi.form.idistanza"/></label>
        <input type="text" id="idIstanza" name="idIstanza" class="textInput required idIstanza" disabled="disabled"/>
    </div>
    <div class="ctrlHolder">
        <label for="codValue"><spring:message code="datiestesi.form.codvalue"/></label>
        <input type="text" id="codValue" name="codValue" class="textInput required codValue" disabled="disabled"/>
    </div>
    <div class="ctrlHolder">
        <label for="value"><spring:message code="datiestesi.form.value"/></label>
        <input type="text" id="value" name="value" class="textInput required value" />
    </div>
</div>

<div id="insertDatoEstestoDialog" class="hidden inserisci_dato_esteso ">
    <div class="ctrlHolder distanziatore">
        <label for="codTipoOggettoIns" class="required"><spring:message code="datiestesi.form.tipooggetto"/></label>
        <form:select id="codTipoOggettoIns" name="codTipoOggetto" path="tipiOggetto" class="textInput required codTipoOggetto">
            <form:option value="" label="${selectDefault}" />
            <form:options items="${tipiOggetto}" itemLabel="desTipoOggetto" itemValue="codTipoOggetto" />
        </form:select>
        <p class="formHint"><spring:message code="datiestesi.campo.obbligatorio"/></p>
    </div>
    <div class="ctrlHolder distanziatore">
        <label for="idIstanzaIns" class="required"><spring:message code="datiestesi.form.idistanza"/></label>
        <input type="text" id="idIstanzaIns" name="idIstanza" class="textInput required idIstanza"/>
        <p class="formHint"><spring:message code="datiestesi.campo.obbligatorio"/></p>
    </div>
    <div class="ctrlHolder distanziatore">
        <label for="codValueIns" class="required"><spring:message code="datiestesi.form.codvalue"/></label>
        <input type="text" id="codValueIns" name="codValue" class="textInput required codValue"/>
        <p class="formHint"><spring:message code="datiestesi.campo.obbligatorio"/></p>
    </div>
    <div class="ctrlHolder">
        <label for="valueIns"><spring:message code="datiestesi.form.value"/></label>
        <input type="text" id="valueIns" name="value" class="textInput required value" />
    </div>
</div>

