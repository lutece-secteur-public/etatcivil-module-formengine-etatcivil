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

import fr.paris.lutece.plugins.formengine.business.jaxb.formdefinition.Choice;
import fr.paris.lutece.plugins.formengine.business.jaxb.formdefinition.Field;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.DateEvenementType;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.DemandeEtatCivil;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.EvenementType;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.LieuType;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.NatureEvenementType;
import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.ObjectFactory;
import fr.paris.lutece.plugins.formengine.web.SubForm;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.sql.Date;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


/**
 * Subform to state the place a date of birth
 */
public class LieuDateActeSubForm extends SubForm
{
    private static final String PROPERTY_FRAGMENT_TEMPLATE_MENU_HEADER = ".template.header.menu";
    private static final String PROPERTY_FRAGMENT_TEMPLATE_HEADER = ".template.header.lieu_date";
    private static final String PROPERTY_FRAGMENT_XSL_FILE_FORM = ".xsl.file.form.lieuDate";
    private static final String PROPERTY_FRAGMENT_NATURE_EVENEMENT = ".evenement.nature";
    private static final String PROPERTY_FRAGMENT_LIEU_VILLE_UNKNOWN = ".lieu.ville.unknown";
    private static final String PARAMETER_DATE_PERIOD_SEPARATOR = " - ";
    private static final String SESSION_PARAMETER_IS_DATE_LIST_INITIALIZED = "IS_INIT_DATE_LIST";
    private static final String FIELD_NAME_DATE_ACTE_DAY = "dateActeJour";
    private static final String FIELD_NAME_DATE_ACTE_MONTH = "dateActeMois";
    private static final String FIELD_NAME_DATE_ACTE_YEAR = "dateActeAnnee";
    private static final String FIELD_NAME_PERIODS_ACTE = "fourchetteDateActe";
    private static final String FIELD_NAME_PLACE_ACTE = "mairieActe";

    /**
     *
     */
    public LieuDateActeSubForm(  )
    {
        super(  );
    }

