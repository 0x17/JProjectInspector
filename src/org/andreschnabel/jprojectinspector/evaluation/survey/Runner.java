package org.andreschnabel.jprojectinspector.evaluation.survey;

import java.io.File;
import java.util.List;

import org.andreschnabel.jprojectinspector.evaluation.projects.UserProjects;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

public class Runner {

	
	public static void main(String[] args) throws Exception {
		List<ResponseProjects> results = SurveyProjectExtractor.extractProjectsFromResults(new File("responses500.csv"));
		UserProjects projs = (UserProjects)XmlHelpers.deserializeFromXml(UserProjects.class, new File("userprojects500.xml"));
		
		for(ResponseProjects rp : results) {			
			rp.user = UserGuesser.guessUserWithProjects(rp, projs);			
		}
		
		XmlHelpers.serializeToXml(results, new File("responses500.xml"));
	}

	

}
