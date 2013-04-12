package org.andreschnabel.jprojectinspector.model.survey;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class UserProjects {
	
	@XmlElementWrapper(name = "projects")
	@XmlElement(name = "proj")
	public List<UserProject> usrProjs;
	
	public UserProjects(List<UserProject> usrProjs) {
		this.usrProjs = usrProjs;
	}
	
	public UserProjects() {}

}
