/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package mondrian.olap;

import java.io.File;

import org.eigenbase.util.property.BooleanProperty;
import org.eigenbase.util.property.IntegerProperty;
import org.eigenbase.util.property.StringProperty;

/**
 * Configuration properties that determine the
 * behavior of a mondrian instance.
 *
 * <p>There is a method for property valid in a
 * <code>mondrian.properties</code> file. Although it is possible to retrieve
 * properties using the inherited {@link java.util.Properties#getProperty(String)}
 * method, we recommend that you use methods in this class.</p>
 */
public class MondrianProperties extends MondrianPropertiesBase {
    /**
     * Properties, drawn from {@link System#getProperties},
     * plus the contents of "mondrian.properties" if it
     * exists. A singleton.
     */
    private static final MondrianProperties instance =
        new MondrianProperties();

    private MondrianProperties() {
        super(
            new FilePropertySource(
                new File(MONDRIAN_DOT_PROPERTIES)));
        populate();
    }

    /**
     * Returns the singleton.
     *
     * @return Singleton instance
     */
    public static MondrianProperties instance() {
        // NOTE: We used to instantiate on demand, but
        // synchronization overhead was significant. See
        // MONDRIAN-978.
        return instance;
    }



    /**
     * Boolean property that controls whether the MDX parser resolves uses
     * case-sensitive matching when looking up identifiers. The default is
     * false.
     */
    public transient final BooleanProperty CaseSensitive =
        new BooleanProperty(
            this, "mondrian.olap.case.sensitive", false);

    /**
     * <p>
     *                 If true, then MDX functions InStr and InStrRev are case sensitive.
     *                 Default value is false.
     *             </p>
     */
    public transient final BooleanProperty CaseSensitiveMdxInstr =
        new BooleanProperty(
            this, "mondrian.olap.case.sensitive.CaseSensitiveMdxInstr", false);

    /**
     * Integer property that, if set to a value greater than zero, sets a hard limit on the
     * number of cells that are batched together when building segments.
     */
    //public transient final IntegerProperty CellBatchSize =
    //    new IntegerProperty(
    //        this, "mondrian.rolap.cellBatchSize", -1);


    /**
     * Boolean property that controls whether sibling members are
     * compared according to order key value fetched from their ordinal
     * expression.  The default is false (only database ORDER BY is used).
     */
    public transient final BooleanProperty CompareSiblingsByOrderKey =
        new BooleanProperty(
            this, "mondrian.rolap.compareSiblingsByOrderKey", false);

    /**
     * Boolean property that controls whether to use a cache for frequently
     * evaluated expressions. With the cache disabled, an expression like
     * <code>Rank([Product].CurrentMember,
     * Order([Product].MEMBERS, [Measures].[Unit Sales]))</code> would perform
     * many redundant sorts. The default is true.
     */
    public transient final BooleanProperty EnableExpCache =
        new BooleanProperty(
            this, "mondrian.expCache.enable", true);

    /**
     * <p>If enabled some NON EMPTY set operations like member.children,
     * level.members and member descendants will be computed in SQL.</p>
     */
    public transient final BooleanProperty EnableNativeNonEmpty =
        new BooleanProperty(
            this, "mondrian.native.nonempty.enable", true);
    /**
     * Boolean property that controls whether each query axis implicit has the
     * NON EMPTY option set. The default is false.
     */
    public transient final BooleanProperty EnableNonEmptyOnAllAxis =
        new BooleanProperty(
            this, "mondrian.rolap.nonempty", false);

    /**
     * <p>Property that determines whether to cache RolapCubeMember objects,
     * each of which associates a member of a shared hierarchy with a
     * particular cube in which it is being used.</p>
     *
     * <p>The default is {@code true}, that is, use a cache. If you wish to use
     * the member cache control aspects of {@link mondrian.olap.CacheControl},
     * you must set this property to {@code false}.</p>
     *
     * <p>RolapCubeMember has recently become more lightweight to
     * construct, and we may obsolete this cache and this
     * property.</p>
     */
    public transient final BooleanProperty EnableRolapCubeMemberCache =
        new BooleanProperty(
            this, "mondrian.rolap.EnableRolapCubeMemberCache", true);


