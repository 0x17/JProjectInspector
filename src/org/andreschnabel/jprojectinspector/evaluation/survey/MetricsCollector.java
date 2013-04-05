package org.andreschnabel.jprojectinspector.evaluation.survey;

import java.util.LinkedList;
import java.util.List;

public class MetricsCollector {

	public static ResponseMetricsLst collectMetricsForResponses(ResponseProjectsLst rpl) {
		List<ResponseMetrics> results = new LinkedList<ResponseMetrics>();

		for(ResponseProjects responseProj : rpl.responseProjs) {
			results.add(collectMetricsForResponse(responseProj));
		}

		ResponseMetricsLst rml = new ResponseMetricsLst(results);
		return rml;
	}

	public static ResponseMetrics collectMetricsForResponse(ResponseProjects rp) {
		return null;
	}

}
