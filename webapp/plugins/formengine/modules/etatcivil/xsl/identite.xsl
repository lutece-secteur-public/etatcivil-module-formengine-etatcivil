<?xml version="1.0"  encoding="UTF-8"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


  <xsl:include href="../../../xsl/formelements.xsl"/>

 <xsl:template match="formElements">
    <div class="span11">
      <xsl:call-template name="mandatory-notice" />
    </div>
  <fieldset class="span11 formengine-fieldset">
      <legend class="formengine-legend">Spécifier l'identité de la personne dont vous demandez l'acte :</legend>

         <xsl:apply-templates select="fields/field[@name='nom']"/>
         <xsl:apply-templates select="fields/field[@name='prenom']"/>
         <xsl:apply-templates select="fields/field[@name='sexe']"/>

      </fieldset>

   <xsl:if test="fields/field[@name='nomConjoint'] or fields/field[@name='prenomConjoint']">

  <fieldset class="span11 formengine-fieldset">
      <legend class="formengine-legend">Renseignements facultatifs : </legend>

        <xsl:apply-templates select="fields/field[@name='nomConjoint']"/>
        <xsl:apply-templates select="fields/field[@name='prenomConjoint']"/>

      </fieldset>

   </xsl:if>

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
