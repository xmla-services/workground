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
}
