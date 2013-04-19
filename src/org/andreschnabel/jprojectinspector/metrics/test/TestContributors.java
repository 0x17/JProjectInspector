package org.andreschnabel.jprojectinspector.metrics.test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHelpers;

public final class TestContributors implements OfflineMetric {
	
	public static int numTestContribs(File root) throws Exception {
		List<File> testFiles = UnitTestDetector.getTestFiles(root);
		List<String> contribNames = new LinkedList<String>();
		for(File testFile : testFiles) {
			contribNames.addAll(GitHelpers.contribNamesForFile(testFile));
		}
		return Func.remDups(contribNames).size();
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
	public double measure(File repoRoot) throws Exception {
		return numTestContribs(repoRoot);
	}
}
