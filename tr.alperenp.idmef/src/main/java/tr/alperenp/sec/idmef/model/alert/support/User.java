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

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import tr.alperenp.sec.idmef.model.misc.IenumIDMEF;

/**
 * The User class is used to describe users. It is primarily used as a "container" class for the UserId aggregate class
 * 
 * @author alperenp
 *
 */
@Data
public class User {
	/**
	 * Optional. A unique identifier for the user
	 */
	private String ident;
	
	/**
	 * Optional. The type of user represented. The permitted values for this attribute are shown below. The default
	 * value is "unknown".
	 */
	private Category category;
	
	/**
	 * One or more. Identification of a user, as indicated by its type attribute
	 */
	private List<UserId> userIds;
	
	@AllArgsConstructor
	public enum Category implements IenumIDMEF {
		/**
		 * User type unknown
		 */
		UNKNOWN(0) {
			public String getKeyword() {
				return "unknown";
			}
		},
		
		/**
		 * An application user
		 */
		APPLICATION(1) {
			public String getKeyword() {
				return "application";
			}
		},
		
		/**
		 * An operating system or device user
		 */
		OSDEVICE(2) {
			public String getKeyword() {
				return "os-device";
			}
		};
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
}
