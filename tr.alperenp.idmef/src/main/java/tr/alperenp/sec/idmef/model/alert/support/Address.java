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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import tr.alperenp.sec.idmef.model.misc.IenumIDMEF;

/**
 * The Address class is used to represent network, hardware, and application addresses.
 * 
 * @author alperenp
 *
 */
@Data
public class Address {
	/**
	 * Optional. A unique identifier for the address
	 */
	String ident;
	
	/**
	 * Optional. The type of address represented. The permitted values for this attribute are shown below. The default
	 * value is "unknown".
	 */
	Category category;
	
	/**
	 * Optional. The type of address represented. The permitted values for this attribute are shown below. The default
	 * value is "unknown"
	 */
	
	/**
	 * Optional. The name of the Virtual LAN to which the address belongs.
	 */
	String vlan_name;
	
	/**
	 * Optional. The number of the Virtual LAN to which the address belongs.
	 * <p>
	 * set to -1 if not defined
	 */
	int vlan_num = -1;
	
	/**
	 * Exactly one. The address information. The format of this data is governed by the category attribute.
	 */
	String address;
	
	/**
	 * Zero or one. The network mask for the address, if appropriate.
	 */
	String netmask;
	
	@AllArgsConstructor
	public enum Category implements IenumIDMEF {
		/**
		 * Address type unknown
		 */
		UNKNOWN(0) {
			public String getKeyword() {
				return "unknown";
			}
		},
		
		/**
		 * Asynchronous Transfer Mode network address
		 */
		ATM(1) {
			public String getKeyword() {
				return "atm";
			}
		},
		
		/**
		 * Electronic mail address (RFC 2822 [12])
		 */
		EMAIL(2) {
			public String getKeyword() {
				return "e-mail";
			}
		},
		
		/**
		 * Lotus Notes e-mail address
		 */
		LOTUSNOTES(3) {
			public String getKeyword() {
				return "lotus-notes";
			}
		},
		
		/**
		 * Media Access Control (MAC) address
		 */
		MAC(4) {
			public String getKeyword() {
				return "mac";
			}
		},
		
		/**
		 * IBM Shared Network Architecture (SNA) address
		 */
		SNA(5) {
			public String getKeyword() {
				return "sna";
			}
		},
		
		/**
		 * IBM VM ("PROFS") e-mail address
		 */
		VM(6) {
			public String getKeyword() {
				return "vm";
			}
		},
		
		/**
		 * IPv4 host address in dotted-decimal notation (a.b.c.d)
		 */
		IPV4ADDR(7) {
			public String getKeyword() {
				return "ipv4-addr";
			}
		},
		
		/**
		 * IPv4 host address in hexadecimal notation
		 */
		IPV4ADDRHEX(8) {
			public String getKeyword() {
				return "ipv4-addr-hex";
			}
		},
		
		/**
		 * IPv4 network address in dotted-decimal notation, slash, significant bits (a.b.c.d/nn)
		 */
		IPV4NET(9) {
			public String getKeyword() {
				return "ipv4-net";
			}
		},
		
		/**
		 * IPv4 network address in dotted-decimal notation, slash, network mask in dotted-decimal notation
		 * (a.b.c.d/w.x.y.z)
		 */
		IPV4NETMASK(10) {
			public String getKeyword() {
				return "ipv4-net-mask";
			}
		},
		
		/**
		 * IPv6 host address
		 */
		IPV6ADDR(11) {
			public String getKeyword() {
				return "ipv6-addr";
			}
		},
		
		/**
		 * IPv6 host address in hexadecimal notation
		 */
		IPV6ADDRHEX(12) {
			public String getKeyword() {
				return "ipv6-addr-hex";
			}
		},
		
		/**
		 * IPv6 network address, slash, significant bits
		 */
		IPV6NET(13) {
			public String getKeyword() {
				return "ipv6-net";
			}
		},
		
		/**
		 * IPv6 network address, slash, network mask
		 */
		IPV6NETMASK(14) {
			public String getKeyword() {
				return "ipv6-net-mask";
			}
		};
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
}
