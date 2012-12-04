package org.andreschnabel.jprojectinspector.testprevalence;

import java.io.File;
import java.io.IOException;

import org.andreschnabel.jprojectinspector.Helpers;
import org.andreschnabel.jprojectinspector.testprevalence.JavaProjectCollector.ProjectList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Runner {
	
	private static Gson gson;
	
	static {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	static class Options {
		public String keyword = "scientific";
		public int numPages = 1;
		public String listFilename = null;
		public String collectFilename = null;
	}
	
	public static void main(String[] args) throws Exception {
		Options options = parseOptions(args);
		
		// Only collect non-forked java project filenames to given file
		if(options.collectFilename != null) {
			collectProjectNames(options);
			return;
		}
		
		detTestPrevForProjects(options);
	}

	private static Options parseOptions(String[] args) throws Exception {
		Options result = new Options();
		for(String arg : args) {
			if(arg.contains("=")) {
				String[] parts = arg.split("=");
				if(parts[0].equals("keyword"))
					result.keyword = parts[1];
				else if(parts[0].equals("pages"))
					result.numPages = Integer.valueOf(parts[1]);
				else if(parts[0].equals("list"))
					result.listFilename = parts[1];
				else if(parts[0].equals("collectTo"))
					result.collectFilename = parts[1];
				else if(parts[0].equals("summaryFolder")) {
					printMergedSummaries(parts[1]);
					System.exit(0);
				}
			}
		}
		return result;
	}
	
	private static class TestPrevMiniSummary {
		public int numTestedProjects;
		public int numProjectsTotal;
	}

	private static void printMergedSummaries(String dirPath) throws Exception {
		File dir = new File(dirPath);
		if(!dir.exists())
			throw new Exception("Directory doesn't exist: " + dirPath);
		if(!dir.isDirectory())
			throw new Exception("Summary folder path not pointing to dir: " + dirPath);
		
		int totalProjectCount = 0;
		int totalTestProjectCount = 0;
		
		for(File f : dir.listFiles()) {
			if(!f.isDirectory() && f.getName().endsWith(".json")) {
				TestPrevMiniSummary s = gson.fromJson(Helpers.readEntireFile(f), TestPrevMiniSummary.class);
				totalProjectCount += s.numProjectsTotal;
				totalTestProjectCount += s.numTestedProjects;
			}
		}
		
		float testPrev = (((float)totalTestProjectCount / totalProjectCount) * 100.0f);
		System.out.println("Total number of projects: " + totalTestProjectCount);
		System.out.println("Number of projects with tests: " + totalProjectCount);
		System.out.println("Test prevalence = " + testPrev);
	}

	private static void collectProjectNames(Options options) throws Exception, IOException {
		JavaProjectCollector jpc = new JavaProjectCollector();
		ProjectList pl = jpc.collectProjects(options.keyword, options.numPages);
		String plJson = gson.toJson(pl);
		Helpers.writeStrToFile(plJson, options.collectFilename);
	}

	private static void detTestPrevForProjects(Options options) throws Exception {
		TestPrevalenceDeterminator tpd = new TestPrevalenceDeterminator();		
		TestPrevalenceSummary summary;
		
		// Load project list from previously generated file if given
		if(options.listFilename != null) {
			String projListJson = Helpers.readEntireFile(new File(options.listFilename));
			ProjectList projList = gson.fromJson(projListJson, ProjectList.class);
			options.keyword = projList.keyword;
			summary = tpd.determineTestPrevalence(projList);
		} else {
			summary = tpd.determineTestPrevalence(options.keyword, options.numPages);
		}		 
		
		String summaryStr = gson.toJson(summary);		
		Helpers.writeStrToFile(summaryStr, Helpers.capitalize(options.keyword) + "TestPrevalence.json");
	}
}
