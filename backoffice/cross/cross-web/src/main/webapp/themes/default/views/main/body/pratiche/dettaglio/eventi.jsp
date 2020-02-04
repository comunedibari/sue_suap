<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    $(function () {
        $(".grid-btn").on("click", function () {
            $("#timeLineEventiDiv").addClass("hidden");
            $("#tableEventiDiv").removeClass("hidden");
            return false;
        });

        $(".time-line-btn").on("click", function () {
            $("#tableEventiDiv").addClass("hidden");
            $("#timeLineEventiDiv").removeClass("hidden");
            return false;
        });


        $("#tableEventi").jqGrid({
            url: "${path}/pratiche/dettaglio/eventi/ajax.htm?idPratica=" + $("#idPratica").attr("value"),
            datatype: "json",
            colNames: [
                'idPraticaEvento',
                'dataEvento',
                'descrizione',
                'protocollo',
                'data protocollo',
                'verso',
                'operatore',
                'soggetti',
                'statoMail',
                'azione'],
            colModel:
                    [{
                            name: 'idPraticaEvento',
                            index: 'idPraticaEvento',
                            hidden: true
                        },
                        {
                            name: 'dataEvento',
                            index: 'dataEvento',
                            srcformat: "NNN dd, y H:mm:ss a",
                            datefmt: 'dd/mm/y'
                        },
                        {
                            name: 'descrizioneEvento',
                            index: 'descrizioneEvento'
                        },
                        {
                            name: 'protocollo',
                            index: 'protocollo'
                        },
                        {
                            name: 'dataProtocollo',
                            index: 'dataProtocollo'
                        }
                        ,
                        {
                            name: 'verso',
                            index: 'verso'
                        },
                        {
                            name: 'denominazioneUtente',
                            index: 'denominazioneUtente'
                        },
                        {
                            name: 'soggetti',
                            width: 250,
                            index: 'soggetti'
                        },
                        {
                            name: 'statoMail',
                            index: 'statoMail',
                            classes: 'statoMail',
                            formatter: function (cellvalue, options, rowObject) {
                                var codiceEmail = rowObject['codStatoMail'];
                                if (codiceEmail === "E") {
                                    return '<div style="text-align: center; width: 100%; height: 100%;"><img title="Errore" alt="Errori" src="<%=path%>/themes/default/images/icons/exclamation.png" width="18" /></div>'
                                } else if (codiceEmail === "X") {
                                    return '<div style="text-align: center; width: 100%; height: 100%;"><img title="Stato indefinito" alt="Stato indefinito" src="<%=path%>/themes/default/images/icons/icon_alert.gif" width="18" /></div>'
                                } else {
                                    return '<div style="text-align: center; width: 100%; height: 100%;"><img title="Ok" alt="Ok" src="<%=path%>/themes/default/images/icons/accept.png" width="18" /></div>'
                                }
                            }
                        },
                        {
                            name: 'azione',
                            index: 'idPraticaEvento',
                            classes: "list_azioni",
                            sortable: false,
                            formatter: function (cellvalue, options, rowObject) {
                                var link = '<div class="gridActionContainer"><a href="${path}/pratica/comunicazione/dettaglio_evento.htm?id_pratica_evento=' + rowObject["idPraticaEvento"] + '&submit=Dettaglio" ><img src="${path}/themes/default/css/images/seleziona_verde.png"></a></div>';
                                return link;
                            }
                        }
                    ],
            rowList: [10, 20, 30, 100],
            pager: '#pager',
            /*
             <c:set var="page" value="1"/>
             
             */
            page: '${page}',
            /*
             <c:set var="orderColumn" value="idPraticaEvento"/>
             */
            sortname: '${orderColumn}',
            viewrecords: true,
            /*
             <c:set var="orderDirection" value="desc"/>
             */
            sortorder: "${orderDirection}",
            /*
             <c:set var="rowNum" value="10"/>
             */
            rowNum: "${rowNum}",
            jsonReader:
                    {
                        repeatitems: false,
                        id: "0"
                    },
            height: 'auto',
            width: $('.frames').width() - 2
        });

        $("#tableEventi").jqGrid('navGrid', '#pager',
                {edit: false, add: false, del: false, search: false, refresh: true});
    });
</script>

<form action="${path}/pratica/evento/index.htm" class="uniForm inlineLabels page" method="post">
    <div class="buttonHolder">
        <%-- ^^CS AGGIUNTA dopo aver premuto vai a pratica origine, do il permesso di rtornare indietro --%>
        <c:if test="${ritornaAidPratica!=null}">
            <input type="hidden" id="ritornaAidPratica" name="id_pratica" class="primaryAction" value="${ritornaAidPratica}"/>
            <input type="submit" id="indietroAidPratica" name="submit" class="primaryAction" value="Indietro" style="margin-right:73%"/>
        </c:if>
        <button class="grid-tab-btn grid-btn"></button>|
        <button class="grid-tab-btn time-line-btn"></button>        
        <%-- Visualizzo il pulsante solo se non sono in ricezione --%>
        <c:if test="${pratica.idStatoPratica.codice != 'R'}">
        	 <c:if test="${visualizzaAssegnazione eq true}"> 
	        	<a title="<spring:message code="gestione.button.riassegna"/>" href="<%=path %>/pratica/riassegna.htm?from=gestisci&idPratica=${pratica.idPratica}"><img src="${path}/themes/default/css/images/assegnare_verde.png" style="cursor: pointer"></a>
 	     	</c:if> 	        
            <input type="submit" name="submit" class="nascondiutente primaryAction crea_nuovo_evento" value="<spring:message code="pratica.button.evento"/>"/>
        </c:if>
    </div>
