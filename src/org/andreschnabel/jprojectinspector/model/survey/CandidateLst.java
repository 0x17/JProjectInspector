package org.andreschnabel.jprojectinspector.model.survey;

import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.List;

@XmlRootElement
public class CandidateLst {

	@XmlElementWrapper(name = "candidates")
	@XmlElement(name = "candidate")
	public List<Candidate> candidates;

	public CandidateLst(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	public CandidateLst() {}

	public CsvData toCsv() throws Exception {
		String[] headers = new String[]{"login", "name", "email"};
		Transform<Candidate, String[]> candidateToRow = new Transform<Candidate, String[]>() {
			@Override
			public String[] invoke(Candidate c) {
				return new String[] {c.login, c.name, c.email };
			}
		};
		return CsvData.fromList(headers, candidateToRow, candidates);
	}

	public static CandidateLst fromCsv(File f) throws Exception {
		return fromCsv(CsvHelpers.parseCsv(f));
	}

	public static CandidateLst fromCsv(CsvData data) throws Exception {
		Transform<String[], Candidate> rowToCandidate = new Transform<String[], Candidate>() {
			@Override
			public Candidate invoke(String[] sa) {
				return new Candidate(sa[0], sa[1], sa[2]);
			}
		};
		return new CandidateLst(CsvData.toList(rowToCandidate, data));
	}
}
