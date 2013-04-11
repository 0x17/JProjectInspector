package org.andreschnabel.jprojectinspector.tools;

import org.andreschnabel.jprojectinspector.evaluation.survey.ProjectMetrics;
import org.andreschnabel.jprojectinspector.metrics.code.Cloc;
import org.andreschnabel.jprojectinspector.metrics.project.Contributors;
import org.andreschnabel.jprojectinspector.metrics.project.Issues;
import org.andreschnabel.jprojectinspector.metrics.test.TestLinesOfCode;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.io.File;
import java.util.List;

public class Analyzer {

	public static void main(String[] args) throws Exception {
		TestingNeedMeasure measure = new TestingNeedMeasure() {
			@Override
			public float invoke(ProjectMetrics metrics) {
				return (float)metrics.linesOfCode / (metrics.testLinesOfCode+1.0f);
			}
		};
		AnalyzeResult result = doAnalyze(Project.fromString(args[0]), measure);
		Helpers.log(""+result);
	}

	public static AnalyzeResult doAnalyze(Project p, TestingNeedMeasure tnm) throws Exception {
		File path = ProjectDownloader.loadProject(p);

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

		float testingNeedValue = tnm.invoke(pm);

		AnalyzeResult result = new AnalyzeResult(pm, testingNeedValue);
		return result;
	}

}
