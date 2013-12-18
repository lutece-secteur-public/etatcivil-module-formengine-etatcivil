/*
 * Copyright (c) 2002-2009, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.formengine.modules.etatcivil.web;

import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.DemandeEtatCivil;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.ObjectFactory;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.service.output.EtatCivilDatabaseIdGenerator;
//import fr.paris.lutece.plugins.formengine.modules.etatcivil.service.output.EtatCivilIdGenerator;
import fr.paris.lutece.plugins.formengine.service.output.GeneratorFactory;
import fr.paris.lutece.plugins.formengine.service.output.IdGenerator;
import fr.paris.lutece.plugins.formengine.web.CaptchaSubForm;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * ValidationSubForm
 */
public class ValidationSubForm extends CaptchaSubForm
{
    private static final String PROPERTY_FRAGMENT_XSL_FILE_SUMMARY = ".xsl.file.summary.validation";
    private static final String PROPERTY_FRAGMENT_XSL_FILE_FORM = ".xsl.file.form.validation";
    private static final String PROPERTY_FRAGMENT_TEMPLATE_MENU_HEADER = ".template.header.menu";
    private static final String PROPERTY_FRAGMENT_TEMPLATE_HEADER = ".template.header.sans_onglet";

    /**
     *
     */
    public ValidationSubForm(  )
    {
        super(  );
    }

    /**
     * @see fr.paris.lutece.plugins.formengine.web.SubForm#doAction(java.lang.String, javax.servlet.http.HttpServletRequest)
     */
    public String doAction( String strActionName, HttpServletRequest request )
    {
        String strNextSubForm = "";

        if ( strActionName == null )
        {
            strNextSubForm = this.getName(  );
        }
        else if ( strActionName.equals( Constants.ACTION_NAME_CONFIRM ) )
        {
            boolean bValid = doSetIdAnDate( request );
            this.getParentForm(  ).processForm( request );

            if ( bValid )
            {
                strNextSubForm = this.getNextSubForm(  ).getName(  );
                this.getParentForm(  ).setIsAccessToSubFormAllowed( request, strNextSubForm, true );
            }
            else
            {
                strNextSubForm = this.getName(  );
            }
        }
        else if ( strActionName.equals( Constants.ACTION_NAME_MODIFY ) )
        {
            strNextSubForm = this.getPreviousSubForm(  ).getName(  );
        }
        else
        {
            strNextSubForm = this.getName(  );
        }

        return strNextSubForm;
    }

    /**
         * @param request
         * @return
         */
    private boolean doSetIdAnDate( HttpServletRequest request )
    {
        boolean bValid = false;

        // set the fields' content
        this.fillFields( request );

        // check that the submitted data is valid
        bValid = this.validateFields( request );

        if ( bValid )
        {
            // create the ObjectFactory
            ObjectFactory factory = new ObjectFactory(  );

            // retrieve the root element in session
            DemandeEtatCivil demandeEtatCivil;
            demandeEtatCivil = (DemandeEtatCivil) getParentForm(  ).getFormDocument( request );

            if ( demandeEtatCivil == null )
            {
                demandeEtatCivil = factory.createDemandeEtatCivil(  );
            }

            //IdGenerator idGenerator = new EtatCivilIdGenerator();
            IdGenerator idGenerator = GeneratorFactory.getGenerator( EtatCivilDatabaseIdGenerator.class.getName(  ) );

            if ( idGenerator != null )
            {
                demandeEtatCivil.setIdentifiant( idGenerator.getNewId( this.getParentForm(  ) ) );
                demandeEtatCivil.setDateDemande( new GregorianCalendar(  ) );
                this.getParentForm(  ).setFormDocument( request, demandeEtatCivil );
            }
            else
            {
                bValid = false;
            }
        }

        return bValid;
    }

    /**
     * @see fr.paris.lutece.plugins.formengine.web.SubForm#displaySummary(javax.servlet.http.HttpServletRequest)
     */
    protected String displaySummary( HttpServletRequest request )
    {
        String strHtml = "";

        // get the data object in session
        DemandeEtatCivil demandeEtatCivil = (DemandeEtatCivil) getParentForm(  ).getFormDocument( request );

        // set temp values to allow generation
        demandeEtatCivil.setDateDemande( new GregorianCalendar(  ) );
        demandeEtatCivil.setIdentifiant( "temp" );

        // generate xml
        String strXml = getParentForm(  ).getXmlFormDocument( request );

        // generate html code via xsl transform
        String strFileName = AppPropertiesService.getProperty( getPropertyXSLFileSummary(  ) );
        Map<String, Object> params = new HashMap<String, Object>();
        strHtml = this.getHtml( strXml, strFileName, params );

        // reset temp values
        demandeEtatCivil.setDateDemande( null );
        demandeEtatCivil.setIdentifiant( null );

        return strHtml;
    }

    /**
     * @see fr.paris.lutece.plugins.formengine.web.SubForm#getXslFormElementsFileName()
     */
    protected String getXslFormElementsFileName(  )
    {
        return AppPropertiesService.getProperty( Constants.SHARED_PROPERTY_PREFIX + PROPERTY_FRAGMENT_XSL_FILE_FORM );
    }

    private String getPropertyXSLFileSummary(  )
    {
    	return Constants.SHARED_PROPERTY_PREFIX + PROPERTY_FRAGMENT_XSL_FILE_SUMMARY;
    }
    
    /**
     * 
     *{@inheritDoc}
     */
    @Override
    public String[] getXslFilesNames()
    {
    	return new String[] { getXslFormElementsFileName(  ), AppPropertiesService.getProperty( getPropertyXSLFileSummary(  ) ), };
    }
    
    protected String displayHeader( HttpServletRequest request )
    {
        HtmlTemplate template = AppTemplateService.getTemplate( AppPropertiesService.getProperty( getParentForm(  )
                                                                                                      .getName(  ) +
                    PROPERTY_FRAGMENT_TEMPLATE_HEADER ) );

        template.substitute( Constants.BOOKMARK_FORM_TITLE, this.getParentForm(  ).getTitle(  ) );
        template.substitute( Constants.BOOKMARK_SUBFORM_TITLE, this.getTitle(  ) );

        HtmlTemplate templateMenu = AppTemplateService.getTemplate( AppPropertiesService.getProperty( getParentForm(  )
                                                                                                          .getName(  ) +
                    PROPERTY_FRAGMENT_TEMPLATE_MENU_HEADER ) );
        template.substitute( Constants.BOOKMARK_MENU_HEADER, templateMenu.getHtml(  ) );

        return template.getHtml(  );
    }
}
