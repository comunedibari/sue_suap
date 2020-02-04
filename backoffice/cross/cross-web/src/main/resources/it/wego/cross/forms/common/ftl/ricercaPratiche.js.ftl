[#ftl]
function disableIdSearchComune(type) {
    $("#search_id_comune_" + type).val('');
    $("#search_id_comune_" + type).attr('disabled', 'disabled');
}

function enableIdSearchComune(type) {
    $("#search_id_comune_" + type).val('');
    $("#search_id_comune_" + type).removeAttr('disabled');
}


function disableSearchComuneComponent(type) {
    $("#search_des_comune_" + type).val('');
    $("#search_des_comune_" + type).attr('disabled', 'disabled');
    $("#search_button_" + type).attr('disabled', 'disabled');
    disableIdSearchComune(type);
}

function enableSearchComuneComponent(type) {
    $("#search_des_comune_" + type).val('');
    $("#search_des_comune_" + type).removeAttr('disabled');
    $("#search_button_" + type).removeAttr('disabled');
    disableIdSearchComune(type);
}

function changeTipoCatasto() {
        var tipoCatasto = $("#search_tipoSistemaCatastale").val();
        if (tipoCatasto == "1") {
            if ($("#divSearch_tipoUnita").hasClass("hidden")) {
                $("#divSearch_tipoUnita").removeClass("hidden");
            }
            if ($("#divSearch_sezione").hasClass("hidden")) {
                $("#divSearch_sezione").removeClass("hidden");
            }
            if ($("#divSearch_foglio").hasClass("hidden")) {
                $("#divSearch_foglio").removeClass("hidden");
            }
            if (!$("#divSearch_tipoParticella").hasClass("hidden")) {
                $("#divSearch_tipoParticella").addClass("hidden");
            }
            if (!$("#divSearch_comuneCensuario").hasClass("hidden")) {
                $("#divSearch_comuneCensuario").addClass("hidden");
            }
            if (!$("#divSearch_estensioneParticella").hasClass("hidden")) {
                $("#divSearch_estensioneParticella").addClass("hidden");
            }
            $("#search_tipoParticella").val('');
            $("#search_comuneCensuario").val('');
            $("#search_estensioneParticella").val('');

        } else if (tipoCatasto == "2") {
            if (!$("#divSearch_tipoUnita").hasClass("hidden")) {
                $("#divSearch_tipoUnita").addClass("hidden");
            }
            if (!$("#divSearch_sezione").hasClass("hidden")) {
                $("#divSearch_sezione").addClass("hidden");
            }
            if (!$("#divSearch_foglio").hasClass("hidden")) {
                $("#divSearch_foglio").addClass("hidden");
            }
            if ($("#divSearch_tipoParticella").hasClass("hidden")) {
                $("#divSearch_tipoParticella").removeClass("hidden");
            }
            if ($("#divSearch_comuneCensuario").hasClass("hidden")) {
                $("#divSearch_comuneCensuario").removeClass("hidden");
            }
            if ($("#divSearch_estensioneParticella").hasClass("hidden")) {
                $("#divSearch_estensioneParticella").removeClass("hidden");
            }

            $("#search_tipoUnita").val('');
            $("#search_sezione").val('');
            $("#search_foglio").val('');
        } else {
            if (!$("#divSearch_tipoUnita").hasClass("hidden")) {
                $("#divSearch_tipoUnita").addClass("hidden");
            }
            if (!$("#divSearch_sezione").hasClass("hidden")) {
                $("#divSearch_sezione").addClass("hidden");
            }
            if (!$("#divSearch_foglio").hasClass("hidden")) {
                $("#divSearch_foglio").addClass("hidden");
            }
            if (!$("#divSearch_tipoParticella").hasClass("hidden")) {
                $("#divSearch_tipoParticella").addClass("hidden");
            }
            if (!$("#divSearch_comuneCensuario").hasClass("hidden")) {
                $("#divSearch_comuneCensuario").addClass("hidden");
            }
            if (!$("#divSearch_estensioneParticella").hasClass("hidden")) {
                $("#divSearch_estensioneParticella").addClass("hidden");
            }
            $("#search_tipoUnita").val('');
            $("#search_sezione").val('');
            $("#search_foglio").val('');
            $("#search_tipoParticella").val('');
            $("#search_comuneCensuario").val('');
            $("#search_estensioneParticella").val('');
        }
    }
$(function() {
    changeTipoCatasto();
    disableIdSearchComune('pratica');
    disableIdSearchComune('catasto');
    $("#search_des_comune_pratica").val('');
    $("#search_des_comune_catasto").val('');

    $('#search_button_catasto').click(function() {
        enableSearchComuneComponent('pratica');
        enableSearchComuneComponent('catasto');
    });
    $('#search_button_pratica').click(function() {
        enableSearchComuneComponent('pratica');
        enableSearchComuneComponent('catasto');
    });

    $('#search_des_comune_catasto').change(function() {
        var content = $("#search_des_comune_catasto").val();
        if (content.length > 0) {
            disableSearchComuneComponent('pratica');
            enableSearchComuneComponent('catasto');
        } else {
            enableSearchComuneComponent('pratica');
            enableSearchComuneComponent('catasto');
        }
    });

    $('#search_des_comune_pratica').change(function() {
        var content = $("#search_des_comune_pratica").val();
        if (content.length > 0) {
            enableSearchComuneComponent('pratica');
            disableSearchComuneComponent()('catasto');
        } else {
            enableSearchComuneComponent('pratica');
            enableSearchComuneComponent('catasto');
        }
    });

    $("#ricerca_button").on('click',function() {
        var urli = $('#${tableId}').getGridParam("url").split("?")[0];
        $('#${tableId}').setGridParam({url: urli + "?" + $('#form_ricerca').serialize()});
        $('#${tableId}').trigger("reloadGrid", [{page: 1}]);
    });

    $("#search_data_from").datepicker({
        dateFormat: 'dd/mm/yy'
    });

    $("#search_data_to").datepicker({
        dateFormat: 'dd/mm/yy'
    });

    $('#search_des_comune_pratica').autocomplete({
        source: function(request, response) {
            $.ajax({
                url: "${data.path}/search/comune.htm",
                dataType: "json",
                data: {
                    description: $("#search_des_comune_pratica").val(),
                    dataValidita: ''
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
            disableSearchComuneComponent('catasto');
            enableIdSearchComune('pratica');
            $('#search_id_comune_pratica').val(ui.item.id);
        },
        minLength: 2
    });

    $('#search_des_comune_catasto').autocomplete({
        source: function(request, response) {
            $.ajax({
                url: "${data.path}/search/comune.htm",
                dataType: "json",
                data: {
                    description: $("#search_des_comune_catasto").val(),
                    dataValidita: ''
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
            disableSearchComuneComponent('pratica');
            enableIdSearchComune('catasto');
            $('#search_id_comune_catasto').val(ui.item.id);
        },
        minLength: 2
    });
});
