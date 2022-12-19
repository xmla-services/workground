package mondrian.rolap.util;

import org.eclipse.daanse.olap.rolap.dbmapper.api.CalculatedMember;

public class CalculatedMemberUtil {

    public static String getFormula(CalculatedMember calculatedMember) {
        if (calculatedMember.formulaElement() != null) {
            return calculatedMember.formulaElement().cdata();
        } else {
            return calculatedMember.formula();
        }
    }

}
