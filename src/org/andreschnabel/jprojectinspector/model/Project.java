package org.andreschnabel.jprojectinspector.model;

import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;

import java.util.List;

public class Project {
	public String owner;
	public String repoName;

	public Project(String owner, String repoName) {
		this.owner = owner;
		this.repoName = repoName;
	}

	public Project() {}

	@Override
	public String toString() {
		return toId();
	}

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

	public static Project fromString(String s) throws Exception {
		if(!s.contains("/"))
			throw new Exception("Project string must contain /!");

		String[] parts = s.split("/");
		if(parts.length != 2)
			throw new Exception("Project string malformed!");

		return new Project(parts[0], parts[1]);
	}

	public static CsvData projectListToCsv(List<Project> projs) {
		Transform<Project, String[]> projToRow = new Transform<Project, String[]>() {
			@Override
			public String[] invoke(Project p) {
				return new String[] {p.owner, p.repoName};
			}
		};
		return CsvData.fromList(new String[]{"owner,repo"}, projToRow, projs);
	}

	public static List<Project> projectListFromCsv(CsvData data) throws Exception {
		Transform<String[], Project> rowToProj = new Transform<String[], Project>() {
			@Override
			public Project invoke(String[] row) {
				return new Project(row[0], row[1]);
			}
		};
		return CsvData.toList(rowToProj, data);
	}
}
