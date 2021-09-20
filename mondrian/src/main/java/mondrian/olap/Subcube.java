// Copyright (c) 2021 Sergei Semenkov
// All rights reserved.

package mondrian.olap;

import java.io.PrintWriter;
import java.util.*;

public class Subcube extends QueryPart {
    private final String cubeName;
    private final Subcube subcube;
    private final QueryAxis[] axes;
    private final QueryAxis slicerAxis;

    public Subcube(
            String cubeName,
            Subcube subcube,
            QueryAxis[] axes,
            QueryAxis slicerAxis)
    {
        this.cubeName = cubeName;
        this.subcube = subcube;
        this.axes = axes;
        this.slicerAxis = slicerAxis;
    }

    @Override
    public void unparse(PrintWriter pw) {
//        pw.print("CREATE SESSION MEMBER ");
//        pw.print("[" + cubeName + "]");
//        pw.print(".");
//        if(e != null) {
//            pw.print(e.getIdentifier());
//            pw.print(" AS ");
//            //pw.print(e,getExpression());
//        }
//        pw.println();
    }

    @Override
    public Object[] getChildren() {
        return new Object[] {cubeName};
    }

    public String getCubeName() {
        if(this.subcube != null) {
            return this.subcube.getCubeName();
        }
        else {
            return this.cubeName;
        }
    }

}

