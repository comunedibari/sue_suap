<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- ^^CS AGGIUNTA--%>

<script type="text/javascript">
    $(document).ready(function() {
        $(".seltipoAnagrafica").change(function()
        {
            var idParent = $($(this).parent()).attr("id");
            if ($(this).val() === 'F')
            {
                $("#" + idParent + " .tipoAnagrafica").val("F");
                $("#" + idParent + " .varianteAnagrafica").val("");
            }
            //            if($(this).val()== 'P')
            //            {
            //                $("#"+idParent+" .tipoAnagrafica").val("F");
            //                $("#"+idParent+" .varianteAnagrafica").val($(this).val());
            //            }
            if ($(this).val() === 'I')
            {
                $("#" + idParent + " .tipoAnagrafica").val("F");
                $("#" + idParent + " .varianteAnagrafica").val($(this).val());
            }
            if ($(this).val() === 'G')
            {
                $("#" + idParent + " .tipoAnagrafica").val("G");
                $("#" + idParent + " .varianteAnagrafica").val("");
            }

        });
        $('#desProvinciaIscrizioneRi').autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "<%= path%>/search/province.htm",
                    dataType: "json",
                    data: {
                        description: $("#desProvinciaIscrizioneRi").val(),
                        flgInfocamere: 'S'
                    },
                    success: function(data) {
                        response($.map(data, function(item) {
                            return {
                                label: item.descrizione,
                                value: item.descrizione,
                                codCatastale: item.codCatastale,
                                id: item.idProvincie
                            };
                        }));
                    }
                });
            },
            select: function(event, ui) {
                $('#nProvinciaIscrizioneRi').val(ui.item.id);
            },
            minLength: 2
        });

        $('#desFormaGiuridica').autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "<%= path%>/search/formagiuridica.htm",
                    dataType: "json",
                    data: {
                        description: $("#desFormaGiuridica").val()
                    },
                    success: function(data) {
                        response($.map(data, function(item) {
                            return {
                                label: item.descrizione,
                                value: item.descrizione,
                                id: item.idFormaGiuridica
                            };
                        }));
                    }
                });
            },
            select: function(event, ui) {
                $('#idFormaGiuridica').val(ui.item.id);
            },
            minLength: 2
        });

        $('#desCittadinanza').autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "<%= path%>/search/cittadinanza.htm",
                    dataType: "json",
                    data: {
                        description: $("#desCittadinanza").val()
                    },
                    success: function(data) {
                        response($.map(data, function(item) {
                            return {
                                label: item.descrizione,
                                value: item.descrizione,
                                id: item.idCittadinanza
                            };
                        }));
                    }
                });
            },
            select: function(event, ui) {
                $('#idCittadinanza').val(ui.item.id);
            },
            minLength: 2
        });

        $('#desComuneNascita').autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "<%= path%>/search/comune.htm",
                    dataType: "json",
                    data: {
                        description: $("#desComuneNascita").val(),
                        dataValidita: $("#dataNascita").val()
                    },
                    success: function(data) {
                        response($.map(data, function(item) {
                            return {
                                label: item.descrizione + "(" + item.provincia.codCatastale + ")",
                                value: item.descrizione + "(" + item.provincia.codCatastale + ")",
                                id: item.idComune
                            };
                        }));
                    }
                });
            },
            select: function(event, ui) {
                $('#nComuneNascita').val(ui.item.id);
            },
            minLength: 2
        });

        $('#desTipoCollegio').autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "<%= path%>/search/tipocollegio.htm",
                    dataType: "json",
                    data: {
                        description: $("#desTipoCollegio").val()
                    },
                    success: function(data) {
                        response($.map(data, function(item) {
                            return {
                                label: item.descrizione,
                                value: item.descrizione,
                                id: item.idTipoCollegio
                            };
                        }));
                    }
                });
            },
            select: function(event, ui) {
                $('#nTipocollegio').val(ui.item.id);
            },
            minLength: 2
        });

        $('#desProvinciaIscrizione').autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "<%= path%>/search/province.htm",
                    dataType: "json",
                    data: {
                        description: $("#desProvinciaIscrizione").val(),
                        flgInfocamere: 'N'
                    },
                    success: function(data) {
                        response($.map(data, function(item) {
                            return {
                                label: item.descrizione,
                                value: item.descrizione,
                                codCatastale: item.codCatastale,
                                id: item.idProvincie
                            };
                        }));
                    }
                });
            },
            select: function(event, ui) {
                $('#nProvinciaIscrizione').val(ui.item.id);
            },
            minLength: 2
        });

        $('.ComuneRecapito').autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "<%= path%>/search/comune.htm",
                    dataType: "json",
                    data: {
                        description: $(this.element).val(),
                        dataValidita: ''
                    },
                    success: function(data) {
                        response($.map(data, function(item) {
                            return {
                                label: item.descrizione + "(" + item.provincia.codCatastale + ")",
                                value: item.descrizione + "(" + item.provincia.codCatastale + ")",
                                id: item.idComune,
                                desc: item.descrizione
                            };
                        }));
                    }
                });
            },
            select: function(event, ui) {
                var idParent = $(this).parent().attr("id");
                $('#' + idParent + ' .nComuneRecapito').val(ui.item.id);
                $('#' + idParent + ' .desComuneRecapito').val(ui.item.desc);
            },
            minLength: 2
        });

        $("#dataIscrizioneRea").datepicker();
        $("#dataIscrizioneRi").datepicker();
        $("#dataNascita").datepicker();
        $("#dataIscrizione").datepicker();

        $("select.tipoIndirizzo:not(:disabled)").change(function() {
            var idParent = $($(this).parent()).attr("id");
            $("#" + idParent + " .descTipoIndirizzo").val($(this).find(":selected").text());
        });

        $(".scelta").hide();
        $(".seltipoAnagrafica").change(function() {
            $(this).parent().parent().find(".scelta").hide();
            var val = $(this).val();
            $(this).parent().parent().find("." + val).show();
            var select = $(this).parent().parent().parent().parent().parent().find("#tipoIndirizzo");
            var optio = select.find("option");
            var id = 0;
            var ricerca = "RESIDENZA";
            if (val === "G")
            {
                ricerca = "SEDE";
            }
            else
            {
                ricerca = "RESIDENZA";
            }
            i = 0;
            for (i = 0; i < $(optio).length; i++)
            {
                if ($($(optio)[i]).text() === ricerca)
                {
                    id = $($(optio)[i]).val();
                    val = $($(optio)[i]).text();
                }
            }
            select.val(id);
            $(this).parent().parent().parent().parent().parent().find(".descTipoIndirizzo").val(val);
            $(this).parent().parent().parent().parent().parent().find(".idTipoIndirizzo").val(id);
        });

    });
