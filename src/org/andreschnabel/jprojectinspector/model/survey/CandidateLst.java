package org.andreschnabel.jprojectinspector.model.survey;

import org.andreschnabel.pecker.functional.ITransform;
import org.andreschnabel.pecker.serialization.CsvData;
import org.andreschnabel.pecker.serialization.CsvHelpers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.List;

/**
 * Liste von Kandidaten für eine Umfrage auf GitHub.
 */
@XmlRootElement
public class CandidateLst {

	/**
	 * Die Kandidatenliste.
	 */
	@XmlElementWrapper(name = "candidates")
	@XmlElement(name = "candidate")
	public List<Candidate> candidates;

	/**
	 * Konstruktor
	 * @param candidates Kandidatenliste.
	 */
	public CandidateLst(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	/**
	 * Leere Kandidatenliste (null).
	 */
	public CandidateLst() {}

	/**
	 * Wandel Kandidatenliste in CSV-Darstellung um.
	 * @return CSV-Darstellung der Kandidatenliste mit Spalten "login", "name", "email".
	 * @throws Exception
	 */
	public CsvData toCsv() throws Exception {
		String[] headers = new String[]{"login", "name", "email"};
		ITransform<Candidate, String[]> candidateToRow = new ITransform<Candidate, String[]>() {
			@Override
			public String[] invoke(Candidate c) {
				return new String[] {c.login, c.name, c.email };
			}
		};
		return CsvData.fromList(headers, candidateToRow, candidates);
	}

	/**
	 * Extrahiere Kandidatenliste aus CSV-Datei.
	 * @param f CSV-Datei.
	 * @return Kandidatenliste daraus.
	 * @throws Exception
	 */
	public static CandidateLst fromCsv(File f) throws Exception {
		return fromCsv(CsvHelpers.parseCsv(f));
	}

	/**
	 * Wandel CSV-Daten in Kandidatenliste um.
	 * @param data CSV-Daten.
	 * @return Kandidatenliste aus CSV-Daten mit erster Spalte Login/Username, zweiter Spalte Klarname und dritter Spalte E-Mail.
	 * @throws Exception
	 */
	public static CandidateLst fromCsv(CsvData data) throws Exception {
		ITransform<String[], Candidate> rowToCandidate = new ITransform<String[], Candidate>() {
			@Override
			public Candidate invoke(String[] sa) {
				return new Candidate(sa[0], sa[1], sa[2]);
			}
		};
		return new CandidateLst(CsvData.toList(rowToCandidate, data));
	}
}
