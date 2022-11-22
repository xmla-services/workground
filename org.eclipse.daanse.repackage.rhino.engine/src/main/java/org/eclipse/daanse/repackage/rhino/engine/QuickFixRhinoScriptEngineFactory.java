/*********************************************************************
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
**********************************************************************/
package org.eclipse.daanse.repackage.rhino.engine;

import javax.script.ScriptEngineFactory;

import aQute.bnd.annotation.spi.ServiceProvider;

@ServiceProvider(value = ScriptEngineFactory.class)
public class QuickFixRhinoScriptEngineFactory extends org.mozilla.javascript.engine.RhinoScriptEngineFactory {

}
