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

import fr.paris.lutece.plugins.formengine.web.Form;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;


public class EtatCivilForm extends Form
{
    private static final String PROPERTY_FRAGMENT_TRANSACTION_CODE = ".transaction.code";
    private static final String PROPERTY_FRAGMENT_TRANSACTION_NAME = ".transaction.name";
    private static final String PROPERTY_FRAGMENT_INSTANCE_NAME = ".instance.name";
    private static final String PROPERTY_FRAGMENT_XSL_DIRECTORY = ".xsl.directory";
    private static final String PROPERTY_FRAGMENT_JAXB_GENERATED_PACKAGENAME = ".jaxb.packagename";

    /**
     * Constructor
     */
    public EtatCivilForm( String strFormName )
    {
        super( strFormName );
    }

    /**
     * Defines the home package name of all classes generated by JAXB
     *
     * @return A package name
     */
    protected String getGeneratedPackageName(  )
    {
        return AppPropertiesService.getProperty( Constants.SHARED_PROPERTY_PREFIX +
            PROPERTY_FRAGMENT_JAXB_GENERATED_PACKAGENAME );
    }

    /**
     * Defines the transaction code.
     * @return The transaction code
     */
    public String getTransactionCode(  )
    {
        return AppPropertiesService.getProperty( this.getName(  ) + PROPERTY_FRAGMENT_TRANSACTION_CODE );
    }

    /**
     * Defines the transaction name.
     * @return The transaction name
     */
    public String getTransactionName(  )
    {
        return AppPropertiesService.getProperty( this.getName(  ) + PROPERTY_FRAGMENT_TRANSACTION_NAME );
    }

    /**
     * Defines the instance name.
     * @return The instance name
     */
    public String getInstanceName(  )
    {
        return AppPropertiesService.getProperty( this.getName(  ) + PROPERTY_FRAGMENT_INSTANCE_NAME );
    }

    /**
     * Defines the absolute path of the directory where to save the generated xml file
     * @return The absolute path
     */
    public String getXslDirectoryPath(  )
    {
        return AppPathService.getPath( Constants.SHARED_PROPERTY_PREFIX + PROPERTY_FRAGMENT_XSL_DIRECTORY );
    }
}