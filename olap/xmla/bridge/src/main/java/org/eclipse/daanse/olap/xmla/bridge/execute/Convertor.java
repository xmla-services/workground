package org.eclipse.daanse.olap.xmla.bridge.execute;

import mondrian.olap.MondrianProperties;
import mondrian.olap.Util;
import mondrian.olap4j.MondrianOlap4jCell;
import mondrian.olap4j.MondrianOlap4jMember;
import mondrian.server.Statement;
import mondrian.util.CompositeList;
import mondrian.xmla.RowsetDefinition;
import mondrian.xmla.XmlaException;
import mondrian.xmla.XmlaUtil;
import org.eclipse.daanse.olap.api.NameSegment;
import org.eclipse.daanse.olap.api.element.Cube;
import org.eclipse.daanse.olap.api.element.Dimension;
import org.eclipse.daanse.olap.api.element.Hierarchy;
import org.eclipse.daanse.olap.api.element.Level;
import org.eclipse.daanse.olap.api.element.Member;
import org.eclipse.daanse.olap.api.query.component.QueryComponent;
import org.eclipse.daanse.olap.api.result.Cell;
import org.eclipse.daanse.olap.api.result.CellSet;
import org.eclipse.daanse.olap.api.result.CellSetAxis;
import org.eclipse.daanse.olap.api.result.CellSetAxisMetaData;
import org.eclipse.daanse.olap.api.result.Datatype;
import org.eclipse.daanse.olap.api.result.IMondrianOlap4jProperty;
import org.eclipse.daanse.olap.api.result.Position;
import org.eclipse.daanse.olap.api.result.Property;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;
import org.eclipse.daanse.xmla.api.mddataset.Axis;
import org.eclipse.daanse.xmla.api.mddataset.AxisInfo;
import org.eclipse.daanse.xmla.api.mddataset.CellInfoItem;
import org.eclipse.daanse.xmla.api.mddataset.CellType;
import org.eclipse.daanse.xmla.api.mddataset.HierarchyInfo;
import org.eclipse.daanse.xmla.api.mddataset.OlapInfoCube;
import org.eclipse.daanse.xmla.api.mddataset.TupleType;
import org.eclipse.daanse.xmla.api.mddataset.TuplesType;
import org.eclipse.daanse.xmla.api.mddataset.Type;
import org.eclipse.daanse.xmla.model.record.exception.ExceptionR;
import org.eclipse.daanse.xmla.model.record.exception.MessagesR;
import org.eclipse.daanse.xmla.model.record.execute.statement.StatementResponseR;
import org.eclipse.daanse.xmla.model.record.mddataset.AxesInfoR;
import org.eclipse.daanse.xmla.model.record.mddataset.AxesR;
import org.eclipse.daanse.xmla.model.record.mddataset.AxisInfoR;
import org.eclipse.daanse.xmla.model.record.mddataset.AxisR;
import org.eclipse.daanse.xmla.model.record.mddataset.CellDataR;
import org.eclipse.daanse.xmla.model.record.mddataset.CellInfoItemR;
import org.eclipse.daanse.xmla.model.record.mddataset.CellInfoR;
import org.eclipse.daanse.xmla.model.record.mddataset.CellSetTypeR;
import org.eclipse.daanse.xmla.model.record.mddataset.CellTypeR;
import org.eclipse.daanse.xmla.model.record.mddataset.CubeInfoR;
import org.eclipse.daanse.xmla.model.record.mddataset.HierarchyInfoR;
import org.eclipse.daanse.xmla.model.record.mddataset.MddatasetR;
import org.eclipse.daanse.xmla.model.record.mddataset.MemberTypeR;
import org.eclipse.daanse.xmla.model.record.mddataset.OlapInfoCubeR;
import org.eclipse.daanse.xmla.model.record.mddataset.OlapInfoR;
import org.eclipse.daanse.xmla.model.record.mddataset.TupleTypeR;
import org.eclipse.daanse.xmla.model.record.mddataset.TuplesTypeR;
import org.eclipse.daanse.xmla.model.record.mddataset.ValueR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static mondrian.xmla.XmlaConstants.HSB_BAD_PROPERTIES_LIST_CODE;
import static mondrian.xmla.XmlaConstants.HSB_BAD_PROPERTIES_LIST_FAULT_FS;
import static mondrian.xmla.XmlaConstants.SERVER_FAULT_FC;

public class Convertor {

