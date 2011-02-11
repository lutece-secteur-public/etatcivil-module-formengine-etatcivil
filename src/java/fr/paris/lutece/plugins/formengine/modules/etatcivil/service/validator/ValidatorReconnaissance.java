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
package fr.paris.lutece.plugins.formengine.modules.etatcivil.service.validator;

import fr.paris.lutece.plugins.formengine.business.jaxb.formdefinition.Field;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.web.Constants;
import fr.paris.lutece.plugins.formengine.service.validator.SubFormValidator;
import fr.paris.lutece.plugins.formengine.web.FormErrorsList;
import fr.paris.lutece.plugins.formengine.web.SubForm;

import javax.servlet.http.HttpServletRequest;


/**
 * @author lutecer
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ValidatorReconnaissance extends SubFormValidator
{
    private static final String FIELD_NAME_MOMENT_REC = "momentReconnaissance";
    private static final String FIELD_NAME_NOM_PERE = "nomPere";
    private static final String FIELD_NAME_PRENOM_PERE = "prenomPere";
    private static final String FIELD_NAME_NOM_MERE = "nomMere";
    private static final String FIELD_NAME_PRENOM_MERE = "prenomMere";
    private static final String FIELD_NAME_NOM = "nom";
    private static final String FIELD_NAME_PRENOM = "prenom";
    private static final String PROPERTY_FRAGMENT_ERROR_MESSAGE_ONE_OF_TWO = ".error.reconnaissance.identite.motherOrFatherRequired";
    private static final String PROPERTY_FRAGMENT_ERROR_MESSAGE_CHILD_REQUIRED = ".error.reconnaissance.identite.childRequired";

    /**
     *
     */
    public ValidatorReconnaissance(  )
    {
    }

    /**
     * @see fr.paris.lutece.plugins.formengine.service.validator.SubFormValidator#validate(fr.paris.lutece.plugins.formengine.web.SubForm, javax.servlet.http.HttpServletRequest, fr.paris.lutece.plugins.formengine.web.FormErrorsList)
     */
    public boolean validate( SubForm subform, HttpServletRequest request, FormErrorsList errors )
    {
        boolean bValid = true;

        Field fieldMomentRec = subform.getPreviousSubForm(  ).getFieldFromName( request, FIELD_NAME_MOMENT_REC );

        Field fieldNomPere = subform.getFieldFromName( request, FIELD_NAME_NOM_PERE );
        Field fieldPrenomPere = subform.getFieldFromName( request, FIELD_NAME_PRENOM_PERE );
        Field fieldNomMere = subform.getFieldFromName( request, FIELD_NAME_NOM_MERE );
        Field fieldPrenomMere = subform.getFieldFromName( request, FIELD_NAME_PRENOM_MERE );

        Field fieldNom = subform.getFieldFromName( request, FIELD_NAME_NOM );
        Field fieldPrenom = subform.getFieldFromName( request, FIELD_NAME_PRENOM );

        boolean bNomPereEmpty = false;

        if ( ( fieldNomPere == null ) || ( fieldNomPere.getValue(  ) == null ) ||
                ( fieldNomPere.getValue(  ).trim(  ).equals( "" ) ) )
        {
            bNomPereEmpty = true;
        }

        boolean bNomMereEmpty = false;

        if ( ( fieldNomMere == null ) || ( fieldNomMere.getValue(  ) == null ) ||
                ( fieldNomMere.getValue(  ).trim(  ).equals( "" ) ) )
        {
            bNomMereEmpty = true;
        }

        boolean bPrenomPereEmpty = false;

        if ( ( fieldPrenomPere == null ) || ( fieldPrenomPere.getValue(  ) == null ) ||
                ( fieldPrenomPere.getValue(  ).trim(  ).equals( "" ) ) )
        {
            bPrenomPereEmpty = true;
        }

        boolean bPrenomMereEmpty = false;

        if ( ( fieldPrenomMere == null ) || ( fieldPrenomMere.getValue(  ) == null ) ||
                ( fieldPrenomMere.getValue(  ).trim(  ).equals( "" ) ) )
        {
            bPrenomMereEmpty = true;
        }

        if ( ( bNomPereEmpty || bPrenomPereEmpty ) && ( bNomMereEmpty || bPrenomMereEmpty ) )
        {
            bValid = false;

            String strRootPropFragment = Constants.SHARED_PROPERTY_PREFIX;

            if ( this.getErrorMessage(  ) != null )
            {
                errors.addErrorMessage( this.getErrorMessage(  ) );
            }
            else
            {
                String strProperty = strRootPropFragment + PROPERTY_FRAGMENT_ERROR_MESSAGE_ONE_OF_TWO;
                errors.addError( strProperty );
            }
        }

        if ( ( fieldMomentRec != null ) && ( fieldMomentRec.getValue(  ) != null ) &&
                ( fieldMomentRec.getValue(  ).equals( "1" ) ) )
        {
            boolean bNomFilsEmpty = false;

            if ( ( fieldNom == null ) || ( fieldNom.getValue(  ) == null ) ||
                    ( fieldNom.getValue(  ).trim(  ).equals( "" ) ) )
            {
                bNomFilsEmpty = true;
            }

            boolean bPrenomFilsEmpty = false;

            if ( ( fieldPrenom == null ) || ( fieldPrenom.getValue(  ) == null ) ||
                    ( fieldPrenom.getValue(  ).trim(  ).equals( "" ) ) )
            {
                bPrenomFilsEmpty = true;
            }

            if ( bNomFilsEmpty || bPrenomFilsEmpty )
            {
                bValid = false;

                String strRootPropFragment = Constants.SHARED_PROPERTY_PREFIX;

                if ( this.getErrorMessage(  ) != null )
                {
                    errors.addErrorMessage( this.getErrorMessage(  ) );
                }
                else
                {
                    String strProperty = strRootPropFragment + PROPERTY_FRAGMENT_ERROR_MESSAGE_CHILD_REQUIRED;
                    errors.addError( strProperty );
                }
            }
        }

        return bValid;
    }
}
