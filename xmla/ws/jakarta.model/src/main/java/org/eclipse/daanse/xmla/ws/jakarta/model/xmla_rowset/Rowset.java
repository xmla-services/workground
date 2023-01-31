/*
* Copyright (c) 2023 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla_rowset;

import java.io.Serializable;
import java.util.List;

import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_exception.Exception;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_exception.Messages;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rowset", propOrder = { "row", "exception", "messages" })
public class Rowset implements Serializable {

    private final static long serialVersionUID = 1L;
    protected List<Row> row;
    @XmlElement(name = "Exception")
    protected Exception exception;
    @XmlElement(name = "Messages")
    protected Messages messages;

    public List<Row> getRow() {
        return row;
    }

    public void setRow(List<Row> row) {
        this.row = row;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception value) {
        this.exception = value;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages value) {
        this.messages = value;
    }

}
