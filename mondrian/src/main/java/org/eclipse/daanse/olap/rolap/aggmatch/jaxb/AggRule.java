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
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.eclipse.daanse.olap.rolap.aggmatch.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * A RolapConnection uses one AggRule. If no name is specified, then
 * the AggRule which is marked as default==true is used (validation
 * fails if one and only one AggRule is not marked as the default).
 * An AggRule has manditory child elements for matching the
 * aggregate table names, aggregate table fact count column,
 * foreign key columns, the measure columns, and the hierarchy level
 * columns. These child elements can be specified as direct children
 * of an AggRule element or by reference to elements defined as a
 * pier to the AggRule (using references allows reuse of the child
 * elements and with one quick edit the reference to use can be
 * changed by changing the refid attribute value).
 */

@XmlType(name = "AggRule", propOrder = {
    "ignoreMap",
    "ignoreMapRef",
    "factCountMatch",
    "factCountMatchRef",
    "foreignKeyMatch",
    "foreignKeyMatchRef",
    "tableMatch",
    "tableMatchRef",
    "levelMap",
    "levelMapRef",
    "measureMap",
    "measureMapRef"
})
@XmlAccessorType(XmlAccessType.FIELD)
public class AggRule extends Base {

    /**
     * Name of this AggRule
     */
    @XmlAttribute(name = "tag", required = true)
    String tag;

    /**
     * Name of the aggregate column containing the count for
     * the row.
     */
    @XmlAttribute(name = "countColumn", required = true)
    String countColumn = "fact_count";

    @XmlElement(name = "IgnoreMap")
    IgnoreMap ignoreMap;

    @XmlElement(name = "IgnoreMapRef")
    IgnoreMapRef ignoreMapRef;

    @XmlElement(name = "FactCountMatch")
    FactCountMatch factCountMatch;

    @XmlElement(name = "FactCountMatchRef")
    FactCountMatchRef factCountMatchRef;

    @XmlElement(name = "ForeignKeyMatch")
    ForeignKeyMatch foreignKeyMatch;

    @XmlElement(name = "ForeignKeyMatchRef")
    ForeignKeyMatchRef foreignKeyMatchRef;

    @XmlElement(name = "TableMatch")
    TableMatch tableMatch;

    @XmlElement(name = "TableMatchRef")
    TableMatchRef tableMatchRef;

    @XmlElement(name = "LevelMap")
    LevelMap levelMap;

    @XmlElement(name = "LevelMapRef")
    LevelMapRef levelMapRef;

    @XmlElement(name = "MeasureMap")
    MeasureMap measureMap;

    @XmlElement(name = "MeasureMapRef")
    MeasureMapRef measureMapRef;

    private boolean isOk(final Base base) {
        return ((base != null) && base.isEnabled());
    }

    private boolean isRef(
        final AggRules rules,
        final mondrian.recorder.MessageRecorder msgRecorder,
        final Base base,
        final Base baseRef,
        final String baseName
    ) {
        if (!isOk(base)) {
            if (isOk(baseRef)) {
                baseRef.validate(rules, msgRecorder);
                return true;
            } else {
                String msg = "Neither base " +
                    baseName +
                    " or baseref " +
                    baseName +
                    "Ref is ok";
                msgRecorder.reportError(msg);
                return false;
            }
        } else if (isOk(baseRef)) {
            String msg = "Both base " +
                base.getName() +
                " and baseref " +
                baseRef.getName() +
                " are ok";
            msgRecorder.reportError(msg);
            return false;
        } else {
            base.validate(rules, msgRecorder);
            return false;
        }
    }

    // called after a constructor is called
    public void validate(
        final AggRules rules,
        final mondrian.recorder.MessageRecorder msgRecorder
    ) {
        msgRecorder.pushContextName(getName());
        try {
            // IgnoreMap is optional
            if (ignoreMap != null) {
                ignoreMap.validate(rules, msgRecorder);
            } else if (ignoreMapRef != null) {
                ignoreMapRef.validate(rules, msgRecorder);
                ignoreMap =
                    rules.lookupIgnoreMap(ignoreMapRef.getRefId());
            }
            if (isRef(rules, msgRecorder, factCountMatch,
                factCountMatchRef, "FactCountMatch")) {
                factCountMatch = rules.lookupFactCountMatch(
                    factCountMatchRef.getRefId());
            }
            if (isRef(rules, msgRecorder, foreignKeyMatch,
                foreignKeyMatchRef, "ForeignKeyMatch")) {
                foreignKeyMatch = rules.lookupForeignKeyMatch(
                    foreignKeyMatchRef.getRefId());
            }
            if (isRef(rules, msgRecorder, tableMatch,
                tableMatchRef, "TableMatch")) {
                tableMatch =
                    rules.lookupTableMatch(tableMatchRef.getRefId());
            }
            if (isRef(rules, msgRecorder, levelMap,
                levelMapRef, "LevelMap")) {
                levelMap = rules.lookupLevelMap(levelMapRef.getRefId());
            }
            if (isRef(rules, msgRecorder, measureMap,
                measureMapRef, "MeasureMap")) {
                measureMap =
                    rules.lookupMeasureMap(measureMapRef.getRefId());
            }
        } finally {
            msgRecorder.popContextName();
        }
    }

    public String getTag() {
        return tag;
    }

    public String getCountColumn() {
        return countColumn;
    }

    public FactCountMatch getFactCountMatch() {
        return factCountMatch;
    }

    public ForeignKeyMatch getForeignKeyMatch() {
        return foreignKeyMatch;
    }

    public TableMatch getTableMatch() {
        return tableMatch;
    }

    public LevelMap getLevelMap() {
        return levelMap;
    }

    public MeasureMap getMeasureMap() {
        return measureMap;
    }

    public IgnoreMap getIgnoreMap() {
        return ignoreMap;
    }

    @Override
    protected String getName() {
        return "AggRule";
    }
}
