package org.andreschnabel.jprojectinspector.testprevalence;

import java.util.List;

public class ProjectList {
	public String keyword;
	public List<Project> projects;
	public ProjectList(String keyword, List<Project> projects) {
		this.keyword = keyword;
		this.projects = projects;
	}
}