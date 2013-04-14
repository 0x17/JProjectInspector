package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OnlineMetric;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

public final class ContributorsOnline implements OnlineMetric {

	public static int countNumContributors(Project project) throws Exception {
		String contribsData = Helpers.loadUrlIntoStr("https://github.com/" + project.owner + "/" + project.repoName + "/graphs/contributors-data");
		int ncontribs = StringHelpers.countOccurencesOfWord(contribsData, "\"author\"");
		// some projects don't have any graphs yet gathered by GitHub...
		return ncontribs == 0 ? 1 : ncontribs; // FIXME: This may be incorrect!
	}

	@Override
	public String getName() {
		return "ncontribsonline";
	}

	@Override
	public float measure(Project p) throws Exception {
		return countNumContributors(p);
	}
}
