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
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;


/**
 * Validator for the first subform of the etat-civil forms. Checks that one of
 * the date fields at least is filled.
 */
public class ValidatorDateActeRequired extends SubFormValidator
{
    private static final String FIELD_NAME_DATE_ACTE_DAY = "dateActeJour";
    private static final String FIELD_NAME_DATE_ACTE_MONTH = "dateActeMois";
    private static final String FIELD_NAME_DATE_ACTE_YEAR = "dateActeAnnee";
    private static final String FIELD_NAME_FOURCHETTE_DATE_ACTE = "fourchetteDateActe";
    private static final String PROPERTY_FRAGMENT_ERROR_MESSAGE = ".error.date.acte.oneOfTwoRequired";
    private static final String PROPERTY_FRAGMENT_START_YEAR_ERROR_MESSAGE = ".error.date.acte.isBeforeStartYear";
    private static final String PROPERTY_FRAGMENT_CURRENT_DATE_ERROR_MESSAGE = ".error.date.acte.isAfterCurrentDate";
    private static final String PROPERTY_ARCHIVE_DATE = ".date.period.referenceYear";
    private static final String DATE_FORMAT_SEPARATOR = "/";
    private static final String DATE_FORMAT_YEAR = "yyyy";
    private static final String DATE_FORMAT_MONTH = "MM";
    private static final String DATE_FORMAT_DAY = "dd";

    /**
     * Constructor
     */
    public ValidatorDateActeRequired(  )
    {
    }

    /**
     * @see fr.paris.lutece.plugins.formengine.service.validator.SubFormValidator#validate(fr.paris.lutece.plugins.formengine.web.SubForm,
     *      javax.servlet.http.HttpServletRequest,
     *      fr.paris.lutece.plugins.formengine.web.FormErrorsList)
     * @param subform
     *            the form to test
     * @param request
     *            the request used to get form fields values
     * @param errors
     *            errors handler
     * @return true if the form fields correctly typed
     */
    public boolean validate( SubForm subform, HttpServletRequest request, FormErrorsList errors )
    {
        boolean bValid = true;

        Field fieldDateActeDay = subform.getFieldFromName( request, FIELD_NAME_DATE_ACTE_DAY );
        Field fieldDateActeMonth = subform.getFieldFromName( request, FIELD_NAME_DATE_ACTE_MONTH );
        Field fieldDateActeYear = subform.getFieldFromName( request, FIELD_NAME_DATE_ACTE_YEAR );
        Field fieldFourchetteDateActe = subform.getFieldFromName( request, FIELD_NAME_FOURCHETTE_DATE_ACTE );

        String strDateValueDay = fieldDateActeDay.getValue(  );
        String strDateValueMonth = fieldDateActeMonth.getValue(  );
        String strDateValueYear = fieldDateActeYear.getValue(  );
        String strFourchetteValue = fieldFourchetteDateActe.getValue(  );

        String strRootPropFragment = Constants.SHARED_PROPERTY_PREFIX;

        // Test the fields mandatory
        MandatoryState mandatoryState = checkMandatory( strDateValueYear, strDateValueMonth, strDateValueDay,
                strFourchetteValue );

        if ( mandatoryState.equals( MandatoryState.FAILED ) )
        {
            bValid = false;

            if ( this.getErrorMessage(  ) != null )
            {
                errors.addErrorMessage( this.getErrorMessage(  ) );
            }
            else
            {
                String strProperty = strRootPropFragment + PROPERTY_FRAGMENT_ERROR_MESSAGE;
                errors.addError( strProperty );
            }
        }
        else if ( !mandatoryState.equals( MandatoryState.USE_FOURCH ) )
        {
            Calendar fieldCalendar = null;

            if ( mandatoryState.equals( MandatoryState.USE_WHOLE_DATE ) )
            {
                fieldCalendar = checkUseWholeDateFormat( strDateValueYear, strDateValueMonth, strDateValueDay );
            }
            else if ( mandatoryState.equals( MandatoryState.USE_YEAR ) )
            {
                fieldCalendar = checkUseYearFormat( strDateValueYear );
            }

            if ( null == fieldCalendar )
            {
                bValid = false;

                if ( this.getErrorMessage(  ) != null )
                {
                    errors.addErrorMessage( this.getErrorMessage(  ) );
                }
                else
                {
                    String strProperty = strRootPropFragment + PROPERTY_FRAGMENT_ERROR_MESSAGE;
                    errors.addError( strProperty );
                }
            }
            else
            {
                // Test the date boundaries respect
                // Error messages handle in method
                bValid = checkBoundaries( strRootPropFragment, fieldCalendar, errors );
            }
        }

        return bValid;
    }

