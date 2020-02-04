<%-- 
    Document   : lista
    Created on : 18-Dec-2012, 11:29:52
    Author     : CS
--%>
<%
    String path = request.getContextPath();
    String url = path + "/template/gestione.htm";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
    select{
        width:250px;
    }
    #descProcedimento{
        height: 19px;    
    }
</style>
<script type="text/javascript">
    //^^CS AGGIUNTA
    var url = document.URL;
    url = url.replace(".htm", "/ajax.htm");
    $(document).ready(function()
    {
        $.jgrid.no_legacy_api = true;
        $.jgrid.useJSON = true;
        $.jgrid.defaults = $.extend($.jgrid.defaults, {loadui: "enable"});
        $(".Nuovo").hide();
        $(".Modifica").hide();
        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    recreateForum: true,
                    colNames:
                            [
                                'id',
                                'evento',
                                'Evento',
                                'Processo',
                                'Ente',
                                'idEnte',
                                'Procedimento',
                                'idProcedimento',
                                'idProcesso',
                                'idTemplate',
                                'Template'
                            ],
                    colModel:
                            [
                                {
                                    name: 'idEventoTemplate',
                                    index: 'idEventoTemplate',
                                    hidden: true,
                                    classes: 'idEventoTemplate'
                                },
                                {
                                    name: 'idEvento',
                                    index: 'idEvento',
                                    classes: "idEvento",
                                    sortable: false,
                                    hidden: true
                                },
                                {
                                    name: 'descEvento',
                                    index: 'descEvento',
                                    classes: 'descEvento',
                                    editable: true
                                },
                                {
                                    name: 'descProcesso',
                                    index: 'descProcesso',
                                    classes: 'descProcesso',
                                    editable: false,
                                    sortable: false
                                },
                                {
                                    name: 'descEnte',
                                    index: 'descEnte',
                                    classes: 'descEnte'

                                },
                                {
                                    name: 'idEnte',
                                    index: 'idEnte',
                                    classes: 'idEnte',
                                    sortable: false,
                                    hidden: true
                                },
                                {
                                    name: 'descProcedimento',
                                    index: 'descProcedimento',
                                    classes: 'descProcedimento',
                                    width: 90,
                                    sortable: true,
                                    resizable: false
                                },
                                {
                                    name: 'idProcesso',
                                    index: 'idProcesso',
                                    classes: "idProcesso",
                                    sortable: false,
                                    hidden: true
                                },
                                {
                                    name: 'idProcedimento',
                                    index: 'idProcedimento',
                                    classes: "idProcedimento",
                                    sortable: false,
                                    hidden: true
                                },
                                {
                                    name: 'idTemplate',
                                    index: 'idTemplate',
                                    classes: "idTemplate",
                                    sortable: false,
                                    hidden: true
                                },
                                {
                                    name: 'descTemplate',
                                    index: 'descTemplate',
                                    classes: 'descTemplate'
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
                     <c:set var="orderColumn" value="descEvento"/>
                     <c:if test="${filtroRicerca!=null && filtroRicerca.orderColumn!=null}">
                     <c:set var="orderColumn" value="${filtroRicerca.orderColumn}"/>
                     </c:if>
                     */
                    sortname: '${orderColumn}',
                    /*
                     <c:set var="orderDirection" value="desc"/>
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
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width(),
                    editurl: url + "?action=salva",
                    ondblClickRow: function(id)
                    {
                        modificaRiga(id);
                    }
                });
        $("#list").jqGrid('navGrid', "#pager", {search: false, edit: true, add: true, save: false, del: true, cancel: false});
        //$("#list").jqGrid('inlineNav',"#pager");
        //^^CS brutta cosa NON FARLO MAI!!
        $("#add_list").unbind();
        $("#add_list").click(function(event) {
            aggiungiRiga();
        })
        $("#edit_list").unbind();
        $("#edit_list").click(function(event) {
            var id = $("tr.ui-state-highlight").attr("id");
            if (id == undefined)
            {
                alert("selezionare una riga");
            }
            else
            {
                modificaRiga(id);
            }
        })
        $("#del_list").unbind();
        $("#del_list").click(function(event) {
            var id = $("tr.ui-state-highlight").attr("id");
            if (id == undefined)
            {
                alert("selezionare una riga");
            }
            else
            {
                eliminaRiga(id);
            }
        });
        $("#listaProcessiDaSelezionare").chained("#listaEntiDaSelezionare");
        $("#listaProcedimentiDaSelezionare").chained("#listaEntiDaSelezionare");
        $("#listaProcessiEventiDaSelezionare").chained("#listaProcessiDaSelezionare");
    });
    function modificaRiga(id)
    {

        var idEventoTemplate = $("#" + id + " .idEventoTemplate").attr("title");
        var idEvento = $("tr#" + id + " .idEvento").attr("title");
        var idEnte = $("tr#" + id + " .idEnte").attr("title");
        var idProcedimento = $("tr#" + id + " .idProcedimento").attr("title");
        var idProcesso = $("tr#" + id + " .idProcesso").attr("title");
        var idTemplate = $("tr#" + id + " .idTemplate").attr("title");
        var descEvento = $("tr#" + id + " .descEvento").attr("title");
        var descEnte = $("tr#" + id + " .descEnte").attr("title");
        var descProcedimento = $("tr#" + id + " .descProcedimento").attr("title");
        var descProcesso = $("tr#" + id + " .descProcesso").attr("title");
        var descTemplate = $("tr#" + id + " .descTemplate").attr("title");
        $(".Modifica #idEventoTemplate").val(idEventoTemplate);
        $(".Modifica #idEvento").val(idEvento);
        $(".Modifica #idEnte").val(idEnte);
        $(".Modifica #idProcedimento").val(idProcedimento);
        $(".Modifica #idProcesso").val(idProcesso);
        $(".Modifica #idTemplateM").val(idTemplate);
        $(".Modifica #descEvento").text(descEvento);
        $(".Modifica #descEnte").text(descEnte);
        $(".Modifica #descProcedimento").text(descProcedimento);
        $(".Modifica #descProcesso").text(descProcesso);
        $(".Modifica").dialog(
                {
                    modal: true,
                    width: '50%',
                    height: "auto",
                    title: "Modifica " + descTemplate,
                    buttons: {
                        Ok: function() {
                            $(".loading").css("display", "block");
                            $("#lui_list").css("display", "block");
                            var enteSelezionato = $(".Modifica #idEnte").val();
                            var processoSelezionato = $(".Modifica #idProcesso").val();
                            var processoEventoSelezionato = $(".Modifica #idEvento").val();
                            var templateSelezionato = $(".Modifica #idTemplateM").val();
                            if (!enteSelezionato || !processoSelezionato || !processoEventoSelezionato || !templateSelezionato) {
                                alert("Compilare i campi obbligatori");
                            } else  {
                                var dialog = this;
                                aggiornaDesTemplate("modificaform", "idTemplateM");
                                $.post(url, $(".Modifica form").serialize(),
                                        function(data) {
                                            if (data.errors != null) {
                                                alert(data.errors)
                                            } else {
                                                mostraMessaggioAjax(data.messages[0]);
                                            }
                                            $("#lui_list").css("display", "none");
                                            $(dialog).dialog('close');
                                            $("#list").trigger('reloadGrid');
                                        },
                                        "json");
                            }
                        },
                        Annulla: function() {
                            $(".loading").css("display", "none");
                            $("#lui_list").css("display", "none");
                            $(this).dialog('close');
                        }
                    }
                });
        $(".Modifica").show();
    }

     function eliminaRiga(id)
    {
        createDialog("Conferma cancellazione", "Proseguo con la cancellazione del record?", function() {

            var idEventoTemplate = $("#" + id + " .idEventoTemplate").attr("title");
            var idEvento = $("#" + id + " .idEvento").attr("title");
            var idEnte = $("#" + id + " .idEnte").attr("title");
            var idProcedimento = $("#" + id + " .idProcedimento").attr("title");
            var idTemplate = $("#" + id + " .idTemplate").attr("title");

            $(".loading").css("display", "block");
            $("#lui_list").css("display", "block");
            $.post(url, {
                action: "elimina",
                idEventoTemplate: idEventoTemplate,
                idEvento: idEvento,
                idEnte: idEnte,
                idProcedimento: idProcedimento,
                idTemplate: idTemplate
            },
            function(data) {
                if (data.errors != null)
                {
                    alert(data.errors)
                }
                else
                {
                    $("#lui_list").css("display", "none");
                    $("#list").trigger('reloadGrid');               
                    mostraMessaggioAjax(data.messages[0]);
                }
            }, "json");
        }, 160);
    }

    function aggiungiRiga()
    {
        $(".Nuovo").dialog(
                {
                    modal: true,
                    width: '50%',
                    height: "auto",
                    title: "Aggiungi Riga",
                    buttons: {
                        Ok: function() {
                            $(".loading").css("display", "block");
                            $("#lui_list").css("display", "block");
                            var enteSelezionato = $(".Nuovo #listaEntiDaSelezionare").val();
                            var processoSelezionato
                                    = $(".Nuovo #listaProcessiDaSelezionare").val();
                            var processoEventoSelezionato = $(".Nuovo #listaProcessiEventiDaSelezionare").val();
                            var templateSelezionato = $(".Nuovo #idTemplateA").val();
                            if (!enteSelezionato || !processoSelezionato || !processoEventoSelezionato || !templateSelezionato) {
                                alert("Compilare i campi obbligatori");
                            } else {
                                var dialog = this;
                                 aggiornaDesTemplate("nuovoform","idTemplateA");
                                $.post(url, $(".Nuovo form").serialize(),
                                        function(data) {
                                            if (data.errors != null) {
                                                alert(data.errors)
                                            } else {
                                                mostraMessaggioAjax(data.messages[0]);
                                            }
                                            $("#lui_list").css("display", "none");
                                            $(dialog).dialog('close');
                                            $("#list").trigger('reloadGrid');
                                        },
                                        "json");
                            }
                        },
                        Annulla: function() {
                            $(".loading").css("display", "none");
                            $("#lui_list").css("display", "none");
                            $(this).dialog('close');
                        }
                    }
                });
        $(".Nuovo").show();
    }
</script>   
<tiles:insertAttribute name="operazioneRiuscitaAjax" />  
<div id="impostazioni_div">
    <%-- <h2 style="text-align: center"><spring:message code="template.descrizione"/>110</h2>--%>
    <div class="table-add-link">
        <a href="<%= path%>/gestione/template/lista.htm" class="addgenerico" alt="<spring:message code="templates.gestione"/>" title="<spring:message code="templates.gestione"/>">
            <spring:message code="templates.gestione"/>
        </a>
    </div>
    <br/>

    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>
    <div class="hidden" id="button">
        <form action="<%=url%>" method="post" >
            <input type="hidden" name="idTemplate" value="_ID_">
            <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
                <spring:message code="button.modifica"/>
            </button>
        </form>
    </div>

    <div class="Nuovo">
        <form id="nuovoform" class="uniForm inlineLabels comunicazione">
            <div class="inlineLabels">
                <input type="hidden" name="action" value="salva" />
                <input type="hidden" name="idEventoTemplate" value="" />
                <div class="ctrlHolder">
                    <div class="listaEnti">
                        <label><spring:message code="templates.gestione.ente"/></label>
                        <select id="listaEntiDaSelezionare" name="idEnte" class="textInput required">
                            <option><spring:message code="templates.gestione.ente.select"/></option>
                            <c:set value="0" var="i"/>
                            <c:forEach begin="0" items="${listaEnti}" var="ente">
                                <option value="${ente.idEnte}" >${ente.descrizione}</option>
                                <c:set value="${i+1}" var="i"/>
                            </c:forEach>
                        </select>                    
                    </div>
                </div>
                <div class="ctrlHolder">
                    <div class="listaProcessi">
                        <label><spring:message code="templates.gestione.processo"/></label>
                        <select id="listaProcessiDaSelezionare" name="idProcesso" class="textInput required">
                            <option value=""><spring:message code="templates.gestione.processo.select"/></option>
                            <c:set value="0" var="i"/>
                            <c:forEach begin="0" items="${listaProcessi}" var="processo">
                                <option value="${processo.idProcesso}" class="${processo.idEnte}" >${processo.desProcesso}</option>
                                <c:set value="${i+1}" var="i"/>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="ctrlHolder">
                    <div class="listaEventi">
                        <label><spring:message code="templates.gestione.evento"/></label>
                        <select id="listaProcessiEventiDaSelezionare" name="idEvento" class="textInput required">
                            <option value=""><spring:message code="templates.gestione.evento.select"/></option>
                            <c:set value="0" var="i"/>
                            <c:forEach begin="0" items="${listaEventi}" var="evento">
                                <option  value="${evento.idEvento}" class="${evento.idProcesso}" name="${evento.idEvento}" >${evento.descrizione}</option>
                                <c:set value="${i+1}" var="i"/>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="ctrlHolder">
                    <div class="listaProcedimenti">
                        <label><spring:message code="templates.gestione.procedimento"/></label>
                        <select id="listaProcedimentiDaSelezionare" name="idProcedimento" class="textInput required">
                            <option value=""><spring:message code="templates.gestione.procedimento.select"/></option>
                            <c:set value="0" var="i"/>
                            <c:forEach begin="0" items="${listaProcedimenti}" var="procedimento">
                                <option value="${procedimento.idProcedimento}" class="${procedimento.idEnteDestinatario}">${procedimento.desProcedimento}</option>
                                <c:set value="${i+1}" var="i"/>
                            </c:forEach>
                        </select>                    
                    </div>
                </div>
                <div class="ctrlHolder">
                    <div class="listaTemplate">
                        <label><spring:message code="templates.gestione.template"/></label>
                        <select id="idTemplateA" name="idTemplate" class="textInput required">
                            <option><spring:message code="templates.gestione.template.select"/></option>
                            <c:forEach begin="0" items="${listaTemplate}" var="template">
                                <option value="${template.idTemplate}" >${template.descrizioneTemplate}</option>
                            </c:forEach>
                        </select> 
                    </div>  
                </div>
            </div>
        </form>
    </div>

    <div class="Modifica">
        <form id="modificaform" class="uniForm inlineLabels comunicazione">
            <div class="inlineLabels">
                <input type="hidden" name="action" value="modifica" />
                <input type="hidden" name="idEventoTemplate" id="idEventoTemplate"  value="" />  
                <div class="ctrlHolder">
                    <div class="listaEnti">
                        <label><spring:message code="templates.gestione.ente"/></label>
                        <span id="descEnte" class="textInput required">&nbsp;</span>
                        <input id="idEnte" name="idEnte" value="" type="hidden"/>
                    </div>
                </div>
                <div class="ctrlHolder">
                    <div class="listaProcessi">
                        <label><spring:message code="templates.gestione.processo"/></label>
                        <span id="descProcesso" class="textInput required">&nbsp;</span>
                        <input id="idProcesso" name="idProcesso" value="" type="hidden"/>
                    </div>
                </div>
                <div class="ctrlHolder">
                    <div class="listaEventi">
                        <label><spring:message code="templates.gestione.evento"/></label>
                        <span id="descEvento" class="textInput required">&nbsp;</span>
                        <input id="idEvento" name="idEvento" value="" type="hidden"/>
                    </div>
                </div>
                <div class="ctrlHolder">
                    <div class="listaProcedimenti">
                        <label><spring:message code="templates.gestione.procedimento"/></label>
                        <span id="descProcedimento" class="textInput required">&nbsp;</span>
                        <input id="idProcedimento" name="idProcedimento" value="" type="hidden"/>
                    </div>
                </div>
                <div class="ctrlHolder">
                    <div class="listaTemplate">
                        <label><spring:message code="templates.gestione.template"/></label>
                        <select id="idTemplateM" name="idTemplate" class="textInput required">
                            <option><spring:message code="templates.gestione.template.select"/></option>
                            <c:forEach begin="0" items="${listaTemplate}" var="template">
                                <option value="${template.idTemplate}" >${template.descrizioneTemplate}</option>
                            </c:forEach>
                        </select> 
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    function aggiornaDesTemplate(idform, selectid) {
        var selected = $("#"+selectid+" option:selected").text();
        $("#"+idform).append($('<input>',
                {
                    'name': 'descTemplate',
                    'value': selected,
                    'type': 'hidden'
                }));
    }
</script>