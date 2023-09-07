package org.eclipse.daanse.query;

import mondrian.olap.api.QueryPart;
import mondrian.olap.api.Refresh;
import org.eclipse.daanse.mdx.model.api.RefreshStatement;
import org.eclipse.daanse.mdx.model.api.expression.NameObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.ObjectIdentifier;
import org.eclipse.daanse.query.base.QueryProviderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QueryProviderImplTest {

    private QueryProviderImpl queryProviderImpl;
    private StringWriter stringWriter;
    private PrintWriter printWriter;


    @BeforeEach
    protected void setUp() throws Exception {
        queryProviderImpl = new QueryProviderImpl();
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);

    }

    @Test
    void refreshStatementToQueryQuotedTest() {
        RefreshStatement refreshStatement = mock(RefreshStatement.class);
        NameObjectIdentifier nameObjectIdentifier = mock(NameObjectIdentifier.class);
        when(nameObjectIdentifier.name()).thenReturn("cube");
        when(nameObjectIdentifier.quoting()).thenReturn(ObjectIdentifier.Quoting.QUOTED);
        when(refreshStatement.cubeName()).thenReturn(nameObjectIdentifier);
        QueryPart queryPart = queryProviderImpl.createQuery(refreshStatement);
        assertThat(queryPart).isNotNull().isInstanceOf(Refresh.class);
        Refresh refresh = (Refresh) queryPart;
        assertThat(refresh.getCubeName()).isEqualTo("[cube]");
        assertThat(refresh.getChildren()).isNotNull();
        assertThat(refresh.getChildren()).hasSize(1);
        assertThat(refresh.getChildren()[0]).isEqualTo("[cube]");
        refresh.unparse(printWriter);
        assertThat(stringWriter.toString()).isNotNull().isEqualTo("REFRESH CUBE [[cube]]");
    }

    @Test
    void refreshStatementToQueryUnquotedTest() {
        RefreshStatement refreshStatement = mock(RefreshStatement.class);
        NameObjectIdentifier nameObjectIdentifier = mock(NameObjectIdentifier.class);
        when(nameObjectIdentifier.name()).thenReturn("cube");
        when(nameObjectIdentifier.quoting()).thenReturn(ObjectIdentifier.Quoting.UNQUOTED);
        when(refreshStatement.cubeName()).thenReturn(nameObjectIdentifier);
        QueryPart queryPart = queryProviderImpl.createQuery(refreshStatement);
        assertThat(queryPart).isNotNull().isInstanceOf(Refresh.class);
        Refresh refresh = (Refresh) queryPart;
        assertThat(refresh.getCubeName()).isEqualTo("cube");
        assertThat(refresh.getChildren()).isNotNull();
        assertThat(refresh.getChildren()).hasSize(1);
        assertThat(refresh.getChildren()[0]).isEqualTo("cube");
        refresh.unparse(printWriter);
        assertThat(stringWriter.toString()).isNotNull().isEqualTo("REFRESH CUBE [cube]");
    }

}
