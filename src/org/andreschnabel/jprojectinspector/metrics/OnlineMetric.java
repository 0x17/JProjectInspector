package org.andreschnabel.jprojectinspector.metrics;

import org.andreschnabel.jprojectinspector.model.Project;

public interface OnlineMetric {

	public String getName();
	public float measure(Project p) throws Exception;

}
