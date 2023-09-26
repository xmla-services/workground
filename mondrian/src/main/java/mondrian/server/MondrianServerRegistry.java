/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mondrian.olap.MondrianServer;
import mondrian.olap.Util;
import mondrian.spi.CatalogLocator;
import mondrian.spi.impl.IdentityCatalogLocator;
import mondrian.util.LockBox;

/**
 * Registry of all servers within this JVM, and also serves as a factory for
 * servers.
 *
 * <p>This class is not a public API. User applications should use the
 * methods in {@link mondrian.olap.MondrianServer}.
 *
 * @author jhyde
 */
public class MondrianServerRegistry {
    public static final Logger logger =
        LoggerFactory.getLogger(MondrianServerRegistry.class);
    public static final MondrianServerRegistry INSTANCE =
        new MondrianServerRegistry();

    public MondrianServerRegistry() {
        super();
    }

    /**
     * Registry of all servers.
     */
    final LockBox lockBox = new LockBox();

    /**
     * The one and only one server that does not have a repository.
     */
    final MondrianServer staticServer =
        createWithRepository(null, null);

    /**
     * Looks up a server with a given id. If the id is null, returns the
     * static server.
     *
     * @param instanceId Unique identifier of server instance
     * @return Server
     * @throws RuntimeException if no server instance exists
     */
    public MondrianServer serverForId(String instanceId) {
        if (instanceId != null) {
            final LockBox.Entry entry = lockBox.get(instanceId);
            if (entry == null) {
                throw Util.newError(
                    new StringBuilder("No server instance has id '").append(instanceId).append("'").toString());
            }
            return (MondrianServer) entry.getValue();
        } else {
            return staticServer;
        }
    }

    public String getCopyrightYear() {
        return MondrianServerVersion.COPYRIGHT_YEAR;
    }

    public String getProductVersion() {
        return MondrianServerVersion.VERSION;
    }

    public int getSchemaVersion() { return MondrianServerVersion.SCHEMA_VERSION; }

   public MondrianServer createWithRepository(
        RepositoryContentFinder contentFinder,
        CatalogLocator catalogLocator)
    {
        if (catalogLocator == null) {
            catalogLocator = new IdentityCatalogLocator();
        }
        final Repository repository;
        if (contentFinder == null) {
            // NOTE: registry.staticServer is initialized by calling this
            // method; this is the only time that it is null.
            if (staticServer != null) {
                return staticServer;
            }
            repository = new ImplicitRepository();
        } else {
            repository = new FileRepository(contentFinder, catalogLocator);
        }
        return new MondrianServerImpl(this, repository, catalogLocator);
    }
}
