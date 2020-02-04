<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
    String urlRicercaDatiCatastali = request.getContextPath() + "/custom/view/genova/ricercatoponomastica.htm";
%>
<script>
    function e(val){
        if(val&&val!=='undefined'){
            return val;          
        }
        return '';
    }
    
    function selezionaDatoCatastale(tableIndex, index) {
        var tr = $('#row_' + tableIndex);
        var desVia = $(tr).find("td").eq(0).html();
        $('#datiCatastali' + index + '_indirizzo').val(desVia);

        var codVia = $('#row_codVia_' + tableIndex).val();
        $('#datiCatastali' + index + '_codVia').val(codVia);

        var codCivico = $(tr).find("td").eq(1).html();
        $('#datiCatastali' + index + '_civico').val(codCivico);
        $('#datiCatastali' + index + '_codCivico').val(codCivico);

        var lettera = $(tr).find("td").eq(2).html();
        $('#datiCatastali' + index + '_lettera').val(lettera);

        var colore = $(tr).find("td").eq(3).html();
        $('#datiCatastali' + index + '_colore').val(colore);

        var interno = $(tr).find("td").eq(4).html();
        $('#datiCatastali' + index + '_interno').val(interno);

        var letteraInterno = $(tr).find("td").eq(5).html();
        $('#datiCatastali' + index + '_letteraInterno').val(letteraInterno);

        var scala = $(tr).find("td").eq(6).html();
        $('#datiCatastali' + index + '_scala').val(scala);
        //Pulizia tabella e visualizzazione pulsante
        $('#datiCatastaliTable' + index).remove();
    }
    
    function ricercaDatiCatastali(index) {
        var nCivico = $('#datiCatastali' + index + '_civico').val();
        var sIndirizzo = $('#datiCatastali' + index + '_indirizzo').val();
        $.post("<%= urlRicercaDatiCatastali%>", {civico: nCivico, indirizzo: sIndirizzo, index: index, idPratica:'${idPratica}'}, function(result) {
            var data = $.parseJSON(result);
            if (data.error != null) {
                alert(data.error);
            } else {
                if (data.rows != null) {
                    $('#datiCatastaliTable' + index).remove();
                    var ul = $('#datiCatastali' + index);
                    var table = "<table cellspacing='0' cellpadding='0' class='master datiCatastaliSearchResult' id='datiCatastaliTable" + index + "'>" +
                            "<tbody>" +
                            "<tr>" +
                            "<th>Indirizzo</th>" +
                            "<th>Civico</th>" +
                            "<th>Lettera</th> " +
                            "<th>Colore</th>" +
                            "<th>Interno</th>" +
                            "<th>Lettera interno</th>" +
                            "<th>Scala</th>" +
                            "<th>CAP</th>" +
                            "<th></th>" +
                            "</tr>";
                    for (var i = 0; i < data.total; i++) {
                        var button = "<button onclick='selezionaDatoCatastale(" + i + ", " + index + ")' class='datiCatastali0 ui-button ui-widget ui-state-default ui-corner-all' role='button' aria-disabled='false'>" +
                                "<span class='ui-button-text'>Seleziona</span>" +
                                "</button>";

                        var tRow = "<tr id='row_" + i + "'>" +
                                "<td>" + e(data.rows[i].desVia) + "</td>" +
                                "<td>" + e(data.rows[i].codCivico) + "</td>" +
                                "<td>" + e(data.rows[i].lettera) + "</td>" +
                                "<td>" + e(data.rows[i].colore) + "</td>" +
                                "<td>" + e(data.rows[i].numeroInterno)+ "</td>" +
                                "<td>" + e(data.rows[i].letteraInterno) + "</td>" +
                                "<td>" + e(data.rows[i].scalaInterno) + "</td>" +
                                "<td>" + e(data.rows[i].cap) + "</td>" +
                                "<td><input type='hidden' name='codVia' id='row_codVia_" + i + "' value='" + data.rows[i].codVia + "' />" + button + "</td>" +
                                "</tr>";
                        table = table + tRow;
                    }
                    table = table + "</tbody></table>";
                    ul.after(table);
                }
            }
        });
    }
</script>

