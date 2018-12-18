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

import java.io.File;
import java.math.BigInteger;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import tr.alperenp.sec.idmef.model.alert.Alert;
import tr.alperenp.sec.idmef.model.alert.support.Address;
import tr.alperenp.sec.idmef.model.alert.support.FileAccess;
import tr.alperenp.sec.idmef.model.alert.support.Inode;
import tr.alperenp.sec.idmef.model.alert.support.Linkage;
import tr.alperenp.sec.idmef.model.alert.support.Node;
import tr.alperenp.sec.idmef.model.alert.support.Process;
import tr.alperenp.sec.idmef.model.alert.support.Reference;
import tr.alperenp.sec.idmef.model.alert.support.Service;
import tr.alperenp.sec.idmef.model.alert.support.User;
import tr.alperenp.sec.idmef.model.alert.support.UserId;
import tr.alperenp.sec.idmef.model.assessment.Action;
import tr.alperenp.sec.idmef.model.assessment.Confidence;
import tr.alperenp.sec.idmef.model.assessment.Impact;
import tr.alperenp.sec.idmef.model.core.AdditionalData;
import tr.alperenp.sec.idmef.model.core.Analyzer;
import tr.alperenp.sec.idmef.model.core.Analyzer.AnalyzerBuilder;
import tr.alperenp.sec.idmef.model.core.Assessment;
import tr.alperenp.sec.idmef.model.core.Classification;
import tr.alperenp.sec.idmef.model.core.Source;
import tr.alperenp.sec.idmef.model.core.Target;
import tr.alperenp.sec.idmef.model.heartbeat.Heartbeat;
import tr.alperenp.sec.idmef.model.misc.IDMEFTime;
import tr.alperenp.sec.idmef.model.misc.IDMEFportRange;
import tr.alperenp.sec.idmef.model.misc.NTPStamp;
import tr.alperenp.sec.idmef.test.util.TestUtil;

/**
 * 
 * @author alperenp
 *
 */
public class IDMEFxmlSerializerTest {
	
	@Test
	void alertTest() {
		Alert alert = new Alert();
		alert.setVersion("1.0");
		alert.setAnalyzer(createAnalyzer());
		alert.setCreateTime(createSomeUTCTime());
		alert.setClassification(createClassification());
		alert.setDetectTime(createSomeUTCTime());
		alert.setAnalyzerTime(createSomeUTCTime());
		alert.setSources(createSources());
		alert.setTargets(createTargets());
		alert.setAssessment(createAssessment());
		alert.setAdditionalDatas(createAdditionalData());
		String testFilePath = TestUtil.CRAFTED_ALERTS_DIRECTORY + "/serializerTest.xml";
		File testFile = new File(getClass().getClassLoader().getResource(testFilePath).getFile());
		TestUtil.evaluateResult(alert, testFile);
	}
	
	@Test
	void heartbeatTest() {
		Heartbeat hb = new Heartbeat();
		
		hb.setVersion("1.0");
		hb.setMessageid("abc123456789");
		hb.setAnalyzer(createAnalyzer());
		hb.setCreateTime(createSomeUTCTime());
		hb.setAnalyzerTime(createAnalyzerTime());
		
		hb.setHeartbeatInterval((short) 7);
		hb.setAdditionalDatas(createAdditionalData());
		
		String testFilePath = TestUtil.CRAFTED_HEARTBEATS_DIRECTORY + "/serializerTest.xml";
		File testFile = new File(getClass().getClassLoader().getResource(testFilePath).getFile());
		TestUtil.evaluateResult(hb, testFile);
	}
	
	@Test
	void serializeHeartbeatRFCTest() {
		Heartbeat hb = new Heartbeat();
		hb.setVersion("1.0");
		hb.setMessageid("abc123456789");
		hb.setAnalyzer(createRFCHeartbeatAnalyzer());
		hb.setCreateTime(createRFCHeartbeatCreateTime());
		hb.setAdditionalDatas(createRFCHeartbeatAdditionalData());
		String testFilePath = TestUtil.RFC_HEARTBEATS_DIRECTORY + "/7.7_heartbeat_idmef.xml";
		File testFile = new File(getClass().getClassLoader().getResource(testFilePath).getFile());
		TestUtil.evaluateResult(hb, testFile);
	}
	
