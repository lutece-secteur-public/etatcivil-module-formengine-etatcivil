<?xml version="1.0"  encoding="UTF-8"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

 <xsl:include href="../../../xsl/formelements.xsl"/>

 <xsl:template match="formElements">

  <div class="well">
      <p class="title">Spécifier l'état civil du père de la personne dont l'acte est demandé :</p>

        <xsl:apply-templates select="fields/field[@name='nomPere']"/>
        <xsl:apply-templates select="fields/field[@name='prenomPere']"/>

    </div>

  <div class="well">
      <p class="title">Spécifier l'état civil de la mère de la personne dont l'acte est demandé :</p>
       <xsl:apply-templates select="fields/field[@name='nomMere']"/>
       <xsl:apply-templates select="fields/field[@name='prenomMere']"/>
   </div>

    <div class="formengine-steps">
          <xsl:call-template name="button-list"/>
      </div>

 </xsl:template>
 
  <xsl:template match="additionalInfo">
     <span class="formengine-additionnal-info">
         <xsl:value-of select="."/>
     </span>
    </xsl:template>
</xsl:stylesheet>
