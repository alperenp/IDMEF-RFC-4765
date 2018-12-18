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
 * The Confidence class is used to represent the analyzer's best estimate of the validity of its analysis.
 * 
 * @author alperenp
 *
 */
@Data
public class Confidence {
	
	public Confidence(String ratingStr) {
		rating = Rating.valueOf(ratingStr);
	}
	
	/**
	 * The analyzer's rating of its analytical validity. The permitted values are shown below. The default value is
	 * "numeric".
	 */
	private Rating rating = Rating.NUMERIC;
	
	@AllArgsConstructor
	public enum Rating implements IenumIDMEF {
		/**
		 * The analyzer has little confidence in its validity
		 */
		LOW(0) {
			public String getKeyword() {
				return "low";
			}
		},
		
		/**
		 * The analyzer has average confidence in its validity
		 */
		MEDIUM(1) {
			public String getKeyword() {
				return "medium";
			}
		},
		
		/**
		 * The analyzer has high confidence in its validity
		 */
		HIGH(2) {
			public String getKeyword() {
				return "high";
			}
		},
		
		/**
		 * The analyzer has provided a posterior probability value indicating its confidence in its validity
		 */
		NUMERIC(3) {
			public String getKeyword() {
				return "numeric";
			}
		};
		@Getter private int value;
		
		@Override
		public abstract String getKeyword();
	}
}
