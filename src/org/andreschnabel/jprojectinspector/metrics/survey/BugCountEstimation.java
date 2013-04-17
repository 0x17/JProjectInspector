package org.andreschnabel.jprojectinspector.metrics.survey;

import org.andreschnabel.jprojectinspector.metrics.SurveyMetric;
import org.andreschnabel.jprojectinspector.model.Project;

public class BugCountEstimation implements SurveyMetric {
	public final static String LOWEST_BUG_COUNT_HEADER = "Which of your GitHub projects do you suspect to have the smallest number of undetected bugs?";
	public final static String HIGHEST_BUG_COUNT_HEADER = "Which of your GitHub projects do you suspect to have the biggest number of undetected bugs?";

	@Override
	public String getName() {
		return "bugcount";
	}

	@Override
	public String getDescription() {
		return "Estimation of relative bug count (lowest/highest) of project the user asked in the survey maintains.";
	}

	@Override
	public Estimation measure(Project p) {
		return SurveyMetricCommon.measureCommon(p, LOWEST_BUG_COUNT_HEADER, HIGHEST_BUG_COUNT_HEADER);
	}
}
