/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2002-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara and others
// All Rights Reserved.
//
// jhyde, 29 March, 2002
*/

package mondrian.xmla;

import mondrian.test.DiffRepository;

/**
 * Test suite for compatibility of Mondrian XMLA with Excel 2000.
 * Simba (the maker of the O2X bridge) supplied captured request/response
 * soap messages between Excel 2000 and SQL Server. These form the
 * basis of the output files in the  excel_2000 directory.
 *
 * @author Richard M. Emberson
 */
public class XmlaExcel2000Test extends XmlaBaseTestCase {

    public XmlaExcel2000Test() {
        super();
    }

    public XmlaExcel2000Test(String name) {
        super(name);
    }

    protected DiffRepository getDiffRepos() {
        return DiffRepository.lookup(XmlaExcel2000Test.class);
    }

    protected Class<? extends XmlaRequestCallback> getServletCallbackClass() {
        return Callback.class;
    }

    static class Callback extends XmlaRequestCallbackImpl {
        Callback() {
            super("XmlaExcel2000Test");
        }
    }

    void test01() {
        helperTest(false);
    }

    // BeginSession
    void test02() {
        helperTest(false);
    }

    void test03() {
        helperTest(true);
    }

    void test04() {
        helperTest(true);
    }

    void test05() {
        helperTest(true);
    }

    void test06() {
        helperTest(true);
    }

    // BeginSession
    void test07() {
        helperTest(false);
    }

    void test08() {
        helperTest(true);
    }

    void test09() {
        helperTest(true);
    }

    void test10() {
        helperTest(true);
    }

    void test11() {
        helperTest(true);
    }

    void test12() {
        helperTest(true);
    }

    void testMdschemaMeasures() {
        helperTest(true);
    }

    void testMdschemaMeasuresEmitInvisible() {
        helperTest(true);
    }

    void test14() {
        helperTest(true);
    }

    void test15() {
        helperTest(true);
    }

    void test16() {
        helperTest(true);
    }
    void test17() {
        helperTest(true);
    }

    void test18() {
        helperTest(true);
    }

    void testExpect01() {
        helperTestExpect(false);
    }

    void testExpect02() {
        helperTestExpect(false);
    }

    void testExpect03() {
        helperTestExpect(true);
    }

    void testExpect04() {
        helperTestExpect(true);
    }

    void testExpect05() {
        helperTestExpect(true);
    }

    void testExpect06() {
        helperTestExpect(true);
    }

    /////////////////////////////////////////////////////////////////////////
    // helpers
    /////////////////////////////////////////////////////////////////////////

    protected String getSessionId(Action action) {
        return getSessionId("XmlaExcel2000Test", action);
    }
}
