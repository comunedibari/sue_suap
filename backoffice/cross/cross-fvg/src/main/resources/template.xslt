<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns="http://www.wego.it/cross" 
    xmlns:mand="http://www.insiel.it/gestioneDocumentale/FEG/common/managedData" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:ist="http://www.insiel.it/masterdata/mdap/istanze" 
    xmlns:mdap="http://www.insiel.it/masterdata/mdap" 
    xmlns:mdas="http://www.insiel.it/masterdata/mdas" 
    xmlns:mdet="http://www.insiel.it/masterdata/mdet" 
    xmlns:mdete="http://www.insiel.it/masterdata/mdet-ext" 
    xmlns:spar="http://www.insiel.it/masterdata/mdap/soggettiPartecipanti" 
    version="1.0">
	<xsl:variable name="srl">SOCIETA' A RESPONSABILITA' LIMITATA</xsl:variable>
	<xsl:variable name="ruoloProcuratore">delegato_procuratore</xsl:variable>
	<xsl:variable name="impresaIndividuale">IMPRESA INDIVIDUALE</xsl:variable>
	<xsl:template match="/">
		<xsl:variable name="presenzaProcuratore">
			<xsl:for-each select="/mand:managedData/mand:datoReferenziabile[@mand:nomeLogicoDato='feg_soggettiPartecipanti']/spar:elencoSoggettiPartecipanti/spar:soggettoPartecipante">
				<xsl:if test="./@spar:ruolo = $ruoloProcuratore">
					<xsl:value-of select="$ruoloProcuratore"/>
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>
		<pratica xmlns="http://www.wego.it/cross" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.wego.it/cross http://dev.wego.it/svn/WegoRepository/Maven/cross2/trunk/cross-plugins/cross-xml/src/main/resources/cross.xsd">
			<id_procedimento_suap>1</id_procedimento_suap>
			<identificativo_pratica/>
			<oggetto>
				<xsl:for-each select="/mand:managedData/mand:datoReferenziabile/ist:parteGenerale/ist:procedimento">
					<xsl:value-of select="./mdap:procedimentoBase/mdap:titolo"/>
					<xsl:value-of select="';'"/>
				</xsl:for-each>
			</oggetto>
			<responsabile_procedimento/>
			<istruttore/>
			<cod_catastale_comune/>
			<des_comune/>
			<id_ente/>
			<cod_ente/>
			<des_ente/>
			<des_comune_ente/>
			<indirizzo_ente/>
			<cap_ente/>
			<fax_ente/>
			<email_ente/>
			<pec_ente/>
			<telefono_ente/>
			<registro/>
			<protocollo/>
			<fascicolo/>
			<anno/>
			<xsl:for-each select="/mand:managedData/mand:datoReferenziabile/ist:parteGenerale/ist:listaSoggettiInteressati/ist:interessato">
				<xsl:variable name="isImpresaIndividuale" select="./ist:richiedenteFormaGiuridica/mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica='IMPRESA INDIVIDUALE'"/>
				<xsl:for-each select="./ist:richiedenteFormaGiuridica">
					<anagrafiche>
						<anagrafica>
							<counter>
								<xsl:value-of select="position()-1"/>
							</counter>
							<confermata/>
							<daRubrica/>
							<id_anagrafica/>
							<tipo_anagrafica>
								<xsl:choose>
									<xsl:when test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">F</xsl:when>
									<xsl:otherwise>G</xsl:otherwise>
								</xsl:choose>
							</tipo_anagrafica>
							<variante_anagrafica>
								<xsl:choose>
									<xsl:when test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">I</xsl:when>
									<xsl:otherwise>G</xsl:otherwise>
								</xsl:choose>
							</variante_anagrafica>
							<cognome>
								<xsl:if test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">
									<xsl:value-of select="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:personaFisicaBase/mdas:cognome"/>
								</xsl:if>
							</cognome>
							<denominazione>
								<xsl:value-of select="./mdas:figuraGiuridicaBase/mdas:denominazione"/>
							</denominazione>
							<nome>
								<xsl:if test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">
									<xsl:value-of select="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:personaFisicaBase/mdas:nome"/>
								</xsl:if>
							</nome>
							<codice_fiscale>
								<xsl:value-of select="./mdas:figuraGiuridicaBase/mdas:codiceFiscale"/>
							</codice_fiscale>
							<partita_iva>
								<xsl:if test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica != $impresaIndividuale">
									<xsl:value-of select="./mdas:figuraGiuridicaBase/mdas:codiceFiscale"/>
								</xsl:if>
								<xsl:if test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">
									<xsl:value-of select="./mdas:figuraGiuridicaBase/mdas:partitaIva"/>
								</xsl:if>
							</partita_iva>
							<flg_individuale>
								<xsl:choose>
									<xsl:when test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">S</xsl:when>
									<xsl:otherwise>N</xsl:otherwise>
								</xsl:choose>
							</flg_individuale>
							<data_nascita>
								<xsl:if test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">
									<xsl:call-template name="dateFormatterEngToIt">
										<xsl:with-param name="dataInglese" select="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:dataNascita"/>
									</xsl:call-template>
								</xsl:if>
							</data_nascita>
							<id_cittadinanza/>
							<cod_cittadinanza>
								<xsl:if test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">
									<xsl:value-of select="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:cittadinanza/mdet:id"/>
								</xsl:if>
							</cod_cittadinanza>
							<des_cittadinanza>
								<xsl:if test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">
									<xsl:value-of select="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:cittadinanza/mdet:denominazione"/>
								</xsl:if>
							</des_cittadinanza>
							<id_nazionalita/>
							<cod_nazionalita/>
							<des_nazionalita/>
							<id_comune_nascita/>
							<des_comune_nascita>
								<xsl:if test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">
									<xsl:value-of select="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:comuneItaliano/mdet:denominazioneComune"/>
									<xsl:value-of select="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera/mdet:stato/mdet:denominazione"/>
								</xsl:if>
							</des_comune_nascita>
							<id_provincia_nascita/>
							<des_provincia_nascita>
								<xsl:if test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">
									<xsl:value-of select="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:comuneItaliano/mdet:provincia/mdet:denominazioneProvincia"/>
									<xsl:if test="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera">Stato estero</xsl:if>
								</xsl:if>
							</des_provincia_nascita>
							<xsl:choose>
								<xsl:when test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">
									<xsl:if test="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:comuneItaliano">
										<id_stato_nascita>1</id_stato_nascita>
										<des_stato_nascita>ITALIA</des_stato_nascita>
									</xsl:if>
									<xsl:if test="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera">
										<id_stato_nascita>
											<xsl:value-of select="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera/mdet:stato/mdet:id"/>
										</id_stato_nascita>
										<des_stato_nascita>
											<xsl:value-of select="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera/mdet:stato/mdet:denominazione"/>
										</des_stato_nascita>
									</xsl:if>
								</xsl:when>
								<xsl:otherwise>
									<id_stato_nascita/>
									<des_stato_nascita/>
								</xsl:otherwise>
							</xsl:choose>
							<localita_nascita>
								<xsl:if test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">
									<xsl:if test="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera">
										<xsl:value-of select="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera//mdet:denominazioneCittaEstera"/>
									</xsl:if>
								</xsl:if>
							</localita_nascita>
							<xsl:choose>
								<xsl:when test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">
									<sesso>
										<xsl:value-of select="./mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica/mdas:sesso"/>
									</sesso>
								</xsl:when>
								<xsl:otherwise>
									<sesso/>
								</xsl:otherwise>
							</xsl:choose>
							<des_tipo_collegio/>
							<numero_iscrizione/>
							<des_provincia_iscrizione/>
							<cod_provincia_cciaa/>
							<des_provincia_cciaa>
								<xsl:value-of select="./mdas:sedeLegale/mdas:iscrizioneREA/mdas:provinciaCCIAA"/>
							</des_provincia_cciaa>
							<flg_attesa_iscrizione_ri>
								<xsl:choose>
									<xsl:when test="./mdas:iscrizioneRI/mdas:numeroRI">false</xsl:when>
									<xsl:otherwise>true</xsl:otherwise>
								</xsl:choose>
							</flg_attesa_iscrizione_ri>
							<flg_obbligo_iscrizione_ri>false</flg_obbligo_iscrizione_ri>
							<n_iscrizione_ri>
								<xsl:value-of select="./mdas:iscrizioneRI/mdas:numeroRI"/>
							</n_iscrizione_ri>
							<data_iscrizione_ri>
								<xsl:call-template name="dateFormatterEngToIt">
									<xsl:with-param name="dataInglese" select="./mdas:iscrizioneRI/mdas:dataIscrizioneRI"/>
								</xsl:call-template>
							</data_iscrizione_ri>
							<flg_attesa_iscrizione_rea>
								<xsl:choose>
									<xsl:when test="./mdas:sedeLegale/mdas:iscrizioneREA">false</xsl:when>
									<xsl:otherwise>true</xsl:otherwise>
								</xsl:choose>
							</flg_attesa_iscrizione_rea>
							<n_iscrizione_rea>
								<xsl:value-of select="./mdas:sedeLegale/mdas:iscrizioneREA/mdas:numeroREA"/>
							</n_iscrizione_rea>
							<xsl:choose>
								<xsl:when test="./mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $srl">
									<id_forma_giuridica>11</id_forma_giuridica>
									<des_forma_giuridica>Societࡡ responsabilit࡬imitata</des_forma_giuridica>
								</xsl:when>
								<xsl:otherwise>
									<id_forma_giuridica>5</id_forma_giuridica>
									<des_forma_giuridica>Societࡳemplice</des_forma_giuridica>
								</xsl:otherwise>
							</xsl:choose>
							<recapiti>
								<xsl:for-each select="./mdas:sedeLegale">
									<recapito>
										<id_recapito/>
										<id_comune/>
										<des_comune>
											<xsl:value-of select="./mdas:indirizzo/mdet:luogo/mdet:comuneItaliano/mdet:denominazioneComune"/>
										</des_comune>
										<id_provincia/>
										<des_provincia>
											<xsl:value-of select="./mdas:indirizzo/mdet:luogo/mdet:comuneItaliano/mdet:provincia/mdet:denominazioneProvincia"/>
										</des_provincia>
										<xsl:if test="./mdas:indirizzo/mdet:luogo/mdet:comuneItaliano">
											<id_stato>1</id_stato>
											<des_stato>ITALIA</des_stato>
										</xsl:if>
										<xsl:if test="./mdas:indirizzo/mdet:luogo/mdet:cittaEstera">
											<id_stato>
												<xsl:value-of select="./mdas:indirizzo/mdet:luogo/mdet:cittaEstera/mdet:stato/mdet:id"/>
											</id_stato>
											<des_stato>
												<xsl:value-of select="./mdas:indirizzo/mdet:luogo/mdet:cittaEstera/mdet:stato/mdet:denominazione"/>
											</des_stato>
										</xsl:if>
										<indirizzo>
											<xsl:value-of select="./mdas:indirizzo/mdet:tipoIndirizzo"/>
											<xsl:value-of select="' '"/>
											<xsl:value-of select="./mdas:indirizzo/mdet:indirizzo"/>
										</indirizzo>
										<codice_via/>
										<n_civico>
											<xsl:value-of select="./mdas:indirizzo/mdet:numeroCivico"/>
										</n_civico>
										<codice_civico/>
										<interno_numero/>
										<interno_lettera/>
										<interno_scala/>
										<lettera/>
										<colore/>
										<cap>
											<xsl:value-of select="./mdas:indirizzo/mdet:cap"/>
										</cap>
										<altre_info_indirizzo/>
										<telefono>
											<xsl:value-of select="./mdas:listaRiferimenti/mdas:riferimento[mdas:tipoRiferimento='Telefono principale']/mdas:valoreRiferimento"/>
										</telefono>
										<cellulare/>
										<email/>
										<pec>
											<xsl:value-of select="./mdas:listaRiferimenti/mdas:riferimento[mdas:tipoRiferimento='PEC']/mdas:valoreRiferimento"/>
										</pec>
										<xsl:choose>
											<xsl:when test="../mdas:figuraGiuridicaBase/mdas:tipoFiguraGiuridica = $impresaIndividuale">
												<id_tipo_indirizzo>1</id_tipo_indirizzo>
												<des_tipo_indirizzo>RESIDENZA</des_tipo_indirizzo>
											</xsl:when>
											<xsl:otherwise>
												<id_tipo_indirizzo>3</id_tipo_indirizzo>
												<des_tipo_indirizzo>SEDE</des_tipo_indirizzo>
											</xsl:otherwise>
										</xsl:choose>
									</recapito>
								</xsl:for-each>
							</recapiti>
						</anagrafica>
						<id_tipo_ruolo>2</id_tipo_ruolo>
						<cod_tipo_ruolo>B</cod_tipo_ruolo>
						<des_tipo_ruolo>Beneficiario</des_tipo_ruolo>
					</anagrafiche>
				</xsl:for-each>
				<xsl:for-each select="/mand:managedData/mand:datoReferenziabile/ist:parteGenerale/ist:listaSoggettiInteressati/ist:interessato/ist:richiedenteFormaGiuridica/mdas:listaCaricaPersona">
					<xsl:if test="./mdas:caricaPersona/mdas:listaCariche/mdas:carica[mdas:tipologia='Legale rappresentante']">
						<anagrafiche>
							<anagrafica>
								<counter>
									<xsl:value-of select="position()-1 + 5"/>
								</counter>
								<confermata/>
								<daRubrica/>
								<id_anagrafica/>
								<tipo_anagrafica>F</tipo_anagrafica>
								<variante_anagrafica>F</variante_anagrafica>
								<cognome>
									<xsl:value-of select="./mdas:caricaPersona/mdas:personaFisica/mdas:personaFisicaBase/mdas:cognome"/>
								</cognome>
								<denominazione/>
								<nome>
									<xsl:value-of select="./mdas:caricaPersona/mdas:personaFisica/mdas:personaFisicaBase/mdas:nome"/>
								</nome>
								<codice_fiscale>
									<xsl:value-of select="./mdas:caricaPersona/mdas:personaFisica/mdas:personaFisicaBase/mdas:codiceFiscale"/>
								</codice_fiscale>
								<partita_iva/>
								<flg_individuale>N</flg_individuale>
								<data_nascita>
									<xsl:call-template name="dateFormatterEngToIt">
										<xsl:with-param name="dataInglese" select="./mdas:caricaPersona/mdas:personaFisica/mdas:dataNascita"/>
									</xsl:call-template>
								</data_nascita>
								<id_cittadinanza/>
								<cod_cittadinanza/>
								<des_cittadinanza/>
								<id_nazionalita/>
								<cod_nazionalita/>
								<des_nazionalita/>
								<id_comune_nascita/>
								<des_comune_nascita>
									<xsl:value-of select="./mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:comuneItaliano/mdet:denominazioneComune"/>
								</des_comune_nascita>
								<id_provincia_nascita/>
								<des_provincia_nascita>
									<xsl:value-of select="./mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:comuneItaliano/mdet:provincia/mdet:denominazioneProvincia"/>
								</des_provincia_nascita>
								<xsl:if test="./mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:comuneItaliano">
									<id_comune_nascita/>
									<des_comune_nascita>
										<xsl:value-of select="./mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:comuneItaliano/mdet:denominazioneComune"/>
									</des_comune_nascita>
									<id_stato_nascita>1</id_stato_nascita>
									<des_stato_nascita>ITALIA</des_stato_nascita>
									<id_provincia_nascita/>
									<des_provincia_nascita>
										<xsl:value-of select="./mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:comuneItaliano/mdet:provincia/mdet:denominazioneProvincia"/>
									</des_provincia_nascita>
									<localita_nascita/>
								</xsl:if>
								<xsl:if test="./mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera">
									<id_comune_nascita/>
									<des_comune_nascita>
										<xsl:value-of select="./mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera/mdet:stato/mdet:denominazione"/>
									</des_comune_nascita>
									<id_stato_nascita>
										<xsl:value-of select="./mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera/mdet:stato/mdet:id"/>
									</id_stato_nascita>
									<des_stato_nascita>
										<xsl:value-of select="./mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera/mdet:stato/mdet:denominazione"/>
									</des_stato_nascita>
									<id_provincia_nascita/>
									<des_provincia_nascita>
										<xsl:value-of select="./mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:comuneItaliano/mdet:provincia/mdet:denominazioneProvincia"/>
									</des_provincia_nascita>
									<localita_nascita>
										<xsl:value-of select="./mdas:caricaPersona/mdas:personaFisica/mdas:luogoNascita/mdet:comuneItaliano/mdet:denominazioneComune"/>
									</localita_nascita>
								</xsl:if>
								<sesso>
									<xsl:value-of select="./mdas:caricaPersona/mdas:personaFisica/mdas:sesso"/>
								</sesso>
								<des_tipo_collegio/>
								<numero_iscrizione/>
								<des_provincia_iscrizione/>
								<cod_provincia_cciaa/>
								<des_provincia_cciaa/>
								<flg_attesa_iscrizione_ri>false</flg_attesa_iscrizione_ri>
								<flg_obbligo_iscrizione_ri>false</flg_obbligo_iscrizione_ri>
								<n_iscrizione_ri/>
								<data_iscrizione_ri/>
								<flg_attesa_iscrizione_rea>false</flg_attesa_iscrizione_rea>
								<n_iscrizione_rea/>
								<recapiti>
									<xsl:for-each select="./mdas:caricaPersona/mdas:personaFisica/mdas:residenza">
										<recapito>
											<id_recapito/>
											<id_comune>
												<xsl:value-of select="./mdet:luogo/mdet:comuneItaliano/mdet:id"/>
											</id_comune>
											<des_comune>
												<xsl:value-of select="./mdet:luogo/mdet:comuneItaliano/mdet:denominazioneComune"/>
											</des_comune>
											<id_provincia/>
											<des_provincia>
												<xsl:value-of select="./mdet:luogo/mdet:comuneItaliano/mdet:provincia/mdet:denominazioneProvincia"/>
											</des_provincia>
											<xsl:if test="./mdet:luogo/mdet:comuneItaliano">
												<id_stato>1</id_stato>
												<des_stato>ITALIA</des_stato>
											</xsl:if>
											<xsl:if test="./mdet:luogo/mdet:cittaEstera">
												<id_stato>
													<xsl:value-of select="./mdet:luogo/mdet:cittaEstera/mdet:stato/mdet:id"/>
												</id_stato>
												<des_stato>
													<xsl:value-of select="./mdet:luogo/mdet:cittaEstera/mdet:stato/mdet:denominazione"/>
												</des_stato>
											</xsl:if>
											<indirizzo>
												<xsl:value-of select="./mdet:tipoIndirizzo"/>
												<xsl:value-of select="' '"/>
												<xsl:value-of select="./mdet:indirizzo"/>
											</indirizzo>
											<codice_via/>
											<n_civico>
												<xsl:value-of select="./mdet:numeroCivico"/>
											</n_civico>
											<codice_civico/>
											<interno_numero/>
											<interno_lettera/>
											<interno_scala/>
											<lettera/>
											<colore/>
											<cap>
												<xsl:value-of select="./mdet:cap"/>
											</cap>
											<altre_info_indirizzo/>
											<telefono/>
											<cellulare/>
											<email/>
											<pec/>
											<id_tipo_indirizzo>1</id_tipo_indirizzo>
											<des_tipo_indirizzo>RESIDENZA</des_tipo_indirizzo>
										</recapito>
									</xsl:for-each>
								</recapiti>
							</anagrafica>
							<id_tipo_ruolo>1</id_tipo_ruolo>
							<cod_tipo_ruolo>R</cod_tipo_ruolo>
							<des_tipo_ruolo>Richiedente</des_tipo_ruolo>
						</anagrafiche>
					</xsl:if>
				</xsl:for-each>
				<xsl:if test="$presenzaProcuratore != $ruoloProcuratore">
					<xsl:for-each select="./ist:richiedenteFormaGiuridica/mdas:listaCaricaPersona/mdas:caricaPersona/mdas:personaFisica">
						<anagrafiche>
							<anagrafica>
								<counter>
									<xsl:value-of select="position()-1 + 10"/>
								</counter>
								<confermata/>
								<daRubrica/>
								<id_anagrafica/>
								<tipo_anagrafica>F</tipo_anagrafica>
								<variante_anagrafica>F</variante_anagrafica>
								<cognome>
									<xsl:value-of select="./mdas:personaFisicaBase/mdas:cognome"/>
								</cognome>
								<denominazione/>
								<nome>
									<xsl:value-of select="./mdas:personaFisicaBase/mdas:nome"/>
								</nome>
								<codice_fiscale>
									<xsl:value-of select="./mdas:personaFisicaBase/mdas:codiceFiscale"/>
								</codice_fiscale>
								<flg_individuale>N</flg_individuale>
								<data_nascita>
									<xsl:call-template name="dateFormatterEngToIt">
										<xsl:with-param name="dataInglese" select="./mdas:dataNascita"/>
									</xsl:call-template>
								</data_nascita>
								<id_cittadinanza>
									<xsl:value-of select="./mdas:cittadinanza/mdet:id"/>
								</id_cittadinanza>
								<cod_cittadinanza>
									<xsl:value-of select="./mdas:cittadinanza/mdet:id"/>
								</cod_cittadinanza>
								<des_cittadinanza>
									<xsl:value-of select="./mdas:cittadinanza/mdet:denominazione"/>
								</des_cittadinanza>
								<id_nazionalita/>
								<cod_nazionalita/>
								<des_nazionalita/>
								<xsl:if test="./mdas:luogoNascita/mdet:comuneItaliano">
									<id_comune_nascita/>
									<des_comune_nascita>
										<xsl:value-of select="./mdas:luogoNascita/mdet:comuneItaliano/mdet:denominazioneComune"/>
									</des_comune_nascita>
									<id_stato_nascita>1</id_stato_nascita>
									<des_stato_nascita>ITALIA</des_stato_nascita>
									<id_provincia_nascita/>
									<des_provincia_nascita>
										<xsl:value-of select="./mdas:luogoNascita/mdet:comuneItaliano/mdet:provincia/mdet:denominazioneProvincia"/>
									</des_provincia_nascita>
									<localita_nascita/>
								</xsl:if>
								<xsl:if test="./mdas:luogoNascita/mdet:cittaEstera">
									<id_comune_nascita/>
									<des_comune_nascita>
										<xsl:value-of select="./mdas:luogoNascita/mdet:cittaEstera/mdet:stato/mdet:denominazione"/>
									</des_comune_nascita>
									<id_stato_nascita>
										<xsl:value-of select="./mdas:luogoNascita/mdet:cittaEstera/mdet:stato/mdet:id"/>
									</id_stato_nascita>
									<des_stato_nascita>
										<xsl:value-of select="./mdas:luogoNascita/mdet:cittaEstera/mdet:stato/mdet:denominazione"/>
									</des_stato_nascita>
									<id_provincia_nascita/>
									<des_provincia_nascita>
										<xsl:value-of select="./mdas:luogoNascita/mdet:comuneItaliano/mdet:provincia/mdet:denominazioneProvincia"/>
									</des_provincia_nascita>
									<localita_nascita>
										<xsl:value-of select="./mdas:luogoNascita/mdet:comuneItaliano/mdet:denominazioneComune"/>
									</localita_nascita>
								</xsl:if>
								<sesso>
									<xsl:value-of select="./mdas:sesso"/>
								</sesso>
								<des_tipo_collegio/>
								<numero_iscrizione/>
								<des_provincia_iscrizione/>
								<cod_provincia_cciaa/>
								<des_provincia_cciaa/>
								<flg_attesa_iscrizione_ri>false</flg_attesa_iscrizione_ri>
								<flg_obbligo_iscrizione_ri>false</flg_obbligo_iscrizione_ri>
								<flg_attesa_iscrizione_rea>false</flg_attesa_iscrizione_rea>
								<recapiti>
									<xsl:for-each select="./mdas:residenza">
										<recapito>
											<id_recapito/>
											<id_comune>
												<xsl:value-of select="./mdet:luogo/mdet:comuneItaliano/mdet:id"/>
											</id_comune>
											<des_comune>
												<xsl:value-of select="./mdet:luogo/mdet:comuneItaliano/mdet:denominazioneComune"/>
											</des_comune>
											<id_provincia/>
											<des_provincia>
												<xsl:value-of select="./mdet:luogo/mdet:comuneItaliano/mdet:provincia/mdet:denominazioneProvincia"/>
											</des_provincia>
											<xsl:if test="./mdet:luogo/mdet:comuneItaliano">
												<id_stato>1</id_stato>
												<des_stato>ITALIA</des_stato>
											</xsl:if>
											<xsl:if test="./mdet:luogo/mdet:cittaEstera">
												<id_stato>
													<xsl:value-of select="./mdet:luogo/mdet:cittaEstera/mdet:stato/mdet:id"/>
												</id_stato>
												<des_stato>
													<xsl:value-of select="./mdet:luogo/mdet:cittaEstera/mdet:stato/mdet:denominazione"/>
												</des_stato>
											</xsl:if>
											<indirizzo>
												<xsl:value-of select="./mdet:tipoIndirizzo"/>
												<xsl:value-of select="' '"/>
												<xsl:value-of select="./mdet:indirizzo"/>
											</indirizzo>
											<codice_via/>
											<n_civico>
												<xsl:value-of select="./mdet:numeroCivico"/>
											</n_civico>
											<codice_civico/>
											<interno_numero/>
											<interno_lettera/>
											<interno_scala/>
											<lettera/>
											<colore/>
											<cap>
												<xsl:value-of select="./mdet:cap"/>
											</cap>
											<altre_info_indirizzo/>
											<telefono/>
											<cellulare/>
											<email/>
											<pec/>
											<id_tipo_indirizzo>1</id_tipo_indirizzo>
											<des_tipo_indirizzo>RESIDENZA</des_tipo_indirizzo>
										</recapito>
									</xsl:for-each>
								</recapiti>
							</anagrafica>
							<id_tipo_ruolo>1</id_tipo_ruolo>
							<cod_tipo_ruolo>R</cod_tipo_ruolo>
							<des_tipo_ruolo>Richiedente</des_tipo_ruolo>
							<pec/>
						</anagrafiche>
					</xsl:for-each>
				</xsl:if>
			</xsl:for-each>
			<xsl:for-each select="/mand:managedData/mand:datoReferenziabile[@mand:nomeLogicoDato='feg_soggettiPartecipanti']/spar:elencoSoggettiPartecipanti/spar:soggettoPartecipante">
				<xsl:if test="./mdas:professionista">
					<anagrafiche>
						<anagrafica>
							<counter>
								<xsl:value-of select="position()-1 + 100"/>
							</counter>
							<confermata/>
							<daRubrica/>
							<id_anagrafica/>
							<tipo_anagrafica>F</tipo_anagrafica>
							<variante_anagrafica>F</variante_anagrafica>
							<cognome>
								<xsl:value-of select="./mdas:professionista/mdas:personaFisicaBase/mdas:cognome"/>
							</cognome>
							<denominazione/>
							<nome>
								<xsl:value-of select="./mdas:professionista/mdas:personaFisicaBase/mdas:nome"/>
							</nome>
							<codice_fiscale>
								<xsl:value-of select="./mdas:professionista/mdas:personaFisicaBase/mdas:codiceFiscale"/>
							</codice_fiscale>
							<flg_individuale>N</flg_individuale>
							<data_nascita>
								<xsl:call-template name="dateFormatterEngToIt">
									<xsl:with-param name="dataInglese" select="./mdas:professionista/mdas:dataNascita"/>
								</xsl:call-template>
							</data_nascita>
							<id_cittadinanza/>
							<cod_cittadinanza>
								<xsl:value-of select="./mdas:professionista/mdas:cittadinanza/mdet:id"/>
							</cod_cittadinanza>
							<des_cittadinanza>
								<xsl:value-of select="./mdas:professionista/mdas:cittadinanza/mdet:denominazione"/>
							</des_cittadinanza>
							<id_nazionalita/>
							<cod_nazionalita/>
							<des_nazionalita/>
							<xsl:if test="./mdas:professionista/mdas:luogoNascita/mdet:comuneItaliano">
								<id_comune_nascita/>
								<des_comune_nascita>
									<xsl:value-of select="./mdas:professionista/mdas:luogoNascita/mdet:comuneItaliano/mdet:denominazioneComune"/>
								</des_comune_nascita>
								<id_stato_nascita>1</id_stato_nascita>
								<des_stato_nascita>ITALIA</des_stato_nascita>
								<id_provincia_nascita/>
								<des_provincia_nascita>
									<xsl:value-of select="./mdas:professionista/mdas:luogoNascita/mdet:comuneItaliano/mdet:provincia/mdet:denominazioneProvincia"/>
								</des_provincia_nascita>
								<localita_nascita/>
							</xsl:if>
							<xsl:if test="./mdas:professionista/mdas:luogoNascita/mdet:cittaEstera">
								<id_comune_nascita/>
								<des_comune_nascita>
									<xsl:value-of select="./mdas:professionista/mdas:luogoNascita/mdet:cittaEstera/mdet:stato/mdet:denominazione"/>
								</des_comune_nascita>
								<id_stato_nascita>
									<xsl:value-of select="./mdas:professionista/mdas:luogoNascita/mdet:cittaEstera/mdet:stato/mdet:id"/>
								</id_stato_nascita>
								<des_stato_nascita>
									<xsl:value-of select="./mdas:professionista/mdas:luogoNascita/mdet:cittaEstera/mdet:stato/mdet:denominazione"/>
								</des_stato_nascita>
								<id_provincia_nascita/>
								<des_provincia_nascita>
									<xsl:value-of select="./mdas:professionista/mdas:luogoNascita/mdet:comuneItaliano/mdet:provincia/mdet:denominazioneProvincia"/>
								</des_provincia_nascita>
								<localita_nascita>
									<xsl:value-of select="./mdas:professionista/mdas:luogoNascita/mdet:comuneItaliano/mdet:denominazioneComune"/>
								</localita_nascita>
							</xsl:if>
							<sesso>
								<xsl:value-of select="./mdas:professionista/mdas:sesso"/>
							</sesso>
							<des_tipo_collegio>
								<xsl:value-of select="./mdas:professionista/mdas:titoloProfessionale/mdas:professione/mdas:denominazione"/>
							</des_tipo_collegio>
							<numero_iscrizione>
								<xsl:value-of select="./mdas:professionista/mdas:titoloProfessionale/mdas:numeroIscrizioneAlbo"/>
							</numero_iscrizione>
							<des_provincia_iscrizione/>
							<cod_provincia_cciaa/>
							<des_provincia_cciaa/>
							<flg_attesa_iscrizione_ri>false</flg_attesa_iscrizione_ri>
							<flg_obbligo_iscrizione_ri>false</flg_obbligo_iscrizione_ri>
							<flg_attesa_iscrizione_rea>false</flg_attesa_iscrizione_rea>
							<recapiti>
								<xsl:for-each select="./mdas:professionista/mdas:domicilioProfessionale">
									<recapito>
										<id_recapito/>
										<id_comune>
											<xsl:value-of select="./mdet:luogo/mdet:comuneItaliano/mdet:id"/>
										</id_comune>
										<des_comune>
											<xsl:value-of select="./mdet:luogo/mdet:comuneItaliano/mdet:denominazioneComune"/>
											<xsl:value-of select="./mdet:luogo/mdet:cittaEstera/mdet:denominazioneCittaEstera"/>
										</des_comune>
										<id_provincia/>
										<des_provincia>
											<xsl:value-of select="./mdet:luogo/mdet:comuneItaliano/mdet:provincia/mdet:denominazioneProvincia"/>
										</des_provincia>
										<xsl:if test="./mdet:luogo/mdet:comuneItaliano">
											<id_stato>1</id_stato>
											<des_stato>ITALIA</des_stato>
										</xsl:if>
										<xsl:if test="./mdet:luogo/mdet:cittaEstera">
											<id_stato>
												<xsl:value-of select="./mdet:luogo/mdet:cittaEstera/mdet:stato/mdet:id"/>
											</id_stato>
											<des_stato>
												<xsl:value-of select="./mdet:luogo/mdet:cittaEstera/mdet:stato/mdet:denominazione"/>
											</des_stato>
										</xsl:if>
										<indirizzo>
											<xsl:value-of select="./mdet:tipoIndirizzo"/>
											<xsl:value-of select="' '"/>
											<xsl:value-of select="./mdet:indirizzo"/>
										</indirizzo>
										<codice_via/>
										<n_civico>
											<xsl:value-of select="./mdet:numeroCivico"/>
										</n_civico>
										<codice_civico/>
										<interno_numero/>
										<interno_lettera/>
										<interno_scala/>
										<lettera/>
										<colore/>
										<cap>
											<xsl:value-of select="./mdet:cap"/>
										</cap>
										<altre_info_indirizzo/>
										<telefono>
											<xsl:if test="../mdas:recapitiProfessionali/mdas:riferimento[mdas:tipoRiferimento='Telefono fisso']">
												<xsl:value-of select="../mdas:recapitiProfessionali/mdas:riferimento[mdas:tipoRiferimento='Telefono fisso']/mdas:valoreRiferimento"/>
											</xsl:if>
											<xsl:if test="../mdas:recapitiProfessionali/mdas:riferimento[mdas:tipoRiferimento='Telefono principale']">
												<xsl:value-of select="../mdas:recapitiProfessionali/mdas:riferimento[mdas:tipoRiferimento='Telefono principale']/mdas:valoreRiferimento"/>
											</xsl:if>
										</telefono>
										<cellulare>
											<xsl:value-of select="../mdas:recapitiProfessionali/mdas:riferimento[mdas:tipoRiferimento='Cellulare']/mdas:valoreRiferimento"/>
										</cellulare>
										<email/>
										<pec>
											<xsl:if test="../mdas:recapitiProfessionali/mdas:riferimento[mdas:tipoRiferimento='PEC']">
												<xsl:value-of select="../mdas:recapitiProfessionali/mdas:riferimento[mdas:tipoRiferimento='PEC']/mdas:valoreRiferimento"/>
											</xsl:if>
										</pec>
										<id_tipo_indirizzo>1</id_tipo_indirizzo>
										<!-- ? check this -->
										<des_tipo_indirizzo>RESIDENZA</des_tipo_indirizzo>
									</recapito>
								</xsl:for-each>
							</recapiti>
						</anagrafica>
						<xsl:if test="./@spar:ruolo = $ruoloProcuratore">
							<id_tipo_ruolo>1</id_tipo_ruolo>
							<!-- TODO check this -->
							<cod_tipo_ruolo>R</cod_tipo_ruolo>
							<des_tipo_ruolo>Richiedente</des_tipo_ruolo>
						</xsl:if>
						<xsl:if test="./@spar:ruolo != $ruoloProcuratore">
							<id_tipo_ruolo>3</id_tipo_ruolo>
							<!-- TODO check this -->
							<cod_tipo_ruolo>P</cod_tipo_ruolo>
							<des_tipo_ruolo>Tecnico professionista</des_tipo_ruolo>
						</xsl:if>
						<pec/>
					</anagrafiche>
				</xsl:if>
