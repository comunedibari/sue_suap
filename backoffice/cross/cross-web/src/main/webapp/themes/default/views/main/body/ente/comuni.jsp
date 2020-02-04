<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>

<script type="text/javascript">
    var url = getUrl();
    var numerocomuni = 0;
    $(document).ready(function()
    {

        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colNames: [
                        'ID',
                        '<spring:message code="ente.comuni.descrizione"/>',
                        '<spring:message code="ente.comuni.provincia"/>',
                        '<spring:message code="ente.comuni.codicecatastale"/>',
                        '<spring:message code="ente.comuni.azione"/>'],
                    colModel:
                            [{
                                    name: 'idComune',
                                    index: 'idComune',
                                    hidden: true
                                },
                                {
                                    name: 'descrizione',
                                    index: 'descrizione',
                                    sortable: false
                                },
                                {
                                    name: 'provincia.descrizione',
                                    index: 'provincia.descrizione',
                                    sortable: false
                                },
                                {
                                    name: 'codCatastale',
                                    index: 'codCatastale',
                                    sortable: false
                                },
                                {
                                    name: 'azione',
                                    index: 'id',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function(cellvalue, options, rowObject) {
                                        var link = '<div class="gridActionContainer"><a href="#" onClick="eliminaComune(' + rowObject["idComune"] + ')" class="elimina_ente"><img src="${path}/themes/default/css/images/basket.png"></a></div>';
                                        return link;
                                    }
                                }
                            ],
                    rowList: [10, 20, 30, 100],
                    pager: '#pager',
                    sortname: 'idComune',
                    viewrecords: true,
                    sortorder: "asc",
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width(),
//                    gridComplete: function() {
//                        var ids = $(".list_azioni");
//                        for (var i = 0; i < ids.length; i++) {
//                            var id = $(".list_azioni")[i].parentNode.id;
//                            var html = $("#button").html().replace(/_ID_/g, id);
//                            $($(".list_azioni")[i]).html(html);
//                        }
//                    }
                });
    });
</script>
<div id="impostazioni_div">

    <div class="table-add-link">
        <a href="<%=path%>/ente/comuni/ricerca.htm">
            <spring:message code="ente.comuni.alt.aggiungi"/>
            <img src="<%=path%>/themes/default/images/icons/add.png" alt="<spring:message code="ente.comuni.alt.aggiungi"/>" title="<spring:message code="ente.comuni.alt.aggiungi"/>">
        </a>
    </div>
    <br />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
<!--    <div class="hidden" id="button">
        <button class="ui-button ui-widget ui-state-default ui-corner-all" onclick="eliminaComune(_ID_)" > 
            <spring:message code="ente.button.comune.cancella"/>
        </button>
    </div>-->
    <div class="buttonHolder">
        <a href="<%=path%>/ente/modifica.htm?codEnte=${codEnte}" class="secondaryAction">&larr; Indietro</a>
    </div>
</div>


<script>
    function eliminaComune(idComune) {
        var div = $("<div>");
        div.html("Confermi di voler eliminare il Comune selezionato?")
        $(div).dialog(
                {
                    modal: true,
                    title: "Conferma eliminazione Comune",
                    buttons: {
                        Ok: function() {
                            var form = $("<form>");
                            form.attr({
                                name: 'idComune',
                                style: 'display:none',
                                method: 'post',
                                action: "<%=path%>/ente/comuni/cancella.htm",
                                class: "gridForm"
                            });
                            form.append($('<input>',
                                    {
                                        'name': 'idComune',
                                        'value': idComune,
                                        'type': 'hidden'
                                    }));
                            $("body").append(form);
                            form.submit();
                            $(div).dialog('close');
                        },
                        Annulla: function() {
                            $(this).dialog('close');
                        }
                    }
                })
    }

</script>

