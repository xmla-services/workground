package org.eclipse.daanse.mdx.parser.ccc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.daanse.mdx.parser.ccc.ExpressionTest.CallExpressionTest.checkArgument;

import java.math.BigDecimal;

import org.eclipse.daanse.mdx.model.api.expression.CallExpression;
import org.eclipse.daanse.mdx.model.api.expression.CompoundId;
import org.eclipse.daanse.mdx.model.api.expression.KeyObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.NameObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.NumericLiteral;
import org.eclipse.daanse.mdx.model.api.expression.ObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.select.MemberPropertyDefinition;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.junit.jupiter.api.Test;

class MemberPropertyDefinitionTest {

	@Test
	void test1() throws MdxParserException {
		MemberPropertyDefinition memberPropertyDefinition = new MdxParserWrapper("[name] = [test]")
				.parseMemberPropertyDefinition();
		assertThat(memberPropertyDefinition).isNotNull();
		assertThat(memberPropertyDefinition.objectIdentifier()).isInstanceOf(NameObjectIdentifier.class);
		NameObjectIdentifier nameObjectIdentifier1 = (NameObjectIdentifier) memberPropertyDefinition.objectIdentifier();
		assertThat(nameObjectIdentifier1.name()).isEqualTo("name");
		assertThat(nameObjectIdentifier1.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
		assertThat(memberPropertyDefinition.expression()).isInstanceOf(CompoundId.class);
		CompoundId compoundId = (CompoundId) memberPropertyDefinition.expression();
		assertThat(compoundId.objectIdentifiers()).hasSize(1);
		assertThat(compoundId.objectIdentifiers().get(0)).isInstanceOf(NameObjectIdentifier.class);
		NameObjectIdentifier nameObjectIdentifier2 = (NameObjectIdentifier) compoundId.objectIdentifiers().get(0);
		assertThat(nameObjectIdentifier2.name()).isEqualTo("test");
		assertThat(nameObjectIdentifier2.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
	}

	@Test
	void test2() throws MdxParserException {
		MemberPropertyDefinition memberPropertyDefinition = new MdxParserWrapper("[name] = 9")
				.parseMemberPropertyDefinition();
		assertThat(memberPropertyDefinition).isNotNull();
		assertThat(memberPropertyDefinition.objectIdentifier()).isInstanceOf(NameObjectIdentifier.class);
		NameObjectIdentifier nameObjectIdentifier1 = (NameObjectIdentifier) memberPropertyDefinition.objectIdentifier();
		assertThat(nameObjectIdentifier1.name()).isEqualTo("name");
		assertThat(nameObjectIdentifier1.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
		assertThat(memberPropertyDefinition.expression()).isInstanceOf(NumericLiteral.class);
		NumericLiteral numericLiteral = (NumericLiteral) memberPropertyDefinition.expression();
		assertThat(numericLiteral.value()).isEqualTo(BigDecimal.valueOf(9));
	}

	@Test
	void test3() throws MdxParserException {
		MemberPropertyDefinition memberPropertyDefinition = new MdxParserWrapper(
				"[name] = { expression1, expression2" + " }").parseMemberPropertyDefinition();
		assertThat(memberPropertyDefinition).isNotNull();
		assertThat(memberPropertyDefinition.objectIdentifier()).isInstanceOf(NameObjectIdentifier.class);
		NameObjectIdentifier nameObjectIdentifier1 = (NameObjectIdentifier) memberPropertyDefinition.objectIdentifier();
		assertThat(nameObjectIdentifier1.name()).isEqualTo("name");
		assertThat(nameObjectIdentifier1.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);

		assertThat(memberPropertyDefinition.expression()).isInstanceOf(CallExpression.class);
		CallExpression callExpression = (CallExpression) memberPropertyDefinition.expression();
		assertThat(callExpression.type()).isEqualTo(CallExpression.Type.BRACES);
		assertThat(callExpression.name()).isEqualTo("{}");
		assertThat(callExpression.expressions()).hasSize(2);
		checkArgument(callExpression, 0, "expression1");
		checkArgument(callExpression, 1, "expression2");
	}

	@Test
	void test4() throws MdxParserException {
		MemberPropertyDefinition memberPropertyDefinition = new MdxParserWrapper("[name] = FunctionName([arg1, arg2])")
				.parseMemberPropertyDefinition();
		assertThat(memberPropertyDefinition).isNotNull();
		assertThat(memberPropertyDefinition.objectIdentifier()).isInstanceOf(NameObjectIdentifier.class);
		NameObjectIdentifier nameObjectIdentifier1 = (NameObjectIdentifier) memberPropertyDefinition.objectIdentifier();
		assertThat(nameObjectIdentifier1.name()).isEqualTo("name");
		assertThat(nameObjectIdentifier1.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);

		assertThat(memberPropertyDefinition.expression()).isInstanceOf(CallExpression.class);
		CallExpression callExpression = (CallExpression) memberPropertyDefinition.expression();
		assertThat(callExpression.type()).isEqualTo(CallExpression.Type.FUNCTION);
		assertThat(callExpression.name()).isEqualTo("FunctionName");
		assertThat(callExpression.expressions()).hasSize(1);
		checkArgument(callExpression, 0, "arg1, arg2");
	}

	@Test
	void test5() throws MdxParserException {
		MemberPropertyDefinition memberPropertyDefinition = new MdxParserWrapper("[name] = [x].&bar")
				.parseMemberPropertyDefinition();
		assertThat(memberPropertyDefinition).isNotNull();
		assertThat(memberPropertyDefinition.objectIdentifier()).isInstanceOf(NameObjectIdentifier.class);
		NameObjectIdentifier nameObjectIdentifier1 = (NameObjectIdentifier) memberPropertyDefinition.objectIdentifier();
		assertThat(nameObjectIdentifier1.name()).isEqualTo("name");
		assertThat(nameObjectIdentifier1.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);

		assertThat(memberPropertyDefinition.expression()).isInstanceOf(CompoundId.class);
		CompoundId compoundId = (CompoundId) memberPropertyDefinition.expression();
		assertThat(compoundId.objectIdentifiers().get(0)).isNotNull().isInstanceOf(NameObjectIdentifier.class);
		assertThat(compoundId.objectIdentifiers().get(1)).isNotNull().isInstanceOf(KeyObjectIdentifier.class);

		NameObjectIdentifier nameObjectIdentifier00 = (NameObjectIdentifier) compoundId.objectIdentifiers().get(0);
		assertThat(nameObjectIdentifier00.name()).isEqualTo("x");
		assertThat(nameObjectIdentifier00.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);

		KeyObjectIdentifier keyObjectIdentifier = (KeyObjectIdentifier) compoundId.objectIdentifiers().get(1);
		assertThat(keyObjectIdentifier.nameObjectIdentifiers()).isNotNull().hasSize(1);
		assertThat(keyObjectIdentifier.nameObjectIdentifiers().get(0)).isInstanceOf(NameObjectIdentifier.class);
		NameObjectIdentifier nameObjectIdentifier0 = keyObjectIdentifier.nameObjectIdentifiers().get(0);
		assertThat(nameObjectIdentifier0.name()).isEqualTo("bar");
		assertThat(nameObjectIdentifier0.quoting()).isEqualTo(ObjectIdentifier.Quoting.UNQUOTED);
	}

}
