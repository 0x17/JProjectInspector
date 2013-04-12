package org.andreschnabel.jprojectinspector.metrics.test;

import org.andreschnabel.jprojectinspector.metrics.code.Cloc;

import java.io.File;
import java.util.List;

public class TestLinesOfCode {

	public static int countTestLocHeuristic(File path) throws Exception {
		List<File> testFiles = UnitTestDetector.getTestFiles(path);
		int locSum = 0;
		for(File testFile : testFiles) {
			List<Cloc.ClocResult> results = Cloc.determineLinesOfCode(testFile.getParentFile(), testFile.getName());
			if(results.size() >= 1)
				locSum += results.get(0).codeLines;
		}
		return locSum;
	}
}
