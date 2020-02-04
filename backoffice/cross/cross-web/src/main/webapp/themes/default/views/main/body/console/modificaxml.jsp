<%
    String path = request.getContextPath();
    String url = path + "/pratica/comunicazione/azione/salva.htm";
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<c:url value="/download/pratica.htm" var="downloadPraticaUrl">
    <c:param name="id_pratica" value="${pratica.idPratica}"/>
</c:url>

<c:url value="/pratica/comunicazione/azione/salva.htm" var="submitEventoUrl">
</c:url>

<tiles:insertAttribute name="body_error" />
<div>
    <tiles:insertAttribute name="operazioneRiuscita" /> 
</div>
<script>
    $(document).bind('dragover', function(e) {
        var dropZoneall = $('#dropzoneall'),
                timeout = window.dropZoneTimeout;
        var dropZone = $('#dropzone');
        if (!timeout) {
            dropZoneall.addClass('in');
            dropZone.addClass('in');
        } else {
            clearTimeout(timeout);
        }
        var found = false,
                node = e.target;
        do {
            if (node === dropZone[0] || node === dropZoneall[0]) {
                found = true;
                break;
            }
            node = node.parentNode;
        } while (node != null);
        if (found) {
            dropZone.addClass('hover');
            dropZoneall.addClass('hover');
        } else {
            dropZone.removeClass('hover');
            dropZoneall.removeClass('hover');
        }
        window.dropZoneTimeout = setTimeout(function() {
            window.dropZoneTimeout = null;
            dropZone.removeClass('in hover');
            dropZoneall.removeClass('in hover');
        }, 100);
    });
</script>


<script>
    var idPratica = '${pratica.idPratica}';
    var idEvento = '${processo_evento.idEvento}';

    function isGiorniScadenzaValid(value) {
        if (value.length === 0) {
            return false;
        }
        var intValue = parseInt(value);
        if (intValue === Number.NaN) {
            return false;
        }
        if (intValue <= 0) {
            return false;
        }
        return true;
    }
    $(document).ready(function() {
        $(".collapsibleContent").hide();
        /*$(".collapsibleContentHeader").click(function() {
         $(this).next(".collapsibleContent").toggle('blind');
         });*/
        $("#creazioneEventoForm").submit(function() {
            var obbligatori = $('.obbligatorio').length;
            errorfile = false;
            for (var i = 0; i < obbligatori; i++) {
                var ob = $('.obbligatorio')[i];
                //^^CS AGGIUNTA
                //******************************************************************************************************
                //AGGIUNTO DA GAB MON 
                //******************************************************************************************************
                //e' stato necessario modificare sensibilmente questo messaggio di errore in modo che non controllasse che i file venissero caricati   
//                var file = $('.fileObbligatorio')[i].value;
                if ((ob.value == undefined || ob.value == ''))
                        // come era prima // || (file== undefined || file== ''))
                        {
                            errorfile = true;
                        }
            }
            if (errorfile)
            {
                //Per ogni file immattere una descrizione e caricare il file
                alert("Per ogni file caricato è obbligatorio immettere una descrizione.");//TODO: ^^CS message
                return false;
            }
            if ($('#giorniScadenzaCustom').length == 1) {
                if (!isGiorniScadenzaValid($('#giorniScadenzaCustom').val())) {
                    alert("Inserire un valore corretto per i giorni scadenza");
                    return false;


                }
            }
        });
    });

    function gestionePostaOrdinari(destinatario) {
        if ($(".ordinariaDiv").length > 0) {
            var id = "tr" + $(".ordinariaDiv tr").lenght;
            $(".ordinariaDiv").show();
            var tr = $(".ordinariaDiv #tr").clone();
            tr.attr("id", id);
            $(".ordinariaDiv table").append(tr);
            $(".ordinariaDiv #" + id + " .ordinariaNominativo").text(destinatario.description);
            $(".ordinariaDiv #" + id + " .ordinariaEmail").text(destinatario.email);
            tr.show();
        }
    }

    function disableSubmit()
    {
        var submit = document.getElementById("creaEvento");
        submit.disabled = true;
        return false;
    }


    function enableSubmit()
    {
        var submit = document.getElementById("creaEvento");
        submit.disabled = false;
        return false;
    }
</script>

<h2 class="short" style="text-align:left">${processo_evento.desEvento}</h2>

<div class="content_sidebar">
    <div class="sidebar_left">
        <h3>Pratica <strong>${pratica.protocollo}</strong></h3>
        <div class="sidebar_elemento">
            ${pratica.oggettoPratica}
            <p><strong><spring:message code="pratica.comunicazione.evento.pratica.dataricezione"/>:</strong> <fmt:formatDate pattern="dd/MM/yyyy" value="${pratica.dataRicezione}" /></p>
            <p><strong><spring:message code="pratica.comunicazione.evento.pratica.stato"/>:</strong> ${pratica.stato}</p>
        </div>
    </div>

    <div class="sidebar_center">
        <br>
        <div class="ctrlHolder">
            <label style="float: left;" >   <spring:message code="console.pratica.xml"/> </label>

            <label  class="originale" style="float: right; display:none;">  <spring:message code="console.pratica.xml.originale"/></label>
            <br>
            <textarea  style="float: left;" id="xml"  rows="30" cols="150"  name="xmlPratica" id="xml"> ${xmlPratica}</textarea>
            <textarea  class="originale" style="float: right; display:none;"   id="xmlo"  rows="30" cols="70"  name="xmlPraticaOriginale" id="originale"> ${xmlPraticaOriginale}</textarea>

        </div>
        <button  id="vista" value="H" onclick="hideShowOriginale();
        return false;"><spring:message code="console.pratica.xml.orginale.visualizza"/></button>
        <button  id="vista"  onclick="sendModifiedXml();
        return false;"><spring:message code="console.pratica.xml.modifica"/></button>
    </div>
    <a href="<%=path%>/console/pratiche.htm" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>


    <form id="form" action="<%=path%>/console/pratiche/modificaXML/salva.htm">
        <input type="hidden" id="xmlPratica" name="xmlPratica" value="">
        <input type="hidden" id="id" name="id" value="${pratica.idPratica}">
    </form>

    <script>
    function  sendModifiedXml() {
        var xml = $("#xml").val();
        $("#xmlPratica").attr("value", xml);
        $("#form").submit();
    }
    function hideShowOriginale() {
        var stat = $("#vista").attr("value");
        if ("H" === stat) {
            $("#vista").attr("value", "S");
            $(".originale").show();
            $("#xml").attr("cols", "70");
            $("#vista").text("<spring:message code="console.pratica.xml.orginale.nascondi"/>");
        }
        if ("S" === stat) {
            $("#vista").attr("value", "H");
            $(".originale").hide();
            $("#xml").attr("cols", "150");
            $("#vista").text("<spring:message code="console.pratica.xml.orginale.visualizza"/>");

        }
    }

    </script>
