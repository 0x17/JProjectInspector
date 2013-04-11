package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.evaluation.survey.Benchmark;
import org.andreschnabel.jprojectinspector.evaluation.survey.PredictionCandidates;
import org.andreschnabel.jprojectinspector.evaluation.survey.ProjectMetricsLst;
import org.andreschnabel.jprojectinspector.evaluation.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.utilities.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.List;

public class BenchmarkRunner {

	public static void main(String[] args) throws Exception {
		checkCandidates();
	}

	public static void checkCandidates() throws Exception {
		final ProjectMetricsLst metrics = (ProjectMetricsLst) XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("metrics500.xml"));
		final ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));

		List<Benchmark.PredictionMethods> candidates = PredictionCandidates.getCandidates();
		Transform<Benchmark.PredictionMethods, Benchmark.Quality> candToQuality = new Transform<Benchmark.PredictionMethods, Benchmark.Quality>() {
			@Override
			public Benchmark.Quality invoke(Benchmark.PredictionMethods pm) {
				try {
					return Benchmark.countCorrectPredictions(pm, metrics.projectMetrics, rpl.responseProjs);
				} catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		};
		List<Benchmark.Quality> qualities = ListHelpers.map(candToQuality, candidates);
		Benchmark.printWinner(qualities);
	}

}
