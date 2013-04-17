package org.andreschnabel.jprojectinspector.metrics.test;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.code.Cloc;
import org.andreschnabel.jprojectinspector.utilities.functional.BinaryOperator;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;

import java.io.File;
import java.util.List;

public class TestLinesOfCode implements OfflineMetric {

	public static int countTestLocHeuristic(File path) throws Exception {
		List<File> testFiles = UnitTestDetector.getTestFiles(path);
		return Func.reduce(new BinaryOperator<File, Integer>() {
			@Override
			public Integer invoke(Integer accum, File testfile) {
				try {
					List<Cloc.ClocResult> results = Cloc.determineLinesOfCode(testfile.getParentFile(), testfile.getName());
					if(results.size() >= 1) {
						accum += Cloc.ClocResult.accumResults(results).codeLines;
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				return accum;
			}
		}, 0, testFiles);
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
