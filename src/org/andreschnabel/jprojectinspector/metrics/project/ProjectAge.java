package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.git.GitRevisionHelpers;

import java.io.File;

public class ProjectAge implements OfflineMetric {

	public static long getProjectAge(File repoRoot) throws Exception {
		String oldestRev = GitRevisionHelpers.getOldestRevision(repoRoot);
		return GitRevisionHelpers.getDateOfRevision(repoRoot, oldestRev);
	}

	@Override
	public String getName() {
		return "ProjectAge";
	}

	@Override
	public String getDescription() {
		return "Get age of project in milliseconds.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return getProjectAge(repoRoot);
	}

}
