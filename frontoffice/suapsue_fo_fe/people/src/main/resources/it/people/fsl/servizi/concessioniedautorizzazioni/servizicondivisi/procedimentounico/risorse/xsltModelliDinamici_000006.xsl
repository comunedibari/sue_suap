<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs" version="2.0">
	<!-- Genova edilizia -->
    <xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyz'" />
    <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />	
	<xsl:template match="/documentRoot">
		<documentRoot xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:call-template name="identificatoreProcedimentoPraticaSpacchettata"/>
			<xsl:call-template name="idBookmark"/>
			<xsl:call-template name="moduloBianco"/>
			<xsl:call-template name="codiceDocumentoPrecompilazione"/>
			<xsl:call-template name="sportello"/>
			<xsl:call-template name="comune"/>
			<xsl:call-template name="descrizioneSettore"/>
			<xsl:call-template name="operazioniIndividuate"/>
			<xsl:call-template name="operazioniNonRichiesteEsplicitamente"/>
			<xsl:call-template name="dichiarazioniStatiche"/>
			<xsl:call-template name="dichiarazioniDinamiche"/>
			<xsl:call-template name="dichiarazioniNormalizzate"/>
			<xsl:call-template name="interventiSelezionati"/>
			<xsl:call-template name="procedimenti"/>
			<xsl:call-template name="bollo"/>
			<xsl:call-template name="oggetto"/>
			<xsl:call-template name="azioneTemplate"/>
			<xsl:call-template name="language"/>
			<anagrafica>
				<xsl:for-each select="/documentRoot/Anagrafica">
					<xsl:call-template name="anagraficaSUE"/>
				</xsl:for-each>
			</anagrafica>
			<xsl:call-template name="allegatiSelezionati"/>
			<xsl:call-template name="allegatiSelezionatiTotali"/>
			<xsl:call-template name="allegatiFisici"/>
			<xsl:call-template name="allegatiFacoltativi"/>
			<xsl:call-template name="combo"/>
			<xsl:call-template name="accreditamenti"/>
			<xsl:call-template name="riepilogoOneri"/>
		</documentRoot>
	</xsl:template>
	<xsl:template match="*|@*|text()" mode="copy">
		<xsl:copy>
			<xsl:apply-templates select="*|@*|text()" mode="copy"/>
		</xsl:copy>
	</xsl:template>
	<xsl:template name="estraiDeleganti">
		<deleganti>
			<xsl:for-each select="/documentRoot/Anagrafica">
				<xsl:if test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valore = CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente">
					<xsl:if test="CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<delegante>
							<codiceFiscale>
								<xsl:value-of select="translate(CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_CODFISCLEG']/valoreUtente, $smallcase, $uppercase)"/>
							</codiceFiscale>
							<cognomeNome>
								<xsl:value-of select="concat(CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_COGNOMELEG']/valoreUtente,' ',CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_NOMELEG']/valoreUtente)"/>
							</cognomeNome>
						</delegante>
					</xsl:if>
					<xsl:if test="CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<delegante>
							<codiceFiscale>
								<xsl:value-of select="translate(CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CODFISC_LEG']/valoreUtente, $smallcase, $uppercase)"/>
							</codiceFiscale>
							<cognomeNome>
								<xsl:value-of select="concat(CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_COG_LEG']/valoreUtente,' ',CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NOME_LEG']/valoreUtente)"/>
							</cognomeNome>
						</delegante>
					</xsl:if>
				</xsl:if>
			</xsl:for-each>
		</deleganti>
	</xsl:template>
	<xsl:template name="accreditamenti">
		<xsl:apply-templates select="/documentRoot/datiAccreditamento[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="comune">
		<xsl:apply-templates select="/documentRoot/comune[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="identificatoreProcedimentoPraticaSpacchettata">
		<xsl:apply-templates select="/documentRoot/identificatoreProcedimentoPraticaSpacchettata[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="idBookmark">
		<xsl:apply-templates select="/documentRoot/idBookmark[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="moduloBianco">
		<xsl:apply-templates select="/documentRoot/moduloBianco[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="stampaBozza">
		<xsl:apply-templates select="/documentRoot/stampaBozza[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="codiceDocumentoPrecompilazione">
		<xsl:apply-templates select="/documentRoot/codiceDocumentoPrecompilazione[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="language">
		<xsl:apply-templates select="/documentRoot/language[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="sportello">
		<xsl:apply-templates select="/documentRoot/sportello[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="descrizioneSettore">
		<xsl:apply-templates select="/documentRoot/descrizioneSettore[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="operazioniIndividuate">
		<xsl:apply-templates select="/documentRoot/operazioniIndividuate[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="operazioniNonRichiesteEsplicitamente">
		<xsl:apply-templates select="/documentRoot/operazioniNonRichiesteEsplicitamente[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="dichiarazioniStatiche">
		<xsl:apply-templates select="/documentRoot/dichiarazioniStatiche[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="interventiSelezionati">
		<xsl:apply-templates select="/documentRoot/interventiSelezionati[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="procedimenti">
		<xsl:apply-templates select="/documentRoot/procedimenti[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="dichiarazioniDinamiche">
		<xsl:apply-templates select="/documentRoot/dichiarazioniDinamiche[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="dichiarazioniNormalizzate">
		<xsl:apply-templates select="/documentRoot/dichiarazioniNormalizzate[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="bollo">
		<xsl:apply-templates select="/documentRoot/bollo[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="oggetto">
		<xsl:apply-templates select="/documentRoot/oggetto[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="azioneTemplate">
		<xsl:apply-templates select="/documentRoot/azioneTemplate[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="allegatiSelezionati">
		<allegatiSelezionati>
			<xsl:apply-templates select="/documentRoot/allegatiSelezionatiTotali[1]/allegato" mode="copy"/>
		</allegatiSelezionati>
	</xsl:template>
	<xsl:template name="allegatiSelezionatiTotali">
		<xsl:apply-templates select="/documentRoot/allegatiSelezionatiTotali[1]" mode="copy"/>
	</xsl:template>
