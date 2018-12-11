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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import tr.alperenp.sec.idmef.model.alert.support.Node;
import tr.alperenp.sec.idmef.model.alert.support.Process;
import tr.alperenp.sec.idmef.model.alert.support.Service;
import tr.alperenp.sec.idmef.model.alert.support.User;
import tr.alperenp.sec.idmef.model.misc.IenumIDMEF;

/**
 * The Source class contains information about the possible source(s) of the event(s) that generated an alert. An event
 * may have more than one source (e.g., in a distributed denial-of-service attack).
 * 
 * @author alperenp
 *
 */
@Data
public class Source {
	
	/**
	 * Optional. A unique identifier for this source; see Section 3.2.9.
	 */
	private String ident;
	
	/**
	 * Optional. An indication of whether the source is, as far as the analyzer can determine, a spoofed address used
	 * for hiding the real origin of the attack.
	 */
	private Spoofed spoofed;
	
	/**
	 * Optional. May be used by a network-based analyzer with multiple interfaces to indicate which interface this
	 * source was seen on.
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
	
	@AllArgsConstructor
	public enum Spoofed implements IenumIDMEF {
		/**
		 * Accuracy of source information unknown
		 */
		UNKNOWN(0) {
			public String getKeyword() {
				return "unknown";
			}
		},
		
		/**
		 * Source is believed to be a decoy
		 */
		YES(1) {
			public String getKeyword() {
				return "yes";
			}
		},
		
		/**
		 * Source is believed to be "real"
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
