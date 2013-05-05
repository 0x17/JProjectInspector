package org.andreschnabel.jprojectinspector.metrics.javaspecific;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ProcessHelpers;

import java.io.File;

/**
 * Duplikaterkennung.
 */
public class Cpd implements IOfflineMetric {
	@Override
	public String getName() {
		return "CopyPasteDetector";
	}

	@Override
	public String getDescription() {
		return "Copy-paste-detector frontend. Tool distributed with PMD. Works with Java, C, C++, C#, PHP, Ruby, Fortran and JavaScript code. Number of duplicate lines.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		String out = null;

		if(Helpers.runningOnUnix()) {
			out = ProcessHelpers.monitorProcess(new File(Pmd.pmdPath), "bin/run.sh","cpd","--minimum-tokens","100","--files",repoRoot.getAbsolutePath(),"--format csv");
		} else {
			// TODO: Windows call!
		}

		if(out == null) {
			return Double.NaN;
		}

		double dupLineSum = 0.0;

		String[] lines = out.split("\n");
		for(int i=1; i<lines.length; i++) {
			dupLineSum += Double.valueOf(lines[i].split(",")[0]);
		}

		return dupLineSum;
	}
}
