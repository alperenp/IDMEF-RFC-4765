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

package tr.alperenp.sec.idmef.model.core;

import lombok.Builder;
import lombok.Data;
import tr.alperenp.sec.idmef.model.alert.support.Node;
import tr.alperenp.sec.idmef.model.alert.support.Process;

/**
 * The Analyzer class identifies the analyzer from which the Alert or Heartbeat message originates. Only one analyzer
 * may be encoded for each alert or heartbeat, and that MUST be the analyzer at which the alert or heartbeat originated.
 * Although the IDMEF data model does not prevent the use of hierarchical intrusion detection systems (where alerts get
 * relayed up the tree), it does not provide any way to record the identity of the "relay" analyzers along the path from
 * the originating analyzer to the manager that ultimately receives the alert.
 * 
 * @author alperenp
 *
 */
@Data
@Builder
public class Analyzer {
	
	/**
	 * A unique identifier for the analyzer
	 * 
	 * This attribute is only "partially" optional. If the analyzer makes use of the "ident" attributes on other classes
	 * to provide unique identifiers for those objects, then it MUST also provide a valid "analyzerid" attribute. This
	 * requirement is dictated by the uniqueness requirements of the "ident" attribute (they are unique only within the
	 * context of a particular "analyzerid"). If the analyzer does not make use of the "ident" attributes, however, it
	 * may also omit the "analyzerid" attribute.
	 */
	private String analyzerid;
	
	/**
	 * Optional. An explicit name for the analyzer that may be easier to understand than the analyzerid.
	 */
	private String name;
	
	/**
	 * Optional. The manufacturer of the analyzer software and/or hardware.
	 */
	private String manufacturer;
	
	/**
	 * Optional. The model name/number of the analyzer software and/or hardware.
	 */
	private String model;
	
	/**
	 * Optional. The version number of the analyzer software and/or hardware.
	 */
	private String version;
	
	/**
	 * Optional. The class of analyzer software and/or hardware.
	 */
	private String clazz;
	
	/**
	 * Optional. Operating system name. On POSIX 1003.1 compliant systems, this is the value returned in utsname.sysname
	 * by the uname() system call, or the output of the "uname -s" command.
	 */
	private String ostype;
	
	/**
	 * Optional. Operating system version. On POSIX 1003.1 compliant systems, this is the value returned in
	 * utsname.release by the uname() system call, or the output of the "uname -r" command.
	 */
	private String osversion;
	
	/**
	 * Zero or one. Information about the host or device on which the analyzer resides (network address, network name,
	 * etc.).
	 */
	private Node node;
	
	/**
	 * Zero or one. Information about the process in which the analyzer is executing.
	 */
	private Process process;
	
	/**
	 * Zero or one. Information about the analyzer from which the message may have gone through. The idea behind this
	 * mechanism is that when a manager receives an alert and wants to forward it to another analyzer, it needs to
	 * substitute the original analyzer
	 */
	private Analyzer analyzer;
}
