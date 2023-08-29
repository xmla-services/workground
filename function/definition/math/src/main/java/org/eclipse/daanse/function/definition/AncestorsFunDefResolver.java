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
package org.eclipse.daanse.function.definition;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.function.FunctionResolver;
import org.eclipse.daanse.function.ReflectiveMultiResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@ServiceProvider(value = FunctionResolver.class, attribute = { "function.definition:String='AncestorsFun'" })
@Component(service = FunctionResolver.class, scope = ServiceScope.SINGLETON)
public class AncestorsFunDefResolver extends ReflectiveMultiResolver {

    public AncestorsFunDefResolver(
    ) {
        super(
            "Ancestors",
            "Ancestors(<Member>, {<Level>|<Numeric Expression>})",
            "Returns the set of all ancestors of a specified member at a specified level or at a specified distance from the member",
            new String[] {"fxml", "fxmn"},
            AncestorsFunDef.class);
    }
}
