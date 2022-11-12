package org.opencube.junit5.xmltests;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Root")
public class XmlResourceRoot {

	@XmlElement(name = "TestCase")
	public List<XmlResourceTestCase> testCase;
}
