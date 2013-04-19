package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.FuncInPlace;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserGuesser {

	public static CsvData assureHasUser(File responseCsv, List<String> users) throws Exception {
		CsvData responseData = CsvHelpers.parseCsv(responseCsv);
		String[] headers = responseData.getHeaders();
		if(!Func.fromArray(headers).contains("user")) {
			List<Project> userProjects = UserProjectCollector.userProjectsForUsers(users);

			responseData.addColumn("user");

			for(int row=0; row<responseData.rowCount(); row++) {
				ResponseProjects rp = new ResponseProjects();
				rp.lowestBugCount = responseData.getCellAt(row, SurveyFormat.LOWEST_BUG_COUNT_HEADER);
				rp.highestBugCount = responseData.getCellAt(row, SurveyFormat.HIGHEST_BUG_COUNT_HEADER);
				rp.leastTested = responseData.getCellAt(row, SurveyFormat.LEAST_TESTED_HEADER);
				rp.mostTested = responseData.getCellAt(row, SurveyFormat.MOST_TESTED_HEADER);
				String user = UserGuesser.guessUserWithProjects(rp, userProjects);
				responseData.setCellAt(row, "user", user);
			}
		}
		return responseData;
	}
	
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
