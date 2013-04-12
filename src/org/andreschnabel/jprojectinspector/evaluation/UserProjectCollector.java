package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.model.survey.Candidate;
import org.andreschnabel.jprojectinspector.model.survey.UserProject;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserProjectCollector {
	
	private UserProjectCollector() {}
	
	public static List<UserProject> userProjectsForCandidates(List<Candidate> candidates) {
		List<UserProject> userProjects = new LinkedList<UserProject>();
		for(Candidate c : candidates) {
			userProjects.addAll(userProjectsForCandidate(c));
		}
		return userProjects;
	}
	
	public static List<UserProject> userProjectsForCandidate(Candidate candidate) {
		List<UserProject> projs = new LinkedList<UserProject>();
		String profile;
		try {
			profile = Helpers.loadHTMLUrlIntoStr("https://github.com/" + candidate.login + "?tab=repositories");
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
				UserProject nproj = new UserProject(candidate.login, nrepo);
				projs.add(nproj);
				Helpers.log(nproj.toString());
			}
		}
		
		return projs;
	}
}
