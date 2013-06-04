package org.andreschnabel.jprojectinspector.model;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.pecker.functional.ITransform;
import org.andreschnabel.pecker.serialization.CsvData;

import java.util.List;

/**
 * Repräsentiert Projekt auf GitHub.
 * Projekte auf GitHub sind eindeutig durch (owner, repo)-Tupel identifiziert.
 */
public class Project {
	/**
	 * login des Verwalters.
	 */
	public String owner;
	/**
	 * Name des Repositories.
	 */
	public String repoName;

	/**
	 * Konstruktor für Projekt (owner, repoName)
	 * @param owner Login des Projektverwalters.
	 * @param repoName Name des Repositories.
	 */
	public Project(String owner, String repoName) {
		this.owner = owner;
		this.repoName = repoName;
	}

	/**
	 * Leeres projekt (null, null).
	 */
	public Project() {}

	/**
	 * Wandel Projekt in "owner/repo"-String-Darstellung.
	 * @return "owner/repo"
	 */
	@Override
	public String toString() {
		return toId();
	}

	/**
	 * Wandel Projekt in "owner/repo"-String-Darstellung.
	 * @return "owner/repo"
	 */
	public String toId() {
		return owner + "/" + repoName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((repoName == null) ? 0 : repoName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Project other = (Project) obj;
		if(owner == null) {
			if(other.owner != null) return false;
		} else if(!owner.equals(other.owner)) return false;
		if(repoName == null) {
			if(other.repoName != null) return false;
		} else if(!repoName.equals(other.repoName)) return false;
		return true;
	}

	/**
	 * Erzeuge Projekt (owner,repo) aus "owner/repo"-Zeichenkette
	 * @param s "owner/repo"-Zeichenkette
	 * @return Projekt (owner,repo)
	 * @throws Exception
	 */
	public static Project fromString(String s) throws Exception {
		if(!s.contains("/"))
			throw new Exception("Project string must contain /!");

		String[] parts = s.split("/");
		if(parts.length != 2) {
			throw new Exception("Project string malformed!");
		}

		return new Project(parts[0], parts[1]);
	}

	/**
	 * CSV-Headerbezeichner für Liste aus Projekten.
	 */
	public final static String[] csvHeaders = new String[]{"owner","repo"};

	/**
	 * Konvertiert Liste von Projekten in CSV-Darstellung.
	 * @param projs Projektliste
	 * @return CSV-Darstellung der gegebenen Liste.
	 * @throws Exception
	 */
	public static CsvData projectListToCsv(List<Project> projs) throws Exception {
		ITransform<Project, String[]> projToRow = new ITransform<Project, String[]>() {
			@Override
			public String[] invoke(Project p) {
				return new String[] {p.owner, p.repoName};
			}
		};
		return CsvData.fromList(csvHeaders, projToRow, projs);
	}

	/**
	 * Wandelt CSV-Darstellung von Projektliste wider in Liste um.
	 * @param data CSV-Darstellung von Projektliste (1. Spalte owner, 2. Spalte repo)
	 * @return Projektliste passend zum CSV.
	 * @throws Exception
	 */
	public static List<Project> projectListFromCsv(CsvData data) throws Exception {
		ITransform<String[], Project> rowToProj = new ITransform<String[], Project>() {
			@Override
			public Project invoke(String[] row) {
				return new Project(row[0], row[1]);
			}
		};
		return CsvData.toList(rowToProj, data);
	}

	/**
	 * Erzeugt den zum Projekt gehörigen Repository-Link.
	 * @return url auf Profil des Projekt-Repositores auf GitHub.
	 */
	public String toUrl() {
		return Config.BASE_URL + owner + "/" + repoName;
	}
}
