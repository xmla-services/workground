package org.eclipse.daanse.mdx.unparser.simple;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.eclipse.daanse.mdx.model.Axis;
import org.eclipse.daanse.mdx.model.NameObjectIdentifier;
import org.eclipse.daanse.mdx.model.ObjectIdentifier.Quoting;
import org.eclipse.daanse.mdx.model.SelectStatement;
import org.eclipse.daanse.mdx.model.select.SelectQueryClause;
import org.eclipse.daanse.mdx.model.select.SelectSlicerAxisClause;
import org.eclipse.daanse.mdx.model.select.SelectSubcubeClause;
import org.eclipse.daanse.mdx.model.select.SelectSubcubeClauseName;
import org.eclipse.daanse.mdx.model.select.SelectSubcubeClauseStatement;
import org.junit.jupiter.api.Test;

public class SimpleUnparserAxisTest {

    private SimpleUnparser unparser = new SimpleUnparser();

    private @Test void testSelectStatement() throws Exception {
        SelectStatement selectStatement = new SelectStatement(null, null, null, null, null);
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
        SelectSubcubeClauseStatement sscs = new SelectSubcubeClauseStatement(selectQueryClause, selectSubcubeClause,
                selectSlicerAxisClause);

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
        SelectSubcubeClauseName sccn = new SelectSubcubeClauseName(noi);
        return sccn;
    }

    private NameObjectIdentifier nameObjectIdentifier(String name, Quoting quoting) {
        NameObjectIdentifier noi = new NameObjectIdentifier(name, quoting);
        return noi;
    }

    @Test
    void testSelectSubcubeClauseStatement() throws Exception {
//        assertThat(unparser.unparse(selectStatement)).asString()
//                .isEqualTo("TODO");

    }

    @Test
    void testAxis() throws Exception {

        Axis axis_M2 = new Axis(-2, false);
        Axis axis_M1 = new Axis(-1, false);
        Axis axis_0 = new Axis(0, false);
        Axis axis_1 = new Axis(1, false);
        Axis axis_2 = new Axis(2, false);
        Axis axis_3 = new Axis(3, false);
        Axis axis_4 = new Axis(4, false);
        Axis axis_5 = new Axis(5, false);

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

        Axis axis_named_M2 = new Axis(-2, true);
        Axis axis_named_M1 = new Axis(-1, true);
        Axis axis_named_0 = new Axis(0, true);
        Axis axis_named_1 = new Axis(1, true);
        Axis axis_named_2 = new Axis(2, true);
        Axis axis_named_3 = new Axis(3, true);
        Axis axis_named_4 = new Axis(4, true);
        Axis axis_named_5 = new Axis(5, true);

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
