<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<tiles:insertAttribute name="body_error" />

<h2 style="text-align: center"><spring:message code="utenti.title.aggiungi"/></h2>
<form id="utenteform" action="${path}/utenti/aggiungi.htm" class="uniForm inlineLabels" method="post">

    <div class="inlineLabels">

        <input type="hidden" name="action" value="add">

        <div class="ctrlHolder">
            <label for="nome" class="required"><spring:message code="utenti.nome"/></label>
            <input name="nome" id="nome" maxlength="255" type="text" class="textInput required" value="${utente.nome}" >
            <p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
        </div>

        <div class="ctrlHolder">
            <label for="cognome" class="required"><spring:message code="utenti.cognome"/></label>
            <input name="cognome" id="cognome" maxlength="255" type="text" class="textInput required" value="${utente.cognome}" >
            <p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
        </div>

        <div class="ctrlHolder">
            <label for="codiceFiscale" class="required"><spring:message code="utenti.codicefiscale"/></label>
            <input name="codiceFiscale" id="codiceFiscale" maxlength="16" type="text" class="textInput required" value="${utente.codiceFiscale}" >
            <p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
        </div>

        <div class="ctrlHolder">
            <label for="username" class="required"><spring:message code="utenti.username"/></label>
            <input name="username" id="username" maxlength="255" type="text" class="textInput required" value="${utente.username}">
            <p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
        </div>
        
        <div class="ctrlHolder">
            <label for="password" class="required"><spring:message code="utenti.password"/></label>
            <input name="password" id="username" maxlength="255" type="password" class="textInput required">
            <p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
        </div>

       <div class="ctrlHolder">
            <label for="email" class="required"><spring:message code="utenti.email"/></label>
            <input name="email" id="email" maxlength="255" type="text"  class="textInput required" value="${utente.email}" >
            <p class="formHint"><spring:message code="utenti.campo.obbligatorio"/></p>
        </div>

        <div class="ctrlHolder" >
            <label for="telefono" class="required"><spring:message code="utenti.telefono"/></label>
            <input name="telefono" id="telefono" maxlength="50" type="text" class="textInput" value="${utente.telefono}">
        </div>

        <div class="ctrlHolder" >
            <label for="superuser" class="required"><spring:message code="utenti.superuser"/></label>
            <input type="checkbox" name="superusercheck" id="superusercheck"  class="textInput" onclick="aggiornacheckbox()"/>   
            <input type="hidden"  id="superuser" name="superuser" value="N"/>
        </div>
        
        <div class="ctrlHolder" >
            <label for="superuser" class="required">Utente per estrazioni</label>
            <input type="checkbox" name="superusercheckestra" id="superusercheckestra"  class="textInput" onclick="aggiornacheckboxestrazioni()"/>   
            <input type="hidden"  id="estrazioniUser" name="estrazioniUser" value="N"/>
        </div>
        
        <div class="ctrlHolder" >
            <label for="superuser" class="required">Utente per estrazioni cila 20%</label>
            <input type="checkbox" name="superusercheckcila" id="superusercheckcila"  class="textInput" onclick="aggiornacheckboxcila()"/>   
            <input type="hidden"  id="estrazioniCilaTodoUser" name="estrazioniCilaTodoUser" value="N"/>
        </div>

        <div class="ctrlHolder">
            <label for="note"><spring:message code="utenti.note"/></label>
            <textarea rows="4" cols="100" name="note" id="note" class="textArea" value="${utente.note}"></textarea>
        </div>
    </div>

    <div class="buttonHolder">
        <a href="${path}/utenti/index.htm" class="secondaryAction">&larr; Indietro</a>
        <button type="submit" class="primaryAction"><spring:message code="utenti.button.nuovo"/></button>
    </div>
</form>
<script>
                function aggiornacheckbox() {
                    var form = $("#superusercheck");
                    if (($("#superusercheck").prop('checked') !== null) && ($("#superusercheck").prop('checked') === true)) {
                        if ($("#superuser").size() > 0) {
                            $("#superuser").remove();
                        }
                        form.append('<input type=\"hidden\"  id=\"superuser\" name=\"superuser\" value=\"S\" >');


                    } else {
                        if ($("#superuser").size() > 0) {
                            $("#superuser").remove();
                        }
                        form.append('<input type=\"hidden\"  id=\"superuser\" name=\"superuser\" value=\"N\" >');
                    }
                    return false;
                }
                
                function aggiornacheckboxestrazioni() {
                    var form = $("#superusercheckestra");
                    if (($("#superusercheckestra").prop('checked') !== null) && ($("#superusercheckestra").prop('checked') === true)) {
                        if ($("#estrazioniUser").size() > 0) {
                            $("#estrazioniUser").remove();
                        }
                        form.append('<input type=\"hidden\"  id=\"estrazioniUser\" name=\"estrazioniUser\" value=\"S\" >');


                    } else {
                        if ($("#estrazioniUser").size() > 0) {
                            $("#estrazioniUser").remove();
                        }
                        form.append('<input type=\"hidden\"  id=\"estrazioniUser\" name=\"estrazioniUser\" value=\"N\" >');
                    }
                    return false;
                }
                
                function aggiornacheckboxcila() {
                    var form = $("#superusercheckcila");
                    if (($("#superusercheckcila").prop('checked') !== null) && ($("#superusercheckcila").prop('checked') === true)) {
                        if ($("#estrazioniCilaTodoUser").size() > 0) {
                            $("#estrazioniCilaTodoUser").remove();
                        }
                        form.append('<input type=\"hidden\"  id=\"estrazioniCilaTodoUser\" name=\"estrazioniCilaTodoUser\" value=\"S\" >');


                    } else {
                        if ($("#estrazioniCilaTodoUser").size() > 0) {
                            $("#estrazioniCilaTodoUser").remove();
                        }
                        form.append('<input type=\"hidden\"  id=\"estrazioniCilaTodoUser\" name=\"estrazioniCilaTodoUser\" value=\"N\" >');
                    }
                    return false;
                }
</script>
 