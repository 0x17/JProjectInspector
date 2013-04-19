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

	public double measure(Project p) throws Exception;

}
