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
package it.people.console.oxm.betwixt;

import it.people.console.oxm.IExtendedMarshaller;
import it.people.console.oxm.IExtendedUnmarshaller;
import it.people.console.utils.Constants;

import java.beans.IntrospectionException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.betwixt.BindingConfiguration;
import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;

import org.apache.commons.betwixt.strategy.DecapitalizeNameMapper;
import org.apache.commons.betwixt.strategy.NamespacePrefixMapper;
import org.apache.commons.betwixt.strategy.PluralStemmer;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException; 
import org.springframework.xml.transform.StaxResult;
import org.springframework.xml.transform.StaxSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException; 

import java.lang.OutOfMemoryError;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 28/gen/2012 11:08:51
 *
 */
public class BetwixtMarshaller implements Marshaller, Unmarshaller, IExtendedMarshaller, IExtendedUnmarshaller {
	
	private boolean decapitalizeNameMapper = false;
	private boolean pluralStemmerRequired = false;
	private String pluralStemmerClass = "";
	private boolean nameSpaceAware = false;
	private String nameSpace = "";
	private String nameSpacePrefix = "";
	private boolean wrapCollectionsInElement = false;
	private boolean attributesForPrimitives = false;
	private boolean decapitalizeElements = false;
	
	private static final String UNMARSHALL_CLASS_EXCEPTION = "Unmarshall class is null or equal to Object.class.";

    private Class<?>[] classesToBeBound;
	
	private Class<?> unmarshallClass;

    public void setClassesToBeBound(Class<?>[] classesToBeBound) {
        this.classesToBeBound = classesToBeBound;
    }
	
	public void marshal(Object arg0, Result arg1) throws XmlMappingException, IOException {

		StringWriter stringWriter = new StringWriter();
		BeanWriter beanWriter = new BeanWriter(stringWriter);
		beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
		beanWriter.getXMLIntrospector().getConfiguration().setWrapCollectionsInElement(false);
		beanWriter.enablePrettyPrint();
		beanWriter.writeXmlDeclaration(Constants.Xml.ISO_8859_15_XML_PROLOG);
		try {
			beanWriter.write(arg0);
		} catch (SAXException ex) {
			throw BetwixtUtils.convertBetwixtException(ex, true);
		} catch (IntrospectionException ex) {
			throw BetwixtUtils.convertBetwixtException(ex, true);
		}
		catch (OutOfMemoryError err) {
			throw BetwixtUtils.convertBetwixtError(err, true);
		}
		beanWriter.flush();
		beanWriter.close();
		
		//StreamResult streamResult = new StreamResult(stringWriter);
		
		if (arg1 instanceof DOMResult) {
			if (((DOMResult)arg1).getNode() != null) {
				marshallDOMNode(stringWriter.toString(), (DOMResult)arg1);
			}
		}
        else if (arg1 instanceof StaxResult) {
            throw new IllegalArgumentException("Source type not yet implemented: " + arg1.getClass());
        }
        else if (arg1 instanceof SAXResult) {
            throw new IllegalArgumentException("Source type not yet implemented: " + arg1.getClass());
        }
        else if (arg1 instanceof StreamResult) {
            throw new IllegalArgumentException("Source type not yet implemented: " + arg1.getClass());
        }
		
	}

	public Object unmarshal(Source arg0) throws XmlMappingException, IOException {

		Object result = null;
		
		validateSourceType(arg0);
		
		this.setUnmarshallClass(getBoundedUnmarshallClass(arg0));
		
		if (this.getUnmarshallClass() == null) {
			throw BetwixtUtils.convertBetwixtException(new BetwixtUnmarshallClassException(UNMARSHALL_CLASS_EXCEPTION), false);
		}
		if (this.getUnmarshallClass() == Object.class) {
			throw BetwixtUtils.convertBetwixtException(new BetwixtUnmarshallClassException(UNMARSHALL_CLASS_EXCEPTION), false);
		}
		
		StringWriter stringWriter = new StringWriter();
		StreamResult streamResult = new StreamResult(stringWriter);
		
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(arg0, streamResult);
		} catch (TransformerConfigurationException ex) {
			throw BetwixtUtils.convertBetwixtException(ex, false);
		} catch (TransformerFactoryConfigurationError ex) {
			throw BetwixtUtils.convertBetwixtError(ex, false);
		} catch (TransformerException ex) {
			throw BetwixtUtils.convertBetwixtException(ex, false);
		}

