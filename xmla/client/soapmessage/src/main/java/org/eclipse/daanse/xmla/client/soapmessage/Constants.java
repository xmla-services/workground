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

public class Constants {

    public static final String URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS = "urn:schemas-microsoft-com:xml-analysis";
    public static final String SOAP_ACTION_DISCOVER = URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS + ":Discover";
    public static final String SOAP_ACTION_EXECUTE = URN_SCHEMAS_MICROSOFT_COM_XML_ANALYSIS + ":Execute";
    public static final String PROPERTIES = "Properties";
    public static final String PROPERTY_LIST = "PropertyList";
    public static final String RESTRICTION_LIST = "RestrictionList";
    public static final String RESTRICTIONS = "Restrictions";
    public static final String DISCOVER_DATASOURCES = "DISCOVER_DATASOURCES";
    public static final String DISCOVER = "Discover";
    public static final String REQUEST_TYPE = "RequestType";
}
