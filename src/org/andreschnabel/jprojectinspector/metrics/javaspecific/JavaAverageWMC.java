package org.andreschnabel.jprojectinspector.metrics.javaspecific;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

import java.io.File;

public class JavaAverageWMC implements OfflineMetric {

	public int determineWMCforSrcStr(String srcStr, Aux a) {
		return StringHelpers.countOccurencesOfWords(srcStr, new String[]{"if", "for", "while", "case", "catch", "&&", "||", "?"});
	}

	public int determineWMCforSrcFile(File srcFile, Aux a) throws Exception {
		return determineWMCforSrcStr(FileHelpers.readEntireFile(srcFile), a);
	}

	@Override
	public String getName() {
		return "javgwmc";
	}

	@Override
	public String getDescription() {
		return "Average weighted methods per class.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return determineAverageWMC(repoRoot);
	}

	private class Aux {
		int ccSum;
		int count;
	}

	public Double determineAverageWMC(File root) throws Exception {
		Aux a = new Aux();
		determineAverageWMCforDir(root, a);
		return (double) a.ccSum / a.count;
	}

	private void determineAverageWMCforDir(File root, Aux a) throws Exception {
		if(root.isDirectory()) {
			for(File f : root.listFiles()) {
				determineAverageWMCforDir(f, a);
			}
		} else {
			if(root.getName().endsWith(".java")) {
				a.count++;
				a.ccSum += determineWMCforSrcFile(root, a);
			}
		}
	}

}
