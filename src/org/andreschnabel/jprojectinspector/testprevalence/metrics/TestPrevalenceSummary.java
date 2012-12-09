package org.andreschnabel.jprojectinspector.testprevalence.metrics;

import org.andreschnabel.jprojectinspector.testprevalence.model.Project;

import java.util.Map;

public class TestPrevalenceSummary {
	public String keyword;
	public int numTestedProjects;
	public int numProjectsTotal;
	public float testPrevalence;
	public Map<Project, Boolean> projectTestMap;
}