	private List<Target> createTargets() {
		Target trg1 = new Target();
		trg1.setDecoy(Target.Decoy.YES);
		trg1.setFiles(null);
		trg1.setIdent("target-1");
		trg1.setIface("target-1 interface");
		trg1.setNode(createNode());
		trg1.setProcess(createProcess());
		trg1.setService(createService());
		trg1.setUser(createUser());
		
		Target trg2 = new Target();
		trg2.setDecoy(Target.Decoy.YES);
		trg2.setFiles(createFiles());
		trg2.setIdent("target-2");
		trg2.setIface("target-2 interface");
		trg2.setNode(null);
		trg2.setProcess(null);
		trg2.setService(null);
		trg2.setUser(createUser());
		List<Target> list = new LinkedList<>();
		list.add(trg1);
		list.add(trg2);
		return list;
	}
	
	private List<tr.alperenp.sec.idmef.model.alert.support.File> createFiles() {
		tr.alperenp.sec.idmef.model.alert.support.File file1 = new tr.alperenp.sec.idmef.model.alert.support.File();
		file1.setAccessTime(createSomeUTCTime());
		file1.setCategory(tr.alperenp.sec.idmef.model.alert.support.File.Category.CURRENT);
		file1.setCreateTime(createSomeUTCTime());
		file1.setDataSize(new BigInteger("8589934592"));
		file1.setDiskSize(new BigInteger("8589934592000000"));
		file1.setFile_type("file type");
		file1.setFileAccesses(createFileAccesses());
		file1.setFstype("fstype");
		file1.setIdent("file-1");
		file1.setInode(createInode());
		file1.setLinkages(createLinkages());
		file1.setModifyTime(createSomeUTCTime());
		file1.setName("file-1 name");
		file1.setPath("file-1 path");
		
		List<tr.alperenp.sec.idmef.model.alert.support.File> list = new LinkedList<>();
		list.add(file1);
		return list;
	}
	
	private List<Linkage> createLinkages() {
		Linkage l1 = new Linkage();
		l1.setCategory(Linkage.Category.REPARSEPOINT);
		l1.setName("linkage name");
		l1.setPath("linkage path");
		
		Linkage l2 = new Linkage();
		
		tr.alperenp.sec.idmef.model.alert.support.File f = new tr.alperenp.sec.idmef.model.alert.support.File();
		f.setName("other file");
		f.setIdent("other file id");
		l2.setFile(f);
		l2.setCategory(Linkage.Category.MOUNTPOINT);
		l2.setName("linkage name MOUNT");
		l2.setPath("linkage path MOUNT");
		
		List<Linkage> list = new LinkedList<>();
		list.add(l1);
		list.add(l2);
		return list;
	}
	
	private Inode createInode() {
		Inode inode = new Inode();
		inode.setChangeTime(createSomeUTCTime());
		inode.setCMajorDevice(1);
		inode.setCMinorDevice(2);
		inode.setMajorDevice(3);
		inode.setMinorDevice(4);
		inode.setNumber(19);
		return inode;
	}
	
	private List<FileAccess> createFileAccesses() {
		FileAccess a1 = new FileAccess();
		a1.setUserId(createUserIds().get(0));
		a1.setPermissions(null);
		
		FileAccess a2 = new FileAccess();
		a2.setUserId(null);
		a2.setPermissions(createPermissions());
		List<FileAccess> list = new LinkedList<>();
		list.add(a1);
		list.add(a2);
		return list;
	}
	
	private List<FileAccess.Permission> createPermissions() {
		List<FileAccess.Permission> list = new LinkedList<>();
		list.add(FileAccess.Permission.READ);
		list.add(FileAccess.Permission.WRITE);
		list.add(FileAccess.Permission.EXECUTE);
		return list;
	}
	
	private List<Source> createSources() {
		Source src1 = new Source();
		src1.setIdent("src-1");
		src1.setIface("src-1 interface");
		src1.setNode(createNode());
		src1.setProcess(createProcess());
		src1.setService(null);
		src1.setSpoofed(Source.Spoofed.UNKNOWN);
		src1.setUser(null);
		
		Source src2 = new Source();
		src2.setIdent("src-2");
		src2.setIface("src-2 interface");
		src2.setNode(createNode());
		src2.setProcess(null);
		src2.setService(createService());
		src2.setSpoofed(Source.Spoofed.NO);
		src2.setUser(createUser());
		
		List<Source> list = new LinkedList<>();
		list.add(src1);
		list.add(src2);
		return list;
	}
	
