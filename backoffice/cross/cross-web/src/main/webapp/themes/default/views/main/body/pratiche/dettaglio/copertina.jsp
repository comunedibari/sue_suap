<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:if test="${isCoverEditable}">
    <script type="text/javascript">
        var isCoverEditable = ${isCoverEditable};
        var statiPraticaList = [];
        <c:forEach items="${statiPratica}" var="statoPratica" varStatus="status">
        statiPraticaList.push({value: ${statoPratica.idStatoPratica}, text: '${statoPratica.descrizione}'});
        </c:forEach>
        var path = '${path}';
        $.fn.editable.defaults.emptytext = '[Nessun valore]';
        $(function() {

            if (isCoverEditable === false) {
                $('.x-editable-text').each(function(i, obj) {
                    $(this).attr("class", "");
                });
            }

            $('.x-editable-text').editable({
                type: 'text',
                url: path + '/pratica/attributi/aggiorna.htm',
                pk: 'pratica',
                params: function(params) {
                    var data = {};
                    data['idPratica'] = "${pratica.idPratica}";
                    data['name'] = params.name;
                    data['value'] = params.value;
                    data['pk'] = params.pk;
                    return data;
                },
                mode: 'inline',
                success: function(response, newValue) {
                    if (response.success === "false") {
                        return response.msg;
                    }
                }
            });
            if (isCoverEditable === true) {
                $('#pratica_stato').editable({
                    source: statiPraticaList,
                    url: path + '/pratica/attributi/aggiorna.htm',
                    pk: 'pratica',
                    params: function(params) {
                        var data = {};
                        data['idPratica'] = "${pratica.idPratica}";
                        data['name'] = params.name;
                        data['value'] = params.value;
                        data['pk'] = params.pk;
                        return data;
                    },
                    mode: 'inline',
                    success: function(response, newValue) {
                        if (response.success === "false") {
                            return response.msg;
                        }
                        if (response.bloccaChiusura === "true") {
                            $("#pratica_chiusura_data_container").hide();
                        } else {
                            $("#pratica_chiusura_data_container").show();
                        }
                    }
                });
            }
            
            
            if (isCoverEditable === true) {
                $('#pratica_tipologia_intervento').editable({
                    source: statiPraticaList,
                    url: path + '/pratica/attributi/aggiorna.htm',
                    pk: 'pratica',
                    params: function(params) {
                        var data = {};
                        data['idPratica'] = "${pratica.idPratica}";
                        data['name'] = params.name;
                        data['value'] = params.value;
                        data['pk'] = params.pk;
                        return data;
                    },
                    mode: 'inline',
                    success: function(response, newValue) {
                        if (response.success === "false") {
                            return response.msg;
                        }
                        if (response.bloccaChiusura === "true") {
                            $("#pratica_chiusura_data_container").hide();
                        } else {
                            $("#pratica_chiusura_data_container").show();
                        }
                    }
                });
            }
            
            
            $('#pratica_protocollo_segnatura').on('shown', function(e, editable) {
                $(".pratica_protocollo_segnatura_container input").maskfield('GPPPP/0000/099999999', {translation: {'P': {pattern: /[a-zA-Z]/, optional: true}, 'G': {pattern: /[a-zA-Z]/}}});
            });
        });
    </script>  
</c:if>
<div>
    <div class="ctrlHolder">
        <label for="pratica_identificativo" class="required"><spring:message code="pratica.copertina.identificativo.label"/></label>
        <a href="#" id="pratica_identificativo" class="x-editable-text" name="pratica_identificativo">${pratica.identificativoPratica}</a>
        <input id="abilitazione" value="${abilitato}" style="display:none">
    </div>
    <div class="ctrlHolder">
        <label for="pratica_oggetto" class="required"><spring:message code="pratica.copertina.oggetto.label"/></label>
        <a href="#" id="pratica_oggetto" class="x-editable-text" name="pratica_oggetto">${pratica.oggettoPratica}</a>
    </div>
