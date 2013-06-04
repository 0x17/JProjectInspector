package org.andreschnabel.jprojectinspector.metrics.churn;

import org.andreschnabel.jprojectinspector.metrics.IOnlineMetric;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.pecker.helpers.Helpers;
import org.andreschnabel.pecker.helpers.RegexHelpers;

import java.util.List;

/**
 * Scrape die Codefrequenz von GitHub.
 */
public class CodeFrequency implements IOnlineMetric {

	public static double countCodeFrequencyForProj(Project project) throws Exception {
		String cfdStr = Helpers.loadUrlIntoStr("https://github.com/" + project.owner + "/" + project.repoName + "/graphs/code-frequency-data");
		List<String[]> triples = RegexHelpers.batchMatch("\\[([0-9]*),([0-9]*),(-[0-9]*)\\]", cfdStr);

		if(triples.size() == 0) return Double.NaN;

		String[] last = triples.get(triples.size() - 1);

		int linesAdded = Integer.valueOf(last[1]);
		int linesRemoved = Integer.valueOf(last[2]);

		return linesAdded - linesRemoved;
	}

	@Override
	public String getName() {
		return "CodeFreq";
	}

	@Override
	public String getDescription() {
		return "Lines added minus lines removed last week.";
	}

	@Override
	public Category getCategory() {
		return Category.Scraping;
	}

	@Override
	public double measure(Project p) throws Exception {
		return countCodeFrequencyForProj(p);
	}
}
