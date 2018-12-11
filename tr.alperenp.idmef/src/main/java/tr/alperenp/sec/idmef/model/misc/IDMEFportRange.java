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

/**
 * This class is created to represent port ranges defined in RFC 4765 (IDMEF)
 * <p>
 * Warning: This class does not part of original RFC 4765 documentation
 * 
 * @author alperenp
 *
 */
@EqualsAndHashCode
public class IDMEFportRange {
	
	/**
	 * 
	 * @param singlePortNum
	 */
	public IDMEFportRange(int singlePortNum) {
		min = singlePortNum;
		max = singlePortNum;
	}
	
	/**
	 * 
	 * @param minPortNum
	 * @param maxPortNum
	 */
	public IDMEFportRange(int minPortNum, int maxPortNum) {
		if (!(minPortNum < maxPortNum)) {
			throw new IllegalArgumentException("min value of port range does not less than max");
		}
		
		this.min = minPortNum;
		this.max = maxPortNum;
	}
	
	/**
	 * min port value
	 */
	private int min = -1;
	
	/**
	 * max port value
	 */
	private int max = -1;
	
	@Override
	public String toString() {
		
		if (min > max) {
			throw new IllegalStateException("max value is less than min value!");
		}
		
		StringBuilder builder = new StringBuilder();
		if (min == -1 && max == -1) {
			return builder.toString();
		}
		
		if (min < max) {
			builder.append(min).append("-").append(max);
		} else if (min == max) {
			builder.append(min);
		}
		return builder.toString();
	}
}
