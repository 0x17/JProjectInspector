package org.andreschnabel.jprojectinspector.metrics.test;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.git.GitContributorHelpers;
import org.andreschnabel.pecker.functional.Func;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Anzahl der Beitragenden zu Tests in einem geklonten Repository.
 */
public final class TestContributors implements IOfflineMetric {

	/**
	 * Zähle Anzahl der Beitragenden zu vermutlichen Modultest-Dateien innerhalb von Wurzelverzeichnis.
	 * @param root Wurzelverzeichnis.
	 * @return Anzahl der Beitragenden zu vermutlichen Modultest-Dateien innerhalb von Wurzelverzeichnis.
	 * @throws Exception
	 */
	public static int numTestContribs(File root) throws Exception {
		List<File> testFiles = UnitTestDetector.getTestFiles(root);
		List<String> contribNames = new LinkedList<String>();
		for(File testFile : testFiles) {
			contribNames.addAll(GitContributorHelpers.contribNamesForFile(testFile));
		}
		return Func.remDups(contribNames).size();
	}

	@Override
	public String getName() {
		return "NumTestContribs";
	}

	@Override
	public String getDescription() {
		return "Number of contributors that have committed to probable test files (according to heuristic).";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return numTestContribs(repoRoot);
	}
}
