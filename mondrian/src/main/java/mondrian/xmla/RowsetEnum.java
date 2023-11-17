/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package mondrian.xmla;

import mondrian.olap.Util;
import org.eclipse.daanse.olap.api.DataType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.eigenbase.xom.XOMUtil.discard;

public enum RowsetEnum {

    /**
     * Returns a list of XML for Analysis data sources
     * available on the server or Web Service. (For an
     * example of how these may be published, see
     * "XML for Analysis Implementation Walkthrough"
     * in the XML for Analysis specification.)
     *
     *  http://msdn2.microsoft.com/en-us/library/ms126129(SQL.90).aspx
     *
     *
     * restrictions
     *
     * Not supported
     */
    DISCOVER_DATASOURCES(
        0,
        "06C03D41-F66D-49F3-B1B8-987F7AF4CF18",
        "Returns a list of XML for Analysis data sources available on the "
            + "server or Web Service.",
        List.of(
            DiscoverDatasourcesRowset.DataSourceName,
            DiscoverDatasourcesRowset.DataSourceDescription,
            DiscoverDatasourcesRowset.URL,
            DiscoverDatasourcesRowset.DataSourceInfo,
            DiscoverDatasourcesRowset.ProviderName,
            DiscoverDatasourcesRowset.ProviderType,
            DiscoverDatasourcesRowset.AuthenticationMode
            )),

    /**
     * Note that SQL Server also returns the data-mining columns.
     *
     *
     * restrictions
     *
     * Not supported
     */
    DISCOVER_SCHEMA_ROWSETS(
        2,
        "EEA0302B-7922-4992-8991-0E605D0E5593",
        "Returns the names, values, and other information of all supported "
            + "RequestType enumeration values.",
        List.of(
            DiscoverSchemaRowsetsRowset.SCHEMA_NAME_COLUMN,
            DiscoverSchemaRowsetsRowset.SCHEMA_GUID_COLUMN,
            DiscoverSchemaRowsetsRowset.RESTRICTIONS_COLUMN,
            DiscoverSchemaRowsetsRowset.DESCRIPTION_COLUMN,
            DiscoverSchemaRowsetsRowset.RESTRICTIONS_MASK_COLUMN
        )),

    /**
     *
     *
     *
     * restrictions
     *
     * Not supported
     */
    DISCOVER_ENUMERATORS(
        3,
        "55A9E78B-ACCB-45B4-95A6-94C5065617A7",
        "Returns a list of names, data types, and enumeration values for "
            + "enumerators supported by the provider of a specific data source.",
        List.of(
            DiscoverEnumeratorsRowset.EnumName,
            DiscoverEnumeratorsRowset.EnumDescription,
            DiscoverEnumeratorsRowset.EnumType,
            DiscoverEnumeratorsRowset.ElementName,
            DiscoverEnumeratorsRowset.ElementDescription,
            DiscoverEnumeratorsRowset.ElementValue
        )),

    /**
     *
     *
     *
     * restrictions
     *
     * Not supported
     */
    DISCOVER_PROPERTIES(
        1,
        "4B40ADFB-8B09-4758-97BB-636E8AE97BCF",
        "Returns a list of information and values about the requested "
            + "properties that are supported by the specified data source "
            + "provider.",
        List.of(
            DiscoverPropertiesRowset.PropertyName,
            DiscoverPropertiesRowset.PropertyDescription,
            DiscoverPropertiesRowset.PropertyType,
            DiscoverPropertiesRowset.PropertyAccessType,
            DiscoverPropertiesRowset.IsRequired,
            DiscoverPropertiesRowset.Value
        )),

    /**
     *
     *
     *
     * restrictions
     *
     * Not supported
     */
    DISCOVER_KEYWORDS(
        4,
        "1426C443-4CDD-4A40-8F45-572FAB9BBAA1",
        "Returns an XML list of keywords reserved by the provider.",
        List.of(
            DiscoverKeywordsRowset.Keyword
        )),

    /**
     *
     *
     *
     * restrictions
     *
     * Not supported
     */
    DISCOVER_LITERALS(
        5,
        "C3EF5ECB-0A07-4665-A140-B075722DBDC2",
        "Returns information about literals supported by the provider.",
        List.of(
            DiscoverLiteralsRowset.LiteralName,
            DiscoverLiteralsRowset.LiteralValue,
            DiscoverLiteralsRowset.LiteralInvalidChars,
            DiscoverLiteralsRowset.LiteralInvalidStartingChars,
            DiscoverLiteralsRowset.LiteralMaxLength,
            DiscoverLiteralsRowset.LiteralNameEnumValue
        )),

    /**
     *
     *
     *
     * restrictions
     *
     * Not supported
     */
    DISCOVER_XML_METADATA(
        23,
        "3444B255-171E-4CB9-AD98-19E57888A75F",
        "Returns an XML document describing a requested object. " +
            "The rowset that is returned always consists of one row and one column.",
        List.of(
            DiscoverXmlMetadataRowset.METADATA,
            // Restrictions
            DiscoverXmlMetadataRowset.DatabaseID
        )),

    /**
     *
     *
     *
     * restrictions
     *
     * Not supported
     */
    DBSCHEMA_CATALOGS(
        6,
        "C8B52211-5CF3-11CE-ADE5-00AA0044773D",
        "Identifies the physical attributes associated with catalogs "
            + "accessible from the provider.",
        List.of(
            DbschemaCatalogsRowset.CatalogName,
            DbschemaCatalogsRowset.Description,
            DbschemaCatalogsRowset.Roles,
            DbschemaCatalogsRowset.DateModified
        )),

    /**
     *
     *
     *
     * restrictions
     *
     * Not supported
     *    COLUMN_OLAP_TYPE
     */
    DBSCHEMA_COLUMNS(
        7,
        "C8B52214-5CF3-11CE-ADE5-00AA0044773D", null,
        List.of(
            DbschemaColumnsRowset.TableCatalog,
            DbschemaColumnsRowset.TableSchema,
            DbschemaColumnsRowset.TableName,
            DbschemaColumnsRowset.ColumnName,
            DbschemaColumnsRowset.OrdinalPosition,
            DbschemaColumnsRowset.ColumnHasDefault,
            DbschemaColumnsRowset.ColumnFlags,
            DbschemaColumnsRowset.IsNullable,
            DbschemaColumnsRowset.DataType,
            DbschemaColumnsRowset.CharacterMaximumLength,
            DbschemaColumnsRowset.CharacterOctetLength,
            DbschemaColumnsRowset.NumericPrecision,
            DbschemaColumnsRowset.NumericScale
        )),

    /**
     *
     *
     *
     * restrictions
     *
     * Not supported
     */
    DBSCHEMA_PROVIDER_TYPES(
        8, "C8B5222C-5CF3-11CE-ADE5-00AA0044773D", null,
        List.of(
            DbschemaProviderTypesRowset.TypeName,
            DbschemaProviderTypesRowset.DataType,
            DbschemaProviderTypesRowset.ColumnSize,
            DbschemaProviderTypesRowset.LiteralPrefix,
            DbschemaProviderTypesRowset.LiteralSuffix,
            DbschemaProviderTypesRowset.IsNullable,
            DbschemaProviderTypesRowset.CaseSensitive,
            DbschemaProviderTypesRowset.Searchable,
            DbschemaProviderTypesRowset.UnsignedAttribute,
            DbschemaProviderTypesRowset.FixedPrecScale,
            DbschemaProviderTypesRowset.AutoUniqueValue,
            DbschemaProviderTypesRowset.IsLong,
            DbschemaProviderTypesRowset.BestMatch
        )),

    DBSCHEMA_SCHEMATA(
        8, "c8b52225-5cf3-11ce-ade5-00aa0044773d", null,
        List.of(
            DbschemaSchemataRowset.CatalogName,
            DbschemaSchemataRowset.SchemaName,
            DbschemaSchemataRowset.SchemaOwner
        )),

    /**
     * http://msdn2.microsoft.com/en-us/library/ms126299(SQL.90).aspx
     *
     * restrictions:
     *   TABLE_CATALOG Optional
     *   TABLE_SCHEMA Optional
     *   TABLE_NAME Optional
     *   TABLE_TYPE Optional
     *   TABLE_OLAP_TYPE Optional
     *
     * Not supported
     */
    DBSCHEMA_TABLES(
        9, "C8B52229-5CF3-11CE-ADE5-00AA0044773D", null,
        List.of(
            DbschemaTablesRowset.TableCatalog,
            DbschemaTablesRowset.TableSchema,
            DbschemaTablesRowset.TableName,
            DbschemaTablesRowset.TableType,
            DbschemaTablesRowset.TableGuid,
            DbschemaTablesRowset.Description,
            DbschemaTablesRowset.TablePropId,
            DbschemaTablesRowset.DateCreated,
            DbschemaTablesRowset.DateModified
            //TableOlapType,
        )),

    DBSCHEMA_SOURCE_TABLES(
        23, "8c3f5858-2742-4976-9d65-eb4d493c693e", null,
        List.of(
            DbschemaSourceTablesRowset.TableCatalog,
            DbschemaSourceTablesRowset.TableSchema,
            DbschemaSourceTablesRowset.TableName,
            DbschemaSourceTablesRowset.TableType
        )),

    /**
     * http://msdn.microsoft.com/library/en-us/oledb/htm/
     * oledbtables_info_rowset.asp
     *
     *
     * restrictions
     *
     * Not supported
     */
    DBSCHEMA_TABLES_INFO(
        10, "c8b522e0-5cf3-11ce-ade5-00aa0044773d", null,
        List.of(
            DbschemaTablesInfoRowset.TableCatalog,
            DbschemaTablesInfoRowset.TableSchema,
            DbschemaTablesInfoRowset.TableName,
            DbschemaTablesInfoRowset.TableType,
            DbschemaTablesInfoRowset.TableGuid,
            DbschemaTablesInfoRowset.Bookmarks,
            DbschemaTablesInfoRowset.BookmarkType,
            DbschemaTablesInfoRowset.BookmarkDataType,
            DbschemaTablesInfoRowset.BookmarkMaximumLength,
            DbschemaTablesInfoRowset.BookmarkInformation,
            DbschemaTablesInfoRowset.TableVersion,
            DbschemaTablesInfoRowset.Cardinality,
            DbschemaTablesInfoRowset.Description,
            DbschemaTablesInfoRowset.TablePropId
        )),

    /**
     * http://msdn2.microsoft.com/en-us/library/ms126032(SQL.90).aspx
     *
     * restrictions
     *   CATALOG_NAME Optional
     *   SCHEMA_NAME Optional
     *   CUBE_NAME Mandatory
     *   ACTION_NAME Optional
     *   ACTION_TYPE Optional
     *   COORDINATE Mandatory
     *   COORDINATE_TYPE Mandatory
     *   INVOCATION
     *      (Optional) The INVOCATION restriction column defaults to the
     *      value of MDACTION_INVOCATION_INTERACTIVE. To retrieve all
     *      actions, use the MDACTION_INVOCATION_ALL value in the
     *      INVOCATION restriction column.
     *   CUBE_SOURCE
     *      (Optional) A bitmap with one of the following valid values:
     *
     *      1 CUBE
     *      2 DIMENSION
     *
     *      Default restriction is a value of 1.
     *
     * Not supported
     */
    MDSCHEMA_ACTIONS(
        11, "A07CCD08-8148-11D0-87BB-00C04FC33942", null, List.of(
        MdschemaActionsRowset.CatalogName,
        MdschemaActionsRowset.SchemaName,
        MdschemaActionsRowset.CubeName,
        MdschemaActionsRowset.ActionName,
        MdschemaActionsRowset.Coordinate,
        MdschemaActionsRowset.CoordinateType
    )),

