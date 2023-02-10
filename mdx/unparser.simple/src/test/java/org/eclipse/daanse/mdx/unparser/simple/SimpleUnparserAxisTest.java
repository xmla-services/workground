package org.eclipse.daanse.mdx.unparser.simple;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.eclipse.daanse.mdx.model.api.expression.NameObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.ObjectIdentifier.Quoting;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSlicerAxisClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClauseName;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClauseStatement;
import org.eclipse.daanse.mdx.model.record.SelectStatementR;
import org.eclipse.daanse.mdx.model.record.select.AxisR;
import org.junit.jupiter.api.Test;

public class SimpleUnparserAxisTest {

    private SimpleUnparser unparser = new SimpleUnparser();

    private @Test void testSelectStatement() throws Exception {
        SelectStatementR selectStatement = new SelectStatementR(null, null, null, null, null);
        assertThat(unparser.unparseSelectStatement(selectStatement)).asString()
                .isEqualTo("TODO");
    }

    @Test
    void testSelectSubcubeClause() throws Exception {
        SelectSubcubeClauseName selectStatementName = selectSubcubeClauseName("c", Quoting.QUOTED);
        assertThat(unparser.unparseSelectSubcubeClause(selectStatementName)).asString()
                .isEqualTo("[c]");

        SelectSubcubeClauseStatement selectStatementsStatement = selectStatementsStatement(null, selectStatementName,
                null);
        assertThat(unparser.unparseSelectSubcubeClause(selectStatementsStatement)).asString()
                .isEqualTo("todo");
    }

    private SelectSubcubeClauseStatement selectStatementsStatement(SelectQueryClause selectQueryClause,
            SelectSubcubeClause selectSubcubeClause, Optional<SelectSlicerAxisClause> selectSlicerAxisClause) {
        SelectSubcubeClauseStatement sscs = mock(SelectSubcubeClauseStatement.class);

        when(sscs.selectQueryClause()).thenReturn(selectQueryClause);
        when(sscs.selectSubcubeClause()).thenReturn(selectSubcubeClause);
        when(sscs.selectSlicerAxisClause()).thenReturn(selectSlicerAxisClause);

        return sscs;
    }

    @Test
    void testSelectSubcubeClauseName() throws Exception {

        // "cube"-> Reserved Word but quoted

        assertThat(unparser.unparseSelectSubcubeClauseName(selectSubcubeClauseName("c", Quoting.UNQUOTED))).asString()
                .isEqualTo("c");
        assertThat(unparser.unparseSelectSubcubeClauseName(selectSubcubeClauseName("c", Quoting.QUOTED))).asString()
                .isEqualTo("[c]");
        assertThat(unparser.unparseSelectSubcubeClauseName(selectSubcubeClauseName("cube", Quoting.QUOTED))).asString()
                .isEqualTo("[cube]");
        assertThat(unparser.unparseSelectSubcubeClauseName(selectSubcubeClauseName("with whitespace", Quoting.QUOTED)))
                .asString()
                .isEqualTo("[with whitespace]");
        assertThat(unparser.unparseSelectSubcubeClauseName(selectSubcubeClauseName("with [inner]", Quoting.QUOTED)))
                .asString()
                .isEqualTo("[with [inner]]]");
        assertThat(unparser.unparseSelectSubcubeClauseName(selectSubcubeClauseName("1", Quoting.QUOTED))).asString()
                .isEqualTo("[1]");
        assertThat(unparser.unparseSelectSubcubeClauseName(selectSubcubeClauseName(".", Quoting.QUOTED))).asString()
                .isEqualTo("[.]");

    }

    private SelectSubcubeClauseName selectSubcubeClauseName(String name, Quoting quoting) {
        NameObjectIdentifier noi = nameObjectIdentifier(name, quoting);

        SelectSubcubeClauseName sccn = mock(SelectSubcubeClauseName.class);
        when(sccn.cubeName()).thenReturn(noi);
        return sccn;
    }

    private NameObjectIdentifier nameObjectIdentifier(String name, Quoting quoting) {
        NameObjectIdentifier noi = mock(NameObjectIdentifier.class);
        when(noi.name()).thenReturn(name);
        when(noi.quoting()).thenReturn(quoting);

        return noi;
    }

    @Test
    void testSelectSubcubeClauseStatement() throws Exception {
//        assertThat(unparser.unparse(selectStatement)).asString()
//                .isEqualTo("TODO");

    }

    @Test
    void testAxis() throws Exception {

        AxisR axis_M2 = new AxisR(-2, false);
        AxisR axis_M1 = new AxisR(-1, false);
        AxisR axis_0 = new AxisR(0, false);
        AxisR axis_1 = new AxisR(1, false);
        AxisR axis_2 = new AxisR(2, false);
        AxisR axis_3 = new AxisR(3, false);
        AxisR axis_4 = new AxisR(4, false);
        AxisR axis_5 = new AxisR(5, false);

        assertThat(unparser.unparseAxis(axis_M2)).asString()
                .isEqualTo("-2");
        assertThat(unparser.unparseAxis(axis_M1)).asString()
                .isEqualTo("-1");
        assertThat(unparser.unparseAxis(axis_0)).asString()
                .isEqualTo("0");
        assertThat(unparser.unparseAxis(axis_1)).asString()
                .isEqualTo("1");
        assertThat(unparser.unparseAxis(axis_2)).asString()
                .isEqualTo("2");
        assertThat(unparser.unparseAxis(axis_3)).asString()
                .isEqualTo("3");
        assertThat(unparser.unparseAxis(axis_4)).asString()
                .isEqualTo("4");
        assertThat(unparser.unparseAxis(axis_5)).asString()
                .isEqualTo("5");
    }

    @Test
    void testAxisNamed() throws Exception {

        AxisR axis_named_M2 = new AxisR(-2, true);
        AxisR axis_named_M1 = new AxisR(-1, true);
        AxisR axis_named_0 = new AxisR(0, true);
        AxisR axis_named_1 = new AxisR(1, true);
        AxisR axis_named_2 = new AxisR(2, true);
        AxisR axis_named_3 = new AxisR(3, true);
        AxisR axis_named_4 = new AxisR(4, true);
        AxisR axis_named_5 = new AxisR(5, true);

        assertThat(unparser.unparseAxis(axis_named_M2)).asString()
                .isEqualTo("NONE");
        assertThat(unparser.unparseAxis(axis_named_M1)).asString()
                .isEqualTo("SLICER");
        assertThat(unparser.unparseAxis(axis_named_0)).asString()
                .isEqualTo("COLUMNS");
        assertThat(unparser.unparseAxis(axis_named_1)).asString()
                .isEqualTo("ROWS");
        assertThat(unparser.unparseAxis(axis_named_2)).asString()
                .isEqualTo("PAGES");
        assertThat(unparser.unparseAxis(axis_named_3)).asString()
                .isEqualTo("CHAPTERS");
        assertThat(unparser.unparseAxis(axis_named_4)).asString()
                .isEqualTo("SECTIONS");
        assertThat(unparser.unparseAxis(axis_named_5)).asString()
                .isEqualTo("AXIS(5)");

    }
}
