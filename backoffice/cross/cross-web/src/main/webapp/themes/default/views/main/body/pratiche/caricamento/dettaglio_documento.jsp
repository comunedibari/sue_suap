<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<tiles:insertAttribute name="body_error" />
<script>
    $(document).ready(function() {
        $('#comuneSelezionato').autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "<%= path%>/search/comuniEnti.htm",
                    dataType: "json",
                    data: {
                        descrizione: $("#comuneSelezionato").val()
                    },
                    success: function(data) {
                        response($.map(data.rows, function(item) {
                            return {
                                label: item.descrizione + " (" + item.provincia.descrizione + ")",
                                value: item.descrizione,
                                id: item.idComune
                            }
                        }));
                    }
                });
            },
            select: function(event, ui) {
                $('#comuneSelezionatoId').val(ui.item.id);

                $.ajax({
                    url: "<%= path%>/search/ente.htm",
                    dataType: "json",
                    data: {
                        idComune: ui.item.id
                    },
                    success: function(data) {
                        $("#descEnteDestinatario").val(data.rows.descrizione);
                        $.ajax({
                            url: "<%= path%>/search/ente.htm",
                            dataType: "json",
                            data: {
                                idComune: ui.item.id
                            },
                            success: function(data) {
                                $("#descEnteDestinatario").val(data.rows[0].descrizione);
                                $("#enteSUAPSelezionato").val(data.rows[0].idEnte);
                                $('#idEnteDestinatario').val(data.rows[0].idEnte);
                                ente = data.rows[0];
                            }
                        });
                    }
                });
            },
            minLength: 2
        });

        $('#descProcedimentoEnte').autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "<%= path%>/search/entiProcedimenti.htm",
                    dataType: "json",
                    data: {
                        desProcedimento: $('#descProcedimentoEnte').val()
                    },
                    success: function(data) {
                        response($.map(data.rows, function(item) {
                            return {
                                label: item.desProcedimento + " (" + item.desEnteDestinatario + ")",
                                value: item.desProcedimento,
                                idProcedimentoEnte: item.idProcedimentoEnte,
                                idProcedimento: item.idProcedimento,
                                idEnteDestinatario: item.idEnteDestinatario,
                                desEnteDestinatario: item.desEnteDestinatario,
                                desProcedimento: item.desProcedimento
                            }
                        }));
                    }
                });
            },
            select: function(event, ui) {
                $('#idProcedimentoEnte').val(ui.item.idProcedimentoEnte);
                $('#descProcedimento').val(ui.item.desProcedimento);
                $('#idProcedimento').val(ui.item.idProcedimento);
                $('#idEnte').val(ui.item.idEnteDestinatario);
                $('#descEnte').val(ui.item.desEnteDestinatario);
            },
            minLength: 2
        });

        var ente = null;
        var numRighe = 0;
        $("#aggiungiPrcedimentiEnti").click(function() {
            var comuneSelezionato = $("#comuneSelezionato").val();
            if ($.trim(comuneSelezionato) != "" && comuneSelezionato != null)
            {
                var proce = $("#descProcedimento").val();
                var idproce = $("#idProcedimento").val();
                var idente = $("#idEnte").val();
                var ente = $("#descEnte").val();
                var id = $("#idProcedimentoEnte").val();
                var tr = $("#ProcedimentiEnti #tr").clone();

                if ($.trim(id) != "" && id != null)
                {
                    $("#descProcedimentoEnte").val("");
                    $("#descProcedimento").val("");
                    $("#idProcedimento").val("");
                    $("#idEnte").val("");
                    $("#descEnte").val("");
                    $("#idProcedimentoEnte").val("");

                    var html = tr.html();
                    if (numRighe == 0)
                    {
                        numRighe = $("#ProcedimentiEnti table tr").length - 1;
                    }
                    html = html.replace("_ID_", id);
                    html = html.replace(/_I_/g, (numRighe))
                    html = html.replace("_idProcedimento_", idproce);
                    html = html.replace(/_idEnteDestinatario_/g, idente);
                    html = html.replace(/_Proce_/g, proce);
                    html = html.replace(/_Ente_/g, ente);
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
            }
            else
            {
                alert("Selezionare il comune");
            }
        });

    });
