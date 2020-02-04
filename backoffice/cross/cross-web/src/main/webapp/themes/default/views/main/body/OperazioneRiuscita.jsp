<%-- 
    Document   : messaggio
    Created on : 25-gen-2012, 17.04.55
    Author     : CS
--%>
<%@page import="it.wego.cross.utils.Utils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${(messaggiooperazioneeffettuata != null)&&(messaggiooperazioneeffettuata ne '')}">
    <div id="operazioneriuscitapanel">
        <div class="uniForm failedSubmit" action="#">
            <div>
                <div id="successMsgCopy">
                    <h3>Operazione eseguita con successo:</h3>
                    <li> ${messaggiooperazioneeffettuata} </li>
                </div>
            </div>
        </div>
    </div>
</c:if>
<script>
setTimeout(function() {
    $('#operazioneriuscitapanel').fadeOut('slow');
}, 7000);
</script>