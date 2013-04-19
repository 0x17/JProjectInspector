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
		/*candidates.add(new Benchmark.PredictionMethods() {
			@Override
			public String getName() { return "method2"; }
			@Override
			public Double testEffortPredictionMeasure(ProjectMetrics m) {
				return (Double)m.numContribs / (m.linesOfCode / 1000.0f);
			}
			@Override
			public Double bugCountPredictionMeasure(ProjectMetrics m) {
				return (Double)m.numCommits / (m.linesOfCode / 1000.0f);
			}
		});
		candidates.add(new Benchmark.PredictionMethods() {
			@Override
			public String getName() { return "method3"; }
			@Override
			public Double testEffortPredictionMeasure(ProjectMetrics m) {
				if(m.testLinesOfCode != 0)
					return (Double) m.testLinesOfCode / (m.linesOfCode+1);
				else
					return m.linesOfCode;
			}
			@Override
			public Double bugCountPredictionMeasure(ProjectMetrics m) {
				if(m.testLinesOfCode == 0)
					return 1.0f / (m.testLinesOfCode + 1.0f) * (Double) m.linesOfCode;
				else
					return 1.0f / m.testLinesOfCode;
			}
		});
		candidates.add(new Benchmark.PredictionMethods() {
			@Override
			public String getName() { return "method4"; }
			@Override
			public Double testEffortPredictionMeasure(ProjectMetrics m) {
				Double term1 = (Double) m.testLinesOfCode / (m.linesOfCode + 1.0f);
				Double term2 = (Double)m.numCommits / (m.linesOfCode / 1000.0f);
				return (term1+term2)/2.0f;
			}
			@Override
			public Double bugCountPredictionMeasure(ProjectMetrics m) {
				Double term1 = 1.0f / (m.testLinesOfCode + 1.0f) * (Double) m.linesOfCode;
				Double term2 = (Double)m.numCommits / (m.linesOfCode / 1000.0f);
				return (term1+term2)/2.0f;
			}
		});*/
		return candidates;
	}

}
