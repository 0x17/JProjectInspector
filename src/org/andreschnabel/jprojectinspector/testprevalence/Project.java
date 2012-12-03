package org.andreschnabel.jprojectinspector.testprevalence;

public class Project {
	public String owner;
	public String repoName;
	
	public Project(String owner, String repoName) {
		this.owner = owner;
		this.repoName = repoName;
	}

	@Override
	public String toString() {
		return "Project [owner=" + owner + ", repoName=" + repoName + "]";
	}
	
	
	
}
