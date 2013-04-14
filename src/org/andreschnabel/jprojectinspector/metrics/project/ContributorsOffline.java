package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHelpers;

import java.io.File;

public class ContributorsOffline implements OfflineMetric {
	@Override
	public float measure(File repoRoot) throws Exception {
		return GitHelpers.numContribs(repoRoot);
	}
}
