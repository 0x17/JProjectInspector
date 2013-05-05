package org.andreschnabel.jprojectinspector.model;

import org.andreschnabel.jprojectinspector.utilities.functional.ITransform;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ein Projekt mit Messergebnissen.
 * Die Messergebnisse haben bestimmte Bezeichnungen und Werte.
 */
public class ProjectWithResults {
	/**
	 * Bezeichnungen der Messergebnis-Spalten.
	 */
	private final String[] resultHeaders;

	/**
	 * Ergebniswerte der Messung.
	 */
	public Double[] results;

	/**
	 * Projekt zu dem die Ergebnisse gehören.
	 */
	public Project project;

	/**
	 * Konstruktor
	 * @param project Projekt auf GitHub.
	 * @param resultHeaders Metrik-Namen der Messwertspalten.
	 * @param results Messwerte.
	 */
	public ProjectWithResults(Project project, String[] resultHeaders, Double[] results) {
		this.resultHeaders = resultHeaders;
		this.project = project;
		this.results = results;
	}

	/**
	 * Ergebnis für gegebenen Metrik-Namen.
	 * @param metric Name der Metrik zu der das Ergebnis kommen soll.
	 * @return Messergebnis.
	 */
	public Double get(String metric) {
		for(int i=0; i< resultHeaders.length; i++) {
			if(resultHeaders[i].equals(metric)) {
				return results[i];
			}
		}
		return Double.NaN;
	}

	/**
	 * Erzeuge Liste aus Projekten mit Ergebnissen aus CSV-Daten.
	 * @param data CSV-Daten mit ersten Spalten (owner, repo) und danach Metrik-Ergebnissen.
	 * @return Liste aus Projekten mit Ergebnissen.
	 * @throws Exception
	 */
	public static List<ProjectWithResults> fromCsv(final CsvData data) throws Exception {
		ITransform<String[], ProjectWithResults> rowToProjWithResults = new ITransform<String[], ProjectWithResults>() {
			@Override
			public ProjectWithResults invoke(String[] sa) {
				Double[] results = new Double[sa.length-2];
				String[] resultHeaders = new String[results.length];
				for(int i=0; i<results.length; i++) {
					results[i] = Double.valueOf(sa[i+2]);
					resultHeaders[i] = data.getHeaders()[i+2];
				}
				return new ProjectWithResults(new Project(sa[0], sa[1]), resultHeaders, results);
			}
		};
		return CsvData.toList(rowToProjWithResults, data);
	}

	/**
	 * Getter für Metrik-Namen.
	 * @return Namen der Metriken, dessen Messwerte in results stehen.
	 */
	public String[] getResultHeaders() {
		return resultHeaders;
	}

	/**
	 * Wandelt Liste aus Projekten mit Ergebnissen in CSV um.
	 * @param pwrs Liste aus Projekten mit Ergebnissen.
	 * @return CSV-Daten wobei ersten Spalten (owner,repo) sind und danach Metrikergebnisse.
	 * @throws Exception
	 */
	public static CsvData toCsv(List<ProjectWithResults> pwrs) throws Exception {
		final int nresults = pwrs.get(0).results.length;

		final String[] headers = new String[2+nresults];
		headers[0] = "owner";
		headers[1] = "repo";
		String[] resHeaders = pwrs.get(0).getResultHeaders();
		for(int i=0; i<headers.length; i++) {
			headers[i+2] = resHeaders[i];
		}

		ITransform<ProjectWithResults, String[]> projWithResultToRow = new ITransform<ProjectWithResults, String[]>() {
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

	public static Map<Project, Double[]> toMap(List<ProjectWithResults> pwrs) {
		Map<Project, Double[]> m = new HashMap<Project, Double[]>();
		for (ProjectWithResults pwr : pwrs) {
			m.put(pwr.project, pwr.results);
		}
		return m;
	}
}
