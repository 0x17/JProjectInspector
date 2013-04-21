package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.git.GitHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

import java.io.File;

public class BugFixCommitMessages implements OfflineMetric {
	private static final String[] BUG_FIX_STRS = new String[] {
			"bug", "defect", "fix", "issue", "solve", "correct"
	};

	@Override
	public String getName() {
		return "NumBugFixCommitMessages";
	}

	@Override
	public String getDescription() {
		return "Number of commit messages containing substring indicating defect or fix.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		return countBugFixCommitMessages(repoRoot);
	}

	private static double countBugFixCommitMessages(File repoRoot) throws Exception {
		Predicate<String> containsBugFixSubstring = new Predicate<String>() {
			@Override
			public boolean invoke(String s) {
				return StringHelpers.containsOneOf(s.toLowerCase(), BUG_FIX_STRS);
			}
		};
		return Func.count(containsBugFixSubstring, GitHelpers.listCommitMessages(repoRoot));
	}
}
