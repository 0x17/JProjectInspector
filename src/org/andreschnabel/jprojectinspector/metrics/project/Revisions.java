package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.git.GitRevisionHelpers;

import java.io.File;

/**
 * Anzahl der Revisionen von geklontem Repository mithilfe von "git rev-list".
 */
public class Revisions implements IOfflineMetric {
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
