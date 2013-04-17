package org.andreschnabel.jprojectinspector.model.metrics;


import org.andreschnabel.jprojectinspector.model.Project;

import java.util.Map;

public class TestPrevalenceSummary {
	public String keyword;
	public int numTestedProjects;
	public int numProjectsTotal;
	public float testPrevalence;
	public Map<Project, Boolean> projectTestMap;
}