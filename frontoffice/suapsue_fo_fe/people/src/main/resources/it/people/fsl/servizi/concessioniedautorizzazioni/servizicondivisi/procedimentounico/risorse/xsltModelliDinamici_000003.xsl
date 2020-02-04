<?xml version="1.0" encoding="UTF-8"?>
<!--
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
  
  http://www.osor.eu/eupl
  
  Unless required by applicable law or agreed to in
  writing, software distributed under the Licence is
  distributed on an "AS IS" basis,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied.
  See the Licence for the specific language governing
  permissions and limitations under the Licence.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs" version="2.0">

    <xsl:template match="/documentRoot">
        <documentRoot xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:call-template name="copia"/>
            <anagrafica>
                <xsl:for-each select="/documentRoot/Anagrafica">
                    <xsl:call-template name="anagrafica"/>
                </xsl:for-each>
            </anagrafica>
            <xsl:call-template name="comune"/>
            <xsl:call-template name="allegatiSelezionati"/>
            <xsl:call-template name="allegatiSelezionatiTotali"/>
            <xsl:call-template name="allegatiFisici"/>
            <xsl:call-template name="combo"/>
            <xsl:call-template name="accreditamenti"/>
            <xsl:call-template name="pagamento"/>
        </documentRoot>
    </xsl:template>

    <xsl:template name="copia">
        <xsl:apply-templates select="identificatoreProcedimentoPraticaSpacchettata[1]" mode="copy"/>
        <idBookmark/>
        <xsl:apply-templates select="moduloBianco[1]" mode="copy"/>
        <xsl:apply-templates select="sportello[1]" mode="copy"/>
        <xsl:apply-templates select="descrizioneSettore[1]" mode="copy"/>
        <xsl:apply-templates select="operazioniIndividuate[1]" mode="copy"/>
        <xsl:apply-templates select="operazioniNonRichiesteEsplicitamente[1]" mode="copy"/>
        <xsl:apply-templates select="dichiarazioniStatiche[1]" mode="copy"/>
        <xsl:apply-templates select="interventiSelezionati[1]" mode="copy"/>
        <xsl:apply-templates select="procedimenti[1]" mode="copy"/>
        <xsl:apply-templates select="dichiarazioniDinamiche[1]" mode="copy"/>
        <xsl:apply-templates select="bollo[1]" mode="copy"/>
        <xsl:apply-templates select="oggetto[1]" mode="copy"/>
        <xsl:apply-templates select="azioneTemplate[1]" mode="copy"/>
        <xsl:apply-templates select="language[1]" mode="copy"/>
    </xsl:template>

    <xsl:template name="comune">
        <xsl:apply-templates select="/documentRoot/comune[1]" mode="copy"/>
    </xsl:template>

    <xsl:template name="accreditamenti">
        <xsl:apply-templates select="/documentRoot/datiAccreditamento[1]" mode="copy"/>
    </xsl:template>


    <xsl:template name="allegatiSelezionati">
        <allegatiSelezionati>
            <xsl:apply-templates select="/documentRoot/allegatiSelezionatiTotali[1]/allegato"
                mode="copy"/>
        </allegatiSelezionati>
    </xsl:template>

    <xsl:template name="allegatiSelezionatiTotali">
        <xsl:apply-templates select="/documentRoot/allegatiSelezionatiTotali" mode="copy"/>
    </xsl:template>

    <xsl:template name="allegatiFisici">
        <xsl:apply-templates select="/documentRoot/allegatiFisici" mode="copy"/>
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
            <xsl:for-each
                select="/documentRoot/Anagrafica/CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_MOTIVAZIONE']/opzioniCombo/OpzioneCombo">
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
            <xsl:for-each
                select="/documentRoot/Anagrafica/CampoAnagrafica[campo_xml_mod='ANAG_PROF_QUALIFICA']/opzioniCombo/OpzioneCombo">
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
            <xsl:for-each
                select="/documentRoot/Anagrafica/CampoAnagrafica[campo_xml_mod='ANAG_RAPPENTE_MOTIVAZIONE']/opzioniCombo/OpzioneCombo">
                <elemento>
                    <descrizione>
                        <xsl:choose>
                            <xsl:when test="Etichetta=''"> altro </xsl:when>
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
            <xsl:for-each
                select="/documentRoot/Anagrafica/CampoAnagrafica[campo_xml_mod='ANAG_PROF_PRESENTAZIONE']/opzioniCombo/OpzioneCombo">
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
            <xsl:for-each
                select="/documentRoot/Anagrafica/CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_MOTIVAZIONE']/opzioniCombo/OpzioneCombo">
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
            <xsl:for-each
                select="/documentRoot/Anagrafica/CampoAnagrafica[campo_xml_mod='ANAG_PROF_ISCRITTOALBO']/opzioniCombo/OpzioneCombo">
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
            <xsl:for-each
                select="/documentRoot/Anagrafica/CampoAnagrafica[contains(descrizione, 'tramite')]/opzioniCombo/OpzioneCombo">
                <elemento>
                    <descrizione>
                        <xsl:value-of select="Etichetta"/>
                    </descrizione>
                </elemento>
            </xsl:for-each>
        </comboTramiteDelega>
    </xsl:template>

    <xsl:template match="*|@*|text()" mode="copy">
        <xsl:copy>
            <xsl:apply-templates select="*|@*|text()" mode="copy"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template name="anagrafica">
        <xsl:variable name="inQualitaDi">
            <xsl:if
                test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valore">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente"
                />
            </xsl:if>
            <xsl:if
                test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valore">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente"
                />
            </xsl:if>
            <xsl:if
                test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_3']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_3']/valore">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_3']/valoreUtente"
                />
            </xsl:if>
            <xsl:if
                test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_4']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_4']/valore">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_4']/valoreUtente"
                />
            </xsl:if>
            <xsl:if
                test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_5']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_5']/valore">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_5']/valoreUtente"
                />
            </xsl:if>
        </xsl:variable>

        <xsl:variable name="inQualitaDiDescrizione">
            <xsl:if
                test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valore">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/descrizione"
                />
            </xsl:if>
            <xsl:if
                test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/valore">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_2']/descrizione"
                />
            </xsl:if>
            <xsl:if
                test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_3']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_3']/valore">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_3']/descrizione"
                />
            </xsl:if>
            <xsl:if
                test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_4']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_4']/valore">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_4']/descrizione"
                />
            </xsl:if>
            <xsl:if
                test="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_5']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_5']/valore">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_5']/descrizione"
                />
            </xsl:if>
        </xsl:variable>

        <xsl:variable name="ProcuraPersona">
            <xsl:choose>
                <xsl:when
                    test="CampoAnagrafica[campo_xml_mod='ANAG_PROF_PERSFISICA']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_PROF_PERSFISICA']/valore">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_PERSFISICA']/valoreUtente"/>
                </xsl:when>
                <xsl:when
                    test="CampoAnagrafica[campo_xml_mod='ANAG_PROF_PERSGIURIDICA']/valoreUtente=CampoAnagrafica[campo_xml_mod='ANAG_PROF_PERSGIURIDICA']/valore">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_PERSGIURIDICA']/valoreUtente"/>
                </xsl:when>
                <xsl:otherwise>2</xsl:otherwise>
            </xsl:choose>            
        </xsl:variable>



        <dichiarante>
            <xsl:call-template name="dichiarante">
                <xsl:with-param name="qualitaDi" select="$inQualitaDi"/>
            </xsl:call-template>
            <xsl:variable name="nomePfisica">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_NOME']/valoreUtente"/>
            </xsl:variable>
            <xsl:variable name="nomeAzienda">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_DENOM']/valoreUtente"/>
            </xsl:variable>
            <deleganti>
                <delegante>
                    <xsl:call-template name="qualitaDi">
                        <xsl:with-param name="qualitaDi" select="$inQualitaDi"/>
                        <xsl:with-param name="qualitaDiDescrizione" select="$inQualitaDiDescrizione"
                        />
                    </xsl:call-template>
                    <xsl:if
                        test="$inQualitaDi = '3' or ($inQualitaDi = '5' and $ProcuraPersona = '1')">
                        <xsl:call-template name="persona">
                            <xsl:with-param name="qualitaDi" select="$inQualitaDi"/>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if
                        test="$inQualitaDi = '1' or ($inQualitaDi = '5' and $ProcuraPersona = '2')">
                        <xsl:call-template name="personaGiuridica">
                            <xsl:with-param name="qualitaDi" select="$inQualitaDi"/>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if test="$inQualitaDi = '4'">
                        <xsl:call-template name="aziendaNoProfit">
                            <xsl:with-param name="qualitaDi" select="$inQualitaDi"/>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if test="$inQualitaDi = '5'">
                        <xsl:call-template name="professionista"/>
                    </xsl:if>
                </delegante>
            </deleganti>
        </dichiarante>
    </xsl:template>

    <xsl:template name="dichiarante">
        <xsl:param name="qualitaDi"/>
        <persona>
            <nome>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_NOME']/valoreUtente"/>
            </nome>
            <cognome>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_COGNOME']/valoreUtente"
                />
            </cognome>
            <sesso>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_SESSO']/valoreUtente"/>
            </sesso>
            <luogoNascita>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_COMUNENASCITA']/valoreUtente"
                />
            </luogoNascita>
            <provinciaNascita>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_PROVNASCITA']/valoreUtente"
                />
            </provinciaNascita>
            <dataNascita>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_DATANASCITA']/valoreUtente"
                />
            </dataNascita>
            <cittadinanza>
                <xsl:variable name="cittadinanza">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_CIT']/valoreUtente"/>
                </xsl:variable>
                <xsl:for-each
                    select="CampoAnagrafica[(campo_xml_mod='ANAG_DICHIARANTE_CIT')]/opzioniCombo/OpzioneCombo">
                    <xsl:if test="Codice = $cittadinanza">
                        <xsl:value-of select="Etichetta"/>
                    </xsl:if>
                </xsl:for-each>
            </cittadinanza>
            <nazionalita>
                <xsl:variable name="nazionalita">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_NAZ']/valoreUtente"/>
                </xsl:variable>
                <xsl:for-each
                    select="CampoAnagrafica[(campo_xml_mod='ANAG_DICHIARANTE_NAZ')]/opzioniCombo/OpzioneCombo">
                    <xsl:if test="Codice = $nazionalita">
                        <xsl:value-of select="Etichetta"/>
                    </xsl:if>
                </xsl:for-each>
            </nazionalita>
            <codiceFiscale>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_CF']/valoreUtente"
                />
            </codiceFiscale>
            <residenza>
                <cap>
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_CAPRES']/valoreUtente"
                    />
                </cap>
                <citta>
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_COMUNERES']/valoreUtente"
                    />
                </citta>
                <provincia>
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_PROVRES']/valoreUtente"
                    />
                </provincia>
                <via>
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_VIARES']/valoreUtente"
                    />
                </via>
                <civico>
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_CIV']/valoreUtente"/>
                </civico>
                <xsl:call-template name="recapiti"/>
            </residenza>
            <xsl:call-template name="motivazioneRappresentanza">
                <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
            </xsl:call-template>
        </persona>
    </xsl:template>

    <xsl:template name="persona">
        <xsl:param name="qualitaDi"/>
        <personaFisica>
            <nome>
                <xsl:if test="$qualitaDi = '3'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_NOMERAP']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '5'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_NOME']/valoreUtente"/>
                </xsl:if>
                <xsl:if test="$qualitaDi = '51'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NOMELEG']/valoreUtente"
                    />
                </xsl:if>
            </nome>
            <cognome>
                <xsl:if test="$qualitaDi = '3'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_COGNOMERAP']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '5'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_COGNOME']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '51'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_COGNOMELEG']/valoreUtente"
                    />
                </xsl:if>
            </cognome>
            <sesso>
                <xsl:if test="$qualitaDi = '3'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_SESSORAP']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '5'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_SESSO']/valoreUtente"/>
                </xsl:if>
                <xsl:if test="$qualitaDi = '51'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_SESSOLEG']/valoreUtente"
                    />
                </xsl:if>
            </sesso>
            <luogoNascita>
                <xsl:if test="$qualitaDi = '3'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_NATOARAP']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '5'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_LUOGONASCITA']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '51'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CITTALEG']/valoreUtente"
                    />
                </xsl:if>
            </luogoNascita>
            <provinciaNascita>
                <xsl:if test="$qualitaDi = '3'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_PROVNASCITARAP']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '5'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_PROVNASCITA']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '51'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NATOPROVLEG']/valoreUtente"
                    />
                </xsl:if>
            </provinciaNascita>
            <dataNascita>
                <xsl:if test="$qualitaDi = '3'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_DATANASCITARAP']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '5'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_DATANASCITA']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '51'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NATOILLEG']/valoreUtente"
                    />
                </xsl:if>
            </dataNascita>
            <codiceFiscale>
                <xsl:if test="$qualitaDi = '3'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_CODFISCRAP']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '5'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_CODFISC']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '51'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CODFISCLEG']/valoreUtente"
                    />
                </xsl:if>
            </codiceFiscale>
            <residenza>
                <cap>
                    <xsl:if test="$qualitaDi = '3'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_CAPRAP']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '5'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_CAPRES']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '51'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_COMUNELEG']/valoreUtente"
                        />
                    </xsl:if>
                </cap>
                <citta>
                    <xsl:if test="$qualitaDi = '3'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_COMUNERAP']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '5'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_COMUNERES']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '51'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CITTALEG']/valoreUtente"
                        />
                    </xsl:if>
                </citta>
                <provincia>
                    <xsl:if test="$qualitaDi = '3'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_PROVRAP']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '5'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_PROVRES']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '51'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PROVLEG']/valoreUtente"
                        />
                    </xsl:if>
                </provincia>
                <via>
                    <xsl:if test="$qualitaDi = '3'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_VIARAP']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '5'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PFISICA_VIARES']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '51'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_VIALEG']/valoreUtente"
                        />
                    </xsl:if>
                </via>
            </residenza>
            <xsl:call-template name="motivazioneRappresentanza">
                <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
            </xsl:call-template>
        </personaFisica>
    </xsl:template>

    <xsl:template name="motivazioneRappresentanza">
        <xsl:param name="qualitaDi"/>
        <xsl:variable name="motivazioneRappSoc">
            <xsl:value-of
                select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_MOTIVAZIONE']/valoreUtente"/>
        </xsl:variable>
        <xsl:variable name="motivazioneRappPriv">
            <xsl:value-of
                select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_MOTIVAZIONE']/valoreUtente"/>
        </xsl:variable>
        <xsl:variable name="motivazioneRappEnte">
            <xsl:value-of
                select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPENTE_MOTIVAZIONE']/valoreUtente"/>
        </xsl:variable>
        <xsl:variable name="rappresentanza">
            <xsl:if test="$qualitaDi = '1'">
                <xsl:choose>
                    <xsl:when test="contains(motivazioneRappSoc, '#')">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_ALTRO']/valoreUtente"
                        />
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:for-each
                            select="CampoAnagrafica[(campo_xml_mod='ANAG_RAPPSOC_MOTIVAZIONE')]/opzioniCombo/OpzioneCombo">
                            <xsl:if test="Codice = $motivazioneRappSoc">
                                <xsl:value-of select="Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
            <xsl:if test="$qualitaDi = '3'">
                <xsl:choose>
                    <xsl:when test="contains($motivazioneRappPriv, '#')">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPPRIV_ALTRO']/valoreUtente"
                        />
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:for-each
                            select="CampoAnagrafica[(campo_xml_mod='ANAG_RAPPPRIV_MOTIVAZIONE')]/opzioniCombo/OpzioneCombo">
                            <xsl:if test="Codice = $motivazioneRappPriv">
                                <xsl:value-of select="Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
            <xsl:if test="$qualitaDi = '4'">
                <xsl:choose>
                    <xsl:when test="contains($motivazioneRappEnte, '#')">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPENTE_ALTRO']/valoreUtente"
                        />
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:for-each
                            select="CampoAnagrafica[(campo_xml_mod='ANAG_RAPPENTE_MOTIVAZIONE')]/opzioniCombo/OpzioneCombo">
                            <xsl:if test="Codice = $motivazioneRappEnte">
                                <xsl:value-of select="Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
        </xsl:variable>
        <descrizioneMotivazioneRappresentanza>
            <xsl:value-of select="$rappresentanza"/>
        </descrizioneMotivazioneRappresentanza>
    </xsl:template>

    <xsl:template name="recapiti">
        <telefono>
            <xsl:value-of
                select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_TELRES']/valoreUtente"/>
        </telefono>
        <email>
            <xsl:value-of
                select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_MAIL']/valoreUtente"/>
        </email>
        <pec>
            <xsl:value-of
                select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_PEC']/valoreUtente"
            />
        </pec>
        <fax>
            <xsl:value-of
                select="CampoAnagrafica[campo_xml_mod='ANAG_DICHIARANTE_FAXRES']/valoreUtente"/>
        </fax>
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
        </codiceInQualitaDi>
    </xsl:template>

    <xsl:template name="azienda">
        <xsl:param name="qualitaDi"/>
        <xsl:call-template name="cciaa">
            <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
        </xsl:call-template>
        <denominazione>
            <xsl:if test="$qualitaDi = '1'">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_DENOM']/valoreUtente"/>
            </xsl:if>
            <xsl:if test="$qualitaDi = '4'">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPENTE_DENOM']/valoreUtente"/>
            </xsl:if>
            <xsl:if test="$qualitaDi = '5'">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_DENOM']/valoreUtente"/>
            </xsl:if>
        </denominazione>
        <descrizioneFormaGiuridica>
            <xsl:variable name="forGiuRappSoc">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_FORGIU']/valoreUtente"/>
            </xsl:variable>
            <xsl:variable name="forGiuRappGiur">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_FORGIU']/valoreUtente"/>
            </xsl:variable>
            <xsl:if test="$qualitaDi = '1'">
                <xsl:for-each
                    select="CampoAnagrafica[(campo_xml_mod='ANAG_RAPPSOC_FORGIU')]/opzioniCombo/OpzioneCombo">
                    <xsl:if test="Codice = $forGiuRappSoc">
                        <xsl:value-of select="Etichetta"/>
                    </xsl:if>
                </xsl:for-each>
            </xsl:if>
            <xsl:if test="$qualitaDi = '5'">
                <xsl:for-each
                    select="CampoAnagrafica[(campo_xml_mod='ANAG_PGIURIDICA_FORGIU')]/opzioniCombo/OpzioneCombo">
                    <xsl:if test="Codice = $forGiuRappGiur">
                        <xsl:value-of select="Etichetta"/>
                    </xsl:if>
                </xsl:for-each>
            </xsl:if>
        </descrizioneFormaGiuridica>
        <codiceFiscale>
            <xsl:if test="$qualitaDi = '1'">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_CODFISC']/valoreUtente"/>
            </xsl:if>
            <xsl:if test="$qualitaDi = '4'">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPENTE_CODFISC']/valoreUtente"/>
            </xsl:if>
            <xsl:if test="$qualitaDi = '5'">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CODFISC']/valoreUtente"/>
            </xsl:if>
        </codiceFiscale>
        <partitaIva>
            <xsl:if test="$qualitaDi = '1'">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_PIVA']/valoreUtente"/>
            </xsl:if>
            <xsl:if test="$qualitaDi = '4'">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPENTE_PIVA']/valoreUtente"/>
            </xsl:if>
            <xsl:if test="$qualitaDi = '5'">
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PIVA']/valoreUtente"/>
            </xsl:if>
        </partitaIva>
        <sede>
            <cap>
                <xsl:if test="$qualitaDi = '1'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_CAPSEDE']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '4'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPENTE_CAPSEDE']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '5'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CAPSEDE']/valoreUtente"
                    />
                </xsl:if>
            </cap>
            <citta>
                <xsl:if test="$qualitaDi = '1'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_COMUNESEDE']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '5'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_COMUNESEDE']/valoreUtente"
                    />
                </xsl:if>
            </citta>
            <email>
                <xsl:if test="$qualitaDi = 1">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_MAILSEDE']/valoreUtente"/>
                </xsl:if>
                <xsl:if test="$qualitaDi = 5">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_MAILSEDE']/valoreUtente"
                    />
                </xsl:if>
            </email>
            <pec>
                <xsl:if test="$qualitaDi = 1">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_PECSEDE']/valoreUtente"/>
                </xsl:if>
                <xsl:if test="$qualitaDi = 5">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PECSEDE']/valoreUtente"
                    />
                </xsl:if>
            </pec>
            <fax>
                <xsl:if test="$qualitaDi = '1'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_FAXSEDE']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '5'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_FAXSEDE']/valoreUtente"
                    />
                </xsl:if>
            </fax>
            <numero>
                <!-- Non è presente il campo per il numero civico -->
            </numero>
            <provincia>
                <xsl:if test="$qualitaDi = '1'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_PROVSEDE']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '4'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPENTE_PROVSEDE']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '5'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PROVSEDE']/valoreUtente"
                    />
                </xsl:if>
            </provincia>
            <stato>
                <!-- Non è presente il campo per lo stato della sede -->
            </stato>
            <telefono>
                <xsl:if test="$qualitaDi = '1'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_TELSEDE']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '4'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPENTE_TELSEDE']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = '5'">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_TELSEDE']/valoreUtente"
                    />
                </xsl:if>
            </telefono>
            <via>
                <xsl:if test="$qualitaDi = 1">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_SL_DENOMINAZIONESTRADALE']/valoreUtente"/>
                </xsl:if>
                <xsl:if test="$qualitaDi = 5">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_SL_DENOMINAZIONESTRADALE']/valoreUtente"
                    />
                </xsl:if>
            </via>
            <civico>
                <xsl:if test="$qualitaDi = 1">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_SL_NUMEROCIVICO']/valoreUtente"
                    />
                </xsl:if>
                <xsl:if test="$qualitaDi = 5">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_SL_NUMEROCIVICO']/valoreUtente"
                    />
                </xsl:if>
            </civico>
            <colore/>
            <lettera/>
            <interno/>
        </sede>
        <xsl:call-template name="tribunale">
            <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
        </xsl:call-template>
        <xsl:call-template name="rea">
            <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
        </xsl:call-template>
        <xsl:call-template name="motivazioneRappresentanza">
            <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
        </xsl:call-template>
        <xsl:if test="$qualitaDi = '5'">
            <xsl:call-template name="legaleRappresentante"/>
        </xsl:if>
    </xsl:template>

    <xsl:template name="cciaa">
        <xsl:param name="qualitaDi"/>
        <xsl:if test="$qualitaDi = '1' or  $qualitaDi = '5'">
            <cameraCommercio>
                <!-- Non c'è un campo per la descrizione CCIAA -->
                <provincia>
                    <xsl:if test="$qualitaDi = 1">
                        <xsl:variable name="cciaaNome">
                            <xsl:value-of
                                select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_PROVREA']/valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="CampoAnagrafica[(campo_xml_mod='ANAG_RAPPSOC_PROVREA')]/opzioniCombo/OpzioneCombo">
                            <xsl:if test="Codice = $cciaaNome">
                                <xsl:value-of select="Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:if>
                    <xsl:if test="$qualitaDi = 5">
                        <xsl:variable name="cciaaNome">
                            <xsl:value-of
                                select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PROVREA']/valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="CampoAnagrafica[(campo_xml_mod='ANAG_PGIURIDICA_PROVREA')]/opzioniCombo/OpzioneCombo">
                            <xsl:if test="Codice = $cciaaNome">
                                <xsl:value-of select="Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:if>
                </provincia>
                <numeroRea>
                    <xsl:if test="$qualitaDi = 1">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_NUMEROREA']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = 5">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NUMREA']/valoreUtente"
                        />
                    </xsl:if>
                </numeroRea>
                <dataRea>
                    <xsl:if test="$qualitaDi = 1">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_DATAREA']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = 5">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_DATAREA']/valoreUtente"
                        />
                    </xsl:if>
                </dataRea>
                <numeroRi>
                    <xsl:if test="$qualitaDi = 1">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_NUMRI']/valoreUtente"/>
                    </xsl:if>
                    <xsl:if test="$qualitaDi = 5">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NUMRI']/valoreUtente"
                        />
                    </xsl:if>
                </numeroRi>
                <dataRi>
                    <xsl:if test="$qualitaDi = 1">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_DARARI']/valoreUtente"/>
                    </xsl:if>
                    <xsl:if test="$qualitaDi = 5">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_DATARI']/valoreUtente"
                        />
                    </xsl:if>
                </dataRi>
            </cameraCommercio>
        </xsl:if>
    </xsl:template>

    <xsl:template name="tribunale">
        <xsl:param name="qualitaDi"/>
        <xsl:if test="$qualitaDi = '1' or  $qualitaDi = '5'">
            <tribunale>
                <!-- Non c'è un campo per la descrizione del tribunale-->
                <descrizione>
                    <xsl:if test="$qualitaDi = '1'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_COMUNETRIB']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '5'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_COMUNETRIB']/valoreUtente"
                        />
                    </xsl:if>
                </descrizione>
                <luogo>
                    <xsl:if test="$qualitaDi = '1'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_COMUNETRIB']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '5'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_COMUNETRIB']/valoreUtente"
                        />
                    </xsl:if>
                </luogo>
                <numero>
                    <xsl:if test="$qualitaDi = '1'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_NUMEROTRIB']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '5'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NUMEROTRIB']/valoreUtente"
                        />
                    </xsl:if>
                </numero>
            </tribunale>
        </xsl:if>
    </xsl:template>

    <xsl:template name="rea">
        <xsl:param name="qualitaDi"/>
        <xsl:if test="$qualitaDi = '1' or  $qualitaDi = '5'">
            <rea>
                <!-- Non c'è un campo per la descrizione del tribunale-->
                <descrizione>
                    <xsl:if test="$qualitaDi = '1'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_PROVREA']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '5'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PROVREA']/valoreUtente"
                        />
                    </xsl:if>
                </descrizione>
                <luogo>
                    <xsl:if test="$qualitaDi = '1'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_PROVREA']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '5'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PROVREA']/valoreUtente"
                        />
                    </xsl:if>
                </luogo>
                <numero>
                    <xsl:if test="$qualitaDi = '1'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_NUMEROREA']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '5'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NUMREA']/valoreUtente"
                        />
                    </xsl:if>
                </numero>
                <data>
                    <xsl:if test="$qualitaDi = '1'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPSOC_DATAREA']/valoreUtente"
                        />
                    </xsl:if>
                    <xsl:if test="$qualitaDi = '5'">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_DATAREA']/valoreUtente"
                        />
                    </xsl:if>
                </data>
            </rea>
        </xsl:if>
    </xsl:template>

    <xsl:template name="legaleRappresentante">
        <legaleRappresentante>
            <nome>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NOMELEG']/valoreUtente"/>
            </nome>
            <cognome>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_COGNOMELEG']/valoreUtente"
                />
            </cognome>
            <sesso>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_SESSOLEG']/valoreUtente"
                />
            </sesso>
            <statoNascita/>
            <cittadinanza>
                <xsl:variable name="cittadinanzaLegaleRappresentante">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CITTADINLEG']/valoreUtente"
                    />
                </xsl:variable>
                <xsl:for-each
                    select="CampoAnagrafica[(campo_xml_mod='ANAG_PGIURIDICA_CITTADINLEG')]/opzioniCombo/OpzioneCombo">
                    <xsl:if test="Codice = $cittadinanzaLegaleRappresentante">
                        <xsl:value-of select="Etichetta"/>
                    </xsl:if>
                </xsl:for-each>
            </cittadinanza>
            <nazionalita>
                <xsl:variable name="nazionalitaLegaleRappresentante">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NAZ']/valoreUtente"/>
                </xsl:variable>
                <xsl:for-each
                    select="CampoAnagrafica[(campo_xml_mod='ANAG_PGIURIDICA_NAZ')]/opzioniCombo/OpzioneCombo">
                    <xsl:if test="Codice = $nazionalitaLegaleRappresentante">
                        <xsl:value-of select="Etichetta"/>
                    </xsl:if>
                </xsl:for-each>
            </nazionalita>
            <provinciaNascita>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NATOPROVLEG']/valoreUtente"
                />
            </provinciaNascita>
            <luogoNascita>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NATOALEG']/valoreUtente"
                />
            </luogoNascita>
            <dataNascita>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_NATOILLEG']/valoreUtente"
                />
            </dataNascita>
            <codiceFiscale>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CODFISCLEG']/valoreUtente"
                />
            </codiceFiscale>
            <residenza>
                <stato/>
                <cap>
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CAPLEG']/valoreUtente"
                    />
                </cap>
                <citta>
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CITTALEG']/valoreUtente"
                    />
                </citta>
                <provincia>
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_PROVLEG']/valoreUtente"
                    />
                </provincia>
                <via>
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_VIALEG']/valoreUtente"
                    />
                </via>
                <civico>
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CIVLEG']/valoreUtente"
                    />
                </civico>
                <colore/>
                <lettera/>
                <interno/>
            </residenza>
	     <carica>
		<xsl:variable name="nazionalitaLegaleRappresentante">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PGIURIDICA_CARICALEG']/valoreUtente"/>
                </xsl:variable>
                <xsl:for-each
                    select="CampoAnagrafica[(campo_xml_mod='ANAG_PGIURIDICA_CARICALEG')]/opzioniCombo/OpzioneCombo">
                    <xsl:if test="Codice = $nazionalitaLegaleRappresentante">
                        <xsl:value-of select="Etichetta"/>
                    </xsl:if>
                </xsl:for-each>
		</carica>
        </legaleRappresentante>
    </xsl:template>

    <xsl:template name="aziendaNoProfit">
        <xsl:param name="qualitaDi"/>
        <datiAziendaNoProfit>
            <regioneRegistro>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPENTE_COMUNEREGISTRO']/valoreUtente"
                />
            </regioneRegistro>
            <numeroRegistro>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_RAPPENTE_NUMEROREGISTRO']/valoreUtente"
                />
            </numeroRegistro>
            <xsl:call-template name="azienda">
                <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
            </xsl:call-template>
        </datiAziendaNoProfit>
    </xsl:template>

    <xsl:template name="personaGiuridica">
        <xsl:param name="qualitaDi"/>
        <personaGiuridica>
            <xsl:call-template name="azienda">
                <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
            </xsl:call-template>
        </personaGiuridica>
        <xsl:if test="$qualitaDi = '5'">
            <xsl:call-template name="persona">
                <xsl:with-param name="qualitaDi">51</xsl:with-param>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>

    <xsl:template name="professionista">
        <datiProfessionista>
            <dataIscrizioneAlbo>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_DATAALBO']/valoreUtente"/>
            </dataIscrizioneAlbo>
            <delega>
                <dataSottoscrizioneDelega>
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_SOTTOSCRITTA']/valoreUtente"
                    />
                </dataSottoscrizioneDelega>
                <descrizioneCausaleDelega>
                <xsl:variable name="causaleDelega">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_PRESENTAZIONE']/valoreUtente"
                    />
                </xsl:variable>
                <xsl:choose>
                    <xsl:when test="contains($causaleDelega, '#')">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_PRESENTAZIONEALTRO']/valoreUtente"/></xsl:when>
                    <xsl:otherwise>
                        <xsl:for-each
                            select="CampoAnagrafica[(campo_xml_mod='ANAG_PROF_PRESENTAZIONE')]/opzioniCombo/OpzioneCombo">
                            <xsl:if test="Codice = $causaleDelega"><xsl:value-of select="Etichetta"/></xsl:if>
                        </xsl:for-each>
                    </xsl:otherwise>
                </xsl:choose>
                </descrizioneCausaleDelega>
                <descrizioneTramitedelega>
                    <xsl:variable name="delega">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_TRAMITE']/valoreUtente"
                        />
                    </xsl:variable>
                    <xsl:choose>
                        <xsl:when test="contains($delega, '#')">
                            <xsl:value-of
                                select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_TRAMITEALTRO']/valoreUtente"
                            />
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:for-each
                                select="CampoAnagrafica[(campo_xml_mod='ANAG_PROF_TRAMITE')]/opzioniCombo/OpzioneCombo">
                                <xsl:if test="Codice = $delega">
                                    <xsl:value-of select="Etichetta"/>
                                </xsl:if>
                            </xsl:for-each>
                        </xsl:otherwise>
                    </xsl:choose>
                </descrizioneTramitedelega>
                <luogoOriginaleDelega>
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_CUSTODITA']/valoreUtente"/>
                </luogoOriginaleDelega>
            </delega>
            <descrizioneAlbo>
                <xsl:variable name="albo">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_ISCRITTOALBO']/valoreUtente"
                    />
                </xsl:variable>
                <xsl:choose>
                    <xsl:when test="contains($albo, '#')">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_ALTROALBO']/valoreUtente"
                        />
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:for-each
                            select="CampoAnagrafica[(campo_xml_mod='ANAG_PROF_ISCRITTOALBO')]/opzioniCombo/OpzioneCombo">
                            <xsl:if test="Codice = $albo">
                                <xsl:value-of select="Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:otherwise>
                </xsl:choose>
            </descrizioneAlbo>
            <descrizioneTipoProfessionista>
                <xsl:variable name="qualifica">
                    <xsl:value-of
                        select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_QUALIFICA']/valoreUtente"/>
                </xsl:variable>
                <xsl:choose>
                    <xsl:when test="contains($qualifica, '#')">
                        <xsl:value-of
                            select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_ALTRO']/valoreUtente"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:for-each
                            select="CampoAnagrafica[(campo_xml_mod='ANAG_PROF_QUALIFICA')]/opzioniCombo/OpzioneCombo">
                            <xsl:if test="Codice = $qualifica">
                                <xsl:value-of select="Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:otherwise>
                </xsl:choose>
            </descrizioneTipoProfessionista>
            <iscrittoCollegioOrdine>
                <xsl:value-of select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_COLLEGIO']/valoreUtente" />
            </iscrittoCollegioOrdine>
            <numeroIscrizione>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_NUMEROALBO']/valoreUtente"/>
            </numeroIscrizione>
            <numeroIscrizioneAlbo>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_NUMEROALBO']/valoreUtente"/>
            </numeroIscrizioneAlbo>
            <provinciaCollegioOrdine>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_PROVALBO']/valoreUtente"/>
            </provinciaCollegioOrdine>
            <provinciaIscrizioneAlbo>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_PROF_PROVALBO']/valoreUtente"/>
            </provinciaIscrizioneAlbo>
            <!-- dati mancanti -->
            <studio>
                <cap/>
                <citta/>
                <email/>
                <fax/>
                <numero/>
                <provincia/>
                <stato/>
                <telefono/>
                <via/>
            </studio>
            <codiceFiscale>
                <xsl:value-of
                    select="CampoAnagrafica[campo_xml_mod='ANAG_CODFISC_DICHIARANTE']/valoreUtente"
                />
            </codiceFiscale>
        </datiProfessionista>
    </xsl:template>

