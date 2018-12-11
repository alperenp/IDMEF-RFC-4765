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

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import lombok.NonNull;
import tr.alperenp.sec.idemef.xml.utils.ConstantAttributes;
import tr.alperenp.sec.idemef.xml.utils.ConstantElementNames;
import tr.alperenp.sec.idemef.xml.utils.IDMEFxmlUtils;
import tr.alperenp.sec.idmef.model.alert.Alert;
import tr.alperenp.sec.idmef.model.alert.CorrelationAlert;
import tr.alperenp.sec.idmef.model.alert.OverflowAlert;
import tr.alperenp.sec.idmef.model.alert.ToolAlert;
import tr.alperenp.sec.idmef.model.alert.support.Checksum;
import tr.alperenp.sec.idmef.model.alert.support.File;
import tr.alperenp.sec.idmef.model.alert.support.FileAccess;
import tr.alperenp.sec.idmef.model.alert.support.FileAccess.Permission;
import tr.alperenp.sec.idmef.model.alert.support.Inode;
import tr.alperenp.sec.idmef.model.alert.support.Linkage;
import tr.alperenp.sec.idmef.model.alert.support.Reference;
import tr.alperenp.sec.idmef.model.alert.support.SNMPService;
import tr.alperenp.sec.idmef.model.alert.support.Service;
import tr.alperenp.sec.idmef.model.alert.support.User;
import tr.alperenp.sec.idmef.model.alert.support.UserId;
import tr.alperenp.sec.idmef.model.alert.support.WebService;
import tr.alperenp.sec.idmef.model.assessment.Action;
import tr.alperenp.sec.idmef.model.assessment.Confidence;
import tr.alperenp.sec.idmef.model.assessment.Impact;
import tr.alperenp.sec.idmef.model.core.Assessment;
import tr.alperenp.sec.idmef.model.core.Classification;
import tr.alperenp.sec.idmef.model.core.Source;
import tr.alperenp.sec.idmef.model.core.Target;
import tr.alperenp.sec.idmef.model.misc.AlertIdent;
import tr.alperenp.sec.idmef.model.misc.IDMEFportRange;

/**
 * 
 * @author alperenp
 *
 */
public class AlertxmlSerializer {
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
	
	/**
	 * Common Serializer methods
	 */
	private HeartbeatxmlSerializer heartbeatSerializer;
	
	protected AlertxmlSerializer(@NonNull Document doc, @NonNull Properties properties) {
		this.tagNames = properties;
		this.document = doc;
		heartbeatSerializer = new HeartbeatxmlSerializer(doc, properties);
		util = new IDMEFxmlUtils();
	}
	
	protected void serializeAlert(Element parentElement, Alert alert) {
		Element alertElement = document.createElement(tagNames.getProperty(ConstantElementNames.ALERT));
		
		if (alert.getMessageid() != null) {
			alertElement.setAttribute(ConstantAttributes.MESSAGEID, alert.getMessageid());
		}
		
		if (alert.getAnalyzer() != null) {
			heartbeatSerializer.serializeAnalyzer(alertElement, alert.getAnalyzer());
		}
		
		heartbeatSerializer.serializeTime(alertElement, alert.getCreateTime(), ConstantElementNames.CREATETIME);
		
		if (alert.getClassification() != null) {
			serializeClassification(alertElement, alert.getClassification());
		}
		
		heartbeatSerializer.serializeTime(alertElement, alert.getDetectTime(), ConstantElementNames.DETECTTIME);
		
		heartbeatSerializer.serializeTime(alertElement, alert.getAnalyzerTime(), ConstantElementNames.ANALYZERTIME);
		
		if (alert.getSources() != null) {
			serializeSources(alertElement, alert.getSources());
		}
		
		if (alert.getTargets() != null) {
			serializeTargets(alertElement, alert.getTargets());
		}
		
		if (alert.getAssessment() != null) {
			serializeAssessment(alertElement, alert.getAssessment());
		}
		
		if (alert.getAdditionalDatas() != null) {
			heartbeatSerializer.serializeAdditionalDatas(alertElement, alert.getAdditionalDatas());
		}
		
		// subclasses
		if (alert instanceof ToolAlert) {
			serializeToolAlert(alertElement, (ToolAlert) alert);
		} else if (alert instanceof OverflowAlert) {
			serializeOverflowAlert(alertElement, (OverflowAlert) alert);
		} else if (alert instanceof CorrelationAlert) {
			serializeCorrelationAlert(alertElement, (CorrelationAlert) alert);
		}
		
		parentElement.appendChild(alertElement);
	}
	
