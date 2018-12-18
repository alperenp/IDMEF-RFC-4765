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

/**
 * Definition of Element names for xml de/serialization via reading Properties
 * 
 * @author alperenp
 *
 */
public class ConstantElementNames {
	
	/**
	 * XML tag name IDMEF message class
	 */
	public static final String IDMEFMESSAGE = "IDMEFMESSAGE";
	
	/**
	 * XML tag name CreateTime class
	 */
	public static final String CREATETIME = "CREATETIME";
	
	/**
	 * XML tag name AdditionalData class
	 */
	public static final String ADDITIONALDATA = "ADDITIONALDATA";
	
	/**
	 * XML tag name for name
	 */
	public static final String name = "name";
	
	/**
	 * XML tag name for pid
	 */
	public static final String pid = "pid";
	
	/**
	 * XML tag name for path
	 */
	public static final String path = "path";
	
	/**
	 * XML tag name for arg
	 */
	public static final String arg = "arg";
	
	/**
	 * XML tag name for env
	 */
	public static final String env = "env";
	
	/************************************************/
	/****************** HEARTBEAT *******************/
	/************************************************/
	
	/**
	 * XML tag name for Heartbeat class
	 */
	public static final String HEARTBEAT = "HEARTBEAT";
	
	/**
	 * XML tag name heartbeatinterval
	 */
	public static final String heartbeatinterval = "heartbeatinterval";
	
	/**
	 * XML tag name for Analyzer class
	 */
	public static final String ANALYZER = "ANALYZER";
	
	/**
	 * XML tag name for Node class
	 */
	public static final String NODE = "NODE";
	
	/**
	 * XML tag name for Process class
	 */
	public static final String PROCESS = "PROCESS";
	
	/**
	 * XML tag name for UserId class
	 */
	public static final String USERID = "USERID";
	
	/**
	 * XML tag name for location (physical location)
	 */
	public static final String LOCATION = "LOCATION";
	
	/************************************************/
	/********************** ALERT *******************/
	/************************************************/
	
	/**
	 * XML tag name Alert class
	 */
	public static final String ALERT = "ALERT";
	
	/**
	 * XML tag name CorrelationAlert class
	 */
	public static final String CORRELATIONALERT = "CORRELATIONALERT";
	
	/**
	 * XML tag name Overflow class
	 */
	public static final String OVERFLOWALERT = "OVERFLOWALERT";
	
	/**
	 * XML tag name CorrelationAlert class
	 */
	public static final String TOOLALERT = "TOOLALERT";
	
	/**
	 * XML tag name DetectTime class
	 */
	public static final String DETECTTIME = "DETECTTIME";
	
	/**
	 * XML tag name AnalyzerTime class
	 */
	public static final String ANALYZERTIME = "ANALYZERTIME";
	
	/**
	 * XML tag name Assessment class
	 */
	public static final String ASSESSMENT = "ASSESSMENT";
	
	/**
	 * XML tag name Impact class
	 */
	public static final String IMPACT = "IMPACT";
	
	/**
	 * XML tag name Action class
	 */
	public static final String ACTION = "ACTION";
	
	/**
	 * XML tag name Confidence class
	 */
	public static final String CONFIDENCE = "CONFIDENCE";
	
	/**
	 * XML tag name Source class
	 */
	public static final String SOURCE = "SOURCE";
	
	/**
	 * XML tag name Target class
	 */
	public static final String TARGET = "TARGET";
	
	/**
	 * XML tag name for File class
	 */
	public static final String FILE = "FILE";
	
	/**
	 * XML tag name Classification class
	 */
	public static final String CLASSIFICATION = "CLASSIFICATION";
	
	/**
	 * XML tag name for User class
	 */
	public static final String USER = "USER";
	
	/**
	 * XML tag name for Service class
	 */
	public static final String SERVICE = "SERVICE";
	
	/**
	 * XML tag name Adress class (Container for IP address and netmask)
	 */
	public static final String Address = "Address";
	
