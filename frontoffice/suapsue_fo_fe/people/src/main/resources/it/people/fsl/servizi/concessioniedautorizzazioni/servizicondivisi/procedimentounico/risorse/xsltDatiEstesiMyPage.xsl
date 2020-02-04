<?xml version="1.0"?>
<!--
    Document   : xsltAnagraficaA&C.xsl
    Created on : September 17, 2014, 6:33 PM
    Author     : piergiorgio
    Description:
        Purpose of transformation follows.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs" version="2.0" xmlns="http://datiestesiMyPage.wego.it">
    <xsl:template match="/ProcessData">
        <datiEstesiMyPage xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <deleganti>
                <xsl:for-each select="/ProcessData/anagrafica">
                    <xsl:call-template name="anagrafica"/>
                </xsl:for-each>
                <xsl:for-each select="/ProcessData/altriRichiedenti/altriRichiedenti">
                    <xsl:call-template name="anagrafica"/>
                </xsl:for-each>
            </deleganti>
            <indirizzi>
                <xsl:for-each select="/ProcessData/listaHref/entry">
                    <xsl:call-template name="indirizzi"/>
                </xsl:for-each>
            </indirizzi>
        </datiEstesiMyPage>
    </xsl:template>
    <xsl:template name="anagrafica">
        <xsl:variable name="inQualitaDi">
            <xsl:if test="listaCampi/listaCampi[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente=listaCampi/listaCampi[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valore">
                <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_1']/valoreUtente"/>
            </xsl:if>
            <xsl:if test="listaCampi/listaCampi[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_5']/valoreUtente=listaCampi/listaCampi[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_5']/valore">
                <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_DICHIARANTE_QUALITA_5']/valoreUtente"/>
            </xsl:if>
        </xsl:variable>
        <xsl:variable name="TipoRichiedente">
            <xsl:choose>
                <xsl:when test="listaCampi/listaCampi[campo_xml_mod='ANAG_RIC_PF']/valoreUtente=listaCampi/listaCampi[campo_xml_mod='ANAG_RIC_PF']/valore">
                    <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_RIC_PF']/valoreUtente"/>
                </xsl:when>
                <xsl:when test="listaCampi/listaCampi[campo_xml_mod='ANAG_RIC_PG']/valoreUtente=listaCampi/listaCampi[campo_xml_mod='ANAG_RIC_PG']/valore">
                    <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_RIC_PG']/valoreUtente"/>
                </xsl:when>
                <xsl:otherwise>0</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="ProcuraPersona">
            <xsl:choose>
                <xsl:when test="listaCampi/listaCampi[campo_xml_mod='ANAG_DEL_PF']/valoreUtente=listaCampi/listaCampi[campo_xml_mod='ANAG_DEL_PF']/valore">
                    <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_DEL_PF']/valoreUtente"/>
                </xsl:when>
                <xsl:when test="listaCampi/listaCampi[campo_xml_mod='ANAG_DEL_PG']/valoreUtente=listaCampi/listaCampi[campo_xml_mod='ANAG_DEL_PG']/valore">
                    <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_DEL_PG']/valoreUtente"/>
                </xsl:when>
                <xsl:otherwise>0</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
             <xsl:if test="$inQualitaDi = '5'">
            <xsl:call-template name="personaGiuridica">
                <xsl:with-param name="qualitaDi" select="$inQualitaDi"/>
            </xsl:call-template>
        </xsl:if>
             <xsl:if test="$inQualitaDi = '1'">
            <xsl:call-template name="personaGiuridica">
                <xsl:with-param name="qualitaDi" select="$inQualitaDi"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>
    <xsl:template name="persona">
        <xsl:param name="qualitaDi"/>
        <xsl:variable name="cognome">
            <xsl:if test="$qualitaDi = '1'">
                <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_DICHIARANTE_COGNOME']/valoreUtente"/>
            </xsl:if>
            <xsl:if test="$qualitaDi = '5'">
                <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_PFISICA_COGNOMELEG']/valoreUtente"/>
            </xsl:if>
        </xsl:variable>
        <xsl:variable name="nome">
            <xsl:if test="$qualitaDi = '1'">
                <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_DICHIARANTE_NOME']/valoreUtente"/>
            </xsl:if>
            <xsl:if test="$qualitaDi = '5'">
                <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_PFISICA_NOMELEG']/valoreUtente"/>
            </xsl:if>
        </xsl:variable>
        <xsl:variable name="codiceFiscale">
            <xsl:if test="$qualitaDi = '1'">
                <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_DICHIARANTE_CF']/valoreUtente"/>
            </xsl:if>
            <xsl:if test="$qualitaDi = '5'">
                <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_PFISICA_CODFISCLEG']/valoreUtente"/>
            </xsl:if>
        </xsl:variable>
        <xsl:if test="$cognome != '' or $nome != '' or $codiceFiscale != ''">
            <delegante>
                <cognome>
                    <xsl:value-of select="$cognome"/>
                </cognome>
                <nome>
                    <xsl:value-of select="$nome"/>
                </nome>
                <codiceFiscale>
                    <xsl:value-of select="$codiceFiscale"/>
                </codiceFiscale>
            </delegante>
        </xsl:if>
    </xsl:template>
    <xsl:template name="personaGiuridica">
        <xsl:param name="qualitaDi"/>
        <xsl:variable name="denominazione">
            <xsl:if test="$qualitaDi = '1'">
                <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_RAPPSOC_DENOM']/valoreUtente"/>
            </xsl:if>
            <xsl:if test="$qualitaDi = '5'">
                <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_PGIURIDICA_DENOM']/valoreUtente"/>
            </xsl:if>
        </xsl:variable>
        <xsl:variable name="codiceFiscale">
            <xsl:if test="$qualitaDi = '1'">
                <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_RAPPSOC_CODFISC']/valoreUtente"/>
            </xsl:if>
            <xsl:if test="$qualitaDi = '5'">
                <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_PGIURIDICA_CODFISC']/valoreUtente"/>
            </xsl:if>
        </xsl:variable>
        <xsl:variable name="partitaIva">
            <xsl:if test="$qualitaDi = '1'">
                <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_RAPPSOC_PIVA']/valoreUtente"/>
            </xsl:if>
            <xsl:if test="$qualitaDi = '5'">
                <xsl:value-of select="listaCampi/listaCampi[campo_xml_mod='ANAG_PGIURIDICA_PIVA']/valoreUtente"/>
            </xsl:if>
        </xsl:variable>
        <xsl:if test="$denominazione != '' or $codiceFiscale != '' or $partitaIva != ''">
            <delegante>
                <denominazione>
                    <xsl:value-of select="$denominazione"/>
                </denominazione>
                <codiceFiscale>
                    <xsl:value-of select="$codiceFiscale"/>
                </codiceFiscale>
                <partitaIva>
                    <xsl:value-of select="$partitaIva"/>
                </partitaIva>
            </delegante>
        </xsl:if>
    </xsl:template>
    <xsl:template name="indirizzi">
        <xsl:for-each select="value">
            <xsl:call-template name="indirizzo"/>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="indirizzo">
        <xsl:variable name="vResultComune">
            <xsl:for-each select="campi/campi[campo_xml_mod='INDIRIZZO_COMUNE']/valoreUtente">
                <xsl:value-of select="../molteplicita"/>
                <xsl:value-of select="','"/>
            </xsl:for-each>
        </xsl:variable>
        <xsl:variable name="vResultVia">
            <xsl:for-each select="campi/campi[campo_xml_mod='INDIRIZZO_VIA']/valoreUtente">
                <xsl:value-of select="../molteplicita"/>
                <xsl:value-of select="','"/>
            </xsl:for-each>
        </xsl:variable>
        <xsl:variable name="vResult">
            <xsl:choose>
                <xsl:when test="$vResultComune = ''">
                    <xsl:value-of select="$vResultVia"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$vResultComune"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:call-template name="Loop">
            <xsl:with-param name="input" select="$vResult"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template name="Loop">
        <xsl:param name="input"/>
        <xsl:if test="string-length($input) &gt; 0">
            <xsl:variable name="v" select="substring-before($input, ',')"/>
            <xsl:variable name="comune">
                <xsl:value-of select="campi/campi[campo_xml_mod='INDIRIZZO_COMUNE'][molteplicita=$v]/valoreUtente"/>
            </xsl:variable>
            <xsl:variable name="via">
                <xsl:value-of select="campi/campi[campo_xml_mod='INDIRIZZO_VIA'][molteplicita=$v]/valoreUtente"/>
            </xsl:variable>
            <xsl:variable name="civico">
                <xsl:value-of select="campi/campi[campo_xml_mod='INDIRIZZO_CIVICO'][molteplicita=$v]/valoreUtente"/>
            </xsl:variable>
            <xsl:variable name="colore">
                <xsl:value-of select="campi/campi[campo_xml_mod='INDIRIZZO_COLORE'][molteplicita=$v]/valoreUtente"/>
            </xsl:variable>
            <xsl:variable name="lettera">
                <xsl:value-of select="campi/campi[campo_xml_mod='INDIRIZZO_LETTERA'][molteplicita=$v]/valoreUtente"/>
            </xsl:variable>
            <xsl:variable name="scala">
                <xsl:value-of select="campi/campi[campo_xml_mod='INDIRIZZO_SCALA'][molteplicita=$v]/valoreUtente"/>
            </xsl:variable>
            <xsl:variable name="piano">
                <xsl:value-of select="campi/campi[campo_xml_mod='INDIRIZZO_PIANO'][molteplicita=$v]/valoreUtente"/>
            </xsl:variable>
            <xsl:variable name="interno">
                <xsl:value-of select="campi/campi[campo_xml_mod='INDIRIZZO_INTERNO'][molteplicita=$v]/valoreUtente"/>
            </xsl:variable>
            <xsl:variable name="internoLettera">
                <xsl:value-of select="campi/campi[campo_xml_mod='INDIRIZZO_INTERNO_LETTERA'][molteplicita=$v]/valoreUtente"/>
            </xsl:variable>                           
            <xsl:call-template name="Loop">
                <xsl:with-param name="input" select="substring-after($input, ',')"/>
            </xsl:call-template>
            <xsl:if test="$comune != '' or $via != '' or $civico != ''">
                <indirizzo>
                    <comune>
                        <xsl:value-of select="$comune"/>
                    </comune>
                    <via>
                        <xsl:value-of select="$via"/>
                    </via>
                    <civico>
                        <xsl:value-of select="$civico"/>
                    </civico>
                    <colore>
                        <xsl:value-of select="$colore"/>
                    </colore>
                    <lettera>
                        <xsl:value-of select="$lettera"/>
                    </lettera>
                    <scala>
                        <xsl:value-of select="$scala"/>
                    </scala>                     
                    <piano>
                        <xsl:value-of select="$piano"/>
                    </piano> 
                    <interno>
                        <xsl:value-of select="$interno"/>
                    </interno> 
                    <internoLettera>
                        <xsl:value-of select="$internoLettera"/>
                    </internoLettera>                                                           
                </indirizzo>
            </xsl:if>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>

