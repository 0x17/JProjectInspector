package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHelpers;

import java.io.File;

public class ContributorsOffline implements OfflineMetric {
	@Override
	public String getName() {
		return "ncontribs";
	}

	@Override
	public String getDescription() {
		return "Total number of contributors of git repostiory.\n"
		 + "Calculated as line count of output from \"git log --all --format=%aN\".";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return GitHelpers.numContribs(repoRoot);
	}
}
