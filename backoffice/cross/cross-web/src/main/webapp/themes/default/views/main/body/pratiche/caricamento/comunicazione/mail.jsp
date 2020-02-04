<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="ctrlHolder form_checkbox_div">
    <label for="inviaEmail" style="font-size: 1em;">
        <spring:message code="pratica.comunicazione.evento.email"/>
    </label>
    <input type="checkbox" name="inviaEmail" id="inviaEmail" value="ok" checked="checked">
    <p class="formHint">&nbsp;</p>
</div>
