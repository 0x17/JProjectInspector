package org.andreschnabel.jprojectinspector.model;

public class Project {
	public String owner;
	public String repoName;
	
	public Project(String owner, String repoName) {
		this.owner = owner;
		this.repoName = repoName;
	}

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
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Project other = (Project)obj;
		if (owner == null) {
			if (other.owner != null) return false;
		} else if (!owner.equals(other.owner)) return false;
		if (repoName == null) {
			if (other.repoName != null) return false;
		} else if (!repoName.equals(other.repoName)) return false;
		return true;
	}

}
