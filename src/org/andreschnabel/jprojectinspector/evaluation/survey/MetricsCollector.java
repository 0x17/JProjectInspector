package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.metrics.code.Cloc;
import org.andreschnabel.jprojectinspector.metrics.project.Contributors;
import org.andreschnabel.jprojectinspector.metrics.project.Issues;
import org.andreschnabel.jprojectinspector.metrics.test.TestLinesOfCode;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MetricsCollector {

	public static ProjectMetricsLst collectMetricsForResponses(ResponseProjectsLst rpl) throws Exception {
		List<ProjectMetrics> results = new LinkedList<ProjectMetrics>();

		for(ResponseProjects responseProj : rpl.responseProjs) {
			results.addAll(collectMetricsForResponse(responseProj));
		}

		ProjectMetricsLst rml = new ProjectMetricsLst(results);
		return rml;
	}

	public static List<ProjectMetrics> collectMetricsForResponse(ResponseProjects rp) throws Exception {
		List<ProjectMetrics> pml = new LinkedList<ProjectMetrics>();

		List<Project> plist = rp.toProjectList();
		for(Project p : plist) {
			File path = ProjectDownloader.loadProject(p);

			ProjectMetrics pm = new ProjectMetrics();

			List<Cloc.ClocResult> clocResults = Cloc.determineLinesOfCode(path);
			int locSum = 0;
			for(Cloc.ClocResult clocResult : clocResults) {
				locSum += clocResult.codeLines;
			}

			pm.linesOfCode = locSum;

			pm.numCommits = GitHelpers.numCommits(path);

			pm.numContribs = Contributors.countNumContributors(p);

			pm.numIssues = Issues.getNumberOfIssues(p);

			pm.project = p;

			pm.testLinesOfCode = TestLinesOfCode.countTestLocHeuristic(path);

			Helpers.log("Determined metrics for " + p + " result: " + pm);

			pml.add(pm);

			ProjectDownloader.deleteProject(p);
		}

		return pml;
	}

}