    /**
     * http://msdn2.microsoft.com/en-us/library/ms126271(SQL.90).aspx
     *
     * restrictions
     *   CATALOG_NAME Optional.
     *   SCHEMA_NAME Optional.
     *   CUBE_NAME Optional.
     *   CUBE_TYPE
     *      (Optional) A bitmap with one of these valid values:
     *      1 CUBE
     *      2 DIMENSION
     *     Default restriction is a value of 1.
     *   BASE_CUBE_NAME Optional.
     *
     * Not supported
     *   CREATED_ON
     *   LAST_SCHEMA_UPDATE
     *   SCHEMA_UPDATED_BY
     *   LAST_DATA_UPDATE
     *   DATA_UPDATED_BY
     *   ANNOTATIONS
     */
    MDSCHEMA_CUBES(
        12, "C8B522D8-5CF3-11CE-ADE5-00AA0044773D", null,
        List.of(
            MdschemaCubesRowset.CatalogName,
            MdschemaCubesRowset.SchemaName,
            MdschemaCubesRowset.CubeName,
            MdschemaCubesRowset.CubeType,
            MdschemaCubesRowset.CubeGuid,
            MdschemaCubesRowset.CreatedOn,
            MdschemaCubesRowset.LastSchemaUpdate,
            MdschemaCubesRowset.SchemaUpdatedBy,
            MdschemaCubesRowset.LastDataUpdate,
            MdschemaCubesRowset.DataUpdatedBy,
            MdschemaCubesRowset.Description,
            MdschemaCubesRowset.IsDrillthroughEnabled,
            MdschemaCubesRowset.IsLinkable,
            MdschemaCubesRowset.IsWriteEnabled,
            MdschemaCubesRowset.IsSqlEnabled,
            MdschemaCubesRowset.CubeCaption,
            MdschemaCubesRowset.BaseCubeName,
            MdschemaCubesRowset.Dimensions,
            MdschemaCubesRowset.Sets,
            MdschemaCubesRowset.Measures,
            MdschemaCubesRowset.CubeSource
        )),

    /**
     * http://msdn2.microsoft.com/en-us/library/ms126180(SQL.90).aspx
     * http://msdn2.microsoft.com/en-us/library/ms126180.aspx
     *
     * restrictions
     *    CATALOG_NAME Optional.
     *    SCHEMA_NAME Optional.
     *    CUBE_NAME Optional.
     *    DIMENSION_NAME Optional.
     *    DIMENSION_UNIQUE_NAME Optional.
     *    CUBE_SOURCE (Optional) A bitmap with one of the following valid
     *    values:
     *      1 CUBE
     *      2 DIMENSION
     *    Default restriction is a value of 1.
     *
     *    DIMENSION_VISIBILITY (Optional) A bitmap with one of the following
     *    valid values:
     *      1 Visible
     *      2 Not visible
     *    Default restriction is a value of 1.
     */
    MDSCHEMA_DIMENSIONS(
        13, "C8B522D9-5CF3-11CE-ADE5-00AA0044773D", null,
        List.of(
            MdschemaDimensionsRowset.CatalogName,
            MdschemaDimensionsRowset.SchemaName,
            MdschemaDimensionsRowset.CubeName,
            MdschemaDimensionsRowset.DimensionName,
            MdschemaDimensionsRowset.DimensionUniqueName,
            MdschemaDimensionsRowset.DimensionGuid,
            MdschemaDimensionsRowset.DimensionCaption,
            MdschemaDimensionsRowset.DimensionOrdinal,
            MdschemaDimensionsRowset.DimensionType,
            MdschemaDimensionsRowset.DimensionCardinality,
            MdschemaDimensionsRowset.DefaultHierarchy,
            MdschemaDimensionsRowset.Description,
            MdschemaDimensionsRowset.IsVirtual,
            MdschemaDimensionsRowset.IsReadWrite,
            MdschemaDimensionsRowset.DimensionUniqueSettings,
            MdschemaDimensionsRowset.DimensionMasterUniqueName,
            MdschemaDimensionsRowset.DimensionIsVisible,
            MdschemaDimensionsRowset.Hierarchies
        )),

    /**
     * http://msdn2.microsoft.com/en-us/library/ms126257(SQL.90).aspx
     *
     * restrictions
     *   LIBRARY_NAME Optional.
     *   INTERFACE_NAME Optional.
     *   FUNCTION_NAME Optional.
     *   ORIGIN Optional.
     *
     * Not supported
     *  DLL_NAME
     *    Optional
     *  HELP_FILE
     *    Optional
     *  HELP_CONTEXT
     *    Optional
     *    - SQL Server xml schema says that this must be present
     *  OBJECT
     *    Optional
     *  CAPTION The display caption for the function.
     */
    MDSCHEMA_FUNCTIONS(
        14, "A07CCD07-8148-11D0-87BB-00C04FC33942", null,
        List.of(
            MdschemaFunctionsRowset.FunctionName,
            MdschemaFunctionsRowset.Description,
            MdschemaFunctionsRowset.ParameterList,
            MdschemaFunctionsRowset.ReturnType,
            MdschemaFunctionsRowset.Origin,
            MdschemaFunctionsRowset.InterfaceName,
            MdschemaFunctionsRowset.LibraryName,
            MdschemaFunctionsRowset.Caption
        )),

    /**
     * http://msdn2.microsoft.com/en-us/library/ms126062(SQL.90).aspx
     *
     * restrictions
     *    CATALOG_NAME Optional.
     *    SCHEMA_NAME Optional.
     *    CUBE_NAME Optional.
     *    DIMENSION_UNIQUE_NAME Optional.
     *    HIERARCHY_NAME Optional.
     *    HIERARCHY_UNIQUE_NAME Optional.
     *    HIERARCHY_ORIGIN
     *       (Optional) A default restriction is in effect
     *       on MD_USER_DEFINED and MD_SYSTEM_ENABLED.
     *    CUBE_SOURCE
     *      (Optional) A bitmap with one of the following valid values:
     *      1 CUBE
     *      2 DIMENSION
     *      Default restriction is a value of 1.
     *    HIERARCHY_VISIBILITY
     *      (Optional) A bitmap with one of the following valid values:
     *      1 Visible
     *      2 Not visible
     *      Default restriction is a value of 1.
     *
     * Not supported
     *  HIERARCHY_ORIGIN
     *  HIERARCHY_DISPLAY_FOLDER
     *  INSTANCE_SELECTION
     */
    MDSCHEMA_HIERARCHIES(
        15, "C8B522DA-5CF3-11CE-ADE5-00AA0044773D", null,
        List.of(
            MdschemaHierarchiesRowset.CatalogName,
            MdschemaHierarchiesRowset.SchemaName,
            MdschemaHierarchiesRowset.CubeName,
            MdschemaHierarchiesRowset.DimensionUniqueName,
            MdschemaHierarchiesRowset.HierarchyName,
            MdschemaHierarchiesRowset.HierarchyUniqueName,
            MdschemaHierarchiesRowset.HierarchyGuid,
            MdschemaHierarchiesRowset.HierarchyCaption,
            MdschemaHierarchiesRowset.DimensionType,
            MdschemaHierarchiesRowset.HierarchyCardinality,
            MdschemaHierarchiesRowset.DefaultMember,
            MdschemaHierarchiesRowset.AllMember,
            MdschemaHierarchiesRowset.Description,
            MdschemaHierarchiesRowset.Structure,
            MdschemaHierarchiesRowset.IsVirtual,
            MdschemaHierarchiesRowset.IsReadWrite,
            MdschemaHierarchiesRowset.DimensionUniqueSettings,
            MdschemaHierarchiesRowset.DimensionIsVisible,
            MdschemaHierarchiesRowset.HierarchyOrdinal,
            MdschemaHierarchiesRowset.DimensionIsShared,
            MdschemaHierarchiesRowset.HierarchyIsVisibile,
            MdschemaHierarchiesRowset.HierarchyOrigin,
            MdschemaHierarchiesRowset.DisplayFolder,
            MdschemaHierarchiesRowset.CubeSource,
            MdschemaHierarchiesRowset.HierarchyVisibility,
            MdschemaHierarchiesRowset.ParentChild,
            MdschemaHierarchiesRowset.Levels
        )),

    /**
     * http://msdn2.microsoft.com/en-us/library/ms126038(SQL.90).aspx
     *
     * restriction
     *   CATALOG_NAME Optional.
     *   SCHEMA_NAME Optional.
     *   CUBE_NAME Optional.
     *   DIMENSION_UNIQUE_NAME Optional.
     *   HIERARCHY_UNIQUE_NAME Optional.
     *   LEVEL_NAME Optional.
     *   LEVEL_UNIQUE_NAME Optional.
     *   LEVEL_ORIGIN
     *       (Optional) A default restriction is in effect
     *       on MD_USER_DEFINED and MD_SYSTEM_ENABLED
     *   CUBE_SOURCE
     *       (Optional) A bitmap with one of the following valid values:
     *       1 CUBE
     *       2 DIMENSION
     *       Default restriction is a value of 1.
     *   LEVEL_VISIBILITY
     *       (Optional) A bitmap with one of the following values:
     *       1 Visible
     *       2 Not visible
     *       Default restriction is a value of 1.
     *
     * Not supported
     *  CUSTOM_ROLLUP_SETTINGS
     *  LEVEL_UNIQUE_SETTINGS
     *  LEVEL_ORDERING_PROPERTY
     *  LEVEL_DBTYPE
     *  LEVEL_MASTER_UNIQUE_NAME
     *  LEVEL_NAME_SQL_COLUMN_NAME Customers:(All)!NAME
     *  LEVEL_KEY_SQL_COLUMN_NAME Customers:(All)!KEY
     *  LEVEL_UNIQUE_NAME_SQL_COLUMN_NAME Customers:(All)!UNIQUE_NAME
     *  LEVEL_ATTRIBUTE_HIERARCHY_NAME
     *  LEVEL_KEY_CARDINALITY
     *  LEVEL_ORIGIN
     *
     */
    MDSCHEMA_LEVELS(
        16, "C8B522DB-5CF3-11CE-ADE5-00AA0044773D", null,
        List.of(
            MdschemaLevelsRowset.CatalogName,
            MdschemaLevelsRowset.SchemaName,
            MdschemaLevelsRowset.CubeName,
            MdschemaLevelsRowset.DimensionUniqueName,
            MdschemaLevelsRowset.HierarchyUniqueName,
            MdschemaLevelsRowset.LevelName,
            MdschemaLevelsRowset.LevelUniqueName,
            MdschemaLevelsRowset.LevelGuid,
            MdschemaLevelsRowset.LevelCaption,
            MdschemaLevelsRowset.LevelNumber,
            MdschemaLevelsRowset.LevelCardinality,
            MdschemaLevelsRowset.LevelType,
            MdschemaLevelsRowset.CustomRollupSettings,
            MdschemaLevelsRowset.LevelUniqueSettings,
            MdschemaLevelsRowset.LevelIsVisible,
            MdschemaLevelsRowset.Description,
            MdschemaLevelsRowset.LevelOrigin,
            MdschemaLevelsRowset.CubeSource,
            MdschemaLevelsRowset.LevelVisibility
        )),

