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
package org.eclipse.daanse.xmla.client.soapmessage;

public class Responses {

    public static String DATA_SOURCES = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
                <DataSourceName>FoodMart</DataSourceName>
                <DataSourceDescription>Foodmart 2000 on local machine</DataSourceDescription>
                <URL>http://localhost/xmla/msxisapi.dll</URL>
                <DataSourceInfo>Foodmart 2000</DataSourceInfo>
                <ProviderName>Microsoft XML for Analysis</ProviderName>
                <ProviderType>TDP</ProviderType>
                <AuthenticationMode>Unauthenticated</AuthenticationMode>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;
    public static String ENUMERARORS = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
                <EnumName>EnumName</EnumName>
                <EnumDescription>EnumDescription</EnumDescription>
                <EnumType>EnumType</EnumType>
                <ElementName>ElementName</ElementName>
                <ElementDescription>ElementDescription</ElementDescription>
                <ElementValue>ElementValue</ElementValue>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static String KEYWORDS = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
                <Keyword>Keyword</Keyword>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static String LITERALS = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
                <LiteralName>LiteralName</LiteralName>
                <LiteralValue>LiteralValue</LiteralValue>
                <LiteralInvalidChars>LiteralInvalidChars</LiteralInvalidChars>
                <LiteralInvalidStartingChars>LiteralInvalidStartingChars</LiteralInvalidStartingChars>
                <LiteralMaxLength>100</LiteralMaxLength>
                <LiteralNameValue>1</LiteralNameValue>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static String PROPERTIES = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
                <PropertyName>PropertyName</PropertyName>
                <PropertyDescription>PropertyDescription</PropertyDescription>
                <PropertyType>PropertyType</PropertyType>
                <PropertyAccessType>PropertyAccessType</PropertyAccessType>
                <IsRequired>true</IsRequired>
                <Value>Value</Value>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static String SCHEMAROWSETS = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
                <SchemaName>SchemaName</SchemaName>
                <SchemaGuid>SchemaGuid</SchemaGuid>
                <Restrictions>Restrictions</Restrictions>
                <Description>Description</Description>
                <RestrictionsMask>100</RestrictionsMask>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static String XML_META_DATA = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
                <MetaData>MetaData</MetaData>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static String CATALOGS = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
                <CATALOG_NAME>CatalogName</CATALOG_NAME>
                <DESCRIPTION>Description</DESCRIPTION>
                <ROLES>Roles</ROLES>
                <DATE_MODIFIED>2023-01-10T10:45:00</DATE_MODIFIED>
                <COMPATIBILITY_LEVEL>10</COMPATIBILITY_LEVEL>
                <TYPE>0x00</TYPE>
                <VERSION>10</VERSION>
                <DATABASE_ID>DatabaseId</DATABASE_ID>
                <DATE_QUERIED>2024-01-10T10:45:00</DATE_QUERIED>
                <CURRENTLY_USED>true</CURRENTLY_USED>
                <POPULARITY>2.25</POPULARITY>
                <WEIGHTEDPOPULARITY>2.26</WEIGHTEDPOPULARITY>
                <CLIENTCACHEREFRESHPOLICY>0</CLIENTCACHEREFRESHPOLICY>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static String COLUMNS = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">

