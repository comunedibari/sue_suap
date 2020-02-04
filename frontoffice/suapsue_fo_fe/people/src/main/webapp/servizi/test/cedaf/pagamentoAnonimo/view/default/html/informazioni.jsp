<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>

<html:xhtml/>

<div class="title1">
    <h1><bean:message key="label.Introduzione" /></h1>
</div>
<div class="text_block">
    <bean:message key="label.Introduzione.Welcome" />
    <p>
        <bean:message key="label.Introduzione.Intro" />
    </p>
</div>