    MDSCHEMA_MEASUREGROUP_DIMENSIONS(
        13, "a07ccd33-8148-11d0-87bb-00c04fc33942", null,
        List.of(
            MdschemaMeasuregroupDimensionsRowset.CatalogName,
            MdschemaMeasuregroupDimensionsRowset.SchemaName,
            MdschemaMeasuregroupDimensionsRowset.CubeName,
            MdschemaMeasuregroupDimensionsRowset.MeasuregroupName,
            MdschemaMeasuregroupDimensionsRowset.MeasuregroupCardinality,
            MdschemaMeasuregroupDimensionsRowset.DimensionUniqueName,
            MdschemaMeasuregroupDimensionsRowset.DimensionCardinality,
            MdschemaMeasuregroupDimensionsRowset.DimensionIsVisible,
            MdschemaMeasuregroupDimensionsRowset.DimensionIsFactDimension,
            MdschemaMeasuregroupDimensionsRowset.DimensionPath,
            MdschemaMeasuregroupDimensionsRowset.DimensionGranularity
        )),

    /**
     * http://msdn2.microsoft.com/en-us/library/ms126250(SQL.90).aspx
     *
     * restrictions
     *   CATALOG_NAME Optional.
     *   SCHEMA_NAME Optional.
     *   CUBE_NAME Optional.
     *   MEASURE_NAME Optional.
     *   MEASURE_UNIQUE_NAME Optional.
     *   CUBE_SOURCE
     *     (Optional) A bitmap with one of the following valid values:
     *     1 CUBE
     *     2 DIMENSION
     *     Default restriction is a value of 1.
     *   MEASURE_VISIBILITY
     *     (Optional) A bitmap with one of the following valid values:
     *     1 Visible
     *     2 Not Visible
     *     Default restriction is a value of 1.
     *
     * Not supported
     *  MEASURE_GUID
     *  NUMERIC_PRECISION
     *  NUMERIC_SCALE
     *  MEASURE_UNITS
     *  EXPRESSION
     *  MEASURE_NAME_SQL_COLUMN_NAME
     *  MEASURE_UNQUALIFIED_CAPTION
     *  MEASUREGROUP_NAME
     *  MEASURE_DISPLAY_FOLDER
     *  DEFAULT_FORMAT_STRING
     */
    MDSCHEMA_MEASURES(
        17, "C8B522DC-5CF3-11CE-ADE5-00AA0044773D", null,
        List.of(
            MdschemaMeasuresRowset.CatalogName,
            MdschemaMeasuresRowset.SchemaName,
            MdschemaMeasuresRowset.CubeName,
            MdschemaMeasuresRowset.MeasureName,
            MdschemaMeasuresRowset.MeasureUniqueName,
            MdschemaMeasuresRowset.MeasureCaption,
            MdschemaMeasuresRowset.MeasureGuid,
            MdschemaMeasuresRowset.MeasureAggregator,
            MdschemaMeasuresRowset.DataType,
            MdschemaMeasuresRowset.MeasureIsVisible,
            MdschemaMeasuresRowset.LevelsList,
            MdschemaMeasuresRowset.Description,
            MdschemaMeasuresRowset.MeasuregroupName,
            MdschemaMeasuresRowset.DisplayFolder,
            MdschemaMeasuresRowset.FormatString,
            MdschemaMeasuresRowset.CubeSource,
            MdschemaMeasuresRowset.MeasureVisiblity
        )),

    /**
     *
     * http://msdn2.microsoft.com/es-es/library/ms126046.aspx
     *
     *
     * restrictions
     *   CATALOG_NAME Optional.
     *   SCHEMA_NAME Optional.
     *   CUBE_NAME Optional.
     *   DIMENSION_UNIQUE_NAME Optional.
     *   HIERARCHY_UNIQUE_NAME Optional.
     *   LEVEL_UNIQUE_NAME Optional.
     *   LEVEL_NUMBER Optional.
     *   MEMBER_NAME Optional.
     *   MEMBER_UNIQUE_NAME Optional.
     *   MEMBER_CAPTION Optional.
     *   MEMBER_TYPE Optional.
     *   TREE_OP (Optional) Only applies to a single member:
     *      MDTREEOP_ANCESTORS (0x20) returns all of the ancestors.
     *      MDTREEOP_CHILDREN (0x01) returns only the immediate children.
     *      MDTREEOP_SIBLINGS (0x02) returns members on the same level.
     *      MDTREEOP_PARENT (0x04) returns only the immediate parent.
     *      MDTREEOP_SELF (0x08) returns itself in the list of
     *                 returned rows.
     *      MDTREEOP_DESCENDANTS (0x10) returns all of the descendants.
     *   CUBE_SOURCE (Optional) A bitmap with one of the
     *      following valid values:
     *        1 CUBE
     *        2 DIMENSION
     *      Default restriction is a value of 1.
     *
     * Not supported
     */
    MDSCHEMA_MEMBERS(
        18, "C8B522DE-5CF3-11CE-ADE5-00AA0044773D", null,
        List.of(
            MdschemaMembersRowset.CatalogName,
            MdschemaMembersRowset.SchemaName,
            MdschemaMembersRowset.CubeName,
            MdschemaMembersRowset.DimensionUniqueName,
            MdschemaMembersRowset.HierarchyUniqueName,
            MdschemaMembersRowset.LevelUniqueName,
            MdschemaMembersRowset.LevelNumber,
            MdschemaMembersRowset.MemberOrdinal,
            MdschemaMembersRowset.MemberName,
            MdschemaMembersRowset.MemberUniqueName,
            MdschemaMembersRowset.MemberType,
            MdschemaMembersRowset.MemberGuid,
            MdschemaMembersRowset.MemberCaption,
            MdschemaMembersRowset.ChildrenCardinality,
            MdschemaMembersRowset.ParentLevel,
            MdschemaMembersRowset.ParentUniqueName,
            MdschemaMembersRowset.ParentCount,
            MdschemaMembersRowset.TreeOp_,
            MdschemaMembersRowset.Depth
        )),

    /**
     * http://msdn2.microsoft.com/en-us/library/ms126309(SQL.90).aspx
     *
     * restrictions
     *    CATALOG_NAME Mandatory
     *    SCHEMA_NAME Optional
     *    CUBE_NAME Optional
     *    DIMENSION_UNIQUE_NAME Optional
     *    HIERARCHY_UNIQUE_NAME Optional
     *    LEVEL_UNIQUE_NAME Optional
     *
     *    MEMBER_UNIQUE_NAME Optional
     *    PROPERTY_NAME Optional
     *    PROPERTY_TYPE Optional
     *    PROPERTY_CONTENT_TYPE
     *       (Optional) A default restriction is in place on MDPROP_MEMBER
     *       OR MDPROP_CELL.
     *    PROPERTY_ORIGIN
     *       (Optional) A default restriction is in place on MD_USER_DEFINED
     *       OR MD_SYSTEM_ENABLED
     *    CUBE_SOURCE
     *       (Optional) A bitmap with one of the following valid values:
     *       1 CUBE
     *       2 DIMENSION
     *       Default restriction is a value of 1.
     *    PROPERTY_VISIBILITY
     *       (Optional) A bitmap with one of the following valid values:
     *       1 Visible
     *       2 Not visible
     *       Default restriction is a value of 1.
     *
     * Not supported
     *    PROPERTY_ORIGIN
     *    CUBE_SOURCE
     *    PROPERTY_VISIBILITY
     *    CHARACTER_MAXIMUM_LENGTH
     *    CHARACTER_OCTET_LENGTH
     *    NUMERIC_PRECISION
     *    NUMERIC_SCALE
     *    DESCRIPTION
     *    SQL_COLUMN_NAME
     *    LANGUAGE
     *    PROPERTY_ATTRIBUTE_HIERARCHY_NAME
     *    PROPERTY_CARDINALITY
     *    MIME_TYPE
     *    PROPERTY_IS_VISIBLE
     */
    MDSCHEMA_PROPERTIES(
        19, "C8B522DD-5CF3-11CE-ADE5-00AA0044773D", null,
        List.of(
            MdschemaPropertiesRowset.CatalogName,
            MdschemaPropertiesRowset.SchemaName,
            MdschemaPropertiesRowset.CubeName,
            MdschemaPropertiesRowset.DimensionUniqueName,
            MdschemaPropertiesRowset.HierarchyUniqueName,
            MdschemaPropertiesRowset.LevelUniqueName,
            MdschemaPropertiesRowset.MemberUniqueName,
            MdschemaPropertiesRowset.PropertyType,
            MdschemaPropertiesRowset.PropertyName,
            MdschemaPropertiesRowset.PropertyCaption,
            MdschemaPropertiesRowset.DataType,
            MdschemaPropertiesRowset.PropertyContentType,
            MdschemaPropertiesRowset.Description
        )),

    /**
     * http://msdn2.microsoft.com/en-us/library/ms126290(SQL.90).aspx
     *
     * restrictions
     *    CATALOG_NAME Optional.
     *    SCHEMA_NAME Optional.
     *    CUBE_NAME Optional.
     *    SET_NAME Optional.
     *    SCOPE Optional.
     *    HIERARCHY_UNIQUE_NAME Optional.
     *    CUBE_SOURCE Optional.
     *        Note: Only one hierarchy can be included, and only those named
     *        sets whose hierarchies exactly match the restriction are
     *        returned.
     *
     * Not supported
     *    EXPRESSION
     *    DIMENSIONS
     *    SET_DISPLAY_FOLDER
     */
    MDSCHEMA_SETS(
        20, "A07CCD0B-8148-11D0-87BB-00C04FC33942", null,
        List.of(
            MdschemaSetsRowset.CatalogName,
            MdschemaSetsRowset.SchemaName,
            MdschemaSetsRowset.CubeName,
            MdschemaSetsRowset.SetName,
            MdschemaSetsRowset.Scope,
            MdschemaSetsRowset.Description,
            MdschemaSetsRowset.Expression,
            MdschemaSetsRowset.Dimensions,
            MdschemaSetsRowset.SetCaption,
            MdschemaSetsRowset.DisplayFolder
//            MdschemaSetsRowset.EvaluationContext,
        )),

    /**
     */
    MDSCHEMA_KPIS(
        21, "2AE44109-ED3D-4842-B16F-B694D1CB0E3F", null,
        List.of(
            MdschemaKpisRowset.CatalogName,
            MdschemaKpisRowset.SchemaName,
            MdschemaKpisRowset.CubeName,
            MdschemaKpisRowset.MeasuregroupName,
            MdschemaKpisRowset.KpiName,
            MdschemaKpisRowset.KpiCaption,
            MdschemaKpisRowset.KpiDescription,
            MdschemaKpisRowset.KpiDisplayFolder,
            MdschemaKpisRowset.KpiValue,
            MdschemaKpisRowset.KpiGoal,
            MdschemaKpisRowset.KpiStatus,
            MdschemaKpisRowset.KpiTrend,
            MdschemaKpisRowset.KpiStatusGraphic,
            MdschemaKpisRowset.KpiTrendGraphic,
            MdschemaKpisRowset.KpiWeight,
            MdschemaKpisRowset.KpiCurrentTimeMember,
            MdschemaKpisRowset.KpiParentKpiName,
            MdschemaKpisRowset.Scope
        )),