	private void serializeClassification(Element parentElement, Classification classification) {
		Element classificationElement = document
				.createElement(tagNames.getProperty(ConstantElementNames.CLASSIFICATION));
		if (classification.getIdent() != null) {
			classificationElement.setAttribute(ConstantAttributes.IDENT, classification.getIdent());
		}
		
		if (classification.getText() != null) {
			classificationElement.setAttribute(ConstantAttributes.TEXT, classification.getText());
		}
		
		if (classification.getReferences() != null) {
			serializeReferences(classificationElement, classification.getReferences());
		}
		
		parentElement.appendChild(classificationElement);
	}
	
	private void serializeReferences(Element parentElement, List<Reference> references) {
		for (Reference reference : references) {
			Element referenceElement = document.createElement(tagNames.getProperty(ConstantElementNames.REFERENCE));
			
			util.putAttributeToElement(referenceElement, ConstantAttributes.ORIGIN, reference.getOrigin());
			
			util.putAttributeToElement(referenceElement, ConstantAttributes.MEANING, reference.getMeaning());
			
			// Required field
			util.createAndAppendStringElementWithTextContent(reference.getName(), document,
					tagNames.getProperty(ConstantElementNames.name), referenceElement);
			
			// Required field
			util.createAndAppendStringElementWithTextContent(reference.getUrl(), document,
					tagNames.getProperty(ConstantElementNames.url), referenceElement);
			
			parentElement.appendChild(referenceElement);
		}
	}
	
	private void serializeSources(Element parentElement, List<Source> sources) {
		for (Source source : sources) {
			Element sourceElement = document.createElement(tagNames.getProperty(ConstantElementNames.SOURCE));
			
			util.putAttributeToElement(sourceElement, ConstantAttributes.IDENT, source.getIdent());
			
			util.putAttributeToElement(sourceElement, ConstantAttributes.SPOOFED, source.getSpoofed());
			
			util.putAttributeToElement(sourceElement, ConstantAttributes.INTERFACE, source.getIface());
			
			if (source.getNode() != null) {
				heartbeatSerializer.serializeNode(sourceElement, source.getNode());
			}
			
			if (source.getUser() != null) {
				serializeUser(sourceElement, source.getUser());
			}
			
			if (source.getProcess() != null) {
				heartbeatSerializer.serializeProcess(sourceElement, source.getProcess());
			}
			
			if (source.getService() != null) {
				serializeService(sourceElement, source.getService());
			}
			parentElement.appendChild(sourceElement);
		}
	}
	
	private void serializeUser(Element parentElement, User user) {
		Element userElement = document.createElement(tagNames.getProperty(ConstantElementNames.USER));
		
		util.putAttributeToElement(userElement, ConstantAttributes.IDENT, user.getIdent());
		
		util.putAttributeToElement(userElement, ConstantAttributes.CATEGORY, user.getCategory());
		
		if (user.getUserIds() != null) {
			// Required field
			serializeUserIds(userElement, user.getUserIds());
		}
		parentElement.appendChild(userElement);
	}
	
	private void serializeUserIds(Element parentElement, List<UserId> userIds) {
		for (UserId userId : userIds) {
			Element userIdElement = document.createElement(tagNames.getProperty(ConstantElementNames.USERID));
			
			util.putAttributeToElement(userIdElement, ConstantAttributes.IDENT, userId.getIdent());
			
			util.putAttributeToElement(userIdElement, ConstantAttributes.TYPE, userId.getType());
			
			util.putAttributeToElement(userIdElement, ConstantAttributes.TTY, userId.getTty());
			
			util.createAndAppendStringElementWithTextContent(userId.getName(), document,
					tagNames.getProperty(ConstantElementNames.name), userIdElement);
			
			util.createAndAppendStringElementWithTextContent(userId.getNumber(), document,
					tagNames.getProperty(ConstantElementNames.number), userIdElement);
			
			parentElement.appendChild(userIdElement);
		}
	}
	
