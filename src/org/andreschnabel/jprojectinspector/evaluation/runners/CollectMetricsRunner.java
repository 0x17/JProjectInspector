package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.evaluation.survey.MetricsCollector;
import org.andreschnabel.jprojectinspector.evaluation.survey.ProjectMetricsLst;
import org.andreschnabel.jprojectinspector.evaluation.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;

public class CollectMetricsRunner {

	public static void main(String[] args) throws Exception {
		collectMetrics();
	}

	public static void collectMetrics() throws Exception {
		ResponseProjectsLst rpl = (ResponseProjectsLst) XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));
		ProjectMetricsLst metrics = MetricsCollector.collectMetricsForResponses(rpl);
		XmlHelpers.serializeToXml(metrics, new File("metrics500.xml"));
	}

}
