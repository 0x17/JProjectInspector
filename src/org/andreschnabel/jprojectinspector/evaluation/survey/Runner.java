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
		countCorrectPredictions(1.0f);
	}

	private static void countCorrectPredictions(final float k) throws Exception {
		ProjectMetricsLst metrics = (ProjectMetricsLst)XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("metrics500.xml"));
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));
		final List<ProjectMetrics> pml = metrics.projectMetrics;

		int teCorrect = 0;
		int bcCorrect = 0;
		int total = 0;

		for(ResponseProjects rp : rpl.responseProjs) {
			if(rp.user == null) continue;

			List<Project> projs = rp.toProjectList();
			total += projs.size();

			Transform<Project, Float> bcPred = new Transform<Project, Float>() {
				@Override
				public Float invoke(Project p) {
					ProjectMetrics m = metricsForProject(p, pml);
					if(m != null) {
						int loc = m.linesOfCode;
						int tloc = m.testLinesOfCode;
						return bugPredictionMeasure(loc, tloc, k);
					} else return 0.0f;
				}
			};
			List<Float> bcPredVals = ListHelpers.map(bcPred, projs);

			int lowestPredIx = 0, highestPredIx = 0;
			float min, max;

			max = 0;
			for(int i=0; i<projs.size(); i++) {
				if(bcPredVals.get(i) > max) {
					max = bcPredVals.get(i);
					highestPredIx = i;
				}
			}

			min = 0;
			for(int i=0; i<projs.size(); i++) {
				if(bcPredVals.get(i) < min) {
					min = bcPredVals.get(i);
					lowestPredIx = i;
				}
			}

			if(rp.highestBugCount.equals(projs.get(highestPredIx).repoName))
				bcCorrect++;
			if(rp.lowestBugCount.equals(projs.get(lowestPredIx).repoName))
				bcCorrect++;

			Transform<Project, Float> tePred = new Transform<Project, Float>() {
				@Override
				public Float invoke(Project p) {
					ProjectMetrics m = metricsForProject(p, pml);
					if(m != null) {
						int loc = m.linesOfCode;
						int tloc = m.testLinesOfCode;
						return testEffortPredictionMeasure(loc, tloc, k);
					} else return 0.0f;
				}
			};
			List<Float> tePredVals = ListHelpers.map(tePred, projs);

			lowestPredIx = highestPredIx = 0;

			max = 0;
			for(int i=0; i<projs.size(); i++) {
				if(tePredVals.get(i) > max) {
					max = tePredVals.get(i);
					highestPredIx = i;
				}
			}

			min = 0;
			for(int i=0; i<projs.size(); i++) {
				if(tePredVals.get(i) < min) {
					min = tePredVals.get(i);
					lowestPredIx = i;
				}
			}

			if(rp.mostTested.equals(projs.get(highestPredIx).repoName))
				teCorrect++;
			if(rp.leastTested.equals(projs.get(lowestPredIx).repoName))
				teCorrect++;
		}

		Helpers.log("k = " + k);
		Helpers.log("Correct test effort predictions = " + teCorrect + " of " + total);
		Helpers.log("Correct bug count predictions = " + bcCorrect + " of " + total);
	}

	private static float testEffortPredictionMeasure(int loc, int tloc, float k) {
		return k * tloc / (loc + 1);
	}

	private static float bugPredictionMeasure(int loc, int tloc, float k) {
		return k / (tloc + 1) * loc;
	}

	public static ProjectMetrics metricsForProject(final Project p, List<ProjectMetrics> pml) {
		Predicate<ProjectMetrics> isProj = new Predicate<ProjectMetrics>() {
			@Override
			public boolean invoke(ProjectMetrics obj) {
				return obj.project.equals(p);
			}
		};
		return ListHelpers.find(isProj, pml);
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
