/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2003-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara
// All Rights Reserved.
*/
package mondrian.rolap.agg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import mondrian.recorder.MessageRecorder;
import mondrian.rolap.RolapStar;
import mondrian.rolap.aggmatcher.AggStar;
import mondrian.rolap.aggmatcher.JdbcSchema;
import mondrian.test.PropertySaver5;

class AggStarTest {

  private AggStar aggStar;
  private RolapStar star;
  private JdbcSchema.Table table;
  private MessageRecorder messageRecorder;
  private static final Long BIG_NUMBER = Integer.MAX_VALUE + 1L;

  private PropertySaver5 propSaver;

  @BeforeEach
  public void beforeEach() {
    propSaver = new PropertySaver5();
    star = mock(RolapStar.class);
    table = mock(JdbcSchema.Table.class);
    messageRecorder = mock(MessageRecorder.class);

    Mockito.when(table.getColumnUsages(Mockito.any())).thenCallRealMethod();
    Mockito.when(table.getName()).thenReturn("TestAggTable");
    Mockito.when(table.getTotalColumnSize()).thenReturn(1);
    Mockito.when(table.getNumberOfRows()).thenReturn(BIG_NUMBER);

    aggStar = AggStar.makeAggStar(star, table, messageRecorder, 0L);
    aggStar = Mockito.spy(aggStar);

    propSaver.set(propSaver.properties.ChooseAggregateByVolume, false);

  }

  @AfterEach
  public void afterEach() {
    propSaver.reset();
  }


  @Test
  void testSizeIntegerOverflow() {
    assertEquals(BIG_NUMBER.longValue(), aggStar.getSize());
  }

  @Test
  void testVolumeIntegerOverflow() {
    propSaver.set(propSaver.properties.ChooseAggregateByVolume, true);
    assertEquals(BIG_NUMBER.longValue(), aggStar.getSize());
  }
}
