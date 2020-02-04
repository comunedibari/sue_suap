<%
    String url = request.getContextPath() + "/anagrafe_tributaria/export/genera.htm";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
    $(function() {
        $('#codFornitura').change(function() {
            var value = $('#codFornitura').val();
            if (value === 'CC') {
                $('.anagrafeTributariaCommercio').show();
            } else {
                $('.anagrafeTributariaCommercio').hide();
            }
        });

        $('form').submit(function(event) {
            var codFornitura = $('#codFornitura').val();
            var soggettoObbligato = $('#idSoggettoObbligato').val();
            var naturaUfficio = $('#idNaturaUfficio').val();
            var idEnte = $('#idEnte').val();
            var codiceFiscale = $('#codiceFiscaleSoggettoObbligato').val();
            var anno = $('#annoRiferimento').val();
            var url = '${path}/anagrafe_tributaria/export/genera.htm?codFornitura=' + codFornitura + '&idNaturaUfficio=' + naturaUfficio + '&idEnte=' + idEnte + '&idSoggettoObbligato=' + soggettoObbligato + '&codiceFiscaleSoggettoObbligato=' + codiceFiscale + '&annoRiferimento=' + anno;
            var win = window.open(url, '_blank');
            win.focus();
            return false;
        });
    });
</script>
<tiles:insertAttribute name="body_error" />
<form:form modelAttribute="anagrafeTributaria" action="<%=url%>" id="anagrafeTributaria" method="post" cssClass="uniForm inlineLabels comunicazione">
    <div>
        <div  class="uniForm ">

            <div class="sidebar_auto">
                <div class="page-control" data-role="page-control">
                    <span class="menu-pull"></span> 
                    <div class="menu-pull-bar"></div>
                    <!-- Etichette cartelle -->
                    <ul>
                        <li class="active"><a href="#frame1"><spring:message code="menu.anagrafe.tributaria"/></a></li>
                    </ul>
                    <!-- Contenuto cartelle -->
                    <div class="frames">
                        <div class="frame active" id="frame1">

                            <div class="inlineLabels">
                                <div class="ctrlHolder dettaglio_liv_0">
                                    <label class="required" for="codFornitura">Tipologia di anagrafe</label>
                                    <form:select id="codFornitura"  path="codFornitura">
                                        <form:option value="" label="-" />
                                        <form:options items="${codiceFornitura}" itemLabel="itemLabel" itemValue="itemValue"/>
                                    </form:select>
                                </div>

                                <div class="ctrlHolder dettaglio_liv_0 anagrafeTributariaCommercio" style="display: none">
                                    <label class="required" for="idNaturaUfficio">Natura ufficio</label>
                                    <form:select id="idNaturaUfficio"  path="idNaturaUfficio">
                                        <form:options items="${naturaUfficio}" itemLabel="itemLabel" itemValue="itemValue"/>
                                    </form:select>
                                </div>

                                <div class="ctrlHolder dettaglio_liv_0 anagrafeTributariaCommercio" style="display: none">
                                    <label class="required" for="idEnte">Ente</label>
                                    <form:select id="idEnte" path="idEnte" >
                                        <form:options items="${enti}" itemLabel="itemLabel" itemValue="itemValue"/>
                                    </form:select>
                                </div>

                                <div class="ctrlHolder dettaglio_liv_0">
                                    <label class="required" for="idSoggettoObbligato">Soggetto obbligato</label>
                                    <form:select id="idSoggettoObbligato" path="idSoggettoObbligato" >
                                        <form:options items="${comuni}" itemLabel="itemLabel" itemValue="itemValue"/>
                                    </form:select>
                                </div>

                                <div class="ctrlHolder dettaglio_liv_0">
                                    <label class="required" for="codiceFiscaleSoggettoObbligato">Codice fiscale ente</label>
                                    <form:input path="codiceFiscaleSoggettoObbligato" id="codiceFiscaleSoggettoObbligato" maxlength="16" cssClass="textInput required"/>
                                    <p class="formHint"></p>
                                </div>

                                <div class="ctrlHolder dettaglio_liv_0">
                                    <label class="required" for="annoRiferimento">Anno riferimento</label>
                                    <form:input path="annoRiferimento" id="annoRiferimento" maxlength="4" cssClass="textInput required"/>
                                    <p class="formHint"></p>
                                </div>

                            </div>
                        </div>



                    </div>
                </div>

                <div class="buttonHolder">
                    <button type="submit" id="generaTributaria" class="primaryAction"><spring:message code="pratica.comunicazione.evento.crea"/></button>
                </div>

            </div>
            <div class="clear"></div>
        </div>
    </div>
</form:form>