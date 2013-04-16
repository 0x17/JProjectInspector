package org.andreschnabel.jprojectinspector.metrics.survey;

import org.andreschnabel.jprojectinspector.metrics.SurveyMetric;
import org.andreschnabel.jprojectinspector.model.Project;

import java.io.File;
import java.util.List;

public class TestEffortEstimation implements SurveyMetric {
	public static final String LEAST_TESTED_HEADER = "Which of your GitHub projects was tested the least?";
	public static final String MOST_TESTED_HEADER = "Which of your GitHub projects was tested the most?";

	@Override
	public String getName() {
		return "testeffort";
	}

	@Override
	public String getDescription() {
		return "Estimation of relative test effort (lowest/highest) project the user asked in the survey maintains.";
	}

	@Override
	public Estimation measure(List<String> surveyUsers, File responseCsv, Project p) {
		return SurveyMetricCommon.measureCommon(surveyUsers, responseCsv, p, LEAST_TESTED_HEADER, MOST_TESTED_HEADER);
	}
}
