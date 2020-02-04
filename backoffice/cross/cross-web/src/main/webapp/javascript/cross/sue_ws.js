$(function() {

    $('#protocollo_automatico').on('change', function() {
        if (String(this.value) === 'false') {
            $($('#protocollo_segnatura').parent()).removeClass('hidden');
            $($('#protocollo_data').parent()).removeClass('hidden');
        } else {
            $($('#protocollo_segnatura').parent()).addClass('hidden');
            $($('#protocollo_data').parent()).addClass('hidden');
        }
    });

    $('input[type=radio][name=invioSueWs]').on('change', function() {
        switch ($(this).val()) {
            case 'TRUE' :
                $('.field_sue_manual').removeClass('hidden');
                $('.field_sue_manual').addClass('hidden');
                break;
            case 'FALSE' :
                $('.field_sue_manual').removeClass('hidden');
                break;
        }
    });

    $("#data_fascicolo").datepicker({
        dateFormat: 'dd/mm/yy'
    });

    $('#submit_sue_ws').submit(function() {
//        $('#submit_sue_ws').attr('disabled', 'disabled');
    });

});
