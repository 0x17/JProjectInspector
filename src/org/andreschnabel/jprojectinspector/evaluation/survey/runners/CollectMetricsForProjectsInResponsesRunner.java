package org.andreschnabel.jprojectinspector.evaluation.survey.runners;

import org.andreschnabel.jprojectinspector.evaluation.survey.MetricsCollector;
import org.andreschnabel.jprojectinspector.model.metrics.ProjectMetricsLst;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.utilities.serialization.XmlHelpers;

import java.io.File;

public class CollectMetricsForProjectsInResponsesRunner {

	public static void main(String[] args) throws Exception {
		collectMetrics();
	}

	public static void collectMetrics() throws Exception {
		ResponseProjectsLst rpl = (ResponseProjectsLst) XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responseswithuser500.xml"));
		ProjectMetricsLst metrics = MetricsCollector.collectMetricsForResponses(rpl);
		XmlHelpers.serializeToXml(metrics, new File("metrics500.xml"));
	}

}