	/**
	 * XML tag name address (IP address)
	 */
	public static final String address = "address";
	
	/**
	 * XML tag name netmask
	 */
	public static final String netmask = "netmask";
	
	/**
	 * XML tag name url
	 */
	public static final String url = "url";
	
	/**
	 * XML tag name cgi
	 */
	public static final String cgi = "cgi";
	
	/**
	 * XML tag name http-method
	 */
	public static final String http_method = "http_method";
	
	/**
	 * XML tag name Reference class
	 */
	public static final String REFERENCE = "REFERENCE";
	
	/**
	 * XML tag name for port
	 */
	public static final String port = "port";
	
	/**
	 * XML tag name for portlist
	 */
	public static final String portlist = "portlist";
	
	/**
	 * XML tag name for protocol
	 */
	public static final String protocol = "protocol";
	
	/**
	 * XML tag name for WebService class
	 */
	public static final String WEBSERVICE = "WEBSERVICE";
	
	/**
	 * XML tag name for SNMPService class
	 */
	public static final String SNMPSERVICE = "SNMPSERVICE";
	
	/**
	 * XML tag name for create-time
	 */
	public static final String create_time = "create_time";
	
	/**
	 * XML tag name for modify-time
	 */
	public static final String modify_time = "modify_time";
	
	/**
	 * XML tag name for access-time
	 */
	public static final String access_time = "access_time";
	
	/**
	 * XML tag name for data-size
	 */
	public static final String data_size = "data_size";
	
	/**
	 * XML tag name for disk-size
	 */
	public static final String disk_size = "disk_size";
	
	/**
	 * XML tag name for FileAccess
	 */
	public static final String FileAccess = "FileAccess";
	
	/**
	 * XML tag name for Linkage
	 */
	public static final String Linkage = "Linkage";
	
	/**
	 * XML tag name for Inode
	 */
	public static final String Inode = "Inode";
	
	/**
	 * XML tag name for Checksum class
	 */
	public static final String CHECKSUM = "CHECKSUM";
	
	/**
	 * XML tag name for permission
	 */
	public static final String permission = "permission";
	
	/**
	 * XML tag name for alertident
	 */
	public static final String alertident = "alertident";
	
	/**
	 * XML tag name for command
	 */
	public static final String command = "command";
	
	/**
	 * XML tag name for value
	 */
	public static final String value = "value";
	
	/**
	 * XML tag name for key
	 */
	public static final String key = "key";
	
	/**
	 * XML tag name for change-time
	 */
	public static final String change_time = "change_time";
	
	/**
	 * XML tag name for number
	 */
	public static final String number = "number";
	
	/**
	 * XML tag name for major-device
	 */
	public static final String major_device = "major_device";
	
	/**
	 * XML tag name for minor-device
	 */
	public static final String minor_device = "minor_device";
	
	/**
	 * XML tag name for c-major-device
	 */
	public static final String c_major_device = "c_major_device";
	
	/**
	 * XML tag name for c-minor-device
	 */
	public static final String c_minor_device = "c_minor_device";
	
	/**
	 * XML tag name for program
	 */
	public static final String program = "program";
	
	/**
	 * XML tag name for size
	 */
	public static final String size = "size";
	
	/**
	 * XML tag name for buffer
	 */
	public static final String buffer = "buffer";
	
	/**
	 * XML tag name for oid
	 */
	public static final String oid = "oid";
	
	/**
	 * XML tag name for messageProcessingModel
	 */
	public static final String messageProcessingModel = "messageProcessingModel";
	
	/**
	 * XML tag name for securityModel
	 */
	public static final String securityModel = "securityModel";
	
	/**
	 * XML tag name for securityName
	 */
	public static final String securityName = "securityName";
	
	/**
	 * XML tag name for securityLevel
	 */
	public static final String securityLevel = "securityLevel";
	
	/**
	 * XML tag name for contextName
	 */
	public static final String contextName = "contextName";
	
	/**
	 * XML tag name for contextEngineID
	 */
	public static final String contextEngineID = "contextEngineID";
}
