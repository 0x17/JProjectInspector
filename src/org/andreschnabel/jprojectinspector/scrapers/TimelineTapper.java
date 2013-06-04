package org.andreschnabel.jprojectinspector.scrapers;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.pecker.functional.Func;
import org.andreschnabel.pecker.functional.ITransform;
import org.andreschnabel.pecker.helpers.Helpers;
import org.andreschnabel.pecker.helpers.RegexHelpers;

import java.util.List;

/**
 * Greife Projekte aus der GitHub-Timeline ab.
 */
public final class TimelineTapper {

	/**
	 * Nur statische Methoden.
	 */
	private TimelineTapper() {}

	public static List<Project> tapProjects() throws Exception {
		String rx = "\"url\":\"https://github.com/([\\w\\-]+)/([\\w\\-]+)\"";
		String timeline = Helpers.loadUrlIntoStr("https://github.com/timeline.json");
		ITransform<String[], Project> projFromStrArray = new ITransform<String[], Project>() {
			@Override
			public Project invoke(String[] strArray) {
				return new Project(strArray[0], strArray[1]);
			}
		};
		return Func.map(projFromStrArray, RegexHelpers.batchMatch(rx, timeline));
	}

}
