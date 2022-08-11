/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2021 Hitachi Vantara and others
// All Rights Reserved.
//
*/
package mondrian.test;

/**
 * TestAppender captures log4j events for unit testing.
 * 
 * @author benny
 */
//public class TestAppender extends AbstractAppender {
public class TestAppender {
  //private final List<LogEvent> logEvents = new ArrayList<>();

  //TODO need slf4j implementation
  public TestAppender() {
    //super( "TestAppender", null, PatternLayout.createDefaultLayout(), true, Property.EMPTY_ARRAY );
  }

  /*
  @Override
  public void append( final LogEvent loggingEvent ) {
    logEvents.add( loggingEvent.toImmutable() );
  }

  public List<LogEvent> getLogEvents() {
    return logEvents;
  }

  public void clear() {
    logEvents.clear();
  }

   */
}

// End TestAppender.java
