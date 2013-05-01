package org.andreschnabel.jprojectinspector.model.survey;

import org.andreschnabel.jprojectinspector.model.Project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

/**
 * Liste aus Projekten aus Antworten von Nutzern in einer Umfrage.
 */
@XmlRootElement
public class ResponseProjectsLst {

	/**
	 * Konstruktor
	 * @param results Antwortprojektliste
	 */
	public ResponseProjectsLst(List<ResponseProjects> results) {
		responseProjs = results;
	}

	/**
	 * (null)
	 */
	public ResponseProjectsLst() {}

	/**
	 * Liste von Antwortprojekten.
	 */
	@XmlElementWrapper(name = "responseprojectslst")
	@XmlElement(name = "responseprojects")
	public List<ResponseProjects> responseProjs;

	/**
	 * Sammel alle Projekte.
	 * @return alle Projekte.
	 */
	public List<Project> allProjects() {
		List<Project> allProjects = new LinkedList<Project>();
		for(ResponseProjects rp : responseProjs) {
			allProjects.addAll(rp.toProjectList());
		}
		return allProjects;
	}
	
}