    private static final Logger LOGGER =
        LoggerFactory.getLogger(Convertor.class);
    private static XmlaUtil.ElementNameEncoder encoder =
        XmlaUtil.ElementNameEncoder.INSTANCE;

    protected static final Map<String, Property.StandardMemberProperty> longProps =
        new HashMap();

    static {
        longProps.put("UName", Property.StandardMemberProperty.MEMBER_UNIQUE_NAME);
        longProps.put("Caption", Property.StandardMemberProperty.MEMBER_CAPTION);
        longProps.put("LName", Property.StandardMemberProperty.LEVEL_UNIQUE_NAME);
        longProps.put("LNum", Property.StandardMemberProperty.LEVEL_NUMBER);
        longProps.put("DisplayInfo", Property.StandardMemberProperty.DISPLAY_INFO);
    }

    protected static Map<String, CellProperty> cellPropertyMap =new HashMap<>();
    static {
        cellPropertyMap.put("CELL_ORDINAL", new CellProperty("CELL_ORDINAL", "CellOrdinal", "xsd:unsignedInt"));
        cellPropertyMap.put("VALUE", new CellProperty("VALUE", "Value", null));
        cellPropertyMap.put("FORMATTED_VALUE", new CellProperty("FORMATTED_VALUE", "FmtValue", "xsd:string"));
        cellPropertyMap.put("FORMAT_STRING", new CellProperty("FORMAT_STRING", "FormatString", "xsd:string"));
        cellPropertyMap.put("LANGUAGE", new CellProperty("LANGUAGE", "Language", "xsd:unsignedInt"));
        cellPropertyMap.put("BACK_COLOR", new CellProperty("BACK_COLOR", "BackColor", "xsd:unsignedInt"));
        cellPropertyMap.put("FORE_COLOR", new CellProperty("FORE_COLOR", "ForeColor", "xsd:unsignedInt"));
        cellPropertyMap.put("FONT_FLAGS", new CellProperty("FONT_FLAGS", "FontFlags", "xsd:int"));
    }

    protected static final List<Property> defaultProps =
        Arrays.asList(
            rename(Property.StandardMemberProperty.MEMBER_UNIQUE_NAME, "UName"),
            rename(Property.StandardMemberProperty.MEMBER_CAPTION, "Caption"),
            rename(Property.StandardMemberProperty.LEVEL_UNIQUE_NAME, "LName"),
            rename(Property.StandardMemberProperty.LEVEL_NUMBER, "LNum"),
            rename(Property.StandardMemberProperty.DISPLAY_INFO, "DisplayInfo"));

    private static boolean DEFAULT_BOOLEAN;
    private static byte DEFAULT_BYTE;
    private static short DEFAULT_SHORT;
    private static int DEFAULT_INT;
    private static long DEFAULT_LONG;
    private static float DEFAULT_FLOAT;
    private static double DEFAULT_DOUBLE;

    public static StatementResponse toStatementResponse(CellSet cellSet, boolean omitDefaultSlicerInfo, boolean json) {

        List<String> queryCellPropertyNames = getQueryCellPropertyNames(cellSet);
        OlapInfoR olapInfo = getOlapInfo(cellSet, queryCellPropertyNames, omitDefaultSlicerInfo);
        List<Hierarchy> slicerAxisHierarchies = getSlicerAxisHierarchies(cellSet, omitDefaultSlicerInfo);
        AxesR axes = getAxes(cellSet, omitDefaultSlicerInfo, slicerAxisHierarchies);
        CellDataR cellData = getCellData(cellSet, queryCellPropertyNames, json);
        ExceptionR exception = null;
        MessagesR messages = null;

        MddatasetR mdDataSet = new MddatasetR(
            olapInfo,
            axes,
            cellData,
            exception,
            messages
        );

        return new StatementResponseR(mdDataSet, null);
    }

