/**
 * Copyright 2018, alperenp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tr.alperenp.sec.idmef.xml.serializer;

import java.io.StringWriter;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import lombok.NonNull;
import tr.alperenp.sec.idemef.xml.utils.ConstantAttributes;
import tr.alperenp.sec.idemef.xml.utils.ConstantElementNames;
import tr.alperenp.sec.idemef.xml.utils.IDMEFxmlUtils;
import tr.alperenp.sec.idmef.model.alert.Alert;
import tr.alperenp.sec.idmef.model.heartbeat.Heartbeat;
import tr.alperenp.sec.idmef.model.idmefmessage.IDMEFMessage;

/**
 * This class is responsible to serialize IDMEF object and create corresponding xml.
 * <p>
 * This serializer uses DOM because it is assumed xml is not too large to cause problems in memory and could be faster
 * for de/serialization
 * 
 * @author alperenp
 *
 */
public class IDMEFxmlSerializer {
	
	/**
	 * {@link DocumentBuilderFactory} object to create {@link Document}
	 */
	private DocumentBuilderFactory factory;
	
	/**
	 * {@link Properties} object containing XML Tag Names to be used in serialization
	 */
	private Properties tagNames;
	
	/**
	 * Constructor with {@link DocumentBuilderFactory} which asks user to define factory and give as input
	 * 
	 * @param factory
	 * @throws ParserConfigurationException
	 */
	public IDMEFxmlSerializer(@NonNull DocumentBuilderFactory factory) throws ParserConfigurationException {
		this(factory, new IDMEFxmlUtils().useDefaultProperties());
	}
	
	/**
	 * Constructor with {@link DocumentBuilderFactory} and {@link Properties} which may be required for using different
	 * tagNames in xml
	 * 
	 * @param factory
	 * @param properties
	 * @throws ParserConfigurationException
	 */
	public IDMEFxmlSerializer(@NonNull DocumentBuilderFactory factory, @NonNull Properties properties)
			throws ParserConfigurationException {
		tagNames = properties;
		this.factory = factory;
	}
	
	/**
	 * Generates default {@link Transformer} instance for serialization
	 * 
	 * @return
	 * @throws TransformerConfigurationException
	 * @throws TransformerFactoryConfigurationError
	 */
	private Transformer generateTransformer()
			throws TransformerConfigurationException, TransformerFactoryConfigurationError {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		return transformer;
	}
	
	/**
	 * Generates default {@link Document} instance for serialization
	 * 
	 * @param factory
	 * @return
	 * @throws ParserConfigurationException
	 */
	private Document generateDocument(DocumentBuilderFactory factory) throws ParserConfigurationException {
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation impl = builder.getDOMImplementation();
		Document doc = impl.createDocument(null, null, null);
		doc.setXmlVersion("1.0");
		doc.setXmlStandalone(true);
		return doc;
	}
	
	/**
	 * serializes given {@link IDMEFMessage} and creates xml {@link String}
	 * 
	 * @param message
	 * @return
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 */
	public String serialize(@NonNull IDMEFMessage message)
			throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException {
		Document document = generateDocument(factory);
		Element mainTag = document.createElement(tagNames.getProperty(ConstantElementNames.IDMEFMESSAGE));
		mainTag.setAttribute(ConstantAttributes.VERSION, message.getVersion());
		mainTag.setAttribute("xmlns:idmef", "http://iana.org/idmef");
		if (message instanceof Heartbeat) {
			Heartbeat heartbeat = (Heartbeat) message;
			HeartbeatxmlSerializer heartBeatSerializer = new HeartbeatxmlSerializer(document, tagNames);
			heartBeatSerializer.serializeHeartbeat(mainTag, heartbeat);
		} else if (message instanceof Alert) {
			Alert alert = (Alert) message;
			AlertxmlSerializer alertSerializer = new AlertxmlSerializer(document, tagNames);
			alertSerializer.serializeAlert(mainTag, alert);
		} else {
			// not supported implementation of IDMEFMessage
		}
		
		document.appendChild(mainTag);
		return convertDomSourceToString(new DOMSource(document));
	}
	
	/**
	 * Creates xml {@link String} from given {@link DOMSource}
	 * 
	 * @param domSource
	 * @return
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	private String convertDomSourceToString(DOMSource domSource)
			throws TransformerFactoryConfigurationError, TransformerException {
		StringWriter sw = new StringWriter();
		StreamResult sr = new StreamResult(sw);
		Transformer transformer = generateTransformer();
		transformer.transform(domSource, sr);
		return sw.toString();
	}
}
