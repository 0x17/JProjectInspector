package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHelpers;

import java.io.File;

public class Commits implements OfflineMetric {
	@Override
	public String getName() {
		return "ncommits";
	}

	@Override
	public String getDescription() {
		return "Total number of commits in git repository.\n" +
				"Calculated as line count of output from \"git rev-list --no-merges master\".";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return GitHelpers.numCommits(repoRoot);
	}
}
