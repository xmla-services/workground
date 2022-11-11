/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2005-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara and others
// All Rights Reserved.
*/
package mondrian.test.loader;

import mondrian.olap.Connection;
import mondrian.rolap.BatchTestCase;
import org.eclipse.daanse.sql.dialect.api.DatabaseProduct;
import org.eclipse.daanse.sql.dialect.api.Dialect;
import org.opencube.junit5.Constants;
import org.opencube.junit5.SchemaUtil;
import org.opencube.junit5.TestUtil;
import org.opencube.junit5.context.Context;

import java.io.File;

import static org.opencube.junit5.TestUtil.getDialect;

/**
 * Base class for tests that use
 * a CSV database defined in a single file. While the CsvDBLoader
 * supports being defined by a single file, list of files, or
 * directory with optional regular expression for matching files
 * in the directory to be loaded, this is simplest at this point.
 *
 * <p>
 * To use this file one must define both the directory and file
 * abstract methods.
 *
 * @author Richard M. Emberson
 */
public abstract class CsvDBTestCase extends BatchTestCase {

    protected void prepareContext(Context context) {
        try {
            File inputFile = new File(Constants.TESTFILES_DIR + "/mondrian/rolap/agg/" +  getFileName());

            CsvDBLoader loader = new CsvDBLoader();
            loader.setConnection(context.createConnection().getDataSource().getConnection());
            loader.initialize();
            loader.setInputFile(inputFile);
            DBLoader.Table[] tables = loader.getTables();
            loader.generateStatements(tables);

            // create database tables
            loader.executeStatements(tables);

            String baseSchema = TestUtil.getRawSchema(context);
            String schema = SchemaUtil.getSchema(baseSchema,
                    getParameterDescription(), getCubeDescription(), getVirtualCubeDescription(), getNamedSetDescription(),
                    getUdfDescription(), getRoleDescription());
            TestUtil.withSchema(context, schema);
        }
        catch (Exception e) {
            throw  new RuntimeException("Prepare context for csv tests failed");
        }
    }

    protected final boolean isApplicable(Connection connection) {
        final Dialect dialect = getDialect(connection);
        return dialect.allowsDdl()
                && dialect.getDatabaseProduct()
                != DatabaseProduct.INFOBRIGHT;
    }

    protected abstract String getFileName();


    protected String getParameterDescription() {
        return null;
    }

    protected String getCubeDescription() {
        throw new UnsupportedOperationException();
    }

    protected String getVirtualCubeDescription() {
        return null;
    }

    protected String getNamedSetDescription() {
        return null;
    }

    protected String getUdfDescription() {
        return null;
    }

    protected String getRoleDescription() {
        return null;
    }
}

// End CsvDBTestCase.java
