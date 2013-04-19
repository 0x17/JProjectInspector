package org.andreschnabel.jprojectinspector.model;

import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;

import java.util.List;

public class ProjectWithResults {
	private final String[] headers;
	public Project project;
	public Float[] results;
	public ProjectWithResults(Project project, String[] headers, Float[] results) {
		this.headers = headers;
		this.project = project;
		this.results = results;
	}

	public float get(String metric) {
		for(int i=2; i<headers.length; i++) {
			if(headers[i].equals(metric)) {
				return results[i];
			}
		}
		return Float.NaN;
	}

	public static List<ProjectWithResults> fromCsv(final CsvData data) throws Exception {
		Transform<String[], ProjectWithResults> rowToProjWithResults = new Transform<String[], ProjectWithResults>() {
			@Override
			public ProjectWithResults invoke(String[] sa) {
				Float[] results = new Float[sa.length-2];
				for(int i=0; i<results.length; i++) {
					results[i] = Float.valueOf(sa[i+2]);
				}
				return new ProjectWithResults(new Project(sa[0], sa[1]), data.getHeaders(), results);
			}
		};
		return CsvData.toList(rowToProjWithResults, data);
	}

	public String[] getHeaders() {
		return headers;
	}
}
