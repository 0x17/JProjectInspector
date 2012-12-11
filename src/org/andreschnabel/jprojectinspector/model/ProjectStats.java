package org.andreschnabel.jprojectinspector.model;

public class ProjectStats {
	public boolean containsTest;

	// Code properties
	public int linesOfCode;
	public float mcCabe;
	public float coupling;

	// Test properties
	public float testCoverage;
	public int testLinesOfCode;
	public int numTestContributors;

	// Project properties
	public int numContributors;
	public int codeFrequency;
	public long projectAge;
	public int selectivity;
	public int numIssues;
}
