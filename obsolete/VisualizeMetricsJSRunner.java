package org.andreschnabel.jprojectinspector.evaluation.survey.runners;

import org.andreschnabel.jprojectinspector.evaluation.survey.Metric;

public class VisualizeMetricsJSRunner {

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
		//ProjectMetricsLst metrics = (ProjectMetricsLst)XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("data/metrics500.xml"));
		//ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responseswithuser500.xml"));
		//String out = ResultVisualizerJS.resultsToJsArrays(rpl.responseProjs, metrics.projectMetrics, m);
		//FileHelpers.writeStrToFile(out, outFilename);
	}

}