/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.eclipse.daanse.xmla.ws.jakarta.basic;

import static org.eclipse.daanse.xmla.ws.jakarta.basic.ConvertorUtil.convertDuration;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.ConvertorUtil.convertToInstant;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.eclipse.daanse.xmla.api.xmla.AttributeBindingTypeEnum;
import org.eclipse.daanse.xmla.api.xmla.Binding;
import org.eclipse.daanse.xmla.api.xmla.FiscalYearNameEnum;
import org.eclipse.daanse.xmla.api.xmla.Group;
import org.eclipse.daanse.xmla.api.xmla.PersistenceEnum;
import org.eclipse.daanse.xmla.api.xmla.RefreshPolicyEnum;
import org.eclipse.daanse.xmla.api.xmla.ReportingWeekToMonthPatternEnum;
import org.eclipse.daanse.xmla.model.record.engine200_200.ExpressionBindingR;
import org.eclipse.daanse.xmla.model.record.engine200_200.RowNumberBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.AttributeBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.CalculatedMeasureBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.ColumnBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.CubeAttributeBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.CubeDimensionBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.DataSourceViewBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.DimensionBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.GroupR;
import org.eclipse.daanse.xmla.model.record.xmla.InheritedBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.MeasureBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.MeasureGroupBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.MeasureGroupDimensionBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.RowBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.TimeAttributeBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.TimeBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.UserDefinedGroupBindingR;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine200_200.ExpressionBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine200_200.RowNumberBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AttributeBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.CalculatedMeasureBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ColumnBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.CubeAttributeBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.CubeDimensionBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DataSourceViewBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DimensionBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.InheritedBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MeasureBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MeasureGroupBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MeasureGroupDimensionBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.RowBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.TimeAttributeBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.TimeBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.UserDefinedGroupBinding;

public class BindingConvertor {

	private BindingConvertor() {
	}

	public static Binding convertBinding(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Binding source) {
		if (source == null) {
			return null;
		}
		if (source instanceof ColumnBinding b) {
			return new ColumnBindingR(b.getTableID(), b.getColumnID());
		}

		if (source instanceof RowBinding b) {
			return new RowBindingR(b.getTableID());

		}
		if (source instanceof DataSourceViewBinding b) {
			return new DataSourceViewBindingR(b.getDataSourceViewID());
		}
		if (source instanceof AttributeBinding b) {
			return new AttributeBindingR(b.getAttributeID(), AttributeBindingTypeEnum.fromValue(b.getType()),
					Optional.ofNullable(b.getOrdinal()));

		}
		if (source instanceof UserDefinedGroupBinding b) {
			return new UserDefinedGroupBindingR(b.getAttributeID(),
					Optional.ofNullable(convertUserDefinedGroupBindingGroups(b.getGroups())));

		}
		if (source instanceof MeasureBinding b) {
			return new MeasureBindingR(b.getMeasureID());

		}
		if (source instanceof CubeAttributeBinding b) {
			return new CubeAttributeBindingR(b.getCubeID(), b.getCubeDimensionID(), b.getAttributeID(),
					AttributeBindingTypeEnum.fromValue(b.getType()),
					Optional.ofNullable(convertCubeAttributeBindingOrdinal(b.getOrdinal())));

		}
		if (source instanceof DimensionBinding b) {
			return new DimensionBindingR(b.getDataSourceID(), b.getDimensionID(),
					Optional.ofNullable(PersistenceEnum.fromValue(b.getPersistence())),
					Optional.ofNullable(RefreshPolicyEnum.fromValue(b.getRefreshPolicy())),
					Optional.ofNullable(convertDuration(b.getRefreshInterval())));

		}
		if (source instanceof CubeDimensionBinding b) {
			return new CubeDimensionBindingR(b.getDataSourceID(), b.getCubeID(), b.getCubeDimensionID(),
					Optional.ofNullable(b.getFilter()));

		}
		if (source instanceof MeasureGroupBinding b) {
			return new MeasureGroupBindingR(b.getDataSourceID(), b.getCubeID(), b.getMeasureGroupID(),
					Optional.ofNullable(PersistenceEnum.fromValue(b.getPersistence())),
					Optional.ofNullable(RefreshPolicyEnum.fromValue(b.getRefreshPolicy())),
					Optional.ofNullable(convertDuration(b.getRefreshInterval())), Optional.ofNullable(b.getFilter()));

		}
		if (source instanceof MeasureGroupDimensionBinding b) {
			return new MeasureGroupDimensionBindingR(b.getCubeDimensionID());

		}
		if (source instanceof TimeBinding b) {
			return new TimeBindingR(convertToInstant(b.getCalendarStartDate()),
					convertToInstant(b.getCalendarEndDate()), Optional.ofNullable(b.getFirstDayOfWeek()),
					Optional.ofNullable(b.getCalendarLanguage()), Optional.ofNullable(b.getFiscalFirstMonth()),
					Optional.ofNullable(b.getFiscalFirstDayOfMonth()),
					Optional.ofNullable(FiscalYearNameEnum.fromValue(b.getFiscalYearName())),
					Optional.ofNullable(b.getReportingFirstMonth()),
					Optional.ofNullable(b.getReportingFirstWeekOfMonth()),
					Optional.ofNullable(ReportingWeekToMonthPatternEnum.fromValue(b.getReportingWeekToMonthPattern())),
					Optional.ofNullable(b.getManufacturingFirstMonth()),
					Optional.ofNullable(b.getManufacturingFirstWeekOfMonth()),
					Optional.ofNullable(b.getManufacturingExtraMonthQuarter()));

		}
		if (source instanceof TimeAttributeBinding b) {
			// TODO: handle b
			return new TimeAttributeBindingR();

		}
		if (source instanceof InheritedBinding b) {
			// TODO: handle b
			return new InheritedBindingR();

		}
		if (source instanceof CalculatedMeasureBinding b) {
			return new CalculatedMeasureBindingR(b.getMeasureName());

		}
		if (source instanceof RowNumberBinding b) {
			// TODO: handle rowNumberBinding
			return new RowNumberBindingR();

		}
		if (source instanceof ExpressionBinding b) {
			return new ExpressionBindingR(b.getExpression());

		}
		return null;
	}

	public static List<Binding> convertBindingList(
			List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Binding> list) {
		if (list == null) {
			return List.of();
		}
		return list.stream().map(BindingConvertor::convertBinding).toList();
	}

	private static List<BigInteger> convertCubeAttributeBindingOrdinal(CubeAttributeBinding.Ordinal ordinal) {
		if (ordinal == null) {
			return List.of();
		}
		return ordinal.getOrdinal();

	}

	private static List<Group> convertUserDefinedGroupBindingGroups(UserDefinedGroupBinding.Groups groups) {
		if (groups == null) {
			return List.of();
		}
		return convertGroupList(groups.getGroup());
	}

	private static List<Group> convertGroupList(
			List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Group> groupList) {
		if (groupList == null) {
			return List.of();
		}
		return groupList.stream().map(BindingConvertor::convertGroup).toList();

	}

	private static Group convertGroup(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Group group) {
		if (group == null) {
			return null;
		}
		return new GroupR(group.getName(), Optional.ofNullable(convertGroupMembers(group.getMembers())));
	}

	private static List<String> convertGroupMembers(
			org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Group.Members members) {
		if (members == null) {
			return List.of();
		}
		return members.getMember();
	}
}
