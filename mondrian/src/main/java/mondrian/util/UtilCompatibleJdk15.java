/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.util;

import java.lang.annotation.Annotation;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.Timer;
import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mondrian.olap.Util;
import mondrian.resource.MondrianResource;
import mondrian.rolap.RolapUtil;

// Only in Java5 and above

/**
 * Implementation of {@link UtilCompatible} which runs in
 * JDK 1.5.
 *
 * <p>Prior to JDK 1.5, this class should never be loaded. Applications should
 * instantiate this class via {@link Class#forName(String)} or better, use
 * methods in {@link mondrian.olap.Util}, and not instantiate it at all.
 *
 * @author jhyde
 * @since Feb 5, 2007
 */
public class UtilCompatibleJdk15 implements UtilCompatible {
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilCompatibleJdk15.class);

    /**
     * This generates a BigDecimal with a precision reflecting
     * the precision of the input double.
     *
     * @param d input double
     * @return BigDecimal
     */
    @Override
	public BigDecimal makeBigDecimalFromDouble(double d) {
        return BigDecimal.valueOf(d);
    }

    @Override
	public String quotePattern(String s) {
        return Pattern.quote(s);
    }

    @Override
	@SuppressWarnings("unchecked")
    public <T> T getAnnotation(
        Method method, String annotationClassName, T defaultValue)
    {
        try {
            Class<? extends Annotation> annotationClass =
                (Class<? extends Annotation>)
                    Class.forName(annotationClassName);
            if (method.isAnnotationPresent(annotationClass)) {
                final Annotation annotation =
                    method.getAnnotation(annotationClass);
                final Method method1 =
                    annotation.getClass().getMethod("value");
                return (T) method1.invoke(annotation);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            return defaultValue;
        }
        return defaultValue;
    }

    @Override
	public String generateUuidString() {
        return UUID.randomUUID().toString();
    }

    @Override
	public <T> T compileScript(
        Class<T> iface,
        String script,
        String engineName)
    {
        throw new UnsupportedOperationException(
            "Scripting not supported until Java 1.6");
    }

    @Override
	public <T> void threadLocalRemove(ThreadLocal<T> threadLocal) {
        threadLocal.remove();
    }

    @Override
	public Util.MemoryInfo getMemoryInfo() {
        return new Util.MemoryInfo() {
            protected static final MemoryPoolMXBean TENURED_POOL =
                findTenuredGenPool();

            @Override
			public Util.MemoryInfo.Usage get() {
                final MemoryUsage memoryUsage = TENURED_POOL.getUsage();
                return new Usage() {
                    @Override
					public long getUsed() {
                        return memoryUsage.getUsed();
                    }

                    @Override
					public long getCommitted() {
                        return memoryUsage.getCommitted();
                    }

                    @Override
					public long getMax() {
                        return memoryUsage.getMax();
                    }
                };
            }
        };
    }

    @Override
	public Timer newTimer(String name, boolean isDaemon) {
        return new Timer(name, isDaemon);
    }

    private static MemoryPoolMXBean findTenuredGenPool() {
        for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            if (pool.getType() == MemoryType.HEAP) {
                return pool;
            }
        }
        throw new AssertionError("Could not find tenured space");
    }

    @Override
	public void cancelStatement(Statement stmt) {
        try {
            stmt.cancel();
        } catch (Exception t) {
            // We can't call stmt.isClosed(); the method doesn't exist until
            // JDK 1.6. So, mask out the error.

            // Also, we MUST catch all throwables. Some drivers (ie. Hive)
            // will choke on canceled queries and throw a OutOfMemoryError.
            // We can't protect ourselves against this. That's a bug on their
            // side.

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("",
                    MondrianResource.instance()
                        .ExecutionStatementCleanupException
                            .ex(t.getMessage(), t),
                    t);
            }
        }
    }

    @Override
	public <T> Set<T> newIdentityHashSet() {
        return Util.newIdentityHashSetFake();
    }

    @Override
	public <T extends Comparable<T>> int binarySearch(
        T[] ts, int start, int end, T t)
    {
        final int i = Collections.binarySearch(
            Arrays.asList(ts).subList(start, end), t,
            RolapUtil.ROLAP_COMPARATOR);
        return (i < 0) ? (i - start) : (i + start);
    }
}
