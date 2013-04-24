package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.utilities.helpers.EquationHelpers;

import java.util.HashMap;
import java.util.Map;

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
		return common(m);
	}

	@Override
	public double bugCountPredictionMeasure(ProjectWithResults m) {
		return common(m);
	}

	private double common(ProjectWithResults m) {
		Map<String, Object> bindings = resultsToBindings(m);
		Object result = EquationHelpers.parseEquation(bindings, eqtn);
		if(result != null) {
			return (Double)result;
		}
		return Double.NaN;
	}

	private Map<String, Object> resultsToBindings(ProjectWithResults m) {
		Map<String, Object> bindings = new HashMap<String, Object>();
		String[] headers = m.getResultHeaders();
		for(int i=0; i<m.results.length; i++) {
			bindings.put(headers[i], m.results[i]);
		}
		return bindings;
	}
}
