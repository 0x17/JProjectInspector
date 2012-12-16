
package org.andreschnabel.jprojectinspector.obsolete.metrics;

import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.io.File;

public class GitMetrics {// implements AutoCloseable {
	
	private final static String BASE_URL = "https://github.com/";
	private String projName;
	private String destPath;
	
	public GitMetrics(String owner, String repoName) throws Exception {
		this(owner, repoName, "/tmp/");
	}
	
	public GitMetrics(String owner, String repoName, String destPath) throws Exception {
		Helpers.system("git clone " + BASE_URL + owner + "/" + repoName + " " + destPath);
		projName = repoName;
		this.destPath = destPath;
	}
	
	//@Override
	public void close() throws Exception {
		FileHelpers.deleteDir(new File(destPath + projName));
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