<!-- per ora escluso				
                                                <xsl:if test="./mdas:personaFisica">
                                                        <anagrafiche>
                                                                <anagrafica>
                                                                        <counter>
                                                                                <xsl:value-of select="position()-1 + 100"/>
                                                                        </counter>
                                                                        <confermata/>
                                                                        <daRubrica/>
                                                                        <id_anagrafica/>
                                                                        <tipo_anagrafica>F</tipo_anagrafica>
                                                                        <variante_anagrafica>F</variante_anagrafica>
                                                                        <cognome>
                                                                                <xsl:value-of select="./mdas:personaFisica/mdas:personaFisicaBase/mdas:cognome"/>
                                                                        </cognome>
                                                                        <denominazione/>
                                                                        <nome>
                                                                                <xsl:value-of select="./mdas:personaFisica/mdas:personaFisicaBase/mdas:nome"/>
                                                                        </nome>
                                                                        <codice_fiscale>
                                                                                <xsl:value-of select="./mdas:personaFisica/mdas:personaFisicaBase/mdas:codiceFiscale"/>
                                                                        </codice_fiscale>
                                                                        <flg_individuale>N</flg_individuale>
                                                                        <data_nascita>
                                                                                <xsl:value-of select="./mdas:personaFisica/mdas:dataNascita"/>
                                                                        </data_nascita>
                                                                        <id_cittadinanza/>
                                                                        <cod_cittadinanza>
                                                                                <xsl:value-of select="./mdas:personaFisica/mdas:cittadinanza/mdet:id"/>
                                                                        </cod_cittadinanza>
                                                                        <des_cittadinanza>
                                                                                <xsl:value-of select="./mdas:personaFisica/mdas:cittadinanza/mdet:denominazione"/>
                                                                        </des_cittadinanza>
                                                                        <id_nazionalita/>
                                                                        <cod_nazionalita/>
                                                                        <des_nazionalita/>
                                                                        <id_comune_nascita/>
                                                                        <des_comune_nascita>
                                                                                <xsl:value-of select="./mdas:personaFisica/mdas:luogoNascita/mdet:comuneItaliano/mdet:denominazioneComune"/>
                                                                                <xsl:value-of select="./mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera/mdet:denominazioneCittaEstera"/>
                                                                        </des_comune_nascita>
                                                                        <id_provincia_nascita/>
                                                                        <des_provincia_nascita>
                                                                                <xsl:value-of select="./mdas:personaFisica/mdas:luogoNascita/mdet:comuneItaliano/mdet:provincia/mdet:denominazioneProvincia"/>
                                                                        </des_provincia_nascita>
                                                                        <xsl:if test="./mdas:personaFisica/mdas:luogoNascita/mdet:comuneItaliano">
                                                                                <id_stato_nascita>1</id_stato_nascita>
                                                                                <des_stato_nascita>ITALIA</des_stato_nascita>
                                                                        </xsl:if>
                                                                        <xsl:if test="./mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera">
                                                                                <id_stato_nascita>
                                                                                        <xsl:value-of select="./mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera/mdet:stato/mdet:id"/>
                                                                                </id_stato_nascita>
                                                                                <des_stato_nascita>
                                                                                        <xsl:value-of select="./mdas:personaFisica/mdas:luogoNascita/mdet:cittaEstera/mdet:stato/mdet:denominazione"/>
                                                                                </des_stato_nascita>
                                                                        </xsl:if>
                                                                        <localita_nascita/>
                                                                        <sesso>
                                                                                <xsl:value-of select="./mdas:personaFisica/mdas:sesso"/>
                                                                        </sesso>
                                                                        <des_tipo_collegio/>
                                                                        <numero_iscrizione/>
                                                                        <des_provincia_iscrizione/>
                                                                        <cod_provincia_cciaa/>
                                                                        <des_provincia_cciaa/>
                                                                        <flg_attesa_iscrizione_ri>false</flg_attesa_iscrizione_ri>
                                                                        <flg_obbligo_iscrizione_ri>false</flg_obbligo_iscrizione_ri>
                                                                        <flg_attesa_iscrizione_rea>false</flg_attesa_iscrizione_rea>
                                                                        <recapiti>
                                                                                <xsl:for-each select="./mdas:personaFisica/mdas:residenza">
                                                                                        <recapito>
                                                                                                <id_recapito/>
                                                                                                <id_comune>
                                                                                                        <xsl:value-of select="./mdet:luogo/mdet:comuneItaliano/mdet:id"/>
                                                                                                </id_comune>
                                                                                                <des_comune>
                                                                                                        <xsl:value-of select="./mdet:luogo/mdet:comuneItaliano/mdet:denominazioneComune"/>
                                                                                                </des_comune>
                                                                                                <id_provincia/>
                                                                                                <des_provincia>
                                                                                                        <xsl:value-of select="./mdet:luogo/mdet:comuneItaliano/mdet:provincia/mdet:denominazioneProvincia"/>
                                                                                                </des_provincia>
                                                                                                <xsl:if test="./mdet:luogo/mdet:comuneItaliano">
                                                                                                        <id_stato>1</id_stato>
                                                                                                        <des_stato>ITALIA</des_stato>
                                                                                                </xsl:if>
                                                                                                <xsl:if test="./mdet:luogo/mdet:cittaEstera">
                                                                                                        <id_stato>
                                                                                                                <xsl:value-of select="./mdet:luogo/mdet:cittaEstera/mdet:stato/mdet:id"/>
                                                                                                        </id_stato>
                                                                                                        <des_stato>
                                                                                                                <xsl:value-of select="./mdet:luogo/mdet:cittaEstera/mdet:stato/mdet:denominazione"/>
                                                                                                        </des_stato>
                                                                                                </xsl:if>
                                                                                                <indirizzo>
                                                                                                        <xsl:value-of select="./mdet:tipoIndirizzo"/>
                                                                                                        <xsl:value-of select="' '"/>
                                                                                                        <xsl:value-of select="./mdet:indirizzo"/>
                                                                                                </indirizzo>
                                                                                                <codice_via/>
                                                                                                <n_civico>
                                                                                                        <xsl:value-of select="./mdet:numeroCivico"/>
                                                                                                </n_civico>
                                                                                                <codice_civico/>
                                                                                                <interno_numero/>
                                                                                                <interno_lettera/>
                                                                                                <interno_scala/>
                                                                                                <lettera/>
                                                                                                <colore/>
                                                                                                <cap>
                                                                                                        <xsl:value-of select="./mdet:cap"/>
                                                                                                </cap>
                                                                                                <altre_info_indirizzo/>
                                                                                                <telefono>
                                                                                                        <xsl:if test="../mdas:recapitiProfessionali/mdas:riferimento[mdas:tipoRiferimento='Telefono fisso']">
                                                                                                                <xsl:value-of select="../mdas:recapitiProfessionali/mdas:riferimento[mdas:tipoRiferimento='Telefono fisso']/mdas:valoreRiferimento"/>
                                                                                                        </xsl:if>
                                                                                                        <xsl:if test="../mdas:recapitiProfessionali/mdas:riferimento[mdas:tipoRiferimento='Telefono principale']">
                                                                                                                <xsl:value-of select="../mdas:recapitiProfessionali/mdas:riferimento[mdas:tipoRiferimento='Telefono principale']/mdas:valoreRiferimento"/>
                                                                                                        </xsl:if>
                                                                                                </telefono>
                                                                                                <cellulare>
                                                                                                        <xsl:value-of select="../mdas:recapitiProfessionali/mdas:riferimento[mdas:tipoRiferimento='Cellulare']/mdas:valoreRiferimento"/>
                                                                                                </cellulare>
                                                                                                <email/>
                                                                                                <pec/>
                                                                                                <id_tipo_indirizzo>1</id_tipo_indirizzo>
                                                                                                <des_tipo_indirizzo>RESIDENZA</des_tipo_indirizzo>
                                                                                        </recapito>
                                                                                </xsl:for-each>
                                                                        </recapiti>
                                                                </anagrafica>
                                                                <xsl:if test="./@spar:ruolo = $ruoloProcuratore">
                                                                        <id_tipo_ruolo>1</id_tipo_ruolo>
                                                                        <cod_tipo_ruolo>R</cod_tipo_ruolo>
                                                                        <des_tipo_ruolo>Richiedente</des_tipo_ruolo>
                                                                </xsl:if>
                                                                <xsl:if test="./@spar:ruolo != $ruoloProcuratore">
                                                                        <id_tipo_ruolo>3</id_tipo_ruolo>
                                                                        <cod_tipo_ruolo>P</cod_tipo_ruolo>
                                                                        <des_tipo_ruolo>Tecnico professionista</des_tipo_ruolo>
                                                                </xsl:if>
                                                                <pec/>
                                                        </anagrafiche>
                                                </xsl:if>
                -->
			</xsl:for-each>
			<dati_catastali>
				<xsl:for-each select="(/mand:managedData/mand:datoReferenziabile/ist:parteGenerale/ist:localizzazioneIntervento/ist:localizzazioneTerritoriale/mdete:estremiCatastali/mdet:catastoFabbricati) | (/mand:managedData/mand:datoReferenziabile/ist:parteGenerale/ist:localizzazioneIntervento/ist:localizzazioneTerritoriale/mdete:estremiCatastali/mdet:catastoTerreni) |(/mand:managedData/mand:datoReferenziabile/ist:parteGenerale/ist:localizzazioneIntervento/ist:localizzazioneTerritoriale/mdete:estremiTavolari)">
					<xsl:call-template name="datiCatastali">
						<xsl:with-param name="counter" select="position()-1"/>
					</xsl:call-template>
				</xsl:for-each>
			</dati_catastali>
			<indirizzi_intervento>
				<xsl:for-each select="/mand:managedData/mand:datoReferenziabile/ist:parteGenerale/ist:localizzazioneIntervento/ist:localizzazioneTerritoriale/mdete:indirizzo">
					<indirizzo_intervento>
						<counter>
							<xsl:value-of select="position()-1"/>
						</counter>
						<localita>
							<xsl:value-of select="./mdet:frazione"/>
						</localita>
						<indirizzo>
							<xsl:value-of select="./mdet:tipoIndirizzo"/>
							<xsl:value-of select="' '"/>
							<xsl:value-of select="./mdet:indirizzo"/>
						</indirizzo>
						<civico>
							<xsl:value-of select="./mdet:numeroCivico"/>
						</civico>
						<cap>
							<xsl:value-of select="./mdet:cap"/>
						</cap>
						<codice_via/>
						<codice_civico/>
						<interno_numero/>
						<lettera/>
						<colore/>
						<confermato>true</confermato>
					</indirizzo_intervento>
				</xsl:for-each>
			</indirizzi_intervento>
			<procedimenti>
				<xsl:for-each select="/mand:managedData/mand:datoReferenziabile/ist:parteGenerale/ist:procedimento">
					<procedimento>
						<id_procedimento/>
						<cod_procedimento>
							<xsl:value-of select="./mdap:procedimentoBase/mdap:id"/>
						</cod_procedimento>
						<termini/>
						<des_procedimento>
							<xsl:value-of select="./mdap:procedimentoBase/mdap:titolo"/>
						</des_procedimento>
						<cod_lang/>
						<cod_ente_destinatario/>
						<des_ente_destinatario/>
					</procedimento>
				</xsl:for-each>
			</procedimenti>
			<allegati/>
			<eventi/>
		</pratica>
	</xsl:template>
	<xsl:template name="datiCatastali">
		<xsl:param name="counter"/>
		<immobile>
			<counter>
				<xsl:value-of select="$counter"/>
			</counter>
			<id_immobile/>
			<xsl:choose>
				<xsl:when test="name(.) = 'catastoFabbricati'">
					<id_tipo_sistema_catastale>1</id_tipo_sistema_catastale>
					<des_tipo_sistema_catastale>ORDINARIO</des_tipo_sistema_catastale>
					<sezione>
						<xsl:value-of select="./mdet:sezioneUrbana"/>
					</sezione>
					<id_tipo_unita>1</id_tipo_unita>
					<des_tipo_unita>FABBRICATI</des_tipo_unita>
					<foglio>
						<xsl:value-of select="./mdet:foglio"/>
					</foglio>
					<mappale>
						<xsl:value-of select="./mdet:particella"/>
					</mappale>
					<id_tipo_particella/>
					<des_tipo_particella/>
					<estensione_particella/>
					<subalterno>
						<xsl:value-of select="./mdet:subalterno"/>
					</subalterno>
					<latitudine/>
					<longitudine/>
					<id_comune>
						<xsl:value-of select="./mdet:comuneCatastale/mdet:id"/>
					</id_comune>
					<des_comune>
						<xsl:value-of select="./mdet:comuneCatastale/mdet:denominazioneComune"/>
					</des_comune>
					<id_provincia/>
					<des_provincia>
						<xsl:value-of select="./mdet:comuneCatastale/mdet:provincia/mdet:denominazioneProvincia"/>
					</des_provincia>
					<sigla_provincia>
						<xsl:value-of select="./mdet:comuneCatastale/mdet:provincia/mdet:siglaProvincia"/>
					</sigla_provincia>
					<confermato>true</confermato>
				</xsl:when>
				<xsl:when test="name(.) = 'catastoTerreni'">
					<id_tipo_sistema_catastale>1</id_tipo_sistema_catastale>
					<des_tipo_sistema_catastale>ORDINARIO</des_tipo_sistema_catastale>
					<sezione>
						<xsl:value-of select="./mdet:sezioneTerreni"/>
					</sezione>
					<id_tipo_unita>2</id_tipo_unita>
					<des_tipo_unita>TERRENI</des_tipo_unita>
					<foglio>
						<xsl:value-of select="./mdet:foglio"/>
					</foglio>
					<mappale>
						<xsl:value-of select="./mdet:mappale"/>
					</mappale>
					<!-- nel catasto non esite il tipo particella -->
					<id_tipo_particella/>
					<des_tipo_particella/>
					<estensione_particella/>
					<subalterno>
						<xsl:value-of select="./mdet:subalterno"/>
					</subalterno>
					<latitudine/>
					<longitudine/>
					<id_comune>
						<xsl:value-of select="./mdet:comuneCatastale/mdet:id"/>
					</id_comune>
					<des_comune>
						<xsl:value-of select="./mdet:comuneCatastale/mdet:denominazioneComune"/>
					</des_comune>
					<id_provincia/>
					<des_provincia>
						<xsl:value-of select="./mdet:comuneCatastale/mdet:provincia/mdet:denominazioneProvincia"/>
					</des_provincia>
					<sigla_provincia>
						<xsl:value-of select="./mdet:comuneCatastale/mdet:provincia/mdet:siglaProvincia"/>
					</sigla_provincia>
					<confermato>false</confermato>
				</xsl:when>
				<xsl:when test="name(.) = 'estremiTavolari'">
					<id_tipo_sistema_catastale>2</id_tipo_sistema_catastale>
					<des_tipo_sistema_catastale>TAVOLARE</des_tipo_sistema_catastale>
					<sezione/>
					<!-- nel tavolare non esite il tipo unita' -->
					<id_tipo_unita/>
					<des_tipo_unita/>
					<foglio/>
					<mappale>
						<xsl:value-of select="./mdet:numeroImmobile"/>
					</mappale>
					<id_tipo_particella>
						<xsl:choose>
							<xsl:when test="./mdet:tipoImmobile = 'p.c.n.'">1</xsl:when>
							<xsl:when test="./mdet:tipoImmobile = 'p.c.e.'">2</xsl:when>
							<xsl:when test="./mdet:tipoImmobile = 'p.c.t.'">3</xsl:when>
							<xsl:when test="./mdet:tipoImmobile = 'cat.t.'">4</xsl:when>
							<xsl:when test="./mdet:tipoImmobile = 'cat.e.'">5</xsl:when>
							<xsl:when test="./mdet:tipoImmobile = 'st'">6</xsl:when>
						</xsl:choose>
					</id_tipo_particella>
					<des_tipo_particella>
						<xsl:value-of select="./mdet:tipoImmobile"/>
					</des_tipo_particella>
					<estensione_particella>
						<xsl:value-of select="./mdet:subnumeroImmobile"/>
					</estensione_particella>
					<subalterno>
						<xsl:value-of select="./mdet:subalterno"/>
					</subalterno>
					<latitudine/>
					<longitudine/>
					<comune_censuario>
						<xsl:value-of select="./mdet:comuneCatastaleTavolare/mdet:denominazioneComuneCatastale"/>
					</comune_censuario>
					<id_provincia/>
					<des_provincia/>
					<sigla_provincia/>
					<confermato>false</confermato>
				</xsl:when>
			</xsl:choose>
		</immobile>
	</xsl:template>
	<xsl:template name="dateFormatterEngToIt">
		<xsl:param name="dataInglese"/>
		<xsl:variable name="giorno" select="substring($dataInglese,9,2)"/>
		<xsl:variable name="mese" select="substring($dataInglese,6,2)"/>
		<xsl:variable name="anno" select="substring($dataInglese,1,4)"/>
		<xsl:if test="$dataInglese != ''">
			<xsl:value-of select="concat($giorno,'/',$mese,'/',$anno)"/>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
