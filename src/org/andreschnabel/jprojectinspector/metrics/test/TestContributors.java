package org.andreschnabel.jprojectinspector.metrics.test;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.BinaryOperator;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;

import java.io.File;
import java.util.List;

public final class TestContributors implements OfflineMetric {
	
	public static int numTestContribs(File root) throws Exception {
		List<File> testFiles = UnitTestDetector.getTestFiles(root);
		return ListHelpers.reduce(new BinaryOperator<File, Integer>() {
			@Override
			public Integer invoke(Integer accum, File test) {
				try {
					return accum + GitHelpers.contribNamesForFile(test).size();
				} catch(Exception e) {
					e.printStackTrace();
				}
				return accum;
			}
		}, 0, testFiles);
	}

	@Override
	public String getName() {
		return "ntestcontribs";
	}

	@Override
	public String getDescription() {
		return "Number of contributors that have committed to probable test files (according to heuristic).";
	}

	@Override
	public float measure(File repoRoot) throws Exception {
		return numTestContribs(repoRoot);
	}
}
