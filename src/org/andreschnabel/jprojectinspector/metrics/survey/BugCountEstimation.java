package org.andreschnabel.jprojectinspector.metrics.survey;

import org.andreschnabel.jprojectinspector.evaluation.SurveyFormat;
import org.andreschnabel.jprojectinspector.metrics.ISurveyMetric;
import org.andreschnabel.jprojectinspector.model.Project;

/**
 * Einschätzung der Fehlerzahl.
 */
public class BugCountEstimation implements ISurveyMetric {

	@Override
	public String getName() {
		return "BugCountEstimate";
	}

	@Override
	public String getDescription() {
		return "Estimation of relative bug count (lowest/highest) of project the user asked in the survey maintains.";
	}

	@Override
	public Estimation measure(Project p) {
		return SurveyMetricCommon.measureCommon(p, SurveyFormat.LOWEST_BUG_COUNT_HEADER, SurveyFormat.HIGHEST_BUG_COUNT_HEADER);
	}
}
