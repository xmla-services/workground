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
package org.eclipse.daanse.olap.xmla.bridge;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.eclipse.daanse.olap.api.Context;

public interface ContextListSupplyer extends Supplier<List<Context>> {

	List<Context> get();

	Optional<Context> tryGetFirstByName(String catalogName);

	Optional<Context> tryGetFirst(Predicate<Context> function);

	List<Context> get(Predicate<Context> predicate);
}