</script> 
<div class="sidebar_auto">                    
    <form id="creazioneEventoForm" action="<%= path%>/pratica/nuove/protocollo/step1.htm" class="uniForm inlineLabels comunicazione" method="post" enctype="multipart/form-data">
        <div class="page-control" data-role="page-control">
            <span class="menu-pull"></span> 
            <div class="menu-pull-bar"></div>
            <!-- Etichette cartelle -->
            <ul>
                <li class="active"><a href="#frame1"><spring:message code="protocollo.dettaglio.datipratica"/></a></li>
                <li><a href="#frame2"><spring:message code="protocollo.dettaglio.procedimenti"/></a></li>
                <c:if test="${!empty pratica.allegatiList}"><li><a href="#frame3"><spring:message code="protocollo.dettaglio.allegati.title"/></a></li></c:if>

                </ul>
                <!-- Contenuto cartelle -->
                <div class="frames">

                    <!-- frame 1 -->
                    <div class="frame active" id="frame1">
                        <div>
                            <div class="ctrlHolder">
                                <label for="fascicolo"><spring:message code="protocollo.dettaglio.fascicolo"/></label>
                            <input name="fascicolo" id="fascicolo" maxlength="255" disabled="disabled" type="text" class="textInput required" value="${protocollo.fascicolo}" />
                            <p class="formHint"></p>
                        </div>
                        <div class="ctrlHolder">
                            <label for="protocollo"><spring:message code="protocollo.dettaglio.protocollo"/></label>
                            <input name="protocollo" id="protocollo" maxlength="255" disabled="disabled" type="text" class="textInput required" value="${protocollo.protocollo}" />
                            <p class="formHint"></p>
                        </div>
                        <div class="ctrlHolder">
                            <label for="anno"><spring:message code="protocollo.dettaglio.anno"/></label>
                            <input name="anno" id="protocollo" maxlength="255" disabled="disabled" type="text" class="textInput required" value="${protocollo.anno}" />
                            <p class="formHint"></p>
                        </div>
                        <div class="ctrlHolder">
                            <label for="oggetto"><spring:message code="protocollo.dettaglio.oggetto"/></label>
                            <input name="oggetto" id="fascicolo" maxlength="255" disabled="disabled" type="text" class="textInput required" value="${protocollo.oggetto}" />
                            <p class="formHint"></p>
                        </div>
                        <div class="ctrlHolder">
                            <label for="dataricezione"><spring:message code="protocollo.dettaglio.dataricezione"/></label>
                            <input name="dataricezione" id="fascicolo" maxlength="255" disabled="disabled" type="text" class="textInput required" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${protocollo.dataRicezione}" />" />
                            <p class="formHint"></p>
                        </div>
                        <div class="ctrlHolder">
                            <label for="dataprotocollazione"><spring:message code="protocollo.dettaglio.dataprotocollazione"/></label>
                            <input name="dataprotocollazione" id="fascicolo" maxlength="255" disabled="disabled" type="text" class="textInput required" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${protocollo.dataProtocollazione}" />" />
                            <p class="formHint"></p>
                        </div>
                        <div class="ctrlHolder">
                            <label for="comuneSelezionato"><spring:message code="protocollo.dettaglio.comune"/></label>
                            <input name="comune.idComune" class=".hiddenValue" id="comuneSelezionatoId" type="hidden" value="${pratica.comune.idComune}"/>
                            <input name="comune.descrizione" id="comuneSelezionato" maxlength="255" size="35" type="text" class="textInput required" value="${pratica.comune.descrizione}"/>
                        </div>   
                        <div class="ctrlHolder">
                            <label for="comuneSelezionato"><spring:message code="protocollo.dettaglio.entesuap"/></label>
                            <select id="enteSUAPSelezionato" name="codEnte" maxlength="255"  class="textInput required" >
                                <option value=""><spring:message code="protocollo.dettaglio.ente.default"/></option>
                                <c:forEach items="${entiSuap}" var="ente" begin="0">
                                    <option value="${ente.idEnte}" <c:if test="${pratica.codEnte == ente.idEnte}">selected="selected"</c:if> >${ente.descrizione}</option>
                                </c:forEach>
                            </select>
                            <input type="hidden" name="codEnte" id="idEnteDestinatario" value="${pratica.codEnte}"/>
                        </div>
                    </div>
                </div>
                <div class="frame" id="frame2">
                    <div class="ctrlHolder">
                        <label><spring:message code="protocollo.dettaglio.procedimenti.seleziona"/></label>
                        <input id="descProcedimentoEnte" name ="" style="width: 58%;" maxlength="255" size="35" type="text" class="textInput required" value=""/>

                        <input id="descProcedimento"    type="hidden" value=""/>
                        <input id="idProcedimento"      type="hidden" value=""/>
                        <input id="idProcedimentoEnte" name="idProcedimentoEnte"  type="hidden" value=""/>
                        <input id="idEnte"             type="hidden"  value=""/>
                        <input id="descEnte"          type="hidden" value=""/>

                        <input type="button" name="action" id="aggiungiPrcedimentiEnti" class="primaryAction_aggiungi" value="Aggiungi"/>

                    </div>
                    <div class="ctrlHolder"<%-- ^^CS perche e' stato tolto questo id???????? Guardare javasccript no?--%> id="ProcedimentiEnti">
                        <table cellspacing="0" cellpadding="0" class="master">
                            <tr>
                                <th><spring:message code="protocollo.dettaglio.procedimenti.azione"/></th>
                                <th><spring:message code="protocollo.dettaglio.procedimenti.procedimento"/></th>
                                <th><spring:message code="protocollo.dettaglio.procedimenti.ente"/></th>
                            </tr>
                            <tr id="tr" style="display: none">
                                <td>
                                    <img src="<%=path%>/themes/default/images/icons/delete.png" alt="Elimina" title="Elimina" onclick="$(this).parent().parent().remove();" class="button"/>
                                    <input id="idProcedimento" disabled="disabled" type="hidden"  name ="procedimentiList[_I_].idProcedimento" value="_idProcedimento_"/>
                                    <input id="desProcedimento" disabled="disabled" type="hidden"  name ="procedimentiList[_I_].desProcedimento" value="_Proce_"/>
                                    <input id="desEnteDestinatario" disabled="disabled" type="hidden"  name ="procedimentiList[_I_].desEnteDestinatario" value="_Ente_"/>
                                    <input id="idEnte" disabled="disabled" type="hidden"  name ="procedimentiList[_I_].idEnteDestinatario" value="_idEnteDestinatario_"/>
                                </td>
                                <td>_Proce_</td>
                                <td>_Ente_</td>
                            </tr>
                            <c:set var="i" value="1"/>
                            <c:if test="${pratica.procedimentiList[0]!=null}">
                                <c:forEach items="${pratica.procedimentiList}" var="procedimentoEnte" begin="0">
                                    <tr >
                                        <td>
                                            <img src="<%=path%>/themes/default/images/icons/delete.png" alt="Elimina" title="Elimina" onclick="$(this).parent().parent().remove();" class="button"/>
                                            <input id="idProcedimento" type="hidden"  name ="procedimentiList[${i}].idProcedimento" value="${procedimentoEnte.idProcedimento}"/>
                                            <input id="desProcedimento" type="hidden"  name ="procedimentiList[${i}].desProcedimento" value="${procedimentoEnte.desProcedimento}"/>
                                            <input id="desEnteDestinatario" type="hidden"  name ="procedimentiList[${i}].desEnteDestinatario" value="${procedimentoEnte.desEnteDestinatario}"/>
                                            <input id="idEnte"  type="hidden"  name ="procedimentiList[${i}].idEnteDestinatario" value="${procedimentoEnte.idEnteDestinatario}"/>
                                        </td>
                                        <td>${procedimentoEnte.desProcedimento}</td>
                                        <td>${procedimentoEnte.desEnteDestinatario}</td> 
                                    </tr >    
                                    <c:set var="i" value="${i+1}"/>
                                </c:forEach>
                            </c:if>
                        </table>
                    </div>
                </div>

                <c:if test="${!empty pratica.allegatiList}">
                    <div class="frame" id="frame3">
                        <div>
                            <table cellspacing="0" cellpadding="0" class="master">
                                <tr>
                                    <th><spring:message code="protocollo.dettaglio.allegati.descrizione"/></th>
                                    <th><spring:message code="protocollo.dettaglio.allegati.nomefile"/></th>
                                    <th><spring:message code="protocollo.dettaglio.allegati.download"/></th>
                                </tr>
                                <c:set var="countAllegati" value="0" scope="page" />
                                <c:forEach items="${pratica.allegatiList}" var="allegato" begin="0">
                                    <tr>
                                        <td>
                                            ${allegato.descrizione}
                                            <input type="hidden" name="allegatiList[${countAllegati}].descrizione" value="${allegato.descrizione}"/>
                                        </td>
                                        <td>
                                            ${allegato.nomeFile}
                                            <input type="hidden" name="allegatiList[${countAllegati}].nomeFile" value="${allegato.nomeFile}"/>
                                        </td>
                                        <td align="center">
                                            <a class="scarica2" href="<%=path%>/protocollo/download.htm?id_file=${allegato.idFileEsterno}" target="_blank">
                                                .
                                            </a>
                                            <input type="hidden" name="allegatiList[${countAllegati}].idFileEsterno" value="${allegato.idFileEsterno}"/>
                                            <input type="hidden" name="allegatiList[${countAllegati}].modelloPratica" value="${allegato.modelloPratica}"/>

                                        </td>             
                                    </tr>
                                    <c:set var="countAllegati" value="${countAllegati + 1}" scope="page"/>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </c:if>
                
            </div>
        </div>
        <!-- fine cartelle -->
        <div class="buttonHolder">
            <button value="<spring:message code="protocollo.dettaglio.button.avanti"/>" class="primaryAction" name="submit" type="submit"><spring:message code="protocollo.dettaglio.button.conferma"/></button>
        </div>
    </form>
</div>