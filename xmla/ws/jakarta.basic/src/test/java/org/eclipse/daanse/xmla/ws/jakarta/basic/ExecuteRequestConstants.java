package org.eclipse.daanse.xmla.ws.jakarta.basic;

public class ExecuteRequestConstants {

    public static final String DRILL_THROUGH_MAX_ROWS_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
        DRILLTHROUGH MAXROWS 3
        SELECT {[Customers].[USA].[CA].[Berkeley]} ON 0,
        {[Time].[1997]} ON 1
        FROM Sales
                 </Statement>
                </Command>
                <Properties>
                  <PropertyList>
                    <Catalog>FoodMart</Catalog>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Tabular</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

    public static final String DRILL_THROUGH_REQUEST = """
            <soapenv:Envelope
                xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <soapenv:Body>
            <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
              <Command>
                <Statement>
                                drillthrough
                                            select
                                            non empty{[Customers].[USA].[CA].[Concord]} on 0,
                                            non empty {[Product].[Drink].[Beverages].[Pure Juice Beverages].[Juice]} on 1
                                            from
                                            [Sales]
                                            where([Measures].[Sales Count])
              </Statement>
              </Command>
              <Properties>
                <PropertyList>
                  <Catalog>FoodMart</Catalog>
                  <DataSourceInfo>FoodMart</DataSourceInfo>
                  <Format>Tabular</Format>
                  <AxisFormat>TupleFormat</AxisFormat>
                </PropertyList>
              </Properties>
            </Execute>
            </soapenv:Body>
            </soapenv:Envelope>
        """;
    public static final String DRILL_THROUGH_ZERO_DIMENSIONAL_QUERY_REQUEST = """
            <soapenv:Envelope
                xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <soapenv:Body>
            <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
              <Command>
                <Statement>
                  DRILLTHROUGH MAXROWS 2
                  select
                  from [Sales]
                  where ([Measures].[Sales Count],
                      [Customers].[USA].[CA].[Concord],
                      [Time].[1997].[Q3],
                      [Product].[Drink].[Beverages].[Pure Juice Beverages].[Juice],
                      [Gender])
              </Statement>
              </Command>
              <Properties>
                <PropertyList>
                  <Catalog>FoodMart</Catalog>
                  <DataSourceInfo>FoodMart</DataSourceInfo>
                  <Format>Tabular</Format>
                  <AxisFormat>TupleFormat</AxisFormat>
                </PropertyList>
              </Properties>
            </Execute>
            </soapenv:Body>
            </soapenv:Envelope>
        """;
    public static final String EXECUTE_SLICER_REQUEST = """
            <soapenv:Envelope
                xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <soapenv:Body>
                    <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                    <Command>
                    <Statement>
            SELECT {[Customers].Children} ON 0,
            {[Gender].Children} ON 1
            FROM Sales
            WHERE ([Time].[1997].[Q2], [Marital Status], [Measures].[Store Sales])
                     </Statement>
                    </Command>
                    <Properties>
                      <PropertyList>
                        <Catalog>FoodMart</Catalog>
                        <DataSourceInfo>FoodMart</DataSourceInfo>
                        <Format>Multidimensional</Format>
                        <AxisFormat>TupleFormat</AxisFormat>
                      </PropertyList>
                    </Properties>
            </Execute>
            </soapenv:Body>
            </soapenv:Envelope>
        """;
    public static final String EXECUTE_SLICER_JSON_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
        SELECT {[Customers].Children} ON 0,
        {[Gender].Children} ON 1
        FROM Sales
        WHERE ([Time].[1997].[Q2], [Marital Status], [Measures].[Store Sales])
                 </Statement>
                </Command>
                <Properties>
                  <PropertyList>
                    <Catalog>FoodMart</Catalog>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                    <ResponseMimeType>application/json</ResponseMimeType>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

    public static final String EXECUTE_WITHOUT_CELL_PROPERTIES_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
        SELECT {[Customers].Children} ON 0,
        {[Gender].Children} ON 1
        FROM Sales
        WHERE ([Time].[1997].[Q2], [Marital Status], [Measures].[Store Sales])
                 </Statement>
                </Command>
                <Properties>
                  <PropertyList>
                    <Catalog>FoodMart</Catalog>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

    public static final String EXECUTE_WITH_CELL_PROPERTIES_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
        SELECT {[Customers].Children} ON 0,
        {[Gender].Children} ON 1
        FROM Sales
        WHERE ([Time].[1997].[Q2], [Marital Status], [Measures].[Store Sales])
        Cell Properties Format_String, Formatted_Value
                 </Statement>
                </Command>
                <Properties>
                  <PropertyList>
                    <Catalog>FoodMart</Catalog>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

    public static final String EXECUTE_WITH_MEMBER_KEY_DIMENSION_PROPERTY_FOR_MEMBER_WITHOUT_KEY_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
        SELECT {[Customers].Children} ON 0,
        {[Gender].Children} DIMENSION PROPERTIES MEMBER_KEY ON 1
        FROM Sales
        WHERE ([Time].[1997].[Q2], [Marital Status], [Measures].[Store Sales])
                 </Statement>
                </Command>
                <Properties>
                  <PropertyList>
                    <Catalog>FoodMart</Catalog>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

