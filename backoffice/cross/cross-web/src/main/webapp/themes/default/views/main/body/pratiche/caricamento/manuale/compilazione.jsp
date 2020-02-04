<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:insertAttribute name="body_error" />

<style type="text/css" >
    .uniForm ul {
        width: 99%;
    }

    .chosen-container abbbr{
        border: 0;
        border-bottom: none;
        cursor: pointer;
    }


</style>

<script>
    $(document).ready(function() {

        $(".allegato").click(function() {
            var count = $("table.listaAllegati tr").length;
            count -= 1;
            var name = "allegatiList[" + count + "]";
            var html = "<tr>";
            html += "<td>";
            html += "   <input type=\"text\"  name=\"" + name + ".descrizione\">";
            html += "</td>";
            html += "<td>";
            html += "   <input type=\"file\" name=\"" + name + ".file\">";
            html += "</td>";
            html += "<td>";
            html += "    <input class=\"allegatoOriginale\" type=\"checkbox\" name=\"" + name + ".modelloPratica\" value=\"false\" />";
            html += "</td> ";
            html += "<td>";
            html += "   <a class=\"eliminaAllegato button\">";
            html += "       Elimina Allegato";
            html += "       <img title=\"Aggiungi Allegato\" alt=\"Aggiungi Allegato\" src=\"${path}/themes/default/images/icons/reject.png\">";
            html += "   </a >";
            html += "</td>";
            html += "</tr>";
            $("table.listaAllegati").append(html);
            eliminaAllegato();
            gestioneAllegato();
        });
        $(".dataPicker").datepicker(
                {
                    dateFormat: "dd/mm/yy",
                    changeMonth: true,
                    changeYear: true,
                    yearRange: "-120: + 0"
                });
        eliminaAllegato();
        gestioneAllegato();
        //scaricaAllegato();
    });
    function gestioneAllegato()
    {
        $(".allegatoOriginale").change(function()
        {
            var check = $(this).attr("checked");
            if (check === null || check === '')
            {
                $(this).val("false");
            }
            else
            {
                $(this).val("true");
            }
        });
    }
    function eliminaAllegato()
    {
        $(".eliminaAllegato").unbind();
        $(".eliminaAllegato").click(function() {
            $(this).parent().parent().remove();
        });
    }

</script>