<!--xsl:template name="allegatiSelezionatiTotali">
        <xsl:apply-templates select="/documentRoot/allegatiSelezionatiTotali" mode="copy"/>
    </xsl:template-->
	<xsl:template name="allegatiFisici">
		<xsl:apply-templates select="/documentRoot/allegatiFisici" mode="copy"/>
	</xsl:template>
	<xsl:template name="allegatiFacoltativi">
		<xsl:apply-templates select="/documentRoot/allegatiFacoltativi" mode="copy"/>
	</xsl:template>
	<xsl:template name="riepilogoOneri">
		<xsl:apply-templates select="/documentRoot/riepilogoOneri[1]" mode="copy"/>
	</xsl:template>
	<xsl:template name="combo">
		<xsl:call-template name="comboAnagrafica"/>
		<xsl:call-template name="comboProfessionista"/>
		<xsl:call-template name="comboAltroEnte"/>
		<xsl:call-template name="comboCausaleDelega"/>
		<xsl:call-template name="comboImpresa"/>
		<xsl:call-template name="comboAlbo"/>
		<xsl:call-template name="comboTramiteDelega"/>
	</xsl:template>
	<xsl:template name="comboAnagrafica">
		<comboAnagrafica>
			<xsl:for-each select="/documentRoot/Anagrafica/CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_MOTIVAZIONE']/opzioniCombo/OpzioneCombo">
				<elemento>
					<descrizione>
						<xsl:value-of select="Etichetta"/>
					</descrizione>
				</elemento>
			</xsl:for-each>
		</comboAnagrafica>
	</xsl:template>
	<xsl:template name="comboProfessionista">
		<comboProfessionista>
			<xsl:for-each select="/documentRoot/Anagrafica/CampoAnagrafica[campo_xml_mod='ANAG_PROF_QUALIFICA']/opzioniCombo/OpzioneCombo">
				<elemento>
					<descrizione>
						<xsl:value-of select="Etichetta"/>
					</descrizione>
				</elemento>
			</xsl:for-each>
		</comboProfessionista>
	</xsl:template>
	<xsl:template name="comboAltroEnte">
		<comboAltroEnte>
			<xsl:for-each select="/documentRoot/Anagrafica/CampoAnagrafica[campo_xml_mod='ANAG_RAPPENTE_MOTIVAZIONE']/opzioniCombo/OpzioneCombo">
				<elemento>
					<descrizione>
						<xsl:choose>
							<xsl:when test="Etichetta=''">altro</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="Etichetta"/>
							</xsl:otherwise>
						</xsl:choose>
					</descrizione>
				</elemento>
			</xsl:for-each>
		</comboAltroEnte>
	</xsl:template>
	<xsl:template name="comboCausaleDelega">
		<comboCausaleDelega>
			<xsl:for-each select="/documentRoot/Anagrafica/CampoAnagrafica[campo_xml_mod='ANAG_PROF_PRESENTAZIONE']/opzioniCombo/OpzioneCombo">
				<elemento>
					<descrizione>
						<xsl:value-of select="Etichetta"/>
					</descrizione>
				</elemento>
			</xsl:for-each>
		</comboCausaleDelega>
	</xsl:template>
	<xsl:template name="comboImpresa">
		<comboImpresa>
			<xsl:for-each select="/documentRoot/Anagrafica/CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CARICALEG']/opzioniCombo/OpzioneCombo">
				<elemento>
					<descrizione>
						<xsl:value-of select="Etichetta"/>
					</descrizione>
				</elemento>
			</xsl:for-each>
		</comboImpresa>
	</xsl:template>
	<xsl:template name="comboAlbo">
		<comboAlbo>
			<xsl:for-each select="/documentRoot/Anagrafica/CampoAnagrafica[campo_xml_mod='ANAG_PROF_ISCRITTOALBO']/opzioniCombo/OpzioneCombo">
				<elemento>
					<descrizione>
						<xsl:value-of select="Etichetta"/>
					</descrizione>
				</elemento>
			</xsl:for-each>
		</comboAlbo>
	</xsl:template>
	<xsl:template name="comboTramiteDelega">
		<comboTramiteDelega>
			<xsl:for-each select="/documentRoot/Anagrafica/CampoAnagrafica[contains(descrizione, 'tramite')]/opzioniCombo/OpzioneCombo">
				<elemento>
					<descrizione>
						<xsl:value-of select="Etichetta"/>
					</descrizione>
				</elemento>
			</xsl:for-each>
		</comboTramiteDelega>
	</xsl:template>
	<xsl:template name="qualitaDi">
		<xsl:param name="qualitaDi"/>
		<xsl:param name="qualitaDiDescrizione"/>
		<descrizioneInQualitaDi>
			<xsl:value-of select="$qualitaDiDescrizione"/>
		</descrizioneInQualitaDi>
		<codiceInQualitaDi>
			<xsl:if test="$qualitaDi = 1">0</xsl:if>
			<xsl:if test="$qualitaDi = 2">1</xsl:if>
			<xsl:if test="$qualitaDi = 3">2</xsl:if>
			<xsl:if test="$qualitaDi = 4">3</xsl:if>
			<xsl:if test="$qualitaDi = 5">4</xsl:if>
			<xsl:if test="$qualitaDi = 6">5</xsl:if>
		</codiceInQualitaDi>
		<collegio>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_COLLEGIO']/valoreUtente"/>
		</collegio>
	</xsl:template>
	<xsl:template name="anagraficaSUE">
		<xsl:variable name="inQualitaDi">
			<xsl:if test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="inQualitaDiDescrizione">
			<xsl:if test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/descrizione"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="tipoRichiedente">
			<xsl:if test="CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente"/>
			</xsl:if>
		</xsl:variable>
		<xsl:variable name="tipoRichiedenteRedattore">
			<xsl:if test="CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente"/>
			</xsl:if>
		</xsl:variable>
		<dichiarante>
			<persona>
				<xsl:call-template name="dichiaranteSUE">
					<xsl:with-param name="qualitaDi" select="$inQualitaDi"/>
				</xsl:call-template>
			</persona>
			<deleganti>
				<delegante>
					<xsl:call-template name="qualitaDi">
						<xsl:with-param name="qualitaDi" select="$inQualitaDi"/>
						<xsl:with-param name="qualitaDiDescrizione" select="$inQualitaDiDescrizione"/>
					</xsl:call-template>
					<xsl:if test="$inQualitaDi = '1' and $tipoRichiedente = '4'">
						<xsl:call-template name="personaGiuridicaSUE">
							<xsl:with-param name="qualitaDi" select="$inQualitaDi"/>
						</xsl:call-template>
					</xsl:if>
					<xsl:if test="$inQualitaDi = '2' and $tipoRichiedenteRedattore = '5'">
						<xsl:call-template name="procuratorePersonaFisicaSUE">
							<xsl:with-param name="tipoRichiedenteRedattore" select="$tipoRichiedenteRedattore"/>
							<xsl:with-param name="qualitaDi" select="$inQualitaDi"/>
						</xsl:call-template>
					</xsl:if>
					<xsl:if test="$inQualitaDi = '2' and $tipoRichiedenteRedattore = '6'">
						<xsl:call-template name="procuratorePersonaGiuridicaSUE">
							<xsl:with-param name="qualitaDi" select="$inQualitaDi"/>
						</xsl:call-template>
					</xsl:if>
					<xsl:call-template name="titoloDiLegittimazioneSUE">
						<xsl:with-param name="qualitaDi" select="$inQualitaDi"/>
					</xsl:call-template>
				</delegante>
			</deleganti>
		</dichiarante>
	</xsl:template>
	<xsl:template name="dichiaranteSUE">
		<xsl:param name="qualitaDi"/>
		<xsl:param name="tipoRichiedenteRedattore"/>
		<nome>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_NOME']/valoreUtente"/>
		</nome>
		<cognome>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_COGNOME']/valoreUtente"/>
		</cognome>
		<sesso>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_SESSO']/valoreUtente"/>
		</sesso>
		<luogoNascita>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_COMUNENASCITA']/valoreUtente"/>
		</luogoNascita>
		<provinciaNascita>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_PROVNASCITA']/valoreUtente"/>
		</provinciaNascita>
		<dataNascita>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_DATANASCITA']/valoreUtente"/>
		</dataNascita>
		<statoNascita/>
		<cittadinanza>
			<xsl:variable name="cittadinanza">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_CIT']/valoreUtente"/>
			</xsl:variable>
			<xsl:for-each select="CampoAnagrafica[(campo_xml_mod='ANAG_DICHIARANTE_CIT')]/opzioniCombo/OpzioneCombo">
				<xsl:if test="Codice = $cittadinanza">
					<xsl:value-of select="Etichetta"/>
				</xsl:if>
			</xsl:for-each>
		</cittadinanza>
		<nazionalita>
			<xsl:variable name="nazionalita">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_NAZ']/valoreUtente"/>
			</xsl:variable>
			<xsl:for-each select="CampoAnagrafica[(campo_xml_mod='ANAG_DICHIARANTE_NAZ')]/opzioniCombo/OpzioneCombo">
				<xsl:if test="Codice = $nazionalita">
					<xsl:value-of select="Etichetta"/>
				</xsl:if>
			</xsl:for-each>
		</nazionalita>
		<codiceFiscale>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_CF']/valoreUtente"/>
		</codiceFiscale>
		<residenza>
			<stato/>
			<cap>
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_CAPRES']/valoreUtente"/>
			</cap>
			<citta>
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_COMUNERES']/valoreUtente"/>
			</citta>
			<provincia>
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_PROVRES']/valoreUtente"/>
			</provincia>
			<via>
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_VIARES']/valoreUtente"/>
			</via>
			<civico>
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_CIV']/valoreUtente"/>
			</civico>
			<colore/>
			<lettera/>
			<interno/>
			<xsl:call-template name="recapitiSUE">
				<xsl:with-param name="qualitaDi" select="$qualitaDi"/>
				<xsl:with-param name="tipoRichiedenteRedattore" select="$tipoRichiedenteRedattore"/>
			</xsl:call-template>
		</residenza>
	</xsl:template>
	<xsl:template name="anagraficaPersonaFisicaSUE">
		<xsl:param name="qualitaDi"/>
		<xsl:param name="tipoRichiedenteRedattore"/>
		<nome>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_NOMELEG']/valoreUtente"/>
		</nome>
		<cognome>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_COGNOMELEG']/valoreUtente"/>
		</cognome>
		<sesso>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_SESSOLEG']/valoreUtente"/>
		</sesso>
		<luogoNascita>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_NATOALEG']/valoreUtente"/>
		</luogoNascita>
		<provinciaNascita>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_NATOPROVLEG']/valoreUtente"/>
		</provinciaNascita>
		<dataNascita>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_NATOILLEG']/valoreUtente"/>
		</dataNascita>
		<statoNascita/>
		<cittadinanza>
			<xsl:variable name="cittadinanza">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_CITTADINLEG']/valoreUtente"/>
			</xsl:variable>
			<xsl:for-each select="CampoAnagrafica[(campo_xml_mod='ANAG_PFISICA_CITTADINLEG')]/opzioniCombo/OpzioneCombo">
				<xsl:if test="Codice = $cittadinanza">
					<xsl:value-of select="Etichetta"/>
				</xsl:if>
			</xsl:for-each>
		</cittadinanza>
		<nazionalita>
			<xsl:variable name="nazionalita">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_NAZ']/valoreUtente"/>
			</xsl:variable>
			<xsl:for-each select="CampoAnagrafica[(campo_xml_mod='ANAG_PFISICA_NAZ')]/opzioniCombo/OpzioneCombo">
				<xsl:if test="Codice = $nazionalita">
					<xsl:value-of select="Etichetta"/>
				</xsl:if>
			</xsl:for-each>
		</nazionalita>
		<codiceFiscale>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_CODFISCLEG']/valoreUtente"/>
		</codiceFiscale>
		<residenza>
			<stato/>
			<cap>
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_CAPLEG']/valoreUtente"/>
			</cap>
			<citta>
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_CITTALEG']/valoreUtente"/>
			</citta>
			<provincia>
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_PROVLEG']/valoreUtente"/>
			</provincia>
			<via>
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_VIALEG']/valoreUtente"/>
			</via>
			<civico>
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_CIVLEG']/valoreUtente"/>
			</civico>
			<colore/>
			<lettera/>
			<interno/>
			<xsl:call-template name="recapitiSUE">
				<xsl:with-param name="qualitaDi" select="$qualitaDi"/>
				<xsl:with-param name="tipoRichiedenteRedattore" select="$tipoRichiedenteRedattore"/>
			</xsl:call-template>
		</residenza>
	</xsl:template>
	<xsl:template name="legaleRappresentanteSUE">
		<xsl:param name="qualitaDi"/>
		<legaleRappresentante>
			<qualita>
				<xsl:value-of select="$qualitaDi"/>
			</qualita>
			<nome>
				<xsl:if test="$qualitaDi = '1'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NOMELEG']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = '2'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NOME_LEG']/valoreUtente"/>
				</xsl:if>
			</nome>
			<cognome>
				<xsl:if test="$qualitaDi = '1'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_COGNOMELEG']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = '2'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_COG_LEG']/valoreUtente"/>
				</xsl:if>
			</cognome>
			<sesso>
				<xsl:if test="$qualitaDi = '1'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_SESSOLEG']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = '2'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_SESSO_LEG']/valoreUtente"/>
				</xsl:if>
			</sesso>
			<statoNascita/>
			<cittadinanza>
				<xsl:if test="$qualitaDi = '1'">
					<xsl:variable name="cittadinanzaLegaleRappresentante">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CIT_LEG']/valoreUtente"/>
					</xsl:variable>
					<xsl:for-each select="CampoAnagrafica[(campo_xml_mod='ANAG_PGIURIDICA_CIT_LEG')]/opzioniCombo/OpzioneCombo">
						<xsl:if test="Codice = $cittadinanzaLegaleRappresentante">
							<xsl:value-of select="Etichetta"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:if>
				<xsl:if test="$qualitaDi = '2'">
					<xsl:variable name="cittadinanzaLegaleRappresentante">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CIT_LEG']/valoreUtente"/>
					</xsl:variable>
					<xsl:for-each select="CampoAnagrafica[(campo_xml_mod='ANAG_PGIURIDICA_CIT_LEG')]/opzioniCombo/OpzioneCombo">
						<xsl:if test="Codice = $cittadinanzaLegaleRappresentante">
							<xsl:value-of select="Etichetta"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:if>
			</cittadinanza>
			<nazionalita>
				<xsl:if test="$qualitaDi = '1'">
					<xsl:variable name="nazionalitaLegaleRappresentante">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NAZ_LEG']/valoreUtente"/>
					</xsl:variable>
					<xsl:for-each select="CampoAnagrafica[(campo_xml_mod='ANAG_PGIURIDICA_NAZ_LEG')]/opzioniCombo/OpzioneCombo">
						<xsl:if test="Codice = $nazionalitaLegaleRappresentante">
							<xsl:value-of select="Etichetta"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:if>
				<xsl:if test="$qualitaDi = '2'">
					<xsl:variable name="nazionalitaLegaleRappresentante">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NAZ_LEG']/valoreUtente"/>
					</xsl:variable>
					<xsl:for-each select="CampoAnagrafica[(campo_xml_mod='ANAG_PGIURIDICA_NAZ_LEG')]/opzioniCombo/OpzioneCombo">
						<xsl:if test="Codice = $nazionalitaLegaleRappresentante">
							<xsl:value-of select="Etichetta"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:if>
			</nazionalita>
			<provinciaNascita>
				<xsl:if test="$qualitaDi = '1'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NATOPROVLEG']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = '2'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PROV_NASC_LEG']/valoreUtente"/>
				</xsl:if>
			</provinciaNascita>
			<luogoNascita>
				<xsl:if test="$qualitaDi = '1'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_COMUNELEG']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = '2'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NATOA_LEG']/valoreUtente"/>
				</xsl:if>
			</luogoNascita>
			<dataNascita>
				<xsl:if test="$qualitaDi = '1'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NATOALEG']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = '2'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_DTNASC_LEG']/valoreUtente"/>
				</xsl:if>
			</dataNascita>
			<codiceFiscale>
				<xsl:if test="$qualitaDi = '1'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CODFISCLEG']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = '2'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CODFISC_LEG']/valoreUtente"/>
				</xsl:if>
			</codiceFiscale>
			<residenza>
				<stato/>
				<cap>
					<xsl:if test="$qualitaDi = '1'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CAPLEG']/valoreUtente"/>
					</xsl:if>
					<xsl:if test="$qualitaDi = '2'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CAP_LEG']/valoreUtente"/>
					</xsl:if>
				</cap>
				<citta>
					<xsl:if test="$qualitaDi = '1'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CITTALEG']/valoreUtente"/>
					</xsl:if>
					<xsl:if test="$qualitaDi = '2'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CITTA_LEG']/valoreUtente"/>
					</xsl:if>
				</citta>
				<provincia>
					<xsl:if test="$qualitaDi = '1'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PROVLEG']/valoreUtente"/>
					</xsl:if>
					<xsl:if test="$qualitaDi = '2'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PROV_LEG']/valoreUtente"/>
					</xsl:if>
				</provincia>
				<via>
					<xsl:if test="$qualitaDi = '1'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_VIALEG']/valoreUtente"/>
					</xsl:if>
					<xsl:if test="$qualitaDi = '2'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_VIA_LEG']/valoreUtente"/>
					</xsl:if>
				</via>
				<civico>
					<xsl:if test="$qualitaDi = '1'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIFICA_CIV_LEG']/valoreUtente"/>
					</xsl:if>
					<xsl:if test="$qualitaDi = '2'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIFICA_CIV_LEG']/valoreUtente"/>
					</xsl:if>
				</civico>
				<pec>
					<xsl:if test="$qualitaDi = '1'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PEC_LEG']/valoreUtente"/>
					</xsl:if>
					<xsl:if test="$qualitaDi = '2'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PEC_LEG']/valoreUtente"/>
					</xsl:if>
				</pec>
				<email>
					<xsl:if test="$qualitaDi = '1'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_MAIL_LEG']/valoreUtente"/>
					</xsl:if>
					<xsl:if test="$qualitaDi = '2'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_MAIL_LEG']/valoreUtente"/>
					</xsl:if>
				</email>
				<fax>
					<xsl:if test="$qualitaDi = '1'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_FAX_LEG']/valoreUtente"/>
					</xsl:if>
					<xsl:if test="$qualitaDi = '2'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_FAX_LEG']/valoreUtente"/>
					</xsl:if>
				</fax>
				<telefono>
					<xsl:if test="$qualitaDi = '1'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_TEL_LEG']/valoreUtente"/>
					</xsl:if>
					<xsl:if test="$qualitaDi = '2'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_TEL_LEG']/valoreUtente"/>
					</xsl:if>
				</telefono>
				<colore/>
				<lettera/>
				<interno/>
				<xsl:call-template name="recapitiSUE">
					<xsl:with-param name="qualitaDi" select="$qualitaDi"/>
				</xsl:call-template>
			</residenza>
		</legaleRappresentante>
	</xsl:template>
	<xsl:template name="recapitiSUE">
		<xsl:param name="qualitaDi"/>
		<xsl:param name="tipoRichiedenteRedattore"/>
		<telefono>
			<xsl:choose>
				<xsl:when test="$tipoRichiedenteRedattore = '5'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_TELLEG']/valoreUtente"/>
				</xsl:when>
				<xsl:when test="$tipoRichiedenteRedattore = '6'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_TEL_LEG']/valoreUtente"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_TELRES']/valoreUtente"/>
				</xsl:otherwise>
			</xsl:choose>
		</telefono>
		<email>
			<xsl:choose>
				<xsl:when test="$tipoRichiedenteRedattore = '5'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_MAILLEG']/valoreUtente"/>
				</xsl:when>
				<xsl:when test="$tipoRichiedenteRedattore = '6'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_MAIL_LEG']/valoreUtente"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_EMAIL']/valoreUtente"/>
				</xsl:otherwise>
			</xsl:choose>
		</email>
		<pec>
			<xsl:choose>
				<xsl:when test="$tipoRichiedenteRedattore = '5'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_PECLEG']/valoreUtente"/>
				</xsl:when>
				<xsl:when test="$tipoRichiedenteRedattore = '6'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PEC_LEG']/valoreUtente"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_PEC']/valoreUtente"/>
				</xsl:otherwise>
			</xsl:choose>
		</pec>
		<fax>
			<xsl:choose>
				<xsl:when test="$tipoRichiedenteRedattore = '5'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_FAXLEG']/valoreUtente"/>
				</xsl:when>
				<xsl:when test="$tipoRichiedenteRedattore = '6'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_FAX_LEG']/valoreUtente"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_FAXRES']/valoreUtente"/>
				</xsl:otherwise>
			</xsl:choose>
		</fax>
	</xsl:template>
	<xsl:template name="recapitiPersonaSUE">
		<telefono>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PROCURA_TEL']/valoreUtente"/>
		</telefono>
		<email>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PROCURA_EMAIL']/valoreUtente"/>
		</email>
		<fax>
			<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PROCURA_FAX']/valoreUtente"/>
		</fax>
	</xsl:template>
	<xsl:template name="personaGiuridicaSUE">
		<xsl:param name="qualitaDi"/>
		<personaGiuridica>
			<xsl:call-template name="aziendaSUE">
				<xsl:with-param name="qualitaDi" select="$qualitaDi"/>
			</xsl:call-template>
		</personaGiuridica>
	</xsl:template>
	<xsl:template name="aziendaSUE">
		<xsl:param name="qualitaDi"/>
		<xsl:call-template name="cciaaSUE">
			<xsl:with-param name="qualitaDi" select="$qualitaDi"/>
		</xsl:call-template>
		<denominazione>
			<xsl:if test="$qualitaDi = 1">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_DENOM']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="$qualitaDi = 2">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_DENOM_AZ']/valoreUtente"/>
			</xsl:if>
		</denominazione>
		<descrizioneFormaGiuridica>
			<xsl:if test="$qualitaDi = 1">
				<xsl:variable name="formaGiuridica">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_FORGIU']/valoreUtente"/>
				</xsl:variable>
				<xsl:for-each select="CampoAnagrafica[(campo_xml_mod='ANAG_RAPPSOC_FORGIU')]/opzioniCombo/OpzioneCombo">
					<xsl:if test="Codice = $formaGiuridica">
						<xsl:value-of select="Etichetta"/>
					</xsl:if>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="$qualitaDi = 2">
				<xsl:variable name="formaGiuridica">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_FORGIU_AZ']/valoreUtente"/>
				</xsl:variable>
				<xsl:for-each select="CampoAnagrafica[(campo_xml_mod='ANAG_PGIURIDICA_FORGIU_AZ')]/opzioniCombo/OpzioneCombo">
					<xsl:if test="Codice = $formaGiuridica">
						<xsl:value-of select="Etichetta"/>
					</xsl:if>
				</xsl:for-each>
			</xsl:if>
		</descrizioneFormaGiuridica>
		<dittaindividualesino>
			<xsl:if test="$qualitaDi = 1">
				<xsl:variable name="disino">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RICH_DI']/valoreUtente"/>
				</xsl:variable>
				<xsl:for-each select="CampoAnagrafica[(campo_xml_mod='ANAG_RICH_DI')]/opzioniCombo/OpzioneCombo">
					<xsl:if test="Codice = $disino">
						<xsl:value-of select="Etichetta"/>
					</xsl:if>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="$qualitaDi = 2">
				<xsl:variable name="disino">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_DELEG_DI']/valoreUtente"/>
				</xsl:variable>
				<xsl:for-each select="CampoAnagrafica[(campo_xml_mod='ANAG_DELEG_DI')]/opzioniCombo/OpzioneCombo">
					<xsl:if test="Codice = $disino">
						<xsl:value-of select="Etichetta"/>
					</xsl:if>
				</xsl:for-each>
			</xsl:if>
		</dittaindividualesino>
		<codiceFiscale>
			<xsl:if test="$qualitaDi = 1">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_CODFISC']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="$qualitaDi = 2">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CODFISC_AZ']/valoreUtente"/>
			</xsl:if>
		</codiceFiscale>
		<partitaIva>
			<xsl:if test="$qualitaDi = 1">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_PIVA']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="$qualitaDi = 2">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PIVA_AZ']/valoreUtente"/>
			</xsl:if>
		</partitaIva>
		<dataCostituzione>
			<xsl:if test="$qualitaDi = 1">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_DT_COST']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="$qualitaDi = 2">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_DT_COST_AZ']/valoreUtente"/>
			</xsl:if>
		</dataCostituzione>
		<sede>
			<tipo/>
			<cap>
				<xsl:if test="$qualitaDi = 1">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_CAPSEDE']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = 2">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CAPSEDE_AZ']/valoreUtente"/>
				</xsl:if>
			</cap>
			<citta>
				<xsl:if test="$qualitaDi = 1">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_COMUNESEDE']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = 2">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_COMUNESEDE_AZ']/valoreUtente"/>
				</xsl:if>
			</citta>
			<email>
				<xsl:if test="$qualitaDi = 1">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_MAIL']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = 2">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_MAIL_AZ']/valoreUtente"/>
				</xsl:if>
			</email>
			<pec>
				<xsl:if test="$qualitaDi = 1">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_PEC']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = 2">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PEC_AZ']/valoreUtente"/>
				</xsl:if>
			</pec>
			<fax>
				<xsl:if test="$qualitaDi = 1">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_FAXSEDE']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = 2">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_FAXSEDE_AZ']/valoreUtente"/>
				</xsl:if>
			</fax>
			<provincia>
				<xsl:if test="$qualitaDi = 1">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_PROVSEDE']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = 2">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PROV_AZ']/valoreUtente"/>
				</xsl:if>
			</provincia>
			<stato/>
			<telefono>
				<xsl:if test="$qualitaDi = 1">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_TELSEDE']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = 2">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_TELSEDE_AZ']/valoreUtente"/>
				</xsl:if>
			</telefono>
			<dug/>
			<via>
				<xsl:if test="$qualitaDi = 1">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_INDIR']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = 2">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_VIA_AZ']/valoreUtente"/>
				</xsl:if>
			</via>
			<civico>
				<xsl:if test="$qualitaDi = 1">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_NUMEROCIVICO']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = 2">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NUMEROCIVICO_AZ']/valoreUtente"/>
				</xsl:if>
			</civico>
			<colore/>
			<lettera/>
			<interno/>
		</sede>
	</xsl:template>
	<xsl:template name="cciaaSUE">
		<xsl:param name="qualitaDi"/>
		<cameraCommercio>
			<provincia>
				<xsl:if test="$qualitaDi = 1">
					<xsl:variable name="cciaaNome">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_PROVREA']/valoreUtente"/>
					</xsl:variable>
					<xsl:for-each select="CampoAnagrafica[(campo_xml_mod='ANAG_RAPPSOC_PROVREA')]/opzioniCombo/OpzioneCombo">
						<xsl:if test="Codice = $cciaaNome">
							<xsl:value-of select="Etichetta"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:if>
				<xsl:if test="$qualitaDi = 2">
					<xsl:variable name="cciaaNome">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PROVREA']/valoreUtente"/>
					</xsl:variable>
					<xsl:for-each select="CampoAnagrafica[(campo_xml_mod='ANAG_PGIURIDICA_PROVREA')]/opzioniCombo/OpzioneCombo">
						<xsl:if test="Codice = $cciaaNome">
							<xsl:value-of select="Etichetta"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:if>
			</provincia>
			<numeroRea>
				<xsl:if test="$qualitaDi = 1">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_NUMEROREA']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = 2">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NUMEROREA']/valoreUtente"/>
				</xsl:if>
			</numeroRea>
			<dataRea>
				<xsl:if test="$qualitaDi = 1">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_DATAREA']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = 2">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_DATAREA']/valoreUtente"/>
				</xsl:if>
			</dataRea>
			<numeroRi>
				<xsl:if test="$qualitaDi = 1">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_NUMRI']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = 2">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NUMRI']/valoreUtente"/>
				</xsl:if>
			</numeroRi>
			<dataRi>
				<xsl:if test="$qualitaDi = 1">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_DARARI']/valoreUtente"/>
				</xsl:if>
				<xsl:if test="$qualitaDi = 2">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_DARARI']/valoreUtente"/>
				</xsl:if>
			</dataRi>
		</cameraCommercio>
	</xsl:template>
	<xsl:template name="procuratorePersonaFisicaSUE">
		<xsl:param name="qualitaDi"/>
		<xsl:param name="tipoRichiedenteRedattore"/>
		<personaFisica>
			<xsl:call-template name="anagraficaPersonaFisicaSUE">
				<xsl:with-param name="qualitaDi" select="$qualitaDi"/>
				<xsl:with-param name="tipoRichiedenteRedattore" select="$tipoRichiedenteRedattore"/>
			</xsl:call-template>
		</personaFisica>
	</xsl:template>
	<xsl:template name="procuratorePersonaGiuridicaSUE">
		<xsl:param name="qualitaDi"/>
		<personaGiuridica>
			<xsl:call-template name="aziendaSUE">
				<xsl:with-param name="qualitaDi" select="$qualitaDi"/>
			</xsl:call-template>
			<xsl:call-template name="legaleRappresentanteSUE">
				<xsl:with-param name="qualitaDi" select="$qualitaDi"/>
			</xsl:call-template>
		</personaGiuridica>
	</xsl:template>
	<xsl:template name="titoloDiLegittimazioneSUE">
		<xsl:param name="qualitaDi"/>
		<xsl:variable name="titoloLegittimazione">
			<!-- Livello 1 -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_PROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_PROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_PROP']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_COMPROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_SUPER']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_SUPER']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_SUPER']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_USUF']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_USUF']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_USUF']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_USO']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_USO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_USO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_ABITAZIONE']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_ABITAZIONE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_ABITAZIONE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_SERVITU']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_SERVITU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_SERVITU']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_LOCATAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_LOCATAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_LOCATAR']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_AFFITTUAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_AFFITTUAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_AFFITTUAR']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_ENFIT']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_ENFIT']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_ENFIT']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_BENI_DEMAN']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_BENI_DEMAN']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_BENI_DEMAN']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_AZ_SERVIZI']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_AZ_SERVIZI']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_AZ_SERVIZI']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_PROMISSARIO']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_PROMISSARIO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_PROMISSARIO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_ABUSO']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_ABUSO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_ABUSO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_AMMINISTRATORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_AMMINISTRATORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_AMMINISTRATORE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_ALTRO']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_ALTRO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_ALTRO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE1']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE1']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE1']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_PROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_PROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_PROP2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_COMPROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_SUPER2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_SUPER2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_SUPER2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE3']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE3']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE4']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE4']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_PROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_PROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_PROP3']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_COMPROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP3']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_ALTRO2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_ALTRO2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_ALTRO2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_CTU']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_CTU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_CTU']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_TECNICO']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_TECNICO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_TECNICO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_RICH_CDU']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_RICH_CDU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_RICH_CDU']/valoreUtente"/>
			</xsl:if>
			<!-- Nuovi valori -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_TIT_EDILIZ']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_TIT_EDILIZ']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_TIT_EDILIZ']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_SUCCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_SUCCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_SUCCESSORE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_TIT_EDILIZ_COND']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_TIT_EDILIZ_COND']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_TIT_EDILIZ_COND']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_SUCCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_SUCCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_SUCCESSORE2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_PROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_PROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_PROP4']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_COMPROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP4']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_BENI_DEMAN2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_BENI_DEMAN2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_BENI_DEMAN2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_AMMINISTRATORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_AMMINISTRATORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_AMMINISTRATORE2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_SUCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_SUCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_SUCESSORE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_SUCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_SUCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_SUCESSORE2']/valoreUtente"/>
			</xsl:if>
			<!-- Livello 2 -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_PROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_PROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_PROP']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_COMPROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_SUPER']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_SUPER']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_SUPER']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_USUF']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_USUF']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_USUF']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_USO']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_USO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_USO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_ABITAZIONE']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_ABITAZIONE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_ABITAZIONE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_SERVITU']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_SERVITU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_SERVITU']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_LOCATAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_LOCATAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_LOCATAR']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_AFFITTUAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_AFFITTUAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_AFFITTUAR']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_ENFIT']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_ENFIT']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_ENFIT']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_BENI_DEMAN']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_BENI_DEMAN']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_BENI_DEMAN']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_AZ_SERVIZI']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_AZ_SERVIZI']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_AZ_SERVIZI']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_PROMISSARIO']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_PROMISSARIO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_PROMISSARIO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_ABUSO']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_ABUSO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_ABUSO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_AMMINISTRATORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_AMMINISTRATORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_AMMINISTRATORE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_ALTRO']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_ALTRO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_ALTRO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE1']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE1']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE1']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_PROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_PROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_PROP2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_COMPROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_SUPER2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_SUPER2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_SUPER2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE3']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE3']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE4']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE4']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_PROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_PROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_PROP3']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_COMPROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP3']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_ALTRO2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_ALTRO2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_ALTRO2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_CTU']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_CTU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_CTU']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_TECNICO']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_TECNICO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_TECNICO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_RICH_CDU']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_RICH_CDU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_RICH_CDU']/valoreUtente"/>
			</xsl:if>
			<!-- Nuovi valori -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_TIT_EDILIZ']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_TIT_EDILIZ']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_TIT_EDILIZ']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_SUCCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_SUCCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_SUCCESSORE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_TIT_EDILIZ_COND']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_TIT_EDILIZ_COND']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_TIT_EDILIZ_COND']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_SUCCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_SUCCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_SUCCESSORE2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_PROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_PROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_PROP4']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_COMPROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP4']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_BENI_DEMAN2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_BENI_DEMAN2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_BENI_DEMAN2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_AMMINISTRATORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_AMMINISTRATORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_AMMINISTRATORE2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_SUCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_SUCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_SUCESSORE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_SUCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_SUCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_SUCESSORE2']/valoreUtente"/>
			</xsl:if>
			<!-- Livello 3 -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_PROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_PROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_PROP']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_COMPROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_SUPER']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_SUPER']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_SUPER']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_USUF']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_USUF']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_USUF']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_USO']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_USO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_USO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_ABITAZIONE']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_ABITAZIONE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_ABITAZIONE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_SERVITU']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_SERVITU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_SERVITU']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_LOCATAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_LOCATAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_LOCATAR']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_AFFITTUAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_AFFITTUAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_AFFITTUAR']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_ENFIT']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_ENFIT']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_ENFIT']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_BENI_DEMAN']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_BENI_DEMAN']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_BENI_DEMAN']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_AZ_SERVIZI']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_AZ_SERVIZI']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_AZ_SERVIZI']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_PROMISSARIO']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_PROMISSARIO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_PROMISSARIO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_ABUSO']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_ABUSO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_ABUSO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_AMMINISTRATORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_AMMINISTRATORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_AMMINISTRATORE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_ALTRO']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_ALTRO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_ALTRO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE1']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE1']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE1']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_PROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_PROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_PROP2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_COMPROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_SUPER2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_SUPER2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_SUPER2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE3']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE3']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE4']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE4']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_PROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_PROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_PROP3']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_COMPROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP3']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_ALTRO2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_ALTRO2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_ALTRO2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_CTU']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_CTU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_CTU']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_TECNICO']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_TECNICO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_TECNICO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_RICH_CDU']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_RICH_CDU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_RICH_CDU']/valoreUtente"/>
			</xsl:if>
			<!-- Nuovi valori -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_TIT_EDILIZ']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_TIT_EDILIZ']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_TIT_EDILIZ']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_SUCCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_SUCCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_SUCCESSORE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_TIT_EDILIZ_COND']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_TIT_EDILIZ_COND']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_TIT_EDILIZ_COND']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_SUCCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_SUCCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_SUCCESSORE2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_PROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_PROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_PROP4']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_COMPROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP4']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_BENI_DEMAN2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_BENI_DEMAN2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_BENI_DEMAN2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_AMMINISTRATORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_AMMINISTRATORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_AMMINISTRATORE2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_SUCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_SUCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_SUCESSORE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_SUCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_SUCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_SUCESSORE2']/valoreUtente"/>
			</xsl:if>
			<!-- 4 Livello -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_PROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_PROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_PROP']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_COMPROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_SUPER']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_SUPER']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_SUPER']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_USUF']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_USUF']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_USUF']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_USO']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_USO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_USO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_ABITAZIONE']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_ABITAZIONE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_ABITAZIONE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_SERVITU']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_SERVITU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_SERVITU']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_LOCATAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_LOCATAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_LOCATAR']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_AFFITTUAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_AFFITTUAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_AFFITTUAR']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_ENFIT']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_ENFIT']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_ENFIT']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_BENI_DEMAN']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_BENI_DEMAN']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_BENI_DEMAN']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_AZ_SERVIZI']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_AZ_SERVIZI']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_AZ_SERVIZI']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_PROMISSARIO']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_PROMISSARIO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_PROMISSARIO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_ABUSO']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_ABUSO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_ABUSO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_AMMINISTRATORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_AMMINISTRATORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_AMMINISTRATORE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_ALTRO']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_ALTRO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_ALTRO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE1']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE1']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE1']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_PROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_PROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_PROP2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_COMPROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_SUPER2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_SUPER2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_SUPER2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE3']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE3']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE4']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE4']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_PROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_PROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_PROP3']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_COMPROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP3']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_ALTRO2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_ALTRO2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_ALTRO2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_CTU']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_CTU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_CTU']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_TECNICO']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_TECNICO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_TECNICO']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_RICH_CDU']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_RICH_CDU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_RICH_CDU']/valoreUtente"/>
			</xsl:if>
			<!-- Nuovi valori -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_TIT_EDILIZ']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_TIT_EDILIZ']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_TIT_EDILIZ']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_SUCCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_SUCCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_SUCCESSORE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_TIT_EDILIZ_COND']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_TIT_EDILIZ_COND']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_TIT_EDILIZ_COND']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_SUCCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_SUCCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_SUCCESSORE2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_PROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_PROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_PROP4']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_COMPROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP4']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_BENI_DEMAN2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_BENI_DEMAN2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_BENI_DEMAN2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_AMMINISTRATORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_AMMINISTRATORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_AMMINISTRATORE2']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_SUCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_SUCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_SUCESSORE']/valoreUtente"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_SUCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_SUCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_SUCESSORE2']/valoreUtente"/>
			</xsl:if>
		</xsl:variable>
		<titoloLegittimazione>
			<!-- Livello 1 -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_PROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_PROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_PROP']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_COMPROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_SUPER']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_SUPER']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_SUPER']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_USUF']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_USUF']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_USUF']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_USO']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_USO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_USO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_ABITAZIONE']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_ABITAZIONE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_ABITAZIONE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_SERVITU']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_SERVITU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_SERVITU']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_LOCATAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_LOCATAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_LOCATAR']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_AFFITTUAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_AFFITTUAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_AFFITTUAR']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_ENFIT']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_ENFIT']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_ENFIT']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_BENI_DEMAN']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_BENI_DEMAN']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_BENI_DEMAN']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_AZ_SERVIZI']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_AZ_SERVIZI']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_AZ_SERVIZI']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_PROMISSARIO']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_PROMISSARIO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_PROMISSARIO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_ABUSO']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_ABUSO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_ABUSO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_AMMINISTRATORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_AMMINISTRATORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_AMMINISTRATORE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_ALTRO']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_ALTRO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_ALTRO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE1']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE1']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE1']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_PROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_PROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_PROP2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_COMPROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_SUPER2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_SUPER2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_SUPER2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE3']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE3']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE4']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_TITOLARE4']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_PROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_PROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_PROP3']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_COMPROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP3']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_ALTRO2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_ALTRO2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_ALTRO2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_CTU']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_CTU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_CTU']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_TECNICO']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_TECNICO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_TECNICO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_RICH_CDU']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_RICH_CDU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_RICH_CDU']/descrizione"/>
			</xsl:if>
			<!-- Nuovi valori -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_TIT_EDILIZ']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_TIT_EDILIZ']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_TIT_EDILIZ']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_SUCCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_SUCCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_SUCCESSORE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_TIT_EDILIZ_COND']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_TIT_EDILIZ_COND']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_TIT_EDILIZ_COND']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_SUCCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_SUCCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_SUCCESSORE2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_PROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_PROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_PROP4']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_COMPROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_COMPROP4']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_BENI_DEMAN2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_BENI_DEMAN2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_BENI_DEMAN2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_AMMINISTRATORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_AMMINISTRATORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_AMMINISTRATORE2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_SUCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_SUCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_SUCESSORE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='1TIT_SUCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='1TIT_SUCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='1TIT_SUCESSORE2']/descrizione"/>
			</xsl:if>
			<!-- Livello 2 -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_PROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_PROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_PROP']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_COMPROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_SUPER']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_SUPER']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_SUPER']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_USUF']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_USUF']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_USUF']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_USO']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_USO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_USO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_ABITAZIONE']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_ABITAZIONE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_ABITAZIONE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_SERVITU']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_SERVITU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_SERVITU']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_LOCATAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_LOCATAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_LOCATAR']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_AFFITTUAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_AFFITTUAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_AFFITTUAR']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_ENFIT']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_ENFIT']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_ENFIT']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_BENI_DEMAN']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_BENI_DEMAN']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_BENI_DEMAN']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_AZ_SERVIZI']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_AZ_SERVIZI']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_AZ_SERVIZI']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_PROMISSARIO']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_PROMISSARIO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_PROMISSARIO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_ABUSO']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_ABUSO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_ABUSO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_AMMINISTRATORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_AMMINISTRATORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_AMMINISTRATORE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_ALTRO']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_ALTRO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_ALTRO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE1']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE1']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE1']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_PROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_PROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_PROP2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_COMPROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_SUPER2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_SUPER2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_SUPER2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE3']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE3']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE4']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_TITOLARE4']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_PROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_PROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_PROP3']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_COMPROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP3']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_ALTRO2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_ALTRO2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_ALTRO2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_CTU']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_CTU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_CTU']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_TECNICO']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_TECNICO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_TECNICO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_RICH_CDU']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_RICH_CDU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_RICH_CDU']/descrizione"/>
			</xsl:if>
			<!-- Nuovi valori -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_TIT_EDILIZ']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_TIT_EDILIZ']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_TIT_EDILIZ']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_SUCCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_SUCCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_SUCCESSORE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_TIT_EDILIZ_COND']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_TIT_EDILIZ_COND']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_TIT_EDILIZ_COND']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_SUCCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_SUCCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_SUCCESSORE2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_PROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_PROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_PROP4']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_COMPROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_COMPROP4']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_BENI_DEMAN2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_BENI_DEMAN2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_BENI_DEMAN2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_AMMINISTRATORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_AMMINISTRATORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_AMMINISTRATORE2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_SUCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_SUCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_SUCESSORE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='2TIT_SUCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='2TIT_SUCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='2TIT_SUCESSORE2']/descrizione"/>
			</xsl:if>
			<!-- Livello 3 -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_PROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_PROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_PROP']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_COMPROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_SUPER']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_SUPER']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_SUPER']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_USUF']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_USUF']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_USUF']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_USO']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_USO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_USO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_ABITAZIONE']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_ABITAZIONE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_ABITAZIONE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_SERVITU']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_SERVITU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_SERVITU']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_LOCATAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_LOCATAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_LOCATAR']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_AFFITTUAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_AFFITTUAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_AFFITTUAR']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_ENFIT']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_ENFIT']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_ENFIT']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_BENI_DEMAN']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_BENI_DEMAN']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_BENI_DEMAN']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_AZ_SERVIZI']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_AZ_SERVIZI']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_AZ_SERVIZI']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_PROMISSARIO']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_PROMISSARIO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_PROMISSARIO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_ABUSO']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_ABUSO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_ABUSO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_AMMINISTRATORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_AMMINISTRATORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_AMMINISTRATORE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_ALTRO']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_ALTRO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_ALTRO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE1']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE1']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE1']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_PROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_PROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_PROP2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_COMPROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_SUPER2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_SUPER2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_SUPER2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE3']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE3']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE4']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_TITOLARE4']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_PROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_PROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_PROP3']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_COMPROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP3']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_ALTRO2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_ALTRO2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_ALTRO2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_CTU']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_CTU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_CTU']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_TECNICO']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_TECNICO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_TECNICO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_RICH_CDU']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_RICH_CDU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_RICH_CDU']/descrizione"/>
			</xsl:if>
			<!-- Nuovi valori -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_TIT_EDILIZ']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_TIT_EDILIZ']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_TIT_EDILIZ']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_SUCCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_SUCCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_SUCCESSORE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_TIT_EDILIZ_COND']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_TIT_EDILIZ_COND']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_TIT_EDILIZ_COND']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_SUCCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_SUCCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_SUCCESSORE2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_PROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_PROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_PROP4']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_COMPROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_COMPROP4']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_BENI_DEMAN2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_BENI_DEMAN2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_BENI_DEMAN2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_AMMINISTRATORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_AMMINISTRATORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_AMMINISTRATORE2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_SUCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_SUCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_SUCESSORE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='3TIT_SUCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='3TIT_SUCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='3TIT_SUCESSORE2']/descrizione"/>
			</xsl:if>
			<!-- 4 Livello -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_PROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_PROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_PROP']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_COMPROP']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_SUPER']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_SUPER']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_SUPER']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_USUF']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_USUF']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_USUF']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_USO']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_USO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_USO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_ABITAZIONE']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_ABITAZIONE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_ABITAZIONE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_SERVITU']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_SERVITU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_SERVITU']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_LOCATAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_LOCATAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_LOCATAR']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_AFFITTUAR']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_AFFITTUAR']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_AFFITTUAR']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_ENFIT']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_ENFIT']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_ENFIT']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_BENI_DEMAN']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_BENI_DEMAN']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_BENI_DEMAN']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_AZ_SERVIZI']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_AZ_SERVIZI']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_AZ_SERVIZI']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_PROMISSARIO']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_PROMISSARIO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_PROMISSARIO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_ABUSO']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_ABUSO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_ABUSO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_AMMINISTRATORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_AMMINISTRATORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_AMMINISTRATORE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_ALTRO']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_ALTRO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_ALTRO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE1']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE1']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE1']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_PROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_PROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_PROP2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_COMPROP2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_SUPER2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_SUPER2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_SUPER2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE3']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE3']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE4']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_TITOLARE4']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_PROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_PROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_PROP3']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP3']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_COMPROP3']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP3']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_ALTRO2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_ALTRO2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_ALTRO2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_CTU']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_CTU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_CTU']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_TECNICO']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_TECNICO']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_TECNICO']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_RICH_CDU']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_RICH_CDU']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_RICH_CDU']/descrizione"/>
			</xsl:if>
			<!-- Nuovi valori -->
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_TIT_EDILIZ']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_TIT_EDILIZ']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_TIT_EDILIZ']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_SUCCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_SUCCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_SUCCESSORE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_TIT_EDILIZ_COND']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_TIT_EDILIZ_COND']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_TIT_EDILIZ_COND']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_SUCCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_SUCCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_SUCCESSORE2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_PROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_PROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_PROP4']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP4']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_COMPROP4']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_COMPROP4']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_BENI_DEMAN2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_BENI_DEMAN2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_BENI_DEMAN2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_AMMINISTRATORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_AMMINISTRATORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_AMMINISTRATORE2']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_SUCESSORE']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_SUCESSORE']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_SUCESSORE']/descrizione"/>
			</xsl:if>
			<xsl:if test="CampoAnagrafica[campo_xml_mod='4TIT_SUCESSORE2']/valoreUtente=CampoAnagrafica[campo_xml_mod='4TIT_SUCESSORE2']/valore">
				<xsl:value-of select="CampoAnagrafica[campo_xml_mod='4TIT_SUCESSORE2']/descrizione"/>
			</xsl:if>
		</titoloLegittimazione>
		<dataAtto>
			<xsl:if test="$titoloLegittimazione = 'u'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO7' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO7' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO7' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO7' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'a'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO1' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO1' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO1' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO1' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'ac'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO2' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO2' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO2' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO2' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'b'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO6' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO6' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO6' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO6' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'v'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO8' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO8' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO8' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO8' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'ad'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO3' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO3' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO3' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO3' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'al'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO4' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO4' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO4' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO4' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'an'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO5' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO5' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO5' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_ATTO5' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
		</dataAtto>
		<annoDiaScia>
			<xsl:choose>
				<!-- Sono richiedente persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_ANNO_DIASCIA' and campo_collegato='tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono richiedente persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_ANNO_DIASCIA' and campo_collegato='2tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_ANNO_DIASCIA' and campo_collegato='3tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_ANNO_DIASCIA' and campo_collegato='4tit002']/valoreUtente"/>
				</xsl:when>
			</xsl:choose>
		</annoDiaScia>
		<dataCondono>
			<xsl:choose>
				<!-- Sono richiedente persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONDONO' and campo_collegato='tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono richiedente persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONDONO' and campo_collegato='2tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONDONO' and campo_collegato='3tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONDONO' and campo_collegato='4tit002']/valoreUtente"/>
				</xsl:when>
			</xsl:choose>
		</dataCondono>
		<dataContratto>
			<xsl:if test="$titoloLegittimazione = 'c'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO1' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO1' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO1' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO1' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'z'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO10' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO10' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO10' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO10' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'd'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO2' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO2' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO2' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO2' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'e'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO3' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO3' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO3' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO3' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'f'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO4' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO4' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO4' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO4' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'g'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO5' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO5' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO5' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO5' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'h'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO6' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO6' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO6' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO6' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'i'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO7' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO7' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO7' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO7' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'l'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO8' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO8' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO8' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO8' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'm'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO9' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO9' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO9' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_CONTRATTO9' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
		</dataContratto>
		<dataRegistrazione>
			<xsl:if test="$titoloLegittimazione = 'u'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG7' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG7' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG7' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG7' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'v'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG8' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG8' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG8' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG8' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'b'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG6' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG6' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG6' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG6' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'ac'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG2' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG2' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG2' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG2' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'a'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG1' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG1' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG1' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG1' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'ad'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG3' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG3' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG3' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG3' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'al'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG4' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG4' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG4' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG4' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'an'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG5' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG5' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG5' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_REG5' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
		</dataRegistrazione>
		<dataFineRapporto>
			<xsl:if test="$titoloLegittimazione = 'h'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP1' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP1' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP1' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP1' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'm'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP4' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP4' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP4' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP4' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'l'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP3' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP3' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP3' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP3' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
				/&gt;
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'i'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP2' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP2' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP2' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_FINE_RAP2' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
		</dataFineRapporto>
		<dataPdc>
			<xsl:choose>
				<!-- Sono richiedente persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_PDC' and campo_collegato='tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono richiedente persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_PDC' and campo_collegato='2tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_PDC' and campo_collegato='3tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_PDC' and campo_collegato='4tit002']/valoreUtente"/>
				</xsl:when>
			</xsl:choose>
		</dataPdc>
		<dataPreliminare>
			<xsl:choose>
				<!-- Sono richiedente persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_PREL' and campo_collegato='tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono richiedente persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_PREL' and campo_collegato='2tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_PREL' and campo_collegato='3tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_PREL' and campo_collegato='4tit002']/valoreUtente"/>
				</xsl:when>
			</xsl:choose>
		</dataPreliminare>
		<dataSuccessione>
			<xsl:if test="$titoloLegittimazione = 'c'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC1' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC1' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC1' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC1' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'd'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC2' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC2' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC2' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC2' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'e'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC3' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC3' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC3' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC3' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'f'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC4' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC4' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC4' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC4' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'g'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC5' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC5' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC5' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC5' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'z'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC6' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC6' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC6' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_SUC6' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
		</dataSuccessione>
		<dataDelegaVisura>
			<xsl:choose>
				<!-- Sono richiedente persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_VISURA' and campo_collegato='tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono richiedente persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_VISURA' and campo_collegato='2tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_VISURA' and campo_collegato='3tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_DT_VISURA' and campo_collegato='4tit002']/valoreUtente"/>
				</xsl:when>
			</xsl:choose>
		</dataDelegaVisura>
		<inForza>
			<xsl:if test="$titoloLegittimazione = 'a'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA1' and campo_collegato='tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA1' and campo_collegato='tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA1' and campo_collegato='2tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA1' and campo_collegato='2tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA1' and campo_collegato='3tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA1' and campo_collegato='3tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA1' and campo_collegato='4tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA1' and campo_collegato='4tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'ac'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA2' and campo_collegato='tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA2' and campo_collegato='tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA2' and campo_collegato='2tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA2' and campo_collegato='2tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA2' and campo_collegato='3tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA2' and campo_collegato='3tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA2' and campo_collegato='4tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA2' and campo_collegato='4tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'ad'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA3' and campo_collegato='tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA3' and campo_collegato='tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA3' and campo_collegato='2tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA3' and campo_collegato='2tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA3' and campo_collegato='3tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA3' and campo_collegato='3tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA3' and campo_collegato='4tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA3' and campo_collegato='4tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'al'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA4' and campo_collegato='tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA4' and campo_collegato='tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA4' and campo_collegato='2tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA4' and campo_collegato='2tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA4' and campo_collegato='3tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA4' and campo_collegato='3tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA4' and campo_collegato='4tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA4' and campo_collegato='4tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'an'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA5' and campo_collegato='tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA5' and campo_collegato='tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA5' and campo_collegato='2tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA5' and campo_collegato='2tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA5' and campo_collegato='3tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA5' and campo_collegato='3tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA5' and campo_collegato='4tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA5' and campo_collegato='4tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'b'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA6' and campo_collegato='tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA6' and campo_collegato='tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA6' and campo_collegato='2tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA6' and campo_collegato='2tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA6' and campo_collegato='3tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA6' and campo_collegato='3tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA6' and campo_collegato='4tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA6' and campo_collegato='4tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'u'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA7' and campo_collegato='tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA7' and campo_collegato='tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA7' and campo_collegato='2tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA7' and campo_collegato='2tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA7' and campo_collegato='3tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA7' and campo_collegato='3tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA7' and campo_collegato='4tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA7' and campo_collegato='4tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'v'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA8' and campo_collegato='tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA8' and campo_collegato='tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA8' and campo_collegato='2tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA8' and campo_collegato='2tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA8' and campo_collegato='3tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA8' and campo_collegato='3tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_FORZA8' and campo_collegato='4tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_FORZA8' and campo_collegato='4tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
		</inForza>
		<luogoRegistrazione>
			<xsl:if test="$titoloLegittimazione = 'ac'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG2' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG2' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG2' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG2' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'b'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG6' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG6' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG6' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG6' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'u'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG7' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG7' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG7' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG7' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'a'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG1' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG1' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG1' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG1' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'ad'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG3' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG3' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG3' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG3' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'v'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG8' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG8' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG8' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG8' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'al'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG4' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG4' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG4' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG4' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'an'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG5' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG5' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG5' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_LUOGO_REG5' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
		</luogoRegistrazione>
		<numeroDenunciaSuccessione>
			<xsl:if test="$titoloLegittimazione = 'g'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC5' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC5' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC5' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC5' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'f'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC4' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC4' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC4' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC4' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'e'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC3' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC3' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC3' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC3' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'd'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC2' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC2' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC2' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC2' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'c'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC1' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC1' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC1' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC1' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'z'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC6' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC6' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC6' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NUM_SUC6' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
		</numeroDenunciaSuccessione>
		<numeroPdc>
			<xsl:choose>
				<!-- Sono richiedente persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPDC' and campo_collegato='tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono richiedente persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPDC' and campo_collegato='2tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPDC' and campo_collegato='3tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPDC' and campo_collegato='4tit002']/valoreUtente"/>
				</xsl:when>
			</xsl:choose>
		</numeroPdc>
		<numeroPratica>
			<xsl:if test="$titoloLegittimazione = 'aa'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPRAT1' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPRAT1' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPRAT1' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPRAT1' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 's'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPRAT2' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPRAT2' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPRAT2' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPRAT2' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 't'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPRAT3' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPRAT3' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPRAT3' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_NPRAT3' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
		</numeroPratica>
		<numeroCondono>
			<xsl:choose>
				<!-- Sono richiedente persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_N_CONDONO' and campo_collegato='tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono richiedente persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_N_CONDONO' and campo_collegato='2tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_N_CONDONO' and campo_collegato='3tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_N_CONDONO' and campo_collegato='4tit002']/valoreUtente"/>
				</xsl:when>
			</xsl:choose>
		</numeroCondono>
		<numeroTitoloEdilizio>
			<xsl:choose>
				<!-- Sono richiedente persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_N_TIT_EDIL' and campo_collegato='tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono richiedente persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_N_TIT_EDIL' and campo_collegato='2tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_N_TIT_EDIL' and campo_collegato='3tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_N_TIT_EDIL' and campo_collegato='4tit002']/valoreUtente"/>
				</xsl:when>
			</xsl:choose>
		</numeroTitoloEdilizio>
		<percentualeProprieta>
			<xsl:if test="$titoloLegittimazione = 'v'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PERCENTUALE3' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PERCENTUALE3' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PERCENTUALE3' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PERCENTUALE3' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'ad'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PERCENTUALE1' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PERCENTUALE1' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PERCENTUALE1' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PERCENTUALE1' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'b'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PERCENTUALE2' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PERCENTUALE2' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PERCENTUALE2' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PERCENTUALE2' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
		</percentualeProprieta>
		<provvedimentoIncarico>
			<xsl:choose>
				<!-- Sono richiedente persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PROV_INCARICO' and campo_collegato='tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono richiedente persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PROV_INCARICO' and campo_collegato='2tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PROV_INCARICO' and campo_collegato='3tit002']/valoreUtente"/>
				</xsl:when>
				<!-- Sono procuratore persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
					<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_PROV_INCARICO' and campo_collegato='4tit002']/valoreUtente"/>
				</xsl:when>
			</xsl:choose>
		</provvedimentoIncarico>
		<repertorioAtto>
			<xsl:if test="$titoloLegittimazione = 'a'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO1' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO1' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO1' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO1' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'u'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO7' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO7' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO7' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO7' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'ad'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO3' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO3' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO3' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO3' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'v'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO8' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO8' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO8' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO8' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'b'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO6' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO6' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO6' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO6' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'ac'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO2' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO2' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO2' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO2' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'al'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO4' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO4' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO4' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO4' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'an'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO5' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO5' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO5' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_REP_ATTO5' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
		</repertorioAtto>
		<specificare>
			<xsl:if test="$titoloLegittimazione = 'r'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_SPEC2' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_SPEC2' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_SPEC2' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_SPEC2' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 'ae'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_SPEC1' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_SPEC1' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_SPEC1' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_SPEC1' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
		</specificare>
		<tipoPratica>
			<xsl:if test="$titoloLegittimazione = 'aa'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT1' and campo_collegato='tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT1' and campo_collegato='tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT1' and campo_collegato='2tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT1' and campo_collegato='2tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT1' and campo_collegato='3tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT1' and campo_collegato='3tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT1' and campo_collegato='4tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT1' and campo_collegato='4tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
			<xsl:if test="$titoloLegittimazione = 't'">
				<xsl:choose>
					<!-- Sono richiedente persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT2' and campo_collegato='tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT2' and campo_collegato='tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono richiedente persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT2' and campo_collegato='2tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT2' and campo_collegato='2tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona fisica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT2' and campo_collegato='3tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT2' and campo_collegato='3tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
					<!-- Sono procuratore persona giuridica -->
					<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
						<xsl:variable name="titLeg">
							<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT2' and campo_collegato='4tit002']/valoreUtente"/>
						</xsl:variable>
						<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_TIPPRAT2' and campo_collegato='4tit002']/opzioniCombo/OpzioneCombo">
							<xsl:if test="Codice = $titLeg">
								<xsl:value-of select="Etichetta"/>
							</xsl:if>
						</xsl:for-each>
					</xsl:when>
				</xsl:choose>
			</xsl:if>
		</tipoPratica>
		<titoloEdilizio>
			<xsl:choose>
				<!-- Sono richiedente persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PF']/valoreUtente = '3'">
					<xsl:variable name="titLeg">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_TIT_EDIL' and campo_collegato='tit002']/valoreUtente"/>
					</xsl:variable>
					<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_TIT_EDIL' and campo_collegato='tit002']/opzioniCombo/OpzioneCombo">
						<xsl:if test="Codice = $titLeg">
							<xsl:value-of select="Etichetta"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:when>
				<!-- Sono richiedente persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente = '1') and CampoAnagrafica[campo_xml_mod='ANAG_RIC_PG']/valoreUtente = '4'">
					<xsl:variable name="titLeg">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_TIT_EDIL' and campo_collegato='2tit002']/valoreUtente"/>
					</xsl:variable>
					<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_TIT_EDIL' and campo_collegato='2tit002']/opzioniCombo/OpzioneCombo">
						<xsl:if test="Codice = $titLeg">
							<xsl:value-of select="Etichetta"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:when>
				<!-- Sono procuratore persona fisica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PF']/valoreUtente = '5'">
					<xsl:variable name="titLeg">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_TIT_EDIL' and campo_collegato='3tit002']/valoreUtente"/>
					</xsl:variable>
					<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_TIT_EDIL' and campo_collegato='3tit002']/opzioniCombo/OpzioneCombo">
						<xsl:if test="Codice = $titLeg">
							<xsl:value-of select="Etichetta"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:when>
				<!-- Sono procuratore persona giuridica -->
				<xsl:when test="(CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente = '2') and CampoAnagrafica[campo_xml_mod='ANAG_DEL_PG']/valoreUtente = '6'">
					<xsl:variable name="titLeg">
						<xsl:value-of select="CampoAnagrafica[campo_xml_mod='TIT_TIT_EDIL' and campo_collegato='4tit002']/valoreUtente"/>
					</xsl:variable>
					<xsl:for-each select="CampoAnagrafica[campo_xml_mod='TIT_TIT_EDIL' and campo_collegato='4tit002']/opzioniCombo/OpzioneCombo">
						<xsl:if test="Codice = $titLeg">
							<xsl:value-of select="Etichetta"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</titoloEdilizio>
	</xsl:template>
</xsl:stylesheet>
