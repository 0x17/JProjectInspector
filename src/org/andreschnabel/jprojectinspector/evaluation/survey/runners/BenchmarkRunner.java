package org.andreschnabel.jprojectinspector.evaluation.survey.runners;

import org.andreschnabel.jprojectinspector.evaluation.survey.Benchmark;
import org.andreschnabel.jprojectinspector.evaluation.survey.PredictionCandidates;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.XmlHelpers;

import java.io.File;
import java.util.List;

public class BenchmarkRunner {

	public static void main(String[] args) throws Exception {
		checkCandidates();
	}

	public static void checkCandidates() throws Exception {
		//final ProjectMetricsLst metrics = (ProjectMetricsLst) XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("data/metrics500.xml"));
		final ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responseswithuser500.xml"));

		final List<ProjectWithResults> pms = ProjectWithResults.fromCsv(CsvHelpers.parseCsv(new File("data/benchmark/metrics500.csv")));

		final List<Benchmark.PredictionMethods> candidates = PredictionCandidates.getCandidates();
		Transform<Benchmark.PredictionMethods, Benchmark.Quality> candToQuality = new Transform<Benchmark.PredictionMethods, Benchmark.Quality>() {
			@Override
			public Benchmark.Quality invoke(Benchmark.PredictionMethods pm) {
				try {
					return Benchmark.countCorrectPredictions(pm, pms, rpl.responseProjs);
				} catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		};
		List<Benchmark.Quality> qualities = Func.map(candToQuality, candidates);
		Benchmark.printWinner(qualities);
	}

}
