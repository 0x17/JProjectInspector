
package org.andreschnabel.jprojectinspector.metrics.impls;

import org.andreschnabel.jprojectinspector.Helpers;

import com.google.gson.Gson;

public class TravisMetrics {
	
	private final static String BASE_URL = "https://api.travis-ci.org/";
	
	private String owner, repoName;
	
	public TravisMetrics(String owner, String repoName) {
		this.owner = owner;
		this.repoName = repoName;
	}
	
	class RepoInfo {
		int id;
		String slug;
		String description;
		String public_key;
		int last_build_id;
		int last_build_number;
		int last_build_status;
		int last_build_result;
		int last_build_duration;
		int last_build_age;
		String last_build_started_at;
		String last_build_finished_at;
	}
	
	public RepoInfo getRepoInfo() throws Exception {
		Gson gson = new Gson();
		return gson.fromJson(Helpers.loadUrlIntoStr(BASE_URL + "repos/" + owner + "/" + repoName), RepoInfo.class);
	}
	
}
