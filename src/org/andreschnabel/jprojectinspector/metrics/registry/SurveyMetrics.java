package org.andreschnabel.jprojectinspector.metrics.registry;

import org.andreschnabel.jprojectinspector.metrics.SurveyMetric;
import org.andreschnabel.jprojectinspector.metrics.survey.BugCountEstimation;
import org.andreschnabel.jprojectinspector.metrics.survey.TestEffortEstimation;

import java.util.HashMap;
import java.util.Map;

public class SurveyMetrics {
	public static Map<String, SurveyMetric> init() {
		Map<String, SurveyMetric> surveyMetrics = new HashMap<String, SurveyMetric>();
		surveyMetrics.put("bugcountestimate", new BugCountEstimation());
		surveyMetrics.put("testeffortestimate", new TestEffortEstimation());
		return surveyMetrics;
	}
}
