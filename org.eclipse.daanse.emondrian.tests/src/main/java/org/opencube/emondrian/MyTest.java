package org.opencube.emondrian;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencube.junit5.MondrianRuntimeExtension;

@ExtendWith(MondrianRuntimeExtension.class)
public class MyTest {

    @Test
    void testName() throws Exception {
	System.out.println(1);
    }
}