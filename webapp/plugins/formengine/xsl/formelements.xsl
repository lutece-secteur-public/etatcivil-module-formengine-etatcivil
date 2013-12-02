<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- This stylesheet defines the general formatting for each form element -->
	<xsl:param name="formName"/>
	<xsl:param name="subFormName"/>
	<xsl:param name="portalURI"/>
	<xsl:param name="uploadSubmitPrefix"/>
	<xsl:param name="uploadDeletePrefix"/>
	<xsl:param name="uploadCheckboxPrefix"/>
	<xsl:param name="captcha"/>
	<xsl:param name="noticeValue"/>
	<xsl:param name="taille"/>
	<xsl:param name="baseUrl"/>
	<xsl:variable name="firstField" />
	<xsl:variable name="count" select="1"/>
	<xsl:variable name="actionUrl" select="'jsp/site/plugins/formengine/DoAction.jsp'" />

	<xsl:template match="/">

<!--  TODO : change form definition : if at least one  field[@type='upload'] then add enctype="multipart/form-data" -->

	<xsl:variable name="firstField" select="formElements/fields/field[position()=1]/@name" />
	<form action="{$baseUrl}{$actionUrl}" id="{$formName}{$subFormName}" name="{$formName}{$subFormName}" enctype="multipart/form-data"  method="post" class="formengine-form" >
		<input type="hidden" name="page" value="formengine"/>
		<input type="hidden" name="form" value="{$formName}"/>
		<input type="hidden" name="subform" value="{$subFormName}"/>
		<input type="hidden" name="portalURI" value="{$portalURI}"/>
		<xsl:apply-templates select="formElements"/>
	</form>

		<!--
		<script type="text/javascript">
			<xsl:text>document.forms['</xsl:text>
			<xsl:value-of select="$formName"/><xsl:value-of select="$subFormName"/>
			<xsl:text>'].elements['</xsl:text>
			<xsl:value-of select="$firstField"/>
			<xsl:text>'].focus();</xsl:text>
		</script>
		<noscript/>
		-->
		<script type="text/javascript">
			baseUrl = '<xsl:value-of select="$baseUrl" />';
		</script>


	</xsl:template>
	<!--**********************************************************************-->
	<!-- Element of type text -->
	<xsl:template match="field[@type='text']">

	    	<div class="formengine-element">
	    		<span class="formengine-label">
					<label for="{@name}">
							<xsl:if test="boolean(checkFieldRules/checkRule[@type='fieldRequired'])">
							<span class="formengine-mandatory" >* </span>
							</xsl:if>
							<xsl:value-of select="label"/>
							<xsl:text> :</xsl:text>
					</label>
							<xsl:apply-templates select="additionalInfo"/>


				</span>
				<span class="formengine-field" >
					<input type="text" name="{@name}" id="{@name}" value="{value}"/>
				</span>
			</div>

	</xsl:template>
	<!--**********************************************************************-->
	<!-- Element of type textarea -->
	<xsl:template match="field[@type='textarea']">

	    	<div class="formengine-element">
	    		<span class="formengine-label">
					<label for="{@name}">
							<xsl:if test="boolean(checkFieldRules/checkRule[@type='fieldRequired'])">
							<span class="formengine-mandatory" >* </span>
							</xsl:if>
							<xsl:value-of select="label"/>
							<xsl:text> :</xsl:text>
					</label>
							<xsl:apply-templates select="additionalInfo"/>


				</span>
				<span class="formengine-field" >
					<textarea name="{@name}" id="{@name}" rows="{@rows}" cols="{@cols}">
						<xsl:text/>
						<xsl:value-of select="value"/>
					</textarea>
				</span>
			</div>

	</xsl:template>
	
	<!--**********************************************************************-->
	<!-- Element of type checkbox -->
	<xsl:template match="field[@type='checkbox']">
			<div class="formengine-element">
				<span class="formengine-label">
					<label for="{@name}">
						<xsl:if test="boolean(checkFieldRules/checkRule[@type='fieldRequired'])">
							<span class="formengine-mandatory" >* </span>
						</xsl:if>
						<xsl:value-of select="label"/>
						<xsl:text> :</xsl:text>
					</label>
						<xsl:apply-templates select="additionalInfo"/>


				</span>
				<span class="formengine-field" >
					<!-- put the checkbox value if exist -->
					<xsl:choose>
						<xsl:when test="not(string(checkboxValue))">
							<input type="checkbox" name="{@name}" id="{@name}" value="1">
								<xsl:if test="value='1'">
									<xsl:attribute name="checked">checked</xsl:attribute>
								</xsl:if>
							</input>
						</xsl:when>
						<xsl:otherwise>
							<input type="checkbox" name="{@name}" id="{@name}" value="{checkboxValue}">
								<xsl:if test="value=checkboxValue">
									<xsl:attribute name="checked">checked</xsl:attribute>
								</xsl:if>
							</input>
						</xsl:otherwise>
					</xsl:choose>
				</span>
			</div>
	</xsl:template>
	<!--**********************************************************************-->
	<!-- Element of type combo -->
	<xsl:template match="field[@type='combo']">

		 	<div class="formengine-element">
	     		<span class="formengine-label">
	     			<label for="{@name}">
						<xsl:if test="boolean(checkFieldRules/checkRule[@type='fieldRequired'])">
						<span class="formengine-mandatory">* </span>
						</xsl:if>
						<xsl:value-of select="label"/>
						<xsl:text> :</xsl:text>
					</label>
						<xsl:apply-templates select="additionalInfo"/>


				</span>
				<span class="formengine-field" >
					<xsl:apply-templates select="choiceList"/>
				</span>
			</div>

	</xsl:template>
	<!-- Element of type choiceList for a combo -->
	<!-- choiceList is used to give the list of items provided by a combo -->
	<xsl:template match="choiceList[../@type='combo']">
				<select name="{../@name}" id="{../@name}" class="formengine-select">
					<xsl:apply-templates select="choice"/>
				</select>
	</xsl:template>
	<!-- Element of type choice of a choiceList for a combo -->
	<xsl:template match="choice[../../@type='combo']">
				<xsl:if test="../../value = @value">
					<option value="{@value}" selected="selected">
						<xsl:value-of select="@label"/>
					</option>
				</xsl:if>
				<xsl:if test="../../value != @value">
					<option value="{@value}">
						<xsl:value-of select="@label"/>
					</option>
				</xsl:if>
	</xsl:template>
	<!--**********************************************************************-->
	<!-- Element of type radio -->
	<xsl:template match="field[@type='radio']">

			<div class="formengine-element">
	    		<span class="formengine-label">
					<label id="{@name}">
						<xsl:if test="boolean(checkFieldRules/checkRule[@type='fieldRequired'])">
						<span class="formengine-mandatory">* </span>
						</xsl:if>
						<xsl:value-of select="label"/>
						<xsl:text> :</xsl:text>
					</label>
						<xsl:apply-templates select="additionalInfo"/>


				</span>
				<span class="formengine-field" >
					<xsl:apply-templates select="choiceList"/>
				</span>
			</div>

	</xsl:template>
	<!-- Element of type choiceList for a radio-->
	<!-- choiceList is used to give the list of items provided by a radio -->
	<xsl:template match="choiceList[../@type='radio']">
				<ul class="radio">
					<xsl:apply-templates select="choice"/>
				</ul>
	</xsl:template>
	<!-- Element of type choice of a choiceList for a radio-->
	<xsl:template match="choice[../../@type='radio']">
					<li>
						<label for="{../../@name}{@value}" >

							<xsl:if test="../../value = @value">
								<input name="{../../@name}" type="radio" value="{@value}" checked="checked" id="{../../@name}{@value}"/>
							</xsl:if>
							<xsl:if test="../../value != @value">
								<input name="{../../@name}" type="radio" value="{@value}" id="{../../@name}{@value}" />
							</xsl:if>

					    	<xsl:value-of select="@label"/>

						</label>
					</li>
	</xsl:template>




	<xsl:template match="additionalInfo">
				<br/>
				<span class="formengine-additionnal-info">
					<xsl:value-of select="."/>
				</span>
	</xsl:template>



	<!--**********************************************************************-->
	<!-- Element of type upload -->
	<xsl:template match="field[@type='upload']">
			<div class="formengine-element">
	    		<span class="formengine-label">
					<label for="{@name}">
						<xsl:if test="boolean(checkFieldRules/checkRule[@type='fieldRequired'])">
							<span class="formengine-mandatory">* </span>
						</xsl:if>
						<xsl:if test="checkFieldRules/checkRule[@type='maxLength']/@parameter &gt; 0">
							<input type="hidden" id="_formengine_upload_maxLength_{@name}" name="_formengine_upload_maxLength_{@name}" value="{checkFieldRules/checkRule[@type='maxLength']/@parameter}" />
						</xsl:if>
						<xsl:if test="string(checkFieldRules/checkRule[@type='fileTypes']/@parameter)">
							<input type="hidden" id="_formengine_upload_fileTypes_{@name}" name="_formengine_upload_fileTypes_{@name}" value="{checkFieldRules/checkRule[@type='fileTypes']/@parameter}" />
						</xsl:if>
						<xsl:if test="checkFieldRules/checkRule[@type='maxFiles']/@parameter  &gt; 0">
							<input type="hidden" id="_formengine_upload_maxFiles_{@name}" name="_formengine_upload_maxFiles_{@name}" value="{checkFieldRules/checkRule[@type='maxFiles']/@parameter}" />
						</xsl:if>
						<xsl:value-of select="label"/>
					</label>
					<xsl:text> :</xsl:text>
					<xsl:apply-templates select="additionalInfo"/>
				</span>
				<span class="formengine-field" >
					<input type="file" name="{@name}" id="{@name}" value=""/>
				</span>
			</div>
			<div class="formengine-element">
	    		<span class="formengine-label">
					<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
				</span>
				<span class="formengine-field" >
					<input type="submit" name="{$uploadSubmitPrefix}{@name}" id="{$uploadSubmitPrefix}{@name}" value="Transmettre" class="formengine-submit"/>
				</span>
			</div>
			<!-- Hide the div if no uploaded file.  -->
			<xsl:if test="count(fileNames/fileName)=0">
					<div id="_formengine_uploaded_files_label_{@name}" class="formengine-element hide">
						<span class="formengine-label">
				            <xsl:text disable-output-escaping="yes">
				            	<![CDATA[Documents d&eacute;j&agrave; transmis :]]>
				            </xsl:text>
						</span>
			       </div>
			       <div id="_formengine_uploaded_files_{@name}" class="hide"></div>
					<div id="_formengine_uploaded_files_button_{@name}" class="formengine-element hide">
			    		<span class="formengine-label"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></span>
						<span class="formengine-field" >
							<input type="submit" name="{$uploadDeletePrefix}{@name}" id="{$uploadDeletePrefix}{@name}" value="Supprimer" class="formengine-submit"/>
						</span>
					</div>
			</xsl:if>
    		<xsl:if test="count(fileNames/fileName)>0">
				<div id="_formengine_uploaded_files_label_{@name}" class="formengine-element">
					<span class="formengine-label">
			            <xsl:text disable-output-escaping="yes">
			            	<![CDATA[Documents d&eacute;j&agrave; transmis :]]>
			            </xsl:text>
					</span>
				</div>
				<div id="_formengine_uploaded_files_{@name}">
				<xsl:apply-templates select="fileNames"/>
				</div>
				<div id="_formengine_uploaded_files_button_{@name}" class="formengine-element">
		    		<span class="formengine-label"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></span>
					<span class="formengine-field" >
						<input type="submit" name="{$uploadDeletePrefix}{@name}" id="{$uploadDeletePrefix}{@name}" value="Supprimer" class="formengine-submit"/>
					</span>
				</div>
			</xsl:if>

	</xsl:template>
	<!-- The list of files already uploaded -->
	<xsl:template match="fileNames">
		<xsl:apply-templates select="fileName"/>
	</xsl:template>
	<!-- An uploaded file -->
	<xsl:template match="fileName">
		<xsl:variable name="index" select="position()-1" />
			<div class="formengine-element">
				<span class="formengine-label"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></span>
				<span class="formengine-field" >
					<label>
						<input type="checkbox"
							name="{$uploadCheckboxPrefix}{../../@name}{$index}"
							id="{$uploadCheckboxPrefix}{../../@name}{$index}"
						/>
						&#160;<xsl:value-of select="@value"/>
					</label>
				</span>
			</div>
	</xsl:template>


	<!--**********************************************************************-->
	<!-- Element button -->
	<xsl:template match="button[@type='submit']">
			<input type="{@type}" name="action" value="{@name}" class="formengine-submit" />
	</xsl:template>
	<xsl:template match="button[@type='reset']">
			<input type="{@type}" />
	</xsl:template>
	<xsl:template match="button[@type='link']">
			<a href="{$actionUrl}?page=formengine&amp;form={$formName}&amp;subform={$subFormName}&amp;action={@name}">
				<xsl:value-of select="@name"/>
			</a>
	</xsl:template>



	<!--**********************************************************************-->
	<!-- template to call to display all the fields of a form at once -->
	<xsl:template name="field-list">
		    <xsl:for-each select="/formElements/fields/field">
		    	<xsl:apply-templates select="." />
		    </xsl:for-each>
	</xsl:template>

	<!--**********************************************************************-->
	<!-- template to call to display all the buttons of a form at once -->
	<xsl:template name="button-list">
			    <xsl:for-each select="/formElements/buttons/button">
			    	<xsl:apply-templates select="."/>
			    </xsl:for-each>
	</xsl:template>


	<!--**********************************************************************-->
	<!-- template to call to display all the notices of a form at once -->
	<xsl:template name="notice-list">
	     <xsl:apply-templates select="/formElements/notices/noticeGroup" />
	</xsl:template>

	<xsl:template match="notices">
		<fieldset class="formengine-fieldset">
      		<p class="title">Informations :</p>
            <xsl:apply-templates select="noticeGroup" />
        </fieldset>
	</xsl:template>

    <!-- default templates, to display the notices in html lists -->
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


	<!-- templates to display the notices in paragraphs, instead of lists with bullets  -->
	<xsl:template match="noticeGroup" mode="no-bullet" >
        <p>
            <xsl:apply-templates select="notice" mode="no-bullet" />
       </p>
    </xsl:template>
    <xsl:template match="notice" mode="no-bullet">
        <p>
			<xsl:choose>
				<xsl:when test="string-length($noticeValue) &gt; 0">
					<!-- for retro-compatibility only, should not be used anymore -->
					<xsl:value-of select="$noticeValue" disable-output-escaping="yes" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="." disable-output-escaping="yes" />
				</xsl:otherwise>
			</xsl:choose>
        </p>
    </xsl:template>

	<xsl:template name="mandatory-notice">
	 	<p class="formengine-mandatory-message">Les champs annotés d'un <span class="formengine-mandatory">*</span> sont obligatoires.</p>
	</xsl:template>

	<!-- Captcha integration template -->
	<xsl:template name="captcha">
		<xsl:if test="$captcha != ''">
			<xsl:value-of select="$captcha" disable-output-escaping="yes" />
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>
