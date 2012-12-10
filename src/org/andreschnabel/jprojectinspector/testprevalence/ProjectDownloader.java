package org.andreschnabel.jprojectinspector.testprevalence;

import org.andreschnabel.jprojectinspector.Helpers;
import org.andreschnabel.jprojectinspector.testprevalence.model.Project;

import java.io.File;

public class ProjectDownloader {

	public File loadProject(Project p) throws Exception {
		String destPath = Globals.DEST_BASE + p.repoName;
		Helpers.system("git clone -v " + Globals.BASE_URL + p.owner + "/" + p.repoName + " " + destPath);
		return new File(destPath);
	}

	public void deleteProject(Project p) throws Exception {
		String destPath = Globals.DEST_BASE + p.repoName;
		//Helpers.deleteDir(new File(destPath));
		Helpers.rmDir(destPath);
	}

}
