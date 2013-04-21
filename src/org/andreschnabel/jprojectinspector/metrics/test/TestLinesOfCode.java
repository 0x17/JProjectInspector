package org.andreschnabel.jprojectinspector.metrics.test;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.code.Cloc;

import java.io.File;
import java.util.List;

public class TestLinesOfCode implements OfflineMetric {

	public static int countTestLocHeuristic(File path) throws Exception {
		List<File> testFiles = UnitTestDetector.getTestFiles(path);
		return Cloc.sumOfLinesOfCodeForFiles(testFiles);
	}

	@Override
	public String getName() {
		return "TestLinesOfCode";
	}

	@Override
	public String getDescription() {
		return "Total lines of code in modules (probably) containing test (according to heuristic).\n"+
				"Lines of code per module is calculated using cloc.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return countTestLocHeuristic(repoRoot);
	}
}
