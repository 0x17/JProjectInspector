package org.andreschnabel.jprojectinspector.metrics.test;

import java.io.File;

import org.andreschnabel.jprojectinspector.metrics.code.LinesOfCode;
import org.andreschnabel.jprojectinspector.metrics.test.prevalence.UnitTestDetector;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

public class TestLinesOfCode {

	public int countTestLocOfDir(File root) throws Exception {
		if(root.isDirectory()) {
			int sum = 0;
			for(File f : root.listFiles()) {
				sum += countTestLocOfDir(f);
			}
			return sum;
		} else {
			return root.getName().endsWith(".java") && UnitTestDetector.isJavaSrcTest(FileHelpers.readEntireFile(root), root.getName()) ? LinesOfCode.countLocOfSrcFile(root) : 0;
		}
	}

}
