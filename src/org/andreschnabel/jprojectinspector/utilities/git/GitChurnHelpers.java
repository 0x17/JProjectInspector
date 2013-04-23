package org.andreschnabel.jprojectinspector.utilities.git;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.ITransform;
import org.andreschnabel.jprojectinspector.utilities.helpers.ProcessHelpers;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitChurnHelpers {

	public static ChurnStats parseChurnStatsFromShortStat(final String shortStat) {
		ChurnStats cs = new ChurnStats();

		Pattern[] pats = new Pattern[] {
			Pattern.compile("(\\d+) files changed"),
			Pattern.compile("(\\d+) insertions\\(\\+\\)"),
			Pattern.compile("(\\d+) deletions\\(\\-\\)")
		};

		ITransform<Pattern, Matcher> matcherFromPattern = new ITransform<Pattern, Matcher>() {
			@Override
			public Matcher invoke(Pattern p) {
				return p.matcher(shortStat);
			}
		};
		List<Matcher> matchers = Func.map(matcherFromPattern, Func.fromArray(pats));

		for(int i=0; i<pats.length; i++) {
			Matcher m = matchers.get(i);
			if(m.find()) {
				int val = Integer.valueOf(m.group(1));
				switch(i) {
				case 0:
					cs.filesChanged = val;
					break;
				case 1:
					cs.numInsertions = val;
					break;
				case 2:
					cs.numDeletions = val;
					break;
				}
			}
		}

		return cs;
	}

	public static ChurnStats getChurnStatsForRevision(File repoPath, String revisionSha1Hash) throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, Config.GIT_PATH, "show", "-w", "-C", "--shortstat", "--format=format:", revisionSha1Hash);
		return parseChurnStatsFromShortStat(out);
	}

	public static ChurnStats getChurnStatsBetweenRevisions(File repoPath, String revA, String revB) throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, Config.GIT_PATH, "diff", "-w", "-C", "--shortstat", "--format=format:", revA, revB);
		return parseChurnStatsFromShortStat(out);
	}

	public static class ChurnStats {
		public int numDeletions;
		public int numInsertions;
		public int filesChanged;

		public int getChurnedLoc() { return numInsertions + numDeletions; }
	}
}
