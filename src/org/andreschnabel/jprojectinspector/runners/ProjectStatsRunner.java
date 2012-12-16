package org.andreschnabel.jprojectinspector.runners;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.andreschnabel.jprojectinspector.metrics.code.ClassCoupling;
import org.andreschnabel.jprojectinspector.metrics.code.LinesOfCode;
import org.andreschnabel.jprojectinspector.metrics.code.McCabe;
import org.andreschnabel.jprojectinspector.metrics.project.*;
import org.andreschnabel.jprojectinspector.metrics.test.TestLinesOfCode;
import org.andreschnabel.jprojectinspector.metrics.test.coverage.TestCoverage;
import org.andreschnabel.jprojectinspector.metrics.test.prevalence.UnitTestDetector;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectStats;
import org.andreschnabel.jprojectinspector.utilities.Helpers;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;

import java.io.File;
import java.io.IOException;

public class ProjectStatsRunner {

	private ProjectStats collectStats(Project project, File projectRoot) throws Exception {
		// For now only determine 7 properties.		
		ProjectStats stats = new ProjectStats();

		UnitTestDetector utd = new UnitTestDetector();
		stats.containsTest = utd.containsTest(project);

		ClassCoupling ccoupling = new ClassCoupling();
		stats.coupling = ccoupling.getAverageCoupling(projectRoot);

		McCabe mcCabe = new McCabe();
		stats.mcCabe = mcCabe.determineMcCabeForDir(projectRoot);

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

		Selectivity selectivity = new Selectivity();
		stats.selectivity = selectivity.getSelectivity(project);

		TestCoverage tc = new TestCoverage();
		stats.testCoverage = tc.determineMethodCoverage(projectRoot);

		TestLinesOfCode tloc = new TestLinesOfCode();
		stats.testLinesOfCode = tloc.countTestLocOfDir(projectRoot);

		return stats;
	}

	private void writeStatsToFile(ProjectStats stats, String outFilename) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(stats);
		Helpers.writeStrToFile(json, outFilename);
	}

	public static void main(String[] args) throws Exception {
		if(args.length != 1 || (args[0].split("/").length != 2)) {
			throw new Exception("Usage: owner/repoName");
		}

		String[] parts = args[0].split("/");
		Project p = new Project(parts[0], parts[1]);

		ProjectDownloader pd = new ProjectDownloader();
		File projectRoot = pd.loadProject(p);

		ProjectStatsRunner psr = new ProjectStatsRunner();
		ProjectStats stats = psr.collectStats(p, projectRoot);
		psr.writeStatsToFile(stats, p.repoName + "Stats.json");

		pd.deleteProject(p);
	}

}
