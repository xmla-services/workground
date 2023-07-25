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
package org.eclipse.daanse.xmla.ws.jakarta.provider.soapmessage;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import org.eclipse.daanse.xmla.api.engine200.WarningColumn;
import org.eclipse.daanse.xmla.api.engine200.WarningLocationObject;
import org.eclipse.daanse.xmla.api.engine200.WarningMeasure;
import org.eclipse.daanse.xmla.api.exception.ErrorType;
import org.eclipse.daanse.xmla.api.exception.Exception;
import org.eclipse.daanse.xmla.api.exception.MessageLocation;
import org.eclipse.daanse.xmla.api.exception.Messages;
import org.eclipse.daanse.xmla.api.exception.StartEnd;
import org.eclipse.daanse.xmla.api.exception.WarningType;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;
import org.eclipse.daanse.xmla.api.mddataset.Axes;
import org.eclipse.daanse.xmla.api.mddataset.AxesInfo;
import org.eclipse.daanse.xmla.api.mddataset.Axis;
import org.eclipse.daanse.xmla.api.mddataset.AxisInfo;
import org.eclipse.daanse.xmla.api.mddataset.CellData;
import org.eclipse.daanse.xmla.api.mddataset.CellInfo;
import org.eclipse.daanse.xmla.api.mddataset.CellInfoItem;
import org.eclipse.daanse.xmla.api.mddataset.CellSetType;
import org.eclipse.daanse.xmla.api.mddataset.CellType;
import org.eclipse.daanse.xmla.api.mddataset.CellTypeError;
import org.eclipse.daanse.xmla.api.mddataset.CubeInfo;
import org.eclipse.daanse.xmla.api.mddataset.HierarchyInfo;
import org.eclipse.daanse.xmla.api.mddataset.Mddataset;
import org.eclipse.daanse.xmla.api.mddataset.MemberType;
import org.eclipse.daanse.xmla.api.mddataset.MembersType;
import org.eclipse.daanse.xmla.api.mddataset.NormTupleSet;
import org.eclipse.daanse.xmla.api.mddataset.OlapInfo;
import org.eclipse.daanse.xmla.api.mddataset.OlapInfoCube;
import org.eclipse.daanse.xmla.api.mddataset.SetListType;
import org.eclipse.daanse.xmla.api.mddataset.TupleType;
import org.eclipse.daanse.xmla.api.mddataset.TuplesType;
import org.eclipse.daanse.xmla.api.mddataset.Type;
import org.eclipse.daanse.xmla.api.mddataset.Union;
import org.eclipse.daanse.xmla.api.mddataset.Value;
import org.eclipse.daanse.xmla.api.msxmla.MemberRef;
import org.eclipse.daanse.xmla.api.msxmla.MembersLookup;
import org.eclipse.daanse.xmla.api.msxmla.NormTuple;
import org.eclipse.daanse.xmla.api.msxmla.NormTuplesType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SoapUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SoapUtil.class);

    public static SOAPBody toStatementResponse(StatementResponse statementResponse) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        addMdDataSet(root, statementResponse.mdDataSet());
        return body;
    }

    private static void addMdDataSet(SOAPElement e, Mddataset it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "mddataset");
            addOlapInfo(el, it.olapInfo());
            addAxes(el, it.axes());
            addCellData(el, it.cellData());
            addException(el, it.exception());
            addMessages(el, it.messages());
        }
    }

    private static void addMessages(SOAPElement e, Messages it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Messages");
            addExceptionTypeList(el, it.warningOrError());

        }
    }

    private static void addExceptionTypeList(
        SOAPElement e,
        List<org.eclipse.daanse.xmla.api.exception.Type> list) {
        if (list != null) {
            list.forEach(it -> addExceptionType(e, it));
        }
    }

    private static void addExceptionType(SOAPElement e, org.eclipse.daanse.xmla.api.exception.Type it) {
        if (it != null) {
            if (it instanceof WarningType warningType) {
                addWarningType(e, warningType);
            }
            if (it instanceof ErrorType errorType) {
                addErrorType(e, errorType);
            }
        }
    }

    private static void addErrorType(SOAPElement e, ErrorType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Error");

            addChildElement(el, "Callstack", it.callstack());
            addChildElement(el, "ErrorCode", String.valueOf(it.errorCode()));
            addMessageLocation(el, it.location());
            addChildElement(el, "Description", it.description());
            addChildElement(el, "Source", it.source());
            addChildElement(el, "HelpFile", it.helpFile());
        }
    }

    private static void addWarningType(SOAPElement e, WarningType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Warning");
            addChildElement(el, "WarningCode", String.valueOf(it.warningCode()));
            addMessageLocation(el, it.location());
            addChildElement(el, "Description", it.description());
            addChildElement(el, "Source", it.source());
            addChildElement(el, "HelpFile", it.helpFile());
        }
    }

    private static void addMessageLocation(SOAPElement e, MessageLocation it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Location");
            addStartEnd(el, "Start", it.start());
            addStartEnd(el, "End", it.end());
            addChildElement(el, "LineOffset", String.valueOf(it.lineOffset()));
            addChildElement(el, "TextLength", String.valueOf(it.textLength()));
            addWarningLocationObject(el, "SourceObject", it.sourceObject());
            addWarningLocationObject(el, "DependsOnObject", it.dependsOnObject());
            addChildElement(el, "RowNumber", String.valueOf(it.rowNumber()));
        }

    }

    private static void addWarningLocationObject(SOAPElement e, String tagName, WarningLocationObject it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, tagName);
            addWarningColumn(el, it.warningColumn());
            addWarningMeasure(el, it.warningMeasure());
        }
    }

    private static void addWarningMeasure(SOAPElement e, WarningMeasure it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "WarningMeasure");
            addChildElement(el, "Cube", it.cube());
            addChildElement(el, "MeasureGroup", it.measureGroup());
            addChildElement(el, "MeasureName", it.measureName());
        }
    }

    private static void addWarningColumn(SOAPElement e, WarningColumn it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "WarningColumn");
            addChildElement(el, "Dimension", it.dimension());
            addChildElement(el, "Attribute", it.attribute());
        }
    }

    private static void addStartEnd(SOAPElement e, String tagName, StartEnd it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, tagName);
            addChildElement(el, "Line", String.valueOf(it.line()));
            addChildElement(el, "Column", String.valueOf(it.column()));
        }
    }


    private static void addException(SOAPElement e, Exception it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Exception");
        }
    }

    private static void addCellData(SOAPElement e, CellData it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "CellData");
            addCellTypeList(el, it.cell());
            addCellSetType(el, it.cellSet());
        }
    }

    private static void addCellSetType(SOAPElement e, CellSetType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "CellSet");
            addDataList(el, it.data());
        }
    }

    private static void addDataList(SOAPElement el, List<byte[]> list) {
        if (list != null) {
            list.forEach(it -> addData(el, it));
        }
    }

    private static void addData(SOAPElement e, byte[] it) {
        if (it != null) {
            addChildElement(e, "Data", new String(it, UTF_8));
        }
    }

    private static void addCellTypeList(SOAPElement el, List<CellType> list) {
        if (list != null) {
            list.forEach(it -> addCellType(el, it));
        }
    }

    private static void addCellType(SOAPElement e, CellType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Cell");
            addCellTypeValue(el, it.value());
            addCellInfoItemList(el, it.any());
            addChildElement(el, "CellOrdinal", String.valueOf(it.cellOrdinal()));
        }
    }

    private static void addCellTypeValue(SOAPElement e, Value it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Value");
            addCellTypeErrorList(el, it.error());
        }
    }

    private static void addCellTypeErrorList(SOAPElement el, List<CellTypeError> list) {
        if (list != null) {
            list.forEach(it -> addCellTypeError(el, it));
        }
    }

    private static void addCellTypeError(SOAPElement e, CellTypeError it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Error");
            addChildElement(el, "ErrorCode", String.valueOf(it.errorCode()));
            addChildElement(el, "Description", it.description());
        }
    }

    private static void addAxes(SOAPElement e, Axes it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Axes");
            addAxisList(el, it.axis());
        }
    }

    private static void addAxisList(SOAPElement e, List<Axis> list) {
        if (list != null) {
            list.forEach(it -> addAxis(e, it));
        }
    }

    private static void addAxis(SOAPElement e, Axis it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Axis");
            addTypeList(el, it.setType());
            addChildElement(el, "name", it.name());
        }
    }

    private static void addTypeList(SOAPElement e, List<Type> list) {
        if (list != null) {
            list.forEach(it -> addType(e, it));
        }
    }

    private static void addType(SOAPElement e, Type it) {
        if (it != null) {
            if (it instanceof MembersType membersType) {
                addMembersType(e, membersType);
            }
            if (it instanceof TuplesType tuplesType) {
                addTuplesType(e, tuplesType);
            }
            if (it instanceof SetListType setListType) {
                addSetListType(e, setListType);
            }
            if (it instanceof NormTupleSet normTupleSet) {
                addNormTupleSet(e, normTupleSet);
            }
            if (it instanceof Union union) {
                addUnion(e, union);
            }
        }
    }

    private static void addUnion(SOAPElement e, Union it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Union");
            addTypeList(el, it.setType());
        }
    }

    private static void addNormTupleSet(SOAPElement e, NormTupleSet it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "NormTupleSet");
            addNormTuplesType(el, it.normTuples());
            addTupleTypeList(el, it.membersLookup());
        }
    }

    private static void addTupleTypeList(SOAPElement e, MembersLookup list) {
        if (list != null) {
            SOAPElement el = addChildElement(e, "MembersLookup");
            if (list.members() != null) {
                list.members().forEach(it -> addTupleType(el, "Members", it));
            }
        }
    }

    private static void addNormTuplesType(SOAPElement e, NormTuplesType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "NormTuples");
            addNormTupleList(el, it.normTuple());
        }
    }

    private static void addNormTupleList(SOAPElement el, List<NormTuple> list) {
        if (list  != null) {
            list.forEach(it -> addNormTuple(el, it));
        }
    }

    private static void addNormTuple(SOAPElement e, NormTuple it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "NormTuple");
            addMemberRefList(e, it.memberRef());
        }
    }

    private static void addMemberRefList(SOAPElement e, List<MemberRef> list) {
        if (list  != null) {
            list.forEach(it -> addMemberRef(e, it));
        }
    }

    private static void addMemberRef(SOAPElement e, MemberRef it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "MemberRef");
            addChildElement(el, "MemberOrdinal", String.valueOf(it.memberOrdinal()));
            addChildElement(el, "MemberDispInfo", String.valueOf(it.memberDispInfo()));
        }
    }

    private static void addSetListType(SOAPElement e, SetListType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "CrossProduct");
            addTypeList(el, it.setType());
            addChildElement(el, "Size", String.valueOf(it.size()));
        }
    }

    private static void addTuplesType(SOAPElement e, TuplesType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Tuples");
            addTuplesTypeList(el, it.tuple());
        }

    }

    private static void addTuplesTypeList(SOAPElement e, List<TupleType> list) {
        if (list != null) {
            list.forEach(it -> addTupleType(e, "Tuple", it));
        }
    }

    private static void addTupleType(SOAPElement e, String tagName, TupleType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, tagName);
            addMemberTypeList(el, it.member());
        }

    }

    private static void addMemberTypeList(SOAPElement e, List<MemberType> list) {
        if (list != null) {
            list.forEach(it -> addMemberType(e, it));
        }
    }

    private static void addMemberType(SOAPElement e, MemberType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Member");
            addCellInfoItemList(el, it.any());
            addChildElement(el, "Hierarchy", it.hierarchy());
        }
    }

    private static void addMembersType(SOAPElement e, MembersType it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Members");
            addMemberTypeList(el, it.member());
            addChildElement(el, "Hierarchy", it.hierarchy());
        }
    }

    private static void addOlapInfo(SOAPElement e, OlapInfo it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "OlapInfo");
            addCubeInfo(el, it.cubeInfo());
            addAxesInfo(el, it.axesInfo());
            addCellInfo(el, it.cellInfo());
        }
    }

    private static void addCellInfo(SOAPElement e, CellInfo it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "CellInfo");
            addCellInfoItemList(el, it.any());
        }
    }

    private static void addCellInfoItemList(SOAPElement e, List<CellInfoItem> list) {
        if (list != null) {
            list.forEach(it -> addCellInfoItem(e, it));
        }
    }

    private static void addCellInfoItem(SOAPElement e, CellInfoItem it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, it.tagName());
            el.setAttribute("name", it.name());
            it.type().ifPresent(v -> el.setAttribute("type", v));
        }
    }

    private static void addAxesInfo(SOAPElement e, AxesInfo it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "AxesInfo");
            addAxisInfoList(el, it.axisInfo());
        }
    }

    private static void addAxisInfoList(SOAPElement el, List<AxisInfo> list) {
        if (list != null) {
            list.forEach(it -> addAxisInfo(el, it));
        }
    }

    private static void addAxisInfo(SOAPElement e, AxisInfo it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "AxisInfo");
            addChildElement(el, "name", it.name());
            addHierarchyInfoList(el, it.hierarchyInfo());
        }
    }

    private static void addHierarchyInfoList(SOAPElement el, List<HierarchyInfo> list) {
        if (list != null) {
            list.forEach(it -> addHierarchyInfo(el, it));
        }
    }

    private static void addHierarchyInfo(SOAPElement e, HierarchyInfo it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "HierarchyInfo");
            addCellInfoItemList(el, it.any());
            addChildElement(el, "name", it.name());
        }
    }

    private static void addCubeInfo(SOAPElement e, CubeInfo it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "CubeInfo");
            addOlapInfoCubeList(el, it.cube());
        }
    }

    private static void addOlapInfoCubeList(SOAPElement e, List<OlapInfoCube> list) {
        if (list != null) {
            list.forEach(it -> addOlapInfoCube(e, it));
        }
    }

    private static void addOlapInfoCube(SOAPElement e, OlapInfoCube it) {
        if (it != null) {
            SOAPElement el = addChildElement(e, "Cube");
            addChildElement(el, "CubeName", it.cubeName());
            addChildElement(el, "LastDataUpdate", String.valueOf(it.lastDataUpdate()));
            addChildElement(el, "LastSchemaUpdate", String.valueOf(it.lastSchemaUpdate()));
        }
    }

    private static SOAPBody createSOAPBody() {
        try {
            SOAPMessage message = MessageFactory.newInstance().createMessage();
            return message.getSOAPBody();
        } catch (SOAPException e) {
            throw new RuntimeException("create SOAPBody error");
        }
    }

    private static SOAPElement addRoot(SOAPElement body) {
        SOAPElement response = addChildElement(body, "DiscoverResponse");
        SOAPElement ret = addChildElement(response, "return");
        return addChildElement(ret, "root");
    }

    private static void addChildElement(SOAPElement element, String childElementName, String value) {
        try {
            if (value != null) {
                element.addChildElement(childElementName).setTextContent(value);
            }
        } catch (SOAPException e) {
            LOGGER.error("addChildElement {} error", childElementName);
            throw new RuntimeException("addChildElement error", e);
        }
    }

    private static SOAPElement addChildElement(SOAPElement element, String childElementName) {
        try {
            return element.addChildElement(childElementName);
        } catch (SOAPException e) {
            LOGGER.error("addChildElement {} error", childElementName);
            throw new RuntimeException("addChildElement error", e);
        }
    }
}
