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

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import tr.alperenp.sec.idmef.model.alert.support.File;
import tr.alperenp.sec.idmef.model.alert.support.Node;
import tr.alperenp.sec.idmef.model.alert.support.Process;
import tr.alperenp.sec.idmef.model.alert.support.Service;
import tr.alperenp.sec.idmef.model.alert.support.User;
import tr.alperenp.sec.idmef.model.misc.IenumIDMEF;

/**
 * The Target class contains information about the possible target(s) of the event(s) that generated an alert. An event
 * may have more than one target (e.g., in the case of a port sweep).
 * 
 * @author alperenp
 *
 */
@Data
public class Target {
	
	/**
	 * Optional. A unique identifier for this target
	 */
	private String ident;
	/**
	 * Optional. An indication of whether the target is, as far as the analyzer can determine, a decoy. The permitted
	 * values for this attribute are shown below. The default value is "unknown".
	 */
	private Decoy decoy;
	
	/**
	 * Optional. May be used by a network-based analyzer with multiple interfaces to indicate which interface this
	 * target was seen on.
	 */
	private String iface;
	
	/**
	 * Zero or one. Information about the host or device that appears to be causing the events (network address, network
	 * name, etc.).
	 */
	private Node node;
	
	/**
	 * Zero or one. Information about the user that appears to be causing the event(s).
	 */
	private User user;
	
	/**
	 * Zero or one. Information about the process that appears to be causing the event(s).
	 */
	private Process process;
	
	/**
	 * Zero or one. Information about the network service involved in the event(s).
	 */
	private Service service;
	
	/**
	 * Optional. Information about file(s) involved in the event(s).
	 */
	private List<File> files;
	
	@AllArgsConstructor
	public enum Decoy implements IenumIDMEF {
		/**
		 * Accuracy of target information unknown
		 */
		UNKNOWN(0) {
			public String getKeyword() {
				return "unknown";
			}
		},
		
		/**
		 * Target is believed to be a decoy
		 */
		YES(1) {
			public String getKeyword() {
				return "yes";
			}
		},
		
		/**
		 * Target is believed to be "real"
		 */
		NO(2) {
			public String getKeyword() {
				return "no";
			}
		};
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
}
