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
import org.eclipse.daanse.xmla.api.xmla.DataItemFormatEnum;
import org.eclipse.daanse.xmla.api.xmla.InvalidXmlCharacterEnum;
import org.eclipse.daanse.xmla.api.xmla.NullProcessingEnum;
import org.eclipse.daanse.xmla.model.record.xmla.DataItemR;

import java.util.List;
import java.util.Optional;
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
                Optional.ofNullable(source.getDataSize()),
                Optional.ofNullable(source.getMimeType()),
                Optional.ofNullable(NullProcessingEnum.fromValue(source.getNullProcessing())),
                Optional.ofNullable(source.getTrimming()),
                Optional.ofNullable(InvalidXmlCharacterEnum.fromValue(source.getInvalidXmlCharacters())),
                Optional.ofNullable(source.getCollation()),
                Optional.ofNullable(DataItemFormatEnum.fromValue(source.getFormat())),
                Optional.ofNullable(convertBinding(source.getSource())),
                Optional.ofNullable(convertAnnotationList(source.getAnnotations() == null ? null :
                    source.getAnnotations().getAnnotation())));
        }
        return null;
    }

}
