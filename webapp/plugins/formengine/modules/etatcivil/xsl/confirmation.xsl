<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


    <xsl:param name="defaultUrlToExit"/>
    <xsl:param name="formName"/>

    <xsl:template match="formElements">

        <xsl:apply-templates select="buttons/button[@name='OK']"/>

    </xsl:template>

    <xsl:template match="button[@name='OK']">
           <form action="{$defaultUrlToExit}" >
               <input type="hidden" name="page" value="formengine" />
               <input type="hidden" name="form" value="{$formName}" />

               <div class="formengine-steps">
               <input type="submit" value="{@name}" class="formengine-submit" />
               </div>
           </form>
    </xsl:template>

</xsl:stylesheet>