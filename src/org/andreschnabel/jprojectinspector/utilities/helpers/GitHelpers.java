package org.andreschnabel.jprojectinspector.utilities.helpers;

import org.andreschnabel.jprojectinspector.utilities.Transform;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitHelpers {

	public static List<String> contribNamesForFile(File f) throws Exception {
		List<String> result = new LinkedList<String>();

		String out = ProcessHelpers.monitorProcess(f.getParentFile(), "git", "log", "--follow", "--pretty=format:%an", "--", f.getName());
		String[] names = out.split("\n");
		for(String name : names) {
			if(name != null && !name.isEmpty()) {
				if(!result.contains(name)) {
					result.add(name);
				}
			}
		}

		return result;
	}

	public static int numContribs(File repoPath) throws Exception {
		return listAllContribs(repoPath).size();
	}

	public static List<String> listAllContribs(File repoPath) throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, "git", "log", "--all", "--format=%aN");
		String[] lines = out.split("\n");
		List<String> contribs = new LinkedList<String>();
		for(String line : lines) {
			if(!contribs.contains(line))
				contribs.add(line);
		}
		return contribs;
	}

	public static int numCommits(File repoPath) throws Exception {
		return listAllCommits(repoPath).length;
	}

	public static String[] listAllCommits(File repoPath) throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, "git", "rev-list", "--no-merges", "master");
		return out.split("\n");
	}

	public static String[] listCommitsInYear(File repoPath, int year) throws Exception {
		return listCommitsBetweenDates(repoPath, String.valueOf(year) + "-01-01", String.valueOf(year + 1) + "-01-01");
	}

	// Date format: YYYY-MM-DD
	public static String[] listCommitsBetweenDates(File repoPath, String startDate, String endDate)  throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, "git", "rev-list", "--since="+startDate, "--before="+endDate, "--no-merges", "master");
		return out.split("\n");
	}

	// Date format: YYYY-MM-DD
	public static String[] listCommitsUntilDate(File repoPath, String date) throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, "git", "rev-list", "--before="+date, "--no-merges", "master");
		return out.split("\n");
	}
	
	public static String latestCommitUntilDate(File repoPath, String date) throws Exception {
		return listCommitsUntilDate(repoPath, date)[0];
	}

	public static class ChurnStats {
		public int numDeletions;
		public int numInsertions;
		public int filesChanged;

		public int getChurnedLoc() { return numInsertions + numDeletions; }
	}

	public static ChurnStats parseChurnStatsFromShortStat(final String shortStat) {
		ChurnStats cs = new ChurnStats();

		Pattern[] pats = new Pattern[] {
			Pattern.compile("(\\d+) files changed"),
			Pattern.compile("(\\d+) insertions\\(\\+\\)"),
			Pattern.compile("(\\d+) deletions\\(\\-\\)")
		};

		Transform<Pattern, Matcher> matcherFromPattern = new Transform<Pattern, Matcher>() {
			@Override
			public Matcher invoke(Pattern p) {
				return p.matcher(shortStat);
			}
		};
		List<Matcher> matchers = ListHelpers.map(matcherFromPattern, ListHelpers.fromArray(pats));

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

	public static ChurnStats getChurnStatsForCommit(File repoPath, String commitSha1Hash) throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, "git", "show", "-w", "-C" ,"--shortstat", "--format=format:", commitSha1Hash);
		return parseChurnStatsFromShortStat(out);
	}

	public static ChurnStats getChurnStatsBetweenCommits(File repoPath, String commitA, String commitB) throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, "git", "diff", "-w", "-C", "--shortstat", "--format=format:", commitA, commitB);
		return parseChurnStatsFromShortStat(out);
	}

	public static List<String> listCommitComments(File repoPath, File f) throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, "git", "--no-pager", "log", "--pretty=oneline", f.getName());
		String[] comments = out.split("[0-9a-zA-Z]{40} ");
		List<String> result = new ArrayList<String>(comments.length-1);
		for(int i=1; i<comments.length; i++) {
			result.add(comments[i].replace("\n", ""));
		}
		return result;
	}
	
public static void revertProjectToDate(File repoPath, String date) throws Exception {
	String sha1 = latestCommitUntilDate(repoPath, date);
	String out = ProcessHelpers.monitorProcess(repoPath, "git", "reset", "--hard", sha1);
	Helpers.log("Revert out = " + out);
}
}
