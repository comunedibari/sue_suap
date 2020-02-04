
$.fn.editable.defaults.emptytext = '[Nessun valore]';

function deleteProcedimento(event) {
    var idEndoprocedimentoEnteToUnlink = $(event.target).attr('data-holder');
    var numeroEndoprocedimenti = $('#procedimenti_box td').length;
    if (numeroEndoprocedimenti > 1) {
        $('<div></div>').appendTo('body')
                .html('<div><h6 class="confirm-body-content">Sei sicuro di voler scollegare il procedimento?</h6></div>')
                .dialog({
                    modal: true,
                    title: 'Conferma cancellazione',
                    zIndex: 10000,
                    width: 300,
                    height: 150,
                    resizable: false,
                    buttons: {
                        'Conferma': function() {
                            $.ajax({
                                type: 'POST',
                                url: path + '/pratica/endoprocedimenti/gestisci.htm',
                                data: {
                                    idProcedimentoEnte: idEndoprocedimentoEnteToUnlink,
                                    idPratica: idPraticaX,
                                    operation: "DELETE"
                                },
                                success: function(data) {
                                    var esito = data.success;
                                    var messaggio = data.msg;
                                    if (esito === 'true') {
                                        $("#endoprocedimento_" + idEndoprocedimentoEnteToUnlink).remove();
                                        mostraMessaggioAjax(messaggio, "success");
                                    } else {
                                        mostraMessaggioAjax(messaggio, "error");
                                    }
                                },
                                dataType: 'json'
                            });
                            $(this).dialog("close");
                        },
                        'Annulla': function() {
                            $(this).dialog("close");
                        }
                    },
                    close: function(event, ui) {
                        $(this).remove();
                    }
                });
    } else {
        mostraMessaggioAjax("Impossibile cancellare. Deve essere presente almeno un endoprocedimento", "error");
    }
}


$(function() {

    $('.box-unlink').on('click', deleteProcedimento);
});