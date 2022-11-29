package org.eclipse.daanse.engine.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PidTest {

    @Test
    public void testPidNotChanged() throws Exception {
        assertThat(BasicContext.class.getName()).isSameAs(BasicContext.PID);
    }
}
