package mondrian.olap.interfaces;

import org.eclipse.daanse.olap.api.model.Member;

public non-sealed interface MemberExpr extends QueryPart {

    Member getMember();

}
