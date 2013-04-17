package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.functional.FuncInPlace;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanguageDetector {
	
	private LanguageDetector() {}
	
	public static List<String> languagesOfProject(Project p) throws Exception {
		List<String> langs = new LinkedList<String>();
		
		try {
			String html = Helpers.loadHTMLUrlIntoStr("https://github.com/"+p.owner+"/"+p.repoName);
			Pattern langPat = Pattern.compile("<span class=\"lang\">([^<]+)</span>");
			Matcher m = langPat.matcher(html);
			while(m.find()) {
				FuncInPlace.addNoDups(langs, m.group(1));
			}
			Helpers.log(p + " uses languages " + langs);
			
		} catch(Exception e) {
			Helpers.log("Error downloading langs for " + p);
		}
		
		return langs;
	}
	

}
