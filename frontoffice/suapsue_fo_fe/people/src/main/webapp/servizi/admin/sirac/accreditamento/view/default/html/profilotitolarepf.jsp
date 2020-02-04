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
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.nome"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.nome" size="70" maxlength="100" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.nome}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.cognome" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.cognome"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
    	      <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.cognome" size="70" maxlength="100" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.cognome}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
  
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.codiceFiscale" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.codiceFiscale"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.codiceFiscale" size="16" maxlength="16" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.codiceFiscale}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.dataNascita" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.dataNascitaString"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.dataNascitaString" size="10" maxlength="10" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.dataNascitaString}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.luogoNascita" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.luogoNascita"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined or empty profiliHelper.profiloTitolare.luogoNascita  or (not empty profiliHelper.profiloTitolare.luogoNascita and profiliHelper.profiloTitolare.luogoNascita eq "_")}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.luogoNascita" size="70" maxlength="70" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.luogoNascita}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.provinciaNascita" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.provinciaNascita"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined or empty profiliHelper.profiloTitolare.provinciaNascita  or (not empty profiliHelper.profiloTitolare.provinciaNascita and profiliHelper.profiloTitolare.provinciaNascita eq "_")}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.provinciaNascita" size="20" maxlength="70" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.provinciaNascita}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.sesso" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.sesso"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.sesso" size="1" maxlength="1" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.sesso}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.indirizzoResidenza" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.indirizzoResidenza"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined or empty profiliHelper.profiloTitolare.indirizzoResidenza  or (not empty profiliHelper.profiloTitolare.indirizzoResidenza and profiliHelper.profiloTitolare.indirizzoResidenza eq "_")}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.indirizzoResidenza" size="70" maxlength="70" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.indirizzoResidenza}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.domicilioElettronico" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.domicilioElettronico"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.domicilioElettronico" size="70" maxlength="150" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.domicilioElettronico}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
  	</table>

</div>

