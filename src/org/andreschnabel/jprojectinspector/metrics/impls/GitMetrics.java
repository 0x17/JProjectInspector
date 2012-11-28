
package org.andreschnabel.jprojectinspector.metrics.impls;

import org.andreschnabel.jprojectinspector.Helpers;

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
		Helpers.system("rm -rf " + destPath + projName);
	}

}
