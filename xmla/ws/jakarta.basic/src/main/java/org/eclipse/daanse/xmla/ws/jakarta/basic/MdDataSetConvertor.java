package org.eclipse.daanse.xmla.ws.jakarta.basic;

import org.eclipse.daanse.xmla.api.exception.Type;
import org.eclipse.daanse.xmla.api.mddataset.CellInfoItem;
import org.eclipse.daanse.xmla.api.mddataset.Value;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.ErrorType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.Exception;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.MessageLocation;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.Messages;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.Axes;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.AxesInfo;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.AxisInfo;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.CellData;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.CellInfo;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.CellSetType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.CellType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.CellTypeError;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.CubeInfo;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.HierarchyInfo;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.Mddataset;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.OlapInfo;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.OlapInfoCube;
import org.w3c.dom.Element;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public class MdDataSetConvertor {

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
            res.setCell(convertCell(cellData.cell()));
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

    private static List<CellType> convertCell(List<org.eclipse.daanse.xmla.api.mddataset.CellType> cellTypeList) {
        if (cellTypeList != null) {
            return cellTypeList.stream().map(MdDataSetConvertor::convertCellType).collect(Collectors.toList());
        }
        return null;
    }

    private static CellType convertCellType(org.eclipse.daanse.xmla.api.mddataset.CellType cellType) {
        if (cellType != null) {
            CellType res = new CellType();
            res.setAny(convertAnyList(cellType.any()));
            res.setValue(convertValue(cellType.value()));
            res.setCellOrdinal(cellType.cellOrdinal());
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
        }
        return null;
    }

    private static List<Element> convertAnyList(List<CellInfoItem> anyList) {
        if (anyList != null) {
            return anyList.stream().map(MdDataSetConvertor::convertAny).collect(Collectors.toList());
        }
        return null;
    }

    private static Element convertAny(CellInfoItem cellInfoItem) {
        //TODO
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
            res.setCube(convertCubeList(cubeInfo.cube()));
        }
        return null;
    }

    private static List<OlapInfoCube> convertCubeList(List<org.eclipse.daanse.xmla.api.mddataset.OlapInfoCube> cubeList) {
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
            res.setAny(convertAnyList(cellInfo.any()));
            return res;
        }
        return null;
    }

    private static AxesInfo convertAxesInfo(org.eclipse.daanse.xmla.api.mddataset.AxesInfo axesInfo) {
        if (axesInfo != null) {
            AxesInfo res = new AxesInfo();
            res.setAxisInfo(convertAxisInfoList(axesInfo.axisInfo()));
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
            res.setAny(convertAnyList(hierarchyInfo.any()));
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
            }
        }
        return null;
    }

    private static MessageLocation convertLocation(org.eclipse.daanse.xmla.api.exception.MessageLocation location) {
        //TODO
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
            return res;
        }
        return null;
    }
}
