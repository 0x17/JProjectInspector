package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.model.survey.Candidate;
import org.andreschnabel.jprojectinspector.model.survey.CandidateLst;
import org.andreschnabel.jprojectinspector.utilities.functional.FuncInPlace;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tap GitHub timeline for candidates for our evaluation experiment.
 */
public class CandidateTapper {
	
	private CandidateTapper() {}
	
	public static List<Candidate> tapCandidates(final int upTo, List<Candidate> candidates, List<Candidate> oldCandidates) throws Exception {
		final Pattern userPattern = Pattern.compile("\\{\"login\":\"([A-Za-z0-9-]+?)\",\"type\":\"User\",.+?\\}");
		final Pattern emailPattern = Pattern.compile("\"email\":\"([A-Za-z0-9-\\.@]+?)\"");
		final Pattern namePattern = Pattern.compile("\"name\":\"(.+?)\"");

		List<Candidate> oldSurveyCandidates = CandidateLst.fromCsv(new File("data/candidates500.csv")).candidates;
		
		if(oldCandidates != null) {
			candidates.addAll(oldCandidates);
		}
		
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

					if(c.name != null && c.email != null && c.login != null && candidateWithoutLogin(c.login, candidates)) {
						addMostRecentRepoTriple(c);
						if(c.repos[0] != null && c.repos[1] != null && c.repos[2] != null) {
							if(notInOldSurvey(c, oldSurveyCandidates)) {
								if(FuncInPlace.addNoDups(candidates, c)) {
									Helpers.log("Candidate: " + c + " " + candidates.size() + "/" + upTo);
								}
							}
						}
					}
				}
			}
			
			lastTimeline = timeline;
		} while(candidates.size() < upTo);
		
		return candidates;
	}

	private static boolean notInOldSurvey(Candidate c, List<Candidate> oldSurveyCandidates) {
		return !oldSurveyCandidates.contains(c);
	}

	private static boolean candidateWithoutLogin(String login, List<Candidate> candidates) {
		for(Candidate c : candidates) {
			if(c.login.equals(login))
				return false;
		}
		
		return true;
	}

	public static Candidate addMostRecentRepoTriple(Candidate candidate) throws Exception {
		String profile;
		try {
			profile = Helpers.loadHTMLUrlIntoStr("https://github.com/" + candidate.login + "?tab=repositories");
		} catch(Exception e) {
			return candidate;
		}

		Pattern projPattern = Pattern.compile("<li class=\"public source[\\s\\S]+?(<time.+?</time>)");
		Matcher projMatcher = projPattern.matcher(profile);

		Pattern projNameSubPattern = Pattern.compile("<a href=\"/.+?/.+?\">(.+?)</a>");

		int curProj = 0;
		while(projMatcher.find() && curProj < 3) {
			String projStr = projMatcher.group(0);
			String timeStr = projMatcher.group(1);

			if(timeStr.contains("2012")) {
				Matcher projNameSubPatternMatcher = projNameSubPattern.matcher(projStr);
				if(projNameSubPatternMatcher.find()) {
					String nrepo = projNameSubPatternMatcher.group(1);
					if(doesntContainRepo(candidate.repos, nrepo)) {
						candidate.repos[curProj++] = nrepo;
					}
				}
			}
		}

		return candidate;
	}

	private static boolean doesntContainRepo(String[] repos, String repo) {
		for(int i=0; i<3; i++) {
			if(repos[i] != null && repos[i].equals(repo))
				return false;
		}
		return true;
	}

}
