<?xml version="1.0"  encoding="UTF-8"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

 <xsl:include href="../../../xsl/formelements.xsl"/>

 <xsl:template match="formElements">


   <xsl:call-template name="mandatory-notice" />

  <fieldset class="formengine-fieldset">
      <legend class="formengine-legend">Remarques :</legend>

     <xsl:call-template name="notice-list"/>

    </fieldset>

  <fieldset class="formengine-fieldset">
        <legend class="formengine-legend">Spécifier les informations complémentaires :</legend>
        <fieldset class="formengine-fieldset" style="margin-top:0px;">
            <legend class="formengine-legend">Avertissement :</legend>
            Avant validation, vérifiez la bonne orthographe de votre adresse électronique.<br/>
            Des erreurs de saisie peuvent entraîner l'envoi à tort d'informations dans la boîte d'un tiers. 
            La Mairie de Paris ne saurait être tenue pour responsable d'une transmission erronée.
        </fieldset>
        
        <xsl:call-template name="field-list"/>
   </fieldset>

   <div class="formengine-steps">
          <xsl:call-template name="button-list"/>
  </div>


 </xsl:template>
</xsl:stylesheet>
