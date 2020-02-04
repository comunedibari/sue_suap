<%--
Copyright (c) 2011, Regione Emilia-Romagna, Italy
 
Licensed under the EUPL, Version 1.1 or - as soon they
will be approved by the European Commission - subsequent
versions of the EUPL (the "Licence");
You may not use this work except in compliance with the
Licence.

For convenience a plain text copy of the English version
of the Licence can be found in the file LICENCE.txt in
the top-level directory of this software distribution.

You may obtain a copy of the Licence in any of 22 European
Languages at:

http://joinup.ec.europa.eu/software/page/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.

This product includes software developed by Yale University

See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<div class="text_block" align="left">
  	<table>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.nome" 
          					  fieldName="data.selAccrProfiliHelper.profiloRichiedente.nome"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloRichiedenteDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloRichiedente.nome" size="70" maxlength="100"  /></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloRichiedente.nome}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.cognome" 
          					  fieldName="data.selAccrProfiliHelper.profiloRichiedente.cognome"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloRichiedenteDefined}'> 
    	      <td><html:text property="data.selAccrProfiliHelper.profiloRichiedente.cognome" size="70" maxlength="100"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloRichiedente.cognome}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
  
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.codiceFiscale" 
          					  fieldName="data.selAccrProfiliHelper.profiloRichiedente.codiceFiscale"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloRichiedenteDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloRichiedente.codiceFiscale" size="16" maxlength="16"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloRichiedente.codiceFiscale}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.dataNascita" 
          					  fieldName="data.selAccrProfiliHelper.profiloRichiedente.dataNascitaString"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloRichiedenteDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloRichiedente.dataNascitaString" size="10" maxlength="10"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloRichiedente.dataNascitaString}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.luogoNascita" 
          					  fieldName="data.selAccrProfiliHelper.profiloRichiedente.luogoNascita"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloRichiedenteDefined or empty profiliHelper.profiloRichiedente.luogoNascita  or (not empty profiliHelper.profiloRichiedente.luogoNascita and profiliHelper.profiloRichiedente.luogoNascita eq "_")}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloRichiedente.luogoNascita" size="70" maxlength="70"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloRichiedente.luogoNascita}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.provinciaNascita" 
          					  fieldName="data.selAccrProfiliHelper.profiloRichiedente.provinciaNascita"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloRichiedenteDefined or empty profiliHelper.profiloRichiedente.provinciaNascita  or (not empty profiliHelper.profiloRichiedente.provinciaNascita and profiliHelper.profiloRichiedente.provinciaNascita eq "_")}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloRichiedente.provinciaNascita" size="20" maxlength="70"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloRichiedente.provinciaNascita}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.sesso" 
          					  fieldName="data.selAccrProfiliHelper.profiloRichiedente.sesso"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloRichiedenteDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloRichiedente.sesso" size="1" maxlength="1"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloRichiedente.sesso}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.indirizzoResidenza" 
          					  fieldName="data.selAccrProfiliHelper.profiloRichiedente.indirizzoResidenza"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloRichiedenteDefined or empty profiliHelper.profiloRichiedente.indirizzoResidenza  or (not empty profiliHelper.profiloRichiedente.indirizzoResidenza and profiliHelper.profiloRichiedente.indirizzoResidenza eq "_")}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloRichiedente.indirizzoResidenza" size="70" maxlength="70"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloRichiedente.indirizzoResidenza}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
       <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.domicilioElettronico" 
          					  fieldName="data.selAccrProfiliHelper.profiloRichiedente.domicilioElettronico"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloRichiedenteDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloRichiedente.domicilioElettronico" size="70" maxlength="70"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloRichiedente.domicilioElettronico}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
   </table>

</div>
