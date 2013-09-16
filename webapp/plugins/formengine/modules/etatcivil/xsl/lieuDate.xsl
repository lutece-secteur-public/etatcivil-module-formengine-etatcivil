<?xml version="1.0"  encoding="UTF-8"  ?>
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
     
     <fieldset class="formengine-fieldset">
          <legend>Remarques :</legend>  
          <ul>
            <li>Seuls les actes enregistrés à Paris peuvent être délivrés. Si la naissance a eu lieu dans une autre commune, veuillez consulter <a target="\_blank" href="https://www.acte-naissance.fr/DemandeActe/Accueil.do">service-public.fr</a>.</li>
            <li>
                <p>Les actes d'état civil conservés par les mairies d'arrondissement ne concernent que la période postérieure à 1903. Si la naissance a eu lieu avant 1903, l'acte pourra être obtenu auprès des Archives départementales de Paris.</p>
                <a target="\_blank" href="http://www.paris.fr/portail/Culture/Portal.lut?page_id=149">» Lien vers les archives de Paris</a>                 
            </li>
            <li>Les notaires, les avocats, les représentants légaux et les administrations spécifiquement autorisées qui peuvent avoir accès aux actes de l'état civil de la personne qui leur a donné mandat sont invités à formuler leur demande de copie intégrale ou d'extrait avec filiation de naissance par courrier et non par internet, compte tenu de la nécessité de produire un justificatif. 
            </li>
        </ul>
     </fieldset>
     
     <div class="formengine-steps">
          <xsl:apply-templates select="buttons/button[@name='Etape suivante']"/>
     </div>
 
 </xsl:template>
 
 
 <xsl:template match="notices" >
      <fieldset class="formengine-fieldset">
          <legend>Informations :</legend>
          <xsl:call-template name="notice-list" />
      </fieldset>    
 </xsl:template>
  
</xsl:stylesheet>
