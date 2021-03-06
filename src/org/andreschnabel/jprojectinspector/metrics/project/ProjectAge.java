package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.git.GitRevisionHelpers;

import java.io.File;

/**
 * Alter eines geklonten Projekt mithilfe von "git rev-list".
 */
public class ProjectAge implements IOfflineMetric {

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
