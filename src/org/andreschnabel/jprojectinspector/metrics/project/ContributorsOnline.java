package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.IOnlineMetric;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

public final class ContributorsOnline implements IOnlineMetric {

	public static int countNumContributors(Project project) throws Exception {
		String contribsData = Helpers.loadUrlIntoStr("https://github.com/" + project.owner + "/" + project.repoName + "/graphs/contributors-data");
		int ncontribs = StringHelpers.countOccurencesOfWord(contribsData, "\"author\"");
		// some projects don't have any graphs yet gathered by GitHub...
		return ncontribs == 0 ? 1 : ncontribs;
	}

	@Override
	public String getName() {
		return "NumContribsOnline";
	}

	@Override
	public String getDescription() {
		return "Scraped number of contributors from GitHub graph.";
	}

	@Override
	public Category getCategory() {
		return Category.Scraping;
	}

	@Override
	public double measure(Project p) throws Exception {
		return countNumContributors(p);
	}
}
