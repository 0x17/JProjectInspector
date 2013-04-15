package org.andreschnabel.jprojectinspector.metrics.test;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.code.Cloc;

import java.io.File;
import java.util.List;

public class TestLinesOfCode implements OfflineMetric {

	public static int countTestLocHeuristic(File path) throws Exception {
		List<File> testFiles = UnitTestDetector.getTestFiles(path);
		int locSum = 0;
		for(File testFile : testFiles) {
			List<Cloc.ClocResult> results = Cloc.determineLinesOfCode(testFile.getParentFile(), testFile.getName());
			if(results.size() >= 1) {
				locSum += Cloc.ClocResult.accumResults(results).codeLines;
			}
		}
		return locSum;
	}

	@Override
	public String getName() {
		return "tloc";
	}

	@Override
	public String getDescription() {
		return "Total lines of code in modules (probably) containing test (according to heuristic).\n"+
				"Lines of code per module is calculated using cloc.";
	}

	@Override
	public float measure(File repoRoot) throws Exception {
		return countTestLocHeuristic(repoRoot);
	}
}
