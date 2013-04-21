package org.andreschnabel.jprojectinspector.metrics.code;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;

import java.io.File;
import java.util.List;

public class AvgLocPerTestFile implements OfflineMetric {
	@Override
	public String getName() {
		return "AvgLocPerTestFile";
	}

	@Override
	public String getDescription() {
		return "Average lines of code of modules containing tests.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		List<File> srcFiles = UnitTestDetector.getTestFiles(repoRoot);
		int numSourceFiles = srcFiles.size();
		int locSum = Cloc.sumOfLinesOfCodeForFiles(srcFiles);
		return (double)locSum / (double)numSourceFiles;
	}
}
