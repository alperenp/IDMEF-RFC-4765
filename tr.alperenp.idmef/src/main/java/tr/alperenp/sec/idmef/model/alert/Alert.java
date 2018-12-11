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

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tr.alperenp.sec.idmef.model.core.AdditionalData;
import tr.alperenp.sec.idmef.model.core.Analyzer;
import tr.alperenp.sec.idmef.model.core.Assessment;
import tr.alperenp.sec.idmef.model.core.Classification;
import tr.alperenp.sec.idmef.model.core.Source;
import tr.alperenp.sec.idmef.model.core.Target;
import tr.alperenp.sec.idmef.model.idmefmessage.IDMEFMessage;
import tr.alperenp.sec.idmef.model.misc.IDMEFTime;

/**
 * Generally, every time an analyzer detects an event that it has been configured to look for, it sends an {@link Alert}
 * message to its manager(s). Depending on the analyzer, an {@link Alert} message may correspond to a single detected
 * event or multiple detected events. Alerts occur asynchronously in response to outside events.
 * 
 * @author alperenp
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Alert extends IDMEFMessage {
	
	public Alert(Alert alert) {
		setAdditionalDatas(alert.getAdditionalDatas());
		setAnalyzer(alert.getAnalyzer());
		setAnalyzerTime(alert.getAnalyzerTime());
		setAssessment(alert.getAssessment());
		setClassification(alert.getClassification());
		setCreateTime(alert.getCreateTime());
		setDetectTime(alert.getDetectTime());
		setMessageid(alert.getMessageid());
		setSources(alert.getSources());
		setTargets(alert.getTargets());
		setVersion(alert.getVersion());
	}
	
	/**
	 * Optional. A unique identifier for the alert
	 */
	private String messageid;
	
	/**
	 * Exactly one. Identification information for the analyzer that originated the alert.
	 */
	private Analyzer analyzer;
	
	/**
	 * Exactly one. The time the alert was created. Of the three times that may be provided with an Alert, this is the
	 * only one that is required.
	 */
	private IDMEFTime createTime;
	
	/**
	 * Exactly one. The "name" of the alert, or other information allowing the manager to determine what it is.
	 */
	private Classification classification;
	
	/**
	 * Zero or one. The time the event(s) leading up to the alert was detected. In the case of more than one event, the
	 * time the first event was detected. In some circumstances, this may not be the same value as CreateTime.
	 */
	private IDMEFTime detectTime;
	
	/**
	 * Zero or one. The current time on the analyzer
	 */
	private IDMEFTime analyzerTime;
	
	/**
	 * Zero or more. The source(s) of the event(s) leading up to the alert.
	 */
	private List<Source> sources;
	
	/**
	 * Zero or more. The target(s) of the event(s) leading up to the alert.
	 */
	private List<Target> targets;
	
	/**
	 * Zero or one. Information about the impact of the event, actions taken by the analyzer in response to it, and the
	 * analyzer's confidence in its evaluation.
	 */
	private Assessment assessment;
	
	/**
	 * Zero or more. Information included by the analyzer that does not fit into the data model. This may be an atomic
	 * piece of data, or a large amount of data provided through an extension to the IDMEF
	 */
	private List<AdditionalData> additionalDatas;
}
