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
package fr.paris.lutece.plugins.formengine.modules.etatcivil.service.output;

import fr.paris.lutece.plugins.formengine.modules.etatcivil.business.jaxb.DemandeEtatCivil;
import fr.paris.lutece.plugins.formengine.service.output.FileOutputProcessor;
import fr.paris.lutece.plugins.formengine.web.Form;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.io.FileOutputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


/**
 * @author lutecer
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EtatCivilFileOutputProcessor extends FileOutputProcessor
{
    private static final String PROPERTY_FRAGMENT_XML_DIRECTORY = ".xml.directory";
    private Marshaller _marshaller;

    /**
     *
     */
    public EtatCivilFileOutputProcessor(  )
    {
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.formengine.service.output.OutputProcessor#init(fr.paris.lutece.plugins.formengine.web.Form, java.lang.Object)
     */
    protected void init( Form form, Object transactionObject )
    {
        DemandeEtatCivil demande = (DemandeEtatCivil) transactionObject;

        String strDirPath = AppPropertiesService.getProperty( form.getName(  ) + PROPERTY_FRAGMENT_XML_DIRECTORY );

        String strId = demande.getIdentifiant(  );
        setXmlFilePath( strDirPath + "/" + strId + ".xml" );

        try
        {
            _marshaller = form.getXmlMarshaller(  );
            _marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
        }
        catch ( JAXBException e )
        {
            AppLogService.error( e.getMessage(  ), e );
        }
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.formengine.service.output.FileOutputProcessor#write(java.io.FileOutputStream, java.lang.Object)
     */
    protected void write( FileOutputStream out, Object transactionObject )
    {
        try
        {
            _marshaller.marshal( (DemandeEtatCivil) transactionObject, out );
        }
        catch ( JAXBException e )
        {
            AppLogService.error( e.getMessage(  ), e );
        }
    }
}
