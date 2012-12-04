package org.andreschnabel.jprojectinspector.testprevalence;

import org.andreschnabel.jprojectinspector.Helpers;

import com.google.gson.Gson;


/**
 * Task:
 * 1. Automatically gather list of GitHub-projects written in Java (JavaProjectCollector)
 * 2. For each project
 * 	2.1. Determine if project contains JUnit tests (JUnitTestDetector)
 * 	2.2. If so: increment test project counter
 */
public class Runner {	
	public static void main(String[] args) throws Exception {
		String keyword = "tux";
		int numPages = 1;		
		
		for(String arg : args) {
			if(arg.contains("=")) {
				String[] parts = arg.split("=");
				if(parts[0].equals("keyword"))
					keyword = parts[1];
				else if(parts[1].equals("pages")) {
					numPages = Integer.valueOf(parts[1]);
				}
			}
		}
		
		TestPrevalenceDeterminator tpd = new TestPrevalenceDeterminator();
		TestPrevalenceSummary summary = tpd.determineTestPrevalence(keyword, numPages);
		
		Gson gson = new Gson();
		String summaryStr = gson.toJson(summary);		
		Helpers.writeStrToFile(summaryStr, "tpSummary.json");
	}
}
