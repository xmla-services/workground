/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2009-2009 SQLstream, Inc.
// Copyright (C) 2009-2017 Hitachi Vantara
// All Rights Reserved.
*/

package mondrian.test.build;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Omnibus code compliance test to wrap various ant tasks that check the code
 * base, such checkFile, as macker, Javadoc, preambles, and so on.
 *
 * @author Chard Nelson
 * @since Sep 8, 2009
 */
@Disabled
//ant test was disabled
public class CodeComplianceTest
        extends AntTestBase
{

    /**
     * Checks source code file formatting.
     */
    @Test
    void testCodeFormatting()  throws Exception
    {
        runAntTest("checkCodeFormatting");
    }

    /**
     * Checks that javadoc can be generated without errors.
     */
    @Test
    void testJavadoc() throws Exception
    {
        runAntTest("checkJavadoc");
    }
}
