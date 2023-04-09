/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.eclipse.daanse.olap.api.Connection;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.context.TestingContext;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import mondrian.tui.CmdRunner;

/**
 * Unit test for {@link CmdRunner}.
 *
 * @author jhyde
 * @since Jun 2, 2006
 */
class CmdRunnerTest {
    public Connection connection;
    protected DiffRepository getDiffRepos() {
        return DiffRepository.lookup(CmdRunnerTest.class);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testQuery(TestingContext context) throws IOException {
        connection = context.createConnection();
        doTest();
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void test7731(TestingContext context) throws IOException {
        connection = context.createConnection();
        doTest();
    }
    protected void doTest() {
        final DiffRepository diffRepos = getDiffRepos();
        String input = diffRepos.expand("input", "${input}");
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        final CmdRunnerTrojan cmdRunner = new CmdRunnerTrojan(null, pw);
        cmdRunner.commandLoop(new StringReader(input), false);
        pw.flush();
        String output = sw.toString();
        diffRepos.assertEquals("output", "${output}", output);
    }

    private class CmdRunnerTrojan extends CmdRunner {
        public CmdRunnerTrojan(Options options, PrintWriter out) {
            super(options, out);
        }

        @Override
		public void commandLoop(Reader in, boolean interactive) {
            super.commandLoop(in, interactive);
        }

        @Override
		public Connection getConnection() {
            return CmdRunnerTest.this.connection;
        }
    }
}
