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

package tr.alperenp.sec.idmef.model.alert.support;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import tr.alperenp.sec.idmef.model.misc.IenumIDMEF;

/**
 * The Node class is used to identify hosts and other network devices (routers, switches, etc.).
 * 
 * @author alperenp
 *
 */
@Data
public class Node {
	/**
	 * Optional. A unique identifier for the node
	 */
	private String ident;
	
	/**
	 * Optional. The "domain" from which the name information was obtained, if relevant. The permitted values for this
	 * attribute are shown in the table below. The default value is "unknown".
	 */
	private Category category;
	
	/**
	 * Zero or one. The location of the equipment
	 */
	private String location;
	
	/**
	 * Zero or one. The name of the equipment. This information MUST be provided if no Address information is given.
	 */
	private String name;
	
	/**
	 * Zero or more. The network or hardware address of the equipment. Unless a name (above) is provided, at least one
	 * address must be specified.
	 */
	private List<Address> addresses;
	
	@AllArgsConstructor
	public enum Category implements IenumIDMEF {
		/**
		 * Domain unknown or not relevant
		 */
		UNKNOWN(0) {
			public String getKeyword() {
				return "unknown";
			}
		},
		
		/**
		 * Windows 2000 Advanced Directory Services
		 */
		ADS(1) {
			public String getKeyword() {
				return "ads";
			}
		},
		
		/**
		 * Andrew File System (Transarc)
		 */
		AFS(2) {
			public String getKeyword() {
				return "afs";
			}
		},
		
		/**
		 * Coda Distributed File System
		 */
		CODA(3) {
			public String getKeyword() {
				return "coda";
			}
		},
		
		/**
		 * Distributed File System (IBM)
		 */
		DFS(4) {
			public String getKeyword() {
				return "dfs";
			}
		},
		
		/**
		 * Domain Name System
		 */
		DNS(5) {
			public String getKeyword() {
				return "dns";
			}
		},
		
		/**
		 * Local hosts file
		 */
		HOSTS(6) {
			public String getKeyword() {
				return "hosts";
			}
		},
		
		/**
		 * Kerberos realm
		 */
		KERBEROS(7) {
			public String getKeyword() {
				return "kerberos";
			}
		},
		
		/**
		 * Novell Directory Services
		 */
		NDS(8) {
			public String getKeyword() {
				return "nds";
			}
		},
		
		/**
		 * Network Information Services (Sun)
		 */
		NIS(9) {
			public String getKeyword() {
				return "nis";
			}
		},
		
		/**
		 * Network Information Services Plus (Sun)
		 */
		NISPLUS(10) {
			public String getKeyword() {
				return "nisplus";
			}
		},
		
		/**
		 * Windows NT domain
		 */
		NT(11) {
			public String getKeyword() {
				return "nt";
			}
		},
		
		/**
		 * Windows for Workgroups
		 */
		WFW(12) {
			public String getKeyword() {
				return "wfw";
			}
		};
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
}
