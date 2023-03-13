package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.jdbc;

import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.jdbc.AbstractSchemaWalker.isEmpty;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Cause;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;

public class XSchemaWalker extends AbstractSchemaWalker {
    
    @Override
    List<VerificationResult> checkCube(Cube cube) {
         super.checkCube(cube);
         
         if (isEmpty(cube.name())) {
             results.add(new VerificationResultR("Cube name must be set", "Cube name must be set", Level.ERROR,
                     Cause.SCHEMA));
         }
         return results;
    }

}
