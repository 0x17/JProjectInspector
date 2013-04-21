package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.git.GitContributorHelpers;

import java.io.File;

public class ContributorsOffline implements OfflineMetric {
	@Override
	public String getName() {
		return "NumContribs";
	}

	@Override
	public String getDescription() {
		return "Total number of contributors of git repostiory.\n"
		 + "Calculated as line count of output from \"git log --all --format=%aN\".";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return GitContributorHelpers.numContribs(repoRoot);
	}
}
