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
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TableMiningStructureColumn", propOrder = {

})
public class TableMiningStructureColumn extends MiningStructureColumn{

    @XmlElement(name = "ForeignKeyColumns")
    protected TableMiningStructureColumn.ForeignKeyColumns foreignKeyColumns;
    @XmlElement(name = "SourceMeasureGroup")
    protected MeasureGroupBinding sourceMeasureGroup;
    @XmlElement(name = "Columns")
    protected TableMiningStructureColumn.Columns columns;
    @XmlElement(name = "Translations")
    protected TableMiningStructureColumn.Translations translations;

    public TableMiningStructureColumn.ForeignKeyColumns getForeignKeyColumns() {
        return foreignKeyColumns;
    }

    public void setForeignKeyColumns(TableMiningStructureColumn.ForeignKeyColumns value) {
        this.foreignKeyColumns = value;
    }

    public MeasureGroupBinding getSourceMeasureGroup() {
        return sourceMeasureGroup;
    }

    public void setSourceMeasureGroup(MeasureGroupBinding value) {
        this.sourceMeasureGroup = value;
    }

    public TableMiningStructureColumn.Columns getColumns() {
        return columns;
    }

    public void setColumns(TableMiningStructureColumn.Columns value) {
        this.columns = value;
    }

    public TableMiningStructureColumn.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(TableMiningStructureColumn.Translations value) {
        this.translations = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"column"})
    public static class Columns {

        @XmlElement(name = "Column", required = true)
        protected List<MiningStructureColumn> column;

        public List<MiningStructureColumn> getColumn() {
            return this.column;
        }

        public void setColumn(List<MiningStructureColumn> column) {
            this.column = column;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"foreignKeyColumn"})
    public static class ForeignKeyColumns {

        @XmlElement(name = "ForeignKeyColumn")
        protected List<DataItem> foreignKeyColumn;

        public List<DataItem> getForeignKeyColumn() {
            return this.foreignKeyColumn;
        }

        public void setForeignKeyColumn(List<DataItem> foreignKeyColumn) {
            this.foreignKeyColumn = foreignKeyColumn;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"translation"})
    public static class Translations {

        @XmlElement(name = "Translation")
        protected List<Translation> translation;

        public List<Translation> getTranslation() {
            return this.translation;
        }

        public void setTranslation(List<Translation> translation) {
            this.translation = translation;
        }
    }

}
