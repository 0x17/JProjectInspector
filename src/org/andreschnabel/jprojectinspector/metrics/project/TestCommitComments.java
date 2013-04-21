package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.git.GitHelpers;

import java.io.File;

public class TestCommitComments implements OfflineMetric {

	public static int countNumTestCommitComments(File root) throws Exception {
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
		return "NumTestCommitCommentContribs";
	}

	@Override
	public String getDescription() {
		return "Number of contributors that have commit comments containing substring 'test'.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return countNumTestCommitComments(repoRoot);
	}
}
