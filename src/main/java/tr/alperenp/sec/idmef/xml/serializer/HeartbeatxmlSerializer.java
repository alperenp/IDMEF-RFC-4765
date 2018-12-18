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

import java.time.Instant;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import lombok.NonNull;
import tr.alperenp.sec.idmef.model.alert.support.Address;
import tr.alperenp.sec.idmef.model.alert.support.Node;
import tr.alperenp.sec.idmef.model.core.AdditionalData;
import tr.alperenp.sec.idmef.model.core.Analyzer;
import tr.alperenp.sec.idmef.model.alert.support.Process;
import tr.alperenp.sec.idmef.model.heartbeat.Heartbeat;
import tr.alperenp.sec.idmef.model.misc.IDMEFTime;
import tr.alperenp.sec.idmef.xml.utils.ConstantAttributes;
import tr.alperenp.sec.idmef.xml.utils.ConstantElementNames;
import tr.alperenp.sec.idmef.xml.utils.IDMEFxmlUtils;

/**
 * 
 * @author alperenp
 *
 */
public class HeartbeatxmlSerializer {
	/**
	 * Properties object containing XML Tag Names to be used in serialization
	 */
	private Properties tagNames;
	
	/**
	 * {@link Document} to create additional elements for serialization
	 */
	private Document document;
	
	/**
	 * Utility for xml
	 */
	private IDMEFxmlUtils util;
	
	protected HeartbeatxmlSerializer(@NonNull Document doc, @NonNull Properties properties) {
		this.tagNames = properties;
		this.document = doc;
		this.util = new IDMEFxmlUtils();
	}
	
	protected void serializeHeartbeat(Element parentElement, Heartbeat heartbeat) {
		Element heartbeatElement = document.createElement(tagNames.getProperty(ConstantElementNames.HEARTBEAT));
		util.putAttributeToElement(heartbeatElement, ConstantAttributes.MESSAGEID, heartbeat.getMessageid());
		
		if (heartbeat.getAnalyzer() != null) {
			serializeAnalyzer(heartbeatElement, heartbeat.getAnalyzer());
		}
		
		serializeTime(heartbeatElement, heartbeat.getCreateTime(), ConstantElementNames.CREATETIME);
		
		util.createAndAppendStringElementWithTextContent(heartbeat.getHeartbeatInterval(), document,
				tagNames.getProperty(ConstantElementNames.heartbeatinterval), heartbeatElement);
		
		serializeTime(heartbeatElement, heartbeat.getAnalyzerTime(), ConstantElementNames.ANALYZERTIME);
		
		if (heartbeat.getAdditionalDatas() != null) {
			serializeAdditionalDatas(heartbeatElement, heartbeat.getAdditionalDatas());
		}
		
		parentElement.appendChild(heartbeatElement);
	}
	
	protected void serializeAdditionalDatas(Element parentElement, List<AdditionalData> additionalDatas) {
		for (AdditionalData data : additionalDatas) {
			
			if (data.getValues() == null) {
				// without values additional data does not makes sense. Therefore they are not added
				continue;
			}
			
			Element additionalDataElement = document
					.createElement(tagNames.getProperty(ConstantElementNames.ADDITIONALDATA));
			additionalDataElement.setAttribute(ConstantAttributes.TYPE, data.getDataType().getKeyword());
			additionalDataElement.setAttribute(ConstantAttributes.MEANING, data.getMeaning());
			
			String typeElementName = "idmef:" + data.getDataType().getKeyword();
			for (String value : data.getValues()) {
				
				util.createAndAppendStringElementWithTextContent(value, document, typeElementName,
						additionalDataElement);
			}
			parentElement.appendChild(additionalDataElement);
		}
	}
	
	protected void serializeTime(Element parentElement, IDMEFTime time, String elementName) {
		if (time != null && time.getNtpstamp() != null) {
			Element timeElement = document.createElement(tagNames.getProperty(elementName));
			timeElement.setAttribute(ConstantAttributes.ntpstamp, time.getNtpstamp().getNtpstamp());
			timeElement.setTextContent(createTimeString(time));
			parentElement.appendChild(timeElement);
		}
	}
	
	/**
	 * @param time
	 * @return
	 */
	private String createTimeString(IDMEFTime time) {
		// Serialize in UTC
		// YYYY-MM-ddTHH:mm:ss.(SSS)
		String timeStr = Instant.ofEpochMilli(time.getUTCtimeInMilis()).toString();
		
		// TODO: modify precision since time is capable of (SSS) for miliseconds
		
		if (time.getAdjustedTime() != 0L) {
			StringBuilder builder = new StringBuilder();
			// 2000-03-09T10:01:25.93464-05:00
			// YYYY-MM-ddTHH:mm:ss[+/-]HH:mm
			builder.append(timeStr.substring(0, timeStr.length() - 1)).append(time.getOperator().getValue())
					.append(getAdjustedTime(time.getAdjustedTime()));
			timeStr = builder.toString();
			// Serialize time adjustment
		}
		return timeStr;
	}
	
