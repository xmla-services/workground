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

import org.eclipse.daanse.xmla.api.xmla.TabularBinding;
import org.eclipse.daanse.xmla.model.record.xmla.DSVTableBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.QueryBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.TableBindingR;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DSVTableBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.QueryBinding;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.TableBinding;

public class TabularBindingConvertor {

    public static TabularBinding convertTabularBinding(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.TabularBinding source) {
        if (source != null) {
            if (source instanceof TableBinding) {
                TableBinding tableBinding = (TableBinding) source;
                return new TableBindingR(tableBinding.getDataSourceID(),
                    tableBinding.getDbTableName(),
                    tableBinding.getDbSchemaName());
            }
            if (source instanceof QueryBinding) {
                QueryBinding queryBinding = (QueryBinding) source;
                return new QueryBindingR(queryBinding.getDataSourceID(),
                    queryBinding.getQueryDefinition());

            }
            if (source instanceof DSVTableBinding) {
                DSVTableBinding dsvTableBinding = (DSVTableBinding) source;
                return new DSVTableBindingR(dsvTableBinding.getDataSourceViewID(),
                    dsvTableBinding.getTableID(),
                    dsvTableBinding.getDataEmbeddingStyle());
            }
        }
        return null;
    }

}