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

package mondrian.test.clearview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.modifier.pojo.PojoMappingModifier;
import org.eclipse.daanse.rolap.mapping.pojo.CalculatedMemberMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CalculatedMemberPropertyMappingImpl;

public class MiscTestModifier extends PojoMappingModifier {

    public MiscTestModifier(CatalogMapping catalog) {
        super(catalog);
    }

    /*
    <CalculatedMember
      name='Sales as % of Cost'
        dimension='Measures'
        formula='([Measures].[Store Sales] - [Measures].[Store Cost])/[Measures].[Store Cost]'>
        <CalculatedMemberProperty name='FORMAT_STRING' value='####0.0%'/>
      </CalculatedMember>
     */
    
    protected List<? extends CalculatedMemberMapping> cubeCalculatedMembers(CubeMapping cube) {
        List<CalculatedMemberMapping> result = new ArrayList<>();
        result.addAll(super.cubeCalculatedMembers(cube));
        if ("Sales".equals(cube.getName())) {
            result.add(CalculatedMemberMappingImpl.builder()
                .withName("Sales as % of Cost")
                //.withDimension("Measures")
                .withFormula("([Measures].[Store Sales] - [Measures].[Store Cost])/[Measures].[Store Cost]")
                .withCalculatedMemberProperties(List.of(
                	CalculatedMemberPropertyMappingImpl.builder()
                        .withName("FORMAT_STRING")
                        .withValue("####0.0%")
                        .build()
                ))
                .build());
        }
        return result;

    }
}
