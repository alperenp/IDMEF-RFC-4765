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

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import tr.alperenp.sec.idmef.model.misc.IenumIDMEF;

/**
 * The AdditionalData class is used to provide information that cannot be represented by the data model.
 * {@link AdditionalData} can be used to provide atomic data (integers, strings, etc.) in cases where only small amounts
 * of additional information need to be sent; it can also be used to extend the data model and the DTD to support the
 * transmission of complex data (such as packet headers).
 * 
 * @author alperenp
 *
 */
@Data
public class AdditionalData {
	/**
	 * Optional. A string describing the meaning of the element content. These values will be vendor/implementation
	 * dependent; the method for ensuring that managers understand the strings sent by analyzers is outside the scope of
	 * this specification. A list of acceptable meaning keywords is not within the scope of the document, although later
	 * versions may undertake to establish such a list.
	 */
	String meaning;
	
	/**
	 * WARNING: This value does not exist in standard however, it is required to type, meaning and "values" of
	 * additional data
	 */
	List<String> values;
	
	Type dataType;
	
	@AllArgsConstructor
	public enum Type implements IenumIDMEF {
		/**
		 * The element contains a boolean value, i.e., the strings "true" or "false"
		 */
		BOOLEAN(0) {
			public String getKeyword() {
				return "boolean";
			}
		},
		
		/**
		 * The element content is a single 8-bit byte
		 */
		BYTE(1) {
			public String getKeyword() {
				return "byte";
			}
		},
		
		/**
		 * The element content is a single character
		 */
		CHARACTER(2) {
			public String getKeyword() {
				return "character";
			}
		},
		
		/**
		 * The element content is a date-time string
		 */
		DATETIME(3) {
			public String getKeyword() {
				return "date-time";
			}
		},
		
		/**
		 * The element content is an integer
		 */
		INTEGER(4) {
			public String getKeyword() {
				return "integer";
			}
		},
		
		/**
		 * The element content is an NTP timestamp
		 */
		NTPSTAMP(5) {
			public String getKeyword() {
				return "ntpstamp";
			}
		},
		
		/**
		 * The element content is a list of ports
		 */
		PORTLIST(6) {
			public String getKeyword() {
				return "portlist";
			}
		},
		
		/**
		 * The element content is a real number
		 */
		REAL(7) {
			public String getKeyword() {
				return "real";
			}
		},
		
		/**
		 * The element content is a string
		 */
		STRING(8) {
			public String getKeyword() {
				return "string";
			}
		},
		
		/**
		 * The element is a byte[]
		 */
		BYTESTRING(9) {
			public String getKeyword() {
				return "byte-string";
			}
		},
		
		/**
		 * The element content is XML-tagged data
		 */
		XMLTEXT(10) {
			public String getKeyword() {
				return "xmltext";
			}
		};
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
}
