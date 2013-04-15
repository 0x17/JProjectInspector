package org.andreschnabel.jprojectinspector.metrics.survey;

import org.andreschnabel.jprojectinspector.metrics.SurveyMetric;
import org.andreschnabel.jprojectinspector.model.Project;

import java.io.File;

public class TestEffortEstimation implements SurveyMetric {
	@Override
	public String getName() {
		return "testeffort";
	}

	@Override
	public String getDescription() {
		return "Estimation of relative test effort (lowest/highest) project the user asked in the survey maintains.";
	}

	@Override
	public Estimation measure(File data, Project p) {
		return null;
	}
}
