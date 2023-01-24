package org.eclipse.daanse.xmla.model.jaxb.util;

import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class ElementToEntryAdapter extends XmlAdapter<Element, Entry<String, String>> {

    @Override
    public Element marshal(Entry<String, String> entry) throws Exception {
        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();

        Element element = document.createElement(entry.getKey());
        element.setTextContent(entry.getValue());

        return element;
    }

    @Override
    public Entry<String, String> unmarshal(Element element) {
        String tagName = element.getTagName();
        String elementValue = element.getChildNodes()
                .item(0)
                .getNodeValue();
        return Map.entry(tagName, elementValue);

    }
}