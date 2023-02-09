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
package org.eclipse.daanse.mdx.model;

import java.util.List;
import java.util.Optional;

import org.eclipse.daanse.mdx.model.select.SelectCellPropertyListClause;
import org.eclipse.daanse.mdx.model.select.SelectQueryClause;
import org.eclipse.daanse.mdx.model.select.SelectSlicerAxisClause;
import org.eclipse.daanse.mdx.model.select.SelectSubcubeClause;
import org.eclipse.daanse.mdx.model.select.SelectWithClause;

public record SelectStatement(List<SelectWithClause> selectWithClauses,
                              SelectQueryClause selectQueryClause,
                              SelectSubcubeClause selectSubcubeClause,
                              Optional<SelectSlicerAxisClause> selectSlicerAxisClause,
                              Optional<SelectCellPropertyListClause> selectCellPropertyListClause)
        implements MdxStatement {



}