<xsl:template name="pagamento">
	<xsl:if test="/documentRoot/riepilogoOneri">
		<pagamento>
			<totaleOneri><xsl:value-of select="/documentRoot/riepilogoOneri/totale"/></totaleOneri>
			<modalitaPagamento><xsl:value-of select="/documentRoot/riepilogoOneri/modalitaPagamento"/></modalitaPagamento>
			<esitoPagamento>
			  <portaleID><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/PortaleID"/></portaleID>
			  <numeroOperazione><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/NumeroOperazione"/></numeroOperazione>
			  <idOrdine><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/IDOrdine"/></idOrdine>
			  <dataOraOrdine><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/DataOraOrdine"/></dataOraOrdine>
			  <idTransazione><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/IDTransazione"/></idTransazione>
			  <dataOraTransazione><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/DataOraTransazione"/></dataOraTransazione>
			  <sistemaPagamento><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/SistemaPagamento"/></sistemaPagamento>
			  <descrizioneSistemaPagamento><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/SistemaPagamentoD"/></descrizioneSistemaPagamento>
			  <circuitoAutorizzativo><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/CircuitoAutorizzativo"/></circuitoAutorizzativo>
			  <descrizioneCircuitoAutorizzativo><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/CircuitoAutorizzativoD"/></descrizioneCircuitoAutorizzativo>
			  <circuitoSelezionato><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/CircuitoSelezionato"/></circuitoSelezionato>
			  <descrizioneCircuitoSelezionato><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/CircuitoSelezionatoD"/></descrizioneCircuitoSelezionato>
			  <importoTransato><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/ImportoTransato"/></importoTransato>
			  <importoAutorizzato><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/ImportoAutorizzato"/></importoAutorizzato>
			  <importoCommissioni><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/ImportoCommissioni"/></importoCommissioni>
			  <codiceEsito><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/Esito"/></codiceEsito>
			  <descrizioneEsito><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/EsitoD"/></descrizioneEsito>
			  <dataOra><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/DataOra"/></dataOra>
			  <autorizzazione><xsl:value-of select="/documentRoot/riepilogoOneri/EsitoPagamento/Autorizzazione"/></autorizzazione>
			</esitoPagamento>
			<xsl:if test="/documentRoot/riepilogoOneri/OneriPagati">
			<oneriPagati>
				<totale><xsl:value-of select="/documentRoot/riepilogoOneri/OneriPagati/totale"/></totale>
				<xsl:for-each select="/documentRoot/riepilogoOneri/OneriPagati/onere">
				<onere>
						<aeCodiceEnte><xsl:value-of select="aeCodiceEnte" /></aeCodiceEnte>
						<aeCodiceUfficio><xsl:value-of select="aeCodiceUfficio" /></aeCodiceUfficio>
						<aeTipoUfficio><xsl:value-of select="aeTipoUfficio" /></aeTipoUfficio>
						<descrizione><xsl:value-of select="descrizione" /></descrizione>
						<importo><xsl:value-of select="importo" /></importo>
						<codiceDestinatario><xsl:value-of select="codiceDestinatario" /></codiceDestinatario>
						<descrizioneDestinatario><xsl:value-of select="descrizioneDestinatario" /></descrizioneDestinatario>
						<riversamentoAutomatico><xsl:value-of select="riversamentoAutomatico" /></riversamentoAutomatico>
				</onere>
				</xsl:for-each>
			</oneriPagati>
			</xsl:if>
		</pagamento>
	</xsl:if>
</xsl:template>
    
</xsl:stylesheet>
