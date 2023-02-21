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
package org.eclipse.daanse.xmla.api.exception;

import org.eclipse.daanse.xmla.api.engine200.WarningLocationObject;

public interface MessageLocation {

    MessageLocation.Start start();

    MessageLocation.End end();

    Integer lineOffset();

    Integer textLength();

    WarningLocationObject sourceObject();

    WarningLocationObject dependsOnObject();

    Integer rowNumber();

    interface End {

        int line();

        int column();

    }

    interface Start {

        int line();

        int column();
    }

}
