package org.andreschnabel.jprojectinspector.metrics.test;

import org.andreschnabel.jprojectinspector.metrics.code.LinesOfCode;

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
			if(!root.getName().toLowerCase().contains("test")) {
				return 0;
			} else {
				return loc.countLocOfSrcFile(root);
			}
		}
	}

}
