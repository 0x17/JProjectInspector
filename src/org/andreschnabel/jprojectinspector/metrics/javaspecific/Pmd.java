package org.andreschnabel.jprojectinspector.metrics.javaspecific;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.pecker.helpers.Helpers;
import org.andreschnabel.pecker.helpers.ProcessHelpers;
import org.andreschnabel.pecker.helpers.StringHelpers;

import java.io.File;

/**
 * Anzahl von PMD-Regelverstößen für Java-Projekt.
 */
public class Pmd implements IOfflineMetric {

	public static String pmdPath = "libs/pmd-5.0.3/";

	@Override
	public String getName() {
		return "PMD";
	}

	@Override
	public String getDescription() {
		return "PMD source code analyzer frontend. Counts number of violations. Works only on Java and JavaScript!";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		String out = null;
		
		if(Helpers.runningOnUnix()) {
			out = ProcessHelpers.monitorProcess(new File(pmdPath), "bin/run.sh", "pmd", "-d", repoRoot.getAbsolutePath(), "-f","text","-R","java-basic,java-design");
		} else {
			// TODO: Windows call!
		}

		if(out == null) {
			return Double.NaN;
		}

		return (double)StringHelpers.countOccurencesOfWord(out, "\n");
	}
}
