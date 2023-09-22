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
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package mondrian.rolap.util;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMemberProperty;

public class CalculatedMemberUtil {

    private CalculatedMemberUtil() {
    }

    public static String getFormula(MappingCalculatedMember calculatedMember) {
        if (calculatedMember.formulaElement() != null) {
            return calculatedMember.formulaElement().cdata();
        } else {
            return calculatedMember.formula();
        }
    }

    /**
     * Returns the format string, looking for a property called
     * "FORMAT_STRING" first, then looking for an attribute called
     * "formatString".
     */
    public static String getFormatString(MappingCalculatedMember calculatedMember) {
        for (MappingCalculatedMemberProperty prop : calculatedMember.calculatedMemberProperties()) {
            if (prop.name().equals(
                mondrian.olap.Property.FORMAT_STRING.name))
            {
                return prop.value();
            }
        }
        return calculatedMember.formatString();
    }
}
