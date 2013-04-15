package org.andreschnabel.jprojectinspector.metrics;

import org.andreschnabel.jprojectinspector.metrics.survey.Estimation;
import org.andreschnabel.jprojectinspector.model.Project;

import java.io.File;

public interface SurveyMetric {

	public String getName();
	public String getDescription();

	public Estimation measure(File data, Project p);

}
