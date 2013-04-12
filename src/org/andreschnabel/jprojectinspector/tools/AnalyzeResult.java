package org.andreschnabel.jprojectinspector.tools;

import org.andreschnabel.jprojectinspector.model.metrics.ProjectMetrics;

public class AnalyzeResult {

	public ProjectMetrics metrics;
	public float testingNeedValue;

	public AnalyzeResult(ProjectMetrics metrics, float testingNeedValue) {
		this.metrics = metrics;
		this.testingNeedValue = testingNeedValue;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		AnalyzeResult that = (AnalyzeResult) o;

		if(Float.compare(that.testingNeedValue, testingNeedValue) != 0) return false;
		if(metrics != null ? !metrics.equals(that.metrics) : that.metrics != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = metrics != null ? metrics.hashCode() : 0;
		result = 31 * result + (testingNeedValue != +0.0f ? Float.floatToIntBits(testingNeedValue) : 0);
		return result;
	}

	@Override
	public String toString() {
		return "AnalyzeResult{" +
				"metrics=" + metrics +
				", testingNeedValue=" + testingNeedValue +
				'}';
	}
}
