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
 * The Action class is used to describe any actions taken by the analyzer in response to the event.
 * 
 * @author alperenp
 *
 */
@Data
public class Action {
	
	/**
	 * Default action category
	 */
	public Action() {
		category = Category.OTHER;
	}
	
	/**
	 * Action with given category
	 * 
	 * @param categoryStr value of {@link Action}
	 */
	public Action(String categoryStr) {
		category = Category.valueOf(categoryStr);
	}
	
	/**
	 * The element itself may be empty, or may contain a textual description of the action, if the analyzer is able to
	 * provide additional details.
	 */
	private String description;
	
	/**
	 * The type of action taken. The permitted values are shown below. The default value is "other".
	 */
	Category category = Category.OTHER;
	
	@AllArgsConstructor
	public enum Category implements IenumIDMEF {
		/**
		 * A block of some sort was installed to prevent an attack from reaching its destination. The block could be a
		 * port block, address block, etc., or disabling a user account.
		 */
		BLOCKINSTALLED(0) {
			public String getKeyword() {
				return "block-installed";
			}
		},
		
		/**
		 * A notification message of some sort was sent out-of-band (via pager, e-mail, etc.). Does not include the
		 * transmission of this alert.
		 */
		NOTIFICATIONSENT(1) {
			public String getKeyword() {
				return "notification-sent";
			}
		},
		
		/**
		 * A system, computer, or user was taken offline, as when the computer is shut down or a user is logged off.
		 */
		TAKENOFFLINE(2) {
			public String getKeyword() {
				return "taken-offline";
			}
		},
		
		/**
		 * Anything not in one of the above categories.
		 */
		OTHER(3) {
			public String getKeyword() {
				return "other";
			}
		};
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
}
