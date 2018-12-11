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

package tr.alperenp.sec.idmef.model.misc;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 3.2.7 NTP timestamp is a 64-bit unsigned fixed-point number. The integer part is in the first 32 bits, and the
 * fraction part is in the last 32 bits.
 * 
 * @author alperenp
 */
@EqualsAndHashCode
public class NTPStamp {
	@Getter String ntpstamp;
	
	public NTPStamp(String stamp) {
		String[] values = stamp.split("\\.");
		if (validate32bitHex(values[0]) && validate32bitHex(values[1]) && values.length == 2) {
			this.ntpstamp = stamp;
		}
	}
	
	private boolean validate32bitHex(String hexString) {
		// escape "0x" and convert hex into number
		Long.parseLong(hexString.substring(2), 16);
		if (hexString.length() == 10) {
			return true;
		}
		return false;
	}
	
}
