package org.eclipse.daanse.xmla.ws.jakarta.basic;

public class DiscoverResponseConstants {

    public static final String DISCOVER_DATASOURCES_RESPONSE = """
        <?xml version="1.0" encoding="UTF-8"?><DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
              <xsd:schema xmlns:sql="urn:schemas-microsoft-com:xml-sql" elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset">
                <xsd:element name="root">
                  <xsd:complexType>
                    <xsd:sequence>
                      <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                    </xsd:sequence>
                  </xsd:complexType>
                </xsd:element>
                <xsd:simpleType name="uuid">
                  <xsd:restriction base="xsd:string">
                    <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                  </xsd:restriction>
                </xsd:simpleType>
                <xsd:complexType name="row">
                  <xsd:sequence>
                    <xsd:element name="DataSourceName" sql:field="DataSourceName" type="xsd:string"/>
                    <xsd:element minOccurs="0" name="DataSourceDescription" sql:field="DataSourceDescription" type="xsd:string"/>
                    <xsd:element minOccurs="0" name="URL" sql:field="URL" type="xsd:string"/>
                    <xsd:element minOccurs="0" name="DataSourceInfo" sql:field="DataSourceInfo" type="xsd:string"/>
                    <xsd:element minOccurs="0" name="ProviderName" sql:field="ProviderName" type="xsd:string"/>
                    <xsd:element maxOccurs="unbounded" name="ProviderType" sql:field="ProviderType" type="xsd:string"/>
                    <xsd:element name="AuthenticationMode" sql:field="AuthenticationMode" type="xsd:string"/>
                  </xsd:sequence>
                </xsd:complexType>
              </xsd:schema>
              <row>
                <DataSourceName>FoodMart</DataSourceName>
                <DataSourceDescription>Mondrian FoodMart Test data source</DataSourceDescription>
                <URL>http://localhost:8080/mondrian/xmla</URL>
                <DataSourceInfo>${data.source.info.response}</DataSourceInfo>
                <ProviderName>Mondrian</ProviderName>
                <ProviderType>MDP</ProviderType>
                <AuthenticationMode>Unauthenticated</AuthenticationMode>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static final String DISCOVER_SCHEMA_ROWSETS_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header>
                <Session SessionId="${session.id}" xmlns="urn:schemas-microsoft-com:xml-analysis"/>
            </SOAP-ENV:Header>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element name="SchemaName" sql:field="SchemaName" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="SchemaGuid" sql:field="SchemaGuid" type="uuid"/>
                                        <xsd:element maxOccurs="unbounded" minOccurs="0" name="Restrictions" sql:field="Restrictions">
                                            <xsd:complexType>
                                                <xsd:sequence>
                                                    <xsd:element name="Name" sql:field="Name" type="xsd:string"/>
                                                    <xsd:element name="Type" sql:field="Type" type="xsd:string"/>
                                                </xsd:sequence>
                                            </xsd:complexType>
                                        </xsd:element>
                                        <xsd:element name="Description" sql:field="Description" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="RestrictionsMask" sql:field="RestrictionsMask" type="xsd:unsignedLong"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <SchemaName>DBSCHEMA_CATALOGS</SchemaName>
                                <SchemaGuid>C8B52211-5CF3-11CE-ADE5-00AA0044773D</SchemaGuid>
                                <Restrictions>
                                    <Name>CATALOG_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description>Identifies the physical attributes associated with catalogs accessible from the provider.</Description>
                            </row>
                            <row>
                                <SchemaName>DBSCHEMA_COLUMNS</SchemaName>
                                <SchemaGuid>C8B52214-5CF3-11CE-ADE5-00AA0044773D</SchemaGuid>
                                <Restrictions>
                                    <Name>TABLE_CATALOG</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>TABLE_SCHEMA</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>TABLE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>COLUMN_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>DBSCHEMA_PROVIDER_TYPES</SchemaName>
                                <SchemaGuid>C8B5222C-5CF3-11CE-ADE5-00AA0044773D</SchemaGuid>
                                <Restrictions>
                                    <Name>DATA_TYPE</Name>
                                    <Type>xsd:unsignedShort</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>BEST_MATCH</Name>
                                    <Type>xsd:boolean</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>DBSCHEMA_SCHEMATA</SchemaName>
                                <SchemaGuid>c8b52225-5cf3-11ce-ade5-00aa0044773d</SchemaGuid>
                                <Restrictions>
                                    <Name>CATALOG_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCHEMA_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCHEMA_OWNER</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>DBSCHEMA_SOURCE_TABLES</SchemaName>
                                <SchemaGuid>8c3f5858-2742-4976-9d65-eb4d493c693e</SchemaGuid>
                                <Restrictions>
                                    <Name>TABLE_CATALOG</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>TABLE_SCHEMA</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>TABLE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>TABLE_TYPE</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>DBSCHEMA_TABLES</SchemaName>
                                <SchemaGuid>C8B52229-5CF3-11CE-ADE5-00AA0044773D</SchemaGuid>
                                <Restrictions>
                                    <Name>TABLE_CATALOG</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>TABLE_SCHEMA</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>TABLE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>TABLE_TYPE</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>DBSCHEMA_TABLES_INFO</SchemaName>
                                <SchemaGuid>c8b522e0-5cf3-11ce-ade5-00aa0044773d</SchemaGuid>
                                <Restrictions>
                                    <Name>TABLE_CATALOG</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>TABLE_SCHEMA</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>TABLE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>TABLE_TYPE</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>DISCOVER_DATASOURCES</SchemaName>
                                <SchemaGuid>06C03D41-F66D-49F3-B1B8-987F7AF4CF18</SchemaGuid>
                                <Restrictions>
                                    <Name>DataSourceName</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>URL</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>ProviderName</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>ProviderType</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>AuthenticationMode</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description>Returns a list of XML for Analysis data sources available on the server or Web Service.</Description>
                            </row>
                            <row>
                                <SchemaName>DISCOVER_ENUMERATORS</SchemaName>
                                <SchemaGuid>55A9E78B-ACCB-45B4-95A6-94C5065617A7</SchemaGuid>
                                <Restrictions>
                                    <Name>EnumName</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description>Returns a list of names, data types, and enumeration values for enumerators supported by the provider of a specific data source.</Description>
                            </row>
                            <row>
                                <SchemaName>DISCOVER_KEYWORDS</SchemaName>
                                <SchemaGuid>1426C443-4CDD-4A40-8F45-572FAB9BBAA1</SchemaGuid>
                                <Restrictions>
                                    <Name>Keyword</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description>Returns an XML list of keywords reserved by the provider.</Description>
                            </row>
                            <row>
                                <SchemaName>DISCOVER_LITERALS</SchemaName>
                                <SchemaGuid>C3EF5ECB-0A07-4665-A140-B075722DBDC2</SchemaGuid>
                                <Restrictions>
                                    <Name>LiteralName</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description>Returns information about literals supported by the provider.</Description>
                            </row>
                            <row>
                                <SchemaName>DISCOVER_PROPERTIES</SchemaName>
                                <SchemaGuid>4B40ADFB-8B09-4758-97BB-636E8AE97BCF</SchemaGuid>
                                <Restrictions>
                                    <Name>PropertyName</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description>Returns a list of information and values about the requested properties that are supported by the specified data source provider.</Description>
                            </row>
                            <row>
                                <SchemaName>DISCOVER_SCHEMA_ROWSETS</SchemaName>
                                <SchemaGuid>EEA0302B-7922-4992-8991-0E605D0E5593</SchemaGuid>
                                <Restrictions>
                                    <Name>SchemaName</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description>Returns the names, values, and other information of all supported RequestType enumeration values.</Description>
                            </row>
                            <row>
                                <SchemaName>DISCOVER_XML_METADATA</SchemaName>
                                <SchemaGuid>3444B255-171E-4CB9-AD98-19E57888A75F</SchemaGuid>
                                <Restrictions>
                                    <Name>DatabaseID</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description>Returns an XML document describing a requested object. The rowset that is returned always consists of one row and one column.</Description>
                            </row>
                            <row>
                                <SchemaName>MDSCHEMA_ACTIONS</SchemaName>
                                <SchemaGuid>A07CCD08-8148-11D0-87BB-00C04FC33942</SchemaGuid>
                                <Restrictions>
                                    <Name>CATALOG_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCHEMA_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>ACTION_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>COORDINATE</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>COORDINATE_TYPE</Name>
                                    <Type>xsd:int</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>MDSCHEMA_CUBES</SchemaName>
                                <SchemaGuid>C8B522D8-5CF3-11CE-ADE5-00AA0044773D</SchemaGuid>
                                <Restrictions>
                                    <Name>CATALOG_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCHEMA_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_TYPE</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>BASE_CUBE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_SOURCE</Name>
                                    <Type>xsd:int</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>MDSCHEMA_DIMENSIONS</SchemaName>
                                <SchemaGuid>C8B522D9-5CF3-11CE-ADE5-00AA0044773D</SchemaGuid>
                                <Restrictions>
                                    <Name>CATALOG_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCHEMA_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>DIMENSION_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>DIMENSION_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>MDSCHEMA_FUNCTIONS</SchemaName>
                                <SchemaGuid>A07CCD07-8148-11D0-87BB-00C04FC33942</SchemaGuid>
                                <Restrictions>
                                    <Name>FUNCTION_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>ORIGIN</Name>
                                    <Type>xsd:int</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>INTERFACE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>LIBRARY_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>MDSCHEMA_HIERARCHIES</SchemaName>
                                <SchemaGuid>C8B522DA-5CF3-11CE-ADE5-00AA0044773D</SchemaGuid>
                                <Restrictions>
                                    <Name>CATALOG_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCHEMA_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>DIMENSION_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>HIERARCHY_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>HIERARCHY_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>HIERARCHY_ORIGIN</Name>
                                    <Type>xsd:unsignedShort</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_SOURCE</Name>
                                    <Type>xsd:unsignedShort</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>HIERARCHY_VISIBILITY</Name>
                                    <Type>xsd:unsignedShort</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>MDSCHEMA_KPIS</SchemaName>
                                <SchemaGuid>2AE44109-ED3D-4842-B16F-B694D1CB0E3F</SchemaGuid>
                                <Restrictions>
                                    <Name>CATALOG_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCHEMA_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>KPI_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>MDSCHEMA_LEVELS</SchemaName>
                                <SchemaGuid>C8B522DB-5CF3-11CE-ADE5-00AA0044773D</SchemaGuid>
                                <Restrictions>
                                    <Name>CATALOG_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCHEMA_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>DIMENSION_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>HIERARCHY_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>LEVEL_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>LEVEL_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>LEVEL_ORIGIN</Name>
                                    <Type>xsd:unsignedShort</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_SOURCE</Name>
                                    <Type>xsd:unsignedShort</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>LEVEL_VISIBILITY</Name>
                                    <Type>xsd:unsignedShort</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>MDSCHEMA_MEASUREGROUP_DIMENSIONS</SchemaName>
                                <SchemaGuid>a07ccd33-8148-11d0-87bb-00c04fc33942</SchemaGuid>
                                <Restrictions>
                                    <Name>CATALOG_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCHEMA_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>MEASUREGROUP_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>DIMENSION_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>MDSCHEMA_MEASUREGROUPS</SchemaName>
                                <SchemaGuid>E1625EBF-FA96-42FD-BEA6-DB90ADAFD96B</SchemaGuid>
                                <Restrictions>
                                    <Name>CATALOG_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCHEMA_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>MEASUREGROUP_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>MDSCHEMA_MEASURES</SchemaName>
                                <SchemaGuid>C8B522DC-5CF3-11CE-ADE5-00AA0044773D</SchemaGuid>
                                <Restrictions>
                                    <Name>CATALOG_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCHEMA_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>MEASURE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>MEASURE_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>MEASUREGROUP_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_SOURCE</Name>
                                    <Type>xsd:unsignedShort</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>MEASURE_VISIBILITY</Name>
                                    <Type>xsd:unsignedShort</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>MDSCHEMA_MEMBERS</SchemaName>
                                <SchemaGuid>C8B522DE-5CF3-11CE-ADE5-00AA0044773D</SchemaGuid>
                                <Restrictions>
                                    <Name>CATALOG_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCHEMA_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>DIMENSION_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>HIERARCHY_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>LEVEL_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>LEVEL_NUMBER</Name>
                                    <Type>xsd:unsignedInt</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>MEMBER_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>MEMBER_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>MEMBER_TYPE</Name>
                                    <Type>xsd:int</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>MEMBER_CAPTION</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>TREE_OP</Name>
                                    <Type>xsd:int</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>MDSCHEMA_PROPERTIES</SchemaName>
                                <SchemaGuid>C8B522DD-5CF3-11CE-ADE5-00AA0044773D</SchemaGuid>
                                <Restrictions>
                                    <Name>CATALOG_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCHEMA_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>DIMENSION_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>HIERARCHY_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>LEVEL_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>MEMBER_UNIQUE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>PROPERTY_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>PROPERTY_TYPE</Name>
                                    <Type>xsd:short</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>PROPERTY_CONTENT_TYPE</Name>
                                    <Type>xsd:short</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                            <row>
                                <SchemaName>MDSCHEMA_SETS</SchemaName>
                                <SchemaGuid>A07CCD0B-8148-11D0-87BB-00C04FC33942</SchemaGuid>
                                <Restrictions>
                                    <Name>CATALOG_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCHEMA_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>CUBE_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SET_NAME</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SCOPE</Name>
                                    <Type>xsd:int</Type>
                                </Restrictions>
                                <Restrictions>
                                    <Name>SET_CAPTION</Name>
                                    <Type>xsd:string</Type>
                                </Restrictions>
                                <Description/>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
            """;

