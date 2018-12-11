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
 * The Reference class provides the "name" of an alert, or other information allowing the manager to determine what it
 * is.
 * 
 * @author alperenp
 *
 */
@Data
public class Reference {
	/**
	 * Exactly one. The name of the alert, from one of the origins listed below.
	 */
	private String name;
	
	/**
	 * Exactly one. A URL at which the manager (or the human operator of the manager) can find additional information
	 * about the alert. The document pointed to by the URL may include an in-depth description of the attack,
	 * appropriate countermeasures, or other information deemed relevant by the vendor.
	 */
	private String url;
	
	/**
	 * Required. The source from which the name of the alert originates. The permitted values for this attribute are
	 * shown below. The default value is "unknown".
	 */
	private Origin origin = Origin.UNKNOWN;
	
	/**
	 * Optional. The meaning of the reference, as understood by the alert provider. This field is only valid if the
	 * value of the <origin> attribute is set to "vendor-specific" or "user-specific".
	 */
	private String meaning;
	
	/**
	 * Required. The source from which the name of the alert originates. The permitted values for this attribute are
	 * shown below. The default value is "unknown".
	 */
	@AllArgsConstructor
	public enum Origin implements IenumIDMEF {
		/**
		 * Origin of the name is not known
		 */
		UNKNOWN(0) {
			public String getKeyword() {
				return "unknown";
			}
		},
		
		/**
		 * A vendor-specific name (and hence, URL); this can be used to provide product-specific information
		 */
		VENDORSPECIFIC(1) {
			public String getKeyword() {
				return "vendor-specific";
			}
		},
		
		/**
		 * A user-specific name (and hence, URL); this can be used to provide installation-specific information
		 */
		USERSPECIFIC(2) {
			public String getKeyword() {
				return "user-specific";
			}
		},
		
		/**
		 * The SecurityFocus ("Bugtraq") vulnerability database identifier (http://www.securityfocus.com/bid)
		 */
		BUGTRAQID(3) {
			public String getKeyword() {
				return "bugtraqid";
			}
		},
		
		/**
		 * The Common Vulnerabilities and Exposures (CVE) name (http://www.cve.mitre.org/)
		 */
		CVE(4) {
			public String getKeyword() {
				return "cve";
			}
		},
		
		/**
		 * The Open Source Vulnerability Database (http://www.osvdb.org)
		 */
		OSVDB(5) {
			public String getKeyword() {
				return "osvdb";
			}
		};
		
		@Getter private int value = 0;
		
		@Override
		public abstract String getKeyword();
	}
}
