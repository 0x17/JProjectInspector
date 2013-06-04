package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.utilities.git.GitHelpers;
import org.andreschnabel.pecker.functional.Func;
import org.andreschnabel.pecker.functional.IPredicate;
import org.andreschnabel.pecker.helpers.StringHelpers;

import java.io.File;

/**
 * Zählt Commit-Nachrichten mit mindestens einem der Teilstrings "bug", "defect", "fix", "issue", "solve", "correct".
 */
public class BugFixCommitMessages implements IOfflineMetric {
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
		IPredicate<String> containsBugFixSubstring = new IPredicate<String>() {
			@Override
			public boolean invoke(String s) {
				return StringHelpers.containsOneOf(s.toLowerCase(), BUG_FIX_STRS);
			}
		};
		return Func.count(containsBugFixSubstring, GitHelpers.listCommitMessages(repoRoot));
	}
}