<div class="">

    <div class="sidebar_auto">      

        <tiles:insertAttribute name="body_error_custom" />

        <form id="creazioneEventoForm" action="${path}/pratiche/caricamento/manuale.htm" class="uniForm inlineLabels comunicazione" method="post" enctype="multipart/form-data">

            <input type="hidden" name="idPraticaProtocollo" value="${praticaCaricamentoManuale.idPraticaProtocollo}"/>
            <input type="hidden" name="initialized" value="true"/>

            <div class="page-control" data-role="page-control" >
                <span class="menu-pull"></span> 
                <div class="menu-pull-bar"></div>
                <!-- Etichette cartelle -->
                <ul>
                    <li class="active"><a href="#frame1"><spring:message code="protocollo.dettaglio.datipratica"/></a></li>
                    <li><a href="#frame2"><spring:message code="protocollo.dettaglio.allegati.title"/></a></li>
                </ul>
                <!-- Contenuto cartelle -->
                <div class="frames">
                    <div class="frame active" id="frame1">
                        <div>
                            <div class="ctrlHolder">
                                <label for="procedimentoSelezionato"><spring:message code="pratica.caricamento.manuale.procedimento"/></label>
                                <select id="procedimentoSelezionato" name="procedimentoSelezionato" maxlength="255" data-placeholder="<spring:message code="pratica.caricamento.manuale.procedimento.placeholder"/>" class="textInput required chosen-select" >
                                    <option value=""></option>
                                    <c:forEach items="${entePerCuiOpero.procedimentiAttivi}" var="procedimento" begin="0">
                                        <option value="${procedimento.idProc}" <c:if test="${fn:length(entePerCuiOpero.procedimentiAttivi)==1 or praticaCaricamentoManuale.procedimentoSelezionato == procedimento.idProc}">selected="selected"</c:if>>${procedimento.descrizione}</option>
                                    </c:forEach>
                                </select>
                                <p class="formHint procedimentoSelezionato"><spring:message code="ente.campo.obbligatorio"/></p>
                            </div>

                            <div class="ctrlHolder">
                                <label for="comuneSelezionato"><spring:message code="pratica.caricamento.manuale.comune"/></label>
                                <select id="comuneSelezionato" name="comuneSelezionato" maxlength="255" data-placeholder="<spring:message code="pratica.caricamento.manuale.comune.placeholder"/>" class="textInput required chosen-select" >
                                    <option value=""></option>
                                    <c:forEach items="${entePerCuiOpero.comuniAbilitati}" var="comune" begin="0">
                                        <option value="${comune.idComune}" <c:if test="${fn:length(entePerCuiOpero.comuniAbilitati)==1 or praticaCaricamentoManuale.comuneSelezionato == comune.idComune}">selected="selected"</c:if> >${comune.descrizione}</option>
                                    </c:forEach>
                                </select>
                                <p class="formHint comuneSelezionato"><spring:message code="ente.campo.obbligatorio"/></p>
                            </div>

                            <div class="ctrlHolder">
                                <label for="oggetto"><spring:message code="pratica.caricamento.manuale.oggetto"/></label>
                                <textarea name="oggetto" id="oggetto" maxlength="255" type="text" class="textInput required" >${praticaCaricamentoManuale.oggetto}</textarea>
                                <p class="formHint oggetto"><spring:message code="ente.campo.obbligatorio"/></p>
                            </div>

                            <div class="ctrlHolder">
                                <label for="ricezioneData"><spring:message code="pratica.caricamento.manuale.ricezione.data"/></label>
                                <input name="ricezioneData" id="ricezioneData" maxlength="255" type="text" class="textInput required dataPicker" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${praticaCaricamentoManuale.ricezioneData}" />" />
                                <p class="formHint ricezioneData"><spring:message code="ente.campo.obbligatorio"/> e deve essere o data odierna o antecedente</p>
                            </div>

                            <div class="ctrlHolder">
                                <label for="protocolloSegnatura"><spring:message code="istanza.protocollo.segnatura.label"/></label>
                                <input name="protocolloSegnatura" id="protocolloSegnatura" maxlength="255" type="text" class="textInput required" value="${praticaCaricamentoManuale.protocolloSegnatura}" />
                                <p class="formHint protocolloSegnatura"><spring:message code="istanza.protocollo.segnatura.hint"/></p>
                            </div>

                            <div class="ctrlHolder">
                                <label for="protocolloData"><spring:message code="istanza.protocollo.data.label"/></label>
                                <input name="protocolloData" id="protocolloData" maxlength="255" type="text" class="textInput required dataPicker" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${praticaCaricamentoManuale.protocolloData}" />" />
                                <p class="formHint protocolloData"><spring:message code="istanza.protocollo.data.hint"/></p>
                            </div>

                            <c:if test="${entePerCuiOpero.tipoEnte == 'SUAP'}">
                                <div class="ctrlHolder">
                                    <label for="endoprocedimentiSelezionati"><spring:message code="pratica.caricamento.manuale.endoprocedimenti"/></label>
                                    <select id="endoprocedimentiSelezionati" name="endoprocedimentiSelezionati" multiple maxlength="255" data-placeholder="<spring:message code="protocollo.dettaglio.endoprocedimento"/>" class="textInput required chosen-select" >
                                        <option value=""></option>
                                        <c:forEach items="${entePerCuiOpero.endoProcedimentiEntiAttivi}" var="endoprocedimentoEnte" begin="0">
                                            <option value="${endoprocedimentoEnte.idProcEnte}" <c:if test="${endoProcedimentiSelezionatiMap[endoprocedimentoEnte.idProcEnte]}">selected="selected"</c:if> >${endoprocedimentoEnte.procedimento.descrizione} (${endoprocedimentoEnte.ente.descrizione})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </c:if>

                        </div>

                    </div>
                    <div class="frame" id="frame2">
                        <b class="allegatiVuoti">Caricare  almeno un allegato. Per ogni allegato si deve indicare descrizione e caricare documento</b>
                        <p class="formHint allegatiRiepilogo">Impostare almeno un allegato come riepilogo della pratica</p>
                        <div class="table-add-link allegato">
                            <a  class="button addgenerico" style="height:auto !important;">
                                Aggiungi Allegato
                            </a>
                        </div>

                        <div>
                            <table cellspacing="0" cellpadding="0" class="master listaAllegati">
                                <tr>
                                    <th><spring:message code="protocollo.dettaglio.allegati.descrizione"/></th>
                                    <th><spring:message code="protocollo.dettaglio.allegati.nomefile"/></th>
                                    <th>Riepilogo pratica</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                <c:if test="${!empty praticaCaricamentoManuale.allegatiList}">
                                    <c:set var="countAllegati" value="0" scope="page" />

                                    <c:forEach items="${praticaCaricamentoManuale.allegatiList}" var="allegato" begin="0">
                                        <c:set var="modificaAllegato" value="N"/>
                                        <c:if test="${allegato.descrizione == null || allegato.descrizione == '' || allegato.nomeFile == null || allegato.nomeFile == ''}">
                                            <c:set var="modificaAllegato" value="S"/>    
                                        </c:if>
                                        <tr>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${modificaAllegato == 'S' }">
                                                        <input type="text" name="allegatiList[${countAllegati}].descrizione" value="${allegato.descrizione}"/>    
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${allegato.descrizione}
                                                        <input type="hidden" name="allegatiList[${countAllegati}].descrizione" value="${allegato.descrizione}"/>        
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${modificaAllegato == 'S' }">
                                                        <input type="file" name="allegatiList[${countAllegati}].file" />   
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${allegato.nomeFile}
                                                        <input type="hidden" name="allegatiList[${countAllegati}].nomeFile" value="${allegato.nomeFile}"/>
                                                    </c:otherwise>
                                                </c:choose>

                                            </td>
                                            <td>

                                                <c:choose>
                                                    <c:when test="${allegato.modelloPratica!=null && allegato.modelloPratica}">
                                                        <input class="allegatoOriginale" type="checkbox" name="allegatiList[${countAllegati}].modelloPratica" value="${allegato.modelloPratica}" checked="checked"/> 
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input class="allegatoOriginale" type="checkbox" name="allegatiList[${countAllegati}].modelloPratica" value="${allegato.modelloPratica}" /> 
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>  
                                            <td class="gridActionContainer">
                                                <c:if test="${!empty allegato.idFileEsterno}">
                                                    <a class="scaricaAllegato scarica" target="_blank" href="/cross/download/protocollo/idfileesterno.htm?idFile=${allegato.idFileEsterno}"> Scarica file </a>
                                                </c:if>
                                                <c:if test="${!empty allegato.pathFile}">
                                                    <a class="scaricaAllegato scarica" target="_blank" href="/cross/download/protocollo/path.htm?fileName=${allegato.nomeFileB64}&filePath=${allegato.pathFileB64}"> Scarica file </a>
                                                </c:if>
                                            </td>                                            
                                            <td class="gridActionContainer">
                                                <input type="hidden" name="allegatiList[${countAllegati}].idFileEsterno" value="${allegato.idFileEsterno}"/>
                                                <input type="hidden" name="allegatiList[${countAllegati}].modelloPratica" value="${allegato.modelloPratica}"/>
                                                <input type="hidden" name="allegatiList[${countAllegati}].idAllegato" value="${allegato.idAllegato}"/>                                                
                                                <input type="hidden" name="allegatiList[${countAllegati}].pathFile" value="${allegato.pathFile}"/>
                                                <input type="hidden" name="allegatiList[${countAllegati}].tipoFile" value="${allegato.tipoFile}"/>
                                                <a class="deletegenerico eliminaAllegato button">
                                                    Elimina Allegato
                                                </a >
                                            </td>             
                                        </tr>
                                        <c:set var="countAllegati" value="${countAllegati + 1}" scope="page"/>
                                    </c:forEach>
                                </c:if>                      
                            </table>
                        </div>

                    </div>
                </div>
            </div>


            <div class="buttonHolder">
                <a href="${referPath}" class="secondaryAction">&larr; <spring:message code="protocollo.dettaglio.button.indietro"/></a>
                <button type="submit" name="submit" class="primaryAction" value="<spring:message code="protocollo.dettaglio.button.avanti"/>"><spring:message code="protocollo.dettaglio.button.avanti"/></button>
            </div>
        </form>

    </div>
