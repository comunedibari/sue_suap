<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : includeXml.xsl
    Created on : 21 aprile 2011, 10.40
    Author     : Piergiorgio
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="includeXml">
        <xsl:apply-templates select="document(@href)/*"/>
    </xsl:template>
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
