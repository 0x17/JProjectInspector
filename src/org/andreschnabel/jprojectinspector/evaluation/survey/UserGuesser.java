package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.evaluation.projects.UserProject;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserGuesser {
	
	public static String guessUserWithProjects(ResponseProjects rp, List<UserProject> usrProjs) {
		List<String> projNames = new ArrayList<String>();
		ListHelpers.addNoDups(projNames, rp.highestBugCount);
		ListHelpers.addNoDups(projNames, rp.lowestBugCount);
		ListHelpers.addNoDups(projNames, rp.leastTested);
		ListHelpers.addNoDups(projNames, rp.mostTested);
		
		Map<String, Integer> userHits = new HashMap<String, Integer>();

		for(UserProject up : usrProjs) {
			if(projNames.contains(up.name)) {
				if(userHits.containsKey(up.user)) {
					userHits.put(up.user, userHits.get(up.user)+1);
				} else {
					userHits.put(up.user, 1);
				}
			}
		}
		
		int candidateHits = 0;
		String candidate = null;
		
		for(String usr : userHits.keySet()) {
			if(userHits.get(usr) > candidateHits) {
				candidateHits = userHits.get(usr);
				candidate = usr;
			}
		}
		
		return candidate;
	}

}
