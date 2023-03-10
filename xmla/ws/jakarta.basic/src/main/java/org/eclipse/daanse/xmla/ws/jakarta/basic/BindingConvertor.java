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

import org.eclipse.daanse.xmla.api.xmla.AttributeBindingTypeEnum;
import org.eclipse.daanse.xmla.api.xmla.Binding;
import org.eclipse.daanse.xmla.api.xmla.Group;
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

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.eclipse.daanse.xmla.ws.jakarta.basic.ConvertorUtil.convertDuration;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.ConvertorUtil.convertToInstant;

public class BindingConvertor {

    public static Binding convertBinding(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Binding source) {
        if (source != null) {
            if (source instanceof ColumnBinding) {
                ColumnBinding s = (ColumnBinding) source;
                return new ColumnBindingR(s.getTableID(),
                    s.getColumnID());
            }

            if (source instanceof RowBinding) {
                RowBinding s = (RowBinding) source;
                return new RowBindingR(s.getTableID());

            }
            if (source instanceof DataSourceViewBinding) {
                DataSourceViewBinding s = (DataSourceViewBinding) source;
                return new DataSourceViewBindingR(s.getDataSourceViewID());
            }
            if (source instanceof AttributeBinding) {
                AttributeBinding s = (AttributeBinding) source;
                return new AttributeBindingR(s.getAttributeID(),
                    AttributeBindingTypeEnum.fromValue(s.getType()),
                    Optional.ofNullable(s.getOrdinal()));

            }
            if (source instanceof UserDefinedGroupBinding) {
                UserDefinedGroupBinding s = (UserDefinedGroupBinding) source;
                return new UserDefinedGroupBindingR(s.getAttributeID(),
                    Optional.ofNullable(convertUserDefinedGroupBindingGroups(s.getGroups())));

            }
            if (source instanceof MeasureBinding) {
                MeasureBinding s = (MeasureBinding) source;
                return new MeasureBindingR(s.getMeasureID());

            }
            if (source instanceof CubeAttributeBinding) {
                CubeAttributeBinding s = (CubeAttributeBinding) source;
                return new CubeAttributeBindingR(s.getCubeID(),
                    s.getCubeDimensionID(),
                    s.getAttributeID(),
                    s.getType(),
                    convertCubeAttributeBindingOrdinal(s.getOrdinal()));

            }
            if (source instanceof DimensionBinding) {
                DimensionBinding s = (DimensionBinding) source;
                return new DimensionBindingR(s.getDataSourceID(),
                    s.getDimensionID(),
                    s.getPersistence(),
                    s.getRefreshPolicy(),
                    convertDuration(s.getRefreshInterval()));

            }
            if (source instanceof CubeDimensionBinding) {
                CubeDimensionBinding s = (CubeDimensionBinding) source;
                return new CubeDimensionBindingR(s.getDataSourceID(),
                    s.getCubeID(),
                    s.getCubeDimensionID(),
                    s.getFilter());

            }
            if (source instanceof MeasureGroupBinding) {
                MeasureGroupBinding s = (MeasureGroupBinding) source;
                return new MeasureGroupBindingR(s.getDataSourceID(),
                    s.getCubeID(),
                    s.getMeasureGroupID(),
                    s.getPersistence(),
                    s.getRefreshPolicy(),
                    convertDuration(s.getRefreshInterval()),
                    s.getFilter());

            }
            if (source instanceof MeasureGroupDimensionBinding) {
                MeasureGroupDimensionBinding s = (MeasureGroupDimensionBinding) source;
                return new MeasureGroupDimensionBindingR(s.getCubeDimensionID());

            }
            if (source instanceof TimeBinding) {
                TimeBinding s = (TimeBinding) source;
                return new TimeBindingR(convertToInstant(s.getCalendarStartDate()),
                    convertToInstant(s.getCalendarEndDate()),
                    s.getFirstDayOfWeek(),
                    s.getCalendarLanguage(),
                    s.getFiscalFirstMonth(),
                    s.getFiscalFirstDayOfMonth(),
                    s.getFiscalYearName(),
                    s.getReportingFirstMonth(),
                    s.getReportingFirstWeekOfMonth(),
                    s.getReportingWeekToMonthPattern(),
                    s.getManufacturingFirstMonth(),
                    s.getManufacturingFirstWeekOfMonth(),
                    s.getManufacturingExtraMonthQuarter());

            }
            if (source instanceof TimeAttributeBinding) {
                TimeAttributeBinding s = (TimeAttributeBinding) source;
                return new TimeAttributeBindingR();

            }
            if (source instanceof InheritedBinding) {
                InheritedBinding s = (InheritedBinding) source;
                return new InheritedBindingR();

            }
            if (source instanceof CalculatedMeasureBinding) {
                CalculatedMeasureBinding s = (CalculatedMeasureBinding) source;
                return new CalculatedMeasureBindingR(s.getMeasureName());

            }
            if (source instanceof RowNumberBinding) {
                RowNumberBinding s = (RowNumberBinding) source;
                return new RowNumberBindingR();

            }
            if (source instanceof ExpressionBinding) {
                ExpressionBinding s = (ExpressionBinding) source;
                return new ExpressionBindingR(s.getExpression());

            }
        }
        return null;
    }

    private static List<BigInteger> convertCubeAttributeBindingOrdinal(
        CubeAttributeBinding.Ordinal ordinal
    ) {
        if (ordinal != null) {
            return ordinal.getOrdinal();
        }
        return null;

    }

    private static List<Group> convertUserDefinedGroupBindingGroups(
        UserDefinedGroupBinding.Groups groups
    ) {
        if (groups != null) {
            return convertGroupList(groups.getGroup());
        }
        return null;
    }

    private static List<Group> convertGroupList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Group> groupList) {
        if (groupList != null) {
            return groupList.stream().map(BindingConvertor::convertGroup).collect(Collectors.toList());
        }
        return null;

    }

    private static Group convertGroup(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Group group) {
        if (group != null) {
            return new GroupR(group.getName(),
                convertGroupMembers(group.getMembers()));
        }
        return null;
    }

    private static List<String> convertGroupMembers(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Group.Members members) {
        if (members != null) {
            return members.getMember();
        }
        return null;
    }
}
