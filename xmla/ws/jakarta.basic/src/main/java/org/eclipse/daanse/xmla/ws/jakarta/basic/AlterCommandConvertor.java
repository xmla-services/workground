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
package org.eclipse.daanse.xmla.ws.jakarta.basic;

import org.eclipse.daanse.xmla.api.xmla.MajorObject;
import org.eclipse.daanse.xmla.api.xmla.ObjectExpansion;
import org.eclipse.daanse.xmla.api.xmla.ObjectReference;
import org.eclipse.daanse.xmla.api.xmla.Scope;
import org.eclipse.daanse.xmla.model.record.xmla.AlterR;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Alter;

import static org.eclipse.daanse.xmla.ws.jakarta.basic.CommandConvertor.convertObjectExpansion;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.CommandConvertor.convertScope;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.MajorObjectConvertor.convertMajorObject;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.ObjectReferenceConvertor.convertObjectReference;

public class AlterCommandConvertor {

    public static AlterR convertAlterCommand(Alter alter) {
        ObjectReference object = convertObjectReference(alter.getObject());
        MajorObject objectDefinition = convertMajorObject(alter.getObjectDefinition());
        Scope scope = convertScope(alter.getScope());
        Boolean allowCreate = alter.isAllowCreate();
        ObjectExpansion objectExpansion = convertObjectExpansion(alter.getObjectExpansion());

        AlterR alterCommand = new AlterR(object, objectDefinition, scope, allowCreate, objectExpansion);
        return alterCommand;
    }


}
