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

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The SNMPService class carries additional information related to SNMP traffic. The aggregate classes composing
 * SNMPService must be interpreted as described in RFC 3411 and RFC 3584.
 * 
 * @author alperenp
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SNMPService extends Service {
	
	public SNMPService(Service service) {
		super(service);
	}
	
	/**
	 * Zero or one. The object identifier in the request
	 */
	private String oid;
	
	/**
	 * Zero or one. The SNMP version, typically 0 for SNMPv1, 1 for SNMPv2c, 2 for SNMPv2u and SNMPv2*, and 3 for
	 * SNMPv3; see RFC 3411
	 * <p>
	 * set to -1 if not defined
	 */
	private int messageProcessingModel = -1;
	
	/**
	 * Zero or one. The identification of the security model in use, typically 0 for any, 1 for SNMPv1, 2 for SNMPv2c,
	 * and 3 for USM; see RFC 3411
	 * <p>
	 * set to -1 if not defined
	 */
	private int securityModel;
	
	/**
	 * Zero or one. The object’s security name; see RFC 3411
	 */
	private String securityName;
	
	/**
	 * Zero or one. The security level of the SNMP request; see RFC 3411
	 * <p>
	 * set to -1 if not defined
	 */
	private int securityLevel = -1;
	
	/**
	 * Zero or one. The object’s context name; see RFC 3411
	 */
	private String contextName;
	
	/**
	 * Zero or one. The object’s context engine identifier; see RFC 3411.
	 */
	private String contextEngineID;
	
	/**
	 * Zero or one. The command sent to the SNMP server (GET, SET, etc.).
	 */
	private String command;
}