    /**
     * This method check if needed parameters been suggested
     *
     * @param strDateValueYear
     *            The year string parameter value from field
     * @param strDateValueMonth
     *            The month string parameter value from field
     * @param strDateValueDay
     *            The day string parameter value from field
     * @param strFourchetteValue
     *            The fouchette string parameter value from field
     * @return true if all required field values presents
     */
    private MandatoryState checkMandatory( String strDateValueYear, String strDateValueMonth, String strDateValueDay,
        String strFourchetteValue )
    {
        // case of no year and no fourchette or no complete date
        if ( ( isEmpty( strDateValueYear ) && isEmpty( strFourchetteValue ) ) ||
                ( !isEmpty( strDateValueYear ) && ( isEmpty( strDateValueMonth ) ^ isEmpty( strDateValueDay ) ) ) ||
                ( isEmpty( strDateValueYear ) && isEmpty( strFourchetteValue ) &&
                ( !isEmpty( strDateValueMonth ) || !isEmpty( strDateValueDay ) ) ) )
        {
            return MandatoryState.FAILED;
        }

        // case of whole date
        if ( !isEmpty( strDateValueYear ) && !isEmpty( strDateValueMonth ) && !isEmpty( strDateValueDay ) )
        {
            return MandatoryState.USE_WHOLE_DATE;
        }

        // case of only year
        else if ( !isEmpty( strDateValueYear ) && isEmpty( strDateValueMonth ) && isEmpty( strDateValueDay ) )
        {
            return MandatoryState.USE_YEAR;
        }

        // last possible case use fouchette
        else
        {
            return MandatoryState.USE_FOURCH;
        }
    }

    /**
     * This method check if the fields values respect format patterns
     * (dd/MM/yyyy or yyyy)
     *
     * @param strDateValueYear
     *            The year string parameter value from field
     * @param strDateValueMonth
     *            The month string parameter value from field
     * @param strDateValueDay
     *            The day string parameter value from field
     * @return true if pattern respected
     */
    private Calendar checkUseWholeDateFormat( String strDateValueYear, String strDateValueMonth, String strDateValueDay )
    {
        Calendar fieldCalendar = getNewCalendar(  );
        fieldCalendar.setLenient( false );

        boolean bValid = true;

        // whole date
        SimpleDateFormat dateFormat = null;

        try
        {
            dateFormat = new SimpleDateFormat( DATE_FORMAT_DAY + DATE_FORMAT_SEPARATOR + DATE_FORMAT_MONTH +
                    DATE_FORMAT_SEPARATOR + DATE_FORMAT_YEAR );
            dateFormat.setLenient( false );
            dateFormat.parse( strDateValueDay.trim(  ) + DATE_FORMAT_SEPARATOR + strDateValueMonth.trim(  ) +
                DATE_FORMAT_SEPARATOR + strDateValueYear.trim(  ) );
            fieldCalendar.set( Calendar.YEAR, dateFormat.getCalendar(  ).get( Calendar.YEAR ) );
            fieldCalendar.set( Calendar.MONTH, dateFormat.getCalendar(  ).get( Calendar.MONTH ) );
            fieldCalendar.set( Calendar.DAY_OF_MONTH, dateFormat.getCalendar(  ).get( Calendar.DAY_OF_MONTH ) );

            if ( strDateValueYear.length(  ) != DATE_FORMAT_YEAR.length(  ) )
            {
                bValid = false;
            }
        }
        catch ( ParseException pe )
        {
            bValid = false;
        }

        return bValid ? fieldCalendar : null;
    }