    public static final String DISCOVER_ENUMERATORS_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element name="EnumName" sql:field="EnumName" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="EnumDescription" sql:field="EnumDescription" type="xsd:string"/>
                                        <xsd:element name="EnumType" sql:field="EnumType" type="xsd:string"/>
                                        <xsd:element name="ElementName" sql:field="ElementName" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="ElementDescription" sql:field="ElementDescription" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="ElementValue" sql:field="ElementValue" type="xsd:string"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <EnumName>Access</EnumName>
                                <EnumDescription>The read/write behavior of a property</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>Read</ElementName>
                                <ElementValue>1</ElementValue>
                            </row>
                            <row>
                                <EnumName>Access</EnumName>
                                <EnumDescription>The read/write behavior of a property</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>Write</ElementName>
                                <ElementValue>2</ElementValue>
                            </row>
                            <row>
                                <EnumName>Access</EnumName>
                                <EnumDescription>The read/write behavior of a property</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>ReadWrite</ElementName>
                                <ElementValue>3</ElementValue>
                            </row>
                            <row>
                                <EnumName>AuthenticationMode</EnumName>
                                <EnumDescription>Specification of what type of security mode the data source uses.</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>Unauthenticated</ElementName>
                                <ElementDescription>no user ID or password needs to be sent.</ElementDescription>
                                <ElementValue>0</ElementValue>
                            </row>
                            <row>
                                <EnumName>AuthenticationMode</EnumName>
                                <EnumDescription>Specification of what type of security mode the data source uses.</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>Authenticated</ElementName>
                                <ElementDescription>User ID and Password must be included in the information required for the connection.</ElementDescription>
                                <ElementValue>1</ElementValue>
                            </row>
                            <row>
                                <EnumName>AuthenticationMode</EnumName>
                                <EnumDescription>Specification of what type of security mode the data source uses.</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>Integrated</ElementName>
                                <ElementDescription>the data source uses the underlying security to determine authorization, such as Integrated Security provided by Microsoft Internet Information Services (IIS).</ElementDescription>
                                <ElementValue>2</ElementValue>
                            </row>
                            <row>
                                <EnumName>ProviderType</EnumName>
                                <EnumDescription>The types of data supported by the provider.</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>TDP</ElementName>
                                <ElementDescription>tabular data provider.</ElementDescription>
                                <ElementValue>0</ElementValue>
                            </row>
                            <row>
                                <EnumName>ProviderType</EnumName>
                                <EnumDescription>The types of data supported by the provider.</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>MDP</ElementName>
                                <ElementDescription>multidimensional data provider.</ElementDescription>
                                <ElementValue>1</ElementValue>
                            </row>
                            <row>
                                <EnumName>ProviderType</EnumName>
                                <EnumDescription>The types of data supported by the provider.</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>DMP</ElementName>
                                <ElementDescription>data mining provider. A DMP provider implements the OLE DB for Data Mining specification.</ElementDescription>
                                <ElementValue>2</ElementValue>
                            </row>
                            <row>
                                <EnumName>TREE_OP</EnumName>
                                <EnumDescription>Bitmap which controls which relatives of a member are returned</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>MDTREEOP_CHILDREN</ElementName>
                                <ElementDescription>Tree operation which returns only the immediate children.</ElementDescription>
                                <ElementValue>1</ElementValue>
                            </row>
                            <row>
                                <EnumName>TREE_OP</EnumName>
                                <EnumDescription>Bitmap which controls which relatives of a member are returned</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>MDTREEOP_SIBLINGS</ElementName>
                                <ElementDescription>Tree operation which returns members on the same level.</ElementDescription>
                                <ElementValue>2</ElementValue>
                            </row>
                            <row>
                                <EnumName>TREE_OP</EnumName>
                                <EnumDescription>Bitmap which controls which relatives of a member are returned</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>MDTREEOP_PARENT</ElementName>
                                <ElementDescription>Tree operation which returns only the immediate parent.</ElementDescription>
                                <ElementValue>4</ElementValue>
                            </row>
                            <row>
                                <EnumName>TREE_OP</EnumName>
                                <EnumDescription>Bitmap which controls which relatives of a member are returned</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>MDTREEOP_SELF</ElementName>
                                <ElementDescription>Tree operation which returns itself in the list of returned rows.</ElementDescription>
                                <ElementValue>8</ElementValue>
                            </row>
                            <row>
                                <EnumName>TREE_OP</EnumName>
                                <EnumDescription>Bitmap which controls which relatives of a member are returned</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>MDTREEOP_DESCENDANTS</ElementName>
                                <ElementDescription>Tree operation which returns all of the descendants.</ElementDescription>
                                <ElementValue>16</ElementValue>
                            </row>
                            <row>
                                <EnumName>TREE_OP</EnumName>
                                <EnumDescription>Bitmap which controls which relatives of a member are returned</EnumDescription>
                                <EnumType>string</EnumType>
                                <ElementName>MDTREEOP_ANCESTORS</ElementName>
                                <ElementDescription>Tree operation which returns all of the ancestors.</ElementDescription>
                                <ElementValue>32</ElementValue>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String DISCOVER_PROPERTIES_RESPONSE = """
            <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element name="PropertyName" sql:field="PropertyName" type="xsd:string"/>
                                        <xsd:element name="PropertyDescription" sql:field="PropertyDescription" type="xsd:string"/>
                                        <xsd:element name="PropertyType" sql:field="PropertyType" type="xsd:string"/>
                                        <xsd:element name="PropertyAccessType" sql:field="PropertyAccessType" type="xsd:string"/>
                                        <xsd:element name="IsRequired" sql:field="IsRequired" type="xsd:boolean"/>
                                        <xsd:element name="Value" sql:field="Value" type="xsd:string"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <PropertyName>AxisFormat</PropertyName>
                                <PropertyDescription>Determines the format used within an MDDataSet result set to describe the axes of the multidimensional dataset. This property can have the values listed in the following table: TupleFormat (default), ClusterFormat, CustomFormat.</PropertyDescription>
                                <PropertyType>Enumeration</PropertyType>
                                <PropertyAccessType>Write</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value/>
                            </row>
                            <row>
                                <PropertyName>BeginRange</PropertyName>
                                <PropertyDescription>Contains a zero-based integer value corresponding to a CellOrdinal attribute value. (The CellOrdinal attribute is part of the Cell element in the CellData section of MDDataSet.)
        Used together with the EndRange property, the client application can use this property to restrict an OLAP dataset returned by a command to a specific range of cells. If -1 is specified, all cells up to the cell specified in the EndRange property are returned.
        The default value for this property is -1.</PropertyDescription>
                                <PropertyType>Integer</PropertyType>
                                <PropertyAccessType>Write</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>-1</Value>
                            </row>
                            <row>
                                <PropertyName>Catalog</PropertyName>
                                <PropertyDescription>When establishing a session with an Analysis Services instance to send an XMLA command, this property is equivalent to the OLE DB property, DBPROP_INIT_CATALOG.
        When you set this property during a session to change the current database for the session, this property is equivalent to the OLE DB property, DBPROP_CURRENTCATALOG.
        The default value for this property is an empty string.</PropertyDescription>
                                <PropertyType>string</PropertyType>
                                <PropertyAccessType>ReadWrite</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>FoodMart</Value>
                            </row>
                            <row>
                                <PropertyName>Content</PropertyName>
                                <PropertyDescription>An enumerator that specifies what type of data is returned in the result set.
        None: Allows the structure of the command to be verified, but not executed. Analogous to using Prepare to check syntax, and so on.
        Schema: Contains the XML schema (which indicates column information, and so on) that relates to the requested query.
        Data: Contains only the data that was requested.
        SchemaData: Returns both the schema information as well as the data.</PropertyDescription>
                                <PropertyType>EnumString</PropertyType>
                                <PropertyAccessType>Write</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>SchemaData</Value>
                            </row>
                            <row>
                                <PropertyName>Cube</PropertyName>
                                <PropertyDescription>The cube context for the Command parameter. If the command contains a cube name (such as an MDX FROM clause) the setting of this property is ignored.</PropertyDescription>
                                <PropertyType>string</PropertyType>
                                <PropertyAccessType>ReadWrite</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value/>
                            </row>
                            <row>
                                <PropertyName>DataSourceInfo</PropertyName>
                                <PropertyDescription>A string containing provider specific information, required to access the data source.</PropertyDescription>
                                <PropertyType>string</PropertyType>
                                <PropertyAccessType>ReadWrite</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value/>
                            </row>
                            <row>
                                <PropertyName>Deep</PropertyName>
                                <PropertyDescription>In an MDSCHEMA_CUBES request, whether to include sub-elements (dimensions, hierarchies, levels, measures, named sets) of each cube.</PropertyDescription>
                                <PropertyType>Boolean</PropertyType>
                                <PropertyAccessType>ReadWrite</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value/>
                            </row>
                            <row>
                                <PropertyName>EmitInvisibleMembers</PropertyName>
                                <PropertyDescription>Whether to include members whose VISIBLE property is false, or measures whose MEASURE_IS_VISIBLE property is false.</PropertyDescription>
                                <PropertyType>Boolean</PropertyType>
                                <PropertyAccessType>ReadWrite</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value/>
                            </row>
                            <row>
                                <PropertyName>EndRange</PropertyName>
                                <PropertyDescription>An integer value corresponding to a CellOrdinal used to restrict an MDDataSet returned by a command to a specific range of cells. Used in conjunction with the BeginRange property. If unspecified, all cells are returned in the rowset. The value -1 means unspecified.</PropertyDescription>
                                <PropertyType>Integer</PropertyType>
                                <PropertyAccessType>Write</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>-1</Value>
                            </row>
                            <row>
                                <PropertyName>Format</PropertyName>
                                <PropertyDescription>Enumerator that determines the format of the returned result set. Values include:
        Tabular: a flat or hierarchical rowset. Similar to the XML RAW format in SQL. The Format property should be set to Tabular for OLE DB for Data Mining commands.
        Multidimensional: Indicates that the result set will use the MDDataSet format (Execute method only).
        Native: The client does not request a specific format, so the provider may return the format  appropriate to the query. (The actual result type is identified by namespace of the result.)</PropertyDescription>
                                <PropertyType>EnumString</PropertyType>
                                <PropertyAccessType>Write</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>Native</Value>
                            </row>
                            <row>
                                <PropertyName>LocaleIdentifier</PropertyName>
                                <PropertyDescription>Use this to read or set the numeric locale identifier for this request. The default is provider-specific.
        For the complete hexadecimal list of language identifiers, search on "Language Identifiers" in the MSDN Library at http://www.msdn.microsoft.com.
        As an extension to the XMLA standard, Mondrian also allows locale codes as specified by ISO-639 and ISO-3166 and as used by Java; for example 'en-US'.
        </PropertyDescription>
                                <PropertyType>UnsignedInteger</PropertyType>
                                <PropertyAccessType>ReadWrite</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>None</Value>
                            </row>
                            <row>
                                <PropertyName>MDXSupport</PropertyName>
                                <PropertyDescription>Enumeration that describes the degree of MDX support. At initial release Core is the only value in the enumeration. In future releases, other values will be defined for this enumeration.</PropertyDescription>
                                <PropertyType>EnumString</PropertyType>
                                <PropertyAccessType>Read</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>Core</Value>
                            </row>
                            <row>
                                <PropertyName>Password</PropertyName>
                                <PropertyDescription>This property is deprecated in XMLA 1.1. To support legacy applications, the provider accepts but ignores the Password property setting when it is used with the Discover and Execute method</PropertyDescription>
                                <PropertyType>string</PropertyType>
                                <PropertyAccessType>Read</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value/>
                            </row>
                            <row>
                                <PropertyName>ProviderName</PropertyName>
                                <PropertyDescription>The XML for Analysis Provider name.</PropertyDescription>
                                <PropertyType>string</PropertyType>
                                <PropertyAccessType>Read</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>Mondrian XML for Analysis Provider</Value>
                            </row>
                            <row>
                                <PropertyName>ProviderVersion</PropertyName>
                                <PropertyDescription>The version of the Mondrian XMLA Provider</PropertyDescription>
                                <PropertyType>string</PropertyType>
                                <PropertyAccessType>Read</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>10.50.1600.1</Value>
                            </row>
                            <row>
                                <PropertyName>ResponseMimeType</PropertyName>
                                <PropertyDescription>Accepted mime type for RPC response; accepted are 'text/xml' (default), 'application/xml' (equivalent to 'text/xml'), or 'application/json'. If not specified, value in the 'Accept' header of the HTTP request is used.</PropertyDescription>
                                <PropertyType>string</PropertyType>
                                <PropertyAccessType>ReadWrite</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>None</Value>
                            </row>
                            <row>
                                <PropertyName>StateSupport</PropertyName>
                                <PropertyDescription>Property that specifies the degree of support in the provider for state. For information about state in XML for Analysis, see "Support for Statefulness in XML for Analysis." Minimum enumeration values are as follows:
        None - No support for sessions or stateful operations.
        Sessions - Provider supports sessions.</PropertyDescription>
                                <PropertyType>EnumString</PropertyType>
                                <PropertyAccessType>Read</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>None</Value>
                            </row>
                            <row>
                                <PropertyName>Timeout</PropertyName>
                                <PropertyDescription>A numeric time-out specifying in seconds the amount of time to wait for a request to be successful.</PropertyDescription>
                                <PropertyType>UnsignedInteger</PropertyType>
                                <PropertyAccessType>ReadWrite</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>Undefined</Value>
                            </row>
                            <row>
                                <PropertyName>UserName</PropertyName>
                                <PropertyDescription>Returns the UserName the server associates with the command.
        This property is deprecated as writeable in XMLA 1.1. To support legacy applications, servers accept but ignore the password setting when it is used with the Execute method.</PropertyDescription>
                                <PropertyType>string</PropertyType>
                                <PropertyAccessType>Read</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value/>
                            </row>
                            <row>
                                <PropertyName>VisualMode</PropertyName>
                                <PropertyDescription>This property is equivalent to the OLE DB property, MDPROP_VISUALMODE.
        The default value for this property is zero (0), equivalent to DBPROPVAL_VISUAL_MODE_DEFAULT.</PropertyDescription>
                                <PropertyType>Enumeration</PropertyType>
                                <PropertyAccessType>Write</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>1</Value>
                            </row>
                            <row>
                                <PropertyName>TableFields</PropertyName>
                                <PropertyDescription>List of fields to return for drill-through.
        The default value of this property is the empty string,in which case, all fields are returned.</PropertyDescription>
                                <PropertyType>string</PropertyType>
                                <PropertyAccessType>Read</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value/>
                            </row>
                            <row>
                                <PropertyName>AdvancedFlag</PropertyName>
                                <PropertyDescription/>
                                <PropertyType>Boolean</PropertyType>
                                <PropertyAccessType>Read</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>false</Value>
                            </row>
                            <row>
                                <PropertyName>SafetyOptions</PropertyName>
                                <PropertyDescription>Determines whether unsafe libraries can be registered and loaded by client applications.</PropertyDescription>
                                <PropertyType>Integer</PropertyType>
                                <PropertyAccessType>ReadWrite</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>0</Value>
                            </row>
                            <row>
                                <PropertyName>MdxMissingMemberMode</PropertyName>
                                <PropertyDescription>Indicates whether missing members are ignored in MDX statements.</PropertyDescription>
                                <PropertyType>string</PropertyType>
                                <PropertyAccessType>Write</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value/>
                            </row>
                            <row>
                                <PropertyName>DbpropMsmdMDXCompatibility</PropertyName>
                                <PropertyDescription>An enumeration value that determines how placeholder members in a ragged or
        unbalanced hierarchy are treated.</PropertyDescription>
                                <PropertyType>Integer</PropertyType>
                                <PropertyAccessType>ReadWrite</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>0</Value>
                            </row>
                            <row>
                                <PropertyName>MdpropMdxSubqueries</PropertyName>
                                <PropertyDescription>A bitmask that indicates the level of support for subqueries in MDX.</PropertyDescription>
                                <PropertyType>Integer</PropertyType>
                                <PropertyAccessType>Read</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value/>
                            </row>
                            <row>
                                <PropertyName>ClientProcessID</PropertyName>
                                <PropertyDescription>The ID of the client process.</PropertyDescription>
                                <PropertyType>Integer</PropertyType>
                                <PropertyAccessType>ReadWrite</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>0</Value>
                            </row>
                            <row>
                                <PropertyName>SspropInitAppName</PropertyName>
                                <PropertyDescription>The name of the client application.</PropertyDescription>
                                <PropertyType>string</PropertyType>
                                <PropertyAccessType>ReadWrite</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value/>
                            </row>
                            <row>
                                <PropertyName>DbpropMsmdSubqueries</PropertyName>
                                <PropertyDescription>An enumeration value that determines the behavior of subqueries.</PropertyDescription>
                                <PropertyType>Integer</PropertyType>
                                <PropertyAccessType>ReadWrite</PropertyAccessType>
                                <IsRequired>false</IsRequired>
                                <Value>1</Value>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        ]]>
                </Resource>
                <Resource name="request">
                    <![CDATA[
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
        <ns1:Discover
            soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"
            xmlns:ns1="urn:schemas-microsoft-com:xml-analysis">
            <ns1:RequestType xsi:type="xsd:string">${request.type}</ns1:RequestType>
            <ns1:Restrictions>
                <ns1:RestrictionList/>
            </ns1:Restrictions>
            <ns1:Properties>
                <ns1:PropertyList>
                    <ns1:Content>${content}</ns1:Content>
                </ns1:PropertyList>
            </ns1:Properties>
        </ns1:Discover>
            </soapenv:Body>
        </soapenv:Envelope>
        """;

