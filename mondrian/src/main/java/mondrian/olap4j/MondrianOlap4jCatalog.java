/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.olap4j;

import java.util.Map;

import org.eclipse.daanse.olap.api.access.Access;
import org.eclipse.daanse.olap.api.model.OlapElement;
import org.olap4j.OlapDatabaseMetaData;
import org.olap4j.OlapException;
import org.olap4j.impl.Named;
import org.olap4j.impl.NamedListImpl;
import org.olap4j.impl.Olap4jUtil;
import org.olap4j.metadata.Catalog;
import org.olap4j.metadata.Database;
import org.olap4j.metadata.NamedList;
import org.olap4j.metadata.Schema;

import mondrian.rolap.RolapSchema;

/**
 * Implementation of {@link Catalog}
 * for the Mondrian OLAP engine.
 *
 * @author jhyde
 * @since May 23, 2007
 */
class MondrianOlap4jCatalog
    extends MondrianOlap4jMetadataElement
    implements Catalog, Named
{
    final MondrianOlap4jDatabaseMetaData olap4jDatabaseMetaData;
    final String name;
    final Map<String, RolapSchema> schemaMap;
    final MondrianOlap4jDatabase olap4jDatabase;

    MondrianOlap4jCatalog(
        MondrianOlap4jDatabaseMetaData olap4jDatabaseMetaData,
        String name,
        MondrianOlap4jDatabase database,
        Map<String, RolapSchema> schemaMap)
    {
        assert database != null;
        this.olap4jDatabaseMetaData = olap4jDatabaseMetaData;
        this.name = name;
        this.olap4jDatabase = database;
        this.schemaMap = schemaMap;
        // Make sure to register the schemas.
        for (Map.Entry<String, RolapSchema> entry : schemaMap.entrySet()) {
            String schemaName = entry.getKey();
            final  org.eclipse.daanse.olap.api.model.Schema schema = entry.getValue();
            if (schemaName == null) {
                schemaName = schema.getName();
            }
            MondrianOlap4jSchema olap4jSchema =
                new MondrianOlap4jSchema(
                    this, schemaName, schema);
            olap4jDatabaseMetaData.olap4jConnection.schemaMap.put(
                schema, olap4jSchema);
        }
    }

    @Override
	public NamedList<Schema> getSchemas() throws OlapException {
        final NamedList<MondrianOlap4jSchema> list =
            new NamedListImpl<>();
        for (Map.Entry<String, RolapSchema> entry : schemaMap.entrySet()) {
            String schemaName = entry.getKey();
            final org.eclipse.daanse.olap.api.model.Schema schema = entry.getValue();
            final MondrianOlap4jConnection oConn =
                ((MondrianOlap4jConnection)olap4jDatabase
                    .getOlapConnection());
            if (oConn
                .getMondrianConnection().getRole().getAccess(schema)
                != Access.NONE)
            {
                if (schemaName == null) {
                    schemaName = schema.getName();
                }
                MondrianOlap4jSchema olap4jSchema =
                    new MondrianOlap4jSchema(
                        this, schemaName, schema);
                list.add(olap4jSchema);
            }
        }
        return Olap4jUtil.cast(list);
    }

    @Override
	public String getName() {
        return name;
    }

    @Override
	public OlapDatabaseMetaData getMetaData() {
        return olap4jDatabaseMetaData;
    }

    @Override
	public Database getDatabase() {
        return olap4jDatabase;
    }

    @Override
	protected OlapElement getOlapElement() {
        return null;
    }
}
