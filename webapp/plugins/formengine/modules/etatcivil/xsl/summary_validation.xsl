<?xml version="1.0"  encoding="UTF-8"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

 <xsl:template match="demandeEtatCivil">

      <xsl:apply-templates select="evenement" />

 </xsl:template>


 <xsl:template match="evenement">

  <div class="well">
      <p class="title">Validation des informations saisies</p>

     <xsl:choose>
         <xsl:when test="lieuEvenement/ville=''">

         <p>
              <strong>Vous n'avez pas indiqué dans votre demande
              l'arrondissement concerné par l'acte.
              Des recherches devront donc être effectuées auprès
              des vingt mairies parisiennes.
              En fonction des renseignements fournis,
              les recherches peuvent durer jusqu'à deux mois. </strong>
         </p>

         </xsl:when>
         <xsl:when test="dateEvenement/dateFin">
         <p>
             <h3>Vous n'avez pas indiqué dans votre demande une
             date précise de l'événement.
             Votre demande exige des recherches qui peuvent
             durer jusqu'à un mois. </h3>
         </p>

         </xsl:when>
         <xsl:otherwise>

         <p>
             Vous avez indiqué dans votre demande une date précise
              de l'événement et l'arrondissement concerné.
              Votre demande sera envoyée au service de l'état civil
              de la mairie <xsl:value-of select="lieuEvenement/ville" />.
              Votre demande sera traitée dans un délai de 72 heures
              (hors week-end et jours fériés et non compris
              les délais de poste).
         </p>


         </xsl:otherwise>

     </xsl:choose>
  </div>




 </xsl:template>



</xsl:stylesheet>
