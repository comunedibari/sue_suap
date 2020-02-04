<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script>
    $(document).ready(function() {
        $("#ricerca").accordion({
            collapsible: true,
            alwaysOpen: false,
            active: false
        });

        $("#ricerca_button").click(function() {
            //^^CS ELIMINA provoca questa stringa ..../dettaglio_search.htmsearch_id_pratica=asd&search_ente...
            //$('#list').setGridParam({url:url+$('#form_ricerca').serialize()});
            var urli = $('#listTable').getGridParam("url").split("?")[0];
            $('#listTable').setGridParam({url: urli + "?" + $('#form_ricerca').serialize()});
            $('#listTable').trigger("reloadGrid", [{page: 1}]);
        });

    });
</script>
<spring:message code="datiestesi.select.default" var="selectDefault"/>
<div id="ricerca">
    <h3><a href="#"><spring:message code="ricerca.title"/></a></h3>
    <div>
        <form id="form_ricerca" action="" class="uniForm inlineLabels" method="post">
            <div style="width:1080px;">    
                <div class="ricerca_div_forms">   
                    <div class="inlineLabels">
                        <div class="ctrlHolder">
                            <fieldset id="richiedentiBeneficiariField"> 
                                <legend><spring:message code="datiestesi.ricerca.label.parametri"/></legend>
                                <div class="ctrlHolder" id="divRicercaAnagraficaNome">
                                    <label for="search_codTipoOggetto">
                                        <spring:message code="datiestesi.ricerca.tipooggetto"/>
                                    </label>
                                    <form:select id="search_codTipoOggetto" name="search_codTipoOggetto" path="tipiOggetto" class="textInput required ricercaTipoOggetto">
                                        <form:option value="" label="${selectDefault}" />
                                        <form:options items="${tipiOggetto}" itemLabel="desTipoOggetto" itemValue="codTipoOggetto" />
                                    </form:select>                                    
                                    <p class="formHint"></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label for="search_idIstanza">
                                        <spring:message code="datiestesi.ricerca.idistanza"/>
                                    </label>
                                    <input id="search_idIstanza" name="search_idIstanza" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.idIstanza}">
                                    <p class="formHint"></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label for="search_codValue">
                                        <spring:message code="datiestesi.ricerca.codvalue"/>
                                    </label>
                                    <input id="search_codValue" name="search_codValue" size="35" maxlength="50" type="text" class="textInput" value="${filtroRicerca.codValue}">
                                    <p class="formHint"></p>
                                </div>
                            </fieldset>
                            <div class="buttonHolder" style="background-color: #E6E6E6;text-align: center">
                                <button id="ricerca_button" type="button" class="primaryAction fondo_destra" style="background-color: #0067A9"><spring:message code="ricerca.button.cerca"/></button>				
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>