    public static final String DISCOVER_KEYWORDS_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element name="Keyword" sql:field="Keyword" type="xsd:string"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <Keyword>$AdjustedProbability</Keyword>
                            </row>
                            <row>
                                <Keyword>$Distance</Keyword>
                            </row>
                            <row>
                                <Keyword>$Probability</Keyword>
                            </row>
                            <row>
                                <Keyword>$ProbabilityStDev</Keyword>
                            </row>
                            <row>
                                <Keyword>$ProbabilityStdDeV</Keyword>
                            </row>
                            <row>
                                <Keyword>$ProbabilityVariance</Keyword>
                            </row>
                            <row>
                                <Keyword>$StDev</Keyword>
                            </row>
                            <row>
                                <Keyword>$StdDeV</Keyword>
                            </row>
                            <row>
                                <Keyword>$Support</Keyword>
                            </row>
                            <row>
                                <Keyword>$Variance</Keyword>
                            </row>
                            <row>
                                <Keyword>AddCalculatedMembers</Keyword>
                            </row>
                            <row>
                                <Keyword>Action</Keyword>
                            </row>
                            <row>
                                <Keyword>After</Keyword>
                            </row>
                            <row>
                                <Keyword>Aggregate</Keyword>
                            </row>
                            <row>
                                <Keyword>All</Keyword>
                            </row>
                            <row>
                                <Keyword>Alter</Keyword>
                            </row>
                            <row>
                                <Keyword>Ancestor</Keyword>
                            </row>
                            <row>
                                <Keyword>And</Keyword>
                            </row>
                            <row>
                                <Keyword>Append</Keyword>
                            </row>
                            <row>
                                <Keyword>As</Keyword>
                            </row>
                            <row>
                                <Keyword>ASC</Keyword>
                            </row>
                            <row>
                                <Keyword>Axis</Keyword>
                            </row>
                            <row>
                                <Keyword>Automatic</Keyword>
                            </row>
                            <row>
                                <Keyword>Back_Color</Keyword>
                            </row>
                            <row>
                                <Keyword>BASC</Keyword>
                            </row>
                            <row>
                                <Keyword>BDESC</Keyword>
                            </row>
                            <row>
                                <Keyword>Before</Keyword>
                            </row>
                            <row>
                                <Keyword>Before_And_After</Keyword>
                            </row>
                            <row>
                                <Keyword>Before_And_Self</Keyword>
                            </row>
                            <row>
                                <Keyword>Before_Self_After</Keyword>
                            </row>
                            <row>
                                <Keyword>BottomCount</Keyword>
                            </row>
                            <row>
                                <Keyword>BottomPercent</Keyword>
                            </row>
                            <row>
                                <Keyword>BottomSum</Keyword>
                            </row>
                            <row>
                                <Keyword>Break</Keyword>
                            </row>
                            <row>
                                <Keyword>Boolean</Keyword>
                            </row>
                            <row>
                                <Keyword>Cache</Keyword>
                            </row>
                            <row>
                                <Keyword>Calculated</Keyword>
                            </row>
                            <row>
                                <Keyword>Call</Keyword>
                            </row>
                            <row>
                                <Keyword>Case</Keyword>
                            </row>
                            <row>
                                <Keyword>Catalog_Name</Keyword>
                            </row>
                            <row>
                                <Keyword>Cell</Keyword>
                            </row>
                            <row>
                                <Keyword>Cell_Ordinal</Keyword>
                            </row>
                            <row>
                                <Keyword>Cells</Keyword>
                            </row>
                            <row>
                                <Keyword>Chapters</Keyword>
                            </row>
                            <row>
                                <Keyword>Children</Keyword>
                            </row>
                            <row>
                                <Keyword>Children_Cardinality</Keyword>
                            </row>
                            <row>
                                <Keyword>ClosingPeriod</Keyword>
                            </row>
                            <row>
                                <Keyword>Cluster</Keyword>
                            </row>
                            <row>
                                <Keyword>ClusterDistance</Keyword>
                            </row>
                            <row>
                                <Keyword>ClusterProbability</Keyword>
                            </row>
                            <row>
                                <Keyword>Clusters</Keyword>
                            </row>
                            <row>
                                <Keyword>CoalesceEmpty</Keyword>
                            </row>
                            <row>
                                <Keyword>Column_Values</Keyword>
                            </row>
                            <row>
                                <Keyword>Columns</Keyword>
                            </row>
                            <row>
                                <Keyword>Content</Keyword>
                            </row>
                            <row>
                                <Keyword>Contingent</Keyword>
                            </row>
                            <row>
                                <Keyword>Continuous</Keyword>
                            </row>
                            <row>
                                <Keyword>Correlation</Keyword>
                            </row>
                            <row>
                                <Keyword>Cousin</Keyword>
                            </row>
                            <row>
                                <Keyword>Covariance</Keyword>
                            </row>
                            <row>
                                <Keyword>CovarianceN</Keyword>
                            </row>
                            <row>
                                <Keyword>Create</Keyword>
                            </row>
                            <row>
                                <Keyword>CreatePropertySet</Keyword>
                            </row>
                            <row>
                                <Keyword>CrossJoin</Keyword>
                            </row>
                            <row>
                                <Keyword>Cube</Keyword>
                            </row>
                            <row>
                                <Keyword>Cube_Name</Keyword>
                            </row>
                            <row>
                                <Keyword>CurrentMember</Keyword>
                            </row>
                            <row>
                                <Keyword>CurrentCube</Keyword>
                            </row>
                            <row>
                                <Keyword>Custom</Keyword>
                            </row>
                            <row>
                                <Keyword>Cyclical</Keyword>
                            </row>
                            <row>
                                <Keyword>DefaultMember</Keyword>
                            </row>
                            <row>
                                <Keyword>Default_Member</Keyword>
                            </row>
                            <row>
                                <Keyword>DESC</Keyword>
                            </row>
                            <row>
                                <Keyword>Descendents</Keyword>
                            </row>
                            <row>
                                <Keyword>Description</Keyword>
                            </row>
                            <row>
                                <Keyword>Dimension</Keyword>
                            </row>
                            <row>
                                <Keyword>Dimension_Unique_Name</Keyword>
                            </row>
                            <row>
                                <Keyword>Dimensions</Keyword>
                            </row>
                            <row>
                                <Keyword>Discrete</Keyword>
                            </row>
                            <row>
                                <Keyword>Discretized</Keyword>
                            </row>
                            <row>
                                <Keyword>DrillDownLevel</Keyword>
                            </row>
                            <row>
                                <Keyword>DrillDownLevelBottom</Keyword>
                            </row>
                            <row>
                                <Keyword>DrillDownLevelTop</Keyword>
                            </row>
                            <row>
                                <Keyword>DrillDownMember</Keyword>
                            </row>
                            <row>
                                <Keyword>DrillDownMemberBottom</Keyword>
                            </row>
                            <row>
                                <Keyword>DrillDownMemberTop</Keyword>
                            </row>
                            <row>
                                <Keyword>DrillTrough</Keyword>
                            </row>
                            <row>
                                <Keyword>DrillUpLevel</Keyword>
                            </row>
                            <row>
                                <Keyword>DrillUpMember</Keyword>
                            </row>
                            <row>
                                <Keyword>Drop</Keyword>
                            </row>
                            <row>
                                <Keyword>Else</Keyword>
                            </row>
                            <row>
                                <Keyword>Empty</Keyword>
                            </row>
                            <row>
                                <Keyword>End</Keyword>
                            </row>
                            <row>
                                <Keyword>Equal_Areas</Keyword>
                            </row>
                            <row>
                                <Keyword>Exclude_Null</Keyword>
                            </row>
                            <row>
                                <Keyword>ExcludeEmpty</Keyword>
                            </row>
                            <row>
                                <Keyword>Exclusive</Keyword>
                            </row>
                            <row>
                                <Keyword>Expression</Keyword>
                            </row>
                            <row>
                                <Keyword>Filter</Keyword>
                            </row>
                            <row>
                                <Keyword>FirstChild</Keyword>
                            </row>
                            <row>
                                <Keyword>FirstRowset</Keyword>
                            </row>
                            <row>
                                <Keyword>FirstSibling</Keyword>
                            </row>
                            <row>
                                <Keyword>Flattened</Keyword>
                            </row>
                            <row>
                                <Keyword>Font_Flags</Keyword>
                            </row>
                            <row>
                                <Keyword>Font_Name</Keyword>
                            </row>
                            <row>
                                <Keyword>Font_size</Keyword>
                            </row>
                            <row>
                                <Keyword>Fore_Color</Keyword>
                            </row>
                            <row>
                                <Keyword>Format_String</Keyword>
                            </row>
                            <row>
                                <Keyword>Formatted_Value</Keyword>
                            </row>
                            <row>
                                <Keyword>Formula</Keyword>
                            </row>
                            <row>
                                <Keyword>From</Keyword>
                            </row>
                            <row>
                                <Keyword>Generate</Keyword>
                            </row>
                            <row>
                                <Keyword>Global</Keyword>
                            </row>
                            <row>
                                <Keyword>Head</Keyword>
                            </row>
                            <row>
                                <Keyword>Hierarchize</Keyword>
                            </row>
                            <row>
                                <Keyword>Hierarchy</Keyword>
                            </row>
                            <row>
                                <Keyword>Hierary_Unique_name</Keyword>
                            </row>
                            <row>
                                <Keyword>IIF</Keyword>
                            </row>
                            <row>
                                <Keyword>IsEmpty</Keyword>
                            </row>
                            <row>
                                <Keyword>Include_Null</Keyword>
                            </row>
                            <row>
                                <Keyword>Include_Statistics</Keyword>
                            </row>
                            <row>
                                <Keyword>Inclusive</Keyword>
                            </row>
                            <row>
                                <Keyword>Input_Only</Keyword>
                            </row>
                            <row>
                                <Keyword>IsDescendant</Keyword>
                            </row>
                            <row>
                                <Keyword>Item</Keyword>
                            </row>
                            <row>
                                <Keyword>Lag</Keyword>
                            </row>
                            <row>
                                <Keyword>LastChild</Keyword>
                            </row>
                            <row>
                                <Keyword>LastPeriods</Keyword>
                            </row>
                            <row>
                                <Keyword>LastSibling</Keyword>
                            </row>
                            <row>
                                <Keyword>Lead</Keyword>
                            </row>
                            <row>
                                <Keyword>Level</Keyword>
                            </row>
                            <row>
                                <Keyword>Level_Number</Keyword>
                            </row>
                            <row>
                                <Keyword>Level_Unique_Name</Keyword>
                            </row>
                            <row>
                                <Keyword>Levels</Keyword>
                            </row>
                            <row>
                                <Keyword>LinRegIntercept</Keyword>
                            </row>
                            <row>
                                <Keyword>LinRegR2</Keyword>
                            </row>
                            <row>
                                <Keyword>LinRegPoint</Keyword>
                            </row>
                            <row>
                                <Keyword>LinRegSlope</Keyword>
                            </row>
                            <row>
                                <Keyword>LinRegVariance</Keyword>
                            </row>
                            <row>
                                <Keyword>Long</Keyword>
                            </row>
                            <row>
                                <Keyword>MaxRows</Keyword>
                            </row>
                            <row>
                                <Keyword>Median</Keyword>
                            </row>
                            <row>
                                <Keyword>Member</Keyword>
                            </row>
                            <row>
                                <Keyword>Member_Caption</Keyword>
                            </row>
                            <row>
                                <Keyword>Member_Guid</Keyword>
                            </row>
                            <row>
                                <Keyword>Member_Name</Keyword>
                            </row>
                            <row>
                                <Keyword>Member_Ordinal</Keyword>
                            </row>
                            <row>
                                <Keyword>Member_Type</Keyword>
                            </row>
                            <row>
                                <Keyword>Member_Unique_Name</Keyword>
                            </row>
                            <row>
                                <Keyword>Members</Keyword>
                            </row>
                            <row>
                                <Keyword>Microsoft_Clustering</Keyword>
                            </row>
                            <row>
                                <Keyword>Microsoft_Decision_Trees</Keyword>
                            </row>
                            <row>
                                <Keyword>Mining</Keyword>
                            </row>
                            <row>
                                <Keyword>Model</Keyword>
                            </row>
                            <row>
                                <Keyword>Model_Existence_Only</Keyword>
                            </row>
                            <row>
                                <Keyword>Models</Keyword>
                            </row>
                            <row>
                                <Keyword>Move</Keyword>
                            </row>
                            <row>
                                <Keyword>MTD</Keyword>
                            </row>
                            <row>
                                <Keyword>Name</Keyword>
                            </row>
                            <row>
                                <Keyword>Nest</Keyword>
                            </row>
                            <row>
                                <Keyword>NextMember</Keyword>
                            </row>
                            <row>
                                <Keyword>Non</Keyword>
                            </row>
                            <row>
                                <Keyword>NonEmpty</Keyword>
                            </row>
                            <row>
                                <Keyword>Normal</Keyword>
                            </row>
                            <row>
                                <Keyword>Not</Keyword>
                            </row>
                            <row>
                                <Keyword>Ntext</Keyword>
                            </row>
                            <row>
                                <Keyword>Nvarchar</Keyword>
                            </row>
                            <row>
                                <Keyword>OLAP</Keyword>
                            </row>
                            <row>
                                <Keyword>On</Keyword>
                            </row>
                            <row>
                                <Keyword>OpeningPeriod</Keyword>
                            </row>
                            <row>
                                <Keyword>OpenQuery</Keyword>
                            </row>
                            <row>
                                <Keyword>Or</Keyword>
                            </row>
                            <row>
                                <Keyword>Ordered</Keyword>
                            </row>
                            <row>
                                <Keyword>Ordinal</Keyword>
                            </row>
                            <row>
                                <Keyword>Pages</Keyword>
                            </row>
                            <row>
                                <Keyword>ParallelPeriod</Keyword>
                            </row>
                            <row>
                                <Keyword>Parent</Keyword>
                            </row>
                            <row>
                                <Keyword>Parent_Level</Keyword>
                            </row>
                            <row>
                                <Keyword>Parent_Unique_Name</Keyword>
                            </row>
                            <row>
                                <Keyword>PeriodsToDate</Keyword>
                            </row>
                            <row>
                                <Keyword>PMML</Keyword>
                            </row>
                            <row>
                                <Keyword>Predict</Keyword>
                            </row>
                            <row>
                                <Keyword>Predict_Only</Keyword>
                            </row>
                            <row>
                                <Keyword>PredictAdjustedProbability</Keyword>
                            </row>
                            <row>
                                <Keyword>PredictHistogram</Keyword>
                            </row>
                            <row>
                                <Keyword>Prediction</Keyword>
                            </row>
                            <row>
                                <Keyword>PredictionScore</Keyword>
                            </row>
                            <row>
                                <Keyword>PredictProbability</Keyword>
                            </row>
                            <row>
                                <Keyword>PredictProbabilityStDev</Keyword>
                            </row>
                            <row>
                                <Keyword>PredictProbabilityVariance</Keyword>
                            </row>
                            <row>
                                <Keyword>PredictStDev</Keyword>
                            </row>
                            <row>
                                <Keyword>PredictSupport</Keyword>
                            </row>
                            <row>
                                <Keyword>PredictVariance</Keyword>
                            </row>
                            <row>
                                <Keyword>PrevMember</Keyword>
                            </row>
                            <row>
                                <Keyword>Probability</Keyword>
                            </row>
                            <row>
                                <Keyword>Probability_StDev</Keyword>
                            </row>
                            <row>
                                <Keyword>Probability_StdDev</Keyword>
                            </row>
                            <row>
                                <Keyword>Probability_Variance</Keyword>
                            </row>
                            <row>
                                <Keyword>Properties</Keyword>
                            </row>
                            <row>
                                <Keyword>Property</Keyword>
                            </row>
                            <row>
                                <Keyword>QTD</Keyword>
                            </row>
                            <row>
                                <Keyword>RangeMax</Keyword>
                            </row>
                            <row>
                                <Keyword>RangeMid</Keyword>
                            </row>
                            <row>
                                <Keyword>RangeMin</Keyword>
                            </row>
                            <row>
                                <Keyword>Rank</Keyword>
                            </row>
                            <row>
                                <Keyword>Recursive</Keyword>
                            </row>
                            <row>
                                <Keyword>Refresh</Keyword>
                            </row>
                            <row>
                                <Keyword>Related</Keyword>
                            </row>
                            <row>
                                <Keyword>Rename</Keyword>
                            </row>
                            <row>
                                <Keyword>Rollup</Keyword>
                            </row>
                            <row>
                                <Keyword>Rows</Keyword>
                            </row>
                            <row>
                                <Keyword>Schema_Name</Keyword>
                            </row>
                            <row>
                                <Keyword>Sections</Keyword>
                            </row>
                            <row>
                                <Keyword>Select</Keyword>
                            </row>
                            <row>
                                <Keyword>Self</Keyword>
                            </row>
                            <row>
                                <Keyword>Self_And_After</Keyword>
                            </row>
                            <row>
                                <Keyword>Sequence_Time</Keyword>
                            </row>
                            <row>
                                <Keyword>Server</Keyword>
                            </row>
                            <row>
                                <Keyword>Session</Keyword>
                            </row>
                            <row>
                                <Keyword>Set</Keyword>
                            </row>
                            <row>
                                <Keyword>SetToArray</Keyword>
                            </row>
                            <row>
                                <Keyword>SetToStr</Keyword>
                            </row>
                            <row>
                                <Keyword>Shape</Keyword>
                            </row>
                            <row>
                                <Keyword>Skip</Keyword>
                            </row>
                            <row>
                                <Keyword>Solve_Order</Keyword>
                            </row>
                            <row>
                                <Keyword>Sort</Keyword>
                            </row>
                            <row>
                                <Keyword>StdDev</Keyword>
                            </row>
                            <row>
                                <Keyword>Stdev</Keyword>
                            </row>
                            <row>
                                <Keyword>StripCalculatedMembers</Keyword>
                            </row>
                            <row>
                                <Keyword>StrToSet</Keyword>
                            </row>
                            <row>
                                <Keyword>StrToTuple</Keyword>
                            </row>
                            <row>
                                <Keyword>SubSet</Keyword>
                            </row>
                            <row>
                                <Keyword>Support</Keyword>
                            </row>
                            <row>
                                <Keyword>Tail</Keyword>
                            </row>
                            <row>
                                <Keyword>Text</Keyword>
                            </row>
                            <row>
                                <Keyword>Thresholds</Keyword>
                            </row>
                            <row>
                                <Keyword>ToggleDrillState</Keyword>
                            </row>
                            <row>
                                <Keyword>TopCount</Keyword>
                            </row>
                            <row>
                                <Keyword>TopPercent</Keyword>
                            </row>
                            <row>
                                <Keyword>TopSum</Keyword>
                            </row>
                            <row>
                                <Keyword>TupleToStr</Keyword>
                            </row>
                            <row>
                                <Keyword>Under</Keyword>
                            </row>
                            <row>
                                <Keyword>Uniform</Keyword>
                            </row>
                            <row>
                                <Keyword>UniqueName</Keyword>
                            </row>
                            <row>
                                <Keyword>Use</Keyword>
                            </row>
                            <row>
                                <Keyword>Value</Keyword>
                            </row>
                            <row>
                                <Keyword>Var</Keyword>
                            </row>
                            <row>
                                <Keyword>Variance</Keyword>
                            </row>
                            <row>
                                <Keyword>VarP</Keyword>
                            </row>
                            <row>
                                <Keyword>VarianceP</Keyword>
                            </row>
                            <row>
                                <Keyword>VisualTotals</Keyword>
                            </row>
                            <row>
                                <Keyword>When</Keyword>
                            </row>
                            <row>
                                <Keyword>Where</Keyword>
                            </row>
                            <row>
                                <Keyword>With</Keyword>
                            </row>
                            <row>
                                <Keyword>WTD</Keyword>
                            </row>
                            <row>
                                <Keyword>Xor</Keyword>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String DISCOVER_LITERALS_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element name="LiteralName" sql:field="LiteralName" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="LiteralValue" sql:field="LiteralValue" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="LiteralInvalidChars" sql:field="LiteralInvalidChars" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="LiteralInvalidStartingChars" sql:field="LiteralInvalidStartingChars" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="LiteralMaxLength" sql:field="LiteralMaxLength" type="xsd:int"/>
                                        <xsd:element minOccurs="0" name="LiteralNameEnumValue" sql:field="LiteralNameEnumValue" type="xsd:int"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <LiteralName>DBLITERAL_CATALOG_NAME</LiteralName>
                                <LiteralInvalidChars>.</LiteralInvalidChars>
                                <LiteralInvalidStartingChars>0123456789</LiteralInvalidStartingChars>
                                <LiteralMaxLength>24</LiteralMaxLength>
                                <LiteralNameEnumValue>2</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_CATALOG_SEPARATOR</LiteralName>
                                <LiteralValue>.</LiteralValue>
                                <LiteralMaxLength>0</LiteralMaxLength>
                                <LiteralNameEnumValue>3</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_COLUMN_ALIAS</LiteralName>
                                <LiteralInvalidChars>'"[]</LiteralInvalidChars>
                                <LiteralInvalidStartingChars>0123456789</LiteralInvalidStartingChars>
                                <LiteralMaxLength>-1</LiteralMaxLength>
                                <LiteralNameEnumValue>5</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_COLUMN_NAME</LiteralName>
                                <LiteralInvalidChars>.</LiteralInvalidChars>
                                <LiteralInvalidStartingChars>0123456789</LiteralInvalidStartingChars>
                                <LiteralMaxLength>-1</LiteralMaxLength>
                                <LiteralNameEnumValue>6</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_CORRELATION_NAME</LiteralName>
                                <LiteralInvalidChars>'"[]</LiteralInvalidChars>
                                <LiteralInvalidStartingChars>0123456789</LiteralInvalidStartingChars>
                                <LiteralMaxLength>-1</LiteralMaxLength>
                                <LiteralNameEnumValue>7</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_CUBE_NAME</LiteralName>
                                <LiteralInvalidChars>.</LiteralInvalidChars>
                                <LiteralInvalidStartingChars>0123456789</LiteralInvalidStartingChars>
                                <LiteralMaxLength>-1</LiteralMaxLength>
                                <LiteralNameEnumValue>21</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_DIMENSION_NAME</LiteralName>
                                <LiteralInvalidChars>.</LiteralInvalidChars>
                                <LiteralInvalidStartingChars>0123456789</LiteralInvalidStartingChars>
                                <LiteralMaxLength>-1</LiteralMaxLength>
                                <LiteralNameEnumValue>22</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_HIERARCHY_NAME</LiteralName>
                                <LiteralInvalidChars>.</LiteralInvalidChars>
                                <LiteralInvalidStartingChars>0123456789</LiteralInvalidStartingChars>
                                <LiteralMaxLength>-1</LiteralMaxLength>
                                <LiteralNameEnumValue>23</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_LEVEL_NAME</LiteralName>
                                <LiteralInvalidChars>.</LiteralInvalidChars>
                                <LiteralInvalidStartingChars>0123456789</LiteralInvalidStartingChars>
                                <LiteralMaxLength>-1</LiteralMaxLength>
                                <LiteralNameEnumValue>24</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_MEMBER_NAME</LiteralName>
                                <LiteralInvalidChars>.</LiteralInvalidChars>
                                <LiteralInvalidStartingChars>0123456789</LiteralInvalidStartingChars>
                                <LiteralMaxLength>-1</LiteralMaxLength>
                                <LiteralNameEnumValue>25</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_PROCEDURE_NAME</LiteralName>
                                <LiteralInvalidChars>.</LiteralInvalidChars>
                                <LiteralInvalidStartingChars>0123456789</LiteralInvalidStartingChars>
                                <LiteralMaxLength>-1</LiteralMaxLength>
                                <LiteralNameEnumValue>14</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_PROPERTY_NAME</LiteralName>
                                <LiteralInvalidChars>.</LiteralInvalidChars>
                                <LiteralInvalidStartingChars>0123456789</LiteralInvalidStartingChars>
                                <LiteralMaxLength>-1</LiteralMaxLength>
                                <LiteralNameEnumValue>26</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_QUOTE</LiteralName>
                                <LiteralValue>[</LiteralValue>
                                <LiteralMaxLength>-1</LiteralMaxLength>
                                <LiteralNameEnumValue>15</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_QUOTE_SUFFIX</LiteralName>
                                <LiteralValue>]</LiteralValue>
                                <LiteralMaxLength>-1</LiteralMaxLength>
                                <LiteralNameEnumValue>28</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_TABLE_NAME</LiteralName>
                                <LiteralInvalidChars>.</LiteralInvalidChars>
                                <LiteralInvalidStartingChars>0123456789</LiteralInvalidStartingChars>
                                <LiteralMaxLength>-1</LiteralMaxLength>
                                <LiteralNameEnumValue>17</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_TEXT_COMMAND</LiteralName>
                                <LiteralMaxLength>-1</LiteralMaxLength>
                                <LiteralNameEnumValue>18</LiteralNameEnumValue>
                            </row>
                            <row>
                                <LiteralName>DBLITERAL_USER_NAME</LiteralName>
                                <LiteralMaxLength>0</LiteralMaxLength>
                                <LiteralNameEnumValue>19</LiteralNameEnumValue>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
                """;
    public static final String DISCOVER_XML_METADATA_RESPONSE = """
        """;

    public static final String DBSCHEMA_CATALOGS_RESPONSE = """
          <?xml version="1.0" encoding="UTF-8"?><DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
              <xsd:schema xmlns:sql="urn:schemas-microsoft-com:xml-sql" elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset">
                <xsd:element name="root">
                  <xsd:complexType>
                    <xsd:sequence>
                      <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                    </xsd:sequence>
                  </xsd:complexType>
                </xsd:element>
                <xsd:simpleType name="uuid">
                  <xsd:restriction base="xsd:string">
                    <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                  </xsd:restriction>
                </xsd:simpleType>
                <xsd:complexType name="row">
                  <xsd:sequence>
                    <xsd:element name="CATALOG_NAME" sql:field="CATALOG_NAME" type="xsd:string"/>
                    <xsd:element name="DESCRIPTION" sql:field="DESCRIPTION" type="xsd:string"/>
                    <xsd:element name="ROLES" sql:field="ROLES" type="xsd:string"/>
                    <xsd:element minOccurs="0" name="DATE_MODIFIED" sql:field="DATE_MODIFIED" type="xsd:dateTime"/>
                  </xsd:sequence>
                </xsd:complexType>
              </xsd:schema>
              <row>
                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                <DESCRIPTION>No description available</DESCRIPTION>
                <ROLES>Administrator,California manager,No HR Cube</ROLES>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static final String DBSCHEMA_COLUMNS_RESPONSE = """
        """;
    public static final String DBSCHEMA_PROVIDER_TYPES_RESPONSE = """
        """;