	private void serializeService(Element parentElement, Service service) {
		Element serviceElement = document.createElement(tagNames.getProperty(ConstantElementNames.SERVICE));
		
		util.putAttributeToElement(serviceElement, ConstantAttributes.IDENT, service.getIdent());
		
		util.putAttributeToElement(serviceElement, ConstantAttributes.IP_VERSION, service.getIp_version());
		
		util.putAttributeToElement(serviceElement, ConstantAttributes.IANA_PROTOCOL_NUMBER,
				service.getIana_protocol_number());
		
		util.putAttributeToElement(serviceElement, ConstantAttributes.IANA_PROTOCOL_NAME,
				service.getIana_protocol_name());
		
		util.createAndAppendStringElementWithTextContent(service.getName(), document,
				tagNames.getProperty(ConstantElementNames.name), serviceElement);
		
		util.createAndAppendStringElementWithTextContent(service.getPort(), document,
				tagNames.getProperty(ConstantElementNames.port), serviceElement);
		
		if (service.getPortlist() != null) {
			serializePortList(serviceElement, service.getPortlist());
		}
		
		util.createAndAppendStringElementWithTextContent(service.getProtocol(), document,
				tagNames.getProperty(ConstantElementNames.protocol), serviceElement);
		
		// sub classes
		if (service instanceof SNMPService) {
			serializeSNMPService(serviceElement, (SNMPService) service);
		} else if (service instanceof WebService) {
			serializeWebService(serviceElement, (WebService) service);
		}
		parentElement.appendChild(serviceElement);
	}
	
	private void serializeSNMPService(Element parentElement, SNMPService snmpService) {
		Element snmpServiceElement = document.createElement(tagNames.getProperty(ConstantElementNames.SNMPSERVICE));
		
		util.createAndAppendStringElementWithTextContent(snmpService.getOid(), document,
				tagNames.getProperty(ConstantElementNames.oid), snmpServiceElement);
		
		util.createAndAppendStringElementWithTextContent(snmpService.getMessageProcessingModel(), document,
				tagNames.getProperty(ConstantElementNames.messageProcessingModel), snmpServiceElement);
		
		util.createAndAppendStringElementWithTextContent(snmpService.getSecurityModel(), document,
				tagNames.getProperty(ConstantElementNames.securityModel), snmpServiceElement);
		
		util.createAndAppendStringElementWithTextContent(snmpService.getSecurityName(), document,
				tagNames.getProperty(ConstantElementNames.securityName), snmpServiceElement);
		
		util.createAndAppendStringElementWithTextContent(snmpService.getSecurityLevel(), document,
				tagNames.getProperty(ConstantElementNames.securityLevel), snmpServiceElement);
		
		util.createAndAppendStringElementWithTextContent(snmpService.getContextName(), document,
				tagNames.getProperty(ConstantElementNames.contextName), snmpServiceElement);
		
		util.createAndAppendStringElementWithTextContent(snmpService.getContextEngineID(), document,
				tagNames.getProperty(ConstantElementNames.contextEngineID), snmpServiceElement);
		
		util.createAndAppendStringElementWithTextContent(snmpService.getCommand(), document,
				tagNames.getProperty(ConstantElementNames.command), snmpServiceElement);
		
		parentElement.appendChild(snmpServiceElement);
	}
	
	private void serializeWebService(Element parentElement, WebService webService) {
		Element webServiceElement = document.createElement(tagNames.getProperty(ConstantElementNames.WEBSERVICE));
		
		// Required field
		util.createAndAppendStringElementWithTextContent(webService.getUrl(), document,
				tagNames.getProperty(ConstantElementNames.url), webServiceElement);
		
		util.createAndAppendStringElementWithTextContent(webService.getCgi(), document,
				tagNames.getProperty(ConstantElementNames.cgi), webServiceElement);
		
		util.createAndAppendStringElementWithTextContent(webService.getHttp_method(), document,
				tagNames.getProperty(ConstantElementNames.http_method), webServiceElement);
		
		if (webService.getArgs() != null) {
			for (String arg : webService.getArgs()) {
				util.createAndAppendStringElementWithTextContent(arg, document,
						tagNames.getProperty(ConstantElementNames.arg), webServiceElement);
			}
		}
		parentElement.appendChild(webServiceElement);
	}
	
