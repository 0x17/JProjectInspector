package org.andreschnabel.jprojectinspector.scrapers;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.FuncInPlace;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * List programming languages used in project according to GitHub.
 */
public final class LanguageDetector {
	private LanguageDetector() {}

	public static List<String> languagesOfProject(Project p) throws Exception {
		List<String> langs = new LinkedList<String>();

		try {
			String html = Helpers.loadHTMLUrlIntoStr("https://github.com/" + p.owner + "/" + p.repoName);
			Pattern langPat = Pattern.compile("<span class=\"lang\">([^<]+)</span>");
			Matcher m = langPat.matcher(html);
			while(m.find()) {
				FuncInPlace.addNoDups(langs, m.group(1));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return langs;
	}

	public static int numProjectsWithLanguage(List<Project> projects, final String language) throws Exception {
		Predicate<Project> isLanguage = new Predicate<Project>() {
			@Override
			public boolean invoke(Project p) {
				try {
					return languagesOfProject(p).contains(language);
				} catch(Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		};
		return Func.count(isLanguage, projects);
	}

	public static void printSurveyPercentageInLanguage(String language) throws Exception {
		List<ResponseProjects> respProjs = ResponseProjects.fromPreprocessedCsvFile(new File("data/benchmark/WeightedEstimates.csv"));
		List<Project> projects = ResponseProjects.allProjects(respProjs);
		int numJavaProjects = numProjectsWithLanguage(projects, language);
		Helpers.log("Number of java projects = " + numJavaProjects);
		Helpers.log("Total number of projects = " + projects.size());
	}

	public static void main(String[] args) throws Exception {
		printSurveyPercentageInLanguage("Java");
	}

}
