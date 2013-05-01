package org.andreschnabel.jprojectinspector.utilities.git;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ProcessHelpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Hilfsfunktionen für Git.
 */
public class GitHelpers {

	public static List<String> listCommitMessages(File repoPath) throws Exception {
		return listCommitMessages(repoPath, new File("."));
	}

	public static List<String> listCommitMessages(File repoPath, File f) throws Exception {
		String out = ProcessHelpers.monitorProcess(repoPath, Config.GIT_PATH, "--no-pager", "log", "--pretty=oneline", f.getName());
		String[] comments = out.split("[0-9a-zA-Z]{40} ");
		List<String> result = new ArrayList<String>(comments.length-1);
		for(int i=1; i<comments.length; i++) {
			result.add(comments[i].replace("\n", ""));
		}
		return result;
	}
	
	public static void revertProjectToDate(File repoPath, String date) throws Exception {
		String sha1 = GitRevisionHelpers.latestRevisionBeforeDate(repoPath, date);
		String out = ProcessHelpers.monitorProcess(repoPath, Config.GIT_PATH, "reset", "--hard", sha1);
		Helpers.log("Revert out = " + out);
	}
}
