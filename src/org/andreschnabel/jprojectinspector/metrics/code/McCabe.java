package org.andreschnabel.jprojectinspector.metrics.code;

import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

import java.io.File;

public class McCabe {

	public int determineMcCabeForSrcStr(String srcStr) {
		return StringHelpers.countOccurencesOfWords(srcStr, new String[]{"if", "for", "while", "case", "catch", "&&", "||", "?"});
	}

	public float determineMcCabeForSrcFile(File srcFile) throws Exception {
		return determineMcCabeForSrcStr(FileHelpers.readEntireFile(srcFile));
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
