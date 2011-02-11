<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:output method="html" encoding="ISO-8859-1" indent="yes"/>
 <xsl:template match="demandeEtatCivil">
<html>
	<body><xsl:text>
    Confirmation,</xsl:text><br /><xsl:text>
     Votre demande est bien enregistrée sous le numéro : </xsl:text><xsl:value-of select="identifiant" /><br /><xsl:text>
	 Vous recevrez un message dès que votre demande sera traitée.</xsl:text><br /><xsl:text>     
     Merci de ne pas répondre à ce mèl.</xsl:text><br /><xsl:text>
     ---------------------------------------------------------------------</xsl:text><br /><xsl:text>
     Le Service d'état civil de la Ville de Paris.</xsl:text>
</body>
</html>
 </xsl:template>
</xsl:stylesheet>
