package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.evaluation.projects.UserProjects;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
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
		metricsResultsToCsv(',');
	}

	private static void metricsResultsToCsv(Character sep) throws Exception {
		ProjectMetricsLst metrics = (ProjectMetricsLst)XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("metrics500.xml"));
		StringBuilder sb = new StringBuilder();
		sb.append("owner,repo,loc,testloc,commits,contribs,issues\n");
		for(ProjectMetrics projectMetric : metrics.projectMetrics) {
			sb.append(projectMetricsToCsvLine(projectMetric, sep));
		}
		FileHelpers.writeStrToFile(sb.toString(), "metrics500.csv");
	}

	private static String projectMetricsToCsvLine(ProjectMetrics pm, Character sep) {
		return pm.project.owner + sep + pm.project.repoName + sep +
				pm.linesOfCode + sep +
				pm.testLinesOfCode + sep +
				pm.numCommits + sep +
				pm.numContribs + sep +
				pm.numIssues + "\n";
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
