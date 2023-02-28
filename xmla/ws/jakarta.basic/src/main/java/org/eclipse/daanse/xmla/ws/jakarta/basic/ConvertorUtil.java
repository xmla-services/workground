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
package org.eclipse.daanse.xmla.ws.jakarta.basic;

import org.eclipse.daanse.xmla.api.exception.StartEnd;
import org.eclipse.daanse.xmla.api.exception.Type;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine200.WarningColumn;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine200.WarningLocationObject;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine200.WarningMeasure;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.ErrorType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.Exception;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.MessageLocation;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.Messages;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.WarningType;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertorUtil {

    public static Exception convertException(org.eclipse.daanse.xmla.api.exception.Exception exception) {
        if (exception != null) {
            Exception res = new Exception();
            return res;
        }
        return null;
    }

    public static Messages convertMessages(org.eclipse.daanse.xmla.api.exception.Messages messages) {
        if (messages != null) {
            Messages res = new Messages();
            res.setWarningOrError(convertWarningOrErrorList(messages.warningOrError()));
            return res;
        }
        return null;
    }

    private static List<Serializable> convertWarningOrErrorList(List<Type> warningOrErrorList) {
        if (warningOrErrorList != null) {
            return warningOrErrorList.stream().map(ConvertorUtil::convertWarningOrError).collect(Collectors.toList());
        }
        return null;
    }

    private static Serializable convertWarningOrError(Type type) {
        if (type != null) {
            if (type instanceof org.eclipse.daanse.xmla.api.exception.ErrorType) {
                org.eclipse.daanse.xmla.api.exception.ErrorType et = (org.eclipse.daanse.xmla.api.exception.ErrorType) type;
                ErrorType res = new ErrorType();
                res.setErrorCode(et.errorCode());
                res.setDescription(et.description());
                res.setCallstack(et.callstack());
                res.setLocation(convertLocation(et.location()));
                res.setHelpFile(et.helpFile());
                res.setSource(et.source());
                return res;
            }
            if (type instanceof org.eclipse.daanse.xmla.api.exception.WarningType) {
                org.eclipse.daanse.xmla.api.exception.WarningType wt = (org.eclipse.daanse.xmla.api.exception.WarningType) type;
                WarningType res = new WarningType();
                res.setDescription(wt.description());
                res.setHelpFile(wt.helpFile());
                res.setWarningCode(wt.warningCode());
                res.setSource(wt.source());
                res.setLocation(convertLocation(wt.location()));

                return res;
            }
        }
        return null;
    }

    private static MessageLocation convertLocation(org.eclipse.daanse.xmla.api.exception.MessageLocation location) {
        if (location != null) {
            MessageLocation res = new MessageLocation();
            res.setEnd(convertEnd(location.end()));
            res.setStart(convertStart(location.start()));
            res.setDependsOnObject(convertWarningLocationObject(location.dependsOnObject()));
            res.setLineOffset(location.lineOffset());
            res.setRowNumber(location.rowNumber());
            res.setSourceObject(convertWarningLocationObject(location.sourceObject()));
            res.setTextLength(location.textLength());
            return res;
        }
        return null;
    }

    private static WarningLocationObject convertWarningLocationObject(org.eclipse.daanse.xmla.api.engine200.WarningLocationObject dependsOnObject) {
        if (dependsOnObject != null) {
            WarningLocationObject res = new WarningLocationObject();
            res.setWarningColumn(convertWarningColumn(dependsOnObject.warningColumn()));
            res.setWarningMeasure(convertWarningMeasure(dependsOnObject.warningMeasure()));
            return res;
        }
        return null;

    }

    private static WarningMeasure convertWarningMeasure(org.eclipse.daanse.xmla.api.engine200.WarningMeasure warningMeasure) {
        if (warningMeasure != null) {
            WarningMeasure res = new WarningMeasure();
            res.setCube(warningMeasure.cube());
            res.setMeasureGroup(warningMeasure.measureGroup());
            res.setMeasureName(warningMeasure.measureName());
            return res;
        }
        return null;

    }

    private static WarningColumn convertWarningColumn(org.eclipse.daanse.xmla.api.engine200.WarningColumn warningColumn) {
        if (warningColumn != null) {
            WarningColumn res = new WarningColumn();
            res.setAttribute(warningColumn.attribute());
            res.setDimension(warningColumn.dimension());
            return res;
        }
        return null;

    }

    private static MessageLocation.End convertEnd(StartEnd end) {
        if (end != null) {
            MessageLocation.End res = new MessageLocation.End();
            res.setColumn(end.column());
            res.setLine(end.line());
            return res;
        }
        return null;
    }

    private static MessageLocation.Start convertStart(StartEnd end) {
        if (end != null) {
            MessageLocation.Start res = new MessageLocation.Start();
            res.setColumn(end.column());
            res.setLine(end.line());
            return res;
        }
        return null;
    }

    public static Instant convertToInstant(XMLGregorianCalendar createdTimestamp) {
        return createdTimestamp.toGregorianCalendar().toInstant();
    }
}
