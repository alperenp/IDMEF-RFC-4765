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

package tr.alperenp.sec.idmef.xml.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import tr.alperenp.sec.idmef.model.misc.IenumIDMEF;

/**
 * Utility class for reading xml properties and serialization.
 * <p>
 * WARNING: This class is not part of RFC 4765 but used as a utility class
 * 
 * @author alperenp
 *
 */
public class IDMEFxmlUtils {
	public Properties useDefaultProperties() {
		Properties prop = new Properties();
		InputStream inputstream = getClass().getClassLoader().getResourceAsStream("IDMEF_elementNames.properties");
		if (inputstream != null) {
			try {
				prop.load(inputstream);
			} catch (IOException e) {
				System.err.println(e);
			}
		}
		return prop;
	}
	
	/* -------- XML SERIALIZER UTILS -------- */
	
	public void createAndAppendStringElementWithTextContent(String text, Document document, String tagName,
			Element parentElement) {
		if (text != null) {
			Element element = document.createElement(tagName);
			element.setTextContent(text);
			parentElement.appendChild(element);
		}
	}
	
	public void createAndAppendStringElementWithTextContent(int textInt, Document document, String tagName,
			Element parentElement) {
		if (textInt != -1) {
			Element element = document.createElement(tagName);
			element.setTextContent(Integer.toString(textInt));
			parentElement.appendChild(element);
		}
	}
	
	public void putAttributeToElement(Element element, String key, String value) {
		if (value != null) {
			element.setAttribute(key, value);
		}
	}
	
	public void putAttributeToElement(Element element, String key, IenumIDMEF enumValue) {
		if (enumValue != null) {
			element.setAttribute(key, enumValue.getKeyword());
		}
	}
	
	public void putAttributeToElement(Element element, String key, int value) {
		if (value != -1) {
			element.setAttribute(key, Integer.toString(value));
		}
	}
}
