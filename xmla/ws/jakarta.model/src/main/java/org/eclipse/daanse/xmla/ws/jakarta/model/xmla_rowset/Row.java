package org.eclipse.daanse.xmla.ws.jakarta.model.xmla_rowset;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType()
public class Row {
    
    @XmlAnyElement
    List<Object> value;

}
