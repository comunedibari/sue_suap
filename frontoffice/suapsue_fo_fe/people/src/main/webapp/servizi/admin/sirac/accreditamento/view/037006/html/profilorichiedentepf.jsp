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
          	<c:when test='${not profiliHelper.profiloRichiedenteDefined}'> 
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
          	<c:when test='${not profiliHelper.profiloRichiedenteDefined}'> 
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
          	<c:when test='${not profiliHelper.profiloRichiedenteDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloRichiedente.indirizzoResidenza" size="70" maxlength="70"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloRichiedente.indirizzoResidenza}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <%--
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
       --%>
   </table>

</div>
