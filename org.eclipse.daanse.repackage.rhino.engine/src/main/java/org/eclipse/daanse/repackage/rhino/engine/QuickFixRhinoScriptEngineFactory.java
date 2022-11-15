package org.eclipse.daanse.repackage.rhino.engine;

import javax.script.ScriptEngineFactory;

import aQute.bnd.annotation.spi.ServiceProvider;

@ServiceProvider(value = ScriptEngineFactory.class)
public class QuickFixRhinoScriptEngineFactory extends org.mozilla.javascript.engine.RhinoScriptEngineFactory {

}
