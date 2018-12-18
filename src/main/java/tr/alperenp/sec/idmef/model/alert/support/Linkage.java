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
 * The Linkage class represents file system connections between the file described in the {@link File} element and other
 * objects in the file system. For example, if the {@link File} element is a symbolic link or shortcut, then the
 * {@link Linkage} element should contain the name of the object the link points to. Further information can be provided
 * about the object in the {@link Linkage} element with another {@link File} element, if appropriate.
 * 
 * @author alperenp
 *
 */
@Data
public class Linkage {
	/**
	 * Exactly one. The name of the file system object, not including the path.
	 */
	private String name;
	
	/**
	 * Exactly one. The full path to the file system object, including the name. The path name should be represented in
	 * as "universal" a manner as possible, to facilitate processing of the alert.
	 */
	private String path;
	
	/**
	 * Exactly one. A {@link File} element may be used in place of the {@linkplain name} and {@linkplain path} elements
	 * if additional information about the file is to be included.
	 */
	private File file;
	
	/**
	 * The type of object that the link describes. The permitted values are shown below. There is no default value.
	 */
	private Category category;
	
	@AllArgsConstructor
	public enum Category implements IenumIDMEF {
		/**
		 * The {@linkplain name} element represents another name for this file. This information may be more easily
		 * obtainable on NTFS file systems than others.
		 */
		HARDLINK(0) {
			public String getKeyword() {
				return "hard-link";
			}
		},
		
		/**
		 * An alias for the directory specified by the parent's {@linkplain name} and {@linkplain path} elements.
		 */
		MOUNTPOINT(1) {
			public String getKeyword() {
				return "mount-point";
			}
		},
		
		/**
		 * Applies only to Windows; excludes symbolic links and mount points, which are specific types of reparse
		 * points.
		 */
		REPARSEPOINT(2) {
			public String getKeyword() {
				return "reparse-point";
			}
		},
		
		/**
		 * The file represented by a Windows "shortcut". A shortcut is distinguished from a symbolic link because of the
		 * difference in their contents, which may be of importance to the manager.
		 */
		SHORTCUT(3) {
			public String getKeyword() {
				return "shortcut";
			}
		},
		
		/**
		 * An Alternate Data Stream (ADS) in Windows; a fork on MacOS. Separate file system entity that is considered an
		 * extension of the main {@link File}.
		 */
		STREAM(4) {
			public String getKeyword() {
				return "stream";
			}
		},
		
		/**
		 * The {@linkplain name} element represents the file to which the link points.
		 */
		SYMBOLICLINK(5) {
			public String getKeyword() {
				return "symbolic-link";
			}
		};
		
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
}