    private static List<Hierarchy> getSlicerAxisHierarchies(CellSet cellSet, boolean omitDefaultSlicerInfo) {
        Cube cube = cellSet.getMetaData().getCube();
        List<Hierarchy> hierarchies = new ArrayList();
        final List<CellSetAxis> axes = cellSet.getAxes();
        List<Hierarchy> axisHierarchyList = new ArrayList<>();
        axes.stream().forEach(a -> axisHierarchyList.addAll(getHierarchies(a)));

        if (omitDefaultSlicerInfo) {
            hierarchies.addAll(getHierarchies(cellSet.getFilterAxis()));
        } else {
            // The slicer axes contains the default hierarchy
            // of each dimension not seen on another axis.
            List<Dimension> unseenDimensionList =
                new ArrayList(cube.getDimensions() != null ? Arrays.asList(cube.getDimensions()) : List.of());
            for (Hierarchy hier1 : axisHierarchyList) {
                unseenDimensionList.remove(hier1.getDimension());
            }
            hierarchies = new ArrayList<>();
            for (Dimension dimension : unseenDimensionList) {
                for (Hierarchy hierarchy : dimension.getHierarchies()) {
                    hierarchies.add(hierarchy);
                }
            }
        }
        return hierarchies;
    }

    private static List<String> getQueryCellPropertyNames(CellSet cellSet) {
        final boolean matchCase =
            MondrianProperties.instance().CaseSensitive.get();
        List<String> queryCellPropertyNames = new ArrayList<>();
        final Statement statement = cellSet.getStatement();
        for(QueryComponent queryPart: statement.getQuery().getCellProperties()){
            org.eclipse.daanse.olap.api.query.component.CellProperty cellProperty = (org.eclipse.daanse.olap.api.query.component.CellProperty)queryPart;
            mondrian.olap.Property property = mondrian.olap.Property.lookup(cellProperty.toString(), matchCase);
            String propertyName = ((NameSegment)
                mondrian.olap.Util.parseIdentifier(cellProperty.toString()).get(0)).getName();
            queryCellPropertyNames.add(propertyName);
        }
        if(queryCellPropertyNames.isEmpty()) {
            queryCellPropertyNames.add("VALUE");
            queryCellPropertyNames.add("FORMATTED_VALUE");
        }
        return queryCellPropertyNames;
    }

    private static CellDataR getCellData(CellSet cellSet, List<String> queryCellPropertyNames, boolean json) {
        List< CellType > cell = new ArrayList<>();
        CellSetTypeR cellSetType = null;
        final int axisCount = cellSet.getAxes().size();
        List<Integer> pos = new ArrayList<>();
        for (int i = 0; i < axisCount; i++) {
            pos.add(-1);
        }
        int[] cellOrdinal = new int[] {0};

        int axisOrdinal = axisCount - 1;
        cell.addAll(recurse(cellSet, pos, axisOrdinal, cellOrdinal, queryCellPropertyNames, json));
        return new CellDataR(cell, cellSetType);
    }

    private static List<CellType> recurse(
        CellSet cellSet,
        List<Integer> pos,
        int axisOrdinal,
        int[] cellOrdinal,
        List<String> queryCellPropertyNames,
        boolean json
    )
    {
        List<CellType> cellList = new ArrayList<>();
        if (axisOrdinal < 0) {
            cellList.addAll(emitCell(cellSet, pos, cellOrdinal[0]++, queryCellPropertyNames, json));
        } else {
            CellSetAxis axis = cellSet.getAxes().get(axisOrdinal);
            List<Position> positions = axis.getPositions();
            for (int i = 0, n = positions.size(); i < n; i++) {
                pos.set(axisOrdinal, i);
                recurse(cellSet, pos, axisOrdinal - 1, cellOrdinal, queryCellPropertyNames, json);
            }
        }
        return cellList;
    }

