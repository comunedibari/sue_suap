<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script>
    $(document).ready(function() {
        
        $('#dataRicezionePratica').datepicker();
        
        $('#procedimento').autocomplete({
            source: function( request, response ) {
                $.ajax({
                    url: "<%= request.getContextPath()%>/search/entiProcedimenti.htm",
                    dataType: "json",
                    data: {
                        desProcedimento: $('#procedimento').val()
                    },
                    success: function( data ) {
                        response( $.map( data.rows, function( item ) {
                            return {
                                label: item.desProcedimento+" ("+item.desEnteDestinatario+")",
                                value: item.desProcedimento,
                                idProcedimento: item.idProcedimento,
                                idEnteDestinatario: item.idEnteDestinatario,
                                desEnteDestinatario: item.desEnteDestinatario,
                                desProcedimento: item.desProcedimento
                            }
                        }));
                    }
                });
            },
            select: function( event, ui ) {
                $('#procedimento').val(ui.item.desProcedimento);
                $('#idProcedimento').val(ui.item.idProcedimento);
                $('#idEnte').val(ui.item.idEnteDestinatario);
                $('#descEnte').val(ui.item.desEnteDestinatario);
            },
            minLength: 2
        });
        
        var ente=null;
        var numRighe=0 ;
        $("#aggiungiProcedimenti").click(function(){
            
            var proce = $("#procedimento").val();
            var idproce = $("#idProcedimento").val();
            var idente = $("#idEnte").val();
            var ente = $("#descEnte").val();
            var tr = $("#ProcedimentiEnti #tr").clone();
                
            if($.trim(idproce)!="" && idproce!=null)
            {
                $("#procedimento").val("");
                $("#idProcedimento").val("");
                $("#idEnte").val("");
                $("#descEnte").val("");

                var html = tr.html();
                if(numRighe==0)
                {
                    numRighe=$("#ProcedimentiEnti table tr").length-2;
                }
                //                html =html .replace("_ID_",id);
                html =html .replace(/_I_/g,(numRighe))
                html =html .replace("_idProcedimento_",idproce);
                html =html .replace(/_idEnteDestinatario_/g,idente);
                html =html .replace(/_Proce_/g,proce);
                html =html .replace(/_Ente_/g,ente);
                numRighe++;
                tr.html(html);
                tr.removeAttr("id");
                tr.find("input").removeAttr("disabled"); 
                tr.show();
                $("#ProcedimentiEnti table").append(tr);
            }
            else
            {
                alert("Selezionare il procedimento");
            }
        });
    });
    
    function rimuoviProcedimento(component){
        var component = $(component).parent().parent();
        component.remove();
        var counter = 0;
        for (var i=2; i<$('#ProcedimentiEnti table tr').length; i++ ){
            var row = $('#ProcedimentiEnti table tr')[i]; 
            //Aggiorno Id procedimento
            var hiddenFields = $(row).children().eq(0); 
            var idProc = $(hiddenFields).children().eq(1); 
            $(idProc).attr('name', 'interventi['+counter+'].id');
            $(idProc).attr('id', 'idProcedimento'+counter);
            //Aggiorno descrizione procedimento
            var desProc = $(hiddenFields).children().eq(2); 
            $(desProc).attr('name', 'interventi['+counter+'].descrizione');
            $(desProc).attr('id', 'desProcedimento'+counter);
            //Aggiorno descrizione ente
            var desEnte = $(hiddenFields).children().eq(3); 
            $(desEnte).attr('name', 'interventi['+counter+'].descrizioneEnte');
            $(desEnte).attr('id', 'desEnteDestinatario'+counter);
            //Aggiorno Id ente
            var idEnte = $(hiddenFields).children().eq(4); 
            $(idEnte).attr('name', 'interventi['+counter+'].idEnte');
            $(idEnte).attr('id', 'idEnte'+counter);
            counter++;
        }
    }
