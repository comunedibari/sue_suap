$(function() {
    $(".recapitoNotifica input[name='notifica\\.descComune']").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: urlConfrontoAnagrafiche,
                dataType: "json",
                data: {
                    descrizione: $(".recapitoNotifica .recapito\\.descComune").val(),
                    action: "trovaComune"
                },
                success: function(data) {
                    response($.map(data.rows, function(item) {
                        return {
                            label: item.descrizione + " ( " + item.provincia.descrizione + " ) ",
                            value: item.descrizione,
                            id: item.codCatastale,
                            idComune: item.idComune,
                            statoId: item.stato.idStato,
                            statoDesc: item.stato.descrizione,
                            provinciaId: item.provincia.idProvincie,
                            provinciaDesc: item.provincia.descrizione
                        };
                    }));
                }
            });
        },
        mustMatch: true,
        minLength: 2,
        select: function(event, ui) {
            $(".recapitoNotifica input[name='notifica\\.descComune']").val(ui.item.value);
            $(".recapitoNotifica input[name='notifica\\.idComune']").val(ui.item.idComune);
            $(".recapitoNotifica input[name='notifica\\.codCatastale']").val(ui.item.id);
            $(".recapitoNotifica input[name='notifica\\.idProvincia']").val(ui.item.provinciaId);
            $(".recapitoNotifica input[name='notifica\\.descProvincia']").val(ui.item.provinciaDesc);
            $(".recapitoNotifica input[name='notifica\\.idStato']").val(ui.item.statoId);
            $(".recapitoNotifica input[name='notifica\\.descStato']").val(ui.item.statoDesc);
        }
    });

    $("#recapitoNotificaConferma").click(function() {
        //Setto 1 il counter: posso avere 1 solo recapito di notifica per la pratica
        $(".recapitoNotifica input[name='notifica\\.counter']").val(1);
        var form = $("<form>").append($(".recapitoNotifica input").clone());
        
        form.append($('<input>', {
            'name': 'action',
            'value': 'salvaRecapitoSingolo',
            'type': 'hidden'
        }));

        form.append($('<input>', {
            'name': 'idPratica',
            'value': idPratica,
            'type': 'hidden'
        }));

        $.post(urlConfrontoAnagrafiche, form.serialize(),
                function(data) {
                    var messages = [];
                    messages.push(data.message);
                    if (data.success) {
                        displayResponseDialog(messages, false);
                        $("#recapitoNotifica").addClass("confermataBG");
                        $("#recapitoNotifica .confermata").val(1);
                        $("#recapitoNotifica .ui-state-error").removeClass("ui-state-error");
                    } else {
                        displayResponseDialog(messages, true);
                    }
                }, 'json');
    });
});