	private void serializePortList(Element parentElement, List<IDMEFportRange> portlist) {
		String portRanges = portlist.stream().map(portRange -> portRange.toString()).collect(Collectors.joining(","));
		util.createAndAppendStringElementWithTextContent(portRanges, document,
				tagNames.getProperty(ConstantElementNames.portlist), parentElement);
	}
	
	private void serializeTargets(Element parentElement, List<Target> targets) {
		for (Target target : targets) {
			Element targetElement = document.createElement(tagNames.getProperty(ConstantElementNames.TARGET));
			
			util.putAttributeToElement(targetElement, ConstantAttributes.IDENT, target.getIdent());
			
			util.putAttributeToElement(targetElement, ConstantAttributes.DECOY, target.getDecoy());
			
			util.putAttributeToElement(targetElement, ConstantAttributes.INTERFACE, target.getIface());
			
			if (target.getNode() != null) {
				heartbeatSerializer.serializeNode(targetElement, target.getNode());
			}
			
			if (target.getUser() != null) {
				serializeUser(targetElement, target.getUser());
			}
			
			if (target.getProcess() != null) {
				heartbeatSerializer.serializeProcess(targetElement, target.getProcess());
			}
			
			if (target.getService() != null) {
				serializeService(targetElement, target.getService());
			}
			
			if (target.getFiles() != null) {
				serializeFiles(targetElement, target.getFiles());
			}
			parentElement.appendChild(targetElement);
		}
	}
	
	private void serializeFiles(Element parentElement, List<File> files) {
		for (File file : files) {
			Element fileElement = document.createElement(tagNames.getProperty(ConstantElementNames.FILE));
			
			util.putAttributeToElement(fileElement, ConstantAttributes.IDENT, file.getIdent());
			
			util.putAttributeToElement(fileElement, ConstantAttributes.CATEGORY, file.getCategory());
			
			util.putAttributeToElement(fileElement, ConstantAttributes.FSTYPE, file.getFstype());
			
			util.putAttributeToElement(fileElement, ConstantAttributes.FILE_TYPE, file.getFile_type());
			
			util.createAndAppendStringElementWithTextContent(file.getName(), document,
					tagNames.getProperty(ConstantElementNames.name), fileElement);
			
			util.createAndAppendStringElementWithTextContent(file.getPath(), document,
					tagNames.getProperty(ConstantElementNames.path), fileElement);
			
			heartbeatSerializer.serializeTime(fileElement, file.getCreateTime(), ConstantElementNames.create_time);
			
			heartbeatSerializer.serializeTime(fileElement, file.getModifyTime(), ConstantElementNames.modify_time);
			
			heartbeatSerializer.serializeTime(fileElement, file.getAccessTime(), ConstantElementNames.access_time);
			
			if (file.getDataSize() != null) {
				util.createAndAppendStringElementWithTextContent(file.getDataSize().toString(), document,
						tagNames.getProperty(ConstantElementNames.data_size), fileElement);
			}
			
			if (file.getDiskSize() != null) {
				util.createAndAppendStringElementWithTextContent(file.getDiskSize().toString(), document,
						tagNames.getProperty(ConstantElementNames.disk_size), fileElement);
			}
			
			if (file.getFileAccesses() != null) {
				serializeFileAccesses(fileElement, file.getFileAccesses());
			}
			
			if (file.getLinkages() != null) {
				serializeLinkages(fileElement, file.getLinkages());
			}
			
			if (file.getInode() != null) {
				serializeINode(fileElement, file.getInode());
			}
			
			if (file.getChecksums() != null) {
				serializeChecksums(fileElement, file.getChecksums());
			}
			
			parentElement.appendChild(fileElement);
		}
	}
	
	private void serializeFileAccesses(Element parentElement, List<FileAccess> accesses) {
		for (FileAccess fileAccess : accesses) {
			Element fileAccessElement = document.createElement(tagNames.getProperty(ConstantElementNames.FileAccess));
			
			if (fileAccess.getUserId() != null) {
				List<UserId> ids = new LinkedList<>();
				ids.add(fileAccess.getUserId());
				serializeUserIds(fileAccessElement, ids);
			}
			
			if (fileAccess.getPermissions() != null) {
				// Required field
				for (Permission permission : fileAccess.getPermissions()) {
					Element permissionElement = document
							.createElement(tagNames.getProperty(ConstantElementNames.permission));
					util.putAttributeToElement(permissionElement, ConstantAttributes.perms, permission);
					fileAccessElement.appendChild(permissionElement);
				}
			}
			
			parentElement.appendChild(fileAccessElement);
		}
	}
	
