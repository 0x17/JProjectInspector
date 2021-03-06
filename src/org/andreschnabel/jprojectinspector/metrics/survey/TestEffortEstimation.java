package org.andreschnabel.jprojectinspector.metrics.survey;

import org.andreschnabel.jprojectinspector.evaluation.SurveyFormat;
import org.andreschnabel.jprojectinspector.metrics.ISurveyMetric;
import org.andreschnabel.jprojectinspector.model.Project;

/**
 * Einschätzung des Testaufwands.
 */
public class TestEffortEstimation implements ISurveyMetric {

	@Override
	public String getName() {
		return "TestEffortEstimate";
	}

	@Override
	public String getDescription() {
		return "Estimation of relative test effort (lowest/highest) project the user asked in the survey maintains.";
	}

	@Override
	public Estimation measure(Project p) {
		return SurveyMetricCommon.measureCommon(p, SurveyFormat.LEAST_TESTED_HEADER, SurveyFormat.MOST_TESTED_HEADER);
	}
}
