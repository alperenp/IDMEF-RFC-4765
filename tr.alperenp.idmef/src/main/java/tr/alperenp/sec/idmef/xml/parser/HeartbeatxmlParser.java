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

import java.text.ParseException;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import lombok.NonNull;
import tr.alperenp.sec.idmef.model.alert.support.Address;
import tr.alperenp.sec.idmef.model.alert.support.Process;
import tr.alperenp.sec.idmef.model.core.AdditionalData;
import tr.alperenp.sec.idmef.model.core.Analyzer;
import tr.alperenp.sec.idmef.model.heartbeat.Heartbeat;
import tr.alperenp.sec.idmef.model.misc.*;
import tr.alperenp.sec.idmef.xml.utils.ConstantAttributes;
import tr.alperenp.sec.idmef.xml.utils.ConstantElementNames;

/**
 * 
 * @author alperenp
 *
 */
public class HeartbeatxmlParser {
	
	/**
	 * Properties object containing XML Tag Names to be used in parse operation
	 */
	Properties tagNames;
	
	protected HeartbeatxmlParser(@NonNull Properties properties) {
		this.tagNames = properties;
	}
	
	/**
	 * Extracts {@link Heartbeat} object from xml {@link Element}
	 * 
	 * @param heartbeatRoot
	 * @return
	 * @throws DOMException
	 * @throws ParseException
	 */
	protected Heartbeat parseHeartbeat(Element heartbeatRoot) throws DOMException, ParseException {
		Heartbeat heartbeat = new Heartbeat();
		heartbeat.setMessageid(heartbeatRoot.getAttribute(ConstantAttributes.MESSAGEID));
		NodeList children = heartbeatRoot.getChildNodes();
		List<AdditionalData> extradata = new LinkedList<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.ANALYZER))) {
					// parse analyzer
					heartbeat.setAnalyzer(parseAnalyzer((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.CREATETIME))) {
					// parse create time
					heartbeat.setCreateTime(parseTime((Element) child));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.heartbeatinterval))) {
					// parse heartbeat interval
					short interaval = Short.parseShort(((Element) child).getTextContent());
					heartbeat.setHeartbeatInterval(interaval);
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.ANALYZERTIME))) {
					// parse AnalyzerTime
					heartbeat.setAnalyzerTime(parseTime((Element) child));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.ADDITIONALDATA))) {
					// parse additional data
					extradata.add(parseAdditionalData((Element) child));
					
				}
			}
		}
		if (!extradata.isEmpty()) {
			heartbeat.setAdditionalDatas(extradata);
		}
		
		return heartbeat;
	}
	
	/**
	 * Extracts {@link AdditionalData} object from xml
	 * <p>
	 * WARNING: additional {@link List} of {@link String} values added to standard rfc in order to get value of
	 * additional information
	 * 
	 * @param additionalDataRoot
	 * @return
	 */
	protected AdditionalData parseAdditionalData(Element additionalDataRoot) {
		AdditionalData data = new AdditionalData();
		String meaning = additionalDataRoot.getAttribute(ConstantAttributes.MEANING).trim();
		if (!"".equals(meaning)) {
			data.setMeaning(meaning);
		}
		
		String type = additionalDataRoot.getAttribute(ConstantAttributes.TYPE).trim().toUpperCase();
		if (!"".equals(type)) {
			type = type.replaceAll("-", "");
			data.setDataType(AdditionalData.Type.valueOf(type));
		}
		
		NodeList children = additionalDataRoot.getChildNodes();
		List<String> values = new LinkedList<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				values.add(child.getTextContent());
			}
		}
		data.setValues(values);
		return data;
	}
	
	/**
	 * Extracts {@link Analyzer} object from xml
	 * 
	 * @param analyzerRoot
	 * @return
	 */
	protected Analyzer parseAnalyzer(Element analyzerRoot) {
		tr.alperenp.sec.idmef.model.core.Analyzer.AnalyzerBuilder builder = Analyzer.builder();
		String analyzerId = analyzerRoot.getAttribute(ConstantAttributes.ANALYZERID).trim();
		if (!"".equals(analyzerId)) {
			builder.analyzerid(analyzerId);
		}
		String name = analyzerRoot.getAttribute(ConstantAttributes.NAME).trim();
		if (!"".equals(name)) {
			builder.name(name);
		}
		String manufacturer = analyzerRoot.getAttribute(ConstantAttributes.MANUFACTURER).trim();
		if (!"".equals(manufacturer)) {
			builder.manufacturer(manufacturer);
		}
		String model = analyzerRoot.getAttribute(ConstantAttributes.MODEL).trim();
		if (!"".equals(model)) {
			builder.model(model);
		}
		String version = analyzerRoot.getAttribute(ConstantAttributes.VERSION).trim();
		if (!"".equals(version)) {
			builder.version(version);
		}
		String clazz = analyzerRoot.getAttribute(ConstantAttributes.CLASS).trim();
		if (!"".equals(clazz)) {
			builder.clazz(clazz);
		}
		String ostype = analyzerRoot.getAttribute(ConstantAttributes.OSTYPE).trim();
		if (!"".equals(ostype)) {
			builder.ostype(ostype);
		}
		String osversion = analyzerRoot.getAttribute(ConstantAttributes.OSVERSION).trim();
		if (!"".equals(osversion)) {
			builder.osversion(osversion);
		}
		
		NodeList children = analyzerRoot.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.NODE))) {
					// parse node
					builder.node(parseNode((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.PROCESS))) {
					// parse process
					builder.process(parseProcess((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.ANALYZER))) {
					// parse analyzer
					builder.analyzer(parseAnalyzer((Element) child));
				}
			}
		}
		return builder.build();
	}
	
	/**
	 * Extracts {@link Process} object from xml
	 * 
	 * @param processRoot
	 * @return
	 */
	protected Process parseProcess(Element processRoot) {
		Process process = new Process();
		String ident = processRoot.getAttribute(ConstantAttributes.IDENT).trim();
		if (!"".equals(ident)) {
			process.setIdent(ident);
		}
		NodeList children = processRoot.getChildNodes();
		List<String> args = new LinkedList<>();
		List<String> envs = new LinkedList<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				String textContent = child.getTextContent().trim();
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.name))) {
					process.setName(textContent);
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.pid))) {
					process.setPid(Integer.parseInt(textContent));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.path))) {
					process.setPath(textContent);
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.arg))) {
					args.add(textContent);
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.env))) {
					envs.add(textContent);
				}
			}
		}
		if (!args.isEmpty()) {
			process.setArgs(args);
		}
		if (!envs.isEmpty()) {
			process.setEnvs(envs);
		}
		return process;
	}
	
	/**
	 * Generic Time parser for IDMEF
	 * <p>
	 * Use to parse: CreateTime, DetectTime, AnalyzerTime, access-time, modify-time, change-time
	 * 
	 * @param timeRoot
	 * @return
	 */
	protected IDMEFTime parseTime(Element timeRoot) {
		IDMEFTime time = new IDMEFTime();
		NTPStamp stamp = new NTPStamp(timeRoot.getAttribute(ConstantAttributes.ntpstamp));
		time.setNtpstamp(stamp);
		String dateTime = timeRoot.getTextContent().trim();
		timeAdjustment(dateTime, time);
		return time;
	}
	
	/**
	 * Time adjustment method for parsing properly
	 * 
	 * @param dateTime
	 * @param time
	 */
	private void timeAdjustment(String dateTime, IDMEFTime time) {
		if (dateTime.contains("Z")) {
			// UTC Date
			time.setUTCtimeInMilis(Instant.parse(dateTime).toEpochMilli());
		} else {
			// non-UTC date
			String[] dateParts = new String[2];
			if (dateTime.contains("+")) {
				// dateTime = YYYY-MM-DDThh:mm:ss.ss+hh:mm
				dateParts = dateTime.split("\\+");
				time.setOperator(IDMEFTime.Operator.PLUS);
				
			} else {
				// dateTime = YYYY-MM-DDThh:mm:ss.ss-hh:mm
				int operatorIndex = dateTime.lastIndexOf("-");
				dateParts[0] = dateTime.substring(0, operatorIndex);
				dateParts[1] = dateTime.substring(operatorIndex + 1, dateTime.length());
				time.setOperator(IDMEFTime.Operator.MINUS);
			}
			time.setUTCtimeInMilis(Instant.parse(dateParts[0] + "Z").toEpochMilli());
			time.setAdjustedTime(timeToMiliseconds(dateParts[1]));
		}
		
	}
	
	/**
	 * Converts time (hh:mm) into miliseconds
	 * 
	 * @param hhmm
	 * @return
	 */
	private long timeToMiliseconds(String hhmm) {
		String[] parts = hhmm.split(":");
		long hourMilis = Integer.parseInt(parts[0]) * 3600000;
		long minMilis = Integer.parseInt(parts[1]) * 60000;
		return hourMilis + minMilis;
	}
	
	/**
	 * Extracts {@link tr.alperenp.sec.idmef.model.alert.support.Node} object from xml
	 * 
	 * @param NodeRoot
	 * @return
	 */
	protected tr.alperenp.sec.idmef.model.alert.support.Node parseNode(Element NodeRoot) {
		tr.alperenp.sec.idmef.model.alert.support.Node node = new tr.alperenp.sec.idmef.model.alert.support.Node();
		String category = NodeRoot.getAttribute(ConstantAttributes.CATEGORY).trim();
		if (!"".equals(category)) {
			node.setCategory(tr.alperenp.sec.idmef.model.alert.support.Node.Category.valueOf(category.toUpperCase()));
		}
		String ident = NodeRoot.getAttribute(ConstantAttributes.IDENT).trim();
		if (!"".equals(ident)) {
			node.setIdent(ident);
		}
		NodeList children = NodeRoot.getChildNodes();
		List<Address> addresses = new LinkedList<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.LOCATION))) {
					node.setLocation(child.getTextContent().trim());
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.name))) {
					node.setName(child.getTextContent().trim());
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.Address))) {
					addresses.add(parseAddress((Element) child));
				}
			}
		}
		if (!addresses.isEmpty()) {
			node.setAddresses(addresses);
		}
		return node;
	}
	
	/**
	 * Extracts {@link Address} object from xml
	 * 
	 * @param addressRoot
	 * @return
	 */
	private Address parseAddress(Element addressRoot) {
		Address address = new Address();
		String ident = addressRoot.getAttribute(ConstantAttributes.IDENT).trim();
		if (!"".equals(ident)) {
			address.setIdent(ident);
		}
		String category = addressRoot.getAttribute(ConstantAttributes.CATEGORY).trim();
		if (!"".equals(category)) {
			category = category.replaceAll("-", "");
			address.setCategory(
					tr.alperenp.sec.idmef.model.alert.support.Address.Category.valueOf(category.toUpperCase()));
		}
		String vlan_name = addressRoot.getAttribute(ConstantAttributes.VLAN_NAME).trim();
		if (!"".equals(vlan_name)) {
			address.setVlan_name(vlan_name);
		}
		String vlan_num = addressRoot.getAttribute(ConstantAttributes.VLAN_NUM).trim();
		if (!"".equals(vlan_num)) {
			address.setVlan_num(Integer.parseInt(vlan_num));
		}
		
		NodeList children = addressRoot.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.address))) {
					address.setAddress(child.getTextContent().trim());
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.netmask))) {
					address.setNetmask(child.getTextContent().trim());
				}
			}
		}
		return address;
	}
}
