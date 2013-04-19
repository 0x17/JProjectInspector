package org.andreschnabel.jprojectinspector.model;

import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;

import java.util.List;

public class ProjectWithResults {
	private final String[] resultHeaders;
	public Project project;
	public Float[] results;
	public ProjectWithResults(Project project, String[] resultHeaders, Float[] results) {
		this.resultHeaders = resultHeaders;
		this.project = project;
		this.results = results;
	}

	public float get(String metric) {
		for(int i=0; i< resultHeaders.length; i++) {
			if(resultHeaders[i].equals(metric)) {
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
				String[] resultHeaders = new String[results.length];
				for(int i=0; i<results.length; i++) {
					results[i] = Float.valueOf(sa[i+2]);
					resultHeaders[i] = data.getHeaders()[i+2];
				}
				return new ProjectWithResults(new Project(sa[0], sa[1]), resultHeaders, results);
			}
		};
		return CsvData.toList(rowToProjWithResults, data);
	}

	public String[] getResultHeaders() {
		return resultHeaders;
	}

	public static CsvData toCsv(List<ProjectWithResults> pwrs) throws Exception {
		final int nresults = pwrs.get(0).results.length;

		final String[] headers = new String[2+nresults];
		headers[0] = "owner";
		headers[1] = "repo";
		String[] resHeaders = pwrs.get(0).getResultHeaders();
		for(int i=0; i<headers.length; i++) {
			headers[i+2] = resHeaders[i];
		}

		Transform<ProjectWithResults, String[]> projWithResultToRow = new Transform<ProjectWithResults, String[]>() {
			@Override
			public String[] invoke(ProjectWithResults pwr) {
				String[] row = new String[headers.length];
				row[0] = pwr.project.owner;
				row[1] = pwr.project.repoName;
				for(int i=0; i<nresults; i++) {
					row[i+2] = String.valueOf(pwr.results[i]);
				}
				return row;
			}
		};
		return CsvData.fromList(headers, projWithResultToRow, pwrs);
	}
}
