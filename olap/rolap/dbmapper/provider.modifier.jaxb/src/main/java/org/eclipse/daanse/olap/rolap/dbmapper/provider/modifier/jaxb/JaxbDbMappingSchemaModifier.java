package org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier.jaxb;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggColumnName;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggExclude;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggForeignKey;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggLevelProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggMeasureFactCount;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMemberProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCellFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingClosure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingColumnDef;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDimensionGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughAction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughElement;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingExpressionView;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingFormula;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchyGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHint;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMemberGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMemberReaderParameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingNamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingParameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingPrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRelation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRelationOrJoin;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRole;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRoleUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRow;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSQL;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchemaGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingScript;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingUnion;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingUserDefinedFunction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingValue;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCubeMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingWritebackColumn;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingWritebackTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.AccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.HideMemberIfEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.InternalTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.MeasureDataTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.MemberGrantAccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.ParameterTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.PropertyTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier.AbstractDbMappingSchemaModifier;

public class JaxbDbMappingSchemaModifier extends AbstractDbMappingSchemaModifier {

	protected JaxbDbMappingSchemaModifier(MappingSchema mappingSchema) {
		super(mappingSchema);
	}

	@Override
	protected MappingSchema new_Schema(String name, String description, String measuresCaption, String defaultRole,
			List<MappingAnnotation> annotations, List<MappingParameter> parameters,
			List<MappingPrivateDimension> dimensions, List<MappingCube> cubes, List<MappingVirtualCube> virtualCubes,
			List<MappingNamedSet> namedSets, List<MappingRole> roles,
			List<MappingUserDefinedFunction> userDefinedFunctions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingAnnotation new_Annotation(String name, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingParameter new_parameter(String name, String description, ParameterTypeEnum type,
			boolean modifiable, String defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingPrivateDimension new_PrivateDimension(String name, DimensionTypeEnum type, String caption,
			String description, String foreignKey, boolean highCardinality, List<MappingAnnotation> annotations,
			List<MappingHierarchy> hierarchies, boolean visible, String usagePrefix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingHierarchy new_Hierarchy(String name, String caption, String description,
			List<MappingAnnotation> annotations, List<MappingLevel> levels,
			List<MappingMemberReaderParameter> memberReaderParameters, boolean hasAll, String allMemberName,
			String allMemberCaption, String allLevelName, String primaryKey, String primaryKeyTable,
			String defaultMember, String memberReaderClass, String uniqueKeyLevelName, boolean visible,
			String displayFolder, MappingRelationOrJoin relation, String origin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingMemberReaderParameter new_MemberReaderParameter(String name, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingCube new_Cube(String name, String caption, String description, String defaultMeasure,
			List<MappingAnnotation> annotations, List<MappingCubeDimension> dimensionUsageOrDimensions,
			List<MappingMeasure> measures, List<MappingCalculatedMember> calculatedMembers,
			List<MappingNamedSet> namedSets, List<MappingDrillThroughAction> drillThroughActions,
			List<MappingWritebackTable> writebackTables, boolean enabled, boolean cache, boolean visible,
			MappingRelation fact, List<MappingAction> actions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingAction new_MappingAction(String name, String caption, String description,
			List<MappingAnnotation> annotations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingRole new_MappingRole(List<MappingAnnotation> annotations, List<MappingSchemaGrant> schemaGrants,
			MappingUnion union, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingFormula new_Formula(String cdata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingCellFormatter new_CellFormatter(String className, MappingScript script) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingScript new_Script(String language, String cdata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingCalculatedMemberProperty new_CalculatedMemberProperty(String name, String caption,
			String description, String expression, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingCalculatedMember new_CalculatedMember(String name, String formatString, String caption,
			String description, String dimension, boolean visible, String displayFolder,
			List<MappingAnnotation> annotations, String formula,
			List<MappingCalculatedMemberProperty> calculatedMemberProperties, String hierarchy, String parent,
			MappingCellFormatter cellFormatter, MappingFormula formulaElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingVirtualCubeMeasure new_VirtualCubeMeasure(String name, String cubeName, boolean visible,
			List<MappingAnnotation> annotations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingVirtualCubeDimension new_VirtualCubeDimension(String name, String cubeName,
			List<MappingAnnotation> annotations, String foreignKey, boolean highCardinality, String caption,
			boolean visible, String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingCubeUsage new_CubeUsage(String cubeName, boolean ignoreUnrelatedDimensions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingVirtualCube new_VirtualCube(String name, String caption, String description, String defaultMeasure,
			boolean enabled, List<MappingAnnotation> annotations, List<MappingCubeUsage> cubeUsages,
			List<MappingVirtualCubeDimension> virtualCubeDimensions,
			List<MappingVirtualCubeMeasure> virtualCubeMeasures, List<MappingCalculatedMember> calculatedMembers,
			List<MappingNamedSet> namedSets, boolean visible) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingNamedSet new_NamedSet(String name, String caption, String description, String formula,
			List<MappingAnnotation> annotations, String displayFolder, MappingFormula formulaElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingUnion new_Union(List<MappingRoleUsage> roleUsages) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingRoleUsage new_RoleUsage(String roleName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingMemberGrant new_MemberGrant(String member, MemberGrantAccessEnum access) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingHierarchyGrant new_HierarchyGrant(String hierarchy, AccessEnum access, String topLevel,
			String bottomLevel, String rollupPolicy, List<MappingMemberGrant> memberGrants) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingDimensionGrant new_DimensionGrant(AccessEnum access, String dimension) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingCubeGrant new_CubeGrant(String cube, String access, List<MappingDimensionGrant> dimensionGrants,
			List<MappingHierarchyGrant> hierarchyGrants) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingSchemaGrant new_SchemaGrant(List<MappingCubeGrant> schemaGrantCubeGrants, AccessEnum access) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingHint new_Hint(String content, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingAggMeasureFactCount new_AggMeasureFactCount(String factColumn, String column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingAggLevelProperty new_AggLevelProperty(String name, String column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingAggLevel new_AggLevel(String column, String name, String ordinalColumn, String nameColumn,
			String captionColumn, Boolean collapsed, List<MappingAggLevelProperty> properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingAggMeasure new_AggMeasure(String column, String name, String rollupType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingAggForeignKey new_AggForeignKey(String factColumn, String aggColumn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingAggColumnName new_AggColumnName(String column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingAggTable new_AggPattern(String pattern, MappingAggColumnName aggFactCount,
			List<MappingAggColumnName> aggIgnoreColumns, List<MappingAggForeignKey> aggForeignKeys,
			List<MappingAggMeasure> aggMeasures, List<MappingAggLevel> aggLevels, List<MappingAggExclude> aggExcludes,
			boolean ignorecase, List<MappingAggMeasureFactCount> measuresFactCounts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingAggTable new_AggName(String name, MappingAggColumnName aggFactCount,
			List<MappingAggMeasure> aggMeasures, List<MappingAggColumnName> aggIgnoreColumns,
			List<MappingAggForeignKey> aggForeignKeys, List<MappingAggLevel> aggLevels, boolean ignorecase,
			List<MappingAggMeasureFactCount> measuresFactCounts, String approxRowCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingAggExclude new_AggExclude(String pattern, String name, boolean ignorecase) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingSQL new_SQL(String content, String dialect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingValue new_Value(String column, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingRow new_Row(List<MappingValue> values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingTable new_Table(String schema, String name, String alias, List<MappingHint> hints, MappingSQL sql,
			List<MappingAggExclude> aggExcludes, List<MappingAggTable> aggTables) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingColumnDef new_ColumnDef(String name, TypeEnum type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingRelation new_View(String alias, List<MappingSQL> sqls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingRelation new_InlineTable(List<MappingColumnDef> columnDefs, List<MappingRow> rows, String alias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingWritebackColumn new_WritebackMeasure(String name, String column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingWritebackColumn new_WritebackAttribute(String dimension, String column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingWritebackTable new_WritebackTable(String schema, String name,
			List<MappingWritebackColumn> columns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingDrillThroughElement new_MappingDrillThroughAttribute(String dimension, String level,
			String hierarchy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingDrillThroughElement new_DrillThroughMeasure(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingDrillThroughAction new_DrillThroughAction(String name, String caption, String description,
			Boolean defaultt, List<MappingAnnotation> annotations,
			List<MappingDrillThroughElement> drillThroughElements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingMeasure new_Measure(String name, String column, MeasureDataTypeEnum datatype, String formatString,
			String aggregator, String formatter, String caption, String description, boolean visible,
			String displayFolder, List<MappingAnnotation> annotations, MappingExpressionView measureExpression,
			List<MappingCalculatedMemberProperty> calculatedMemberProperties, MappingElementFormatter cellFormatter,
			String backColor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingCubeDimension new_CubeDimension(List<MappingAnnotation> annotations, String name,
			String foreignKey, boolean highCardinality, String caption, boolean visible, String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingElementFormatter new_ElementFormatter(String className, MappingScript script) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingProperty new_Property(String name, String column, PropertyTypeEnum type, String formatter,
			String caption, String description, boolean dependsOnLevelValue,
			MappingElementFormatter propertyFormatter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingClosure new_Closure(MappingTable table, String parentColumn, String childColumn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingLevel new_Level(String name, String table, String column, String nameColumn, String ordinalColumn,
			String parentColumn, String nullParentValue, TypeEnum type, String approxRowCount, boolean uniqueMembers,
			LevelTypeEnum levelType, HideMemberIfEnum hideMemberIf, String formatter, String caption,
			String description, String captionColumn, List<MappingAnnotation> annotations,
			MappingExpressionView keyExpression, MappingExpressionView nameExpression,
			MappingExpressionView captionExpression, MappingExpressionView ordinalExpression,
			MappingExpressionView parentExpression, MappingClosure closure, List<MappingProperty> properties,
			boolean visible, InternalTypeEnum internalType, MappingElementFormatter memberFormatter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingJoin new_Join(List<MappingRelationOrJoin> relations, String leftAlias, String leftKey,
			String rightAlias, String rightKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MappingUserDefinedFunction new_UserDefinedFunction(String name, String className, MappingScript script) {
		// TODO Auto-generated method stub
		return null;
	}

}