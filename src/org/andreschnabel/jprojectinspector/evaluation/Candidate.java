package org.andreschnabel.jprojectinspector.evaluation;

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
	public String toString() {
		return "Candidate [login=" + login + ", name=" + name + ", email=" + email + "]";
	}
	
	

}
