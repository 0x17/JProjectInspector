package org.andreschnabel.jprojectinspector.model.metrics;

import org.andreschnabel.jprojectinspector.model.Project;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "projectmetrics")
public class ProjectMetrics {

	public Project project;

	public int linesOfCode;
	public int testLinesOfCode;

	public int numCommits;
	public int numContribs;

	public int numIssues;

	public ProjectMetrics() {
	}

	public ProjectMetrics(Project project, int linesOfCode, int testLinesOfCode, int numCommits, int numContribs, int numIssues) {
		this.project = project;
		this.linesOfCode = linesOfCode;
		this.testLinesOfCode = testLinesOfCode;
		this.numCommits = numCommits;
		this.numContribs = numContribs;
		this.numIssues = numIssues;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		ProjectMetrics that = (ProjectMetrics) o;

		if(linesOfCode != that.linesOfCode) return false;
		if(numCommits != that.numCommits) return false;
		if(numContribs != that.numContribs) return false;
		if(numIssues != that.numIssues) return false;
		if(testLinesOfCode != that.testLinesOfCode) return false;
		if(!project.equals(that.project)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = project.hashCode();
		result = 31 * result + linesOfCode;
		result = 31 * result + testLinesOfCode;
		result = 31 * result + numCommits;
		result = 31 * result + numContribs;
		result = 31 * result + numIssues;
		return result;
	}

	@Override
	public String toString() {
		return "ProjectMetrics{" +
				"project=" + project +
				", linesOfCode=" + linesOfCode +
				", testLinesOfCode=" + testLinesOfCode +
				", numCommits=" + numCommits +
				", numContribs=" + numContribs +
				", numIssues=" + numIssues +
				'}';
	}
}

