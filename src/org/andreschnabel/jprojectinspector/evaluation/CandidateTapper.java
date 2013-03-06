package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tap GitHub timeline for candidates for our evaluation experiment.
 * @author André Schnabel
 */
public class CandidateTapper {
	
	private CandidateTapper() {}
	
	public static List<Candidate> tapCandidates(final int upTo) throws Exception {
		final Pattern userPattern = Pattern.compile("\\{\"login\":\"([A-Za-z0-9-]+?)\",\"type\":\"User\",.+?\\}");
		final Pattern emailPattern = Pattern.compile("\"email\":\"([A-Za-z0-9-\\.@]+?)\"");
		final Pattern namePattern = Pattern.compile("\"name\":\"(.+?)\"");
		
		List<Candidate> candidates = new LinkedList<Candidate>();
		
		String lastTimeline = null;
		
		do {
			String timeline = Helpers.loadUrlIntoStr("https://github.com/timeline.json");
			if(lastTimeline != null && lastTimeline.equals(timeline)) {
				Thread.sleep(1000);
				Helpers.log("No change... repeat!");
				continue;
			}
			
			Matcher usrMatcher = userPattern.matcher(timeline);
			
			while(usrMatcher.find()) {
				String userStr = usrMatcher.group(0);
				if(userStr.contains("\"email\":")
					&& userStr.contains("\"name\":")
					&& !userStr.contains("\"email\":\"\"")
					&& !userStr.contains("\"name\":\"\"")) {

					Candidate c = new Candidate();
					c.login = usrMatcher.group(1);

					Matcher emailMatcher = emailPattern.matcher(userStr);
					if(emailMatcher.find()) {
						c.email = emailMatcher.group(1);
					}

					Matcher nameMatcher = namePattern.matcher(userStr);
					if(nameMatcher.find()) {
						c.name = nameMatcher.group(1);
					}

					if(c.name != null && c.email != null && c.login != null && !candidates.contains(c)) {
						candidates.add(c);
						Helpers.log("Added rough candidate: " + c);
					}
				}
			}
			
			lastTimeline = timeline;
		} while(candidates.size() < upTo);
		
		return candidates;
	}

	public static List<Candidate> addMostRecentRepoTriples(List<Candidate> candidates) throws Exception {
		for(Candidate c : candidates) {
			addMostRecentRepoTriple(c);
		}
		return candidates;
	}

	public static Candidate addMostRecentRepoTriple(Candidate candidate) throws Exception {
		String profile = Helpers.loadHTMLUrlIntoStr("https://github.com/" + candidate.login + "?tab=repositories");

		Pattern projPattern = Pattern.compile("<li class=\"public source[\\s\\S]+?(<time.+?</time>)");
		Matcher projMatcher = projPattern.matcher(profile);

		Pattern projNameSubPattern = Pattern.compile("<a href=\"/.+?/.+?\">(.+?)</a>");

		int curProj = 0;
		while(projMatcher.find() && curProj < 3) {
			String timeStr = projMatcher.group(1);
			if(timeStr.contains("2013")) {
				String projStr = projMatcher.group(0);
				Matcher projNameSubPatternMatcher = projNameSubPattern.matcher(projStr);
				if(projNameSubPatternMatcher.find()) {
					candidate.repos[curProj++] = projNameSubPatternMatcher.group(1);
				}
			}
		}

		return candidate;
	}

}
