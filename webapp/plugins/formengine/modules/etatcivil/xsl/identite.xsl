<?xml version="1.0"  encoding="UTF-8"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


  <xsl:include href="../../../xsl/formelements.xsl"/>

    <xsl:template match="formElements">
        <div>
            <xsl:call-template name="mandatory-notice" />
        </div>
        <div class="well">
        <p class="title">Spécifier l'identité de la personne dont vous demandez l'acte :</p>
	         <xsl:apply-templates select="fields/field[@name='nom']"/>
	         <xsl:apply-templates select="fields/field[@name='prenom']"/>
	         <xsl:apply-templates select="fields/field[@name='sexe']"/>
        </div>

        <xsl:if test="fields/field[@name='nomConjoint'] or fields/field[@name='prenomConjoint']">
	        <div class="well">
	            <p class="title">Renseignements facultatifs : </p>
		        <xsl:apply-templates select="fields/field[@name='nomConjoint']"/>
		        <xsl:apply-templates select="fields/field[@name='prenomConjoint']"/>
	        </div>
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
