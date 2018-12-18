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
 * The Checksum class represents checksum information associated with the file. This checksum information can be
 * provided by file integrity checkers, among others.
 * 
 * @author alperenp
 *
 */
@Data
public class Checksum {
	/**
	 * Exactly one. The value of the checksum.
	 */
	private String value;
	
	/**
	 * Zero or one. The key to the checksum, if appropriate.
	 */
	private String key;
	
	/**
	 * The cryptographic algorithm used for the computation of the checksum. There is no default value.
	 */
	private Algorithm algorithm;
	
	@AllArgsConstructor
	public enum Algorithm implements IenumIDMEF {
		/**
		 * The MD4 algorithm
		 */
		MD4(0) {
			public String getKeyword() {
				return "MD4";
			}
		},
		
		/**
		 * The MD5 algorithm.
		 */
		MD5(1) {
			public String getKeyword() {
				return "MD5";
			}
		},
		
		/**
		 * The SHA1 algorithm.
		 */
		SHA1(2) {
			public String getKeyword() {
				return "SHA1";
			}
		},
		
		/**
		 * The SHA2 algorithm with 256 bits length.
		 */
		SHA2256(3) {
			public String getKeyword() {
				return "SHA2-256";
			}
		},
		
		/**
		 * The SHA2 algorithm with 384 bits length.
		 */
		SHA2384(4) {
			public String getKeyword() {
				return "SHA2-384";
			}
		},
		
		/**
		 * The SHA2 algorithm with 512 bits length.
		 */
		SHA2512(5) {
			public String getKeyword() {
				return "SHA2-512";
			}
		},
		
		/**
		 * The CRC algorithm with 32 bits length.
		 */
		CRC32(6) {
			public String getKeyword() {
				return "CRC-32";
			}
		},
		
		/**
		 * The Haval algorithm.
		 */
		HAVAL(7) {
			public String getKeyword() {
				return "Haval";
			}
		},
		
		/**
		 * The Tiger algorithm.
		 */
		TIGER(8) {
			public String getKeyword() {
				return "Tiger";
			}
		},
		
		/**
		 * The Gost algorithm.
		 */
		GOST(9) {
			public String getKeyword() {
				return "Gost";
			}
		};
		
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
}
