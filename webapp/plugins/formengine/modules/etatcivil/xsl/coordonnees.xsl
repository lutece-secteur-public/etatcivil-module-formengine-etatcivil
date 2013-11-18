<?xml version="1.0"  encoding="UTF-8"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

 <xsl:include href="../../../xsl/formelements.xsl"/>

 <xsl:template match="formElements">
    <div class="span11">
        <xsl:call-template name="mandatory-notice" />
    </div>
     <xsl:apply-templates select="notices" /> 

    <fieldset class="span11 formengine-fieldset">
      <legend class="formengine-legend">Spécifier les coordonnées du demandeur :</legend>

       <xsl:apply-templates select="fields/field[@name='civilite']"/>
       <xsl:apply-templates select="fields/field[@name='nom']"/>
       <xsl:apply-templates select="fields/field[@name='prenom']"/>
       <xsl:apply-templates select="fields/field[@name='adresse']"/>
       <xsl:apply-templates select="fields/field[@name='codePostal']"/>
       <xsl:apply-templates select="fields/field[@name='ville']"/>

    </fieldset>

  <fieldset class="span11 formengine-fieldset">
      <legend class="formengine-legend">Si l'adresse du demandeur est à l’étranger :</legend>

       <xsl:apply-templates select="fields/field[@name='pays']"/>
       <xsl:apply-templates select="fields/field[@name='province']"/>

   </fieldset>

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

</xsl:stylesheet>
