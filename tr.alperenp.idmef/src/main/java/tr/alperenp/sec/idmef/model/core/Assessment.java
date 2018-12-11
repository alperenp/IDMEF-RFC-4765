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

package tr.alperenp.sec.idmef.model.core;

import java.util.List;

import lombok.Data;
import tr.alperenp.sec.idmef.model.assessment.Action;
import tr.alperenp.sec.idmef.model.assessment.Confidence;
import tr.alperenp.sec.idmef.model.assessment.Impact;

/**
 * The Assessment class is used to provide the analyzer's assessment of an event -- its impact, actions taken in
 * response, and confidence.
 * 
 * @author alperenp
 *
 */
@Data
public class Assessment {
	/**
	 * Zero or one. The analyzer's assessment of the impact of the event on the target(s).
	 */
	private Impact impact;
	
	/**
	 * Zero or more. The action(s) taken by the analyzer in response to the event.
	 */
	private List<Action> actions;
	
	/**
	 * Zero or one. A measurement of the confidence the analyzer has in its evaluation of the event.
	 */
	private Confidence confidence;
}
