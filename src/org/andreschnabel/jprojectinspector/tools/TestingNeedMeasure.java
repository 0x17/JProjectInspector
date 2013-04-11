package org.andreschnabel.jprojectinspector.tools;

import org.andreschnabel.jprojectinspector.evaluation.survey.ProjectMetrics;

public interface TestingNeedMeasure {
	float invoke(ProjectMetrics metrics);
}
