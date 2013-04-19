package org.andreschnabel.jprojectinspector.utilities.helpers;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.util.Map;

public class EquationHelpers {

	public static Object parseEquation(Map<String, Object> varBindings, String equation) {
		try {
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
			return engine.eval(equation, new SimpleBindings(varBindings));
		} catch(ScriptException e) {
			return null;
		}
	}

}
