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
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception;

import java.io.Serializable;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Messages", propOrder = {"warningOrError"})
public class Messages implements Serializable {

    private final static long serialVersionUID = 1L;
    @XmlElements({@XmlElement(name = "Warning", type = WarningType.class),
        @XmlElement(name = "Error", type = ErrorType.class)})
    protected List<Serializable> warningOrError;

    public List<Serializable> getWarningOrError() {
        return this.warningOrError;
    }

    public void setWarningOrError(List<Serializable> warningOrError) {
        this.warningOrError = warningOrError;
    }
}
