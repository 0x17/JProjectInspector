package org.andreschnabel.jprojectinspector.metrics.churn;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.git.GitChurnHelpers;
import org.andreschnabel.jprojectinspector.utilities.git.GitRevisionHelpers;

import java.io.File;

public class AverageChurnPerRevision implements IOfflineMetric {
	@Override
	public String getName() {
		return "AverageChurnPerRevision";
	}

	@Override
	public String getDescription() {
		return "Average number of lines added / removed per revision over all revisions.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		String[] commits = GitRevisionHelpers.listAllRevisions(repoRoot);

		int numCommits = commits.length;
		int churnedLocSum = 0;
		for(String commit : commits) {
			GitChurnHelpers.ChurnStats stats = GitChurnHelpers.getChurnStatsForRevision(repoRoot, commit);
			churnedLocSum += stats.getChurnedLoc();
		}

		if(numCommits == 0) {
			return 0.0;
		} else {
			return (double)churnedLocSum / (double)numCommits;
		}
	}
}