<div class="inlineLabels">
    <c:choose>
        <c:when test="${pratica.datiCatastali != null && pratica.datiCatastali[0] != null}">
            <c:set var="disabled" value="disabled=\"disabled\" readonly=\"readonly\""/>
            <c:if test="${(pratica.tipoMessaggio != null || pratica.tipoMessaggio != 'AEC')}">
                <c:set var="disabled" value=""/>
                <button class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false" name="datiCatastaliAggiungi" id="datiCatastaliAggiungi">
                    <span class="ui-button-text">Aggiungi</span>
                </button> 
            </c:if>
            <div class="ctrlHolder DatiCatastali">
                <c:set var="i" value="0"/>
                <c:forEach items="${pratica.datiCatastali}" var="datiCatastali" begin="0">
                    <ul id="datiCatastali${i}" class="DatiCatastaliSingle">
                        <li class="hidden">
                            <input class="counter" type="hidden" value="${datiCatastali.counter}" name="datiCatastali[${i}].counter"/>
                            <input class="idPratica" type="hidden" value="${idPratica}" name="datiCatastali[${i}].idPratica"/>
                            <input class="idImmobile" type="hidden" value="${datiCatastali.idImmobile}" name="datiCatastali[${i}].idImmobile"/>
                            <input class="idComune"  type="hidden" value="${datiCatastali.idComune}" name="datiCatastali[${i}].idComune" />
                            <input class="idProvincia"  type="hidden" value="${datiCatastali.idProvincia}" name="datiCatastali[${i}].idProvincia"/>
                            <input class="codVia"  type="hidden" value="${datiCatastali.codVia}" name="datiCatastali[${i}].codVia"/>
                            <input class="codCivico"  type="hidden" value="${datiCatastali.codCivico}" name="datiCatastali[${i}].codCivico"/>
                            <input class="index"  type="hidden" value="${i}" name="datiCatastali[${i}].index"/>
                        </li>
                        <li>
                            <div class="ctrlHolder distanziatore">
                                <label class="required">
                                    <spring:message code="datiCatastali.idTipoSistemaCatastale"/>
                                </label>
                                <input name="datiCatastali[${i}].idTipoSistemaCatastale" value="${datiCatastali.idTipoSistemaCatastale}" type="hidden"  class="datiCatastali${i} idTipoSistemaCatastale  " />
                                <input name="datiCatastali[${i}].desTipoSistemaCatastale" value="${datiCatastali.desTipoSistemaCatastale}" type="text" ${disabled} class="input_anagrafica_disable " /><!-- datiCatastali${i} desTipoSistemaCatastale -->
                            </div>
                        </li>
                        <li>
                            <div class="ctrlHolder distanziatore">
                                <label class="required"><spring:message code="datiCatastali.indirizzo"/></label>
                                <input name="datiCatastali[${i}].indirizzo" value="${datiCatastali.indirizzo}" id="datiCatastali${i}_indirizzo" type="text" ${disabled} class="datiCatastali${i} indirizzo  " />
                            </div>
                        </li>
                        <li>
                            <div class="ctrlHolder distanziatore">
                                <label class="required"><spring:message code="datiCatastali.civico"/></label>
                                <input name="datiCatastali[${i}].civico" value="${datiCatastali.civico}" type="text" id="datiCatastali${i}_civico" ${disabled} class="datiCatastali${i} civico  " />
                            </div>
                        </li>
                        <li>
                            <div class="ctrlHolder distanziatore">
                                <label class="required"><spring:message code="datiCatastali.interno"/></label>
                                <input name="datiCatastali[${i}].interno" value="${datiCatastali.interno}" type="text" id="datiCatastali${i}_interno" ${disabled} class="datiCatastali${i} interno  " />
                            </div>
                        </li>
                        <li>
                            <div class="ctrlHolder distanziatore">
                                <label class="required"><spring:message code="datiCatastali.lettera"/></label>
                                <input name="datiCatastali[${i}].lettera" value="${datiCatastali.lettera}" type="text" ${disabled} id="datiCatastali${i}_lettera" class="datiCatastali${i} lettera  " />
                            </div>
                        </li>
                        <li>
                            <div class="ctrlHolder distanziatore">
                                <label class="required"><spring:message code="datiCatastali.scala"/></label>
                                <input name="datiCatastali[${i}].scala" value="${datiCatastali.scala}" type="text" ${disabled} id="datiCatastali${i}_scala" class="datiCatastali${i} scala  " />
                            </div>
                        </li>
                        <li>
                            <div class="ctrlHolder distanziatore">
                                <label class="required"><spring:message code="datiCatastali.letterainterno"/></label>
                                <input name="datiCatastali[${i}].letterainterno" value="${datiCatastali.letterainterno}" type="text" ${disabled} id="datiCatastali${i}_letteraInterno" class="datiCatastali${i} letterainterno  " />
                            </div>
                        </li>
                        <li>
                            <div class="ctrlHolder distanziatore">
                                <label class="required"><spring:message code="datiCatastali.foglio"/></label>
                                <input name="datiCatastali[${i}].foglio" value="${datiCatastali.foglio}" type="text" ${disabled} class="datiCatastali${i} foglio  " /></div>
                        </li>
                        <li>
                            <div class="ctrlHolder distanziatore">
                                <label class="required"><spring:message code="datiCatastali.mappale"/></label>
                                <input name="datiCatastali[${i}].mappale" value="${datiCatastali.mappale}" type="text" ${disabled} class="datiCatastali${i} mappale  " /></div>
                        </li>
                        <li>
                            <div class="ctrlHolder distanziatore">
                                <label class="required"><spring:message code="datiCatastali.sezione"/></label>
                                <input name="datiCatastali[${i}].desProvincia" value="${datiCatastali.sezione}" type="text" ${disabled} class="datiCatastali${i} sezione  " /></div>
                        </li>
                        <li>
                            <div class="ctrlHolder distanziatore"><label class="required"><spring:message code="datiCatastali.subalterno"/></label>
                                <input name="datiCatastali[${i}].subalterno" value="${datiCatastali.subalterno}" type="text" ${disabled} class="datiCatastali${i} subalterno  " /></div>
                        </li>
                        <li class="datiCatastali0 " style="display:block;width:100%">
                            <button class="datiCatastali${i}} ui-button ui-widget ui-state-default ui-corner-all salvaDatiCatastali" role="button" aria-disabled="false" name="datiCatastali_0">
                                <span class="ui-button-text"><spring:message code="pratica.titolo.salva"/></span>
                            </button>

                            <button onclick="ricercaDatiCatastali(${i})" class="datiCatastali${i} ui-button ui-widget ui-state-default ui-corner-all cercaDatiCatastali " role="button" aria-disabled="false" id="datiCatastali${i}_cerca">
                                <span class="ui-button-text">Cerca</span>
                            </button>

                            <button class="datiCatastali0 ui-button ui-widget ui-state-default ui-corner-all eliminaDatiCatastali" role="button" aria-disabled="false" name="datiCatastali_${i}">
                                <span class="ui-button-text">Elimina</span>
                            </button>
                        </li>
                        <c:set var="i" value="${i+1}"/>
                    </ul>
                </c:forEach>
            </div>
        </c:when>
        <c:when test="${empty pratica.datiCatastali &&(pratica.tipoMessaggio != null || pratica.tipoMessaggio != 'AEC')}">
            <button class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false" name="datiCatastaliAggiungi" id="datiCatastaliAggiungi">
                <span class="ui-button-text">Aggiungi</span>
            </button> 
            <div class="ctrlHolder DatiCatastali">
                <ul id="datiCatastali0" class="DatiCatastaliSingle">
                    <li class="hidden">
                        <input class="idPratica" type="hidden" value="${idPratica}" name="datiCatastali[0].idPratica"/>
                        <input class="idImmobile" type="hidden" name="datiCatastali[0].idImmobile"/>
                        <input class="idComune"  type="hidden" name="datiCatastali[0].idComune" />
                        <input class="idProvincia"  type="hidden" name="datiCatastali[0].idProvincia"/>
                        <input class="codVia"  type="hidden" id="datiCatastali0_codVia" name="datiCatastali[0].codVia" />
                        <input class="codCivico"  type="hidden" id="datiCatastali0_codCivico" name="datiCatastali[0].codCivico"/>
                        <input class="index"  type="hidden" value="0" name="datiCatastali[0].index"/>
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required">
                                <spring:message code="datiCatastali.idTipoSistemaCatastale"/>
                            </label>
                            <form:select name="datiCatastali[0].idTipoSistemaCatastale" path="pratica" cssClass="datiCatastali0 desTipoSistemaCatastale" >
                                <form:option value="" label="Seleziona"/>
                                <form:options items="${TipoSistemaCatastaleList}" />
                            </form:select>
                        </div>
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.indirizzo"/></label>
                            <input name="datiCatastali[0].indirizzo" id="datiCatastali0_indirizzo" value="" type="text"  class="datiCatastali0 indirizzo " />
                        </div>
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.civico"/></label>
                            <input name="datiCatastali[0].civico" id="datiCatastali0_civico" value="" type="text"  class="datiCatastali0 civico " />
                        </div>
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.interno"/></label>
                            <input name="datiCatastali[0].interno" value="" type="text" id="datiCatastali0_interno"  class="datiCatastali0 interno " />
                        </div>
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.lettera"/></label>
                            <input name="datiCatastali[0].lettera" value="" type="text" id="datiCatastali0_lettera" class="datiCatastali0 lettera " />
                        </div>
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.scala"/></label>
                            <input name="datiCatastali[0].scala" value="" type="text" id="datiCatastali0_scala" class="datiCatastali0 scala " />
                        </div>
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.letterainterno"/></label>
                            <input name="datiCatastali[0].letterainterno" value="" type="text" id="datiCatastali0_letteraInterno" class="datiCatastali0 letterainterno " />
                        </div>
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.foglio"/></label>
                            <input name="datiCatastali[0].foglio" value="" type="text"  class="datiCatastali0 foglio" />
                        </div>
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.mappale"/></label>
                            <input name="datiCatastali[0].mappale" value="" type="text"  class="datiCatastali0 mappale" />
                        </div>
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore">
                            <label class="required"><spring:message code="datiCatastali.sezione"/></label>
                            <input name="datiCatastali[0].sezione" value="" type="text"  class="datiCatastali0 sezione" />
                        </div>
                    </li>
                    <li>
                        <div class="ctrlHolder distanziatore"><label class="required"><spring:message code="datiCatastali.subalterno"/></label>
                            <input name="datiCatastali[0].subalterno" value="" type="text"  class="datiCatastali0 subalterno" />
                        </div>
                    </li>
                    <li class="datiCatastali0 " style="display:block;width:100%">

                        <button class="datiCatastali0 ui-button ui-widget ui-state-default ui-corner-all salvaDatiCatastali" role="button" aria-disabled="false" name="datiCatastali_0">
                            <span class="ui-button-text"><spring:message code="pratica.titolo.salva"/></span>
                        </button>

                        <button onclick="ricercaDatiCatastali(0)" class="datiCatastali0 ui-button ui-widget ui-state-default ui-corner-all cercaDatiCatastali " role="button" aria-disabled="false" id="datiCatastali0_cerca">
                            <span class="ui-button-text">Cerca</span>
                        </button>

                        <button class="datiCatastali0 ui-button ui-widget ui-state-default ui-corner-all eliminaDatiCatastali" role="button" aria-disabled="false" name="datiCatastali_0">
                            <span class="ui-button-text">Elimina</span>
                        </button>
                    </li>
                </ul>
            </div>
        </c:when>
    </c:choose>
