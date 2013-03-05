package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CandidateFilter {

	private CandidateFilter() {}

	public static List<Candidate> filterCandidates(List<Candidate> candidates) throws Exception {
		List<Candidate> result = new LinkedList<Candidate>();
		for(Candidate c : candidates) {
			if(qualifies(c))
				result.add(c);
		}
		return result;
	}

	public static boolean qualifies(Candidate candidate) throws Exception {
		String profile = Helpers.loadHTMLUrlIntoStr("https://github.com/" + candidate.login + "?tab=repositories");
		Pattern projPattern = Pattern.compile("<li class=\"public source[\\s\\S]+?(<time.+?</time>)");
		Matcher projMatcher = projPattern.matcher(profile);
		int nvalidprojs = 0;
		while(projMatcher.find()) {
			String timeStr = projMatcher.group(1);
			if(timeStr.contains("2013"))
				nvalidprojs++;
		}
		return nvalidprojs >= 3;
	}

}
