/*
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
 */
package org.opencube.junit5.xmltests;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmlResourceTestCase implements ResourceTestCase {
	@XmlAttribute
	public String name;
	@XmlElement(name = "Resource")
	public List<XmlResourceResource> resources;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue(String key) {
		return resources.stream().filter(r -> r.name.equals(key)).map(r -> r.content).findFirst().orElse(null);
	}

	@Override
	public String toString() {
		return name;
	}

}
