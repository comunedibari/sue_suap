function resendEmail(dialog) {
    var emailId = $(dialog).find('input[name="idEmail"]').val();
    var taskId = $(dialog).find('input[name="taskId"]').val();
    var destinatario = $(dialog).find('input[name="destinatario"]').val();
    var parameters = {};
    parameters.emailId = emailId;
    parameters.taskId = taskId;
    parameters.destinatario = destinatario;
    $.ajax({
        type: 'POST',
        url: path + '/pratiche/mail/resend.htm',
        data: parameters,
        dataType: 'json',
        success: function(data) {
            var esito = data.success;
            var messaggio = data.message;
            if (String(esito) === 'true') {
                mostraMessaggioAjax(messaggio, "success");
            } else {
                mostraMessaggioAjax(messaggio, "error");
            }
            $(dialog).dialog('close');
            setTimeout(function() {
                location.reload();
            }, 6000);

        }
    });
}

$(function() {
    var url = getUrl();

    $("#pubblicazionePortale").change(function() {
        var $form = $("<form>");
        $form.append($('<input>',
                {
                    'name': 'idPratica',
                    'value': idPratica,
                    'type': 'hidden'
                }));
        $form.append($('<input>',
                {
                    'name': 'idPraticaEvento',
                    'value': idPraticaEvento,
                    'type': 'hidden'
                }));
        $form.append($('<input>',
                {
                    'name': 'idEvento',
                    'value': idEvento,
                    'type': 'hidden'
                }));
        $form.append($('<input>',
                {
                    'name': 'pubblicazionePortale',
                    'value': $('#pubblicazionePortale').is(':checked') ? 'S' : 'N',
                    'type': 'hidden'
                }));
        $form.attr({
            method: 'post',
            action: url
        });
        $.post(url, $form.serialize(),
                function(data) {
                    if (data.errors !== null) {
                        alert(data.errors);
                    } else {
                        alert("Operazione Effettuata");
                    }
                }, 'json');
    });

//    $("#dettaglio_email td form").submit(function() {
//        gestioneEmail($(this));
//        return false;
//    });
});