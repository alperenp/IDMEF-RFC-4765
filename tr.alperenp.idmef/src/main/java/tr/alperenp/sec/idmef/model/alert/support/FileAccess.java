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
 * The FileAccess class represents the access permissions on a file. The representation is intended to be useful across
 * operating systems.
 * 
 * @author alperenp
 *
 */
@Data
public class FileAccess {
	/**
	 * Exactly one. The user (or group) to which these permissions apply. The value of the "type" attribute must be
	 * "user-privs", "group-privs", or "other-privs" as appropriate. Other values for "type" MUST NOT be used in this
	 * context.
	 */
	private UserId userId;
	
	/**
	 * One or more. Level of access allowed. The permitted values are shown below. There is no default value.
	 */
	private List<Permission> permissions;
	
	/**
	 * The "changePermissions" and "takeOwnership" strings represent those concepts in Windows. On Unix, the owner of
	 * the file always has "changePermissions" access, even if no other access is allowed for that user. "Full Control"
	 * in Windows is represented by enumerating the permissions it contains. The "executeAs" string represents the
	 * set-user-id and set-group-id features in Unix.
	 * 
	 * @author alperenp
	 *
	 */
	@AllArgsConstructor
	public enum Permission implements IenumIDMEF {
		/**
		 * No access at all is allowed for this user
		 */
		NOACCESS(0) {
			public String getKeyword() {
				return "noAccess";
			}
		},
		
		/**
		 * This user has read access to the file
		 */
		READ(1) {
			public String getKeyword() {
				return "read";
			}
		},
		
		/**
		 * This user has write access to the file
		 */
		WRITE(2) {
			public String getKeyword() {
				return "write";
			}
		},
		
		/**
		 * This user has the ability to execute the file
		 */
		EXECUTE(3) {
			public String getKeyword() {
				return "execute";
			}
		},
		
		/**
		 * This user has the ability to search this file (applies to "execute" permission on directories in Unix)
		 */
		SEARCH(4) {
			public String getKeyword() {
				return "search";
			}
		},
		
		/**
		 * This user has the ability to delete this file
		 */
		DELETE(5) {
			public String getKeyword() {
				return "delete";
			}
		},
		
		/**
		 * This user has the ability to execute this file as another user
		 */
		EXECUTEAS(6) {
			public String getKeyword() {
				return "executeAs";
			}
		},
		
		/**
		 * This user has the ability to change the access permissions on this file
		 */
		CHANGEPERMISSIONS(7) {
			public String getKeyword() {
				return "changePermissions";
			}
		},
		
		/**
		 * This user has the ability to take ownership of this file
		 */
		TAKEOWNERSHIP(8) {
			public String getKeyword() {
				return "takeOwnership";
			}
		};
		
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
}
