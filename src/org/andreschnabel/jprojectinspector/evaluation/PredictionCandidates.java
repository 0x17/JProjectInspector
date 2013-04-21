package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.model.ProjectWithResults;

import java.util.LinkedList;
import java.util.List;

public class PredictionCandidates {

	public static List<Benchmark.PredictionMethods> getCandidates() {
		List<Benchmark.PredictionMethods> candidates = new LinkedList<Benchmark.PredictionMethods>();
		candidates.add(new Benchmark.PredictionMethods() {
			@Override
			public String getName() { return "method1"; }
			@Override
			public double testEffortPredictionMeasure(ProjectWithResults m) {
				return m.get("testloc") / (m.get("loc") + 1.0);
			}
			@Override
			public double bugCountPredictionMeasure(ProjectWithResults m) {
				return 1.0 / (m.get("testloc") + 1.0) * m.get("loc");
				//return (m.linesOfCode - m.testLinesOfCode) / (m.linesOfCode + 1.0f);
			}
		});
		return candidates;
	}

}
