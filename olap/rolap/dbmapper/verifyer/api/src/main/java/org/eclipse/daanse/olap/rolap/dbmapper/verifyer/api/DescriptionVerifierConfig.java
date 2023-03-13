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
package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api;

public interface DescriptionVerifierConfig {

    default Level calculatedMemberProperty() {
        return null;
    }

    default Level action() {
        return null;
    }

    default Level calculatedMember() {
        return null;
    }

    default Level cube() {
        return null;
    }

    default Level cubeDimension() {
        return null;
    }

    default Level drillThroughAction() {
        return null;
    }

    default Level hierarchy() {
        return null;
    }

    default Level level() {
        return null;
    }

    default Level measure() {
        return null;
    }

    default Level namedSet() {
        return null;
    }

    default Level parameter() {
        return null;
    }

    default Level privateDimension() {
        return null;
    }

    default Level property() {
        return null;
    }

    default Level schema() {
        return null;
    }

    default Level sharedDimension() {
        return null;
    }

    default Level virtualCube() {
        return null;
    }
}
