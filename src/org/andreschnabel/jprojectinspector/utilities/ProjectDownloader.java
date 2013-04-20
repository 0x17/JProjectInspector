package org.andreschnabel.jprojectinspector.utilities;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ProcessHelpers;

import java.io.File;

public final class ProjectDownloader {
	
	private ProjectDownloader() {}

	public static File loadProject(Project p) throws Exception {
		if(!isProjectOnline(p)) {
			return null;
		}

		String destPath = Config.DEST_BASE + p.repoName;
		File f = new File(destPath);
		if(f.exists()) {
			return f;
		}

		ProcessHelpers.system(Config.GIT_PATH + " clone -v " + Config.BASE_URL + p.owner + "/" + p.repoName + " " + destPath);

		return f.exists() ? f : null;
	}

	public static boolean isProjectOnline(Project p) {
		String projHtml;
		try {
			projHtml = Helpers.loadHTMLUrlIntoStr("https://github.com/"+p.owner+"/"+p.repoName);
		} catch(Exception e) {
			return false;
		}
		return projHtml != null && !projHtml.contains("<title>Page not found &middot; GitHub</title>");
	}
	
	public static boolean isUserOnline(String user) {
		String userHtml;
		try {
			userHtml = Helpers.loadHTMLUrlIntoStr("https://github.com/"+user);
		} catch(Exception e) {
			return false;
		}
		return userHtml != null && !userHtml.contains("<title>Page not found &middot; GitHub</title>");
	}

	public static void deleteProject(Project p) throws Exception {
		String destPath = Config.DEST_BASE + p.repoName;
		//Helpers.deleteDir(new File(destPath));
		FileHelpers.rmDir(destPath);
	}
}
