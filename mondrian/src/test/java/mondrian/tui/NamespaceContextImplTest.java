/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2005-2017 Hitachi Vantara and others
// All Rights Reserved.
*/
package mondrian.tui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NamespaceContextImplTest {

    Map<String, String> namespaces = new HashMap<>();
    NamespaceContext namespaceContext;
    
    @BeforeEach
    public void setUp() {
        namespaces.put("FOO", "http://foo.bar.baz");
        namespaces.put("BAR", "http://foo.bar.baz");
        namespaces.put("BAZ", "http://schema.other");
        namespaces.put("BOP", "http://schema.other2");
        namespaceContext =  new NamespaceContextImpl(namespaces);
    }
    
    @Test
    void testGetNamespaceURI() throws Exception
    {
        assertEquals(
            "http://foo.bar.baz",
            namespaceContext.getNamespaceURI("FOO"));
        assertEquals(
            "http://foo.bar.baz",
            namespaceContext.getNamespaceURI("BAR"));
        assertEquals(
            "http://schema.other",
            namespaceContext.getNamespaceURI("BAZ"));
    }

    @Test
    void testGetPrefix() throws Exception {
        String prefix = namespaceContext.getPrefix("http://foo.bar.baz");
        // will arbitrarily get one when more than one prefix maps
        assertTrue(prefix.equals("FOO") || prefix.equals("BAR"));
    }

    @Test
    void testGetPrefixNullArg() throws Exception {
        try {
            namespaceContext.getPrefix(null);
            fail("expected exception");
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    void testGetPrefixes() throws Exception {
        Iterator iter = namespaceContext.getPrefixes("http://foo.bar.baz");
        assertTrue(iter.hasNext());
        List<String> list = new ArrayList<>();
        list.add((String)iter.next());
        list.add((String)iter.next());
        assertFalse(iter.hasNext());
        assertTrue(list.contains("FOO"));
        assertTrue(list.contains("BAR"));
    }

}