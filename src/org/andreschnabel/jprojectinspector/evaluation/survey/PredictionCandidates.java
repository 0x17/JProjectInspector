package org.andreschnabel.jprojectinspector.evaluation.survey;

import java.util.LinkedList;
import java.util.List;

public class PredictionCandidates {

	public static List<Benchmark.PredictionMethods> getCandidates() {
		List<Benchmark.PredictionMethods> candidates = new LinkedList<Benchmark.PredictionMethods>();
		candidates.add(new Benchmark.PredictionMethods() {
			@Override
			public String getName() { return "method1"; }
			@Override
			public float testEffortPredictionMeasure(ProjectMetrics m) {
				return (float) m.testLinesOfCode / (m.linesOfCode + 1.0f);
			}
			@Override
			public float bugCountPredictionMeasure(ProjectMetrics m) {
				return 1.0f / (m.testLinesOfCode + 1.0f) * (float) m.linesOfCode;
				//return (m.linesOfCode - m.testLinesOfCode) / (m.linesOfCode + 1.0f);
			}
		});
		candidates.add(new Benchmark.PredictionMethods() {
			@Override
			public String getName() { return "method2"; }
			@Override
			public float testEffortPredictionMeasure(ProjectMetrics m) {
				return (float)m.numContribs / (m.linesOfCode / 1000.0f);
			}
			@Override
			public float bugCountPredictionMeasure(ProjectMetrics m) {
				return (float)m.numCommits / (m.linesOfCode / 1000.0f);
			}
		});
		candidates.add(new Benchmark.PredictionMethods() {
			@Override
			public String getName() { return "method3"; }
			@Override
			public float testEffortPredictionMeasure(ProjectMetrics m) {
				if(m.testLinesOfCode == 0)
					return (float) m.testLinesOfCode / (m.linesOfCode + 1.0f);
				else
					return m.testLinesOfCode;
			}
			@Override
			public float bugCountPredictionMeasure(ProjectMetrics m) {
				if(m.testLinesOfCode == 0)
					return 1.0f / (m.testLinesOfCode + 1.0f) * (float) m.linesOfCode;
				else
					return 1.0f / m.testLinesOfCode;
			}
		});
		candidates.add(new Benchmark.PredictionMethods() {
			@Override
			public String getName() { return "method4"; }
			@Override
			public float testEffortPredictionMeasure(ProjectMetrics m) {
				float term1 = (float) m.testLinesOfCode / (m.linesOfCode + 1.0f);
				float term2 = (float)m.numCommits / (m.linesOfCode / 1000.0f);
				return (term1+term2)/2.0f;
			}
			@Override
			public float bugCountPredictionMeasure(ProjectMetrics m) {
				float term1 = 1.0f / (m.testLinesOfCode + 1.0f) * (float) m.linesOfCode;
				float term2 = (float)m.numCommits / (m.linesOfCode / 1000.0f);
				return (term1+term2)/2.0f;
			}
		});
		return candidates;
	}

}
