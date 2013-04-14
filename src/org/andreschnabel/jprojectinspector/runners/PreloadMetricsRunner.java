package org.andreschnabel.jprojectinspector.runners;

import org.andreschnabel.jprojectinspector.metrics.javaspecific.JavaTestContributors;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.JavaTestFrameworkDetector;
import org.andreschnabel.jprojectinspector.metrics.project.ContributorsOnline;
import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectList;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class PreloadMetricsRunner {
	
	public static void main(String[] args) throws Exception {
		testContribsForPreloads();
	}
	
	public static void testContribsForPreloads() throws Exception {
		File[] preloadPaths = ProjectDownloader.getPreloadPaths();
		
		int nprojects = preloadPaths.length;
		int ctr = 1;
		int contribsTotal = 0;
		
		for(File pf : preloadPaths) {
			int ncontribs = JavaTestContributors.numTestContribs(pf);
			contribsTotal += ncontribs;
			Helpers.log(pf.getName() + " has " + ncontribs + " test contributors! (" + (ctr++) + "/" + nprojects + ")");			
		}
		
		float contribsPerProj = (float)contribsTotal / nprojects;
		
		Helpers.log(contribsTotal + " test contributors total.\nThat is: " + contribsPerProj + " contributors per project.");
	}
	
	public static void contribsForPreloads() throws Exception {
		ProjectList plist = ProjectDownloader.getPreloadProjs();
		int nprojects = plist.projects.size();
		int ncontribs = 0;
		int ctr = 1;
		for(Project p : plist.projects) {
			int nc = ContributorsOnline.countNumContributors(p);
			Helpers.log(p + " has " + nc + " contributors. (" + (ctr++) + "/" + nprojects + ")");
			ncontribs += nc;
		}
		
		float contribsPerProj = (float)ncontribs / nprojects;
		
		Helpers.log(ncontribs + " Contributors total.\nThat is: " + contribsPerProj + " contributors per project.");
	}
	
	public static void testPrevalenceForPreloads() throws Exception {
		File[] preloadPaths = ProjectDownloader.getPreloadPaths();
		
		int numProjs = preloadPaths.length;
		int numTestedProjs = 0;
		
		int ctr = 0;
		
		for(File f : preloadPaths) {
			Helpers.log("Looking for tests in " + f.getName() + " (" + (ctr++) + "/" + numProjs + ")");
			if(UnitTestDetector.containsTest(f)) {
				numTestedProjs++;
				Helpers.log("Found a test!");
			}
		}
		
		float prev = (float)numTestedProjs / numProjs;
		Helpers.log("Test prevalence: " + prev + " (" +numTestedProjs + "/" + numProjs + ")");
	}	
	
	public static void testFrameworksForPreloads() throws Exception {
		File[] preloadPaths = ProjectDownloader.getPreloadPaths();
		
		int numProjs = preloadPaths.length;
		
		int ctr = 0;
		Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
		
		for(File f : preloadPaths) {
			Helpers.log("Looking for test framework in " + f.getName() + " (" + (ctr++) + "/" + numProjs + ")");
			int fw = JavaTestFrameworkDetector.checkForFramework(f);
			if(counts.containsKey(fw))
				counts.put(fw, counts.get(fw)+1);
			else
				counts.put(fw, 1);
		}
		
		Helpers.log("Total project count = " + numProjs);
		for(Integer fw : counts.keySet()) {
			Helpers.log("Framework key=" + fw + " count=" + counts.get(fw));
		}
	}
	

}
