package org.andreschnabel.jprojectinspector.utilities.helpers;

import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.IPredicate;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.util.List;
import java.util.Map;

public class EquationHelpers {

	private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

	public static Object parseEquation(Map<String, Object> varBindings, String equation) {
		try {
			return engine.eval(equation, new SimpleBindings(varBindings));
		} catch(ScriptException e) {
			return null;
		}
	}

	public static boolean validateEquationSuccess(final List<String> varNames, String equation) {
		if(equation.isEmpty()) { return false; }
		List<String> referencedVars = RegexHelpers.batchMatchOneGroup("([A-Za-z]\\w*)", equation);
		IPredicate<String> notInVarNames = new IPredicate<String>() {
			@Override
			public boolean invoke(String s) {
				return !varNames.contains(s);
			}
		};
		List<String> undefVars = Func.filter(notInVarNames, referencedVars);
		boolean parenCountMatches = StringHelpers.countOccurencesOfWord(equation, "(") == StringHelpers.countOccurencesOfWord(equation, ")");
		return undefVars.isEmpty() && parenCountMatches;
	}
}
