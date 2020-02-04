<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">

    function disableIdSearchComune(type) {
        $("#search_id_comune_" + type).val('');
        $("#search_id_comune_" + type).attr('disabled', 'disabled');
    }

    function enableIdSearchComune(type) {
        $("#search_id_comune_" + type).val('');
        $("#search_id_comune_" + type).removeAttr('disabled');
    }


    function disableSearchComuneComponent(type) {
        $("#search_des_comune_" + type).val('');
        $("#search_des_comune_" + type).attr('disabled', 'disabled');
        $("#search_button_" + type).attr('disabled', 'disabled');
        disableIdSearchComune(type);
    }

    function enableSearchComuneComponent(type) {
        $("#search_des_comune_" + type).val('');
        $("#search_des_comune_" + type).removeAttr('disabled');
        $("#search_button_" + type).removeAttr('disabled');
        disableIdSearchComune(type);
    }

    $(document).ready(function () {
        disableIdSearchComune('pratica');
        disableIdSearchComune('catasto');
        $("#search_des_comune_pratica").val('');

        $("#ricerca").accordion({
            collapsible: true,
            alwaysOpen: false,
            active: false
        });

//         $('#search_button_pratica').click(function () {
//             enableSearchComuneComponent('pratica');
//             enableSearchComuneComponent('catasto');
//         });

      

        $('#search_des_comune_pratica').change(function () {
            var content = $("#search_des_comune_pratica").val();
            if (content.length > 0) {
                enableSearchComuneComponent('pratica');
                disableSearchComuneComponent()('catasto');
            } else {
                enableSearchComuneComponent('pratica');
                enableSearchComuneComponent('catasto');
            }
        });

        $("#ricerca_button").click(function () {
            //^^CS ELIMINA provoca questa stringa ..../dettaglio_search.htmsearch_id_pratica=asd&search_ente...
            //$('#list').setGridParam({url:url+$('#form_ricerca').serialize()});
            if($("#search_data_from").val().trim().length != 0 && $("#search_data_to").val().trim().length != 0 && $("#search_ente").val().trim().length != 0){
	            var urli = $('#list').getGridParam("url").split("?")[0];
	            $('#list').setGridParam({url: urli + "?" + $('#form_ricerca').serialize()});
	            $('#list').trigger("reloadGrid", [{page: 1}]);
            }
            else{
            	alert("Inserisci tutti i campi di ricerca");
            }
        });

        $("#search_data_from").datepicker({
            dateFormat: 'dd/mm/yy'
        });

        $("#search_data_to").datepicker({
            dateFormat: 'dd/mm/yy'
        });

        $('#search_des_comune_pratica').autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "<%= path%>/search/comune.htm",
                    dataType: "json",
                    data: {
                        description: $("#search_des_comune_pratica").val(),
                        dataValidita: ''
                    },
                    success: function (data) {
                        response($.map(data, function (item) {
                            return {
                                label: item.descrizione + "(" + item.provincia.codCatastale + ")",
                                value: item.descrizione + "(" + item.provincia.codCatastale + ")",
                                id: item.idComune
                            }
                        }));
                    }
                });
            },
            select: function (event, ui) {
                disableSearchComuneComponent('catasto');
                enableIdSearchComune('pratica');
                $('#search_id_comune_pratica').val(ui.item.id);
            },
            minLength: 2
        });

        
    });


</script>
<div id="ricerca">
    <h3><a href="#"><spring:message code="ricerca.title"/></a></h3>
    <div>
        <form id="form_ricerca" action="<%= path%>/pratiche/comunicazione/dettaglio_search.htm" class="uniForm inlineLabels" method="post">
            <input name="tipoFiltro" type="hidden" value="utente" />
            <div style="width:1080px;">    


                <div class="ricerca_div_forms">    
                    <div class="inlineLabels">
                        <fieldset id="praticaField">
                            <legend><spring:message code="ricerca.label.pratica"/></legend>
                            
                             <div class="ctrlHolder" id="divSearch_data_from">
                                <label class="required" for="search_data_from"><spring:message code="ricerca.datainizio"/></label>
                                <input id="search_data_from" name="search_data_from" size="35" maxlength="50" type="text" class="textInput" value="<fmt:formatDate pattern="dd/MM/yyyy"   value="${filtroRicerca.dataInizio}"/>">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="divSearch_data_to">
                                <label class="required" for="search_data_to"><spring:message code="ricerca.datafine"/></label>
                                <input id="search_data_to" name="search_data_to" size="35" maxlength="50" type="text" class="textInput" value="<fmt:formatDate pattern="dd/MM/yyyy"   value="${filtroRicerca.dataFine}"/>">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="divSearch_ente">
                                <label class="required" for="search_ente"><spring:message code="ricerca.ente"/></label>
                                <select id="search_ente" name="search_ente" class="textInput">
                                    <option  value="" ><spring:message code="ricerca.entestart"/></option>
                                    <c:forEach items="${entiRicerca}" var="ente" begin="0">
                                        <c:set var="selected" value=""/> 
                                        <c:if test="${filtroRicerca.enteSelezionato != null &&  filtroRicerca.enteSelezionato.idEnte == ente.idEnte}">
                                            <c:set var="selected" value="selected=\"selected\""/>     
                                        </c:if>
                                        <option  value="${ente.idEnte}" ${selected}>${ente.descrizione}</option>
                                    </c:forEach>
                                </select>
                                <p class="formHint"></p>
                            </div> 
                            <%-- <div class="ctrlHolder" id="divSearch_des_comune_pratica">
                                <label for="search_des_comune_pratica"><spring:message code="ricerca.comune"/></label>
                                <input id="search_des_comune_pratica" name="search_des_comune" size="35" maxlength="255" type="text" style="width: 60%;" class="textInput" value="${filtroRicerca.desComune}">
                                <input id="search_id_comune_pratica" name="search_id_comune" size="35" maxlength="35" type="hidden" class="textInput" value="${filtroRicerca.idComune}">
                                <button type="button" id="search_button_pratica" style="
                                        margin-left: 10px;
                                        margin-bottom: 0px;
                                        margin-top: 0px;
                                        float: left;
                                        ">X</button>
                                <p class="formHint"></p>
                            </div> --%>          
                           
                        </fieldset>                        
                    </div>

                </div>
                <div class="buttonHolder" style="background-color: #E6E6E6;text-align: center">
                    <button id="ricerca_button" type="button" class="primaryAction fondo_destra" style="background-color: #0067A9"><spring:message code="ricerca.button.cerca"/></button>				
                </div>
            </div>
        </form>
    </div>
</div>