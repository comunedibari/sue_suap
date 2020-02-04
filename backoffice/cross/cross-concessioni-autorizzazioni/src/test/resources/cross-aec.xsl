<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs"
    xmlns:cea="http://gruppoinit.it/b110/ConcessioniEAutorizzazioni/procedimentoUnico"
    xmlns:ogg="http://egov.diviana.it/b109/OggettiCondivisi" xmlns:cross="http://www.wego.it/cross"
    xmlns="http://www.wego.it/cross">

    <xsl:template match="/cea:Richiesta">
        <xsl:variable name="indirizzoNotifica">
            <xsl:choose>
                <xsl:when
                    test="//cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome = 'di001')]/cea:valoreUtente != '' or 
                    //cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome = 'di002')]/cea:valoreUtente != '' or 
                    //cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome = 'di003')]/cea:valoreUtente != '' or
                    //cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome = 'di004')]/cea:valoreUtente != '' or
                    //cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome = 'di005')]/cea:valoreUtente != '' or
                    //cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome = 'di006')]/cea:valoreUtente != '' or
                    //cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome = 'di007')]/cea:valoreUtente != '' or
                    //cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome = 'di008')]/cea:valoreUtente != '' or
                    //cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome = 'di009')]/cea:valoreUtente != '' or
                    //cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome = 'di010')]/cea:valoreUtente != '' or
                    //cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome = 'di011')]/cea:valoreUtente != '' or
                    //cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome = 'di012')]/cea:valoreUtente != '' or
                    //cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome = 'di013')]/cea:valoreUtente != ''"                    
                    >SI</xsl:when>
                <xsl:otherwise>NO</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
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

                <xsl:variable name="countEdilizia">
                    <xsl:value-of
                        select="count(//cea:Procedimenti/cea:procedimento/*[local-name()='codiceCud' and (text()='2' or text() = '4')])"
                    />
                </xsl:variable>
                <xsl:variable name="countCommercio">
                    <xsl:value-of
                        select="count(//cea:Procedimenti/cea:procedimento/*[local-name()='codiceCud' and text()='1'])"
                    />
                </xsl:variable>
                <xsl:variable name="countTelecomunicazioni">
                    <xsl:value-of
                        select="count(//cea:Procedimenti/cea:procedimento/*[local-name()='codiceCud' and text()='3'])"
                    />
                </xsl:variable>

                <xsl:variable name="identificativoProcedimento">
                    <xsl:choose>
                        <xsl:when test="$countEdilizia > 0 and $tipoProc = 'SCIA'">EDILIZIA_AUTOMATICO</xsl:when>
                        <xsl:when test="$countEdilizia > 0 and $tipoProc = 'ORDINARIO'">EDILIZIA_ORDINARIO</xsl:when>
                        <xsl:when
                            test="$countCommercio > 0 and $countEdilizia = 0 and $tipoProc = 'SCIA'"
                            >COMMERCIO_AUTOMATIZZATO</xsl:when>
                        <xsl:when
                            test="$countCommercio > 0 and $countEdilizia = 0 and $tipoProc = 'ORDINARIO'"
                            >COMMERCIO_ORDINARIO</xsl:when>
                        <xsl:otherwise>
                            <xsl:if test="$tipoProc = 'SCIA'">TELECOMUNICAZIONI_AUTOMATIZZATO</xsl:if>
                            <xsl:if test="$tipoProc = 'ORDINARIO'">TELECOMUNICAZIONI_ORDINARIO</xsl:if>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <xsl:value-of select="$identificativoProcedimento"/>
            </id_procedimento_suap>
            <identificativo_pratica>
                <xsl:value-of
                    select="//ogg:IdentificatorediRichiesta/ogg:IdentificatoreUnivoco/ogg:CodiceIdentificativoOperazione"
                />
            </identificativo_pratica>
            <oggetto>
                [OGGETTO STUB - SOVRASCRITTO DAL PLUGIN]
