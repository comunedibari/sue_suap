   <div class="text_block" align="left">
    <table>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaGiuridica.codiceFiscale" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.codiceFiscale"/></td>
          <td><c:out value="${accrSelected.profilo.codiceFiscaleIntermediario}" /></td>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaGiuridica.partitaIva" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.partitaIva"/></td>
          <td><c:out value="${accrSelected.profilo.partitaIvaIntermediario}" /></td>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaGiuridica.denominazione" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.denominazione"/></td>
          <td><c:out value="${accrSelected.profilo.denominazione}" /></td>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaGiuridica.sedeLegale" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.sedeLegale"/></td>
          <td><c:out value="${accrSelected.profilo.sedeLegale}" /></td>
      </tr>
    </table>
	</div>
  <div class="text_block" align="left">
      <bean:message key="label.personaGiuridica.titoloRapprLegale" />
  </div>

  <div class="text_block" align="left">
  	<table>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.nome" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.nome"/></td>
          <td><c:out value="${accrSelected.profilo.rappresentanteLegale.nome}" /></td>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.cognome" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.cognome"/></td>
          <td><c:out value="${accrSelected.profilo.rappresentanteLegale.cognome}" /></td>
      </tr>
  
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.codiceFiscale" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.codiceFiscale"/></td>
	          <td><c:out value="${accrSelected.profilo.rappresentanteLegale.codiceFiscale}" /></td>
      </tr>
      <%--
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.dataNascita" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.dataNascitaString"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.dataNascitaString" size="10" maxlength="10"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.rappresentanteLegale.dataNascitaString}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.luogoNascita" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.luogoNascita"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.luogoNascita" size="70" maxlength="70"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.rappresentanteLegale.luogoNascita}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.provinciaNascita" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.provinciaNascita"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.provinciaNascita" size="20" maxlength="70"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.rappresentanteLegale.provinciaNascita}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.sesso" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.sesso"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.sesso" size="1" maxlength="1"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.rappresentanteLegale.sesso}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.indirizzoResidenza" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.indirizzoResidenza"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.indirizzoResidenza" size="70" maxlength="70"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.rappresentanteLegale.indirizzoResidenza}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      --%>
  	</table>
  </div>
</div>
