package org.andreschnabel.jprojectinspector.metrics.registry;

import org.andreschnabel.jprojectinspector.metrics.ISurveyMetric;
import org.andreschnabel.jprojectinspector.metrics.survey.BugCountEstimation;
import org.andreschnabel.jprojectinspector.metrics.survey.TestEffortEstimation;

import java.util.HashMap;
import java.util.Map;

/**
 * Liste bekannter Umfrage-Metriken.
 */
public class SurveyMetrics {
	public static Map<String, ISurveyMetric> init() {
		Map<String, ISurveyMetric> surveyMetrics = new HashMap<String, ISurveyMetric>();
		surveyMetrics.put("bugcountestimate", new BugCountEstimation());
		surveyMetrics.put("testeffortestimate", new TestEffortEstimation());
		return surveyMetrics;
	}
}
