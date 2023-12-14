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
@org.osgi.annotation.bundle.Export
@org.osgi.annotation.versioning.Version("0.0.1")
@RecordBuilder.Include(packages="org.eclipse.daanse.olap.rolap.dbmapper.model.record")
@RecordBuilder.Options(addStaticBuilder = false, enableWither = false, enableGetters = false)//, componentsMethodName = "")
package org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder;


import io.soabase.recordbuilder.core.RecordBuilder;