    /**
     */
    MDSCHEMA_MEASUREGROUPS(
        22, "E1625EBF-FA96-42FD-BEA6-DB90ADAFD96B", null,
        List.of(
            MdschemaMeasuregroupsRowset.CatalogName,
            MdschemaMeasuregroupsRowset.SchemaName,
            MdschemaMeasuregroupsRowset.CubeName,
            MdschemaMeasuregroupsRowset.MeasuregroupName,
            MdschemaMeasuregroupsRowset.Description,
            MdschemaMeasuregroupsRowset.IsWriteEnabled,
            MdschemaMeasuregroupsRowset.MeasuregroupCaption
        ));

    private final String description;
    private final String schemaGuid;
    private final List<Column> columns;

    RowsetEnum(int i, String schemaGuid, String description, List<Column> columns) {
        this.description = description;
        this.schemaGuid = schemaGuid;
        this.columns = new ArrayList<>(columns);
    }

    public String getDescription() {
        return this.description;
    }

    public String getSchemaGuid() {
        return this.schemaGuid;
    }

    public List<Column> getColumnDefinitions() {
        return columns;
    }

    public static class Column {

        /**
         * This is used as the true value for the restriction parameter.
         */
        static final boolean RESTRICTION_TRUE = true;
        /**
         * This is used as the false value for the restriction parameter.
         */
        static final boolean RESTRICTION_FALSE = false;

        /**
         * This is used as the false value for the nullable parameter.
         */
        static final boolean REQUIRED = false;
        /**
         * This is used as the true value for the nullable parameter.
         */
        static final boolean OPTIONAL = true;

        /**
         * This is used as the false value for the unbounded parameter.
         */
        static final boolean ONE_MAX = false;
        /**
         * This is used as the true value for the unbounded parameter.
         */
        static final boolean UNBOUNDED_TRUE = true;

        final String name;
        final Type type;
        final Enumeration enumeration;
        final String description;
        final boolean restriction;
        final boolean nullable;
        final boolean unbounded;
        final int restrictionOrder;

        /**
         * Creates a column.
         *
         * @param name Name of column
         * @param type A {@link Type} value
         * @param enumeratedType Must be specified for enumeration or array
         *                       of enumerations
         * @param description Description of column
         * @param restriction Whether column can be used as a filter on its
         *     rowset
         * @param nullable Whether column can contain null values
         * @pre type != null
         * @pre (type == Type.Enumeration
         *  || type == Type.EnumerationArray
         *  || type == Type.EnumString)
         *  == (enumeratedType != null)
         * @pre description == null || description.indexOf('\r') == -1
         */
        Column(
            String name,
            Type type,
            Enumeration enumeratedType,
            boolean restriction,
            boolean nullable,
            String description)
        {
            this(
                name, type, enumeratedType,
                restriction, 0, nullable, ONE_MAX, description);
        }

        Column(
            String name,
            Type type,
            Enumeration enumeratedType,
            boolean restriction,
            int restrictionOrder,
            boolean nullable,
            String description)
        {
            this(
                name, type, enumeratedType,
                restriction, restrictionOrder, nullable, ONE_MAX, description);
        }

        Column(
            String name,
            Type type,
            Enumeration enumeratedType,
            boolean restriction,
            boolean nullable,
            boolean unbounded,
            String description)
        {
            this(
                name, type, enumeratedType,
                restriction, 0, nullable, unbounded, description);
        }

        Column(
            String name,
            Type type,
            Enumeration enumeratedType,
            boolean restriction,
            int restrictionOrder,
            boolean nullable,
            boolean unbounded,
            String description)
        {
            assert type != null;
            assert (type == Type.ENUMERATION
                || type == Type.ENUMERATION_ARRAY
                || type == Type.ENUM_STRING)
                == (enumeratedType != null);
            // Line endings must be UNIX style (LF) not Windows style (LF+CR).
            // Thus the client will receive the same XML, regardless
            // of the server O/S.
            assert description == null || description.indexOf('\r') == -1;
            this.name = name;
            this.type = type;
            this.enumeration = enumeratedType;
            this.description = description;
            this.restriction = restriction;
            this.nullable = nullable;
            this.unbounded = unbounded;
            this.restrictionOrder = restrictionOrder;
        }

        /**
         * Retrieves a value of this column from a row. The base implementation
         * uses reflection to call an accessor method; a derived class may
         * provide a different implementation.
         *
         * @param row Row
         */
        protected Object get(Object row) {
            return getFromAccessor(row);
        }

        /**
         * Retrieves the value of this column "MyColumn" from a field called
         * "myColumn".
         *
         * @param row Current row
         * @return Value of given this property of the given row
         */
        protected final Object getFromField(Object row) {
            try {
                String javaFieldName =
                    name.substring(0, 1).toLowerCase()
                        + name.substring(1);
                Field field = row.getClass().getField(javaFieldName);
                return field.get(row);
            } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
                throw Util.newInternal(
                    e, "Error while accessing rowset column " + name);
            }
        }

        /**
         * Retrieves the value of this column "MyColumn" by calling a method
         * called "getMyColumn()".
         *
         * @param row Current row
         * @return Value of given this property of the given row
         */
        protected final Object getFromAccessor(Object row) {
            try {
                String javaMethodName = "get" + name;
                Method method = row.getClass().getMethod(javaMethodName);
                return method.invoke(row);
            } catch (SecurityException | IllegalAccessException
                | NoSuchMethodException | InvocationTargetException e) {
                throw Util.newInternal(
                    e, "Error while accessing rowset column " + name);
            }
        }

        public String getColumnType() {
            if (type.isEnum()) {
                return enumeration.type.columnType;
            }
            return type.columnType;
        }
        public String getName() {
            return name;
        }

