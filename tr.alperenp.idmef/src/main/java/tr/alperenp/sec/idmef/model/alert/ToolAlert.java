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
 * The ToolAlert class carries additional information related to the use of attack tools or malevolent programs such as
 * Trojan horses and can be used by the analyzer when it is able to identify these tools. It is intended to group one or
 * more previously-sent alerts together, to say "these alerts were all the result of someone using this tool".
 * 
 * @author alperenp
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ToolAlert extends Alert {
	
	public ToolAlert(Alert alert) {
		super(alert);
	}
	
	private String analyzerid;
	
	/**
	 * Exactly one. The reason for grouping the alerts together, for example, the name of a particular tool.
	 */
	private String name;
	
	/**
	 * Zero or one. The command or operation that the tool was asked to perform, for example, a BackOrifice ping.
	 */
	private String command;
	
	/**
	 * One or more. The list of alert identifiers that are related to this alert. Because alert identifiers are only
	 * unique across the alerts sent by a single analyzer, the optional "analyzerid" attribute of "alertident" should be
	 * used to identify the analyzer that a particular alert came from. If the "analyzerid" is not provided, the alert
	 * is assumed to have come from the same analyzer that is sending the ToolAlert.
	 */
	private List<AlertIdent> alertidents;
}
