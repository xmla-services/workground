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
package org.eclipse.daanse.grabber.api;

import java.util.List;

public class GrabberInitData {

    private List<String> sourcesEndPoints;

    public List<String> getSourcesEndPoints() {
        return sourcesEndPoints;
    }

    public void setSourcesEndPoints(List<String> sourcesEndPoints) {
        this.sourcesEndPoints = sourcesEndPoints;
    }

}
