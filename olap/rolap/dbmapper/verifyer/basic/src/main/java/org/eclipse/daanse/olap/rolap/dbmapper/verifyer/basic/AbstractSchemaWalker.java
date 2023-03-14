package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.api.NamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.api.UserDefinedFunction;
import org.eclipse.daanse.olap.rolap.dbmapper.api.VirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;

public abstract class AbstractSchemaWalker {
    protected List<VerificationResult> results = new ArrayList<>();

    public List<VerificationResult> checkSchema(Schema schema) {

        schema.cube()
                .forEach(this::checkCube);

        checkNamedSetList(schema.namedSet());

        if (schema.virtualCube() != null) {
            schema.virtualCube()
                    .forEach(this::checkVirtualCube);
        }

        if (schema.userDefinedFunctions() != null) {
            schema.userDefinedFunctions()
                    .forEach(this::checkUserDefinedFunction);
        }
        return results;

    }

    protected void checkCube(Cube cube1) {
    }

    protected void checkNamedSetList(List<? extends NamedSet> namedSet) {
        if (namedSet != null) {
            namedSet.forEach(ns -> checkNamedSet(ns));

        }

    }

    protected void checkNamedSet(NamedSet ns) {

    }

    protected void checkVirtualCube(VirtualCube virtualcube1) {
    }

    protected void checkUserDefinedFunction(UserDefinedFunction userdefinedfunction1) {
    }

    protected static boolean isEmpty(String v) {
        return (v == null) || v.equals("");
    }

}
