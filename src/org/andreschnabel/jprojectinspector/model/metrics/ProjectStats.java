package org.andreschnabel.jprojectinspector.model.metrics;

import org.andreschnabel.jprojectinspector.model.Project;

public class ProjectStats {
	public ProjectStats(Project project) {
		owner = project.owner;
		name = project.repoName;
	}

	public final String owner;
	public final String name;

	public boolean containsTest;

	// Code properties
	public int linesOfCode;
	public float avgWMC;
	public float avgCoupling;

	// Test properties
	public float testCoverage;
	public int testLinesOfCode;
	//public int numTestContributors;

	// Project properties
	public int numContributors;
	public int codeFrequency;
	public long projectAge;
	public int selectivity;
	public int numIssues;
}
