package org.andreschnabel.jprojectinspector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.andreschnabel.jprojectinspector.metrics.test.TestPrevalence;
import org.andreschnabel.jprojectinspector.metrics.test.TestPrevalenceSummary;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectList;
import org.andreschnabel.jprojectinspector.utilities.Helpers;
import org.eclipse.egit.github.core.client.GitHubClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Runner {
	
	private static Gson gson;
	private static GitHubClient ghc;

	static {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	static class Options {
		public String keyword = "unknown";
		public int numPages = 1;
		public String listFilename = null;
		public String collectFilename = null;
		public String dictionaryFilename = null;
		public int numProjects = 1;
	}
	
	public static void main(String[] args) throws Exception {
		Options options = parseOptions(args);

		if(ProjectCollector.USE_EGIT && options.listFilename == null) {
			ghc = new GitHubClient();
			String user = Helpers.prompt("Username");
			String pw = Helpers.prompt("Password");
			ghc.setCredentials(user, pw);
		}
		
		// Only collect non-forked java project filenames to given file
		if(options.collectFilename != null) {
			collectProjectNames(options);
			return;
		}
		
		detTestPrevForProjects(options);
		
		//UnitTestDetector utd = new UnitTestDetector();
		//utd.containsTest(new Project("isaaccp","fun-todo"));
	}

	private static Options parseOptions(String[] args) throws Exception {
		Options result = new Options();
		
		for(String arg : args) {
			if(arg.contains("=")) {
				String[] parts = arg.split("=");
				if(parts[0].equals("dict"))
					result.dictionaryFilename = parts[1];
				else if(parts[0].equals("numProjects"))
					result.numProjects = Integer.valueOf(parts[1]);
				else if(parts[0].equals("keyword"))
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
				} else if(parts[0].equals("minimize")) {
					int destSize = Integer.valueOf(Helpers.prompt("Dest size"));
					randomlyMinimizeProjectListFile(parts[1], "minimized" + parts[1], destSize);
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

	private static void collectProjectNames(Options options) throws Exception {
		ProjectCollector jpc = new ProjectCollector(ghc);
		ProjectList pl;
		if(options.dictionaryFilename != null) {
			String[] keywords = randomlyChoseKeywords(options);
			pl = jpc.collectProjects(keywords, options.numPages);
		} else {
			pl = jpc.collectProjects(options.keyword, options.numPages);
		}
		String plJson = gson.toJson(pl);
		Helpers.writeStrToFile(plJson, options.collectFilename);
		System.out.println("Finished collecting project list into: " + options.collectFilename + "!");
	}

	private static void detTestPrevForProjects(Options options) throws Exception {
		TestPrevalence tpd = new TestPrevalence();
		TestPrevalenceSummary summary;
		
		// Load project list from previously generated file if given
		if(options.listFilename != null) {
			String projectListJson = Helpers.readEntireFile(new File(options.listFilename));
			ProjectList projectList = gson.fromJson(projectListJson, ProjectList.class);
			options.keyword = projectList.keyword;
			summary = tpd.determineTestPrevalence(projectList);
		} else {
			if(options.dictionaryFilename == null)
				summary = tpd.determineTestPrevalence(options.keyword, options.numPages, ghc);
			else {
				ProjectCollector jpc = new ProjectCollector(ghc);
				String[] keywords = randomlyChoseKeywords(options);
				ProjectList projectList = jpc.collectProjects(keywords, options.numPages);
				summary = tpd.determineTestPrevalence(projectList);
			}
		}		 
		
		String summaryStr = gson.toJson(summary);		
		Helpers.writeStrToFile(summaryStr, Helpers.capitalize(options.keyword) + "TestPrevalence.json");
	}

	private static String[] randomlyChoseKeywords(Options options) throws Exception {
		String[] dictionary = loadDictionary(options.dictionaryFilename);
		Random r = new Random();

		List<String> usedKeywords = new LinkedList<String>();
		String[] keywords = new String[options.numProjects];
		int randomIndex;
		for(int i=0; i<keywords.length; i++) {
			// don't use keyword twice!
			do {
				randomIndex = r.nextInt(dictionary.length);
			} while(usedKeywords.contains(dictionary[randomIndex]));

			keywords[i] = dictionary[randomIndex];
			usedKeywords.add(keywords[i]);
		}

		return keywords;
	}

	private static String[] loadDictionary(String dictionaryFilename) throws Exception {
		List<String> dict = new LinkedList<String>();
		FileReader fr = new FileReader(dictionaryFilename);
		BufferedReader br = new BufferedReader(fr);
		
		while(br.ready()) {
			String line = br.readLine().trim();
			if(line.length() > 0)
				dict.add(line);
		}
		
		br.close();
		fr.close();
		return dict.toArray(new String[]{});
	}
	
	private static void randomlyMinimizeProjectListFile(String srcFilename, String destFilename, int destSize) throws Exception {
		String projectListJson = Helpers.readEntireFile(new File(srcFilename));
		ProjectList projectList = gson.fromJson(projectListJson, ProjectList.class);
		
		randomlyMinimizeProjectList(projectList, destSize);		
		String outJson = gson.toJson(projectList);
		Helpers.writeStrToFile(outJson, destFilename);
	}
	
	private static void randomlyMinimizeProjectList(ProjectList src, int destSize) throws Exception {
		List<Project> srcProjs = src.projects;
		int initialSize = srcProjs.size();		
		Random r = new Random();
		
		if(initialSize <= destSize) {
			throw new Exception("Dest size must be smaller than orig size for minimize!");
		}
		
		for(int i=0; i<initialSize - destSize; i++) {
			int rval = r.nextInt(srcProjs.size());
			srcProjs.remove(rval);
		}
	}
}
