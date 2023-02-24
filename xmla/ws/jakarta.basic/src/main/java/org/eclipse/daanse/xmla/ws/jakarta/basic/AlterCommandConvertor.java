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
import org.eclipse.daanse.xmla.model.record.execute.alter.AlterCommandR;
import org.eclipse.daanse.xmla.model.record.xmla.MajorObjectR;
import org.eclipse.daanse.xmla.model.record.xmla.ObjectReferenceR;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Alter;

import java.util.Optional;

public class AlterCommandConvertor {

    public static AlterCommandR convertAlterCommand(Alter alter) {
        Optional<ObjectReference> object = convertObjectReference(alter.getObject());
        MajorObject objectDefinition = convertMajorObject(alter.getObjectDefinition());
        Scope scope = convertScope(alter.getScope());
        Boolean allowCreate = alter.isAllowCreate();
        ObjectExpansion objectExpansion = convertObjectExpansion(alter.getObjectExpansion());

        AlterCommandR alterCommand = new AlterCommandR(object, objectDefinition, scope, allowCreate, objectExpansion);
        return alterCommand;
    }

    private static ObjectExpansion convertObjectExpansion(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ObjectExpansion objectExpansion) {
        return ObjectExpansion.fromValue(objectExpansion.value());
    }

    private static Scope convertScope(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Scope scope) {
        return Scope.fromValue(scope.value());
    }

    private static MajorObject convertMajorObject(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MajorObject objectDefinition) {
        return new MajorObjectR();
    }

    private static Optional<ObjectReference> convertObjectReference(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ObjectReference object) {
        return Optional.of(new ObjectReferenceR());
    }
}
