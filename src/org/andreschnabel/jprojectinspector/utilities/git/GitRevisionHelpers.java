package org.andreschnabel.jprojectinspector.utilities.git;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.utilities.helpers.ProcessHelpers;

import java.io.File;

/**
 * Hilfsfunktionen zum Umgang mit Revisionen eines geklonten Git-Repositories.
 */
public class GitRevisionHelpers {
	public static int numRevisions(File repoPath) throws Exception {
		return listAllRevisions(repoPath).length;
	}

	public static String[] listAllRevisions(File repoPath) throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, Config.GIT_PATH, "rev-list", "--no-merges", "master");
		return out.split("\n");
	}

	public static String[] listRevisionsInYear(File repoPath, int year) throws Exception {
		return listRevisionsBetweenDates(repoPath, String.valueOf(year) + "-01-01", String.valueOf(year + 1) + "-01-01");
	}

	// Date format: YYYY-MM-DD
	public static String[] listRevisionsBetweenDates(File repoPath, String startDate, String endDate)  throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, Config.GIT_PATH, "rev-list", "--since="+startDate, "--before="+endDate, "--no-merges", "master");
		return out.split("\n");
	}

	// Date format: YYYY-MM-DD
	public static String[] listRevisionsBeforeDate(File repoPath, String date) throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, Config.GIT_PATH, "rev-list", "--before="+date, "--no-merges", "master");
		return out.split("\n");
	}

	public static String latestRevisionBeforeDate(File repoPath, String date) throws Exception {
		return listRevisionsBeforeDate(repoPath, date)[0];
	}
	
	public static long getDateOfRevision(File repoPath, String rev) throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, "git", "log","-1","--format=\"%ct\"",rev);
		return Long.valueOf(out.replace("\n", "").replace("\"", ""));
	}
	
	public static String getOldestRevision(File repoPath) throws Exception {
		return ProcessHelpers.monitorProcess(repoPath, "git", "rev-list", "--max-parents=0", "HEAD").replace("\n", "").replace("\"", "");
	}
}
