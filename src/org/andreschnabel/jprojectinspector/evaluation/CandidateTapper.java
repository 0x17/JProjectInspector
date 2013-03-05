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
					emailMatcher.find();
					c.email = emailMatcher.group(1);

					Matcher nameMatcher = namePattern.matcher(userStr);
					nameMatcher.find();
					c.name = nameMatcher.group(1);

					candidates.add(c);
				}
			}
			
			lastTimeline = timeline;
		} while(candidates.size() < upTo);
		
		return candidates;
	}
	
	public static void main(String[] args) throws Exception {
		for(Candidate candidate : tapCandidates(1))
			System.out.println(candidate);
	}

}
