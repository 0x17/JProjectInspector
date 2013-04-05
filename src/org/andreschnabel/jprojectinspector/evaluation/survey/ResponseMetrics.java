package org.andreschnabel.jprojectinspector.evaluation.survey;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "responsemetrics")
public class ResponseMetrics {

	public ResponseProjects responses;

	public int linesOfCode;
	public int testLinesOfCode;

	public int numCommits;
	public int numContribs;

	public int numIssues;

	public ResponseMetrics(ResponseProjects responses, int linesOfCode, int testLinesOfCode, int numCommits, int numContribs, int numIssues) {
		this.responses = responses;
		this.linesOfCode = linesOfCode;
		this.testLinesOfCode = testLinesOfCode;
		this.numCommits = numCommits;
		this.numContribs = numContribs;
		this.numIssues = numIssues;
	}

	public ResponseMetrics() {
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		ResponseMetrics that = (ResponseMetrics) o;

		if(linesOfCode != that.linesOfCode) return false;
		if(numCommits != that.numCommits) return false;
		if(numContribs != that.numContribs) return false;
		if(numIssues != that.numIssues) return false;
		if(testLinesOfCode != that.testLinesOfCode) return false;
		if(!responses.equals(that.responses)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = responses.hashCode();
		result = 31 * result + linesOfCode;
		result = 31 * result + testLinesOfCode;
		result = 31 * result + numCommits;
		result = 31 * result + numContribs;
		result = 31 * result + numIssues;
		return result;
	}
}