    private static List<CellType> emitCell(
        CellSet cellSet,
        List<Integer> pos,
        int ordinal,
        List<String> queryCellPropertyNames,
        boolean json
    )
    {
        List<CellType> cellList = new ArrayList<>();
        Cell cell = cellSet.getCell(pos);

        Boolean allPropertyIsEmpty = true;
        for(String propertyName: queryCellPropertyNames) {
            if(((mondrian.olap4j.MondrianOlap4jCell)cell).getRolapCell().getPropertyValue(propertyName) != null) {
                allPropertyIsEmpty = false;
                break;
            }
        }

        if (cell.isNull() && allPropertyIsEmpty && ordinal != 0)
        {
            // Ignore null cell like MS AS, except for Oth ordinal
            return cellList;
        }

        ValueR val = null;
        List<CellInfoItem> any = new ArrayList<>();
        for(String propertyName: queryCellPropertyNames) {
            if (propertyName != null) {
                propertyName = propertyName.toUpperCase();
                if (propertyName.equals("CELL_ORDINAL")) {
                    continue;
                }
            }
            MondrianOlap4jCell mondrianOlap4jCell = (MondrianOlap4jCell)cell;
            Object value = mondrianOlap4jCell.getRolapCell().getPropertyValue(propertyName);
            if (value == null) {
                continue;
            }

            if (!json && Property.StandardCellProperty.VALUE.getName().equals(propertyName)) {
                if (cell.isNull()) {
                    // Return cell without value as in case of AS2005
                    continue;
                }
                final String dataType =
                    (String) cell.getPropertyValue(
                        Property.StandardCellProperty.DATATYPE.getName());

                //final ValueInfo vi = new ValueInfo(dataType, value);
                //will always get real datatype (see ValueInfo logic)
                final ValueInfo vi = new ValueInfo(null, value);

                final String valueType = vi.valueType;
                final String valueString;
                if (vi.value instanceof Double && (Double)vi.value == Double.POSITIVE_INFINITY){
                    valueString = "INF";
                } else if (vi.isDecimal) {
                    valueString =
                        XmlaUtil.normalizeNumericString(
                            vi.value.toString());
                } else {
                    valueString = vi.value.toString();
                }
                any.add(new CellInfoItemR(cellPropertyMap.get(propertyName).getAlias(),
                    valueString,
                    Optional.of(valueType)));
            } else {
                any.add(new CellInfoItemR(cellPropertyMap.get(propertyName).getAlias(),
                    value.toString(),
                    Optional.empty()));
            }
        }
        cellList.add(new CellTypeR(val, any, ordinal));
        return cellList;
    }


    private static AxesR getAxes(CellSet cellSet, boolean omitDefaultSlicerInfo, List<Hierarchy> slicerAxisHierarchies) {
        List<Axis> axisList = new ArrayList<>();
        final List<CellSetAxis> axes = cellSet.getAxes();
        for (int i = 0; i < axes.size(); i++) {
            final CellSetAxis axis = axes.get(i);
            final List<Property> props = getProps(axis.getAxisMetaData());
            axisList.add(getAxis(cellSet, axis, props, "Axis" + i));
        }

        ////////////////////////////////////////////
        // now generate SlicerAxis information
        //

        CellSetAxis slicerAxis = cellSet.getFilterAxis();
        if (omitDefaultSlicerInfo) {
            // We always write a slicer axis. There are two 'empty' cases:
            // zero positions (which happens when the WHERE clause evalutes
            // to an empty set) or one position containing a tuple of zero
            // members (which happens when there is no WHERE clause) and we
            // need to be able to distinguish between the two.

            if(!slicerAxisHierarchies.isEmpty()) {
                axisList.add(getAxis(cellSet,
                    slicerAxis,
                    getProps(slicerAxis.getAxisMetaData()),
                    "SlicerAxis"));
            }
        } else {
            List<Hierarchy> hierarchies = slicerAxisHierarchies;
            List<Type> setTypes = new ArrayList<>();
            List<TupleType> tuples = new ArrayList<>();
            List<MemberTypeR> mem = new ArrayList<>();
            tuples.add(new TupleTypeR(mem));
            setTypes.add(new TuplesTypeR(tuples));
            new AxisR(setTypes, "SlicerAxis");


            Map<String, Integer> memberMap = new HashMap<>();
            Member positionMember;
            final List<Position> slicerPositions =
                slicerAxis.getPositions();
            if (slicerPositions != null
                && !slicerPositions.isEmpty())
            {
                final Position pos0 = slicerPositions.get(0);
                int i = 0;
                for (Member member : pos0.getMembers()) {
                    memberMap.put(member.getHierarchy().getName(), i++);
                }
            }

            final List<Member> slicerMembers =
                slicerPositions.isEmpty()
                    ? List.of()
                    : slicerPositions.get(0).getMembers();
            for (Hierarchy hierarchy : hierarchies) {
                // Find which member is on the slicer.
                // If it's not explicitly there, use the default member.
                Member member = hierarchy.getDefaultMember();
                final Integer indexPosition =
                    memberMap.get(hierarchy.getName());
                if (indexPosition != null) {
                    positionMember = slicerMembers.get(indexPosition);
                } else {
                    positionMember = null;
                }
                for (Member slicerMember : slicerMembers) {
                    if (slicerMember.getHierarchy().equals(hierarchy)) {
                        member = slicerMember;
                        break;
                    }
                }

                if (member != null) {
                    if (positionMember != null) {
                        mem.add(
                        getMember(
                            positionMember, null,
                            slicerPositions.get(0), indexPosition,
                            getProps(slicerAxis.getAxisMetaData())));
                    } else {
                        slicerAxis(
                            member,
                            getProps(slicerAxis.getAxisMetaData()));
                    }
                } else {
                    LOGGER.warn(
                        "Can not create SlicerAxis: null default member for Hierarchy {}",
                        hierarchy.getUniqueName());
                }
            }
        }

        //
        ////////////////////////////////////////////


        return new AxesR(axisList);
    }

