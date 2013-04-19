package org.andreschnabel.jprojectinspector.scrapers;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.functional.FuncInPlace;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

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


}
