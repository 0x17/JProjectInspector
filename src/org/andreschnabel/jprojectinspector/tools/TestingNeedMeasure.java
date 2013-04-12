package org.andreschnabel.jprojectinspector.tools;

import org.andreschnabel.jprojectinspector.model.metrics.ProjectMetrics;

public interface TestingNeedMeasure {
	float invoke(ProjectMetrics metrics);
}
