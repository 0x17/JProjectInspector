package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.scrapers.LanguageDetector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bestimme Sprachverteilung für Projekte.
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

}
