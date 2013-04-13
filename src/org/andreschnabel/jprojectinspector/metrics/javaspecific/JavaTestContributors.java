package org.andreschnabel.jprojectinspector.metrics.javaspecific;

import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.GitHelpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public final class JavaTestContributors {
	
	private JavaTestContributors() {}
	
	public static int numTestContribs(File pf) throws Exception {
		List<String> testContribs = new LinkedList<String>();
		traverseForContribs(pf, testContribs);
		return testContribs.size();
	}
	
	public static void traverseForContribs(File pf, List<String> testContribs) throws Exception {
		if(pf.isDirectory()) {			
			for(File f : pf.listFiles()) {
				traverseForContribs(f, testContribs);
			}			
		} else {
			if(pf.getName().endsWith(".java") && UnitTestDetector.isJavaSrcTest(FileHelpers.readEntireFile(pf), pf.getName())) {
				List<String> contribs = GitHelpers.contribNamesForFile(pf);
				for(String contrib : contribs) {
					if(!testContribs.contains(contrib))
						testContribs.add(contrib);
				}
			}
		}
	}



}