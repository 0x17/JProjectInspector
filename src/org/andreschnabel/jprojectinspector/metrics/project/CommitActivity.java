package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.Helpers;

import com.google.gson.Gson;

public class CommitActivity {
	
	private class CommitActivityData {
		public CommitActivityTuple[] tuples;
	}
	
	@SuppressWarnings("unused")
	private class CommitActivityTuple {		
		public int[] days;
		public long week;
		public int total;
	}
	
	public int getNumOfRecentCommits(Project project) throws Exception {
		Gson gson = new Gson();
		String castr = Helpers.loadUrlIntoStr("https://github.com/"+project.owner+"/"+project.repoName+"/graphs/commit-activity-data");
		CommitActivityData cad = gson.fromJson(castr, CommitActivityData.class);
		return cad.tuples[cad.tuples.length-1].total;
	}

}
