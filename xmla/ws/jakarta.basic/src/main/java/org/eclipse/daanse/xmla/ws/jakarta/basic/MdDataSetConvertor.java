package org.eclipse.daanse.xmla.ws.jakarta.basic;

import org.eclipse.daanse.xmla.api.exception.StartEnd;
import org.eclipse.daanse.xmla.api.exception.Type;
import org.eclipse.daanse.xmla.api.mddataset.CellInfoItem;
import org.eclipse.daanse.xmla.api.mddataset.Value;
import org.eclipse.daanse.xmla.api.msxmla.MemberRef;
import org.eclipse.daanse.xmla.api.msxmla.MembersLookup;
import org.eclipse.daanse.xmla.api.msxmla.NormTuple;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine200.WarningColumn;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine200.WarningLocationObject;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine200.WarningMeasure;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.msxmla.NormTupleSet;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.msxmla.NormTuplesType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.ErrorType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.Exception;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.MessageLocation;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.Messages;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.WarningType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.Axes;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.AxesInfo;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.Axis;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.AxisInfo;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.CellData;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.CellInfo;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.CellSetType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.CellType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.CellTypeError;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.CubeInfo;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.HierarchyInfo;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.Mddataset;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.MemberType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.MembersType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.OlapInfo;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.OlapInfoCube;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.SetListType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.TupleType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.TuplesType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.Union;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public class MdDataSetConvertor {

    public static final String NAME = "name";
    public static final String TYPE = "type";

    public static Mddataset convertMdDataSet(org.eclipse.daanse.xmla.api.mddataset.Mddataset mdDataSet) {
        Mddataset mddatasetXml =
            new Mddataset();
        mddatasetXml.setAxes(convertAxes(mdDataSet.axes()));
        mddatasetXml.setCellData(convertCellData(mdDataSet.cellData()));
        mddatasetXml.setException(convertException(mdDataSet.exception()));
        mddatasetXml.setMessages(convertMessages(mdDataSet.messages()));
        mddatasetXml.setOlapInfo(convertOlapInfo(mdDataSet.olapInfo()));
        return mddatasetXml;
    }

    private static CellData convertCellData(org.eclipse.daanse.xmla.api.mddataset.CellData cellData) {
        if (cellData != null) {
            CellData res = new CellData();
            res.setCell(convertCellTypeList(cellData.cell()));
            res.setCellSet(convertCellSet(cellData.cellSet()));
            return res;
        }
        return null;
    }

    private static CellSetType convertCellSet(org.eclipse.daanse.xmla.api.mddataset.CellSetType cellSet) {
        if (cellSet != null) {
            CellSetType res = new CellSetType();
            res.setData(cellSet.data());
        }
        return null;
    }

    private static List<CellType> convertCellTypeList(List<org.eclipse.daanse.xmla.api.mddataset.CellType> cellTypeList) {
        if (cellTypeList != null) {
            return cellTypeList.stream().map(MdDataSetConvertor::convertCellType).collect(Collectors.toList());
        }
        return null;
    }

    private static CellType convertCellType(org.eclipse.daanse.xmla.api.mddataset.CellType cellType) {
        if (cellType != null) {
            CellType res = new CellType();
            res.setAny(convertElementList(cellType.any()));
            res.setValue(convertValue(cellType.value()));
            res.setCellOrdinal(cellType.cellOrdinal());
            return res;
        }
        return null;
    }

    private static CellType.Value convertValue(Value value) {
        if (value != null) {
            CellType.Value res = new CellType.Value();
            res.setError(convertCellTypeErrorList(value.error()));
            return res;
        }
        return null;
    }

    private static List<CellTypeError> convertCellTypeErrorList(List<org.eclipse.daanse.xmla.api.mddataset.CellTypeError> errorList) {
        if (errorList != null) {
            return errorList.stream().map(MdDataSetConvertor::convertCellTypeError).collect(Collectors.toList());
        }
        return null;
    }

    private static CellTypeError convertCellTypeError(org.eclipse.daanse.xmla.api.mddataset.CellTypeError cellTypeError) {
        if (cellTypeError != null) {
            CellTypeError res = new CellTypeError();
            res.setErrorCode(cellTypeError.errorCode());
            res.setDescription(cellTypeError.description());
            return res;
        }
        return null;
    }

    private static List<Element> convertElementList(List<CellInfoItem> anyList) {
        if (anyList != null) {
            return anyList.stream().map(MdDataSetConvertor::convertElement).collect(Collectors.toList());
        }
        return null;
    }

    private static Element convertElement(CellInfoItem cellInfoItem) {
        if (cellInfoItem != null) {
            try {
                Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .newDocument();
                Element element = document.createElement(cellInfoItem.tagName());
                element.setAttribute(NAME, cellInfoItem.name());
                if (cellInfoItem.type() != null && cellInfoItem.type().isPresent()) {
                    element.setAttribute(TYPE, cellInfoItem.type().get());
                }
                return element;
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    private static OlapInfo convertOlapInfo(org.eclipse.daanse.xmla.api.mddataset.OlapInfo olapInfo) {
        if (olapInfo != null) {
            OlapInfo res = new OlapInfo();
            res.setAxesInfo(convertAxesInfo(olapInfo.axesInfo()));
            res.setCellInfo(convertCellInfo(olapInfo.cellInfo()));
            res.setCubeInfo(convertCubeInfo(olapInfo.cubeInfo()));
            return res;
        }
        return null;
    }

    private static CubeInfo convertCubeInfo(org.eclipse.daanse.xmla.api.mddataset.CubeInfo cubeInfo) {
        if (cubeInfo != null) {
            CubeInfo res = new CubeInfo();
            res.setCube(convertOlapInfoCubeList(cubeInfo.cube()));
            return res;
        }
        return null;
    }

    private static List<OlapInfoCube> convertOlapInfoCubeList(List<org.eclipse.daanse.xmla.api.mddataset.OlapInfoCube> cubeList) {
        if (cubeList != null) {
            return cubeList.stream().map(MdDataSetConvertor::convertOlapInfoCube).collect(Collectors.toList());
        }
        return null;
    }

    private static OlapInfoCube convertOlapInfoCube(org.eclipse.daanse.xmla.api.mddataset.OlapInfoCube olapInfoCube) {
        if (olapInfoCube != null) {
            OlapInfoCube res = new OlapInfoCube();
            res.setCubeName(olapInfoCube.cubeName());
            res.setLastDataUpdate(convertToXMLGregorianCalendar(olapInfoCube.lastDataUpdate()));
            res.setLastSchemaUpdate(convertToXMLGregorianCalendar(olapInfoCube.lastSchemaUpdate()));
            return res;
        }
        return null;
    }

    private static XMLGregorianCalendar convertToXMLGregorianCalendar(Instant instant) throws RuntimeException {
        GregorianCalendar gregorianCalendar = GregorianCalendar
            .from(instant.atZone(ZoneId.systemDefault()));
        try {
            return DatatypeFactory
                .newInstance()
                .newXMLGregorianCalendar(
                    gregorianCalendar
                );
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static CellInfo convertCellInfo(org.eclipse.daanse.xmla.api.mddataset.CellInfo cellInfo) {
        if (cellInfo != null) {
            CellInfo res = new CellInfo();
            res.setAny(convertElementList(cellInfo.any()));
            return res;
        }
        return null;
    }

    private static AxesInfo convertAxesInfo(org.eclipse.daanse.xmla.api.mddataset.AxesInfo axesInfo) {
        if (axesInfo != null) {
            AxesInfo res = new AxesInfo();
            res.setAxisInfo(convertAxisInfoList(axesInfo.axisInfo()));
            return res;
        }
        return null;
    }

    private static List<AxisInfo> convertAxisInfoList(List<org.eclipse.daanse.xmla.api.mddataset.AxisInfo> axisInfoList) {
        if (axisInfoList != null) {
            return axisInfoList.stream().map(MdDataSetConvertor::convertAxisInfo).collect(Collectors.toList());
        }
        return null;
    }

    private static AxisInfo convertAxisInfo(org.eclipse.daanse.xmla.api.mddataset.AxisInfo axisInfo) {
        if (axisInfo != null) {
            AxisInfo res = new AxisInfo();
            res.setName(axisInfo.name());
            res.setHierarchyInfo(convertHierarchyInfoList(axisInfo.hierarchyInfo()));
            return res;
        }
        return null;
    }

    private static List<HierarchyInfo> convertHierarchyInfoList(List<org.eclipse.daanse.xmla.api.mddataset.HierarchyInfo> hierarchyInfoList) {
        if (hierarchyInfoList != null) {
            return hierarchyInfoList.stream().map(MdDataSetConvertor::convertHierarchyInfo).collect(Collectors.toList());
        }
        return null;
    }

    private static HierarchyInfo convertHierarchyInfo(org.eclipse.daanse.xmla.api.mddataset.HierarchyInfo hierarchyInfo) {
        if (hierarchyInfo != null) {
            HierarchyInfo res = new HierarchyInfo();
            res.setAny(convertElementList(hierarchyInfo.any()));
            res.setName(hierarchyInfo.name());
            return res;
        }
        return null;
    }

    private static Messages convertMessages(org.eclipse.daanse.xmla.api.exception.Messages messages) {
        if (messages != null) {
            Messages res = new Messages();
            res.setWarningOrError(convertWarningOrErrorList(messages.warningOrError()));
            return res;
        }
        return null;
    }

    private static List<Serializable> convertWarningOrErrorList(List<Type> warningOrErrorList) {
        if (warningOrErrorList != null) {
            return warningOrErrorList.stream().map(MdDataSetConvertor::convertWarningOrError).collect(Collectors.toList());
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

    private static Exception convertException(org.eclipse.daanse.xmla.api.exception.Exception exception) {
        if (exception != null) {
            Exception res = new Exception();
            return res;
        }
        return null;
    }

    private static Axes convertAxes(org.eclipse.daanse.xmla.api.mddataset.Axes axes) {
        if (axes != null) {
            Axes res = new Axes();
            res.setAxis(convertAxisList(axes.axis()));
            return res;
        }
        return null;
    }

    private static List<Axis> convertAxisList(List<org.eclipse.daanse.xmla.api.mddataset.Axis> axisList) {
        if (axisList != null) {
            return axisList.stream().map(MdDataSetConvertor::convertAxis).collect(Collectors.toList());
        }
        return null;
    }

    private static Axis convertAxis(org.eclipse.daanse.xmla.api.mddataset.Axis axis) {
        if (axis != null) {
            Axis res = new Axis();
            res.setName(axis.name());
            res.setSetType(convertSetTypeList(axis.setType()));
            return res;
        }
        return null;
    }

    private static List<Object> convertSetTypeList(List<org.eclipse.daanse.xmla.api.mddataset.Type> setTypeList) {
        if (setTypeList != null) {
            return setTypeList.stream().map(MdDataSetConvertor::convertSetType).collect(Collectors.toList());
        }
        return null;
    }

    private static Object convertSetType(org.eclipse.daanse.xmla.api.mddataset.Type type) {
        if (type != null) {
            if (type instanceof org.eclipse.daanse.xmla.api.mddataset.MembersType) {
                org.eclipse.daanse.xmla.api.mddataset.MembersType mt = (org.eclipse.daanse.xmla.api.mddataset.MembersType) type;
                MembersType res = new MembersType();
                res.setHierarchy(mt.hierarchy());
                res.setMember(convertMemberTypeList(mt.member()));
                return res;
            }
            if (type instanceof org.eclipse.daanse.xmla.api.mddataset.TuplesType) {
                org.eclipse.daanse.xmla.api.mddataset.TuplesType tt = (org.eclipse.daanse.xmla.api.mddataset.TuplesType) type;
                TuplesType res = new TuplesType();
                res.setTuple(convertTupleTypeList(tt.tuple()));
                return res;
            }
            if (type instanceof org.eclipse.daanse.xmla.api.mddataset.SetListType) {
                org.eclipse.daanse.xmla.api.mddataset.SetListType st = (org.eclipse.daanse.xmla.api.mddataset.SetListType) type;
                SetListType res = new SetListType();
                res.setSize(st.size());
                res.setSetType(convertSetTypeList(st.setType()));
                return res;
            }
            if (type instanceof org.eclipse.daanse.xmla.api.mddataset.NormTupleSet) {
                org.eclipse.daanse.xmla.api.mddataset.NormTupleSet nts = (org.eclipse.daanse.xmla.api.mddataset.NormTupleSet) type;
                NormTupleSet res = new NormTupleSet();
                res.setMembersLookup(convertMembersLookup(nts.membersLookup()));
                res.setNormTuples(convertNormTuplesType(nts.normTuples()));
                return res;
            }
            if (type instanceof org.eclipse.daanse.xmla.api.mddataset.Union) {
                org.eclipse.daanse.xmla.api.mddataset.Union u = (org.eclipse.daanse.xmla.api.mddataset.Union) type;
                Union res = new Union();
                res.setSetType(convertSetTypeList(u.setType()));
                return res;
            }
        }
        return null;
    }

    private static NormTuplesType convertNormTuplesType(org.eclipse.daanse.xmla.api.msxmla.NormTuplesType normTuplesType) {
        if (normTuplesType != null) {
            NormTuplesType res = new NormTuplesType();
            res.setNormTuple(convertNormTupleList(normTuplesType.normTuple()));
            return res;
        }
        return null;
    }

    private static List<NormTuplesType.NormTuple> convertNormTupleList(List<NormTuple> normTupleList) {
        if (normTupleList != null) {
            return normTupleList.stream().map(MdDataSetConvertor::convertNormTuple).collect(Collectors.toList());
        }
        return null;
    }

    private static NormTuplesType.NormTuple convertNormTuple(NormTuple normTuple) {
        if (normTuple != null) {
            NormTuplesType.NormTuple res = new NormTuplesType.NormTuple();
            res.setMemberRef(convertMemberRefList(normTuple.memberRef()));
            return res;
        }
        return null;
    }

    private static List<NormTuplesType.NormTuple.MemberRef> convertMemberRefList(List<MemberRef> memberRefList) {
        if (memberRefList != null) {
            return memberRefList.stream().map(MdDataSetConvertor::convertMemberRef).collect(Collectors.toList());
        }
        return null;
    }

    private static NormTuplesType.NormTuple.MemberRef convertMemberRef(MemberRef memberRef) {
        if (memberRef != null) {
            NormTuplesType.NormTuple.MemberRef res = new NormTuplesType.NormTuple.MemberRef();
            res.setMemberDispInfo(memberRef.memberDispInfo());
            res.setMemberOrdinal(memberRef.memberOrdinal());
            return res;
        }
        return null;
    }

    private static NormTupleSet.MembersLookup convertMembersLookup(MembersLookup membersLookup) {
        if (membersLookup != null) {
            NormTupleSet.MembersLookup res = new NormTupleSet.MembersLookup();
            res.setMembers(convertTupleTypeList(membersLookup.members()));
            return res;
        }
        return null;
    }

    private static List<TupleType> convertTupleTypeList(List<org.eclipse.daanse.xmla.api.mddataset.TupleType> tupleTypeList) {
        if (tupleTypeList != null) {
            return tupleTypeList.stream().map(MdDataSetConvertor::convertTupleType).collect(Collectors.toList());
        }
        return null;
    }

    private static TupleType convertTupleType(org.eclipse.daanse.xmla.api.mddataset.TupleType tupleType) {
        if (tupleType != null) {
            TupleType res = new TupleType();
            res.setMember(convertMemberTypeList(tupleType.member()));
            return res;
        }
        return null;
    }

    private static List<MemberType> convertMemberTypeList(List<org.eclipse.daanse.xmla.api.mddataset.MemberType> memberTypeList) {
        if (memberTypeList != null) {
            return memberTypeList.stream().map(MdDataSetConvertor::convertMemberType).collect(Collectors.toList());
        }
        return null;
    }

    private static MemberType convertMemberType(org.eclipse.daanse.xmla.api.mddataset.MemberType memberType) {
        if (memberType != null) {
            MemberType res = new MemberType();
            res.setHierarchy(memberType.hierarchy());
            res.setAny(convertElementList(memberType.any()));
            return res;
        }
        return null;
    }
}