    /**
     * This method check if the year field respect the aaaa pattern
     * @param strDateValueYear
     *            the year field value
     * @return field value if well formed, null in other cases
     */
    private Calendar checkUseYearFormat( String strDateValueYear )
    {
        Calendar fieldCalendar = getNewCalendar(  );
        fieldCalendar.setLenient( false );

        boolean bValid = true;

        // only year
        SimpleDateFormat df = null;

        try
        {
            df = new SimpleDateFormat( DATE_FORMAT_YEAR );
            df.setLenient( false );
            df.parse( strDateValueYear.trim(  ) );
            fieldCalendar.set( Calendar.YEAR, df.getCalendar(  ).get( Calendar.YEAR ) );
            fieldCalendar.set( Calendar.MONTH, Calendar.JANUARY );
            fieldCalendar.set( Calendar.DAY_OF_MONTH, fieldCalendar.getMinimum( Calendar.DAY_OF_MONTH ) );

            if ( strDateValueYear.length(  ) != DATE_FORMAT_YEAR.length(  ) )
            {
                bValid = false;
            }
        }
        catch ( ParseException pe )
        {
            bValid = false;
        }

        return bValid ? fieldCalendar : null;
    }

    /**
     * This method check if the entered dateField is after the porperties
     * reference year and before today
     *
     * @param strRootPropFragment
     *            root properties used to reach reference year and error
     *            messages
     * @param dateField
     *            the date to be tested
     * @param errors
     *            errors handler
     * @return true if dateField respect boundaries
     */
    private boolean checkBoundaries( String strRootPropFragment, Calendar dateField, FormErrorsList errors )
    {
        String strArchiveDate = AppPropertiesService.getProperty( strRootPropFragment + PROPERTY_ARCHIVE_DATE );
        Calendar gStartYear = getNewCalendar(  );
        gStartYear.set( Calendar.YEAR, Integer.valueOf( strArchiveDate ) );
        gStartYear.set( Calendar.MONTH, Calendar.JANUARY );
        gStartYear.set( Calendar.DAY_OF_MONTH, gStartYear.getMinimum( Calendar.DAY_OF_MONTH ) );

        if ( dateField.before( gStartYear ) )
        {
            if ( this.getErrorMessage(  ) != null )
            {
                errors.addError( this.getErrorMessage(  ) );
            }
            else
            {
                String strProperty = strRootPropFragment + PROPERTY_FRAGMENT_START_YEAR_ERROR_MESSAGE;
                errors.addError( strProperty );
            }

            return false;
        }

        Calendar currentCalendar = getNewCalendar(  );

        if ( dateField.after( currentCalendar ) )
        {
            if ( this.getErrorMessage(  ) != null )
            {
                errors.addError( this.getErrorMessage(  ) );
            }
            else
            {
                String strProperty = strRootPropFragment + PROPERTY_FRAGMENT_CURRENT_DATE_ERROR_MESSAGE;
                errors.addError( strProperty );
            }

            return false;
        }

        return true;
    }

    /**
     * @return zero initialized GregorianCalendar
     */
    private Calendar getNewCalendar(  )
    {
        Calendar calendar = new GregorianCalendar(  );
        calendar.set( Calendar.HOUR_OF_DAY, 0 );
        calendar.set( Calendar.MINUTE, 0 );
        calendar.set( Calendar.SECOND, 0 );
        calendar.set( Calendar.MILLISECOND, 0 );

        return calendar;
    }

    /**
     * @param strValue
     *            the string to test
     * @return true if strValue is null or empty
     */
    private boolean isEmpty( String strValue )
    {
        return ( strValue == null ) || ( strValue.trim(  ).equals( "" ) );
    }

    /**
     * Enumeration used to describe what form fields we use
     */
    enum MandatoryState
    {FAILED,
        USE_WHOLE_DATE,
        USE_YEAR,
        USE_FOURCH;
    }
}
