package org.andreschnabel.jprojectinspector.obsolete;

import org.andreschnabel.jprojectinspector.metrics.javaspecific.JavaAverageWMC;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.JavaClassCoupling;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.JavaLinesOfCode;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.JavaTestLinesOfCode;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.simplejavacoverage.SimpleJavaTestCoverage;
import org.andreschnabel.jprojectinspector.metrics.project.CodeFrequency;
import org.andreschnabel.jprojectinspector.metrics.project.ContributorsOnline;
import org.andreschnabel.jprojectinspector.metrics.project.Issues;
import org.andreschnabel.jprojectinspector.metrics.project.ProjectAge;
import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.metrics.ProjectStats;

import java.io.File;

public final class ProjectStatsMeasurer {
	
	private ProjectStatsMeasurer() {}

	public static ProjectStats collectStats(Project project, File projectRoot) throws Exception {
		ProjectStats stats = new ProjectStats(project);

		stats.containsTest = UnitTestDetector.containsTest(projectRoot);

		JavaClassCoupling ccoupling = new JavaClassCoupling();
		stats.avgCoupling = ccoupling.getAverageCoupling(projectRoot);

		JavaAverageWMC mcCabe = new JavaAverageWMC();
		stats.avgWMC = mcCabe.determineAverageWMC(projectRoot);

		stats.linesOfCode = JavaLinesOfCode.countLocForProj(project);

		CodeFrequency cf = new CodeFrequency();
		stats.codeFrequency = cf.countCodeFrequencyForProj(project);

		stats.numContributors = ContributorsOnline.countNumContributors(project);
		//stats.numTestContributors = contribs.countNumTestContributors(projectRoot);

		stats.numIssues = Issues.getNumberOfIssues(project);

		stats.projectAge = ProjectAge.getProjectAge(project);

		/*Selectivity selectivity = new Selectivity();
		stats.selectivity = selectivity.getSelectivity(project);*/
		// FIXME: Don't measure selectivity for now to avoid rate limit!
		stats.selectivity = -1;

		stats.testCoverage = SimpleJavaTestCoverage.determineMethodCoverage(projectRoot);

		stats.testLinesOfCode = JavaTestLinesOfCode.countJavaTestLocOfDir(projectRoot);

		return stats;
	}

}
