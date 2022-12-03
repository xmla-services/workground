/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * History:
 *  This files came from the mondrian project. Some of the Flies
 *  (mostly the Tests) did not have License Header.
 *  But the Project is EPL Header. 2002-2022 Hitachi Vantara.
 *
 * Contributors:
 *   Hitachi Vantara.
 *   SmartCity Jena - initial  Java 8, Junit5
 */

package mondrian.olap;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.daanse.engine.api.Context;
import org.eclipse.daanse.olap.api.access.Access;
import org.eclipse.daanse.olap.api.access.Role;
import org.eclipse.daanse.olap.api.model.Cube;
import org.eclipse.daanse.olap.api.model.Dimension;
import org.eclipse.daanse.olap.api.model.Hierarchy;
import org.eclipse.daanse.olap.api.model.Level;
import org.eclipse.daanse.olap.api.model.Member;
import org.eclipse.daanse.olap.api.model.NamedSet;
import org.eclipse.daanse.olap.api.model.OlapElement;

import mondrian.calc.Calc;
import mondrian.olap.Id.NameSegment;
import mondrian.olap.Id.Segment;
import mondrian.olap.NameResolver.Namespace;
import mondrian.rolap.RolapSchema;

public class SpySchemaReader implements SchemaReader {

	private SchemaReader delegate;

	public SpySchemaReader(SchemaReader schemaReader) {
		this.delegate=schemaReader;
	}

	public RolapSchema getSchema() {
		return delegate.getSchema();
	}

	public Role getRole() {
		return delegate.getRole();
	}

	public List<Dimension> getCubeDimensions(Cube cube) {
		return delegate.getCubeDimensions(cube);
	}

	public List<Hierarchy> getDimensionHierarchies(Dimension dimension) {
		return delegate.getDimensionHierarchies(dimension);
	}

	public List<Member> getHierarchyRootMembers(Hierarchy hierarchy) {
		return delegate.getHierarchyRootMembers(hierarchy);
	}

	public int getChildrenCountFromCache(Member member) {
		return delegate.getChildrenCountFromCache(member);
	}

	public int getLevelCardinality(Level level, boolean approximate, boolean materialize) {
		return delegate.getLevelCardinality(level, approximate, materialize);
	}

	public Member substitute(Member member) {
		return delegate.substitute(member);
	}

	public List<Member> getMemberChildren(Member member) {
		return delegate.getMemberChildren(member);
	}

	public List<Member> getMemberChildren(Member member, Evaluator context) {
		return delegate.getMemberChildren(member, context);
	}

	public List<Member> getMemberChildren(List<Member> members) {
		return delegate.getMemberChildren(members);
	}

	public List<Member> getMemberChildren(List<Member> members, Evaluator context) {
		return delegate.getMemberChildren(members, context);
	}

	public void getParentChildContributingChildren(Member dataMember, Hierarchy hierarchy, List<Member> list) {
		delegate.getParentChildContributingChildren(dataMember, hierarchy, list);
	}

	public Member getMemberParent(Member member) {
		return delegate.getMemberParent(member);
	}

	public void getMemberAncestors(Member member, List<Member> ancestorList) {
		delegate.getMemberAncestors(member, ancestorList);
	}

	public int getMemberDepth(Member member) {
		return delegate.getMemberDepth(member);
	}

	public Member getMemberByUniqueName(List<Segment> uniqueNameParts, boolean failIfNotFound, MatchType matchType) {
		return delegate.getMemberByUniqueName(uniqueNameParts, failIfNotFound, matchType);
	}

	public Member getMemberByUniqueName(List<Segment> uniqueNameParts, boolean failIfNotFound) {
		return delegate.getMemberByUniqueName(uniqueNameParts, failIfNotFound);
	}

	public OlapElement lookupCompound(OlapElement parent, List<Segment> names, boolean failIfNotFound, int category,
			MatchType matchType) {
		return delegate.lookupCompound(parent, names, failIfNotFound, category, matchType);
	}

