package org.andreschnabel.jprojectinspector.model.survey;

import org.andreschnabel.jprojectinspector.evaluation.SurveyFormat;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.functional.FuncInPlace;
import org.andreschnabel.jprojectinspector.utilities.functional.ITransform;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Projekte aus Antwort eines GitHub-Nutzers in einer Umfrage.
 * Enthält zugehörigen Nutzer und genannte Projekte mit
 * <ul>
 *     <li>Niedrigstem Testaufwand (least tested)</li>
 *     <li>Höchstem Testaufwand (most tested)</li>
 *     <li>Niedrigster Fehlerzahl (least bug count)</li>
 *     <li>Höchster Fehlerzahl (most bug count)</li>
 * </ul>
 */
@XmlRootElement(name = "responseprojects")
public class ResponseProjects {

	/**
	 * Nutzername, welcher Fragebogen beantwortet hat und die Projekte angegeben mit entsprechenden Einschätzungen.
	 */
	public String user;

	/**
	 * Projekt des Nutzers welches geschätzt wurde den geringsten bisherigen Testaufwand seiner Projekte zu haben.
 	 */
	public String leastTested;
	/**
	 * Projekt des Nutzers welches geschätzt wurde den höchsten bisherigen Testaufwand seiner Projekte zu haben.
	 */
	public String mostTested;

	/**
	 * Projekt des Nutzers welches dieser schätzte die geringste Fehlerzahl seiner Projekte zu haben.
 	 */
	public String lowestBugCount;

	/**
	 * Projekt des Nutzers welches dieser schätzte die höchste Fehlerzahl seiner Projekte zu haben.
	 */
	public String highestBugCount;

	/**
	 * Gewichtung des Nutzers. Das ist der Anteil der Buzzwords, welcher dieser als einem Bekannten erklärbar kennzeichnete.
	 */
	public double weight;

	/**
	 * Konstruktor wobei User auf null und Gewicht auf NaN gesetzt werrden.
	 * @param leastTested Projekt mit geschätztem minimalen relativen Testaufwand.
	 * @param mostTested Projekt mit geschätztem maximalen relativen Testaufwand.
	 * @param lowestBugCount Projekt mit geschätzter minimaler Fehlerzahl.
	 * @param highestBugCount Projekt mit geschätzter maximaler Fehlerzahl.
	 */
	public ResponseProjects(String leastTested, String mostTested, String lowestBugCount, String highestBugCount) {
		this(null, leastTested, mostTested, lowestBugCount, highestBugCount, Double.NaN);
	}

	/**
	 * Konstruktor
	 * @param user Zugehöriger Nutzer.
	 * @param leastTested Projekt mit geschätztem minimalen relativen Testaufwand.
	 * @param mostTested Projekt mit geschätztem maximalen relativen Testaufwand.
	 * @param lowestBugCount Projekt mit geschätzter minimaler Fehlerzahl.
	 * @param highestBugCount Projekt mit geschätzter maximaler Fehlerzahl.
	 * @param weight Gewicht nach Anteil der bekannten Buzzwords.
	 */
	public ResponseProjects(String user, String leastTested, String mostTested, String lowestBugCount, String highestBugCount, Double weight) {
		this.user = user;
		this.leastTested = leastTested;
		this.mostTested = mostTested;
		this.lowestBugCount = lowestBugCount;
		this.highestBugCount = highestBugCount;
		this.weight = weight;
	}

	/**
	 * Leere Antworten (null, null, ...)
	 */
	public ResponseProjects() {}

	/**
	 * Getter für genannte Projekte.
	 * @return Liste der gennannten Projekte ohne Duplikate.
	 */
	public List<Project> toProjectList() {
		List<Project> projs = new LinkedList<Project>();
		FuncInPlace.addNoDups(projs, new Project(user, leastTested));
		FuncInPlace.addNoDups(projs, new Project(user, mostTested));
		FuncInPlace.addNoDups(projs, new Project(user, lowestBugCount));
		FuncInPlace.addNoDups(projs, new Project(user, highestBugCount));
		return projs;
	}