                <TABLE_CATALOG>TableCatalog</TABLE_CATALOG>
                <TABLE_SCHEMA>TableSchema</TABLE_SCHEMA>
                <TABLE_NAME>TableName</TABLE_NAME>
                <COLUMN_NAME>ColumnName</COLUMN_NAME>
                <COLUMN_GUID>10</COLUMN_GUID>
                <COLUMN_PROPID>11</COLUMN_PROPID>
                <ORDINAL_POSITION>12</ORDINAL_POSITION>
                <COLUMN_HAS_DEFAULT>true</COLUMN_HAS_DEFAULT>
                <COLUMN_DEFAULT>ColumnDefault</COLUMN_DEFAULT>
                <COLUMN_FLAG>0x1</COLUMN_FLAG>
                <IS_NULLABLE>true</IS_NULLABLE>
                <DATA_TYPE>10</DATA_TYPE>
                <TYPE_GUID>11</TYPE_GUID>
                <CHARACTER_MAXIMUM_LENGTH>12</CHARACTER_MAXIMUM_LENGTH>
                <CHARACTER_OCTET_LENGTH>14</CHARACTER_OCTET_LENGTH>
                <NUMERIC_PRECISION>15</NUMERIC_PRECISION>
                <NUMERIC_SCALE>16</NUMERIC_SCALE>
                <DATETIME_PRECISION>17</DATETIME_PRECISION>
                <CHARACTER_SET_CATALOG>CharacterSetCatalog</CHARACTER_SET_CATALOG>
                <CHARACTER_SET_SCHEMA>CharacterSetSchema</CHARACTER_SET_SCHEMA>
                <CHARACTER_SET_NAME>CharacterSetName</CHARACTER_SET_NAME>
                <COLLATION_CATALOG>CollationCatalog</COLLATION_CATALOG>
                <COLLATION_SCHEMA>CollationSchema</COLLATION_SCHEMA>
                <COLLATION_NAME>CollationName</COLLATION_NAME>
                <DOMAIN_CATALOG>DomainCatalog</DOMAIN_CATALOG>
                <DOMAIN_SCHEMA>DomainSchema</DOMAIN_SCHEMA>
                <DOMAIN_NAME>DomainName</DOMAIN_NAME>
                <DESCRIPTION>Description</DESCRIPTION>
                <COLUMN_OLAP_TYPE>ATTRIBUTE</COLUMN_OLAP_TYPE>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static String PROVIDER_TYPES = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">

