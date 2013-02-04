package org.andreschnabel.jprojectinspector.metrics.test;

import java.io.File;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.andreschnabel.jprojectinspector.metrics.test.prevalence.UnitTestDetector;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

public final class TestContributors {
	
	private TestContributors() {}
	
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
				List<String> contribs = contribNamesForFile(pf);
				for(String contrib : contribs) {
					if(!testContribs.contains(contrib))
						testContribs.add(contrib);
				}
			}
		}
	}

	public static List<String> contribNamesForFile(File f) throws Exception {
		//String cmd = "git log --follow --pretty=format:%an -- " + f.getName();
		
		List<String> result = new LinkedList<String>();
		
		ProcessBuilder pb = new ProcessBuilder("git", "log", "--follow", "--pretty=format:%an", "--", f.getName());
		pb.directory(f.getParentFile());
		
		Process p = pb.start();
		InputStreamReader isr = new InputStreamReader(p.getInputStream());
		
		p.waitFor();
		
		StringBuilder output = new StringBuilder();
		int c;
		while((c = isr.read()) != -1) {
			output.append((char) c);
		}
		
		String[] names = output.toString().split("\n");
		for(String name : names) {
			if(name != null && !name.isEmpty()) {
				if(!result.contains(name)) {
					result.add(name);
				}
			}
		}
		
		return result;
	}

}
