<?xml version="1.0"  encoding="UTF-8"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

 <xsl:template match="demandeEtatCivil">

  <div class="well">
      <p class="title">Confirmation</p>
   Votre demande nous a bien été transmise sous le numéro :
    <strong><xsl:value-of  select="identifiant" /></strong>.

    <br/><br/>
    Si vous avez indiqué votre adresse mèl, vous recevrez un accusé de réception de la demande.
   </div>


 </xsl:template>



</xsl:stylesheet>
