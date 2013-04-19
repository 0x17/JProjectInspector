package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OnlineMetric;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;

import java.util.List;

public class CodeFrequency implements OnlineMetric {

	public static int countCodeFrequencyForProj(Project project) throws Exception {
		String cfdStr = Helpers.loadUrlIntoStr("https://github.com/" + project.owner + "/" + project.repoName + "/graphs/code-frequency-data");
		List<String[]> triples = RegexHelpers.batchMatch("\\[([0-9]*),([0-9]*),(-[0-9]*)\\]", cfdStr);

		if(triples.size() == 0) return 0;

		String[] last = triples.get(triples.size() - 1);

		int linesAdded = Integer.valueOf(last[1]);
		int linesRemoved = Integer.valueOf(last[2]);

		return linesAdded - linesRemoved;
	}

	@Override
	public String getName() {
		return "codefreq";
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
