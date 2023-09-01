package mondrian.olap.interfaces;

import java.io.PrintWriter;

public interface QueryPart {

    /**
     * Returns an array of the object's children.  Those which are not are ignored.
     */
    Object[] getChildren();

    /**
     * Writes a string representation of this parse tree
     * node to the given writer.
     *
     * @param pw writer
     */
    void unparse(PrintWriter pw);

    /**
     * Returns the plan that Mondrian intends to use to execute this query.
     *
     * @param pw Print writer
     */
    void explain(PrintWriter pw);
}
