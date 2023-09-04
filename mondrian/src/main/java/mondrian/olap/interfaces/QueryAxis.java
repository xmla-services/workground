package mondrian.olap.interfaces;

import mondrian.calc.ExpCompiler;
import mondrian.calc.ResultStyle;
import mondrian.mdx.MdxVisitor;
import mondrian.olap.AxisOrdinal;
import mondrian.olap.Exp;
import mondrian.olap.Id;
import mondrian.olap.QueryAxisImpl;
import mondrian.olap.Validator;
import mondrian.spi.SegmentHeader;
import org.eclipse.daanse.olap.api.model.Level;
import org.eclipse.daanse.olap.calc.api.Calc;

public interface QueryAxis extends QueryPart{

    String getAxisName();

    AxisOrdinal getAxisOrdinal();

    boolean isNonEmpty();

    void setNonEmpty(boolean nonEmpty);

    Exp getSet();

    void setSet(Exp set);

    Calc compile(ExpCompiler compiler, ResultStyle resultStyle);

    Object accept(MdxVisitor visitor);

    Id[] getDimensionProperties();

    QueryAxisImpl.SubtotalVisibility getSubtotalVisibility();

    void validate(Validator validator);

    void addLevel(Level level);

    void resolve(Validator validator);

    boolean isOrdered();

    void setOrdered(boolean ordered);

}
