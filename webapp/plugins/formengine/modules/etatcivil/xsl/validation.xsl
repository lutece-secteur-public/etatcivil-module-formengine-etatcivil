<?xml version="1.0"  encoding="UTF-8"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:include href="../../../xsl/formelements.xsl"/>

 <xsl:template match="formElements">

    <div class="formengine-steps">
      <xsl:apply-templates select="buttons/button[@name='Modifier']"/>
    </div>


  <fieldset class="formengine-fieldset">
    <legend class="formengine-legend">Informations complementaires</legend>
      <xsl:call-template name="notice-list"/>
    </fieldset>

    <div class="formengine-steps">
      <xsl:apply-templates select="buttons/button[@name='Confirmer']"/>
    </div>


 </xsl:template>
</xsl:stylesheet>