	private void serializeLinkages(Element parentElement, List<Linkage> linkages) {
		for (Linkage linkage : linkages) {
			Element linkageElement = document.createElement(tagNames.getProperty(ConstantElementNames.Linkage));
			
			util.putAttributeToElement(linkageElement, ConstantAttributes.CATEGORY, linkage.getCategory());
			
			util.createAndAppendStringElementWithTextContent(linkage.getName(), document,
					tagNames.getProperty(ConstantElementNames.name), linkageElement);
			
			util.createAndAppendStringElementWithTextContent(linkage.getPath(), document,
					tagNames.getProperty(ConstantElementNames.path), linkageElement);
			
			if (linkage.getFile() != null) {
				List<File> files = new LinkedList<>();
				files.add(linkage.getFile());
				serializeFiles(linkageElement, files);
			}
			
			parentElement.appendChild(linkageElement);
		}
	}
	
	private void serializeINode(Element parentElement, Inode iNode) {
		Element iNodeElement = document.createElement(tagNames.getProperty(ConstantElementNames.Inode));
		
		heartbeatSerializer.serializeTime(iNodeElement, iNode.getChangeTime(), ConstantElementNames.change_time);
		
		util.createAndAppendStringElementWithTextContent(iNode.getNumber(), document,
				tagNames.getProperty(ConstantElementNames.number), iNodeElement);
		
		util.createAndAppendStringElementWithTextContent(iNode.getMajorDevice(), document,
				tagNames.getProperty(ConstantElementNames.major_device), iNodeElement);
		
		util.createAndAppendStringElementWithTextContent(iNode.getMinorDevice(), document,
				tagNames.getProperty(ConstantElementNames.minor_device), iNodeElement);
		
		util.createAndAppendStringElementWithTextContent(iNode.getCMajorDevice(), document,
				tagNames.getProperty(ConstantElementNames.c_major_device), iNodeElement);
		
		util.createAndAppendStringElementWithTextContent(iNode.getCMinorDevice(), document,
				tagNames.getProperty(ConstantElementNames.c_minor_device), iNodeElement);
		
		parentElement.appendChild(iNodeElement);
	}
	
	private void serializeChecksums(Element parentElement, List<Checksum> checksums) {
		for (Checksum checksum : checksums) {
			Element checkSumElement = document.createElement(tagNames.getProperty(ConstantElementNames.CHECKSUM));
			
			if (checksum.getValue() == null) {
				// without value, checksum makes no sense. Therefore, it is skipped if it does not exist
				continue;
			}
			
			util.putAttributeToElement(checkSumElement, ConstantAttributes.ALGORITHM, checksum.getAlgorithm());
			
			util.createAndAppendStringElementWithTextContent(checksum.getValue(), document,
					tagNames.getProperty(ConstantElementNames.value), checkSumElement);
			
			util.createAndAppendStringElementWithTextContent(checksum.getKey(), document,
					tagNames.getProperty(ConstantElementNames.key), checkSumElement);
			
			parentElement.appendChild(checkSumElement);
		}
		
	}
	
	private void serializeAssessment(Element parentElement, Assessment assessment) {
		Element assessmentElement = document.createElement(tagNames.getProperty(ConstantElementNames.ASSESSMENT));
		
		if (assessment.getImpact() != null) {
			serializeImpact(assessmentElement, assessment.getImpact());
		}
		
		if (assessment.getActions() != null) {
			serializeActions(assessmentElement, assessment.getActions());
		}
		
		if (assessment.getConfidence() != null) {
			serializeConfidence(assessmentElement, assessment.getConfidence());
		}
		parentElement.appendChild(assessmentElement);
	}
	
