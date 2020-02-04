   <div class="text_block" align="left">
    <table>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaGiuridica.codiceFiscale" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.codiceFiscale"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
              <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.codiceFiscale" size="11" maxlength="11" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
            </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.codiceFiscale}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaGiuridica.partitaIva" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.partitaIva"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
              <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.partitaIva" size="11" maxlength="11" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
            </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.partitaIva}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaGiuridica.denominazione" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.denominazione"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
              <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.denominazione" size="70" maxlength="100" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
            </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.denominazione}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaGiuridica.sedeLegale" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.sedeLegale"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
              <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.sedeLegale" size="70" maxlength="100" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
            </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.sedeLegale}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <%--
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaGiuridica.descrizione" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.descrizione"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
              <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.descrizione" size="70" maxlength="255"/></td>
            </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.descrizione}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaGiuridica.domicilioElettronico" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.domicilioElettronico"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
              <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.domicilioElettronico" size="70" maxlength="100"/></td>
            </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.domicilioElettronico}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      --%>
    </table>


</div>
    <div class="text_block" align="left">
      <h2>
        <bean:message key="label.personaGiuridica.titoloRapprLegale" />
      </h2>
    </div>

  <div class="text_block" align="left">
  	<table>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.nome" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.nome"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.nome" size="70" maxlength="100"  disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.rappresentanteLegale.nome}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.cognome" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.cognome"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
    	      <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.cognome" size="70" maxlength="100" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.rappresentanteLegale.cognome}" /></td>
	        </c:otherwise>
          </c:choose>
      </tr>
  
      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.codiceFiscale" 
          					  fieldName="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.codiceFiscale"/></td>
          <c:choose>
          	<c:when test='${not profiliHelper.profiloTitolareDefined}'> 
	          <td><html:text property="data.selAccrProfiliHelper.profiloTitolare.rappresentanteLegale.codiceFiscale" size="20" maxlength="20" disabled="<%=!myData.getEnableModifyTitolare()%>"/></td>
	        </c:when>
	        <c:otherwise>
	          <td><c:out value="${profiliHelper.profiloTitolare.rappresentanteLegale.codiceFiscale}" /></td>
	        </c:otherwise>
          </c:choose>
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
