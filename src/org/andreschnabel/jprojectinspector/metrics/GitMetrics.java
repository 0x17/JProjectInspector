
package org.andreschnabel.jprojectinspector.metrics;

import org.andreschnabel.jprojectinspector.Helpers;

import java.io.File;

public class GitMetrics implements AutoCloseable {
	
	private final static String BASE_URL = "https://github.com/";
	private String projName;
	private String destPath;
	
	public GitMetrics(String owner, String repoName) {
		this(owner, repoName, "/tmp/");
	}
	
	public GitMetrics(String owner, String repoName, String destPath) {
		Helpers.system("git clone " + BASE_URL + owner + "/" + repoName + " " + destPath);
		projName = repoName;
		this.destPath = destPath;
	}
	
	@Override
	public void close() throws Exception {
		Helpers.deleteDir(new File(destPath + projName));
	}

	public GitSummary getSummary() {
		GitSummary summary = new GitSummary();
		summary.numCommits = getNumCommits();
		return summary;
	}

	private int getNumCommits() {
		return 0;
	}

	public class GitSummary {
		int numCommits;
	}
}
