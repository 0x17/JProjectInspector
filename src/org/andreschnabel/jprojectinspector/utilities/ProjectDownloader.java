package org.andreschnabel.jprojectinspector.utilities;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.io.File;

public class ProjectDownloader {

	public File loadProject(Project p) throws Exception {
		String destPath = Config.DEST_BASE + p.repoName;
		Helpers.system("git clone -v " + Config.BASE_URL + p.owner + "/" + p.repoName + " " + destPath);
		File f = new File(destPath);
		return f.exists() ? f : null;
	}

	public void deleteProject(Project p) throws Exception {
		String destPath = Config.DEST_BASE + p.repoName;
		//Helpers.deleteDir(new File(destPath));
		FileHelpers.rmDir(destPath);
	}

}
