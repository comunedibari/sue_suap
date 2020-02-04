<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="anagraficaContainer">
    <!-- Cont div -->
    <c:set var="conto_div" value="0" scope="page" />


    <c:forEach items="${dettaglio.anagrafiche}" begin="0" var="anagrafica" varStatus="status">
        <c:choose>
            <c:when test="${anagrafica.tipoAnagrafica == 'G'}">
                <c:set var="conto_div" value="${conto_div + 1}" scope="page"/>
            </c:when>
            <c:otherwise>
                <c:set var="conto_div" value="${conto_div + 1}" scope="page"/>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <!-- fine cont div --> 
    <c:set var="conto_anagrafiche" value="0" scope="page"/>
    <div class=" imbox_frame  <c:if test="${conto_div > 1}" >margine_imbox_frame</c:if> anagraficabox">
            <div class="nascondiutente table-add-link">
                <a href="<%=path%>/pratica/dettaglio/collegaAnagrafica.htm">
                <spring:message code="pratica.comunicazione.collega.anagrafica"/>
                <img src="<%=path%>/themes/default/images/icons/add.png" alt="<spring:message code="pratica.comunicazione.collega.anagrafica"/>" title="<spring:message code="pratica.comunicazione.collega.anagrafica"/>">
            </a>
        </div>

        <c:if test="${conto_div > 1}">
            <div class="controllo_sinistra">
                <div class="controller_box">
                    <div class="box_left" onclick="item_animate('<c:out value="${conto_div}"></c:out>', '#anagrafica_box', '-=277px');"></div>
                    </div>
                </div>
        </c:if>

        <div id="anagrafica_box">   

            <table cellpadding="0" cellspacing="0">
                <tr>
                    <c:forEach items="${dettaglio.anagrafiche}" begin="0" var="anagrafica" varStatus="status">
                        <c:choose>
                            <c:when test="${anagrafica.tipoAnagrafica == 'G'}">
                                <c:set var="conto_anagrafiche" value="${conto_anagrafiche + 1}" scope="page"/>
                                <td>
                                    <script>
                        $(document).ready(function() {
                            // Load dialog on page load
                            //$('#basic-modal-content').modal();

                            // Load dialog on click
                            $('#<c:out value="anagrafica_${conto_anagrafiche}"/> .showdetail').click(function(e) {
                                $.ajax({
                                    type: 'POST',
                                    url: '<%=path%>/pratica/comunicazione/dettaglio/anagrafica.htm',

                                    data: {id_anagrafica: '${anagrafica.idAnagrafica}'}
                                }).done(function(data) {
                                    var wHeight = $(window).height() * 0.8;
                                    $('.dettaglioAnagraficaContainer').empty();
                                    $('#anagrafica_detail_${conto_anagrafiche}').html(data);
                                    $('#anagrafica_detail_${conto_anagrafiche}').dialog({
                                        title: 'Dettaglio anagrafica',
                                        modal: true,
                                        height: wHeight,
                                        width: '50%',
                                        dialogClass: 'cross_modal', /*,
                                         create: function(event, ui) {
                                         },
                                         open: function(event, ui) {
                                         $('html').css('overflow', 'hidden');
                                         },
                                         close: function(event, ui) {
                                         $('html').css('overflow', 'scroll');
                                         }*/
                                        buttons: {
                                            OK: function() {
                                                $(this).dialog("close");
                                            }
                                        }
                                    });
                                    return false;
                                });
                            });
                        });</script>  

                                    <div class="circle <c:if test="${status.last}">lastCircle</c:if> anagraficacircle" id="<c:out value="anagrafica_${conto_anagrafiche}" />" >
                                            <div class="ctrlHolder">
                                                <label class="required">
                                                <spring:message code="pratica.comunicazione.anagrafica.ruolo"/>
                                            </label>  
                                            <c:if test="${!(((anagrafica.ruolo eq 'Richiedente') && (fn:length(richiedenti) lt 2) ) ||  ( (anagrafica.ruolo eq 'Beneficiario') && (fn:length(beneficiari) lt 2)))}"> 
                                                <a style="padding: 10px; width: 75%;" href="<%=path%>/pratica/dettaglio/collegaAnagrafica/scollega.htm?idAnagrafica=${anagrafica.idAnagrafica}&idRuolo=${anagrafica.idRuolo}" class='nascondiutente showdetail'>
                                                    <img src="<%=path%>/themes/default/images/icons/link_break.png" alt="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>" title="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>">
                                                    &nbsp;&nbsp;<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>
                                                </a>
                                            </c:if>
                                            <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.ruolo}">
                                        </div>
                                        <div class="ctrlHolder">
                                            <label class="required">
                                                <spring:message code="pratica.comunicazione.anagrafica.denominazione"/>
                                            </label>
                                            <textarea class="textarea_anagrafica_disable" disabled="disabled">${anagrafica.ragioneSociale}</textarea>
                                        </div>
                                        <div class="ctrlHolder">
                                            <label class="required">
                                                <spring:message code="pratica.comunicazione.anagrafica.partitaiva"/>
                                            </label>
                                            <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.partitaIVA}">
                                        </div>

                                        <div id="<c:out value="anagrafica_detail_${conto_anagrafiche}" />" class="modal-content">
                                        </div>   

                                        <div class="cerca_lente_rosso  showdetail">
                                            <spring:message code="pratica.comunicazione.anagrafica.apridettaglio.click"/>
                                        </div>
                                    </div>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <c:set var="conto_anagrafiche" value="${conto_anagrafiche + 1}" scope="page"/>
                            <script>
                                $(document).ready(function() {
                                    // Load dialog on page load
                                    //$('#basic-modal-content').modal();

                                    // Load dialog on click
                                    $('#<c:out value="anagrafica_${conto_anagrafiche}"/> .showdetail').click(function(e) {
                                        $.ajax({
                                            type: 'POST',
                                            url: '<%=path%>/pratica/comunicazione/dettaglio/anagrafica.htm',
                                            data: {id_anagrafica: '${anagrafica.idAnagrafica}'}
                                        }).done(function(data) {
                                            var wHeight = $(window).height() * 0.8;
                                            $('.dettaglioAnagraficaContainer').empty();
                                            $('#anagrafica_detail_${conto_anagrafiche}').html(data);
                                            $('#anagrafica_detail_${conto_anagrafiche}').dialog({
                                                title: 'Dettaglio anagrafica',
                                                modal: true,
                                                height: wHeight,
                                                width: '50%',
                                                dialogClass: 'cross_modal', /*,
                                                 create: function(event, ui) {
                                                 },
                                                 open: function(event, ui) {
                                                 $('html').css('overflow', 'hidden');
                                                 },
                                                 close: function(event, ui) {
                                                 $('html').css('overflow', 'scroll');
                                                 }*/
                                                buttons: {
                                                    OK: function() {
                                                        $(this).dialog("close");
                                                    }
                                                }
                                            });
                                            return false;
                                        });
                                    });
                                });</script>  
                            <td class="anagraficadettaglio">
                                <div class=" anagraficacircle circle   <c:if test="${status.last}">lastCircle</c:if> " id="<c:out value="anagrafica_${conto_anagrafiche}" />" >
                                        <div class="ctrlHolder">
                                            <label class="required">
                                            <spring:message code="pratica.comunicazione.anagrafica.ruolo"/>
                                        </label>
                                        <c:if test="${!(((anagrafica.ruolo eq 'Richiedente') && (fn:length(richiedenti) lt 2) ) ||  ( (anagrafica.ruolo eq 'Beneficiario') && (fn:length(beneficiari) lt 2)))}"> 
                                            <a style="padding: 10px; width: 75%;" href="<%=path%>/pratica/dettaglio/collegaAnagrafica/scollega.htm?idAnagrafica=${anagrafica.idAnagrafica}&idRuolo=${anagrafica.idRuolo}" class='nascondiutente showdetail'>
                                                <img src="<%=path%>/themes/default/images/icons/link_break.png" alt="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>" title="<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>">
                                                &nbsp;&nbsp;<spring:message code="pratica.comunicazione.anagrafica.scollega.click"/>
                                            </a>
                                        </c:if>
                                        <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.ruolo}">
                                    </div>
                                    <div class="ctrlHolder">
                                        <label class="required">
                                            <spring:message code="pratica.comunicazione.anagrafica.nome"/>
                                        </label>
                                        <textarea class="textarea_anagrafica_disable" disabled="disabled">${anagrafica.nome}</textarea>
                                    </div>
                                    <div class="ctrlHolder">
                                        <label class="required">
                                            <spring:message code="pratica.comunicazione.anagrafica.cognome"/>
                                        </label>
                                        <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.cognome}">
                                    </div>
                                    <div class="ctrlHolder">
                                        <label class="required">
                                            <spring:message code="pratica.comunicazione.anagrafica.codicefiscale"/>
                                        </label>
                                        <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.codiceFiscale}">
                                    </div>
                                    <c:if test="${anagrafica.isDittaIndividuale}">
                                        <div class="ctrlHolder">
                                            <label class="required">
                                                <spring:message code="pratica.comunicazione.anagrafica.denominazione"/>
                                            </label>
                                            <span class="value">${anagrafica.ragioneSociale}</span>
                                        </div>
                                        <div class="ctrlHolder">
                                            <label class="required">
                                                <spring:message code="pratica.comunicazione.anagrafica.partitaiva"/>
                                            </label>
                                            <input class="input_anagrafica_disable" type="text" disabled="" value="${anagrafica.partitaIVA}">
                                        </div>
                                        <div class="ctrlHolder">
                                            <label class="required">
                                                <spring:message code="pratica.comunicazione.anagrafica.dittaindividuale"/>
                                            </label>
                                            <input class="input_anagrafica_disable" type="text" disabled="" value="<spring:message code="pratica.comunicazione.anagrafica.dittaindividuale.text"/>">
                                        </div>
                                    </c:if>

                                    <div  id="<c:out value="anagrafica_detail_${conto_anagrafiche}" />" class="modal-content">
                                    </div>


                                    <div class="cerca_lente_rosso  showdetail">
                                        <spring:message code="pratica.comunicazione.anagrafica.apridettaglio.click"/>
                                    </div>
                                </div>
                            </td>

                        </c:otherwise>
                    </c:choose>

                </c:forEach>
                </tr>
            </table>
        </div>

        <c:if test="${conto_div > 1}">
            <div class="controllo_destra">
                <div class="controller_box">
                    <div class="box_right" onclick="item_animate('<c:out value="${conto_div}"></c:out>', '#anagrafica_box', '+=277px');"></div>
                    </div>
                </div>
        </c:if>
        <div class="clear"></div>
    </div>
</div> 
<script language="javascript">
                                function item_animate(conto, id, px) {

                                    var massimo = (conto - 1) * 277;
                                    var ultimo = 0 - ((conto - 2) * 277);
                                    var minimo = 0 - ((conto - 1) * 277);
                                    var attuale = parseInt($(id).css("marginLeft"));
                                    if (attuale === minimo) {
                                        $(id).animate({'marginLeft': 0});
                                    }
                                    else {
                                        if (attuale === massimo) {
                                            $(id).animate({'marginLeft': ultimo});
                                        } else {
                                            $(id).animate({'marginLeft': px});
                                        }
                                    }
                                }
</script>