</div>
<script type="text/javascript">

    $("#frame1 .chosen-select").chosen({allow_single_deselect: true});
    $("#protocolloSegnatura").maskfield('GPPPP/0000/099999999', {translation: {'P': {pattern: /[a-zA-Z]/, optional: true}, 'G': {pattern: /[a-zA-Z]/}}});
    $("#creazioneEventoForm").submit(function(event) {
        var error = false;
        $(".message").remove();
        if (!$("#procedimentoSelezionato").val()) {
            $(".formHint.procedimentoSelezionato").addClass('errorHint');
            error = true;
        } else {
            $(".formHint.procedimentoSelezionato").removeClass('errorHint');
        }
        if (!$("#comuneSelezionato").val()) {
            $(".formHint.comuneSelezionato").addClass('errorHint');
            error = true;
        } else {
            $(".formHint.comuneSelezionato").removeClass('errorHint');
        }
        if (!$("#oggetto").val()) {
            $(".formHint.oggetto").addClass('errorHint');
            error = true;
        } else {
            $(".formHint.oggetto").removeClass('errorHint');
        }
        if (!$("#ricezioneData").val()) {
            $(".formHint.ricezioneData").addClass('errorHint');
            error = true;
        } else {
            $(".formHint.ricezioneData").removeClass('errorHint');
        }
        if (!$("#protocolloSegnatura").val() || !$("#protocolloSegnatura").val().match(/^[^/]+[/][^/]+[/][^/]+$/)) {
            $(".formHint.protocolloSegnatura").addClass('errorHint');
            error = true;
        } else {
            $(".formHint.protocolloSegnatura").removeClass('errorHint');
        }
        if (!$("#protocolloData").val()) {
            $(".formHint.protocolloData").addClass('errorHint');
            error = true;
        } else {
            $(".formHint.protocolloData").removeClass('errorHint');
        }

        var showPratica = error;
        var allegatiSize = $(".listaAllegati tr").size() - 1;
        if (allegatiSize < 1) {
            $(".allegatiVuoti").addClass('errorHint');
            showAllegatiTab();
            error = true;
        } else {
            $(".allegatiVuoti").removeClass('errorHint');
        }

        var riepilogoSettato = false;
        for (var i = 0; i < allegatiSize; i++) {
            if ($("input[type='checkbox'][name='allegatiList[" + i + "].modelloPratica']").val() == 'true') {
                var fileUploaded = $("input[name='allegatiList[" + i + "].file']").val();
                if (fileUploaded === '') {
                    riepilogoSettato = false;    
                } else {
                    riepilogoSettato = true;    
                }
                break;
            }
        }
        if (!riepilogoSettato) {
            $(".formHint.allegatiRiepilogo").addClass('errorHint');
            showAllegatiTab();
            error = true;
        } else {
            $(".formHint.allegatiRiepilogo").removeClass('errorHint');
        }

        if (showPratica) {
            showPraticaTab();
        }
        if (!showPratica && error) {
            showAllegatiTab();
        }
        if (error) {
            event.preventDefault();
        }
    });
    function showPraticaTab() {
        $("li>a[href='#frame2']").parent().removeClass("active");
        $("li>a[href='#frame1']").parent().addClass("active");
        $('.frames .frame').hide();
        $('#frame1').show();
    }

    function showAllegatiTab() {
        $("li>a[href='#frame1']").parent().removeClass("active");
        $("li>a[href='#frame2']").parent().addClass("active");
        $('.frames .frame').hide();
        $('#frame2').show();
    }

</script>