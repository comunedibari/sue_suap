<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String url = path + "/pratica/anagrafe_tributaria_edilizia/submit.htm";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script>
    $(document).ready(function() {

        $("#dataInizioLavori").datepicker({
            dateFormat: 'dd/mm/yy'
        });

        $("#dataFineLavori").datepicker({
            dateFormat: 'dd/mm/yy'
        });

    });
</script>
<tiles:insertAttribute name="body_error" />
<form:form modelAttribute="anagrafeTributaria" action="<%=url%>"  id="anagrafeTributaria" method="post" cssClass="uniForm inlineLabels comunicazione">

    <h2 style="text-align: center">${titoloPagina}</h2>
    <form:hidden path="idPratica"/>
    <form:hidden path="idEvento" />
    <form:hidden path="idDettaglio" />
    <form:hidden path="codFornitura" />

    <div>
        <div  class="uniForm ">

            <div class="sidebar_auto">
                <div class="page-control" data-role="page-control">
                    <span class="menu-pull"></span> 
                    <div class="menu-pull-bar"></div>
                    <!-- Etichette cartelle -->
                    <ul>
                        <li class="active"><a href="#frame1">Richiesta</a></li>
                        <li><a href="#frame2">Dati catastali</a></li>
                        <li><a href="#frame3">Richiedente</a></li>
                        <li><a href="#frame4">Beneficiari</a></li>
                        <li><a href="#frame5">Professionista</a></li>
                        <li><a href="#frame6">Imprese</a></li>
                    </ul>
                    <!-- Contenuto cartelle -->
                    <div class="frames">
                        <div class="frame active" id="frame1">    
                            <div class="inlineLabels">
                                <fieldset class="fieldsetzero">
                                    <div class="ctrlHolder dettaglio_liv_0">
                                        <label class="required" for="tipoRichiesta">Tipo richiesta</label>
                                        <form:select id="tipoRichiesta" path="tipoRichiesta" >
                                            <form:options items="${tipoRichiesta}" itemLabel="itemLabel" itemValue="itemValue"/>
                                        </form:select>
                                    </div>

                                    <div class="ctrlHolder dettaglio_liv_0">
                                        <label class="required" for="tipoIntervento">Tipologia intervento</label>
                                        <form:select id="tipoIntervento"  path="tipoIntervento">
                                            <form:options items="${tipoIntervento}" itemLabel="itemLabel" itemValue="itemValue"/>
                                        </form:select>
                                    </div>

                                    <div class="ctrlHolder dettaglio_liv_0">
                                        <label class="required" for="tipologiaRichiesta">Tipologia richiesta</label>
                                        <form:select id="tipologiaRichiesta" path="tipologiaRichiesta" >
                                            <form:options items="${tipologiaRichiesta}" itemLabel="itemLabel" itemValue="itemValue"/>
                                        </form:select>
                                        <p class="formHint">Si considerano atti di cessazione: revoca, abrogazione, ritiro, annullamento, pronuncia di decadenza, diniego di rinnovo o di proroga, rinuncia ed estinzione</p>
                                    </div>

                                    <div class="ctrlHolder">
                                        <label class="required" for="dataInizioLavori">Data inizio lavori</label>
                                        <form:input path="dataInizioLavori" id="dataInizioLavori" maxlength="10" cssClass="textInput required"/>
                                        <p class="formHint"></p>
                                    </div>

                                    <div class="ctrlHolder">
                                        <label class="required" for="dataFineLavori">Data fine lavori</label>
                                        <form:input path="dataFineLavori" id="dataFineLavori" maxlength="10" cssClass="textInput required"/>
                                        <p class="formHint"></p>
                                    </div>

                                    <div class="ctrlHolder dettaglio_liv_0">
                                        <label class="required" for="idIndirizzo">Indirizzo</label>
                                        <c:choose>
                                            <c:when test="${empty indirizziRichiesta}">
                                                Non ci sono indirizzi associati alla pratica
                                            </c:when>
                                            <c:otherwise>
                                                <select name="indirizzo.idRecapito" id="idIndirizzo">
                                                    <c:forEach items="${indirizziRichiesta}" var="indRich">   
                                                        <option value="${indRich.idRecapito}" <c:if test="${indRich.idRecapito == indirizzo.idRecapito}">selected="selected"</c:if>>${indRich.descrizione}</option>
                                                    </c:forEach>
                                                </select>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </fieldset>
                            </div>
                        </div>  
                        <div class="frame" id="frame2">  
                            <div class="inlineLabels">

                                <fieldset id="datiCatastaliFieldset" class="fieldsetzero">
                                    <c:choose>
                                        <c:when test="${empty anagrafeTributaria.datiCatastali}">
                                            <div>
                                                Non sono presenti dati catastali sulla pratica
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="datiCatastaliCounter" value="0" scope="page" />
                                            <c:forEach items="${anagrafeTributaria.datiCatastali}" var="datiCatastali" begin="0"> 
                                                <div class="datiCatastali" id="datoCatastale_${datiCatastaliCounter}">
                                                    <c:if test="${datiCatastaliCounter > 0}">
                                                        <hr />
                                                    </c:if>
                                                    <div class="ctrlHolder dettaglio_liv_0">
                                                        <label class="required" for="desTipoCatasto_${datiCatastaliCounter}">Tipo catasto</label>
                                                        <select name="datiCatastali[${datiCatastaliCounter}].desTipoCatasto" id="desTipoCatasto_${datiCatastaliCounter}">
                                                            <c:forEach items="${tipologiaCatasto}" var="tipo">   
                                                                <option value="${tipo.itemValue}" <c:if test="${tipo.itemValue == datiCatastali.desTipoCatasto}">selected="selected"</c:if>>${tipo.itemLabel}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                                    <div class="ctrlHolder dettaglio_liv_0">
                                                        <label class="required" for="tipoUnita_${datiCatastaliCounter}">Tipo unita</label>

                                                        <select name="datiCatastali[${datiCatastaliCounter}].idTipoUnita" id="tipoUnita_${datiCatastaliCounter}">
                                                            <c:forEach items="${tipoUnita}" var="unita">   
                                                                <option value="${unita.itemValue}" <c:if test="${unita.itemValue == datiCatastali.idTipoUnita}">selected="selected"</c:if>>${unita.itemLabel}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                                    <div class="ctrlHolder">
                                                        <label class="required" for="sezione_${datiCatastaliCounter}">Sezione</label>
                                                        <input type="text" name="datiCatastali[${datiCatastaliCounter}].sezione" value="${datiCatastali.sezione}" id="sezione_${datiCatastaliCounter}" maxlength="3" class="textInput required" />
                                                        <p class="formHint"></p>
                                                    </div>

                                                    <div class="ctrlHolder">
                                                        <label class="required" for="foglio_${datiCatastaliCounter}">Foglio</label>
                                                        <input type="text" name="datiCatastali[${datiCatastaliCounter}].foglio" id="foglio{datiCatastaliCounter}" value="${datiCatastali.foglio}" maxlength="5" class="textInput required" />
                                                        <p class="formHint"></p>
                                                    </div>

                                                    <div class="ctrlHolder">
                                                        <label class="required" for="particella_${datiCatastaliCounter}">Particella</label>
                                                        <input type="text" name="datiCatastali[${datiCatastaliCounter}].particella" id="particella{datiCatastaliCounter}" value="${datiCatastali.particella}" maxlength="5" class="textInput required" />
                                                        <p class="formHint"></p>
                                                    </div>

                                                    <div class="ctrlHolder">
                                                        <label class="required" for="estensioneParticella_${datiCatastaliCounter}">Estensione particella</label>
                                                        <input type="text" name="datiCatastali[${datiCatastaliCounter}].estensioneParticella" id="estensioneParticella_{datiCatastaliCounter}" value="${datiCatastali.estensioneParticella}" maxlength="4" class="textInput required" />
                                                        <p class="formHint"></p>
                                                    </div> 

                                                    <div class="ctrlHolder dettaglio_liv_0">
                                                        <label class="required" for="tipologiaParticella_${datiCatastaliCounter}">Tipo particella</label>
                                                        <select name="datiCatastali[${datiCatastaliCounter}].idTipologiaParticella" id="tipologiaParticella_${datiCatastaliCounter}">
                                                            <option value=""></option>
                                                            <c:forEach items="${tipologiaParticella}" var="tipo">   
                                                                <option value="${tipo.itemValue}" <c:if test="${tipo.itemValue == datiCatastali.idTipologiaParticella}">selected="selected"</c:if>>${tipo.itemLabel}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                                    <div class="ctrlHolder">
                                                        <label class="required" for="subalterno_${datiCatastaliCounter}">Subalterno</label>
                                                        <input type="text" name="datiCatastali[${datiCatastaliCounter}].subalterno" id="subalterno_${datiCatastaliCounter}" maxlength="4" class="textInput required" value="${datiCatastali.subalterno}"/>
                                                        <p class="formHint"></p>
                                                    </div>
                                                </div>
                                                <c:set var="datiCatastaliCounter" value="${datiCatastaliCounter + 1}" scope="page"/>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </fieldset>
                            </div>
                        </div>  

                        <div class="frame" id="frame3">  
                            <div class="inlineLabels">
                                <c:set var="richiedentiCounter" value="0" scope="page" />
                                <c:forEach items="${anagrafeTributaria.richiedenti}" var="r" begin="0"> 
                                    <c:if test="${richiedentiCounter > 0}">
                                        <hr />
                                    </c:if>
                                    <div class="ctrlHolder dettaglio_liv_0">
                                        <label class="required">Nome richiedente</label>
                                        <input type="text" readonly="readonly" value="${r.descrizione}" class="textInput required"/>
                                        <input type="hidden" name="richiedenti[${richiedentiCounter}].idAnagrafica" value="${r.idAnagrafica}" />
                                        <input type="hidden" name="richiedenti[${richiedentiCounter}].descrizione" value="${r.descrizione}" />
                                    </div>

                                    <div class="ctrlHolder dettaglio_liv_0">
                                        <label class="required" for="idQualificaRichiedente_${richiedentiCounter}">Qualifica del richiedente</label>
                                        <select name="richiedenti[${richiedentiCounter}].idQualifica" id="idQualificaRichiedente_${richiedentiCounter}">
                                            <c:forEach items="${qualifiche}" var="qualifica">   
                                                <option value="${qualifica.codice}" <c:if test="${qualifica.codice == r.idQualifica}">selected="selected"</c:if>>${qualifica.descrizione}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <c:set var="richiedentiCounter" value="${richiedentiCounter + 1}" scope="page"/>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="frame" id="frame4">  
                            <div class="inlineLabels">
                                <fieldset id="beneficiariFieldset" class="fieldsetzero">
                                    <c:choose>
                                        <c:when test="${empty anagrafeTributaria.beneficiari}">
                                            <p>Non sono stati individuati beneficairi</p>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="beneficiariCounter" value="0" scope="page" />
                                            <c:forEach items="${anagrafeTributaria.beneficiari}" var="beneficiarioIndex" begin="0"> 
                                                <div class="beneficiari" id="beneficiario_${beneficiariCounter}">
                                                    <c:if test="${beneficiariCounter > 0}">
                                                        <hr />
                                                    </c:if>
                                                    <div class="ctrlHolder dettaglio_liv_0">
                                                        <label class="required" for="idAnagraficaBeneficiario_${beneficiariCounter}">Beneficiario</label>
                                                        <input type="text" readonly="readonly" value="${beneficiarioIndex.descrizione}" id="idAnagraficaBeneficiario_${beneficiariCounter}" class="textInput required"/>
                                                        <input type="hidden" name="beneficiari[${beneficiariCounter}].idAnagrafica" value="${beneficiarioIndex.idAnagrafica}"/>
                                                        <input type="hidden" name="beneficiari[${beneficiariCounter}].descrizione" value="${beneficiarioIndex.descrizione}"/>
                                                    </div>

                                                    <div class="ctrlHolder dettaglio_liv_0">
                                                        <label class="required" for="qualificaBeneficiario_${beneficiariCounter}">Qualifica beneficiario</label>
                                                        <select name="beneficiari[${beneficiariCounter}].idQualifica" id="qualificaBeneficiario_${beneficiariCounter}">
                                                            <c:forEach items="${qualifiche}" var="qualifica">   
                                                                <option value="${qualifica.codice}" <c:if test="${qualifica.codice == beneficiarioIndex.idQualifica}">selected="selected"</c:if>>${qualifica.descrizione}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <c:set var="beneficiariCounter" value="${beneficiariCounter + 1}" scope="page"/>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </fieldset>
                            </div>
                        </div>  

                        <div class="frame" id="frame5">  
                            <div class="inlineLabels">

                                <fieldset id="professionistiFieldset" class="fieldsetzero">
                                    <c:choose>
                                        <c:when test="${empty anagrafeTributaria.professionisti}">
                                            Non ci sono professionisti
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="professionistiCounter" value="0" scope="page" />
                                            <c:forEach items="${anagrafeTributaria.professionisti}" var="professionistiIndex" begin="0"> 
                                                <div class="professionisti" id="professionista_${professionistiCounter}">
                                                    <c:if test="${professionistiCounter > 0}">
                                                        <hr />
                                                    </c:if>
                                                    <div class="ctrlHolder dettaglio_liv_0">
                                                        <label class="required" for="idAnagraficaProfessionista_${professionistiCounter}">Professionista</label>
                                                        <input type="text" readonly="readonly" value="${professionistiIndex.descrizione}" id="idAnagraficaProfessionista_${professionistiCounter}" class="textInput required"/>
                                                        <input type="hidden" name="professionisti[${professionistiCounter}].idAnagrafica" value="${professionistiIndex.idAnagrafica}"/>
                                                        <input type="hidden" name="professionisti[${professionistiCounter}].descrizione" value="${professionistiIndex.descrizione}"/>
                                                    </div>

                                                    <div class="ctrlHolder dettaglio_liv_0">
                                                        <label class="required" for="qualificaProfessionisti_${professionistiCounter}">Qualifica professionista</label>
                                                        <select name="professionisti[${professionistiCounter}].idQualifica" id="qualificaProfessionisti_${professionistiCounter}">
                                                            <c:forEach items="${qualificaProfessionista}" var="qualifica">   
                                                                <option value="${qualifica.codice}" <c:if test="${qualifica.codice == professionistiIndex.idQualifica}">selected="selected"</c:if>>${qualifica.descrizione}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>                                                        

                                                    <div class="ctrlHolder dettaglio_liv_0">
                                                        <label class="required" for="alboProfessionale_${professionistiCounter}">Albo professionale</label>
                                                        <select name="professionisti[${professionistiCounter}].idTipoCollegio" id="alboProfessionale_${professionistiCounter}">
                                                            <c:forEach items="${albiProfessionali}" var="albo">   
                                                                <option value="${albo.itemValue}" <c:if test="${albo.itemValue == professionistiIndex.idTipoCollegio}">selected="selected"</c:if>>${albo.itemLabel}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                                    <div class="ctrlHolder">
                                                        <label class="required" for="numeroIscrizioneAlbo_${professionistiCounter}">Numero iscrizione albo</label>
                                                        <input type="text" name="professionisti[${professionistiCounter}].numeroIscrizioneAlbo" maxlength="10" value="${professionistiIndex.numeroIscrizioneAlbo}" id="numeroIscrizioneAlbo_${professionistiCounter}" class="textInput required"/>
                                                    </div>

                                                    <div class="ctrlHolder dettaglio_liv_0">
                                                        <label class="required" for="provinciaAlbo_${professionistiCounter}">Provincia albo</label>
                                                        <select name="professionisti[${professionistiCounter}].codProvinciaIscrizione" id="provinciaAlbo_${professionistiCounter}">
                                                            <c:forEach items="${provinceAlbo}" var="prov">   
                                                                <option value="${prov.itemValue}" <c:if test="${prov.itemValue == professionistiIndex.codProvinciaIscrizione}">selected="selected"</c:if>>${prov.itemLabel}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <c:set var="professionistiCounter" value="${professionistiCounter + 1}" scope="page"/>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </fieldset>
                            </div>
                        </div>  

                        <div class="frame" id="frame6">  
                            <div class="inlineLabels">
                                <fieldset id="impreseFieldset" class="fieldsetzero">
                                    <c:choose>
                                        <c:when test="${empty anagrafeTributaria.imprese}">
                                            Non sono state individuate imprese
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="impreseCounter" value="0" scope="page" />
                                            <c:forEach items="${anagrafeTributaria.imprese}" var="impresaIndex" begin="0"> 
                                                <div class="imprese" id="impresa_${impreseCounter}">
                                                    <c:if test="${impreseCounter > 0}">
                                                        <hr />
                                                    </c:if>
                                                    <div class="ctrlHolder dettaglio_liv_0">
                                                        <label class="required" for="idAnagraficaImpresa_${impreseCounter}">Impresa</label>
                                                        <input type="text" readonly="readonly" value="${impresaIndex.descrizione}" id="idAnagraficaImpresa_${impreseCounter}" class="textInput required"/>
                                                        <input type="hidden" name="imprese[${impreseCounter}].idAnagrafica" value="${impresaIndex.idAnagrafica}"/>
                                                        <input type="hidden" name="imprese[${impreseCounter}].descrizione" value="${impresaIndex.descrizione}"/>
                                                    </div>
                                                    <div class="ctrlHolder dettaglio_liv_0">
                                                        <label class="required" for="recapitoSedeLegale_${impreseCounter}">Sede legale</label>
                                                        <input type="text" readonly="readonly" value="${impresaIndex.descrizioneRecapito}" id="recapitoSedeLegale_${impreseCounter}" class="textInput required"/>
                                                        <input type="hidden" name="imprese[${impreseCounter}].idRecapito" value="${impresaIndex.idRecapito}"/>
                                                        <input type="hidden" name="imprese[${impreseCounter}].descrizioneRecapito" value="${impresaIndex.descrizioneRecapito}"/>
                                                    </div>
                                                </div>
                                                <c:set var="impreseCounter" value="${impreseCounter + 1}" scope="page"/>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </fieldset>
                            </div>
                        </div>
                        <!-- fine cartelle -->
                    </div>
                    <div class="clear"></div>    
                </div>

                <div class="buttonHolder">
                    <a href="<%=path%>/pratica/evento/index.htm" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
                    <button type="submit" class="primaryAction"><spring:message code="pratica.comunicazione.evento.crea"/></button>
                </div>
            </div>
        </div>
    </form:form>