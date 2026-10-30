
<%
	String path = request.getContextPath();
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="it.wego.cross.utils.JspSet" %>

<script type="text/javascript">
	$(document).ready(function() {
		$('#unselected_subjects').on('dblclick', function(e) {
		      var options = $(this).find('option:selected');
		      $('#selected_subjects').append(options);
		    });
		  
		  $('#selected_subjects').on('dblclick', function(e) {
		      var options = $(this).find('option:selected');
		      $('#unselected_subjects').append(options);
		    });
		  
		  $('#unselected_cadastral_data').on('dblclick', function(e) {
		      var options = $(this).find('option:selected');
		      $('#selected_cadastral_data').append(options);
		    });
		  
		  $('#selected_cadastral_data').on('dblclick', function(e) {
		      var options = $(this).find('option:selected');
		      $('#unselected_cadastral_data').append(options);
		    });
		  
		  $('#unselected_cadastral_address').on('dblclick', function(e) {
		      var options = $(this).find('option:selected');
		      $('#selected_cadastral_address').append(options);
		    });
		  
		  $('#selected_cadastral_address').on('dblclick', function(e) {
		      var options = $(this).find('option:selected');
		      $('#unselected_cadastral_address').append(options);
		    });
	});
</script>   
<jsp:useBean id="distinctAnagrafiche" class="it.wego.cross.utils.JspSet"/> 
<jsp:useBean id="distinctCatasti" class="it.wego.cross.utils.JspSet"/> 
<jsp:useBean id="distinctIndirizzi" class="it.wego.cross.utils.JspSet"/> 
<c:if test="${!abilitazioneConsultazione}">
<div id="ricerca">
    <h3><a href="#"><spring:message code="ricerca.title"/></a></h3>
    <div>
        <form class="uniForm inlineLabels" method="post">
            <input name="tipoFiltro" type="hidden" value="utente" />
            <div>    
                <div class="ricerca_div_forms">    
                    <div class="inlineLabels">
                    	<fieldset id="praticaField" class="no-margin">	
                            <legend><spring:message code="ricerca.label.soggettiCoinvolti"/></legend>
                            
                            <div class="witdh50 ctrlHolder" id="divSearch_stato">
                                <label><spring:message code="ricerca.label.notSelected"/></label>
                                <select id="unselected_subjects" class="select textInput" multiple="true" class="witdh50">
                                	<c:forEach items="${dettaglio.anagrafiche}" begin="0" var="anagrafica" varStatus="status">
							            <c:choose>
									    	<c:when  test="${anagrafica.nome != null or anagrafica.cognome != null}">
									    		<c:if test="${!distinctAnagrafiche.contains(anagrafica.nome, anagrafica.cognome) }">
											    	${distinctAnagrafiche.add(anagrafica.nome, anagrafica.cognome)}
										    		<option subject_surname="${anagrafica.cognome}" subject_name="${anagrafica.nome}">${anagrafica.cognome}&nbsp;${anagrafica.nome}</option>
									    		</c:if>
									    	</c:when >
									    	<c:when  test="${anagrafica.ragioneSociale != null}">
									    		<c:if test="${!distinctAnagrafiche.contains(anagrafica.ragioneSociale) }">
											    	${distinctAnagrafiche.add(anagrafica.ragioneSociale)}
									    			<option company_name="${anagrafica.ragioneSociale}">${anagrafica.ragioneSociale}</option>
									    		</c:if>
									    	</c:when >
								    	</c:choose>
								    </c:forEach>
                                </select>
                                <p class="formHint"></p>
                            </div>   
                            <div class="width30 ctrlHolder" id="divSearch_operatore">
                                <label><spring:message code="ricerca.label.selected"/></label>
                                <select multiple="true" class="selected" id="selected_subjects" class="width50">
				       			</select>
                                <p class="formHint"></p>
                            </div>
						</fieldset>
						<fieldset id="praticaField" class="no-margin">	
                            <legend><spring:message code="ricerca.label.datiCatastali"/></legend>
                            
                            <div class="witdh50 ctrlHolder" id="divSearch_stato">
                                <label><spring:message code="ricerca.label.notSelected"/></label>
                                <select id="unselected_cadastral_data" class="select textInput" multiple="true" class="witdh50">
                                	<c:forEach items="${dettaglio.datiCatastali}" begin="0"  var="immobile" varStatus="loop">
							    		<c:choose>
									    	<c:when  test="${immobile.desTipoUnita != null or immobile.sezione != null or immobile.foglio != null or immobile.mappale != null or immobile.subalterno!= null}">
									    		<c:if test="${!distinctCatasti.contains(immobile.desTipoUnita, immobile.sezione, immobile.foglio, immobile.mappale, immobile.subalterno) }">
											    	${distinctCatasti.add(immobile.desTipoUnita, immobile.sezione, immobile.foglio, immobile.mappale, immobile.subalterno)}
											    	
											    	<option unit_type="${immobile.desTipoUnita}" section="${immobile.sezione}" sheet="${immobile.foglio}" map="${immobile.mappale}" subordinate="${immobile.subalterno}">
										    			${immobile.desTipoUnita}
										    			${empty immobile.sezione ? "" : "\\"}
										    			${immobile.sezione}
										    			${empty immobile.foglio ? "" : "\\"}
										    			${immobile.foglio}
										    			${empty immobile.mappale ? "" : "\\"}
										    			${immobile.mappale}
										    			${empty immobile.subalterno ? "" : "\\"}
														${immobile.subalterno}
										    		</option>
									    		</c:if>
									    	</c:when >
								    	</c:choose>
								    </c:forEach>
                                </select>
                                <p class="formHint"></p>
                            </div>   

                            
                            <div class="width30 ctrlHolder" id="divSearch_operatore">
                                <label><spring:message code="ricerca.label.selected"/></label>
                                <select multiple="true" class="selected" id="selected_cadastral_data" class="width50">
				       			</select>
                                <p class="formHint"></p>
                            </div>
						</fieldset>
					
						<fieldset id="praticaField" class="floats no-margin">	
                            <legend><spring:message code="ricerca.label.indirizzoIntervento"/></legend>
                          
                             <div class="width50 ctrlHolder" id="divCadastralAddress">
                                <label><spring:message code="ricerca.label.notSelected"/></label>
                                <select id="unselected_cadastral_address" class="select textInput" multiple="true" class="width50">
                                	<c:forEach items="${pratica.indirizziInterventoList}" begin="0"  var="address" varStatus="loop">
							    		
							    		<c:choose>
									    	<c:when  test="${address.indirizzo != null or address.civico != null or address.localita != null}">
									    		<c:if test="${!distinctIndirizzi.contains(address.indirizzo, address.civico , address.localita) }">
											    	${distinctIndirizzi.add(address.indirizzo, address.civico , address.localita)}
										    		<option street="${address.indirizzo}" number="${address.civico}" town="${address.localita}">${address.indirizzo}&nbsp;${address.civico}&nbsp;${address.localita}</option>
									    		</c:if>
									    	</c:when >
								    	</c:choose>
								    </c:forEach>
                                </select>
                                <p class="formHint"></p>
                            </div>   

                            
                            <div class="width30 ctrlHolder" id="divSearch_operatore">
                                <label><spring:message code="ricerca.label.selected"/></label>
                                <select multiple="true" class="selected" id="selected_cadastral_address" class="width50">
				       			</select>
                                <p class="formHint"></p>
                            </div>
                        </fieldset>
            		</div>
           		</div>
         	</div>
        </form>
    </div>
</div>
</c:if>