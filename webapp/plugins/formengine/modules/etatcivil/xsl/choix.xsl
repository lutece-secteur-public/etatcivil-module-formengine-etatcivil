<?xml version="1.0"  encoding="UTF-8"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

 <xsl:include href="../../../xsl/formelements.xsl"/>

 <xsl:template match="formElements">

  <xsl:call-template name="mandatory-notice" />

  <fieldset class="formengine-fieldset">
      <legend class="formengine-legend">Type d'acte demandé :</legend>
        <xsl:apply-templates select="fields/field[@name='typeActe']"/>
        <xsl:apply-templates select="fields/field[@name='infoActes']"/>
    </fieldset>

  <fieldset class="formengine-fieldset">
      <legend class="formengine-legend">Nombre d'actes demandés :</legend>
     <xsl:apply-templates select="fields/field[@name='nombreActes']"/>
     </fieldset>

     <xsl:apply-templates select="notices" />

       <div class="formengine-steps">
          <xsl:call-template name="button-list"/>
      </div>

     </xsl:template>

      <!-- redefinition of template -->
     <xsl:template match="noticeGroup[@name='informations']" mode="no-bullet" >
        <fieldset class="formengine-fieldset">
        <legend class="formengine-legend">Informations :</legend>
        <p>
            <xsl:apply-templates select="notice" mode="no-bullet"/>
        </p>
        </fieldset>
     </xsl:template>
     
           <!-- redefinition of template -->
     <xsl:template match="field[@name='infoActes']">
         <xsl:value-of select="label" disable-output-escaping="yes"/>
     </xsl:template>
     

</xsl:stylesheet>
