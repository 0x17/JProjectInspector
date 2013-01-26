package org.andreschnabel.jprojectinspector.scrapers;

import java.util.LinkedList;
import java.util.List;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;

public class SearchScraper {

	public static List<Project> searchByStars(String lang, int minStars, int numPages) throws Exception {
		String requestPrefix = "https://github.com/search?l="+lang+"&p=";
		String requestSuffix = "&q=stars%3A%3E"+minStars+"&ref=advsearch&type=Repositories";
		return searchByCommon(requestPrefix, requestSuffix, numPages);
	}
	
	public static List<Project> searchByForks(String lang, int minForks, int numPages) throws Exception {
		String requestPrefix = "https://github.com/search?l="+lang+"&p=";
		String requestSuffix = "&q=forks%3A%3E"+minForks+"&ref=advsearch&type=Repositories";
		return searchByCommon(requestPrefix, requestSuffix, numPages);
	}
	
	public static List<Project> searchByCommon(String requestPrefix, String requestSuffix, int numPages) throws Exception {
		List<Project> result = new LinkedList<Project>();
		final String regex = "<a href=\"(.+?/.+?)/stargazers\" title=\"Stargazers\" class=\"\">";
		for(int i=0; i<numPages; i++) {
			String requestUrl = requestPrefix + (i+1) + requestSuffix;
			String src = Helpers.loadHTMLUrlIntoStr(requestUrl);
			List<String> matches = RegexHelpers.batchMatchOneGroup(regex, src);
			for(String match : matches) {
				result.add(Project.fromString(match));
			}
		}
		return result;
	}
}
