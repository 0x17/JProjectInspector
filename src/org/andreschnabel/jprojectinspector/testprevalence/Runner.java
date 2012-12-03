package org.andreschnabel.jprojectinspector.testprevalence;

import java.util.LinkedList;
import java.util.List;

/**
 * Task:
 * 1. Automatically gather list of GitHub-projects written in Java (JavaProjectCollector)
 * 2. For each project
 * 	2.1. Determine if project contains JUnit tests (JUnitTestDetector)
 * 	2.2. If so: increment test project counter
 */
public class Runner {
	public static void main(String[] args) throws Exception {
		JavaProjectCollector jpc = new JavaProjectCollector();
		JUnitTestDetector jtd = new JUnitTestDetector();
		
		//List<Project> projects = jpc.collectProjects();
		List<Project> projects = new LinkedList<Project>();
		projects.add(new Project("skeeto", "sample-java-project"));
		
		int numTestProjs = 0;		
		int numProjs = projects.size();
		
		for(Project p : projects) {
			if(jtd.containsTest(p)) {
				numTestProjs++;
			}
		}
		
		System.out.println("Number of projects containing tests: " + numTestProjs);
		System.out.println("Number of projects total: " + numProjs);
		System.out.println("Test prevalence: " + (((float)numTestProjs / numProjs) * 100.0f) + "%");		
	}
}
