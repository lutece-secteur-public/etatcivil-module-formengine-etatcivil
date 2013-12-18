<?xml version="1.0"  encoding="UTF-8"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

 <xsl:include href="../../../xsl/formelements.xsl"/>

 <xsl:template match="formElements">
    <div>
        <xsl:call-template name="mandatory-notice" />
    </div>
  <div class="well">
      <p class="title">Type d'acte demandé :</p>
        <xsl:apply-templates select="fields/field[@name='typeActe']"/>
        <xsl:apply-templates select="fields/field[@name='infoActes']"/>
    </div>

    <div class="well">
      <p class="title">Nombre d'actes demandés :</p>
     <xsl:apply-templates select="fields/field[@name='nombreActes']"/>
     </div>

     <xsl:apply-templates select="notices" />

       <div class="formengine-steps">
          <xsl:call-template name="button-list"/>
      </div>

     </xsl:template>

     <!-- redefinition of template -->
     <xsl:template match="field[@name='infoActes']">
         <xsl:value-of select="label" disable-output-escaping="yes"/>
     </xsl:template>

</xsl:stylesheet>
