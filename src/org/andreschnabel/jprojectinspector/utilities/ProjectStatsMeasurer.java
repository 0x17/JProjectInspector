package org.andreschnabel.jprojectinspector.utilities;

import java.io.File;

import org.andreschnabel.jprojectinspector.metrics.code.AverageWMC;
import org.andreschnabel.jprojectinspector.metrics.code.ClassCoupling;
import org.andreschnabel.jprojectinspector.metrics.code.LinesOfCode;
import org.andreschnabel.jprojectinspector.metrics.project.CodeFrequency;
import org.andreschnabel.jprojectinspector.metrics.project.Contributors;
import org.andreschnabel.jprojectinspector.metrics.project.Issues;
import org.andreschnabel.jprojectinspector.metrics.project.ProjectAge;
import org.andreschnabel.jprojectinspector.metrics.test.TestLinesOfCode;
import org.andreschnabel.jprojectinspector.metrics.test.coverage.TestCoverage;
import org.andreschnabel.jprojectinspector.metrics.test.prevalence.UnitTestDetector;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectStats;

public class ProjectStatsMeasurer {
	
	public ProjectStats collectStats(Project project, File projectRoot) throws Exception {		
		ProjectStats stats = new ProjectStats(project);

		UnitTestDetector utd = new UnitTestDetector();
		stats.containsTest = utd.containsTest(projectRoot);

		ClassCoupling ccoupling = new ClassCoupling();
		stats.avgCoupling = ccoupling.getAverageCoupling(projectRoot);

		AverageWMC mcCabe = new AverageWMC();
		stats.avgWMC = mcCabe.determineAverageWMC(projectRoot);

		LinesOfCode loc = new LinesOfCode();
		stats.linesOfCode = loc.countLocForProj(project);

		CodeFrequency cf = new CodeFrequency();
		stats.codeFrequency = cf.countCodeFrequencyForProj(project);

		Contributors contribs = new Contributors();
		stats.numContributors = contribs.countNumContributors(project);
		//stats.numTestContributors = contribs.countNumTestContributors(projectRoot);

		Issues issues = new Issues();
		stats.numIssues = issues.getNumberOfIssues(project);

		ProjectAge pa = new ProjectAge();
		stats.projectAge = pa.getProjectAge(project);

		/*Selectivity selectivity = new Selectivity();
		stats.selectivity = selectivity.getSelectivity(project);*/
		// FIXME: Don't measure selectivity for now to avoid rate limit!
		stats.selectivity = -1;

		TestCoverage tc = new TestCoverage();
		stats.testCoverage = tc.determineMethodCoverage(projectRoot);

		TestLinesOfCode tloc = new TestLinesOfCode();
		stats.testLinesOfCode = tloc.countTestLocOfDir(projectRoot);

		return stats;
	}

}
