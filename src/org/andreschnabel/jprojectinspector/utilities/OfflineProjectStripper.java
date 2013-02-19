package org.andreschnabel.jprojectinspector.utilities;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectList;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.util.LinkedList;

public final class OfflineProjectStripper {
	
	private OfflineProjectStripper() {}
	
	public static void stripOfflineProjs(String projLstFilename) throws Exception {
		ProjectList projLst = ProjectList.fromFile(projLstFilename);
		ProjectList outLst = new ProjectList(projLst.keyword, new LinkedList<Project>());
		for(Project p : projLst.projects) {			
			boolean online = !isOffline(p);
			if(online) {
				outLst.projects.add(p);
			}
			Helpers.log("Checked: " + p + " " + (online? "online" : "offline"));
		}
		FileHelpers.writeObjToJsonFile(outLst, "STRIPPED" + projLstFilename);
	}

	public static boolean isOffline(Project p) throws Exception {
		try {
			String projPageSrc = Helpers.loadUrlIntoStr("https://github.com/" + p.owner + "/" + p.repoName);
			return projPageSrc.contains("Page not found &middot; GitHub");
		} catch(Exception e) {
			return true;
		}
	}

}
