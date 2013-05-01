package org.andreschnabel.jprojectinspector.metrics.javaspecific;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

import java.io.File;

/**
 * Anzahl Testcodezeilen speziell für Java implementiert.
 * Ignoriert Kommentarzeilen.
 */
public class JavaTestLinesOfCode implements IOfflineMetric {

	public static int countJavaTestLocOfDir(File root) throws Exception {
		if(root.isDirectory()) {
			int sum = 0;
			for(File f : root.listFiles()) {
				sum += countJavaTestLocOfDir(f);
			}
			return sum;
		} else {
			return FileHelpers.extension(root).equals("java") && UnitTestDetector.isJavaSrcTest(FileHelpers.readEntireFile(root), root.getName()) ? JavaLinesOfCode.countLocOfSrcFile(root) : 0;
		}
	}

	@Override
	public String getName() {
		return "JavaTestLinesOfCode";
	}

	@Override
	public String getDescription() {
		return "Java-specific version of total number of LOC for test files. Test files detected using heuristic.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		if(JavaCommon.containsNoJavaCode(repoRoot)){
			return Double.NaN;
		}

		return countJavaTestLocOfDir(repoRoot);
	}
}
