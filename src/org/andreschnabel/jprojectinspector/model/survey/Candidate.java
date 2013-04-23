package org.andreschnabel.jprojectinspector.model.survey;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "candidate")
public class Candidate {
	public String login;
	public String name;
	public String email;
	
	public Candidate(String login, String name, String email) {
		super();
		this.login = login;
		this.name = name;
		this.email = email;
	}

	public Candidate() {
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		Candidate candidate = (Candidate) o;

		if(email != null ? !email.equals(candidate.email) : candidate.email != null) return false;
		if(login != null ? !login.equals(candidate.login) : candidate.login != null) return false;
		if(name != null ? !name.equals(candidate.name) : candidate.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = login != null ? login.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Candidate{" +
				"login='" + login + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
