<?xml version="1.0"  encoding="UTF-8"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

 <xsl:include href="../../../xsl/formelements.xsl"/>

 <xsl:template match="formElements">

    <div>
        <xsl:call-template name="mandatory-notice" />
    </div>
    <div class="well">
        <p class="title">Remarques :</p>
        <xsl:call-template name="notice-list"/>
    </div>
    <div class="well">
        <p class="title">Spécifier les informations complémentaires :</p>
        <div class="inline-well" style="margin-top:0px;">
            <p class="title">Avertissement :</p>
            Avant validation, vérifiez la bonne orthographe de votre adresse électronique.<br/>
            Des erreurs de saisie peuvent entraîner l'envoi à tort d'informations dans la boîte d'un tiers. 
            La Mairie de Paris ne saurait être tenue pour responsable d'une transmission erronée.
        </div>
        <xsl:call-template name="field-list"/>
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
