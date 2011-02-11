<?xml version="1.0"  encoding="UTF-8"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


  <xsl:include href="../../../xsl/formelements.xsl"/>

  <xsl:param name="showChildFields" />

 <xsl:template match="formElements">

   <xsl:call-template name="mandatory-notice" />

   <fieldset class="formengine-fieldset">
    <legend class="formengine-legend"> Spécifier l'identité du ou des auteurs de la reconnaissance :</legend>

     <xsl:apply-templates select="fields/field[@name='nomMere']"/>
     <xsl:apply-templates select="fields/field[@name='prenomMere']"/>


     <xsl:apply-templates select="fields/field[@name='nomPere']"/>
     <xsl:apply-templates select="fields/field[@name='prenomPere']"/>
		<xsl:text>Le nom et le prénom du père OU de la mère doivent être spécifiés. </xsl:text>
     </fieldset>


    <xsl:if test="$showChildFields" >
     <fieldset class="formengine-fieldset">
     <legend class="formengine-legend">Spécifier l'identité de l'enfant reconnu :</legend>
     <xsl:apply-templates select="fields/field[@name='nom']"/>
     <xsl:apply-templates select="fields/field[@name='prenom']"/>
     </fieldset>
    </xsl:if>

     <fieldset class="formengine-fieldset">
    <legend class="formengine-legend">Nombre d'actes demandés :</legend>
     <xsl:apply-templates select="fields/field[@name='nombreActes']"/>
    </fieldset>

	<xsl:apply-templates select="notices" />
   <div class="formengine-steps">
       <xsl:apply-templates select="buttons/button[@name='Etape précédente']"/>
       <xsl:apply-templates select="buttons/button[@name='Etape suivante']"/>
    </div>



 </xsl:template>

 <!-- Redefine generic template to force mandatory display -->
 <xsl:template match="field[@name='nom' or @name='prenom']">

	    	<div class="formengine-element">
	    		<span class="formengine-label">
					<label for="{@name}">

							<span class="formengine-mandatory" >* </span>

							<xsl:value-of select="label"/>
							<xsl:text> :</xsl:text>

							<xsl:apply-templates select="additionalInfo"/>

					</label>
				</span>
				<span class="formengine-field" >
					<input type="text" name="{@name}" id="{@name}" value="{value}"/>
				</span>
			</div>

	</xsl:template>



</xsl:stylesheet>