		String stringBean = stringWriter.toString();

		BindingConfiguration bc = new BindingConfiguration();
		BeanReader beanReader = new BeanReader();
		
		bc.setMapIDs(false);
		
		beanReader.getXMLIntrospector().getConfiguration().setWrapCollectionsInElement(false);
		beanReader.setBindingConfiguration(bc);
		try {
			beanReader.registerBeanClass(this.getUnmarshallClass());
		} catch (IntrospectionException ex) {
			throw BetwixtUtils.convertBetwixtException(ex, false);
		}
		
		try {
			result = (Object)beanReader.parse(new StringReader(stringBean));
		} catch (SAXException ex) {
			throw BetwixtUtils.convertBetwixtException(ex, false);
		}
		
		return result;
	}


	
	
	

	public String marshal(Object arg0) throws XmlMappingException, IOException {

		StringWriter stringWriter = new StringWriter();
		BeanWriter beanWriter = new BeanWriter(stringWriter);
		beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
		beanWriter.getXMLIntrospector().getConfiguration().setWrapCollectionsInElement(false);

		if (this.isDecapitalizeNameMapper()) {
			beanWriter.getXMLIntrospector().getConfiguration().setElementNameMapper(new DecapitalizeNameMapper());
		}
		
		beanWriter.enablePrettyPrint();
		try {
			beanWriter.write(arg0);
		} catch (SAXException ex) {
			throw BetwixtUtils.convertBetwixtException(ex, true);
		} catch (IntrospectionException ex) {
			throw BetwixtUtils.convertBetwixtException(ex, true);
		}
		beanWriter.flush();
		beanWriter.close();

		return stringWriter.toString();
		
	}
	
	
	
	public Object unmarshal(byte[] bytes, Class<?> clazz) throws XmlMappingException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

		Object result = null;
		
		this.setUnmarshallClass(clazz);
		
		if (this.getUnmarshallClass() == null) {
			throw BetwixtUtils.convertBetwixtException(new BetwixtUnmarshallClassException(UNMARSHALL_CLASS_EXCEPTION), false);
		}
		if (this.getUnmarshallClass() == Object.class) {
			throw BetwixtUtils.convertBetwixtException(new BetwixtUnmarshallClassException(UNMARSHALL_CLASS_EXCEPTION), false);
		}
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byteArrayOutputStream.write(bytes);

		String stringBean = byteArrayOutputStream.toString();

		BindingConfiguration bc = new BindingConfiguration();
		BeanReader beanReader = new BeanReader();
		
		bc.setMapIDs(false);
		
		if (this.isPluralStemmerRequired()) {
				beanReader.getXMLIntrospector().getConfiguration().setPluralStemmer(getPluralStemmer());
		}
		
		if (this.isNameSpaceAware()) {
			NamespacePrefixMapper namespacePrefixMapper = new NamespacePrefixMapper();
			namespacePrefixMapper.setPrefix(this.getNameSpace(), this.getNameSpacePrefix());
			beanReader.getXMLIntrospector().getConfiguration().setPrefixMapper(namespacePrefixMapper);
			beanReader.setNamespaceAware(true);
		}
		
		beanReader.getXMLIntrospector().getConfiguration().setWrapCollectionsInElement(false);
		beanReader.setBindingConfiguration(bc);
		try {
			beanReader.registerBeanClass(this.getUnmarshallClass());
		} catch (IntrospectionException ex) {
			throw BetwixtUtils.convertBetwixtException(ex, false);
		}
		
		try {
			result = (Object)beanReader.parse(new StringReader(stringBean));
		} catch (SAXException ex) {
			throw BetwixtUtils.convertBetwixtException(ex, false);
		}
		
		return result;
	}
	
	
	
	
	public Object unmarshal(byte[] bytes, Class<?>[] clazz) throws XmlMappingException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

		Object result = null;
		
		this.setClassesToBeBound(clazz);
		
		if (this.getUnmarshallClass() == null) {
			throw BetwixtUtils.convertBetwixtException(new BetwixtUnmarshallClassException(UNMARSHALL_CLASS_EXCEPTION), false);
		}
		if (this.getUnmarshallClass() == Object.class) {
			throw BetwixtUtils.convertBetwixtException(new BetwixtUnmarshallClassException(UNMARSHALL_CLASS_EXCEPTION), false);
		}
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byteArrayOutputStream.write(bytes);

		String stringBean = byteArrayOutputStream.toString();

		BindingConfiguration bc = new BindingConfiguration();
		BeanReader beanReader = new BeanReader();
		
		bc.setMapIDs(false);
		
		if (this.isDecapitalizeElements()) {
			beanReader.getXMLIntrospector().getConfiguration().setElementNameMapper(new DecapitalizeNameMapper());
		}
		
		if (this.isPluralStemmerRequired()) {
				beanReader.getXMLIntrospector().getConfiguration().setPluralStemmer(getPluralStemmer());
		}
		
		if (this.isNameSpaceAware()) {
			NamespacePrefixMapper namespacePrefixMapper = new NamespacePrefixMapper();
			namespacePrefixMapper.setPrefix(this.getNameSpace(), this.getNameSpacePrefix());
			beanReader.getXMLIntrospector().getConfiguration().setPrefixMapper(namespacePrefixMapper);
			beanReader.setNamespaceAware(true);
		}
		
		beanReader.getXMLIntrospector().getConfiguration().setWrapCollectionsInElement(this.isWrapCollectionsInElement());
		beanReader.setBindingConfiguration(bc);
		try {
			for (int i = 0; i < clazz.length; i++) {
				beanReader.registerBeanClass(clazz[i]);
			}
		} catch (IntrospectionException ex) {
			throw BetwixtUtils.convertBetwixtException(ex, false);
		}
		
		try {
			result = (Object)beanReader.parse(new StringReader(stringBean));
		} catch (SAXException ex) {
			throw BetwixtUtils.convertBetwixtException(ex, false);
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public boolean supports(Class arg0) {
		return true;
	}

	/**
	 * @return the unmarshallClass
	 */
	public Class<?> getUnmarshallClass() {
		return unmarshallClass;
	}

	/**
	 * @param unmarshallClass the unmarshallClass to set
	 */
	public void setUnmarshallClass(Class<?> unmarshallClass) {
		this.unmarshallClass = unmarshallClass;
	}

	private void validateSourceType(Source source) {
		String feature = "";
        if (source instanceof DOMSource) {
        	feature = DOMSource.FEATURE;
        }
        else if (source instanceof StaxSource) {
        	feature = StaxSource.FEATURE;
        }
        else if (source instanceof SAXSource) {
        	feature = SAXSource.FEATURE;
        }
        else if (source instanceof StreamSource) {
        	feature = StreamSource.FEATURE;
        }
        else {
            throw new IllegalArgumentException("Unknown Source type: " + source.getClass());
        }
        
        if (!transformerFactorySupportedFeature(feature)) {
            throw new IllegalArgumentException("Unsupported Source type: " + source.getClass());
        }
        
	}
	
	private boolean transformerFactorySupportedFeature(String feature) {
		return TransformerFactory.newInstance().getFeature(feature);
	}
	
	private Class<?> getBoundedUnmarshallClass(Source source) {
		Class<?> result = null;
    	String sourceClass = "";
        if (source instanceof DOMSource) {
        	DOMSource domSource = (DOMSource)source;
        	sourceClass = domSource.getNode().getNodeName();
        }
        else if (source instanceof StaxSource) {
            throw new IllegalArgumentException("Source type not yet implemented: " + source.getClass());
        }
        else if (source instanceof SAXSource) {
            throw new IllegalArgumentException("Source type not yet implemented: " + source.getClass());
        }
        else if (source instanceof StreamSource) {
            throw new IllegalArgumentException("Source type not yet implemented: " + source.getClass());
        }
        
        for (int index = 0; index < this.classesToBeBound.length; index++) {
        	Class<?> clazz = (Class<?>)this.classesToBeBound[index];
        	if (clazz.getSimpleName().equalsIgnoreCase(sourceClass)) {
        		result = clazz;
        		break;
        	}
        }
        
		return result;
	}
	
	private void marshallDOMNode(String response, DOMResult result) throws IOException {
		
        DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException ex) {
			throw BetwixtUtils.convertBetwixtException(ex, true);
		} catch (FactoryConfigurationError err) {
			throw BetwixtUtils.convertBetwixtError(err, true);
		}
        
        ByteArrayInputStream responseByteArrayInputStream = new ByteArrayInputStream(response.getBytes());

		Document responseDocument = null;
        
        try {
        	responseDocument = documentBuilder.parse(responseByteArrayInputStream);
		} catch (SAXException ex) {
			throw BetwixtUtils.convertBetwixtException(ex, true);
		}
		
        Document resultDocument = result.getNode().getOwnerDocument();

        Node resultBodyContent = resultDocument.importNode(responseDocument.getFirstChild(), true);

        result.getNode().appendChild(resultBodyContent);
		
	}

	/**
	 * @return the decapitalizeNameMapper
	 */
	public boolean isDecapitalizeNameMapper() {
		return decapitalizeNameMapper;
	}

	/**
	 * @param decapitalizeNameMapper the decapitalizeNameMapper to set
	 */
	public void setDecapitalizeNameMapper(boolean decapitalizeNameMapper) {
		this.decapitalizeNameMapper = decapitalizeNameMapper;
	}

	/**
	 * @return the pluralStemmerRequired
	 */
	public boolean isPluralStemmerRequired() {
		return pluralStemmerRequired;
	}

	/**
	 * @param pluralStemmerRequired the pluralStemmerRequired to set
	 */
	public void setPluralStemmerRequired(boolean pluralStemmerRequired) {
		this.pluralStemmerRequired = pluralStemmerRequired;
	}

	/**
	 * @return the pluralStemmerClass
	 */
	public String getPluralStemmerClass() {
		return pluralStemmerClass;
	}

	/**
	 * @param pluralStemmerClass the pluralStemmerClass to set
	 */
	public void setPluralStemmerClass(String pluralStemmerClass) {
		this.pluralStemmerClass = pluralStemmerClass;
	}

	/**
	 * @return the nameSpaceAware
	 */
	public boolean isNameSpaceAware() {
		return nameSpaceAware;
	}

	/**
	 * @param nameSpaceAware the nameSpaceAware to set
	 */
	public void setNameSpaceAware(boolean nameSpaceAware) {
		this.nameSpaceAware = nameSpaceAware;
	}

	/**
	 * @return the nameSpace
	 */
	public String getNameSpace() {
		return nameSpace;
	}

	/**
	 * @param nameSpace the nameSpace to set
	 */
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	
	/**
	 * @return the nameSpacePrefix
	 */
	public String getNameSpacePrefix() {
		return nameSpacePrefix;
	}

	/**
	 * @param nameSpacePrefix the nameSpacePrefix to set
	 */
	public void setNameSpacePrefix(String nameSpacePrefix) {
		this.nameSpacePrefix = nameSpacePrefix;
	}

	private PluralStemmer getPluralStemmer() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		Class pluralStemmerClass = BetwixtMarshaller.class.forName(this.getPluralStemmerClass());
		PluralStemmer pluralStemmer = (PluralStemmer)pluralStemmerClass.newInstance();
		
		return pluralStemmer;
		
	}

	/**
	 * @return the wrapCollectionsInElement
	 */
	public boolean isWrapCollectionsInElement() {
		return wrapCollectionsInElement;
	}

	/**
	 * @param wrapCollectionsInElement the wrapCollectionsInElement to set
	 */
	public void setWrapCollectionsInElement(boolean wrapCollectionsInElement) {
		this.wrapCollectionsInElement = wrapCollectionsInElement;
	}

	/**
	 * @return the attributesForPrimitives
	 */
	public boolean isAttributesForPrimitives() {
		return attributesForPrimitives;
	}

	/**
	 * @param attributesForPrimitives the attributesForPrimitives to set
	 */
	public void setAttributesForPrimitives(boolean attributesForPrimitives) {
		this.attributesForPrimitives = attributesForPrimitives;
	}

	/**
	 * @return the decapitalizeElements
	 */
	public final boolean isDecapitalizeElements() {
		return decapitalizeElements;
	}

	/**
	 * @param decapitalizeElements the decapitalizeElements to set
	 */
	public final void setDecapitalizeElements(boolean decapitalizeElements) {
		this.decapitalizeElements = decapitalizeElements;
	}
	
}