	public OlapElement lookupCompound(OlapElement parent, List<Segment> names, boolean failIfNotFound, int category) {
		return delegate.lookupCompound(parent, names, failIfNotFound, category);
	}

	public Member getCalculatedMember(List<Segment> nameParts) {
		return delegate.getCalculatedMember(nameParts);
	}

	public NamedSet getNamedSet(List<Segment> nameParts) {
		return delegate.getNamedSet(nameParts);
	}

	public void getMemberRange(Level level, Member startMember, Member endMember, List<Member> list) {
		delegate.getMemberRange(level, startMember, endMember, list);
	}

	public Member getLeadMember(Member member, int n) {
		return delegate.getLeadMember(member, n);
	}

	public int compareMembersHierarchically(Member m1, Member m2) {
		return delegate.compareMembersHierarchically(m1, m2);
	}

	public OlapElement getElementChild(OlapElement parent, Segment name, MatchType matchType) {
		return delegate.getElementChild(parent, name, matchType);
	}

	public OlapElement getElementChild(OlapElement parent, Segment name) {
		return delegate.getElementChild(parent, name);
	}

	public List<Member> getLevelMembers(Level level, boolean includeCalculated) {
		return delegate.getLevelMembers(level, includeCalculated);
	}

	public List<Member> getLevelMembers(Level level, boolean includeCalculated, Evaluator context) {
		return delegate.getLevelMembers(level, includeCalculated, context);
	}

	public List<Member> getLevelMembers(Level level, Evaluator context) {
		return delegate.getLevelMembers(level, context);
	}

	public List<Level> getHierarchyLevels(Hierarchy hierarchy) {
		return delegate.getHierarchyLevels(hierarchy);
	}

	public Member getHierarchyDefaultMember(Hierarchy hierarchy) {
		return delegate.getHierarchyDefaultMember(hierarchy);
	}

	public boolean isDrillable(Member member) {
		return delegate.isDrillable(member);
	}

	public boolean isVisible(Member member) {
		return delegate.isVisible(member);
	}

	public Cube[] getCubes() {
		return delegate.getCubes();
	}

	public List<Member> getCalculatedMembers(Hierarchy hierarchy) {
		return delegate.getCalculatedMembers(hierarchy);
	}

	public List<Member> getCalculatedMembers(Level level) {
		return delegate.getCalculatedMembers(level);
	}

	public List<Member> getCalculatedMembers() {
		return delegate.getCalculatedMembers();
	}

	public Member lookupMemberChildByName(Member parent, Segment childName, MatchType matchType) {
		return delegate.lookupMemberChildByName(parent, childName, matchType);
	}

	public List<Member> lookupMemberChildrenByNames(Member parent, List<NameSegment> childNames, MatchType matchType) {
		return delegate.lookupMemberChildrenByNames(parent, childNames, matchType);
	}

	public NativeEvaluator getNativeSetEvaluator(FunDef fun, Exp[] args, Evaluator evaluator, Calc calc) {
		return delegate.getNativeSetEvaluator(fun, args, evaluator, calc);
	}

	public Parameter getParameter(String name) {
		return delegate.getParameter(name);
	}

	public DataSource getDataSource() {
		return delegate.getDataSource();
	}

	public SchemaReader withoutAccessControl() {
		return delegate.withoutAccessControl();
	}

	public Cube getCube() {
		return delegate.getCube();
	}

	public SchemaReader withLocus() {
		return delegate.withLocus();
	}

	public List<Namespace> getNamespaces() {
		return delegate.getNamespaces();
	}

	public Map<? extends Member, Access> getMemberChildrenWithDetails(Member member, Evaluator evaluator) {
		return delegate.getMemberChildrenWithDetails(member, evaluator);
	}

    @Override
    public Context getContext() {
        return delegate.getContext();
    }


}
