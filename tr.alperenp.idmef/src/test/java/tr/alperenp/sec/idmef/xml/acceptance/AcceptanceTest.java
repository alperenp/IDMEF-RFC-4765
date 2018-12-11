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

package tr.alperenp.sec.idmef.xml.acceptance;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import tr.alperenp.sec.idmef.model.idmefmessage.IDMEFMessage;
import tr.alperenp.sec.idmef.test.util.TestUtil;
import tr.alperenp.sec.idmef.xml.parser.IDMEFxmlParser;
import tr.alperenp.sec.idmef.xml.serializer.IDMEFxmlSerializer;

/**
 * 
 * @author alperenp
 *
 */
@Slf4j
public class AcceptanceTest {
	@Test
	public void acceptanceTest() throws ParserConfigurationException {
		log.info("----- Acceptance Test started -----");
		
		// Find test files
		File idmefTestFolder = new File(
				getClass().getClassLoader().getResource(TestUtil.IDMEF_TEST_DIRECTORY).getFile());
		List<File> files = new LinkedList<>();
		
		// create parser
		IDMEFxmlParser parser = new IDMEFxmlParser(TestUtil.createFactory());
		
		// create serializer
		IDMEFxmlSerializer serializer = new IDMEFxmlSerializer(TestUtil.createFactory());
		
		getAllFilesRecursively(idmefTestFolder, files);
		for (File testFile : files) {
			log.info("Testing file {}/{} - {}", files.indexOf(testFile) + 1, files.size(), testFile.getName());
			try {
				String originalXml = new String(Files.readAllBytes(Paths.get(testFile.getAbsolutePath())));
				IDMEFMessage idmef1 = parser.parseXml(originalXml);
				String serializedXml = serializer.serialize(idmef1);
				IDMEFMessage idmef2 = parser.parseXml(serializedXml);
				Assertions.assertTrue(idmef1.equals(idmef2));
				TestUtil.xmlCompare(originalXml, serializedXml);
			} catch (Exception e) {
				log.error("Error during Acceptance Test! Details: {}", e.getMessage());
			}
		}
		log.info("Acceptance Test finished successfully!");
	}
	
	/**
	 * Iterates over all files under the given directory and returns all files existing under this and other
	 * sub-directories
	 * 
	 * @param directory
	 * @param list
	 */
	private void getAllFilesRecursively(@NonNull File directory, @NonNull List<File> list) {
		if (!directory.exists()) {
			return;
		}
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				getAllFilesRecursively(file, list);
			}
		} else {
			list.add(directory);
		}
	}
}
