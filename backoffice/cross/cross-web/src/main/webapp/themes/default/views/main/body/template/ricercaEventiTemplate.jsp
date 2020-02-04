<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
        <form id="form_ricerca" action="<%= path%>/gestione/eventoTemplate/lista/ajax.htm" class="uniForm inlineLabels" method="post">
            <input type="hidden" name="tipoFiltro" value="utente"/>
            <div style="width:1080px;">    
                <div class="ricerca_div_forms">   
                    <div class="inlineLabels">
                        <div class="ctrlHolder">
                            <label for="search_stato">Evento</label>
                            <select id="listaEventi" name="descEvento" class="textInput required">
                                <option value="">Selezionare Evento</option>
                                <c:set value="0" var="i"/>
                                <c:forEach begin="0" items="${listaEventi}" var="evento">
                                    <c:set var="selected" value=""/>
                                    <c:if test="${filtroRicerca.descEvento != null && evento.descrizione == filtroRicerca.descEvento}">
                                        <c:set var="selected" value="selected=\"selected\""/>    
                                    </c:if>
                                    <option value="${evento.descrizione}" name="${evento.descrizione}" ${selected}>${evento.descrizione}
                                    (${evento.desProcesso})</option>
                                    <c:set value="${i+1}" var="i"/>
                                </c:forEach>
                            </select>
                            <p class="formHint"></p>
                        </div>
                        <div class="ctrlHolder">
                            <label for="search_stato">Enti</label>
                            <select id="listaEnti" name="idEnte" class="textInput required">
                                <option>Selezionare Ente</option>
                                <c:set value="0" var="i"/>
                                <c:forEach begin="0" items="${listaEnti}" var="ente">
                                    <c:set var="selected" value=""/>
                                    <c:if test="${filtroRicerca.idEnte != null && ente.idEnte == filtroRicerca.idEnte}">
                                        <c:set var="selected" value="selected=\"selected\""/>    
                                    </c:if>
                                    <option value="${ente.idEnte}" name="${ente.idEnte}" ${selected}>${ente.descrizione}</option>
                                    <c:set value="${i+1}" var="i"/>
                                </c:forEach>
                            </select>   
                            <p class="formHint"></p>
                        </div>
                        <div class="ctrlHolder">
                            <label for="search_stato">Procedimento</label>
                            <select id="listaProcedimenti" name="idProcedimento" class="textInput required">
                                <option>Selezionare Procedimento</option>
                                <c:set value="0" var="i"/>
                                <c:forEach begin="0" items="${listaProcedimenti}" var="procedimento">
                                    <c:set var="selected" value=""/>
                                    <c:if test="${filtroRicerca.idProcedimento != null && procedimento.idProcedimento == filtroRicerca.idProcedimento}">
                                        <c:set var="selected" value="selected=\"selected\""/>    
                                    </c:if>
                                    <option value="${procedimento.idProcedimento}" ${selected}>${procedimento.desProcedimento}</option>
                                    <c:set value="${i+1}" var="i"/>
                                </c:forEach>
                            </select>  
                            <p class="formHint"></p>
                        </div>
                    </div>    
                </div>
                <div class="ricerca_div_forms">    
                    <div class="inlineLabels">
                        <div class="ctrlHolder">
                            <label for="search_stato">Template</label>
                            <select id="idTemplate" name="idTemplate" class="textInput required">
                                <option>Selezionare</option>
                                <c:forEach begin="0" items="${listaTemplate}" var="template">
                                    <c:set var="selected" value=""/>
                                    <c:if test="${filtroRicerca.idTemplate != null && procedimento.idTemplate == filtroRicerca.idTemplate}">
                                        <c:set var="selected" value="selected=\"selected\""/>    
                                    </c:if>
                                    <option value="${template.idTemplate}" ${selected}>${template.descrizioneTemplate}</option>
                                </c:forEach>
                            </select>
                            <p class="formHint"></p>
                        </div>
                    </div>

                </div>
                <div class="buttonHolder" style="background-color: #E6E6E6;text-align: center">
                    <button id="ricerca_button" type="button" class="primaryAction fondo_destra" style="background-color: #0067A9"><spring:message code="ricerca.button.cerca"/></button>				
                </div>
            </div>
        </form>
    </div>
</div>