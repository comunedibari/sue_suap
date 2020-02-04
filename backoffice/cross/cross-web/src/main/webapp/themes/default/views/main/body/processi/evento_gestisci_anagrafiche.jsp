<%
    String path = request.getContextPath();
    String url = path + "/processi/eventi/aggiungi/salvaEvento.htm";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
    var urlCercaAnagrafica = "${path}/processi/eventi/cercaAnagraficaAjax.htm";
    var urlCercaEnte = "${path}/processi/eventi/cercaEnteAjax.htm";
    function cancellaAnagrafica(id, tags) {
        var div = $("<div>");
        div.html("<spring:message code="evento.anagraficheEnti.conferma.cancella.anagrafiche"/>")
        $(div).dialog(
                {
                    modal: true,
                    title: "<spring:message code="evento.anagraficheEnti.conferma.cancella.title"/>",
                    buttons: {
                        Ok: function () {
                            $("#li_" + tags + "_" + id).remove();
                            $(div).dialog('close');
                            $('.list' + tags).children('.idAnagrafica').each(function (index, value) {
                                $(this).attr('name', tags + '[' + index + '].idAnagrafica');
                            });
                            $('.list' + tags).children('.idEvento').each(function (index, value) {
                                $(this).attr('name', tags + '[' + index + '].idEvento');
                            });
                        },
                        Annulla: function () {
                            $(this).dialog('close');
                        }
                    }
                });
    }

    function cancellaEnte(id, tags) {
        var div = $("<div>");
        div.html("<spring:message code="evento.anagraficheEnti.conferma.cancella.enti"/>")
        $(div).dialog(
                {
                    modal: true,
                    title: "<spring:message code="evento.anagraficheEnti.conferma.cancella.title"/>",
                    buttons: {
                        Ok: function () {
                            $("#li_" + tags + "_" + id).remove();
                            $(div).dialog('close');
                            $('.list' + tags).children('.idEnte').each(function (index, value) {
                                $(this).attr('name', tags + '[' + index + '].idEnte');
                            });
                            $('.list' + tags).children('.idEvento').each(function (index, value) {
                                $(this).attr('name', tags + '[' + index + '].idEvento');
                            });
                        },
                        Annulla: function () {
                            $(this).dialog('close');
                        }
                    }
                });
    }
    function aggiungiAnagrafica(id, tags) {
        var wHeight = $(window).height() * 0.8;
        $('#windowAnagrafica').dialog({
            title: '<spring:message code="evento.anagraficheEnti.windowAnagrafiche.title"/>', modal: true,
            height: wHeight,
            width: '50%',
            close: function () {
                $("#windowAnagrafica input[name='stringaRicercaAnagrafica']").val("");
            },
            buttons: {
                'Cerca': function () {
                    if ($("#stringaRicercaAnagrafica").val().trim() === "") {
                        alert("Inserire una stringa di ricerca");
                    } else {
                        cercaAnagrafica(id, tags);
                    }
                }, 'Annulla': function () {
                    $('#anagraficaTable').remove();
                    $("#stringaRicercaAnagrafica").val("");
                    $(this).dialog("close");
                }
            }
        });
    }
    function aggiungiEnte(id, tags) {
        var wHeight = $(window).height() * 0.8;
        $('#windowEnte').dialog({
            title: '<spring:message code="evento.anagraficheEnti.windowEnte.title"/>',
            modal: true,
            height: wHeight,
            width: '50%',
            close: function () {
                $("#windowEnte input[name='stringaRicercaAnagrafica']").val("");
            },
            buttons: {
                'Cerca': function () {
                    if ($("#stringaRicercaEnte").val().trim() === "") {
                        alert("Inserire una stringa di ricerca");
                    } else {
                        cercaEnte(id, tags);
                    }
                },
                'Annulla': function () {
                    $('#enteTable').remove();
                    $("#stringaRicercaEnte").val("");
                    $(this).dialog("close");
                }
            }
        });
    }
    function selezionaAnagrafica(button, idEvento, tags) {
		var td = $(button).parent();
		var tds = $(td).siblings();
		var cf = $(tds)[0].textContent;
		var cognome = $(tds)[1].textContent;
		var nome = $(tds)[2].textContent;
		var denominazione = $(tds)[3].textContent
		var id =  $(button).siblings('input[name="idAnagrafica"]').val();
        if ($("#li_" + tags + "_" + id).length === 0) {
            var text = $("<span/>", {
                html: '&nbsp;' + cf + '&nbsp;' + cognome + '&nbsp;' + nome + '&nbsp;' + denominazione
            });
            var count = $("#ul_" + tags + " li").length;
            var messageCenterAnchor = $('<a/>', {
                href: '#',
                class: 'icona_cancella',
                onclick: "cancellaAnagrafica(" + id + ",'" + tags + "')"
            });
            var newListItem = $('<li/>', {
                html: messageCenterAnchor,
                class: "list" + tags,
                "id": "li_" + tags + "_" + id
            });
            $("#ul_" + tags).append(newListItem);
            text.appendTo('#li_' + tags + '_' + id);
            $('<input/>').attr({type: 'hidden', class: "idAnagrafica", id: tags + '_' + count + "_idAnagrafica", name: tags + '[' + count + '].idAnagrafica', value: id}).appendTo('#li_' + tags + '_' + id);
            $('<input/>').attr({type: 'hidden', class: "idEvento", id: tags + '_' + count + "_idEvento", name: tags + '[' + count + '].idEvento', value: idEvento}).appendTo('#li_' + tags + '_' + id);
            $('<input/>').attr({type: 'hidden', class: "codiceFiscale", id: tags + '_' + count + "_codiceFiscale", name: tags + '[' + count + '].codiceFiscale', value: cf}).appendTo('#li_' + tags + '_' + id);
            $('<input/>').attr({type: 'hidden', class: "cognome", id: tags + '_' + count + "_cognome", name: tags + '[' + count + '].cognome', value: cognome}).appendTo('#li_' + tags + '_' + id);
            $('<input/>').attr({type: 'hidden', class: "nome", id: tags + '_' + count + "_nome", name: tags + '[' + count + '].nome', value: nome}).appendTo('#li_' + tags + '_' + id);
            $('<input/>').attr({type: 'hidden', class: "denominazione", id: tags + '_' + count + "_denominazione", name: tags + '[' + count + '].denominazione', value: denominazione}).appendTo('#li_' + tags + '_' + id);
            $('#anagraficaTable').remove();
            $("#stringaRicercaAnagrafica").val("");
            $('#windowAnagrafica').dialog("close");
        } else {
            alert("esiste già l'anagrafica");
        }
    }

    function cercaAnagrafica(id, tags) {
        var wHeight = $(window).height() * 0.8;
        var descrizione = $("#stringaRicercaAnagrafica").val();
        $.post(urlCercaAnagrafica, {descrizione: descrizione,
            action: "ricercaAnagrafica"
        }, function (result) {
            var data = result;
            if (data.error !== null && data.error !== undefined) {
                alert(data.error);
            } else {
                if (data.rows != null) {
                    $('#anagraficaTable').remove();
                    var ul = $('#windowAnagrafica');
                    var table = "";
                    table = table + "<table cellspacing='0' cellpadding='0' class='master anagraficaSearchResult' id='anagraficaTable'>" +
                            "<tbody>" +
                            "<tr>" +
                            "<th><spring:message code="evento.anagraficheEnti.grid.codiceFiscale.title"/></th>" +
                            "<th><spring:message code="evento.anagraficheEnti.grid.cognome.title"/></th>" +
                            "<th><spring:message code="evento.anagraficheEnti.grid.nome.title"/></th> " +
                            "<th><spring:message code="evento.anagraficheEnti.grid.denominazione.title"/></th>" +
                            "<th></th>" +
                            "</tr>";
                }
                for (var i = 0; i < data.total; i++) {
                    //var button = "<button onclick='selezionaAnagrafica(" + data.rows[i].idAnagrafica + ",\"" + e(data.rows[i].codiceFiscale) + "\",\"" + e(data.rows[i].cognome) + "\",\"" + e(data.rows[i].nome) + "\",\"" + e(data.rows[i].denominazione) + "\",\"" + id + "\",\"" + tags + "\")' class='selezionaAnagrafica0 ui - button ui - widget ui - state - default ui - corner - all' role='button' aria-disabled='false'>" +
                    var button = "<button onclick='selezionaAnagrafica(this,\"" + id + "\",\"" + tags + "\")' class='selezionaAnagrafica0 ui - button ui - widget ui - state - default ui - corner - all' role='button' aria-disabled='false'>" +
                            "<span class='ui-button-text'>Seleziona</span>" +
                            "</button>";
                    var tRow = "";

                    tRow = tRow + "<tr id='row_" + i + "'>" +
                            "<td>" + e(data.rows[i].codiceFiscale) + "</td>" +
                            "<td>" + e(data.rows[i].cognome) + "</td>" +
                            "<td>" + e(data.rows[i].nome) + "</td>" +
                            "<td>" + e(data.rows[i].denominazione) + "</td>" +
                            "<td><input type='hidden' name='idAnagrafica' id='row_idAnagrafica_" + i + "' value='" + data.rows[i].idAnagrafica + "' />" + button + "</td>" +
                            "</tr>";

                    table = table + tRow;
                }
                table = table + "</tbody></table>";
                ul.append(table);
            }
        });

    }
    function selezionaEnte(button, idEvento, tags) {
		var td = $(button).parent();
		var tds = $(td).siblings();
		var cf = $(tds)[0].textContent;
		var descrizione = $(tds)[1].textContent;
		var id =  $(button).siblings('input[name="idEnte"]').val();	
        if ($("#li_" + tags + "_" + id).length === 0) {
            var text = $("<span/>", {
                html: '&nbsp;' + cf + '&nbsp;' + descrizione
            });
            var count = $("#ul_" + tags + " li").length;
            var messageCenterAnchor = $('<a/>', {
                href: '#',
                class: 'icona_cancella',
                onclick: "cancellaEnte(" + id + ",'" + tags + "')"
            });
            var newListItem = $('<li/>', {
                html: messageCenterAnchor,
                class: "list" + tags,
                "id": "li_" + tags + "_" + id
            });
            $("#ul_" + tags).append(newListItem);
            text.appendTo('#li_' + tags + '_' + id);
            $('<input/>').attr({type: 'hidden', class: "idEnte", id: tags + '_' + count + "_idEnte", name: tags + '[' + count + '].idEnte', value: id}).appendTo('#li_' + tags + '_' + id);
            $('<input/>').attr({type: 'hidden', class: "idEvento", id: tags + '_' + count + "_idEvento", name: tags + '[' + count + '].idEvento', value: idEvento}).appendTo('#li_' + tags + '_' + id);
            $('<input/>').attr({type: 'hidden', class: "codiceFiscale", id: tags + '_' + count + "_codiceFiscale", name: tags + '[' + count + '].codiceFiscale', value: cf}).appendTo('#li_' + tags + '_' + id);
            $('<input/>').attr({type: 'hidden', class: "descrizione", id: tags + '_' + count + "_descrizione", name: tags + '[' + count + '].descrizione', value: descrizione}).appendTo('#li_' + tags + '_' + id);
            $('#enteTable').remove();
            $("#stringaRicercaEnte").val("");
            $('#windowEnte').dialog("close");
        } else {
            alert("esiste già l'anagrafica");
        }
    }
    function cercaEnte(id, tags) {
        var wHeight = $(window).height() * 0.8;
        var descrizione = $("#stringaRicercaEnte").val();
        $.post(urlCercaEnte, {descrizione: descrizione,
            action: "ricercaEnte"
        }, function (result) {
            var data = result;
            if (data.error !== null && data.error !== undefined) {
                alert(data.error);
            } else {
                if (data.rows != null) {
                    $('#enteTable').remove();
                    var ul = $('#windowEnte');
                    var table = "";
                    table = table + "<table cellspacing='0' cellpadding='0' class='master enteSearchResult' id='enteTable'>" +
                            "<tbody>" +
                            "<tr>" +
                            "<th><spring:message code="evento.anagraficheEnti.grid.codiceFiscale.title"/></th>" +
                            "<th><spring:message code="evento.anagraficheEnti.grid.descrizione.title"/></th>" +
                            "<th></th>" +
                            "</tr>";
                }
                for (var i = 0; i < data.total; i++) {
                    //var button = "<button onclick='selezionaEnte(" + data.rows[i].idEnte + ",\"" + e(data.rows[i].codiceFiscale) + "\",\"" + e(data.rows[i].descrizione) + "\",\"" + id + "\",\"" + tags + "\")' class='selezionaAnagrafica0 ui - button ui - widget ui - state - default ui - corner - all' role='button' aria-disabled='false'>" +
					var button = "<button onclick='selezionaEnte(this,\"" + id + "\",\"" + tags + "\")' class='selezionaAnagrafica0 ui - button ui - widget ui - state - default ui - corner - all' role='button' aria-disabled='false'>" +
                            "<span class='ui-button-text'>Seleziona</span>" +
                            "</button>";
                    var tRow = "";

                    tRow = tRow + "<tr id='row_" + i + "'>" +
                            "<td>" + e(data.rows[i].codiceFiscale) + "</td>" +
                            "<td>" + e(data.rows[i].descrizione) + "</td>" +
                            "<td><input type='hidden' name='idEnte' id='row_idEnte_" + i + "' value='" + data.rows[i].idEnte + "' />" + button + "</td>" +
                            "</tr>";

                    table = table + tRow;
                }
                table = table + "</tbody></table>";
                ul.append(table);
            }
        });

    }
    function e(val) {
        if (val && val !== 'undefined') {
            return val;
        }
        return '';
    }
	
	String.prototype.addSlashes = function() { 
   //no need to do (str+'') anymore because 'this' can only be a string
          return this.replace(/'/g, '&apos;');
    }
</script>
<div class="inlineLabels">

    <div class="ctrlHolder">
        <p><span class="required"><spring:message code="evento.anagraficheEnti.anagraficheDestinatari"/></span>&nbsp;<a class="icona_aggiungi" href="#" onclick="aggiungiAnagrafica('${evento.idEvento}', 'anagraficaDestinatari')"></a></p>
        <ul id="ul_anagraficaDestinatari">
            <c:forEach items="${evento.anagraficaDestinatari}" var="anagraficaDestinatario" begin="0" varStatus="indice">
                <li id="li_anagraficaDestinatari_${anagraficaDestinatario.idAnagrafica}" class="listanagraficaDestinatari">
                    <input class="idAnagrafica" type="hidden" id="anagraficaDestinatari_${anagraficaDestinatario.idAnagrafica}_idAnagrafica"  name="anagraficaDestinatari[${indice.index}].idAnagrafica" value="${anagraficaDestinatario.idAnagrafica}">
                    <input class="idEvento" type="hidden" id="anagraficaDestinatari_${anagraficaDestinatario.idAnagrafica}_idEvento"  name="anagraficaDestinatari[${indice.index}].idEvento" value="${anagraficaDestinatario.idEvento}">
                    <input class="codiceFiscale" type="hidden" id="anagraficaDestinatari_${anagraficaDestinatario.idAnagrafica}_codiceFiscale"  name="anagraficaDestinatari[${indice.index}].codiceFiscale" value="${anagraficaDestinatario.codiceFiscale}">
                    <input class="cognome" type="hidden" id="anagraficaDestinatari_${anagraficaDestinatario.idAnagrafica}_cognome"  name="anagraficaDestinatari[${indice.index}].cognome" value="${anagraficaDestinatario.cognome}">
                    <input class="nome" type="hidden" id="anagraficaDestinatari_${anagraficaDestinatario.idAnagrafica}_nome"  name="anagraficaDestinatari[${indice.index}].nome" value="${anagraficaDestinatario.nome}">
                    <input class="denominazione" type="hidden" id="anagraficaDestinatari_${anagraficaDestinatario.idAnagrafica}_denominazione"  name="anagraficaDestinatari[${indice.index}].denominazione" value="${anagraficaDestinatario.denominazione}">
                    <a class="icona_cancella" href="#" onclick="cancellaAnagrafica('${anagraficaDestinatario.idAnagrafica}', 'anagraficaDestinatari')"></a>&nbsp;${anagraficaDestinatario.codiceFiscale}&nbsp; ${anagraficaDestinatario.cognome}&nbsp; ${anagraficaDestinatario.nome}&nbsp; ${anagraficaDestinatario.denominazione}
                </li>
            </c:forEach>
        </ul>
        <p class="formHint">&nbsp;</p>
    </div>
    <div class="ctrlHolder">
        <p><span class="required"><spring:message code="evento.anagraficheEnti.entiDestinatari"/></span>&nbsp;<a class="icona_aggiungi" href="#" onclick="aggiungiEnte('${evento.idEvento}', 'enteDestinatari')"></a></p>
        <ul id="ul_enteDestinatari">
            <c:forEach items="${evento.enteDestinatari}" var="enteDestinatario" begin="0" varStatus="indice">
                <li id="li_enteDestinatari_${enteDestinatario.idEnte}" class="listenteDestinatari">
                    <input class="idEnte" type="hidden" id="enteDestinatari_${enteDestinatario.idEnte}_idEnte"  name="enteDestinatari[${indice.index}].idEnte" value="${enteDestinatario.idEnte}">
                    <input class="idEvento" type="hidden" id="enteDestinatari_${enteDestinatario.idEnte}_idEvento"  name="enteDestinatari[${indice.index}].idEvento" value="${enteDestinatario.idEvento}">
                    <input class="codiceFiscale" type="hidden" id="enteDestinatari_${enteDestinatario.idEnte}_codiceFiscale"  name="enteDestinatari[${indice.index}].codiceFiscale" value="${enteDestinatari.codiceFiscale}">
                    <input class="descrizione" type="hidden" id="enteDestinatari_${enteDestinatario.idEnte}_descrizione"  name="enteDestinatari[${indice.index}].descrizione" value="${enteDestinatari.descrizione}">
                    <a class="icona_cancella" href="#" onclick="cancellaEnte('${enteDestinatario.idEnte}', 'enteDestinatari')"></a>&nbsp;${enteDestinatario.codiceFiscale}&nbsp; ${enteDestinatario.descrizione}
                </li>
            </c:forEach>
        </ul>
        <p class="formHint">&nbsp;</p>
    </div>     
</div>
<div id="windowAnagrafica" class="modal-content">
    <div class="box_cercaAnagrafica ctrlHolder">
        <label class="required">
            <spring:message code="evento.anagraficheEnti.stringaRicerca"/>
        </label>
        <input type="text" id="stringaRicercaAnagrafica" name="stringaRicercaAnagrafica" value="" class="ui-widget ui-widget-content ui-corner-all"/>
        <div id="anagraficaTable">
        </div>
    </div>
</div>
<div id="windowEnte" class="modal-content">
    <div class="box_cercaEnte ctrlHolder">
        <label class="required">
            <spring:message code="evento.anagraficheEnti.stringaRicerca"/>
        </label>
        <input type="text" id="stringaRicercaEnte" name="stringaRicercaEnte" value="" class="ui-widget ui-widget-content ui-corner-all"/>
        <div id="enteTable">
        </div>
    </div>
</div>        
