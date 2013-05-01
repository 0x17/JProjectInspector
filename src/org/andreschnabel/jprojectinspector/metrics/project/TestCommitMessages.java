package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.IPredicate;
import org.andreschnabel.jprojectinspector.utilities.git.GitHelpers;

import java.io.File;

/**
 * Anzahl der Commit-Nachrichten mit Teilstring "test" (case insensitive).
 */
public class TestCommitMessages implements IOfflineMetric {

	public static int countNumTestCommitMessages(File root) throws Exception {
		if(!root.exists())
			throw new Exception("Check out first!");

		IPredicate<String> containsTestSubstring = new IPredicate<String>() {
			@Override
			public boolean invoke(String s) {
				return s.toLowerCase().contains("test");
			}
		};
		return Func.count(containsTestSubstring, GitHelpers.listCommitMessages(root));
	}

	@Override
	public String getName() {
		return "NumTestCommitMessages";
	}

	@Override
	public String getDescription() {
		return "Number of commit messages containing substring 'test'.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return countNumTestCommitMessages(repoRoot);
	}
}