</script>
<div>
    <div class="ctrlHolder dettaglio_liv_0">

        <fieldset class="fieldsetComunicazione">
            <legend><spring:message code="comunica.riepilogo.ufficio.title"/></legend>
            <div>
                <div class="ctrlHolder dettaglio_liv_0">
                    <div class="ctrlHolder">
                        <label class="required"><spring:message code="comunica.riepilogo.ufficio.label"/></label>
                        <span class="value">${comunica.ufficio.descrizione}</span>
                        <form:hidden path="ufficio.descrizione"/>
                    </div>
                    <div class="ctrlHolder">
                        <label class="required"><spring:message code="comunica.riepilogo.codamm"/></label>
                        <span class="value">${comunica.ufficio.codiceAmministrazione}</span>
                        <form:hidden path="ufficio.codiceAmministrazione"/>
                    </div>
                    <div class="ctrlHolder">
                        <label class="required"><spring:message code="comunica.riepilogo.codaoo"/></label>
                        <span class="value">${comunica.ufficio.codiceAoo}</span>
                        <form:hidden path="ufficio.codiceAoo"/>
                    </div>
                    <div class="ctrlHolder">
                        <label class="required"><spring:message code="comunica.riepilogo.idsuap"/></label>
                        <span class="value">${comunica.ufficio.identificativoSuap}</span>
                        <form:hidden path="ufficio.identificativoSuap"/>
                    </div>
                    <div class="ctrlHolder">
                        <label for="comuneRiferimento"><spring:message code="comunica.riepilogo.comuneriferimento"/></label>
                        <span class="value">${comunica.comuneRiferimento.description}</span>
                        <form:hidden path="comuneRiferimento.id" />
                        <form:hidden path="comuneRiferimento.description" />
                    </div>   
                    <div class="ctrlHolder">
                        <label for="comunica.sportello" class="required"><spring:message code="comunica.riepilogo.sportello"/></label>
                        <span class="value">${comunica.sportello.descrizione}</span>
                        <form:hidden path="sportello.descrizione"/>
                        <form:hidden path="sportello.id"/>
                    </div>     
                    <div class="ctrlHolder">
                        <label for="dataRicezionePratica" class="required"><spring:message code="comunica.riepilogo.dataricezione"/></label>
                        <input type="text" id="dataRicezionePratica" name="dataRicezionePratica" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${comunica.dataRicezionePratica}"/>"/>
                    </div>   
                </div>
            </div>
        </fieldset>

        <fieldset class="fieldsetComunicazione">
            <legend><spring:message code="comunica.riepilogo.pratica.title"/></legend>
            <div>
                <div class="ctrlHolder">
                    <label for="oggetto" class="required"><spring:message code="comunica.riepilogo.pratica.oggetto"/></label>
                    <span class="value">${comunica.oggetto}</span>
                    <form:hidden path="oggetto" />
                </div>
                <div class="ctrlHolder">
                    <label for="idProcedimentoSuap" class="required"><spring:message code="comunica.riepilogo.pratica.procedimento"/></label>
                    <form:select id="idProcedimentoSuap" name="idProcedimentoSuap" path="idProcedimentoSuap" cssClass="select required">
                        <form:option value="" label="" />
                        <form:options items="${procedimentiSuap}" itemLabel="description" itemValue="id"/>
                    </form:select>
                    <form:hidden path="tipoProcedimento" />
                    <p class="formHint"><spring:message code="comunica.note"/> <c:out value="${comunica.tipoProcedimento}" /></p>
                </div>
                <div class="ctrlHolder">
                    <label for="procedimento" class="required"><spring:message code="comunica.riepilogo.pratica.intervento"/></label>
                    <input id="procedimento" name ="" maxlength="255" type="text" class="textInput required" value=""/>
                    <input id="idProcedimento" type="hidden" value=""/>
                    <input id="idEnte" type="hidden"  value=""/>
                    <input id="descEnte" type="hidden" value=""/>
                    <input type="button" name="action" id="aggiungiProcedimenti" class="primaryAction_aggiungi" value="Aggiungi"/>
                    <div id="ProcedimentiEnti">
                        <table cellspacing="0" cellpadding="0" class="master">
                            <tr>
                                <th><spring:message code="protocollo.dettaglio.procedimenti.azione"/></th>
                                <th><spring:message code="protocollo.dettaglio.procedimenti.procedimento"/></th>
                                <th><spring:message code="protocollo.dettaglio.procedimenti.ente"/></th>
                            </tr>
                            <tr id="tr" style="display: none">
                                <td>
                                    <img src="<%=request.getContextPath()%>/themes/default/images/icons/delete.png" alt="Elimina" title="Elimina" onclick="rimuoviProcedimento(this)" class="button"/>
                                    <input id="idProcedimento__I_" disabled="disabled" type="hidden"  name ="interventi[_I_].id" value="_idProcedimento_"/>
                                    <input id="desProcedimento__I_" disabled="disabled" type="hidden"  name ="interventi[_I_].descrizione" value="_Proce_"/>
                                    <input id="desEnteDestinatario__I_" disabled="disabled" type="hidden"  name ="interventi[_I_].descrizioneEnte" value="_Ente_"/>
                                    <input id="idEnte__I_" disabled="disabled" type="hidden"  name ="interventi[_I_].idEnte" value="_idEnteDestinatario_"/>
                                </td>
                                <td>_Proce_</td>
                                <td>_Ente_</td>
                            </tr>
                            <c:set var="count" value="0" scope="page" />
                            <c:forEach var="intervento" begin="0" items="${comunica.interventi}">
                                <tr>
                                    <td>
                                        <img src="<%=request.getContextPath()%>/themes/default/images/icons/delete.png" alt="Elimina" title="Elimina" onclick="rimuoviProcedimento(this)" class="button">
                                        <input id="idProcedimento_${count}" type="hidden" name="interventi[${count}].id" value="${intervento.id}">
                                        <input id="desProcedimento_${count}" type="hidden" name="interventi[${count}].descrizione" value="${intervento.descrizione}">
                                        <input id="desEnteDestinatario_${count}" type="hidden" name="interventi[${count}].descrizioneEnte" value="${intervento.descrizioneEnte}">
                                        <input id="idEnte_${count}" type="hidden" name="interventi[${count}].idEnte" value="${intervento.idEnte}">
                                    </td>
                                    <td>${intervento.descrizione}</td>
                                    <td>${intervento.descrizioneEnte}</td>
                                </tr>
                                <c:set var="count" value="${count + 1}" scope="page"/>
                            </c:forEach>
                        </table>
                    </div>
                </div>
                <p class="formHint">
                    <spring:message code="comunica.note"/> ${comunica.interventoOrigine}
                    <form:hidden path="interventoOrigine" />
                </p>
            </div>     
        </fieldset>

        <fieldset class="fieldsetComunicazione">
            <legend><spring:message code="comunica.riepilogo.protocollo"/></legend>
            <div>
                <div class="ctrlHolder">
                    <label for="protocollo.registro" class="required"><spring:message code="comunica.riepilogo.protocollo.registro"/></label>
                    <form:input path="protocollo.registro" id="protocollo.registro" maxlength="255" cssClass="textInput required"/>
                    <form:errors path="protocollo.registro" />
                </div>
                <div class="ctrlHolder">
                    <label for="protocollo.anno" class="required"><spring:message code="comunica.riepilogo.protocollo.anno"/></label>
                    <form:input path="protocollo.anno" id="protocollo.anno" maxlength="255" cssClass="textInput required"/>
                    <form:errors path="protocollo.anno" />
                </div>
                <div class="ctrlHolder">
                    <label for="protocollo.numero" class="required"><spring:message code="comunica.riepilogo.protocollo.numero"/></label>
                    <form:input path="protocollo.numero" id="protocollo.numero" maxlength="255" cssClass="textInput required"/>
                    <form:errors path="protocollo.numero" />
                </div>     
                <div class="ctrlHolder">
                    <label for="protocollo.fascicolo" class="required"><spring:message code="comunica.riepilogo.protocollo.fascicolo"/></label>
                    <form:input path="protocollo.fascicolo" id="protocollo.fascicolo" maxlength="255" cssClass="textInput required"/>
                </div>     
            </div>
        </fieldset>       
    </div>
</div>