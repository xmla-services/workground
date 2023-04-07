package org.eclipse.daanse.mdx.parser.ccc;

import org.assertj.core.data.Index;
import org.eclipse.daanse.mdx.model.api.select.SelectDimensionPropertyListClause;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectDimensionPropertyListClauseTest {

    @Test
    public void test() throws MdxParserException {
        SelectDimensionPropertyListClause selectDimensionPropertyListClause =
            new MdxParserWrapper("DIMENSION PROPERTIES BACK_COLOR, FORE_COLOR").parseSelectDimensionPropertyListClause();
        assertThat(selectDimensionPropertyListClause).isNotNull();
        assertThat(selectDimensionPropertyListClause.properties()).isNotNull().hasSize(2);
        assertThat(selectDimensionPropertyListClause.properties()).contains("BACK_COLOR", Index.atIndex(0));
        assertThat(selectDimensionPropertyListClause.properties()).contains("FORE_COLOR", Index.atIndex(1));
    }
}
