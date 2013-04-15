package org.andreschnabel.jprojectinspector.metrics;

import org.andreschnabel.jprojectinspector.model.Project;

public interface OnlineMetric {

	public static enum Category {
		Scraping,
		GitHubApi
	}

	public String getName();
	public String getDescription();

	public Category getCategory();

	public float measure(Project p) throws Exception;

}