    private static MemberTypeR slicerAxis(Member member, List<Property> props) {
        List<CellInfoItem> any = new ArrayList<>();
        for (Property prop : props) {
            Object value;
            Property longProp = longProps.get(prop.getName());
            if (longProp == null) {
                longProp = prop;
            }
            if (longProp == Property.StandardMemberProperty.DISPLAY_INFO) {
                Integer childrenCard =
                    (Integer) member.getPropertyValue(
                        Property.StandardMemberProperty.CHILDREN_CARDINALITY.getName());
                // NOTE: don't know if this is correct for
                // SlicerAxis
                int displayInfo = 0xffff & childrenCard;
/*
                    int displayInfo =
                        calculateDisplayInfo((j == 0 ? null : positions[j - 1]),
                          (j + 1 == positions.length ? null : positions[j + 1]),
                          member, k, childrenCard.intValue());
*/
                value = displayInfo;
            } else if (longProp == Property.StandardMemberProperty.DEPTH) {
                value = member.getDepth();
            } else {
                value = member.getPropertyValue(longProp.getName());
            }
            if(value == null) {
                value = getDefaultValue(prop);
            }
            if (value != null) {
                any.add(new CellInfoItemR(encoder.encode(prop.getName()), value.toString(), Optional.empty()));
            }
        }
        return new MemberTypeR(any,
            member.getHierarchy().getUniqueName());
    }

    private static Object getHierarchyProperty(Member member, Property longProp) {
        IMondrianOlap4jProperty currentProperty =
            (IMondrianOlap4jProperty) longProp;
        String thisHierarchyName = member.getHierarchy().getName();
        String thatHierarchyName = currentProperty.getLevel()
            .getHierarchy().getName();
        if (thisHierarchyName.equals(thatHierarchyName)) {
            try {
                return member.getPropertyValue(currentProperty.getName());
            } catch (Exception e) {
                throw new XmlaException(
                    SERVER_FAULT_FC,
                    HSB_BAD_PROPERTIES_LIST_CODE,
                    HSB_BAD_PROPERTIES_LIST_FAULT_FS,
                    e);
            }
        }
        // if property doesn't belong to current hierarchy return null
        return null;
    }

    private static String getXsdType(Property property) {
        Datatype datatype = property.getDatatype();
        switch (datatype) {
            case UNSIGNED_INTEGER:
                return RowsetDefinition.Type.UNSIGNED_INTEGER.columnType;
            case DOUBLE:
                return RowsetDefinition.Type.DOUBLE.columnType;
            case LARGE_INTEGER:
                return RowsetDefinition.Type.LONG.columnType;
            case INTEGER:
                return RowsetDefinition.Type.INTEGER.columnType;
            case BOOLEAN:
                return RowsetDefinition.Type.BOOLEAN.columnType;
            default:
                return RowsetDefinition.Type.STRING.columnType;
        }
    }

    private static int calculateDisplayInfo(
        Position prevPosition,
        Position nextPosition,
        Member currentMember,
        int memberOrdinal,
        int childrenCount)
    {
        int displayInfo = 0xffff & childrenCount;

        if (nextPosition != null) {
            Member nextMember =
                nextPosition.getMembers().get(memberOrdinal);
            String nextParentUName = parentUniqueName(nextMember);
            if (currentMember.equals(nextMember.getParentMember())) {
                displayInfo |= 0x10000;
            }
        }
        if (prevPosition != null) {
            String currentParentUName = parentUniqueName(currentMember);
            Member prevMember =
                prevPosition.getMembers().get(memberOrdinal);
            String prevParentUName = parentUniqueName(prevMember);
            if (currentParentUName != null
                && currentParentUName.equals(prevParentUName))
            {
                displayInfo |= 0x20000;
            }
        }
        return displayInfo;
    }

    private static String parentUniqueName(Member member) {
        final Member parent = member.getParentMember();
        if (parent == null) {
            return null;
        }
        return parent.getUniqueName();
    }

