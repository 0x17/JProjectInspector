package org.andreschnabel.jprojectinspector.utilities.helpers;

import de.congrace.exp4j.ExpressionBuilder;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.IPredicate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hilfsfunktionen zum Auswerten von Formeln in Zeichenkettendarstellung.
 */
public class EquationHelpers {

	public static Object parseEquation(Map<String, Double> varBindings, String equation) {
		ExpressionBuilder builder = new ExpressionBuilder(equation);
		builder.withVariables(varBindings);
      	try {
			return builder.build().calculate();
		} catch (Exception e) {
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

	public static double evaluateEquationForProjectWithResultBindings(ProjectWithResults m, String equation) {
		Map<String, Double> bindings = resultsToBindings(m);
		Object result = parseEquation(bindings, equation);
		if(result != null) {
			return (Double)result;
		}
		return Double.NaN;
	}

	public static Map<String, Double> resultsToBindings(ProjectWithResults m) {
		Map<String, Double> bindings = new HashMap<String, Double>();
		String[] headers = m.getResultHeaders();
		for(int i=0; i<m.results.length; i++) {
			bindings.put(headers[i], m.results[i]);
		}
		return bindings;
	}
}
