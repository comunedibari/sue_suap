/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$(function() {
    $('#originalupload').fileupload({
        dataType: 'json',
        done: function(e, data) {
            //$("#uploaded-files tr:has(td)").remove();
          
            enableSubmit();
            $.each(data.result, function(index, file) {
                if(file.name==="_ERRORE_ERRORE_"){  
                   
                    alert("Si e' verificato un errore nel salvataggio temporaneo del file. Nessun file e' stato caricato. Si prega di riprovare e contattare l'aministratore qualora il problema dovesse ripresentarsi.");
                    return false;
                };
                $('.loadedfile').remove();
                 var type = '<td class="loadedfile">' +' <input style="display:none" name="allegatoOriginale.tipoFile" value="'+ file.type+'">'+ '<div class="'+file.typeCode+'" style="text-align: center;">'+'</div>';
                $("#uploaded-files-info") 
                    .append( $('<td class="loadedfile"><input style="display:none"  name="allegatoOriginale.pathFile" value="'+file.tempPath+'"/><input style="display:none"  name="allegatoOriginale.nomeFile" value="'+file.name    +'"/>'+file.name+    '</td>'))
                    .append(type);
            });
        },
        progressall: function(e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css(
                    'width',
                    progress + '%'
                    );
        },
        dropZone: $('#dropzone')
    });
});

