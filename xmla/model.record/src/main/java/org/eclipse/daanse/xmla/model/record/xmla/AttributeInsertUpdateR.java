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
package org.eclipse.daanse.xmla.model.record.xmla;

import org.eclipse.daanse.xmla.api.xmla.AttributeInsertUpdate;
import org.eclipse.daanse.xmla.api.xmla.TranslationInsertUpdate;

import java.math.BigInteger;
import java.util.List;

public record AttributeInsertUpdateR(String attributeName,
                                     String name,
                                     AttributeInsertUpdate.Keys keys,
                                     AttributeInsertUpdate.Translations translations,
                                     String value,
                                     String customrollup,
                                     String customrollupproperties,
                                     String unaryoperator,
                                     BigInteger skippedlevels
) implements AttributeInsertUpdate {

    public record Keys(List<Object> key) implements AttributeInsertUpdate.Keys {

    }

    public record Translations(
        List<TranslationInsertUpdate> translation) implements AttributeInsertUpdate.Translations {

    }

}
