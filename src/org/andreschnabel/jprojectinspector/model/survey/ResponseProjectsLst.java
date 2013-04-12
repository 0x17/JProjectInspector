package org.andreschnabel.jprojectinspector.model.survey;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseProjectsLst {

	public ResponseProjectsLst(List<ResponseProjects> results) {
		responseProjs = results;
	}
	
	public ResponseProjectsLst() {}

	@XmlElementWrapper(name = "responseprojectslst")
	@XmlElement(name = "responseprojects")
	public List<ResponseProjects> responseProjs;
	
}
