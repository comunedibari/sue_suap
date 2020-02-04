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
                            <div class="ctrlHolder" id="divSearch_oggetto"
								<label for="search_oggetto"><spring:message code="ricerca.oggetto"/></label>
								<select id="search_oggetto" name="search_oggetto" class="textInput">
                                	<option value="" ></option>
                                    <option value="SCIA" ><spring:message code="ricerca.scia"/></option>
                                    <option value="CILA" ><spring:message code="ricerca.cila"/></option>
                                    <option value="permesso di costruire" ><spring:message code="ricerca.pdc"/></option>
                                    <option value="agibilit" ><spring:message code="ricerca.agibilita"/></option>
                                    <option value="fine lavori" ><spring:message code="ricerca.fine.lavori"/></option>
                                    <option value="Autorizzazione Paesaggistica" ><spring:message code="ricerca.autorizzazione.paesagg"/></option>
                                    <option value="gestionale generico" ><spring:message code="ricerca.gestionale.generico"/></option>
                                    <option value="scia alternativa" ><spring:message code="ricerca.scia.alternativa"/></option>
                                </select>
                                <p class="formHint"></p>
                                <p class="formHint"></p>
                            </div>
                            <%-- <div class="ctrlHolder" id="divSearch_oggetto">
                                <label for="search_oggetto"><spring:message code="ricerca.oggetto"/></label>
                                <input id="search_oggetto" name="search_oggetto" size="35" maxlength="250" type="text" class="textInput" value="${filtroRicerca.oggetto}">
                                <p class="formHint"></p>
                            </div> --%>
                        </fieldset>
                        <div class="buttonHolder" style="background-color: #E6E6E6;text-align: center">
                            <button id="ricerca_button" type="button" class="primaryAction fondo_destra" style="background-color: #0067A9"><spring:message code="ricerca.button.cerca"/></button>				
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>