    /**
     * Actions to perform : - if no action or unknown action, stay on this
     * subform - else go to next subform :
     * @param strActionName the name of the action to perform
     * @param request the request handled by this action
     * @return the string that represent the next subForm
     */
    public String doAction( String strActionName, HttpServletRequest request )
    {
        String strNextSubForm = "";

        if ( strActionName == null )
        {
            strNextSubForm = this.getName(  );
        }
        else if ( strActionName.equals( Constants.ACTION_NAME_NEXT ) )
        {
            boolean bValid = doSetPlaceAndDate( request );

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
        else
        {
            strNextSubForm = this.getName(  );
        }

        return strNextSubForm;
    }

    /**
     * This method is used to get form values
     * @param request used to get form fields values
     * @return true if form fields are valid
     */
    private boolean doSetPlaceAndDate( HttpServletRequest request )
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

            EvenementType evenement = demandeEtatCivil.getEvenement(  );

            if ( evenement == null )
            {
                evenement = factory.createEvenementType(  );
            }

            DateEvenementType date = factory.createDateEvenementType(  );

            Field dateFieldDay = this.getFieldFromName( request, FIELD_NAME_DATE_ACTE_DAY );
            Field dateFieldMonth = this.getFieldFromName( request, FIELD_NAME_DATE_ACTE_MONTH );
            Field dateFieldYear = this.getFieldFromName( request, FIELD_NAME_DATE_ACTE_YEAR );
            Field dateFourchetteField = this.getFieldFromName( request, FIELD_NAME_PERIODS_ACTE );

            if ( ( dateFieldDay != null ) && ( dateFieldDay.getValue(  ) != null ) &&
                    ( !dateFieldDay.getValue(  ).trim(  ).equals( "" ) ) && ( dateFieldMonth != null ) &&
                    ( dateFieldMonth.getValue(  ) != null ) && ( !dateFieldMonth.getValue(  ).trim(  ).equals( "" ) ) &&
                    ( dateFieldYear != null ) && ( dateFieldYear.getValue(  ) != null ) &&
                    ( !dateFieldYear.getValue(  ).trim(  ).equals( "" ) ) )
            {
                int nDateFieldValueYear = Integer.valueOf( dateFieldYear.getValue(  ) );
                int nDateFieldValueMonth = Integer.valueOf( dateFieldMonth.getValue(  ) );
                int nDateFieldValueDay = Integer.valueOf( dateFieldDay.getValue(  ) );
                Calendar dateField = new GregorianCalendar(  );
                dateField.set( nDateFieldValueYear, ( nDateFieldValueMonth + Calendar.JANUARY ) - 1, nDateFieldValueDay );

                Date sqlDate = new Date( dateField.getTime(  ).getTime(  ) );
                date.setDateDebut( sqlDate );

                dateFourchetteField.setValue( "" );
            }
            else if ( ( ( dateFieldYear.getValue(  ) != null ) && ( !dateFieldYear.getValue(  ).trim(  ).equals( "" ) ) ) &&
                    ( ( null == dateFieldDay.getValue(  ) ) || ( dateFieldDay.getValue(  ).trim(  ).equals( "" ) ) ) &&
                    ( ( null == dateFieldMonth.getValue(  ) ) || ( dateFieldMonth.getValue(  ).trim(  ).equals( "" ) ) ) )
            {
                int nDateFieldValueYear = Integer.valueOf( dateFieldYear.getValue(  ) );
                Calendar startDate = new GregorianCalendar(  );
                startDate.set( Calendar.MONTH, Calendar.JANUARY );
                startDate.set( Calendar.YEAR, nDateFieldValueYear );
                startDate.set( Calendar.DAY_OF_MONTH, startDate.getMinimum( Calendar.DAY_OF_MONTH ) );

                Calendar endDate = new GregorianCalendar(  );
                endDate.set( Calendar.MONTH, Calendar.DECEMBER );
                endDate.set( Calendar.YEAR, nDateFieldValueYear );
                endDate.set( Calendar.DAY_OF_MONTH, endDate.getActualMaximum( Calendar.DAY_OF_MONTH ) );

                Date sqlDateDebut = new Date( startDate.getTimeInMillis(  ) );
                Date sqlDateFin = new Date( endDate.getTimeInMillis(  ) );

                date.setDateDebut( sqlDateDebut );
                date.setDateFin( sqlDateFin );

                dateFourchetteField.setValue( "" );
            }
            else if ( ( dateFourchetteField != null ) && ( dateFourchetteField.getValue(  ) != null ) &&
                    ( !dateFourchetteField.getValue(  ).trim(  ).equals( "" ) ) )
            {
                String strDatePeriod = dateFourchetteField.getValue(  );

                String[] dates = strDatePeriod.split( PARAMETER_DATE_PERIOD_SEPARATOR );
                String strStartDate = dates[0].trim(  );
                String strEndingDate = dates[1].trim(  );
                int nStartYear = Integer.parseInt( strStartDate );
                int nEndingYear = Integer.parseInt( strEndingDate );

                Calendar calendar = new GregorianCalendar(  );
                calendar.set( Calendar.MONTH, Calendar.JANUARY );
                calendar.set( Calendar.YEAR, nStartYear );
                calendar.set( Calendar.DAY_OF_MONTH, calendar.getMinimum( Calendar.DAY_OF_MONTH ) );

                Date sqlStartDate = new Date( calendar.getTimeInMillis(  ) );

                date.setDateDebut( sqlStartDate );
                calendar.set( Calendar.YEAR, nEndingYear );
                calendar.set( Calendar.MONTH, Calendar.DECEMBER );
                calendar.set( Calendar.DATE, calendar.getActualMaximum( Calendar.DAY_OF_MONTH ) );

                Date sqlEndingDate = new Date( calendar.getTimeInMillis(  ) );

                date.setDateFin( sqlEndingDate );

                dateFieldDay.setValue( "" );
                dateFieldMonth.setValue( "" );
                dateFieldYear.setValue( "" );
            }

            evenement.setDateEvenement( date );

            LieuType lieu = factory.createLieuType(  );

            Field lieuField = this.getFieldFromName( request, FIELD_NAME_PLACE_ACTE );

            // take care when the value of the unkwnown city is modified in the
            // properties
            // the xml of definition must be modified too (think also about the
            // xsl of validation).
            String strValueVilleUnknown = AppPropertiesService.getProperty( Constants.SHARED_PROPERTY_PREFIX +
                    PROPERTY_FRAGMENT_LIEU_VILLE_UNKNOWN );

            if ( lieuField.getValue(  ).equals( strValueVilleUnknown ) )
            {
                lieu.setVille( "" );
            }
            else
            {
                lieu.setVille( lieuField.getValue(  ) );
            }

            evenement.setLieuEvenement( lieu );

            // load nature evenement from properties
            String strNature = AppPropertiesService.getProperty( this.getParentForm(  ).getName(  ) +
                    PROPERTY_FRAGMENT_NATURE_EVENEMENT );

            if ( strNature != null )
            {
                evenement.setNatureEvenement( NatureEvenementType.fromValue( strNature ) );
            }

            demandeEtatCivil.setEvenement( evenement );
            this.getParentForm(  ).setFormDocument( request, demandeEtatCivil );
        }

