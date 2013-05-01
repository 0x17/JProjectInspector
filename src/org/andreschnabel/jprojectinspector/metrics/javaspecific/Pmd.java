package org.andreschnabel.jprojectinspector.metrics.javaspecific;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ProcessHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

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

		// TODO: Refactor out PmdHelpers for cpd and pmd stuff making OnlineMetric classes minimal.
		// TODO: PmdJunit for java-junit ruleset
		// JUnit violation count of this project is 110
		if(Helpers.runningOnUnix()) {
			out = ProcessHelpers.monitorProcess(new File(pmdPath), "bin/run.sh", "pmd", "-d", repoRoot.getAbsolutePath(), "-f","text","-R","java-basic,java-design");
		}

		if(out == null) {
			return Double.NaN;
		}

		return (double)StringHelpers.countOccurencesOfWord(out, "\n");
	}
}
