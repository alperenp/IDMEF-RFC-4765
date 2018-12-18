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

import java.util.List;

import lombok.Data;

/**
 * The Process class is used to describe processes being executed on sources, targets, and analyzers.
 * 
 * @author alperenp
 *
 */
@Data
public class Process {
	/**
	 * Optional. A unique identifier for the process
	 */
	String ident;
	
	/**
	 * Exactly one. The name of the program being executed. This is a short name; path and argument information are
	 * provided elsewhere.
	 */
	String name;
	
	/**
	 * Zero or one. The process identifier of the process.
	 * <p>
	 * set to -1 if not defined
	 */
	int pid = -1;
	
	/**
	 * Zero or one. The full path of the program being executed.
	 */
	String path;
	
	/**
	 * Zero or more. A command-line argument to the program. Multiple arguments may be specified (they are assumed to
	 * have occurred in the same order they are provided) with multiple uses of arg.
	 */
	List<String> args;
	
	/**
	 * Zero or more. An environment string associated with the process; generally of the format "VARIABLE=value".
	 * Multiple environment strings may be specified with multiple uses of env.
	 */
	List<String> envs;
}
