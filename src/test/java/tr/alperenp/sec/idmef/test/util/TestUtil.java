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

package tr.alperenp.sec.idmef.test.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.junit.jupiter.api.Assertions;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;
import org.xmlunit.diff.ElementSelectors;

import lombok.extern.slf4j.Slf4j;
import tr.alperenp.sec.idmef.model.idmefmessage.IDMEFMessage;
import tr.alperenp.sec.idmef.xml.serializer.IDMEFxmlSerializer;

/**
 * 
 * @author alperenp
 *
 */
@Slf4j
public class TestUtil {
	public static final String IDMEF_TEST_DIRECTORY = "idmef_samples/";
	
	public static final String RFC_HEARTBEATS_DIRECTORY = IDMEF_TEST_DIRECTORY + "fromrfc/heartbeats";
	
	public static final String CRAFTED_HEARTBEATS_DIRECTORY = IDMEF_TEST_DIRECTORY + "crafted/heartbeats";
	
	public static final String RFC_ALERTS_DIRECTORY = IDMEF_TEST_DIRECTORY + "fromrfc/alerts";
	
	public static final String CRAFTED_ALERTS_DIRECTORY = IDMEF_TEST_DIRECTORY + "crafted/alerts";
	
	public static DocumentBuilderFactory createFactory() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// disable DTDs (External Entities) completely
		factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		// instructs the implementation to process XML securely
		factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		return factory;
	}
	
	public static void evaluateResult(IDMEFMessage message, File testFile) {
		try {
			String result = new IDMEFxmlSerializer(TestUtil.createFactory()).serialize(message);
			String originalData = new String(Files.readAllBytes(Paths.get(testFile.getAbsolutePath())));
			xmlCompare(originalData, result);
		} catch (TransformerFactoryConfigurationError | TransformerException | ParserConfigurationException
				| IOException e) {
			log.error("{}", e);
		}
	}
	
	public static void xmlCompare(String originalData, String testXml) {
		
		Diff myDiff = DiffBuilder.compare(originalData).withTest(testXml).ignoreComments()
				.ignoreElementContentWhitespace().ignoreWhitespace()
				.withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName)).checkForSimilar().build();
		Iterator<Difference> iter = myDiff.getDifferences().iterator();
		int size = 0;
		while (iter.hasNext()) {
			log.error("Difference: {}", iter.next().toString());
			size++;
		}
		if (size > 0) {
			System.out.println(" --- ");
			System.out.println(originalData);
			System.out.println(" --- ");
			System.out.println(testXml);
		}
		Assertions.assertEquals(0, size);
	}
}
