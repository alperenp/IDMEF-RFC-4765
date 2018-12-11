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

package tr.alperenp.sec.idmef.model.heartbeat;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tr.alperenp.sec.idmef.model.core.AdditionalData;
import tr.alperenp.sec.idmef.model.core.Analyzer;
import tr.alperenp.sec.idmef.model.idmefmessage.IDMEFMessage;
import tr.alperenp.sec.idmef.model.misc.IDMEFTime;

/**
 * Analyzers use Heartbeat messages to indicate their current status to managers. Heartbeats are intended to be sent in
 * a regular period, say, every ten minutes or every hour. The receipt of a Heartbeat message from an analyzer indicates
 * to the manager that the analyzer is up and running; lack of a Heartbeat message (or more likely, lack of some number
 * of consecutive Heartbeat messages) indicates that the analyzer or its network connection has failed.
 * 
 * All managers MUST support the receipt of Heartbeat messages; however, the use of these messages by analyzers is
 * OPTIONAL. Developers of manager software SHOULD permit the software to be configured on a per-analyzer basis to
 * use/not use Heartbeat messages.
 * 
 * @author alperenp
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Heartbeat extends IDMEFMessage {
	
	/**
	 * Optional. A unique identifier for the heartbeat
	 */
	private String messageid;
	
	/**
	 * Exactly one. Identification information for the analyzer that originated the heartbeat.
	 */
	private Analyzer analyzer;
	
	/**
	 * indicate the date and time the alert or heartbeat was created by the analyzer. Exactly one. The time the
	 * heartbeat was created.
	 */
	private IDMEFTime createTime;
	
	/**
	 * Zero or one. The current time on the analyzer.
	 */
	private IDMEFTime analyzerTime;
	
	/**
	 * Zero or one. The interval in seconds at which heartbeats are generated.
	 * <p>
	 * Warnging: default value is set to negative value since it cannot be negative
	 */
	private short heartbeatInterval = -1;
	
	/**
	 * Zero or more. Information included by the analyzer that does not fit into the data model. This may be an atomic
	 * piece of data or a large amount of data provided through an extension to the IDMEF
	 */
	private List<AdditionalData> additionalDatas;
	
}
