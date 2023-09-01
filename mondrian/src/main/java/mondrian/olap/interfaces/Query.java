package mondrian.olap.interfaces;

import mondrian.calc.ExpCompiler;
import mondrian.calc.ResultStyle;
import mondrian.olap.Exp;
import mondrian.olap.Formula;
import mondrian.olap.FunDef;
import mondrian.olap.Parameter;
import mondrian.olap.QueryAxis;
import mondrian.olap.SchemaReader;
import mondrian.olap.Validator;
import mondrian.rolap.RolapCube;
import mondrian.server.Statement;
import org.eclipse.daanse.olap.api.Connection;
import org.eclipse.daanse.olap.api.model.Cube;
import org.eclipse.daanse.olap.api.model.Hierarchy;
import org.eclipse.daanse.olap.api.model.Member;
import org.eclipse.daanse.olap.api.model.OlapElement;
import org.eclipse.daanse.olap.calc.api.Calc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Query extends QueryPart {

    SchemaReader getSchemaReader(boolean b);

    Cube getCube();

    void setResultStyle(ResultStyle list);

    QueryAxis[] getAxes();

    Calc compileExpression(Exp exp, boolean scalar, ResultStyle resultStyle);

    Map<Hierarchy, Calc> getSubcubeHierarchyCalcs();

    void replaceSubcubeMembers();

    void resolve();

    void clearEvalCache();

    QueryAxis getSlicerAxis();

    QueryPart[] getCellProperties();

    Set<Member> getMeasuresMembers();

    Calc getSlicerCalc();

    Calc[] getAxisCalcs();

    void setSubcubeHierarchies(HashMap<Hierarchy, HashMap<Member, Member>> subcubeHierarchies);

    void putEvalCache(String key, Object value);

    Object getEvalCache(String key);

    Formula[] getFormulas();

    Statement getStatement();

    Connection getConnection();

    void addFormulas(Formula[] toArray);

    Formula findFormula(String toString);

    Validator createValidator();

    Collection<RolapCube> getBaseCubes();

    void addMeasuresMembers(OlapElement olapElement);

    void setBaseCubes(List<RolapCube> baseCubeList);

    boolean nativeCrossJoinVirtualCube();

    boolean shouldAlertForNonNative(FunDef fun);

    ExpCompiler createCompiler();

    boolean hasCellProperty(String name);

    Parameter[] getParameters();

    ResultStyle getResultStyle();

    boolean ignoreInvalidMembers();

    boolean isCellPropertyEmpty();
}
