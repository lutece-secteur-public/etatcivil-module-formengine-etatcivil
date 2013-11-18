<?xml version="1.0"  encoding="UTF-8"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

 <xsl:include href="../../../xsl/formelements.xsl"/>

 <xsl:template match="formElements">
    <div class="span11">
        <xsl:call-template name="mandatory-notice" />
    </div>
  <fieldset class="span11 formengine-fieldset">
      <legend class="formengine-legend">Type d'acte demandé :</legend>
        <xsl:apply-templates select="fields/field[@name='typeActe']"/>
        <xsl:apply-templates select="fields/field[@name='infoActes']"/>
    </fieldset>

  <fieldset class="span11 formengine-fieldset">
      <legend class="formengine-legend">Nombre d'actes demandés :</legend>
     <xsl:apply-templates select="fields/field[@name='nombreActes']"/>
     </fieldset>

     <xsl:apply-templates select="notices" />

       <div class="formengine-steps">
          <xsl:call-template name="button-list"/>
      </div>

     </xsl:template>

    <xsl:template match="notices">
        <fieldset class="span11 formengine-fieldset">
            <legend class="formengine-legend">Informations :</legend>
            <xsl:apply-templates select="noticeGroup" />
        </fieldset>
    </xsl:template>
     
     <!-- redefinition of template -->
     <xsl:template match="field[@name='infoActes']">
         <xsl:value-of select="label" disable-output-escaping="yes"/>
     </xsl:template>

</xsl:stylesheet>
