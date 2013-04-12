package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.metrics.code.Cloc;
import org.andreschnabel.jprojectinspector.metrics.project.Contributors;
import org.andreschnabel.jprojectinspector.metrics.project.Issues;
import org.andreschnabel.jprojectinspector.metrics.test.TestLinesOfCode;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.metrics.ProjectMetrics;
import org.andreschnabel.jprojectinspector.model.metrics.ProjectMetricsLst;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.utilities.IndexedTransform;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MetricsCollector {

	public static ProjectMetricsLst collectMetricsForResponses(ResponseProjectsLst rpl) throws Exception {
		List<ProjectMetrics> results = new LinkedList<ProjectMetrics>();

		for(ResponseProjects responseProj : rpl.responseProjs) {
			if(responseProj.user != null)
				results.addAll(collectMetricsForResponse(responseProj));
		}

		ProjectMetricsLst rml = new ProjectMetricsLst(results);
		return rml;
	}

	public static List<ProjectMetrics> collectMetricsForResponse(ResponseProjects rp) {
		IndexedTransform<Project, ProjectMetrics> projToMetrics = new IndexedTransform<Project, ProjectMetrics>() {
			@Override
			public ProjectMetrics invoke(int i, Project p) {
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

					ProjectMetrics pm = new ProjectMetrics();
					pm.linesOfCode = locSum;
					pm.numCommits = GitHelpers.numCommits(path);
					pm.numContribs = Contributors.countNumContributors(p);
					pm.numIssues = Issues.getNumberOfIssues(p);
					pm.project = p;
					pm.testLinesOfCode = TestLinesOfCode.countTestLocHeuristic(path);

					Helpers.log("Determined metrics for " + p + " result: " + pm + " :: " + (i+1));

					ProjectDownloader.deleteProject(p);

					return pm;

				} catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		};

		return ListHelpers.filter(new Predicate<ProjectMetrics>() {
			@Override
			public boolean invoke(ProjectMetrics pm) {
				return pm != null;
			}
		}, ListHelpers.mapi(projToMetrics, rp.toProjectList()));
	}

}
