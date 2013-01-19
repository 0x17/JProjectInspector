package org.andreschnabel.jprojectinspector.metrics.project;

import java.util.List;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;

public class CommitActivity {

	private final static int NUM_PREV_WEEKS_CONSIDERED = 3;

	public int getNumOfRecentCommits(Project project) throws Exception {
		String castr = Helpers.loadUrlIntoStr("https://github.com/" + project.owner + "/" + project.repoName + "/graphs/commit-activity-data");
		String regex = "\"total\":([0-9]+)";
		List<String> totals = RegexHelpers.batchMatchOneGroup(regex, castr);

		if(totals.isEmpty()) return 0;

		int numRecentCommits = 0;
		int numWeeks = totals.size();

		for(int i = numWeeks - 1; i >= numWeeks - NUM_PREV_WEEKS_CONSIDERED; i--) {
			numRecentCommits += Integer.valueOf(totals.get(i));
		}

		return numRecentCommits;
	}

}
