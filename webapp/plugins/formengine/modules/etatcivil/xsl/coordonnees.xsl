<?xml version="1.0"  encoding="UTF-8"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

 <xsl:include href="../../../xsl/formelements.xsl"/>

 <xsl:template match="formElements">
    <div>
        <xsl:call-template name="mandatory-notice" />
    </div>
     <xsl:apply-templates select="notices" /> 

    <div class="well">
      <p class="title">Spécifier les coordonnées du demandeur :</p>

       <xsl:apply-templates select="fields/field[@name='civilite']"/>
       <xsl:apply-templates select="fields/field[@name='nom']"/>
       <xsl:apply-templates select="fields/field[@name='prenom']"/>
       <xsl:apply-templates select="fields/field[@name='adresse']"/>
       <xsl:apply-templates select="fields/field[@name='codePostal']"/>
       <xsl:apply-templates select="fields/field[@name='ville']"/>

    </div>

  <div class="well">
      <p class="title">Si l'adresse du demandeur est à l’étranger :</p>

       <xsl:apply-templates select="fields/field[@name='pays']"/>
       <xsl:apply-templates select="fields/field[@name='province']"/>
   </div>

   <div class="formengine-steps">
       <xsl:call-template name="button-list"/>
   </div>
</xsl:template>

</xsl:stylesheet>
