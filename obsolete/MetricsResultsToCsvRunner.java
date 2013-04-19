package org.andreschnabel.jprojectinspector.evaluation.survey.runners;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;

import java.util.List;

public class MetricsResultsToCsvRunner {

	public static void main(String[] args) throws Exception {
		//metricsResultsToCsv(',');
	}

	public static String surveyResultForProject(Project p, List<ResponseProjects> rps) {
		for(ResponseProjects rp : rps) {
			if(rp.user != null && rp.user.equals(p.owner)) {
				StringBuilder sb = new StringBuilder();
				if(rp.highestBugCount.equals(p.repoName))
					sb.append("B");
				if(rp.lowestBugCount.equals(p.repoName))
					sb.append("b");
				if(rp.mostTested.equals(p.repoName))
					sb.append("T");
				if(rp.leastTested.equals(p.repoName))
					sb.append("t");
				String result = sb.toString();
				return result.isEmpty() ? "0" : result;
			}
		}

		return "0";
	}

}
