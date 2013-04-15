package org.andreschnabel.jprojectinspector.metrics.survey;

import org.andreschnabel.jprojectinspector.metrics.SurveyMetric;
import org.andreschnabel.jprojectinspector.model.Project;

import java.io.File;

public class BugCountEstimation implements SurveyMetric {

	@Override
	public String getName() {
		return "bugcount";
	}

	@Override
	public String getDescription() {
		return "Estimation of relative bug count (lowest/highest) of project the user asked in the survey maintains.";
	}

	@Override
	public Estimation measure(File data, Project p) {
		return null;
	}
}
