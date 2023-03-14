package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.jdbc;

import org.eclipse.daanse.olap.rolap.dbmapper.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Cause;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level;

public class BasicSchemaWalker extends AbstractSchemaWalker {

    public BasicSchemaWalker(BasicVerifierConfig config) {
        // TODO Auto-generated constructor stub
    }

    @Override
    void checkCube(Cube cube) {
        super.checkCube(cube);

        if (isEmpty(cube.name())) {
            results.add(new VerificationResultR("Cube name must be set", "Cube name must be set", Level.ERROR,
                    Cause.SCHEMA));
        }

    }

}