<!--                <xsl:value-of
                    select="//cea:DichiarazioneDinamica[cea:href='h00097']/cea:campiHref/cea:campoHref/cea:valoreUtente"
                />-->
            </oggetto>
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
                <xsl:value-of select="//cea:Ente/cea:CodEnte"/>
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
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_2']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_2']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_2']/cea:descrizione"
                />
            </xsl:if>
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_3']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_3']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_3']/cea:descrizione"
                />
            </xsl:if>
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_4']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_4']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_4']/cea:descrizione"
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
            <!-- Legale rappresentante di privato -->
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_3']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_3']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_3']/cea:valoreUtente"
                />
            </xsl:if>
            <!-- Rappresentante ente/associazione -->
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_4']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_4']/cea:valore">
                <xsl:value-of
                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_QUALITA_4']/cea:valoreUtente"
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
                    test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FORGIU']/cea:valoreUtente=1"
                    >S</xsl:when>
                <xsl:otherwise>N</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        
        <xsl:variable name="dittaIndividualeProcuraSpecialeVar">
            <xsl:choose>
                <xsl:when
                    test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FORGIU']/cea:valoreUtente=1"
                    >S</xsl:when>
                <xsl:otherwise>N</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        
        <xsl:variable name="procurato">
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PROF_PERSGIURIDICA']/cea:valoreUtente=2"
                >G</xsl:if>
            <xsl:if
                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PROF_PERSFISICA']/cea:valoreUtente=1"
                >F</xsl:if>
        </xsl:variable>
        <xsl:if test="$qualitaDi = 1">
            <!-- Rappresentante di società/ditta individuale -->
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
                <id_tipo_qualifica>1</id_tipo_qualifica>
                <des_tipo_qualifica>Proprietario</des_tipo_qualifica>
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
                <id_tipo_qualifica>1</id_tipo_qualifica>
                <des_tipo_qualifica>Proprietario</des_tipo_qualifica>
                <descrizione_titolarita/>
                <assenso_uso_pec/>
                <pec/>
            </anagrafiche>
        </xsl:if>
        <xsl:if test="$qualitaDi = 3">
            <!-- Legale rappresentante di privato -->
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
            <!-- anagrafica privato -->
            <anagrafiche>
                <xsl:variable name="tipo">Privato</xsl:variable>
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
        <xsl:if test="$qualitaDi = 4">
            <!-- Rappresentante di ente/associazione -->
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
            <!-- anagrafica ente/associazione -->
            <anagrafiche>
                <xsl:variable name="tipo">Associazione</xsl:variable>
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
            <!-- in qualità di professionista munito di procura speciale -->
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
            <!-- anagrafica persona fisica -->
            <xsl:if test="$procurato = 'F'">
                <anagrafiche>
                    <contatoreGlobale>
                        <xsl:value-of select="$contatoreGlobale"/>
                    </contatoreGlobale>
                    <xsl:variable name="tipo">ProcuraPersonaFisica</xsl:variable>
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
            <!-- anagrafica persona giuridica -->
            <xsl:if test="$procurato = 'G'">
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
                <!-- 
                    Non inserisco l'anagrafica legale rappresentante
                    Chi beneficia è la ditta                    
                <anagrafiche>
                    <contatoreGlobale>
                        <xsl:value-of select="$contatoreGlobale"/>
                    </contatoreGlobale>
                    <xsl:variable name="tipo">ProcuraPersonaGiuridicaLegaleRappresentante </xsl:variable>
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
                -->
            </xsl:if>
        </xsl:if>
        <!-- Le prossime anagrafiche sono fisse, non multiple, definite all'interno di href -->
        <!-- E' stato spostato e messo in una anagrafica dinamica in un href
        <xsl:variable name="progettista">
            <xsl:value-of
                select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di001']/cea:valoreUtente"
            />
        </xsl:variable>
        <xsl:if test="$progettista != ''">
            <anagrafiche>
                <xsl:variable name="tipo">ProgettistaHref</xsl:variable>
                <xsl:call-template name="anagrafica">
                    <xsl:with-param name="tipo" select="$tipo"/>
                    <xsl:with-param name="counter">4</xsl:with-param>
                </xsl:call-template>
                <id_tipo_ruolo>3</id_tipo_ruolo>
                <cod_tipo_ruolo>P</cod_tipo_ruolo>
                <des_tipo_ruolo>Tecnico professionista</des_tipo_ruolo>
                <data_inizio_validita/>
                <id_tipo_qualifica>5</id_tipo_qualifica>
                <des_tipo_qualifica>Progettista</des_tipo_qualifica>
                <descrizione_titolarita/>
                <assenso_uso_pec/>
                <pec/>
            </anagrafiche>
        </xsl:if>
        -->
        <xsl:variable name="direttoreLavori">
            <!-- Cerco il codice fiscale -->
            <xsl:value-of
                select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di013']/cea:valoreUtente"
            />
        </xsl:variable>
        <xsl:if test="$direttoreLavori != ''">
            <anagrafiche>
                <xsl:variable name="tipo">DirettoreLavoriHref</xsl:variable>
                <xsl:call-template name="anagrafica">
                    <xsl:with-param name="tipo" select="$tipo"/>
                    <xsl:with-param name="counter">5</xsl:with-param>
                </xsl:call-template>
                <id_tipo_ruolo>3</id_tipo_ruolo>
                <cod_tipo_ruolo>P</cod_tipo_ruolo>
                <des_tipo_ruolo>Tecnico professionista</des_tipo_ruolo>
                <data_inizio_validita/>
                <id_tipo_qualifica>6</id_tipo_qualifica>
                <des_tipo_qualifica>Direttore lavori</des_tipo_qualifica>
                <descrizione_titolarita/>
                <assenso_uso_pec/>
                <pec/>
            </anagrafiche>
        </xsl:if>
        <xsl:variable name="responsabileTecnico">
            <!-- Cerco il codice fiscale (obbligatorio) -->
            <xsl:value-of
                select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di013']/cea:valoreUtente"
            />
        </xsl:variable>
        <xsl:if test="$responsabileTecnico != ''">
            <anagrafiche>
                <xsl:variable name="tipo">ResponsabileTecnicoHref</xsl:variable>
                <xsl:call-template name="anagrafica">
                    <xsl:with-param name="tipo" select="$tipo"/>
                    <xsl:with-param name="counter">6</xsl:with-param>
                </xsl:call-template>
                <id_tipo_ruolo>3</id_tipo_ruolo>
                <cod_tipo_ruolo>P</cod_tipo_ruolo>
                <des_tipo_ruolo>Tecnico professionista</des_tipo_ruolo>
                <data_inizio_validita/>
                <id_tipo_qualifica>6</id_tipo_qualifica>
                <des_tipo_qualifica>Direttore lavori</des_tipo_qualifica>
                <descrizione_titolarita/>
                <assenso_uso_pec/>
                <pec/>
            </anagrafiche>
        </xsl:if>

        <xsl:variable name="proprietarioImmobile">
            <!-- Cerco il codice fiscale -->
            <xsl:value-of
                select="//cea:DichiarazioneDinamica[cea:href='h00220']//cea:campoHref[cea:nome='d017']/cea:valoreUtente"
            />
        </xsl:variable>
        <xsl:if test="$proprietarioImmobile != ''">
            <anagrafiche>
                <xsl:variable name="tipo">ProprietarioImmobileHref</xsl:variable>
                <xsl:call-template name="anagrafica">
                    <xsl:with-param name="tipo" select="$tipo"/>
                    <xsl:with-param name="counter">7</xsl:with-param>
                </xsl:call-template>
                <id_tipo_ruolo>2</id_tipo_ruolo>
                <cod_tipo_ruolo>B</cod_tipo_ruolo>
                <des_tipo_ruolo>Beneficiario</des_tipo_ruolo>
                <data_inizio_validita/>
                <id_tipo_qualifica>1</id_tipo_qualifica>
                <des_tipo_qualifica>Proprietario</des_tipo_qualifica>
                <descrizione_titolarita/>
                <assenso_uso_pec/>
                <pec/>
            </anagrafiche>
        </xsl:if>
        <xsl:variable name="impresaEsecutrice">
            <!-- Cerco il nome azienda -->
            <xsl:value-of
                select="//cea:DichiarazioneDinamica[cea:href='h00257']//cea:campoHref[cea:nome='d010']/cea:valoreUtente"
            />
        </xsl:variable>
        <xsl:if test="$impresaEsecutrice != ''">
            <anagrafiche>
                <xsl:variable name="tipo">ImpresaEsecutriceHref</xsl:variable>
                <xsl:call-template name="anagrafica">
                    <xsl:with-param name="tipo" select="$tipo"/>
                    <xsl:with-param name="counter">8</xsl:with-param>
                </xsl:call-template>
                <id_tipo_ruolo>4</id_tipo_ruolo>
                <cod_tipo_ruolo>I</cod_tipo_ruolo>
                <des_tipo_ruolo>Impresa</des_tipo_ruolo>
                <data_inizio_validita/>
                <id_tipo_qualifica/>
                <des_tipo_qualifica/>
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
            <confermata></confermata>
            <id_anagrafica/>
            <tipo_anagrafica>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">F</xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">F</xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">G</xsl:when>
                    <xsl:when test="$tipo='Privato'">F</xsl:when>
                    <xsl:when test="$tipo='Associazione'">G</xsl:when>
                    <xsl:when test="$tipo='Professionista'">F</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">F</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">G</xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">F</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridicaLegaleRappresentante'">F</xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">F</xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">F</xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">F</xsl:when>
                    <xsl:when test="$tipo='ProprietarioImmobileHref'">F</xsl:when>
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">G</xsl:when>
                </xsl:choose>
            </tipo_anagrafica>
            <variante_anagrafica>
                <xsl:choose>
                    <xsl:when test="$tipo='Professionista'">P</xsl:when>
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
		            <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COGNOME']/cea:valoreUtente"
                    />
                    </xsl:when>
                    <xsl:when test="$tipo='Privato'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPPRIV_COGNOMERAP']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COGNOME']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_COGNOME']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridicaLegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_COGNOMELEG']/cea:valoreUtente"
                        />
                    </xsl:when>
		          <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_COGNOMELEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di001']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di002']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di002']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Associazione'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPENTE_DENOM']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00257']//cea:campoHref[cea:nome='d010']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProprietarioImmobileHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00220']//cea:campoHref[cea:nome='d018']/cea:valoreUtente"
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
		            <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_NOME']/cea:valoreUtente"
                    />
			        </xsl:when>
                    <xsl:when test="$tipo='Privato'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPPRIV_NOMERAP']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_NOME']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_NOME']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NOMELEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di002']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di001']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di001']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Privato'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPPRIV_CODFISCRAP']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Associazione'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPENTE_CODFISC']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_CODFISC_DICHIARANTE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_CODFISC']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='ProcuraPersonaGiuridicaLegaleRappresentante'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CODFISCLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di013']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di013']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di013']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProprietarioImmobileHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00220']//cea:campoHref[cea:nome='d017']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00257']//cea:campoHref[cea:nome='d030']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='Associazione'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPENTE_PIVA']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di012']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di012']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di012']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00257']//cea:campoHref[cea:nome='d020']/cea:valoreUtente"
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
		              <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_DATANASCITA']/cea:valoreUtente"
                        />
                    </xsl:when>

                    <xsl:when test="$tipo='Privato'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPPRIV_DATANASCITARAP']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Associazione'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_CAPRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_DATANASCITA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_DATANASCITA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_NATOILLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
		      <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='d005']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='d005']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='d005']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </data_nascita>
            <id_cittadinanza/>
            <des_cittadinanza/>
            <id_comune_nascita/>
            <des_comune_nascita>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COMUNENASCITA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COMUNENASCITA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Privato'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPPRIV_NATOARAP']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COMUNENASCITA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_LUOGONASCITA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_COMUNELEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di003']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di003']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di003']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </des_comune_nascita>
            <id_provincia_nascita/>
            <des_provincia_nascita>
                <xsl:choose>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di007']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='d004']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROVNASLEG']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_SESSO']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Privato'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPPRIV_SESSORAP']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_SESSO']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_SESSO']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </sesso>
            <id_tipo_collegio>
                <xsl:choose>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:variable name="albo">
                            <xsl:if
                                test="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PROF_ISCRITTOALBO']/cea:valoreUtente=cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PROF_ISCRITTOALBO']/cea:valore">
                                <xsl:value-of
                                    select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PROF_ISCRITTOALBO']/cea:valoreUtente"
                                />
                            </xsl:if>
                        </xsl:variable>
                        <xsl:choose>
                            <!-- Agronomo -->
                            <xsl:when test="$albo='2'">1</xsl:when>
                            <!-- Architetto -->
                            <xsl:when test="$albo='10'">2</xsl:when>
                            <!-- Geometra -->
                            <xsl:when test="$albo='11'">3</xsl:when>
                            <!-- Ingegnere -->
                            <xsl:when test="$albo='9'">4</xsl:when>
                            <!-- Perito -->
                            <xsl:when test="$albo='16'">5</xsl:when>
                            <!-- Perito -->
                            <xsl:when test="$albo='17'">5</xsl:when>
                            <xsl:otherwise>6</xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                </xsl:choose>
            </id_tipo_collegio>
            <des_tipo_collegio>
                <xsl:choose>
                    <xsl:when test="$tipo='Associazione'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPENTE_COMUNEREGISTRO']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:variable name="albo">
                            <xsl:value-of
                                select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PROF_ISCRITTOALBO']/cea:valoreUtente"
                            />
                        </xsl:variable>
                        <xsl:for-each
                            select="cea:CampoAnagrafica[(cea:CampoXmlMod='ANAG_PROF_ISCRITTOALBO')]/cea:opzioniCombo/cea:OpzioneCombo">
                            <xsl:if test="cea:Codice = $albo">
                                <xsl:value-of select="cea:Etichetta"/>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di019']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di019']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </des_tipo_collegio>
            <numero_iscrizione>
                <xsl:choose>
                    <xsl:when test="$tipo='Associazione'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPENTE_NUMEROREGISTRO']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PROF_NUMEROALBO']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di018']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di018']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di018']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di017']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di017']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di017']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </des_provincia_iscrizione>
            <id_provincia_cciaa/>
            <des_provincia_cciaa>
                <xsl:choose>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_COMUNECCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_COMUNECCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_COMUNECCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_COMUNECCIAA']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </des_provincia_cciaa>
            <flg_attesa_iscrizione_ri/>
            <flg_obbligo_iscrizione_ri/>
            <data_iscrizione_ri/>
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
            <data_iscrizione_rea/>
            <n_iscrizione_rea/>
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
                        <xsl:when test="$formaGiuridicaId = 1"
                            ><!-- DITTA INDIVIDUALE -->2</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 2"><!-- S.A.P.A. -->13</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 3"><!-- S.A.S. -->7</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 4"><!-- S.C.A.R.L. -->15</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 5"><!-- S.C.R.L. -->19</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 6"><!-- S.N.C. -->6</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 7"
                            ><!-- SOCIETA` COOPERATIVA -->16</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 8"
                            ><!-- SOCIETA`-LEGISL. ALTRO STATO -->62</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 9"><!-- S.P.A. -->10</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 10"
                            ><!-- S.P.A. CON UNICO SOCIO -->10</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 11"><!-- S.R.L. -->11</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 12"
                            ><!-- S.R.L. CON UNICO SOCIO -->12</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 13"><!-- ALTRE FORME -->34</xsl:when>
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
                        <xsl:when test="$formaGiuridicaId = 1"
                            ><!-- DITTA INDIVIDUALE -->Imprenditore individuale non agricolo</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 2"><!-- S.A.P.A. -->Società in accomandita per azioni</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 3"><!-- S.A.S. -->Società in accomandita semplice</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 4"><!-- S.C.A.R.L. -->Società cooperativa diversa</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 5"><!-- S.C.R.L. -->Società consortile</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 6"><!-- S.N.C. -->Società in nome collettivo</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 7"
                            ><!-- SOCIETA` COOPERATIVA -->Società cooperativa sociale</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 8"
                            ><!-- SOCIETA`-LEGISL. ALTRO STATO -->ALTRO</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 9"><!-- S.P.A. -->Società per azioni</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 10"
                            ><!-- S.P.A. CON UNICO SOCIO -->Società per azioni</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 11"><!-- S.R.L. -->Società a responsabilità limitata</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 12"
                            ><!-- S.R.L. CON UNICO SOCIO -->Società a responsabilità limitata con un unico socio</xsl:when>
                        <xsl:when test="$formaGiuridicaId = 13"><!-- ALTRE FORME -->Altra forma di ente privato senza personalità giuridica</xsl:when>
                    </xsl:choose>
                </xsl:if>
                <!--
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
                -->
            </des_forma_giuridica>
            <recapiti>
                <xsl:variable name="counterRecapito">1</xsl:variable>
                <xsl:call-template name="recapito">
                    <xsl:with-param name="tipo" select="$tipo"/>
                    <xsl:with-param name="counterRecapito" select="$counterRecapito"/>
                </xsl:call-template>
                <xsl:variable name="sedeOperativa">
                    <xsl:if test="$tipo='DittaIndividuale'">SedeOperativaDI</xsl:if>
                    <xsl:if test="$tipo='ProcuraDittaIndividuale'">SedeOperativaPDI</xsl:if>
                </xsl:variable>
                <xsl:if test="$tipo='DittaIndividuale'">
                    <xsl:call-template name="recapito">
                        <xsl:with-param name="tipo" select="$sedeOperativa"/>
                        <xsl:with-param name="counterRecapito" select="$counterRecapito + 1"/>
                    </xsl:call-template>
                </xsl:if>
                <xsl:if test="$tipo='ProcuraDittaIndividuale'">
                    <xsl:call-template name="recapito">
                        <xsl:with-param name="tipo" select="$sedeOperativa"/>
                        <xsl:with-param name="counterRecapito" select="$counterRecapito + 1"/>
                    </xsl:call-template>
                </xsl:if>
            </recapiti>
        </anagrafica>
    </xsl:template>

    <xsl:template name="recapito">
        <xsl:param name="tipo"/>
        <xsl:param name="counterRecapito"/>
        <recapito>
            <counter>
                <!-- <xsl:value-of select="position()"/> --> 
                <xsl:value-of select="$counterRecapito"/>
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
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COMUNERES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='SedeOperativaDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_COMUNESEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_COMUNESEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Privato'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPPRIV_COMUNERAP']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Associazione'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPENTE_COMUNESEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_COMUNERES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_COMUNERES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_COMUNESEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='SedeOperativaPDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_COMUNESEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_RESILEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di006']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di006']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di006']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProprietarioImmobileHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00220']//cea:campoHref[cea:nome='d019']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00257']//cea:campoHref[cea:nome='d045']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_PROVSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_PROVRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='SedeOperativaDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_PROVSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Privato'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPPRIV_PROVRAP']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Associazione'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPENTE_PROVSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_PROVRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_PROVRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='SedeOperativaPDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROVSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROVSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_PROVRESLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di007']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di007']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di007']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProprietarioImmobileHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00220']//cea:campoHref[cea:nome='d020']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00257']//cea:campoHref[cea:nome='d050']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </des_provincia>
            <id_stato/>
            <des_stato>
                <xsl:choose>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di008']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProprietarioImmobileHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00220']//cea:campoHref[cea:nome='d021']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </des_stato>
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
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_VIARES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='SedeOperativaDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_VIASEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_VIASEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Privato'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPPRIV_VIARAP']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Associazione'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPENTE_VIASEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_VIARES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_VIARES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_VIASEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='SedeOperativaPDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_VIASEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_INDILEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di009']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di009']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di009']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProprietarioImmobileHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00220']//cea:campoHref[cea:nome='d022']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00257']//cea:campoHref[cea:nome='d070']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </indirizzo>
            <codice_via/>
            <n_civico>
                <xsl:choose>
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di010']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di010']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di010']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProprietarioImmobileHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00220']//cea:campoHref[cea:nome='d023']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CIVICORESLEG']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00257']//cea:campoHref[cea:nome='d080']/cea:valoreUtente"
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
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_CAPRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='SedeOperativaDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_CAPSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_CAPSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Privato'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPPRIV_CAPRAP']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Associazione'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPENTE_CAPSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Professionista'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_CAPRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PFISICA_CAPRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CAPSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='SedeOperativaPDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_CAPSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_CAPSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di011']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di011']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di011']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProprietarioImmobileHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00220']//cea:campoHref[cea:nome='d024']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00257']//cea:campoHref[cea:nome='d050']/cea:valoreUtente"
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
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_TELRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='SedeOperativaDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_TELSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_TELSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Associazione'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPENTE_TELSEDE']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='SedeOperativaPDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_TELSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_TELSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='d015']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='d015']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='d015']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProprietarioImmobileHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00220']//cea:campoHref[cea:nome='d026']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00257']//cea:campoHref[cea:nome='d100']/cea:valoreUtente"
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
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_DICHIARANTE_FAXRES']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='SedeOperativaDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FAXSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FAXSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Associazione'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPENTE_FAXSEDE']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='SedeOperativaPDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_FAXSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_FAXSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di016']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di016']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di016']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00257']//cea:campoHref[cea:nome='d120']/cea:valoreUtente"
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
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_EMAIL_DICHIARANTE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='SedeOperativaDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Associazione'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPENTE_EMAILSEDE']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='SedeOperativaPDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00098']//cea:campoHref[cea:nome='di014']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00099']//cea:campoHref[cea:nome='di014']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00181']//cea:campoHref[cea:nome='di014']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProprietarioImmobileHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00220']//cea:campoHref[cea:nome='d025']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00257']//cea:campoHref[cea:nome='d100']/cea:valoreUtente"
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
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_EMAIL_DICHIARANTE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='SedeOperativaDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='Associazione'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPENTE_EMAILSEDE']/cea:valoreUtente"
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
                    <xsl:when test="$tipo='SedeOperativaPDI'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_PGIURIDICA_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">
                        <xsl:value-of
                            select="cea:CampoAnagrafica[cea:CampoXmlMod='ANAG_RAPPSOC_EMAILSEDE']/cea:valoreUtente"
                        />
                    </xsl:when>
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">
                        <xsl:value-of
                            select="//cea:DichiarazioneDinamica[cea:href='h00257']//cea:campoHref[cea:nome='d105']/cea:valoreUtente"
                        />
                    </xsl:when>
                </xsl:choose>
            </pec>
            <id_tipo_indirizzo>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">1</xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">1</xsl:when>
                    <xsl:when test="$tipo='SedeOperativaDI'">4</xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">3</xsl:when>
                    <xsl:when test="$tipo='Privato'">1</xsl:when>
                    <xsl:when test="$tipo='Associazione'">3</xsl:when>
                    <xsl:when test="$tipo='Professionista'">1</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">1</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">1</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">3</xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">1</xsl:when>
                    <xsl:when test="$tipo='SedeOperativaPDI'">4</xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">1</xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">3</xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">1</xsl:when>
                    <xsl:when test="$tipo='ProprietarioImmobileHref'">3</xsl:when>
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">3</xsl:when>
                </xsl:choose>
            </id_tipo_indirizzo>
            <des_tipo_indirizzo>
                <xsl:choose>
                    <xsl:when test="$tipo='Default'">RESIDENZA</xsl:when>
                    <xsl:when test="$tipo='DittaIndividuale'">RESIDENZA</xsl:when>
                    <xsl:when test="$tipo='SedeOperativaDI'">SEDE OPERATIVA</xsl:when>
                    <xsl:when test="$tipo='PersonaGiuridica'">SEDE</xsl:when>
                    <xsl:when test="$tipo='Privato'">RESIDENZA</xsl:when>
                    <xsl:when test="$tipo='Associazione'">SEDE</xsl:when>
                    <xsl:when test="$tipo='Professionista'">RESIDENZA</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">RESIDENZA</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaFisica'">RESIDENZA</xsl:when>
                    <xsl:when test="$tipo='ProcuraPersonaGiuridica'">SEDE</xsl:when>
                    <xsl:when test="$tipo='ProcuraDittaIndividuale'">RESIDENZA</xsl:when>
                    <xsl:when test="$tipo='SedeOperativaPDI'">SEDE OPERATIVA</xsl:when>
                    <xsl:when test="$tipo='ProgettistaHref'">RESIDENZA</xsl:when>
                    <xsl:when test="$tipo='DirettoreLavoriHref'">SEDE</xsl:when>
                    <xsl:when test="$tipo='ResponsabileTecnicoHref'">RESIDENZA</xsl:when>
                    <xsl:when test="$tipo='ProprietarioImmobileHref'">SEDE</xsl:when>
                    <xsl:when test="$tipo='ImpresaEsecutriceHref'">SEDE</xsl:when>
                </xsl:choose>
            </des_tipo_indirizzo>
        </recapito>
    </xsl:template>

    <xsl:template name="notifica">
        <notifica>
            <presso>
                <xsl:value-of
                    select="//cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome='di001')]/cea:valoreUtente"
                /> <xsl:value-of
                    select="//cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome='di002')]/cea:valoreUtente"
                /> <xsl:value-of
                    select="//cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome='di003')]/cea:valoreUtente"
                />
            </presso>
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
                    select="//cea:DichiarazioniDinamiche/cea:DichiarazioneDinamica[(cea:href = 'h00288')]/cea:campiHref/cea:campoHref[(cea:nome='di007')]/cea:valoreUtente"
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
