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

package tr.alperenp.sec.idmef.model.alert;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The OverflowAlert carries additional information related to buffer overflow attacks. It is intended to enable an
 * analyzer to provide the details of the overflow attack itself.
 * 
 * @author alperenp
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OverflowAlert extends Alert {
	
	public OverflowAlert(Alert alert) {
		super(alert);
	}
	
	/**
	 * Exactly one. The program that the overflow attack attempted to run (NOTE: this is not the program that was
	 * attacked).
	 */
	private String program;
	
	/**
	 * Zero or one. The size, in bytes, of the overflow (i.e., the number of bytes the attacker sent).
	 */
	private BigInteger size;
	
	/**
	 * Zero or one. Some or all of the overflow data itself (dependent on how much the analyzer can capture).
	 */
	private List<Byte> buffer;
}
