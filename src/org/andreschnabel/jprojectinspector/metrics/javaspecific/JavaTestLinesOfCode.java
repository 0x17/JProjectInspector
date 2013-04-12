package org.andreschnabel.jprojectinspector.metrics.javaspecific;

import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

import java.io.File;

public class JavaTestLinesOfCode {

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

}
