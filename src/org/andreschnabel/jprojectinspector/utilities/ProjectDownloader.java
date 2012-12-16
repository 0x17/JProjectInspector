package org.andreschnabel.jprojectinspector.utilities;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.model.Project;

import java.io.File;

public class ProjectDownloader {

	public File loadProject(Project p) throws Exception {
		String destPath = Config.DEST_BASE + p.repoName;
		Helpers.system("git clone -v " + Config.BASE_URL + p.owner + "/" + p.repoName + " " + destPath);
		return new File(destPath);
	}

	public void deleteProject(Project p) throws Exception {
		String destPath = Config.DEST_BASE + p.repoName;
		//Helpers.deleteDir(new File(destPath));
		Helpers.rmDir(destPath);
	}

}
