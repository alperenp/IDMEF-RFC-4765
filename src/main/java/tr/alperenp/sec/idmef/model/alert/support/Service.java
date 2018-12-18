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

import lombok.Data;
import lombok.NoArgsConstructor;
import tr.alperenp.sec.idmef.model.misc.IDMEFportRange;

/**
 * The Service class describes network services on sources and targets. It can identify services by name, port, and
 * protocol. When Service occurs as an aggregate class of Source, it is understood that the service is one from which
 * activity of interest is originating; and that the service is "attached" to the Node, Process, and User information
 * also contained in Source. Likewise, when Service occurs as an aggregate class of Target, it is understood that the
 * service is one to which activity of interest is being directed; and that the service is "attached" to the Node,
 * Process, and User information also contained in Target. If Service occurs in both Source and Target, then information
 * in both locations should be the same. If information is the same in both locations and implementers wish to carry it
 * in only one location, they should specify it as an aggregate of the Target class.
 * 
 * @author alperenp
 *
 */
@Data
@NoArgsConstructor
public class Service {
	public Service(Service service) {
		setIdent(service.getIdent());
		setIp_version(service.getIp_version());
		setIana_protocol_number(service.getIana_protocol_number());
		setIana_protocol_name(service.getIana_protocol_name());
		setPort(service.getPort());
		setProtocol(service.getProtocol());
	}
	
	/**
	 * Optional. A unique identifier for the service
	 */
	private String ident;
	
	/**
	 * Optional. The IP version number.
	 * <p>
	 * Warning: Set -1 if it is not defined
	 */
	private int ip_version = -1;
	
	/**
	 * Optional. The IANA protocol number.
	 * <p>
	 * Warning: Set -1 if it is not defined
	 */
	private int iana_protocol_number = -1;
	
	/**
	 * Optional. The IANA protocol name.
	 */
	private String iana_protocol_name;
	
	/**
	 * Zero or one. STRING. The name of the service. Whenever possible, the name from the IANA list of well-known ports
	 * SHOULD be used.
	 */
	private String name;
	
	/**
	 * Zero or one. The port number being used.
	 * <p>
	 * Warning: Set -1 if it is not defined
	 */
	private int port = -1;
	
	/**
	 * Zero or one. A list of port numbers being used; see Section 3.2.8 for formatting rules. If a portlist is given,
	 * the iana_protocol_number and iana_protocol_name MUST apply to all the elements of the list.
	 */
	private List<IDMEFportRange> portlist;
	
	/**
	 * Zero or one. Additional information about the protocol being used. The intent of the protocol field is to carry
	 * additional information related to the protocol being used when the {@link Service} attributes
	 * iana_protocol_number or/and iana_protocol_name are filed.
	 */
	private String protocol;
}