</script>

<div class="">
    <div class="sidebar_auto">
        <div class="wizard-steps">
            <div class="completed-step"><a href="javascript:void(0)" class="disabled"><span>1</span> Integrazione pratica</a></div>
            <div class="active-step"><a href="javascript:void(0)" class="disabled"><span>2</span> Integrazioni anagrafiche</a></div>
            <div><a href="javascript:void(0)" class="disabled"><span>3</span> Conferma</a></div>
        </div>  
        <hr/>

        <tiles:insertAttribute name="body_error_custom" />

        <form id="confermaAnagraficheForm" novalidate="novalidate" action="<%= path%>/pratica/nuove/protocollo/step2.htm" class="uniForm inlineLabels comunicazione" method="post" enctype="multipart/form-data">
            <input type="hidden" name="idPraticaProtocollo" value="${praticaProtocolloDTO.idProtocollo}"/>
            <div class="page-control" data-role="page-control">
                <span class="menu-pull"></span> 
                <div class="menu-pull-bar"></div>
                <!-- Etichette cartelle -->
                <ul>
                    <li class="active"><a href="#frame1"><spring:message code="protocollo.dettaglio.anagrafica.title"/></a></li>
                    <li><a href="#frame2"><spring:message code="protocollo.dettaglio.notifica.title"/></a></li>
                </ul>
                <!-- Contenuto cartelle -->
                <div class="frames">
                    <div class="frame active" id="frame1">
                        <c:if test="${not empty mittenti}">
                            <div class="ctrlHolder">
                                <div class="mittentiTitle"><spring:message code="protocollo.dettaglio.mittenti.title"/></div>
                                <div class="mittentiBox">${mittenti}</div>
                            </div>
                        </c:if>
                        <c:set var="count" value="0" scope="page" />
                        <c:forEach items="${anagrafiche}" var="anagrafica" begin="0">

                            <%-- <h2 class="separatore_h2"><spring:message code="protocollo.dettaglio.anagrafica.title"/></h2> --%>
                            <div>
                                <input  name="anagraficaList[${count}].counter" type="hidden" value="${anagrafica.counter}" />
                                <c:choose>
                                    <c:when test="${!empty anagrafica.idAnagrafica}">

                                        <input name="anagraficaList[${count}].tipoAnagrafica" type="hidden" value="${anagrafica.tipoAnagrafica}" />
                                        <input name="anagraficaList[${count}].varianteAnagrafica" type="hidden" value="${anagrafica.varianteAnagrafica}" />
                                        <input  name="anagraficaList[${count}].manuale" type="hidden" value="false" />
                                        <c:if test="${anagrafica.tipoAnagrafica == 'G'}">
                                            <div class="personaGiuridica">
                                                <%-- Persona giuridica --%>
                                                <div class="separatore_bordo">
                                                    <legend><spring:message code="anagrafica.personagiuridica"/></legend>
                                                    <div>
                                                        <div class="ctrlHolder">
                                                            <label for="tipoRuolo"><spring:message code="protocollo.dettaglio.anagrafica.ruolo"/></label>
                                                            <select id="tipoRuolo" name="anagraficaList[${count}].idTipoRuolo">
                                                                <option value="">Seleziona tipo ruolo</option>

                                                                <c:forEach items="${ruoliAnagrafica}" var="ruolo" begin="0">
                                                                    <option value="${ruolo.idTipoRuolo}" <c:if test="${anagrafica.idTipoRuolo == ruolo.idTipoRuolo}">selected="selected"</c:if> >${ruolo.desTipoRuolo}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                        <div class="ctrlHolder">
                                                            <label for="tipoQualifica"><spring:message code="protocollo.dettaglio.anagrafica.qualifica"/></label>
                                                            <select id="tipoQualifica" name="anagraficaList[${count}].idTipoQualifica">
                                                                <option value=""><spring:message code="protocollo.dettaglio.anagrafica.qualifica.default"/></option>
                                                                <c:forEach items="${qualificaAnagrafica}" var="qualifica" begin="0">
                                                                    <option value="${qualifica.idTipoQualifica}" <c:if test="${anagrafica.idTipoQualifica == qualifica.idTipoQualifica}">selected="selected"</c:if> >${qualifica.desTipoQualifica}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                        <div class="ctrlHolder">
                                                            <label for="denominazione"><spring:message code="anagrafica.denominazione"/></label>
                                                            <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].denominazione" id="denominazione" maxlength="255" type="text" class="textInput required" value="${anagrafica.denominazione}" />
                                                        </div>

                                                        <div class="ctrlHolder">
                                                            <label for="desFormaGiuridica"><spring:message code="anagrafica.formagiuridica"/></label>
                                                            <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].idFormaGiuridica" id="idFormaGiuridica" class="hiddenValue" type="hidden" value="${anagrafica.idFormaGiuridica}"/>
                                                            <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].desFormaGiuridica" id="desFormaGiuridica" maxlength="255" type="text" class="textInput required" value="${anagrafica.desFormaGiuridica}" />
                                                        </div>
                                                        <%--
                                                        <div class="ctrlHolder">
                                                            
                                                            <label for="flgIndividuale"><spring:message code="anagrafica.impresaindividuale"/></label>
                                                            <select readonly="readonly" disabled="disabled" id="flgIndividuale" name="anagraficaList[${count}].flgIndividuale">
                                                                <option value=""><spring:message code="anagrafica.impresaindividuale.default"/></option>
                                                                <option value="S" <c:if test="${anagrafica.flgIndividuale == 'S'}">selected="selected"</c:if> ><spring:message code="anagrafica.impresaindividuale.si"/></option>
                                                                <option value="N" <c:if test="${anagrafica.flgIndividuale == 'N'}">selected="selected"</c:if> ><spring:message code="anagrafica.impresaindividuale.no"/></option>
                                                                </select>
                                                            </div>
                                                        --%>
                                                        <div class="ctrlHolder">
                                                            <label for="partitaIva"><spring:message code="anagrafica.partitaiva"/></label>
                                                            <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].partitaIva" id="partitaIva" maxlength="255" type="text" class="textInput required ui-autocomplete-input " value="${anagrafica.partitaIva}" />
                                                        </div>

                                                        <div class="ctrlHolder">
                                                            <label for="codiceFiscale"><spring:message code="protocollo.dettaglio.anagrafica.codiceFiscale"/></label>
                                                            <input readonly="readonly"  name="anagraficaList[${count}].codiceFiscale" id="codiceFiscale" maxlength="255" type="text" class="textInput required ui-autocomplete-input " value="${anagrafica.codiceFiscale}" />
                                                        </div>

                                                        <%-- Iscrizione Registro Imprese --%>
                                                        <div class="ctrlHolder">
                                                            <label for="flgAttesaIscrizioneRi"><spring:message code="anagrafica.attesascrizioneri"/></label>
                                                            <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].flgAttesaIscrizioneRi" id="flgAttesaIscrizioneRi" type="checkbox" class="choice required" value="S" <c:if test="${anagrafica.flgAttesaIscrizioneRi == 'S'}">checked="checked"</c:if> />
                                                            </div>

                                                            <div class="ctrlHolder">
                                                                <label for="nIscrizioneRi"><spring:message code="anagrafica.numeroiscrizioneri"/></label>
                                                            <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].nIscrizioneRi" id="nIscrizioneRi" maxlength="255" type="text" class="textInput required" value="${anagrafica.nIscrizioneRi}" />
                                                        </div>

                                                        <div class="ctrlHolder">
                                                            <label for="dataIscrizioneRi"><spring:message code="anagrafica.dataiscrizioneri"/></label>
                                                            <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].dataIscrizioneRi" id="dataIscrizioneRi" maxlength="255" type="text" class="textInput required" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${anagrafica.dataIscrizioneRi}" />" />
                                                        </div>

                                                        <div class="ctrlHolder">
                                                            <label for="desProvinciaIscrizioneRi"><spring:message code="anagrafica.provinciacciaa"/></label>
                                                            <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].provinciaCciaa.idProvincie" class="hiddenValue" id="nProvinciaIscrizioneRi" type="hidden" value="${anagrafica.provinciaCciaa.idProvincie}" />
                                                            <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].provinciaCciaa.descrizione" id="desProvinciaIscrizioneRi" maxlength="255" type="text" class="textInput required" value="${anagrafica.provinciaCciaa.descrizione}" />
                                                        </div>

                                                        <%-- Iscrizione REA --%>
                                                        <div class="ctrlHolder">
                                                            <label for="flgAttesaIscrizioneRea"><spring:message code="anagrafica.attesascrizionerea"/></label>
                                                            <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].flgAttesaIscrizioneRea" id="flgAttesaIscrizioneRea" type="checkbox" class="choice required" value="S" <c:if test="${anagrafica.flgAttesaIscrizioneRea == 'S'}">checked="checked"</c:if> />
                                                            </div>

                                                            <div class="ctrlHolder">
                                                                <label for="nIscrizioneRea"><spring:message code="anagrafica.numeroiscrizionerea"/></label>
                                                            <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].nIscrizioneRea" id="nIscrizioneRea" maxlength="255" type="text" class="textInput required" value="${anagrafica.nIscrizioneRea}" />
                                                        </div>

                                                        <div class="ctrlHolder">
                                                            <label for="dataIscrizioneRea"><spring:message code="anagrafica.dataiscrizionerea"/></label>
                                                            <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].dataIscrizioneRea" id="dataIscrizioneRea" maxlength="255" type="text" class="textInput required" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${anagrafica.dataIscrizioneRea}" />" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                        <%-- Persona fisica --%>
                                        <c:if test="${anagrafica.tipoAnagrafica == 'F'}">
                                            <div class="personaFisica">
                                                <div class="separatore_bordo">
                                                    <legend id="legendPersonaFisica"><spring:message code="anagrafica.personafisica"/></legend>
                                                    <div>
                                                        <div class="ctrlHolder">
                                                            <label for="tipoRuolo"><spring:message code="protocollo.dettaglio.anagrafica.ruolo"/></label>
                                                            <select id="tipoRuolo" name="anagraficaList[${count}].idTipoRuolo">
                                                                <option  >Selezionare ruolo</option>
                                                                <c:forEach items="${ruoliAnagrafica}" var="ruolo" begin="0">
                                                                    <option value="${ruolo.idTipoRuolo}" <c:if test="${anagrafica.idTipoRuolo == ruolo.idTipoRuolo}">selected="selected"</c:if> >${ruolo.desTipoRuolo}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                        <div class="ctrlHolder">
                                                            <label for="tipoQualifica"><spring:message code="protocollo.dettaglio.anagrafica.qualifica"/></label>
                                                            <select id="tipoQualifica" name="anagraficaList[${count}].idTipoQualifica">
                                                                <option value=""><spring:message code="protocollo.dettaglio.anagrafica.qualifica.default"/></option>
                                                                <c:forEach items="${qualificaAnagrafica}" var="qualifica" begin="0">
                                                                    <option value="${qualifica.idTipoQualifica}" <c:if test="${anagrafica.idTipoQualifica == qualifica.idTipoQualifica}">selected="selected"</c:if> >${qualifica.desTipoQualifica}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                        <div class="ctrlHolder">
                                                            <label for="nome"><spring:message code="anagrafica.nome"/></label>
                                                            <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].nome" id="nome" maxlength="255" type="text" class="textInput required" value="${anagrafica.nome}" />                                
                                                        </div>
                                                        <div class="ctrlHolder">
                                                            <label for="cognome"><spring:message code="anagrafica.cognome"/></label>
                                                            <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].cognome" id="cognome" maxlength="255" type="text" class="textInput required" value="${anagrafica.cognome}" />
                                                        </div>

                                                        <div class="ctrlHolder">
                                                            <label for="sesso"><spring:message code="anagrafica.sesso"/></label>
                                                            <select  readonly="readonly" disabled="disabled" id="sesso" name="anagraficaList[${count}].sesso">
                                                                <option value=""><spring:message code="anagrafica.sesso.default"/></option>
                                                                <option value="M" <c:if test="${anagrafica.sesso == 'M'}">selected="selected"</c:if> ><spring:message code="anagrafica.sesso.maschio"/></option>
                                                                <option value="F" <c:if test="${anagrafica.sesso == 'F'}">selected="selected"</c:if> ><spring:message code="anagrafica.sesso.femmina"/></option>
                                                                </select>
                                                            </div>

                                                            <div class="ctrlHolder">
                                                                <label for="codiceFiscale"><spring:message code="anagrafica.codicefiscale"/></label>
                                                            <input  readonly="readonly" name="anagraficaList[${count}].codiceFiscale" id="codiceFiscale" maxlength="255" type="text" class="textInput required" value="${anagrafica.codiceFiscale}" />
                                                        </div>

                                                        <div class="ctrlHolder">
                                                            <label for="dataNascita"><spring:message code="anagrafica.datanascita"/></label>
                                                            <input  readonly="readonly" disabled="disabled" name="anagraficaList[${count}].dataNascita" id="dataNascita" maxlength="255" type="text" class="textInput required" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${anagrafica.dataNascita}" />" />
                                                        </div>

                                                        <div class="ctrlHolder">
                                                            <label for="desCittadinanza"><spring:message code="anagrafica.cittadinanza"/></label>
                                                            <input  readonly="readonly" disabled="disabled" name="anagraficaList[${count}].idCittadinanza" class="hiddenValue" id="idCittadinanza" type="hidden" value="${anagrafica.idCittadinanza}" />
                                                            <input  readonly="readonly" disabled="disabled" name="anagraficaList[${count}].desCittadinanza" id="desCittadinanza" maxlength="255" type="text" class="textInput required" value="${anagrafica.desCittadinanza}" />
                                                        </div>

                                                        <div class="ctrlHolder">
                                                            <label for="desComuneNascita"><spring:message code="anagrafica.comunenascita"/></label>
                                                            <input  readonly="readonly" disabled="disabled" name="anagraficaList[${count}].comuneNascita.idComune" class="hiddenValue" id="nComuneNascita" type="hidden" value="${anagrafica.comuneNascita.idComune}" />
                                                            <input  readonly="readonly" disabled="disabled" name="anagraficaList[${count}].comuneNascita.descrizione" id="desComuneNascita" maxlength="255" type="text" class="textInput required" value="${anagrafica.comuneNascita.descrizione}" />
                                                            <p class="formHint"></p>
                                                        </div>

                                                        <div class="ctrlHolder">
                                                            <label for="localitaNascita"><spring:message code="anagrafica.localitanascita"/></label>
                                                            <input  readonly="readonly" disabled="disabled" name="anagraficaList[${count}].localitaNascita" id="localitaNascita" maxlength="255" type="text" class="textInput required" value="${anagrafica.localitaNascita}" />
                                                        </div>

                                                        <%-- Anagrafica professionista --%>
                                                        <%-- NUOVA LOGICA VARIANTE ANAGRAFICA --%>
                                                        <c:if test="${anagrafica.varianteAnagrafica == 'P'}">
                                                            <div class="anagraficaProfessionista">

                                                                <div class="ctrlHolder">
                                                                    <label for="partitaIvaProfessionista"><spring:message code="anagrafica.partitaiva"/></label>
                                                                    <input  readonly="readonly" disabled="disabled" name="anagraficaList[${count}].partitaIva" id="partitaIvaProfessionista" maxlength="255" type="text" class="textInput required" value="${anagrafica.partitaIva}" />
                                                                </div>

                                                                <div class="ctrlHolder">
                                                                    <label for="desTipoCollegio"><spring:message code="anagrafica.tipocollegio"/></label>
                                                                    <input  readonly="readonly" disabled="disabled" name="anagraficaList[${count}].codTipoCollegio" class="hiddenValue" id="nTipoCollegio" type="hidden" value="${anagrafica.codTipoCollegio}" />
                                                                    <input  readonly="readonly" disabled="disabled" name="anagraficaList[${count}].desTipoCollegio" id="desTipoCollegio" maxlength="255" type="text" class="textInput required" value="${anagrafica.desTipoCollegio}" />
                                                                </div>

                                                                <div class="ctrlHolder">
                                                                    <label for="numeroIscrizione"><spring:message code="anagrafica.iscrizione"/></label>

                                                                    <input  readonly="readonly" disabled="disabled" name="anagraficaList[${count}].numeroIscrizione" id="numeroIscrizione" maxlength="255" type="text" class="textInput required" value="${anagrafica.numeroIscrizione}" />
                                                                </div>

                                                                <div class="ctrlHolder">
                                                                    <label for="dataIscrizione"><spring:message code="anagrafica.dataiscrizione"/></label>
                                                                    <input  readonly="readonly" disabled="disabled" name="anagraficaList[${count}].dataIscrizione" id="dataIscrizione" maxlength="255" type="text" class="textInput required" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${anagrafica.dataIscrizione}" />" />
                                                                </div>

                                                                <div class="ctrlHolder">
                                                                    <label for="desProvinciaIscrizione"><spring:message code="anagrafica.provinciaiscrizione"/></label>
                                                                    <input  readonly="readonly" disabled="disabled" name="anagraficaList[${count}].provinciaIscrizione.idProvincie" class="hiddenValue" id="nProvinciaIscrizione" type="hidden" value="${anagrafica.provinciaIscrizione.idProvincie}" />
                                                                    <input  readonly="readonly" disabled="disabled" name="anagraficaList[${count}].provinciaIscrizione.descrizione" id="desProvinciaIscrizione" maxlength="255" type="text" class="textInput required" value="${anagrafica.provinciaIscrizione.descrizione}" />
                                                                </div>
                                                            </div>
                                                        </c:if>
                                                        <c:if test="${anagrafica.varianteAnagrafica == 'I'}">
                                                            <div class="anagraficaDittaIndividuale">

                                                                <div class="ctrlHolder">
                                                                    <label for="partitaIvaProfessionista"><spring:message code="anagrafica.partitaiva"/></label>
                                                                    <input  readonly="readonly" disabled="disabled" name="anagraficaList[${count}].partitaIva" id="partitaIvaProfessionista" maxlength="255" type="text" class="textInput required" value="${anagrafica.partitaIva}" />
                                                                </div>
                                                                <div class="ctrlHolder">
                                                                    <label for="denominazione"><spring:message code="anagrafica.denominazione"/></label>
                                                                    <input readonly="readonly" disabled="disabled" name="anagraficaList[${count}].denominazione" id="denominazione" maxlength="255" type="text" class="textInput required" value="${anagrafica.denominazione}" />
                                                                </div>
                                                            </div>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Non esiste in DB l'anagrafica, uso quella del protocollo -->
                                        <div class="personaFisica">
                                            <div class="separatore_bordo">
                                                <legend id="legendPersonaFisica"><spring:message code="protocollo.dettaglio.anagrafica"/></legend>
                                                <div>
                                                    <div class="ctrlHolder" id="seltipoAnagrafica${count}">
                                                        <input  name="anagraficaList[${count}].manuale" type="hidden" value="true" />
                                                        <label for="tipoAnagrafica"><spring:message code="protocollo.dettaglio.anagrafica.tipologia"/>*</label>
                                                        <select class="seltipoAnagrafica textInput required">
                                                            <%-- ^^CS AGGIUNTA --%>
                                                            <option  ><spring:message code="protocollo.dettaglio.selezionaTipoAnagarafica"/></option>
                                                            <option value="F" <c:if test="${anagrafica.tipoAnagrafica == 'F' && anagrafica.varianteAnagrafica == 'F'}">selected="selected"</c:if> ><spring:message code="protocollo.dettaglio.anagrafica.tipologia.fisica"/></option>
                                                            <option value="G" <c:if test="${anagrafica.tipoAnagrafica == 'G'}">selected="selected"</c:if> ><spring:message code="protocollo.dettaglio.anagrafica.tipologia.giuridica"/></option>
                                                            <%--<option value="P" <c:if test="${anagrafica.tipoAnagrafica == 'F' && anagrafica.varianteAnagrafica == 'P'}">selected="selected"</c:if> ><spring:message code="protocollo.dettaglio.anagrafica.tipologia.professionista"/></option>--%>
                                                            <option value="I" <c:if test="${anagrafica.tipoAnagrafica == 'F' && anagrafica.varianteAnagrafica == 'I'}">selected="selected"</c:if> >Ditta Individuale</option>
                                                            </select>
                                                            <input type="hidden" name="anagraficaList[${count}].tipoAnagrafica" class="tipoAnagrafica"  value="${anagrafica.tipoAnagrafica}"/>
                                                        <input type="hidden" name="anagraficaList[${count}].varianteAnagrafica" class="varianteAnagrafica"  value="${anagrafica.varianteAnagrafica}"/>
                                                    </div>
                                                    <div class="ctrlHolder">
                                                        <label for="tipoRuolo"><spring:message code="protocollo.dettaglio.anagrafica.ruolo"/>*</label>
                                                        <select id="tipoRuolo" name="anagraficaList[${count}].idTipoRuolo" class="textInput required">
                                                            <option   ><spring:message code="protocollo.dettaglio.anagrafica.selezionaRuoloAnagarafica"/></option>
                                                            <c:forEach items="${ruoliAnagrafica}" var="ruolo" begin="0">
                                                                <option value="${ruolo.idTipoRuolo}" <c:if test="${anagrafica.idTipoRuolo == ruolo.idTipoRuolo}">selected="selected"</c:if> >${ruolo.desTipoRuolo}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="ctrlHolder">
                                                        <label for="tipoQualifica"><spring:message code="protocollo.dettaglio.anagrafica.qualifica"/></label>
                                                        <select id="tipoQualifica" name="anagraficaList[${count}].idTipoQualifica" class="textInput required">
                                                            <option value=""><spring:message code="protocollo.dettaglio.anagrafica.qualifica.default"/></option>
                                                            <c:forEach items="${qualificaAnagrafica}" var="qualifica" begin="0">
                                                                <option value="${qualifica.idTipoQualifica}" <c:if test="${anagrafica.idTipoQualifica == qualifica.idTipoQualifica}">selected="selected"</c:if> >${qualifica.desTipoQualifica}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="ctrlHolder scelta G I">
                                                        <label for="denominazione_${count}"><spring:message code="protocollo.dettaglio.anagrafica.denominazione"/></label>
                                                        <input name="anagraficaList[${count}].denominazione" id="denominazione_${count}" maxlength="255" type="text" class="textInput required" value="${anagrafica.denominazione}" />
                                                        <p class="formHint"></p>
                                                    </div>
                                                    <div class="ctrlHolder scelta F P I">
                                                        <label for="cognome_${count}"><spring:message code="protocollo.dettaglio.anagrafica.cognome"/></label>
                                                        <input name="anagraficaList[${count}].cognome" id="cognome_${count}" maxlength="255" type="text" class="textInput required" value="${anagrafica.cognome}" />
                                                        <p class="formHint"></p>
                                                    </div>
                                                    <div class="ctrlHolder scelta F P I">
                                                        <label for="nome_${count}"><spring:message code="protocollo.dettaglio.anagrafica.nome"/></label>
                                                        <input name="anagraficaList[${count}].nome" id="nome_${count}" maxlength="255" type="text" class="textInput required" value="${anagrafica.nome}" />
                                                        <p class="formHint"></p>
                                                    </div>
                                                    <div class="ctrlHolder">
                                                        <label for="codiceFiscale_${count}"><spring:message code="protocollo.dettaglio.anagrafica.codiceFiscale"/>*</label>
                                                        <input name="anagraficaList[${count}].codiceFiscale" id="codiceFiscale_${count}" maxlength="255" type="text" class="textInput required" value="${anagrafica.codiceFiscale}" />
                                                        <p class="formHint"></p>
                                                    </div>
                                                    <div class="ctrlHolder scelta G P I">
                                                        <label for="partitaIva_${count}"><spring:message code="protocollo.dettaglio.anagrafica.partitaIva"/></label>
                                                        <input name="anagraficaList[${count}].partitaIva" id="partitaIva_${count}" maxlength="255" type="text" class="textInput required" value="${anagrafica.partitaIva}" />
                                                        <p class="formHint"></p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${!empty anagrafica.recapiti}">
                                    <%-- ^^CS AGGIUNTA --%>
                                    <c:set var="blinda" value="" />
                                    <c:if test="${anagrafica.idAnagrafica!=null && anagrafica.idAnagrafica!=''}">
                                        <c:set var="blinda" value=" readonly='readonly' disabled='disabled' " />
                                    </c:if>

                                    <%-- <div id="anagrafica_detail_${anagrafica.idAnagrafica}"> --%>
                                    <c:set var="countRecapiti" value="0" scope="page" />
                                    <c:forEach items="${anagrafica.recapiti}" var="recapito" begin="0">
                                        <%-- <div class="ctrlHolder dettaglioAnagraficaContainer"> --%>
                                        <div class="separatore_bordo">
                                            <legend><spring:message code="protocollo.dettaglio.anagrafica.recapito.title"/></legend>
                                            <div>
                                                <input type="hidden" class="idRecapito" name="anagraficaList[${count}].recapiti[${countRecapiti}].idRecapito" value="${recapito.idRecapito}" />
                                                <div id="tipoIndirizzo${count}${countRecapiti}" class="ctrlHolder">
                                                    <label for="tipoIndirizzo"><spring:message code="protocollo.dettaglio.anagrafica.tiporecapito"/>*</label>

                                                    <select ${blinda} disabled="disabled"class="tipoIndirizzo textInput required" id="tipoIndirizzo">
                                                        <option value=""><spring:message code="protocollo.dettaglio.selezionaTipoRecapito"/></option>
                                                        <c:forEach items="${tipoIndirizzo}" var="indirizzo" begin="0">
                                                            <%-- ^^CS AGGIUNTA--%>
                                                            <c:if test="${indirizzo.idTipoIndirizzo==recapito.idTipoIndirizzo}">
                                                                <c:set var="selected" value="selected=\"selected\""/>
                                                            </c:if>
                                                            <c:if test="${indirizzo.idTipoIndirizzo!=recapito.idTipoIndirizzo}">
                                                                <c:set var="selected" value=""/>
                                                            </c:if>
                                                            <option ${selected} value="${indirizzo.idTipoIndirizzo}" <c:if test="${indirizzo.idTipoIndirizzo == recapito.idTipoIndirizzo}">selected="selected"</c:if> >${indirizzo.descTipoIndirizzo}</option>
                                                        </c:forEach> 
                                                    </select>
                                                    <%-- ^^CS AGGIUNTA--%>
                                                    <input type="hidden" class="idTipoIndirizzo" name="anagraficaList[${count}].recapiti[${countRecapiti}].idTipoIndirizzo" value="${recapito.idTipoIndirizzo}" />
                                                    <input type="hidden" class="descTipoIndirizzo" name="anagraficaList[${count}].recapiti[${countRecapiti}].descTipoIndirizzo" value="${recapito.descTipoIndirizzo}" />
                                                </div>
                                                <div id="recapito${count}${countRecapiti}" class="ctrlHolder">
                                                    <label class="detailKey" for="ComuneRecapito"><spring:message code="anagrafica.recapito.comune"/>*</label>
                                                    <input ${blinda} name="anagraficaList[${count}].recapiti[${countRecapiti}].idComune" class="nComuneRecapito hiddenValue" type="hidden" value="${recapito.idComune}"/>
                                                    <input ${blinda} maxlength="255" type="text" class="ComuneRecapito textInput required" value="${recapito.descComune}"/>
                                                    <input ${blinda} name="anagraficaList[${count}].recapiti[${countRecapiti}].descComune" maxlength="255" type="hidden" class="desComuneRecapito textInput required detailValue" value="${recapito.descComune}"/>
                                                    <p class="formHint"></p>
                                                </div>
                                                <div class="ctrlHolder">
                                                    <label class="detailKey" for="localitaRecapito"><spring:message code="anagrafica.recapito.localita"/></label>
                                                    <input ${blinda} name="anagraficaList[${count}].recapiti[${countRecapiti}].localita" id="localitaRecapito" maxlength="255" type="text" class="textInput required detailValue" value="${recapito.localita}"/>
                                                </div>
                                                <div class="ctrlHolder">
                                                    <label class="detailKey" for="indirizzoRecapito"><spring:message code="anagrafica.recapito.indirizzo"/>*</label>
                                                    <input ${blinda} name="anagraficaList[${count}].recapiti[${countRecapiti}].indirizzo" id="indirizzoRecapito" maxlength="255" type="text" class="textInput required detailValue" value="${recapito.indirizzo}"/>
                                                </div>
                                                <div class="ctrlHolder">
                                                    <label class="detailKey" for="civicoRecapito"><spring:message code="anagrafica.recapito.civico"/>*</label>
                                                    <input ${blinda} name="anagraficaList[${count}].recapiti[${countRecapiti}].nCivico" id="civicoRecapito" maxlength="45" type="text" class="textInput required detailValue" value="${recapito.nCivico}"/>
                                                </div>
                                                <div class="ctrlHolder">
                                                    <label class="detailKey" for="capRecapito"><spring:message code="anagrafica.recapito.cap"/>*</label>
                                                    <input ${blinda} name="anagraficaList[${count}].recapiti[${countRecapiti}].cap" id="capRecapito" maxlength="20" type="text" class="textInput required detailValue" value="${recapito.cap}"/>
                                                </div>
                                                <div class="ctrlHolder">
                                                    <label class="detailKey" for="casellaPostaleRecapito"><spring:message code="anagrafica.recapito.casellapostale"/></label>
                                                    <input ${blinda} name="anagraficaList[${count}].recapiti[${countRecapiti}].casellaPostale" id="casellaPostaleRecapito" maxlength="255" type="text" class="textInput required detailValue" value="${recapito.casellaPostale}"/>
                                                </div>
                                                <div class="ctrlHolder">
                                                    <label class="detailKey" for="telefonoRecapito"><spring:message code="anagrafica.recapito.telefono"/></label>
                                                    <input ${blinda} name="anagraficaList[${count}].recapiti[${countRecapiti}].telefono" id="telefonoRecapito" maxlength="30" type="text" class="textInput required detailValue" value="${recapito.telefono}"/>
                                                </div>
                                                <div class="ctrlHolder">
                                                    <label class="detailKey" for="cellulareRecapito"><spring:message code="anagrafica.recapito.cellulare"/></label>
                                                    <input ${blinda}  name="anagraficaList[${count}].recapiti[${countRecapiti}].cellulare" id="cellulareRecapito" maxlength="30" type="text" class="textInput required detailValue" value="${recapito.cellulare}"/>
                                                </div>
                                                <div class="ctrlHolder">
                                                    <label class="detailKey" for="faxRecapito"><spring:message code="anagrafica.recapito.fax"/></label>
                                                    <input ${blinda} name="anagraficaList[${count}].recapiti[${countRecapiti}].fax" id="faxRecapito" maxlength="255" type="text" class="textInput required detailValue" value="${recapito.fax}"/>
                                                </div>
                                                <div class="ctrlHolder">
                                                    <label class="detailKey" for="emailRecapito"><spring:message code="anagrafica.recapito.email"/>*(se pec non presente)</label>
                                                    <input ${blinda} name="anagraficaList[${count}].recapiti[${countRecapiti}].email" id="emailRecapito" maxlength="255" type="text" class="textInput required detailValue" value="${recapito.email}"/>
                                                </div>
                                                <div class="ctrlHolder">
                                                    <label class="detailKey" for="pecRecapito"><spring:message code="anagrafica.recapito.pec"/>*</label>
                                                    <input ${blinda} name="anagraficaList[${count}].recapiti[${countRecapiti}].pec" id="pecRecapito" maxlength="255" type="text" class="textInput required detailValue" value="${recapito.pec}"/>
                                                </div>
                                            </div>
                                            <c:set var="countRecapiti" value="${countRecapiti + 1}" scope="page"/>
                                        </div>
                                        <%-- </div> --%>
                                    </c:forEach>
                                    <%-- </div> --%>
                                </c:if>
                                <c:set var="count" value="${count + 1}" scope="page"/>
                            </div>

                        </c:forEach>

                    </div>
                    <div class="frame" id="frame2">

                        <!-- Notifica -->
                        <div id="dettaglio_recapito_notifica" class="ctrlHolder dettaglioAnagraficaContainer">
                            <div class="separatore_bordo">
                                <legend><spring:message code="protocollo.dettaglio.notifica.title"/></legend>
                                <div class="ctrlHolder">
                                    <label class="detailKey" for="presso"><spring:message code="protocollo.dettaglio.notifica.presso"/></label>
                                    <input name="notifica.presso" id="presso" maxlength="255" type="text" class="textInput required detailValue" value="${pratica.notifica.presso}" />
                                    <p class="formHint"></p>
                                </div>
                                <div id="praticaNotifica" class="ctrlHolder">
                                    <input name="notifica.descTipoIndirizzo" maxlength="255" type="hidden"value="NOTIFICA" />
                                    <label class="detailKey" for="desComune"><spring:message code="protocollo.dettaglio.notifica.comune"/></label>
                                    <input name="notifica.idComune" class="nComuneRecapito hiddenValue"  type="hidden" value="${pratica.notifica.idComune}" />
                                    <input  id="desComune" maxlength="255" type="text" class="ComuneRecapito textInput required detailValue" value="${pratica.notifica.descComune}" />
                                    <input name="notifica.descComune" maxlength="255" type="hidden" class="desComuneRecapito textInput required detailValue" value="${pratica.notifica.descComune}" />
                                    <p class="formHint"></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="detailKey" for="localita"><spring:message code="protocollo.dettaglio.notifica.localita"/></label>
                                    <input name="notifica.localita" id="localita" maxlength="255" type="text" class="textInput required detailValue" value="${pratica.notifica.localita}" />
                                    <p class="formHint"></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="detailKey" for="indirizzo"><spring:message code="protocollo.dettaglio.notifica.indirizzo"/></label>
                                    <input name="notifica.indirizzo" id="indirizzo" maxlength="255" type="text" class="textInput required detailValue" value="${pratica.notifica.indirizzo}" />
                                    <p class="formHint"></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="detailKey" for="nCivico"><spring:message code="protocollo.dettaglio.notifica.civico"/></label>
                                    <input name="notifica.nCivico" id="nCivico" maxlength="255" type="text" class="textInput required detailValue" value="${pratica.notifica.nCivico}" />
                                    <p class="formHint"></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="detailKey" for="cap"><spring:message code="protocollo.dettaglio.notifica.cap"/></label>
                                    <input name="notifica.cap" id="cap" maxlength="255" type="text" class="textInput required detailValue" value="${pratica.notifica.cap}" />
                                    <p class="formHint"></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="detailKey" for="casellaPostale"><spring:message code="protocollo.dettaglio.notifica.casellapostale"/></label>
                                    <input name="notifica.casellaPostale" id="casellaPostale" maxlength="255" type="text" class="textInput required detailValue" value="${pratica.notifica.casellaPostale}" />
                                    <p class="formHint"></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="detailKey" for="altreInfoIndirizzo"><spring:message code="protocollo.dettaglio.notifica.altreinfoindirizzo"/></label>
                                    <input name="notifica.altreInfoIndirizzo" id="altreInfoIndirizzo" maxlength="255" type="text" class="textInput required detailValue" value="${pratica.notifica.altreInfoIndirizzo}" />
                                    <p class="formHint"></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="detailKey" for="telefono"><spring:message code="protocollo.dettaglio.notifica.telefono"/></label>
                                    <input name="notifica.telefono" id="telefono" maxlength="255" type="text" class="textInput required detailValue" value="${pratica.notifica.telefono}" />

                                    <p class="formHint"></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="detailKey" for="cellulare"><spring:message code="protocollo.dettaglio.notifica.cellulare"/></label>
                                    <input name="notifica.cellulare" id="cellulare" maxlength="255" type="text" class="textInput required detailValue" value="${pratica.notifica.cellulare}" />
                                    <p class="formHint"></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="detailKey" for="fax"><spring:message code="protocollo.dettaglio.notifica.fax"/></label>
                                    <input name="notifica.fax" id="fax" maxlength="255" type="text" class="textInput required detailValue" value="${pratica.notifica.fax}" />
                                    <p class="formHint"></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="detailKey" for="email"><spring:message code="protocollo.dettaglio.notifica.email"/>*(se pec non presente)</label>
                                    <input name="notifica.email" id="email" maxlength="255" type="text" class="textInput required detailValue" value="${pratica.notifica.email}" />
                                    <p class="formHint"></p>
                                </div>
                                <div class="ctrlHolder">
                                    <label class="detailKey" for="pec"><spring:message code="protocollo.dettaglio.notifica.pec"/>*</label>
                                    <input name="notifica.pec" id="pec" maxlength="255" type="text" class="textInput required detailValue" value="${pratica.notifica.pec}" />
                                    <p class="formHint"></p>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>    
            </div>                


            <div class="inlineLabels">


                <div class="buttonHolder">
                    <a href="<%= path%>/pratiche/caricamento/seleziona.htm" class="secondaryAction">&larr; <spring:message code="protocollo.dettaglio.button.indietro"/></a>

                    <button type="submit" name="submit" class="primaryAction" value="<spring:message code="protocollo.dettaglio.button.conferma"/>"><spring:message code="protocollo.dettaglio.button.conferma"/></button>
                    <%-- <input class="primaryAction" type="submit" name="submit" value="<spring:message code="protocollo.dettaglio.button.conferma"/>"/>--%>
                </div>
            </div>
        </form>

    </div>
</div>