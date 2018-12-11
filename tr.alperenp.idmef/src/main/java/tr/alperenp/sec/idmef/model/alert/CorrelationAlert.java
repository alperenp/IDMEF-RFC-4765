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
import tr.alperenp.sec.idmef.model.misc.AlertIdent;

/**
 * The CorrelationAlert class carries additional information related to the correlation of alert information. It is
 * intended to group one or more previously-sent alerts together, to say "these alerts are all related".
 * 
 * @author alperenp
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CorrelationAlert extends Alert {
	
	public CorrelationAlert(Alert alert) {
		super(alert);
	}
	
	/**
	 * Exactly one. The reason for grouping the alerts together, for example, a particular correlation method.
	 */
	private String name;
	
	/**
	 * One or more. The list of alert identifiers that are related to this alert. Because alert identifiers are only
	 * unique across the alerts sent by a single analyzer, the optional "analyzerid" attribute of "alertident" should be
	 * used to identify the analyzer that a particular alert came from. If the "analyzerid" is not provided, the alert
	 * is assumed to have come from the same analyzer that is sending the CorrelationAlert.
	 */
	private List<AlertIdent> alertidents;
	
}
