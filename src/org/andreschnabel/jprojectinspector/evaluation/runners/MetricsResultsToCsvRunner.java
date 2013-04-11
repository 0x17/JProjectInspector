package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.evaluation.survey.ProjectMetrics;
import org.andreschnabel.jprojectinspector.evaluation.survey.ProjectMetricsLst;
import org.andreschnabel.jprojectinspector.evaluation.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.evaluation.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.List;

public class MetricsResultsToCsvRunner {

	public static void main(String[] args) throws Exception {
		metricsResultsToCsv(',');
	}

	public static void metricsResultsToCsv(Character sep) throws Exception {
		ProjectMetricsLst metrics = (ProjectMetricsLst) XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("metrics500.xml"));
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));
		StringBuilder sb = new StringBuilder();
		sb.append("owner,repo,loc,testloc,commits,contribs,issues,survey\n");
		for(ProjectMetrics projectMetric : metrics.projectMetrics) {
			sb.append(projectMetricsToCsvLine(projectMetric, sep, rpl.responseProjs));
		}
		FileHelpers.writeStrToFile(sb.toString(), "metrics500.csv");
	}

	public static String projectMetricsToCsvLine(ProjectMetrics pm, Character sep, List<ResponseProjects> responseProjs) {
		return pm.project.owner + sep + pm.project.repoName + sep +
				pm.linesOfCode + sep +
				pm.testLinesOfCode + sep +
				pm.numCommits + sep +
				pm.numContribs + sep +
				pm.numIssues + sep +
				surveyResultForProject(pm.project, responseProjs) + "\n";
	}

	public static String surveyResultForProject(Project p, List<ResponseProjects> rps) {
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

}
