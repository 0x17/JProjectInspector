package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.model.survey.UserProject;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserProjectCollector {
	
	private UserProjectCollector() {}
	
	public static List<UserProject> userProjectsForUsers(List<String> users) {
		List<UserProject> userProjects = new LinkedList<UserProject>();
		for(String user : users) {
			userProjects.addAll(userProjectsForUser(user));
		}
		return userProjects;
	}
	
	public static List<UserProject> userProjectsForUser(String user) {
		List<UserProject> projs = new LinkedList<UserProject>();
		String profile;
		try {
			profile = Helpers.loadHTMLUrlIntoStr("https://github.com/" + user + "?tab=repositories");
		} catch(Exception e) {
			return projs;
		}

		Pattern projPattern = Pattern.compile("<li class=\"public source[\\s\\S]+?(<time.+?</time>)");
		Matcher projMatcher = projPattern.matcher(profile);

		Pattern projNameSubPattern = Pattern.compile("<a href=\"/.+?/.+?\">(.+?)</a>");

		while(projMatcher.find()) {
			String projStr = projMatcher.group(0);
			//String timeStr = projMatcher.group(1);
			
			Matcher projNameSubPatternMatcher = projNameSubPattern.matcher(projStr);
			if(projNameSubPatternMatcher.find()) {
				String nrepo = projNameSubPatternMatcher.group(1);
				UserProject nproj = new UserProject(user, nrepo);
				projs.add(nproj);
				Helpers.log(nproj.toString());
			}
		}
		
		return projs;
	}
}
