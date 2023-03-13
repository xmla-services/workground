package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;

public abstract class AbstractSchemaWalker {
    List<VerificationResult> results = new ArrayList<>();
    
    

    List<VerificationResult> checkCube(Cube cube) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    protected static boolean isEmpty(String v) {
        return (v == null) || v.equals("");
    }
}
