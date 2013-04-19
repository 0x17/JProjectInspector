package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.scrapers.LanguageDetector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Determine distribution of programming languages for projects named by users in a survey (response projects).
 */
public class LanguageDistribution {
	
	public static Map<String, Integer> determineLanguageDistribution(List<Project> projects) throws Exception {
		Map<String, Integer> langDistr = new HashMap<String, Integer>();
		for(Project p : projects) {
			List<String> langs = LanguageDetector.languagesOfProject(p);
			for(String lang : langs) {
				addLangOccurenceToDistribution(lang, langDistr);
			}
		}
		return langDistr;
	}
	
	private static void addLangOccurenceToDistribution(String lang, Map<String, Integer> langDistr) {
		if(langDistr.containsKey(lang)) {
			langDistr.put(lang, langDistr.get(lang)+1);
		}
		else {
			langDistr.put(lang, 1);
		}
	}

	public static List<Project> respProjectsToProjects(List<ResponseProjects> respProjsWithUser) {
		List<Project> projs = new LinkedList<Project>();
		List<ResponseProjects> rprojs = respProjsWithUser;
		for(ResponseProjects rp : rprojs) {
			for(Project p : rp.toProjectList()) {
				projs.add(p);
			}
		}
		return projs;
	}

}
