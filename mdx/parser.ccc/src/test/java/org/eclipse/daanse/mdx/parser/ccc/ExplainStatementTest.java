package org.eclipse.daanse.mdx.parser.ccc;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.daanse.mdx.model.api.DrillthroughStatement;
import org.eclipse.daanse.mdx.model.api.ExplainStatement;
import org.eclipse.daanse.mdx.model.api.SelectStatement;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.junit.jupiter.api.Test;

class ExplainStatementTest {

	@Test
	void test1() throws MdxParserException {
		String mdx = """
				EXPLAIN PLAN FOR
				SELECT [Customer].[Gender].[Gender].Membmers ON COLUMNS,
				         {[Customer].[Customer].[Aaron A. Allen],
				          [Customer].[Customer].[Abigail Clark]} ON ROWS
				   FROM [Adventure Works]
				   WHERE [Measures].[Internet Sales Amount]
				""";
		ExplainStatement explainStatement = new MdxParserWrapper(mdx).parseExplainStatement();
		assertThat(explainStatement).isNotNull();
		assertThat(explainStatement.mdxStatement()).isNotNull().isInstanceOf(SelectStatement.class);
	}

	@Test
	void test2() throws MdxParserException {
		String mdx = """
				EXPLAIN PLAN FOR
				DRILLTHROUGH MAXROWS 10
				FIRSTROWSET 1
				SELECT *
				FROM [Adventure Works]
				RETURN a
				""";
		ExplainStatement explainStatement = new MdxParserWrapper(mdx).parseExplainStatement();
		assertThat(explainStatement).isNotNull();
		assertThat(explainStatement.mdxStatement()).isNotNull().isInstanceOf(DrillthroughStatement.class);
	}

    @Test
    void testExplain() throws MdxParserException {
        assertParseExplainStatement("""
            explain plan for
            with member [Mesaures].[Foo] as 1 + 3
            select [Measures].[Unit Sales] on 0,
            [Product].Children on 1
            from [Sales]
            """);

        assertParseExplainStatement("""
            explain plan for
            drillthrough maxrows 5
            with member [Mesaures].[Foo] as 1 + 3
            select [Measures].[Unit Sales] on 0,
            [Product].Children on 1
            from [Sales]
            """);
    }

    private void assertParseExplainStatement(String mdx) throws MdxParserException {
        ExplainStatement selectStatement = new MdxParserWrapper(mdx).parseExplainStatement();
        assertThat(selectStatement).isNotNull();
    }
}
