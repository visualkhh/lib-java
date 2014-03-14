<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:template match="/">
<html>

   <head>
      <title>CD Catalog</title>
      <style type="text/css">
        table {
            font-family: verdana;
        }

          tr {
              height: 30px;
          }

          td.col1 {
              width: 100px;
          }

          td.col2 {
              width: 400px;
          }

          td.title {
              background: #efe7d9 ;
              border-bottom: 1px solid #336699;
              font: 16 verdana;
              padding: 0 0 0 15px;
          }

          .red {
              color: #ff0000;
          }

          .green {
              color: #249821;
          }

          td.greenRow
          {
              background: #d6efd6;
              border-bottom: 1px solid #437841;

          }
      </style>
   </head>

   <body>
      <table>
         <xsl:for-each select="catalog/cd">
             <!-- if artist is assigned enya, set bg to green. -->
            <tr>
            <xsl:choose>
                   <xsl:when test="artist = 'Enya'">
                       <td colspan="2" class="greenRow">
                          <xsl:value-of select="title" />
                       </td>
                   </xsl:when>
                   <xsl:otherwise>
                       <td colspan="2"><xsl:value-of select="title" /></td>
                   </xsl:otherwise>
            </xsl:choose>
            </tr>
            <tr>
               <td>Artist:</td>
               <td><xsl:value-of select="artist" /></td>
            </tr>
            <tr>
               <td>Company:</td>
               <td><xsl:value-of select="company" /></td>
            </tr>
            <tr>
               <td>Country:</td>
               <td><xsl:value-of select="country" /></td>
            </tr>
            <tr>
               <td>Year:</td>
               <td><xsl:value-of select="year" /></td>
            </tr>
            <tr>
               <td>Price:</td>

               <!-- if price greater than $10, show in red.
                    Otherwise, show green -->
               <xsl:choose>
                       <xsl:when test="price &gt; 10">
                           <td class="green">
                              $<xsl:value-of select="price" />
                           </td>
                       </xsl:when>
                       <xsl:otherwise>
                           <td class="red">
                              $<xsl:value-of select="price" />
                           </td>
                       </xsl:otherwise>
               </xsl:choose>
            </tr>
         </xsl:for-each>
      </table>
    </body>
</html>
</xsl:template>
</xsl:stylesheet>