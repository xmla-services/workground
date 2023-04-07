package org.eclipse.daanse.mdx.parser.ccc;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.data.Index;
import org.eclipse.daanse.mdx.model.api.select.SelectDimensionPropertyListClause;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.junit.jupiter.api.Test;

class SelectDimensionPropertyListClauseTest {

	@Test
	void test() throws MdxParserException {
		SelectDimensionPropertyListClause selectDimensionPropertyListClause = new MdxParserWrapper(
				"DIMENSION PROPERTIES BACK_COLOR, FORE_COLOR").parseSelectDimensionPropertyListClause();
		assertThat(selectDimensionPropertyListClause).isNotNull();
		assertThat(selectDimensionPropertyListClause.properties()).isNotNull().hasSize(2);
		assertThat(selectDimensionPropertyListClause.properties()).contains("BACK_COLOR", Index.atIndex(0));
		assertThat(selectDimensionPropertyListClause.properties()).contains("FORE_COLOR", Index.atIndex(1));
	}
}