        return bValid;
    }

    /**
     * @see fr.paris.lutece.plugins.formengine.web.SubForm#displaySummary(javax.servlet.http.HttpServletRequest)
     * @param request the used request
     * @return empty string
     */
    protected String displaySummary( HttpServletRequest request )
    {
        return "";
    }

    /**
     * Initializes the combo of periods and display the form area
     *
     * @see fr.paris.lutece.plugins.formengine.web.SubForm#displayForm(javax.servlet.http.HttpServletRequest)
     * @param request the used request to get fields values
     * @return the builded html of the form elements
     */
    protected String displayForm( HttpServletRequest request )
    {
        // display the form area
        Field comboPeriodsField = this.getFieldFromName( request, FIELD_NAME_PERIODS_ACTE );

        // append the generated list to keep the first item defined in xml file.
        // this forces us to add a control in session so that the list doesn't
        // keep growing when looping or coming back on this page
        if ( this.getSessionAttribute( request, SESSION_PARAMETER_IS_DATE_LIST_INITIALIZED ) == null )
        {
            List<Choice> dateList = comboPeriodsField.getChoiceList(  ).getChoice(  );
            Choice lastDatePeriod = null;
            int nHighestEndingYear = 0;

            for ( Choice dateEntry : dateList )
            {
                if ( ( null != dateEntry ) && !dateEntry.getValue(  ).equals( "" ) )
                {
                    String strDatePeriod = dateEntry.getValue(  );
                    String[] dates = strDatePeriod.split( PARAMETER_DATE_PERIOD_SEPARATOR );
                    String strEndingDate = dates[1].trim(  );
                    int nCurrentEndingYear = Integer.parseInt( strEndingDate );

                    if ( nCurrentEndingYear > nHighestEndingYear )
                    {
                        nHighestEndingYear = nCurrentEndingYear;
                        lastDatePeriod = dateEntry;
                    }
                }
            }

            if ( lastDatePeriod != null )
            {
                String strLastDatePeriod = lastDatePeriod.getValue(  );
                String[] dates = strLastDatePeriod.split( PARAMETER_DATE_PERIOD_SEPARATOR );
                String strStartingDate = dates[0].trim(  );

                Calendar currentDate = new GregorianCalendar(  );
                String strCurrentYear = String.valueOf( currentDate.get( Calendar.YEAR ) );

                strLastDatePeriod = strStartingDate + PARAMETER_DATE_PERIOD_SEPARATOR + strCurrentYear;
                lastDatePeriod.setValue( strLastDatePeriod );
                lastDatePeriod.setLabel( strLastDatePeriod );
            }

            this.setSessionAttribute( request, SESSION_PARAMETER_IS_DATE_LIST_INITIALIZED, Boolean.TRUE );
        }

        // build the form page
        String strForm = "";

        strForm = this.buildHtmlForm( this.getFormElements( request ), null );

        return strForm;
    }

    /**
     * @see fr.paris.lutece.plugins.formengine.web.SubForm#getXslFormElementsFileName()
     * @return form element xsl file name
     */
    protected String getXslFormElementsFileName(  )
    {
        return AppPropertiesService.getProperty( Constants.SHARED_PROPERTY_PREFIX + PROPERTY_FRAGMENT_XSL_FILE_FORM );
    }

    /**
     * this method is used to display form header
     * @param request the used request
     * @return the html content of header template
     */
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
