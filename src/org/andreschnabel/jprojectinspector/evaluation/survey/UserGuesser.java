package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.evaluation.UserProjectCollector;
import org.andreschnabel.jprojectinspector.metrics.survey.BugCountEstimation;
import org.andreschnabel.jprojectinspector.metrics.survey.TestEffortEstimation;
import org.andreschnabel.jprojectinspector.model.CsvData;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.utilities.helpers.CsvHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserGuesser {

	public static CsvData assureHasUser(File responseCsv, List<String> users) throws Exception {
		CsvData responseData = CsvHelpers.parseCsv(responseCsv);
		String[] headers = responseData.getHeaders();
		if(!ListHelpers.fromArray(headers).contains("user")) {
			List<Project> userProjects = UserProjectCollector.userProjectsForUsers(users);

			responseData.addColumn("user");

			for(int row=0; row<responseData.rowCount(); row++) {
				ResponseProjects rp = new ResponseProjects();
				rp.lowestBugCount = responseData.getCellAt(row, BugCountEstimation.LOWEST_BUG_COUNT_HEADER);
				rp.highestBugCount = responseData.getCellAt(row, BugCountEstimation.HIGHEST_BUG_COUNT_HEADER);
				rp.leastTested = responseData.getCellAt(row, TestEffortEstimation.LEAST_TESTED_HEADER);
				rp.mostTested = responseData.getCellAt(row, TestEffortEstimation.MOST_TESTED_HEADER);
				String user = UserGuesser.guessUserWithProjects(rp, userProjects);
			}
		}
		return responseData;
	}
	
	public static String guessUserWithProjects(ResponseProjects rp, List<Project> usrProjs) {
		List<String> projNames = new ArrayList<String>();
		ListHelpers.addNoDups(projNames, rp.highestBugCount);
		ListHelpers.addNoDups(projNames, rp.lowestBugCount);
		ListHelpers.addNoDups(projNames, rp.leastTested);
		ListHelpers.addNoDups(projNames, rp.mostTested);
		
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
