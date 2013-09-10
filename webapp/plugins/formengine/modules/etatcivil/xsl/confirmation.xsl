<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:param name="defaultUrlToExit"/>
    <xsl:param name="formName"/>

    <xsl:template match="formElements">

  <fieldset class="formengine-fieldset">
      <legend class="formengine-legend"></legend>
         <xsl:apply-templates select="notices/noticeGroup" />

    </fieldset>
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
    
    <xsl:template match="noticeGroup" >
        <p><ul>
            <xsl:apply-templates select="notice" />
       </ul></p> 
    </xsl:template>
    
    <xsl:template match="notice">
        <li>
            <xsl:value-of select="." disable-output-escaping="yes" />
        </li>
    </xsl:template>

</xsl:stylesheet>