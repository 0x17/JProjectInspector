package org.andreschnabel.jprojectinspector.metrics;

import org.andreschnabel.jprojectinspector.model.Project;

public interface OnlineMetric {

	public String getName();
	public String getDescription();

	public float measure(Project p) throws Exception;

}