	private String getAdjustedTime(long timeInMiliseconds) {
		String hour = Long.toString((timeInMiliseconds / 3600000) % 24);
		String min = Long.toString((timeInMiliseconds / 60000) % 60);
		StringBuilder builder = new StringBuilder();
		if (hour.length() == 1) {
			builder.append("0");
		}
		builder.append(hour).append(":");
		if (min.length() == 1) {
			builder.append("0");
		}
		builder.append(min);
		return builder.toString();
	}
	
	protected void serializeAnalyzer(Element parentElement, Analyzer analyzer) {
		Element analyzerElement = document.createElement(tagNames.getProperty(ConstantElementNames.ANALYZER));
		
		util.putAttributeToElement(analyzerElement, ConstantAttributes.ANALYZERID, analyzer.getAnalyzerid());
		
		util.putAttributeToElement(analyzerElement, ConstantAttributes.NAME, analyzer.getName());
		
		util.putAttributeToElement(analyzerElement, ConstantAttributes.MANUFACTURER, analyzer.getManufacturer());
		
		util.putAttributeToElement(analyzerElement, ConstantAttributes.MODEL, analyzer.getModel());
		
		util.putAttributeToElement(analyzerElement, ConstantAttributes.VERSION, analyzer.getVersion());
		
		util.putAttributeToElement(analyzerElement, ConstantAttributes.CLASS, analyzer.getClazz());
		
		util.putAttributeToElement(analyzerElement, ConstantAttributes.OSTYPE, analyzer.getOstype());
		
		util.putAttributeToElement(analyzerElement, ConstantAttributes.OSVERSION, analyzer.getOsversion());
		
		if (analyzer.getNode() != null) {
			serializeNode(analyzerElement, analyzer.getNode());
		}
		
		if (analyzer.getProcess() != null) {
			serializeProcess(analyzerElement, analyzer.getProcess());
		}
		
		if (analyzer.getAnalyzer() != null) {
			serializeAnalyzer(analyzerElement, analyzer.getAnalyzer());
		}
		
		parentElement.appendChild(analyzerElement);
	}
	
	protected void serializeNode(Element parentElement, Node node) {
		Element nodeElement = document.createElement(tagNames.getProperty(ConstantElementNames.NODE));
		
		util.putAttributeToElement(nodeElement, ConstantAttributes.IDENT, node.getIdent());
		
		util.putAttributeToElement(nodeElement, ConstantAttributes.CATEGORY, node.getCategory());
		
		util.createAndAppendStringElementWithTextContent(node.getLocation(), document,
				tagNames.getProperty(ConstantElementNames.LOCATION), nodeElement);
		
		util.createAndAppendStringElementWithTextContent(node.getName(), document,
				tagNames.getProperty(ConstantElementNames.name), nodeElement);
		
		if (node.getAddresses() != null) {
			serializeAddress(nodeElement, node.getAddresses());
		}
		
		parentElement.appendChild(nodeElement);
	}
	
	protected void serializeProcess(Element parentElement, Process process) {
		Element processElement = document.createElement(tagNames.getProperty(ConstantElementNames.PROCESS));
		
		util.putAttributeToElement(processElement, ConstantAttributes.IDENT, process.getIdent());
		
		util.createAndAppendStringElementWithTextContent(process.getName(), document,
				tagNames.getProperty(ConstantElementNames.name), processElement);
		
		util.createAndAppendStringElementWithTextContent(process.getPid(), document,
				tagNames.getProperty(ConstantElementNames.pid), processElement);
		
		util.createAndAppendStringElementWithTextContent(process.getPath(), document,
				tagNames.getProperty(ConstantElementNames.path), processElement);
		
		if (process.getArgs() != null) {
			for (String arg : process.getArgs()) {
				util.createAndAppendStringElementWithTextContent(arg, document,
						tagNames.getProperty(ConstantElementNames.arg), processElement);
			}
		}
		
		if (process.getEnvs() != null) {
			for (String env : process.getEnvs()) {
				util.createAndAppendStringElementWithTextContent(env, document,
						tagNames.getProperty(ConstantElementNames.env), processElement);
			}
		}
		
		parentElement.appendChild(processElement);
	}
	
	private void serializeAddress(Element parentElement, List<Address> addresses) {
		for (Address address : addresses) {
			Element AddressElement = document.createElement(tagNames.getProperty(ConstantElementNames.Address));
			
			util.putAttributeToElement(AddressElement, ConstantAttributes.IDENT, address.getIdent());
			
			util.putAttributeToElement(AddressElement, ConstantAttributes.CATEGORY, address.getCategory());
			
			util.putAttributeToElement(AddressElement, ConstantAttributes.VLAN_NAME, address.getVlan_name());
			
			util.putAttributeToElement(AddressElement, ConstantAttributes.VLAN_NUM, address.getVlan_num());
			
			util.createAndAppendStringElementWithTextContent(address.getAddress(), document,
					tagNames.getProperty(ConstantElementNames.address), AddressElement);
			
			util.createAndAppendStringElementWithTextContent(address.getNetmask(), document,
					tagNames.getProperty(ConstantElementNames.netmask), AddressElement);
			
			parentElement.appendChild(AddressElement);
		}
	}
}
