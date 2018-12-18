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
 * The UserId class provides specific information about a user. More than one UserId can be used within the User class
 * to indicate attempts to transition from one user to another, or to provide complete information about a user's (or
 * process') privileges.
 * 
 * @author alperenp
 *
 */
@Data
public class UserId {
	/**
	 * Optional. A unique identifier for the user id
	 */
	private String ident;
	
	/**
	 * Optional. The type of user information represented. The permitted values for this attribute are shown below. The
	 * default value is "original-user".
	 */
	private Type type = Type.ORIGINALUSER;
	
	/**
	 * Optional. The tty the user is using.
	 */
	private String tty;
	
	/**
	 * Zero or one. A user or group name.
	 */
	private String name;
	
	/**
	 * Zero or one. A user or group number.
	 * <p>
	 * Warning: default value is -1
	 */
	private int number = -1;
	
	@AllArgsConstructor
	public enum Type implements IenumIDMEF {
		/**
		 * The current user id being used by the user or process. On Unix systems, this would be the "real" user id, in
		 * general.
		 */
		CURRENTUSER(0) {
			public String getKeyword() {
				return "current-user";
			}
		},
		
		/**
		 * The actual identity of the user or process being reported on. On those systems that (a) do some type of
		 * auditing and (b) support extracting a user id from the "audit id" token, that value should be used. On those
		 * systems that do not support this, and where the user has logged into the system, the "login id" should be
		 * used.
		 */
		ORIGINALUSER(1) {
			public String getKeyword() {
				return "original-user";
			}
		},
		
		/**
		 * The user id the user or process is attempting to become. This would apply, on Unix systems for example, when
		 * the user attempts to use "su", "rlogin", "telnet", etc.
		 */
		TARGETUSER(2) {
			public String getKeyword() {
				return "target-user";
			}
		},
		
		/**
		 * Another user id the user or process has the ability to use, or a user id associated with a file permission.
		 * On Unix systems, this would be the "effective" user id in a user or process context, and the owner
		 * permissions in a file context. Multiple UserId elements of this type may be used to specify a list of
		 * privileges.
		 */
		USERPRIVS(3) {
			public String getKeyword() {
				return "user-privs";
			}
		},
		
		/**
		 * The current group id (if applicable) being used by the user or process. On Unix systems, this would be the
		 * "real" group id, in general.
		 */
		CURRENTGROUP(4) {
			public String getKeyword() {
				return "current-group";
			}
		},
		
		/**
		 * Another group id the group or process has the ability to use, or a group id associated with a file
		 * permission. On Unix systems, this would be the "effective" group id in a group or process context, and the
		 * group permissions in a file context. On BSD-derived Unix systems, multiple UserId elements of this type would
		 * be used to include all the group ids on the "group list".
		 */
		GROUPPRIVS(5) {
			public String getKeyword() {
				return "group-privs";
			}
		},
		
		/**
		 * Not used in a user, group, or process context, only used in the file context. The file permissions assigned
		 * to users who do not match either the user or group permissions on the file. On Unix systems, this would be
		 * the "world" permissions.
		 */
		OTHERPRIVS(6) {
			public String getKeyword() {
				return "other-privs";
			}
		};
		
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
}
