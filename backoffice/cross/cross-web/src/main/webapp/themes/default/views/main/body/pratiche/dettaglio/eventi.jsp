<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
	var rowsAutoToColor = [];
	function eventDescriptionFormatterHander(cellvalue, options, rowObject) {
        var descrizioneEvento = rowObject["descrizioneEvento"] || ""
        var dataEvento = rowObject["dataEvento"]|| ""
		var cell= "<span>";
        cell+=descrizioneEvento+"<br/>";
        cell+=dataEvento;
        cell+="</span>";
		return cell;
    }

	function protocolFormatterHander(cellvalue, options, rowObject) {
    	if (rowObject["codEvento"] === "RIASPRATAUTO"){
            rowsAutoToColor[rowsAutoToColor.length] = options.rowId;
    	}
		var protocollo = rowObject["protocollo"] || '<spring:message code="evento.missing.user" />';
		if (rowObject["flgProtocollazione"] == 'N')
			protocollo = '<spring:message code="evento.user.not.provided" />';
		
			var dataProtocollo = rowObject["dataProtocollo"] || "";
        var cell= "<span>";
        cell+=protocollo + "<br/>";
        cell+=dataProtocollo;
        cell+="</span>";
		return cell;
    }

	function userFormatterHander(cellvalue, options, rowObject) {
		var denominazioneUtente = rowObject["denominazioneUtente"];
        if(!denominazioneUtente)
        	denominazioneUtente = '<spring:message code="evento.automatico" />'
		return denominazioneUtente;
    }
	
	function attachmentFormatterHander(cellvalue, options, rowObject) {
		var found = false;
		var mainAttachment = null;
		var cell ="";
		if(rowObject["allegati"].length > 0){
			mainAttachment = rowObject["allegati"].find((element) => element.principale ==="S");;
			found = !!mainAttachment;
		}
		if(found){
			cell = '<a href="/cross/preview.htm?id_file='+mainAttachment["id"]+'" target="_blank">'+mainAttachment["nomeFile"]+'</a>'
		} else{
			cell = rowObject["note"] || "";
		}
		return cell;
    }
	
    function register_papework_event(idPraticaEvento){
    	$.ajax({
             type: 'POST',
             url: '<%=path%>/pratica/comunicazione/register/ajax.htm',
             data: {"idPraticaEvento": idPraticaEvento}
        }).done(function(data) {
        	if(!!data.errors)
            	alert(data.errors[0])
           	else
	            location.reload();
        });
    	return false;
    }
	$(document).ready(function() {

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
    	function personalDataSorting3(a,b,o){
    		return o*(a==b? 0 : (a>b?1:-1));
    	}
    	$("#tableEventi").jqGrid({
            url: "${path}/pratiche/dettaglio/eventi/ajax.htm?idPratica=" + $("#idPratica").attr("value"), 
            datatype: "json",
            colNames: [
                'idPraticaEvento',
                '<spring:message code="pratica.comunicazione.evento.tipoEvento" /><br/><spring:message code="pratica.comunicazione.evento.dataevento" />',
                '<spring:message code="pratica.comunicazione.dettaglio.protocollo" /><br/><spring:message code="pratica.comunicazione.dettaglio.protocollo.data" />',
                'verso',
                'operatore',
                'soggetti',
                'contenuto',
                'statoMail',
                <c:if test="${utenteConnesso.idUtente == 1 && isProd}">
                'Operazioni',
                </c:if>
                ],
            colModel:
                    [{
                            name: 'idPraticaEvento',
                            index: 'idPraticaEvento',
                            hidden: true,
                        },
                        {
                        	sortable : false,
                        	hidden : false,
                        	width : 180,
                        	autowidth: true,
                        	formatter: eventDescriptionFormatterHander

                        },
                        {
                        	sortable : false,
                        	hidden : false,
                        	width : 180,
                        	autowidth: true,
                        	formatter: protocolFormatterHander
                        },
                        {
                            name: 'verso',
                            index: 'verso'
                        },
                        {
                            name: 'denominazioneUtente',
                           	formatter: userFormatterHander,
                           	sorttype : "String",
               	        	sortfunc : personalDataSorting3
                        },
                        {
                            name: 'soggetti',
                            width: 250,
                            index: 'soggetti'
                        },
                        {
                        	sortable : false,
                        	hidden : false,
                        	width : 180,
                        	autowidth: true,
                        	formatter: attachmentFormatterHander
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
                        }
                        <c:if test="${utenteConnesso.idUtente == 1 && isProd}">
                        ,{
                            formatter: function (cellvalue, options, rowObject) {
                            	var button = "";
                            	var protocollo = rowObject["protocollo"] || "";
                      	       	if(rowObject["codEvento"] == "PROCINT" && protocollo.length == 0)
                            		button = '<a href="javascript:void(0)" title="Protocolla" onclick="javascript:register_papework_event('+rowObject["idPraticaEvento"]+')" class="riassegna2"></a>&nbsp';
                      	       	
								return button
                            }
                        }
                        </c:if>
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
            ondblClickRow : function(rowid, status, e){ 
            	var myGrid = $('#tableEventi'),
            	selRowId = myGrid.jqGrid ('getGridParam', 'selrow'),
            	idPraticaEvento = myGrid.jqGrid ('getCell', selRowId, 'idPraticaEvento');
            	$("#dettaglio_evento_form #idPraticaEvento").val(idPraticaEvento);
                $("#dettaglio_evento_form").trigger('submit');
            	
            },
            gridComplete: function () {
                for (var i = 0; i < rowsAutoToColor.length; i++) {
                	$("#tableEventi").find("tr#" + rowsAutoToColor[i]).find("td").css("background-color", "#C5DBEC");
                	$("#tableEventi").find("tr#" + rowsAutoToColor[i]).find("td").css("color", "black");
                }
                rowsAutoToColor = []
            },
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

        <c:if test="${ritornaAidPratica!=null}">
            <input type="hidden" id="ritornaAidPratica" name="id_pratica" class="primaryAction" value="${ritornaAidPratica}"/>
            <input type="submit" id="indietroAidPratica" name="submit" class="primaryAction" value="Indietro" style="margin-right:73%"/>
        </c:if>
        <button class="grid-tab-btn grid-btn"></button>|
        <button class="grid-tab-btn time-line-btn"></button>        
        <%-- Visualizzo il pulsante solo se non sono in ricezione --%>
        <c:if test="${!abilitazioneConsultazione && pratica.idStatoPratica.codice != 'R'}">
        	 <c:if test="${visualizzaAssegnazione eq true}"> 
	        	<a title="<spring:message code="gestione.button.riassegna"/>" href="<%=path %>/pratica/riassegna.htm?from=gestisci&idPratica=${pratica.idPratica}"><img src="${path}/themes/default/css/images/assegnare_verde.png" style="cursor: pointer"></a>
 	     	</c:if> 	        
            <input type="submit" name="submit" class="nascondiutente primaryAction crea_nuovo_evento" value="<spring:message code="pratica.button.evento"/>"/>
        </c:if>
    </div>
    <input type="hidden" name="id_pratica"  value="${pratica.idPratica}"/>
</form>

<!---->
<div id="tableEventiDiv">
    <div id="apertura_pratiche" class="jqtable_eventi">
        <table id="tableEventi"></table> 
        <div id="pager"></div> 
    </div>
</div>

<form id="dettaglio_evento_form" class="hide" action="${path}/pratica/comunicazione/dettaglio_evento.htm" >
	<input type="hidden" id="idPraticaEvento" name="id_pratica_evento" />
</form>


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
