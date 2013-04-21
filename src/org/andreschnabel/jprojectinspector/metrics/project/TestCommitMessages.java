package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.git.GitHelpers;

import java.io.File;

public class TestCommitMessages implements OfflineMetric {

	public static int countNumTestCommitMessages(File root) throws Exception {
		if(!root.exists())
			throw new Exception("Check out first!");

		Predicate<String> containsTestSubstring = new Predicate<String>() {
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
