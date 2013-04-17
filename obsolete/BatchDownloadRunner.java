package org.andreschnabel.jprojectinspector.runners;

import org.andreschnabel.jprojectinspector.model.ProjectList;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;

public class BatchDownloadRunner {
	
	public static void main(String[] args) throws Exception {
		ProjectList plist = ProjectList.fromJson(args[0]);
		ProjectDownloader.loadProjects(plist);
		//ProjectDownloader.deleteEmtpyPreloads();
	}

}
