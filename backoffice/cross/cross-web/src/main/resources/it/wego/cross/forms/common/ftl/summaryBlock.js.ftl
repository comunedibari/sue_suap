[#ftl]
[#assign removeCallbackFunction="uiRemove"+component.attributes.config.prefix?cap_first]
[#assign confirmDeleteCallbackFunction="confirmDelete"+component.attributes.config.prefix?cap_first]
    function ${removeCallbackFunction}(idRecord){
        var elementToRemove="#summary_block-${component.attributes.config.prefix}-"+idRecord;
        $(elementToRemove).remove();
    }
    function ${confirmDeleteCallbackFunction}(event){
        var idRecord = event.target.getAttribute('data-holder');
        $('<div></div>').appendTo('body').html('<div><h6 class="confirm-body-content">Sei sicuro di voler scollegare il record?</h6></div>').dialog({
            title: 'Conferma cancellazione',
            modal: true,
            resizable: false,
            width: 500,
            //dialogClass: 'collega-procedimento-dialog',
            buttons: {
                'Ok': function() {
                    if (idRecord!=='') {
                        ${component.attributes.config.handlers.remove}(idRecord, ${removeCallbackFunction});
                    } else {
                        mostraMessaggioAjax("Impossibile trovare il record da eliminare", "error");
                    }
                    $(this).dialog('close');
                },
                'Annulla': function() {
                    $(this).dialog('close');
                }
            }
        });
        return false;
    }
$(function() {
    
    [#if component.attributes.config.handlers.detail?? && component.attributes.config.handlers.detail!=""]
        $('.summary_block.detail.${component.attributes.config.cssClass}').on('click', function(event){
            var idRecord = event.target.getAttribute('data-holder');
            ${component.attributes.config.handlers.detail}(idRecord);
        });
    [/#if]
    [#if component.attributes.config.handlers.add?? && component.attributes.config.handlers.add!=""]
        $('.summary_block.add.${component.attributes.config.cssClass}').on('click', function(event){
            ${component.attributes.config.handlers.add}();
        });
    [/#if]
    [#if component.attributes.config.handlers.remove?? && component.attributes.config.handlers.remove!=""]
        $('.summary_block.remove.${component.attributes.config.cssClass}').on('click', function(event){
            ${confirmDeleteCallbackFunction}(event);
        });
    [/#if]
});