    public static final String DBSCHEMA_SCHEMATA_RESPONSE = """
        <?xml version="1.0" encoding="UTF-8"?><DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
          <return>
            <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
              <xsd:schema xmlns:sql="urn:schemas-microsoft-com:xml-sql" elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset">
                <xsd:element name="root">
                  <xsd:complexType>
                    <xsd:sequence>
                      <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                    </xsd:sequence>
                  </xsd:complexType>
                </xsd:element>
                <xsd:simpleType name="uuid">
                  <xsd:restriction base="xsd:string">
                    <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                  </xsd:restriction>
                </xsd:simpleType>
                <xsd:complexType name="row">
                  <xsd:sequence>
                    <xsd:element name="CATALOG_NAME" sql:field="CATALOG_NAME" type="xsd:string"/>
                    <xsd:element name="SCHEMA_NAME" sql:field="SCHEMA_NAME" type="xsd:string"/>
                    <xsd:element name="SCHEMA_OWNER" sql:field="SCHEMA_OWNER" type="xsd:string"/>
                  </xsd:sequence>
                </xsd:complexType>
              </xsd:schema>
              <row>
                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                <SCHEMA_OWNER/>
              </row>
            </root>
          </return>
        </DiscoverResponse>
        """;

    public static final String DBSCHEMA_TABLES_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element name="TABLE_CATALOG" sql:field="TABLE_CATALOG" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="TABLE_SCHEMA" sql:field="TABLE_SCHEMA" type="xsd:string"/>
                                        <xsd:element name="TABLE_NAME" sql:field="TABLE_NAME" type="xsd:string"/>
                                        <xsd:element name="TABLE_TYPE" sql:field="TABLE_TYPE" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="TABLE_GUID" sql:field="TABLE_GUID" type="uuid"/>
                                        <xsd:element minOccurs="0" name="DESCRIPTION" sql:field="DESCRIPTION" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="TABLE_PROPID" sql:field="TABLE_PROPID" type="xsd:unsignedInt"/>
                                        <xsd:element minOccurs="0" name="DATE_CREATED" sql:field="DATE_CREATED" type="xsd:dateTime"/>
                                        <xsd:element minOccurs="0" name="DATE_MODIFIED" sql:field="DATE_MODIFIED" type="xsd:dateTime"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Department:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Department Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Department:Department Description</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Department Hierarchy - Department Description Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Employees:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Employees Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Employees:Employee Id</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Employees Hierarchy - Employee Id Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Pay Type:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Pay Type Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Pay Type:Pay Type</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Pay Type Hierarchy - Pay Type Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Position:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Position Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Position:Management Role</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Position Hierarchy - Management Role Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Position:Position Title</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Position Hierarchy - Position Title Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Store Type:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Store Type Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Store Type:Store Type</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Store Type Hierarchy - Store Type Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Store:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Store Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Store:Store City</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Store Hierarchy - Store City Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Store:Store Country</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Store Hierarchy - Store Country Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Store:Store Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Store Hierarchy - Store Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Store:Store State</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Store Hierarchy - Store State Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Time:Month</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Time Hierarchy - Month Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Time:Quarter</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Time Hierarchy - Quarter Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR:Time:Year</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube - Time Hierarchy - Year Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Gender:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Gender Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Gender:Gender</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Gender Hierarchy - Gender Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Product:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Product Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Product:Brand Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Product Hierarchy - Brand Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Product:Product Category</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Product Hierarchy - Product Category Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Product:Product Department</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Product Hierarchy - Product Department Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Product:Product Family</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Product Hierarchy - Product Family Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Product:Product Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Product Hierarchy - Product Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Product:Product Subcategory</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Product Hierarchy - Product Subcategory Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Time.Weekly:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Time.Weekly Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Time.Weekly:Day</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Time.Weekly Hierarchy - Day Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Time.Weekly:Week</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Time.Weekly Hierarchy - Week Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Time.Weekly:Year</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Time.Weekly Hierarchy - Year Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Time:Month</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Time Hierarchy - Month Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Time:Quarter</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Time Hierarchy - Quarter Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2:Time:Year</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube - Time Hierarchy - Year Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Customers:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Customers Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Customers:City</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Customers Hierarchy - City Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Customers:Country</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Customers Hierarchy - Country Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Customers:Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Customers Hierarchy - Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Customers:State Province</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Customers Hierarchy - State Province Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Education Level:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Education Level Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Education Level:Education Level</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Education Level Hierarchy - Education Level Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Gender:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Gender Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Gender:Gender</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Gender Hierarchy - Gender Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Geography:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Geography Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Geography:City</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Geography Hierarchy - City Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Geography:Country</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Geography Hierarchy - Country Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Geography:State</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Geography Hierarchy - State Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Marital Status:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Marital Status Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Marital Status:Marital Status</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Marital Status Hierarchy - Marital Status Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Product:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Product Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Product:Brand Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Product Hierarchy - Brand Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Product:Product Category</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Product Hierarchy - Product Category Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Product:Product Department</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Product Hierarchy - Product Department Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Product:Product Family</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Product Hierarchy - Product Family Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Product:Product Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Product Hierarchy - Product Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Product:Product Subcategory</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Product Hierarchy - Product Subcategory Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Promotion Media:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Promotion Media Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Promotion Media:Media Type</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Promotion Media Hierarchy - Media Type Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Promotions:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Promotions Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Promotions:Promotion Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Promotions Hierarchy - Promotion Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Store Size in SQFT:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Store Size in SQFT Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Store Size in SQFT:Store Sqft</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Store Size in SQFT Hierarchy - Store Sqft Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Store Type:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Store Type Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Store Type:Store Type</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Store Type Hierarchy - Store Type Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Store:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Store Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Store:Store City</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Store Hierarchy - Store City Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Store:Store Country</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Store Hierarchy - Store Country Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Store:Store Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Store Hierarchy - Store Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Store:Store State</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Store Hierarchy - Store State Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Time.Weekly:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Time.Weekly Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Time.Weekly:Day</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Time.Weekly Hierarchy - Day Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Time.Weekly:Week</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Time.Weekly Hierarchy - Week Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Time.Weekly:Year</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Time.Weekly Hierarchy - Year Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Time:Month</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Time Hierarchy - Month Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Time:Quarter</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Time Hierarchy - Quarter Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Time:Year</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Time Hierarchy - Year Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Yearly Income:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Yearly Income Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged:Yearly Income:Yearly Income</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube - Yearly Income Hierarchy - Yearly Income Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Customers:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Customers Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Customers:City</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Customers Hierarchy - City Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Customers:Country</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Customers Hierarchy - Country Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Customers:Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Customers Hierarchy - Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Customers:State Province</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Customers Hierarchy - State Province Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Education Level:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Education Level Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Education Level:Education Level</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Education Level Hierarchy - Education Level Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Gender:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Gender Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Gender:Gender</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Gender Hierarchy - Gender Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Marital Status:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Marital Status Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Marital Status:Marital Status</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Marital Status Hierarchy - Marital Status Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Product:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Product Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Product:Brand Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Product Hierarchy - Brand Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Product:Product Category</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Product Hierarchy - Product Category Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Product:Product Department</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Product Hierarchy - Product Department Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Product:Product Family</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Product Hierarchy - Product Family Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Product:Product Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Product Hierarchy - Product Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Product:Product Subcategory</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Product Hierarchy - Product Subcategory Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Promotion Media:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Promotion Media Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Promotion Media:Media Type</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Promotion Media Hierarchy - Media Type Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Promotions:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Promotions Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Promotions:Promotion Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Promotions Hierarchy - Promotion Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Store Size in SQFT:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Store Size in SQFT Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Store Size in SQFT:Store Sqft</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Store Size in SQFT Hierarchy - Store Sqft Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Store Type:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Store Type Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Store Type:Store Type</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Store Type Hierarchy - Store Type Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Store:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Store Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Store:Store City</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Store Hierarchy - Store City Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Store:Store Country</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Store Hierarchy - Store Country Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Store:Store Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Store Hierarchy - Store Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Store:Store State</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Store Hierarchy - Store State Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Time.Weekly:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Time.Weekly Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Time.Weekly:Day</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Time.Weekly Hierarchy - Day Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Time.Weekly:Week</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Time.Weekly Hierarchy - Week Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Time.Weekly:Year</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Time.Weekly Hierarchy - Year Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Time:Month</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Time Hierarchy - Month Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Time:Quarter</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Time Hierarchy - Quarter Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Time:Year</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Time Hierarchy - Year Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Yearly Income:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Yearly Income Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales:Yearly Income:Yearly Income</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube - Yearly Income Hierarchy - Yearly Income Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Store:Has coffee bar:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Store Cube - Has coffee bar Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Store:Has coffee bar:Has coffee bar</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Store Cube - Has coffee bar Hierarchy - Has coffee bar Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Store:Store Type:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Store Cube - Store Type Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Store:Store Type:Store Type</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Store Cube - Store Type Hierarchy - Store Type Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Store:Store:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Store Cube - Store Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Store:Store:Store City</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Store Cube - Store Hierarchy - Store City Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Store:Store:Store Country</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Store Cube - Store Hierarchy - Store Country Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Store:Store:Store Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Store Cube - Store Hierarchy - Store Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Store:Store:Store State</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Store Cube - Store Hierarchy - Store State Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Customers:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Customers Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Customers:City</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Customers Hierarchy - City Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Customers:Country</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Customers Hierarchy - Country Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Customers:Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Customers Hierarchy - Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Customers:State Province</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Customers Hierarchy - State Province Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Education Level:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Education Level Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Education Level:Education Level</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Education Level Hierarchy - Education Level Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Gender:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Gender Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Gender:Gender</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Gender Hierarchy - Gender Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Marital Status:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Marital Status Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Marital Status:Marital Status</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Marital Status Hierarchy - Marital Status Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Product:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Product Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Product:Brand Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Product Hierarchy - Brand Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Product:Product Category</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Product Hierarchy - Product Category Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Product:Product Department</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Product Hierarchy - Product Department Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Product:Product Family</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Product Hierarchy - Product Family Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Product:Product Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Product Hierarchy - Product Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Product:Product Subcategory</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Product Hierarchy - Product Subcategory Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Promotion Media:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Promotion Media Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Promotion Media:Media Type</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Promotion Media Hierarchy - Media Type Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Promotions:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Promotions Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Promotions:Promotion Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Promotions Hierarchy - Promotion Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Store:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Store Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Store:Store City</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Store Hierarchy - Store City Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Store:Store Country</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Store Hierarchy - Store Country Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Store:Store Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Store Hierarchy - Store Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Store:Store State</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Store Hierarchy - Store State Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Time.Weekly:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Time.Weekly Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Time.Weekly:Day</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Time.Weekly Hierarchy - Day Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Time.Weekly:Week</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Time.Weekly Hierarchy - Week Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Time.Weekly:Year</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Time.Weekly Hierarchy - Year Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Time:Month</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Time Hierarchy - Month Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Time:Quarter</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Time Hierarchy - Quarter Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Time:Year</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Time Hierarchy - Year Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Warehouse:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Warehouse Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Warehouse:City</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Warehouse Hierarchy - City Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Warehouse:Country</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Warehouse Hierarchy - Country Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Warehouse:State Province</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Warehouse Hierarchy - State Province Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Warehouse:Warehouse Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Warehouse Hierarchy - Warehouse Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Yearly Income:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Yearly Income Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales:Yearly Income:Yearly Income</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube - Yearly Income Hierarchy - Yearly Income Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Product:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Product Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Product:Brand Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Product Hierarchy - Brand Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Product:Product Category</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Product Hierarchy - Product Category Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Product:Product Department</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Product Hierarchy - Product Department Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Product:Product Family</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Product Hierarchy - Product Family Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Product:Product Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Product Hierarchy - Product Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Product:Product Subcategory</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Product Hierarchy - Product Subcategory Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Store Size in SQFT:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Store Size in SQFT Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Store Size in SQFT:Store Sqft</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Store Size in SQFT Hierarchy - Store Sqft Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Store Type:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Store Type Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Store Type:Store Type</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Store Type Hierarchy - Store Type Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Store:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Store Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Store:Store City</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Store Hierarchy - Store City Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Store:Store Country</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Store Hierarchy - Store Country Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Store:Store Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Store Hierarchy - Store Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Store:Store State</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Store Hierarchy - Store State Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Time.Weekly:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Time.Weekly Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Time.Weekly:Day</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Time.Weekly Hierarchy - Day Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Time.Weekly:Week</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Time.Weekly Hierarchy - Week Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Time.Weekly:Year</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Time.Weekly Hierarchy - Year Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Time:Month</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Time Hierarchy - Month Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Time:Quarter</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Time Hierarchy - Quarter Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Time:Year</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Time Hierarchy - Year Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Warehouse:(All)</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Warehouse Hierarchy - (All) Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Warehouse:City</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Warehouse Hierarchy - City Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Warehouse:Country</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Warehouse Hierarchy - Country Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Warehouse:State Province</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Warehouse Hierarchy - State Province Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse:Warehouse:Warehouse Name</TABLE_NAME>
                                <TABLE_TYPE>SYSTEM TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube - Warehouse Hierarchy - Warehouse Name Level</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>HR</TABLE_NAME>
                                <TABLE_TYPE>TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - HR Cube</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales</TABLE_NAME>
                                <TABLE_TYPE>TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Cube</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales 2</TABLE_NAME>
                                <TABLE_TYPE>TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales 2 Cube</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Sales Ragged</TABLE_NAME>
                                <TABLE_TYPE>TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Sales Ragged Cube</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Store</TABLE_NAME>
                                <TABLE_TYPE>TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Store Cube</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse</TABLE_NAME>
                                <TABLE_TYPE>TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse Cube</DESCRIPTION>
                            </row>
                            <row>
                                <TABLE_CATALOG>FoodMart</TABLE_CATALOG>
                                <TABLE_NAME>Warehouse and Sales</TABLE_NAME>
                                <TABLE_TYPE>TABLE</TABLE_TYPE>
                                <DESCRIPTION>FoodMart - Warehouse and Sales Cube</DESCRIPTION>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
                """;
    public static final String DBSCHEMA_SOURCE_TABLES_RESPONSE = """
        """;
    public static final String DBSCHEMA_TABLES_INFO_RESPONSE = """
        """;
    public static final String MDSCHEMA_ACTIONS_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element minOccurs="0" name="CATALOG_NAME" sql:field="CATALOG_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="SCHEMA_NAME" sql:field="SCHEMA_NAME" type="xsd:string"/>
                                        <xsd:element name="CUBE_NAME" sql:field="CUBE_NAME" type="xsd:string"/>
                                        <xsd:element name="ACTION_NAME" sql:field="ACTION_NAME" type="xsd:string"/>
                                        <xsd:element name="COORDINATE" sql:field="COORDINATE" type="xsd:string"/>
                                        <xsd:element name="COORDINATE_TYPE" sql:field="COORDINATE_TYPE" type="xsd:int"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
                """;
    public static final String MDSCHEMA_CUBES_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element minOccurs="0" name="CATALOG_NAME" sql:field="CATALOG_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="SCHEMA_NAME" sql:field="SCHEMA_NAME" type="xsd:string"/>
                                        <xsd:element name="CUBE_NAME" sql:field="CUBE_NAME" type="xsd:string"/>
                                        <xsd:element name="CUBE_TYPE" sql:field="CUBE_TYPE" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="CUBE_GUID" sql:field="CUBE_GUID" type="uuid"/>
                                        <xsd:element minOccurs="0" name="CREATED_ON" sql:field="CREATED_ON" type="xsd:dateTime"/>
                                        <xsd:element minOccurs="0" name="LAST_SCHEMA_UPDATE" sql:field="LAST_SCHEMA_UPDATE" type="xsd:dateTime"/>
                                        <xsd:element minOccurs="0" name="SCHEMA_UPDATED_BY" sql:field="SCHEMA_UPDATED_BY" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="LAST_DATA_UPDATE" sql:field="LAST_DATA_UPDATE" type="xsd:dateTime"/>
                                        <xsd:element minOccurs="0" name="DATA_UPDATED_BY" sql:field="DATA_UPDATED_BY" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="DESCRIPTION" sql:field="DESCRIPTION" type="xsd:string"/>
                                        <xsd:element name="IS_DRILLTHROUGH_ENABLED" sql:field="IS_DRILLTHROUGH_ENABLED" type="xsd:boolean"/>
                                        <xsd:element name="IS_LINKABLE" sql:field="IS_LINKABLE" type="xsd:boolean"/>
                                        <xsd:element name="IS_WRITE_ENABLED" sql:field="IS_WRITE_ENABLED" type="xsd:boolean"/>
                                        <xsd:element name="IS_SQL_ENABLED" sql:field="IS_SQL_ENABLED" type="xsd:boolean"/>
                                        <xsd:element minOccurs="0" name="CUBE_CAPTION" sql:field="CUBE_CAPTION" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="BASE_CUBE_NAME" sql:field="BASE_CUBE_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="DIMENSIONS" sql:field="DIMENSIONS"/>
                                        <xsd:element minOccurs="0" name="SETS" sql:field="SETS"/>
                                        <xsd:element minOccurs="0" name="MEASURES" sql:field="MEASURES"/>
                                        <xsd:element minOccurs="0" name="CUBE_SOURCE" sql:field="CUBE_SOURCE" type="xsd:int"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <CUBE_TYPE>CUBE</CUBE_TYPE>
                                <LAST_SCHEMA_UPDATE>xxxx-xx-xxTxx:xx:xx</LAST_SCHEMA_UPDATE>
                                <LAST_DATA_UPDATE>xxxx-xx-xxTxx:xx:xx</LAST_DATA_UPDATE>
                                <DESCRIPTION>FoodMart Schema - HR Cube</DESCRIPTION>
                                <IS_DRILLTHROUGH_ENABLED>true</IS_DRILLTHROUGH_ENABLED>
                                <IS_LINKABLE>false</IS_LINKABLE>
                                <IS_WRITE_ENABLED>false</IS_WRITE_ENABLED>
                                <IS_SQL_ENABLED>false</IS_SQL_ENABLED>
                                <CUBE_CAPTION>HR</CUBE_CAPTION>
                                <CUBE_SOURCE>1</CUBE_SOURCE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <CUBE_TYPE>CUBE</CUBE_TYPE>
                                <LAST_SCHEMA_UPDATE>xxxx-xx-xxTxx:xx:xx</LAST_SCHEMA_UPDATE>
                                <LAST_DATA_UPDATE>xxxx-xx-xxTxx:xx:xx</LAST_DATA_UPDATE>
                                <DESCRIPTION>FoodMart Schema - Sales Cube</DESCRIPTION>
                                <IS_DRILLTHROUGH_ENABLED>true</IS_DRILLTHROUGH_ENABLED>
                                <IS_LINKABLE>false</IS_LINKABLE>
                                <IS_WRITE_ENABLED>false</IS_WRITE_ENABLED>
                                <IS_SQL_ENABLED>false</IS_SQL_ENABLED>
                                <CUBE_CAPTION>Sales</CUBE_CAPTION>
                                <CUBE_SOURCE>1</CUBE_SOURCE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales 2</CUBE_NAME>
                                <CUBE_TYPE>CUBE</CUBE_TYPE>
                                <LAST_SCHEMA_UPDATE>xxxx-xx-xxTxx:xx:xx</LAST_SCHEMA_UPDATE>
                                <LAST_DATA_UPDATE>xxxx-xx-xxTxx:xx:xx</LAST_DATA_UPDATE>
                                <DESCRIPTION>FoodMart Schema - Sales 2 Cube</DESCRIPTION>
                                <IS_DRILLTHROUGH_ENABLED>true</IS_DRILLTHROUGH_ENABLED>
                                <IS_LINKABLE>false</IS_LINKABLE>
                                <IS_WRITE_ENABLED>false</IS_WRITE_ENABLED>
                                <IS_SQL_ENABLED>false</IS_SQL_ENABLED>
                                <CUBE_CAPTION>Sales 2</CUBE_CAPTION>
                                <CUBE_SOURCE>1</CUBE_SOURCE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <CUBE_TYPE>CUBE</CUBE_TYPE>
                                <LAST_SCHEMA_UPDATE>xxxx-xx-xxTxx:xx:xx</LAST_SCHEMA_UPDATE>
                                <LAST_DATA_UPDATE>xxxx-xx-xxTxx:xx:xx</LAST_DATA_UPDATE>
                                <DESCRIPTION>FoodMart Schema - Sales Ragged Cube</DESCRIPTION>
                                <IS_DRILLTHROUGH_ENABLED>true</IS_DRILLTHROUGH_ENABLED>
                                <IS_LINKABLE>false</IS_LINKABLE>
                                <IS_WRITE_ENABLED>false</IS_WRITE_ENABLED>
                                <IS_SQL_ENABLED>false</IS_SQL_ENABLED>
                                <CUBE_CAPTION>Sales Ragged</CUBE_CAPTION>
                                <CUBE_SOURCE>1</CUBE_SOURCE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Store</CUBE_NAME>
                                <CUBE_TYPE>CUBE</CUBE_TYPE>
                                <LAST_SCHEMA_UPDATE>xxxx-xx-xxTxx:xx:xx</LAST_SCHEMA_UPDATE>
                                <LAST_DATA_UPDATE>xxxx-xx-xxTxx:xx:xx</LAST_DATA_UPDATE>
                                <DESCRIPTION>FoodMart Schema - Store Cube</DESCRIPTION>
                                <IS_DRILLTHROUGH_ENABLED>true</IS_DRILLTHROUGH_ENABLED>
                                <IS_LINKABLE>false</IS_LINKABLE>
                                <IS_WRITE_ENABLED>false</IS_WRITE_ENABLED>
                                <IS_SQL_ENABLED>false</IS_SQL_ENABLED>
                                <CUBE_CAPTION>Store</CUBE_CAPTION>
                                <CUBE_SOURCE>1</CUBE_SOURCE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <CUBE_TYPE>CUBE</CUBE_TYPE>
                                <LAST_SCHEMA_UPDATE>xxxx-xx-xxTxx:xx:xx</LAST_SCHEMA_UPDATE>
                                <LAST_DATA_UPDATE>xxxx-xx-xxTxx:xx:xx</LAST_DATA_UPDATE>
                                <DESCRIPTION>FoodMart Schema - Warehouse Cube</DESCRIPTION>
                                <IS_DRILLTHROUGH_ENABLED>true</IS_DRILLTHROUGH_ENABLED>
                                <IS_LINKABLE>false</IS_LINKABLE>
                                <IS_WRITE_ENABLED>false</IS_WRITE_ENABLED>
                                <IS_SQL_ENABLED>false</IS_SQL_ENABLED>
                                <CUBE_CAPTION>Warehouse</CUBE_CAPTION>
                                <CUBE_SOURCE>1</CUBE_SOURCE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <CUBE_TYPE>VIRTUAL CUBE</CUBE_TYPE>
                                <LAST_SCHEMA_UPDATE>xxxx-xx-xxTxx:xx:xx</LAST_SCHEMA_UPDATE>
                                <LAST_DATA_UPDATE>xxxx-xx-xxTxx:xx:xx</LAST_DATA_UPDATE>
                                <DESCRIPTION>FoodMart Schema - Warehouse and Sales Cube</DESCRIPTION>
                                <IS_DRILLTHROUGH_ENABLED>true</IS_DRILLTHROUGH_ENABLED>
                                <IS_LINKABLE>false</IS_LINKABLE>
                                <IS_WRITE_ENABLED>false</IS_WRITE_ENABLED>
                                <IS_SQL_ENABLED>false</IS_SQL_ENABLED>
                                <CUBE_CAPTION>Warehouse and Sales</CUBE_CAPTION>
                                <CUBE_SOURCE>1</CUBE_SOURCE>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_DIMENSIONS_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element minOccurs="0" name="CATALOG_NAME" sql:field="CATALOG_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="SCHEMA_NAME" sql:field="SCHEMA_NAME" type="xsd:string"/>
                                        <xsd:element name="CUBE_NAME" sql:field="CUBE_NAME" type="xsd:string"/>
                                        <xsd:element name="DIMENSION_NAME" sql:field="DIMENSION_NAME" type="xsd:string"/>
                                        <xsd:element name="DIMENSION_UNIQUE_NAME" sql:field="DIMENSION_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="DIMENSION_GUID" sql:field="DIMENSION_GUID" type="uuid"/>
                                        <xsd:element name="DIMENSION_CAPTION" sql:field="DIMENSION_CAPTION" type="xsd:string"/>
                                        <xsd:element name="DIMENSION_ORDINAL" sql:field="DIMENSION_ORDINAL" type="xsd:unsignedInt"/>
                                        <xsd:element name="DIMENSION_TYPE" sql:field="DIMENSION_TYPE" type="xsd:short"/>
                                        <xsd:element name="DIMENSION_CARDINALITY" sql:field="DIMENSION_CARDINALITY" type="xsd:unsignedInt"/>
                                        <xsd:element name="DEFAULT_HIERARCHY" sql:field="DEFAULT_HIERARCHY" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="DESCRIPTION" sql:field="DESCRIPTION" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="IS_VIRTUAL" sql:field="IS_VIRTUAL" type="xsd:boolean"/>
                                        <xsd:element minOccurs="0" name="IS_READWRITE" sql:field="IS_READWRITE" type="xsd:boolean"/>
                                        <xsd:element minOccurs="0" name="DIMENSION_UNIQUE_SETTINGS" sql:field="DIMENSION_UNIQUE_SETTINGS" type="xsd:int"/>
                                        <xsd:element minOccurs="0" name="DIMENSION_MASTER_UNIQUE_NAME" sql:field="DIMENSION_MASTER_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="DIMENSION_IS_VISIBLE" sql:field="DIMENSION_IS_VISIBLE" type="xsd:boolean"/>
                                        <xsd:element minOccurs="0" name="HIERARCHIES" sql:field="HIERARCHIES"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME/>
                                <DIMENSION_NAME>Product</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Product]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Product</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>0</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>1561</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Product]</DEFAULT_HIERARCHY>
                                <DESCRIPTION> Cube - Product Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME/>
                                <DIMENSION_NAME>Store</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>1</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>26</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store]</DEFAULT_HIERARCHY>
                                <DESCRIPTION> Cube - Store Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME/>
                                <DIMENSION_NAME>Store Size in SQFT</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store Size in SQFT]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store Size in SQFT</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>2</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>22</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store Size in SQFT]</DEFAULT_HIERARCHY>
                                <DESCRIPTION> Cube - Store Size in SQFT Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME/>
                                <DIMENSION_NAME>Store Type</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store Type]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store Type</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>3</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>7</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store Type]</DEFAULT_HIERARCHY>
                                <DESCRIPTION> Cube - Store Type Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME/>
                                <DIMENSION_NAME>Time</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Time]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Time</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>4</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>1</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>25</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Time]</DEFAULT_HIERARCHY>
                                <DESCRIPTION> Cube - Time Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME/>
                                <DIMENSION_NAME>Warehouse</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Warehouse]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Warehouse</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>5</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>25</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Warehouse]</DEFAULT_HIERARCHY>
                                <DESCRIPTION> Cube - Warehouse Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_NAME>Department</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Department]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Department</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>6</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>13</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Department]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>HR Cube - Department Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_NAME>Employees</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Employees]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Employees</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>7</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>1156</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Employees]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>HR Cube - Employees Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_NAME>Measures</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Measures]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Measures</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>0</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>2</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>6</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Measures]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>HR Cube - Measures Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_NAME>Pay Type</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Pay Type]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Pay Type</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>3</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>3</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Pay Type]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>HR Cube - Pay Type Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_NAME>Position</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Position]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Position</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>5</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>19</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Position]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>HR Cube - Position Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_NAME>Store</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>2</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>26</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>HR Cube - Store Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_NAME>Store Type</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store Type]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store Type</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>4</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>7</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store Type]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>HR Cube - Store Type Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_NAME>Time</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Time]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Time</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>1</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>1</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>25</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Time]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>HR Cube - Time Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_NAME>Customers</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Customers</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>8</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>10282</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Customers]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Cube - Customers Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_NAME>Education Level</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Education Level]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Education Level</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>9</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>6</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Education Level]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Cube - Education Level Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_NAME>Gender</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Gender]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Gender</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>10</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>3</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Gender]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Cube - Gender Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_NAME>Marital Status</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Marital Status]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Marital Status</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>11</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>112</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Marital Status]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Cube - Marital Status Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_NAME>Measures</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Measures]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Measures</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>0</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>2</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>10</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Measures]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Cube - Measures Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_NAME>Product</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Product]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Product</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>5</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>1561</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Product]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Cube - Product Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_NAME>Promotion Media</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Promotion Media]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Promotion Media</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>6</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>15</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Promotion Media]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Cube - Promotion Media Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_NAME>Promotions</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Promotions]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Promotions</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>7</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>52</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Promotions]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Cube - Promotions Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_NAME>Store</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>1</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>26</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Cube - Store Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_NAME>Store Size in SQFT</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store Size in SQFT]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store Size in SQFT</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>2</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>22</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store Size in SQFT]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Cube - Store Size in SQFT Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_NAME>Store Type</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store Type]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store Type</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>3</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>7</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store Type]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Cube - Store Type Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_NAME>Time</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Time]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Time</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>4</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>1</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>25</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Time]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Cube - Time Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_NAME>Yearly Income</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Yearly Income]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Yearly Income</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>12</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>9</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Yearly Income]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Cube - Yearly Income Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales 2</CUBE_NAME>
                                <DIMENSION_NAME>Gender</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Gender]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Gender</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>3</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>3</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Gender]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales 2 Cube - Gender Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales 2</CUBE_NAME>
                                <DIMENSION_NAME>Measures</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Measures]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Measures</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>0</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>2</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>8</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Measures]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales 2 Cube - Measures Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales 2</CUBE_NAME>
                                <DIMENSION_NAME>Product</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Product]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Product</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>2</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>1561</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Product]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales 2 Cube - Product Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales 2</CUBE_NAME>
                                <DIMENSION_NAME>Time</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Time]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Time</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>1</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>1</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>25</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Time]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales 2 Cube - Time Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_NAME>Customers</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Customers</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>9</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>10182</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Customers]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Ragged Cube - Customers Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_NAME>Education Level</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Education Level]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Education Level</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>10</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>6</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Education Level]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Ragged Cube - Education Level Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_NAME>Gender</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Gender]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Gender</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>11</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>3</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Gender]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Ragged Cube - Gender Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_NAME>Geography</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Geography]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Geography</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>2</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>25</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Geography]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Ragged Cube - Geography Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_NAME>Marital Status</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Marital Status]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Marital Status</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>12</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>3</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Marital Status]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Ragged Cube - Marital Status Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_NAME>Measures</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Measures]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Measures</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>0</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>2</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>6</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Measures]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Ragged Cube - Measures Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_NAME>Product</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Product]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Product</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>6</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>1561</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Product]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Ragged Cube - Product Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_NAME>Promotion Media</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Promotion Media]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Promotion Media</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>7</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>15</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Promotion Media]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Ragged Cube - Promotion Media Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_NAME>Promotions</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Promotions]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Promotions</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>8</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>52</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Promotions]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Ragged Cube - Promotions Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_NAME>Store</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>1</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>26</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Ragged Cube - Store Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_NAME>Store Size in SQFT</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store Size in SQFT]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store Size in SQFT</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>3</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>22</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store Size in SQFT]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Ragged Cube - Store Size in SQFT Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_NAME>Store Type</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store Type]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store Type</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>4</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>7</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store Type]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Ragged Cube - Store Type Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_NAME>Time</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Time]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Time</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>5</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>1</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>25</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Time]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Ragged Cube - Time Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_NAME>Yearly Income</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Yearly Income]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Yearly Income</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>13</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>9</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Yearly Income]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Sales Ragged Cube - Yearly Income Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Store</CUBE_NAME>
                                <DIMENSION_NAME>Has coffee bar</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Has coffee bar]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Has coffee bar</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>3</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>3</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Has coffee bar]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Store Cube - Has coffee bar Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Store</CUBE_NAME>
                                <DIMENSION_NAME>Measures</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Measures]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Measures</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>0</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>2</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>4</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Measures]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Store Cube - Measures Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Store</CUBE_NAME>
                                <DIMENSION_NAME>Store</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>2</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>26</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Store Cube - Store Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Store</CUBE_NAME>
                                <DIMENSION_NAME>Store Type</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store Type]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store Type</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>1</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>7</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store Type]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Store Cube - Store Type Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_NAME>Measures</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Measures]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Measures</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>0</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>2</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>10</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Measures]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse Cube - Measures Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_NAME>Product</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Product]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Product</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>5</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>1561</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Product]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse Cube - Product Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_NAME>Store</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>1</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>26</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse Cube - Store Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_NAME>Store Size in SQFT</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store Size in SQFT]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store Size in SQFT</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>2</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>22</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store Size in SQFT]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse Cube - Store Size in SQFT Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_NAME>Store Type</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store Type]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store Type</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>3</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>7</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store Type]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse Cube - Store Type Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_NAME>Time</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Time]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Time</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>4</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>1</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>25</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Time]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse Cube - Time Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_NAME>Warehouse</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Warehouse]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Warehouse</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>6</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>25</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Warehouse]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse Cube - Warehouse Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_NAME>Customers</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Customers</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>1</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>10282</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Customers]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse and Sales Cube - Customers Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_NAME>Education Level</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Education Level]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Education Level</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>2</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>6</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Education Level]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse and Sales Cube - Education Level Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_NAME>Gender</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Gender]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Gender</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>3</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>3</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Gender]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse and Sales Cube - Gender Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_NAME>Marital Status</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Marital Status]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Marital Status</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>4</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>112</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Marital Status]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse and Sales Cube - Marital Status Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_NAME>Measures</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Measures]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Measures</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>0</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>2</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>16</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Measures]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse and Sales Cube - Measures Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_NAME>Product</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Product]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Product</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>5</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>1561</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Product]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse and Sales Cube - Product Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_NAME>Promotion Media</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Promotion Media]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Promotion Media</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>6</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>15</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Promotion Media]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse and Sales Cube - Promotion Media Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_NAME>Promotions</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Promotions]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Promotions</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>7</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>52</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Promotions]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse and Sales Cube - Promotions Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_NAME>Store</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Store</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>8</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>26</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Store]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse and Sales Cube - Store Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_NAME>Time</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Time]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Time</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>9</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>1</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>25</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Time]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse and Sales Cube - Time Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_NAME>Warehouse</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Warehouse]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Warehouse</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>11</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>25</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Warehouse]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse and Sales Cube - Warehouse Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_NAME>Yearly Income</DIMENSION_NAME>
                                <DIMENSION_UNIQUE_NAME>[Yearly Income]</DIMENSION_UNIQUE_NAME>
                                <DIMENSION_CAPTION>Yearly Income</DIMENSION_CAPTION>
                                <DIMENSION_ORDINAL>10</DIMENSION_ORDINAL>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <DIMENSION_CARDINALITY>9</DIMENSION_CARDINALITY>
                                <DEFAULT_HIERARCHY>[Yearly Income]</DEFAULT_HIERARCHY>
                                <DESCRIPTION>Warehouse and Sales Cube - Yearly Income Dimension</DESCRIPTION>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_FUNCTIONS_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element name="FUNCTION_NAME" sql:field="FUNCTION_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="DESCRIPTION" sql:field="DESCRIPTION" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="PARAMETER_LIST" sql:field="PARAMETER_LIST" type="xsd:string"/>
                                        <xsd:element name="RETURN_TYPE" sql:field="RETURN_TYPE" type="xsd:int"/>
                                        <xsd:element name="ORIGIN" sql:field="ORIGIN" type="xsd:int"/>
                                        <xsd:element name="INTERFACE_NAME" sql:field="INTERFACE_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="LIBRARY_NAME" sql:field="LIBRARY_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="CAPTION" sql:field="CAPTION" type="xsd:string"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <FUNCTION_NAME>Item</FUNCTION_NAME>
                                <DESCRIPTION>Returns a member from the tuple specified in &lt;Tuple&gt;. The member to be returned is specified by the zero-based position of the member in the set in &lt;Index&gt;.</DESCRIPTION>
                                <PARAMETER_LIST>Tuple, Numeric Expression</PARAMETER_LIST>
                                <RETURN_TYPE>12</RETURN_TYPE>
                                <ORIGIN>1</ORIGIN>
                                <INTERFACE_NAME/>
                                <CAPTION>Item</CAPTION>
                            </row>
                            <row>
                                <FUNCTION_NAME>Item</FUNCTION_NAME>
                                <DESCRIPTION>Returns a tuple from the set specified in &lt;Set&gt;. The tuple to be returned is specified by the zero-based position of the tuple in the set in &lt;Index&gt;.</DESCRIPTION>
                                <PARAMETER_LIST>Set, Numeric Expression</PARAMETER_LIST>
                                <RETURN_TYPE>12</RETURN_TYPE>
                                <ORIGIN>1</ORIGIN>
                                <INTERFACE_NAME/>
                                <CAPTION>Item</CAPTION>
                            </row>
                            <row>
                                <FUNCTION_NAME>Item</FUNCTION_NAME>
                                <DESCRIPTION>Returns a tuple from the set specified in &lt;Set&gt;. The tuple to be returned is specified by the member name (or names) in &lt;String&gt;.</DESCRIPTION>
                                <PARAMETER_LIST>(none)</PARAMETER_LIST>
                                <RETURN_TYPE>1</RETURN_TYPE>
                                <ORIGIN>1</ORIGIN>
                                <INTERFACE_NAME/>
                                <CAPTION>Item</CAPTION>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_HIERARCHIES_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element minOccurs="0" name="CATALOG_NAME" sql:field="CATALOG_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="SCHEMA_NAME" sql:field="SCHEMA_NAME" type="xsd:string"/>
                                        <xsd:element name="CUBE_NAME" sql:field="CUBE_NAME" type="xsd:string"/>
                                        <xsd:element name="DIMENSION_UNIQUE_NAME" sql:field="DIMENSION_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element name="HIERARCHY_NAME" sql:field="HIERARCHY_NAME" type="xsd:string"/>
                                        <xsd:element name="HIERARCHY_UNIQUE_NAME" sql:field="HIERARCHY_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="HIERARCHY_GUID" sql:field="HIERARCHY_GUID" type="uuid"/>
                                        <xsd:element name="HIERARCHY_CAPTION" sql:field="HIERARCHY_CAPTION" type="xsd:string"/>
                                        <xsd:element name="DIMENSION_TYPE" sql:field="DIMENSION_TYPE" type="xsd:short"/>
                                        <xsd:element name="HIERARCHY_CARDINALITY" sql:field="HIERARCHY_CARDINALITY" type="xsd:unsignedInt"/>
                                        <xsd:element minOccurs="0" name="DEFAULT_MEMBER" sql:field="DEFAULT_MEMBER" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="ALL_MEMBER" sql:field="ALL_MEMBER" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="DESCRIPTION" sql:field="DESCRIPTION" type="xsd:string"/>
                                        <xsd:element name="STRUCTURE" sql:field="STRUCTURE" type="xsd:short"/>
                                        <xsd:element name="IS_VIRTUAL" sql:field="IS_VIRTUAL" type="xsd:boolean"/>
                                        <xsd:element name="IS_READWRITE" sql:field="IS_READWRITE" type="xsd:boolean"/>
                                        <xsd:element name="DIMENSION_UNIQUE_SETTINGS" sql:field="DIMENSION_UNIQUE_SETTINGS" type="xsd:int"/>
                                        <xsd:element name="DIMENSION_IS_VISIBLE" sql:field="DIMENSION_IS_VISIBLE" type="xsd:boolean"/>
                                        <xsd:element name="HIERARCHY_ORDINAL" sql:field="HIERARCHY_ORDINAL" type="xsd:unsignedInt"/>
                                        <xsd:element name="DIMENSION_IS_SHARED" sql:field="DIMENSION_IS_SHARED" type="xsd:boolean"/>
                                        <xsd:element name="HIERARCHY_IS_VISIBLE" sql:field="HIERARCHY_IS_VISIBLE" type="xsd:boolean"/>
                                        <xsd:element minOccurs="0" name="HIERARCHY_ORIGIN" sql:field="HIERARCHY_ORIGIN" type="xsd:unsignedShort"/>
                                        <xsd:element minOccurs="0" name="HIERARCHY_DISPLAY_FOLDER" sql:field="HIERARCHY_DISPLAY_FOLDER" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="CUBE_SOURCE" sql:field="CUBE_SOURCE" type="xsd:unsignedShort"/>
                                        <xsd:element minOccurs="0" name="HIERARCHY_VISIBILITY" sql:field="HIERARCHY_VISIBILITY" type="xsd:unsignedShort"/>
                                        <xsd:element minOccurs="0" name="PARENT_CHILD" sql:field="PARENT_CHILD" type="xsd:boolean"/>
                                        <xsd:element minOccurs="0" name="LEVELS" sql:field="LEVELS"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_NAME>Customers</HIERARCHY_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <HIERARCHY_CAPTION>Customers</HIERARCHY_CAPTION>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <HIERARCHY_CARDINALITY>10407</HIERARCHY_CARDINALITY>
                                <DEFAULT_MEMBER>[Customers].[All Customers]</DEFAULT_MEMBER>
                                <ALL_MEMBER>[Customers].[All Customers]</ALL_MEMBER>
                                <DESCRIPTION>Sales Cube - Customers Hierarchy</DESCRIPTION>
                                <STRUCTURE>0</STRUCTURE>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                                <HIERARCHY_ORDINAL>9</HIERARCHY_ORDINAL>
                                <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
                                <HIERARCHY_IS_VISIBLE>true</HIERARCHY_IS_VISIBLE>
                                <HIERARCHY_ORIGIN>1</HIERARCHY_ORIGIN>
                                <HIERARCHY_DISPLAY_FOLDER/>
                                <HIERARCHY_VISIBILITY>1</HIERARCHY_VISIBILITY>
                                <PARENT_CHILD>false</PARENT_CHILD>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Education Level]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_NAME>Education Level</HIERARCHY_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Education Level]</HIERARCHY_UNIQUE_NAME>
                                <HIERARCHY_CAPTION>Education Level</HIERARCHY_CAPTION>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <HIERARCHY_CARDINALITY>6</HIERARCHY_CARDINALITY>
                                <DEFAULT_MEMBER>[Education Level].[All Education Levels]</DEFAULT_MEMBER>
                                <ALL_MEMBER>[Education Level].[All Education Levels]</ALL_MEMBER>
                                <DESCRIPTION>Sales Cube - Education Level Hierarchy</DESCRIPTION>
                                <STRUCTURE>0</STRUCTURE>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                                <HIERARCHY_ORDINAL>10</HIERARCHY_ORDINAL>
                                <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
                                <HIERARCHY_IS_VISIBLE>true</HIERARCHY_IS_VISIBLE>
                                <HIERARCHY_ORIGIN>1</HIERARCHY_ORIGIN>
                                <HIERARCHY_DISPLAY_FOLDER/>
                                <HIERARCHY_VISIBILITY>1</HIERARCHY_VISIBILITY>
                                <PARENT_CHILD>false</PARENT_CHILD>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Gender]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_NAME>Gender</HIERARCHY_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Gender]</HIERARCHY_UNIQUE_NAME>
                                <HIERARCHY_CAPTION>Gender</HIERARCHY_CAPTION>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <HIERARCHY_CARDINALITY>3</HIERARCHY_CARDINALITY>
                                <DEFAULT_MEMBER>[Gender].[All Gender]</DEFAULT_MEMBER>
                                <ALL_MEMBER>[Gender].[All Gender]</ALL_MEMBER>
                                <DESCRIPTION>Sales Cube - Gender Hierarchy</DESCRIPTION>
                                <STRUCTURE>0</STRUCTURE>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                                <HIERARCHY_ORDINAL>11</HIERARCHY_ORDINAL>
                                <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
                                <HIERARCHY_IS_VISIBLE>true</HIERARCHY_IS_VISIBLE>
                                <HIERARCHY_ORIGIN>1</HIERARCHY_ORIGIN>
                                <HIERARCHY_DISPLAY_FOLDER/>
                                <HIERARCHY_VISIBILITY>1</HIERARCHY_VISIBILITY>
                                <PARENT_CHILD>false</PARENT_CHILD>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Marital Status]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_NAME>Marital Status</HIERARCHY_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Marital Status]</HIERARCHY_UNIQUE_NAME>
                                <HIERARCHY_CAPTION>Marital Status</HIERARCHY_CAPTION>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <HIERARCHY_CARDINALITY>112</HIERARCHY_CARDINALITY>
                                <DEFAULT_MEMBER>[Marital Status].[All Marital Status]</DEFAULT_MEMBER>
                                <ALL_MEMBER>[Marital Status].[All Marital Status]</ALL_MEMBER>
                                <DESCRIPTION>Sales Cube - Marital Status Hierarchy</DESCRIPTION>
                                <STRUCTURE>0</STRUCTURE>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                                <HIERARCHY_ORDINAL>12</HIERARCHY_ORDINAL>
                                <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
                                <HIERARCHY_IS_VISIBLE>true</HIERARCHY_IS_VISIBLE>
                                <HIERARCHY_ORIGIN>1</HIERARCHY_ORIGIN>
                                <HIERARCHY_DISPLAY_FOLDER/>
                                <HIERARCHY_VISIBILITY>1</HIERARCHY_VISIBILITY>
                                <PARENT_CHILD>false</PARENT_CHILD>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Measures]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_NAME>Measures</HIERARCHY_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Measures]</HIERARCHY_UNIQUE_NAME>
                                <HIERARCHY_CAPTION>Measures</HIERARCHY_CAPTION>
                                <DIMENSION_TYPE>2</DIMENSION_TYPE>
                                <HIERARCHY_CARDINALITY>9</HIERARCHY_CARDINALITY>
                                <DEFAULT_MEMBER>[Measures].[Unit Sales]</DEFAULT_MEMBER>
                                <DESCRIPTION>Sales Cube - Measures Hierarchy</DESCRIPTION>
                                <STRUCTURE>0</STRUCTURE>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                                <HIERARCHY_ORDINAL>0</HIERARCHY_ORDINAL>
                                <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
                                <HIERARCHY_IS_VISIBLE>true</HIERARCHY_IS_VISIBLE>
                                <HIERARCHY_ORIGIN>6</HIERARCHY_ORIGIN>
                                <HIERARCHY_DISPLAY_FOLDER/>
                                <HIERARCHY_VISIBILITY>1</HIERARCHY_VISIBILITY>
                                <PARENT_CHILD>false</PARENT_CHILD>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Product]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_NAME>Product</HIERARCHY_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Product]</HIERARCHY_UNIQUE_NAME>
                                <HIERARCHY_CAPTION>Product</HIERARCHY_CAPTION>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <HIERARCHY_CARDINALITY>2256</HIERARCHY_CARDINALITY>
                                <DEFAULT_MEMBER>[Product].[All Products]</DEFAULT_MEMBER>
                                <ALL_MEMBER>[Product].[All Products]</ALL_MEMBER>
                                <DESCRIPTION>Sales Cube - Product Hierarchy</DESCRIPTION>
                                <STRUCTURE>0</STRUCTURE>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                                <HIERARCHY_ORDINAL>6</HIERARCHY_ORDINAL>
                                <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
                                <HIERARCHY_IS_VISIBLE>true</HIERARCHY_IS_VISIBLE>
                                <HIERARCHY_ORIGIN>1</HIERARCHY_ORIGIN>
                                <HIERARCHY_DISPLAY_FOLDER/>
                                <HIERARCHY_VISIBILITY>1</HIERARCHY_VISIBILITY>
                                <PARENT_CHILD>false</PARENT_CHILD>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Promotion Media]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_NAME>Promotion Media</HIERARCHY_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Promotion Media]</HIERARCHY_UNIQUE_NAME>
                                <HIERARCHY_CAPTION>Promotion Media</HIERARCHY_CAPTION>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <HIERARCHY_CARDINALITY>15</HIERARCHY_CARDINALITY>
                                <DEFAULT_MEMBER>[Promotion Media].[All Media]</DEFAULT_MEMBER>
                                <ALL_MEMBER>[Promotion Media].[All Media]</ALL_MEMBER>
                                <DESCRIPTION>Sales Cube - Promotion Media Hierarchy</DESCRIPTION>
                                <STRUCTURE>0</STRUCTURE>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                                <HIERARCHY_ORDINAL>7</HIERARCHY_ORDINAL>
                                <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
                                <HIERARCHY_IS_VISIBLE>true</HIERARCHY_IS_VISIBLE>
                                <HIERARCHY_ORIGIN>1</HIERARCHY_ORIGIN>
                                <HIERARCHY_DISPLAY_FOLDER/>
                                <HIERARCHY_VISIBILITY>1</HIERARCHY_VISIBILITY>
                                <PARENT_CHILD>false</PARENT_CHILD>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Promotions]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_NAME>Promotions</HIERARCHY_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Promotions]</HIERARCHY_UNIQUE_NAME>
                                <HIERARCHY_CAPTION>Promotions</HIERARCHY_CAPTION>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <HIERARCHY_CARDINALITY>52</HIERARCHY_CARDINALITY>
                                <DEFAULT_MEMBER>[Promotions].[All Promotions]</DEFAULT_MEMBER>
                                <ALL_MEMBER>[Promotions].[All Promotions]</ALL_MEMBER>
                                <DESCRIPTION>Sales Cube - Promotions Hierarchy</DESCRIPTION>
                                <STRUCTURE>0</STRUCTURE>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                                <HIERARCHY_ORDINAL>8</HIERARCHY_ORDINAL>
                                <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
                                <HIERARCHY_IS_VISIBLE>true</HIERARCHY_IS_VISIBLE>
                                <HIERARCHY_ORIGIN>1</HIERARCHY_ORIGIN>
                                <HIERARCHY_DISPLAY_FOLDER/>
                                <HIERARCHY_VISIBILITY>1</HIERARCHY_VISIBILITY>
                                <PARENT_CHILD>false</PARENT_CHILD>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store Size in SQFT]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_NAME>Store Size in SQFT</HIERARCHY_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store Size in SQFT]</HIERARCHY_UNIQUE_NAME>
                                <HIERARCHY_CAPTION>Store Size in SQFT</HIERARCHY_CAPTION>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <HIERARCHY_CARDINALITY>22</HIERARCHY_CARDINALITY>
                                <DEFAULT_MEMBER>[Store Size in SQFT].[All Store Size in SQFTs]</DEFAULT_MEMBER>
                                <ALL_MEMBER>[Store Size in SQFT].[All Store Size in SQFTs]</ALL_MEMBER>
                                <DESCRIPTION>Sales Cube - Store Size in SQFT Hierarchy</DESCRIPTION>
                                <STRUCTURE>0</STRUCTURE>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                                <HIERARCHY_ORDINAL>2</HIERARCHY_ORDINAL>
                                <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
                                <HIERARCHY_IS_VISIBLE>true</HIERARCHY_IS_VISIBLE>
                                <HIERARCHY_ORIGIN>1</HIERARCHY_ORIGIN>
                                <HIERARCHY_DISPLAY_FOLDER/>
                                <HIERARCHY_VISIBILITY>1</HIERARCHY_VISIBILITY>
                                <PARENT_CHILD>false</PARENT_CHILD>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store Type]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_NAME>Store Type</HIERARCHY_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store Type]</HIERARCHY_UNIQUE_NAME>
                                <HIERARCHY_CAPTION>Store Type</HIERARCHY_CAPTION>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <HIERARCHY_CARDINALITY>7</HIERARCHY_CARDINALITY>
                                <DEFAULT_MEMBER>[Store Type].[All Store Types]</DEFAULT_MEMBER>
                                <ALL_MEMBER>[Store Type].[All Store Types]</ALL_MEMBER>
                                <DESCRIPTION>Sales Cube - Store Type Hierarchy</DESCRIPTION>
                                <STRUCTURE>0</STRUCTURE>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                                <HIERARCHY_ORDINAL>3</HIERARCHY_ORDINAL>
                                <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
                                <HIERARCHY_IS_VISIBLE>true</HIERARCHY_IS_VISIBLE>
                                <HIERARCHY_ORIGIN>1</HIERARCHY_ORIGIN>
                                <HIERARCHY_DISPLAY_FOLDER/>
                                <HIERARCHY_VISIBILITY>1</HIERARCHY_VISIBILITY>
                                <PARENT_CHILD>false</PARENT_CHILD>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_NAME>Store</HIERARCHY_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <HIERARCHY_CAPTION>Store</HIERARCHY_CAPTION>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <HIERARCHY_CARDINALITY>63</HIERARCHY_CARDINALITY>
                                <DEFAULT_MEMBER>[Store].[All Stores]</DEFAULT_MEMBER>
                                <ALL_MEMBER>[Store].[All Stores]</ALL_MEMBER>
                                <DESCRIPTION>Sales Cube - Store Hierarchy</DESCRIPTION>
                                <STRUCTURE>0</STRUCTURE>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                                <HIERARCHY_ORDINAL>1</HIERARCHY_ORDINAL>
                                <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
                                <HIERARCHY_IS_VISIBLE>true</HIERARCHY_IS_VISIBLE>
                                <HIERARCHY_ORIGIN>1</HIERARCHY_ORIGIN>
                                <HIERARCHY_DISPLAY_FOLDER/>
                                <HIERARCHY_VISIBILITY>1</HIERARCHY_VISIBILITY>
                                <PARENT_CHILD>false</PARENT_CHILD>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Time]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_NAME>Time</HIERARCHY_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Time]</HIERARCHY_UNIQUE_NAME>
                                <HIERARCHY_CAPTION>Time</HIERARCHY_CAPTION>
                                <DIMENSION_TYPE>1</DIMENSION_TYPE>
                                <HIERARCHY_CARDINALITY>34</HIERARCHY_CARDINALITY>
                                <DEFAULT_MEMBER>[Time].[1997]</DEFAULT_MEMBER>
                                <DESCRIPTION>Sales Cube - Time Hierarchy</DESCRIPTION>
                                <STRUCTURE>0</STRUCTURE>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                                <HIERARCHY_ORDINAL>4</HIERARCHY_ORDINAL>
                                <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
                                <HIERARCHY_IS_VISIBLE>true</HIERARCHY_IS_VISIBLE>
                                <HIERARCHY_ORIGIN>1</HIERARCHY_ORIGIN>
                                <HIERARCHY_DISPLAY_FOLDER/>
                                <HIERARCHY_VISIBILITY>1</HIERARCHY_VISIBILITY>
                                <PARENT_CHILD>false</PARENT_CHILD>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Time]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_NAME>Weekly</HIERARCHY_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Time].[Weekly]</HIERARCHY_UNIQUE_NAME>
                                <HIERARCHY_CAPTION>Weekly</HIERARCHY_CAPTION>
                                <DIMENSION_TYPE>1</DIMENSION_TYPE>
                                <HIERARCHY_CARDINALITY>837</HIERARCHY_CARDINALITY>
                                <DEFAULT_MEMBER>[Time].[Weekly].[All Weeklys]</DEFAULT_MEMBER>
                                <ALL_MEMBER>[Time].[Weekly].[All Weeklys]</ALL_MEMBER>
                                <DESCRIPTION>Sales Cube - Time.Weekly Hierarchy</DESCRIPTION>
                                <STRUCTURE>0</STRUCTURE>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                                <HIERARCHY_ORDINAL>5</HIERARCHY_ORDINAL>
                                <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
                                <HIERARCHY_IS_VISIBLE>true</HIERARCHY_IS_VISIBLE>
                                <HIERARCHY_ORIGIN>1</HIERARCHY_ORIGIN>
                                <HIERARCHY_DISPLAY_FOLDER/>
                                <HIERARCHY_VISIBILITY>1</HIERARCHY_VISIBILITY>
                                <PARENT_CHILD>false</PARENT_CHILD>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Yearly Income]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_NAME>Yearly Income</HIERARCHY_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Yearly Income]</HIERARCHY_UNIQUE_NAME>
                                <HIERARCHY_CAPTION>Yearly Income</HIERARCHY_CAPTION>
                                <DIMENSION_TYPE>3</DIMENSION_TYPE>
                                <HIERARCHY_CARDINALITY>9</HIERARCHY_CARDINALITY>
                                <DEFAULT_MEMBER>[Yearly Income].[All Yearly Incomes]</DEFAULT_MEMBER>
                                <ALL_MEMBER>[Yearly Income].[All Yearly Incomes]</ALL_MEMBER>
                                <DESCRIPTION>Sales Cube - Yearly Income Hierarchy</DESCRIPTION>
                                <STRUCTURE>0</STRUCTURE>
                                <IS_VIRTUAL>false</IS_VIRTUAL>
                                <IS_READWRITE>false</IS_READWRITE>
                                <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
                                <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
                                <HIERARCHY_ORDINAL>13</HIERARCHY_ORDINAL>
                                <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
                                <HIERARCHY_IS_VISIBLE>true</HIERARCHY_IS_VISIBLE>
                                <HIERARCHY_ORIGIN>1</HIERARCHY_ORIGIN>
                                <HIERARCHY_DISPLAY_FOLDER/>
                                <HIERARCHY_VISIBILITY>1</HIERARCHY_VISIBILITY>
                                <PARENT_CHILD>false</PARENT_CHILD>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_LEVELS_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element minOccurs="0" name="CATALOG_NAME" sql:field="CATALOG_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="SCHEMA_NAME" sql:field="SCHEMA_NAME" type="xsd:string"/>
                                        <xsd:element name="CUBE_NAME" sql:field="CUBE_NAME" type="xsd:string"/>
                                        <xsd:element name="DIMENSION_UNIQUE_NAME" sql:field="DIMENSION_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element name="HIERARCHY_UNIQUE_NAME" sql:field="HIERARCHY_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element name="LEVEL_NAME" sql:field="LEVEL_NAME" type="xsd:string"/>
                                        <xsd:element name="LEVEL_UNIQUE_NAME" sql:field="LEVEL_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="LEVEL_GUID" sql:field="LEVEL_GUID" type="uuid"/>
                                        <xsd:element name="LEVEL_CAPTION" sql:field="LEVEL_CAPTION" type="xsd:string"/>
                                        <xsd:element name="LEVEL_NUMBER" sql:field="LEVEL_NUMBER" type="xsd:unsignedInt"/>
                                        <xsd:element name="LEVEL_CARDINALITY" sql:field="LEVEL_CARDINALITY" type="xsd:unsignedInt"/>
                                        <xsd:element name="LEVEL_TYPE" sql:field="LEVEL_TYPE" type="xsd:int"/>
                                        <xsd:element name="CUSTOM_ROLLUP_SETTINGS" sql:field="CUSTOM_ROLLUP_SETTINGS" type="xsd:int"/>
                                        <xsd:element name="LEVEL_UNIQUE_SETTINGS" sql:field="LEVEL_UNIQUE_SETTINGS" type="xsd:int"/>
                                        <xsd:element name="LEVEL_IS_VISIBLE" sql:field="LEVEL_IS_VISIBLE" type="xsd:boolean"/>
                                        <xsd:element minOccurs="0" name="DESCRIPTION" sql:field="DESCRIPTION" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="LEVEL_ORIGIN" sql:field="LEVEL_ORIGIN" type="xsd:unsignedShort"/>
                                        <xsd:element minOccurs="0" name="CUBE_SOURCE" sql:field="CUBE_SOURCE" type="xsd:unsignedShort"/>
                                        <xsd:element minOccurs="0" name="LEVEL_VISIBILITY" sql:field="LEVEL_VISIBILITY" type="xsd:unsignedShort"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_NAME>(All)</LEVEL_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[(All)]</LEVEL_UNIQUE_NAME>
                                <LEVEL_CAPTION>(All)</LEVEL_CAPTION>
                                <LEVEL_NUMBER>0</LEVEL_NUMBER>
                                <LEVEL_CARDINALITY>1</LEVEL_CARDINALITY>
                                <LEVEL_TYPE>1</LEVEL_TYPE>
                                <CUSTOM_ROLLUP_SETTINGS>0</CUSTOM_ROLLUP_SETTINGS>
                                <LEVEL_UNIQUE_SETTINGS>3</LEVEL_UNIQUE_SETTINGS>
                                <LEVEL_IS_VISIBLE>true</LEVEL_IS_VISIBLE>
                                <DESCRIPTION>Sales Cube - Customers Hierarchy - (All) Level</DESCRIPTION>
                                <LEVEL_ORIGIN>0</LEVEL_ORIGIN>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_NAME>Country</LEVEL_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[Country]</LEVEL_UNIQUE_NAME>
                                <LEVEL_CAPTION>Country</LEVEL_CAPTION>
                                <LEVEL_NUMBER>1</LEVEL_NUMBER>
                                <LEVEL_CARDINALITY>3</LEVEL_CARDINALITY>
                                <LEVEL_TYPE>0</LEVEL_TYPE>
                                <CUSTOM_ROLLUP_SETTINGS>0</CUSTOM_ROLLUP_SETTINGS>
                                <LEVEL_UNIQUE_SETTINGS>1</LEVEL_UNIQUE_SETTINGS>
                                <LEVEL_IS_VISIBLE>true</LEVEL_IS_VISIBLE>
                                <DESCRIPTION>Sales Cube - Customers Hierarchy - Country Level</DESCRIPTION>
                                <LEVEL_ORIGIN>0</LEVEL_ORIGIN>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_NAME>State Province</LEVEL_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[State Province]</LEVEL_UNIQUE_NAME>
                                <LEVEL_CAPTION>State Province</LEVEL_CAPTION>
                                <LEVEL_NUMBER>2</LEVEL_NUMBER>
                                <LEVEL_CARDINALITY>13</LEVEL_CARDINALITY>
                                <LEVEL_TYPE>0</LEVEL_TYPE>
                                <CUSTOM_ROLLUP_SETTINGS>0</CUSTOM_ROLLUP_SETTINGS>
                                <LEVEL_UNIQUE_SETTINGS>1</LEVEL_UNIQUE_SETTINGS>
                                <LEVEL_IS_VISIBLE>true</LEVEL_IS_VISIBLE>
                                <DESCRIPTION>Sales Cube - Customers Hierarchy - State Province Level</DESCRIPTION>
                                <LEVEL_ORIGIN>0</LEVEL_ORIGIN>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_NAME>City</LEVEL_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[City]</LEVEL_UNIQUE_NAME>
                                <LEVEL_CAPTION>City</LEVEL_CAPTION>
                                <LEVEL_NUMBER>3</LEVEL_NUMBER>
                                <LEVEL_CARDINALITY>109</LEVEL_CARDINALITY>
                                <LEVEL_TYPE>0</LEVEL_TYPE>
                                <CUSTOM_ROLLUP_SETTINGS>0</CUSTOM_ROLLUP_SETTINGS>
                                <LEVEL_UNIQUE_SETTINGS>0</LEVEL_UNIQUE_SETTINGS>
                                <LEVEL_IS_VISIBLE>true</LEVEL_IS_VISIBLE>
                                <DESCRIPTION>Sales Cube - Customers Hierarchy - City Level</DESCRIPTION>
                                <LEVEL_ORIGIN>0</LEVEL_ORIGIN>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_NAME>Name</LEVEL_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[Name]</LEVEL_UNIQUE_NAME>
                                <LEVEL_CAPTION>Name</LEVEL_CAPTION>
                                <LEVEL_NUMBER>4</LEVEL_NUMBER>
                                <LEVEL_CARDINALITY>10281</LEVEL_CARDINALITY>
                                <LEVEL_TYPE>0</LEVEL_TYPE>
                                <CUSTOM_ROLLUP_SETTINGS>0</CUSTOM_ROLLUP_SETTINGS>
                                <LEVEL_UNIQUE_SETTINGS>1</LEVEL_UNIQUE_SETTINGS>
                                <LEVEL_IS_VISIBLE>true</LEVEL_IS_VISIBLE>
                                <DESCRIPTION>Sales Cube - Customers Hierarchy - Name Level</DESCRIPTION>
                                <LEVEL_ORIGIN>0</LEVEL_ORIGIN>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_MEASUREGROUP_DIMENSIONS_RESPONSE = """
        """;
    public static final String MDSCHEMA_MEASURES_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element minOccurs="0" name="CATALOG_NAME" sql:field="CATALOG_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="SCHEMA_NAME" sql:field="SCHEMA_NAME" type="xsd:string"/>
                                        <xsd:element name="CUBE_NAME" sql:field="CUBE_NAME" type="xsd:string"/>
                                        <xsd:element name="MEASURE_NAME" sql:field="MEASURE_NAME" type="xsd:string"/>
                                        <xsd:element name="MEASURE_UNIQUE_NAME" sql:field="MEASURE_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element name="MEASURE_CAPTION" sql:field="MEASURE_CAPTION" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="MEASURE_GUID" sql:field="MEASURE_GUID" type="uuid"/>
                                        <xsd:element name="MEASURE_AGGREGATOR" sql:field="MEASURE_AGGREGATOR" type="xsd:int"/>
                                        <xsd:element name="DATA_TYPE" sql:field="DATA_TYPE" type="xsd:unsignedShort"/>
                                        <xsd:element name="MEASURE_IS_VISIBLE" sql:field="MEASURE_IS_VISIBLE" type="xsd:boolean"/>
                                        <xsd:element minOccurs="0" name="LEVELS_LIST" sql:field="LEVELS_LIST" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="DESCRIPTION" sql:field="DESCRIPTION" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="MEASUREGROUP_NAME" sql:field="MEASUREGROUP_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="MEASURE_DISPLAY_FOLDER" sql:field="MEASURE_DISPLAY_FOLDER" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="DEFAULT_FORMAT_STRING" sql:field="DEFAULT_FORMAT_STRING" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="CUBE_SOURCE" sql:field="CUBE_SOURCE" type="xsd:unsignedShort"/>
                                        <xsd:element minOccurs="0" name="MEASURE_VISIBILITY" sql:field="MEASURE_VISIBILITY" type="xsd:unsignedShort"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <MEASURE_NAME>Customer Count</MEASURE_NAME>
                                <MEASURE_UNIQUE_NAME>[Measures].[Customer Count]</MEASURE_UNIQUE_NAME>
                                <MEASURE_CAPTION>Customer Count</MEASURE_CAPTION>
                                <MEASURE_AGGREGATOR>0</MEASURE_AGGREGATOR>
                                <DATA_TYPE>3</DATA_TYPE>
                                <MEASURE_IS_VISIBLE>true</MEASURE_IS_VISIBLE>
                                <LEVELS_LIST>[Store].[Store Name],[Store Size in SQFT].[Store Sqft],[Store Type].[Store Type],[Time].[Month],[Time].[Weekly].[Day],[Product].[Product Name],[Promotion Media].[Media Type],[Promotions].[Promotion Name],[Customers].[Name],[Education Level].[Education Level],[Gender].[Gender],[Marital Status].[Marital Status],[Yearly Income].[Yearly Income]</LEVELS_LIST>
                                <DESCRIPTION>Sales Cube - Customer Count Member</DESCRIPTION>
                                <MEASUREGROUP_NAME>Sales</MEASUREGROUP_NAME>
                                <MEASURE_DISPLAY_FOLDER/>
                                <DEFAULT_FORMAT_STRING>#,###</DEFAULT_FORMAT_STRING>
                                <MEASURE_VISIBILITY>1</MEASURE_VISIBILITY>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <MEASURE_NAME>Profit</MEASURE_NAME>
                                <MEASURE_UNIQUE_NAME>[Measures].[Profit]</MEASURE_UNIQUE_NAME>
                                <MEASURE_CAPTION>Profit</MEASURE_CAPTION>
                                <MEASURE_AGGREGATOR>127</MEASURE_AGGREGATOR>
                                <DATA_TYPE>130</DATA_TYPE>
                                <MEASURE_IS_VISIBLE>true</MEASURE_IS_VISIBLE>
                                <DESCRIPTION>Sales Cube - Profit Member</DESCRIPTION>
                                <MEASUREGROUP_NAME>Sales</MEASUREGROUP_NAME>
                                <MEASURE_DISPLAY_FOLDER/>
                                <DEFAULT_FORMAT_STRING>$#,##0.00</DEFAULT_FORMAT_STRING>
                                <MEASURE_VISIBILITY>1</MEASURE_VISIBILITY>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <MEASURE_NAME>Profit Growth</MEASURE_NAME>
                                <MEASURE_UNIQUE_NAME>[Measures].[Profit Growth]</MEASURE_UNIQUE_NAME>
                                <MEASURE_CAPTION>Gewinn-Wachstum</MEASURE_CAPTION>
                                <MEASURE_AGGREGATOR>127</MEASURE_AGGREGATOR>
                                <DATA_TYPE>130</DATA_TYPE>
                                <MEASURE_IS_VISIBLE>true</MEASURE_IS_VISIBLE>
                                <DESCRIPTION>Sales Cube - Profit Growth Member</DESCRIPTION>
                                <MEASUREGROUP_NAME>Sales</MEASUREGROUP_NAME>
                                <MEASURE_DISPLAY_FOLDER/>
                                <DEFAULT_FORMAT_STRING>0.0%</DEFAULT_FORMAT_STRING>
                                <MEASURE_VISIBILITY>1</MEASURE_VISIBILITY>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <MEASURE_NAME>Profit last Period</MEASURE_NAME>
                                <MEASURE_UNIQUE_NAME>[Measures].[Profit last Period]</MEASURE_UNIQUE_NAME>
                                <MEASURE_CAPTION>Profit last Period</MEASURE_CAPTION>
                                <MEASURE_AGGREGATOR>127</MEASURE_AGGREGATOR>
                                <DATA_TYPE>130</DATA_TYPE>
                                <MEASURE_IS_VISIBLE>false</MEASURE_IS_VISIBLE>
                                <DESCRIPTION>Sales Cube - Profit last Period Member</DESCRIPTION>
                                <MEASUREGROUP_NAME>Sales</MEASUREGROUP_NAME>
                                <MEASURE_DISPLAY_FOLDER/>
                                <DEFAULT_FORMAT_STRING>$#,##0.00</DEFAULT_FORMAT_STRING>
                                <MEASURE_VISIBILITY>2</MEASURE_VISIBILITY>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <MEASURE_NAME>Promotion Sales</MEASURE_NAME>
                                <MEASURE_UNIQUE_NAME>[Measures].[Promotion Sales]</MEASURE_UNIQUE_NAME>
                                <MEASURE_CAPTION>Promotion Sales</MEASURE_CAPTION>
                                <MEASURE_AGGREGATOR>1</MEASURE_AGGREGATOR>
                                <DATA_TYPE>5</DATA_TYPE>
                                <MEASURE_IS_VISIBLE>true</MEASURE_IS_VISIBLE>
                                <LEVELS_LIST>[Store].[Store Name],[Store Size in SQFT].[Store Sqft],[Store Type].[Store Type],[Time].[Month],[Time].[Weekly].[Day],[Product].[Product Name],[Promotion Media].[Media Type],[Promotions].[Promotion Name],[Customers].[Name],[Education Level].[Education Level],[Gender].[Gender],[Marital Status].[Marital Status],[Yearly Income].[Yearly Income]</LEVELS_LIST>
                                <DESCRIPTION>Sales Cube - Promotion Sales Member</DESCRIPTION>
                                <MEASUREGROUP_NAME>Sales</MEASUREGROUP_NAME>
                                <MEASURE_DISPLAY_FOLDER/>
                                <DEFAULT_FORMAT_STRING>#,###.00</DEFAULT_FORMAT_STRING>
                                <MEASURE_VISIBILITY>1</MEASURE_VISIBILITY>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <MEASURE_NAME>Sales Count</MEASURE_NAME>
                                <MEASURE_UNIQUE_NAME>[Measures].[Sales Count]</MEASURE_UNIQUE_NAME>
                                <MEASURE_CAPTION>Sales Count</MEASURE_CAPTION>
                                <MEASURE_AGGREGATOR>2</MEASURE_AGGREGATOR>
                                <DATA_TYPE>3</DATA_TYPE>
                                <MEASURE_IS_VISIBLE>true</MEASURE_IS_VISIBLE>
                                <LEVELS_LIST>[Store].[Store Name],[Store Size in SQFT].[Store Sqft],[Store Type].[Store Type],[Time].[Month],[Time].[Weekly].[Day],[Product].[Product Name],[Promotion Media].[Media Type],[Promotions].[Promotion Name],[Customers].[Name],[Education Level].[Education Level],[Gender].[Gender],[Marital Status].[Marital Status],[Yearly Income].[Yearly Income]</LEVELS_LIST>
                                <DESCRIPTION>Sales Cube - Sales Count Member</DESCRIPTION>
                                <MEASUREGROUP_NAME>Sales</MEASUREGROUP_NAME>
                                <MEASURE_DISPLAY_FOLDER/>
                                <DEFAULT_FORMAT_STRING>#,###</DEFAULT_FORMAT_STRING>
                                <MEASURE_VISIBILITY>1</MEASURE_VISIBILITY>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <MEASURE_NAME>Store Cost</MEASURE_NAME>
                                <MEASURE_UNIQUE_NAME>[Measures].[Store Cost]</MEASURE_UNIQUE_NAME>
                                <MEASURE_CAPTION>Store Cost</MEASURE_CAPTION>
                                <MEASURE_AGGREGATOR>1</MEASURE_AGGREGATOR>
                                <DATA_TYPE>5</DATA_TYPE>
                                <MEASURE_IS_VISIBLE>true</MEASURE_IS_VISIBLE>
                                <LEVELS_LIST>[Store].[Store Name],[Store Size in SQFT].[Store Sqft],[Store Type].[Store Type],[Time].[Month],[Time].[Weekly].[Day],[Product].[Product Name],[Promotion Media].[Media Type],[Promotions].[Promotion Name],[Customers].[Name],[Education Level].[Education Level],[Gender].[Gender],[Marital Status].[Marital Status],[Yearly Income].[Yearly Income]</LEVELS_LIST>
                                <DESCRIPTION>Sales Cube - Store Cost Member</DESCRIPTION>
                                <MEASUREGROUP_NAME>Sales</MEASUREGROUP_NAME>
                                <MEASURE_DISPLAY_FOLDER/>
                                <DEFAULT_FORMAT_STRING>#,###.00</DEFAULT_FORMAT_STRING>
                                <MEASURE_VISIBILITY>1</MEASURE_VISIBILITY>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <MEASURE_NAME>Store Sales</MEASURE_NAME>
                                <MEASURE_UNIQUE_NAME>[Measures].[Store Sales]</MEASURE_UNIQUE_NAME>
                                <MEASURE_CAPTION>Store Sales</MEASURE_CAPTION>
                                <MEASURE_AGGREGATOR>1</MEASURE_AGGREGATOR>
                                <DATA_TYPE>5</DATA_TYPE>
                                <MEASURE_IS_VISIBLE>true</MEASURE_IS_VISIBLE>
                                <LEVELS_LIST>[Store].[Store Name],[Store Size in SQFT].[Store Sqft],[Store Type].[Store Type],[Time].[Month],[Time].[Weekly].[Day],[Product].[Product Name],[Promotion Media].[Media Type],[Promotions].[Promotion Name],[Customers].[Name],[Education Level].[Education Level],[Gender].[Gender],[Marital Status].[Marital Status],[Yearly Income].[Yearly Income]</LEVELS_LIST>
                                <DESCRIPTION>Sales Cube - Store Sales Member</DESCRIPTION>
                                <MEASUREGROUP_NAME>Sales</MEASUREGROUP_NAME>
                                <MEASURE_DISPLAY_FOLDER/>
                                <DEFAULT_FORMAT_STRING>#,###.00</DEFAULT_FORMAT_STRING>
                                <MEASURE_VISIBILITY>1</MEASURE_VISIBILITY>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <MEASURE_NAME>Unit Sales</MEASURE_NAME>
                                <MEASURE_UNIQUE_NAME>[Measures].[Unit Sales]</MEASURE_UNIQUE_NAME>
                                <MEASURE_CAPTION>Unit Sales</MEASURE_CAPTION>
                                <MEASURE_AGGREGATOR>1</MEASURE_AGGREGATOR>
                                <DATA_TYPE>5</DATA_TYPE>
                                <MEASURE_IS_VISIBLE>true</MEASURE_IS_VISIBLE>
                                <LEVELS_LIST>[Store].[Store Name],[Store Size in SQFT].[Store Sqft],[Store Type].[Store Type],[Time].[Month],[Time].[Weekly].[Day],[Product].[Product Name],[Promotion Media].[Media Type],[Promotions].[Promotion Name],[Customers].[Name],[Education Level].[Education Level],[Gender].[Gender],[Marital Status].[Marital Status],[Yearly Income].[Yearly Income]</LEVELS_LIST>
                                <DESCRIPTION>Sales Cube - Unit Sales Member</DESCRIPTION>
                                <MEASUREGROUP_NAME>Sales</MEASUREGROUP_NAME>
                                <MEASURE_DISPLAY_FOLDER/>
                                <DEFAULT_FORMAT_STRING>Standard</DEFAULT_FORMAT_STRING>
                                <MEASURE_VISIBILITY>1</MEASURE_VISIBILITY>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_MEMBERS_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element minOccurs="0" name="CATALOG_NAME" sql:field="CATALOG_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="SCHEMA_NAME" sql:field="SCHEMA_NAME" type="xsd:string"/>
                                        <xsd:element name="CUBE_NAME" sql:field="CUBE_NAME" type="xsd:string"/>
                                        <xsd:element name="DIMENSION_UNIQUE_NAME" sql:field="DIMENSION_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element name="HIERARCHY_UNIQUE_NAME" sql:field="HIERARCHY_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element name="LEVEL_UNIQUE_NAME" sql:field="LEVEL_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element name="LEVEL_NUMBER" sql:field="LEVEL_NUMBER" type="xsd:unsignedInt"/>
                                        <xsd:element name="MEMBER_ORDINAL" sql:field="MEMBER_ORDINAL" type="xsd:unsignedInt"/>
                                        <xsd:element name="MEMBER_NAME" sql:field="MEMBER_NAME" type="xsd:string"/>
                                        <xsd:element name="MEMBER_UNIQUE_NAME" sql:field="MEMBER_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element name="MEMBER_TYPE" sql:field="MEMBER_TYPE" type="xsd:int"/>
                                        <xsd:element minOccurs="0" name="MEMBER_GUID" sql:field="MEMBER_GUID" type="uuid"/>
                                        <xsd:element name="MEMBER_CAPTION" sql:field="MEMBER_CAPTION" type="xsd:string"/>
                                        <xsd:element name="CHILDREN_CARDINALITY" sql:field="CHILDREN_CARDINALITY" type="xsd:unsignedInt"/>
                                        <xsd:element name="PARENT_LEVEL" sql:field="PARENT_LEVEL" type="xsd:unsignedInt"/>
                                        <xsd:element minOccurs="0" name="PARENT_UNIQUE_NAME" sql:field="PARENT_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element name="PARENT_COUNT" sql:field="PARENT_COUNT" type="xsd:unsignedInt"/>
                                        <xsd:element minOccurs="0" name="TREE_OP" sql:field="TREE_OP" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="DEPTH" sql:field="DEPTH" type="xsd:int"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Gender]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Gender]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Gender].[(All)]</LEVEL_UNIQUE_NAME>
                                <LEVEL_NUMBER>0</LEVEL_NUMBER>
                                <MEMBER_ORDINAL>0</MEMBER_ORDINAL>
                                <MEMBER_NAME>All Gender</MEMBER_NAME>
                                <MEMBER_UNIQUE_NAME>[Gender].[All Gender]</MEMBER_UNIQUE_NAME>
                                <MEMBER_TYPE>2</MEMBER_TYPE>
                                <MEMBER_CAPTION>All Gender</MEMBER_CAPTION>
                                <CHILDREN_CARDINALITY>2</CHILDREN_CARDINALITY>
                                <PARENT_LEVEL>0</PARENT_LEVEL>
                                <PARENT_COUNT>0</PARENT_COUNT>
                                <DEPTH>0</DEPTH>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Gender]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Gender]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Gender].[Gender]</LEVEL_UNIQUE_NAME>
                                <LEVEL_NUMBER>1</LEVEL_NUMBER>
                                <MEMBER_ORDINAL>0</MEMBER_ORDINAL>
                                <MEMBER_NAME>F</MEMBER_NAME>
                                <MEMBER_UNIQUE_NAME>[Gender].[F]</MEMBER_UNIQUE_NAME>
                                <MEMBER_TYPE>1</MEMBER_TYPE>
                                <MEMBER_CAPTION>F</MEMBER_CAPTION>
                                <CHILDREN_CARDINALITY>0</CHILDREN_CARDINALITY>
                                <PARENT_LEVEL>0</PARENT_LEVEL>
                                <PARENT_UNIQUE_NAME>[Gender].[All Gender]</PARENT_UNIQUE_NAME>
                                <PARENT_COUNT>1</PARENT_COUNT>
                                <DEPTH>1</DEPTH>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Gender]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Gender]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Gender].[Gender]</LEVEL_UNIQUE_NAME>
                                <LEVEL_NUMBER>1</LEVEL_NUMBER>
                                <MEMBER_ORDINAL>0</MEMBER_ORDINAL>
                                <MEMBER_NAME>M</MEMBER_NAME>
                                <MEMBER_UNIQUE_NAME>[Gender].[M]</MEMBER_UNIQUE_NAME>
                                <MEMBER_TYPE>1</MEMBER_TYPE>
                                <MEMBER_CAPTION>M</MEMBER_CAPTION>
                                <CHILDREN_CARDINALITY>0</CHILDREN_CARDINALITY>
                                <PARENT_LEVEL>0</PARENT_LEVEL>
                                <PARENT_UNIQUE_NAME>[Gender].[All Gender]</PARENT_UNIQUE_NAME>
                                <PARENT_COUNT>1</PARENT_COUNT>
                                <DEPTH>1</DEPTH>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_PROPERTIES_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element minOccurs="0" name="CATALOG_NAME" sql:field="CATALOG_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="SCHEMA_NAME" sql:field="SCHEMA_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="CUBE_NAME" sql:field="CUBE_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="DIMENSION_UNIQUE_NAME" sql:field="DIMENSION_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="HIERARCHY_UNIQUE_NAME" sql:field="HIERARCHY_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="LEVEL_UNIQUE_NAME" sql:field="LEVEL_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="MEMBER_UNIQUE_NAME" sql:field="MEMBER_UNIQUE_NAME" type="xsd:string"/>
                                        <xsd:element name="PROPERTY_TYPE" sql:field="PROPERTY_TYPE" type="xsd:short"/>
                                        <xsd:element name="PROPERTY_NAME" sql:field="PROPERTY_NAME" type="xsd:string"/>
                                        <xsd:element name="PROPERTY_CAPTION" sql:field="PROPERTY_CAPTION" type="xsd:string"/>
                                        <xsd:element name="DATA_TYPE" sql:field="DATA_TYPE" type="xsd:unsignedShort"/>
                                        <xsd:element minOccurs="0" name="PROPERTY_CONTENT_TYPE" sql:field="PROPERTY_CONTENT_TYPE" type="xsd:short"/>
                                        <xsd:element minOccurs="0" name="DESCRIPTION" sql:field="DESCRIPTION" type="xsd:string"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Type</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Type</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>HR Cube - Store Hierarchy - Store Name Level - Store Type Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Manager</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Manager</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>HR Cube - Store Hierarchy - Store Name Level - Store Manager Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>HR Cube - Store Hierarchy - Store Name Level - Store Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Grocery Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Grocery Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>HR Cube - Store Hierarchy - Store Name Level - Grocery Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Frozen Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Frozen Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>HR Cube - Store Hierarchy - Store Name Level - Frozen Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Meat Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Meat Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>HR Cube - Store Hierarchy - Store Name Level - Meat Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Has coffee bar</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Has coffee bar</PROPERTY_CAPTION>
                                <DATA_TYPE>11</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>HR Cube - Store Hierarchy - Store Name Level - Has coffee bar Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Street address</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Street address</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>HR Cube - Store Hierarchy - Store Name Level - Street address Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Employees]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Employees]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Employees].[Employee Id]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Marital Status</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Marital Status</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>HR Cube - Employees Hierarchy - Employee Id Level - Marital Status Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Employees]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Employees]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Employees].[Employee Id]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Position Title</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Position Title</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>HR Cube - Employees Hierarchy - Employee Id Level - Position Title Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Employees]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Employees]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Employees].[Employee Id]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Gender</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Gender</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>HR Cube - Employees Hierarchy - Employee Id Level - Gender Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Employees]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Employees]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Employees].[Employee Id]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Salary</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Salary</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>HR Cube - Employees Hierarchy - Employee Id Level - Salary Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Employees]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Employees]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Employees].[Employee Id]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Education Level</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Education Level</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>HR Cube - Employees Hierarchy - Employee Id Level - Education Level Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>HR</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Employees]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Employees]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Employees].[Employee Id]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Management Role</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Management Role</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>HR Cube - Employees Hierarchy - Employee Id Level - Management Role Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Type</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Type</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Cube - Store Hierarchy - Store Name Level - Store Type Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Manager</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Manager</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Cube - Store Hierarchy - Store Name Level - Store Manager Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Cube - Store Hierarchy - Store Name Level - Store Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Grocery Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Grocery Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Cube - Store Hierarchy - Store Name Level - Grocery Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Frozen Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Frozen Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Cube - Store Hierarchy - Store Name Level - Frozen Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Meat Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Meat Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Cube - Store Hierarchy - Store Name Level - Meat Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Has coffee bar</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Has coffee bar</PROPERTY_CAPTION>
                                <DATA_TYPE>11</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Cube - Store Hierarchy - Store Name Level - Has coffee bar Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Street address</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Street address</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Cube - Store Hierarchy - Store Name Level - Street address Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Gender</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Gender</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Cube - Customers Hierarchy - Name Level - Gender Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Marital Status</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Marital Status</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Cube - Customers Hierarchy - Name Level - Marital Status Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Education</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Education</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Cube - Customers Hierarchy - Name Level - Education Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Yearly Income</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Yearly Income</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Cube - Customers Hierarchy - Name Level - Yearly Income Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Type</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Type</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Ragged Cube - Store Hierarchy - Store Name Level - Store Type Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Manager</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Manager</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Ragged Cube - Store Hierarchy - Store Name Level - Store Manager Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Ragged Cube - Store Hierarchy - Store Name Level - Store Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Grocery Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Grocery Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Ragged Cube - Store Hierarchy - Store Name Level - Grocery Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Frozen Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Frozen Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Ragged Cube - Store Hierarchy - Store Name Level - Frozen Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Meat Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Meat Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Ragged Cube - Store Hierarchy - Store Name Level - Meat Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Has coffee bar</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Has coffee bar</PROPERTY_CAPTION>
                                <DATA_TYPE>11</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Ragged Cube - Store Hierarchy - Store Name Level - Has coffee bar Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Street address</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Street address</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Ragged Cube - Store Hierarchy - Store Name Level - Street address Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Gender</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Gender</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Ragged Cube - Customers Hierarchy - Name Level - Gender Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Marital Status</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Marital Status</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Ragged Cube - Customers Hierarchy - Name Level - Marital Status Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Education</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Education</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Ragged Cube - Customers Hierarchy - Name Level - Education Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Sales Ragged</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Yearly Income</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Yearly Income</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Sales Ragged Cube - Customers Hierarchy - Name Level - Yearly Income Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Store</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Type</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Type</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Store Cube - Store Hierarchy - Store Name Level - Store Type Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Store</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Manager</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Manager</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Store Cube - Store Hierarchy - Store Name Level - Store Manager Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Store</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Store Cube - Store Hierarchy - Store Name Level - Store Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Store</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Grocery Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Grocery Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Store Cube - Store Hierarchy - Store Name Level - Grocery Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Store</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Frozen Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Frozen Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Store Cube - Store Hierarchy - Store Name Level - Frozen Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Store</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Meat Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Meat Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Store Cube - Store Hierarchy - Store Name Level - Meat Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Store</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Has coffee bar</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Has coffee bar</PROPERTY_CAPTION>
                                <DATA_TYPE>11</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Store Cube - Store Hierarchy - Store Name Level - Has coffee bar Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Store</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Street address</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Street address</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Store Cube - Store Hierarchy - Store Name Level - Street address Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Type</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Type</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse Cube - Store Hierarchy - Store Name Level - Store Type Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Manager</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Manager</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse Cube - Store Hierarchy - Store Name Level - Store Manager Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse Cube - Store Hierarchy - Store Name Level - Store Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Grocery Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Grocery Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse Cube - Store Hierarchy - Store Name Level - Grocery Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Frozen Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Frozen Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse Cube - Store Hierarchy - Store Name Level - Frozen Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Meat Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Meat Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse Cube - Store Hierarchy - Store Name Level - Meat Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Has coffee bar</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Has coffee bar</PROPERTY_CAPTION>
                                <DATA_TYPE>11</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse Cube - Store Hierarchy - Store Name Level - Has coffee bar Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Street address</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Street address</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse Cube - Store Hierarchy - Store Name Level - Street address Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Gender</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Gender</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse and Sales Cube - Customers Hierarchy - Name Level - Gender Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Marital Status</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Marital Status</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse and Sales Cube - Customers Hierarchy - Name Level - Marital Status Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Education</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Education</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse and Sales Cube - Customers Hierarchy - Name Level - Education Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Customers]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Customers].[Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Yearly Income</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Yearly Income</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse and Sales Cube - Customers Hierarchy - Name Level - Yearly Income Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Type</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Type</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse and Sales Cube - Store Hierarchy - Store Name Level - Store Type Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Manager</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Manager</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse and Sales Cube - Store Hierarchy - Store Name Level - Store Manager Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Store Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Store Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse and Sales Cube - Store Hierarchy - Store Name Level - Store Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Grocery Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Grocery Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse and Sales Cube - Store Hierarchy - Store Name Level - Grocery Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Frozen Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Frozen Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse and Sales Cube - Store Hierarchy - Store Name Level - Frozen Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Meat Sqft</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Meat Sqft</PROPERTY_CAPTION>
                                <DATA_TYPE>5</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse and Sales Cube - Store Hierarchy - Store Name Level - Meat Sqft Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Has coffee bar</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Has coffee bar</PROPERTY_CAPTION>
                                <DATA_TYPE>11</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse and Sales Cube - Store Hierarchy - Store Name Level - Has coffee bar Property</DESCRIPTION>
                            </row>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse and Sales</CUBE_NAME>
                                <DIMENSION_UNIQUE_NAME>[Store]</DIMENSION_UNIQUE_NAME>
                                <HIERARCHY_UNIQUE_NAME>[Store]</HIERARCHY_UNIQUE_NAME>
                                <LEVEL_UNIQUE_NAME>[Store].[Store Name]</LEVEL_UNIQUE_NAME>
                                <PROPERTY_TYPE>1</PROPERTY_TYPE>
                                <PROPERTY_NAME>Street address</PROPERTY_NAME>
                                <PROPERTY_CAPTION>Street address</PROPERTY_CAPTION>
                                <DATA_TYPE>130</DATA_TYPE>
                                <PROPERTY_CONTENT_TYPE>0</PROPERTY_CONTENT_TYPE>
                                <DESCRIPTION>Warehouse and Sales Cube - Store Hierarchy - Store Name Level - Street address Property</DESCRIPTION>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_SETS_RESPONSE = """
        <?xml version="1.0"?>
        <SOAP-ENV:Envelope SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <DiscoverResponse xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <return>
                        <root xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:EX="urn:schemas-microsoft-com:xml-analysis:exception" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                            <xsd:schema elementFormDefault="qualified" targetNamespace="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns="urn:schemas-microsoft-com:xml-analysis:rowset" xmlns:sql="urn:schemas-microsoft-com:xml-sql" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                                <xsd:element name="root">
                                    <xsd:complexType>
                                        <xsd:sequence>
                                            <xsd:element maxOccurs="unbounded" minOccurs="0" name="row" type="row"/>
                                        </xsd:sequence>
                                    </xsd:complexType>
                                </xsd:element>
                                <xsd:simpleType name="uuid">
                                    <xsd:restriction base="xsd:string">
                                        <xsd:pattern value="[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"/>
                                    </xsd:restriction>
                                </xsd:simpleType>
                                <xsd:complexType name="row">
                                    <xsd:sequence>
                                        <xsd:element minOccurs="0" name="CATALOG_NAME" sql:field="CATALOG_NAME" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="SCHEMA_NAME" sql:field="SCHEMA_NAME" type="xsd:string"/>
                                        <xsd:element name="CUBE_NAME" sql:field="CUBE_NAME" type="xsd:string"/>
                                        <xsd:element name="SET_NAME" sql:field="SET_NAME" type="xsd:string"/>
                                        <xsd:element name="SCOPE" sql:field="SCOPE" type="xsd:int"/>
                                        <xsd:element minOccurs="0" name="DESCRIPTION" sql:field="DESCRIPTION" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="EXPRESSION" sql:field="EXPRESSION" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="DIMENSIONS" sql:field="DIMENSIONS" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="SET_CAPTION" sql:field="SET_CAPTION" type="xsd:string"/>
                                        <xsd:element minOccurs="0" name="SET_DISPLAY_FOLDER" sql:field="SET_DISPLAY_FOLDER" type="xsd:string"/>
                                    </xsd:sequence>
                                </xsd:complexType>
                            </xsd:schema>
                            <row>
                                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                                <SCHEMA_NAME>FoodMart</SCHEMA_NAME>
                                <CUBE_NAME>Warehouse</CUBE_NAME>
                                <SET_NAME>Top Sellers</SET_NAME>
                                <SCOPE>1</SCOPE>
                                <EXPRESSION>TopCount([Warehouse].[Warehouse Name].Members, 5, [Measures].[Warehouse Sales])</EXPRESSION>
                                <DIMENSIONS>[Warehouse]</DIMENSIONS>
                                <SET_CAPTION>Top Sellers</SET_CAPTION>
                            </row>
                        </root>
                    </return>
                </DiscoverResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_KPIS_RESPONSE = """
        """;
    public static final String MDSCHEMA_MEASUREGROUPS_RESPONSE = """
        """;


}
