<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs"
    xmlns:cea="http://gruppoinit.it/b110/ConcessioniEAutorizzazioni/procedimentoUnico"
    xmlns:ogg="http://egov.diviana.it/b109/OggettiCondivisi" xmlns:cross="http://www.wego.it/cross"
    xmlns="http://www.wego.it/cross">

    <xsl:template match="/cea:Richiesta">
        <xsl:variable name="indirizzoNotifica">NO</xsl:variable>
        <pratica>
            <id_pratica/>
            <id_procedimento_suap>
                <xsl:variable name="isProcedimentoUnico">
                    <xsl:value-of select="//cea:SportelloDestinatario/cea:flgPu"/>
                </xsl:variable>
                <xsl:variable name="codProcedimentoUnico">
                    <xsl:variable name="minimo">
                        <xsl:for-each select="//cea:Procedimenti/cea:procedimento">
                            <xsl:sort select="cea:flagProcedimento" data-type="number"/>
                            <xsl:if test="position()=1">
                                <xsl:value-of select="cea:flagProcedimento"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:variable>
                    <xsl:variable name="massimo">
                        <xsl:for-each select="//cea:Procedimenti/cea:procedimento">
                            <xsl:sort select="cea:flagProcedimento" data-type="number"
                                order="descending"/>
                            <xsl:if test="position()=1">
                                <xsl:value-of select="cea:flagProcedimento"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:variable>
                    <xsl:variable name="tipoProc">
                        <!-- La DIA è assimilata alla scia e ha valore 1 -->
                        <xsl:if test="$minimo = $massimo and $minimo = 1">SCIA</xsl:if>
                        <xsl:if test="$minimo = $massimo and $minimo = 2">SCIA</xsl:if>
                        <xsl:if test="not($minimo = $massimo and ($minimo = 1 or $minimo = 2))" >ORDINARIO</xsl:if>
                    </xsl:variable>

                    <xsl:variable name="identificativoProcedimento">
                        <xsl:if test="$tipoProc = 'SCIA'">EDILIZIA_AUTOMATIZZATO</xsl:if>
                        <xsl:if test="$tipoProc = 'ORDINARIO'">EDILIZIA_ORDINARIO</xsl:if>
                    </xsl:variable>
                    <xsl:value-of select="$identificativoProcedimento"/>
                </xsl:variable>
                <xsl:choose>
                    <xsl:when test="$isProcedimentoUnico='S'">
                        <xsl:value-of select="$codProcedimentoUnico"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="/cea:Richiesta/cea:RiepilogoDomanda/cea:Procedimenti/cea:procedimento[1]/cea:codiceProcedimento" />
                    </xsl:otherwise>
                </xsl:choose>
            </id_procedimento_suap>
            <identificativo_pratica>
                <xsl:value-of
                    select="//ogg:IdentificatorediRichiesta/ogg:IdentificatoreUnivoco/ogg:CodiceIdentificativoOperazione"
                />
            </identificativo_pratica>
            <oggetto/>
            <responsabile_procedimento>
                <xsl:value-of select="//cea:SportelloDestinatario/cea:Rup"/>
            </responsabile_procedimento>
            <istruttore/>
            <termini_evasione_pratica/>
            <xsl:if test="$indirizzoNotifica = 'SI'">
                <xsl:call-template name="notifica">
                    <xsl:with-param name="indirizzoNotifica" select="$indirizzoNotifica"/>
                </xsl:call-template>
            </xsl:if>
            <cod_catastale_comune>
                <xsl:value-of select="//cea:Ente/cea:CodBf"/>
            </cod_catastale_comune>
            <des_comune>
                <xsl:value-of select="//cea:Ente/cea:DesEnte"/>
            </des_comune>
            <id_ente>
                <xsl:value-of select="//cea:SportelloDestinatario/cea:CodiceSportello"/>
            </id_ente>
            <cod_ente>
                <xsl:value-of select="//cea:SportelloDestinatario/cea:CodiceSportello"/>
            </cod_ente>
            <des_ente>
                <xsl:value-of select="//cea:SportelloDestinatario/cea:DescrizioneSportello"/>
            </des_ente>
            <des_comune_ente>
                <xsl:value-of select="//cea:SportelloDestinatario/cea:Citta"/>
            </des_comune_ente>
            <indirizzo_ente>
                <xsl:value-of select="//cea:SportelloDestinatario/cea:Indirizzo"/>
            </indirizzo_ente>
            <cap_ente>
                <xsl:value-of select="//cea:SportelloDestinatario/cea:Cap"/>
            </cap_ente>
            <fax_ente>
                <xsl:value-of select="//cea:SportelloDestinatario/cea:Fax"/>
            </fax_ente>
            <email_ente>
                <xsl:value-of select="//cea:SportelloDestinatario/cea:Email"/>
            </email_ente>
            <pec_ente>
                <xsl:value-of select="//cea:SportelloDestinatario/cea:Pec"/>
            </pec_ente>
            <telefono_ente>
                <xsl:value-of select="//cea:SportelloDestinatario/cea:Telefono"/>
            </telefono_ente>
            <id_protocollo_manuale/>
            <registro/>
            <protocollo/>
            <fascicolo/>
            <anno/>
            <data_protocollo/>
            <data_ricezione/>
            <xsl:for-each select="//cea:Anagrafica">
                <xsl:variable name="contatoreGlobale" select="position() - 1"/>
                <xsl:call-template name="anagrafiche">
                    <xsl:with-param name="contatoreGlobale" select="$contatoreGlobale"/>
                </xsl:call-template>
            </xsl:for-each>
            <!-- Lo inizializzo a 10 per evitare conflitti derivanti dalle anagrafiche "standard" -->
            <xsl:variable name="altriDichiarantiCounter">10</xsl:variable>
            <xsl:for-each select="//cea:AnagraficaAltriDichiaranti">
                <!-- Ogni nuova anagrafica sarà distante 10 posizioni dalla precedente -->
                <xsl:variable name="contatoreGlobale" select="position() * $altriDichiarantiCounter"/>
                <xsl:call-template name="anagrafiche">
                    <xsl:with-param name="contatoreGlobale" select="$contatoreGlobale"/>
                </xsl:call-template>
            </xsl:for-each>
            <xsl:if test="cea:RiepilogoDomanda/cea:Procedimenti != ''">
                <procedimenti>
                    <xsl:for-each select="cea:RiepilogoDomanda/cea:Procedimenti">
                        <xsl:for-each select="cea:procedimento">
                            <xsl:call-template name="procedimento"/>
                        </xsl:for-each>
                    </xsl:for-each>
                </procedimenti>
            </xsl:if>
            <eventi/>
        </pratica>
    </xsl:template>

    <xsl:template name="anagrafiche">
        <xsl:param name="contatoreGlobale"/>

        <xsl:variable name="qualitaDiDescrizione">
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_1']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_1']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_1']/cea:descrizione"
                />
            </xsl:if>
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_5']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_5']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_5']/cea:descrizione"
                />
            </xsl:if>
        </xsl:variable>

        <xsl:variable name="qualitaDi">
            <!-- Richiedente-->
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_1']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_1']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_1']/cea:valoreUtente"
                />
            </xsl:if>
            <!-- Procuratore -->
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_2']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_2']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_2']/cea:valoreUtente"
                />
            </xsl:if>
        </xsl:variable>

        <xsl:variable name="tipoPersonaRichiedente">
            <!-- Persona fisica - Richiedente -->
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RIC_PF']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RIC_PF']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RIC_PF']/cea:valoreUtente"/>
            </xsl:if>
            <!-- Persona giuridica - Richiedente -->
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RIC_PG']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RIC_PG']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RIC_PG']/cea:valoreUtente"/>
            </xsl:if>
            <!-- Persona fisica - Procuratore -->
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DEL_PF']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DEL_PF']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DEL_PF']/cea:valoreUtente"/>
            </xsl:if>
            <!-- Persona giuridica - Procuratore -->
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DEL_PG']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DEL_PG']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DEL_PG']/cea:valoreUtente"/>
            </xsl:if>
        </xsl:variable>

        <xsl:variable name="dittaIndividualeRichiedenteVar">
            <xsl:choose>
                <xsl:when
                    test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FORGIU']/cea:valoreUtente='00330'"
                    >S</xsl:when>
                <xsl:when
                    test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FORGIU']/cea:valoreUtente='00380'"
                    >S</xsl:when>
                <xsl:when
                    test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FORGIU']/cea:valoreUtente='00390'"
                    >S</xsl:when>
                <xsl:otherwise>N</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="dittaIndividualeProcuraSpecialeVar">
            <xsl:choose>
                <xsl:when
                    test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FORGIU']/cea:valoreUtente='00330'"
                    >S</xsl:when>
                <xsl:when
                    test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FORGIU']/cea:valoreUtente='00380'"
                    >S</xsl:when>
                <xsl:when
                    test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FORGIU']/cea:valoreUtente='00390'"
                    >S</xsl:when>
                <xsl:otherwise>N</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:if test="$qualitaDi = 1">
            <!-- Richiedente -->
            <xsl:if test="$tipoPersonaRichiedente = '3'">
                <anagrafiche>
                    <xsl:variable name="tipo">Default</xsl:variable>
                    <xsl:call-template name="anagrafica">
                        <xsl:with-param name="tipo" select="$tipo"/>
                        <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
                        <xsl:with-param name="counter" select="$contatoreGlobale + 1"/>
                    </xsl:call-template>
                    <id_tipo_ruolo>1</id_tipo_ruolo>
                    <cod_tipo_ruolo>R</cod_tipo_ruolo>
                    <des_tipo_ruolo>Richiedente</des_tipo_ruolo>
                    <data_inizio_validita/>
                    <id_tipo_qualifica/>
                    <des_tipo_qualifica/>
                    <descrizione_titolarita/>
                    <assenso_uso_pec/>
                    <pec/>
                </anagrafiche>
                <!-- Il richiedente è anche il beneficiario -->
                <anagrafiche>
                    <xsl:variable name="tipo">Default</xsl:variable>
                    <xsl:call-template name="anagrafica">
                        <xsl:with-param name="tipo" select="$tipo"/>
                        <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
                        <xsl:with-param name="counter" select="$contatoreGlobale + 2"/>
                    </xsl:call-template>
                    <id_tipo_ruolo>2</id_tipo_ruolo>
                    <cod_tipo_ruolo>B</cod_tipo_ruolo>
                    <des_tipo_ruolo>Beneficiario</des_tipo_ruolo>
                    <data_inizio_validita/>
                    <id_tipo_qualifica/>
                    <des_tipo_qualifica/>
                    <descrizione_titolarita/>
                    <assenso_uso_pec/>
                    <pec/>
                </anagrafiche>
            </xsl:if>
            <!-- anagrafica azienda -->
            <xsl:if test="$tipoPersonaRichiedente = '4'">
                <!-- Richiedente -->
                <anagrafiche>
                    <xsl:variable name="tipo">Default</xsl:variable>
                    <xsl:call-template name="anagrafica">
                        <xsl:with-param name="tipo" select="$tipo"/>
                        <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
                        <xsl:with-param name="counter" select="$contatoreGlobale + 1"/>
                    </xsl:call-template>
                    <id_tipo_ruolo>1</id_tipo_ruolo>
                    <cod_tipo_ruolo>R</cod_tipo_ruolo>
                    <des_tipo_ruolo>Richiedente</des_tipo_ruolo>
                    <data_inizio_validita/>
                    <id_tipo_qualifica>4</id_tipo_qualifica>
                    <des_tipo_qualifica>Legale rappresentante o delegato avente
                        titolo</des_tipo_qualifica>
                    <descrizione_titolarita/>
                    <assenso_uso_pec/>
                    <pec/>
                </anagrafiche>
                <xsl:choose>
                    <xsl:when test="$dittaIndividualeRichiedenteVar='S'">
                        <anagrafiche>
                            <xsl:variable name="tipo">DittaIndividuale</xsl:variable>
                            <xsl:call-template name="anagrafica">
                                <xsl:with-param name="tipo" select="$tipo"/>
                                <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
                                <xsl:with-param name="counter" select="$contatoreGlobale + 2"/>
                            </xsl:call-template>
                            <id_tipo_ruolo>2</id_tipo_ruolo>
                            <cod_tipo_ruolo>B</cod_tipo_ruolo>
                            <des_tipo_ruolo>Beneficiario</des_tipo_ruolo>
                            <data_inizio_validita/>
                            <id_tipo_qualifica/>
                            <des_tipo_qualifica/>
                            <descrizione_titolarita/>
                            <assenso_uso_pec/>
                            <pec/>
                        </anagrafiche>
                    </xsl:when>
                    <xsl:otherwise>
                        <anagrafiche>
                            <xsl:variable name="tipo">PersonaGiuridica</xsl:variable>
                            <xsl:call-template name="anagrafica">
                                <xsl:with-param name="tipo" select="$tipo"/>
                                <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
                                <xsl:with-param name="counter" select="$contatoreGlobale + 2"/>
                            </xsl:call-template>
                            <id_tipo_ruolo>2</id_tipo_ruolo>
                            <cod_tipo_ruolo>B</cod_tipo_ruolo>
                            <des_tipo_ruolo>Beneficiario</des_tipo_ruolo>
                            <data_inizio_validita/>
                            <id_tipo_qualifica/>
                            <des_tipo_qualifica/>
                            <descrizione_titolarita/>
                            <assenso_uso_pec/>
                            <pec/>
                        </anagrafiche>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
        </xsl:if>
        <!-- Procuratore -->
        <xsl:if test="$qualitaDi = 2">
            <xsl:if test="$tipoPersonaRichiedente = '5'">
                <!-- Richiedente -->
                <anagrafiche>
                    <xsl:variable name="tipo">PersonaFisicaProcuratore</xsl:variable>
                    <xsl:call-template name="anagrafica">
                        <xsl:with-param name="tipo" select="$tipo"/>
                        <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
                        <xsl:with-param name="counter" select="$contatoreGlobale + 1"/>
                    </xsl:call-template>
                    <id_tipo_ruolo>1</id_tipo_ruolo>
                    <cod_tipo_ruolo>R</cod_tipo_ruolo>
                    <des_tipo_ruolo>Richiedente</des_tipo_ruolo>
                    <data_inizio_validita/>
                    <id_tipo_qualifica/>
                    <des_tipo_qualifica/>
                    <descrizione_titolarita/>
                    <assenso_uso_pec/>
                    <pec/>
                </anagrafiche>
                <!-- Il richiedente è anche il beneficiario -->
                <anagrafiche>
                    <xsl:variable name="tipo">PersonaFisicaProcuratore</xsl:variable>
                    <xsl:call-template name="anagrafica">
                        <xsl:with-param name="tipo" select="$tipo"/>
                        <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
                        <xsl:with-param name="counter" select="$contatoreGlobale + 2"/>
                    </xsl:call-template>
                    <id_tipo_ruolo>2</id_tipo_ruolo>
                    <cod_tipo_ruolo>B</cod_tipo_ruolo>
                    <des_tipo_ruolo>Beneficiario</des_tipo_ruolo>
                    <data_inizio_validita/>
                    <id_tipo_qualifica/>
                    <des_tipo_qualifica/>
                    <descrizione_titolarita/>
                    <assenso_uso_pec/>
                    <pec/>
                </anagrafiche>
            </xsl:if>
            <xsl:if test="$tipoPersonaRichiedente = '6'">
                <!-- Legale rappresentante -->
                <anagrafiche>
                    <xsl:variable name="tipo">LegaleRappresentanteProcuratore</xsl:variable>
                    <xsl:call-template name="anagrafica">
                        <xsl:with-param name="tipo" select="$tipo"/>
                        <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
                        <xsl:with-param name="counter" select="$contatoreGlobale + 1"/>
                    </xsl:call-template>
                    <id_tipo_ruolo>1</id_tipo_ruolo>
                    <cod_tipo_ruolo>R</cod_tipo_ruolo>
                    <des_tipo_ruolo>Richiedente</des_tipo_ruolo>
                    <data_inizio_validita/>
                    <id_tipo_qualifica/>
                    <des_tipo_qualifica/>
                    <descrizione_titolarita/>
                    <assenso_uso_pec/>
                    <pec/>
                </anagrafiche>
                <!-- Azienda -->
                <xsl:choose>
                    <xsl:when test="$dittaIndividualeProcuraSpecialeVar='S'">
                        <anagrafiche>
                            <contatoreGlobale>
                                <xsl:value-of select="$contatoreGlobale"/>
                            </contatoreGlobale>
                            <xsl:variable name="tipo">ProcuraDittaIndividuale</xsl:variable>
                            <xsl:call-template name="anagrafica">
                                <xsl:with-param name="tipo" select="$tipo"/>
                                <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
                                <xsl:with-param name="counter" select="$contatoreGlobale + 2"/>
                            </xsl:call-template>
                            <id_tipo_ruolo>2</id_tipo_ruolo>
                            <cod_tipo_ruolo>B</cod_tipo_ruolo>
                            <des_tipo_ruolo>Beneficiario</des_tipo_ruolo>
                            <data_inizio_validita/>
                            <id_tipo_qualifica/>
                            <des_tipo_qualifica/>
                            <descrizione_titolarita/>
                            <assenso_uso_pec/>
                            <pec/>
                        </anagrafiche>
                    </xsl:when>
                    <xsl:otherwise>
                        <anagrafiche>
                            <contatoreGlobale>
                                <xsl:value-of select="$contatoreGlobale"/>
                            </contatoreGlobale>
                            <xsl:variable name="tipo">ProcuraPersonaGiuridica</xsl:variable>
                            <xsl:call-template name="anagrafica">
                                <xsl:with-param name="tipo" select="$tipo"/>
                                <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
                                <xsl:with-param name="counter" select="$contatoreGlobale + 2"/>
                            </xsl:call-template>
                            <id_tipo_ruolo>2</id_tipo_ruolo>
                            <cod_tipo_ruolo>B</cod_tipo_ruolo>
                            <des_tipo_ruolo>Beneficiario</des_tipo_ruolo>
                            <data_inizio_validita/>
                            <id_tipo_qualifica/>
                            <des_tipo_qualifica/>
                            <descrizione_titolarita/>
                            <assenso_uso_pec/>
                            <pec/>
                        </anagrafiche>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
        </xsl:if>
    </xsl:template>

    <xsl:template name="anagrafica">
        <xsl:param name="tipo"/>
        <xsl:param name="qualitaDi"/>
        <xsl:param name="counter"/>
        <anagrafica>
            <counter>
                <xsl:value-of select="$counter"/>
            </counter>
            <confermata/>
            <id_anagrafica/>
            <tipo_anagrafica>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">F</xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">F</xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">G</xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">F</xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">F</xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">F</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">G</xsl:when>
                </xsl:choose>
            </tipo_anagrafica>
            <variante_anagrafica>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">I</xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">I</xsl:when>
                </xsl:choose>
            </variante_anagrafica>
            <cognome>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COGNOME']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_COGNOMELEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_COG_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </cognome>
            <denominazione>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_DENOM']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_DENOM']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_DENOM_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_DENOM_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </denominazione>
            <nome>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_NOME']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_NOMELEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NOME_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </nome>
            <codice_fiscale>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_CF']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_CODFISC']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_CODFISC']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_CODFISCLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CODFISC_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CODFISC_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CODFISC_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </codice_fiscale>
            <partita_iva>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_PIVA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_PIVA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PIVA_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PIVA_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </partita_iva>
            <flg_individuale/>
            <data_nascita>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_DATANASCITA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_NATOILLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_DTNASC_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </data_nascita>
            <id_cittadinanza/>
            <cod_cittadinanza>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_CIT']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_CITTADINLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CIT_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </cod_cittadinanza>
            <des_cittadinanza>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:variable name="cittadinanza">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_CIT']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_DICHIARANTE_CIT')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $cittadinanza">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:variable name="cittadinanza">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_CITTADINLEG']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_PFISICA_CITTADINLEG')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $cittadinanza">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:variable name="cittadinanza">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CIT_LEG']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_PGIURIDICA_CIT_LEG')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $cittadinanza">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                </xsl:choose>
            </des_cittadinanza>
            <id_nazionalita/>
            <cod_nazionalita>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_NAZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_NAZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NAZ_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </cod_nazionalita>
            <des_nazionalita>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:variable name="nazionalita">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_NAZ']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_DICHIARANTE_NAZ')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $nazionalita">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:variable name="nazionalita">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_NAZ']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_PFISICA_NAZ')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $nazionalita">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:variable name="nazionalita">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NAZ_LEG']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_PGIURIDICA_NAZ_LEG')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $nazionalita">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                </xsl:choose>
            </des_nazionalita>
            <id_comune_nascita/>
            <des_comune_nascita>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COMUNENASCITA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_NATOALEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NATOA_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </des_comune_nascita>
            <id_provincia_nascita/>
            <des_provincia_nascita>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_PROVNASCITA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_NATOPROVLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROV_NASC_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </des_provincia_nascita>
            <id_stato_nascita/>
            <des_stato_nascita/>
            <localita_nascita/>
            <sesso>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_SESSO']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_SESSOLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_SESSO_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </sesso>
            <id_tipo_collegio/>
            <des_tipo_collegio/>
            <numero_iscrizione/>
            <data_iscrizione/>
            <id_provincia_iscrizione/>
            <des_provincia_iscrizione/>
            <id_provincia_cciaa/>
            <des_provincia_cciaa>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:variable name="provinciaRea">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_PROVREA']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_RAPPSOC_PROVREA')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $provinciaRea">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:variable name="provinciaRea">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_PROVREA']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_RAPPSOC_PROVREA')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $provinciaRea">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:variable name="provinciaRea">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROVREA']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_PGIURIDICA_PROVREA')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $provinciaRea">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:variable name="provinciaRea">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROVREA']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_PGIURIDICA_PROVREA')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $provinciaRea">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                </xsl:choose>
            </des_provincia_cciaa>
            <flg_attesa_iscrizione_ri>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:choose>
                            <xsl:when
                                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_NUMRI']/cea:valoreUtente != ''"
                                >N</xsl:when>
                            <xsl:otherwise>S</xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:choose>
                            <xsl:when
                                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_NUMRI']/cea:valoreUtente != ''"
                                >N</xsl:when>
                            <xsl:otherwise>S</xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:choose>
                            <xsl:when
                                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMRI']/cea:valoreUtente!= ''"
                                >N</xsl:when>
                            <xsl:otherwise>S</xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:choose>
                            <xsl:when
                                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMRI']/cea:valoreUtente != ''"
                                >N</xsl:when>
                            <xsl:otherwise>S</xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                </xsl:choose>
            </flg_attesa_iscrizione_ri>
            <flg_obbligo_iscrizione_ri/>
            <data_iscrizione_ri>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_DARARI']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_DARARI']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_DARARI']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_DARARI']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </data_iscrizione_ri>
            <n_iscrizione_ri>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_NUMRI']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_NUMRI']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMRI']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMRI']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </n_iscrizione_ri>
            <flg_attesa_iscrizione_rea>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:choose>
                            <xsl:when
                                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_NUMEROREA']/cea:valoreUtente != ''"
                                >N</xsl:when>
                            <xsl:otherwise>S</xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:choose>
                            <xsl:when
                                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_NUMEROREA']/cea:valoreUtente != ''"
                                >N</xsl:when>
                            <xsl:otherwise>S</xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:choose>
                            <xsl:when
                                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMEROREA']/cea:valoreUtente != ''"
                                >N</xsl:when>
                            <xsl:otherwise>S</xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:choose>
                            <xsl:when
                                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMEROREA']/cea:valoreUtente != ''"
                                >N</xsl:when>
                            <xsl:otherwise>S</xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                </xsl:choose>
            </flg_attesa_iscrizione_rea>
            <data_iscrizione_rea>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_DATAREA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_DATAREA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_DATAREA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_DATAREA']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </data_iscrizione_rea>
            <n_iscrizione_rea>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_NUMEROREA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_NUMEROREA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMEROREA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMEROREA']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </n_iscrizione_rea>
            <id_forma_giuridica>
                <xsl:variable name="formaGiuridicaId">
                    <xsl:choose>
                        <xsl:when test="$tipo='DittaIndividuale'">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FORGIU']/cea:valoreUtente"
                            />
                        </xsl:when>
                        <xsl:when test="$tipo='PersonaGiuridica'">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FORGIU']/cea:valoreUtente"
                            />
                        </xsl:when>
                        <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FORGIU_AZ']/cea:valoreUtente"
                            />
                        </xsl:when>
                        <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FORGIU_AZ']/cea:valoreUtente"
                            />
                        </xsl:when>
                    </xsl:choose>
                </xsl:variable>
                <xsl:if test="$formaGiuridicaId != ''">
                    <xsl:choose>
                        <xsl:when test="$formaGiuridicaId = '00010'">39</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00020'">30</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00030'">34</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00040'">62</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00050'">40</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00060'">31</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00070'">20</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00080'">25</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00090'">41</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00100'">48</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00110'">24</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00120'">23</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00130'">53</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00140'">47</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00150'">32</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00160'">44</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00170'">45</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00180'">33</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00190'">18</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00200'">55</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00210'">60</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00220'">58</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00230'">28</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00240'">57</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00250'">56</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00260'">59</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00270'">61</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00280'">22</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00290'">26</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00300'">27</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00310'">21</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00330'">2</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00340'">35</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00350'">49</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00360'">51</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00370'">52</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00380'">4</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00390'">3</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00400'">38</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00410'">54</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00420'">36</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00430'">37</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00440'">43</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00450'">42</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00460'">11</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00470'">12</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00480'">19</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00490'">14</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00500'">15</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00510'">16</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00520'">9</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00530'">17</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00540'">29</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00550'">13</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00560'">7</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00570'">6</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00580'">10</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00590'">5</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00600'">8</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00610'">46</xsl:when>
                        <xsl:when test="$formaGiuridicaId = '00620'">50</xsl:when>
                    </xsl:choose>
                </xsl:if>
            </id_forma_giuridica>
            <des_forma_giuridica>
                <xsl:variable name="formaGiuridicaId">
                    <xsl:choose>
                        <xsl:when test="$tipo='DittaIndividuale'">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FORGIU']/cea:valoreUtente"
                            />
                        </xsl:when>
                        <xsl:when test="$tipo='PersonaGiuridica'">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FORGIU']/cea:valoreUtente"
                            />
                        </xsl:when>
                        <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FORGIU_AZ']/cea:valoreUtente"
                            />
                        </xsl:when>
                        <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FORGIU_AZ']/cea:valoreUtente"
                            />
                        </xsl:when>
                    </xsl:choose>
                </xsl:variable>
                <xsl:choose>
                    <xsl:when test="$formaGiuridicaId = '00010'">Agenzia dello StatoSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00020'">Altra forma di ente privato con
                        personalità giuridicaSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00030'">Altra forma di ente privato senza
                        personalità giuridicaSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00040'">Altro ente pubblico non economico
                        nazionaleSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00050'">Archivio notarileSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00060'">Associazione non
                        riconosciutaSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00070'">Associazione o raggruppamento
                        temporaneo di impreseSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00080'">Associazione riconosciutaSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00090'">Autorità indipendentiSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00100'">Azienda o ente del servizio
                        sanitario nazionaleSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00110'">Azienda pubblica di servizi alle
                        persone ai sensi del d.lgs n. 207/2001Società in accomandita per
                        azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00120'">Azienda speciale ai sensi del t.u.
                        267/2000Società in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00130'">Camera di commercioSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00140'">Città metropolitanaSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00150'">ComitatoSocietà in accomandita per
                        azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00160'">ComuneSocietà in accomandita per
                        azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00170'">Comunità montana o isolanaSocietà
                        in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00180'">CondominioSocietà in accomandita
                        per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00190'">Consorzio di diritto privatoSocietà
                        in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00200'">Consorzio di diritto
                        pubblicoSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00210'">Ente ambientale regionaleSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00220'">Ente di sviluppo agricolo regionale
                        o di altro ente localeSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00230'">Ente ecclesiasticoSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00240'">Ente o autorità portualeSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00250'">Ente parcoSocietà in accomandita
                        per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00260'">Ente per il turismoSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00270'">Ente per la ricerca e per
                        l’aggiornamento educativoSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00280'">Ente pubblico economicoSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00290'">Fondazione (esclusa fondazione
                        bancaria)Società in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00300'">Fondazione bancariaSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00310'">Gruppo europeo di interesse
                        economicoSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00330'">Imprenditore individuale non
                        agricoloSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00340'">Impresa o ente privato costituito
                        all'estero non altrimenti classificabile che svolge una attività economica
                        in ItaliaSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00350'">Istituto e scuola pubblica di ogni
                        ordine e gradoSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00360'">Istituto o ente pubblico di
                        ricercaSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00370'">Istituto pubblico di assistenza e
                        beneficenzaSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00380'">Lavoratore autonomoSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00390'">Libero professionistaSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00400'">MinisteroSocietà in accomandita per
                        azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00410'">Ordine e collegio
                        professionaleSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00420'">Organo costituzionale o a rilevanza
                        costituzionaleSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00430'">Presidenza del consiglioSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00440'">ProvinciaSocietà in accomandita per
                        azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00450'">RegioneSocietà in accomandita per
                        azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00460'">Società a responsabilità
                        limitataSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00470'">Società a responsabilità limitata
                        con un unico socioSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00480'">Società consortileSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00490'">Società cooperativa a mutualità
                        prevalenteSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00500'">Società cooperativa diversaSocietà
                        in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00510'">Società cooperativa socialeSocietà
                        in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00520'">Società di fatto o irregolare,
                        comunione ereditariaSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00530'">Società di mutua
                        assicurazioneSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00540'">Società di mutuo soccorsoSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00550'">Società in accomandita per
                        azioniSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00560'">Società in accomandita
                        sempliceSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00570'">Società in nome collettivoSocietà
                        in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00580'">Società per azioniSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00590'">Società sempliceSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00600'">Studio associato e società di
                        professionistiSocietà in accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00610'">Unione di comuniSocietà in
                        accomandita per azioni</xsl:when>
                    <xsl:when test="$formaGiuridicaId = '00620'">Università pubblicaSocietà in
                        accomandita per azioni</xsl:when>
                </xsl:choose>
            </des_forma_giuridica>
            <recapiti>
                <xsl:call-template name="recapito">
                    <xsl:with-param name="tipo" select="$tipo"/>
                </xsl:call-template>
            </recapiti>
        </anagrafica>
    </xsl:template>

    <xsl:template name="recapito">
        <xsl:param name="tipo"/>
        <recapito>
            <counter>
                <xsl:value-of select="position()"/>
            </counter>
            <id_comune/>
            <des_comune>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COMUNERES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_COMUNESEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_COMUNESEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_CITTALEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CITTA_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_COMUNESEDE_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_COMUNESEDE_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </des_comune>
            <localita/>
            <id_provincia/>
            <des_provincia>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_PROVRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_PROVSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_PROVSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_PROVLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROV_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROV_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROV_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </des_provincia>
            <id_stato/>
            <des_stato/>
            <id_dug/>
            <indirizzo>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_VIARES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_INDIR']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_INDIR']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_VIALEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_VIA_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_VIA_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_VIA_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </indirizzo>
            <codice_via/>
            <n_civico>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_CIV']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_NUMEROCIVICO']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_NUMEROCIVICO']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_CIVLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMEROCIVICO_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMEROCIVICO_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIFICA_CIV_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </n_civico>
            <codice_civico/>
            <interno_numero/>
            <interno_lettera/>
            <interno_scala/>
            <lettera/>
            <colore/>
            <cap>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_CAPRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_CAPLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_CAPSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_CAPSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CAP_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CAPSEDE_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CAPSEDE_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </cap>
            <casella_postale/>
            <altre_info_indirizzo/>
            <telefono>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_TELRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_TELLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_TELSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_TELSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_TELSEDE_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_TELSEDE_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_TEL_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </telefono>
            <cellulare/>
            <fax>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_FAXRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_FAXLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FAXSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FAXSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FAXSEDE_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FAXSEDE_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FAX_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </fax>
            <email>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_EMAIL']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_MAILLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_MAIL']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_MAIL']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_MAIL_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_MAIL_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_MAIL_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </email>
            <pec>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_PEC']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_PECLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_PEC']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_PEC']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PEC_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PEC_AZ']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PEC_LEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </pec>
            <id_tipo_indirizzo>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">1</xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">3</xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">3</xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">1</xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">1</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">3</xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">3</xsl:when>
                </xsl:choose>
            </id_tipo_indirizzo>
            <des_tipo_indirizzo>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">RESIDENZA</xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">SEDE</xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">SEDE</xsl:when>
                    <xsl:when test="$tipo='PersonaFisicaProcuratore'">RESIDENZA</xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentanteProcuratore'">RESIDENZA</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">SEDE</xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">SEDE</xsl:when>
                </xsl:choose>
            </des_tipo_indirizzo>
        </recapito>
    </xsl:template>

    <xsl:template name="notifica">
        <notifica>
            <id_comune/>
            <des_comune>
                <xsl:value-of
                    select="//cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome='di004')]/cea:valoreUtente"
                />
            </des_comune>
            <localita/>
            <id_provincia/>
            <des_provincia>
                <xsl:value-of
                    select="//cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome='di005')]/cea:valoreUtente"
                />
            </des_provincia>
            <id_stato/>
            <des_stato>
                <xsl:value-of
                    select="//cea:DichiarazioniDinamiche/cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome='di006')]/cea:valoreUtente"
                />
            </des_stato>
            <id_dug/>
            <indirizzo>
                <xsl:value-of
                    select="../cea:DichiarazioniDinamiche/cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome='di007')]/cea:valoreUtente"
                />
            </indirizzo>
            <n_civico>
                <xsl:value-of
                    select="//cea:DichiarazioniDinamiche/cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome='di008')]/cea:valoreUtente"
                />
            </n_civico>
            <cap>
                <xsl:value-of
                    select="//cea:DichiarazioniDinamiche/cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome='di009')]/cea:valoreUtente"
                />
            </cap>
            <casella_postale/>
            <altre_info_indirizzo/>
            <telefono>
                <xsl:value-of
                    select="//cea:DichiarazioniDinamiche/cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome='di011')]/cea:valoreUtente"
                />
            </telefono>
            <cellulare>
                <xsl:value-of
                    select="//cea:DichiarazioniDinamiche/cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome='di012')]/cea:valoreUtente"
                />
            </cellulare>
            <fax>
                <xsl:value-of
                    select="//cea:DichiarazioniDinamiche/cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome='di013')]/cea:valoreUtente"
                />
            </fax>
            <email>
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_EMAIL_DICHIARANTE']/cea:valoreUtente"
                />
            </email>
            <pec>
                <xsl:value-of
                    select="//cea:DichiarazioniDinamiche/cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome='di010')]/cea:valoreUtente"
                />
            </pec>
            <id_tipo_indirizzo>4</id_tipo_indirizzo>
            <des_tipo_indirizzo>NOTIFICA</des_tipo_indirizzo>
        </notifica>
    </xsl:template>

    <xsl:template name="procedimento">
        <xsl:variable name="codiceDestinatario">
            <xsl:value-of select="cea:codiceDestinatario"/>
        </xsl:variable>
        <procedimento>
            <id_procedimento/>
            <cod_procedimento>
                <xsl:value-of select="cea:codiceProcedimento"/>
            </cod_procedimento>
            <termini>
                <xsl:value-of select="cea:terminiEvasione"/>
            </termini>
            <des_procedimento>
                <xsl:value-of select="cea:nome"/>
            </des_procedimento>
            <cod_lang/>
            <id_ente_destinatario/>
            <cod_ente_destinatario>
                <xsl:value-of select="cea:codiceDestinatario"/>
            </cod_ente_destinatario>
            <des_ente_destinatario>
                <xsl:for-each select="//cea:Destinatari/cea:Destinatario">
                    <xsl:if test="$codiceDestinatario = cea:codiceDestinatario ">
                        <xsl:value-of select="cea:Nome"/>
                    </xsl:if>
                </xsl:for-each>
            </des_ente_destinatario>
        </procedimento>
    </xsl:template>

</xsl:stylesheet>
