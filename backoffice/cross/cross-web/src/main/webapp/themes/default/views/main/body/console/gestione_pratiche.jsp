<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script type="text/javascript">
    function poll() {
        setTimeout(function() {
            $('#mail').trigger('reloadGrid');
        }, 30000);
    }

    $(document).ready(function() {
        $("#pratiche").jqGrid({
            url: '<%= request.getContextPath()%>/console/pratiche/ajax.htm',
            datatype: "json",
            colNames: ['Id pratica', 'Identificativo pratica', 'Oggetto', 'Data ricezione', 'Azione'],
            colModel: [
                {name: 'idPratica', index: 'idPratica', hidden: true},
                {name: 'identificativoPratica', index: 'identificativoPratica'},
                {name: 'oggettoPratica', index: 'oggettoPratica'},
                {name: 'dataRicezione', index: 'dataRicezione'},
                {
                    name: 'azione',
                    index: 'azione',
                    classes: 'list_azioni',
                    sortable: false,
                    formatter: function(cellvalue, options, rowObject) {
                        var link = '<div><form id="praticaForm"><input type="hidden" name="idPratica" value="' + rowObject["idPratica"] + '"><select name="azione" class="praticaAction"><option value=""></option><option value="resend">Riprova inserimento</option><option value="xml">Genera xml</option><option value="delete">Cancella pratica</option></select><input class="button ui - state - default ui - corner - all gestione_pratica" type="button" value="OK" onclick="gestisci_pratica(this)"/><input type="button" value="Modifica XML " class="button ui-state-default ui-corner-all gestione_pratica" onclick="editXML(); return false;"></button></form></div>';
                        return link;
                    }
                }
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#pagerPratiche',
            sortname: 'idPratica',
            jsonReader: {repeatitems: false},
            viewrecords: true,
            sortorder: 'desc',
            caption: 'Pratiche non protocollate',
            height: 'auto',
            width: $('.tableContainer').width()
//            gridComplete: function() {
//                var ids = $(".list_azioni");
//                for (var i = 0; i < ids.length; i++) {
//                    var id = $(".list_azioni")[i].parentNode.id;
//                    var idPratica = $($("#pratiche").jqGrid('getCell', id, 'idPratica')).text();
//                    $('#button input[name=idPratica]').val(idPratica);
//                    var button = $('#button').clone();
//                    $(button).attr('id', 'button_' + i);
//                    $(button).removeClass('hidden');
//                    $($(".list_azioni")[i]).html(button);
//                }
//            }
        });
        $("#pratiche").jqGrid('navGrid', '#pagerPratiche', {
            add: false,
            edit: false,
            del: false,
            search: false,
            refresh: true
        });
        poll();

    });

    function gestisci_pratica(btn) {
        var form = $(btn).closest('form');
        var idPratica = $(form).find('input[name=idPratica]').val();
        var azione = $(form).find('select').val();
        if (azione === 'resend') {
            var confirmDialog = $('<div />').attr({
                title: 'Conferma azione'
            }).html('Vuoi procedere con il reinoltro della pratica?');
            $(confirmDialog).dialog({
                resizable: false,
                height: 180,
                modal: true,
                buttons: {
                    Ok: function() {
                        $(this).dialog("close");
                        $.isLoading({
                            text: "Loading"
                        });
                        $.ajax({
                            dataType: 'json',
                            type: 'POST',
                            url: '<%= request.getContextPath()%>/console/pratiche/gestisci/resend.htm',
                            data: {
                                idPratica: idPratica
                            },
                            success: function(data) {
                                $.isLoading("hide");
                                var esito = data.success;
                                var messaggio = data.message;
                                var title = '';
                                if (esito === true) {
                                    title = 'Operazione eseguita correttamente';
                                } else {
                                    title = 'Errore'
                                }
                                var dialog = $('<div />').attr({
                                    title: title
                                }).text(messaggio);
                                dialog.dialog({
                                    resizable: false,
                                    modal: true,
                                    buttons: {
                                        Ok: function() {
                                            $(this).dialog("close");
                                            $("#pratiche").trigger("reloadGrid");
                                        }
                                    }
                                });
                            }
                        });
                    },
                    Cancel: function() {
                        $(this).dialog("close");
                    }
                }
            });
        } else if (azione === 'xml') {
            var url = '<%= request.getContextPath()%>/console/pratiche/gestisci/generaxmlcross.htm?idPratica=' + idPratica;
            window.open(url, '_blank');
        } else if (azione === 'delete') {
            var confirmDialog = $('<div />').attr({
                title: 'Attezione!'
            }).html('Questa operazione cancellerà irrimediabilmente la pratica e tutti i dati a lei collegati.<br />Vuoi procedere?');
            $(confirmDialog).dialog({
                resizable: false,
                height: 200,
                width: 400,
                modal: true,
                buttons: {
                    Ok: function() {
                        $(this).dialog("close");
                        $.isLoading({
                            text: "Loading"
                        });
                        $.ajax({
                            dataType: 'json',
                            type: 'POST',
                            url: '<%= request.getContextPath()%>/console/pratiche/gestisci/cancella.htm',
                            data: {
                                idPratica: idPratica
                            },
                            success: function(data) {
                                $.isLoading("hide");
                                var esito = data.success;
                                var messaggio = data.message;
                                var title = '';
                                if (esito === true) {
                                    title = 'Operazione eseguita correttamente';
                                } else {
                                    title = 'Errore'
                                }
                                var dialog = $('<div />').attr({
                                    title: title
                                }).text(messaggio);
                                dialog.dialog({
                                    resizable: false,
                                    modal: true,
                                    buttons: {
                                        Ok: function() {
                                            $(this).dialog("close");
                                            $("#pratiche").trigger("reloadGrid");
                                        }
                                    }
                                });
                            }
                        });
                    },
                    Cancel: function() {
                        $(this).dialog("close");
                    }
                }
            });
        } else {
            var confirmDialog = $('<div />').attr({
                title: 'Attenzione!'
            }).text('Non è stata selezionata nessuna azione');
            $(confirmDialog).dialog({
                resizable: false,
                height: 160,
                modal: true,
                buttons: {
                    Ok: function() {
                        $(this).dialog("close");
                    }
                }
            });
        }
        return false;
    }
</script>
<h2>Pratiche non protocollate</h2>
<tiles:insertAttribute name="body_error" />
<div class="tableContainer">
    <div id="impostazioni_div">
        <table id="pratiche"></table>
        <div id="pagerPratiche"></div>
    </div>
</div>
<div style="float:left;">
    <a class="secondaryAction" href="/cross/console/index.htm">&larr; Indietro</a>
</div>
<!--<div class="hidden" id="button">
    <form id="praticaForm">
        <input type="hidden" name="idPratica" value="_ID_">
        <select name="azione" class="praticaAction">
            <option value=""></option>
            <option value="resend">Riprova inserimento</option>
            <option value="xml">Genera xml</option>
            <option value="delete">Cancella pratica</option>
        </select>
        <input class='button ui-state-default ui-corner-all gestione_pratica' type='button' value='OK' onclick='gestisci_pratica(this)'/>
        <input type="button" value="Modifica XML " class="button ui-state-default ui-corner-all gestione_pratica" onclick="editXML();
        return false;"> </button>
    </form>
</div>-->

<script>
    function editXML() {
        $("#praticaForm").append('<input type="hidden" name="editXml" value="editXml">');
        $("#praticaForm").submit();
        return false;
    }
</script>