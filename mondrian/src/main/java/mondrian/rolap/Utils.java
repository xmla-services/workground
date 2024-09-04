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
package mondrian.rolap;

import java.util.List;
import java.util.Objects;

import org.eclipse.daanse.rolap.mapping.api.model.InlineTableQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.QueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SqlSelectQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryMapping;

public class Utils {

    private Utils() {
    }

    public static boolean equalsQuery(QueryMapping relation, QueryMapping q2) {
        if (relation instanceof TableQueryMapping t1 && q2 instanceof TableQueryMapping t2) {
            return t1.getName() != null && t1.getName().equals(t2.getName()) &&
                Objects.equals(t1.getAlias(), t2.getAlias()) &&
                Objects.equals(t1.getSchema(), t2.getSchema());
        }
        if (relation instanceof SqlSelectQueryMapping s1 && q2 instanceof SqlSelectQueryMapping s2) {
            if (!Objects.equals(s1.getAlias(), s2.getAlias())) {
                return false;
            }
            if (s1.getSQL() == null || s2.getSQL() == null ||
                s1.getSQL().size() != s2.getSQL().size()) {
                return false;
            }
            for (int i = 0; i < s1.getSQL().size(); i++) {
                String statement1 = s1.getSQL().get(i).getStatement();
                String statement2 = s2.getSQL().get(i).getStatement();
                List<String> dialects1 = s1.getSQL().get(i).getDialects();
                List<String> dialects2 = s2.getSQL().get(i).getDialects();

                if (!Objects.equals(statement1, statement2)) {
                    return false;
                }
                if (dialects1 == null || dialects2 == null ||
                    dialects1.size() != dialects2.size()) {
                    return false;
                }
                for (int ii = 0; ii < dialects1.size(); ii++) {
                    if (!dialects1.get(ii).equals(dialects2.get(ii))) {
                        return false;
                    }
                }
            }
            return true;
        }
        if (relation instanceof InlineTableQueryMapping it1 && q2 instanceof InlineTableQueryMapping it2) {
            return it1.getAlias() != null && it1.getAlias().equals(it2.getAlias()); //old implementation
        }
        return false;
    }
}
