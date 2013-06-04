package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.pecker.functional.FuncInPlace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Versuche zugehörigen Nutzer zu gegebener Antwortliste von Projekten zu erraten.
 */
public class UserGuesser {

	public static String guessUserWithProjects(ResponseProjects rp, List<Project> usrProjs) {
		List<String> projNames = new ArrayList<String>();
		FuncInPlace.addNoDups(projNames, rp.highestBugCount);
		FuncInPlace.addNoDups(projNames, rp.lowestBugCount);
		FuncInPlace.addNoDups(projNames, rp.leastTested);
		FuncInPlace.addNoDups(projNames, rp.mostTested);

		Pattern p = Pattern.compile("https://github.com/(.+)/");

		for(int i=0; i<projNames.size(); i++) {
			String pname = projNames.get(i);
			// Sonderfall: Nutzer hat in Antwort den gesamten Repo-Link angegeben.
			// Sein Name ist schon im Link!
			if(pname.startsWith("https://github.com/")) {
				Matcher m = p.matcher(pname);
				m.find();
				return m.group(1);
			}
		}
		
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
