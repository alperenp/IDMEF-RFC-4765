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

import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import tr.alperenp.sec.idmef.model.misc.IDMEFTime;
import tr.alperenp.sec.idmef.model.misc.IenumIDMEF;

/**
 * The File class provides specific information about a file or other file-like object that has been created, deleted,
 * or modified on the target. The description can provide either the file settings prior to the event or the file
 * settings at the time of the event, as specified using the "category" attribute.
 * <p>
 * Warning: FSTYPE did not implemented because given fields are not defined as strict usage and original example from
 * RFC alert (7.3.3. File Modification) contains fstype='tmpfs' which does not exist in documented file systems. Instead
 * it is extracted as a {@link String}
 * 
 * @author alperenp
 *
 */
@Data
public class File {
	
	/**
	 * Optional. A unique identifier for this file
	 */
	private String ident;
	
	/**
	 * Required. The context for the information being provided.
	 * <p>
	 * The permitted values are shown below. There is no default value.
	 */
	private Category category;
	
	/**
	 * Optional. The type of file system the file resides on.
	 * <p>
	 * This attribute governs how path names and other attributes are interpreted.
	 */
	private String fstype;
	
	/**
	 * Optional. The type of file, as a mime-type
	 */
	private String file_type;
	
	/**
	 * Exactly one. The name of the file to which the alert applies, not including the path to the file.
	 */
	private String name;
	
	/**
	 * Exactly one. The full path to the file, including the name. The path name should be represented in as "universal"
	 * a manner as possible, to facilitate processing of the alert.
	 * <p>
	 * For Windows systems, the path should be specified using the Universal Naming Convention (UNC) for remote files,
	 * and using a drive letter for local files (e.g., "C:\boot.ini"). For Unix systems, paths on network file systems
	 * should use the name of the mounted resource instead of the local mount point (e.g.,
	 * "fileserver:/usr/local/bin/foo"). The mount point can be provided using the <Linkage> element.
	 */
	private String path;
	
	/**
	 * Zero or one. Time the file was created. Note that this is *not* the Unix "st_ctime" file attribute (which is not
	 * file creation time). The Unix "st_ctime" attribute is contained in the "Inode" class.
	 */
	private IDMEFTime createTime;
	
	/**
	 * Zero or one. Time the file was last modified.
	 */
	private IDMEFTime modifyTime;
	
	/**
	 * Zero or one. Time the file was last accessed.
	 */
	private IDMEFTime accessTime;
	
	/**
	 * Zero or one. INTEGER. The size of the data, in bytes. Typically what is meant when referring to file size. On
	 * Unix UFS file systems, this value corresponds to stat.st_size. On Windows NTFS, this value corresponds to Valid
	 * Data Length (VDL).
	 */
	private BigInteger dataSize;
	
	/**
	 * Zero or one. INTEGER. The physical space on disk consumed by the file, in bytes. On Unix UFS file systems, this
	 * value corresponds to 512 * stat.st_blocks. On Windows NTFS, this value corresponds to End of File (EOF).
	 */
	private BigInteger diskSize;
	
	/**
	 * Zero or more. Access permissions on the file.
	 */
	private List<FileAccess> fileAccesses;
	
	/**
	 * Zero or more. File system objects to which this file is linked (other references for the file).
	 */
	private List<Linkage> linkages;
	
	/**
	 * Zero or one. Inode information for this file (relevant to Unix).
	 */
	private Inode inode;
	
	/**
	 * Zero or more. Checksum information for this file.
	 */
	private List<Checksum> checksums;
	
	@AllArgsConstructor
	public enum Category implements IenumIDMEF {
		/**
		 * The file information is from after the reported change
		 */
		CURRENT(0) {
			public String getKeyword() {
				return "current";
			}
		},
		
		/**
		 * The file information is from before the reported change
		 */
		ORIGINAL(1) {
			public String getKeyword() {
				return "original";
			}
		};
		
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
}
