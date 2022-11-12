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
