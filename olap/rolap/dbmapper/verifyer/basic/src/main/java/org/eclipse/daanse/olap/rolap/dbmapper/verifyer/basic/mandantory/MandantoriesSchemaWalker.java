package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.mandantory;

import org.eclipse.daanse.olap.rolap.dbmapper.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Cause;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.AbstractSchemaWalker;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.VerificationResultR;

public class MandantoriesSchemaWalker extends AbstractSchemaWalker {

    public MandantoriesSchemaWalker(MandantoriesVerifierConfig config) {
    }

    @Override
    protected void checkCube(Cube cube) {
        super.checkCube(cube);

        if (isEmpty(cube.name())) {
            results.add(new VerificationResultR("Cube name must be set", "Cube name must be set", Level.ERROR,
                    Cause.SCHEMA));
        }

    }

}
