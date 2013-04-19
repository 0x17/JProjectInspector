package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.metrics.code.Cloc;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.IndexedTransform;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MetricsCollector {

	public static List<ProjectWithResults> collectMetricsForResponses(ResponseProjectsLst rpl) throws Exception {
		List<ProjectWithResults> results = new LinkedList<ProjectWithResults>();

		for(ResponseProjects responseProj : rpl.responseProjs) {
			if(responseProj.user != null)
				results.addAll(collectMetricsForResponse(responseProj));
		}

		return results;
	}

	public static List<ProjectWithResults> collectMetricsForResponse(ResponseProjects rp) {
		IndexedTransform<Project, ProjectWithResults> projToMetrics = new IndexedTransform<Project, ProjectWithResults>() {
			@Override
			public ProjectWithResults invoke(int i, Project p) {
				try {
					File path = ProjectDownloader.loadProject(p);
					if(path == null) {
						throw new Exception("Download of "+p+" failed. Skip!");
					}

					List<Cloc.ClocResult> clocResults = Cloc.determineLinesOfCode(path);
					int locSum = 0;
					for(Cloc.ClocResult clocResult : clocResults) {
						locSum += clocResult.codeLines;
					}

					ProjectWithResults pm = new ProjectWithResults(null, null, null);
					/*pm.linesOfCode = locSum;
					pm.numCommits = GitHelpers.numCommits(path);
					pm.numContribs = ContributorsOnline.countNumContributors(p);
					pm.numIssues = Issues.getNumberOfIssues(p);
					pm.project = p;
					pm.testLinesOfCode = TestLinesOfCode.countTestLocHeuristic(path);*/

					Helpers.log("Determined metrics for " + p + " result: " + pm + " :: " + (i+1));

					ProjectDownloader.deleteProject(p);

					return pm;

				} catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		};

		return Func.filter(new Predicate<ProjectWithResults>() {
			@Override
			public boolean invoke(ProjectWithResults pm) {
				return pm != null;
			}
		}, Func.mapi(projToMetrics, rp.toProjectList()));
	}

}
