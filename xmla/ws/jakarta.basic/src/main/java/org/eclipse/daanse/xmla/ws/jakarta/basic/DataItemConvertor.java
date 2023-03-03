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

import org.eclipse.daanse.xmla.api.xmla.DataItem;
import org.eclipse.daanse.xmla.model.record.xmla.DataItemR;

import java.util.List;
import java.util.stream.Collectors;

import static org.eclipse.daanse.xmla.ws.jakarta.basic.AnnotationConvertor.convertAnnotationList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.BindingConvertor.convertBinding;

public class DataItemConvertor {

    public static List<DataItem> convertDataItemList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DataItem> keyColumnList) {
        if (keyColumnList != null) {
            return keyColumnList.stream().map(DataItemConvertor::convertDataItem).collect(Collectors.toList());
        }
        return null;
    }

    public static DataItem convertDataItem(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DataItem source) {
        if (source != null) {
            return new DataItemR(source.getDataType(),
                source.getDataSize(),
                source.getMimeType(),
                source.getNullProcessing(),
                source.getTrimming(),
                source.getInvalidXmlCharacters(),
                source.getCollation(),
                source.getFormat(),
                convertBinding(source.getSource()),
                convertAnnotationList(source.getAnnotations() == null ? null :
                    source.getAnnotations().getAnnotation()));
        }
        return null;
    }

}