</form>
<!---->
<div id="tableEventiDiv">
    <div id="apertura_pratiche">
        <table id="tableEventi"></table> 
        <div id="pager"></div> 
    </div>
</div>
<!--</div>--> 

<div  id="timeLineEventiDiv" class="hidden">
    <div class="timelineCard tl" >
        <input type="hidden" id="idPratica" name="idPratica" type="text" disabled="" value="${pratica.idPratica}">
        <c:if test="${!empty pratica.praticheEventiList}">
            <c:forEach items="${pratica.praticheEventiList}" var="evento" begin="0">

                <div style="height:460px;" class="item" data-id="${evento.dataEvento}" data-description="${evento.descrizioneEvento}">
                    <div style="padding:10px;  height:460px;">

                        <div class="ctrlHolder">
                            <label class="required">
                                <spring:message code="pratica.comunicazione.evento.dataevento"/><!--Data evento-->:
                            </label>
                            <input class="input_anagrafica_disable" type="text" disabled="" value="${evento.dataEvento}">
                        </div>
                        <div class="ctrlHolder">
                            <label class="required">
                                <spring:message code="pratica.comunicazione.evento.descrizione"/><!--Descrizione-->:
                            </label>
                            <input class="input_anagrafica_disable" type="text" disabled="" value="${evento.descrizioneEvento}">
                        </div>
                        <div class="ctrlHolder">
                            <label class="required">
                                <spring:message code="pratica.comunicazione.dettaglio.protocollo"/>:
                            </label>
                            <input class="input_anagrafica_disable" type="text" disabled="" value="${evento.protocollo}">
                        </div>
                        <div class="ctrlHolder">
                            <label class="required">
                                <spring:message code="pratica.comunicazione.dettaglio.protocollo.data"/>:
                            </label>
                            <input class="input_anagrafica_disable" type="text" disabled="" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${evento.dataProtocollo}" />">
                        </div>
                        <div class="ctrlHolder">
                            <label class="required">
                                <spring:message code="pratica.comunicazione.evento.verso"/>
                            </label>
                            <c:choose>
                                <c:when test="${evento.verso==null||evento.verso=='I'}">
                                    <input class="input_anagrafica_disable" type="text" disabled="" value="<spring:message code="pratica.comunicazione.evento.verso.input"/>">
                                </c:when>
                                <c:otherwise>
                                    <input class="input_anagrafica_disable" type="text" disabled="" value="<spring:message code="pratica.comunicazione.evento.verso.output"/>">
                                </c:otherwise>                            
                            </c:choose>
                        </div>
                        <div class="ctrlHolder">
                            <label class="required">
                                <spring:message code="pratica.comunicazione.evento.operatore"/><!--Operatore-->:
                            </label>
                            <input class="input_anagrafica_disable" type="text" disabled="" value="${evento.idUtente.cognome} ${evento.idUtente.nome}">
                        </div>
                        <div class="ctrlHolder">
                            <label class="required">
                                <spring:message code="pratica.titolo.statoEmail"/>:
                            </label>
                            <div>
                                <c:if test="${evento.statoMail == null}">
                                    <div></div>
                                </c:if>
                                <c:if test="${evento.statoMail != null  && evento.statoMail.codice == 'E' }">
                                    <img style="float:none;" title="Errore" alt="Errori" src="${path}/themes/default/images/icons/icon_alert.gif" width="18">
                                </c:if>
                                <c:if test="${evento.statoMail != null && evento.statoMail.codice != 'E' }">
                                    <img style="float:none;" title="Errore" alt="Errori" src="${path}/themes/default/images/icons/accept.png" width="18">
                                </c:if>
                            </div>


                        </div>


                        <div class=" showdetail-evento"> 
                            <form action="${path}/pratica/comunicazione/dettaglio_evento.htm" class="uniForm inlineLabels action" method="post">
                                <input type="hidden" name="id_pratica_evento" value="${evento.idPraticaEvento}">
                                <input type="submit" name="submit" value="<spring:message code="pratica.comunicazione.evento.dettaglio"/>" class="button ui-state-default ui-corner-all cerca_lente_rosso"/>
                            </form>
                        </div>
                    </div>
                </div>

            </c:forEach>
        </c:if>
    </div>
</div>

