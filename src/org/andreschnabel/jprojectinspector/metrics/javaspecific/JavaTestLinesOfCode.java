package org.andreschnabel.jprojectinspector.metrics.javaspecific;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

import java.io.File;

public class JavaTestLinesOfCode implements OfflineMetric {

	public static int countJavaTestLocOfDir(File root) throws Exception {
		if(root.isDirectory()) {
			int sum = 0;
			for(File f : root.listFiles()) {
				sum += countJavaTestLocOfDir(f);
			}
			return sum;
		} else {
			return root.getName().endsWith(".java") && UnitTestDetector.isJavaSrcTest(FileHelpers.readEntireFile(root), root.getName()) ? JavaLinesOfCode.countLocOfSrcFile(root) : 0;
		}
	}

	@Override
	public String getName() {
		return "jtloc";
	}

	@Override
	public String getDescription() {
		return "Java-specific version of total number of LOC for test files. Test files detected using heuristic.";
	}

	@Override
	public float measure(File repoRoot) throws Exception {
		return countJavaTestLocOfDir(repoRoot);
	}
}
