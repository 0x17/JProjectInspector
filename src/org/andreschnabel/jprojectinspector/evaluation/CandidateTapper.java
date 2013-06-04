package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.model.survey.Candidate;
import org.andreschnabel.jprojectinspector.model.survey.CandidateLst;
import org.andreschnabel.pecker.functional.FuncInPlace;
import org.andreschnabel.pecker.helpers.Helpers;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Greife Kandidaten für eine Umfrage von der GitHub-Timeline ab.
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
						if(notInOldSurvey(c, oldSurveyCandidates)) {
							if(FuncInPlace.addNoDups(candidates, c)) {
								Helpers.log("Candidate: " + c + " " + candidates.size() + "/" + upTo);
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

}
