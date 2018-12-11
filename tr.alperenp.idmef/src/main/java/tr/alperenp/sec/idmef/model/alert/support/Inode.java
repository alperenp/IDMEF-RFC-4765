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

import lombok.Data;
import tr.alperenp.sec.idmef.model.misc.IDMEFTime;

/**
 * The Inode class is used to represent the additional information contained in a Unix file system i-node.
 * 
 * @author alperenp
 *
 */
@Data
public class Inode {
	/**
	 * Zero or one. The time of the last inode change, given by the st_ctime element of "struct stat".
	 */
	private IDMEFTime changeTime;
	
	/**
	 * Zero or one. The inode number.
	 */
	private int number;
	
	/**
	 * Zero or one. The major device number of the device the file resides on.
	 */
	private int majorDevice;
	
	/**
	 * Zero or one. The minor device number of the device the file resides on.
	 */
	private int minorDevice;
	
	/**
	 * Zero or one. The major device of the file itself, if it is a character special device.
	 */
	private int cMajorDevice;
	
	/**
	 * Zero or one. The minor device of the file itself, if it is a character special device.
	 */
	private int cMinorDevice;
}