                <TYPE_NAME>TypeName</TYPE_NAME>
                <DATA_TYPE>0</DATA_TYPE>
                <COLUMN_SIZE>10</COLUMN_SIZE>
                <LITERAL_PREFIX>LiteralPrefix</LITERAL_PREFIX>
                <LITERAL_SUFFIX>LiteralSuffix</LITERAL_SUFFIX>
                <CREATE_PARAMS>CreateParams</CREATE_PARAMS>
                <IS_NULLABLE>true</IS_NULLABLE>
                <CASE_SENSITIVE>true</CASE_SENSITIVE>
                <SEARCHABLE>0x01</SEARCHABLE>
                <UNSIGNED_ATTRIBUTE>true</UNSIGNED_ATTRIBUTE>
                <FIXED_PREC_SCALE>true</FIXED_PREC_SCALE>
                <AUTO_UNIQUE_VALUE>true</AUTO_UNIQUE_VALUE>
                <LOCAL_TYPE_NAME>LocalTypeName</LOCAL_TYPE_NAME>
                <MINIMUM_SCALE>10</MINIMUM_SCALE>
                <MAXIMUM_SCALE>11</MAXIMUM_SCALE>
                <GUID>12</GUID>
                <TYPE_LIB>TypeLib</TYPE_LIB>
                <VERSION>Version</VERSION>
                <IS_LONG>true</IS_LONG>
                <BEST_MATCH>true</BEST_MATCH>
                <IS_FIXEDLENGTH>true</IS_FIXEDLENGTH>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static String SCHEMATA = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
                <CATALOG_NAME>CatalogName</CATALOG_NAME>
                <SCHEMA_NAME>SchemaName</SCHEMA_NAME>
                <SCHEMA_OWNER>SchemaOwner</SCHEMA_OWNER>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static String SOURCE_TABLES = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
                <TABLE_CATALOG>CatalogName</TABLE_CATALOG>
                <TABLE_SCHEMA>SchemaName</TABLE_SCHEMA>
                <TABLE_NAME>TableName</TABLE_NAME>
                <TABLE_TYPE>TABLE</TABLE_TYPE>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static String TABLES = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
                <TABLE_CATALOG>TableCatalog</TABLE_CATALOG>
                <TABLE_SCHEMA>TableSchema</TABLE_SCHEMA>
                <TABLE_NAME>TableName</TABLE_NAME>
                <TABLE_TYPE>TableType</TABLE_TYPE>
                <TABLE_GUID>TableGuid</TABLE_GUID>
                <DESCRIPTION>Description</DESCRIPTION>
                <TABLE_PROP_ID>10</TABLE_PROP_ID>
                <DATE_CREATED>2023-01-10T10:45:00</DATE_CREATED>
                <DATE_MODIFIED>2024-01-10T10:45:00</DATE_MODIFIED>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static String TABLES_INFO = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
                <TABLE_CATALOG>CatalogName</TABLE_CATALOG>
                <TABLE_SCHEMA>SchemaName</TABLE_SCHEMA>
                <TABLE_NAME>TableName</TABLE_NAME>
                <TABLE_TYPE>TableType</TABLE_TYPE>
                <TABLE_GUID>10</TABLE_GUID>
                <BOOKMARKS>true</BOOKMARKS>
                <BOOKMARK_TYPE>10</BOOKMARK_TYPE>
                <BOOKMARK_DATA_TYPE>11</BOOKMARK_DATA_TYPE>
                <BOOKMARK_MAXIMUM_LENGTH>12</BOOKMARK_MAXIMUM_LENGTH>
                <BOOKMARK_INFORMATION>14</BOOKMARK_INFORMATION>
                <TABLE_VERSION>15</TABLE_VERSION>
                <CARDINALITY>16</CARDINALITY>
                <DESCRIPTION>Description</DESCRIPTION>
                <TABLE_PROP_ID>17</TABLE_PROP_ID>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static String ACTIONS = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
                <CATALOG_NAME>CatalogName</CATALOG_NAME>
                <SCHEMA_NAME>SchemaName</SCHEMA_NAME>
                <CUBE_NAME>CubeName</CUBE_NAME>
                <ACTION_NAME>ActionName</ACTION_NAME>
                <ACTION_TYPE>0x01</ACTION_TYPE>
                <COORDINATE>Coordinate</COORDINATE>
                <COORDINATE_TYPE>1</COORDINATE_TYPE>
                <ACTION_CAPTION>ActionCaption</ACTION_CAPTION>
                <DESCRIPTION>Description</DESCRIPTION>
                <CONTENT>Content</CONTENT>
                <APPLICATION>Application</APPLICATION>
                <INVOCATION>1</INVOCATION>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static String CUBES = """
        <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root
              xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:xsd="http://www.w3.org/2001/XMLSchema"
              xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
              <row
                xmlns="urn:schemas-microsoft-com:xml-analysis:rowset"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception">
                <CATALOG_NAME>CatalogName</CATALOG_NAME>
                <SCHEMA_NAME>SchemaName</SCHEMA_NAME>
                <CUBE_NAME>CubeName</CUBE_NAME>
                <CUBE_TYPE>CUBE</CUBE_TYPE>
                <CUBE_GUID>10</CUBE_GUID>
                <CREATED_ON>10</CREATED_ON>
                <CREATED_ON>2023-01-10T10:45:00</CREATED_ON>
                <LAST_SCHEMA_UPDATE>2024-01-10T10:45:00</LAST_SCHEMA_UPDATE>
                <SCHEMA_UPDATED_BY>SchemaUpdatedBy</SCHEMA_UPDATED_BY>
                <LAST_DATA_UPDATE>2025-01-10T10:45:00</LAST_DATA_UPDATE>
                <DATA_UPDATED_BY>DataUpdatedBy</DATA_UPDATED_BY>
                <DESCRIPTION>Description</DESCRIPTION>
                <IS_DRILLTHROUGH_ENABLED>true</IS_DRILLTHROUGH_ENABLED>
                <IS_LINKABLE>true</IS_LINKABLE>
                <IS_WRITE_ENABLED>true</IS_WRITE_ENABLED>
                <IS_SQL_ENABLED>true</IS_SQL_ENABLED>
                <CUBE_CAPTION>CubeCaption</CUBE_CAPTION>
                <BASE_CUBE_NAME>BaseCubeName</BASE_CUBE_NAME>
                <CUBE_SOURCE>1</CUBE_SOURCE>
                <PREFERRED_QUERY_PATTERNS>0</PREFERRED_QUERY_PATTERNS>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

}
