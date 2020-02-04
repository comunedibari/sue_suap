/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


// jQuery plugin to prevent double submission of forms

function createDialog( title, text,callback) {
    return $("<div class='dialog' title='" + title + "'><p>" + text + "</p></div>")
    .dialog({
        resizable: false,
        height:140,
        modal: true,
        buttons: {
            "Si": function() {
                $( this ).dialog( "close" );
                callback();
            },
            "No": function() {
                $( this ).dialog( "close" );
            }
        }
    });    
}
$.fn.preventDoubleSubmission = function(titolo, testo, attesa, func) {
    $(this).each(function(){
        var form=$(this);
        form.on('submit',function(e){
            var ritorno = true;
            if (typeof func == "function") {
                ritorno = func();
            }  
            if (ritorno){
                if (form.data('submitted') === true) {
                    // Previously submitted - don't submit again
                    if (form.data('submittedOk') === true) {
                        form.data('submittedOk', false);
                    } else {
                        e.preventDefault();
                    }
                } else {
                    // Mark it so that the next submit can be ignored
                    createDialog(titolo,testo,function(){
                        form.data('submittedOk', true);
                        form.data('submitted', true);
                        HTMLFormElement.prototype.submit.call(form[0]);
                        // var tasto = "submit";
                        // if (form.data('tastoPremuto')) {
                        //    tasto = form.data('tastoPremuto');
                        //}
                        //$('input[name='+tasto+']',form).trigger('click');
                        $("#wrapper").mask(attesa);
                    });
                    e.preventDefault();
                }
            } else {
                e.preventDefault();
            }
        });
    }); 
};