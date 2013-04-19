package org.andreschnabel.jprojectinspector.model.survey;

import org.andreschnabel.jprojectinspector.evaluation.SurveyFormat;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.functional.FuncInPlace;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "responseprojects")
public class ResponseProjects {
	
	public String user;
	
	public String leastTested;
	public String mostTested;	
	
	public String lowestBugCount;
	public String highestBugCount;

	public double weight;

	public ResponseProjects(String leastTested, String mostTested, String lowestBugCount, String highestBugCount) {
		this(null, leastTested, mostTested, lowestBugCount, highestBugCount, Double.NaN);
	}
	
	public ResponseProjects(String user, String leastTested, String mostTested, String lowestBugCount, String highestBugCount, Double weight) {
		this.user = user;
		this.leastTested = leastTested;
		this.mostTested = mostTested;
		this.lowestBugCount = lowestBugCount;
		this.highestBugCount = highestBugCount;
		this.weight = weight;
	}
	
	public ResponseProjects() {}
	
	public List<Project> toProjectList() {
		List<Project> projs = new LinkedList<Project>();
		FuncInPlace.addNoDups(projs, new Project(user, leastTested));
		FuncInPlace.addNoDups(projs, new Project(user, mostTested));
		FuncInPlace.addNoDups(projs, new Project(user, lowestBugCount));
		FuncInPlace.addNoDups(projs, new Project(user, highestBugCount));
		return projs;
	}

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

	public static List<ResponseProjects> fromCsvFile(File f) throws Exception {
		final CsvData data = CsvHelpers.parseCsv(f);
		Transform<String[], ResponseProjects> rowToRespProjs = new Transform<String[], ResponseProjects>() {
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

	public static CsvData toCsv(List<ResponseProjects> rps) throws Exception {
		String[] headers = new String[] {"user",
				SurveyFormat.LEAST_TESTED_HEADER,
				SurveyFormat.MOST_TESTED_HEADER,
				SurveyFormat.LOWEST_BUG_COUNT_HEADER,
				SurveyFormat.HIGHEST_BUG_COUNT_HEADER,
				"weight"};
		Transform<ResponseProjects, String[]> respProjToRow = new Transform<ResponseProjects, String[]>() {
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
}
