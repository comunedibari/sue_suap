<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<script>
    function mostraMessaggioAjax(messaggio, result) {
        if (!result) {
            result = 'success';
        }
        if(result==='success'){$("#esitoPanel h3").text('Operazione eseguita con successo:');}
        if(result==='warning'){$("#esitoPanel h3").text('Attenzione:');}
        if(result==='error'){$("#esitoPanel h3").text('Si è verificato un errore:');}
        
        $("#esitoPanelMessage").text(messaggio);
        
        
        $("#esitoPanel").show();
        
        $("#esitoPanel>div>div").removeClass();
        $("#esitoPanel>div>div").addClass("message");
        $("#esitoPanel>div>div").addClass(result);
        
        setTimeout(function() {
            var selectedEffect = 'blind';
            var options = {};
            $("#esitoPanel").hide(selectedEffect, options, 500);
        }, 5000);
    }
</script>

<div id="esitoPanel" style="display:none">
    <div class="uniForm">
        <div>
            <div>
                <h3></h3>
                <span id="esitoPanelMessage">  </span>
            </div>
        </div>
    </div>
</div>