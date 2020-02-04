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
                    <xsl:if test="$minimo = $massimo and $minimo = 1">SCIA</xsl:if>
                    <xsl:if test="not($minimo = $massimo and $minimo = 1)">ORDINARIO</xsl:if>
                </xsl:variable>

                <xsl:variable name="sportelloDestinazione">
                    <xsl:value-of select="//cea:SportelloDestinatario/cea:CodiceSportello"/>
                </xsl:variable>

                <xsl:variable name="identificativoProcedimento">
                    <!-- Commercio -->
                    <xsl:if test="$sportelloDestinazione = '1038'">
                        <xsl:if test="$tipoProc = 'SCIA'">COMMERCIO_AUTOMATIZZATO</xsl:if>
                        <xsl:if test="$tipoProc = 'ORDINARIO'">COMMERCIO_ORDINARIO</xsl:if>    
                    </xsl:if>
                    <!-- Edilizia -->
                    <xsl:if test="$sportelloDestinazione = '800'">
                        <xsl:if test="$tipoProc = 'SCIA'">EDILIZIA_AUTOMATIZZATO</xsl:if>
                        <xsl:if test="$tipoProc = 'ORDINARIO'">EDILIZIA_ORDINARIO</xsl:if>    
                    </xsl:if>
                    <!-- Servizi -->
                    <xsl:if test="$sportelloDestinazione = '700'">SERVIZI_CITTADINI</xsl:if>
                </xsl:variable>
                <xsl:value-of select="$identificativoProcedimento"/>
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
                <xsl:value-of select="//cea:Ente/cea:CodIstat"/>
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
                <!-- Ogni nuova anagrafica sarÃƒÂ  distante 10 posizioni dalla precedente -->
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
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_2']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_2']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_2']/cea:descrizione"
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
            <!-- Rappresentante di ditta individuale-->
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_1']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_1']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_1']/cea:valoreUtente"
                />
            </xsl:if>
            <!-- Conto proprio -->
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_2']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_2']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_2']/cea:valoreUtente"
                />
            </xsl:if>
            <!-- Procura speciale -->
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_5']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_5']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_5']/cea:valoreUtente"
                />
            </xsl:if>
        </xsl:variable>

        <xsl:variable name="dittaIndividualeVar">
            <xsl:choose>
                <xsl:when
                    test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_MOTIVAZIONE']/cea:valoreUtente='TI'"
                    >S</xsl:when>
                <xsl:otherwise>N</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:if test="$qualitaDi = 1">
            <!-- Rappresentante di societÃƒÂ /ditta individuale -->
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
            <!-- anagrafica azienda -->
            <xsl:choose>
                <xsl:when test="$dittaIndividualeVar='S'">
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
        <xsl:if test="$qualitaDi = 2">
            <!-- Per conto proprio -->
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
        <xsl:if test="$qualitaDi = 5">
            <!-- in qualitÃƒÂ  di professionista munito di procura speciale -->
            <anagrafiche>
                <contatoreGlobale>
                    <xsl:value-of select="$contatoreGlobale"/>
                </contatoreGlobale>
                <xsl:variable name="tipo">Professionista</xsl:variable>
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
            <!-- anagrafica persona giuridica -->
            <xsl:choose>
                <xsl:when test="$dittaIndividualeVar='S'">
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
            <anagrafiche>
                <contatoreGlobale>
                    <xsl:value-of select="$contatoreGlobale"/>
                </contatoreGlobale>
                <xsl:variable name="tipo">LegaleRappresentante</xsl:variable>
                <xsl:call-template name="anagrafica">
                    <xsl:with-param name="tipo" select="$tipo"/>
                    <xsl:with-param name="qualitaDi" select="$qualitaDi"/>
                    <xsl:with-param name="counter" select="$contatoreGlobale + 3"/>
                </xsl:call-template>
                <id_tipo_ruolo>2</id_tipo_ruolo>
                <cod_tipo_ruolo>B</cod_tipo_ruolo>
                <des_tipo_ruolo>Beneficiario</des_tipo_ruolo>
                <data_inizio_validita/>
                <id_tipo_qualifica>4</id_tipo_qualifica>
                <des_tipo_qualifica>Legale rappresentante o delegato avente
                    titolo</des_tipo_qualifica>
                <descrizione_titolarita/>
                <assenso_uso_pec/>
                <pec/>
            </anagrafiche>

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
                    <xsl:when test="$tipo='Professionista'">F</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">F</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">G</xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">F</xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">F</xsl:when>
                </xsl:choose>
            </tipo_anagrafica>
            <variante_anagrafica>
                <xsl:choose>
                    <xsl:when test="$tipo='Professionista'">P</xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">I</xsl:when>
                </xsl:choose>
            </variante_anagrafica>
            <cognome>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COGNOME']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COGNOME']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_COGNOMELEG']/cea:valoreUtente"
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
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_DENOM']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_DENOM']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_NOME']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NOMELEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </nome>
            <codice_fiscale>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_CODFISC_DICHIARANTE']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_CODFISC_DICHIARANTE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CODFISC']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CODFISC']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CODFISCLEG']/cea:valoreUtente"
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
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PIVA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PIVA']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_DATANASCITA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NATOILLEG']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_CIT']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CITLEG']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Professionista'">
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
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:variable name="cittadinanza">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CITLEG']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_PGIURIDICA_CITLEG')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $cittadinanza">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                </xsl:choose>
            </des_cittadinanza>
            <id_comune_nascita/>
            <des_comune_nascita>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COMUNENASCITA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COMUNENASCITA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NATOALEG']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_PROVNASCITA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NATOPROVLEG']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_SESSO']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_SESSOLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </sesso>
            <id_tipo_collegio/>
            <des_tipo_collegio>
                <xsl:choose>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:variable name="albo">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PROF_QUALIFICA']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_PROF_QUALIFICA')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $albo">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                </xsl:choose>
            </des_tipo_collegio>
            <numero_iscrizione>
                <xsl:choose>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PROF_NUMEROALBO']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </numero_iscrizione>
            <data_iscrizione>
                <xsl:choose>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PROF_DATAALBO']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </data_iscrizione>
            <id_provincia_iscrizione/>
            <des_provincia_iscrizione>
                <xsl:choose>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PROF_PROVALBO']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </des_provincia_iscrizione>
            <id_provincia_cciaa/>
            <des_provincia_cciaa>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_PROVCCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_PROVCCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROVCCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROVCCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </des_provincia_cciaa>
            <flg_attesa_iscrizione_ri/>
            <flg_obbligo_iscrizione_ri/>
            <data_iscrizione_ri>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_DATACCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_DATACCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_DATACCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_DATACCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </data_iscrizione_ri>
            <n_iscrizione_ri>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_NUMEROCCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_NUMEROCCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMEROCCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMEROCCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </n_iscrizione_ri>
            <flg_attesa_iscrizione_rea/>
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
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMREA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMREA']/cea:valoreUtente"
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
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FORGIU']/cea:valoreUtente"
                            />
                        </xsl:when>
                        <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FORGIU']/cea:valoreUtente"
                            />
                        </xsl:when>
                    </xsl:choose>
                </xsl:variable>
                <xsl:if test="$formaGiuridicaId != ''">
                    <xsl:choose>
                        <xsl:when test="$formaGiuridicaId = AA"
                            ><!-- SOCIETA' IN ACCOMANDITA PER AZIONI -->13</xsl:when>
                        <xsl:when test="$formaGiuridicaId = AC"><!-- ASSOCIAZIONE -->25</xsl:when>
                        <xsl:when test="$formaGiuridicaId = AE"
                            ><!-- SOCIETA' CONSORTILE IN ACCOMANDITA SEMPLICE -->19</xsl:when>
                        <xsl:when test="$formaGiuridicaId = AF"><!-- ALTRE FORME -->30</xsl:when>
                        <xsl:when test="$formaGiuridicaId = AI"
                            ><!-- ASSOCIAZIONE IMPRESA -->63</xsl:when>
                        <xsl:when test="$formaGiuridicaId = AL"
                            ><!-- AZIENDA SPECIALE DI ENTE LOCALE -->23</xsl:when>
                        <xsl:when test="$formaGiuridicaId = AM"
                            ><!-- AZIENDA MUNICIPALE -->24</xsl:when>
                        <xsl:when test="$formaGiuridicaId = AN"
                            ><!-- SOCIETA' CONSORTILE IN NOME COLLETTIVO -->19</xsl:when>
                        <xsl:when test="$formaGiuridicaId = AP"
                            ><!-- AZIENDA PROVINCIALE -->64</xsl:when>
                        <xsl:when test="$formaGiuridicaId = AR"
                            ><!-- AZIENDA REGIONALE -->65</xsl:when>
                        <xsl:when test="$formaGiuridicaId = AS"
                            ><!-- SOCIETA' IN ACCOMANDITA SEMPLICE -->7</xsl:when>
                        <xsl:when test="$formaGiuridicaId = AT"
                            ><!-- AZIENDA AUTONOMA STATALE -->39</xsl:when>
                    </xsl:choose>
                </xsl:if>
            </id_forma_giuridica>
            <des_forma_giuridica>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:variable name="formaGiuridica">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FORGIU']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_RAPPSOC_FORGIU')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $formaGiuridica">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:variable name="formaGiuridica">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FORGIU']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_RAPPSOC_FORGIU')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $formaGiuridica">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:variable name="formaGiuridica">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FORGIU']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_PGIURIDICA_FORGIU')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $formaGiuridica">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:variable name="formaGiuridica">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FORGIU']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_PGIURIDICA_FORGIU')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $formaGiuridica">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
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
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COMUNERES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_COMUNESEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_COMUNESEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CITTALEG']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_PROVRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROVSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROVSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROVLEG']/cea:valoreUtente"
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
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_VIASEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_VIASEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_VIARES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_VIASEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_VIASEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_VIALEG']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_CIV']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMEROCIVICO']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NUMEROCIVICO']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CIVLEG']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_CAPRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CAPSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CAPSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CAPLEG']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_TELRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_TELSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_TELSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_TELLEG']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_FAXRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FAXSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FAXSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FAXLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </fax>
            <email>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_EMAIL_DICHIARANTE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_EMAIL_DICHIARANTE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_EMAILLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </email>
            <pec>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_EMAIL_DICHIARANTE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_EMAIL_DICHIARANTE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_EMAILLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </pec>
            <id_tipo_indirizzo>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">1</xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">4</xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">3</xsl:when>
                    <xsl:when test="$tipo='Professionista'">1</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">3</xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">4</xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">1</xsl:when>
                </xsl:choose>
            </id_tipo_indirizzo>
            <des_tipo_indirizzo>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">RESIDENZA</xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">SEDE OPERATIVA</xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">SEDE</xsl:when>
                    <xsl:when test="$tipo='Professionista'">RESIDENZA</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">SEDE</xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">SEDE OPERATIVA</xsl:when>
                    <xsl:when test="$tipo='LegaleRappresentante'">RESIDENZA</xsl:when>
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
                <xsl:value-of select="cea:codiceEnte"/>
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
