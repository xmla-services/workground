package org.eclipse.daanse.xmla.ws.tck;

import java.util.List;
import java.util.Optional;

import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.model.record.discover.discover.properties.DiscoverPropertiesResponseRowR;

public class SpecCompatibitilityDiscoverResponses {

    
    // 4.3.1 Client Sends Request
    // 4.3.2 Server Response
    
    
   // For all RequestTypes

    DiscoverPropertiesResponseRowR rowCatalog = new DiscoverPropertiesResponseRowR("Catalog", Optional.of("Catalog"),
            Optional.of("string"), "ReadWrite", Optional.of(false), Optional.of("AdventureWorks_SSAS"));

    DiscoverPropertiesResponseRowR rowTimeout = new DiscoverPropertiesResponseRowR("Timeout", Optional.of("Timeout"),
            Optional.of("int"), "ReadWrite", Optional.of(false), Optional.empty());

    DiscoverPropertiesResponseRowR rowContent = new DiscoverPropertiesResponseRowR("Content", Optional.of("Content"),
            Optional.of("string"), "Write", Optional.of(false), Optional.of("SchemaData"));

    DiscoverPropertiesResponseRowR rowFormat = new DiscoverPropertiesResponseRowR("Format", Optional.of("Format"),
            Optional.of("string"), "Write", Optional.of(false), Optional.of("Native"));

    DiscoverPropertiesResponseRowR rowAxisFormat = new DiscoverPropertiesResponseRowR("AxisFormat",
            Optional.of("AxisFormat"), Optional.of("string"), "Write", Optional.of(false), Optional.of("TupleFormat"));

    DiscoverPropertiesResponseRowR rowBeginRange = new DiscoverPropertiesResponseRowR("BeginRange",
            Optional.of("BeginRange"), Optional.of("int"), "Write", Optional.of(false), Optional.of("-1"));

    DiscoverPropertiesResponseRowR rowEndRange = new DiscoverPropertiesResponseRowR("EndRange", Optional.of("EndRange"),
            Optional.of("int"), "Write", Optional.of(false), Optional.of("-1"));

    DiscoverPropertiesResponseRowR rowShowHiddenCubes = new DiscoverPropertiesResponseRowR("ShowHiddenCubes",
            Optional.of("ShowHiddenCubes"), Optional.of("boolean"), "ReadWrite", Optional.of(false),
            Optional.of("false"));

    DiscoverPropertiesResponseRowR rowMaximumRows = new DiscoverPropertiesResponseRowR("MaximumRows",
            Optional.of("MaximumRows"), Optional.of("int"), "Write", Optional.of(false), Optional.empty());

    DiscoverPropertiesResponseRowR rowVisualMode = new DiscoverPropertiesResponseRowR("VisualMode",
            Optional.of("VisualMode"), Optional.of("int"), "Write", Optional.of(false), Optional.of("0"));

    DiscoverPropertiesResponseRowR rowDbpropMsmdCachePolicy = new DiscoverPropertiesResponseRowR(
            "DbpropMsmdCachePolicy", Optional.of("DbpropMsmdCachePolicy"), Optional.of("int"), "ReadWrite",
            Optional.of(false), Optional.empty());

    DiscoverPropertiesResponseRowR rowDbpropMsmdCacheRatio = new DiscoverPropertiesResponseRowR("DbpropMsmdCacheRatio",
            Optional.of("DbpropMsmdCacheRatio"), Optional.of("double"), "ReadWrite", Optional.of(false),
            Optional.empty());

    DiscoverPropertiesResponseRowR rowDbpropMsmdCacheMode = new DiscoverPropertiesResponseRowR("DbpropMsmdCacheMode",
            Optional.of("DbpropMsmdCacheMode"), Optional.of("int"), "ReadWrite", Optional.of(false), Optional.empty());

    List<DiscoverPropertiesResponseRow> resultSpec = List.of(rowCatalog, rowTimeout, rowContent, rowFormat,
            rowAxisFormat, rowBeginRange, rowEndRange, rowShowHiddenCubes, rowMaximumRows, rowVisualMode,
            rowDbpropMsmdCachePolicy, rowDbpropMsmdCacheRatio, rowDbpropMsmdCacheMode);

}
