package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.utilities.functional.FuncInPlace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserGuesser {

	public static String guessUserWithProjects(ResponseProjects rp, List<Project> usrProjs) {
		List<String> projNames = new ArrayList<String>();
		FuncInPlace.addNoDups(projNames, rp.highestBugCount);
		FuncInPlace.addNoDups(projNames, rp.lowestBugCount);
		FuncInPlace.addNoDups(projNames, rp.leastTested);
		FuncInPlace.addNoDups(projNames, rp.mostTested);
		
		Map<String, Integer> userHits = new HashMap<String, Integer>();

		for(Project up : usrProjs) {
			if(projNames.contains(up.repoName)) {
				if(userHits.containsKey(up.owner)) {
					userHits.put(up.owner, userHits.get(up.owner)+1);
				} else {
					userHits.put(up.owner, 1);
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