    private static Object getDefaultValue(Property property) {
        Datatype datatype = property.getDatatype();
        switch (datatype) {
            case UNSIGNED_INTEGER:
                return DEFAULT_INT;
            case DOUBLE:
                return DEFAULT_DOUBLE;
            case LARGE_INTEGER:
                return DEFAULT_LONG;
            case INTEGER:
                return DEFAULT_INT;
            case BOOLEAN:
                return DEFAULT_BOOLEAN;
            default:
                return null;
        }
    }

    private static Axis getAxis(CellSet cellSet, CellSetAxis axis, List<Property> props, String name) {
        List<Type> setType = new ArrayList<>();

        List<MemberTypeR> memberList = new ArrayList<>();
        TupleType tupleType = new TupleTypeR(memberList);
        List<TupleType> tuples = new ArrayList<>();
        tuples.add(tupleType);
        TuplesType tuplesType = new TuplesTypeR(tuples);
        setType.add(tuplesType);

        HashMap<Level, ArrayList<Member>> levelMembers = new HashMap<>();

        for (Position p : axis.getPositions()) {
            for (Member member : p.getMembers()) {
                Level level = member.getLevel();
                if(!levelMembers.containsKey(level)){
                    levelMembers.put(level, new ArrayList<Member>());
                }
                levelMembers.get(level)
                    .add(((MondrianOlap4jMember)member).getOlapMember());
            }
        }
        /*
        mondrian.rolap.RolapConnection rolapConnection =
            ((mondrian.olap4j.MondrianOlap4jConnection)this.connection).getMondrianConnection();
        final Statement statement =
            cellSet.getStatement();

        for(Map.Entry<Level, ArrayList<Member>> entry : levelMembers.entrySet()) {
            Level level = entry.getKey();
            ArrayList<Member> members = entry.getValue();

            if (!members.isEmpty()
                && members.get(0).getLevel().getChildLevel() != null
                && !members.get(0).getLevel().isAll()) {
                Locus.execute(
                    rolapConnection,
                    "MondrianOlap4jMember.getChildMembers",
                    new Locus.Action<List<org.eclipse.daanse.olap.api.element.Member>>() {
                        @Override
                        public List<org.eclipse.daanse.olap.api.element.Member> execute() {
                            return
                                statement.getQuery().getSchemaReader(true).getMemberChildren(members);
                        }
                    });
            }
        }
         */

        List<Position> positions = axis.getPositions();
        Iterator<Position> pit = positions.iterator();
        Position prevPosition = null;
        Position position = pit.hasNext() ? pit.next() : null;
        Position nextPosition = pit.hasNext() ? pit.next() : null;
        while (position != null) {


            int k = 0;
            for (Member member : position.getMembers()) {
                memberList.add(getMember(
                    member, prevPosition, nextPosition, k++, props));
            }
            prevPosition = position;
            position = nextPosition;
            nextPosition = pit.hasNext() ? pit.next() : null;
        }
        return new AxisR(setType, name);
    }

    private static MemberTypeR getMember(Member member, Position prevPosition, Position nextPosition, int k, List<Property> props) {
        List<CellInfoItem> any = new ArrayList<>();
        for (final Property prop : props) {
            Object value = null;
            Property longProp = (longProps.get(prop.getName()) != null)
                ? longProps.get(prop.getName()) : prop;
            if (longProp == Property.StandardMemberProperty.DISPLAY_INFO) {
                Integer childrenCard = (Integer) member
                    .getPropertyValue(
                        Property.StandardMemberProperty.CHILDREN_CARDINALITY.getName());
                value = calculateDisplayInfo(
                    prevPosition, nextPosition, member, k, childrenCard);
            } else if (longProp == Property.StandardMemberProperty.DEPTH) {
                value = member.getDepth();
            } else {
                if(longProp instanceof IMondrianOlap4jProperty currentProperty) {
                    String thisHierarchyName = member.getHierarchy().getName();
                    String thatHierarchyName = currentProperty.getLevel()
                        .getHierarchy().getName();
                    if (thisHierarchyName.equals(thatHierarchyName)) {
                        value = getHierarchyProperty(member, longProp);
                    }
                    else {
                        // Property belongs to other hierarchy
                        continue;
                    }
                }
                else {
                    value = member.getPropertyValue(longProp.getName());
                }
            }
            if(longProp != prop && value == null) {
                value = getDefaultValue(prop);
            }
            if (value != null) {
                if(longProp instanceof mondrian.olap4j.IMondrianOlap4jProperty) {
                    any.add(new CellInfoItemR(encoder.encode(prop.getName()),
                        value.toString(),
                        Optional.of(getXsdType(prop))));
                }
                else {
                    any.add(new CellInfoItemR(encoder.encode(prop.getName()),
                        value.toString(),
                        Optional.empty()));
                }
            }
        }
        return new MemberTypeR(any,
            member.getHierarchy().getUniqueName());

    }

