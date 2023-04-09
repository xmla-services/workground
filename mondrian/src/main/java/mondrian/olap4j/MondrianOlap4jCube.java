/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
* Copyright (c) 2021 Sergei Semenkov
*/

package mondrian.olap4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.eclipse.daanse.olap.api.access.Role;
import org.eclipse.daanse.olap.api.model.OlapElement;
import org.olap4j.OlapException;
import org.olap4j.impl.Named;
import org.olap4j.impl.NamedListImpl;
import org.olap4j.impl.Olap4jUtil;
import org.olap4j.mdx.IdentifierSegment;
import org.olap4j.metadata.Cube;
import org.olap4j.metadata.Dimension;
import org.olap4j.metadata.Hierarchy;
import org.olap4j.metadata.Measure;
import org.olap4j.metadata.Member;
import org.olap4j.metadata.NamedList;
import org.olap4j.metadata.NamedSet;
import org.olap4j.metadata.Schema;

import mondrian.olap.SchemaReader;
import mondrian.olap.Util;

/**
 * Implementation of {@link Cube}
 * for the Mondrian OLAP engine.
 *
 * @author jhyde
 * @since May 24, 2007
 */
class MondrianOlap4jCube
    extends MondrianOlap4jMetadataElement
    implements Cube, Named
{
    final org.eclipse.daanse.olap.api.model.Cube cube;
    final MondrianOlap4jSchema olap4jSchema;

    MondrianOlap4jCube(
        org.eclipse.daanse.olap.api.model.Cube cube,
        MondrianOlap4jSchema olap4jSchema)
    {
        this.cube = cube;
        this.olap4jSchema = olap4jSchema;
    }

    @Override
	public Schema getSchema() {
        return olap4jSchema;
    }

    @Override
	public int hashCode() {
        return olap4jSchema.hashCode()
            ^ cube.hashCode();
    }

    @Override
	public boolean equals(Object obj) {
        if (obj instanceof MondrianOlap4jCube that) {
            return this.olap4jSchema == that.olap4jSchema
                && this.cube.equals(that.cube);
        }
        return false;
    }

    @Override
	public NamedList<Dimension> getDimensions() {
        NamedList<MondrianOlap4jDimension> list =
            new NamedListImpl<>();
        final MondrianOlap4jConnection olap4jConnection =
            olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData.olap4jConnection;
        final mondrian.olap.SchemaReader schemaReader =
            olap4jConnection.getMondrianConnection2().getSchemaReader()
            .withLocus();
        for (org.eclipse.daanse.olap.api.model.Dimension dimension
            : schemaReader.getCubeDimensions(cube))
        {
            list.add(
                new MondrianOlap4jDimension(
                    olap4jSchema, dimension));
        }
        return Olap4jUtil.cast(list);
    }

    @Override
	public NamedList<Hierarchy> getHierarchies() {
        NamedList<MondrianOlap4jHierarchy> list =
            new NamedListImpl<>();
        final MondrianOlap4jConnection olap4jConnection =
            olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData.olap4jConnection;
        final mondrian.olap.SchemaReader schemaReader =
            olap4jConnection.getMondrianConnection2().getSchemaReader()
            .withLocus();
        for (org.eclipse.daanse.olap.api.model.Dimension dimension
            : schemaReader.getCubeDimensions(cube))
        {
            for (org.eclipse.daanse.olap.api.model.Hierarchy hierarchy
                : schemaReader.getDimensionHierarchies(dimension))
            {
                list.add(
                    new MondrianOlap4jHierarchy(
                        olap4jSchema, hierarchy));
            }
        }
        return Olap4jUtil.cast(list);
    }

    @Override
	public List<Measure> getMeasures() {
        final Dimension dimension = getDimensions().get("Measures");
        if (dimension == null) {
            return Collections.emptyList();
        }
        final MondrianOlap4jConnection olap4jConnection =
            olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData.olap4jConnection;
        try {
            final mondrian.olap.SchemaReader schemaReader =
                olap4jConnection.getMondrianConnection().getSchemaReader()
                .withLocus();
            final MondrianOlap4jLevel measuresLevel =
                (MondrianOlap4jLevel)
                    dimension.getDefaultHierarchy()
                        .getLevels().get(0);
            final List<Measure> measures =
                new ArrayList<>();
            List<org.eclipse.daanse.olap.api.model.Member> levelMembers = cube.getMeasures();
            for (org.eclipse.daanse.olap.api.model.Member member : levelMembers) {
                // This corrects MONDRIAN-1123, a ClassCastException (see below)
                // that occurs when you create a calculated member on a
                // dimension other than Measures:
                // java.lang.ClassCastException:
                // mondrian.olap4j.MondrianOlap4jMember cannot be cast to
                // org.olap4j.metadata.Measure
                MondrianOlap4jMember olap4jMember = olap4jConnection.toOlap4j(
                    member);
                if (olap4jMember instanceof Measure) {
                    measures.add((Measure) olap4jMember);
                }
            }
            return measures;
        } catch (OlapException e) {
            // OlapException not possible, since measures are stored in memory.
            // Demote from checked to unchecked exception.
            throw new RuntimeException(e);
        }
    }

    @Override
	public NamedList<NamedSet> getSets() {
        final NamedListImpl<MondrianOlap4jNamedSet> list =
            new NamedListImpl<>();
        final MondrianOlap4jConnection olap4jConnection =
            olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData.olap4jConnection;
        for (org.eclipse.daanse.olap.api.model.NamedSet namedSet : cube.getNamedSets()) {
            list.add(olap4jConnection.toOlap4j(cube, namedSet));
        }
        return Olap4jUtil.cast(list);
    }

    @Override
	public Collection<Locale> getSupportedLocales() {
        throw new UnsupportedOperationException();
    }

    @Override
	public String getName() {
        return cube.getName();
    }

    @Override
	public String getUniqueName() {
        return cube.getUniqueName();
    }

    @Override
	public String getCaption() {
        return cube.getLocalized(
            OlapElement.LocalizedProperty.CAPTION, olap4jSchema.getLocale());
    }

    @Override
	public String getDescription() {
        return cube.getLocalized(
            OlapElement.LocalizedProperty.DESCRIPTION,
            olap4jSchema.getLocale());
    }

    @Override
	public boolean isVisible() {
        return cube.isVisible();
    }

    @Override
	public MondrianOlap4jMember lookupMember(
        List<IdentifierSegment> nameParts)
        throws OlapException
    {
        final MondrianOlap4jConnection olap4jConnection =
            olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData.olap4jConnection;
        final Role role = olap4jConnection.getMondrianConnection().getRole();
        final SchemaReader schemaReader =
            cube.getSchemaReader(role).withLocus();
        return lookupMember(schemaReader, nameParts);
    }

    private MondrianOlap4jMember lookupMember(
        SchemaReader schemaReader,
        List<IdentifierSegment> nameParts)
    {
        final List<mondrian.olap.Id.Segment> segmentList =
            new ArrayList<>();
        for (IdentifierSegment namePart : nameParts) {
            segmentList.add(Util.convert(namePart));
        }
        final org.eclipse.daanse.olap.api.model.Member member =
            schemaReader.getMemberByUniqueName(segmentList, false);
        if (member == null) {
            return null;
        }

        return olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData
            .olap4jConnection.toOlap4j(member);
    }

    @Override
	public List<Member> lookupMembers(
        Set<Member.TreeOp> treeOps,
        List<IdentifierSegment> nameParts) throws OlapException
    {
        final MondrianOlap4jConnection olap4jConnection =
            olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData.olap4jConnection;
        final Role role = olap4jConnection.getMondrianConnection().getRole();
        final SchemaReader schemaReader =
            cube.getSchemaReader(role).withLocus();
        final MondrianOlap4jMember member =
            lookupMember(schemaReader, nameParts);
        if (member == null) {
            return Collections.emptyList();
        }

        // Add ancestors and/or the parent. Ancestors are prepended, to ensure
        // hierarchical order.
        final List<MondrianOlap4jMember> list =
            new ArrayList<>();
        if (treeOps.contains(Member.TreeOp.ANCESTORS)) {
            for (MondrianOlap4jMember m = member.getParentMember();
                m != null;
                m = m.getParentMember())
            {
                list.add(0, m);
            }
        } else if (treeOps.contains(Member.TreeOp.PARENT)) {
            final MondrianOlap4jMember parentMember = member.getParentMember();
            if (parentMember != null) {
                list.add(parentMember);
            }
        }

        // Add siblings. Siblings which occur after the member are deferred,
        // because they occur after children and descendants in the
        // hierarchical ordering.
        List<MondrianOlap4jMember> remainingSiblingsList = null;
        if (treeOps.contains(Member.TreeOp.SIBLINGS)) {
            final MondrianOlap4jMember parentMember = member.getParentMember();
            NamedList<MondrianOlap4jMember> siblingMembers;
            if (parentMember != null) {
                siblingMembers =
                    olap4jConnection.toOlap4j(
                        schemaReader.getMemberChildren(parentMember.member));
            } else {
                siblingMembers =
                    olap4jConnection.toOlap4j(
                        schemaReader.getHierarchyRootMembers(
                            member.member.getHierarchy()));
            }
            List<MondrianOlap4jMember> targetList = list;
            for (MondrianOlap4jMember siblingMember : siblingMembers) {
                if (siblingMember.equals(member)) {
                    targetList =
                        remainingSiblingsList =
                            new ArrayList<>();
                } else {
                    targetList.add(siblingMember);
                }
            }
        }

        // Add the member itself.
        if (treeOps.contains(Member.TreeOp.SELF)) {
            list.add(member);
        }

        // Add descendants and/or children.
        if (treeOps.contains(Member.TreeOp.DESCENDANTS)) {
            addDescendants(list, schemaReader, olap4jConnection, member, true);
        } else if (treeOps.contains(Member.TreeOp.CHILDREN)) {
            addDescendants(list, schemaReader, olap4jConnection, member, false);
        }
        // Lastly, add siblings which occur after the member itself. They
        // occur after all of the descendants in the hierarchical ordering.
        if (remainingSiblingsList != null) {
            list.addAll(remainingSiblingsList);
        }
        return Olap4jUtil.cast(list);
    }

    private void addDescendants(
        List<MondrianOlap4jMember> list,
        SchemaReader schemaReader,
        MondrianOlap4jConnection olap4jConnection,
        MondrianOlap4jMember member,
        boolean recurse)
    {
        for (org.eclipse.daanse.olap.api.model.Member m
            : schemaReader.getMemberChildren(member.member))
        {
            MondrianOlap4jMember childMember = olap4jConnection.toOlap4j(m);
            list.add(childMember);
            if (recurse) {
                addDescendants(
                    list, schemaReader, olap4jConnection, childMember, recurse);
            }
        }
    }

    @Override
	public boolean isDrillThroughEnabled() {
        return true;
    }

    @Override
	public OlapElement getOlapElement() {
        return cube;
    }
}
