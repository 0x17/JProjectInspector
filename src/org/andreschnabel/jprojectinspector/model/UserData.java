package org.andreschnabel.jprojectinspector.model;

import java.util.List;

/**
 * Daten zu einem GitHub-Nutzer.
 */
public class UserData {
	/**
	 * Login name.
	 */
	public String name;
	/**
	 * Klarname.
	 */
	public String realName;
	/**
	 * GitHub-Beitrittsdatum.
	 */
	public String joinDate;
	/**
	 * Liste seiner Projekte.
	 */
	public List<Project> projects;
	/**
	 * Anzahl gesternter Projekte.
	 */
	public int numStarredProjects;
	/**
	 * Liste von Nutzern, welche ihm folgen.
	 */
	public List<String> followers;
	/**
	 * Liste von Nutzern, welche er folgt.
	 */
	public List<String> following;

	/**
	 * Leerer Nutzer (null, null, ...)
	 */
	public UserData() {
	}

	/**
	 * Konstruktor
	 * @param name login name.
	 * @param joinDate Beitrittsdatum.
	 * @param projects Projektliste.
	 * @param numStarredProjects Anzahl gesternter Projekte.
	 * @param followers Liste der Followers.
	 * @param following Liste der gefolgten Nutzer.
	 */
	public UserData(String name, String joinDate, List<Project> projects, int numStarredProjects, List<String> followers, List<String> following) {
		this.name = name;
		this.joinDate = joinDate;
		this.projects = projects;
		this.numStarredProjects = numStarredProjects;
		this.followers = followers;
		this.following = following;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		UserData userData = (UserData) o;

		if(numStarredProjects != userData.numStarredProjects) return false;
		if(followers != null ? !followers.equals(userData.followers) : userData.followers != null) return false;
		if(following != null ? !following.equals(userData.following) : userData.following != null) return false;
		if(joinDate != null ? !joinDate.equals(userData.joinDate) : userData.joinDate != null) return false;
		if(name != null ? !name.equals(userData.name) : userData.name != null) return false;
		if(projects != null ? !projects.equals(userData.projects) : userData.projects != null) return false;
		if(realName != null ? !realName.equals(userData.realName) : userData.realName != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (realName != null ? realName.hashCode() : 0);
		result = 31 * result + (joinDate != null ? joinDate.hashCode() : 0);
		result = 31 * result + (projects != null ? projects.hashCode() : 0);
		result = 31 * result + numStarredProjects;
		result = 31 * result + (followers != null ? followers.hashCode() : 0);
		result = 31 * result + (following != null ? following.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "UserData{" +
				"name='" + name + '\'' +
				", realName='" + realName + '\'' +
				", joinDate='" + joinDate + '\'' +
				", projects=" + projects +
				", numStarredProjects=" + numStarredProjects +
				", followers=" + followers +
				", following=" + following +
				'}';
	}
}
