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

package tr.alperenp.sec.idmef.model.idmefmessage;

import lombok.Data;

/**
 * All IDMEF messages are instances of the IDMEF-Message class; it is the top-level class of the IDMEF data model, as
 * well as the IDMEF DTD. There are currently two types (subclasses) of IDMEF-Message: Alert and Heartbeat.
 * 
 * The IDMEF-Message class has a single attribute: version
 * 
 * @author alperenp
 *
 */
@Data
public abstract class IDMEFMessage {
	
	/**
	 * The version of the IDMEF-Message specification this message conforms to. Applications specifying a value for this
	 * attribute MUST specify the value "1.0".
	 */
	public String version = "1.0";
}
