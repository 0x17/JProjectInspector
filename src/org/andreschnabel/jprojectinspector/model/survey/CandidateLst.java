package org.andreschnabel.jprojectinspector.model.survey;

import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
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

	public CsvData toCsv() {
		String[] headers = new String[]{"login", "name", "email"};
		Transform<Candidate, String[]> candidateToRow = new Transform<Candidate, String[]>() {
			@Override
			public String[] invoke(Candidate c) {
				return new String[] {CsvHelpers.escapeIfComma(c.login), CsvHelpers.escapeIfComma(c.name), CsvHelpers.escapeIfComma(c.email) };
			}
		};
		return CsvData.fromList(headers, candidateToRow, candidates);
	}
}
