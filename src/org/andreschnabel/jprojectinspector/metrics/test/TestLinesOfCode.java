package org.andreschnabel.jprojectinspector.metrics.test;

import org.andreschnabel.jprojectinspector.metrics.code.LinesOfCode;
import org.andreschnabel.jprojectinspector.utilities.Helpers;

import java.io.File;

public class TestLinesOfCode {

	private LinesOfCode loc = new LinesOfCode();
	
	public int countTestLocOfDir(File root) throws Exception {
		if(root.isDirectory()) {
			int sum = 0;
			for(File f : root.listFiles()) {
				sum += countTestLocOfDir(f);
			}
			return sum;
		}
		else {
			return root.getName().endsWith(".java") && UnitTestDetector.isJavaSrcTest(Helpers.readEntireFile(root), root.getName()) ? loc.countLocOfSrcFile(root) : 0;
		}
	}

}
