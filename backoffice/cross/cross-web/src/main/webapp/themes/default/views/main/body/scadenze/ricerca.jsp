<%@page import="it.wego.cross.constants.SessionConstants"%>
<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    $(document).ready(function() {
        $("#ricerca").accordion({
            collapsible: true,
            alwaysOpen: false,
            active: false
        });

        $("#ricerca_button").click(function() {
            $('#list').setGridParam({url: url + "?" + $('#form_ricerca').serialize()});
            $('#list').trigger("reloadGrid", [{page: 1}]);
        });



        $("#search_data_from").datepicker({
            dateFormat: 'dd/mm/yy'
        });

        $("#search_data_to").datepicker({
            dateFormat: 'dd/mm/yy'
        });
        $('#search_des_comune_pratica').autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "/cross/search/comune.htm",
                    dataType: "json",
                    data: {
                        description: $("#search_des_comune_pratica").val(),
                        dataValidita: ''
                    },
                    success: function(data) {
                        response($.map(data, function(item) {
                            return {
                                label: item.descrizione + "(" + item.provincia.codCatastale + ")",
                                value: item.descrizione + "(" + item.provincia.codCatastale + ")",
                                id: item.idComune
                            }
                        }));
                    }
                });
            },
            select: function(event, ui) {
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
                        <fieldset id="richiedentiBeneficiariField"> 
                            <legend><spring:message code="ricerca.label.richiedentiBeneficiari"/></legend>
                            <div class="ctrlHolder" id="divRicercaAnagraficaNome">
                                <label for="ricercaAnagraficaNome">
                                    <spring:message code="ricerca.cognomeRagSociale"/>
                                </label>
                                <input id="ricercaAnagraficaNome" name="ricercaAnagraficaNome" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.ricercaAnagraficaNome}">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="divRicercaAnagraficaCF">
                                <label for="ricercaAnagraficaCF">
                                    <spring:message code="ricerca.cfPartitaIva"/>
                                </label>
                                <input id="ricercaAnagraficaCF" name="ricercaAnagraficaCF" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.ricercaAnagraficaCF}">
                                <p class="formHint"></p>
                            </div>
                        </fieldset>
                        <fieldset id="praticaField">
                            <legend><spring:message code="ricerca.label.pratica"/></legend>
                            <div class="ctrlHolder" id="divSearch_annoRiferimento">
                                <label for="search_annoRiferimento"><spring:message code="ricerca.annoRiferimento"/></label>
                                <select id="search_annoRiferimento" name="search_annoRiferimento" class="textInput">
                                    <option  value="" ><spring:message code="ricerca.inizio.anno"/></option>
                                    <c:forEach items="${anniRiferimento}" var="anno" begin="0">
                                        <c:set var="selected" value=""/> 
                                        <c:if test="${anno != null &&  filtroRicerca.annoRiferimento == anno}">
                                            <c:set var="selected" value="selected=\"selected\""/>     
                                        </c:if>
                                        <option  value="${anno}" ${selected}>${anno}</option>
                                    </c:forEach>
                                </select>
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="divSearch_id_pratica"> 
                                <label for="search_id_pratica"><spring:message code="ricerca.identificativopratica"/></label>
                                <input id="search_id_pratica" name="search_id_pratica" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.numeroProtocollo}">
                                <p class="formHint"></p>
                            </div>  
                            <div class="ctrlHolder" id="divSearch_data_from">
                                <label for="search_data_from"><spring:message code="ricerca.datainizio"/></label>
                                <input id="search_data_from" name="search_data_from" size="35" maxlength="50" type="text" class="textInput" value="<fmt:formatDate pattern="dd/MM/yyyy"   value="${filtroRicerca.dataInizio}"/>">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="divSearch_data_to">
                                <label for="search_data_to"><spring:message code="ricerca.datafine"/></label>
                                <input id="search_data_to" name="search_data_to" size="35" maxlength="50" type="text" class="textInput" value="<fmt:formatDate pattern="dd/MM/yyyy"   value="${filtroRicerca.dataFine}"/>">
                                <p class="formHint"></p>
                            </div>
                            <div class="ctrlHolder" id="divSearch_ente">
                                <label for="search_ente"><spring:message code="ricerca.ente"/></label>
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
                            <div class="ctrlHolder" id="divSearch_des_comune_pratica">
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
                            </div>          
                            <div class="ctrlHolder" id="divSearch_stato">
                                <label for="search_stato"><spring:message code="ricerca.statopratica"/></label>
                                <select id="search_stato" name="search_stato" class="textInput">
                                    <option value="ATTIVE" ><spring:message code="ricerca.statopraticaactive"/></option>
                                    <option value="CHIUSE" ><spring:message code="ricerca.statopraticaclose"/></option>
                                    <option value="ALL" ><spring:message code="ricerca.statopraticastart"/></option>
                                    <c:forEach items="${statoPraticaRicerca}" var="stato" begin="0">
                                        <c:set var="selected" value=""/> 
                                        <c:if test="${filtroRicerca.idStatoPratica != null &&  filtroRicerca.idStatoPratica.idStatoPratica == stato.idStatoPratica}">
                                            <c:set var="selected" value="selected=\"selected\""/>     
                                        </c:if>
                                        <c:if test='${stato.idStatoPratica ne "3" && stato.idStatoPratica ne "8"}'>
                                            <option value="${stato.idStatoPratica}" ${selected}>${stato.descrizione}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                <p class="formHint"></p>
                            </div>   
                            <div class="ctrlHolder" id="divSearch_all">
                                <label for="search_all"><spring:message code="ricerca.tuttiutenti"/>
                                    <c:set var="selected" value=""/> 
                                    <c:if test="${filtroRicerca.searchByUtenteConnesso != null &&  filtroRicerca.searchByUtenteConnesso}">
                                        <c:set var="selected" value="checked=\"checked\""/>     
                                    </c:if>
                                    <input id="search_all" name="search_all" type="checkbox" class="textInput checkbox_width" value="ok"  ${selected} /></label>
                                <p class="formHint"></p>
                            </div>
                        </fieldset>

                        <fieldset id="CatastoField">
                            <legend><spring:message code="ricerca.label.scadenze"/></legend>
                            <div class="ctrlHolder">
                                <label for="search_tipo_scadenza"><spring:message code="scadenzario.ricerca.tiposcadenza"/></label>
                                <select id="search_tipo_scadenza" name="search_tipo_scadenza" class="textInput">
                                    <option  value="" ><spring:message code="scadenzario.ricerca.tiposcadenza.start"/></option>
                                    <c:forEach items="${tipoScadenzeSearch}" var="tipo" begin="0">
                                        <c:set var="selected" value=""/> 
                                        <c:if test="${filtroRicerca.idStatoScadenza.idStato == tipo.idStato}">
                                            <c:set var="selected" value="selected=\"selected\""/>   
                                        </c:if>
                                        <option value="${tipo.idStato}"  ${selected}>${tipo.desStatoScadenza}</option>
                                    </c:forEach>
                                </select>
                                <p class="formHint"></p>
                            </div>

                        </fieldset>                         
                    </div>

                </div>

                <div class="buttonHolder" style="background-color: #E6E6E6;text-align: center">
                    <button id="ricerca_button" type="button" class="primaryAction fondo_destra" style="background-color: #0067A9"><spring:message code="ricerca.button.cerca"/></button>
                </div>
                <div class="clear"></div> 
            </div>
        </form>
    </div>
</div>