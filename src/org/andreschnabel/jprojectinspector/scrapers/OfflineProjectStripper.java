package org.andreschnabel.jprojectinspector.scrapers;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectList;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.JsonHelpers;

import java.util.LinkedList;

/**
 * Entferne nicht mehr verfügbare Projekte aus JSON-Projektliste.
 * Obsoleter Code?
 */
public final class OfflineProjectStripper {
	/**
	 * Nur statische Methoden.
	 */
	private OfflineProjectStripper() {}

	public static void stripOfflineProjs(String projLstFilename) throws Exception {
		ProjectList projLst = ProjectList.fromJson(projLstFilename);
		ProjectList outLst = new ProjectList(projLst.keyword, new LinkedList<Project>());
		for(Project p : projLst.projects) {			
			boolean online = !isOffline(p);
			if(online) {
				outLst.projects.add(p);
			}
			Helpers.log("Checked: " + p + " " + (online? "online" : "offline"));
		}
		JsonHelpers.writeObjToJsonFile(outLst, "STRIPPED" + projLstFilename);
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