    private static OlapInfoR getOlapInfo(CellSet cellSet, List<String> queryCellPropertyNames, boolean omitDefaultSlicerInfo) {
        Cube cube = cellSet.getMetaData().getCube();
        String cubeName = cube.getName();
        Instant lastDataUpdate = Instant.now(); //TODO get from getMondrianOlap4jSchema()
        Instant lastSchemaUpdate = Instant.now(); //TODO
        OlapInfoCubeR olapInfoCube = new OlapInfoCubeR(cubeName, lastDataUpdate, lastSchemaUpdate);
        List<OlapInfoCube> cubes = List.of(olapInfoCube);
        CubeInfoR cubeInfo = new CubeInfoR(cubes);

        AxesInfoR axesInfo = getAxesInfo(cellSet, cube, omitDefaultSlicerInfo);

        CellInfoR cellInfo = getCellInfo(cellSet, queryCellPropertyNames);

        return new OlapInfoR(cubeInfo, axesInfo, cellInfo);
    }

    private static CellInfoR getCellInfo(CellSet cellSet, List<String> queryCellPropertyNames) {
        List<CellInfoItem> any = new ArrayList<>();
        for(String cellPropertyName: queryCellPropertyNames){
            if(cellPropertyName != null) {
                cellPropertyName = cellPropertyName.toUpperCase();
            }
            CellProperty cellProperty = cellPropertyMap.get(cellPropertyName);
            if (cellProperty != null) {
                Optional<String> oType = cellProperty.getXsdType() != null ? Optional.of(cellProperty.getXsdType()) : Optional.empty();
                any.add(new CellInfoItemR(cellProperty.getAlias(), cellPropertyName, oType));
            }
        }
        return new CellInfoR(any);
    }

    private static AxesInfoR getAxesInfo(CellSet cellSet, Cube cube, boolean omitDefaultSlicerInfo)
    {
        List<AxisInfo> axisInfo = new ArrayList<>();
        final List<CellSetAxis> axes = cellSet.getAxes();
        List<Hierarchy> axisHierarchyList = new ArrayList<>();
        for (int i = 0; i < axes.size(); i++) {
            axisInfo.add(getAxisInfo(axes.get(i), "Axis" + i));
            axisHierarchyList.addAll(getHierarchies(axes.get(i)));
        }
        ///////////////////////////////////////////////
        // create AxesInfo for slicer axes
        //
        List<Hierarchy> hierarchies = new ArrayList<>();
        CellSetAxis slicerAxis = cellSet.getFilterAxis();
        if (omitDefaultSlicerInfo) {
            axisInfo.add(getAxisInfo(slicerAxis, "SlicerAxis"));
            hierarchies.addAll(getHierarchies(slicerAxis));
        } else {
            // The slicer axes contains the default hierarchy
            // of each dimension not seen on another axis.
            List<Dimension> unseenDimensionList =
                new ArrayList(cube.getDimensions() != null ? Arrays.asList(cube.getDimensions()) : List.of());
            for (Hierarchy hier1 : axisHierarchyList) {
                unseenDimensionList.remove(hier1.getDimension());
            }
            hierarchies = new ArrayList<>();
            for (Dimension dimension : unseenDimensionList) {
                for (Hierarchy hierarchy : dimension.getHierarchies()) {
                    hierarchies.add(hierarchy);
                }
            }
            axisInfo.add(new AxisInfoR(getHierarchyInfoList(hierarchies, getProps(slicerAxis.getAxisMetaData())), "SlicerAxis" ));
        }
        //slicerAxisHierarchies = hierarchies;
        return new AxesInfoR(axisInfo);
    }

    private static List<Hierarchy> getHierarchies(CellSetAxis axis) {
        List<Hierarchy> hierarchies = new ArrayList();
        Iterator<Position> it = axis.getPositions().iterator();
        if (it.hasNext()) {
            final Position position = it.next();
            hierarchies = new ArrayList<>();
            for (Member member : position) {
                hierarchies.add(member.getHierarchy());
            }
        } else {
            hierarchies = axis.getAxisMetaData().getHierarchies();
        }
        return hierarchies;

    }

