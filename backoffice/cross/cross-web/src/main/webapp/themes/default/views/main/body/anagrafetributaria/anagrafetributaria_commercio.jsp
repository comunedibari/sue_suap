<%
    String path = request.getContextPath();
    String url =path+ "/pratica/anagrafe_tributaria_commercio/submit.htm";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script>
    $(document).ready(function(){
                         
        $(".dataProvvedimento").datepicker({
            dateFormat: 'dd/mm/yy'
        });
        
    });
    
    function settaDatePicker(){
        $(".dataProvvedimento").datepicker({
            dateFormat: 'dd/mm/yy'
        });
    }
</script>
<tiles:insertAttribute name="body_error" />
<form:form modelAttribute="anagrafeCommercio" action="<%=url%>"  id="anagrafeTributaria" method="post" cssClass="uniForm inlineLabels comunicazione">
    <h2 style="text-align: center">${titoloPagina}</h2>
    <div class="inlineLabels">

        <fieldset id="anagrafeTributariaCommercioFieldset" class="fieldsetComunicazione fieldsetEvento">
            <div class="table-add-link">
                <a class="button" onclick="aggiungiSoggetto()">
                    Aggiungi soggetto
                    <img src="<%=path%>/themes/default/images/icons/add.png" alt="Aggiungi soggetto" title="Aggiungi soggetto">
                </a>
            </div>
            <legend>Soggetti</legend>
            <form:hidden path="idPratica"/>
            <form:hidden path="idEvento" />
            <script>
                var soggetti = '';
                <c:forEach items="${soggetti}" var="soggetto" begin="0">
                    soggetti += '<option value="${soggetto.idAnagrafica}">${soggetto.descrizione}</option>';
                </c:forEach>
                    var provvedimenti = '';
                <c:forEach items="${provvedimenti}" var="provvedimento" begin="0">
                    provvedimenti += '<option value="${provvedimento.itemValue}">${provvedimento.itemLabel}</option>';
                </c:forEach>
                    
                    function aggiungiSoggetto(){
                        var labelSoggetto= 'Soggetto';
                        var labelProvvedimento = 'Provvedimento';
                        var labelInizioProvvedimento = 'Inizio provvedimento';
                        var labelFineProvvedimento = 'Fine provveidmento';
                        var labelRimuoviSoggetto = 'Rimuovi soggetto';
                        var counter = $('#anagrafeTributariaCommercioFieldset > div').length - 1;
                        var newsection = '<div class="soggetti" id="soggetto_'+ counter +'">';
                        if (counter > 0) {
                            newsection += '<hr />';
                        }
                        
                        newsection += '<input type="hidden" name="anagrafiche['+counter+'].codFornitura" value="CC" /></div>';                        
                        
                        newsection += '<div class="ctrlHolder dettaglio_liv_0"><label class="required" for="idAnagraficaSoggetto_'+counter+'">'+labelSoggetto+'</label>';
                        newsection += '<select name="anagrafiche['+counter+'].idSoggetto" id="idAnagraficaSoggetto_'+counter+'">'+soggetti+'</select></div>';
                        
                        newsection += '<div class="ctrlHolder dettaglio_liv_0"><label class="required" for="codProvvedimento_'+counter+'">'+labelProvvedimento+'</label>';
                        newsection += '<select name="anagrafiche['+counter+'].codProvvedimento" id="codProvvedimento_'+counter+'">'+provvedimenti+'</select></div>';
                        
                        newsection += '<div class="ctrlHolder dettaglio_liv_0"><label class="required" for="inizioProvvedimento_'+counter+'">'+labelInizioProvvedimento+'</label>';
                        newsection += '<input type="text" name="anagrafiche['+counter+'].inizioProvvedimento" class="textInput required dataProvvedimento" id="inizioProvvedimento_'+counter+'" /></div>';                        
                        
                        newsection += '<div class="ctrlHolder dettaglio_liv_0"><label class="required" for="fineProvvedimento_'+counter+'">'+labelFineProvvedimento+'</label>';
                        newsection += '<input type="text" name="anagrafiche['+counter+'].fineProvvedimento" class="textInput required dataProvvedimento" id="fineProvvedimento_'+counter+'" /></div>';                        
                        
                        newsection += '<div class="table-add-link"><a class="button" onclick="rimuoviSoggetto(\'#soggetto_'+counter+'\')">'+labelRimuoviSoggetto;
                        newsection += '<img src="<%=path%>/themes/default/images/icons/delete.png" alt="'+labelRimuoviSoggetto+'" title="'+labelRimuoviSoggetto+'" </a></div>';
                        newsection += '</div>';
                        $('#anagrafeTributariaCommercioFieldset').append(newsection);
                        settaDatePicker();
                    }
                
                    function rimuoviSoggetto(sezione){
                        $(sezione).remove();
                    }
            </script>
            <c:set var="countAnagrafeCommercio" value="0" scope="page" />
            <c:forEach items="${anagrafeCommercio.anagrafiche}" var="anagrafeTributariaCommercio" begin="0">         

                <div class="soggetti" id="soggetto_${countAnagrafeCommercio}">
                    <form:hidden path="anagrafiche[${countAnagrafeCommercio}].idDettaglio" />
                    <form:hidden path="anagrafiche[${countAnagrafeCommercio}].codFornitura" />
                    <div class="ctrlHolder dettaglio_liv_0">
                        <label class="required" for="idAnagraficaSoggetto_${countAnagrafeCommercio}">Soggetto</label>
                        <form:select id="idAnagraficaSoggetto_${countAnagrafeCommercio}" path="anagrafiche[${countAnagrafeCommercio}].idSoggetto" >
                            <form:options items="${soggetti}" itemLabel="descrizione" itemValue="idAnagrafica"/>
                        </form:select>
                    </div>

                    <div class="ctrlHolder dettaglio_liv_0">
                        <label class="required" for="codProvvedimento_${countAnagrafeCommercio}">Provvedimento</label>
                        <form:select id="codProvvedimento_${countAnagrafeCommercio}" path="anagrafiche[${countAnagrafeCommercio}].codProvvedimento" >
                            <form:options items="${provvedimenti}" itemLabel="itemLabel" itemValue="itemValue"/>
                        </form:select>
                    </div>

                    <div class="ctrlHolder">
                        <label class="required" for="inizioProvvedimento_${countAnagrafeCommercio}">Inizio provvedimento</label>
                        <form:input path="anagrafiche[${countAnagrafeCommercio}].inizioProvvedimento" id="inizioProvvedimento_${countAnagrafeCommercio}" cssClass="textInput required dataProvvedimento"/>
                        <p class="formHint"></p>
                    </div>

                    <div class="ctrlHolder">
                        <label class="required" for="fineProvvedimento_${countAnagrafeCommercio}">Fine provvedimento</label>
                        <form:input path="anagrafiche[${countAnagrafeCommercio}].fineProvvedimento" id="fineProvvedimento_${countAnagrafeCommercio}" cssClass="textInput required dataProvvedimento"/>
                        <p class="formHint"></p>
                    </div>
                </div>
                <c:set var="countAnagrafeCommercio" value="${datiCatastaliCounter + 1}" scope="page"/>
            </c:forEach>
        </fieldset>
    </div>

    <div class="buttonHolder">
        <a href="<%=path%>/pratica/evento/index.htm" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
        <button type="submit" class="primaryAction"><spring:message code="pratica.comunicazione.evento.crea"/></button>
    </div>
</form:form>