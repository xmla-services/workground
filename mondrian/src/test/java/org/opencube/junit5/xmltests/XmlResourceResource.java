/*********************************************************************
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * History:
 *  This files came from the mondrian project. Some of the Flies
 *  (mostly the Tests) did not have License Header.
 *  But the Project is EPL Header. 2002-2022 Hitachi Vantara.
 *
 * Contributors:
 *   Hitachi Vantara.
 *   SmartCity Jena - initial  Java 8, Junit5
 **********************************************************************/
package org.opencube.junit5.xmltests;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmlResourceResource {
	@XmlAttribute
	public String name;

//   (()) @XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlValue
	public String content;

	class AdaptorCDATA extends XmlAdapter<String, String> {

		@Override
		public String marshal(String arg0) throws Exception {
			return "<![CDATA[" + arg0 + "]]>";
		}

		@Override
		public String unmarshal(String arg0) throws Exception {
			return arg0;
		}
	}
}
