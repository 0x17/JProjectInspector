package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.utilities.helpers.EquationHelpers;

/**
 * Implementiere PredictionMethods-Schnittstelle für Benchmark mithilfe von Formel,
 * welche in Textform gegeben worden ist.
 */
public class PredictionMethodsFromString implements Benchmark.PredictionMethods {

	private final String eqtn;

	public PredictionMethodsFromString(String eqtn) {
		this.eqtn = eqtn;

	}

	@Override
	public String getName() {
		return "gui-input";
	}

	@Override
	public double testEffortPredictionMeasure(ProjectWithResults m) {
		return EquationHelpers.evaluateEquationForProjectWithResultBindings(m, eqtn);
	}

	@Override
	public double bugCountPredictionMeasure(ProjectWithResults m) {
		return EquationHelpers.evaluateEquationForProjectWithResultBindings(m, eqtn);
	}

}