	private User createUser() {
		User user = new User();
		user.setCategory(User.Category.APPLICATION);
		user.setIdent("application user");
		user.setUserIds(createUserIds());
		return user;
	}
	
	private List<UserId> createUserIds() {
		UserId id1 = new UserId();
		id1.setIdent("userid-1");
		id1.setName("userid-1 name");
		id1.setNumber(9);
		id1.setTty("userid-1 tty");
		id1.setType(UserId.Type.ORIGINALUSER);
		
		UserId id2 = new UserId();
		id2.setIdent("userid-2");
		id2.setName("userid-2 name");
		id2.setNumber(79);
		id2.setTty("userid-2 tty");
		id2.setType(UserId.Type.TARGETUSER);
		
		List<UserId> list = new LinkedList<>();
		list.add(id1);
		list.add(id2);
		return list;
	}
	
	private Service createService() {
		Service service = new Service();
		service.setName("service name");
		service.setPort(112);
		service.setIp_version(4);
		service.setProtocol("some protocol");
		service.setIana_protocol_name("iana protool name");
		service.setIana_protocol_number(66);
		IDMEFportRange range1 = new IDMEFportRange(15);
		IDMEFportRange range2 = new IDMEFportRange(55, 115);
		List<IDMEFportRange> portList = new LinkedList<>();
		portList.add(range1);
		portList.add(range2);
		service.setPortlist(portList);
		
		return service;
		
	}
	
	private Assessment createAssessment() {
		Assessment assess = new Assessment();
		assess.setActions(createActions());
		assess.setConfidence(createConfidence());
		assess.setImpact(createImpact());
		return assess;
	}
	
	private Confidence createConfidence() {
		Confidence conf = new Confidence("LOW");
		return conf;
	}
	
	private Impact createImpact() {
		Impact impact = new Impact();
		impact.setCompletion(Impact.Completion.SUCCEEDED);
		impact.setSeverity(Impact.Severity.MEDIUM);
		impact.setType(Impact.Type.USER);
		return impact;
	}
	
	private List<Action> createActions() {
		Action act1 = new Action();
		act1.setCategory(Action.Category.BLOCKINSTALLED);
		act1.setDescription("block action");
		
		Action act2 = new Action();
		act2.setCategory(Action.Category.TAKENOFFLINE);
		act2.setDescription("logoff action");
		
		List<Action> actions = new LinkedList<>();
		actions.add(act1);
		actions.add(act2);
		return actions;
	}
	
	private Classification createClassification() {
		Classification cla = new Classification();
		cla.setIdent("classification-ident");
		cla.setText("classification text");
		cla.setReferences(createReferences());
		return cla;
	}
	
	private List<Reference> createReferences() {
		Reference ref1 = new Reference();
		ref1.setMeaning("Reference meaning");
		ref1.setName("reference name");
		ref1.setOrigin(Reference.Origin.VENDORSPECIFIC);
		ref1.setUrl("reference url here");
		
		Reference ref2 = new Reference();
		ref2.setMeaning("Reference meaning - 2");
		ref2.setName("reference name - 2");
		ref2.setUrl("reference url here - 2");
		
		List<Reference> list = new LinkedList<>();
		list.add(ref1);
		list.add(ref2);
		return list;
	}
	
	private IDMEFTime createSomeUTCTime() {
		IDMEFTime time = new IDMEFTime();
		NTPStamp stamp = new NTPStamp("0xbc722ebe.0x00000000");
		time.setNtpstamp(stamp);
		time.setUTCtimeInMilis(1543934863663L);
		return time;
	}
	
	private IDMEFTime createAnalyzerTime() {
		IDMEFTime time = createSomeUTCTime();
		time.setOperator(IDMEFTime.Operator.MINUS);
		time.setAdjustedTime(7200000);
		return time;
	}
	
	private List<AdditionalData> createAdditionalData() {
		List<AdditionalData> datas = new LinkedList<>();
		;
		AdditionalData data1 = new AdditionalData();
		data1.setDataType(AdditionalData.Type.BOOLEAN);
		data1.setMeaning("truth");
		data1.setValues((List.of("true")));
		datas.add(data1);
		
		AdditionalData data2 = new AdditionalData();
		data2.setDataType(AdditionalData.Type.REAL);
		data2.setMeaning("reality");
		data2.setValues((List.of("3.14", "1.1")));
		datas.add(data2);
		
		AdditionalData data3 = new AdditionalData();
		data3.setDataType(AdditionalData.Type.PORTLIST);
		data3.setMeaning("ports");
		data3.setValues((List.of("3-15,19,29", "80-8080")));
		datas.add(data3);
		return datas;
	}
	