	/**
	 * Getter für gennante Projekte mit Duplikation.
	 * @return Liste der genannten Projekte mit Duplikaten
	 */
	public List<Project> toProjectListDups() {
		List<Project> projs = new LinkedList<Project>();
		projs.add(new Project(user, leastTested));
		projs.add(new Project(user, mostTested));
		projs.add(new Project(user, lowestBugCount));
		projs.add(new Project(user, highestBugCount));
		return projs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((highestBugCount == null) ? 0 : highestBugCount.hashCode());
		result = prime * result + ((leastTested == null) ? 0 : leastTested.hashCode());
		result = prime * result + ((lowestBugCount == null) ? 0 : lowestBugCount.hashCode());
		result = prime * result + ((mostTested == null) ? 0 : mostTested.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ResponseProjects other = (ResponseProjects)obj;
		if (highestBugCount == null) {
			if (other.highestBugCount != null) return false;
		} else if (!highestBugCount.equals(other.highestBugCount)) return false;
		if (leastTested == null) {
			if (other.leastTested != null) return false;
		} else if (!leastTested.equals(other.leastTested)) return false;
		if (lowestBugCount == null) {
			if (other.lowestBugCount != null) return false;
		} else if (!lowestBugCount.equals(other.lowestBugCount)) return false;
		if (mostTested == null) {
			if (other.mostTested != null) return false;
		} else if (!mostTested.equals(other.mostTested)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponseProjects{" +
				"user='" + user + '\'' +
				", leastTested='" + leastTested + '\'' +
				", mostTested='" + mostTested + '\'' +
				", lowestBugCount='" + lowestBugCount + '\'' +
				", highestBugCount='" + highestBugCount + '\'' +
				", weight=" + weight +
				'}';
	}

	/**
	 * Konvertiere CSV-Daten mit nutzer, least tested, ...., gewicht Spalten zu Antwortprojekten-Liste.
	 * @param data CSV-Daten mit Spalten nutzer,leasttested,mosttested,lowestbugcount,highestbugcount,gewicht in der Reihenfolge (Bezeichner egal).
	 * @return passende Liste von Antwortprojekten.
	 * @throws Exception
	 */
	public static List<ResponseProjects> fromPreprocessedCsvData(CsvData data) throws Exception {
		ITransform<String[], ResponseProjects> rowToRespProjs = new ITransform<String[], ResponseProjects>() {
			@Override
			public ResponseProjects invoke(String[] sa) {
				ResponseProjects rps = new ResponseProjects();
				rps.user = sa[0];
				rps.leastTested = sa[1];
				rps.mostTested = sa[2];
				rps.lowestBugCount = sa[3];
				rps.highestBugCount = sa[4];
				rps.weight = Double.valueOf(sa[5]);
				return rps;
			}
		};
		return CsvData.toList(rowToRespProjs, data);
	}

	/**
	 * Liste aus Antwortprojekten aus vorbearbeiteter CSV-Datei. D.h. Nutzer und Gewicht eingetragen und in Format
	 * user,leasttested,mostttested,lowestbugcount,highestbugcount,gewicht in der Reihenfolge wobei Bezeichner
	 * egal sind.
	 * @param f vorbearbeitete CSV-Datei.
	 * @return Liste aus Antwortprojekten.
	 * @throws Exception
	 */
	public static List<ResponseProjects> fromPreprocessedCsvFile(File f) throws Exception {
		CsvData data = CsvHelpers.parseCsv(f);
		if(data == null) {
			return null;
		}
		return fromPreprocessedCsvData(data);
	}

	/**
	 * Erzeuge Liste aus Umfragerohdaten.
	 * @param f CSV-Datei mit Umfragerohdaten.
	 * @return Liste aus Antwortprojekten wobei Nutzer auf null gesetzt ist.
	 * @throws Exception
	 */
	public static List<ResponseProjects> fromCsvFile(File f) throws Exception {
		final CsvData data = CsvHelpers.parseCsv(f);
		ITransform<String[], ResponseProjects> rowToRespProjs = new ITransform<String[], ResponseProjects>() {
			@Override
			public ResponseProjects invoke(String[] row) {
				ResponseProjects rps = new ResponseProjects();
				rps.user = null;

				rps.leastTested = row[data.columnWithHeader(SurveyFormat.LEAST_TESTED_HEADER)];
				rps.mostTested = row[data.columnWithHeader(SurveyFormat.MOST_TESTED_HEADER)];
				rps.lowestBugCount = row[data.columnWithHeader(SurveyFormat.LOWEST_BUG_COUNT_HEADER)];
				rps.highestBugCount = row[data.columnWithHeader(SurveyFormat.HIGHEST_BUG_COUNT_HEADER)];

				if(row[data.columnWithHeader(SurveyFormat.RESPONSIBLE_FOR_QA)].equals("No")) {
					rps.weight = 0.0;
				} else {
					String buzzwords = row[data.columnWithHeader(SurveyFormat.BUZZWORDS)];
					int nwords = StringHelpers.countOccurencesOfWords(buzzwords, SurveyFormat.BUZZWORD_ARRAY);
					rps.weight = (double)nwords / SurveyFormat.BUZZWORD_ARRAY.length;
				}
				return rps;
			}
		};
		return CsvData.toList(rowToRespProjs, data);
	}

	/**
	 * Convertiere Antwortprojketliste in CSV-Daten
	 * @param rps Antwortprojektliste.
	 * @return Zugehörige CSV-Daten
	 * @throws Exception
	 */
	public static CsvData toCsv(List<ResponseProjects> rps) throws Exception {
		String[] headers = new String[] {"user",
				SurveyFormat.LEAST_TESTED_HEADER,
				SurveyFormat.MOST_TESTED_HEADER,
				SurveyFormat.LOWEST_BUG_COUNT_HEADER,
				SurveyFormat.HIGHEST_BUG_COUNT_HEADER,
				"weight"};
		ITransform<ResponseProjects, String[]> respProjToRow = new ITransform<ResponseProjects, String[]>() {
			@Override
			public String[] invoke(ResponseProjects rp) {
				return new String[] {rp.user == null ? "null" : rp.user,
						rp.leastTested,
						rp.mostTested,
						rp.lowestBugCount,
						rp.highestBugCount,
						String.valueOf(rp.weight)};
			}
		};
		return CsvData.fromList(headers, respProjToRow, rps);
	}

	/**
	 * Sammel alle Projekte aus einer Liste von Antwortprojekten.
	 * @param respProjs Liste von Antwortprojekten.
	 * @return alle enthaltenen Projekte.
	 */
	public static List<Project> allProjects(List<ResponseProjects> respProjs) {
		List<Project> projs = new LinkedList<Project>();
		for(ResponseProjects rp : respProjs) {
			for(Project p : rp.toProjectList()) {
				projs.add(p);
			}
		}
		return projs;
	}

	/**
	 * Vereinfache alle Projektnamen.
	 */
	public void simplify() {
		leastTested = simplify(leastTested);
		mostTested = simplify(mostTested);
		lowestBugCount = simplify(lowestBugCount);
		highestBugCount = simplify(highestBugCount);
	}

	/**
	 * Vereinfache https://github.com/user/repo zu repo.
	 * @param repoName eventuell link.
	 * @return vereinfachter Repository Name.
	 */
	private String simplify(String repoName) {
		if(repoName.startsWith("https://github.com/")) {
			Matcher m = Pattern.compile("https://github.com/.+?/(.+)").matcher(repoName);
			m.find();
			return m.group(1);
		} else {
			return repoName;
		}
	}
}
