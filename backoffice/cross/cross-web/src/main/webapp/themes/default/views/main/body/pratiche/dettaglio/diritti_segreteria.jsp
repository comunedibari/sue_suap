<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="w" uri="/WEB-INF/wegoTag.tld"  %>


<script>

    function detailPraticaCollegata(idRecord) {
        console.log("DETAIL: " + idRecord);
        window.location.href = '${path}/pratiche/dettaglio.htm?id_pratica=' + idRecord;
        return false;
    }

    function selectPraticaDaCollegare(idPratica) {
        console.log("Pratica: " + idPratica);

        $.ajax({
            url: '${path}/pratiche/pratiche_collegate/add.htm',
            dataType: "json",
            data: {
                idPratica: ${pratica.idPratica},
                idPraticaCollegata: idPratica
            },
            async: false,
            success: function(data) {
                $('.ui-dialog-content').dialog('close');
                if (data.success) {
                    var praticaCollegataTemplateHtml = $("#template-pratica-collegata").html();
                    var praticaCollegataTemplate = Handlebars.compile(praticaCollegataTemplateHtml);
                    var praticaCollegataNewBox = praticaCollegataTemplate({
                        idPratica: data.attributes.idPraticaCollegata,
                        oggettoPraticaCollegata: data.attributes.oggettoPraticaCollegata,
                        dataRicezionePraticaCollegata: data.attributes.dataRicezionePraticaCollegata,
                        protocolloPraticaCollegata: data.attributes.protocolloPraticaCollegata,
                        statoPraticaCollegata: data.attributes.statoPraticaCollegata
                    });

                    $('.container-praticacollegata table tr').append(praticaCollegataNewBox);

                    $('#summary_block-praticacollegata-' + idPratica + ' .remove').on('click', function(event) {
                        return confirmDeletePraticacollegata(event);
                    });

                    $('#summary_block-praticacollegata-' + idPratica + ' .detail').on('click', function(event) {
                        return detailPraticaCollegata(data.attributes.idPraticaCollegata);
                    });
                } else {
                    mostraMessaggioAjax(data.message, "error");
                }
            }
        });
    }

    function deletePraticaCollegata(idRecord, uiRemoveCallback) {
        console.log("DELETE: " + idRecord);

        $.get(
                '${path}/pratiche/pratiche_collegate/delete.htm',
                {
                    idPratica: "${pratica.idPratica}",
                    idPraticaCollegata: idRecord
                },
        wgf.ajax.responseHandler(function() {
            uiRemoveCallback(idRecord);
        }));

    }
    function addPraticaCollegata() {
        console.log("ADD ");
        wgf.utils.showFormDialog(
                'Collega pratica',
                '${path}/form/ajax/view.htm?idForm=it.wego.cross.forms.common.PraticheCollegateGrid');
                    }
</script>

<w:form formId="it.wego.cross.forms.common.SummaryBlockPraticheCollegate" />
