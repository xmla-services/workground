package org.eclipse.daanse.xmla.ws.jakarta.basic;

public class DiscoverRequestConstants {

    public static final String DISCOVER_DATASOURCES_REQUEST = """
        <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
          <RequestType>DISCOVER_DATASOURCES</RequestType>
          <Restrictions>
            <RestrictionList/>
          </Restrictions>
          <Properties>
            <PropertyList>
              <Format>Tabular</Format>
            </PropertyList>
          </Properties>
        </Discover>
        """;

    public static final String DISCOVER_SCHEMA_ROWSETS_REQUEST = """
        <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
          <Header>
            <BeginSession
                mustUnderstand="1"
                xmlns="urn:schemas-microsoft-com:xml-analysis"
            />
            <NamespaceCompatibility
                xmlns="http://schemas.microsoft.com/analysisservices/2003/xmla"
                mustUnderstand="0"
            />
          </Header>
          <Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
              <RequestType>DISCOVER_SCHEMA_ROWSETS</RequestType>
              <Restrictions />
              <Properties>
                <PropertyList>
                  <LocaleIdentifier>1033</LocaleIdentifier>
                  <DataSourceInfo>FoodMart</DataSourceInfo>
                  <Catalog>FoodMart</Catalog>
                  <Format>Tabular</Format>
                </PropertyList>
              </Properties>
            </Discover>
          </Body>
        </Envelope>
            """;

    public static final String DISCOVER_ENUMERATORS_REQUEST = """
            <SOAP-ENV:Envelope
            xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
            SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <SOAP-ENV:Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"
            SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
            <RequestType>DISCOVER_ENUMERATORS</RequestType>
            <Restrictions>
              <RestrictionList>
              </RestrictionList>
            </Restrictions>
            <Properties>
              <PropertyList>
            <DataSourceInfo>FoodMart</DataSourceInfo>
            <Content>SchemaData</Content>
              </PropertyList>
            </Properties>
            </Discover>
        </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String DISCOVER_PROPERTIES_REQUEST = """
            <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
        <ns1:Discover
            soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"
            xmlns:ns1="urn:schemas-microsoft-com:xml-analysis">
            <ns1:RequestType xsi:type="xsd:string">DISCOVER_PROPERTIES</ns1:RequestType>
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

