package org.eclipse.daanse.function.definition;

import mondrian.olap.type.MemberType;
import mondrian.olap.type.SetType;

import org.eclipse.daanse.function.FunDef;
import org.eclipse.daanse.function.FunUtil;
import org.eclipse.daanse.function.FunctionResolver;
import org.eclipse.daanse.function.MultiResolver;
import org.eclipse.daanse.olap.api.query.component.Expression;
import org.eclipse.daanse.olap.api.type.Type;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import aQute.bnd.annotation.spi.ServiceProvider;


@ServiceProvider(value = FunctionResolver.class, attribute = { "function.definition:String='AddCalculatedMembersFun'" })
@Component(service = FunctionResolver.class, scope = ServiceScope.SINGLETON)
public class AddCalculatedMembersFunResolver extends MultiResolver {
    private static AddCalculatedMembersFunDef addCalculatedMembersFunDef = new AddCalculatedMembersFunDef();

    public AddCalculatedMembersFunResolver() {
        super(
            addCalculatedMembersFunDef.getName(),
            addCalculatedMembersFunDef.getSignature(),
            addCalculatedMembersFunDef.getDescription(),
            new String[] {AddCalculatedMembersFunDef.FLAG});
    }

    @Override
    protected FunDef createFunDef(Expression[] args, FunDef dummyFunDef) {
        if (args.length == 1) {
            Expression arg = args[0];
            final Type type1 = arg.getType();
            if (type1 instanceof SetType type) {
                if (type.getElementType() instanceof MemberType) {
                    return addCalculatedMembersFunDef;
                } else {
                    throw FunUtil.newEvalException(
                        addCalculatedMembersFunDef,
                        "Only single dimension members allowed in set for AddCalculatedMembers");
                }
            }
        }
        return null;
    }

    @Override
    public int compareTo(FunctionResolver resolver) {
        return 0;
    }
}