	private Analyzer createAnalyzer() {
		AnalyzerBuilder builder = Analyzer.builder();
		builder.analyzerid("analyzer-id");
		builder.name("analyzer-name");
		builder.clazz("analyzer-class");
		builder.manufacturer("analyzer-manufacturer");
		builder.model("analyzer-model");
		builder.version("analyzer-version");
		builder.ostype("analyzer-os-type");
		builder.osversion("analyzer-os-version");
		builder.node(createNode());
		builder.process(createProcess());
		builder.analyzer(createInnerAnalyzer());
		return builder.build();
	}
	
	private Analyzer createInnerAnalyzer() {
		AnalyzerBuilder builder = Analyzer.builder();
		builder.analyzerid("analyzer-id-2");
		builder.name("analyzer-name-2");
		builder.clazz("analyzer-class-2");
		builder.manufacturer("analyzer-manufacturer-2");
		builder.model("analyzer-model-2");
		builder.version("analyzer-version-2");
		builder.ostype("analyzer-os-type-2");
		builder.osversion("analyzer-os-version-2");
		builder.node(createNode());
		builder.process(createProcess());
		return builder.build();
	}
	
	private Node createNode() {
		Node node = new Node();
		node.setIdent("node-Identifier");
		node.setLocation("node-location");
		node.setName("node-name");
		node.setCategory(Node.Category.CODA);
		List<Address> addresses = createAddresses();
		node.setAddresses(addresses);
		return node;
	}
	
	private List<Address> createAddresses() {
		List<Address> addresses = new LinkedList<>();
		Address addr1 = new Address();
		addr1.setAddress("addr-1");
		addr1.setCategory(Address.Category.IPV4ADDR);
		addr1.setIdent("addr-id");
		addr1.setNetmask("addr1-netmask");
		addr1.setVlan_name("addr1-vlan_name");
		addr1.setVlan_num(55);
		addresses.add(addr1);
		return addresses;
	}
	
	private Process createProcess() {
		Process process = new Process();
		process.setIdent("process-id");
		process.setName("process-name");
		process.setPath("process-path");
		process.setPid(44);
		process.setArgs(createArgs());
		process.setEnvs(createEnvs());
		return process;
	}
	
	private List<String> createEnvs() {
		List<String> envs = new LinkedList<>();
		envs.add("env-1");
		envs.add("env-2");
		envs.add("env-3");
		return envs;
	}
	
	private List<String> createArgs() {
		List<String> args = new LinkedList<>();
		args.add("arg-1");
		args.add("arg-2");
		return args;
	}
	
	private List<AdditionalData> createRFCHeartbeatAdditionalData() {
		List<AdditionalData> datas = new LinkedList<>();
		AdditionalData data1 = new AdditionalData();
		data1.setDataType(AdditionalData.Type.REAL);
		data1.setMeaning("%memused");
		data1.setValues(List.of("62.5"));
		datas.add(data1);
		
		AdditionalData data2 = new AdditionalData();
		data2.setDataType(AdditionalData.Type.REAL);
		data2.setMeaning("%diskused");
		data2.setValues(List.of("87.1"));
		datas.add(data2);
		
		return datas;
	}
	
	private IDMEFTime createRFCHeartbeatCreateTime() {
		IDMEFTime time = new IDMEFTime();
		time.setNtpstamp(new NTPStamp("0xbc722ebe.0x00000000"));
		time.setUTCtimeInMilis(Instant.parse("2000-03-09T14:07:58Z").toEpochMilli());
		return time;
	}
	
	private Analyzer createRFCHeartbeatAnalyzer() {
		AnalyzerBuilder builder = Analyzer.builder();
		builder.analyzerid("hq-dmz-analyzer01");
		builder.node(createHeartbeatAnalyzerNode());
		return builder.build();
	}
	
	private Node createHeartbeatAnalyzerNode() {
		Node node = new Node();
		node.setLocation("Headquarters DMZ Network");
		node.setName("analyzer01.example.com");
		node.setCategory(Node.Category.DNS);
		return node;
	}
}
