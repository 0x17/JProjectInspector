package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.evaluation.projects.UserProjects;
import org.andreschnabel.jprojectinspector.evaluation.survey.DeltaCalculator.Deltas;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Runner {

	public static void main(String[] args) throws Exception {
		//connectProjectsWithUsers();
		//showProjectWithoutUserCount();
		//showLanguageDistribution();
		//collectMetrics();
		//metricsResultsToCsv(',');
		//visualizeAllMetrics();
		//calcDeltas();
		checkCandidates();
	}

	private static void checkCandidates() {
		List<Benchmark.PredictionMethods> candidates = PredictionCandidates.getCandidates();
		Transform<Benchmark.PredictionMethods, Benchmark.Quality> candToQuality = new Transform<Benchmark.PredictionMethods, Benchmark.Quality>() {
			@Override
			public Benchmark.Quality invoke(Benchmark.PredictionMethods pm) {
				try {
					return Benchmark.countCorrectPredictions(pm);
				} catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		};
		List<Benchmark.Quality> qualities = ListHelpers.map(candToQuality, candidates);
		Benchmark.printWinner(qualities);
	}

	private static List<Deltas> calcDeltas() throws Exception {
		ProjectMetricsLst metrics = (ProjectMetricsLst)XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("metrics500.xml"));
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));
		
		List<Deltas> deltasLst = DeltaCalculator.calculateDeltas(rpl.responseProjs, metrics.projectMetrics);
		
		Predicate<Deltas> nonNull = new Predicate<DeltaCalculator.Deltas>() {
			@Override
			public boolean invoke(Deltas obj) {
				return obj.user != null;
			}
		};
		deltasLst = ListHelpers.filter(nonNull, deltasLst);
		
		Transform<Deltas, String> deltaToCsvLine = new Transform<DeltaCalculator.Deltas, String>() {
			@Override
			public String invoke(Deltas d) {
				return d.toCsvLine(',');
			}
		};
		
		List<String> lines = ListHelpers.map(deltaToCsvLine , deltasLst);
		
		StringBuilder sb = new StringBuilder();
		sb.append(Deltas.csvHeader(','));
		for(String line : lines) {
			sb.append(line);
		}
		
		FileHelpers.writeStrToFile(sb.toString(), "deltas.csv");
		
		return deltasLst;
	}

	private static void visualizeAllMetrics() throws Exception {
		visualize(Metric.LOC, "linesLoc.js");
		visualize(Metric.TLOC, "linesTloc.js");
		visualize(Metric.Contribs, "linesContribs.js");
		visualize(Metric.Commits, "linesCommits.js");
		visualize(Metric.Issues, "linesIssues.js");
	}

	private static void visualize(final Metric m, String outFilename) throws Exception {
		ProjectMetricsLst metrics = (ProjectMetricsLst)XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("metrics500.xml"));
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));
		String out = ResultVisualizer.resultsToJsArrays(rpl.responseProjs, metrics.projectMetrics, m);
		FileHelpers.writeStrToFile(out, outFilename);
	}

	private static void metricsResultsToCsv(Character sep) throws Exception {
		ProjectMetricsLst metrics = (ProjectMetricsLst)XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("metrics500.xml"));
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));
		StringBuilder sb = new StringBuilder();
		sb.append("owner,repo,loc,testloc,commits,contribs,issues,survey\n");
		for(ProjectMetrics projectMetric : metrics.projectMetrics) {
			sb.append(projectMetricsToCsvLine(projectMetric, sep, rpl.responseProjs));
		}
		FileHelpers.writeStrToFile(sb.toString(), "metrics500.csv");
	}

	private static String projectMetricsToCsvLine(ProjectMetrics pm, Character sep, List<ResponseProjects> responseProjs) {
		return pm.project.owner + sep + pm.project.repoName + sep +
				pm.linesOfCode + sep +
				pm.testLinesOfCode + sep +
				pm.numCommits + sep +
				pm.numContribs + sep +
				pm.numIssues + sep +
				surveyResultForProject(pm.project, responseProjs) + "\n";
	}

	private static String surveyResultForProject(Project p, List<ResponseProjects> rps) {
		for(ResponseProjects rp : rps) {
			if(rp.user != null && rp.user.equals(p.owner)) {
				StringBuilder sb = new StringBuilder();
				if(rp.highestBugCount.equals(p.repoName))
					sb.append("B");
				if(rp.lowestBugCount.equals(p.repoName))
					sb.append("b");
				if(rp.mostTested.equals(p.repoName))
					sb.append("T");
				if(rp.leastTested.equals(p.repoName))
					sb.append("t");
				String result = sb.toString();
				return result.isEmpty() ? "0" : result;
			}
		}

		return "0";
	}

	private static void collectMetrics() throws Exception {
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));
		ProjectMetricsLst metrics = MetricsCollector.collectMetricsForResponses(rpl);
		XmlHelpers.serializeToXml(metrics, new File("metrics500.xml"));
	}

	private static void showLanguageDistribution() throws Exception {
		List<Project> projs = LanguageDistribution.respProjectsToProjects(getProjectsWithUser());
		Map<String, Integer> distr = LanguageDistribution.determineLanguageDistribution(projs);
		for(String lang : distr.keySet()) {
			Helpers.log(lang + " => " + distr.get(lang));
		}
	}

	private static void showProjectWithoutUserCount() throws Exception {
		int count = countProjectsWithoutUser();
		System.out.println("Projs without user: " + count);
	}

	private static int countProjectsWithoutUser() throws Exception {
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));
		Predicate<ResponseProjects> isWithoutUser = new Predicate<ResponseProjects>() {
			@Override
			public boolean invoke(ResponseProjects rp) {
				return rp.user == null;
			}
		};
		return ListHelpers.count(isWithoutUser, rpl.responseProjs);
	}
	
	private static List<ResponseProjects> getProjectsWithUser() throws Exception {
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));
		Predicate<ResponseProjects> isWithUser = new Predicate<ResponseProjects>() {
			@Override
			public boolean invoke(ResponseProjects rp) {
				return rp.user != null;
			}
		};
		return ListHelpers.filter(isWithUser, rpl.responseProjs);
	}

	private static void connectProjectsWithUsers() throws Exception {
		List<ResponseProjects> results = SurveyProjectExtractor.extractProjectsFromResults(new File("responses500.csv"));
		UserProjects projs = (UserProjects)XmlHelpers.deserializeFromXml(UserProjects.class, new File("userprojects500.xml"));
		
		for(ResponseProjects rp : results) {			
			rp.user = UserGuesser.guessUserWithProjects(rp, projs);			
		}
		
		XmlHelpers.serializeToXml(new ResponseProjectsLst(results), new File("responses500.xml"));
	}

}
