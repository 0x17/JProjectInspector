package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.git.GitRevisionHelpers;

import java.io.File;

public class Revisions implements OfflineMetric {
	@Override
	public String getName() {
		return "NumRevisions";
	}

	@Override
	public String getDescription() {
		return "Total number of revisions in git repository.\n" +
				"Calculated as line count of output from \"git rev-list --no-merges master\".";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return GitRevisionHelpers.numRevisions(repoRoot);
	}
}
