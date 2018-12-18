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

package tr.alperenp.sec.idmef.xml.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import lombok.NonNull;
import tr.alperenp.sec.idmef.model.idmefmessage.IDMEFMessage;
import tr.alperenp.sec.idmef.xml.utils.ConstantAttributes;
import tr.alperenp.sec.idmef.xml.utils.ConstantElementNames;
import tr.alperenp.sec.idmef.xml.utils.IDMEFxmlUtils;

/**
 * This class is responsible to parse xml IDMEF and create IDMEF object.
 * <p>
 * This parser uses DOM because it is assumed xml is not too large to cause problems in memory and could be faster for
 * de/serialization
 * 
 * @author alperenp
 *
 */
public class IDMEFxmlParser {
	
	/**
	 * DocumentBuilder for DOM parse
	 */
	private DocumentBuilder builder;
	
	/**
	 * {@link Properties} object containing XML Tag Names to be used in parse operation
	 */
	private Properties tagNames;
	
	/**
	 * instance of {@link HeartbeatxmlParser} to speed up parseing
	 */
	private HeartbeatxmlParser hbParser;
	
	/**
	 * instance of {@link AlertxmlParser} to speed up parseing
	 */
	private AlertxmlParser aParser;
	
	/**
	 * Constructor with {@link DocumentBuilderFactory} which asks user to define factory and give as input
	 * 
	 * @param factory
	 * @throws ParserConfigurationException
	 */
	public IDMEFxmlParser(@NonNull DocumentBuilderFactory factory) throws ParserConfigurationException {
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
	public IDMEFxmlParser(@NonNull DocumentBuilderFactory factory, @NonNull Properties properties)
			throws ParserConfigurationException {
		this.tagNames = properties;
		builder = factory.newDocumentBuilder();
		hbParser = new HeartbeatxmlParser(tagNames);
		aParser = new AlertxmlParser(tagNames);
	}
	
	/**
	 * parses given xml {@link String} and creates {@link IDMEFMessage} object
	 * 
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParseException
	 * @throws DOMException
	 */
	public IDMEFMessage parseXml(@NonNull String xmlString)
			throws ParserConfigurationException, SAXException, IOException, DOMException, ParseException {
		IDMEFMessage message = null;
		ByteArrayInputStream input = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
		Document doc = builder.parse(input);
		Node mainTag = doc.getFirstChild();
		if (!mainTag.getNodeName().equals(tagNames.getProperty(ConstantElementNames.IDMEFMESSAGE))) {
			System.err.println("XML does not satisfy IDMEF standards");
			// return null
			return message;
		}
		
		NodeList children = mainTag.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.HEARTBEAT))) {
					// parse heartbeat
					message = hbParser.parseHeartbeat((Element) child);
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.ALERT))) {
					// parse alert
					message = aParser.parseAlert((Element) child);
				}
				break;
			}
			
		}
		message.setVersion(((Element) mainTag).getAttribute(ConstantAttributes.VERSION));
		return message;
	}
}
