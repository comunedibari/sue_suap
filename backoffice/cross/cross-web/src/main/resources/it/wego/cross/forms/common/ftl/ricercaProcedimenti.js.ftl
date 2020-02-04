[#ftl]
$(function() {
    $("#ricerca_button").on('click',function() {
        var urli = $('#${tableId}').getGridParam("url").split("?")[0];
        $('#${tableId}').setGridParam({url: urli + "?" + $('#form_ricerca').serialize()});
        $('#${tableId}').trigger("reloadGrid", [{page: 1}]);
    });
});