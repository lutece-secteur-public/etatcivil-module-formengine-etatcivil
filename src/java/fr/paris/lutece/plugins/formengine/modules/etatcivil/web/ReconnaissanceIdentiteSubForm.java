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
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.DemandeEtatCivil;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.EvenementType;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.IndividuType;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.NatureDocumentType;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.NomsType;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.ObjectFactory;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.TypeInteresseType;
import fr.paris.lutece.plugins.formengine.web.SubForm;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


/**
 * @author lutecer
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ReconnaissanceIdentiteSubForm extends SubForm
{
    private static final String PROPERTY_FRAGMENT_XSL_FILE_FORM = ".xsl.file.form.reconnaissance.identite";
    private static final String FIELD_NAME_MOMENT_REC = "momentReconnaissance";
    private static final String PARAMETER_SHOW_CHILD_FIELDS = "showChildFields";
    private static final String PROPERTY_FRAGMENT_TEMPLATE_MENU_HEADER = ".template.header.menu";
    private static final String PROPERTY_FRAGMENT_TEMPLATE_HEADER_IDENTITE = ".template.header.identite";

    /**
     *
     */
    public ReconnaissanceIdentiteSubForm( )
    {
        super( );
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.paris.lutece.plugins.formengine.web.SubForm#getXslFormElementsFileName
     * ()
     */
    protected String getXslFormElementsFileName( )
    {
        return AppPropertiesService.getProperty( Constants.SHARED_PROPERTY_PREFIX + PROPERTY_FRAGMENT_XSL_FILE_FORM );
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
            boolean bValid = doSetIdentite( request );

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
    private boolean doSetIdentite( HttpServletRequest request )
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

            EvenementType evenement = demandeEtatCivil.getEvenement( );

            if ( evenement == null )
            {
                evenement = factory.createEvenementType( );
            }

            IndividuType interesse;
            interesse = evenement.getInteresse( );

            if ( interesse == null )
            {
                interesse = factory.createIndividuType( );
            }

            IndividuType pere;
            pere = interesse.getPere( );

            if ( pere == null )
            {
                pere = factory.createIndividuType( );
            }

            Field fieldNomPere = this.getFieldFromName( request, "nomPere" );

            if ( ( fieldNomPere.getValue( ) != null ) && ( !fieldNomPere.getValue( ).trim( ).equals( "" ) ) )
            {
                NomsType noms = factory.createNomsType( );
                noms.setNomDeFamille( fieldNomPere.getValue( ) );
                pere.setNoms( noms );
            }
            else
            {
                pere.setNoms( null );
            }

            Field fieldPrenomPere = this.getFieldFromName( request, "prenomPere" );

            if ( ( fieldPrenomPere.getValue( ) != null ) && ( !fieldPrenomPere.getValue( ).trim( ).equals( "" ) ) )
            {
                pere.setPrenoms( fieldPrenomPere.getValue( ) );

                if ( pere.getNoms( ) == null )
                {
                    NomsType nomsVide = factory.createNomsType( );
                    nomsVide.setNomDeFamille( "" );
                    pere.setNoms( nomsVide );
                }
            }
            else
            {
                pere.setPrenoms( null );
            }

            if ( ( pere.getNoms( ) != null ) )
            {
                interesse.setPere( pere );
            }

            IndividuType mere;
            mere = interesse.getMere( );

            if ( mere == null )
            {
                mere = factory.createIndividuType( );
            }

            Field fieldNomMere = this.getFieldFromName( request, "nomMere" );

            if ( ( fieldNomMere.getValue( ) != null ) && ( !fieldNomMere.getValue( ).trim( ).equals( "" ) ) )
            {
                NomsType noms = factory.createNomsType( );
                noms.setNomDeFamille( fieldNomMere.getValue( ) );
                mere.setNoms( noms );
            }
            else
            {
                mere.setNoms( null );
            }

            Field fieldPrenomMere = this.getFieldFromName( request, "prenomMere" );

            if ( ( fieldPrenomMere.getValue( ) != null ) && ( !fieldPrenomMere.getValue( ).trim( ).equals( "" ) ) )
            {
                mere.setPrenoms( fieldPrenomMere.getValue( ) );

                if ( mere.getNoms( ) == null )
                {
                    NomsType nomsVide = factory.createNomsType( );
                    nomsVide.setNomDeFamille( "" );
                    mere.setNoms( nomsVide );
                }
            }
            else
            {
                mere.setPrenoms( null );
            }

            if ( ( mere.getNoms( ) != null ) )
            {
                interesse.setMere( mere );
            }

            NomsType noms = factory.createNomsType( );
            Field fieldNom = this.getFieldFromName( request, "nom" );
            String strNomValue = fieldNom.getValue( );

            Field fieldPrenom = this.getFieldFromName( request, "prenom" );
            String strPrenomValue = fieldPrenom.getValue( );

            //if the name of the chield is not known we take the name of the author of the "reconnaisasnce".
            if ( strNomValue.equals( "" ) )
            {
                if ( ( fieldPrenomPere.getValue( ) != null ) && ( !fieldPrenomPere.getValue( ).trim( ).equals( "" ) ) )
                {
                    strNomValue = pere.getNoms( ).getNomDeFamille( );
                    strPrenomValue = pere.getPrenoms( );
                }
                else if ( ( fieldNomMere.getValue( ) != null ) && ( !fieldNomMere.getValue( ).trim( ).equals( "" ) ) )
                {
                    strNomValue = mere.getNoms( ).getNomDeFamille( );
                    strPrenomValue = mere.getPrenoms( );
                }

                evenement.setTypeInteresse( TypeInteresseType.AUTEUR );
            }
            else
            {
                evenement.setTypeInteresse( TypeInteresseType.RECONNU );
            }

            noms.setNomDeFamille( strNomValue );
            interesse.setNoms( noms );

            interesse.setPrenoms( strPrenomValue );

            evenement.setInteresse( interesse );

            demandeEtatCivil.setEvenement( evenement );

            Field fieldQuantity = this.getFieldFromName( request, "nombreActes" );

            int nQuantity = Integer.parseInt( fieldQuantity.getValue( ) );

            demandeEtatCivil.setNbExemplaire( nQuantity );

            demandeEtatCivil.setNatureDocument( NatureDocumentType.CPI );

            this.getParentForm( ).setFormDocument( request, demandeEtatCivil );
        }

        return bValid;
    }

    /**
    *
    */
    protected String displayForm( HttpServletRequest request )
    {
        String strForm = StringUtils.EMPTY;

        Map<String, Object> params = new HashMap<String, Object>( );

        Field fieldMomentReconnaissance = this.getPreviousSubForm( ).getFieldFromName( request, FIELD_NAME_MOMENT_REC );

        if ( ( fieldMomentReconnaissance != null ) && ( fieldMomentReconnaissance.getValue( ) != null )
                && ( fieldMomentReconnaissance.getValue( ).equals( "1" ) ) )
        {
            params.put( PARAMETER_SHOW_CHILD_FIELDS, "1" );
        }

        LuteceUser luteceUser = SecurityService.getInstance( ).getRegisteredUser( request );
        if ( luteceUser != null )
        {
            Field field = this.getFieldFromName( request, "nom" );
            if ( field != null && StringUtils.isEmpty( field.getValue( ) ) )
            {
                field.setValue( luteceUser.getUserInfo( AppPropertiesService
                        .getProperty( Constants.PROPERTY_LUTECE_USER_LAST_NAME ) ) );
            }
            field = this.getFieldFromName( request, "prenom" );
            if ( field != null && StringUtils.isEmpty( field.getValue( ) ) )
            {
                field.setValue( luteceUser.getUserInfo( AppPropertiesService
                        .getProperty( Constants.PROPERTY_LUTECE_USER_FIRST_NAME ) ) );
            }
        }

        strForm = this.buildHtmlForm( getFormElements( request ), params );

        return strForm;
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

    protected String displayHeader( HttpServletRequest request )
    {
        HtmlTemplate templateMenu = AppTemplateService.getTemplate( AppPropertiesService.getProperty( getParentForm( )
                .getName( ) + PROPERTY_FRAGMENT_TEMPLATE_MENU_HEADER ) );

        HtmlTemplate template = AppTemplateService.getTemplate( AppPropertiesService.getProperty( getParentForm( )
                .getName( ) + PROPERTY_FRAGMENT_TEMPLATE_HEADER_IDENTITE ) );
        template.substitute( Constants.BOOKMARK_FORM_TITLE, this.getParentForm( ).getTitle( ) );
        template.substitute( Constants.BOOKMARK_SUBFORM_TITLE, this.getTitle( ) );
        template.substitute( Constants.BOOKMARK_MENU_HEADER, templateMenu.getHtml( ) );

        return template.getHtml( );
    }
}
