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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * Time class defined in Section 3.2.6 (Date-Time Strings).
 * 
 * @author alperenp
 *
 */
@Data
public class IDMEFTime {
	
	/**
	 * UTC time in miliseconds
	 */
	long UTCtimeInMilis;
	
	/**
	 * WARNING: this attribute does not exist in rfc documentation
	 * <p>
	 * Optional. If time is not in UTC, operator defines whether it is "+" or "-"
	 */
	@AllArgsConstructor
	public enum Operator {
		
		PLUS("+"),
		
		MINUS("-");
		
		@Getter private String value;
	}
	
	/**
	 * WARNING: this attribute does not exist in rfc documentation
	 * <p>
	 * Optional. If time is not in UTC, operator defines whether it is "+" or "-"
	 */
	Operator operator;
	
	/**
	 * WARNING: this attribute does not exist in rfc documentation
	 * <p>
	 * If given time is not in UTC, this value is used to determine the difference in time
	 */
	long adjustedTime = 0L;
	
	/**
	 * 3.2.7 NTP timestamp is a 64-bit unsigned fixed-point number. The integer part is in the first 32 bits, and the
	 * fraction part is in the last 32 bits.
	 */
	NTPStamp ntpstamp;
}
