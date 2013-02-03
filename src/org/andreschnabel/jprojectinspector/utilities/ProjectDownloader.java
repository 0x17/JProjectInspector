package org.andreschnabel.jprojectinspector.utilities;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectList;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.io.File;

public final class ProjectDownloader {
	
	private ProjectDownloader() {}

	public static File loadProject(Project p) throws Exception {
		String destPath = Config.DEST_BASE + p.repoName;
		Helpers.system("git clone -v " + Config.BASE_URL + p.owner + "/" + p.repoName + " " + destPath);
		File f = new File(destPath);
		return f.exists() ? f : null;
	}

	public static void deleteProject(Project p) throws Exception {
		String destPath = Config.DEST_BASE + p.repoName;
		//Helpers.deleteDir(new File(destPath));
		FileHelpers.rmDir(destPath);
	}


	public static void loadProjects(ProjectList plist) throws Exception {
		int ctr=1;
		int numProjs = plist.projects.size();
		for(Project p : plist.projects) {
			Helpers.log("Loading " + p + " (" + ctr + "/" + numProjs + ")"); 
			String destPath = preloadPath(p);
			Helpers.system("git clone -v " + Config.BASE_URL + p.owner + "/" + p.repoName + " " + destPath);
		}
	}
	
	public static String preloadPath(Project p) {
		return Config.DEST_BASE + p.owner + File.separator + p.repoName;
	}

}
