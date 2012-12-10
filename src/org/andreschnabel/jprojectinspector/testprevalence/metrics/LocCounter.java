package org.andreschnabel.jprojectinspector.testprevalence.metrics;

import org.andreschnabel.jprojectinspector.Helpers;
import org.andreschnabel.jprojectinspector.testprevalence.Globals;
import org.andreschnabel.jprojectinspector.testprevalence.model.Project;

import java.io.File;

public class LocCounter {

	public int countLocForProj(Project project) throws Exception {
		File root = new File(Globals.DEST_BASE+project.repoName);
		return countLocOfDir(root);
	}

	public int countLocOfSrcFile(File srcFile) throws Exception {
		String srcStr = Helpers.readEntireFile(srcFile);
		return countLocOfSrcStr(srcStr);
	}

	public int countLocOfSrcStr(String srcStr) {
		int counter = 0;
		for(int i=0; i<srcStr.length(); i++) {
			char c = srcStr.charAt(i);
			if(c == '\n')
				counter++;
		}
		return counter;
	}

	public int countLocOfDir(File root) throws Exception {
		if(root.isDirectory()) {
			int sum = 0;
			for(File f : root.listFiles()) {
				sum += countLocOfDir(f);
			}
			return sum;
		} else
			return countLocOfSrcFile(root);
	}

}