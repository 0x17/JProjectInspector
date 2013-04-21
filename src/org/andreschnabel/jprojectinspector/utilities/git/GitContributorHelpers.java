package org.andreschnabel.jprojectinspector.utilities.git;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.utilities.helpers.ProcessHelpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class GitContributorHelpers {
	public static List<String> contribNamesForFile(File f) throws Exception {
		List<String> result = new LinkedList<String>();

		String out = ProcessHelpers.monitorProcess(f.getParentFile(), Config.GIT_PATH, "log", "--follow", "--pretty=format:%an", "--", f.getName());
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
		String out = ProcessHelpers.monitorProcess(repoPath, Config.GIT_PATH, "log", "--all", "--format=%aN");
		String[] lines = out.split("\n");
		List<String> contribs = new LinkedList<String>();
		for(String line : lines) {
			if(!contribs.contains(line))
				contribs.add(line);
		}
		return contribs;
	}
}
