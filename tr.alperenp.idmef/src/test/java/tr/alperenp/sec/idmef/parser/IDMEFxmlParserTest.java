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

package tr.alperenp.sec.idmef.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;
import tr.alperenp.sec.idmef.model.alert.Alert;
import tr.alperenp.sec.idmef.model.alert.CorrelationAlert;
import tr.alperenp.sec.idmef.model.alert.OverflowAlert;
import tr.alperenp.sec.idmef.model.alert.ToolAlert;
import tr.alperenp.sec.idmef.model.heartbeat.Heartbeat;
import tr.alperenp.sec.idmef.model.idmefmessage.IDMEFMessage;
import tr.alperenp.sec.idmef.test.util.TestUtil;
import tr.alperenp.sec.idmef.xml.parser.IDMEFxmlParser;
import tr.alperenp.sec.idmef.xml.util.IDMEFXmlTestUtils;

/**
 * 
 * @author alperenp
 *
 */
@Slf4j
public class IDMEFxmlParserTest {
	
	@Test
	public void parseRFCHeartbeatTest() {
		log.info("Parse Heartbeat from RFC Test started...");
		IDMEFxmlParser parser = null;
		try {
			parser = new IDMEFxmlParser(TestUtil.createFactory(), new IDMEFXmlTestUtils().readProperties());
		} catch (ParserConfigurationException | IOException e) {
			log.error("{}", e);
			Assertions.fail();
		}
		
		File heartbeatsFolder = new File(
				getClass().getClassLoader().getResource(TestUtil.RFC_HEARTBEATS_DIRECTORY).getFile());
		List<IDMEFMessage> heartbeats = new LinkedList<>();
		for (File testFile : heartbeatsFolder.listFiles()) {
			if (!testFile.getAbsolutePath().endsWith(".xml")) {
				continue;
			}
			try {
				String data = new String(Files.readAllBytes(Paths.get(testFile.getAbsolutePath())));
				heartbeats.add(parser.parseXml(data));
			} catch (IOException | ParserConfigurationException | SAXException | DOMException | ParseException e) {
				log.error("{}", e);
				Assertions.fail();
			}
		}
		Assertions.assertTrue(!heartbeats.isEmpty());
		for (IDMEFMessage idmefMessage : heartbeats) {
			Heartbeat message = (Heartbeat) idmefMessage;
			Assertions.assertTrue(message.getMessageid() != null);
			Assertions.assertTrue(!message.getAdditionalDatas().isEmpty());
			Assertions.assertTrue(message.getAnalyzer() != null);
			Assertions.assertTrue(message.getCreateTime() != null);
		}
		log.info("Parse Heartbeat from RFC Test finished!");
	}
	
	@Test
	public void parseCraftedHeartbeatWithDefaultPropertiesTest() {
		log.info("Parse Heartbeat from Crafted files Test started...");
		IDMEFxmlParser parser = null;
		
		try {
			parser = new IDMEFxmlParser(TestUtil.createFactory());
		} catch (ParserConfigurationException e) {
			log.error("{}", e);
			Assertions.fail();
		}
		
		File heartbeatsFolder = new File(
				getClass().getClassLoader().getResource(TestUtil.CRAFTED_HEARTBEATS_DIRECTORY).getFile());
		List<IDMEFMessage> heartbeats = new LinkedList<>();
		for (File testFile : heartbeatsFolder.listFiles()) {
			if (!testFile.getAbsolutePath().endsWith(".xml")) {
				continue;
			}
			try {
				String data = new String(Files.readAllBytes(Paths.get(testFile.getAbsolutePath())));
				heartbeats.add(parser.parseXml(data));
			} catch (IOException | ParserConfigurationException | SAXException | DOMException | ParseException e) {
				log.error("{}", e);
				Assertions.fail();
			}
		}
		Assertions.assertTrue(!heartbeats.isEmpty());
		for (IDMEFMessage idmefMessage : heartbeats) {
			Heartbeat message = (Heartbeat) idmefMessage;
			Assertions.assertTrue(message.getMessageid() != null);
			Assertions.assertTrue(!message.getAdditionalDatas().isEmpty());
			Assertions.assertTrue(message.getAnalyzer() != null);
			Assertions.assertTrue(message.getCreateTime() != null);
			Assertions.assertTrue(message.getHeartbeatInterval() != -1);
		}
		log.info("Parse Heartbeat from Crafted files Test finished!");
	}
	
