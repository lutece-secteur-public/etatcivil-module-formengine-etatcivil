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

import fr.paris.lutece.plugins.formengine.business.jaxb.formdefinition.Field;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.AdresseType;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.DemandeEtatCivil;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.DemandeurType;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.GenreType;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.IndividuType;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.LieuType;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.NomsType;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.ObjectFactory;
import fr.paris.lutece.plugins.formengine.web.SubForm;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.HtmlTemplate;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


/**
 * @author lutecer
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CoordonneesSubForm extends SubForm
{
    private static final String PROPERTY_FRAGMENT_XSL_FILE_FORM = ".xsl.file.form.coordonnees";
    private static final String PROPERTY_FRAGMENT_TEMPLATE_MENU_HEADER = ".template.header.menu";
    private static final String PROPERTY_FRAGMENT_TEMPLATE_HEADER = ".template.header.coordonnees";

    private static final String FIELD_NAME_NOM = "nom";
    private static final String FIELD_NAME_PRENOM = "prenom";

    /**
     *
     */
    public CoordonneesSubForm( )
    {
        super( );
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.paris.lutece.plugins.formengine.web.SubForm#doAction(java.lang.String,
     * javax.servlet.http.HttpServletRequest)
     */
    public String doAction( String strActionName, HttpServletRequest request )
    {
        String strNextSubForm = "";

        if ( strActionName == null )
        {
            strNextSubForm = this.getName( );
        }
        else if ( strActionName.equals( Constants.ACTION_NAME_NEXT ) )
        {
            boolean bValid = doSetCoordonnees( request );

            if ( bValid )
            {
                strNextSubForm = this.getNextSubForm( ).getName( );
                this.getParentForm( ).setIsAccessToSubFormAllowed( request, strNextSubForm, true );
            }
            else
            {
                strNextSubForm = this.getName( );
            }
        }
        else if ( strActionName.equals( Constants.ACTION_NAME_PREVIOUS ) )
        {
            this.fillFields( request );
            strNextSubForm = this.getPreviousSubForm( ).getName( );
        }
        else
        {
            strNextSubForm = this.getName( );
        }

        return strNextSubForm;
    }

    /**
     * @param request
     * @return
     */
    private boolean doSetCoordonnees( HttpServletRequest request )
    {
        boolean bValid = false;

        // set the fields' content
        this.fillFields( request );

        // check that the submitted data is valid
        bValid = this.validateFields( request );

        if ( bValid )
        {
            // create the ObjectFactory
            ObjectFactory factory = new ObjectFactory( );

            // retrieve the root element in session
            DemandeEtatCivil demandeEtatCivil;
            demandeEtatCivil = (DemandeEtatCivil) getParentForm( ).getFormDocument( request );

            if ( demandeEtatCivil == null )
            {
                demandeEtatCivil = factory.createDemandeEtatCivil( );
            }

            DemandeurType demandeur = demandeEtatCivil.getDemandeur( );

            if ( demandeur == null )
            {
                demandeur = factory.createDemandeurType( );
            }

            demandeur.getIndividu( );

            IndividuType individu;
            individu = demandeur.getIndividu( );

            if ( individu == null )
            {
                individu = factory.createIndividuType( );
            }

            NomsType noms = factory.createNomsType( );
            Field fieldNom = this.getFieldFromName( request, FIELD_NAME_NOM );
            noms.setNomDeFamille( fieldNom.getValue( ) );
            individu.setNoms( noms );

            Field fieldPrenom = this.getFieldFromName( request, FIELD_NAME_PRENOM );
            individu.setPrenoms( fieldPrenom.getValue( ) );

            Field fieldCivilite = this.getFieldFromName( request, "civilite" );
            String strCivilite = fieldCivilite.getValue( );

            if ( strCivilite.trim( ).equals( "Mr" ) )
            {
                individu.setGenre( GenreType.M );
            }
            else if ( strCivilite.trim( ).equals( "Mme" ) )
            {
                individu.setGenre( GenreType.MME );
            }

            AdresseType adresse = factory.createAdresseType( );

            Field fieldAdresse = this.getFieldFromName( request, "adresse" );
            adresse.setLigneAdr1( fieldAdresse.getValue( ) );

            Field fieldCodePostal = this.getFieldFromName( request, "codePostal" );
            adresse.setCodePostal( fieldCodePostal.getValue( ) );

            LieuType lieu = factory.createLieuType( );
            Field fieldVille = this.getFieldFromName( request, "ville" );
            lieu.setVille( fieldVille.getValue( ) );

            Field fieldPays = this.getFieldFromName( request, "pays" );

            if ( ( fieldPays.getValue( ) != null ) && ( !fieldPays.getValue( ).trim( ).equals( "" ) ) )
            {
                lieu.setPays( fieldPays.getValue( ) );
            }

            Field fieldProvince = this.getFieldFromName( request, "province" );

            if ( ( fieldProvince.getValue( ) != null ) && ( !fieldProvince.getValue( ).trim( ).equals( "" ) ) )
            {
                lieu.setProvince( fieldProvince.getValue( ) );
            }

            adresse.setLieu( lieu );
            individu.setAdresse( adresse );
            demandeur.setIndividu( individu );
            demandeEtatCivil.setDemandeur( demandeur );
            this.getParentForm( ).setFormDocument( request, demandeEtatCivil );
        }

        return bValid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.paris.lutece.plugins.formengine.web.SubForm#displaySummary(javax.servlet
     * .http.HttpServletRequest)
     */
    protected String displaySummary( HttpServletRequest request )
    {
        return StringUtils.EMPTY;
    }

    /**
     * @see fr.paris.lutece.plugins.formengine.web.SubForm#getXslFormElementsFileName()
     */
    protected String getXslFormElementsFileName( )
    {
        return AppPropertiesService.getProperty( Constants.SHARED_PROPERTY_PREFIX + PROPERTY_FRAGMENT_XSL_FILE_FORM );
    }

    protected String displayHeader( HttpServletRequest request )
    {
        HtmlTemplate template = AppTemplateService.getTemplate( AppPropertiesService.getProperty( getParentForm( )
                .getName( ) + PROPERTY_FRAGMENT_TEMPLATE_HEADER ) );

        template.substitute( Constants.BOOKMARK_FORM_TITLE, this.getParentForm( ).getTitle( ) );
        template.substitute( Constants.BOOKMARK_SUBFORM_TITLE, this.getTitle( ) );

        HtmlTemplate templateMenu = AppTemplateService.getTemplate( AppPropertiesService.getProperty( getParentForm( )
                .getName( ) + PROPERTY_FRAGMENT_TEMPLATE_MENU_HEADER ) );
        template.substitute( Constants.BOOKMARK_MENU_HEADER, templateMenu.getHtml( ) );

        return template.getHtml( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String displayForm( final HttpServletRequest request )
    {
        LuteceUser luteceUser = SecurityService.getInstance( ).getRegisteredUser( request );
        if ( luteceUser != null )
        {
            Field field = this.getFieldFromName( request, FIELD_NAME_NOM );
            if ( StringUtils.isEmpty( field.getValue( ) ) )
            {
                field.setValue( luteceUser.getUserInfo( AppPropertiesService
                        .getProperty( Constants.PROPERTY_LUTECE_USER_LAST_NAME ) ) );
            }
            field = this.getFieldFromName( request, FIELD_NAME_PRENOM );
            if ( StringUtils.isEmpty( field.getValue( ) ) )
            {
                field.setValue( luteceUser.getUserInfo( AppPropertiesService
                        .getProperty( Constants.PROPERTY_LUTECE_USER_FIRST_NAME ) ) );
            }
        }
        return super.displayForm( request );
    }
}
