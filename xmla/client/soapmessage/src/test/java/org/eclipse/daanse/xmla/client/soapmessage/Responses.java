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
}
