package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.evaluation.Benchmark;
import org.andreschnabel.jprojectinspector.evaluation.PredictionCandidates;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.pecker.functional.Func;
import org.andreschnabel.pecker.functional.ITransform;
import org.andreschnabel.pecker.helpers.Helpers;
import org.andreschnabel.pecker.serialization.CsvHelpers;
import org.andreschnabel.pecker.serialization.XmlHelpers;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BenchmarkRunner {

	public static void main(String[] args) throws Exception {
		List<Benchmark.Quality> qualities = checkCandidates();
		Helpers.log(""+qualities);
	}

	public static List<Benchmark.Quality> checkCandidates() throws Exception {
		final ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responseswithuser500.xml"));

		final List<ProjectWithResults> pms = ProjectWithResults.fromCsv(CsvHelpers.parseCsv(new File("data/benchmark/metrics500.csv")));

		final Map<Project, ProjectWithResults> pml = new HashMap<Project, ProjectWithResults>();
		for(ProjectWithResults pwr : pms) {
			pml.put(pwr.project, pwr);
		}

		final List<Benchmark.PredictionMethods> candidates = PredictionCandidates.getCandidates();
		ITransform<Benchmark.PredictionMethods, Benchmark.Quality> candToQuality = new ITransform<Benchmark.PredictionMethods, Benchmark.Quality>() {
			@Override
			public Benchmark.Quality invoke(Benchmark.PredictionMethods pm) {
				try {
					return Benchmark.runBenchmark(pm, pml, rpl.responseProjs, false);
				} catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		};

		return Func.map(candToQuality, candidates);
	}

}
