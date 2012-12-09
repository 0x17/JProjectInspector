package org.andreschnabel.jprojectinspector.testprevalence.metrics;

import org.andreschnabel.jprojectinspector.Helpers;

import java.io.File;

public class McCabeDeterminator {

	public int determineMcCabeForSrcStr(String srcStr) {
		return Helpers.countOccurencesOfWords(srcStr, new String[]{"switch", "if", "else", "case"});
	}

	public float determineMcCabeForSrcFile(File srcFile) throws Exception {
		return determineMcCabeForSrcStr(Helpers.readEntireFile(srcFile));
	}

	public float determineMcCabeForDir(File root) throws Exception {
		if(root.isDirectory()) {
			int sum = 0;
			int count = root.listFiles().length;
			for(File f : root.listFiles()) {
				sum += determineMcCabeForDir(f);
			}
			return (float)sum / count;
		}
		else
			return determineMcCabeForSrcFile(root);
	}

}
