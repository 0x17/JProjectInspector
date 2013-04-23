package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.evaluation.Benchmark;
import org.andreschnabel.jprojectinspector.evaluation.PredictionCandidates;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.ITransform;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.XmlHelpers;

import java.io.File;
import java.util.List;

public class BenchmarkRunner {

	public static void main(String[] args) throws Exception {
		List<Benchmark.Quality> qualities = checkCandidates();
		Helpers.log(""+qualities);
	}

	public static List<Benchmark.Quality> checkCandidates() throws Exception {
		final ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responseswithuser500.xml"));

		final List<ProjectWithResults> pms = ProjectWithResults.fromCsv(CsvHelpers.parseCsv(new File("data/benchmark/metrics500.csv")));

		final List<Benchmark.PredictionMethods> candidates = PredictionCandidates.getCandidates();
		ITransform<Benchmark.PredictionMethods, Benchmark.Quality> candToQuality = new ITransform<Benchmark.PredictionMethods, Benchmark.Quality>() {
			@Override
			public Benchmark.Quality invoke(Benchmark.PredictionMethods pm) {
				try {
					return Benchmark.runBenchmark(pm, pms, rpl.responseProjs);
				} catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		};

		return Func.map(candToQuality, candidates);
	}

}