        public boolean isRestriction() {
            return restriction;
        }
    }


    public enum Type {
        STRING("string","xsd:string"),
        STRING_ARRAY("StringArray","xsd:string"),
        ARRAY("Array","xsd:string"),
        ENUMERATION("Enumeration","xsd:string"),
        ENUMERATION_ARRAY("EnumerationArray","xsd:string"),
        ENUM_STRING("EnumString","xsd:string"),
        BOOLEAN("Boolean","xsd:boolean"),
        STRING_SOMETIMES_ARRAY("StringSometimesArray","xsd:string"),
        INTEGER("Integer","xsd:int"),
        UNSIGNED_INTEGER("UnsignedInteger","xsd:unsignedInt"),
        DOUBLE("Double","xsd:double"),
        DATE_TIME("DateTime","xsd:dateTime"),
        ROW_SET("Rowset",null),
        SHORT("Short","xsd:short"),
        UUID("UUID","uuid"),
        UNSIGNED_SHORT("UnsignedShort","xsd:unsignedShort"),
        LONG("Long","xsd:long"),
        UNSIGNED_LONG("UnsignedLong","xsd:unsignedLong");

        public final String columnType;
        public final String value;

        Type(String value, String columnType) {
            this.value = value;
            this.columnType = columnType;
        }

        boolean isEnum() {
            return this == ENUMERATION
                || this == ENUMERATION_ARRAY
                || this == ENUM_STRING;
        }

        String getName() {
            return value;
        }
    }



    // -------------------------------------------------------------------------
    // From this point on, just rowset classess.

    static class DiscoverDatasourcesRowset {
        private static final Column DataSourceName =
            new Column(
                "DataSourceName",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the data source, such as FoodMart 2000.");
        private static final Column DataSourceDescription =
            new Column(
                "DataSourceDescription",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A description of the data source, as entered by the "
                    + "publisher.");
        private static final Column URL =
            new Column(
                "URL",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The unique path that shows where to invoke the XML for "
                    + "Analysis methods for that data source.");
        private static final Column DataSourceInfo =
            new Column(
                "DataSourceInfo",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A string containing any additional information required to "
                    + "connect to the data source. This can include the Initial "
                    + "Catalog property or other information for the provider.\n"
                    + "Example: \"Provider=MSOLAP;Data Source=Local;\"");
        private static final Column ProviderName =
            new Column(
                "ProviderName",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the provider behind the data source.\n"
                    + "Example: \"MSDASQL\"");
        private static final Column ProviderType =
            new Column(
                "ProviderType",
                Type.ENUMERATION_ARRAY,
                Enumeration.PROVIDER_TYPE,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                Column.UNBOUNDED_TRUE,
                "The types of data supported by the provider. May include one "
                    + "or more of the following types. Example follows this "
                    + "table.\n"
                    + "TDP: tabular data provider.\n"
                    + "MDP: multidimensional data provider.\n"
                    + "DMP: data mining provider. A DMP provider implements the "
                    + "OLE DB for Data Mining specification.");
        private static final Column AuthenticationMode =
            new Column(
                "AuthenticationMode",
                Type.ENUM_STRING,
                Enumeration.AUTHENTICATION_MODE,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "Specification of what type of security mode the data source "
                    + "uses. Values can be one of the following:\n"
                    + "Unauthenticated: no user ID or password needs to be sent.\n"
                    + "Authenticated: User ID and Password must be included in the "
                    + "information required for the connection.\n"
                    + "Integrated: the data source uses the underlying security to "
                    + "determine authorization, such as Integrated Security "
                    + "provided by Microsoft Internet Information Services (IIS).");

    }

    static class DiscoverSchemaRowsetsRowset {
        private static final Column SCHEMA_NAME_COLUMN =
            new Column(
                "SchemaName",
                Type.STRING_ARRAY,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the schema/request. This returns the values in "
                    + "the RequestTypes enumeration, plus any additional types "
                    + "supported by the provider. The provider defines rowset "
                    + "structures for the additional types");
        private static final Column SCHEMA_GUID_COLUMN =
            new Column(
                "SchemaGuid",
                Type.UUID,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The GUID of the schema.");
        private static final Column RESTRICTIONS_MASK_COLUMN =
            new Column(
                "RestrictionsMask",
                Type.UNSIGNED_LONG,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "");
        private static final Column RESTRICTIONS_COLUMN =
            new Column(
                "Restrictions",
                Type.ARRAY,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "An array of the restrictions suppoted by provider. An example "
                    + "follows this table.");
        private static final Column DESCRIPTION_COLUMN =
            new Column(
                "Description",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A localizable description of the schema");

    }

    static class DiscoverPropertiesRowset {

        private static final Column PropertyName =
            new Column(
                "PropertyName",
                Type.STRING_SOMETIMES_ARRAY,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the property.");
        private static final Column PropertyDescription =
            new Column(
                "PropertyDescription",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A localizable text description of the property.");
        private static final Column PropertyType =
            new Column(
                "PropertyType",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The XML data type of the property.");
        private static final Column PropertyAccessType =
            new Column(
                "PropertyAccessType",
                Type.ENUM_STRING,
                Enumeration.ACCESS,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Access for the property. The value can be Read, Write, or "
                    + "ReadWrite.");
        private static final Column IsRequired =
            new Column(
                "IsRequired",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "True if a property is required, false if it is not required.");
        private static final Column Value =
            new Column(
                "Value",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The current value of the property.");

    }

    static class DiscoverEnumeratorsRowset {

        private static final Column EnumName =
            new Column(
                "EnumName",
                Type.STRING_ARRAY,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the enumerator that contains a set of values.");
        private static final Column EnumDescription =
            new Column(
                "EnumDescription",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A localizable description of the enumerator.");
        private static final Column EnumType =
            new Column(
                "EnumType",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The data type of the Enum values.");
        private static final Column ElementName =
            new Column(
                "ElementName",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The name of one of the value elements in the enumerator set.\n"
                    + "Example: TDP");
        private static final Column ElementDescription =
            new Column(
                "ElementDescription",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A localizable description of the element (optional).");
        private static final Column ElementValue =
            new Column(
                "ElementValue",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The value of the element.\nExample: 01");
    }

    static class DiscoverKeywordsRowset {

        private static final Column Keyword =
            new Column(
                "Keyword",
                Type.STRING_SOMETIMES_ARRAY,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "A list of all the keywords reserved by a provider.\n"
                    + "Example: AND");
    }

    static class DiscoverLiteralsRowset {

        private static final Column LiteralName = new Column(
            "LiteralName",
            Type.STRING_SOMETIMES_ARRAY,
            null,
            Column.RESTRICTION_TRUE,
            Column.REQUIRED,
            "The name of the literal described in the row.\n"
                + "Example: DBLITERAL_LIKE_PERCENT");

        private static final Column LiteralValue = new Column(
            "LiteralValue",
            Type.STRING,
            null,
            Column.RESTRICTION_FALSE,
            Column.OPTIONAL,
            "Contains the actual literal value.\n"
                + "Example, if LiteralName is DBLITERAL_LIKE_PERCENT and the "
                + "percent character (%) is used to match zero or more characters "
                + "in a LIKE clause, this column's value would be \"%\".");

        private static final Column LiteralInvalidChars = new Column(
            "LiteralInvalidChars",
            Type.STRING,
            null,
            Column.RESTRICTION_FALSE,
            Column.OPTIONAL,
            "The characters, in the literal, that are not valid.\n"
                + "For example, if table names can contain anything other than a "
                + "numeric character, this string would be \"0123456789\".");

        private static final Column LiteralInvalidStartingChars = new Column(
            "LiteralInvalidStartingChars",
            Type.STRING,
            null,
            Column.RESTRICTION_FALSE,
            Column.OPTIONAL,
            "The characters that are not valid as the first character of the "
                + "literal. If the literal can start with any valid character, "
                + "this is null.");

        private static final Column LiteralMaxLength = new Column(
            "LiteralMaxLength",
            Type.INTEGER,
            null,
            Column.RESTRICTION_FALSE,
            Column.OPTIONAL,
            "The maximum number of characters in the literal. If there is no "
                + "maximum or the maximum is unknown, the value is ?1.");

        private static final Column LiteralNameEnumValue = new Column(
            "LiteralNameEnumValue",
            Type.INTEGER,
            null,
            Column.RESTRICTION_FALSE,
            Column.OPTIONAL,
            "");
    }

    static class DiscoverXmlMetadataRowset {

        private static final Column METADATA = new Column(
            "METADATA",
            Type.STRING,
            null,
            Column.RESTRICTION_FALSE,
            Column.REQUIRED,
            "An XML document that describes the object requested by the restriction.");

        private static final Column DatabaseID = new Column(
            "DatabaseID",
            Type.STRING,
            null,
            Column.RESTRICTION_TRUE,
            Column.OPTIONAL,
            null);
    }

    static class DbschemaCatalogsRowset {

        private static final Column CatalogName =
            new Column(
                "CATALOG_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "Catalog name. Cannot be NULL.");
        private static final Column Description =
            new Column(
                "DESCRIPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Human-readable description of the catalog.");
        private static final Column Roles =
            new Column(
                "ROLES",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A comma delimited list of roles to which the current user "
                    + "belongs. An asterisk (*) is included as a role if the "
                    + "current user is a server or database administrator. "
                    + "Username is appended to ROLES if one of the roles uses "
                    + "dynamic security.");
        private static final Column DateModified =
            new Column(
                "DATE_MODIFIED",
                Type.DATE_TIME,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The date that the catalog was last modified.");
    }

    static class DbschemaColumnsRowset {

        private static final Column TableCatalog =
            new Column(
                "TABLE_CATALOG",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the Database.");
        private static final Column TableSchema =
            new Column(
                "TABLE_SCHEMA",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                null);
        private static final Column TableName =
            new Column(
                "TABLE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the cube.");
        private static final Column ColumnName =
            new Column(
                "COLUMN_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the attribute hierarchy or measure.");
        private static final Column OrdinalPosition =
            new Column(
                "ORDINAL_POSITION",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The position of the column, beginning with 1.");
        private static final Column ColumnHasDefault =
            new Column(
                "COLUMN_HAS_DEFAULT",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Not supported.");
        /*
         *  A bitmask indicating the information stored in
         *      DBCOLUMNFLAGS in OLE DB.
         *  1 = Bookmark
         *  2 = Fixed length
         *  4 = Nullable
         *  8 = Row versioning
         *  16 = Updateable column
         *
         * And, of course, MS SQL Server sometimes has the value of 80!!
         */
        private static final Column ColumnFlags =
            new Column(
                "COLUMN_FLAGS",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A DBCOLUMNFLAGS bitmask indicating column properties.");
        private static final Column IsNullable =
            new Column(
                "IS_NULLABLE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Always returns false.");
        private static final Column DataType =
            new Column(
                "DATA_TYPE",
                Type.UNSIGNED_SHORT,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The data type of the column. Returns a string for dimension "
                    + "columns and a variant for measures.");
        private static final Column CharacterMaximumLength =
            new Column(
                "CHARACTER_MAXIMUM_LENGTH",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The maximum possible length of a value within the column.");
        private static final Column CharacterOctetLength =
            new Column(
                "CHARACTER_OCTET_LENGTH",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The maximum possible length of a value within the column, in "
                    + "bytes, for character or binary columns.");
        private static final Column NumericPrecision =
            new Column(
                "NUMERIC_PRECISION",
                Type.UNSIGNED_SHORT,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The maximum precision of the column for numeric data types "
                    + "other than DBTYPE_VARNUMERIC.");
        private static final Column NumericScale =
            new Column(
                "NUMERIC_SCALE",
                Type.SHORT,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The number of digits to the right of the decimal point for "
                    + "DBTYPE_DECIMAL, DBTYPE_NUMERIC, DBTYPE_VARNUMERIC. "
                    + "Otherwise, this is NULL.");
    }

    static class DbschemaProviderTypesRowset {

        /*
        DATA_TYPE DBTYPE_UI2
        BEST_MATCH DBTYPE_BOOL
        Column(String name, Type type, Enumeration enumeratedType,
        boolean restriction, boolean nullable, String description)
        */
        /*
         * These are the columns returned by SQL Server.
         */
        private static final Column TypeName =
            new Column(
                "TYPE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The provider-specific data type name.");
        private static final Column DataType =
            new Column(
                "DATA_TYPE",
                Type.UNSIGNED_SHORT,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The indicator of the data type.");
        private static final Column ColumnSize =
            new Column(
                "COLUMN_SIZE",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The length of a non-numeric column. If the data type is "
                    + "numeric, this is the upper bound on the maximum precision "
                    + "of the data type.");
        private static final Column LiteralPrefix =
            new Column(
                "LITERAL_PREFIX",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The character or characters used to prefix a literal of this "
                    + "type in a text command.");
        private static final Column LiteralSuffix =
            new Column(
                "LITERAL_SUFFIX",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The character or characters used to suffix a literal of this "
                    + "type in a text command.");
        private static final Column IsNullable =
            new Column(
                "IS_NULLABLE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A Boolean that indicates whether the data type is nullable. "
                    + "NULL-- indicates that it is not known whether the data type "
                    + "is nullable.");
        private static final Column CaseSensitive =
            new Column(
                "CASE_SENSITIVE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A Boolean that indicates whether the data type is a "
                    + "characters type and case-sensitive.");
        private static final Column Searchable =
            new Column(
                "SEARCHABLE",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "An integer indicating how the data type can be used in "
                    + "searches if the provider supports ICommandText; otherwise, "
                    + "NULL.");
        private static final Column UnsignedAttribute =
            new Column(
                "UNSIGNED_ATTRIBUTE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A Boolean that indicates whether the data type is unsigned.");
        private static final Column FixedPrecScale =
            new Column(
                "FIXED_PREC_SCALE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A Boolean that indicates whether the data type has a fixed "
                    + "precision and scale.");
        private static final Column AutoUniqueValue =
            new Column(
                "AUTO_UNIQUE_VALUE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A Boolean that indicates whether the data type is "
                    + "autoincrementing.");
        private static final Column IsLong =
            new Column(
                "IS_LONG",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A Boolean that indicates whether the data type is a binary "
                    + "large object (BLOB) and has very long data.");
        private static final Column BestMatch =
            new Column(
                "BEST_MATCH",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "A Boolean that indicates whether the data type is a best "
                    + "match.");
    }

    static class DbschemaSchemataRowset {

        /*
         * These are the columns returned by SQL Server.
         */
        private static final Column CatalogName =
            new Column(
                "CATALOG_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The provider-specific data type name.");
        private static final Column SchemaName =
            new Column(
                "SCHEMA_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The indicator of the data type.");
        private static final Column SchemaOwner =
            new Column(
                "SCHEMA_OWNER",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The length of a non-numeric column. If the data type is "
                    + "numeric, this is the upper bound on the maximum precision "
                    + "of the data type.");

    }

    static class DbschemaTablesRowset {

        private static final Column TableCatalog =
            new Column(
                "TABLE_CATALOG",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the catalog to which this object belongs.");
        private static final Column TableSchema =
            new Column(
                "TABLE_SCHEMA",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the cube to which this object belongs.");
        private static final Column TableName =
            new Column(
                "TABLE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the object, if TABLE_TYPE is TABLE.");
        private static final Column TableType =
            new Column(
                "TABLE_TYPE",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The type of the table. TABLE indicates the object is a "
                    + "measure group. SYSTEM TABLE indicates the object is a "
                    + "dimension.");

        private static final Column TableGuid =
            new Column(
                "TABLE_GUID",
                Type.UUID,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Not supported.");
        private static final Column Description =
            new Column(
                "DESCRIPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A human-readable description of the object.");
        private static final Column TablePropId =
            new Column(
                "TABLE_PROPID",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Not supported.");
        private static final Column DateCreated =
            new Column(
                "DATE_CREATED",
                Type.DATE_TIME,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Not supported.");
        private static final Column DateModified =
            new Column(
                "DATE_MODIFIED",
                Type.DATE_TIME,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The date the object was last modified.");
    }

    static class DbschemaSourceTablesRowset {

        private static final Column TableCatalog =
            new Column(
                "TABLE_CATALOG",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "Catalog name. NULL if the provider does not support "
                    + "catalogs.");
        private static final Column TableSchema =
            new Column(
                "TABLE_SCHEMA",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "Unqualified schema name. NULL if the provider does not "
                    + "support schemas.");
        private static final Column TableName =
            new Column(
                "TABLE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "Table name.");
        private static final Column TableType =
            new Column(
                "TABLE_TYPE",
                Type.STRING_SOMETIMES_ARRAY,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "Table type. One of the following or a provider-specific "
                    + "value: ALIAS, TABLE, SYNONYM, SYSTEM TABLE, VIEW, GLOBAL "
                    + "TEMPORARY, LOCAL TEMPORARY, EXTERNAL TABLE, SYSTEM VIEW");

    }


    // TODO: Is this needed????
    static class DbschemaTablesInfoRowset {

        private static final Column TableCatalog =
            new Column(
                "TABLE_CATALOG",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "Catalog name. NULL if the provider does not support "
                    + "catalogs.");
        private static final Column TableSchema =
            new Column(
                "TABLE_SCHEMA",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "Unqualified schema name. NULL if the provider does not "
                    + "support schemas.");
        private static final Column TableName =
            new Column(
                "TABLE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "Table name.");
        private static final Column TableType =
            new Column(
                "TABLE_TYPE",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "Table type. One of the following or a provider-specific "
                    + "value: ALIAS, TABLE, SYNONYM, SYSTEM TABLE, VIEW, GLOBAL "
                    + "TEMPORARY, LOCAL TEMPORARY, EXTERNAL TABLE, SYSTEM VIEW");
        private static final Column TableGuid =
            new Column(
                "TABLE_GUID",
                Type.UUID,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "GUID that uniquely identifies the table. Providers that do "
                    + "not use GUIDs to identify tables should return NULL in this "
                    + "column.");

        private static final Column Bookmarks =
            new Column(
                "BOOKMARKS",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Whether this table supports bookmarks. Allways is false.");
        private static final Column BookmarkType =
            new Column(
                "BOOKMARK_TYPE",
                Type.INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Default bookmark type supported on this table.");
        private static final Column BookmarkDataType =
            new Column(
                "BOOKMARK_DATATYPE",
                Type.UNSIGNED_SHORT,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The indicator of the bookmark's native data type.");
        private static final Column BookmarkMaximumLength =
            new Column(
                "BOOKMARK_MAXIMUM_LENGTH",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Maximum length of the bookmark in bytes.");
        private static final Column BookmarkInformation =
            new Column(
                "BOOKMARK_INFORMATION",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A bitmask specifying additional information about bookmarks "
                    + "over the rowset. ");
        private static final Column TableVersion =
            new Column(
                "TABLE_VERSION",
                Type.LONG,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Version number for this table or NULL if the provider does "
                    + "not support returning table version information.");
        private static final Column Cardinality =
            new Column(
                "CARDINALITY",
                Type.UNSIGNED_LONG,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Cardinality (number of rows) of the table.");
        private static final Column Description =
            new Column(
                "DESCRIPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Human-readable description of the table.");
        private static final Column TablePropId =
            new Column(
                "TABLE_PROPID",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Property ID of the table. Return null.");

    }

    static class MdschemaActionsRowset {

        private static final Column CatalogName =
            new Column(
                "CATALOG_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the catalog to which this action belongs.");
        private static final Column SchemaName =
            new Column(
                "SCHEMA_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the schema to which this action belongs.");
        private static final Column CubeName =
            new Column(
                "CUBE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the cube to which this action belongs.");
        private static final Column ActionName =
            new Column(
                "ACTION_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the action.");
        private static final Column Coordinate =
            new Column(
                "COORDINATE",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                null);
        private static final Column CoordinateType =
            new Column(
                "COORDINATE_TYPE",
                Type.INTEGER,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                null);
    }

    public static class MdschemaCubesRowset {


        private static final Column CatalogName =
            new Column(
                "CATALOG_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the catalog to which this cube belongs.");
        private static final Column SchemaName =
            new Column(
                "SCHEMA_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the schema to which this cube belongs.");
        private static final Column CubeName =
            new Column(
                "CUBE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "Name of the cube.");
        private static final Column CubeType =
            new Column(
                "CUBE_TYPE",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "Cube type.");
        private static final Column BaseCubeName =
            new Column(
                "BASE_CUBE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the source cube if this cube is a perspective cube.");
        private static final Column CubeGuid =
            new Column(
                "CUBE_GUID",
                Type.UUID,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Cube type.");
        private static final Column CreatedOn =
            new Column(
                "CREATED_ON",
                Type.DATE_TIME,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Date and time of cube creation.");
        private static final Column LastSchemaUpdate =
            new Column(
                "LAST_SCHEMA_UPDATE",
                Type.DATE_TIME,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Date and time of last schema update.");
        private static final Column SchemaUpdatedBy =
            new Column(
                "SCHEMA_UPDATED_BY",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "User ID of the person who last updated the schema.");
        private static final Column LastDataUpdate =
            new Column(
                "LAST_DATA_UPDATE",
                Type.DATE_TIME,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Date and time of last data update.");
        private static final Column DataUpdatedBy =
            new Column(
                "DATA_UPDATED_BY",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "User ID of the person who last updated the data.");
        private static final Column IsDrillthroughEnabled =
            new Column(
                "IS_DRILLTHROUGH_ENABLED",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Describes whether DRILLTHROUGH can be performed on the "
                    + "members of a cube");
        private static final Column IsWriteEnabled =
            new Column(
                "IS_WRITE_ENABLED",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Describes whether a cube is write-enabled");
        private static final Column IsLinkable =
            new Column(
                "IS_LINKABLE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Describes whether a cube can be used in a linked cube");
        private static final Column IsSqlEnabled =
            new Column(
                "IS_SQL_ENABLED",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Describes whether or not SQL can be used on the cube");
        private static final Column CubeCaption =
            new Column(
                "CUBE_CAPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The caption of the cube.");
        private static final Column Description =
            new Column(
                "DESCRIPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A user-friendly description of the dimension.");
        private static final Column Dimensions =
            new Column(
                "DIMENSIONS",
                Type.ROW_SET,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Dimensions in this cube.");
        private static final Column Sets =
            new Column(
                "SETS",
                Type.ROW_SET,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Sets in this cube.");
        private static final Column Measures =
            new Column(
                "MEASURES",
                Type.ROW_SET,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Measures in this cube.");
        private static final Column CubeSource =
            new Column(
                "CUBE_SOURCE",
                Type.INTEGER,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "A bitmap with one of these valid values:\n" +
                    "\n" +
                    "1 CUBE\n" +
                    "\n" +
                    "2 DIMENSION");

    }

    static class MdschemaDimensionsRowset {

        private static final Column CatalogName =
            new Column(
                "CATALOG_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the database.");
        private static final Column SchemaName =
            new Column(
                "SCHEMA_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "Not supported.");
        private static final Column CubeName =
            new Column(
                "CUBE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the cube.");
        private static final Column DimensionName =
            new Column(
                "DIMENSION_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the dimension.");
        private static final Column DimensionUniqueName =
            new Column(
                "DIMENSION_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The unique name of the dimension.");
        private static final Column DimensionGuid =
            new Column(
                "DIMENSION_GUID",
                Type.UUID,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Not supported.");
        private static final Column DimensionCaption =
            new Column(
                "DIMENSION_CAPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The caption of the dimension.");
        private static final Column DimensionOrdinal =
            new Column(
                "DIMENSION_ORDINAL",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The position of the dimension within the cube.");
        /*
         * SQL Server returns values:
         *   MD_DIMTYPE_TIME (1)
         *   MD_DIMTYPE_MEASURE (2)
         *   MD_DIMTYPE_OTHER (3)
         */
        private static final Column DimensionType =
            new Column(
                "DIMENSION_TYPE",
                Type.SHORT,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The type of the dimension.");
        private static final Column DimensionCardinality =
            new Column(
                "DIMENSION_CARDINALITY",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The number of members in the key attribute.");
        private static final Column DefaultHierarchy =
            new Column(
                "DEFAULT_HIERARCHY",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A hierarchy from the dimension. Preserved for backwards "
                    + "compatibility.");
        private static final Column Description =
            new Column(
                "DESCRIPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A user-friendly description of the dimension.");
        private static final Column IsVirtual =
            new Column(
                "IS_VIRTUAL",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Always FALSE.");
        private static final Column IsReadWrite =
            new Column(
                "IS_READWRITE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A Boolean that indicates whether the dimension is "
                    + "write-enabled.");
        /*
         * SQL Server returns values: 0 or 1
         */
        private static final Column DimensionUniqueSettings =
            new Column(
                "DIMENSION_UNIQUE_SETTINGS",
                Type.INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A bitmap that specifies which columns contain unique values "
                    + "if the dimension contains only members with unique names.");
        private static final Column DimensionMasterUniqueName =
            new Column(
                "DIMENSION_MASTER_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Always NULL.");
        private static final Column DimensionIsVisible =
            new Column(
                "DIMENSION_IS_VISIBLE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Always TRUE.");
        private static final Column Hierarchies =
            new Column(
                "HIERARCHIES",
                Type.ROW_SET,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Hierarchies in this dimension.");
    }

    static class MdschemaMeasuregroupDimensionsRowset {

        private static final Column CatalogName =
            new Column(
                "CATALOG_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the database.");
        private static final Column SchemaName =
            new Column(
                "SCHEMA_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "Not supported.");
        private static final Column CubeName =
            new Column(
                "CUBE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the cube.");
        private static final Column MeasuregroupName =
            new Column(
                "MEASUREGROUP_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the measure group.");
        private static final Column MeasuregroupCardinality =
            new Column(
                "MEASUREGROUP_CARDINALITY",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The number of instances a measure in the measure group can have for a single dimension member." +
                    " Possible values include:\n" +
                    "ONE\n" +
                    "MANY");
        private static final Column DimensionUniqueName =
            new Column(
                "DIMENSION_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The unique name of the dimension.");
        private static final Column DimensionCardinality =
            new Column(
                "DIMENSION_CARDINALITY",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The number of instances a dimension member can have for a single instance of a measure group measure.\n" +
                    "Possible values include:\n" +
                    "ONE\n" +
                    "MANY");
        private static final Column DimensionIsVisible =
            new Column(
                "DIMENSION_IS_VISIBLE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A Boolean that indicates whether hieararchies in the dimension are visible.\n" +
                    "Returns TRUE if one or more hierarchies in the dimension is visible; otherwise, FALSE.");
        private static final Column DimensionIsFactDimension =
            new Column(
                "DIMENSION_IS_FACT_DIMENSION",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "");
        private static final Column DimensionPath =
            new Column(
                "DIMENSION_PATH",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A list of dimensions for the reference dimension.");
        private static final Column DimensionGranularity =
            new Column(
                "DIMENSION_GRANULARITY",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The unique name of the granularity hierarchy.");

    }

    public static class MdschemaFunctionsRowset {
        /**
         * http://www.csidata.com/custserv/onlinehelp/VBSdocs/vbs57.htm
         */
        public enum VarType {
            Empty("Uninitialized (default)"),
            Null("Contains no valid data"),
            Integer("Integer subtype"),
            Long("Long subtype"),
            Single("Single subtype"),
            Double("Double subtype"),
            Currency("Currency subtype"),
            Date("Date subtype"),
            String("String subtype"),
            Object("Object subtype"),
            Error("Error subtype"),
            Boolean("Boolean subtype"),
            Variant("Variant subtype"),
            DataObject("DataObject subtype"),
            Decimal("Decimal subtype"),
            Byte("Byte subtype"),
            Array("Array subtype");

            public static MdschemaFunctionsRowset.VarType forCategory(DataType category) {
                switch (category) {
                    case UNKNOWN:
                        // expression == unknown ???
                        // case Category.Expression:
                        return Empty;
                    case ARRAY:
                        return Array;
                    case DIMENSION,
                        HIERARCHY,
                        LEVEL,
                        MEMBER,
                        SET,
                        TUPLE,
                        CUBE,
                        VALUE:
                        return Variant;
                    case LOGICAL:
                        return Boolean;
                    case NUMERIC:
                        return Double;
                    case STRING, SYMBOL:
                        return String;
                    case DATE_TIME:
                        return Date;
                    case INTEGER:
                        return Integer;
                    default:
                        break;
                }
                // NOTE: this should never happen
                return Empty;
            }

            VarType(String description) {
                discard(description);
            }
        }

        private static final Column FunctionName =
            new Column(
                "FUNCTION_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the function.");
        private static final Column Description =
            new Column(
                "DESCRIPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A description of the function.");
        private static final Column ParameterList =
            new Column(
                "PARAMETER_LIST",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A comma delimited list of parameters.");
        private static final Column ReturnType =
            new Column(
                "RETURN_TYPE",
                Type.INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The VARTYPE of the return data type of the function.");
        private static final Column Origin =
            new Column(
                "ORIGIN",
                Type.INTEGER,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The origin of the function:  1 for MDX functions.  2 for "
                    + "user-defined functions.");
        private static final Column InterfaceName =
            new Column(
                "INTERFACE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the interface for user-defined functions");
        private static final Column LibraryName =
            new Column(
                "LIBRARY_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the type library for user-defined functions. "
                    + "NULL for MDX functions.");
        private static final Column Caption =
            new Column(
                "CAPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The display caption for the function.");

    }

    static class MdschemaHierarchiesRowset {

        private static final Column CatalogName =
            new Column(
                "CATALOG_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the catalog to which this hierarchy belongs.");
        private static final Column SchemaName =
            new Column(
                "SCHEMA_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "Not supported");
        private static final Column CubeName =
            new Column(
                "CUBE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the cube to which this hierarchy belongs.");
        private static final Column DimensionUniqueName =
            new Column(
                "DIMENSION_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The unique name of the dimension to which this hierarchy "
                    + "belongs.");
        private static final Column HierarchyName =
            new Column(
                "HIERARCHY_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the hierarchy. Blank if there is only a single "
                    + "hierarchy in the dimension.");
        private static final Column HierarchyUniqueName =
            new Column(
                "HIERARCHY_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The unique name of the hierarchy.");

        private static final Column HierarchyGuid =
            new Column(
                "HIERARCHY_GUID",
                Type.UUID,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Hierarchy GUID.");

        private static final Column HierarchyCaption =
            new Column(
                "HIERARCHY_CAPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A label or a caption associated with the hierarchy.");
        private static final Column DimensionType =
            new Column(
                "DIMENSION_TYPE",
                Type.SHORT,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The type of the dimension.");
        private static final Column HierarchyCardinality =
            new Column(
                "HIERARCHY_CARDINALITY",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The number of members in the hierarchy.");
        private static final Column DefaultMember =
            new Column(
                "DEFAULT_MEMBER",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The default member for this hierarchy.");
        private static final Column AllMember =
            new Column(
                "ALL_MEMBER",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The member at the highest level of rollup in the hierarchy.");
        private static final Column Description =
            new Column(
                "DESCRIPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A human-readable description of the hierarchy. NULL if no "
                    + "description exists.");
        private static final Column Structure =
            new Column(
                "STRUCTURE",
                Type.SHORT,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The structure of the hierarchy.");
        private static final Column IsVirtual =
            new Column(
                "IS_VIRTUAL",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Always returns False.");
        private static final Column IsReadWrite =
            new Column(
                "IS_READWRITE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A Boolean that indicates whether the Write Back to dimension "
                    + "column is enabled.");
        private static final Column DimensionUniqueSettings =
            new Column(
                "DIMENSION_UNIQUE_SETTINGS",
                Type.INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Always returns MDDIMENSIONS_MEMBER_KEY_UNIQUE (1).");
        private static final Column DimensionIsVisible =
            new Column(
                "DIMENSION_IS_VISIBLE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A Boolean that indicates whether the parent dimension is visible.");
        private static final Column HierarchyIsVisibile =
            new Column(
                "HIERARCHY_IS_VISIBLE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A Boolean that indicates whether the hieararchy is visible.");
        private static final Column HierarchyOrigin =
            new Column(
                "HIERARCHY_ORIGIN",
                Type.UNSIGNED_SHORT,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "A bit mask that determines the source of the hierarchy:\n" +
                    "MD_ORIGIN_USER_DEFINED identifies levels in a user defined hierarchy (0x0000001).\n" +
                    "MD_ORIGIN_ATTRIBUTE identifies levels in an attribute hierarchy (0x0000002).\n" +
                    "MD_ORIGIN_INTERNAL identifies levels in attribute hierarchies that are not enabled (0x0000004).\n" +
                    "MD_ORIGIN_KEY_ATTRIBUTE identifies levels in a key attribute hierarchy (0x0000008).\n");
        private static final Column DisplayFolder =
            new Column(
                "HIERARCHY_DISPLAY_FOLDER",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The path to be used when displaying the hierarchy in the user interface. Folder names will be separated by a semicolon (;). Nested folders are indicated by a backslash (\\).");
        private static final Column CubeSource =
            new Column(
                "CUBE_SOURCE",
                Type.UNSIGNED_SHORT,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "A bitmap with one of the following valid values:\n" +
                    "1 CUBE\n" +
                    "2 DIMENSION\n" +
                    "Default restriction is a value of 1.");
        private static final Column HierarchyVisibility =
            new Column(
                "HIERARCHY_VISIBILITY",
                Type.UNSIGNED_SHORT,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "A bitmap with one of the following valid values: 1 Visible, 2 Not visible.");
        private static final Column HierarchyOrdinal =
            new Column(
                "HIERARCHY_ORDINAL",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The ordinal number of the hierarchy across all hierarchies of "
                    + "the cube.");
        private static final Column DimensionIsShared =
            new Column(
                "DIMENSION_IS_SHARED",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Always returns true.");
        private static final Column Levels =
            new Column(
                "LEVELS",
                Type.ROW_SET,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Levels in this hierarchy.");


        /*
         * NOTE: This is non-standard, where did it come from?
         */
        private static final Column ParentChild =
            new Column(
                "PARENT_CHILD",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Is hierarchy a parent.");

    }

    static class MdschemaLevelsRowset {

        private static final Column CatalogName =
            new Column(
                "CATALOG_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the catalog to which this level belongs.");
        private static final Column SchemaName =
            new Column(
                "SCHEMA_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the schema to which this level belongs.");
        private static final Column CubeName =
            new Column(
                "CUBE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the cube to which this level belongs.");
        private static final Column DimensionUniqueName =
            new Column(
                "DIMENSION_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The unique name of the dimension to which this level "
                    + "belongs.");
        private static final Column HierarchyUniqueName =
            new Column(
                "HIERARCHY_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The unique name of the hierarchy.");
        private static final Column LevelName =
            new Column(
                "LEVEL_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the level.");
        private static final Column LevelUniqueName =
            new Column(
                "LEVEL_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The properly escaped unique name of the level.");
        private static final Column LevelGuid =
            new Column(
                "LEVEL_GUID",
                Type.UUID,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Level GUID.");
        private static final Column LevelCaption =
            new Column(
                "LEVEL_CAPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A label or caption associated with the hierarchy.");
        private static final Column LevelNumber =
            new Column(
                "LEVEL_NUMBER",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The distance of the level from the root of the hierarchy. "
                    + "Root level is zero (0).");
        private static final Column LevelCardinality =
            new Column(
                "LEVEL_CARDINALITY",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The number of members in the level. This value can be an "
                    + "approximation of the real cardinality.");
        private static final Column LevelType =
            new Column(
                "LEVEL_TYPE",
                Type.INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Type of the level");
        private static final Column CustomRollupSettings =
            new Column(
                "CUSTOM_ROLLUP_SETTINGS",
                Type.INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A bitmap that specifies the custom rollup options.");
        private static final Column LevelUniqueSettings =
            new Column(
                "LEVEL_UNIQUE_SETTINGS",
                Type.INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A bitmap that specifies which columns contain unique values, "
                    + "if the level only has members with unique names or keys.");
        private static final Column LevelIsVisible =
            new Column(
                "LEVEL_IS_VISIBLE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A Boolean that indicates whether the level is visible.");
        private static final Column Description =
            new Column(
                "DESCRIPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A human-readable description of the level. NULL if no "
                    + "description exists.");
        private static final Column LevelOrigin =
            new Column(
                "LEVEL_ORIGIN",
                Type.UNSIGNED_SHORT,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "A bit map that defines how the level was sourced:\n" +
                    "MD_ORIGIN_USER_DEFINED identifies levels in a user defined hierarchy.\n" +
                    "MD_ORIGIN_ATTRIBUTE identifies levels in an attribute hierarchy.\n" +
                    "MD_ORIGIN_KEY_ATTRIBUTE identifies levels in a key attribute hierarchy.\n" +
                    "MD_ORIGIN_INTERNAL identifies levels in attribute hierarchies that are not enabled.\n");
        private static final Column CubeSource =
            new Column(
                "CUBE_SOURCE",
                Type.UNSIGNED_SHORT,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "A bitmap with one of the following valid values:\n" +
                    "1 CUBE\n" +
                    "2 DIMENSION\n" +
                    "Default restriction is a value of 1.");
        private static final Column LevelVisibility =
            new Column(
                "LEVEL_VISIBILITY",
                Type.UNSIGNED_SHORT,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "A bitmap with one of the following values:\n" +
                    "1 Visible\n" +
                    "2 Not visible\n" +
                    "Default restriction is a value of 1.");
    }


    public static class MdschemaMeasuresRowset {

        private static final Column CatalogName =
            new Column(
                "CATALOG_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the catalog to which this measure belongs.");
        private static final Column SchemaName =
            new Column(
                "SCHEMA_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the schema to which this measure belongs.");
        private static final Column CubeName =
            new Column(
                "CUBE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the cube to which this measure belongs.");
        private static final Column MeasureName =
            new Column(
                "MEASURE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The name of the measure.");
        private static final Column MeasureUniqueName =
            new Column(
                "MEASURE_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The Unique name of the measure.");
        private static final Column MeasureCaption =
            new Column(
                "MEASURE_CAPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A label or caption associated with the measure.");
        private static final Column MeasureGuid =
            new Column(
                "MEASURE_GUID",
                Type.UUID,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Measure GUID.");
        private static final Column MeasureAggregator =
            new Column(
                "MEASURE_AGGREGATOR",
                Type.INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "How a measure was derived.");
        private static final Column DataType =
            new Column(
                "DATA_TYPE",
                Type.UNSIGNED_SHORT,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Data type of the measure.");
        private static final Column MeasureIsVisible =
            new Column(
                "MEASURE_IS_VISIBLE",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "A Boolean that always returns True. If the measure is not "
                    + "visible, it will not be included in the schema rowset.");
        private static final Column LevelsList =
            new Column(
                "LEVELS_LIST",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A string that always returns NULL. EXCEPT that SQL Server "
                    + "returns non-null values!!!");
        private static final Column Description =
            new Column(
                "DESCRIPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A human-readable description of the measure.");
        private static final Column MeasuregroupName =
            new Column(
                "MEASUREGROUP_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the measure group to which the measure belongs.");
        private static final Column DisplayFolder =
            new Column(
                "MEASURE_DISPLAY_FOLDER",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The path to be used when displaying the measure in the user interface. Folder names will be separated by a semicolon. Nested folders are indicated by a backslash (\\).");
        private static final Column FormatString =
            new Column(
                "DEFAULT_FORMAT_STRING",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The default format string for the measure.");
        private static final Column MeasureVisiblity =
            new Column(
                "MEASURE_VISIBILITY",
                Type.UNSIGNED_SHORT,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "A bitmap with one of the following valid values: 1 Visible, 2 Not visible.");
        private static final Column CubeSource =
            new Column(
                "CUBE_SOURCE",
                Type.UNSIGNED_SHORT,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "A bitmap with one of the following valid values:\n" +
                    "1 CUBE\n" +
                    "2 DIMENSION\n" +
                    "Default restriction is a value of 1.");

    }

    static class MdschemaMembersRowset {

        private static final Column CatalogName =
            new Column(
                "CATALOG_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the catalog to which this member belongs.");
        private static final Column SchemaName =
            new Column(
                "SCHEMA_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the schema to which this member belongs.");
        private static final Column CubeName =
            new Column(
                "CUBE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "Name of the cube to which this member belongs.");
        private static final Column DimensionUniqueName =
            new Column(
                "DIMENSION_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "Unique name of the dimension to which this member belongs.");
        private static final Column HierarchyUniqueName =
            new Column(
                "HIERARCHY_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "Unique name of the hierarchy. If the member belongs to more "
                    + "than one hierarchy, there is one row for each hierarchy to "
                    + "which it belongs.");
        private static final Column LevelUniqueName =
            new Column(
                "LEVEL_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                " Unique name of the level to which the member belongs.");
        private static final Column LevelNumber =
            new Column(
                "LEVEL_NUMBER",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "The distance of the member from the root of the hierarchy.");
        private static final Column MemberOrdinal =
            new Column(
                "MEMBER_ORDINAL",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Ordinal number of the member. Sort rank of the member when "
                    + "members of this dimension are sorted in their natural sort "
                    + "order. If providers do not have the concept of natural "
                    + "ordering, this should be the rank when sorted by "
                    + "MEMBER_NAME.");
        private static final Column MemberName =
            new Column(
                "MEMBER_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "Name of the member.");
        private static final Column MemberUniqueName =
            new Column(
                "MEMBER_UNIQUE_NAME",
                Type.STRING_SOMETIMES_ARRAY,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                " Unique name of the member.");
        private static final Column MemberType =
            new Column(
                "MEMBER_TYPE",
                Type.INTEGER,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "Type of the member.");
        private static final Column MemberGuid =
            new Column(
                "MEMBER_GUID",
                Type.UUID,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Memeber GUID.");
        private static final Column MemberCaption =
            new Column(
                "MEMBER_CAPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.REQUIRED,
                "A label or caption associated with the member.");
        private static final Column ChildrenCardinality =
            new Column(
                "CHILDREN_CARDINALITY",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Number of children that the member has.");
        private static final Column ParentLevel =
            new Column(
                "PARENT_LEVEL",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "The distance of the member's parent from the root level of "
                    + "the hierarchy.");
        private static final Column ParentUniqueName =
            new Column(
                "PARENT_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "Unique name of the member's parent.");
        private static final Column ParentCount =
            new Column(
                "PARENT_COUNT",
                Type.UNSIGNED_INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.REQUIRED,
                "Number of parents that this member has.");
        private static final Column TreeOp_ =
            new Column(
                "TREE_OP",
                Type.ENUMERATION,
                Enumeration.TREE_OP,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "Tree Operation");
        /* Mondrian specified member properties. */
        private static final Column Depth =
            new Column(
                "DEPTH",
                Type.INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "depth");

    }

    static class MdschemaSetsRowset {

        private static final Column CatalogName =
            new Column(
                "CATALOG_NAME",
                Type.STRING,
                null,
                true,
                true,
                null);
        private static final Column SchemaName =
            new Column(
                "SCHEMA_NAME",
                Type.STRING,
                null,
                true,
                true,
                null);
        private static final Column CubeName =
            new Column(
                "CUBE_NAME",
                Type.STRING,
                null,
                true,
                false,
                null);
        private static final Column SetName =
            new Column(
                "SET_NAME",
                Type.STRING,
                null,
                true,
                false,
                null);
        private static final Column SetCaption =
            new Column(
                "SET_CAPTION",
                Type.STRING,
                null,
                true,
                true,
                null);
        private static final Column Scope =
            new Column(
                "SCOPE",
                Type.INTEGER,
                null,
                true,
                false,
                null);
        private static final Column Description =
            new Column(
                "DESCRIPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A human-readable description of the measure.");
        private static final Column Expression =
            new Column(
                "EXPRESSION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The expression for the set.");
        private static final Column Dimensions =
            new Column(
                "DIMENSIONS",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A comma delimited list of hierarchies included in the set.");
        private static final Column DisplayFolder =
            new Column(
                "SET_DISPLAY_FOLDER",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A string that identifies the path of the display folder that the client application " +
                    "uses to show the set. The folder level separator is defined by the client application. " +
                    "For the tools and clients supplied by Analysis Services, the backslash (\\) is the level separator. " +
                    "To provide multiple display folders, use a semicolon (;) to separate the folders.");
        private static final Column EvaluationContext =
            new Column(
                "SET_EVALUATION_CONTEXT",
                Type.INTEGER,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The context for the set. The set can be static or dynamic.\n" +
                    "This column can have one of the following values:\n" +
                    "MDSET_RESOLUTION_STATIC=1\n" +
                    "MDSET_RESOLUTION_DYNAMIC=2");

    }

    static class MdschemaKpisRowset {

        private static final Column CatalogName =
            new Column(
                "CATALOG_NAME",
                Type.STRING,
                null,
                true,
                true,
                null);
        private static final Column SchemaName =
            new Column(
                "SCHEMA_NAME",
                Type.STRING,
                null,
                true,
                true,
                null);
        private static final Column CubeName =
            new Column(
                "CUBE_NAME",
                Type.STRING,
                null,
                true,
                true,
                null);
        private static final Column MeasuregroupName =
            new Column(
                "MEASUREGROUP_NAME",
                Type.STRING,
                null,
                false,
                false,
                null);
        private static final Column KpiName =
            new Column(
                "KPI_NAME",
                Type.STRING,
                null,
                true,
                true,
                null);
        private static final Column KpiCaption =
            new Column(
                "KPI_CAPTION",
                Type.STRING,
                null,
                false,
                false,
                null);
        private static final Column KpiDescription =
            new Column(
                "KPI_DESCRIPTION",
                Type.STRING,
                null,
                false,
                false,
                null);
        private static final Column KpiDisplayFolder =
            new Column(
                "KPI_DISPLAY_FOLDER",
                Type.STRING,
                null,
                false,
                false,
                null);
        private static final Column KpiValue =
            new Column(
                "KPI_VALUE",
                Type.STRING,
                null,
                false,
                false,
                null);
        private static final Column KpiGoal =
            new Column(
                "KPI_GOAL",
                Type.STRING,
                null,
                false,
                false,
                null);
        private static final Column KpiStatus =
            new Column(
                "KPI_STATUS",
                Type.STRING,
                null,
                false,
                false,
                null);
        private static final Column KpiTrend =
            new Column(
                "KPI_TREND",
                Type.STRING,
                null,
                false,
                false,
                null);
        private static final Column KpiStatusGraphic =
            new Column(
                "KPI_STATUS_GRAPHIC",
                Type.STRING,
                null,
                false,
                false,
                null);
        private static final Column KpiTrendGraphic =
            new Column(
                "KPI_TREND_GRAPHIC",
                Type.STRING,
                null,
                false,
                false,
                null);
        private static final Column KpiWeight =
            new Column(
                "KPI_WEIGHT",
                Type.STRING,
                null,
                false,
                false,
                null);
        private static final Column KpiCurrentTimeMember =
            new Column(
                "KPI_CURRENT_TIME_MEMBER",
                Type.STRING,
                null,
                false,
                false,
                null);
        private static final Column KpiParentKpiName =
            new Column(
                "KPI_PARENT_KPI_NAME",
                Type.STRING,
                null,
                false,
                false,
                null);
        private static final Column Scope =
            new Column(
                "SCOPE",
                Type.INTEGER,
                null,
                false,
                false,
                null);

    }

    static class MdschemaMeasuregroupsRowset {

        private static final Column CatalogName =
            new Column(
                "CATALOG_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the catalog to which this measure group belongs. " +
                    "NULL if the provider does not support catalogs.");
        private static final Column SchemaName =
            new Column(
                "SCHEMA_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "Not supported.");
        private static final Column CubeName =
            new Column(
                "CUBE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the cube to which this measure group belongs.");
        private static final Column MeasuregroupName =
            new Column(
                "MEASUREGROUP_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                Column.OPTIONAL,
                "The name of the measure group.");
        private static final Column Description =
            new Column(
                "DESCRIPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A human-readable description of the measure group.");
        private static final Column IsWriteEnabled =
            new Column(
                "IS_WRITE_ENABLED",
                Type.BOOLEAN,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "A Boolean that indicates whether the measure group is write-enabled.");
        private static final Column MeasuregroupCaption =
            new Column(
                "MEASUREGROUP_CAPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                Column.OPTIONAL,
                "The display caption for the measure group.");
    }

    static class MdschemaPropertiesRowset {

        private static final Column CatalogName =
            new Column(
                "CATALOG_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                0,
                Column.OPTIONAL,
                "The name of the database.");
        private static final Column SchemaName =
            new Column(
                "SCHEMA_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                1,
                Column.OPTIONAL,
                "The name of the schema to which this property belongs.");
        private static final Column CubeName =
            new Column(
                "CUBE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                2,
                Column.OPTIONAL,
                "The name of the cube.");
        private static final Column DimensionUniqueName =
            new Column(
                "DIMENSION_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                3,
                Column.OPTIONAL,
                "The unique name of the dimension.");
        private static final Column HierarchyUniqueName =
            new Column(
                "HIERARCHY_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                4,
                Column.OPTIONAL,
                "The unique name of the hierarchy.");
        private static final Column LevelUniqueName =
            new Column(
                "LEVEL_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                5,
                Column.OPTIONAL,
                "The unique name of the level to which this property belongs.");
        // According to MS this should not be nullable
        private static final Column MemberUniqueName =
            new Column(
                "MEMBER_UNIQUE_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                6,
                Column.OPTIONAL,
                "The unique name of the member to which the property belongs.");
        private static final Column PropertyType =
            new Column(
                "PROPERTY_TYPE",
                Type.SHORT,
                null,
                Column.RESTRICTION_TRUE,
                8,
                Column.REQUIRED,
                "A bitmap that specifies the type of the property");
        private static final Column PropertyName =
            new Column(
                "PROPERTY_NAME",
                Type.STRING,
                null,
                Column.RESTRICTION_TRUE,
                7,
                Column.REQUIRED,
                "Name of the property.");
        private static final Column PropertyCaption =
            new Column(
                "PROPERTY_CAPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                10,
                Column.REQUIRED,
                "A label or caption associated with the property, used "
                    + "primarily for display purposes.");
        private static final Column DataType =
            new Column(
                "DATA_TYPE",
                Type.UNSIGNED_SHORT,
                null,
                Column.RESTRICTION_FALSE,
                11,
                Column.REQUIRED,
                "Data type of the property.");
        private static final Column PropertyContentType =
            new Column(
                "PROPERTY_CONTENT_TYPE",
                Type.SHORT,
                null,
                Column.RESTRICTION_TRUE,
                9,
                Column.OPTIONAL,
                "The type of the property.");
        private static final Column Description =
            new Column(
                "DESCRIPTION",
                Type.STRING,
                null,
                Column.RESTRICTION_FALSE,
                12,
                Column.OPTIONAL,
                "A human-readable description of the measure.");

    }

}
