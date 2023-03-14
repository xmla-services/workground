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
package org.eclipse.daanse.xmla.api.xmla;

/**
 * The type of action. The following values are allowed:
 * "Url" – Opens a URL string in an Internet browser.
 * "Html" – Renders an HTML script in an Internet browser.
 * "Statement" – Executes a statement that is understood by the client
 * application.
 * "DrillThrough" - See DrillThroughAction.
 * "Dataset" – Executes an MDX statement whose results are returned
 * as a dataset.
 * "Rowset" – Executes an MDX statement whose results are returned
 * as a rowset.
 * "CommandLine" – Executes a command.
 * "Proprietary" – Executes an action whose structure is understood by
 * a particular proprietary client application.
 * "Report" – See ReportAction.
 * The DrillThrough type can be defined only with actions where the target
 * type is Cells. The DrillThrough type is referenced in the
 * MDSCHEMA_ACTIONS schema rowset, Action_Type column, as a Rowset
 * action (0x010). The report action is exposed in the
 * MDSCHEMA_ACTIONS schema rowset, Action_Type column, as a URL
 * action(0x01). Note that for the derived types ReportAction and
 * DrillThroughAction, this value MUST be set to "Report" and
 * "DrillThrough", respectively.
 */
public enum TypeEnum {

    /**
     * Opens a URL string in an Internet browser.
     */
    Url,

    /**
     * Renders an HTML script in an Internet browser.
     */
    Html,

    /**
     * Executes a statement that is understood by the client
     * application.
     */
    Statement,

    /**
     * See DrillThroughAction.
     */
    DrillThrough,

    /**
     * Executes an MDX statement whose results are returned
     * as a dataset.
     */
    Dataset,

    /**
     * Executes an MDX statement whose results are returned
     * as a rowset.
     */
    Rowset,

    /**
     * Executes a command.
     */
    CommandLine,

    /**
     * Executes an action whose structure is understood by
     * a particular proprietary client application.
     */
    Proprietary,

    /**
     * See ReportAction.
     */
    Report;

    public static TypeEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        for (TypeEnum e : TypeEnum.values()) {
            if (e.name().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("AccessEnum Illegal argument ")
            .append(v).toString());
    }

}
