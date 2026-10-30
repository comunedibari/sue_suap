<script type="text/javascript" src="<c:url value="/javascript/jquery.dynatree.js"/>"></script>
<script type="text/javascript">
var treeData = ${tree};
var urlGestisciRuolo = "${path}/pratiche/gestisci/gestisciruoliAjax.htm";
$(function () {
    $("#alberoEntiRuoli").dynatree({
        checkbox: true,
        selectMode: 3,
        children: treeData,
        onSelect: function (select, node) {
            $.ajax({
                url: urlGestisciRuolo,
                dataType: "json",
                data: {
                    select: select,
                    key: node.data.key,
                    parent: node.getParent().data.key,
                    idUtente: ${utente.idUtente}
                },
                success: function (data) {
                    if (data.success) {
                        var stringa = ".attivaBottone" + node.getParent().data.key + "\\\#" + node.data.key;
                        $(stringa+" > input").removeAttr('onclick');
                        $(stringa+" > input").unbind('click');
                        if (select) {
                            $(stringa).removeClass("hidden");
                            var chiave=data.attributes['idRuoloEnte'];
                            //$(stringa+" > input").attr('onclick',"gestisciProcedimentiPopUp("+chiave+")");
                        } else {
                            $(stringa).addClass("hidden");
                        }
                        mostraMessaggioAjax(data.message, "success");
                    } else {
                        mostraMessaggioAjax(data.message, "error");
                    }
                }
            });
        },
        onDblClick: function (node, event) {
            node.toggleSelect();
        },
        onKeydown: function (node, event) {
            if (event.which === 32) {
                node.toggleSelect();
                return false;
            }
        }
    });
});
</script>