	private void serializeImpact(Element parentElement, Impact impact) {
		Element impactElement = document.createElement(tagNames.getProperty(ConstantElementNames.IMPACT));
		
		util.putAttributeToElement(impactElement, ConstantAttributes.SEVERITY, impact.getSeverity());
		
		util.putAttributeToElement(impactElement, ConstantAttributes.COMPLETION, impact.getCompletion());
		
		util.putAttributeToElement(impactElement, ConstantAttributes.TYPE, impact.getType());
		
		parentElement.appendChild(impactElement);
	}
	
	private void serializeActions(Element parentElement, List<Action> actions) {
		for (Action action : actions) {
			Element actionElement = document.createElement(tagNames.getProperty(ConstantElementNames.ACTION));
			
			// Required field
			util.putAttributeToElement(actionElement, ConstantAttributes.CATEGORY, action.getCategory());
			
			if (action.getDescription() != null) {
				// Required field
				actionElement.setTextContent(action.getDescription());
			}
			parentElement.appendChild(actionElement);
		}
	}
	
	private void serializeConfidence(Element parentElement, Confidence confidence) {
		Element confidenceElement = document.createElement(tagNames.getProperty(ConstantElementNames.CONFIDENCE));
		
		util.putAttributeToElement(confidenceElement, ConstantAttributes.RATING, confidence.getRating());
		
		parentElement.appendChild(confidenceElement);
	}
	
	private void serializeToolAlert(Element parentElement, ToolAlert tAlert) {
		Element tAlertElement = document.createElement(tagNames.getProperty(ConstantElementNames.TOOLALERT));
		
		util.createAndAppendStringElementWithTextContent(tAlert.getName(), document,
				tagNames.getProperty(ConstantElementNames.name), tAlertElement);
		
		util.createAndAppendStringElementWithTextContent(tAlert.getCommand(), document,
				tagNames.getProperty(ConstantElementNames.command), tAlertElement);
		
		if (tAlert.getAlertidents() != null) {
			serializeAlertIdents(tAlertElement, tAlert.getAlertidents());
		}
		
		parentElement.appendChild(tAlertElement);
	}
	
	private void serializeOverflowAlert(Element parentElement, OverflowAlert oAlert) {
		Element oAlertElement = document.createElement(tagNames.getProperty(ConstantElementNames.OVERFLOWALERT));
		
		util.createAndAppendStringElementWithTextContent(oAlert.getProgram(), document,
				tagNames.getProperty(ConstantElementNames.program), oAlertElement);
		
		if (oAlert.getSize() != null) {
			util.createAndAppendStringElementWithTextContent(oAlert.getSize().toString(), document,
					tagNames.getProperty(ConstantElementNames.size), oAlertElement);
		}
		if (oAlert.getBuffer() != null) {
			util.createAndAppendStringElementWithTextContent(binaryListToHexString(oAlert.getBuffer()), document,
					tagNames.getProperty(ConstantElementNames.buffer), oAlertElement);
		}
		
		parentElement.appendChild(oAlertElement);
	}
	
	private String binaryListToHexString(List<Byte> buffer) {
		StringBuilder sb = new StringBuilder();
		for (Byte puff : buffer) {
			sb.append(String.format("%02X ", puff));
		}
		return sb.toString().replaceAll(" ", "").toLowerCase();
	}
	
	private void serializeCorrelationAlert(Element parentElement, CorrelationAlert cAlert) {
		Element cAlertElement = document.createElement(tagNames.getProperty(ConstantElementNames.CORRELATIONALERT));
		
		util.createAndAppendStringElementWithTextContent(cAlert.getName(), document,
				tagNames.getProperty(ConstantElementNames.name), cAlertElement);
		
		if (cAlert.getAlertidents() != null) {
			// Required field
			serializeAlertIdents(cAlertElement, cAlert.getAlertidents());
		}
		
		parentElement.appendChild(cAlertElement);
	}
	
	private void serializeAlertIdents(Element parentElement, List<AlertIdent> alertIdents) {
		for (AlertIdent alertIdent : alertIdents) {
			Element alertIdentElement = document.createElement(tagNames.getProperty(ConstantElementNames.alertident));
			
			if (alertIdent.getAlertident() != null) {
				// Required field
				alertIdentElement.setTextContent(alertIdent.getAlertident());
			}
			
			util.putAttributeToElement(alertIdentElement, ConstantAttributes.ANALYZERID, alertIdent.getAnalyzerId());
			
			parentElement.appendChild(alertIdentElement);
		}
	}
}
