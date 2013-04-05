package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.evaluation.projects.UserProjects;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Runner {

	public static void main(String[] args) throws Exception {
		//connectProjectsWithUsers();
		//showProjectWithoutUserCount();
		//showLanguageDistribution();
		collectMetrics();
	}

	private static void collectMetrics() throws Exception {
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));
		ProjectMetricsLst metrics = MetricsCollector.collectMetricsForResponses(rpl);
		XmlHelpers.serializeToXml(metrics, new File("metrics500.xml"));
	}

	private static void showLanguageDistribution() throws Exception {
		List<Project> projs = LanguageDistribution.respProjectsToProjects(getProjectsWithUser());
		Map<String, Integer> distr = LanguageDistribution.determineLanguageDistribution(projs);
		for(String lang : distr.keySet()) {
			Helpers.log(lang + " => " + distr.get(lang));
		}
	}

	private static void showProjectWithoutUserCount() throws Exception {
		int count = countProjectsWithoutUser();
		System.out.println("Projs without user: " + count);
	}

	private static int countProjectsWithoutUser() throws Exception {
		int count = 0;
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));
		for(ResponseProjects rp : rpl.responseProjs) {
			if(rp.user == null) {
				count++;
			}
		}
		return count;
	}
	
	private static List<ResponseProjects> getProjectsWithUser() throws Exception {
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));
		List<ResponseProjects> toRem = new LinkedList<ResponseProjects>();
		for(ResponseProjects rp : rpl.responseProjs) {
			if(rp.user == null) {
				toRem.add(rp);
			}
		}
		rpl.responseProjs.removeAll(toRem);
		return rpl.responseProjs;
	}

	private static void connectProjectsWithUsers() throws Exception {
		List<ResponseProjects> results = SurveyProjectExtractor.extractProjectsFromResults(new File("responses500.csv"));
		UserProjects projs = (UserProjects)XmlHelpers.deserializeFromXml(UserProjects.class, new File("userprojects500.xml"));
		
		for(ResponseProjects rp : results) {			
			rp.user = UserGuesser.guessUserWithProjects(rp, projs);			
		}
		
		XmlHelpers.serializeToXml(new ResponseProjectsLst(results), new File("responses500.xml"));
	}

}
