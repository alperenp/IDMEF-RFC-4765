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

package tr.alperenp.sec.idmef.xml.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author alperenp
 *
 */
public class IDMEFXmlTestUtils {
	
	private static final String PROPERTIESFILE = "IDMEF_elementNames.properties";
	
	public Properties readProperties() throws IOException {
		Properties prop = new Properties();
		InputStream inputstream = getClass().getClassLoader().getResourceAsStream(PROPERTIESFILE);
		if (inputstream != null) {
			prop.load(inputstream);
		}
		return prop;
	}
}