    /**
     * <p>Boolean property that controls whether to notify the Mondrian system
     * when a {@link MondrianProperties property value} changes.</p>
     *
     * <p>This allows objects dependent on Mondrian properties to react (that
     * is, reload), when a given property changes via, say,
     * <code>MondrianProperties.instance().populate(null)</code> or
     * <code>MondrianProperties.instance().QueryLimit.set(50)</code>.</p>
     */
    public transient final BooleanProperty EnableTriggers =
        new BooleanProperty(
            this, "mondrian.olap.triggers.enable", true);

    /**
     * <p>Property that defines how many previous execution instances the
     * <code>Monitor</code> keeps in its history so that it can send the events
     * which happen after the fact. Setting this property too high will make the
     * JVM run out of memory. Setting it too low might prevent some events from
     * reaching the listeners of the monitor.</p>
     * <p>This property is for internal use only and should not be changed
     * unless required. Defaults to 1,000.</p>
     */
    public transient final IntegerProperty ExecutionHistorySize =
        new IntegerProperty(
            this, "mondrian.server.monitor.executionHistorySize", 1000);

    /**
     * <p>Property that defines
     * whether to generate joins to filter out members in a snowflake
     * dimension that do not have any children.</p>
     *
     * <p>If true (the default), some queries to query members of high
     * levels snowflake dimensions will be more expensive. If false, and if
     * there are rows in an outer snowflake table that are not referenced by
     * a row in an inner snowflake table, then some queries will return members
     * that have no children.</p>
     *
     * <p>Our recommendation, for best performance, is to remove rows outer
     * snowflake tables are not referenced by any row in an inner snowflake
     * table, during your ETL process, and to set this property to
     * {@code false}.</p>
     */
    public transient final BooleanProperty FilterChildlessSnowflakeMembers =
        new BooleanProperty(
            this, "mondrian.rolap.FilterChildlessSnowflakeMembers", true);

    /**
     * <p>Max number of constraints in a single 'IN' SQL clause.</p>
     *
     * <p>This value may be variant among database products and their runtime
     * settings. Oracle, for example, gives the error "ORA-01795: maximum
     * number of expressions in a list is 1000".</p>
     *
     * <p>Recommended values:</p>
     * <ul>
     * <li>Oracle: 1,000</li>
     * <li>DB2: 2,500</li>
     * <li>Other: 10,000</li>
     * </ul>
     */
    public transient final IntegerProperty MaxConstraints =
        new IntegerProperty(
            this, "mondrian.rolap.maxConstraints", 1000);

    /**
     * <p>Property that defines
     * the name of the class used as a memory monitor.</p>
     *
     * <p>If the value is
     * non-null, it is used by the <code>MemoryMonitorFactory</code>
     * to create the implementation.</p>
     */
    public transient final StringProperty MemoryMonitorClass =
        new StringProperty(
            this, "mondrian.util.MemoryMonitor.class", null);

    /**
     * <p>Property that determines how a null member value is represented in the
     * result output.</p>
     * <p>AS 2000 shows this as empty value</p>
     * <p>AS 2005 shows this as "(null)" value</p>
     */
    public transient final StringProperty NullMemberRepresentation =
        new StringProperty(
            this, "mondrian.olap.NullMemberRepresentation", "#null");



    /**
     * Integer property that, if set to a value greater than zero, limits the
     * maximum size of a result set.
     */
    public transient final IntegerProperty ResultLimit =
        new IntegerProperty(
            this, "mondrian.result.limit", 0);

    /**
     * <p>Property that defines the name of the class used in SqlMemberSource
     * to pool common values.</p>
     *
     * <p>If the value is non-null, it is used by the
     * <code>SqlMemberSource.ValueMapFactory</code>
     * to create the implementation.  If it is not set, then
     * {@link mondrian.rolap.SqlMemberSource.NullValuePoolFactory}
     * will be used, meaning common values will not be pooled.</p>
     */
    public transient final StringProperty SqlMemberSourceValuePoolFactoryClass =
        new StringProperty(
            this, "mondrian.rolap.SqlMemberSource.ValuePoolFactory.class", null);

    /**
     * <p>Property that defines
     * whether to enable new naming behavior.</p>
     *
     * <p>If true, hierarchies are named [Dimension].[Hierarchy]; if false,
     * [Dimension.Hierarchy].</p>
     */
    public transient final BooleanProperty SsasCompatibleNaming =
        new BooleanProperty(
            this, "mondrian.olap.SsasCompatibleNaming", false);

}

// End MondrianProperties.java
