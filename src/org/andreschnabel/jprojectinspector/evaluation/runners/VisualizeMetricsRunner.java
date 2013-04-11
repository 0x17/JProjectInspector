package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.evaluation.survey.Metric;
import org.andreschnabel.jprojectinspector.evaluation.survey.ProjectMetricsLst;
import org.andreschnabel.jprojectinspector.evaluation.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.evaluation.survey.ResultVisualizer;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;

public class VisualizeMetricsRunner {

	public static void main(String[] args) throws Exception {
		visualizeAllMetrics();
	}

	public static void visualizeAllMetrics() throws Exception {
		visualize(Metric.LOC, "linesLoc.js");
		visualize(Metric.TLOC, "linesTloc.js");
		visualize(Metric.Contribs, "linesContribs.js");
		visualize(Metric.Commits, "linesCommits.js");
		visualize(Metric.Issues, "linesIssues.js");
	}

	public static void visualize(final Metric m, String outFilename) throws Exception {
		ProjectMetricsLst metrics = (ProjectMetricsLst) XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("metrics500.xml"));
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));
		String out = ResultVisualizer.resultsToJsArrays(rpl.responseProjs, metrics.projectMetrics, m);
		FileHelpers.writeStrToFile(out, outFilename);
	}

}
