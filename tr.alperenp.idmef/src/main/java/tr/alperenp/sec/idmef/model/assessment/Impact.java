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

package tr.alperenp.sec.idmef.model.assessment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import tr.alperenp.sec.idmef.model.misc.IenumIDMEF;

/**
 * The Impact class is used to provide the analyzer's assessment of the impact of the event on the target(s).
 * 
 * All three attributes are optional. The element itself may be empty, or may contain a textual description of the
 * impact, if the analyzer is able to provide additional details.
 * 
 * @author alperenp
 *
 */
@Data
public class Impact {
	
	/**
	 * An estimate of the relative severity of the event. The permitted values are shown below. There is no default
	 * value.
	 */
	Severity severity;
	
	/**
	 * An indication of whether the analyzer believes the attempt that the event describes was successful or not. The
	 * permitted values are shown below. There is no default value.
	 */
	Completion completion;
	
	/**
	 * The type of attempt represented by this event, in relatively broad categories. The permitted values are shown
	 * below. The default value is "other".
	 */
	Type type = Type.OTHER;
	
	@AllArgsConstructor
	public enum Severity implements IenumIDMEF {
		/**
		 * Alert represents informational activity
		 */
		INFO(0) {
			public String getKeyword() {
				return "info";
			}
		},
		
		/**
		 * Low severity
		 */
		LOW(1) {
			public String getKeyword() {
				return "low";
			}
		},
		
		/**
		 * Medium severity
		 */
		MEDIUM(2) {
			public String getKeyword() {
				return "medium";
			}
		},
		
		/**
		 * High severity
		 */
		HIGH(3) {
			public String getKeyword() {
				return "high";
			}
		};
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
	
	@AllArgsConstructor
	public enum Completion implements IenumIDMEF {
		/**
		 * The attempt was not successful
		 */
		FAILED(0) {
			public String getKeyword() {
				return "failed";
			}
		},
		
		/**
		 * The attempt succeeded
		 */
		SUCCEEDED(1) {
			public String getKeyword() {
				return "succeeded";
			}
		};
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
	
	@AllArgsConstructor
	public enum Type implements IenumIDMEF {
		/**
		 * Administrative privileges were attempted or obtained
		 */
		ADMIN(0) {
			public String getKeyword() {
				return "admin";
			}
		},
		
		/**
		 * A denial of service was attempted or completed
		 */
		DOS(1) {
			public String getKeyword() {
				return "dos";
			}
		},
		
		/**
		 * An action on a file was attempted or completed
		 */
		FILE(2) {
			public String getKeyword() {
				return "file";
			}
		},
		
		/**
		 * A reconnaissance probe was attempted or completed
		 */
		RECON(3) {
			public String getKeyword() {
				return "recon";
			}
		},
		
		/**
		 * User privileges were attempted or obtained
		 */
		USER(4) {
			public String getKeyword() {
				return "user";
			}
		},
		
		/**
		 * Anything not in one of the above categories
		 */
		OTHER(5) {
			public String getKeyword() {
				return "other";
			}
		};
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
}
