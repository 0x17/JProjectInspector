package org.andreschnabel.jprojectinspector.scrapers;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.pecker.functional.Func;
import org.andreschnabel.pecker.functional.FuncInPlace;
import org.andreschnabel.pecker.functional.IPredicate;
import org.andreschnabel.pecker.helpers.Helpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Liste Programmiersprachen auf, welche in Projekt laut GitHub genutzt worden sind.
 * Benutzt Scraping!
 */
public final class LanguageDetector {
	/**
	 * Nur statische Methoden.
	 */
	private LanguageDetector() {}

	/**
	 * Sprachen, welche von Projekt p genutzt werden.
	 * @param p Projekt (owner, repo).
	 * @return Benutzte Sprachen in p.
	 * @throws Exception
	 */
	public static List<String> languagesOfProject(Project p) throws Exception {
		List<String> langs = new LinkedList<String>();

		try {
			String html = Helpers.loadHTMLUrlIntoStrRetry("https://github.com/" + p.owner + "/" + p.repoName, 10);
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

	/**
	 * Zähle Anzahl von Projekten, welche unter anderem gegebene Sprache verwenden.
	 * @param projects Projektliste.
	 * @param language Name einer Programmiersprache.
	 * @return Anzahl der Projekte aus der Liste, welche gegbene Sprache benutzen.
	 * @throws Exception
	 */
	public static int numProjectsWithLanguage(List<Project> projects, final String language) throws Exception {
		IPredicate<Project> isLanguage = new IPredicate<Project>() {
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

	/**
	 * Gebe Anteil der Projekte aus, die bestimmte Sprache nutzen.
	 * @param language Programmiersprache.
	 * @throws Exception
	 */
	public static void printSurveyPercentageInLanguage(String language) throws Exception {
		List<ResponseProjects> respProjs = ResponseProjects.fromPreprocessedCsvFile(new File("data/benchmark/WeightedEstimates.csv"));
		List<Project> projects = ResponseProjects.allProjects(respProjs);
		int numLangProjs = numProjectsWithLanguage(projects, language);
		Helpers.log("Number "+language+" projects = " + numLangProjs);
		Helpers.log("Total number of projects = " + projects.size());
	}

	public static void main(String[] args) throws Exception {
		printSurveyPercentageInLanguage("Java");
	}

}