    private static AxisInfo getAxisInfo(CellSetAxis axis, String axisName) {
        List<Hierarchy> hierarchies;
        List<Property> props = new ArrayList<>(getProps(axis.getAxisMetaData()));
        Iterator<Position> it = axis.getPositions().iterator();
        if (it.hasNext()) {
            final Position position = it.next();
            hierarchies = new ArrayList<>();
            for (Member member : position) {
                hierarchies.add(member.getHierarchy());
            }
        } else {
            hierarchies = axis.getAxisMetaData().getHierarchies();
        }

        // remove a property without a valid associated hierarchy
        props.removeIf(prop -> !isValidProp(axis.getPositions(), prop));

        return new AxisInfoR(getHierarchyInfoList(hierarchies, props), axisName );
    }

    private static List<HierarchyInfo> getHierarchyInfoList(List<Hierarchy> hierarchies, List<Property> props) {
        List<HierarchyInfo> result = new ArrayList<>();
        for (Hierarchy hierarchy : hierarchies) {
            List<CellInfoItem> ciiList = new ArrayList<>();
            for (final Property prop : props) {
                if (prop instanceof IMondrianOlap4jProperty iMondrianProp) {
                    Optional<CellInfoItem> op = getCellInfoItemProperty(hierarchy, iMondrianProp);
                    if (op.isPresent()) {
                        ciiList.add(op.get());
                    }
                } else {
                    ciiList.add(getCellInfoItem(hierarchy, prop));
                }
            }
            result.add(new HierarchyInfoR(ciiList, hierarchy.getUniqueName()));
        }
        return result;

    }


    private static Optional<CellInfoItem> getCellInfoItemProperty(
        Hierarchy hierarchy,
        final IMondrianOlap4jProperty prop
    )
    {
        String thisHierarchyName = hierarchy.getName();
        String thatHierarchiName = prop.getLevel()
            .getHierarchy().getName();
        if (thisHierarchyName.equals(thatHierarchiName)) {
            return Optional.of(getCellInfoItem(hierarchy, prop));
        }
        return Optional.empty();
    }

    private static CellInfoItem getCellInfoItem(
        Hierarchy hierarchy,
        final Property prop
    )
    {
        final String encodedProp = encoder
            .encode(prop.getName());
        final Object[] attributes = getAttributes(
            prop, hierarchy);
        return new CellInfoItemR(encodedProp, attributes != null && attributes.length > 0 ?  attributes[0].toString() : null,
            Optional.empty());
    }

    private static Object[] getAttributes(Property prop, Hierarchy hierarchy) {
        Property longProp = longProps.get(prop.getName());
        if (longProp == null) {
            longProp = prop;
        }
        List<Object> values = new ArrayList<>();
        values.add("name");
        values.add(
            new StringBuilder(hierarchy.getUniqueName())
                .append(".")
                .append(Util.quoteMdxIdentifier(longProp.getName())).toString());
        if (!(longProp instanceof mondrian.olap4j.IMondrianOlap4jProperty)) {
            values.add("type");
            values.add(getXsdType(longProp));
        }
        return values.toArray();
    }

    private static List<Property> getProps(CellSetAxisMetaData queryAxis) {
        if (queryAxis == null) {
            return defaultProps;
        }
        return CompositeList.of(
            defaultProps,
            queryAxis.getProperties());
    }

    private static boolean isValidProp(List<Position> positions, Property prop) {
        if(!(prop instanceof IMondrianOlap4jProperty)){
            return true;
        }
        for (Position pos : positions){
            if(pos.getMembers().stream()
                .anyMatch(member -> Objects.nonNull(getHierarchyProperty(member, prop)))){
                return true;
            }
        }
        return false;
    }

    static class CellProperty {
        String name;
        String alias;
        String xsdType;

        public CellProperty(String name, String alias, String xsdType) {
            this.name = name;
            this.alias = alias;
            this.xsdType = xsdType;
        }

        public String getName() {return this.name; }
        public String getAlias() {return this.alias; }
        public String getXsdType() {return this.xsdType; }
    }

    private static Property rename(
        final Property property,
        final String name)
    {
        return new Property() {
            @Override
            public Datatype getDatatype() {
                return property.getDatatype();
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }
}
