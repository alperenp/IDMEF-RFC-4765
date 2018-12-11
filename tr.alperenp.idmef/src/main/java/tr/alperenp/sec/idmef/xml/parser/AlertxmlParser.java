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

import java.math.BigInteger;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tr.alperenp.sec.idemef.xml.utils.ConstantAttributes;
import tr.alperenp.sec.idemef.xml.utils.ConstantElementNames;
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
import tr.alperenp.sec.idmef.model.core.AdditionalData;
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
public class AlertxmlParser {
	
	/**
	 * Properties object containing XML Tag Names to be used in parse operation
	 */
	Properties tagNames;
	
	/**
	 * parser for common functionality
	 */
	HeartbeatxmlParser commonParser;
	
	protected AlertxmlParser(Properties properties) {
		this.tagNames = properties;
		commonParser = new HeartbeatxmlParser(tagNames);
	}
	
	/**
	 * Extracts {@link Alert} object from xml {@link Element}
	 * 
	 * @param alertRoot
	 * @return
	 * @throws DOMException
	 * @throws ParseException
	 */
	protected Alert parseAlert(Element alertRoot) throws DOMException, ParseException {
		Alert alert = new Alert();
		String messageId = alertRoot.getAttribute("messageid");
		if (!"".equals(messageId)) {
			alert.setMessageid(messageId);
		}
		NodeList children = alertRoot.getChildNodes();
		List<AdditionalData> extradata = new LinkedList<>();
		List<Source> sources = new LinkedList<>();
		List<Target> targets = new LinkedList<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.ANALYZER))) {
					// parse analyzer
					alert.setAnalyzer(commonParser.parseAnalyzer((Element) child));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.CREATETIME))) {
					// parse create time
					alert.setCreateTime(commonParser.parseTime((Element) child));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.DETECTTIME))) {
					// parse detect time
					alert.setDetectTime(commonParser.parseTime((Element) child));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.ANALYZERTIME))) {
					// parse analyzer time
					alert.setAnalyzerTime(commonParser.parseTime((Element) child));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.SOURCE))) {
					// parse source
					sources.add(parseSource((Element) child));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.TARGET))) {
					// parse target
					targets.add(parseTarget((Element) child));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.CLASSIFICATION))) {
					// parse classification
					alert.setClassification(parseClassification((Element) child));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.ASSESSMENT))) {
					// parse assessment
					alert.setAssessment(parseAssessment((Element) child));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.ADDITIONALDATA))) {
					// parse additional data
					extradata.add(commonParser.parseAdditionalData((Element) child));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.CORRELATIONALERT))) {
					// parse correlation alert
					alert = parseCorrelationAlert(alert, (Element) child);
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.TOOLALERT))) {
					// parse tool alert
					alert = parseToolAlert(alert, (Element) child);
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.OVERFLOWALERT))) {
					// parse overflow alert
					alert = parseOverflowAlert(alert, (Element) child);
				}
			}
		}
		if (!extradata.isEmpty()) {
			alert.setAdditionalDatas(extradata);
		}
		
		if (!sources.isEmpty()) {
			alert.setSources(sources);
		}
		
		if (!targets.isEmpty()) {
			alert.setTargets(targets);
		}
		return alert;
	}
	
	/**
	 * Extracts {@link Assessment} object from xml {@link Element}
	 * 
	 * @param assessmentRoot
	 * @return
	 */
	private Assessment parseAssessment(Element assessmentRoot) {
		Assessment assessment = new Assessment();
		NodeList children = assessmentRoot.getChildNodes();
		List<Action> actions = new LinkedList<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.IMPACT))) {
					assessment.setImpact(parseImpact((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.ACTION))) {
					actions.add(parseAction((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.CONFIDENCE))) {
					String rating = ((Element) child).getAttribute(ConstantAttributes.RATING).trim().toUpperCase();
					if (!"".equals(rating)) {
						assessment.setConfidence(new Confidence(rating));
					}
				}
			}
		}
		if (!actions.isEmpty()) {
			assessment.setActions(actions);
		}
		return assessment;
	}
	
	/**
	 * Extracts {@link Impact} object from xml {@link Element}
	 * 
	 * @param impactRoot
	 * @return
	 */
	private Impact parseImpact(Element impactRoot) {
		Impact impact = new Impact();
		String severity = impactRoot.getAttribute(ConstantAttributes.SEVERITY).trim().toUpperCase();
		if (!"".equals(severity)) {
			impact.setSeverity(Impact.Severity.valueOf(severity));
		}
		
		String completion = impactRoot.getAttribute(ConstantAttributes.COMPLETION).trim().toUpperCase();
		if (!"".equals(completion)) {
			impact.setCompletion(Impact.Completion.valueOf(completion));
		}
		
		String type = impactRoot.getAttribute(ConstantAttributes.TYPE).trim().toUpperCase();
		if (!"".equals(type)) {
			impact.setType(Impact.Type.valueOf(type));
		}
		return impact;
	}
	
	/**
	 * Extracts {@link Action} object from xml {@link Element}
	 * 
	 * @param actionRoot
	 * @return
	 */
	private Action parseAction(Element actionRoot) {
		String category = actionRoot.getAttribute(ConstantAttributes.CATEGORY).trim().toUpperCase();
		category = category.replaceAll("-", "");
		Action action = null;
		if (!"".equals(category)) {
			action = new Action(category);
		} else {
			// default value
			action = new Action();
		}
		
		String description = actionRoot.getTextContent().trim();
		if (!"".equals(description)) {
			action.setDescription(description);
		}
		return action;
	}
	
	/**
	 * Extracts {@link CorrelationAlert} object from xml {@link Element}
	 * 
	 * @param alert
	 * @param correlationAlertRoot
	 * @return
	 */
	private CorrelationAlert parseCorrelationAlert(Alert alert, Element correlationAlertRoot) {
		CorrelationAlert correlationAlert = new CorrelationAlert(alert);
		NodeList children = correlationAlertRoot.getChildNodes();
		List<AlertIdent> alertidents = new LinkedList<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.name))) {
					correlationAlert.setName(child.getTextContent().trim());
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.alertident))) {
					alertidents.add(parseAlertIdent((Element) child));
				}
			}
		}
		
		if (!alertidents.isEmpty()) {
			correlationAlert.setAlertidents(alertidents);
		}
		return correlationAlert;
	}
	
	/**
	 * Extracts {@link ToolAlert} object from xml {@link Element}
	 * 
	 * @param alert
	 * @param toolAlertRoot
	 * @return
	 */
	private ToolAlert parseToolAlert(Alert alert, Element toolAlertRoot) {
		ToolAlert toolAlert = new ToolAlert(alert);
		NodeList children = toolAlertRoot.getChildNodes();
		List<AlertIdent> alertidents = new LinkedList<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.name))) {
					toolAlert.setName(child.getTextContent().trim());
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.alertident))) {
					alertidents.add(parseAlertIdent((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.command))) {
					toolAlert.setCommand(child.getTextContent().trim());
				}
			}
		}
		
		if (!alertidents.isEmpty()) {
			toolAlert.setAlertidents(alertidents);
		}
		return toolAlert;
	}
	
	/**
	 * Extracts {@link OverflowAlert} object from xml {@link Element}
	 * 
	 * @param alert
	 * @param overflowAlertRoot
	 * @return
	 * @throws NumberFormatException
	 */
	private OverflowAlert parseOverflowAlert(Alert alert, Element overflowAlertRoot) throws NumberFormatException {
		OverflowAlert overflowAlert = new OverflowAlert(alert);
		NodeList children = overflowAlertRoot.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.program))) {
					overflowAlert.setProgram(child.getTextContent().trim());
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.size))) {
					overflowAlert.setSize(new BigInteger(child.getTextContent().trim()));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.buffer))) {
					overflowAlert.setBuffer(parseHexBinary(child.getTextContent().trim()));
				}
			}
		}
		return overflowAlert;
	}
	
	/**
	 * @see <a href=
	 *      "https://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java">stackoverflow</a>
	 *      <p>
	 *      Warning: may contain negative elements since byte is defined [-2^7, (2^7 - 1)]
	 * @param s
	 * @return list of Decimal from signed 2's complement: for each 2 digits of hex
	 */
	private List<Byte> parseHexBinary(final String hexString) {
		int len = hexString.length();
		if (len % 2 != 0) {
			throw new IllegalArgumentException("hexBinary needs to be even-length: " + hexString);
		}
		
		List<Byte> byteList = new LinkedList<>();
		for (int i = 0; i < len; i += 2) {
			Byte data = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
					+ Character.digit(hexString.charAt(i + 1), 16));
			byteList.add(data);
		}
		
		return byteList;
	}
	
	/**
	 * Extracts {@link AlertIdent} object from xml {@link Element}
	 * 
	 * @param alertIdentRoot
	 * @return
	 */
	private AlertIdent parseAlertIdent(Element alertIdentRoot) {
		AlertIdent alertIdent = new AlertIdent();
		String analyzerId = alertIdentRoot.getAttribute(ConstantAttributes.ANALYZERID).trim();
		if (!"".equals(analyzerId)) {
			alertIdent.setAnalyzerId(analyzerId);
		}
		
		alertIdent.setAlertident(alertIdentRoot.getTextContent().trim());
		return alertIdent;
	}
	
	/**
	 * Extracts {@link Classification} object from xml {@link Element}
	 * 
	 * @param classificationRoot
	 * @return
	 */
	private Classification parseClassification(Element classificationRoot) {
		Classification classification = new Classification();
		String ident = classificationRoot.getAttribute(ConstantAttributes.IDENT).trim();
		if (!"".equals(ident)) {
			classification.setIdent(ident);
		}
		String text = classificationRoot.getAttribute(ConstantAttributes.TEXT).trim();
		if (!"".equals(text)) {
			classification.setText(text);
		}
		NodeList children = classificationRoot.getChildNodes();
		List<Reference> references = new LinkedList<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.REFERENCE))) {
					references.add(parseReference((Element) child));
				}
			}
		}
		if (!references.isEmpty()) {
			classification.setReferences(references);
		}
		return classification;
	}
	
	/**
	 * Extracts {@link Reference} object from xml {@link Element}
	 * 
	 * @param referenceRoot
	 * @return
	 */
	private Reference parseReference(Element referenceRoot) {
		Reference reference = new Reference();
		String origin = referenceRoot.getAttribute(ConstantAttributes.ORIGIN).trim();
		if (!"".equals(origin)) {
			origin = origin.replaceAll("-", "");
			reference.setOrigin(Reference.Origin.valueOf(origin.toUpperCase()));
		} else {
			reference.setOrigin(Reference.Origin.UNKNOWN);
		}
		
		String meaning = referenceRoot.getAttribute(ConstantAttributes.MEANING).trim();
		if (!"".equals(meaning)) {
			reference.setMeaning(meaning);
		}
		NodeList children = referenceRoot.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.name))) {
					reference.setName(child.getTextContent().trim());
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.url))) {
					reference.setUrl(child.getTextContent().trim());
				}
			}
		}
		return reference;
	}
	
	/**
	 * Extracts {@link Source} object from xml {@link Element}
	 * 
	 * @param sourceRoot
	 * @return
	 */
	private Source parseSource(Element sourceRoot) {
		Source source = parseCommonSourceTarget(sourceRoot);
		String spoofed = sourceRoot.getAttribute(ConstantAttributes.SPOOFED).trim();
		if (!"".equals(spoofed)) {
			source.setSpoofed(Source.Spoofed.valueOf(spoofed.toUpperCase()));
		}
		return source;
	}
	
	/**
	 * Extracts {@link Target} object from xml {@link Element}
	 * <p>
	 * Since {@link Source} and {@link Target} share common attributes, extracts common attributes from {@link Source}
	 * and remaning fields are extracted
	 * 
	 * @param targetRoot
	 * @return
	 */
	private Target parseTarget(Element targetRoot) {
		Target target = createTargetFromSource(parseCommonSourceTarget(targetRoot));
		String decoy = targetRoot.getAttribute(ConstantAttributes.DECOY).trim();
		if (!"".equals(decoy)) {
			target.setDecoy(Target.Decoy.valueOf(decoy.toUpperCase()));
		}
		NodeList children = targetRoot.getChildNodes();
		List<File> files = new LinkedList<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.FILE))) {
					files.add(parseFile((Element) child));
				}
			}
		}
		
		if (!files.isEmpty()) {
			target.setFiles(files);
		}
		return target;
	}
	
	/**
	 * Extracts {@link Checksum} object from xml {@link Element}
	 * 
	 * @param checksumRoot
	 * @return
	 */
	private Checksum parseCheckSum(Element checksumRoot) {
		Checksum checksum = new Checksum();
		String algorithm = checksumRoot.getAttribute(ConstantAttributes.ALGORITHM).trim();
		if (!"".equals(algorithm)) {
			algorithm = algorithm.replaceAll("-", "").toUpperCase();
			checksum.setAlgorithm(Checksum.Algorithm.valueOf(algorithm));
		}
		NodeList children = checksumRoot.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				String text = ((Element) child).getTextContent().trim();
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.value))) {
					checksum.setValue(text);
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.key))) {
					checksum.setKey(text);
				}
			}
		}
		return checksum;
	}
	
	/**
	 * Creates a {@link Target} from {@link Source}
	 * <p>
	 * puts common fields from source to target
	 * 
	 * @param source
	 * @return
	 */
	private Target createTargetFromSource(Source source) {
		Target target = new Target();
		target.setIdent(source.getIdent());
		target.setIface(source.getIface());
		target.setNode(source.getNode());
		target.setProcess(source.getProcess());
		target.setService(source.getService());
		target.setUser(source.getUser());
		return target;
	}
	
	/**
	 * Extracts {@link File} object from xml {@link Element}
	 * 
	 * @param fileRoot
	 * @return
	 */
	private File parseFile(Element fileRoot) {
		File file = new File();
		String ident = fileRoot.getAttribute(ConstantAttributes.IDENT).trim();
		if (!"".equals(ident)) {
			file.setIdent(ident);
		}
		String category = fileRoot.getAttribute(ConstantAttributes.CATEGORY).trim();
		if (!"".equals(category)) {
			category = category.toUpperCase();
			file.setCategory(File.Category.valueOf(category));
		}
		
		String fsType = fileRoot.getAttribute(ConstantAttributes.FSTYPE).trim();
		if (!"".equals(fsType)) {
			file.setFstype(fsType);
		}
		
		String file_type = fileRoot.getAttribute(ConstantAttributes.FILE_TYPE).trim();
		if (!"".equals(file_type)) {
			file.setFile_type(file_type);
		}
		
		NodeList children = fileRoot.getChildNodes();
		List<FileAccess> fileAccesses = new LinkedList<>();
		List<Linkage> linkages = new LinkedList<>();
		List<Checksum> checksums = new LinkedList<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.name))) {
					file.setName(child.getTextContent().trim());
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.path))) {
					file.setPath(child.getTextContent().trim());
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.create_time))) {
					file.setCreateTime(commonParser.parseTime((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.modify_time))) {
					file.setModifyTime(commonParser.parseTime((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.access_time))) {
					file.setAccessTime(commonParser.parseTime((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.data_size))) {
					String data_size = child.getTextContent().trim();
					file.setDataSize(new BigInteger(data_size));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.disk_size))) {
					String disk_size = child.getTextContent().trim();
					file.setDiskSize(new BigInteger(disk_size));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.FileAccess))) {
					fileAccesses.add(parseFileAccess((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.Linkage))) {
					linkages.add(parseLinkage((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.Inode))) {
					file.setInode(parseInode((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.CHECKSUM))) {
					checksums.add(parseCheckSum((Element) child));
				}
			}
		}
		
		if (!fileAccesses.isEmpty()) {
			file.setFileAccesses(fileAccesses);
		}
		
		if (!linkages.isEmpty()) {
			file.setLinkages(linkages);
		}
		
		if (!checksums.isEmpty()) {
			file.setChecksums(checksums);
		}
		
		return file;
	}
	
	/**
	 * Extracts {@link Inode} object from xml {@link Element}
	 * 
	 * @param inodeRoot
	 * @return
	 * @throws NumberFormatException
	 */
	private Inode parseInode(Element inodeRoot) throws NumberFormatException {
		Inode inode = new Inode();
		NodeList children = inodeRoot.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.change_time))) {
					inode.setChangeTime(commonParser.parseTime((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.number))) {
					String number = ((Element) child).getTextContent().trim();
					inode.setNumber(Integer.parseInt(number));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.major_device))) {
					String major_device = ((Element) child).getTextContent().trim();
					inode.setMajorDevice(Integer.parseInt(major_device));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.minor_device))) {
					String minor_device = ((Element) child).getTextContent().trim();
					inode.setMinorDevice(Integer.parseInt(minor_device));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.c_major_device))) {
					String c_major_device = ((Element) child).getTextContent().trim();
					inode.setCMajorDevice(Integer.parseInt(c_major_device));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.c_minor_device))) {
					String c_minor_device = ((Element) child).getTextContent().trim();
					inode.setCMinorDevice(Integer.parseInt(c_minor_device));
				}
			}
		}
		return inode;
	}
	
	/**
	 * Extracts {@link FileAccess} object from xml {@link Element}
	 * 
	 * @param fileAccessRoot
	 * @return
	 */
	private FileAccess parseFileAccess(Element fileAccessRoot) {
		FileAccess fileAccess = new FileAccess();
		NodeList children = fileAccessRoot.getChildNodes();
		List<Permission> permissions = new LinkedList<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.USERID))) {
					fileAccess.setUserId(parseUserId((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.permission))) {
					String perms = ((Element) child).getAttribute(ConstantAttributes.perms).trim();
					permissions.add(FileAccess.Permission.valueOf(perms.toUpperCase()));
				}
			}
		}
		
		if (!permissions.isEmpty()) {
			fileAccess.setPermissions(permissions);
		}
		return fileAccess;
	}
	
	/**
	 * Extracts {@link Linkage} object from xml {@link Element}
	 * 
	 * @param linkageRoot
	 * @return
	 */
	private Linkage parseLinkage(Element linkageRoot) {
		Linkage linkage = new Linkage();
		String category = linkageRoot.getAttribute(ConstantAttributes.CATEGORY).trim();
		if (!"".equals(category)) {
			category = category.replaceAll("-", "").toUpperCase();
			linkage.setCategory(Linkage.Category.valueOf(category));
		}
		
		NodeList children = linkageRoot.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.name))) {
					linkage.setName(child.getTextContent().trim());
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.path))) {
					linkage.setPath(child.getTextContent().trim());
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.FILE))) {
					linkage.setFile(parseFile((Element) child));
				}
			}
		}
		return linkage;
	}
	
	/**
	 * Extracts {@link Source} object from xml {@link Element}
	 * <p>
	 * This method is used common extraction for {@link Source} and {@link Target}
	 * 
	 * @param sourceRoot
	 * @return
	 */
	private Source parseCommonSourceTarget(Element sourceRoot) {
		Source source = new Source();
		String ident = sourceRoot.getAttribute(ConstantAttributes.IDENT).trim();
		if (!"".equals(ident)) {
			source.setIdent(ident);
		}
		String s_interface = sourceRoot.getAttribute(ConstantAttributes.INTERFACE).trim();
		if (!"".equals(s_interface)) {
			source.setIface(s_interface);
		}
		NodeList children = sourceRoot.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.NODE))) {
					// parse node
					source.setNode(commonParser.parseNode((Element) child));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.USER))) {
					// parse user
					source.setUser(parseUser((Element) child));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.SERVICE))) {
					// parse service
					source.setService(parseService((Element) child));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.PROCESS))) {
					// parse process
					source.setProcess(commonParser.parseProcess((Element) child));
				}
			}
		}
		return source;
	}
	
	/**
	 * Extracts {@link Service} object from xml {@link Element}
	 * 
	 * @param serviceRoot
	 * @return
	 */
	private Service parseService(Element serviceRoot) {
		Service service = new Service();
		String ident = serviceRoot.getAttribute(ConstantAttributes.IDENT).trim();
		if (!"".equals(ident)) {
			service.setIdent(ident);
		}
		String ipVersion = serviceRoot.getAttribute(ConstantAttributes.IP_VERSION).trim();
		if (!"".equals(ipVersion)) {
			service.setIp_version(Integer.parseInt(ipVersion));
		}
		String iana_prot_num = serviceRoot.getAttribute(ConstantAttributes.IANA_PROTOCOL_NUMBER).trim();
		if (!"".equals(iana_prot_num)) {
			service.setIana_protocol_number(Integer.parseInt(iana_prot_num));
		}
		String iana_prot_name = serviceRoot.getAttribute(ConstantAttributes.IANA_PROTOCOL_NAME).trim();
		if (!"".equals(iana_prot_name)) {
			service.setIana_protocol_name(iana_prot_name);
		}
		NodeList children = serviceRoot.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.name))) {
					service.setName(child.getTextContent().trim());
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.port))) {
					// parse port
					service.setPort(Integer.parseInt(child.getTextContent().trim()));
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.portlist))) {
					// parse portlist
					service.setPortlist(parsePortList((Element) child));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.protocol))) {
					// parse protocol
					service.setProtocol(child.getTextContent().trim());
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.WEBSERVICE))) {
					// parse webservice
					service = parseWebService(service, (Element) child);
					
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.SNMPSERVICE))) {
					// parse snmp service
					service = parseSNMPService(service, (Element) child);
				}
			}
		}
		
		return service;
	}
	
	/**
	 * Extracts {@link List portlist} object from xml {@link Element}
	 * 
	 * @param portListRoot
	 * @return
	 */
	private List<IDMEFportRange> parsePortList(Element portListRoot) {
		List<IDMEFportRange> portlist = new LinkedList<>();
		String text = portListRoot.getTextContent().trim();
		String[] ports = text.split(",");
		for (int i = 0; i < ports.length; i++) {
			String[] portRange = ports[i].split("-");
			if (portRange.length == 1) {
				int portNum = Integer.parseInt(portRange[0]);
				portlist.add(new IDMEFportRange(portNum));
			} else if (portRange.length == 2) {
				int start = Integer.parseInt(portRange[0]);
				int end = Integer.parseInt(portRange[1]);
				if (start < end) {
					portlist.add(new IDMEFportRange(start, end));
				}
			}
			
		}
		return portlist;
	}
	
	/**
	 * Extracts {@link WebService} object from xml {@link Element}
	 * 
	 * @param service
	 * @param webServiceRoot
	 * @return
	 */
	private WebService parseWebService(Service service, Element webServiceRoot) {
		WebService webService = new WebService(service);
		NodeList children = webServiceRoot.getChildNodes();
		List<String> args = new LinkedList<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				String text = child.getTextContent().trim();
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.url))) {
					webService.setUrl(text);
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.cgi))) {
					webService.setCgi(text);
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.http_method))) {
					webService.setHttp_method(text);
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.arg))) {
					args.add(text);
				}
			}
		}
		
		if (!args.isEmpty()) {
			webService.setArgs(args);
		}
		return webService;
	}
	
	/**
	 * Extracts {@link SNMPService} object from xml {@link Element}
	 * 
	 * @param service
	 * @param snmpServiceRoot
	 * @return
	 */
	private SNMPService parseSNMPService(Service service, Element snmpServiceRoot) {
		SNMPService snmpService = new SNMPService(service);
		NodeList children = snmpServiceRoot.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				String text = child.getTextContent().trim();
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.oid))) {
					snmpService.setOid(text);
				} else if (child.getNodeName()
						.equals(tagNames.getProperty(ConstantElementNames.messageProcessingModel))) {
					snmpService.setMessageProcessingModel(Integer.parseInt(text));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.securityModel))) {
					snmpService.setSecurityModel(Integer.parseInt(text));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.securityName))) {
					snmpService.setSecurityName(text);
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.securityLevel))) {
					snmpService.setSecurityLevel(Integer.parseInt(text));
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.contextName))) {
					snmpService.setContextName(text);
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.contextEngineID))) {
					snmpService.setContextEngineID(text);
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.command))) {
					snmpService.setCommand(text);
				}
			}
		}
		return snmpService;
	}
	
	/**
	 * Extracts {@link User} object from xml {@link Element}
	 * 
	 * @param userRoot
	 * @return
	 */
	private User parseUser(Element userRoot) {
		User user = new User();
		String ident = userRoot.getAttribute(ConstantAttributes.IDENT).trim();
		if (!"".equals(ident)) {
			user.setIdent(ident);
		}
		String category = userRoot.getAttribute(ConstantAttributes.CATEGORY).trim();
		if (!"".equals(category)) {
			category = category.replaceAll("-", "");
			user.setCategory(User.Category.valueOf(category.toUpperCase()));
		}
		NodeList children = userRoot.getChildNodes();
		List<UserId> userIds = new LinkedList<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.USERID))) {
					userIds.add(parseUserId((Element) child));
				}
			}
		}
		if (!userIds.isEmpty()) {
			user.setUserIds(userIds);
		}
		return user;
	}
	
	/**
	 * Extracts {@link UserId} object from xml {@link Element}
	 * 
	 * @param userIdRoot
	 * @return
	 */
	private UserId parseUserId(Element userIdRoot) {
		UserId userid = new UserId();
		String ident = userIdRoot.getAttribute(ConstantAttributes.IDENT).trim();
		if (!"".equals(ident)) {
			userid.setIdent(ident);
		}
		String type = userIdRoot.getAttribute(ConstantAttributes.TYPE).trim();
		if (!"".equals(type)) {
			type = type.replaceAll("-", "");
			userid.setType(UserId.Type.valueOf(type.toUpperCase()));
		}
		String tty = userIdRoot.getAttribute(ConstantAttributes.TTY).trim();
		if (!"".equals(tty)) {
			userid.setTty(tty);
		}
		NodeList children = userIdRoot.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.name))) {
					userid.setName(child.getTextContent().trim());
				} else if (child.getNodeName().equals(tagNames.getProperty(ConstantElementNames.number))) {
					userid.setNumber(Integer.parseInt(child.getTextContent().trim()));
				}
			}
		}
		return userid;
	}
}
