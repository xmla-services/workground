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
