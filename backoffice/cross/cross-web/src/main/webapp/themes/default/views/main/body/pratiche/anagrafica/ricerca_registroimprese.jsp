<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
    String path = request.getContextPath();
%>
<script>
    $(document).ready(function(){

        $('#buttonRicercaRI').click(function(e){
            var codiceFiscale = $('#codiceFiscaleGiuridica').val();
            //trim della stringa
            codiceFiscale = codiceFiscale.replace(/^\s+|\s+$/g,'')
            if (codiceFiscale == ''){
                $('#codiceFiscaleAssente').dialog({
                    buttons: {
                        'Chiudi': function() {
                            $( this ).dialog( "close" );
                        }
                    }
                });
            } else {
                $.ajax({
                    type: 'POST',
                    url: '<%=path%>/gestione/anagrafiche/personagiuridica.htm',
                    data: {codiceFiscale: codiceFiscale}
                }).done(function(data) {
                    var wHeight = $(window).height() * 0.8;
                    $('.dettaglioAziendaContainer').empty();
                    $('#dettaglioAzienda').html(data);
                    $('#dettaglioAzienda').dialog({
                        title: 'Dettaglio anagrafica azienda',
                        modal: true,
                        height: wHeight,
                        width: '50%',
                        buttons: {
                            'Accetta valori': function() {
                                $('#denominazione').val($.trim($('#denominazioneRI').text()));
                                $('#codiceFiscale').val($.trim($('#codiceFiscaleRI').text()));
                                $('#partitaIva').val($.trim($('#partitaIvaRI').text()));
                                $('#desProvinciaIscrizioneRi').val($.trim($('#desCciaaRI').text()));
                                $('#nProvinciaIscrizioneRi').val($.trim($('#idCciaaRI').val()));
                                $('#nIscrizioneRea').val($.trim($('#numeroReaRI').text()));
                                $('#desComuneRecapito').val($.trim($('#descComune').text()));
                                $('#nComuneRecapito').val($.trim($('#idComune').text()));
                                $('#capRecapito').val($.trim($('#cap').text()));
                                $('#civicoRecapito').val($.trim($('#nCivico').text()));
                                $('#indirizzoRecapito').val($.trim($('#indirizzo').text()));
                                $('#telefonoRecapito').val($.trim($('#telefono').text()));
                                $( this ).dialog( "close" );
                            },
                            'Annulla': function() {
                                $( this ).dialog( "close" );
                            }
                        }
                    });
                    return false;
                });   
            }
        });
    });
</script>

<div id="dettaglioAzienda" class="modal-content"></div>
<div id="codiceFiscaleAssente" class="modal-content"><spring:message code="anagrafica.error.partitaivaassente"/></div>