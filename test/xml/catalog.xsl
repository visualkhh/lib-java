<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">
 
    <xsl:output method="html" />

    <xsl:attribute-set name="imgAttrs">
        <xsl:attribute name="width">150</xsl:attribute>
        <xsl:attribute name="border">1</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:variable name="author">
        <xsl:text>Author</xsl:text>
    </xsl:variable>

    <xsl:template match="/">
        <HTML>
            <HEAD>
                <TITLE>Book Catalog</TITLE>
            </HEAD>
            <BODY>
                <h2>Book Catalog</h2>
                <hr color="black" />
                <xsl:apply-templates select="Catalog/Book" />
            </BODY>
        </HTML>
    </xsl:template>

    <xsl:template match="Catalog/Book">
        <xsl:variable name="titleNode" select="Title" />
        <p>
            <xsl:choose>
                <xsl:when test="$titleNode/@part='XML'">
                    <font color="red">
                        <b><xsl:value-of select="$titleNode" /></b>
                    </font>
                </xsl:when>
                <xsl:when test="$titleNode/@part='XSLT'">
                    <font color="blue">
                        <b><xsl:value-of select="$titleNode" /></b>
                    </font>
                </xsl:when>
                <xsl:otherwise>
                    <font color="black">
                        <b><xsl:value-of select="$titleNode" /></b>
                    </font>
                </xsl:otherwise>
            </xsl:choose>
        </p>
        <p>
            <b><xsl:value-of select="$author" /> : </b>
            <xsl:for-each select="Authors/Author">
                <xsl:sort select="." order="descending" />
                <xsl:variable name="position" select="position()" />
                <xsl:number value="$position" format="1"/>
                <xsl:text>. </xsl:text>
                <xsl:value-of select="." />
                <xsl:if test="$position != last()">, </xsl:if>
            </xsl:for-each>
        </p>
        <xsl:call-template name="priceTemplate" />
        <p>
            <img xsl:use-attribute-sets="imgAttrs">
                <xsl:attribute name="src">
                    <xsl:value-of select="CoverImage" />
                </xsl:attribute>
            </img>
        </p>
    </xsl:template>
    
    <xsl:template name="priceTemplate">
        <xsl:variable name="price" select="Price" />
        <p>
            <b>Price : </b>
            <xsl:value-of select="concat('$ ', $price)" />
        </p>
    </xsl:template>

</xsl:stylesheet>
