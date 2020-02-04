/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 **/
package it.people.core.persistence.converters.sql;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.accesslayer.conversions.ConversionException;
import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;

import it.people.vsl.SerializablePipelineData;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         17/ott/2012 12:14:35
 */
public class SerializablePipelineDataConverter implements FieldConversion {

    private static final long serialVersionUID = 6954635933283520738L;

    private static final Logger logger = LogManager
	    .getLogger(SerializablePipelineDataConverter.class);

    /**
	 * 
	 */
    public SerializablePipelineDataConverter() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.ojb.broker.accesslayer.conversions.FieldConversion#javaToSql
     * (java.lang.Object)
     */
    public Object javaToSql(Object source) throws ConversionException {

	if (source != null) {
	    return this.getEncodedObject(source);
	} else {
	    return source;
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.ojb.broker.accesslayer.conversions.FieldConversion#sqlToJava
     * (java.lang.Object)
     */
    public Object sqlToJava(Object source) throws ConversionException {
	if (source instanceof java.lang.String) {
	    return this.getDecodedObject(source);
	} else {
	    if (source == null)
		return source;
	    else
		return source.toString();
	}
    }

    /**
     * @param source
     * @return
     */
    private String getEncodedObject(Object source) {

	String result = null;

	if (source instanceof SerializablePipelineData) {
	    try {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			byteArrayOutputStream);
		objectOutputStream.writeObject(source);
		objectOutputStream.flush();
		byteArrayOutputStream.flush();
		byte[] serializablePipelineDataEncodedBytes = org.apache.commons.codec.binary.Base64
			.encodeBase64(byteArrayOutputStream.toByteArray());
		byteArrayOutputStream.close();
		byteArrayOutputStream = new ByteArrayOutputStream();
		byteArrayOutputStream
			.write(serializablePipelineDataEncodedBytes);
		byteArrayOutputStream.flush();
		result = byteArrayOutputStream.toString();
		byteArrayOutputStream.close();
	    } catch (IOException e) {
		logger.error("Unable to get encoded object.", e);
	    }
	}

	return result;

    }

    /**
     * @param source
     * @return
     */
    private SerializablePipelineData getDecodedObject(Object source) {

	SerializablePipelineData result = null;

	try {
	    byte[] serializablePipelineDataDecodedBytes = org.apache.commons.codec.binary.Base64
		    .decodeBase64(String.valueOf(source).getBytes());
	    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
		    serializablePipelineDataDecodedBytes);
	    ObjectInputStream objectInputStream = new ObjectInputStream(
		    byteArrayInputStream);
	    result = (SerializablePipelineData) objectInputStream.readObject();
	    objectInputStream.close();
	    byteArrayInputStream.close();
	} catch (IOException e) {
	    logger.error("Unable to get decoded object.", e);
	} catch (ClassNotFoundException e) {
	    logger.error("Unable to get decoded object.", e);
	}

	return result;

    }

}