    //with special schema
    public static final String EXECUTE_ALIAS_WITH_SHARED_DIMENSION_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
                    select {[Measures].[Unit Sales]} on columns,
                    {[Customers-Alias].[Country].Members} on rows
                     from [Sales]
                 </Statement>
                </Command>
                <Properties>
                  <PropertyList>
                    <Catalog>FoodMart</Catalog>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

    public static final String EXECUTE_WITH_MEMBER_KEY_DIMENSION_PROPERTY_FOR_MEMBER_WITH_KEY_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
        SELECT {customers.[all customers].[USA].[CA].[Altadena].[Alice Cantrell]}
         DIMENSION PROPERTIES MEMBER_KEY ON 0
        FROM Sales
                 </Statement>
                </Command>
                <Properties>
                  <PropertyList>
                    <Catalog>FoodMart</Catalog>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

    public static final String EXECUTE_WITH_MEMBER_KEY_DIMENSION_PROPERTY_FOR_ALL_MEMBER_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
        SELECT {customers.[all customers]} DIMENSION PROPERTIES MEMBER_KEY ON 0
        FROM Sales
                 </Statement>
                </Command>
                <Properties>
                  <PropertyList>
                    <Catalog>FoodMart</Catalog>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

    public static final String EXECUTE_WITH_DIMENSION_PROPERTY_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
        SELECT {customers.[all customers].[USA].[CA].[Altadena].[Alice Cantrell]}
         DIMENSION PROPERTIES MEMBER_KEY ON 0
        FROM Sales
                 </Statement>
                </Command>
                <Properties>
                  <PropertyList>
                    <Catalog>FoodMart</Catalog>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

    public static final String EXECUTE_WITH_DIMENSION_PROPERTIES_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
        SELECT {[Customers].Children} ON 0,
        {[Gender].Children} DIMENSION PROPERTIES PARENT_LEVEL, PARENT_UNIQUE_NAME ON 1
        FROM Sales
        WHERE ([Time].[1997].[Q2], [Marital Status], [Measures].[Store Sales])
                 </Statement>
                </Command>
                <Properties>
                  <PropertyList>
                    <Catalog>FoodMart</Catalog>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

    public static final String EXECUTE_CROSS_JOIN_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
        SELECT CrossJoin({[Product].[All Products].children},
        {[Customers].[All Customers].children}) ON columns FROM Sales
                 </Statement>
                </Command>
                <Properties>
                  <PropertyList>
                    <Catalog>FoodMart</Catalog>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

    //with specific roles
    public static final String EXECUTE_CROSS_JOIN_ROLE_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
        SELECT CrossJoin({[Product].[All Products].children},
        {[Customers].[All Customers].children}) ON columns FROM Sales
                 </Statement>
                </Command>
                <Properties>
                  <PropertyList>
                    <Catalog>FoodMart</Catalog>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

    public static final String EXECUTE_BUG_MONDRIAN_762_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
        select NON EMPTY Hierarchize({[Time].[Year].Members}) DIMENSION PROPERTIES PARENT_UNIQUE_NAME ON COLUMNS
        from [HR]
        where [Measures].[Count]
                 </Statement>
                </Command>
                <Properties>
                  <PropertyList>
                    <Catalog>FoodMart</Catalog>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;
    public static final String EXECUTE_BUG_MONDRIAN_1316_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
        select [Measures].[Unit Sales] on columns from [Sales]
                 </Statement>
                </Command>
                <Restrictions>
                  <RestrictionList>
                    <CATALOG_NAME>FoodMart</CATALOG_NAME>
                  </RestrictionList>
                </Restrictions>
                <Properties>
                  <PropertyList>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

    public static final String EXECUTE_WITH_LOCALE_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
                   with
                   member [Measures].[Sales Formatted] as '[Measures].[Unit Sales]',FORMAT_STRING='Currency'
                   SELECT [Measures].[Sales Formatted] ON COLUMNS
                   FROM Sales
                 </Statement>
                </Command>
                <Restrictions>
                  <RestrictionList>
                    <CATALOG_NAME>FoodMart</CATALOG_NAME>
                  </RestrictionList>
                </Restrictions>
                <Properties>
                  <PropertyList>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                    <LocaleIdentifier>de_DE</LocaleIdentifier>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

    public static final String EMPTY_SET_REQUEST = """
        <soapenv:Envelope
            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <soapenv:Body>
                <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">
                <Command>
                <Statement>
                   SELECT {} ON COLUMNS FROM Sales
                 </Statement>
                </Command>
                <Restrictions>
                  <RestrictionList>
                    <CATALOG_NAME>FoodMart</CATALOG_NAME>
                  </RestrictionList>
                </Restrictions>
                <Properties>
                  <PropertyList>
                    <DataSourceInfo>FoodMart</DataSourceInfo>
                    <Format>Multidimensional</Format>
                    <AxisFormat>TupleFormat</AxisFormat>
                  </PropertyList>
                </Properties>
        </Execute>
        </soapenv:Body>
        </soapenv:Envelope>
        """;

}