	@Test
	public void parseRFCAlertTest() {
		log.info("Parse Alerts from RFC Test started...");
		IDMEFxmlParser parser = null;
		try {
			parser = new IDMEFxmlParser(TestUtil.createFactory(), new IDMEFXmlTestUtils().readProperties());
		} catch (ParserConfigurationException | IOException e) {
			log.error("{}", e);
			Assertions.fail();
		}
		
		File alertsFolder = new File(getClass().getClassLoader().getResource(TestUtil.RFC_ALERTS_DIRECTORY).getFile());
		List<IDMEFMessage> alerts = new LinkedList<>();
		for (File testFile : alertsFolder.listFiles()) {
			if (!testFile.getAbsolutePath().endsWith(".xml")) {
				continue;
			}
			try {
				String data = new String(Files.readAllBytes(Paths.get(testFile.getAbsolutePath())));
				alerts.add(parser.parseXml(data));
			} catch (IOException | ParserConfigurationException | SAXException | DOMException | ParseException e) {
				log.error("{}", e);
				Assertions.fail();
			}
		}
		Assertions.assertTrue(!alerts.isEmpty());
		for (IDMEFMessage idmefMessage : alerts) {
			Alert alert = (Alert) idmefMessage;
			Assertions.assertTrue(alert.getSources() != null && !alert.getSources().isEmpty());
			Assertions.assertTrue(alert.getTargets() != null && !alert.getTargets().isEmpty());
			if (alert instanceof CorrelationAlert) {
				CorrelationAlert correlationAlert = (CorrelationAlert) alert;
				Assertions.assertTrue(correlationAlert.getName() != null);
				Assertions.assertTrue(
						correlationAlert.getAlertidents() != null && !correlationAlert.getAlertidents().isEmpty());
			}
		}
		log.info("Parse Alerts from RFC Test finished!");
	}
	
	@Test
	public void parseCraftedAlertTest() {
		log.info("Parse Alerts from crafted xml files Test started...");
		IDMEFxmlParser parser = null;
		try {
			parser = new IDMEFxmlParser(TestUtil.createFactory());
		} catch (ParserConfigurationException e) {
			log.error("{}", e);
			Assertions.fail();
		}
		
		File alertsFolder = new File(
				getClass().getClassLoader().getResource(TestUtil.CRAFTED_ALERTS_DIRECTORY).getFile());
		List<IDMEFMessage> alerts = new LinkedList<>();
		for (File testFile : alertsFolder.listFiles()) {
			if (!testFile.getAbsolutePath().endsWith(".xml")) {
				continue;
			}
			try {
				String data = new String(Files.readAllBytes(Paths.get(testFile.getAbsolutePath())));
				alerts.add(parser.parseXml(data));
			} catch (IOException | ParserConfigurationException | SAXException | DOMException | ParseException e) {
				log.error("{}", e);
				Assertions.fail();
			}
		}
		Assertions.assertTrue(!alerts.isEmpty());
		for (IDMEFMessage idmefMessage : alerts) {
			Alert alert = (Alert) idmefMessage;
			Assertions.assertTrue(alert.getSources() != null && !alert.getSources().isEmpty());
			Assertions.assertTrue(alert.getTargets() != null && !alert.getTargets().isEmpty());
			if (alert instanceof OverflowAlert) {
				OverflowAlert overflowAlert = (OverflowAlert) alert;
				Assertions.assertTrue(overflowAlert.getProgram() != null);
				Assertions.assertTrue(overflowAlert.getSize() != null);
				Assertions.assertTrue(overflowAlert.getBuffer() != null && !overflowAlert.getBuffer().isEmpty());
			} else if (alert instanceof ToolAlert) {
				ToolAlert toolAlert = (ToolAlert) alert;
				Assertions.assertTrue(toolAlert.getName() != null);
				Assertions.assertTrue(toolAlert.getCommand() != null);
				Assertions.assertTrue(toolAlert.getAlertidents() != null && !toolAlert.getAlertidents().isEmpty());
			}
		}
		log.info("Parse Alerts from crafted xml files Test finished!");
	}
}
