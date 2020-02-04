<%-- 
    Document   : lista_processi
    Created on : 21-Jan-2013, 12:15:51
    Author     : CS
--%>
<%
    String path = request.getContextPath();
    String url = path + "/processi/eventi/flusso/salva.htm";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
    var url = getUrl();
    $(document).ready(function()
    {

        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    colNames: ['id',
                        '<spring:message code="evento.step.codEventoResult"/>',
                        '<spring:message code="evento.step.desEventoResult"/>',
                        '<spring:message code="evento.step.operazione"/>',
                        '<spring:message code="evento.step.azione"/>'],
                    colModel:
                            [
                                {
                                    name: 'idEventoResult',
                                    index: 'idEventoResult',
                                    classes: 'idEventoResult',
                                    hidden: true
                                },
                                {
                                    name: 'codEventoResult',
                                    index: 'codEventoResult',
                                    sortable: false
                                },
                                {
                                    name: 'desEventoResult',
                                    index: 'desEventoResult',
                                    sortable: false
                                },
                                {
                                    name: 'operazione',
                                    index: 'operazione',
                                    sortable: false,
                                    formatter: function(cellvalue) {
                                        if (cellvalue == 'ADD') {
                                            cellvalue = 'Aggiungi evento';
                                        } else if (cellvalue == 'SUB') {
                                            cellvalue = 'Rimuovi evento';
                                        } else {
                                            cellvalue = '';
                                        }
                                        return cellvalue;
                                    }
                                },
                                {
                                    name: 'azione',
                                    index: 'idEventoResult',
                                    classes: "list_azioni",
                                    sortable: false,
                                    formatter: function(cellvalue, options, rowObject) {
                                        var link = '  <div  id="button"><form action="<%=url%>" method="post" class="gridForm"><input type="hidden" name="idEventoResult" value="' + rowObject["idEventoResult"] + '"><select name="step"><option value="">-</option><option value="ADD"><spring:message code="evento.step.operazione.add"/></option><option value="SUB"><spring:message code="evento.step.operazione.sub"/></option></select><button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"><spring:message code="evento.step.button.operazione"/></button></form></div>';
                                        return link;
                                    }
                                },
                            ],
                    rowNum: 30,
                    rowList: [30, 100],
                    pager: '#pager',
                    sortname: 'idEventoResult',
                    viewrecords: true,
                    sortorder: "desc",
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
//                            var idEventoTemplate = $("#" + i + " .idEventoResult").attr("title");
//                            var id = $(".list_azioni")[i].parentNode.id;
//                            var html = $("#button").html().replace(/_ID_/g, idEventoTemplate);
//                            $($(".list_azioni")[i]).html(html);
//                        }
//                    }
                });
    });
</script>
<div id="impostazioni_div">
<!--    <h2 style="text-align: center"><spring:message code="utenti.procedimenti.title"/>888</h2>-->
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
<!--    <div class="hidden" id="button">

        <form action="<%=url%>" method="post" class="gridForm">
            <input type="hidden" name="idEventoResult" value="_ID_">
            <select name="step">
                <option value="">-</option>
                <option value="ADD"><spring:message code="evento.step.operazione.add"/></option>
                <option value="SUB"><spring:message code="evento.step.operazione.sub"/></option>
            </select>
            <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
                <spring:message code="evento.step.button.operazione"/>
            </button>
        </form>
    </div>-->
    <div class="uniForm inlineLabels">
        <div class="buttonHolder">
            <a href="<%= path%>/processi/eventi/lista.htm" class="secondaryAction">&larr; <spring:message code="processo.button.indietro"/></a>
        </div>
    </div>
</div>