package org.andreschnabel.jprojectinspector.model;

import java.util.List;

public class ProjectList {
	public final String keyword;
	public final List<Project> projects;
	public ProjectList(String keyword, List<Project> projects) {
		this.keyword = keyword;
		this.projects = projects;
	}
}