</div>

<div class="hidden">
    <ul id="datiCatastaliX" class="DatiCatastaliSingle" style="border-top: 1px solid;">

        <li class="hidden">
            <input class="idPratica" type="hidden" value="${idPratica}" name="datiCatastali[X].idPratica"/>
            <input class="idImmobile" type="hidden" value="" name="datiCatastali[X].idImmobile"/>
            <input class="idComune"  type="hidden" value="" name="datiCatastali[X].idComune" />
            <input class="idProvincia"  type="hidden" value="" name="datiCatastali[X].idProvincia"/>
            <input class="codVia"  type="hidden" value="" id="datiCatastaliX_codVia" name="datiCatastali[X].codVia"/>
            <input class="codCivico"  type="hidden" value="" id="datiCatastaliX_codCivico" name="datiCatastali[X].codCivico"/>
            <input class="index"  type="hidden" value="X" name="datiCatastali[X].index"/>
        </li>
        <li>
            <div class="ctrlHolder distanziatore">
                <label class="required">
                    <spring:message code="datiCatastali.idTipoSistemaCatastale"/>
                </label>
                <form:select name="datiCatastali[X].idTipoSistemaCatastale" path="pratica" cssClass="datiCatastaliX desTipoSistemaCatastale" >
                    <form:option value="" label="Seleziona"/>
                    <form:options items="${TipoSistemaCatastaleList}" />
                </form:select>
            </div>
        </li>
        <li>
            <div class="ctrlHolder distanziatore">
                <label class="required"><spring:message code="datiCatastali.indirizzo"/></label>
                <input name="datiCatastali[X].indirizzo" value="" type="text" id="datiCatastaliX_indirizzo" class="datiCatastaliX indirizzo " />
            </div>
        </li>
        <li>
            <div class="ctrlHolder distanziatore">
                <label class="required"><spring:message code="datiCatastali.civico"/></label>
                <input name="datiCatastali[X].civico" value="" type="text" id="datiCatastaliX_civico"  class="datiCatastaliX civico " />
            </div>
        </li>
        <li>
            <div class="ctrlHolder distanziatore">
                <label class="required"><spring:message code="datiCatastali.interno"/></label>
                <input name="datiCatastali[X].interno" value="" type="text" id="datiCatastaliX_interno"  class="datiCatastaliX interno " />
            </div>
        </li>
        <li>
            <div class="ctrlHolder distanziatore">
                <label class="required"><spring:message code="datiCatastali.lettera"/></label>
                <input name="datiCatastali[X].lettera" value="" type="text" id="datiCatastaliX_lettera"  class="datiCatastaliX lettera " />
            </div>
        </li>
        <li>
            <div class="ctrlHolder distanziatore">
                <label class="required"><spring:message code="datiCatastali.scala"/></label>
                <input name="datiCatastali[X].scala" value="" type="text"  id="datiCatastaliX_scala" class="datiCatastaliX scala " />
            </div>
        </li>
        <li>
            <div class="ctrlHolder distanziatore">
                <label class="required"><spring:message code="datiCatastali.letterainterno"/></label>
                <input name="datiCatastali[X].letterainterno" value="" type="text" id="datiCatastaliX_letteraInterno" class="datiCatastaliX letterainterno " />
            </div>
        </li>
        <li>
            <div class="ctrlHolder distanziatore">
                <label class="required"><spring:message code="datiCatastali.foglio"/></label>
                <input name="datiCatastali[X].foglio" value="" type="text"  class="datiCatastaliX foglio" />
            </div>
        </li>
        <li>
            <div class="ctrlHolder distanziatore">
                <label class="required"><spring:message code="datiCatastali.mappale"/></label>
                <input name="datiCatastali[X].mappale" value="" type="text"  class="datiCatastaliX mappale" />
            </div>
        </li>
        <li>
            <div class="ctrlHolder distanziatore">
                <label class="required"><spring:message code="datiCatastali.sezione"/></label>
                <input name="datiCatastali[X].sezione" value="" type="text"  class="datiCatastaliX sezione" />
            </div>
        </li>
        <li>
            <div class="ctrlHolder distanziatore"><label class="required"><spring:message code="datiCatastali.subalterno"/></label>
                <input name="datiCatastali[X].subalterno" value="" type="text"  class="datiCatastaliX subalterno" />
            </div>
        </li>
        <li class="datiCatastaliX " style="display:block;width:100%">
            <button class="datiCatastaliX ui-button ui-widget ui-state-default ui-corner-all salvaDatiCatastali" role="button" aria-disabled="false" name="datiCatastali_X">
                <span class="ui-button-text"><spring:message code="pratica.titolo.salva"/></span>
            </button>

            <button onclick="ricercaDatiCatastali(X)" class="datiCatastaliX ui-button ui-widget ui-state-default ui-corner-all cercaDatiCatastali " role="button" aria-disabled="false" id="datiCatastaliX_cerca">
                <span class="ui-button-text">Cerca</span>
            </button>

            <button class="datiCatastaliX ui-button ui-widget ui-state-default ui-corner-all eliminaDatiCatastali" role="button" aria-disabled="false" name="datiCatastali_X">
                <span class="ui-button-text">Elimina</span>
            </button>
        </li>
    </ul>                            
</div>
