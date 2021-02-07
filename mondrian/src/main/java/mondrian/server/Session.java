/*
 * This software is subject to the terms of the Eclipse Public License v1.0
 * Agreement, available at the following URL:
 * http://www.eclipse.org/legal/epl-v10.html.
 * You must accept the terms of that agreement to use this software.
 *
 * Copyright (c) 2021 Sergei Semenkov.  All rights reserved.
 */

package mondrian.server;

import java.util.*;

import org.apache.log4j.Logger;

import org.olap4j.OlapException;

import mondrian.rolap.*;

public class Session
{
    private static final Logger LOGGER = Logger.getLogger(Session.class);
    final static Map<String, Session> sessions = new HashMap<String, Session>();

    static java.util.Timer timer = new Timer(true);
    static java.util.TimerTask timerTask = new java.util.TimerTask() {
        public void run() {
            List<String> toRemove = new ArrayList<String>();
            for(Map.Entry<String, Session> entry : sessions.entrySet()) {
                Session session = entry.getValue();
                java.time.Duration duration = java.time.Duration.between(
                        session.checkInTime,
                        java.time.LocalDateTime.now());
                if(duration.getSeconds() >
                        60*60) {
//                        mondrian.olap.MondrianProperties.instance().IdleOrphanSessionTimeout.get()) {
                    toRemove.add(entry.getKey());
                }
            }
            for(String sessionId : toRemove) {
                closeInternal(sessionId);
            }
        }
    };
    static
    {
        timer.scheduleAtFixedRate(timerTask, 0, 60*1000);
    }

    Session(String sessionId)
    {
    }
    public static Session create(String sessionId) throws OlapException
    {
        if(sessions.containsKey(sessionId)) {
            throw new OlapException("Session with id \"" + sessionId + "\" already exists.");
        }

        Session session = new Session(sessionId);

        sessions.put(sessionId, session);
        session.checkInTime = java.time.LocalDateTime.now();

        return session;
    }

    public static Session get(String sessionId) throws OlapException
    {
        if(!sessions.containsKey(sessionId)) {
            throw new OlapException("Session with id \"" + sessionId + "\" does not exist");
        }
        return sessions.get(sessionId);
    }

    java.time.LocalDateTime checkInTime = null;

    public static void checkIn(String sessionId) throws OlapException
    {
        Session session = get(sessionId);
        session.checkInTime = java.time.LocalDateTime.now();
    }

    static void closeInternal(String sessionId)
    {
        List<RolapSchema> rolapSchemas = RolapSchemaPool.instance().getRolapSchemas();
        for(RolapSchema rolapSchema: rolapSchemas) {
            final String rolapSchemaSessionId = rolapSchema.getInternalConnection().getConnectInfo().get("sessionId");
            if(sessionId.equals(rolapSchemaSessionId)) {
                RolapSchemaPool.instance().remove(rolapSchema);
            }
        }

        sessions.remove(sessionId);
    }

    public static void close(String sessionId) throws OlapException
    {
        Session session = Session.get(sessionId);

        closeInternal(sessionId);
    }

}
