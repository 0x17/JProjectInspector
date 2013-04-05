package org.andreschnabel.jprojectinspector.utilities;

import java.io.File;

import org.andreschnabel.jprojectinspector.metrics.code.AverageWMC;
import org.andreschnabel.jprojectinspector.metrics.code.ClassCoupling;
import org.andreschnabel.jprojectinspector.metrics.code.JavaLinesOfCode;
import org.andreschnabel.jprojectinspector.metrics.project.CodeFrequency;
import org.andreschnabel.jprojectinspector.metrics.project.Contributors;
import org.andreschnabel.jprojectinspector.metrics.project.Issues;
import org.andreschnabel.jprojectinspector.metrics.project.ProjectAge;
import org.andreschnabel.jprojectinspector.metrics.test.TestLinesOfCode;
import org.andreschnabel.jprojectinspector.metrics.test.prevalence.UnitTestDetector;
import org.andreschnabel.jprojectinspector.metrics.test.simplecoverage.SimpleTestCoverage;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectStats;

public final class ProjectStatsMeasurer {
	
	private ProjectStatsMeasurer() {}

	public static ProjectStats collectStats(Project project, File projectRoot) throws Exception {
		ProjectStats stats = new ProjectStats(project);

		stats.containsTest = UnitTestDetector.containsTest(projectRoot);

		ClassCoupling ccoupling = new ClassCoupling();
		stats.avgCoupling = ccoupling.getAverageCoupling(projectRoot);

		AverageWMC mcCabe = new AverageWMC();
		stats.avgWMC = mcCabe.determineAverageWMC(projectRoot);

		stats.linesOfCode = JavaLinesOfCode.countLocForProj(project);

		CodeFrequency cf = new CodeFrequency();
		stats.codeFrequency = cf.countCodeFrequencyForProj(project);

		stats.numContributors = Contributors.countNumContributors(project);
		//stats.numTestContributors = contribs.countNumTestContributors(projectRoot);

		Issues issues = new Issues();
		stats.numIssues = issues.getNumberOfIssues(project);

		ProjectAge pa = new ProjectAge();
		stats.projectAge = pa.getProjectAge(project);

		/*Selectivity selectivity = new Selectivity();
		stats.selectivity = selectivity.getSelectivity(project);*/
		// FIXME: Don't measure selectivity for now to avoid rate limit!
		stats.selectivity = -1;

		SimpleTestCoverage tc = new SimpleTestCoverage();
		stats.testCoverage = tc.determineMethodCoverage(projectRoot);

		TestLinesOfCode tloc = new TestLinesOfCode();
		stats.testLinesOfCode = tloc.countTestLocOfDir(projectRoot);

		return stats;
	}

}
