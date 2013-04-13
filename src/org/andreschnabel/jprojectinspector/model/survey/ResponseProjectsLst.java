package org.andreschnabel.jprojectinspector.model.survey;

import org.andreschnabel.jprojectinspector.model.Project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
public class ResponseProjectsLst {

	public ResponseProjectsLst(List<ResponseProjects> results) {
		responseProjs = results;
	}
	
	public ResponseProjectsLst() {}

	@XmlElementWrapper(name = "responseprojectslst")
	@XmlElement(name = "responseprojects")
	public List<ResponseProjects> responseProjs;

	public List<Project> allProjects() {
		List<Project> allProjects = new LinkedList<Project>();
		for(ResponseProjects rp : responseProjs) {
			allProjects.addAll(rp.toProjectList());
		}
		return allProjects;
	}
	
}
