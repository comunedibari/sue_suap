<%-- 
    Document   : lista
    Created on : 18-Dec-2012, 11:29:52
    Author     : CS
--%>
<%
    String path = request.getContextPath();
    String url = path + "/gestione/template/modifica.htm";
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
</style>
<script type="text/javascript">
    var url = document.URL;
    url = url.replace(".htm", "/ajax.htm");
    $(document).ready(function()
    {
        $.jgrid.no_legacy_api = true;
        $.jgrid.useJSON = true;
        $.jgrid.defaults = $.extend($.jgrid.defaults, {loadui: "enable"});

        $("#list").jqGrid
                ({
                    url: url,
                    datatype: "json",
                    recreateForum: true,
                    colNames:
                            [
                                'ID',
                                'Template',
                                'Nome file generato',
                                'Tipologia di file generato',
                                'Azioni'
                            ],
                    colModel:
                            [
                                {
                                    name: 'idTemplate',
                                    index: 'idTemplate',
                                    hidden: true
                                },
                                {
                                    name: 'descrizioneTemplate',
                                    index: 'descrizioneTemplate',
                                    classes: "descrizioneTemplate",
                                    sortable: true

                                },
                                {
                                    name: 'nomeFile',
                                    index: 'nomeFile',
                                    classes: 'nomeFile',
                                    sortable: false
                                },
                                {
                                    name: 'mimeType',
                                    index: 'mimeType',
                                    classes: 'mimeType',
                                    sortable: false
                                },
                                {
                                    name: 'azioni',
                                    index: 'azioni',
                                    classes: "list_azioni",
                                    sortable: false,
                                      formatter: function(cellvalue, options, rowObject){
                          var link = '<div gridActionContainer"><a href="${path}/gestione/template/modifica.htm?idTemplate=' + rowObject["idTemplate"] + '" ><img src="${path}/themes/default/css/images/pencil.png""></a> <a href="#" onClick="eliminaTemplate('+rowObject["idTemplate"]+')" class="elimina_ente"><img src="${path}/themes/default/css/images/basket.png"></a>  <a class="scarica" href="<%=path%>/download/template.htm?idTemplate='+rowObject["idTemplate"]+'"><spring:message code="templates.gestione.documento.scarica"/></a></div>';
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
                     <c:set var="orderColumn" value="idTemplate"/>
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
                    rowList: [5, 10, 20, 30, 100],
                    pager: '#pager',
                    viewrecords: true,
                    jsonReader:
                            {
                                repeatitems: false,
                                id: "0"
                            },
                    height: 'auto',
                    width: $('.tableContainer').width(),
                    editurl: url + "?action=salva",
                    //Ora non funziona
//                    ondblClickRow: function(id)
//                    {
//                        modificaRiga(id);
//                    },
//                    gridComplete: function() {
//                        var ids = $(".list_azioni");
//                        for (var i = 0; i < ids.length; i++) {
//                            var id = $(".list_azioni")[i].parentNode.id;
//                            var html = $("#button").html().replace(/_ID_/g, id);
//                            $($(".list_azioni")[i]).html(html);
//                        }
//                    },
//                    loadComplete: function() {
//                        $("button.elimina").click(function(event, form)
//                        {
//                            $($(this).parent()).unbind();
//                            $($(this).parent()).submit(function(event, form)
//                            {
//                                $(".loading").css("display", "block");
//                                $("#lui_list").css("display", "block");
//                                $.ajax({
//                                    type: 'POST',
//                                    url: url,
//                                    data: $(this).serialize(),
//                                    success: function(data)
//                                    {
//                                        if (data != null && data.errors != null)
//                                        {
//                                            alert(data.errors)
//                                        }
//
//                                        $("#lui_list").css("display", "none");
//                                        $("#list").trigger('reloadGrid');
//
//                                    },
//                                    dataType: "json",
//                                    async: false
//                                });
//                                return false;
//                            });
//                        });
//                    }
                });


    });
</script>
<div id="impostazioni_div">
    <tiles:insertAttribute name="operazioneRiuscitaAjax" />
    <tiles:insertAttribute name="operazioneRiuscita" />

    <%--    <h2 style="text-align: center"><spring:message code="template.descrizione"/>120</h2> --%>

    <div class="table-add-link">
        <a href="<%=url%>" class="addgenerico" title="<spring:message code="templates.gestione.documento.carica"/>">
            <spring:message code="templates.gestione.documento.carica"/>

        </a>
<!--         <img title="<spring:message code="templates.gestione.documento.carica"/>" alt="<spring:message code="templates.gestione.documento.carica"/>" src="<%=path%>/themes/default/images/icons/add.png"> -->


    </div>
    <br/>
    <tiles:insertAttribute name="ricerca" />
    <div class="tableContainer">
        <table id="list"><tr><td/></tr></table> 
        <div id="pager"></div> 
    </div>


    <!--    <div class="hidden" id="button">
            <form action="<%=path%>/template/modifica.htm" method="post" style="float:left" >
                <input type="hidden" name="idTemplate" value="_ID_">
                <button type="submit" class="modifica_ente ui-button ui-widget ui-corner-all ui-button-text-only" name="submit" value="modifica" title="<spring:message code="button.modifica"/>">
                </button>
            </form>    
    
            <form class="gridForm">  
                <button onclick="eliminaTemplate(_ID_);
            return false;" class="cancella_ente ui-button ui-widget ui-corner-all ui-button-text-only cancella_ente" title="<spring:message code="ente.alt.cancella"/>"> ui-state-default 
                </button>
            </form>
            <input type="hidden" name="action" value="elimina">
            <button type="submit" class="cancella_ente elimina ui-button ui-widget ui-corner-all ui-button-text-only" name="submit" value="elimina" title="<spring:message code="utenti.button.elimina"/>">
    
            </button>
            <a class="scarica" href="<%=path%>/download/template.htm?idTemplate=_ID_"><spring:message code="templates.gestione.documento.scarica"/></a>
    
        </div>-->
<!--    <div class="hidden" id="button">-->
       
<!--        <div class="centra_impostazzioni_bottoni">
            <form action="<%=path%>/gestione/template/modifica.htm" method="post" class="gridForm">
                <input type="hidden" name="idTemplate" value="_ID_">
                <button type="submit" class="modifica_ente ui-button ui-widget ui-corner-all ui-button-text-only " title="<spring:message code="template.modifica"/>">  ui-state-default  

                </button>
            </form>
            <form class="gridForm">  
                <button onclick="eliminaTemplate(_ID_);
        return false;" class="cancella_ente ui-button ui-widget ui-corner-all ui-button-text-only cancella_ente" title="<spring:message code="template.elimina"/>"> ui-state-default 
                </button>
            </form>
                 <a class="scarica" href="<%=path%>/download/template.htm?idTemplate=_ID_"><spring:message code="templates.gestione.documento.scarica"/></a>-->

            <script type="text/javascript" charset="utf-8">
    $(function() {
        $("#gridForm__ID_").preventDoubleSubmission('Conferma cancellazione', 'Proseguo ?', 'Attendi ...');
    });
            </script>
<!--        </div>-->
    </div>
                
    <div class="uniForm inlineLabels">
        <div class="buttonHolder">
            <a href="<%=path%>/gestione/eventoTemplate/lista.htm" class="secondaryAction">&larr; <spring:message code="templates.button.indietro"/></a>
        </div>
    </div> 
</div>


<script>
    function eliminaTemplate(idTemplate)
    {
        createDialog("Conferma cancellazione", "Proseguo con la cancellazione del record?", function() {
            $(".loading").css("display", "block");
            $("#lui_list").css("display", "block");
            $.post(url, {
                action: "elimina",
                idTemplate: idTemplate
            },
            function(data) {
                if (data.errors != null)
                {
                    alert(data.errors);
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

</script>