    public static final String DISCOVER_KEYWORDS_REQUEST = """
        <SOAP-ENV:Envelope
            xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
            SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <SOAP-ENV:Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"
                SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
            <RequestType>DISCOVER_KEYWORDS</RequestType>
            <Restrictions>
              <RestrictionList>
              </RestrictionList>
            </Restrictions>
            <Properties>
              <PropertyList>
                <DataSourceInfo>FoodMart</DataSourceInfo>
                <Content>SchemaData</Content>
              </PropertyList>
            </Properties>
            </Discover>
        </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;

    public static final String DISCOVER_LITERALS_REQUEST = """
        <SOAP-ENV:Envelope
            xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
            SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <SOAP-ENV:Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"
                SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
            <RequestType>DISCOVER_LITERALS</RequestType>
            <Restrictions>
              <RestrictionList>
              </RestrictionList>
            </Restrictions>
            <Properties>
              <PropertyList>
                <DataSourceInfo>FoodMart</DataSourceInfo>
                <Content>SchemaData</Content>
              </PropertyList>
            </Properties>
            </Discover>
        </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
                """;
    public static final String DISCOVER_XML_METADATA_REQUEST = """
        """;

    public static final String DBSCHEMA_CATALOGS_REQUEST = """
        <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
        	<soap:Header></soap:Header>
        		<soap:Body>
        			<Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
                		<RequestType>DBSCHEMA_CATALOGS</RequestType>
                			<Restrictions>
                     			<RestrictionList/>
                 			</Restrictions>
                 			<Properties>
                     			<PropertyList/>
                 			</Properties>
        			</Discover>
        	</soap:Body>
        </soap:Envelope>
        """;

    public static final String DBSCHEMA_COLUMNS_REQUEST = """
        """;
    public static final String DBSCHEMA_PROVIDER_TYPES_REQUEST = """
        """;

    public static final String DBSCHEMA_SCHEMATA_REQUEST = """
        <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">
          <RequestType>DBSCHEMA_SCHEMATA</RequestType>
          <Restrictions>
            <RestrictionList/>
          </Restrictions>
          <Properties>
            <PropertyList>
              <DataSourceInfo>FoodMart</DataSourceInfo>
              <Catalog>${catalog}</Catalog>
              <Format>Tabular</Format>
            </PropertyList>
          </Properties>
        </Discover>
        """;

    public static final String DBSCHEMA_TABLES_REQUEST = """
        <SOAP-ENV:Envelope
            xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
            SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <SOAP-ENV:Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"
                SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
            <RequestType>$DBSCHEMA_TABLES</RequestType>
            <Restrictions>
              <RestrictionList>
              </RestrictionList>
            </Restrictions>
            <Properties>
              <PropertyList>
                <DataSourceInfo>FoodMart</DataSourceInfo>
                <Content>SchemaData</Content>
              </PropertyList>
            </Properties>
            </Discover>
        </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String DBSCHEMA_SOURCE_TABLES_REQUEST = """
        """;
    public static final String DBSCHEMA_TABLES_INFO_REQUEST = """
        """;
    public static final String MDSCHEMA_ACTIONS_REQUEST = """
        <SOAP-ENV:Envelope
            xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
            SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <SOAP-ENV:Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"
                SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                <RequestType>MDSCHEMA_ACTIONS</RequestType>
                <Restrictions>
                  <RestrictionList>
                  </RestrictionList>
                </Restrictions>
                <Properties>
                  <PropertyList>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Catalog>FoodMart</Catalog>
                    <Format>Tabular</Format>
                    <Content>SchemaData</Content>
                  </PropertyList>
                </Properties>
            </Discover>
          </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_CUBES_REQUEST = """
        <SOAP-ENV:Envelope
            xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
            SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <SOAP-ENV:Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"
                SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                <RequestType>MDSCHEMA_CUBES</RequestType>
                <Restrictions>
                  <RestrictionList>
                  </RestrictionList>
                </Restrictions>
                <Properties>
                  <PropertyList>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Catalog>FoodMart</Catalog>
                    <Format>Tabular</Format>
                    <Content>SchemaData</Content>
                  </PropertyList>
                </Properties>
            </Discover>
          </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_DIMENSIONS_REQUEST = """
        <SOAP-ENV:Envelope
            xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
            SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <SOAP-ENV:Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"
                SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
            <RequestType>MDSCHEMA_DIMENSIONS</RequestType>
            <Restrictions>
              <RestrictionList>
              </RestrictionList>
            </Restrictions>
            <Properties>
              <PropertyList>
                <DataSourceInfo>FoodMart</DataSourceInfo>
                <Catalog>FoodMart</Catalog>
                <Format>Tabular</Format>
                <Content>SchemaData</Content>
              </PropertyList>
            </Properties>
            </Discover>
        </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_FUNCTIONS_REQUEST = """
        <SOAP-ENV:Envelope
        xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
          SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <SOAP-ENV:Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"
                SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
            <RequestType>MDSCHEMA_FUNCTIONS</RequestType>
            <Restrictions>
              <RestrictionList>
                <FUNCTION_NAME>Item</FUNCTION_NAME>
              </RestrictionList>
            </Restrictions>
            <Properties>
              <PropertyList>
                <DataSourceInfo>FoodMart</DataSourceInfo>
                <Content>SchemaData</Content>
              </PropertyList>
            </Properties>
            </Discover>
        </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_HIERARCHIES_REQUEST = """
        <SOAP-ENV:Envelope
            xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
            SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <SOAP-ENV:Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"
                SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
            <RequestType>MDSCHEMA_HIERARCHIES</RequestType>
            <Restrictions>
              <RestrictionList>
                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                <CUBE_NAME>Sales</CUBE_NAME>
              </RestrictionList>
            </Restrictions>
            <Properties>
              <PropertyList>
                <DataSourceInfo>FoodMart</DataSourceInfo>
                <Content>SchemaData</Content>
              </PropertyList>
            </Properties>
            </Discover>
        </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_LEVELS_REQUEST = """
        <SOAP-ENV:Envelope
        xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
          SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <SOAP-ENV:Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"
                SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
            <RequestType>MDSCHEMA_LEVELS</RequestType>
            <Restrictions>
              <RestrictionList>
                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                <CUBE_NAME>Sales</CUBE_NAME>
                <DIMENSION_UNIQUE_NAME>[Customers]</DIMENSION_UNIQUE_NAME>
              </RestrictionList>
            </Restrictions>
            <Properties>
              <PropertyList>
                <DataSourceInfo>FoodMart</DataSourceInfo>
                <Catalog>FoodMart</Catalog>
                <Format>Tabular</Format>
                <Content>SchemaData</Content>
              </PropertyList>
            </Properties>
            </Discover>
        </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_MEASUREGROUP_DIMENSIONS_REQUEST = """
        """;
    public static final String MDSCHEMA_MEASURES_REQUEST = """
        <SOAP-ENV:Envelope
        xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
          SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <SOAP-ENV:Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"
                SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
            <RequestType>MDSCHEMA_MEASURES</RequestType>
            <Restrictions>
              <RestrictionList>
                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                <CUBE_NAME>Sales</CUBE_NAME>
              </RestrictionList>
            </Restrictions>
            <Properties>
              <PropertyList>
                <DataSourceInfo>FoodMart</DataSourceInfo>
                <Catalog>FoodMart</Catalog>
                <Format>Tabular</Format>
                <Content>SchemaData</Content>
                <EmitInvisibleMembers>true</EmitInvisibleMembers>
              </PropertyList>
            </Properties>
            </Discover>
        </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_MEMBERS_REQUEST = """
        <SOAP-ENV:Envelope
        xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
          SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <SOAP-ENV:Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"
                SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
            <RequestType>MDSCHEMA_MEMBERS</RequestType>
            <Restrictions>
              <RestrictionList>
                <CATALOG_NAME>FoodMart</CATALOG_NAME>
                <CUBE_NAME>Sales</CUBE_NAME>
                <HIERARCHY_UNIQUE_NAME>[Gender]</HIERARCHY_UNIQUE_NAME>
              </RestrictionList>
            </Restrictions>
            <Properties>
              <PropertyList>
                <DataSourceInfo>FoodMart</DataSourceInfo>
                <Catalog>FoodMart</Catalog>
                <Format>Tabular</Format>
                <Content>SchemaData</Content>
              </PropertyList>
            </Properties>
            </Discover>
        </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_PROPERTIES_REQUEST = """
        <SOAP-ENV:Envelope
            xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
            SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <SOAP-ENV:Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"
                SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
            <RequestType>MDSCHEMA_PROPERTIES</RequestType>
            <Restrictions>
              <RestrictionList>
              </RestrictionList>
            </Restrictions>
            <Properties>
              <PropertyList>
                <DataSourceInfo>FoodMart</DataSourceInfo>
                <Content>SchemaData</Content>
              </PropertyList>
            </Properties>
            </Discover>
        </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_SETS_REQUEST = """
        <SOAP-ENV:Envelope
            xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
            SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
          <SOAP-ENV:Body>
            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"
                SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
            <RequestType>${request.type}</RequestType>
            <Restrictions>
              <RestrictionList>
              </RestrictionList>
            </Restrictions>
            <Properties>
              <PropertyList>
                <DataSourceInfo>FoodMart</DataSourceInfo>
                <Catalog>FoodMart</Catalog>
                <Format>Tabular</Format>
                <Content>SchemaData</Content>
              </PropertyList>
            </Properties>
            </Discover>
        </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        """;
    public static final String MDSCHEMA_KPIS_REQUEST = """
        """;
    public static final String MDSCHEMA_MEASUREGROUPS_REQUEST = """
        """;


}
