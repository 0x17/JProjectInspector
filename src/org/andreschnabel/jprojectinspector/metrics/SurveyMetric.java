package org.andreschnabel.jprojectinspector.metrics;

import org.andreschnabel.jprojectinspector.metrics.survey.Estimation;
import org.andreschnabel.jprojectinspector.model.Project;

import java.io.File;
import java.util.List;

public interface SurveyMetric {

	public String getName();
	public String getDescription();

	public Estimation measure(List<String> surveyUsers, File data, Project p);

}
