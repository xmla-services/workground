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

import org.eigenbase.util.property.BooleanProperty;
import org.eigenbase.util.property.DoubleProperty;
import org.eigenbase.util.property.IntegerProperty;
import org.eigenbase.util.property.StringProperty;

import java.io.File;

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
     * <p>String property that is the AggRule element's tag value.</p>
     *
     * <p>Normally, this property is not set by a user.</p>
     */
    public transient final StringProperty AggregateRuleTag =
        new StringProperty(
            this, "mondrian.rolap.aggregates.rule.tag", "default");

    /**
     * <p>String property containing the name of the file which defines the
     * rules for recognizing an aggregate table. Can be either a resource in
     * the Mondrian jar or a URL.</p>
     *
     * <p>The default value is "/DefaultRules.xml", which is in the
     * mondrian.rolap.aggmatcher package in Mondrian.jar.</p>
     *
     * <p>Normally, this property is not set by a user.</p>
     */
    public transient final StringProperty AggregateRules =
        new StringProperty(
            this, "mondrian.rolap.aggregates.rules", "/DefaultRules.xml");

    /**
     * <p>Alerting action to take in case native evaluation of a function is
     * enabled but not supported for that function's usage in a particular
     * query.  (No alert is ever raised in cases where native evaluation would
     * definitely have been wasted effort.)</p>
     *
     * <p>Recognized actions:</p>
     *
     * <ul>
     * <li><code>OFF</code>:  do nothing (default action, also used if
     * unrecognized action is specified)</li>
     * <li><code>WARN</code>:  log a warning to RolapUtil logger</li>
     * <li><code>ERROR</code>:  throw an instance of
     * {@link NativeEvaluationUnsupportedException}</li>
     * </ul>
     */
    public transient final StringProperty AlertNativeEvaluationUnsupported =
        new StringProperty(
            this, "mondrian.native.unsupported.alert", "OFF");

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
     * <p>Positive integer property that determines loop iterations number between checks for whether the current mdx query has been cancelled or timeout was exceeded.<br></br>
     * Setting the interval too small may result in a performance degradation when reading large result sets;
     * setting it too high can cause a big delay between the query being marked as cancelled or timeout was exceeded and system resources associated to it being released.
     * </p>
     */
    public transient final IntegerProperty CheckCancelOrTimeoutInterval =
        new IntegerProperty(
            this, "mondrian.util.checkCancelOrTimeoutInterval", 1000);

    /**
     * Boolean property that controls whether sibling members are
     * compared according to order key value fetched from their ordinal
     * expression.  The default is false (only database ORDER BY is used).
     */
    public transient final BooleanProperty CompareSiblingsByOrderKey =
        new BooleanProperty(
            this, "mondrian.rolap.compareSiblingsByOrderKey", false);

    /**
     * <p>Property that defines
     * when to apply the crossjoin optimization algorithm.</p>
     *
     * <p>If a crossjoin input list's size is larger than this property's
     * value and the axis has the "NON EMPTY" qualifier, then
     * the crossjoin non-empty optimizer is applied.
     * Setting this value to '0' means that for all crossjoin
     * input lists in non-empty axes will have the optimizer applied.
     * On the other hand, if the value is set larger than any possible
     * list, say <code>Integer.MAX_VALUE</code>, then the optimizer
     * will never be applied.</p>
     */
    public transient final IntegerProperty CrossJoinOptimizerSize =
        new IntegerProperty(
            this, "mondrian.olap.fun.crossjoin.optimizer.size", 0);

    /**
     * <p>Alerting action to take when a CurrentMember function is applied to
     *                 a dimension that is also a compound slicer</p>
     *
     *             <p>Recognized actions:</p>
     *
     *             <ul>
     *                 <li><code>OFF</code>:  do nothing</li>
     *                 <li><code>WARN</code>:  log a warning</li>
     *                 <li><code>ERROR</code>:  throw an CurrentMemberWithCompoundSlicer
     *                     MondrianException</li>
     *             </ul>
     */
    public transient final StringProperty CurrentMemberWithCompoundSlicerAlert =
        new StringProperty(
            this, "mondrian.olap.fun.currentmemberwithcompoundslicer.alert", "ERROR");


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
     * If enabled, first row in the result of an XML/A drill-through request
     * will be filled with the total count of rows in underlying database.
     */
    public transient final BooleanProperty EnableTotalCount =
        new BooleanProperty(
            this, "mondrian.xmla.drillthroughTotalCount.enable", true);

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
     * <p>Boolean property that controls pretty-print mode.</p>
     *
     * <p>If true, the all SqlQuery SQL strings will be generated in
     * pretty-print mode, formatted for ease of reading.</p>
     */
    public transient final BooleanProperty GenerateFormattedSql =
        new BooleanProperty(
            this, "mondrian.rolap.generate.formatted.sql", false);

    /**
     * <p>Property that defines whether non-existent member errors should be
     * ignored during schema load. If so, the non-existent member is treated
     * as a null member.</p>
     */
    public transient final BooleanProperty IgnoreInvalidMembers =
        new BooleanProperty(
            this, "mondrian.rolap.ignoreInvalidMembers", false);

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
     * <p>Boolean property that defines the maximum number of passes
     * allowable while evaluating an MDX expression.</p>
     *
     * <p>If evaluation exceeds this depth (for example, while evaluating a
     * very complex calculated member), Mondrian will throw an error.</p>
     */
    public transient final IntegerProperty MaxEvalDepth =
        new IntegerProperty(
            this, "mondrian.rolap.evaluate.MaxEvalDepth", 10);

    /**
     * <p>Property that defines whether the <code>MemoryMonitor</code> should
     * be enabled. By default it is disabled; memory monitor is not available
     * before Java version 1.5.</p>
     */
    public transient final BooleanProperty MemoryMonitor =
        new BooleanProperty(
            this, "mondrian.util.memoryMonitor.enable", false);

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
     * <p>Property that defines the default <code>MemoryMonitor</code>
     * percentage threshold. If enabled, when Java's memory monitor detects
     * that post-garbage collection is above this value, notifications are
     * generated.</p>
     */
    public transient final IntegerProperty MemoryMonitorThreshold =
        new IntegerProperty(
            this, "mondrian.util.memoryMonitor.percentage.threshold", 90);

    /**
     * <p>Property that controls the maximum number of results contained in a
     * NativizeSet result set.</p>
     *
     * <p>If the number of tuples contained in the result set exceeds this
     * value Mondrian throws a LimitExceededDuringCrossjoin error.</p>
     */
    public transient final IntegerProperty NativizeMaxResults =
        new IntegerProperty(
            this, "mondrian.native.NativizeMaxResults", 150000);

    /**
     * <p>Property that controls minimum expected cardinality required in
     * order for NativizeSet to natively evaluate a query.</p>
     *
     * <p>If the expected cardinality falls below this level the query is
     * executed non-natively.</p>
     *
     * <p>It is possible for the actual cardinality to fall below this
     * threshold even though the expected cardinality falls above this
     * threshold.  In this case the query will be natively evaluated.</p>
     */
    public transient final IntegerProperty NativizeMinThreshold =
        new IntegerProperty(
            this, "mondrian.native.NativizeMinThreshold", 100000);

    /**
     * <p>Property determines if elements of dimension (levels, hierarchies,
     * members) need to be prefixed with dimension name in MDX query.</p>
     *
     * <p>For example when the property is true, the following queries
     * will error out. The same queries will work when this property
     * is set to false.</p>
     *
     * <blockquote><code>select {[M]} on 0 from sales<br></br>
     * select {[USA]} on 0 from sales<br></br>
     * select {[USA].[CA].[Santa Monica]}  on 0 from sales</code></blockquote>
     *
     * <p>When the property is set to true, any query where elements are
     * prefixed with dimension name as below will work</p>
     *
     * <blockquote><code>select {[Gender].[F]} on 0 from sales<br></br>
     * select {[Customers].[Santa Monica]} on 0 from sales</code></blockquote>
     *
     * <p>Please note that this property does not govern the behaviour
     * wherein</p>
     *
     * <blockquote><code>[Gender].[M]</code></blockquote>
     *
     * <p>is resolved into a fully qualified</p>
     *
     * <blockquote><code>[Gender].[M]</code></blockquote>
     *
     * <p> In a scenario where the schema is very large and dimensions have
     * large number of members a MDX query that has a invalid member in it will
     * cause mondrian to to go through all the dimensions, levels, hierarchies,
     * members and properties trying to resolve the element name. This behavior
     * consumes considerable time and resources on the server. Setting this
     * property to true will make it fail fast in a scenario where it is
     * desirable.</p>
     */
    public transient final BooleanProperty NeedDimensionPrefix =
        new BooleanProperty(
            this, "mondrian.olap.elements.NeedDimensionPrefix", false);

    /**
     * <p>Property that defines
     * the behavior of division if the denominator evaluates to zero.</p>
     *
     * <p>If false (the default), if a division has a non-null numerator and
     * a null denominator, it evaluates to "Infinity", which conforms to SSAS
     * behavior.</p>
     *
     * <p>If true, the result is null if the denominator is null. Setting to
     * true enables the old semantics of evaluating this to null; this does
     * not conform to SSAS, but is useful in some applications.</p>
     */
    public transient final BooleanProperty NullDenominatorProducesNull =
        new BooleanProperty(
            this, "mondrian.olap.NullDenominatorProducesNull", false);

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
     * <p>Boolean property that determines whether Mondrian optimizes predicates.</p>
     *
     * <p>If true, Mondrian may retrieve a little more data than specified in
     * MDX query and cache it for future use.  For example, if you ask for
     * data on 48 states of the United States for 3 quarters of 2011,
     * Mondrian will round out to all 50 states and all 4 quarters.  If
     * false, Mondrian still optimizes queries that involve all members of a
     * dimension.</p>
     */
    public transient final BooleanProperty OptimizePredicates =
        new BooleanProperty(
            this, "mondrian.rolap.aggregates.optimizePredicates", true);

    /**
     * <p>Maximum number of simultaneous queries the system will allow.</p>
     *
     * <p>Oracle fails if you try to run more than the 'processes' parameter in
     * init.ora, typically 150. The throughput of Oracle and other databases
     * will probably reduce long before you get to their limit.</p>
     */
    public transient final IntegerProperty QueryLimit =
        new IntegerProperty(
            this, "mondrian.query.limit", 40);

    /**
     * <p>Property that defines the timeout value (in seconds) for queries. A
     * value of 0 (the default) indicates no timeout.</p>
     */
    public transient final IntegerProperty QueryTimeout =
        new IntegerProperty(
            this, "mondrian.rolap.queryTimeout", 0);

    /**
     * <p>Boolean property that determines whether Mondrian should read
     * aggregate tables.</p>
     *
     * <p>If set to true, then Mondrian scans the database for aggregate tables.
     * Unless mondrian.rolap.aggregates.Use is set to true, the aggregates
     * found will not be used.</p>
     */
    public transient final BooleanProperty ReadAggregates =
        new BooleanProperty(
            this, "mondrian.rolap.aggregates.Read", false);

    /**
     * Integer property that, if set to a value greater than zero, limits the
     * maximum size of a result set.
     */
    public transient final IntegerProperty ResultLimit =
        new IntegerProperty(
            this, "mondrian.result.limit", 0);


     /**
     * <p>Property that, with {@link #SparseSegmentDensityThreshold}, determines
     * whether to choose a sparse or dense representation when storing
     * collections of cell values in memory.</p>
     *
     * <p>When storing collections of cell values, Mondrian has to choose
     * between a sparse and a dense representation, based upon the
     * <code>possible</code> and <code>actual</code> number of values.
     * The <code>density</code> is <code>actual / possible</code>.</p>
     *
     * <p>We use a sparse representation if
     * <code>(possible -
     * {@link #SparseSegmentCountThreshold countThreshold}) *
     * {@link #SparseSegmentDensityThreshold densityThreshold} &gt;
     * actual</code></p>
     *
     * <p>For example, at the default values
     * ({@link #SparseSegmentCountThreshold countThreshold} = 1000,
     * {@link #SparseSegmentDensityThreshold} = 0.5),
     * we use a dense representation for</p>
     *
     * <ul>
     * <li>(1000 possible, 0 actual), or</li>
     * <li>(2000 possible, 500 actual), or</li>
     * <li>(3000 possible, 1000 actual).</li>
     * </ul>
     *
     * <p>Any fewer actual values, or any more
     * possible values, and Mondrian will use a sparse representation.</p>
     */
    public transient final IntegerProperty SparseSegmentCountThreshold =
        new IntegerProperty(
            this, "mondrian.rolap.SparseSegmentValueThreshold", 1000);

    /**
     * Property that, with {@link #SparseSegmentCountThreshold},
     * determines whether to choose a sparse or dense representation when
     * storing collections of cell values in memory.
     */
    public transient final DoubleProperty SparseSegmentDensityThreshold =
        new DoubleProperty(
            this, "mondrian.rolap.SparseSegmentDensityThreshold", 0.5);

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

    /**
     * <p>Integer property that controls whether to test operators'
     * dependencies, and how much time to spend doing it.</p>
     *
     * <p>If this property is positive, Mondrian's test framework allocates an
     * expression evaluator which evaluates each expression several times, and
     * makes sure that the results of the expression are independent of
     * dimensions which the expression claims to be independent of.</p>
     *
     * <p>The default is 0.</p>
     */
    public transient final IntegerProperty TestExpDependencies =
        new IntegerProperty(
            this, "mondrian.test.ExpDependencies", 0);

    /**
     * <p>Boolean property that controls whether Mondrian uses aggregate
     * tables.</p>
     *
     * <p>If true, then Mondrian uses aggregate tables. This property is
     * queried prior to each aggregate query so that changing the value of this
     * property dynamically (not just at startup) is meaningful.</p>
     *
     * <p>Aggregates can be read from the database using the
     * {@link #ReadAggregates} property but will not be used unless this
     * property is set to true.</p>
     */
    public transient final BooleanProperty UseAggregates =
        new BooleanProperty(
            this, "mondrian.rolap.aggregates.Use", false);

    /**
     * <p>Property that controls whether warning messages should be printed if a SQL
     * comparison test does not contain expected SQL statements for the specified
     * dialect. The tests are skipped if no expected SQL statements are
     * found for the current dialect.</p>
     *
     * <p>Possible values are the following:</p>
     *
     * <ul>
     * <li>"NONE": no warning (default)</li>
     * <li>"ANY": any dialect</li>
     * <li>"ACCESS"</li>
     * <li>"DERBY"</li>
     * <li>"LUCIDDB"</li>
     * <li>"MYSQL"</li>
     * <li>... and any Dialect enum in SqlPattern.Dialect</li>
     * </ul>
     *
     * <p>Specific tests can overwrite the default setting. The priority is:<ul>
     * <li>Settings besides "ANY" in mondrian.properties file</li>
     * <li>&lt; Any setting in the test</li>
     * <li>&lt; "ANY"</li>
     * </ul>
     * </p>
     */
    public transient final StringProperty WarnIfNoPatternForDialect =
        new StringProperty(
            this, "mondrian.test.WarnIfNoPatternForDialect", "NONE");

}

// End MondrianProperties.java
