package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;

import java.io.File;

public class ContributorsTestCommit implements OfflineMetric {

	public static int countNumTestContributors(File root) throws Exception {
		if(!root.exists())
			throw new Exception("Check out first!");

		// TODO: Implement this!
		return -1;
	}

	@Override
	public String getName() {
		return "ncontribstestcommit";
	}

	@Override
	public String getDescription() {
		return "Number of contributors that have commit comments containing substring 'test'.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return countNumTestContributors(repoRoot);
	}
}
