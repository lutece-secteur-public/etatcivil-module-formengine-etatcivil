<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">



 <xsl:template match="demandeEtatCivil">

  <fieldset class="formengine-fieldset">
      <legend class="formengine-legend">Récapitulatif des informations saisies</legend>
    <p>
    <h3>Lieu et date de l'acte :</h3>
        <xsl:apply-templates select="evenement" />
    </p>

    <p>
    <h3> Etat civil de la personne dont l'acte est demandé :</h3>
        <xsl:apply-templates select="evenement/interesse" />
        <xsl:apply-templates select="evenement/conjoint" />
    </p>

    <p>
    <h3>Choix de l'acte :</h3>
        <ul>
        <li>
        <strong>Type : </strong><xsl:apply-templates select="natureDocument"/>
        </li>
        <li>
        <strong>Nombre d'exemplaires :  </strong><xsl:apply-templates select="nbExemplaire"/>
        </li>
        </ul>
    </p>



    <p>
    <h3> Filiation :</h3>
        <ul>
         <li><strong>Nom du père : </strong><xsl:apply-templates select="evenement/interesse/pere/noms/nomDeFamille"/></li>
         <li><strong>Prénom du père : </strong><xsl:apply-templates select="evenement/interesse/pere/prenoms"/></li>
        </ul>
        <ul>
         <li><strong>Nom de la mère : </strong><xsl:apply-templates select="evenement/interesse/mere/noms/nomDeFamille"/></li>
         <li><strong>Prénom de la mère : </strong><xsl:apply-templates select="evenement/interesse/mere/prenoms"/></li>
      </ul>

    </p>

    <p>
    <h3> Coordonnées du demandeur :</h3>
        <xsl:apply-templates select="demandeur/individu" />
    </p>

    <p>
    <h3> Informations complémentaires :</h3>
        <ul>
        <li><strong>Téléphone : </strong><xsl:apply-templates select="demandeur/individu/adresse/tel"/></li>
        <li><strong>Mèl : </strong><xsl:apply-templates select="demandeur/individu/adresse/mail"/></li>
        <li><strong>Lien : </strong><xsl:apply-templates select="demandeur/qualiteDemandeur"/></li>
        <li><strong>Motif : </strong><xsl:apply-templates select="motif"/></li>
        </ul>
    </p>

   </fieldset>

 </xsl:template>


 <xsl:template match="natureDocument">
     <xsl:if test="current()='CPI'">Copie intégrale de l'acte</xsl:if>
     <xsl:if test="current()='EXTAF'">Extrait d'acte avec filiation</xsl:if>
     <xsl:if test="current()='EXTSF'">Extrait d'acte sans filiation</xsl:if>
     <xsl:if test="current()='EXTPL'">Extrait plurilingue</xsl:if>
 </xsl:template>


 <xsl:template match="evenement">
     <ul>
     <li>
             <strong>Lieu de l'acte : </strong><xsl:apply-templates select="lieuEvenement/ville"/>
     </li>
     <li>
             <xsl:apply-templates select="dateEvenement" />
     </li>
     </ul>


 </xsl:template>

  <xsl:template match="dateEvenement">

       <xsl:choose>
         <xsl:when test="substring-before(dateDebut,'-')=substring-before(dateFin,'-')">
             <strong>Année : </strong>
             <xsl:value-of select="substring-before(dateDebut,'-')"/>
         </xsl:when>
         <xsl:when test="dateFin">
             <strong>Plage année : </strong>
             <xsl:value-of select="substring-before(dateDebut,'-')"/>
             <xsl:text> - </xsl:text>
             <xsl:value-of select="substring-before(dateFin,'-')"/>
         </xsl:when>
         <xsl:otherwise>
             <strong>Date de l'évènement : </strong>
             <xsl:variable name="year" select="substring-before(dateDebut,'-')"/>
		     <xsl:variable name="monthAndDay" select="substring-after(dateDebut,'-')"/>
		     <xsl:variable name="month" select="substring-before($monthAndDay,'-')"/>
		     <xsl:variable name="day" select="substring-after($monthAndDay,'-')"/>
			<xsl:value-of select="concat($day,'/', $month,'/', $year)"/><br />
         </xsl:otherwise>
     </xsl:choose>

 </xsl:template>

 <xsl:template match="interesse">
     <ul>
     <li><strong>Nom : </strong><xsl:apply-templates select="noms/nomDeFamille"/></li>
     <li><strong>Prénom : </strong><xsl:apply-templates select="prenoms"/></li>
     <li><strong>Sexe : </strong><xsl:apply-templates select="sexe" /></li>
      </ul>
 </xsl:template>
  <xsl:template match="sexe">
      <xsl:if test="current()='M'">
          Masculin
      </xsl:if>
      <xsl:if test="current()='F'">
          Féminin
      </xsl:if>
      <xsl:if test="current()='NA'">
          Non connu
      </xsl:if>

 </xsl:template>

 <xsl:template match="conjoint">
     <ul>
     <li><strong>Nom du conjoint : </strong><xsl:apply-templates select="noms/nomDeFamille"/></li>
     <li><strong>Prénom du conjoint : </strong><xsl:apply-templates select="prenoms"/></li>
      </ul>
 </xsl:template>

 <xsl:template match="individu">
    <ul>
     <li><strong>Civilité : </strong><xsl:apply-templates select="genre" /></li>
     <li><strong>Nom : </strong><xsl:apply-templates select="noms/nomDeFamille"/></li>
     <li><strong>Prénom : </strong><xsl:apply-templates select="prenoms"/></li>
     <li><strong>Adresse : </strong><xsl:apply-templates select="adresse/ligneAdr1"/></li>
     <li><strong>Code postal : </strong><xsl:apply-templates select="adresse/codePostal"/></li>
     <li><strong>Ville : </strong><xsl:apply-templates select="adresse/lieu/ville"/></li>
     <li><strong>Province : </strong><xsl:apply-templates select="adresse/lieu/province"/></li>
     <li><strong>Pays : </strong><xsl:apply-templates select="adresse/lieu/pays"/></li>
     </ul>
 </xsl:template>


  <xsl:template match="nomDeFamille">
      <xsl:value-of select="."/>
 </xsl:template>
  <xsl:template match="prenoms">
      <xsl:value-of select="."/>
 </xsl:template>
  <xsl:template match="ville">
      <xsl:value-of select="."/>
 </xsl:template>
   <xsl:template match="province">
      <xsl:value-of select="."/>
 </xsl:template>
   <xsl:template match="pays">
      <xsl:value-of select="."/>
 </xsl:template>
   <xsl:template match="genre">
      <xsl:value-of select="."/>
 </xsl:template>
   <xsl:template match="ligneAdr1">
      <xsl:value-of select="."/>
 </xsl:template>
   <xsl:template match="codePostal">
      <xsl:value-of select="."/>
 </xsl:template>
   <xsl:template match="tel">
      <xsl:value-of select="."/>
 </xsl:template>
   <xsl:template match="mail">
      <xsl:value-of select="."/>
 </xsl:template>
 <xsl:template match="qualiteDemandeur">
      <xsl:value-of select="."/>
 </xsl:template>
   <xsl:template match="motif">
      <xsl:value-of select="."/>
 </xsl:template>

   <xsl:template match="nbExemplaire">
      <xsl:value-of select="."/>
 </xsl:template>
</xsl:stylesheet>
