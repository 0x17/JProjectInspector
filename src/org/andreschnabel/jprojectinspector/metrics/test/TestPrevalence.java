package org.andreschnabel.jprojectinspector.metrics.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.andreschnabel.jprojectinspector.ProjectCollector;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectList;
import org.eclipse.egit.github.core.client.GitHubClient;

public class TestPrevalence {
	
	public TestPrevalenceSummary determineTestPrevalence(ProjectList lst) throws Exception {
		UnitTestDetector jtd = new UnitTestDetector();
		
		List<Project> projects = lst.projects;
		int numTestProjs = 0;
		int numProjs = projects.size();
		System.out.println("Gathering test prevalence in " + numProjs + " projects...");
		
		Map<Project, Boolean> ptm = new HashMap<Project, Boolean>();
		
		for(int i=0; i<numProjs; i++) {
			Project p = projects.get(i);
			System.out.println("Checking project " + p.toId() + " " + (i+1) + "/" + numProjs);
			boolean tested = false;
			if(jtd.containsTest(p)) {
				System.out.println("Found test!");
				numTestProjs++;
				tested = true;
			}
			ptm.put(p, tested);
		}
		
		System.out.println("Number of projects containing tests: " + numTestProjs);
		System.out.println("Number of projects total: " + numProjs);
		float testPrev = (((float)numTestProjs / numProjs) * 100.0f);
		System.out.println("Test prevalence: " + testPrev + "%");
		
		TestPrevalenceSummary summary = new TestPrevalenceSummary();
		summary.numTestedProjects = numTestProjs;
		summary.numProjectsTotal = numProjs;
		summary.testPrevalence = testPrev;
		summary.projectTestMap = ptm;
		summary.keyword = lst.keyword;
		return summary;
	}
	
	public TestPrevalenceSummary determineTestPrevalence(String keyword, int numPages, GitHubClient ghc) throws Exception {
		ProjectCollector jpc = new ProjectCollector(ghc);
		return determineTestPrevalence(jpc.collectProjects(keyword, numPages));		
	}

}
