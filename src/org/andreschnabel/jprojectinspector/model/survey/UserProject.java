package org.andreschnabel.jprojectinspector.model.survey;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "project")
public class UserProject {
	
	public String user;
	public String name;
	
	public UserProject(String user, String name) {
		super();
		this.user = user;
		this.name = name;
	}
	
	public UserProject() {}

	@Override
	public String toString() {
		return "UserProject [user=" + user + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		UserProject other = (UserProject)obj;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		if (user == null) {
			if (other.user != null) return false;
		} else if (!user.equals(other.user)) return false;
		return true;
	}
	
	
	
}
