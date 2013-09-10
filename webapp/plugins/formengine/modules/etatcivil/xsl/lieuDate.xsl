<?xml version="1.0"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 
  <xsl:include href="../../../xsl/formelements.xsl"/>
  
  
 <xsl:template match="formElements">
  
     <xsl:call-template name="mandatory-notice" />
     
     <xsl:apply-templates select="notices" /> 

     <fieldset class="formengine-fieldset">
          <legend>Mairie d'arrondissement où est détenu l'acte demandé :</legend>
          <xsl:apply-templates select="fields/field[@name='mairieActe']"/>  
     </fieldset>
     
     <fieldset class="formengine-fieldset">
          <legend>Date de l'acte :</legend>  
          <div class="formengine-element">
	    		<span class="formengine-label">
					<label for="{fields/field[@name='dateActeJour']/@name}">
					<span class="formengine-mandatory" >* </span>
						<xsl:value-of select="fields/field[@name='dateActeJour']/label"/>
						<xsl:text> :</xsl:text>
					</label>
					<xsl:apply-templates select="fields/field[@name='dateActeJour']/additionalInfo"/>
				</span>
			 	<span class="formengine-field" >
					<input type="text" name="{fields/field[@name='dateActeJour']/@name}" id="{fields/field[@name='dateActeJour']/@name}" value="{fields/field[@name='dateActeJour']/value}" size="1"/>
					<xsl:text> / </xsl:text>
					<input type="text" name="{fields/field[@name='dateActeMois']/@name}" id="{fields/field[@name='dateActeMois']/@name}" value="{fields/field[@name='dateActeMois']/value}" size="1"/>
					<xsl:text> / </xsl:text>
					<input type="text" name="{fields/field[@name='dateActeAnnee']/@name}" id="{fields/field[@name='dateActeAnnee']/@name}" value="{fields/field[@name='dateActeAnnee']/value}" size="3"/>
				</span>
		  </div>          
          <xsl:apply-templates select="fields/field[@name='fourchetteDateActe']"/>
          <xsl:apply-templates select="fields/field[@name='momentReconnaissance']"/>
     </fieldset>
     
     <div class="formengine-steps">
          <xsl:apply-templates select="buttons/button[@name='Etape suivante']"/>
     </div>
 
 </xsl:template>
 
 
 <xsl:template match="notices" >
      <fieldset class="formengine-fieldset">
          <legend>Remarques :</legend>
          <xsl:call-template name="notice-list" />
      </fieldset>    
 </xsl:template>
  
</xsl:stylesheet>
