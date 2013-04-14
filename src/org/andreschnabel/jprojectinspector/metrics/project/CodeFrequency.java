package org.andreschnabel.jprojectinspector.metrics.project;

import org.andreschnabel.jprojectinspector.metrics.OnlineMetric;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;

import java.util.List;

public class CodeFrequency implements OnlineMetric {

	public int countCodeFrequencyForProj(Project project) throws Exception {
		String cfdStr = Helpers.loadUrlIntoStr("https://github.com/" + project.owner + "/" + project.repoName + "/graphs/code-frequency-data");
		List<RegexHelpers.StringTriple> triples = RegexHelpers.batchMatchThreeGroups("\\[([0-9]*),([0-9]*),(-[0-9]*)\\]", cfdStr);

		if(triples.size() == 0) return 0;

		RegexHelpers.StringTriple last = triples.get(triples.size() - 1);

		int linesAdded = Integer.valueOf(last.second);
		int linesRemoved = Integer.valueOf(last.third);

		return linesAdded - linesRemoved;
	}

	@Override
	public float measure(Project p) throws Exception {
		return countCodeFrequencyForProj(p);
	}
}
