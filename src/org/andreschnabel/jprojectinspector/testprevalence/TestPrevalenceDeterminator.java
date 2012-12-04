package org.andreschnabel.jprojectinspector.testprevalence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestPrevalenceDeterminator {
	
	public TestPrevalenceSummary determineTestPrevalence(String keyword, int numPages) throws Exception {
		JavaProjectCollector jpc = new JavaProjectCollector();
		JUnitTestDetector jtd = new JUnitTestDetector();
		
		List<Project> projects = jpc.collectProjects(keyword, numPages);
		
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
		return summary;
	}

}