<!--     Tipologia intervento -->
    <c:choose>
		<c:when test="${not empty pratica.prot_suap}">
			<div class="ctrlHolder">
		        <label for="pratica_procedimento" class="required"><spring:message code="pratica.copertina.procedimento.label"/></label>
		        <a href="#" id="pratica_procedimento" class="" name="pratica_procedimento">${pratica.tipoProcedimentoSuap}</a>
		    </div>
		    
			<div class="ctrlHolder">
        		<label for="pratica_stato" class="required"><spring:message code="pratica.tipologia.intervento.label"/></label>
        		<a href="#" id="pratica_tipologia_intervento" class="" name="pratica_tipologia_intervento" data-type="select">${pratica.tipoInterventoSuap}</a>
    		</div>
    		
    		<div class="ctrlHolder">
        		<label for="numero_protocollo_suap" class="required"><spring:message code="pratica.prot_suap.label"/></label>
        		<a href="#" id="numero_protocollo_suap" class="" name="numero_protocollo_suap" data-type="select">${pratica.prot_suap}</a>
    		</div>
    		
    		<div class="ctrlHolder">
        		<label for="data_protocollo_suap" class="required"><spring:message code="pratica.data_prot_suap.label"/></label>
        		<a href="#" id="data_protocollo_suap" class="x-editable-text" data-type="date" data-format="dd/mm/yyyy" name="data_protocollo_suap"><fmt:formatDate pattern="dd/MM/yyyy" value="${pratica.data_prot_suap}" /></a>
    		</div>
    		
		</c:when>
		<c:otherwise>
			<div class="ctrlHolder">
		        <label for="pratica_stato" class="required"><spring:message code="pratica.tipologia.intervento.label"/></label>
		        <a href="#" id="pratica_tipologia_intervento" class="" name="pratica_tipologia_intervento" data-type="select">${pratica.idStatoPratica.descrizione}</a>
		    </div>
		    <div class="ctrlHolder">
		        <label for="pratica_procedimento" class="required"><spring:message code="pratica.copertina.procedimento.label"/></label>
		        <a href="#" id="pratica_procedimento" class="" name="pratica_procedimento">${pratica.procedimento.descrizione}</a>
		    </div>
		    
		    <div class="ctrlHolder">
		        <label for="pratica_processo" class="required">Processo</label>
		        <a href="#" id="pratica_processo" class="" name="pratica_processo">${pratica.idProcesso.desProcesso}</a>
		    </div>
		</c:otherwise>
	</c:choose>
    
    
    
    <div class="ctrlHolder">
        <label for="pratica_ricezione_data" class="required"><spring:message code="pratica.copertina.ricezione.data.label"/></label>
        <a href="#" id="pratica_ricezione_data" class="x-editable-text" data-type="date" data-format="dd/mm/yyyy" name="pratica_ricezione_data"><fmt:formatDate pattern="dd/MM/yyyy" value="${pratica.dataRicezione}" /></a>
    </div>
<!--     <div class="ctrlHolder pratica_protocollo_segnatura_container"> -->
	 <div class="ctrlHolder">
        <label for="pratica_protocollo_segnatura" class="required"><spring:message code="pratica.copertina.protocollo.segnatura.label"/></label>
        <a href="#" id="pratica_protocollo_segnatura" class="x-editable-text" name="pratica_protocollo_segnatura">${pratica.protocollo.segnatura}</a>
    </div>
    <div class="ctrlHolder">
        <label for="pratica_identificativo_esterno" class="required"><spring:message code="pratica.copertina.identificativo.esterno.label"/></label>
        <a href="#" id="pratica_procedimento" class="" name="pratica_procedimento">${pratica.identificativoEsterno}</a>
    </div>
    <div class="ctrlHolder">
        <label for="pratica_protocollo_data" class="required"><spring:message code="pratica.copertina.protocollo.data.label"/></label>
        <a href="#" id="pratica_protocollo_data" class="x-editable-text" data-type="date" data-format="dd/mm/yyyy" name="pratica_protocollo_data"><fmt:formatDate pattern="dd/MM/yyyy" value="${pratica.protocollo.dataProtocollazione}" /></a>
    </div>
    <div class="ctrlHolder">
        <label for="pratica_stato" class="required"><spring:message code="pratica.copertina.stato.label"/></label>
        <a href="#" id="pratica_stato" class="" name="pratica_stato" data-type="select">${pratica.idStatoPratica.descrizione}</a>
    </div>
    <div class="ctrlHolder <c:if test="${pratica.idStatoPratica.grpStatoPratica != 'C'}">hidden</c:if>" id="pratica_chiusura_data_container">
        <label for="pratica_chiusura_data" class="required"><spring:message code="pratica.copertina.chiusura.data.label"/></label>
        <a href="#" id="pratica_chiusura_data" class="x-editable-text" data-type="date" data-format="dd/mm/yyyy" name="pratica_chiusura_data"><fmt:formatDate pattern="dd/MM/yyyy" value="${pratica.dataChiusura}" /></a>
    </div>

    <!--                        <div class="ctrlHolder">
                                <label for="pratica_responsabile_istruttoria" class="required"><spring:message code="pratica.copertina.responsabile.istruttoria.label"/></label>
                                <a href="#" id="pratica_responsabile_istruttoria" class="x-editable-text" name="pratica_responsabile_istruttoria">${pratica.responsabileProcedimento}</a>
                            </div>-->
    <div class="ctrlHolder">
        <label for="pratica_responsabile_procedimento" class="required"><spring:message code="pratica.copertina.responsabile.procedimento.label"/></label>
        <a href="#" id="pratica_responsabile_procedimento" class="x-editable-text" name="pratica_responsabile_procedimento">${pratica.responsabileProcedimento}</a>
    </div>
    <div class="ctrlHolder">
        <label for="pratica_comune" class="required"><spring:message code="pratica.copertina.comune.label"/></label>
        <a href="#" id="pratica_comune" name="pratica_comune">${pratica.idComune.descrizione}</a>
    </div>
</div>

<script>
    $(function() {
        if ($("#abilitazione").val() !== "true") {
            $('.nascondiutente').each(function(i, obj) {
                $(this).detach();
            });
        }
    })
</script>